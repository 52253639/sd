/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.client.AbstractProjectScheduleListUI.ActionViewWorkFlow;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ScheduleExecEditUI extends AbstractScheduleExecEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ScheduleExecEditUI.class);
    
    /**
     * output class constructor
     */
    public ScheduleExecEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	//修改提交选项不显示
    	this.menuSubmitOption.setVisible(false);
    	actionAttachment.setVisible(false);
    	btnAttachment.setVisible(false);
    	MenuItemAttachment.setVisible(false);
    	actionAudit.setVisible(false);
    	actionUnAudit.setVisible(false);
    }
    
    protected UIContext getPropertyUIContext() {
    	UIContext uiContext = super.getPropertyUIContext();
    	//逻辑移到属性界面里面去了
/*    	List selectedTasks = getScheduleGanttProject().getTaskSelectionContext().getSelectedTasks();
    	if(selectedTasks!=null&&selectedTasks.size()>0){
    		KDTask task=(KDTask)selectedTasks.get(0);
//    		uiContext.put("isScheduleExec", Boolean.valueOf(isExecTask((FDCScheduleTaskInfo)task.getScheduleTaskInfo())));
    	}else{
    		FDCMsgBox.showWarning(this, "请选择任务!");
    		SysUtil.abort();
    	}*/
    	
    	uiContext.put("OprtState", OprtState.VIEW);
    	uiContext.put("isScheduleExec", getUIContext().get("isScheduleExec"));
		return uiContext;
    }
    
    /**
     * 判断选择的节点是否处在执行状态
     * 
     * 任务处于执行状态,且执行人是当前用户对应的职员才可以执行,否者只能查看属性
     * @param task
     * @return
     */
    protected boolean isExecTask(FDCScheduleTaskInfo task){
    	if(getUIContext().get("isScheduleExec")==null){
    		//点击执行按钮才可以执行,否者为查看 
    		return false;
    	}
    	if(task!=null&&task.getScheduleBase().getState()==ScheduleStateEnum.EXETING){
    		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
    		if(task.getAdminPerson()!=null&&currentUserInfo!=null&&task.getAdminPerson().getId().equals(currentUserInfo.getPerson().getId())){
    			return true;
    		}
    	}
    	return false;
    }
    public void onShow() throws Exception {
    	// TODO Auto-generated method stub
    	super.onShow();
    	this.btnUnAudit.setVisible(false);
    	this.menuItemUnAduit.setVisible(false);
    	this.actionUnAudit.setVisible(false);
    }
    protected void initWorkButton() {
    	super.initWorkButton();
    	FDCClientHelper.setActionEnable(this.actionEdit, false);
    	FDCClientHelper.setActionEnable(this.actionAttachment, false);
    	actionEdit.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_datatask_execute"));
    }
    public void loadFields() {
    	super.loadFields();
    }
    
    /* 
     * 计划执行
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionEdit_actionPerformed(e);
    	getUIContext().put("isScheduleExec", Boolean.TRUE);
    	this.actionEdit.setVisible(true);
    	this.actionEdit.setEnabled(false);
    }
    
    public void setOprtState(String oprtType) {
    	super.setOprtState(oprtType);
    	if(getUIContext().get("isScheduleExec")==Boolean.TRUE){
    		this.actionEdit.setVisible(true);
    		this.actionEdit.setEnabled(false);
    	}else{
    		this.actionEdit.setVisible(true);
    		this.actionEdit.setEnabled(true);
    	}
    }
}