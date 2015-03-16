/**
* output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMultiLangArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.DeductBillEntryCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.fdc.finance.DeductBillInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * 
 * 描述:扣款单编辑界面
 * 
 * @author liupd date:2006-10-10
 *         <p>
 * @version EAS5.1.3
 */
public class DeductBillEditUI extends AbstractDeductBillEditUI {
	private static final String REMARK = "remark";

	private static final String DEDUCT_AMT = "deductAmt";

	private static final String DEDUCT_ITEM = "deductItem";

	private static final String CONTRACT_ID = "contractId";

	private static final String CONTRACT_NUMBER = "contractNumber";

	private static final Logger logger = CoreUIObject
			.getLogger(DeductBillEditUI.class);

	//private CurProjectInfo projInfo = null;
	private boolean hasSettle ;
	
	/**
	 * output class constructor
	 */
	public DeductBillEditUI() throws Exception {
		super();
		jbInit() ;
	}

	private void jbInit() {	    
		titleMain = getUITitle();
	}
	/**
	 * 
	 * 描述：初始化控件
	 * @author:liupd
	 * 创建时间：2006-10-17 <p>
	 */
	private void initCtrl() {
		// 设置扣款类型的F7
		KDBizPromptBox kdtEntrys_deductType_PromptBox = new KDBizPromptBox();
		kdtEntrys_deductType_PromptBox
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.DeductTypeQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		//过滤掉甲供类型
		filter.getFilterItems().add(new FilterItemInfo("id", DeductTypeInfo.partAMaterialType, CompareType.NOTEQUALS));
		view.setFilter(filter);
		kdtEntrys_deductType_PromptBox.setEntityViewInfo(view);
		kdtEntrys_deductType_PromptBox.setVisible(true);
		kdtEntrys_deductType_PromptBox.setEditable(true);
		kdtEntrys_deductType_PromptBox.setDisplayFormat("$number$");
		kdtEntrys_deductType_PromptBox.setEditFormat("$number$");
		kdtEntrys_deductType_PromptBox.setCommitFormat("$number$");
		KDTDefaultCellEditor kdtEntrys_deductType_CellEditor = new KDTDefaultCellEditor(
				kdtEntrys_deductType_PromptBox);
		this.kdtEntrys.getColumn("deductType").setEditor(
				kdtEntrys_deductType_CellEditor);
		ObjectValueRender kdtEntrys_deductType_OVR = new ObjectValueRender();
		kdtEntrys_deductType_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtEntrys.getColumn("deductType").setRenderer(
				kdtEntrys_deductType_OVR);
		ICellEditor cEditor = this.kdtEntrys.getColumn("deductOriginalAmt").getEditor();

		if (cEditor != null) {
			KDFormattedTextField amtTextField = (KDFormattedTextField) cEditor
					.getComponent();
			amtTextField.setPrecision(2);
			amtTextField.setHorizontalAlignment(JTextField.RIGHT);
			amtTextField.setMaximumValue(FDCHelper.MAX_VALUE);
			amtTextField.setMinimumValue(FDCHelper.MIN_VALUE);
		}

		cEditor = this.kdtEntrys.getColumn(DEDUCT_AMT).getEditor();

		if (cEditor != null) {
			KDFormattedTextField amtTextField = (KDFormattedTextField) cEditor
					.getComponent();
			amtTextField.setPrecision(2);
			amtTextField.setHorizontalAlignment(JTextField.RIGHT);
			amtTextField.setMaximumValue(FDCHelper.MAX_VALUE);
			amtTextField.setMinimumValue(FDCHelper.MIN_VALUE);
		}
		
		ICellEditor uEditor = this.kdtEntrys.getColumn("deductUnit").getEditor();
		if(uEditor != null) {
			KDBizPromptBox uBox = (KDBizPromptBox)uEditor.getComponent();
//			uBox.setQueryInfo(FDCConstants.SUPPLIER_F7_QUERY);
//			uBox.setEntityViewInfo(FDCClientUtils.getApprovedSupplierView());
			String cuId = this.curProject!=null?curProject.getCU().getId().toString():
				SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
			FDCClientUtils.initSupplierF7(this, uBox, cuId);
		}

		FDCHelper.formatTableNumber(kdtEntrys, DEDUCT_AMT);
		FDCHelper.formatTableNumber(kdtEntrys, "exrate");
		kdtEntrys.getSelectManager().getSelectRange();//.setActiveRowIndex(0);//.getEditManager().editCellAt(0, 0);
		
		this.chkconTypeBefContr.setVisible(false);
	}

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.finance.client.DeductBillEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.finance.DeductBillFactory
				.getRemoteInstance();
	}

	/**
	 * output getDetailTable method
	 */
	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		com.kingdee.eas.fdc.finance.DeductBillEntryInfo deductBillEntryInfo = new com.kingdee.eas.fdc.finance.DeductBillEntryInfo();
//		deductBillEntryInfo.setDeductDate(FDCHelper.getCurrentDate());
		try {
			deductBillEntryInfo.setDeductDate(new Date(FDCDateHelper.getServerTimeStamp().getTime()));
		} catch (BOSException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		deductBillEntryInfo.setHasApplied(false);
		return deductBillEntryInfo;
	}

	protected void fetchInitData() throws Exception{
		super.fetchInitData();
		
		//工程项目
		BOSUuid projId = (BOSUuid) getUIContext().get("projectId");
		if(projId != null) {
			curProject = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(projId));
		}
		
	}
	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		DeductBillInfo objectValue = new DeductBillInfo();
		objectValue.setCreator((SysContext.getSysContext().getCurrentUserInfo()));
