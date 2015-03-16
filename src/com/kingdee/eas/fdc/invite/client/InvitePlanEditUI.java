/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.programming.Programming;
import com.kingdee.eas.fdc.contract.programming.ProgrammingCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.fdc.invite.InviteDocumentsCollection;
import com.kingdee.eas.fdc.invite.InviteDocumentsFactory;
import com.kingdee.eas.fdc.invite.InviteMonthPlanEntrysInfo;
import com.kingdee.eas.fdc.invite.InvitePlanCollection;
import com.kingdee.eas.fdc.invite.InvitePlanEntrysInfo;
import com.kingdee.eas.fdc.invite.InvitePlanFactory;
import com.kingdee.eas.fdc.invite.InvitePlanInfo;
import com.kingdee.eas.fdc.invite.InviteStateEnum;
import com.kingdee.eas.fdc.invite.TenderAccepterResultCollection;
import com.kingdee.eas.fdc.invite.TenderAccepterResultFactory;
import com.kingdee.eas.fdc.invite.app.InvitePlanControllerBean;
import com.kingdee.eas.fdc.invite.supplier.AppraiseTypeEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class InvitePlanEditUI extends AbstractInvitePlanEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InvitePlanEditUI.class);
    public InvitePlanEditUI() throws Exception
    {
        super();
    }
	protected void attachListeners() {
		addDataChangeListener(this.prmtProgramming);
		addDataChangeListener(this.prmtProject);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.prmtProgramming);
		removeDataChangeListener(this.prmtProject);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return InvitePlanFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return this.kdtEntry;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewData() {
		InvitePlanInfo info=(InvitePlanInfo)this.getUIContext().get("info");
		if(info==null){
			info= new InvitePlanInfo();
			info.setVersion(new BigDecimal("1.0"));
		}else{
			info.setDescription(info.getId().toString());
			info.setVersion(info.getVersion().add(new BigDecimal(1)));
			info.setId(null);
			for(int i=0;i<info.getEntry().size();i++){
				info.getEntry().get(i).setId(null);
			}
		}
		info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		try {
			info.setBizDate(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		return info;
	}
	public void onLoad() throws Exception {
		initTable();
		super.onLoad();
		initControl();
	}
	protected void initTable(){
		this.kdtEntry.checkParsed();
		this.kdtEntry.getStyleAttributes().setLocked(true);
		KDFormattedTextField level = new KDFormattedTextField();
		level.setDataType(KDFormattedTextField.INTEGER);
		level.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		KDTDefaultCellEditor levelEditor = new KDTDefaultCellEditor(level);
		this.kdtEntry.getColumn("level").setEditor(levelEditor);
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		this.kdtEntry.getColumn("amount").setEditor(amountEditor);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
	 	
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		String formatString = "yyyy-MM-dd";
		this.kdtEntry.getColumn("documentsAuditDate").getStyleAttributes().setLocked(false);
		this.kdtEntry.getColumn("documentsAuditDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntry.getColumn("documentsAuditDate").setEditor(dateEditor);
		
		this.kdtEntry.getColumn("resultAuditDate").getStyleAttributes().setLocked(false);
		this.kdtEntry.getColumn("resultAuditDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntry.getColumn("resultAuditDate").setEditor(dateEditor);
		
		this.kdtEntry.getColumn("contractAuditDate").getStyleAttributes().setLocked(false);
		this.kdtEntry.getColumn("contractAuditDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntry.getColumn("contractAuditDate").setEditor(dateEditor);
		
		this.kdtEntry.getColumn("enterAuditDate").getStyleAttributes().setLocked(false);
		this.kdtEntry.getColumn("enterAuditDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntry.getColumn("enterAuditDate").setEditor(dateEditor);
	}
	public void storeFields()
    {
        super.storeFields();
    }
    public void loadFields() {
    	detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		for(int i = 0; i < this.kdtEntry.getRowCount(); i++){
			IRow row=this.kdtEntry.getRow(i);
			InvitePlanEntrysInfo entry=(InvitePlanEntrysInfo) row.getUserObject();
			if(entry.getProgrammingContractID()!=null){
				FilterInfo isExistContract=new FilterInfo();
				isExistContract.getFilterItems().add(new FilterItemInfo("programmingContract.id",entry.getProgrammingContractID().toString()));
	            isExistContract.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
	        	try {
					if(ContractBillFactory.getRemoteInstance().exists(isExistContract)){
						row.getCell("documentsAuditDate").getStyleAttributes().setLocked(true);
						row.getCell("resultAuditDate").getStyleAttributes().setLocked(true);
						row.getCell("contractAuditDate").getStyleAttributes().setLocked(true);
						row.getCell("enterAuditDate").getStyleAttributes().setLocked(true);
						row.getStyleAttributes().setBackground(new Color(245,245,245));
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		setTableTree();
		
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
    protected void initControl() {
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.btnCalculator.setVisible(true);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		this.txtVersion.setPrecision(1);
		
		if(this.editData.getDescription()!=null&&!"".equals(this.editData.getDescription())){
			this.prmtProject.setEnabled(false);
		}
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtMeasureStage.setEntityViewInfo(view);
		
		view=new EntityViewInfo();
		filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",editData.getOrgUnit().getId().toString()));
		view.setFilter(filter);
		this.prmtProject.setEntityViewInfo(view);
		
		setProgrammingFilter();
	}
    protected void setProgrammingFilter(){
    	if(this.prmtProject.getValue()!=null){
    		this.prmtProgramming.setEnabled(true);
    		
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("project.id",((CurProjectInfo)this.prmtProject.getValue()).getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("isLatest",Boolean.TRUE));
			
			view.setFilter(filter);
			
			this.prmtProgramming.setEntityViewInfo(view);
    	}else{
    		this.prmtProgramming.setEnabled(false);
    		this.prmtProgramming.setValue(null);
    	}
    }
	protected void prmtProgramming_dataChanged(DataChangeEvent e) throws Exception {
		 boolean isChanged = true;
		 isChanged = BizCollUtil.isF7ValueChanged(e);
         if(!isChanged){
        	 return;
         }
         ProgrammingInfo pro=(ProgrammingInfo)this.prmtProgramming.getValue();
         boolean isShowWarn=false;
         boolean isUpdate=false;
         if(this.kdtEntry.getRowCount()>0||this.kdtEntry.getRowCount()>0){
        	 isShowWarn=true;
         }
         if(isShowWarn){
        	 if(FDCMsgBox.showConfirm2(this, "合约规划改变会覆盖分录数据，是否继续？")== FDCMsgBox.YES){
        		 isUpdate=true;
             }
         }else{
        	 isUpdate=true;
         }
         if(isUpdate){
        	 this.kdtEntry.removeRows();
        	 this.editData.getEntry().clear();
        	 if(pro!=null){
        		 this.storeFields();
        		 BigDecimal area=FDCHelper.ZERO;
        		 EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("project.id",((CurProjectInfo)this.prmtProject.getValue()).getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("isLastVersion",Boolean.TRUE));
				view.setFilter(filter);
				MeasureCostCollection mcCol=MeasureCostFactory.getRemoteInstance().getMeasureCostCollection(view);
				if(mcCol.size()>0){
					view=new EntityViewInfo();
					filter=new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("headID",mcCol.get(0).getId().toString()));
					view.setFilter(filter);
					SelectorItemCollection sic=new SelectorItemCollection();
					sic.add("totalBuildArea");
					sic.add("totalSellArea");
					view.setSelector(sic);
					PlanIndexCollection col=PlanIndexFactory.getRemoteInstance().getPlanIndexCollection(view);
					if(col.size()>0){
						area=col.get(0).getTotalBuildArea();
					}
				}
					
        		  view=new EntityViewInfo();
            	  filter = new FilterInfo();
                 filter.getFilterItems().add(new FilterItemInfo("programming.id" , pro.getId().toString()));
                 view.setFilter(filter);
                 ProgrammingContractCollection col=ProgrammingContractFactory.getRemoteInstance().getProgrammingContractCollection(view);
                 for(int i=0;i<col.size();i++){
                	 ProgrammingContractInfo pc=col.get(i);
                	 InvitePlanEntrysInfo entry=new InvitePlanEntrysInfo();
                	 entry.setProgrammingContractID(pc.getId());
                	 entry.setLevel(pc.getLevel());
                	 entry.setLongNumber(pc.getLongNumber());
                	 entry.setName(pc.getName());
                	 entry.setCostAccountNames(pc.getCostAccountNames());
                	 entry.setWorkContent(pc.getWorkContent());
                	 entry.setAmount(pc.getAmount());
                	 if(area!=null&&area.compareTo(FDCHelper.ZERO)!=0){
                		 entry.setPrice(FDCHelper.divide(pc.getAmount(), area, 2, BigDecimal.ROUND_HALF_UP));
                	 }
                	 this.editData.getEntry().add(entry);
                 }
                 this.loadFields();
        	 }
         }
	}
	protected void setTableTree(){
		int maxLevel = 0;
 		int[] levelArray = new int[this.kdtEntry.getRowCount()];
 		for (int i = 0; i < this.kdtEntry.getRowCount(); i++) {
 			IRow row = this.kdtEntry.getRow(i);
 			int level = Integer.parseInt(row.getCell("level").getValue().toString());
 			levelArray[i] = level;
 			row.setTreeLevel(level - 1);
 		}
 		for (int i = 0; i < this.kdtEntry.getRowCount(); i++) {
 			maxLevel = Math.max(levelArray[i], maxLevel);
 		}
 		this.kdtEntry.getTreeColumn().setDepth(maxLevel);
	}
	public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sel=super.getSelectors();
    	sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("description");
    	sel.add("entry.programmingContractID");
		return sel;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtMeasureStage);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtProject);
		
		Date startDate=(Date)this.pkPlanBeginDate.getValue();
		Date endDate=(Date)this.pkPlanEndDate.getValue();
		if(startDate!=null&&endDate!=null){
			if(startDate.compareTo(endDate)>0){
				FDCMsgBox.showWarning(this,"计划结束日期不能小于计划开始日期！");
				SysUtil.abort();
			}
		}
	}
	
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
		} else {
			this.unLockUI();
		}
	}
	protected void prmtProject_dataChanged(DataChangeEvent e) throws Exception {
		setProgrammingFilter();
		if(this.prmtProject.getValue()!=null){
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("project.id",((CurProjectInfo)this.prmtProject.getValue()).getId().toString()));
			if(this.editData.getId()!=null){
				filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
			}
			if(InvitePlanFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this, "该项目已存在项目采购招标计划，请进行修订！");
				this.prmtProject.setValue(null);
			}
		}
		
	}
}