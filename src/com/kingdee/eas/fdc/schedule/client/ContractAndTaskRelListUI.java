/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryCollection;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryInfo;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelFactory;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ContractAndTaskRelListUI extends AbstractContractAndTaskRelListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractAndTaskRelListUI.class);
    
    /**
     * output class constructor
     */
    public ContractAndTaskRelListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	actionAuditResult.setVisible(false);
    	menuBiz.setVisible(true);
    	menuWorkFlow.setVisible(false);
    	fillTable();
    	actionAttachment.setVisible(false);
    	btnAttachment.setVisible(false);
    	treeProject.setSelectionRow(0);
    	
    	 //增加KDtreeView
		KDTreeView treeView=new KDTreeView();
		treeView.setTree(treeProject);
		treeView.setShowButton(true);
		treeView.setTitle("工程项目");
		kDSplitPane1.add(treeView,"left");
		treeView.setShowControlPanel(true);
		
		menuWorkFlow.setVisible(true);
		//add by warship at 2010/08/19 改变合计的数据格式
//		IRow footRow=null;
//		KDTFootManager footManager= this.tblMain.getFootManager();
//		if (footManager==null)  {
//            String total=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
//            footManager = new KDTFootManager(this.tblMain);
//            footManager.addFootView();
//            this.tblMain.setFootManager(footManager);
//            footRow= footManager.addFootRow(0);
//            footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
//            footRow.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
//            this.tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
//            this.tblMain.getIndexColumn().setWidth(30);
//            footManager.addIndexText(0, total);
//            footRow.getCell("amount").getStyleAttributes().setNumberFormat("#,###.00");
//        }else{
//            footRow=footManager.getFootRow(0);
//            footRow.getCell("amount").getStyleAttributes().setNumberFormat("#,###.00");
//        }
//		if(footManager!=null && footManager.getFootRow(0)!=null){
//			logger.error("-------the kdfoodmananger is not null---------");
//			IRow footRow =footManager.getFootRow(0);
//			footRow.getCell("amount").getStyleAttributes().setNumberFormat("#,###.00");
//		}
    }
    
    protected void initWorkButton() {
    	super.initWorkButton();
//    	actionAudit.setVisible(false);
//    	actionUnAudit.setVisible(false);
//    	actionViewDoProccess.setVisible(false);
//    	actionWorkFlowG.setVisible(false);
//    	actionWorkflowList.setVisible(false);
    	actionAttachment.setVisible(false);
    	actionCancel.setVisible(true);
    	actionCancel.setEnabled(true);
    	actionCancelCancel.setVisible(true);
    	actionCancelCancel.setEnabled(true);
    	btnAttachment.setVisible(false);
    	menuWorkFlow.setVisible(true);
    }
    /*
    * 要显示的合同的状态集合,用于过滤合同
    * @return
    */
    protected Set getContractBillStateSet() {
		Set set=new HashSet();
		set.add(FDCBillStateEnum.SAVED_VALUE);
		set.add(FDCBillStateEnum.SUBMITTED_VALUE);
		set.add(FDCBillStateEnum.AUDITTING_VALUE);
		set.add(FDCBillStateEnum.AUDITTED_VALUE);
		return set;
	}
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
        super.tblMain_tableClicked(e);
        fillTable();
    }


    
	private void fillTable() throws BOSException, EASBizException {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
        if(actRowIdx < 0) return;
        if(tblTask.getRowCount() > 0)
        	tblTask.removeRows();
        String contractId = tblMain.getCell(actRowIdx, "id").getValue().toString();
        fillTable(contractId);
	}

	private void fillTable(String contractId) throws BOSException,EASBizException {
		Map taskListMap = ContractAndTaskRelFactory.getRemoteInstance().getTaskData(contractId);
        ContractAndTaskRelInfo info;
        if(taskListMap.get("conTask") != null && ((Map)taskListMap.get("conTask")).get("conAndTaskRel") != null){
        	if(tblTask.getRowCount() > 0)	tblTask.removeRows();
        	info = (ContractAndTaskRelInfo) ((Map)taskListMap.get("conTask")).get("conAndTaskRel");
        	ContractAndTaskRelEntryCollection infoEntrys = info.getEntrys();
        	Map otherDataMap = new HashMap();
        	Map fillBillMap = new HashMap();
        	Map planWorkLoadMap = new HashMap();
        	Map taskMap = new HashMap();
        	if(taskListMap.get("otherDataMap") != null){
        		otherDataMap = (Map) taskListMap.get("otherDataMap");
        	}
        	if(otherDataMap.get("fillBill") != null){
        		fillBillMap = (Map) otherDataMap.get("fillBill");
        	}
        	if(otherDataMap.get("planWorkload") != null){
        		planWorkLoadMap = (Map) otherDataMap.get("planWorkload");
        	}
        	if(otherDataMap.get("task") != null){
        		taskMap = (Map) otherDataMap.get("task");
        	}
        	if(infoEntrys.size() >0){
        		for(int i=0;i<infoEntrys.size();i++){
        			ContractAndTaskRelEntryInfo entryInfo = infoEntrys.get(i);
        			FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) taskMap.get(
        					entryInfo.getWbs().getId().toString());
        			if(taskInfo==null){
        				continue;
        			}
        			IRow row = tblTask.addRow();
        			row.getCell("id").setValue(info.getId().toString());
        			row.getCell("number").setValue(info.getNumber());
        			row.getCell("name").setValue(info.getName());
        			row.getCell("state").setValue(info.getState());
        			row.getCell("task.name").setValue(taskInfo.getName());
        			row.getCell("startTime").setValue(taskInfo.getStart());
        			row.getCell("endTime").setValue(taskInfo.getEnd());
//        			row.getCell("plannedWorkload").setValue(entryInfo.getTask().getWorkLoad());
        			if(planWorkLoadMap != null && planWorkLoadMap.get(taskInfo.getId().toString()) != null){
        				row.getCell("plannedWorkload").setValue(planWorkLoadMap.get(taskInfo.getId().toString()));
        			}
        			row.getCell("task.id").setValue(taskInfo.getId().toString());
        			row.getCell("description").setValue(entryInfo.getDescription());
        			row.getCell("isEnabled").setValue(Boolean.valueOf(info.isIsEnabled()));
        			if(fillBillMap != null && fillBillMap.get(taskInfo.getId().toString()) != null){
        				row.getCell("accWorkload").setValue(
        						((ArrayList)fillBillMap.get(taskInfo.getId().toString())).get(0));
        				row.getCell("accPercent").setValue(
        						((ArrayList)fillBillMap.get(taskInfo.getId().toString())).get(1));
        			}
        		}
        	}else{
        		IRow row = tblTask.addRow();
    			row.getCell("id").setValue(info.getId().toString());
    			row.getCell("number").setValue(info.getNumber());
    			row.getCell("name").setValue(info.getName());
    			row.getCell("isEnabled").setValue(Boolean.valueOf(info.isIsEnabled()));
    			//Add by zhiyuan_tang 2010-08-16  没有分录的单据，单据状态显示不出来
    			row.getCell("state").setValue(info.getState());
        	}
        }
        KDTMergeManager mm = tblTask.getMergeManager();
        tblTask.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
        mm.mergeBlock(0,0,tblTask.getRowCount()-1,0,KDTMergeManager.SPECIFY_MERGE);
        mm.mergeBlock(0,1,tblTask.getRowCount()-1,1,KDTMergeManager.SPECIFY_MERGE);
        tblTask.getColumn("isEnabled").getStyleAttributes().setHided(false);
        mm.mergeBlock(0,12,tblTask.getRowCount()-1,12,KDTMergeManager.SPECIFY_MERGE);
        
        BigDecimal totalAmt = FDCHelper.ZERO;
        for(int i=0;i<this.tblMain.getRowCount();i++){
        	IRow row = this.tblMain.getRow(i);
        	totalAmt=FDCHelper.add(totalAmt, row.getCell("amount").getValue());
        }
        IRow footRow=null;
        KDTFootManager footManager= this.tblMain.getFootManager();
		if (footManager==null)  {
            String total=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
            footManager = new KDTFootManager(this.tblMain);
            footManager.addFootView();
            this.tblMain.setFootManager(footManager);
            footRow= footManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
            footRow.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
            this.tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
            this.tblMain.getIndexColumn().setWidth(30);
            footManager.addIndexText(0, total);
            footRow.getCell("amount").getStyleAttributes().setNumberFormat("#,###.00");
            footRow.getCell("amount").setValue(totalAmt);
        }else{
            footRow=footManager.getFootRow(0);
            footRow.getCell("amount").getStyleAttributes().setNumberFormat("#,###.00");
        }
	}

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
        if(actRowIdx < 0) return;
        if(tblMain.getCell(actRowIdx, "id").getValue() != null){
        	getUIContext().put("contractId", tblMain.getCell(actRowIdx, "id").getValue().toString());
        }
    	super.tblMain_tableSelectChanged(e);
    	fillTable();
    	if(tblTask.getRow(0) != null){
    		tblTask.getSelectManager().select(0, -1);
    		tblTask.requestFocus();
    	}
    }
  

    /**
     * output treeProject_valueChanged method
     */
    protected void treeProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeProject_valueChanged(e);
        fillTable();
        //add by warship at 2010/08/19 改变合计的数据格式
