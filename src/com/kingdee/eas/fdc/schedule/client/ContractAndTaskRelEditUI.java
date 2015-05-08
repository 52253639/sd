/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryFactory;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryInfo;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelFactory;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ContractAndTaskRelEditUI extends AbstractContractAndTaskRelEditUI
{
    private static final String COL_ID = "id";
    private static final String COL_PROJECT = "project";
	private static final String COL_DESCRIPTION = "description";
	private static final String COL_WBS_ID = "WBS.id";
	private static final String COL_TASK_ID = "task.id";
	private static final String COL_TASK_NAME = "task.name";
	private static final String COL_ACC_PERCENT = "accPercent";
	private static final String COL_ACC_AMOUNT = "accAmount";
	private static final String COL_ACC_WORKLOAD = "accWorkload";
	private static final String COL_PLANNED_WORKLOAD = "plannedWorkload";
	private static final String COL_END_TIME = "endTime";
	private static final String COL_START_TIME = "startTime";
	private static final String COL_WBS_NUMBER = "WBS.number";
	private static final Logger logger = CoreUIObject.getLogger(ContractAndTaskRelEditUI.class);
    
//	用来存放WBSID
	private Set selectedWBSIds = new HashSet();
//	用来存放WBSID对应的taskID
	private Map wbsTaskMap = new HashMap();
	
    /**
     * output class constructor
     */
    public ContractAndTaskRelEditUI() throws Exception {
        super();
    }
//    private String projectId = null;
    private String contractId = null;

    public void loadFields() {
    	super.loadFields();
    	//Add by zhiyuan_tang 2010-08-16  合同与任务关联工作流中的单据，没有显示关联合同的相关信息。
 		//原因是无法从工作流中把合同单据带过来，所以要根据合同与任务关联单据来取合同单据
    	 if(contractBill == null) {
     		if(this.editData != null && this.editData.getId() != null) {
     			contractBill = editData.getContract();
     		}
     	}
    	if(contractBill != null){
    		txtConName.setText(contractBill.getName());
    		txtConNumber.setText(contractBill.getNumber());
    	}
    	for(int i=0;i<kdtEntrys.getRowCount();i++){
    		if(kdtEntrys.getCell(i, COL_WBS_NUMBER).getValue() != null){
    			selectedWBSIds.add(((FDCWBSInfo)kdtEntrys.getCell(i, COL_WBS_NUMBER).getValue()).getId().toString());
    		}
    	}
    	try {
    		Map map = ContractAndTaskRelEntryFactory.getRemoteInstance().getTaskOtherData(selectedWBSIds);
    		Map taskMap = (Map) map.get("task");
			Map planWorkLoadMap = (Map) map.get("planWorkload");
			for(int i=0;i<kdtEntrys.getRowCount();i++){
	    		if(kdtEntrys.getCell(i, COL_WBS_NUMBER).getValue() != null){
	    			FDCWBSInfo wbsInfo = (FDCWBSInfo) kdtEntrys.getCell(i, COL_WBS_NUMBER).getValue();
	    			FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) taskMap.get(wbsInfo.getId().toString());
	    			kdtEntrys.getCell(i, COL_TASK_ID).setValue(taskInfo.getId().toString());
	    			kdtEntrys.getCell(i, COL_TASK_NAME).setValue(taskInfo);
	    			kdtEntrys.getCell(i, COL_START_TIME).setValue(taskInfo.getStart());
	    			kdtEntrys.getCell(i, COL_END_TIME).setValue(taskInfo.getEnd());
	    			kdtEntrys.getCell(i, COL_ACC_WORKLOAD).setValue(taskInfo.getWorkLoad());
					kdtEntrys.getCell(i, COL_ACC_PERCENT).setValue(taskInfo.getComplete());
	    			if(planWorkLoadMap.get(taskInfo.getId().toString()) != null){
	    				kdtEntrys.getCell(i,COL_PLANNED_WORKLOAD).setValue(planWorkLoadMap.get(taskInfo.getId().toString()));
	    			}
	    		}
	    	}
		} catch (Exception e) {
			this.handleException(e);
		}
    }
    
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	menuWorkflow.setVisible(false);
    	menuBiz.setVisible(false);
    	menuTable1.setVisible(false);
    	menuView.setVisible(false);
    	actionAuditResult.setVisible(false);
    	actionAttachment.setVisible(false);
    	actionSubmitOption.setVisible(false);
    	initTable();
    	/**
    	 * 给contractId赋值 ,否则后续无法正常使用。--by jiadong
    	 */
    	if(contractBill !=null){
    		contractId = contractBill.getId().toString();
    	}
    }

    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = super.getSelectors();
    	sic.add("state");
    	sic.add("entrys.id");
    	sic.add("contract.id");
    	sic.add("contract.name");
    	sic.add("contract.number");
    	sic.add("wbs.curProject.id");
    	sic.add("wbs.curProject.name");
    	sic.add("wbs.curProject.number");
    	return sic;
    }
    

    
    /**
     * 目前做的是在F7选完之后把所需的数据再重新算一遍，现在改为自己写的F7，可以修改F7的取数，从而减少一次RPC调用
     * 这个补丁任务时间太紧，以后有时间进行优化
     */
	private void initTable() {
		F7FDCWBSSelectPromptBox selector = new F7FDCWBSSelectPromptBox(this);
		KDBizPromptBox prmtWBS = new KDBizPromptBox(){
			protected String valueToString(Object o) {
				String str = "";
				if(o != null && o instanceof FDCWBSInfo){
					str = ((FDCWBSInfo)o).getLongNumber().replace('!','.');
				}
				return str;
			}
		};
		prmtWBS.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e) {
				int actRowIdx = kdtEntrys.getSelectManager().getActiveRowIndex();
				if(actRowIdx < 0) return;
				FDCWBSInfo wbsInfo = (FDCWBSInfo) kdtEntrys.getCell(actRowIdx, COL_WBS_NUMBER).getValue();
				if(wbsInfo == null) return;
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(new FilterInfo());
				view.getFilter().getFilterItems().add(new FilterItemInfo("project",wbsInfo.getCurProject().getId().toString()));
				view.getFilter().getFilterItems().add(new FilterItemInfo("wbs",wbsInfo.getId().toString()));
				KDBizPromptBox bizBox = (KDBizPromptBox) e.getSource();
				bizBox.setEntityViewInfo(view);
			}
		});
		
		prmtWBS.setEditFormat("$number$");
		prmtWBS.setEditable(false);
		prmtWBS.setDisplayFormat("$name$");
		prmtWBS.setSelector(selector);
		prmtWBS.setEnabledMultiSelection(true);
		kdtEntrys.getColumn(COL_TASK_NAME).getStyleAttributes().setLocked(true);
		ObjectValueRender numRender = new ObjectValueRender();
		numRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o instanceof Object[]) {
					Object[] object = (Object[])o;
					if(object.length < 1){
						return null;
					}else{
						String number =((FDCScheduleTaskInfo)object[0]).getWbs().getLongNumber();
						return number.replace('!', '.');
					}
				} 
				if (o instanceof FDCWBSInfo) {
					return ((FDCWBSInfo)o).getLongNumber().replace('!', '.');
				} else
					return o.toString();
			}
		});
		ObjectValueRender taskRender = new ObjectValueRender();
		taskRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o instanceof Object[]) {
					Object[] object = (Object[])o;
					if(object.length < 1){
						return null;
					}else{
						String name=((FDCScheduleTaskInfo)object[0]).getName();
						return name;
					}
				} 
				else if (o instanceof FDCScheduleTaskInfo) {
					String name=((FDCScheduleTaskInfo)o).getName();
					return name;
				} else{
					return o.toString();
				}
			}
		});
		kdtEntrys.getColumn(COL_PROJECT).getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn(COL_TASK_NAME).setRenderer(taskRender);
		kdtEntrys.getColumn(COL_WBS_NUMBER).setRenderer(numRender);
		KDTDefaultCellEditor wbsEditor = new KDTDefaultCellEditor(prmtWBS);
		kdtEntrys.getColumn(COL_WBS_NUMBER).setEditor(wbsEditor);
		kdtEntrys.getColumn(COL_START_TIME).getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn(COL_END_TIME).getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn(COL_PLANNED_WORKLOAD).getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn(COL_ACC_WORKLOAD).getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn(COL_ACC_AMOUNT).getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn(COL_ACC_PERCENT).getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn(COL_WBS_NUMBER).setRequired(true);
		KDTextField description = new KDTextField();
		description.setMaxLength(500);
		kdtEntrys.getColumn(COL_DESCRIPTION).setEditor(new KDTDefaultCellEditor(description));
	}
	
	
	
    protected IObjectValue createNewData() {
    	ContractAndTaskRelInfo conTaskInfo = new ContractAndTaskRelInfo();
    	conTaskInfo.setIsEnabled(false);
    	conTaskInfo.setContract(contractBill);
    	return conTaskInfo;
    }

    protected void initWorkButton() {
    	super.initWorkButton();
    	actionCopy.setVisible(false);
    	actionCopyFrom.setVisible(false);
//    	actionSubmit.setVisible(false);
    	actionRemove.setVisible(false);
    	actionFirst.setVisible(false);
    	actionLast.setVisible(false);
    	actionPre.setVisible(false);
//    	actionNext.setVisible(false);
//    	actionNextPerson.setVisible(false);
//    	actionMultiapprove.setVisible(false);
    	actionTraceDown.setVisible(false);
    	actionTraceUp.setVisible(false);
//    	actionWorkFlowG.setVisible(false);
    	actionCreateFrom.setVisible(false);
    	actionCreateTo.setVisible(false);
    	actionAddLine.setVisible(false);
    	actionInsertLine.setVisible(false);
    	actionRemoveLine.setVisible(false);
    	actionAttachment.setVisible(false);
    	btnAudit.setVisible(false);
    	btnUnAudit.setVisible(false);
    	btnProperty.setVisible(true);
    	btnProperty.setEnabled(true);
    	menuItemProperty.setEnabled(true);
    	KDWorkButton btnAddNewLine = new KDWorkButton("新增行",	SCHClientHelper.ICON_ADDLINE);
		KDWorkButton btnDeleteLine = new KDWorkButton("删除行",SCHClientHelper.ICON_REMOVELINE);
    	btnAddNewLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(OprtState.VIEW.equals(getOprtState())) return;
				kdtEntrys.addRow();
			}
		});
		btnDeleteLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int actRowIdx = kdtEntrys.getSelectManager().getActiveRowIndex();
				if(actRowIdx < 0 || OprtState.VIEW.equals(getOprtState())) return;
				if(kdtEntrys.getCell(actRowIdx, COL_WBS_NUMBER).getValue() != null){
					selectedWBSIds.remove(((FDCWBSInfo)
							kdtEntrys.getCell(actRowIdx, COL_WBS_NUMBER).getValue()).getId().toString());
				}
				kdtEntrys.removeRow(actRowIdx);
			}
		});
		conTask.removeAllButton();
		conTask.addButton(btnAddNewLine);
		conTask.addButton(btnDeleteLine);
    }
    
    /**
     * output storeFields method
     */
    public void storeFields() {
    	super.storeFields();
    }

    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	verify();
    	verifyEntry();
        super.actionSave_actionPerformed(e);
        actionAttachment.setVisible(false);
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	verify();
    	int count = kdtEntrys.getRowCount();
    	if(count == 0){
    		FDCMsgBox.showWarning("必须至少关联一条任务！");
    		SysUtil.abort();
    	}
    	verifyEntry();
    	super.actionSubmit_actionPerformed(e);
    }
    //add by warship at 2010/08/19 点击"向后"时，隐藏"附件管理"
    public void actionNext_actionPerformed(ActionEvent e) throws Exception {
    	super.actionNext_actionPerformed(e);
    	actionAttachment.setVisible(false);
    }

	private void verify() throws BOSException, EASBizException {
		if(txtNumber.getText() == null || txtNumber.getText().length() < 1){
    		FDCMsgBox.showError("单据编码不能为空！");
    		SysUtil.abort();
    	}
    	if(txtName.getText() == null || txtName.getText().length() < 1){
        	FDCMsgBox.showError("单据名称不能为空！");
        	SysUtil.abort();
        }
        FilterInfo filter = new FilterInfo();
        if(getUIContext().get("contractBillId") != null){
        	filter.getFilterItems().add(new FilterItemInfo("contract.id",getUIContext().get("contractBillId").toString(),CompareType.EQUALS));
        }else if(editData.getContract() != null){
        	filter.getFilterItems().add(new FilterItemInfo("contract.id",editData.getContract().getId().toString(),CompareType.EQUALS));
        }
        if(editData.getId() != null){
        	filter.getFilterItems().add(new FilterItemInfo("id",editData.getId().toString(),CompareType.NOTEQUALS));
        }
        if(ContractAndTaskRelFactory.getRemoteInstance().exists(filter)){
        	FDCMsgBox.showError("已添加记录，不允许新增！");
        	SysUtil.abort();
        }
	}


	private void verifyEntry() {
		Set taskIds = new HashSet();
        for(int i=0;i<kdtEntrys.getRowCount();i++){
        	ContractAndTaskRelEntryInfo info = new ContractAndTaskRelEntryInfo();
        	if(kdtEntrys.getCell(i, COL_TASK_ID).getValue() != null && kdtEntrys.getCell(i, COL_TASK_ID).getValue().toString().length() >0){
        		FDCScheduleTaskInfo taskInfo = new FDCScheduleTaskInfo();
        		taskInfo.setId(BOSUuid.read(kdtEntrys.getCell(i, COL_TASK_ID).getValue().toString()));
        		info.setTask(taskInfo);
        		if(kdtEntrys.getCell(i, COL_ID).getValue() != null && kdtEntrys.getCell(i, COL_ID).getValue().toString().length()>0){
        			info.setId(BOSUuid.read(kdtEntrys.getCell(i, COL_ID).getValue().toString()));
        		}
        		if(kdtEntrys.getCell(i,COL_DESCRIPTION).getValue() != null){
        			info.setDescription(kdtEntrys.getCell(i,COL_DESCRIPTION).getValue().toString());
        		}
        		if(taskIds.contains(kdtEntrys.getCell(i, COL_TASK_ID).getValue().toString())){
        			FDCMsgBox.showWarning("所选任务不能重复！");
            		SysUtil.abort();
        		}
        		taskIds.add(kdtEntrys.getCell(i, COL_TASK_ID).getValue().toString());
        	}else{
        		FDCMsgBox.showWarning("第"+(i+1)+"行WBS编码不能为空！");
        		SysUtil.abort();
        	}
        }
	}
    
    protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractAndTaskRelFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return kdtEntrys;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	public void actionProperty_actionPerformed(ActionEvent e) throws Exception {
		int actRowIdx = kdtEntrys.getSelectManager().getActiveRowIndex();
		if(actRowIdx < 0) return;
		String taskId;
		FDCScheduleTaskInfo task=new FDCScheduleTaskInfo();
		if(kdtEntrys.getCell(actRowIdx, COL_TASK_ID).getValue() != null){
			taskId = kdtEntrys.getCell(actRowIdx, COL_TASK_ID).getValue().toString();
			task = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo(new ObjectUuidPK(BOSUuid.read(taskId)));
			KDTask selectTask=new KDTask(ScheduleGanttProject.getScheduleGanttProjectSingleForOther().getTaskManager(),task);
			FDCScheduleTaskPropertiesUI.showUI(this, selectTask, OprtState.VIEW);
		}
	}

	protected String getPropertityUIName(){
		String propertyUIName="com.kingdee.eas.fdc.schedule.client.FDCScheduleTaskPropertiesUI";
		return propertyUIName;
	}
	public boolean isModify() {
		return super.isModify();
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		initWorkButton();
		FilterInfo filter = new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("contract.id",contractId));
        filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
        if(ContractAndTaskRelFactory.getRemoteInstance().exists(filter)){
        	FDCMsgBox.showError("已启用，不允许修改！");
        	SysUtil.abort();
        }
        if(this.editData!=null&&this.editData.getId()!=null){
        	FDCSQLBuilder builder = new FDCSQLBuilder();
        	builder.appendSql("select fstate from T_SCH_ContractAndTaskRel where  fid = ?  ");
        	builder.addParam(this.editData.getId().toString());
        	IRowSet rowSet = builder.executeQuery();
        	if(rowSet.next()){
        		String state = rowSet.getString("fstate");
        		if(FDCBillStateEnum.AUDITTED_VALUE.equals(state)||FDCBillStateEnum.AUDITTING_VALUE.equals(state)){
        			FDCMsgBox.showWarning("您当前选择的单据的状态不适合修改操作");
        			SysUtil.abort();
        		}
        	}
        }
        
		super.actionEdit_actionPerformed(e);
		btnAttachment.setVisible(false);
		actionAttachment.setVisible(false);		
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		initWorkButton();
		FilterInfo filter = new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("contract.id",contractId));
        if(ContractAndTaskRelFactory.getRemoteInstance().exists(filter)){
        	FDCMsgBox.showError("已添加记录，不允许新增！");
        	SysUtil.abort();
        }
		super.actionAddNew_actionPerformed(e);
		actionAttachment.setVisible(false);//add by warship at 2010/08/18
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		btnAttachment.setVisible(false);
		actionAttachment.setVisible(false);
	}
	public void onShow() throws Exception {
		super.onShow();
		btnAttachment.setVisible(false);
		actionAttachment.setVisible(false);
		this.menuWorkflow.setVisible(true);
		
		//Add by zhiyuan_tang 2010-08-16   提交状态的单据不允许修改		
		if (!OprtState.ADDNEW.equals(getOprtState()))
			if(this.editData != null && FDCBillStateEnum.SUBMITTED_VALUE.equals(editData.getState().getValue())) {
				actionSave.setEnabled(false);
			}
	}
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		int actRowIdx = kdtEntrys.getSelectManager().getActiveRowIndex();
		int actColIdx = kdtEntrys.getSelectManager().getActiveColumnIndex();
		if(actColIdx != 1 || actRowIdx < 0) return;
		Set selectedTaskIds = new HashSet();
		for(int i=0;i<kdtEntrys.getRowCount();i++){
			if(kdtEntrys.getCell(i, COL_TASK_ID).getValue() != null){
				selectedTaskIds.add(kdtEntrys.getCell(i,COL_TASK_ID).getValue().toString());
			}
		}
		if(kdtEntrys.getCell(actRowIdx, COL_WBS_NUMBER).getValue() != null
				&& kdtEntrys.getCell(actRowIdx, COL_WBS_NUMBER).getValue() instanceof Object[]){
			Object[] taskObjs = (Object[]) kdtEntrys.getCell(actRowIdx, COL_WBS_NUMBER).getValue();
			for(int i=0;i<taskObjs.length;i++){
				FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) taskObjs[i];
				FDCWBSInfo wbsInfo = taskInfo.getWbs();
				IRow row = null;
				if( !selectedWBSIds.contains(wbsInfo.getId().toString())){
					if(i == 0 && kdtEntrys.getCell(actRowIdx, COL_TASK_NAME).getValue() == null)	row = kdtEntrys.getRow(actRowIdx);
					else row = kdtEntrys.addRow();
					fillRow(taskInfo, wbsInfo, row);
					selectedWBSIds.add(wbsInfo.getId().toString());
				}
			}
		}
	}


	private void fillRow(FDCScheduleTaskInfo taskInfo, FDCWBSInfo wbsInfo,IRow row) {
		ContractAndTaskRelEntryInfo entryInfo = new ContractAndTaskRelEntryInfo();
		row.getCell(COL_TASK_ID).setValue(taskInfo.getId().toString());
		row.getCell(COL_TASK_NAME).setValue(taskInfo);
		row.getCell(COL_WBS_ID).setValue(wbsInfo.getId());
		row.getCell(COL_WBS_NUMBER).setValue(wbsInfo);
		row.getCell(COL_START_TIME).setValue(taskInfo.getStart());
		row.getCell(COL_END_TIME).setValue(taskInfo.getEnd());
		row.getCell(COL_PROJECT).setValue(wbsInfo.getCurProject());
		row.getCell(COL_PLANNED_WORKLOAD).setValue(taskInfo.get("planWorkLoad"));
		row.getCell(COL_ACC_WORKLOAD).setValue(taskInfo.getWorkLoad());
		row.getCell(COL_ACC_PERCENT).setValue(taskInfo.getComplete());
		entryInfo.setWbs(wbsInfo);
	}
	
	
}
