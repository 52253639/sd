package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.TaskMaterialPlanFactory;
import com.kingdee.eas.fdc.schedule.TaskScheduleLogCollection;
import com.kingdee.eas.fdc.schedule.TaskScheduleLogFactory;
import com.kingdee.eas.fdc.schedule.TaskScheduleLogInfo;
import com.kingdee.util.UuidException;

public class TaskScheduleLogHelper {
	private final TaskExtProPanelHelper taskExtPropHelper; 
	private KDTable tblTaskLog;
	private KDWorkButton btnAddLine;
	private KDWorkButton btnEditLine;
	private KDWorkButton btnDelLine;
	private KDTextArea txtDescription;
	private TaskScheduleLogCollection taskLogCollection;
	public TaskScheduleLogHelper(TaskExtProPanelHelper helper) {
		this.taskExtPropHelper = helper;
		this.tblTaskLog = helper.getFDCSCHTaskPropUI().tblTaskLog;
		this.txtDescription = helper.getFDCSCHTaskPropUI().txtTaskLogDetail;
		this.btnAddLine = helper.getFDCSCHTaskPropUI().btnAddTaskLog;
		this.btnEditLine = helper.getFDCSCHTaskPropUI().btnEditTaskLog;
		this.btnDelLine = helper.getFDCSCHTaskPropUI().btnDelTaskLog;
	}
	
	public void load(){
		tblTaskLog.checkParsed();
		tblTaskLog.addKDTSelectListener(new KDTSelectListener(){
			public void tableSelectChanged(KDTSelectEvent e) {
				int actRowIdx = tblTaskLog.getSelectManager().getActiveRowIndex();
				if(actRowIdx < 0) return;
				IRow row = tblTaskLog.getRow(actRowIdx);
				if(row.getUserObject() != null){
					txtDescription.setStringValue(row.getUserObject().toString());
				}
			}
		});
		if(taskExtPropHelper.getExtProperties().get("taskScheduleLog") == null){
			taskLogCollection = new TaskScheduleLogCollection();
		}else{
			if(taskExtPropHelper.getExtProperties().get("taskScheduleLog") instanceof TaskScheduleLogInfo){
				taskLogCollection = new TaskScheduleLogCollection();
				taskLogCollection.add((TaskScheduleLogInfo) taskExtPropHelper.getExtProperties().get("taskScheduleLog"));
			}else{
				taskLogCollection = (TaskScheduleLogCollection) taskExtPropHelper.getExtProperties().get("taskScheduleLog");
			}
		}
		initTableBtn();
		fillTable();
	}
	
	private void initTableBtn(){
		btnAddLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				UIContext uiContext = new UIContext();
				uiContext.put(UIContext.OWNER, taskExtPropHelper.getFDCSCHTaskPropUI());
				uiContext.put("taskExt",taskExtPropHelper.getTaskExtInfo());
				uiContext.put("wbsID", taskExtPropHelper.getWBS().getId().toString());
				IUIWindow uiWindow;
				try {
					uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
							TaskScheduleLogEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
					uiWindow.show();
					uiWindow.getUIObject();
				} catch (UIException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnEditLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int actRowIdx = tblTaskLog.getSelectManager().getActiveRowIndex();
				if(actRowIdx < 0) return;
				String taskLogId = null;
				if(tblTaskLog.getCell(actRowIdx, "id").getValue() != null){
					taskLogId = tblTaskLog.getCell(actRowIdx, "id").getValue().toString();
				}else{
					return;
				}
				UIContext uiContext = new UIContext();
				uiContext.put(UIContext.OWNER, taskExtPropHelper.getFDCSCHTaskPropUI());
				uiContext.put("taskExt",taskExtPropHelper.getTaskExtInfo());
				uiContext.put("wbsID", taskExtPropHelper.getWBS().getId().toString());
				uiContext.put("id",taskLogId);
				IUIWindow uiWindow;
				try {
					uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
							TaskScheduleLogEditUI.class.getName(), uiContext, null, OprtState.EDIT);
					uiWindow.show();
					uiWindow.getUIObject();
				} catch (UIException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDelLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int actRowIdx = tblTaskLog.getSelectManager().getActiveRowIndex();
				if(actRowIdx < 0) return;
				String logId;
				if(tblTaskLog.getCell(actRowIdx,"id").getValue() != null){
					logId = tblTaskLog.getCell(actRowIdx, "id").getValue().toString();
				}else return;
				try {
					TaskScheduleLogFactory.getRemoteInstance().delete(new ObjectUuidPK(BOSUuid.read(logId)));
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
			}
		});
		txtDescription.setEditable(false);
		tblTaskLog.setEditable(false);
	}
	
	private void fillTable(){
		tblTaskLog.removeRows();
		for(int i=0;i<taskLogCollection.size();i++){
			TaskScheduleLogInfo logInfo = taskLogCollection.get(i);
			IRow row = tblTaskLog.addRow();
			row.getCell("id").setValue(logInfo.getId().toString());
			row.getCell("happenTime").setValue(logInfo.getHappenTime());
			row.getCell("title").setValue(logInfo.getName());
			row.setUserObject(logInfo.getDetail());
		}
	}
	
	public void setEditStatus() {
		btnAddLine.setEnabled(false);
		btnEditLine.setEnabled(false);
		btnDelLine.setEnabled(false);
	}
	public void setViewStatus() {
		btnAddLine.setEnabled(false);
		btnEditLine.setEnabled(false);
		btnDelLine.setEnabled(false);
	}
	public void setExecutingStatus(){
		FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) taskExtPropHelper.getFDCSCHTaskPropUI().getSelectedTask().getScheduleTaskInfo();
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		if(task.getAdminPerson()!=null&&currentUserInfo!=null&&task.getAdminPerson().getId().equals(currentUserInfo.getPerson().getId())){
			btnAddLine.setEnabled(true);
			btnEditLine.setEnabled(true);
			btnDelLine.setEnabled(true);
		}else{
			btnAddLine.setEnabled(false);
			btnEditLine.setEnabled(false);
			btnDelLine.setEnabled(false);
		}
	}
	
	public void relaod()throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("taskExt.wbs.id",taskExtPropHelper.getWBS().getId().toString()));
		view.setFilter(filter);
		taskLogCollection = TaskScheduleLogFactory.getRemoteInstance().getTaskScheduleLogCollection(view);
		fillTable();
	}
}