//        IRow footRow=null;
//		KDTFootManager footManager= this.tblMain.getFootManager();
//		if (footManager==null)  {
//            String total=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
//            footManager = new KDTFootManager(this.tblMain);
//            footManager.addFootView();
//            this.tblMain.setFootManager(footManager);
//            footRow= footManager.addFootRow(0);
//            footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
//            footRow.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
//            this.tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
//            this.tblMain.getIndexColumn().setWidth(30);
//            footManager.addIndexText(0, total);
//            footRow.getCell("amount").getStyleAttributes().setNumberFormat("#,###.00");
//        }else{
//            footRow=footManager.getFootRow(0);
//            footRow.getCell("amount").getStyleAttributes().setNumberFormat("#,###.00");
//        }
//        KDTFootManager footManager= this.tblMain.getFootManager();
//        if(footManager!=null && footManager.getFootRow(0)!=null){
//			IRow footRow =footManager.getFootRow(0);
//			footRow.getCell(8).getStyleAttributes().setNumberFormat("#,###.00");
//        }
    }
    //add by warship at 2010/08/19 改变合计的数据格式
    protected void treeContractType_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception{
    	super.treeContractType_valueChanged(e);
//    	IRow footRow=null;
//		KDTFootManager footManager= this.tblMain.getFootManager();
//		if (footManager==null)  {
//            String total=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
//            footManager = new KDTFootManager(this.tblMain);
//            footManager.addFootView();
//            this.tblMain.setFootManager(footManager);
//            footRow= footManager.addFootRow(0);
//            footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
//            footRow.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
//            this.tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
//            this.tblMain.getIndexColumn().setWidth(30);
//            footManager.addIndexText(0, total);
//            footRow.getCell("amount").getStyleAttributes().setNumberFormat("#,###.00");
//        }else{
//            footRow=footManager.getFootRow(0);
//            footRow.getCell("amount").getStyleAttributes().setNumberFormat("#,###.00");
//        }
//    	KDTFootManager footManager= this.tblMain.getFootManager();
//    	if(footManager!=null && footManager.getFootRow(0)!=null){
//			IRow footRow =footManager.getFootRow(0);
//			footRow.getCell(8).getStyleAttributes().setNumberFormat("#,###.00");
//    	}
    }

    protected void audit(List ids) throws Exception {
    	ContractAndTaskRelFactory.getRemoteInstance().audit(ids);
	}

	protected SelectorItemCollection genBillQuerySelector() {
		// TODO Auto-generated method stub
		return null;
	}

	protected KDTable getBillListTable() {
		return tblTask;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return getRemoteInterface();
	}

	protected String getEditUIName() {
		return ContractAndTaskRelEditUI.class.getName();
	}

	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return ContractAndTaskRelFactory.getRemoteInstance();
	}
	//合同提交审批时是否必须与计划任务进行关联 2010-08-09
	private boolean isRelatedTask(){
		boolean isRealtedTask = false;
		String cuID = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		try {
			isRealtedTask = FDCUtils.getDefaultFDCParamByKey(null, cuID, FDCConstants.FDC_PARAM_RELATEDTASK);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isRealtedTask;
	}
	protected boolean isFootVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	protected void unAudit(List ids) throws Exception {
		// TODO Auto-generated method stub
		ContractAndTaskRelFactory.getRemoteInstance().unAudit(ids);
	}
	protected void fetchInitData() throws Exception {
		
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		if(actRowIdx <0) return;
		String contractId = tblMain.getCell(actRowIdx, "id").getValue().toString();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contract.id",contractId,CompareType.EQUALS));
		if(ContractAndTaskRelFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showError("已添加记录，不允许新增！");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		int actRowIdx = tblTask.getSelectManager().getActiveRowIndex();
		if(actRowIdx < 0) return;
		if(tblTask.getCell(actRowIdx,"id").getValue() != null){
			ContractAndTaskRelFactory.getRemoteInstance().
				updateIsEnabled(tblTask.getCell(actRowIdx,"id").getValue().toString(),false);
		}
		if(tblMain.getSelectManager().getActiveRowIndex() >= 0){
			fillTable(tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(),"id").getValue().toString());
		}
		FDCMsgBox.showInfo("禁用成功！");
	}
	
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		String contractId = null; 
		if(actRowIdx >= 0 && tblMain.getCell(actRowIdx, "id").getValue() != null){
			contractId = tblMain.getCell(actRowIdx, "id").getValue().toString();
		}
		super.actionRefresh_actionPerformed(e);
//		if(contractId != null && contractId.length()>=0){
//			fillTable(contractId);
//		}else{
//			fillTable();
//		}
	}
	
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		int actRowIdx = tblTask.getSelectManager().getActiveRowIndex();
		if(actRowIdx < 0) return;
		if(tblTask.getCell(actRowIdx,"id").getValue() != null){
			ContractAndTaskRelFactory.getRemoteInstance().
				updateIsEnabled(tblTask.getCell(actRowIdx,"id").getValue().toString(),true);
		}
		if(tblMain.getSelectManager().getActiveRowIndex() >= 0){
			fillTable(tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(),"id").getValue().toString());
		}
		FDCMsgBox.showInfo("启用成功！");
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkIsEnabled("已启用，不能修改！");
		super.actionEdit_actionPerformed(e);
	}


	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkIsEnabled("已启用，不能删除！");
		//如果提交或者审批状态的合同已经与任务关联起来就不能将任务全都删除，变态的需求！ by cassiel
