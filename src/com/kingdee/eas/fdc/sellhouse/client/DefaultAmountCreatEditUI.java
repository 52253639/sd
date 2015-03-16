/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.service.formula.engine.FormulaEngine;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BusTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CalculateTypedefaultEnum;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatEntryInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerCollection;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultCollectionFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultCollectionInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 生成违约金界面
 */
public class DefaultAmountCreatEditUI extends AbstractDefaultAmountCreatEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(DefaultAmountCreatEditUI.class);
    
    public DefaultAmountCreatEditUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception 
	{
    	this.kdtParent.checkParsed();
    	this.kdtParent.getStyleAttributes().setLocked(true);
	    this.kdtParent.getColumn("subDeAmount").getStyleAttributes().setLocked(false);
		
	  //计划费用
    	ICellEditor bigDecimalEditor = CommerceHelper.getKDFormattedTextDecimalEditor();
    	kdtParent.getColumn("carryAmount").setEditor(bigDecimalEditor);
    	kdtParent.getColumn("carryAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	kdtParent.getColumn("carryAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	kdtParent.getColumn("refDeAmount").setEditor(bigDecimalEditor);
    	kdtParent.getColumn("refDeAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	kdtParent.getColumn("refDeAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	kdtParent.getColumn("subDeAmount").setEditor(bigDecimalEditor);
    	kdtParent.getColumn("subDeAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	kdtParent.getColumn("subDeAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	kdtParent.getColumn("contractAmount").setEditor(bigDecimalEditor);
    	kdtParent.getColumn("contractAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	kdtParent.getColumn("contractAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	kdtParent.getColumn("argAmount").setEditor(bigDecimalEditor);
    	kdtParent.getColumn("argAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	kdtParent.getColumn("argAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	
    	kdtParent.getColumn("busType").getStyleAttributes().setHided(true);
    	
		super.onLoad();
		this.txtNumber.setRequired(true);
		this.prmtDefCalFormula.setRequired(true);
		
	    this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.menuBiz.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCalculator.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionAudit.setVisible(false);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", editData.getProject().getId()));
		view.setFilter(filter);
		this.prmtDefCalFormula.setEntityViewInfo(view);
		
		this.pkDefCalDate.setEnabled(false);
		this.prmtDefCalFormula.setRequired(true);
	}
   
	 protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtDefCalFormula);
	}
	/**
     * 选择计算公式计算违约金
     */
	protected void prmtDefCalFormula_dataChanged(DataChangeEvent e) throws Exception {
		if (!STATUS_VIEW.equals(this.oprtState)) {
			if (this.prmtDefCalFormula.getValue() != null) {
				DefaultCollectionInfo dcInfo = (DefaultCollectionInfo) this.prmtDefCalFormula.getValue();
				BigDecimal cal = dcInfo.getScale();
				
				for(int i = 0; i < kdtParent.getRowCount(); i++){
					IRow row = kdtParent.getRow(i);
					
					DefaultAmountCreatEntryInfo entry=(DefaultAmountCreatEntryInfo)row.getUserObject();
					DefaultAmountMangerEntryInfo entryInfo = entry.getDefaultAmountMangerEntry();
					TranBusinessOverViewInfo tran=entry.getTranBusinessOverView();
					SignPayListEntryInfo signEntryInfo =entry.getSignPayListEntry();
					
					BigDecimal argAmount = entryInfo.getArgAmount();
					if (argAmount == null) {
						argAmount = FDCHelper.ZERO;
					}
					if (cal == null) {
						cal = FDCHelper.ZERO;
					}
					if (CalculateTypedefaultEnum.GENERAL.equals(dcInfo.getCalculateType())) {
						entryInfo.setReferAmount(argAmount.multiply(cal));// 参考违约金计算
					} else if (CalculateTypedefaultEnum.PATULOUS.equals(dcInfo.getCalculateType())) { // 扩展模式
						Map valuemap = new HashMap();
						valuemap.put("DefaultAmountMangerEntry", entryInfo);
						String stdFormulaScript = dcInfo.getStdFormulaScript();
						if(stdFormulaScript==null){
							entryInfo.setReferAmount(FDCHelper.ZERO);
						}else{
							Object result = FormulaEngine.runFormula(stdFormulaScript, valuemap);
							entryInfo.setReferAmount(new BigDecimal(result != null ? result.toString() : "0"));
						}
					}
					entryInfo.setCarryAmount(entryInfo.getReferAmount().subtract(entryInfo.getSubDeAmount()==null?FDCHelper.ZERO:entryInfo.getSubDeAmount()));
					entryInfo.getHead().getEntry().get(entryInfo.getId()).setCarryAmount(entryInfo.getReferAmount().subtract(entryInfo.getSubDeAmount()==null?FDCHelper.ZERO:entryInfo.getSubDeAmount()));

					tran.setAppAmount(entryInfo.getCarryAmount());
					signEntryInfo.setAppAmount(entryInfo.getCarryAmount());
					
					row.getCell("refDeAmount").setValue(entryInfo.getReferAmount());
					row.getCell("carryAmount").setValue(entryInfo.getReferAmount().subtract(entryInfo.getSubDeAmount()==null?FDCHelper.ZERO:entryInfo.getSubDeAmount()));
				}
			}
		}
	}
	/**
     * 改变减免违约金
     */
   protected void kdtParent_editStopped(KDTEditEvent e) throws Exception {
	   int rowIndex = kdtParent.getSelectManager().getActiveRowIndex();
	   IRow row     = kdtParent.getRow(rowIndex);
	   BigDecimal refDeAmounts =(BigDecimal)row.getCell("refDeAmount").getValue()==null?FDCHelper.ZERO:(BigDecimal)row.getCell("refDeAmount").getValue();
	   BigDecimal subDeAmounts =(BigDecimal)row.getCell("subDeAmount").getValue()==null?FDCHelper.ZERO:(BigDecimal)row.getCell("subDeAmount").getValue();
	   
	   DefaultAmountCreatEntryInfo entry=(DefaultAmountCreatEntryInfo)row.getUserObject();
	   DefaultAmountMangerEntryInfo entryInfo = entry.getDefaultAmountMangerEntry();
	   TranBusinessOverViewInfo tran=entry.getTranBusinessOverView();
	   SignPayListEntryInfo signEntryInfo =entry.getSignPayListEntry();
	   
	   entryInfo.setSubDeAmount(subDeAmounts);
	   entryInfo.getHead().getEntry().get(entryInfo.getId()).setSubDeAmount(subDeAmounts);
		
	   entryInfo.setCarryAmount(refDeAmounts.subtract(subDeAmounts));
	   entryInfo.getHead().getEntry().get(entryInfo.getId()).setCarryAmount(refDeAmounts.subtract(subDeAmounts));
	   
	   tran.setAppAmount(entryInfo.getCarryAmount());
	   signEntryInfo.setAppAmount(entryInfo.getCarryAmount());
	   
	   row.getCell("carryAmount").setValue(refDeAmounts.subtract(subDeAmounts));
   }
   /**
    * 改变分录减免违约金，同时改变违约金管理对象的减免违约金，结转违约金
    */
   public void setDefaultAmountMangerCollection(IRow row){
	   String tracsationId = (String)row.getCell("remak").getValue();
	   BigDecimal subDeAmounts =(BigDecimal)row.getCell("subDeAmount").getValue();
	   BigDecimal carryAmount =(BigDecimal)row.getCell("carryAmount").getValue();
	   DefaultAmountMangerCollection damColl = (DefaultAmountMangerCollection)getUIContext().get("damColl");
	   for(int i=0;i<damColl.size();i++){
			DefaultAmountMangerInfo  defaultInfo = damColl.get(i);
			if(tracsationId.equals(defaultInfo.getTransaction().getId().toString())){
				defaultInfo.setSubDeAmount(subDeAmounts);
				defaultInfo.setCarryAmount(carryAmount);
				break;
			}
	   }
   }
	protected void attachListeners() {
		addDataChangeListener(this.prmtDefCalFormula);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.prmtDefCalFormula);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return DefaultAmountCreatFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return this.kdtParent;
	}
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
    protected IObjectValue createNewData() {
    	DefaultAmountCreatInfo createInfo = (DefaultAmountCreatInfo)getUIContext().get("createInfo");
		return createInfo ;
	}
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState(STATUS_VIEW);
		super.destroyWindow();
	}
    public SelectorItemCollection getSelectors()
    {
    	SelectorItemCollection sic=super.getSelectors();
    	sic.add("parent.tranBusinessOverView.*");
    	sic.add("parent.tranBusinessOverView.moneyDefine.*");
    	sic.add("parent.signPayListEntry.*");
    	sic.add("parent.signPayListEntry.moneyDefine.*");
    	sic.add("parent.defaultAmountMangerEntry.*");
    	sic.add("parent.defaultAmountMangerEntry.moneyDefine.*");
    	sic.add("parent.defaultAmountMangerEntry.tranOverView.*");
    	sic.add("parent.defaultAmountMangerEntry.head.*");
    	sic.add("parent.defaultAmountMangerEntry.head.project.*");
    	sic.add("parent.defaultAmountMangerEntry.head.transaction.*");
    	
    	sic.add("parent.defaultAmountMangerEntry.head.room.productType.*");
    	return sic;
    }
}
