/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.VerticalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDFrame;
import com.kingdee.bos.ctrl.swing.KDMultiLangArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.pm.DataTypeEnum;
import com.kingdee.eas.fdc.pm.client.PMClientHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSCollection;
import com.kingdee.eas.fdc.schedule.FDCWBSFactory;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.WorkAmountBillFactory;
import com.kingdee.eas.fdc.schedule.WorkAmountBillInfo;
import com.kingdee.eas.fdc.schedule.WorkAmountEntryCollection;
import com.kingdee.eas.fdc.schedule.WorkAmountEntryInfo;
import com.kingdee.eas.fdc.schedule.app.FDCScheduleTaskControllerBean;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.framework.client.ScheduleCellEditor;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.ObjectUtils;

/**
 * output class name
 */
public class WorkAmountBillEditUI extends AbstractWorkAmountBillEditUI
{
//	private CurProjectInfo project = null;
	
    private static final Logger logger = CoreUIObject.getLogger(WorkAmountBillEditUI.class);
    
    private Map alreadyEnterMap = new HashMap();
    
    private Map taskCompleteInfo = new HashMap();
    
    private CurProjectInfo project = null;
    
    private String projectID;
    
    
    /***
     * 工作流审批时能修改确认金额和确认比率
     */
    private void editConfirmAmountInAudit(){
    	Object isFormWorkFlow = getUIContext().get("isFromWorkflow");
    	if(isFormWorkFlow!=null 
    			&& isFormWorkFlow.toString().equals("true") 
    			&& getOprtState().equals(STATUS_FINDVIEW) 
    			&& actionSave.isVisible() 
    			&& (editData.getState()==FDCBillStateEnum.SUBMITTED 
    					|| editData.getState() == FDCBillStateEnum.AUDITTING)){
    	
    		getDetailTable().setEditable(true);
    		
    		for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				IRow row = getDetailTable().getRow(i);
				row.getCell("confirmAmount").getStyleAttributes().setLocked(false);
				row.getCell("confirmPercent").getStyleAttributes().setLocked(false);
				row.getCell("amount").getStyleAttributes().setLocked(true);
				row.getCell("percent").getStyleAttributes().setLocked(true);
			}
    		this.actionSave.setEnabled(true);
    		this.btnSelected.setEnabled(false);//add by warship at 2010/08/017
    	}
    