//		if(isRelatedTask()){
//			checkBeforeDel();
//		}
		super.actionRemove_actionPerformed(e);
		btnAttachment.setVisible(false);
		actionAttachment.setVisible(false);
	}

	//如果提交或者审批状态的合同已经与任务关联起来就不能将任务全都删除，变态的需求！ by cassiel
	private void checkBeforeDel() throws BOSException, SQLException {
		int[] selectedRows = KDTableUtil.getSelectedRows(tblMain);
		String contractId = (String) tblMain.getCell(selectedRows[0], "id").getValue();//不支持多选
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select FState from T_CON_ContractBill where FID=? ");
		builder.addParam(contractId);
		IRowSet rowSet = builder.executeQuery();
		if(rowSet.next()){
			String state = rowSet.getString("FState");
			if(!FDCBillStateEnum.SAVED_VALUE.equals(state)){
				builder.clear();
				builder.appendSql("select count(fid) jishu from T_SCH_ContractAndTaskRelEntry where FParentID=  \n ");
				builder.appendSql("(select fid from T_SCH_ContractAndTaskRel where FContractID=?  \n )");
				builder.addParam(contractId);
				IRowSet _rowSet = builder.executeQuery();
				if(_rowSet.next()){
					int count = _rowSet.getInt("jishu");
					if(count ==0 ){
						FDCMsgBox.showWarning("提交、审批状态的合同必须有一条任务与其关联！");
						SysUtil.abort();
					}
				}
			}
		}
	}
	private void checkIsEnabled(String msg) throws EASBizException, BOSException {
		int actRowIdx = tblTask.getSelectManager().getActiveRowIndex();
		if(actRowIdx < 0) return;
		FilterInfo filter = new FilterInfo();
		if(tblTask.getCell(actRowIdx, "id").getValue() != null){
			filter.getFilterItems().add(new FilterItemInfo("id",tblTask.getCell(actRowIdx, "id").getValue().toString(),CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE,CompareType.EQUALS));
			if(ContractAndTaskRelFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showError(msg);
				SysUtil.abort();
			}
		}
	}
	public String[] getMergeColumnKeys() {
		String[] keys = new String[]{};//{"name","number"};
		return keys;
	}
	
	public void buildProjectTree() throws Exception {
		OrgUnitInfo currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		try{
			//用财务组织构造树保证下级组织能够看到其他的项目
			SysContext.getSysContext().setCurrentOrgUnit(SysContext.getSysContext().getCurrentFIUnit());
			ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

			projectTreeBuilder.build(this, treeProject, actionOnLoad);
			authorizedOrgs = (Set)ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
			if(authorizedOrgs==null){
				authorizedOrgs = new HashSet();
				Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
						 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
				            OrgType.CostCenter, 
				            null,  null, null);
				if(orgs!=null){
					Set orgSet = orgs.keySet();
					Iterator it = orgSet.iterator();
					while(it.hasNext()){
						authorizedOrgs.add(it.next());
					}
				}		
			}
		} finally {
			SysContext.getSysContext().setCurrentOrgUnit(currentOrgUnit);
		}
		treeProject.setShowsRootHandles(true);
	}
}