/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtInfo;
import com.kingdee.eas.fdc.schedule.TaskScheduleLogFactory;
import com.kingdee.eas.fdc.schedule.TaskScheduleLogInfo;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class TaskScheduleLogEditUI extends AbstractTaskScheduleLogEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(TaskScheduleLogEditUI.class);
    private FDCScheduleTaskExtInfo taskExtInfo;
    public FDCScheduleTaskExtInfo getTaskExtInfo() {
		return taskExtInfo;
	}

	public void setTaskExtInfo(FDCScheduleTaskExtInfo taskExtInfo) {
		this.taskExtInfo = taskExtInfo;
	}

	/**
     * output class constructor
     */
    public TaskScheduleLogEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields() {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	if(getUIContext().get("id") != null){
    		entityTaskScheduleLog = TaskScheduleLogFactory.getRemoteInstance().getTaskScheduleLogInfo(
    				new ObjectUuidPK(BOSUuid.read(getUIContext().get("id").toString())));
    		txtName.setStringValue(entityTaskScheduleLog.getName());
    		txtDescription.setStringValue(entityTaskScheduleLog.getDetail());
    		pkHappenTime.setValue(entityTaskScheduleLog.getHappenTime());
    	}
    	if(OprtState.ADDNEW.equals(getOprtState())
    			||OprtState.EDIT.equals(getOprtState())){
    		setComponentStatus(false);
    	}else{
    		setComponentStatus(true);
    	}
    	txtName.setMaxLength(200);
    	txtDescription.setMaxLength(1000);
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	veryfy();
    	if(entityTaskScheduleLog == null)
    		entityTaskScheduleLog = new TaskScheduleLogInfo();
    	entityTaskScheduleLog.setHappenTime((Date) pkHappenTime.getValue());
    	entityTaskScheduleLog.setName(txtName.getStringValue());
    	entityTaskScheduleLog.setDetail(txtDescription.getStringValue());
    	if(getUIContext().get("taskExt") == null
    			|| ((FDCScheduleTaskExtInfo)getUIContext().get("taskExt")).getId() == null){
    		Map map = new HashMap();
    		map.put("taskLog", entityTaskScheduleLog);
    		map.put("wbsID", getUIContext().get("wbsID"));
    		Map map2 = TaskScheduleLogFactory.getRemoteInstance().saveTaskExt(map);
    		if(map2.get("taskScheduleLog") != null){
    			entityTaskScheduleLog = (TaskScheduleLogInfo) map2.get("taskScheduleLog");
    		}
    		if(map2.get("taskExtInfo") != null){
    			setTaskExtInfo((FDCScheduleTaskExtInfo) map2.get("taskExtInfo"));
    		}
    	}else{
    		if(OprtState.ADDNEW.equals(getOprtState())){
    			entityTaskScheduleLog.setTaskExt((FDCScheduleTaskExtInfo) getUIContext().get("taskExt"));
    			entityTaskScheduleLog.setId(BOSUuid.read(
    					TaskScheduleLogFactory.getRemoteInstance().save(entityTaskScheduleLog).toString()));
    		}else{
    			TaskScheduleLogFactory.getRemoteInstance().save(entityTaskScheduleLog);
    		}
    	}
    	FDCMsgBox.showInfo("保存成功！");
    }
    private void veryfy() throws EASBizException, BOSException{
    	if(txtName.getText() == null||txtName.getText().trim().length()<1){
    		FDCMsgBox.showError("标题不能为空！");
    		SysUtil.abort();
    	}
    	if(pkHappenTime.getValue() == null){
    		FDCMsgBox.showError("发生时间不能为空！");
    		SysUtil.abort();
    	}
    	FilterInfo filter = new FilterInfo();
    	if(getUIContext().get("taskExt") != null){
    		filter.getFilterItems().add(
    				new FilterItemInfo("taskExt.id",((FDCScheduleTaskExtInfo)getUIContext().get("taskExt")).getId().toString(),CompareType.EQUALS));
    		filter.getFilterItems().add(new FilterItemInfo("name",txtName.getText().trim(),CompareType.EQUALS));
    		if(entityTaskScheduleLog!=null && entityTaskScheduleLog.getId() != null){
    			filter.getFilterItems().add(new FilterItemInfo("id",entityTaskScheduleLog.getId().toString(),CompareType.NOTEQUALS));
    		}
    		if(TaskScheduleLogFactory.getRemoteInstance().exists(filter)){
        		FDCMsgBox.showError("标题不能重复!");
        		SysUtil.abort();
        	}
    	}
    }
    
    private void setComponentStatus(boolean isView){
    	if(isView){
    		txtName.setEditable(false);
    		txtDescription.setEditable(false);
    		pkHappenTime.setEditable(false);
    		actionSave.setEnabled(false);
    		btnSave.setEnabled(false);
    	}else{
    		txtName.setEditable(true);
    		txtDescription.setEditable(true);
    		pkHappenTime.setEditable(true);
    		actionSave.setEnabled(true);
    		btnSave.setEnabled(true);
    	}
    }
    protected void disposeUIWindow() {
    	if(OprtState.VIEW.equals(getOprtState())) return;
    	super.disposeUIWindow();
		if (entityTaskScheduleLog != null && entityTaskScheduleLog.getId() != null) {
			if (getUIContext().get(UIContext.OWNER) instanceof FDCScheduleTaskPropertiesUI) {
				FDCScheduleTaskPropertiesUI mainPanelHelper = (FDCScheduleTaskPropertiesUI) getUIContext().get(UIContext.OWNER);
				try {
					mainPanelHelper.loadTaskLog();
				} catch (BOSException e) {
					this.handleException(e);
				}
				
			}
		}
    }
}