//		objectValue.setCreateTime(new Timestamp(new Date().getTime()));
		try {
			objectValue.setCreateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		objectValue.setSourceType(SourceTypeEnum.ADDNEW);
		
		//工程项目
		if(curProject != null) {		
			objectValue.setCurProject(curProject);

			//设置管理单元
			objectValue.setCU(curProject.getCU());			
			FullOrgUnitInfo costOrg = FDCClientUtils.getCostOrgByProj(curProject.getId().toString());

			objectValue.setOrgUnit(costOrg);
		}

		return objectValue;
	}

	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		/* TODO 自动生成方法存根 */
		super.kdtEntrys_editStopped(e);
		if (e.getColIndex() == kdtEntrys.getColumn(CONTRACT_NUMBER).getColumnIndex()) {
			
			if(e.getValue() != null){
				ICell nameCell = kdtEntrys.getCell(e.getRowIndex(), "contractName");
				if (e.getValue() instanceof ContractBillInfo) {
					ContractBillInfo contractInfo = (ContractBillInfo) e.getValue();
					nameCell.setValue(contractInfo.getName());
					if(kdtEntrys.getCell(e.getRowIndex(), "deductUnit").getValue()==null){
						kdtEntrys.getCell(e.getRowIndex(), "deductUnit").
								setValue(contractInfo.getPartB());
					}
				} else {
					nameCell.setValue(((ContractWithoutTextInfo) e.getValue())
							.getName());
				}
				CurrencyInfo currency = getCurrency(((IObjectValue)e.getValue()).getString("id"));
				getDetailTable().getCell(e.getRowIndex(), "currency").setValue(currency.getName());
				getDetailTable().getCell(e.getRowIndex(),"exrate").setValue(((ContractBillInfo) e.getValue()).getExRate());
				//如果为本位币，则不允许修改汇率
				if(baseCurrency != null && baseCurrency.getId().equals(currency.getId())){
					getDetailTable().getCell(e.getRowIndex(), "exrate")
						.getStyleAttributes().setLocked(true);
				}else{
					getDetailTable().getCell(e.getRowIndex(), "exrate")
					.getStyleAttributes().setLocked(false);
				}
			}else{
				ICell nameCell = kdtEntrys.getCell(e.getRowIndex(), "contractName");
				nameCell.setValue(null);
				
				getDetailTable().getCell(e.getRowIndex(), "currency").setValue(null);
			}
		}
		
		if (e.getColIndex() == kdtEntrys.getColumn("deductOriginalAmt").getColumnIndex() && e.getValue() != null) {
			if(getDetailTable().getCell(e.getRowIndex(),"exrate")!= null&&getDetailTable().getCell(e.getRowIndex(),"exrate").getValue() instanceof BigDecimal) {
				if(e.getValue() instanceof BigDecimal){
					BigDecimal lAmount = ((BigDecimal)e.getValue()).multiply((BigDecimal)getDetailTable().getCell(e.getRowIndex(),"exrate").getValue());
		    		getDetailTable().getCell(e.getRowIndex(),DEDUCT_AMT).setValue(lAmount);
				}
	    	}
			sumTable();
		}
		if (e.getColIndex() == kdtEntrys.getColumn("exrate").getColumnIndex()) {
			BigDecimal exRate = FDCHelper.toBigDecimal(e.getValue());
			BigDecimal rate = FDCHelper.ZERO;
			Object value = kdtEntrys.getCell(e.getRowIndex(),CONTRACT_NUMBER).getValue();
			if(value!=null&&value instanceof ContractBillInfo){
				ContractBillInfo info = (ContractBillInfo)value;
				rate = info.getExRate();
			}
			if(exRate.compareTo(FDCHelper.ZERO) == 0){
				getDetailTable().getCell(e.getRowIndex(),"exrate").setValue(rate);
				getDetailTable().getEditManager().editCellAt(e.getRowIndex(),e.getColIndex());
				MsgBox.showWarning(this,"汇率不允许为空或者“0”值!");
				SysUtil.abort();
			}
			if(getDetailTable().getCell(e.getRowIndex(),"deductOriginalAmt")!= null&&getDetailTable().getCell(e.getRowIndex(),"deductOriginalAmt").getValue() instanceof BigDecimal) {
				if(e.getValue() instanceof BigDecimal){
					BigDecimal lAmount = ((BigDecimal)e.getValue()).multiply((BigDecimal)getDetailTable().getCell(e.getRowIndex(),"deductOriginalAmt").getValue());
		    		getDetailTable().getCell(e.getRowIndex(),DEDUCT_AMT).setValue(lAmount);
				}
	    	}
			sumTable();
		}
	}

	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
        SelectorItemCollection sic = new SelectorItemCollection();
        
        sic.add(new SelectorItemInfo("entrys.deductUnit.name"));
        sic.add(new SelectorItemInfo("entrys.deductUnit.number"));
        sic.add(new SelectorItemInfo("entrys.deductType.name"));
        sic.add(new SelectorItemInfo("entrys.deductType.number"));
        
        sic.add(new SelectorItemInfo("entrys.deductItem"));
        sic.add(new SelectorItemInfo("entrys.deductAmt"));
        sic.add(new SelectorItemInfo("entrys.deductOriginalAmt"));
        sic.add(new SelectorItemInfo("entrys.deductDate"));
        sic.add(new SelectorItemInfo("entrys.remark"));
        sic.add(new SelectorItemInfo("entrys.contractId"));
        sic.add(new SelectorItemInfo("entrys.exRate"));
        
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("conTypeBefContr"));
        sic.add(new SelectorItemInfo("auditTime"));
            
		sic.add("curProject.id");
		sic.add("curProject.CU.id");
		sic.add("curProject.orgUnit.id");
		sic.add("orgUnit.id");
		sic.add("state");
		sic.add("fiVouchered");
		
		return sic;
	}

	private final static String DEDUCTTYPE = "deductType";

	private final static String CONTRACTNUMBER = CONTRACT_NUMBER;

	private final static String DEDUCTITEM = DEDUCT_ITEM;

	protected void chkconTypeBefContr_itemStateChanged(ItemEvent e)
			throws Exception {
		/* TODO 自动生成方法存根 */
		super.chkconTypeBefContr_itemStateChanged(e);

		boolean selected = e.getStateChange() == ItemEvent.SELECTED;
		
		
		updateTabHeader(selected);
	}

	private void updateTabHeader(boolean selected) {
		int decuctTypeIdx = kdtEntrys.getColumn(DEDUCTTYPE).getColumnIndex();
		int contractNumberIdx = kdtEntrys.getColumn(CONTRACTNUMBER)
				.getColumnIndex();
		int deductItemIdx = kdtEntrys.getColumn(DEDUCTITEM).getColumnIndex();
		
		if (selected) {
			kdtEntrys.moveColumn(decuctTypeIdx, contractNumberIdx);
		} else {
			kdtEntrys.moveColumn(decuctTypeIdx, deductItemIdx);
		}
	}
	
	public void onShow() throws Exception {
				
		// 显示info的数据到表格
		super.onShow();
		
		tHelper.getDisabledTables().add(this.getDetailTable());
		enableExportExcel(getDetailTable());
		getDetailTable().getSortMange().setSortAuto(false);
		
		this.menuItemPrint.setEnabled(true);
		this.menuItemPrint.setVisible(true);
		this.menuItemPrintPreview.setEnabled(true);
		this.menuItemPrintPreview.setVisible(true);
		this.btnPrint.setEnabled(true);
		this.btnPrint.setVisible(true);
		this.btnPrintPreview.setEnabled(true);
		this.btnPrintPreview.setVisible(true);
	}
	
	protected void updateButtonStatus() {
		// TODO Auto-generated method stub
		super.updateButtonStatus();
		String oprtType = getOprtState();
		if(oprtType.equals(STATUS_VIEW) || oprtType.equals(STATUS_FINDVIEW)) {
			kdtEntrys.getStyleAttributes().setLocked(true);
	
			actionInsertLine.setEnabled(false);
	        actionAddLine.setEnabled(false);
	        actionRemoveLine.setEnabled(false);
	        btnInsertLine.setEnabled(false);
	        btnAddLine.setEnabled(false);
	        btnRemoveLine.setEnabled(false);
		}
		else {
			kdtEntrys.getStyleAttributes().setLocked(false);
		}
	//	kdtEntrys.getColumn("currency").getStyleAttributes().setLocked(true);
		
		if(STATUS_ADDNEW.equals(oprtType) || STATUS_EDIT.equals(oprtType)) {
			setNumberEditable();
		}
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		initEntryTable();
		txtNumber.setMaxLength(80);

		//从合同汇总信息界面而来
		if(this.getUIContext().get("disableSplit")!=null){
			this.actionAudit.setVisible(false);
			this.actionUnAudit.setVisible(false);
		}

		tHelper.getDisabledTables().add(this.getDetailTable());
		enableExportExcel(getDetailTable());
		//getDetailTable().getSortMange().setSortAuto(false);
		/*
		 * 设置合同编号的F7
		 */
		String projId = null;
		if (STATUS_ADDNEW.equals(getOprtState())) {
			projId = getUIContext().get("projectId").toString();

		} else if (STATUS_EDIT.equals(getOprtState()) || STATUS_VIEW.equals(getOprtState())) {
			projId = editData.getCurProject().getId().toString();
		}
		
		KDBizPromptBox kdtEntrys_contractNumber_PromptBox = new KDBizPromptBox();
		//无文本已经已经结算的合同不能做变更
		kdtEntrys_contractNumber_PromptBox
				.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ContractBill4DeductQuery");
		kdtEntrys_contractNumber_PromptBox.setVisible(true);
		kdtEntrys_contractNumber_PromptBox.setEditable(true);
		kdtEntrys_contractNumber_PromptBox.setDisplayFormat("$number$");
		kdtEntrys_contractNumber_PromptBox.setEditFormat("$number$");
		kdtEntrys_contractNumber_PromptBox.setCommitFormat("$number$");
		
		if (projId != null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("curProject.id", projId));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			
			//filter.getFilterItems().add(new FilterItemInfo("hasSettled", Boolean.TRUE));			
			//filter.getFilterItems().add(new FilterItemInfo("isCoseSplit",Boolean.FALSE));
//			view.getSelector().add("contractBill.partB.id");
//			view.getSelector().add("contractBill.partB.name");
			//filter.setMaskString("#0 and #1 and (#2 or #3)");
			view.setFilter(filter);
			
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("name");
			sic.add("number");
			sic.add("partB.id");
			sic.add("partB.name");
			sic.add("exRate");
			kdtEntrys_contractNumber_PromptBox.setEntityViewInfo(view);
			kdtEntrys_contractNumber_PromptBox.setSelectorCollection(sic);
		}
		
		KDTDefaultCellEditor kdtEntrys_contractNumber_CellEditor = new KDTDefaultCellEditor(
				kdtEntrys_contractNumber_PromptBox);
		this.kdtEntrys.getColumn(CONTRACT_NUMBER).setEditor(
				kdtEntrys_contractNumber_CellEditor);
		ObjectValueRender kdtEntrys_contractNumber_OVR = new ObjectValueRender();
		kdtEntrys_contractNumber_OVR.setFormat(new BizDataFormat("$number$"));
		this.kdtEntrys.getColumn(CONTRACT_NUMBER).setRenderer(
				kdtEntrys_contractNumber_OVR);
		
		pkauditTime.setEnabled(false);

		initCtrl();
		
		setNumberEditable();
		
		actionSubmitOption.setEnabled(false);
		actionSubmitOption.setVisible(false);
		
		handleOldData();		
		afterPressDeleteButton();
		
		/**
		 * 用于物业保修验收单下查扣款单，透过没有合同权限而导致看不到数据的问题
		 */
		if(getUIContext().get("source") != null && getUIContext().get("source") instanceof String)
		{
			String source = String.valueOf(getUIContext().get("source"));
			if(source.equals("PpmRepairFinishReviewListUI"))
			{
				this.actionAddNew.setEnabled(false);
				this.actionEdit.setEnabled(false);
				this.actionRemove.setEnabled(false);
				
				this.actionCopy.setEnabled(false);
			}
		}
		
	}

	/**
	 * 初始化分录表格，如样式、是否支持自动换行等
	 * @author owen_wen 2010-11-24
	 */
	private void initEntryTable() {
		kdtEntrys.setColumnMoveable(true);

		kdtEntrys.getColumn(CONTRACT_NUMBER).getStyleAttributes().setBackground(
				FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);
		kdtEntrys.getColumn("contractName").getStyleAttributes().setBackground(
				FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);
		kdtEntrys.getColumn("deductUnit").getStyleAttributes().setBackground(FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);
		kdtEntrys.getColumn("deductType").getStyleAttributes().setBackground(FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);
		kdtEntrys.getColumn(DEDUCT_ITEM).getStyleAttributes().setBackground(FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);
		kdtEntrys.getColumn(DEDUCT_AMT).getStyleAttributes().setBackground(FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);
		kdtEntrys.getColumn("deductDate").getStyleAttributes().setBackground(FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);
		kdtEntrys.getColumn("currency").getStyleAttributes().setBackground(new Color(0xE8E8E3));

		kdtEntrys.getColumn(REMARK).setWidth(250);
		kdtEntrys.getColumn(REMARK).getStyleAttributes().setWrapText(true);
		this.detailTableAutoFitRowHeight();
		
		kdtEntrys.addKDTEditListener(new KDTEditListener(){

			public void editCanceled(KDTEditEvent e) {}

			public void editStarted(KDTEditEvent e) {}

			public void editStarting(KDTEditEvent e) {}

			public void editStopped(KDTEditEvent e) {	
				KDTableHelper.autoFitRowHeight(kdtEntrys,e.getRowIndex(),5);
			}

			public void editStopping(KDTEditEvent e) {}

			public void editValueChanged(KDTEditEvent e) {}			
		});
	}
	
	/**
	 * 根据remark列的内容，自适应行高
	 * @author owen_wen 2010-11-25  
	 */
	private void detailTableAutoFitRowHeight() {
		for (int i = 0; i< kdtEntrys.getRowCount(); i++) {			
			KDTableHelper.autoFitRowHeight(kdtEntrys, i, 5);
			// 设置 remark 列的样式
			KDMultiLangArea textField = new KDMultiLangArea();
			textField.setMaxLength(500);
			textField.setAutoscrolls(true);
			textField.setEditable(true);
			KDTDefaultCellEditor remarkEditor = new KDTDefaultCellEditor(textField);
			if (this.kdtEntrys.getRow(i).getCell(REMARK).getValue() != null)
				textField.setDefaultLangItemData(this.kdtEntrys.getRow(i).getCell(REMARK).getValue().toString());
			this.kdtEntrys.getRow(i).getCell(REMARK).setEditor(remarkEditor);			
		}
	}

	private void addTotalRow() {
		IRow totalRow = getDetailTable().addRow();
		totalRow.getCell(DEDUCT_ITEM).setValue(FDCClientUtils.getRes("total"));
		totalRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		totalRow.getStyleAttributes().setLocked(true);
		sumTable();
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.detailTableAutoFitRowHeight();
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		this.detailTableAutoFitRowHeight();
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
		this.detailTableAutoFitRowHeight();
	}
	
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionInsertLine_actionPerformed(e);
		this.detailTableAutoFitRowHeight();
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {

		//保存不进行任何校验
		if(isSaveAction()) {
			verifyInputForSave();
			return;
		}

		FDCClientVerifyHelper.verifyRequire(this);

		verifyInput(this, kdtEntrys, CONTRACT_NUMBER);
		verifyInput(this, kdtEntrys, "deductType");
		verifyInput(this, kdtEntrys, "deductUnit");
		verifyInput(this, kdtEntrys, DEDUCT_ITEM);
		verifyInput(this, kdtEntrys, DEDUCT_AMT);
		verifyInput(this, kdtEntrys, "deductDate");
		verifyInput(this, kdtEntrys, "exrate");
		if(kdtEntrys.getRowCount() < 2) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("atLeastOneEntry"));
			SysUtil.abort();
		}

		super.verifyInput(e);
	}

	/**
	 * 校验kdtable单元格是否为空
	 * @param ui
	 * @param kdtEntries
	 * @param i 具体某一列的索引
	 */
	public void verifyInput(CoreUIObject ui, KDTable kdtEntries, String key) 
	{
		//是否存在非罚款的扣款类型
		boolean  exitDeductType = false ;
		//存在结算的合同
		boolean hasSettled = false;
		boolean hasNoSettled = false;
		
	    IRow row = null;
	    for(int j=0; j<kdtEntries.getRowCount() - 1; j++)
	    {
	        row = kdtEntries.getRow(j);
	        FDCClientVerifyHelper.verifyInput(ui, kdtEntries, row, key);
	        if(key.equals("exrate")){
	        	BigDecimal exrate = FDCHelper.toBigDecimal(row.getCell(key).getValue());
	        	if(exrate.compareTo(FDCHelper.ZERO)==0){
	        		MsgBox.showWarning(this,"汇率不允许为空或者“0”值!");
	        		SysUtil.abort();
	        	}
	        }
	        //如果是编码列,那么检查合同是否存在已经结算的
	        if(CONTRACT_NUMBER.equals(key) && (row.getCell(key).getValue() instanceof ContractBillInfo)){
	        	ContractBillInfo billInfo = (ContractBillInfo)row.getCell(key).getValue();
	        	if(billInfo.isHasSettled()){
	        		hasSettled = true ;
	        	}else{
	        		hasNoSettled = true ;
	        	}
	        	
	        	if(hasSettled){
		        	DeductTypeInfo deductType = (DeductTypeInfo) row.getCell("deductType").getValue();
		        	if(deductType!=null){
		        		if(!"003".equals(deductType.getNumber())){
		        			exitDeductType = true;      			
		        		}
		        	}
	        	}
	        	
	        }
	    }
	    //去除对已结算合同的限制
//	    //分录同时存在已结算合同和未结算合同
//	    if(hasSettled && hasNoSettled){
//	    	MsgBox.showWarning(this,"同时存在已经结算的合同和没有结算的合同！");
//	    	SysUtil.abort();
//	    }
	    
//	    if(hasSettled && exitDeductType){
//	    	MsgBox.showWarning(this,"存在已经结算的合同和不是罚款的扣款类型");
//	    	SysUtil.abort();
//	    }
	    //如果存在已结算合同
//	    if(hasSettled){
//	    	MsgBox.showWarning(this,"存在已经结算的合同!");
//	    	SysUtil.abort();
//	    }
	}
	
	/**
	 * 增加单据行后的相关处理
	 */
	protected void afterAddLine(KDTable table, IObjectValue lineData) {
		int lastRowIdx = table.getRowCount() - 3;
		if (lastRowIdx >= 0) {
			int curRowIdx = lastRowIdx + 1;
			table.getCell(curRowIdx, CONTRACT_NUMBER).setValue(
					table.getCell(lastRowIdx, CONTRACT_NUMBER).getValue());
			table.getCell(curRowIdx, "contractName").setValue(
					table.getCell(lastRowIdx, "contractName").getValue());
			table.getCell(curRowIdx, "deductType").setValue(
					table.getCell(lastRowIdx, "deductType").getValue());
			
			table.getCell(curRowIdx, "currency").setValue(
					table.getCell(lastRowIdx, "currency").getValue());
			table.getCell(curRowIdx, "exrate").setValue(
					table.getCell(lastRowIdx, "exrate").getValue());
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();
		actionTraceDown.setVisible(true);
		actionTraceDown.setEnabled(true);
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	protected Map getHalfFullContractInfo(Set paramIdSet) throws BOSException, UuidException, SQLException
	{
		Map contractMap = new HashMap();
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select conBill.Fid as contractId, conBill.Fnumber as contractNum, conBill.Fname as contractName, conBill.Fstate as state, ");
    	builder.appendSql(" conbill.fgrtamount as amount, sup.fid as supId, sup.fnumber as supNumber,sup.fname_l2 as supName, ");
    	builder.appendSql(" prj.fid as prjId, prj.fnumber as prjNumber, prj.fname_l2 as prjName, org.fname_l2 as orgName, ");
    	builder.appendSql(" cur.fid as curId, cur.fnumber as curNumber, cur.fname_l2 as curName, conBill.FExRate as exRate ");
    	builder.appendSql(" from t_con_contractbill conBill ");
    	builder.appendSql(" left outer join T_BD_Supplier sup on sup.fid = conBill.Fpartbid ");
    	builder.appendSql(" left outer join T_FDC_CurProject prj on prj.fid = conBill.Fcurprojectid ");
    	builder.appendSql(" left outer join T_BD_Currency cur on cur.fid = conBill.Fcurrencyid ");
    	builder.appendSql(" left outer join T_ORG_BaseUnit org on org.fid = conBill.Forgunitid ");
    	
    	builder.appendSql(" where ");
    	builder.appendParam("conbill.fid", paramIdSet.toArray());
 
    	IRowSet rowSet = builder.executeQuery();
    	
    	if(rowSet.size()>0){
			while(rowSet.next()){
				ContractBillInfo billInfo = new ContractBillInfo();
		    	
		    	billInfo.setId(BOSUuid.read(rowSet.getString("contractId")));
		    	billInfo.setNumber(rowSet.getString("contractNum"));
		    	billInfo.setName(rowSet.getString("contractName"));
		    	
		    	billInfo.setGrtAmount(rowSet.getBigDecimal("amount"));
		    	billInfo.setExRate(rowSet.getBigDecimal("exRate"));
		    	
		    	CurrencyInfo currency = new CurrencyInfo();
		    	currency.setId(BOSUuid.read(rowSet.getString("curId")));
		    	currency.setNumber(rowSet.getString("curNumber"));
		    	currency.setName(rowSet.getString("curName"));
		    	
		    	billInfo.setCurrency(currency);
		    	
		    	SupplierInfo supInfo = new SupplierInfo();
		    	supInfo.setId(BOSUuid.read(rowSet.getString("supId")));
		    	supInfo.setNumber(rowSet.getString("supNumber"));
		    	supInfo.setName(rowSet.getString("supName"));
		    	
		    	billInfo.setPartB(supInfo);
		    	
		    	contractMap.put(billInfo.getId().toString(), billInfo);
			}
		}
		
		return contractMap ;
	}

	public void loadFields() {
		super.loadFields();
		if(STATUS_ADDNEW.equals(this.getOprtState())){
			this.menuBiz.setVisible(false);
		}
		Set idSet = new HashSet();
		DeductBillEntryCollection entrys = editData.getEntrys();
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			DeductBillEntryInfo element = (DeductBillEntryInfo) iter.next();
			idSet.add(element.getContractId());
		}
		Map contractMap = new HashMap();
		
		/**
		 * 用于物业保修验收单下查扣款单，透过没有合同权限而导致看不到数据的问题
		 */
		if(getUIContext().get("source") != null && getUIContext().get("source") instanceof String)
		{
			String source = String.valueOf(getUIContext().get("source"));
			if(source.equals("PpmRepairFinishReviewListUI"))
			{
				try {
					contractMap = getHalfFullContractInfo(idSet);
				} catch (BOSException e) {
					this.handleException(e);
				} catch (UuidException e) {
					this.handleException(e);
				} catch (SQLException e) {
					this.handleException(e);
				}
			}
		}
		else
		{
			contractMap = ContractClientUtils.getContractMap(idSet);
		}
		
		int count = getDetailTable().getRowCount();
		
		for (int i = 0; i < count; i++) {
			String id = (String)getDetailTable().getCell(i, CONTRACT_ID).getValue();
			FDCBillInfo billInfo = (FDCBillInfo)contractMap.get(id);
			if(billInfo == null) continue;
			
			/**
			 * 用于物业保修验收单下查扣款单，透过没有合同权限而导致看不到数据的问题
			 */
			if(getUIContext().get("source") != null && getUIContext().get("source") instanceof String)
			{
				String source = String.valueOf(getUIContext().get("source"));
				if(source.equals("PpmRepairFinishReviewListUI"))
				{
					getDetailTable().getCell(i, CONTRACT_NUMBER).setValue(billInfo);
					getDetailTable().getCell(i, "contractName").setValue(billInfo.getName());
					getDetailTable().getCell(i, "currency").setValue(billInfo.get("currency"));
				}
			}
			else
			{
				getDetailTable().getCell(i, CONTRACT_NUMBER).setValue(billInfo);
				getDetailTable().getCell(i, "contractName").setValue(billInfo.getName());
				getDetailTable().getCell(i, "currency").setValue(getCurrency(id));
			}
		}
		
		if(curProject==null){
			curProject = this.editData.getCurProject();
		}
		
		setSaveActionStatus();
		
		addTotalRow();
		
		updateTabHeader(chkconTypeBefContr.isSelected());
	    if(STATUS_EDIT.equals(getOprtState()) && !this.actionAudit.isVisible()
	    		&& !this.actionUnAudit.isVisible()){
	    	this.menuBiz.setVisible(false);
	    }
		//2009-1-21 在loadFields加入设置审批、反审批按钮状态的方法，在通过上一、下一功能切换单据时，正确显示审批、反审批按钮。
		setAuditButtonStatus(this.getOprtState());
	}
	protected void initDataStatus() {
		// TODO Auto-generated method stub
		super.initDataStatus();
		if(STATUS_VIEW.equals(getOprtState()) || STATUS_FINDVIEW.equals(getOprtState())) {
			actionInsertLine.setEnabled(false);
	        actionAddLine.setEnabled(false);
	        actionRemoveLine.setEnabled(false);
		}
	}

	public void storeFields() {
		int count = getDetailTable().getRowCount();
		
		BigDecimal oriTotal = FDCHelper.ZERO;
		BigDecimal localTotal = FDCHelper.ZERO;
		for (int i = 0; i < count - 1; i++) {
			if(getDetailTable().getCell(i,"deductOriginalAmt") != null)
				oriTotal =  FDCHelper.add(oriTotal,(BigDecimal) getDetailTable().getCell(i,"deductOriginalAmt").getValue());
			if(getDetailTable().getCell(i,"deductAmt") != null)
				localTotal = FDCHelper.add(localTotal,(BigDecimal) getDetailTable().getCell(i,"deductAmt").getValue());
			FDCBillInfo info = ((FDCBillInfo)getDetailTable().getCell(i, CONTRACT_NUMBER).getValue());
			if(info == null) continue;
			String id = info.getId().toString();
			getDetailTable().getCell(i, CONTRACT_ID).setValue(id);
		}
		editData.setAmount(localTotal);
		editData.setOriginalAmount(oriTotal);
		super.storeFields();
		
		

	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if(oprtType.equals(STATUS_VIEW) || oprtType.equals(STATUS_FINDVIEW)) {
			kdtEntrys.getStyleAttributes().setLocked(true);

			actionInsertLine.setEnabled(false);
            actionAddLine.setEnabled(false);
            actionRemoveLine.setEnabled(false);
            btnInsertLine.setEnabled(false);
            btnAddLine.setEnabled(false);
            btnRemoveLine.setEnabled(false);
		}
		else {
			kdtEntrys.getStyleAttributes().setLocked(false);
		}
		
		if(STATUS_ADDNEW.equals(oprtType) || STATUS_EDIT.equals(oprtType)) {
			setNumberEditable();
		}
		
		kdtEntrys.getColumn("contractName").getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn("currency").getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn("deductAmt").getStyleAttributes().setLocked(true);
	}

	private void setNumberEditable() {
		String orgId = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		if(FDCClientUtils.isAllowModifyNumber(new DeductBillInfo(), orgId)) {
			txtNumber.setEnabled(true);
			txtNumber.setEditable(true);
		}
	}
	
	
	/**
     * 在指定表格中新增行（新增到最后一行）
     * 
     * @param table
     */
    protected void addLine(KDTable table)
    {
    	//或者SHOW_ONLYLEFTSTATUSBAR 风格下直接返回
        if(table == null || (getUIContext().get("WinStyle")!=null && WinStyle.SHOW_ONLYLEFTSTATUSBAR==
        	((Integer)getUIContext().get("WinStyle")).intValue()))
        {
            return;
        }       

        IObjectValue detailData = createNewDetailData(table);
        IRow row = table.addRow(table.getRowCount() - 1);
        loadLineFields(table, row, detailData);
        afterAddLine(table, detailData);
    }

   private void sumTable() {
		int count = getDetailTable().getRowCount();
		BigDecimal total = FDCHelper.ZERO;
		BigDecimal totalOriginal = FDCHelper.ZERO;
		for (int i = 0; i < count - 1; i++) {
			Object value = getDetailTable().getCell(i, DEDUCT_AMT).getValue();
			Object valueOriginal = getDetailTable().getCell(i,"deductOriginalAmt").getValue();
			if(value != null) {
				BigDecimal amt = (BigDecimal)value;
				total = total.add(amt);
			}
			if(valueOriginal != null) {
				BigDecimal amt = (BigDecimal)valueOriginal;
				totalOriginal = totalOriginal.add(amt);
			}
		}
		getDetailTable().getCell(count - 1, DEDUCT_AMT).setValue(total);
		getDetailTable().getCell(count - 1,"deductOriginalAmt").setValue(totalOriginal);
	}
   protected void setActionState(){
     super.setActionState();
     setNumberEditable();
   }
    /**
     * 在指定表格中删除当前选中行
     * 
     * @param table
     */
    protected void removeLine(KDTable table)
    {
    	 KDTSelectBlock selectBlock = table.getSelectManager().get();

		if (selectBlock == null || selectBlock.getMode() == KDTSelectManager.COLUMN_SELECT) {
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_NoneEntry"));

			// MsgBox.showInfo(this,"没有选中分录，无法删除！");
			return;
		}

        int count = table.getRowCount();
        // [begin]进行删除分录的提示处理。
        if(confirmRemove())
        {
           
			int top = selectBlock.getBeginRow();
            int bottom = selectBlock.getEndRow();
//			int bottom=0;
			
            if (selectBlock.getMode() == KDTSelectManager.TABLE_SELECT){
            	top=0;
				bottom = 0;//count - 1;
				}
//            else{
//            	bottom=table.getSelectManager().size()-1;
//            }
//            int x=top;
//            if(top<bottom)top=bottom;
//            bottom=x;
            for(int i =top ;i<=bottom ;i++)
            {
            	if(i == count - 1) continue;	//合计行不删除
            	
                if (table.getRow(top) == null)
                {
                    MsgBox.showInfo(this, EASResource
                            .getString(FrameWorkClientUtils.strResource
                                    + "Msg_NoneEntry"));
        
                    //            MsgBox.showInfo(this,"没有选中分录，无法删除！");
                    return;
                }
        
                IObjectValue detailData = (IObjectValue) table.getRow(top)
                        .getUserObject();
                table.removeRow(top);
                IObjectCollection collection = (IObjectCollection) table
                        .getUserObject();
                if (collection == null)
                {
                    logger.error("collection not be binded to table");
                }
                else
                {
                    // Modify By Jacky Zhang
                    if( detailData != null ) {
                        collection.removeObject(detailData);
                    }
                }
                afterRemoveLine(table, detailData);
            }
        }
        //[end]
    	sumTable();
    }

    /**
	 * by Cassiel_peng
	 */
	 public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
			ArrayList idList = new ArrayList();
	    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
	    		idList.add(editData.getString("id"));
	    	}
	        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null){
				MsgBox.showWarning(this,FDCClientUtils.getRes("cantPrint"));
				return;
	        }          
	        DeductBillPrintProvider data = new DeductBillPrintProvider(editData.getString("id"),getTDQueryPK());
	        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
	        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
		}

		public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
	        ArrayList idList = new ArrayList();
	        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
	    		idList.add(editData.getString("id"));
	    	}
	        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null){
				MsgBox.showWarning(this,FDCClientUtils.getRes("cantPrint"));
				return;
	        }
	        DeductBillPrintProvider data = new DeductBillPrintProvider(editData.getString("id"),getTDQueryPK());
	        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
	        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
		}
		//获得扣款单套打对应的目录
		protected String getTDFileName() {
	    	return "/bim/fdc/contract/deduct";
		}
		//对应的套打Query
		protected IMetaDataPK getTDQueryPK() {
	    	return new MetaDataPK("com.kingdee.eas.fdc.finance.app.DeductBillQuery");
		}
	
    protected void insertLine(KDTable table) {
    	if(table == null)
        {
            return;
        }
        IObjectValue detailData = createNewDetailData(table);
        IRow row = null;

        if (table.getSelectManager().size() > 0)
        {
            int top = table.getSelectManager().get().getTop();
            row = table.addRow(top);
            /*if (isTableColumnSelected(table))
            {
                row = table.addRow(top);
            }
            else
            {
                row = table.addRow(top);
            }*/
        }
        else
        {
            row = table.addRow(table.getRowCount() - 1);
        }

        loadLineFields(table, row, detailData);
        afterInsertLine(table, detailData); 
    }
    
    protected void setFieldsNull(AbstractObjectValue arg0) {
    
    	super.setFieldsNull(arg0);
    	
    	DeductBillInfo info = (DeductBillInfo)arg0;
    	DeductBillEntryCollection entrys = info.getEntrys();
    	for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			DeductBillEntryInfo element = (DeductBillEntryInfo) iter.next();
			if(element.getContractId() == null)
				iter.remove();
			
		}
    	
    	info.setCurProject(editData.getCurProject());
    }
    //锁定界面上元素的可编辑。供业务系统重载。
    protected void lockUIForViewStatus()
    {
    	super.lockUIForViewStatus();
    	getDetailTable().getStyleAttributes().setLocked(true);
    }
    
    /*
     * 增加编辑控件的解锁。
     */
    protected void unLockUI()
    {
    	super.unLockUI();
    	getDetailTable().getStyleAttributes().setLocked(false);
    	if(getDetailTable().getRowCount() > 0)
    	getDetailTable().getRow(getDetailTable().getRowCount() - 1).getStyleAttributes().setLocked(true);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	
    	super.actionEdit_actionPerformed(e);
    	actionSave.setEnabled(true);
    	actionSubmit.setEnabled(true);
    	this.menuBiz.setVisible(false);
    	setSaveActionStatus();
//    	kdtEntrys.getColumn("currency").getStyleAttributes().setLocked(true);
    }

    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = getSelectBOID();
        if(boID == null)
        {
            return;
        }
        boolean isEdit = this.getOprtState().equals(STATUS_ADDNEW)||this.getOprtState().equals(STATUS_EDIT)?true:false;
		acm.showAttachmentListUIByBoID(boID, null, this, isEdit, null);
		
    }
    
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
    	super.actionCopy_actionPerformed(e);

    	//如果该合同已经结算，则清除合同编码
		int count = getDetailTable().getRowCount();
		
		for (int i = 0; i < count; i++) {
			String contractId = (String)getDetailTable().getCell(i, CONTRACT_ID).getValue();
			//FDCBillInfo billInfo = (FDCBillInfo)contractMap.get(id);
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
			
			if (ContractSettlementBillFactory.getRemoteInstance().exists(filter)) {
				getDetailTable().getCell(i, CONTRACT_NUMBER).setValue(null);
				getDetailTable().getCell(i, "contractName").setValue(null);
				getDetailTable().getCell(i, "currency").setValue(null);
			}
			//if(billInfo == null) continue;
		}