    	if(isFormWorkFlow!=null 
    			&& isFormWorkFlow.toString().equals("true") 
    			&& getOprtState().equals(STATUS_EDIT)){
    		actionRemove.setEnabled(false);
    	}
    }
    
    
    public void onShow() throws Exception {
    	// TODO Auto-generated method stub
    	super.onShow();
    	SwingUtilities.invokeLater(new Runnable(){
   	  		public void run() {
   	  		editConfirmAmountInAudit();
   	 		}
   	   });
    }
    /**
     * output class constructor
     */
    public WorkAmountBillEditUI() throws Exception
    {
        super();
    }

    
    public void onLoad() throws Exception {
    	//UIContext uiContext = new UIContext(this);
    	Object o = getUIContext().get("isPlan");
		if(o != null&&((Boolean)o).booleanValue()){
			projectID=getUIContext().get("projectId").toString();
		}
    	
    	super.onLoad();
    	//填报部门可用，并且需要支持可以选取全集团下的组织 by cassiel 2010-06-29
		if(ScheduleClientHelper.chooseAllOrg()){
			ScheduleClientHelper.setRespDept(prmtBizDept, this, null);
		}else{
			CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
			ScheduleClientHelper.setRespDept(prmtBizDept, this, currentFIUnit!=null?currentFIUnit.getId().toString():null);
		}
    	
    	this.prmtCreator.setDisplayFormat("$name$");
    	initButtonStyle();
    	initContainerButtonStyle();
    	initCtrl();
    	initTableStyle();
    	setUITitle("任务工程量填报编辑");
    	//显示计划汇总的任务
    	displayPlanTask();
    	
    }
    
    public void displayPlanTask(){
    	Object o = getUIContext().get("taskID");
		if(o != null){
			String taskID=o.toString();
			try {
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("*"));
				sic.add(new SelectorItemInfo("adminDept"));
				sic.add(new SelectorItemInfo("adminDept.name"));
				sic.add(new SelectorItemInfo("adminPerson"));
				sic.add(new SelectorItemInfo("adminPerson.name"));
				FDCScheduleTaskInfo task=FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo((new ObjectUuidPK(taskID)), sic);
				KDTable table=getDetailTable();
				IRow row=table.addRow();
				//row.getCell("id").setValue(task.getId().toString());
				row.getCell("tasknumber").setUserObject(task);
				row.getCell("tasknumber").setValue(task.getLongNumber().replace('!', '.'));
				row.getCell("taskname").setValue(task.getName());
				row.getCell("startDate").setValue(task.getStart());
				row.getCell("endDate").setValue(task.getEnd());
				if(task.getAdminPerson()!=null){
					row.getCell("adminPerson").setValue(task.getAdminPerson().getName());
				}
				//modify warship at 2010/08/20
				BigDecimal [] data=null;
				if(task!=null && task.getId()!=null && task.getId().toString()!=null){
					data = getTotalInfoByTaskId(task.getId().toString());
				}
				if(data != null){
					row.getCell("totalAmount").setValue(data[0]);
					row.getCell("totalPercent").setValue(data[1]);
				}
				//row.getCell("totalPercent").setValue(task.getComplete());
				prmtAdminPerson.setValue(task.getAdminPerson());
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
    }
    
    public void initAllTaskInfoByProjectId(String projectId){
    	if(projectId == null){
    		return ;
    	}
    	try {
			taskCompleteInfo = WorkAmountBillFactory.getRemoteInstance().initTaskInfo(projectId);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
    
    public CurProjectInfo getProjectFormUIContext(){
    	if(getUIContext().get("projectInfo") != null){
    		project = (CurProjectInfo) getUIContext().get("projectInfo");
    	}
    	return project;
    }
    
    /***
     * 取执行状态的
     * @return
     */
    public Set canEnterWorkAmount(){
    	Set canOperSet = new HashSet();
//    	canOperSet.add(ScheduleStateEnum.AUDITTED_VALUE);
    	canOperSet.add(ScheduleStateEnum.EXETING_VALUE);
//    	canOperSet.add(ScheduleStateEnum.STARTED_VALUE);
    	return canOperSet;
    }
    protected void prmtBizDept_dataChanged(DataChangeEvent e) throws Exception {
		Object oldValue = e.getOldValue();
		Object newValue = e.getNewValue();
		if(newValue == null){
			prmtAdminPerson.setValue(null);
		}
		if(newValue!=null&&newValue!=oldValue&&!newValue.equals(oldValue)&&!isFirstOnload()){
			prmtAdminPerson.setValue(null);
		}
		if(newValue instanceof CostCenterOrgUnitInfo){
			CostCenterOrgUnitInfo costcenter = (CostCenterOrgUnitInfo)newValue;
			FullOrgUnitInfo fullOrg = costcenter.castToFullOrgUnitInfo();
			this.editData.setBizDept(fullOrg);
		}
		if(newValue instanceof FullOrgUnitInfo){
			FullOrgUnitInfo fullOrg = (FullOrgUnitInfo)newValue;
			this.editData.setBizDept(fullOrg);
		}
	}
    
    protected void prmt_adminPerson_willShow(SelectorEvent e) throws Exception {
		//责任人是否按当前用户所在组织进行过滤 by cassiel_peng 2010-06-07
    	CtrlUnitInfo currentCtrlUnit = SysContext.getSysContext().getCurrentCtrlUnit();
		if(!FDCSCHClientHelper.filterRespPerson()){//跟合同的责任人保持一致
			if(FDCSCHClientHelper.canSelectOtherOrgPerson()){
				FDCClientUtils.setPersonF7(prmtAdminPerson, this,null );
			}else{
				if(currentCtrlUnit==null){
					FDCMsgBox.showWarning("管理单元为空，出错！");
					SysUtil.abort();
				}
				FDCClientUtils.setPersonF7(prmtAdminPerson, this,currentCtrlUnit.getId().toString());
			}
		}else{
			//如果没有选择责任部门
			if(prmtBizDept.getValue()==null){
				if(currentCtrlUnit==null){
					FDCMsgBox.showWarning("管理单元为空，出错！");
					SysUtil.abort();
				}
				FDCClientUtils.setPersonF7(prmtAdminPerson, this,currentCtrlUnit.getId().toString());
			}else{
				if(prmtBizDept.getValue()!=null&&prmtBizDept.getValue() instanceof CostCenterOrgUnitInfo){
					if(((CostCenterOrgUnitInfo)prmtBizDept.getValue()).getCU()==null){
						FDCMsgBox.showWarning("管理单元为空，出错！");
						SysUtil.abort();
					}
					String cuID = ((CostCenterOrgUnitInfo)prmtBizDept.getValue()).getCU().getId().toString();
					FDCClientUtils.setPersonF7(prmtAdminPerson, this,cuID);
				}
				if(prmtBizDept.getValue()!=null&&prmtBizDept.getValue() instanceof FullOrgUnitInfo){
					if(((FullOrgUnitInfo)prmtBizDept.getValue()).getCU()==null){
						FDCMsgBox.showWarning("管理单元为空，出错！");
						SysUtil.abort();
					}
					String cuID = ((FullOrgUnitInfo)prmtBizDept.getValue()).getCU().getId().toString();
					FDCClientUtils.setPersonF7(prmtAdminPerson, this,cuID);
				}
			}
		}
    	
	}
    
    
    
    public void initTableStyle()
    {
    	
    	final KDBizPromptBox prmtTask = new KDBizPromptBox();
    	
    	FilterInfo filter = new FilterInfo();
    	if(projectID!=null){
    		filter.getFilterItems().add(new FilterItemInfo("schedule.project.id",projectID));
    	}else{
    		filter.getFilterItems().add(new FilterItemInfo("schedule.project.id",editData.getProject().getId().toString()));
    	}
		filter.getFilterItems().add(new FilterItemInfo("schedule.state",canEnterWorkAmount(),CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
//		filter.getFilterItems().add(new FilterItemInfo("adminDept.id",editData.getBizDept().getId()));
//		filter.getFilterItems().add(new FilterItemInfo("admin.isLatestVer",Boolean.TRUE));
		/****
		 * 不能将100%的任务过滤掉，不然f7弹出的任务编码会乱掉
		 */
//		filter.getFilterItems().add(new FilterItemInfo("complete",null,CompareType.EQUALS));
//		filter.getFilterItems().add(new FilterItemInfo("complete",FDCHelper.ONE_HUNDRED,CompareType.LESS));
//		filter.setMaskString("#0 and #1 and #2 and #3 and (#4 or #5)");
//		
		prmtTask.setSelector(new F7WorkAmountTaskPromptBox(filter));
    	
//    	prmtTask.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7ScheduleTaskQuery");
    	prmtTask.setDisplayFormat("$number$");
    	prmtTask.setEditFormat("$number$");
    	prmtTask.setCommitFormat("$number");
    	prmtTask.setHistoryRecordEnabled(false);
    	ICellEditor editor = new KDTDefaultCellEditor(prmtTask);

    	getDetailTable().getColumn("tasknumber").setEditor(editor);
    	ObjectValueRender numberRender = new ObjectValueRender();
    	numberRender.setFormat(new IDataFormat(){
			public String format(Object o) {
				if(o instanceof FDCScheduleTaskInfo){
					return ((FDCScheduleTaskInfo)o).getNumber();
				}
				return o.toString();
			}});
    	getDetailTable().getColumn("tasknumber").setRenderer(numberRender);
    	//锁定相关列
    	getDetailTable().getColumn("taskname").getStyleAttributes().setLocked(true);
    	getDetailTable().getColumn("startDate").getStyleAttributes().setLocked(true);
    	getDetailTable().getColumn("endDate").getStyleAttributes().setLocked(true);
    	getDetailTable().getColumn("totalPercent").getStyleAttributes().setLocked(true);
    	
    
    	
    	KDFormattedTextField decEditor = new KDFormattedTextField();
    	decEditor.setDataType(BigDecimal.class);
    	decEditor.setMaximumValue(new BigDecimal(100));
    	decEditor.setMinimumValue(new BigDecimal(0));
    	decEditor.setPrecision(2);
    	KDTDefaultCellEditor dec = new KDTDefaultCellEditor(decEditor);
    	
    	getDetailTable().getColumn("totalPercent").setEditor(dec);
    	getDetailTable().getColumn("totalPercent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	getDetailTable().getColumn("percent").setEditor(dec);
    	getDetailTable().getColumn("percent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	getDetailTable().getColumn("confirmPercent").setEditor(dec);
    	getDetailTable().getColumn("confirmPercent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	getDetailTable().getColumn("totalPercent").getStyleAttributes().setNumberFormat("#,###.00");
    	getDetailTable().getColumn("percent").getStyleAttributes().setNumberFormat("#,###.00");
    	getDetailTable().getColumn("confirmPercent").getStyleAttributes().setNumberFormat("#,###.00");
    	getDetailTable().getColumn("totalAmount").getStyleAttributes().setNumberFormat("#,###.00");
    	getDetailTable().getColumn("amount").getStyleAttributes().setNumberFormat("#,###.00");
    	getDetailTable().getColumn("confirmAmount").getStyleAttributes().setNumberFormat("#,###.00");
    	
    	KDFormattedTextField amount = new KDFormattedTextField();
    	amount.setDataType(BigDecimal.class);
    	amount.setPrecision(2);
    	
    	KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
    	getDetailTable().getColumn("totalAmount").getStyleAttributes().setLocked(true);
    	getDetailTable().getColumn("totalAmount").setEditor(amountEditor);
    	getDetailTable().getColumn("totalAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	getDetailTable().getColumn("amount").setEditor(amountEditor);
    	getDetailTable().getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	getDetailTable().getColumn("confirmAmount").setEditor(amountEditor);
    	getDetailTable().getColumn("confirmAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	
//    	KDMultiLangArea txtArea = new KDMultiLangArea();
//    	txtArea.setMaxLength(1000);
//    	KDTDefaultCellEditor remarkEditor = new KDTDefaultCellEditor(txtArea);
//    	getDetailTable().getColumn("remark").setEditor(remarkEditor);
    	getDetailTable().getColumn("remark").getStyleAttributes().setWrapText(true);
    	getDetailTable().getColumn("remark").getStyleAttributes().setVerticalAlign(VerticalAlignment.TOP);
    	getDetailTable().getColumn("remark").setWidth(200);
    	
    	KDDatePicker dp = new KDDatePicker();
    	dp.setValue(serverDate);
    	KDTDefaultCellEditor dEditor = new KDTDefaultCellEditor(dp);
    	getDetailTable().getColumn("completeDate").setEditor(dEditor);
    	getDetailTable().getColumn("completeDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
    	
    	getDetailTable().getColumn("percent").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
    	getDetailTable().getColumn("confirmPercent").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
    	getDetailTable().getColumn("completeDate").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
       
    	
    	prmtTask.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent eventObj) {
				Object o  = eventObj.getNewValue();
				Object old = eventObj.getOldValue();
				FDCScheduleTaskInfo info = null;
				if(o != null && o instanceof FDCScheduleTaskInfo){
					info = (FDCScheduleTaskInfo) o;
					if(!info.isIsLeaf()){
						FDCMsgBox.showError("请选择明细任务！");
						prmtTask.setData(null);
						int rowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
						if(rowIndex != -1){
								kdtEntry.removeRow(rowIndex);
						}
						refreshAlreayTaskMap();
						abort();
						
					}else{
						if(info.getComplete()!=null&&info.getComplete().compareTo(FDCHelper.ONE_HUNDRED)==0){
							FDCMsgBox.showError("选中的任务已完工100%！");
							prmtTask.setData(null);
							int rowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
							if(rowIndex != -1){
									kdtEntry.removeRow(rowIndex);
							}
							refreshAlreayTaskMap();
							abort();
						}
						
						if(info.getAdminDept()!= null && prmtBizDept.getValue() !=null){
							if(!info.getAdminDept().getId().equals(((OrgUnitInfo)prmtBizDept.getValue()).getId())){
								FDCMsgBox.showError("任务工程量不属于该责任部门！");
								prmtTask.setData(null);
								int rowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
								if(rowIndex != -1){
										kdtEntry.removeRow(rowIndex);
								}
								refreshAlreayTaskMap();
								abort();
							}
						}
						PersonInfo person =(PersonInfo)prmtAdminPerson.getValue();
						if(info.getAdminPerson()!= null && person!=null){
							if(!info.getAdminPerson().getId().equals(person.getId())&& fillByRespPerson()){
								FDCMsgBox.showError("任务工程量只能按责任人填报！");
								prmtTask.setData(null);
								int rowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
								if(rowIndex != -1){
										kdtEntry.removeRow(rowIndex);
								}
								refreshAlreayTaskMap();
								abort();
							}
						}						
						
						if(alreadyEnterMap.containsKey(info.getId().toString())){
							if(old != null){
							    info = (FDCScheduleTaskInfo) o;
							    if(info.getNumber().equals(old)){
							    	return;
							    }								
							}							
							FDCMsgBox.showError("当前任务已经选中，不能重复选择！");
							prmtTask.setData(null);
							int rowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
							if(rowIndex != -1){
								kdtEntry.removeRow(rowIndex);
							}
							refreshAlreayTaskMap();
							abort();
						}
						setData(info);//						
						refreshAlreayTaskMap();
					}
				}else{
//					prmtTask.setDataNoNotify(eventObj.getOldValue());
				}
				
			}
    		
    	});
    	
    	
    	kdtEntry.getColumn("tasknumber").setRequired(true);
    	
    	kdtEntry.addKDTEditListener(new KDTEditListener(){

			public void editCanceled(KDTEditEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void editStarted(KDTEditEvent e) {
				// TODO Auto-generated method stub
//				int rowIndex = e.getRowIndex();
//				kdtEntry.getRow(rowIndex).getCell("tasknumber").setValue(null);
			}

			public void editStarting(KDTEditEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void editStopped(KDTEditEvent e) {
				// TODO Auto-generated method stub
				KDTableHelper.autoFitRowHeight(kdtEntry,e.getRowIndex(),5);
				IRow row = kdtEntry.getRow(e.getRowIndex());
				//取累计完工百分比
				BigDecimal percent = FDCHelper.toBigDecimal(row.getCell("totalPercent").getValue());
				//判断当前录入完工百分比+累计完工百分比不能大于100%
				if(e.getColIndex() == row.getCell("percent").getColumnIndex()){
					BigDecimal old = (BigDecimal) e.getOldValue();
					if(old == null){
						old = FDCHelper.ZERO;
					}
					logger.info(old);
					BigDecimal curr = (BigDecimal) e.getValue();
					if(curr == null){
						curr =FDCHelper.ZERO;
					}
					logger.info(curr);
					if(FDCHelper.add(percent, curr).subtract(old).compareTo(FDCHelper.ONE_HUNDRED)>0){
						FDCMsgBox.showError("本次完工百分比+累计完工百分比不能大于100%");
						row.getCell(e.getColIndex()).setValue(old);
						return ;
					}else{
						row.getCell("confirmPercent").setValue(curr);
					}
					if(curr.compareTo(old)>0){
						row.getCell("totalPercent").setValue((FDCHelper.add(percent, curr.subtract(old))));
					}else if(curr.compareTo(old)<0){
						row.getCell("totalPercent").setValue((FDCHelper.subtract(percent, old.subtract(curr))));
					}
					
				}
				
				//判断当前录入确认工程量百分比是否大于本次录入的工程量和百分比
				if(e.getColIndex() == row.getCell("confirmPercent").getColumnIndex()){
					BigDecimal old = (BigDecimal) e.getOldValue();
					if(old == null){
						old = FDCHelper.ZERO;
					}
					BigDecimal curr = (BigDecimal) e.getValue();
					if(curr == null){
						curr =FDCHelper.ZERO;
					}
					if(row.getCell("percent").getValue()!=null && row.getCell("totalPercent").getValue() != null){
						BigDecimal per = (BigDecimal) row.getCell("totalPercent").getValue();
						if(per == null){
							per = FDCHelper.ZERO;
						}
						if(FDCHelper.add(per, curr).subtract(old).compareTo(FDCHelper.ONE_HUNDRED) > 0){
							FDCMsgBox.showError("本次确认百分比+累计完工百分比不能大于100%");
							row.getCell(e.getColIndex()).setValue(old);
						}else{
							if(curr.compareTo(old)>0){
								row.getCell("totalPercent").setValue((FDCHelper.add(percent, curr.subtract(old))));
							}else if(curr.compareTo(old)<0){
								row.getCell("totalPercent").setValue((FDCHelper.subtract(percent, old.subtract(curr))));
							}							
						}
						
						
					
					}else{
						FDCMsgBox.showError("请先输入本次完工百分比！");
						row.getCell(e.getColIndex()).setValue(null);
					}
				}
				if(e.getColIndex() == row.getCell("confirmAmount").getColumnIndex()){
					BigDecimal curr = (BigDecimal) e.getValue();
					if(row.getCell("amount").getValue() != null){
//						BigDecimal per = (BigDecimal) row.getCell("amount").getValue();
//						if(curr.compareTo(per) > 0){
//							FDCMsgBox.showError("本次确认工程量不能大于本次完工工程量");
//							row.getCell(e.getColIndex()).setValue(null);
//						}
					}else{
						FDCMsgBox.showError("请先输入本次完工工程量！");
						row.getCell(e.getColIndex()).setValue(null);
					}
				}
				if(e.getColIndex() == row.getCell("tasknumber").getColumnIndex()){
					FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) row.getCell("tasknumber").getUserObject();
					if(info != null){
						BigDecimal [] data = null;
//						if(taskCompleteInfo.containsKey(info.getId().toString())){
//							data = (BigDecimal[]) taskCompleteInfo.get(info.getId().toString());
//							row.getCell("totalAmount").setValue(data[0]);
//							row.getCell("totalPercent").setValue(data[1]);
//						}else{//modify warship at 2010/08/19
						    data = getTotalInfoByTaskId(info.getId().toString());
						    row.getCell("totalAmount").setValue(data[0]);
							row.getCell("totalPercent").setValue(data[1]);
//						}
						row.getCell("tasknumber").setValue(info.getNumber());
					}
					
				}
				//取累计完工量 add by warship at 2010/08/27
				BigDecimal totalAmount = FDCHelper.toBigDecimal(row.getCell("totalAmount").getValue());
				if(e.getColIndex() == row.getCell("amount").getColumnIndex()){
						BigDecimal old = (BigDecimal) e.getOldValue();
						if(old == null){
							old = FDCHelper.ZERO;
						}
						logger.info(old);
						BigDecimal curr = (BigDecimal) e.getValue();
						if(curr == null){
							curr =FDCHelper.ZERO;
						}
						logger.info(curr);
						row.getCell("confirmAmount").setValue(curr);
						if(curr.compareTo(old)>0){
							row.getCell("totalAmount").setValue((FDCHelper.add(totalAmount, curr.subtract(old))));
						}else if(curr.compareTo(old)<0){
							row.getCell("totalAmount").setValue((FDCHelper.subtract(totalAmount, old.subtract(curr))));
						}
				}
				
				if(e.getColIndex() == row.getCell("completeDate").getColumnIndex()){
					if(row.getCell("completeDate").getValue() != null){
						Date d = (Date) row.getCell("completeDate").getValue();
						
						if(d!=null&&d.after(new Date())){
							FDCMsgBox.showError("完工日期不得不大于当前日期！");
							row.getCell("completeDate").setValue(new Date());
							
						}
					}
					
					
				}
				
			}

			public void editStopping(KDTEditEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void editValueChanged(KDTEditEvent e) {
				// TODO Auto-generated method stub
				
			}});
    	
    	
    	for(int i = 0; i < kdtEntry.getRowCount(); ++i){
			FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) kdtEntry.getCell(i, "tasknumber").getUserObject();
			if(task != null && !task.isIsLeaf()){
				for(int j = 0; j < kdtEntry.getColumnCount(); ++j)
				kdtEntry.getCell(i, j).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
			}
		}
    
    }
    
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
    	if(prmtBizDept.getValue() == null){
    		FDCMsgBox.showError("填报部门不能为空！");
    		abort();
    	}
		Object o = prmtAdminPerson.getValue();
		if(o==null){
			FDCMsgBox.showError("责任人不能为空！");
			abort();
		}
		super.actionAddLine_actionPerformed(e);
	}
    
    protected void afterAddLine(KDTable table, IObjectValue lineData) {
    	// TODO Auto-generated method stub
		super.afterInsertLine(table, lineData);
    }
    
    private BigDecimal[] getTotalInfoByTaskId(String taskId){
    	BigDecimal [] data = null;
    	if(taskId == null){
    		return new BigDecimal[]{FDCHelper.ZERO,FDCHelper.ZERO};
    	}else{
    		FDCSQLBuilder builder = new FDCSQLBuilder();
//    		builder.appendSql("select isnull(sum(fconfirmamount),0),isnull(sum(fconfirmpercent),0) from t_sch_workamountentry where  fparentid in(select fid from t_sch_workamountbill where fstate in('2SUBMITTED','4AUDITTED')) and ftaskid = ?");
    		builder.appendSql("select isnull(sum(fconfirmamount),0),isnull(sum(fconfirmpercent),0) from t_sch_workamountentry ");
    		builder.appendSql(" where  fparentid in(select fid from t_sch_workamountbill )");
    		builder.appendSql(" and fwbsid = (select fwbsid from t_sch_fdcscheduletask where fid=?)");
    		builder.addParam(taskId);
    		try {
				RowSet rs = builder.executeQuery();
				while(rs.next()){
					data = new BigDecimal[]{rs.getBigDecimal(1),rs.getBigDecimal(2)};
					taskCompleteInfo.put(taskId, data);
				}
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return data;
    }
    
    public void setData(FDCScheduleTaskInfo info){
    	int rowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
        //设置相关数据
    	IRow row = kdtEntry.getRow(rowIndex);
    	row.getCell("taskname").setValue(info.getName());
    	row.getCell("startDate").setValue(info.getStart());
    	row.getCell("adminPerson").setValue(info.getAdminPerson().getName());
    	row.getCell("endDate").setValue(info.getEnd());
    	row.getCell("tasknumber").setUserObject(info);
    	
    }
    
    public void initContainerButtonStyle(){
    	remove(btnAddLine);
		remove(btnRemoveLine);
//		btnAddLine.setIcon();
    	mainContainer.addButton(btnAddLine);
    	mainContainer.addButton(btnRemoveLine);
    }
    public void initCtrl(){
//    	this.conTaskEndDateEnd.setVisible(false);
//    	this.conTaskEndDateStart.setVisible(false);
    }
    
    public void initButtonStyle(){
    	this.actionAudit.setVisible(false);
    	this.actionUnAudit.setVisible(false);
    	
    	this.actionInsertLine.setVisible(false);
    	this.actionPrint.setVisible(false);
    	this.actionPrintPreview.setVisible(false);
    	this.actionCancel.setVisible(false);
    	this.actionCancelCancel.setVisible(false);
    	this.actionPre.setVisible(false);
    	this.actionNext.setVisible(false);
    	this.actionFirst.setVisible(false);
    	this.actionLast.setVisible(false);
    	this.actionCopy.setVisible(false);
    	this.actionCopyFrom.setVisible(false);
    	this.actionCopyLine.setVisible(false);
    	this.actionSubmitOption.setVisible(false);
    	this.actionCopyLine.setVisible(false);
        this.actionTraceDown.setVisible(false);
        this.actionTraceUp.setVisible(false);
        this.actionCreateFrom.setVisible(false);
        this.actionCreateTo.setVisible(false);
        
        this.btnSelected.setEnabled(true);
        
    	this.menuView.setVisible(false);
    	this.menuBiz.setVisible(false);
    	this.menuTable1.setVisible(false);
    	if(getOprtState().equals(OprtState.VIEW)){
    		this.actionAddLine.setEnabled(false);
    		this.actionRemoveLine.setEnabled(false);
    		this.btnSelected.setEnabled(false);
    	}
    	
    
    }
    
    public void actionRemoveLine_actionPerformed(ActionEvent e)
    		throws Exception {
    	// TODO Auto-generated method stub
    	KDTSelectManager selectManager = getDetailTable().getSelectManager();
    	int size = selectManager.size();
		KDTSelectBlock selectBlock = null;
		for (int blockIndex = 0; blockIndex < size; blockIndex++) {
			selectBlock = selectManager.get(blockIndex);
			int top = selectBlock.getBeginRow();
			int bottom = selectBlock.getEndRow();
			for (int i = top; i <= bottom; i++) {
				Object userObject = getDetailTable().getCell(i, "tasknumber").getUserObject();
				if(userObject != null && userObject instanceof FDCScheduleTaskInfo){
					FDCScheduleTaskInfo task = (FDCScheduleTaskInfo)userObject;
					if(!task.isIsLeaf()){
						FDCMsgBox.showWarning("您选择的行中包含非明细任务，  不能删除");
						SysUtil.abort();
					}
				}
			}
		}
    	super.actionRemoveLine_actionPerformed(e);
    	refreshAlreayTaskMap();
    	
    }
    
    public QuerySolutionInfo getQuerySolutionInfo() {
    	// TODO Auto-generated method stub
    	return super.getQuerySolutionInfo();
    }
    
    public void refreshAlreayTaskMap(){
    	alreadyEnterMap.clear();
    	FDCScheduleTaskInfo info = null;
    	for(int i=0;i<kdtEntry.getRowCount();i++){
    		if(kdtEntry.getCell(i, "tasknumber").getUserObject()!= null){
    			info = (FDCScheduleTaskInfo) kdtEntry.getCell(i, "tasknumber").getUserObject();
    			alreadyEnterMap.put(info.getId().toString(), info);
    		}
    	}
    }
    
    
    protected void afterRemoveLine(KDTable table, IObjectValue lineData) {
    	// TODO Auto-generated method stub
    	super.afterRemoveLine(table, lineData);
    	
    }
   
    private boolean fillByRespPerson(){
		boolean fillByRespPerson = false;
		try {
			fillByRespPerson = FDCUtils.getDefaultFDCParamByKey(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString(), FDCConstants.FDC_PARAM_FILLBYRESPPERSON);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fillByRespPerson;
	} 
    public void actionSelected_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
//    	super.actionSelected_actionPerformed(e);
    	
    	Map taskMap = new HashMap();
    	Date sDate = (Date) this.startDateStart.getValue();
    	Date eDate = (Date) this.startDateEndDate.getValue();
    	if(sDate == null || eDate == null){
    		FDCMsgBox.showError("必须录入开始时间和截止时间！");
    		abort();
    	}
    	if(sDate != null && eDate != null&& eDate.before(sDate)){
    		FDCMsgBox.showError("截止日期不能大于开始日期！");
    		abort();
    	}
    	taskMap.put("sDate", sDate);
    	taskMap.put("eDate", eDate);
    	
    	if(editData.getProject() == null){
    		FDCMsgBox.showError("工程项目为空！");
    		abort();
    	}
    	taskMap.put("projectId", editData.getProject().getId().toString());
    	
    	if(editData.getBizDept() == null){
    		FDCMsgBox.showError("填报部门为空！");
    		abort();
    	}
    	taskMap.put("adminDept", editData.getBizDept().getId().toString());
    	
    	// 任务工程量填报是否按责任人填报 by cassiel_peng 2010-06-11
    	if(fillByRespPerson()){
    		Object o = prmtAdminPerson.getValue();
			if(o==null){
    			FDCMsgBox.showError("责任人不能为空！");
    			abort();
    		}else{
//    			UserInfo user = (UserInfo) o ;
//    			if(user.getPerson()==null){
//    				FDCMsgBox.showError("当前登录用户没有绑定职员，不能查看数据！");
//    				SysUtil.abort();
//    			}else{
//    				taskMap.put("creator", user.getPerson().getId().toString());
//    			}
    			if(o instanceof PersonInfo){
    				PersonInfo person = (PersonInfo)o;
    				if(person.getId() != null){
    					taskMap.put("creator", person.getId().toString());
    				}else{
    					FDCMsgBox.showError("当前登录用户没有绑定职员，不能查看数据！");
        				SysUtil.abort();
    				}
    				
    			}
    		}
    	}
    	
    	taskMap = WorkAmountBillFactory.getRemoteInstance().getSelectedTask(taskMap);
    	FDCScheduleTaskCollection cols = null;
         if(taskMap.get("taskCols") != null){
        	 cols = (FDCScheduleTaskCollection) taskMap.get("taskCols");
         }
         
         if(cols.size()>0 ){
        	if(getDetailTable().getRowCount()>0){
        	     int result = MsgBox.showConfirm2New(null, "当前的操作会覆盖当前已经录入的数据！是否确认？");
        	     if(JOptionPane.NO_OPTION == result){
        		     abort();
        	      }
        	}
        		this.getDetailTable().removeRows();
        		this.alreadyEnterMap.clear();
        		fillTable(cols);
        	
         }
    	
    }
    
    public void fillTable(FDCScheduleTaskCollection cols) throws BOSException{
    	Set wbsIds = new HashSet();
    	for(Iterator it = cols.iterator();it.hasNext();){
    		FDCScheduleTaskInfo entry = (FDCScheduleTaskInfo) it.next();
			wbsIds.add(entry.getWbs().getId().toString());
    	}
    	buildTreeTable(wbsIds);
    	IRow row = null;
    	for(Iterator it = cols.iterator();it.hasNext();){
    		FDCScheduleTaskInfo entry = (FDCScheduleTaskInfo) it.next();
    		if(entry!=null){
				if(!alreadyEnterMap.containsKey(entry.getId().toString())){
					row = getRowByTask(entry);
					if(row == null){
						row = kdtEntry.addRow();
						row.getCell("tasknumber").setUserObject(entry);
						row.getCell("tasknumber").setValue(entry);
						row.getCell("taskname").setValue(entry.getName());
						row.getCell("startDate").setValue(entry.getStart());
						row.getCell("endDate").setValue(entry.getEnd());
						row.getCell("adminPerson").setValue(entry.getAdminPerson().getName());
					}
					row.getStyleAttributes().setLocked(false);				
					if(entry != null){
						BigDecimal [] data = null;
//						if(taskCompleteInfo.containsKey(entry.getId().toString())){
//							data = (BigDecimal[]) taskCompleteInfo.get(entry.getId().toString());
//							row.getCell("totalAmount").setValue(data[0]);
//							row.getCell("totalPercent").setValue(data[1]);
//						}else{//modify warship at 2010/08/19
						    data = getTotalInfoByTaskId(entry.getId().toString());
						    row.getCell("totalAmount").setValue(data[0]);
							row.getCell("totalPercent").setValue(data[1]);
//						}
					}
			    	
			    	KDTDefaultCellEditor remarkEditor = PMClientHelper.getDateTractCellEditor(DataTypeEnum.STRING);
			    	row.getCell("remark").setEditor(remarkEditor);
					alreadyEnterMap.put(entry.getId().toString(), entry);
				}
			}
    	}
    	for(int i = 0; i < kdtEntry.getRowCount(); ++i){
			FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) kdtEntry.getCell(i, "tasknumber").getUserObject();
			if(task != null && !task.isIsLeaf()){
				for(int j = 0; j < kdtEntry.getColumnCount(); ++j)
				kdtEntry.getCell(i, j).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
			}
		}
    }
    
    /**
	 * output storeFields method
	 * 
	 */
    /* (non-Javadoc)
     * @see com.kingdee.eas.fdc.schedule.client.AbstractWorkAmountBillEditUI#storeFields()
     */
    
    
    public void storeFields()
    {
        super.storeFields();
        
        if(kdtEntry.getRowCount()>0){
        	editData.getEntry().clear();
        	WorkAmountEntryInfo entryInfo = null;
        	IRow row = null;
        	for(int i=0;i<kdtEntry.getRowCount();i++){
        		entryInfo = new WorkAmountEntryInfo();
        		row = kdtEntry.getRow(i);
        		if(row.getCell("tasknumber").getUserObject() != null){
        			if(row.getCell("tasknumber").getUserObject() instanceof FDCScheduleTaskInfo){
        				FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) row.getCell("tasknumber").getUserObject();
        				if(!task.isIsLeaf()) continue;
        				entryInfo.setTask(task);
        				entryInfo.setWbs(task.getWbs());
        				//by tim_gao 分录提交时，如果提交不加入ID，每次提交值是，会重新分配ID给各条分录 2011-4-11
        				// 这样会导致，上传附件提交后，由于新id,找不到之前id的附件
        				if(!FDCHelper.isEmpty(this.kdtEntry.getCell(i, "id").getValue())){
        					entryInfo.setId(BOSUuid.read(this.kdtEntry.getCell(i, "id").getValue().toString()));
            				
        				}
        				
        			}
        		}
        		if (row.getCell("amount").getValue()!=null)
        		entryInfo.setAmount(FDCHelper.toBigDecimal(row.getCell("amount").getValue()));
        		if (row.getCell("percent").getValue()!=null)
        		entryInfo.setPercent(FDCHelper.toBigDecimal(row.getCell("percent").getValue()));
//        		if (row.getCell("totalAmount").getValue()!=null)
//        			entryInfo.setTotalAmount(FDCHelper.toBigDecimal(row.getCell("totalAmount").getValue()));
//        		if (row.getCell("totalPercent").getValue()!=null)
//        			entryInfo.setTotalPercent(FDCHelper.toBigDecimal(row.getCell("totalPercent").getValue()));
        		if (row.getCell("confirmAmount").getValue()!=null)
        		entryInfo.setConfirmAmount(FDCHelper.toBigDecimal(row.getCell("confirmAmount").getValue()));
        		if (row.getCell("confirmPercent").getValue()!=null)
        		entryInfo.setConfirmPercent(FDCHelper.toBigDecimal(row.getCell("confirmPercent").getValue()));
        		if (row.getCell("completeDate").getValue()!=null)
        		entryInfo.setCompleteDate((Date)row.getCell("completeDate").getValue());
        		if (row.getCell("remark").getValue()!=null  )
        		entryInfo.setRemark(row.getCell("remark").getValue().toString().trim());
        		editData.getEntry().add(entryInfo);
        	}
        	
        }
        
        
    }

    
   
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
       
    }

 
   
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        if(editData.getProject() != null){
        	project = editData.getProject();
        }
    	super.actionAddNew_actionPerformed(e);
        editData.setProject(project);
        alreadyEnterMap.clear();
    }
    

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(editData.getState().equals(FDCBillStateEnum.AUDITTED) || editData.getState().equals(FDCBillStateEnum.AUDITTING)){
    		FDCMsgBox.showError("当前单据的状态不能执行此操作！");
    		abort();
    	}
        super.actionEdit_actionPerformed(e);
        this.btnSelected.setEnabled(true);
        
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	if(editData.getState().equals(FDCBillStateEnum.AUDITTED) || editData.getState().equals(FDCBillStateEnum.AUDITTING)){
    		FDCMsgBox.showError("当前单据的状态不能执行此操作！");
    		abort();
    	}
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
//       super.actionAttachment_actionPerformed(e);
    	
//    	checkSelected();
    	int rowIndex = getDetailTable().getSelectManager().getActiveRowIndex();
    	if(rowIndex == -1){
    		FDCMsgBox.showWarning("请选择相应的分录行！");
    		abort();
    	}
    	IRow  row = getDetailTable().getRow(rowIndex);
    	if(row.getCell("id").getValue() != null){
    		String id = row.getCell("id").getValue().toString();
    		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    		boolean isEdit = true;
    		if(editData.getState().equals(FDCBillStateEnum.AUDITTED)|| getOprtState().equals(OprtState.VIEW)){
    			isEdit = false;
    		}
    		acm.showAttachmentListUIByBoID(id, this,isEdit);
    		
    	}else{
    		//by tim_gao 确定可编辑分录行 2011-04-13
    		if(FDCColorConstants.cantEditColor.equals(row.getCell("tasknumber").getStyleAttributes().getBackground()))
    		{
    			FDCMsgBox.showError("请选择可编辑分录行！");
    		}
    		else{
    		FDCMsgBox.showError("请先保存单据，再进行附件管理 ！");
    		}
    		return ;
    	}
    	
    	
    	
    }

   
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

    
	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return WorkAmountBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return this.kdtEntry;
	}

	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return this.txtNumber;
	}
	
	protected IObjectValue createNewData() {
		// TODO Auto-generated method stub
		WorkAmountBillInfo info = new WorkAmountBillInfo();
	    CurProjectInfo projectInfo = getProjectFormUIContext();
	    
    	if(projectID==null){
		    if(projectInfo != null){
		    	info.setProject(projectInfo);
		    	project = projectInfo;
		    }else{
		    	FDCMsgBox.showError("请选择明细的工程项目！");
		    	logger.info(curProject);
		    	abort();
		    	
		    }
    	}else{//由计划汇总创建
    		try {
				projectInfo=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(projectID));
				info.setProject(projectInfo);
		    	project = projectInfo;
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
	    
		info.setBizDate(serverDate);
		info.setBizDept(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
	    info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		return info;
	}
	
	protected IObjectValue createNewDetailData(KDTable table) {
		// TODO Auto-generated method stub
		return new WorkAmountEntryInfo();
	}
 
	public SelectorItemCollection getSelectors() {
		// TODO Auto-generated method stub
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("entry.task.*"));
		sic.add(new SelectorItemInfo("entry.task.adminPerson.name"));
		sic.add(new SelectorItemInfo("entry.id"));
		sic.add(new SelectorItemInfo("project.*"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("cu.id"));
		return sic;
	}
	
	public void loadFields() {
		// TODO Auto-generated method stub
		super.loadFields();
		
		 CurProjectInfo projectInfo = getProjectFormUIContext();
		    if(projectInfo == null){
		    	projectInfo = editData.getProject();
		    }
		if(projectInfo != null){
			initAllTaskInfoByProjectId(projectInfo.getId().toString());
		}    
		
		try {
			fillDetailData();
		} catch (BOSException e) {
			handUIException(e);
		}
	}
	
	private void fillLongNumber(String longNumber, Set parentsLongNumbers){
		parentsLongNumbers.add(longNumber);
		int index = longNumber.lastIndexOf("!");
		if(index > 0){
			String parentLongNumber = longNumber.substring(0, index);
			fillLongNumber(parentLongNumber, parentsLongNumbers);
		}
	}
	
	private FDCScheduleTaskCollection getInitWBSTree(Set leafWbsIds) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);		
		filter.getFilterItems().add(new FilterItemInfo("id", leafWbsIds, CompareType.INCLUDE));
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("longNumber");
		selector.add("curProject");
		view.setSelector(selector);
		FDCWBSCollection wbsCol = FDCWBSFactory.getRemoteInstance().getFDCWBSCollection(view);
		Set longNumbers = new HashSet();
		for(int i = 0; i < wbsCol.size(); ++i){
			FDCWBSInfo wbs = wbsCol.get(i);
			fillLongNumber(wbs.getLongNumber(), longNumbers);
		}
		//避免脏数据引起中断
		if(wbsCol.isEmpty() || wbsCol.get(0).getCurProject() == null) return new FDCScheduleTaskCollection();
		view = new EntityViewInfo();
		filter = new FilterInfo();
		view.setFilter(filter);		
		filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("wbs.longNumber", longNumbers, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("wbs.curProject", wbsCol.get(0).getCurProject().getId().toString(), CompareType.INCLUDE));
		selector = new SelectorItemCollection();
		view.setSelector(selector);
		selector.add("*");
		selector.add("adminPerson.name");
		selector.add("wbs.level");
		SorterItemCollection sorter = new SorterItemCollection();
		view.setSorter(sorter);
		view.getSorter().add(new SorterItemInfo("wbs.curProject"));
		view.getSorter().add(new SorterItemInfo("wbs.longNumber"));
		return FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
	}
	
	private void buildTreeTable(Set leafWbsIds) throws BOSException{
		long start = System.currentTimeMillis();
		FDCScheduleTaskCollection tasks = getInitWBSTree(leafWbsIds);
		int depth = 1;
		for(int i = 0; i < tasks.size(); ++i){
			int level = tasks.get(i).getWbs().getLevel();
			depth = level > depth ? level: depth;
		}
		kdtEntry.getTreeColumn().setDepth(depth );
		IRow row = null;
		for(int i = 0; i < tasks.size(); ++i){
			row = kdtEntry.addRow();
			row.getCell("tasknumber").setUserObject(tasks.get(i));
			row.getCell("tasknumber").setValue(tasks.get(i).getNumber());
			row.getCell("taskname").setValue(tasks.get(i).getName());
			row.getCell("startDate").setValue(tasks.get(i).getStart());
			row.getCell("endDate").setValue(tasks.get(i).getEnd());
			row.getCell("adminPerson").setValue(tasks.get(i).getAdminPerson().getName());
			row.setTreeLevel(tasks.get(i).getWbs().getLevel());
			row.getStyleAttributes().setLocked(true);
		}
		logger.info("buildTreeTable.cost.time: "+(System.currentTimeMillis() - start));
	}
	
	private IRow getRowByTask(FDCScheduleTaskInfo task){
		int count = kdtEntry.getRowCount();
		for(int i = 0; i < count; ++i){			
			FDCScheduleTaskInfo temp = (FDCScheduleTaskInfo) kdtEntry.getCell(i, "tasknumber").getUserObject();
			if(temp != null && temp.getWbs().getId().toString().equals(task.getWbs().getId().toString())){
				return kdtEntry.getRow(i); 
			}
		}
		return null;		
	}
	public void fillDetailData() throws BOSException{
		WorkAmountEntryCollection cols = editData.getEntry();
		if(cols.size()>0){
			kdtEntry.removeRows();
			Set wbsIds = new HashSet();
			for(int i = 0; i < cols.size(); ++i){
				WorkAmountEntryInfo work = cols.get(i);
				if(work.getWbs()!=null){//避免脏数据引起中断
					wbsIds.add(work.getWbs().getId().toString());
				}
			}
			buildTreeTable(wbsIds);
			IRow row = null;
			for(Iterator it = cols.iterator();it.hasNext();){
				WorkAmountEntryInfo entry = (WorkAmountEntryInfo) it.next();
				if(entry.getTask()!=null){		
					row = getRowByTask(entry.getTask());
					if(row == null){
						row = kdtEntry.addRow();
						row.getCell("tasknumber").setUserObject(entry.getTask());
						row.getCell("tasknumber").setValue(entry.getTask());
						row.getCell("taskname").setValue(entry.getTask().getName());
						row.getCell("startDate").setValue(entry.getTask().getStart());
						row.getCell("endDate").setValue(entry.getTask().getEnd());
						row.getCell("adminPerson").setValue(entry.getTask().getAdminPerson().getName());
					}
					row.getStyleAttributes().setLocked(false);
					alreadyEnterMap.put(entry.getTask().getId().toString(), entry.getTask());
//				
					BigDecimal [] data=null;
					if(entry.getTask()!=null && entry.getTask().getId()!=null && entry.getTask().getId().toString()!=null){
						data = getTotalInfoByTaskId(entry.getTask().getId().toString());
					}
					if(data != null){
						row.getCell("totalAmount").setValue(data[0]);
						row.getCell("totalPercent").setValue(data[1]);
					}
					
					
					row.getCell("amount").setValue(entry.getAmount());
					row.getCell("percent").setValue(entry.getPercent());
					row.getCell("confirmAmount").setValue(entry.getConfirmAmount());
					row.getCell("confirmPercent").setValue(entry.getConfirmPercent());
					row.getCell("completeDate").setValue(entry.getCompleteDate());
					row.getCell("id").setValue(entry.getId().toString());
					//新增多条分录，“形象进度描述”下移动光标不能自动带出值add by warship at 2010/07/26
					KDMultiLangArea txtArea = new KDMultiLangArea();
			    	txtArea.setMaxLength(1000);
			    	txtArea.setSelectedItemData(entry.getRemark());
			    	KDTDefaultCellEditor remarkEditor = new KDTDefaultCellEditor(txtArea);
			    	row.getCell("remark").setEditor(remarkEditor);
					row.getCell("remark").setValue(entry.getRemark());
					((KDMultiLangArea)remarkEditor.getComponent()).setDefaultLangItemData(entry.getRemark());
				}			
			}
			for(int i = 0; i < kdtEntry.getRowCount(); ++i){
				FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) kdtEntry.getCell(i, "tasknumber").getUserObject();
				if(task != null && !task.isIsLeaf()){
					for(int j = 0; j < kdtEntry.getColumnCount(); ++j)
					kdtEntry.getCell(i, j).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
				}
			}
		}
	}
	
	protected void verifyInputForSave() throws Exception {
		// TODO Auto-generated method stub
//		super.verifyInputForSave();
		if(txtNumber.isEnabled()){
			if(editData.getNumber() == null || editData.getNumber().trim().length()==0){
				FDCMsgBox.showError("编码不能为空！");
				abort();
			}
		}
		if(editData.getName() == null || editData.getName().trim().length()==0){
			FDCMsgBox.showError("名称不能为空！");
			abort();
		}
		if(editData.getBizDept() == null){
    		FDCMsgBox.showError("填报部门不能为空！");
    		abort();
    	}
		Object o = prmtAdminPerson.getValue();
		if(o==null){
			FDCMsgBox.showError("责任人不能为空！");
			abort();
		}
		if(getDetailTable().getRowCount() == 0){
			FDCMsgBox.showError("分录不能为空！");
			abort();	
		}else{
			StringBuffer str = new StringBuffer("由于发生以下错误，不能保存：\n");
			int errCount = 0;
			IRow row = null;
			for(int i=0;i<getDetailTable().getRowCount();i++){
				row = getDetailTable().getRow(i);
				FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) row.getCell("tasknumber").getUserObject();
				if(task!=null && !task.isIsLeaf()){
					continue;
				}
				if(row.getCell("tasknumber").getValue()==null){
					errCount++;
					str.append("第");
					str.append(i+1);
					str.append("行任务编码为空！\n");
				}
				if(row.getCell("percent").getValue() == null){
					errCount++;
					str.append("第");
					str.append(i+1);
					str.append("行本次完工百分比为空！\n");
				}
				if(row.getCell("completeDate").getValue() == null){
					errCount++;
					str.append("第");
					str.append(i+1);
					str.append("行本次完工日期为空！\n");
				}else{		//任务工程量录入:实际完成时间不小于实际开始时间 modify by warship at 2010/07/02
						if(task != null && task.getActualStartDate() != null &&
								((Date)row.getCell("completeDate").getValue()).before(task.getActualStartDate())){
							errCount++;
							str.append("第");
							str.append(i+1);
							str.append("行完工日期 不能小于实际开始日期！\n");							
						}
					
				}
				if(row.getCell("confirmPercent").getValue() == null){
					errCount++;
					str.append("第");
					str.append(i+1);
					str.append("行本次确认百分比为空！\n");
				}
				
			}
			if(errCount>0){
				FDCMsgBox.showError(str.toString());
				abort();
			}
		}
	}
	
	protected void verifyInputForSubmint() throws Exception {
		// TODO Auto-generated method stub
//		super.verifyInputForSubmint();
		if(txtNumber.isEnabled()){
			if(editData.getNumber() == null || editData.getNumber().trim().length()==0){
				FDCMsgBox.showError("编码不能为空！");
				abort();
			}
		}
		if(editData.getName() == null || editData.getName().trim().length()==0){
			FDCMsgBox.showError("名称不能为空！");
			abort();
		}
		if(editData.getBizDept() == null){
    		FDCMsgBox.showError("填报部门不能为空！");
    		abort();
    	}
		Object o = prmtAdminPerson.getValue();
		if(o==null){
			FDCMsgBox.showError("责任人不能为空！");
			abort();
		}
		if(getDetailTable().getRowCount() == 0){
			FDCMsgBox.showError("分录不能为空！");
			abort();	
		}else{
			StringBuffer str = new StringBuffer("由于发生以下错误，不能提交：\n");
			int errCount = 0;
			IRow row = null;
			for(int i=0;i<getDetailTable().getRowCount();i++){
				row = getDetailTable().getRow(i);
				FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) row.getCell("tasknumber").getUserObject();
				if(task!=null && !task.isIsLeaf()){
					continue;
				}
				if(row.getCell("tasknumber").getValue()==null){
					errCount++;
					str.append("第");
					str.append(i+1);
					str.append("行任务编码为空！\n");
				}
//				if(row.getCell("amount").getValue() == null){
//					errCount++;
//					str.append("第");
//					str.append(i+1);
//					str.append("行本次完工工程量为空！\n");
//				}
				if(row.getCell("completeDate").getValue() == null){
					errCount++;
					str.append("第");
					str.append(i+1);
					str.append("行本次完工日期为空！\n");
				}else{			//任务工程量录入:实际完成时间不小于实际开始时间 modify by warship at 2010/07/02					
					if(task != null && task.getActualStartDate() != null &&
					((Date)row.getCell("completeDate").getValue()).before(task.getActualStartDate())){
							errCount++;
							str.append("第");
							str.append(i+1);
							str.append("行完工日期 不能小于实际开始日期！\n");
							//modify warship at 2010/08/19
//						}else if(this.editData.getEntry().get(i).getTask().getStart() != null &&
//								((Date)row.getCell("completeDate").getValue()).before(this.editData.getEntry().get(i).getTask().getStart())){
//							errCount++;
//							str.append("第");
//							str.append(i+1);
//							str.append("行完工日期 不能小于实际开始日期！\n");
						}
				}
				if(row.getCell("percent").getValue() == null){
					errCount++;
					str.append("第");
					str.append(i+1);
					str.append("行本次完工百分比为空！\n");
				}
//				if(row.getCell("confirmAmount").getValue() == null){
//					errCount++;
//					str.append("第");
//					str.append(i+1);
//					str.append("行本次确认工程量为空！\n");
//				}
				if(row.getCell("confirmPercent").getValue() == null){
					errCount++;
					str.append("第");
					str.append(i+1);
					str.append("行本次确认百分比为空！\n");
				}
				
			}
			if(errCount>0){
				FDCMsgBox.showError(str.toString());
				abort();
			}
		}
		
	
	}
	
	
	public String isOver(){
		StringBuffer str = new StringBuffer();
		IRow curRow = null;
		WorkAmountBillInfo info = null;
		for(int i=0;i<getDetailTable().getRowCount();i++){
			curRow = getDetailTable().getRow(i);
			if(curRow.getCell("tasknumber").getUserObject() != null){
				info = (WorkAmountBillInfo) curRow.getCell("tasknumber").getUserObject();
			}
		}
		return str.toString();
	}
	
}