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
import com.kingdee.eas.fdc.schedule.TaskMaterialPlanFactory;
import com.kingdee.eas.fdc.schedule.TaskMaterialPlanInfo;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class TaskMaterialPlanEditUI extends AbstractTaskMaterialPlanEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(TaskMaterialPlanEditUI.class);
    private FDCScheduleTaskExtInfo taskExtInfo;
    public FDCScheduleTaskExtInfo getTaskExtInfo() {
		return taskExtInfo;
	}
	public void setTaskExtInfo(FDCScheduleTaskExtInfo taskExtInfo) {
		this.taskExtInfo = taskExtInfo;
	}
	public TaskMaterialPlanEditUI() throws Exception  {
        super();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	if(getUIContext().get("id")!=null){
    		entityTaskMaterialPlan = 
    			TaskMaterialPlanFactory.getRemoteInstance().getTaskMaterialPlanInfo("where id='"+getUIContext().get("id")+"'");
    		txtName.setText(entityTaskMaterialPlan.getName());
        	txtDescription.setText(entityTaskMaterialPlan.getDescription());
        	pkDate.setValue(entityTaskMaterialPlan.getDate());
    	}
    	txtName.setMaxLength(80);
    	txtDescription.setMaxLength(255);
    	
    }
    
    public void storeFields()  {
        super.storeFields();
    }
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	verify();
    	if(entityTaskMaterialPlan == null)
    		entityTaskMaterialPlan = new TaskMaterialPlanInfo();
    	entityTaskMaterialPlan.setName(txtName.getText());
    	entityTaskMaterialPlan.setDate((Date)pkDate.getValue());
    	entityTaskMaterialPlan.setDescription(txtDescription.getText());
    	if(getUIContext().get("taskExt")==null 
    			|| ((FDCScheduleTaskExtInfo)getUIContext().get("taskExt")).getId() == null){
    		Map map = new HashMap();
    		map.put("materialPlan", entityTaskMaterialPlan);
    		map.put("wbsID", getUIContext().get("wbsID"));
    		Map map2 = TaskMaterialPlanFactory.getRemoteInstance().saveTaskExt(map);
    		if(map2.get("materialPlan")!=null){
    			entityTaskMaterialPlan = (TaskMaterialPlanInfo) map2.get("materialPlan");
    		}
    		if(map2.get("taskExtInfo") != null){
    			setTaskExtInfo((FDCScheduleTaskExtInfo) map2.get("taskExtInfo"));
    		}
    	}else{
    		entityTaskMaterialPlan.setTaskExt((FDCScheduleTaskExtInfo) getUIContext().get("taskExt"));
    		entityTaskMaterialPlan.setId(BOSUuid.read(
    			TaskMaterialPlanFactory.getRemoteInstance().save(entityTaskMaterialPlan).toString()));
    	}
    	FDCMsgBox.showInfo("保存成功！");
    }
    public void loadFields() {
    	super.loadFields();
    	txtName.setMaxLength(80);
    	txtDescription.setMaxLength(255);
    }
    private void verify() throws EASBizException, BOSException{
    	if(txtName.getText() == null||txtName.getText().trim().length()<1){
    		FDCMsgBox.showError("标题不能为空！");
    		SysUtil.abort();
    	}
    	if(pkDate.getValue() == null){
    		FDCMsgBox.showError("计划时间不能为空！");
    		SysUtil.abort();
    	}
    	FilterInfo filter = new FilterInfo();
    	if(getUIContext().get("taskExt") != null){
    		filter.getFilterItems().add(
    				new FilterItemInfo("taskExt.id",((FDCScheduleTaskExtInfo)getUIContext().get("taskExt")).getId().toString(),CompareType.EQUALS));
    		filter.getFilterItems().add(new FilterItemInfo("name",txtName.getText().trim(),CompareType.EQUALS));
    		if(entityTaskMaterialPlan!=null && entityTaskMaterialPlan.getId() != null){
    			filter.getFilterItems().add(new FilterItemInfo("id",entityTaskMaterialPlan.getId().toString(),CompareType.NOTEQUALS));
    		}
    		if(TaskMaterialPlanFactory.getRemoteInstance().exists(filter)){
        		FDCMsgBox.showError("标题不能重复!");
        		SysUtil.abort();
        	}
    	}
    }
    
    protected void disposeUIWindow() {
    	if(OprtState.VIEW.equals(getOprtState())) return;
    	boolean isModify = false;
		if (entityTaskMaterialPlan == null
				&& (!this.txtName.getText().equals("") || !this.txtDescription.getText().equals("") || this.pkDate
						.getValue() != null)) {
			isModify = true;
		} else {
			if (!this.txtName.getText().equals(entityTaskMaterialPlan.getName() == null ? "" : entityTaskMaterialPlan.getName())) {
				isModify = true;
			}
			if (!this.txtDescription.getText().equals(
					entityTaskMaterialPlan.getDescription() == null ? "" : entityTaskMaterialPlan.getDescription())) {
				isModify = true;
			}
			if (entityTaskMaterialPlan.getDate() != null && this.pkDate.getValue() == null) {
				isModify = true;
			} else if (entityTaskMaterialPlan.getDate() == null && this.pkDate.getValue() != null) {
				isModify = true;
			} else if (entityTaskMaterialPlan.getDate() != null && this.pkDate.getValue() != null) {
				if (((Date) this.pkDate.getValue()).getTime() != entityTaskMaterialPlan.getDate().getTime()) {
					isModify = true;
				}
			}
		}
		if (isModify) {
			int rtn = FDCMsgBox.showConfirm3("数据有修改，是否保存？");
			if (rtn == FDCMsgBox.CANCEL) {
				SysUtil.abort();
			} else if (rtn == FDCMsgBox.YES) {
				if (this.actionSave.isEnabled()) {
					try {
						actionSave_actionPerformed(null);
					} catch (Exception e) {
						this.handleException(e);
					}
				}
			}
		}
		super.disposeUIWindow();
		if (entityTaskMaterialPlan != null && entityTaskMaterialPlan.getId() != null) {
			if (getUIContext().get(UIContext.OWNER) instanceof FDCScheduleTaskPropertiesUI) {
				FDCScheduleTaskPropertiesUI mainPanelHelper = (FDCScheduleTaskPropertiesUI) getUIContext().get(UIContext.OWNER);
				try {
					mainPanelHelper.loadMaterial();
				} catch (BOSException e) {
					this.handleException(e);
				}
				
			}
		}
    }
}