//		kdtEntrys.getColumn("currency").getStyleAttributes().setLocked(true);
    }
    
    /**
     * 覆盖抽象类处理编码规则的行为,统一在FDCBillEditUI.handCodingRule方法中处理
     */
    protected void setAutoNumberByOrg(String orgType) {
    	
    }
    private final BOSObjectType type=(new ContractBillInfo()).getBOSType();
    private CurrencyInfo getCurrency(String contractId){
    	if(contractId==null){
    		return null;
    	}
    	try{
	    	if(BOSUuid.read(contractId).getType().equals(type)){
	    		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo("select currenty.id,currency.name where id='"+contractId+"'");
	    		return contractBillInfo.getCurrency();
	    	}else{
	    		PayRequestBillInfo payRequestBillInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo("select currenty.id,currency.name where contractId='"+contractId+"'");
	    		return payRequestBillInfo.getCurrency();
	    	}
    	}catch (Exception e) {
    		return null;
		}
    }
    /*
     * 解决bt问题
     * @see com.kingdee.eas.framework.client.CoreBillEditUI#actionRemoveLine_actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
    	KDTable table=getDetailTable() ;
		if (table != null) {
			removeLine(table);
			appendFootRow(table);
		}
	}

	protected void attachListeners() {
		// TODO 自动生成方法存根
		
	}

	protected void detachListeners() {
		// TODO 自动生成方法存根
		
	}
	
    protected boolean isUseMainMenuAsTitle(){
    	return false;
    }
    
    private void afterPressDeleteButton() {
		kdtEntrys.setBeforeAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_DELETE == e.getType()) {
					for (int i = 0; i < kdtEntrys.getSelectManager().size(); i++) {
						KDTSelectBlock block = kdtEntrys.getSelectManager().get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++) {
							for (int colIndex = block.getBeginCol(); colIndex <= block.getEndCol(); colIndex++) {
								KDTEditEvent event = new KDTEditEvent(kdtEntrys, null, null, rowIndex, colIndex,
										true, 1);
								try {
									kdtEntrys_editStopped(event);
								} catch (Exception e1) {
									handleException(e1);
								}
							}
						}
					}
				}
			}
		});
	}
}