/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerCollection;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerEntryCollection;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerFactory;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class ProjectScheduleUI extends AbstractProjectScheduleUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectScheduleUI.class);
    
    /**
     * output class constructor
     */
    public ProjectScheduleUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	actionEdit.setVisible(false);
    	menuEdit.setVisible(false);
    	actionAttachment.setVisible(false);
    	actionSubmitOption.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionSubmit.setVisible(true);
		this.actionSubmit.setEnabled(true);
		this.actionAuditResult.setVisible(true);
		this.btnWorkFlowG.setVisible(true);
		this.btnUnAudit.setVisible(true);
		this.btnMultiapprove.setVisible(true);
		this.btnNextPerson.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		menuItemUnAduit.setVisible(true);
    }
    
	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		//为了保证性能getValue只在打开的时候真真执行
		if(this.editData!=null){
			return this.editData;
		}
		if(pk==null){
			return null;
		}
		FDCScheduleInfo info;
//		若是从工作流中来，则传递的是FDCScheduleID
//		if(Boolean.TRUE.equals(getUIContext().get("isFromWorkflow"))){
//			info = FDCScheduleFactory.getRemoteInstance().getFDCScheduleInfo(pk);
//		}else{
//			info = ScheduleVerManagerFactory.getRemoteInstance().getVerData(pk.toString());
//			info.setId(BOSUuid.read(pk.toString()));
//		}
		info = ScheduleVerManagerFactory.getRemoteInstance().getVerData(pk.toString());
		info.setId(BOSUuid.read(pk.toString()));
		info.setBoolean("WEAKMODE", false);
		return info;
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
//		this.menuWorkflow.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionSubmit.setVisible(false);
	}
	public void onShow() throws Exception {
		super.onShow();
		this.btnAuditResult.setVisible(true);
		this.btnAuditResult.setEnabled(true);
	}
	
	protected boolean isOnlyView() {
		return false;
	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		this.actionSaveNewTask.setEnabled(false);
		this.actionSaveNewTask.setVisible(false);
		
		if(this.getScheduleGanttProject()!=null){
    		this.getScheduleGanttProject().isOnlyViewer=true;
    	}
	}
	
	/* 
	 * 对于计划只是查看
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#isModify()
	 */
	public boolean isModify() {
		return false;
	}
	/**
	 * 仅当选择的总进度计划为该工程项目最新版本且未进入工作流且为非已审批状态时，提交按钮才可用。
	 * 否则，提交操作可将对应单据状态设置为已提交，并根据是否启用工作流确定是否启动工作流。
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("state");
		sic.add("entrys.*");
		sic.add("entrys.schedule.*");
		ScheduleVerManagerInfo schVerInfo = ScheduleVerManagerFactory.getRemoteInstance().getScheduleVerManagerInfo(
				new ObjectUuidPK(editData.getBaseVer().getId()),sic);
		if(ScheduleStateEnum.SUBMITTED.equals(schVerInfo.getState())
				|| ScheduleStateEnum.SAVED.equals(schVerInfo.getState())
				|| schVerInfo.getState() == null){
			ScheduleVerManagerEntryCollection schVerCol = schVerInfo.getEntrys();
			for(int i = 0;i<schVerCol.size();i++){
				if(ScheduleStateEnum.SAVED.equals(schVerCol.get(i).getSchedule().getState())
						|| ScheduleStateEnum.EXETING.equals(schVerCol.get(i).getSchedule().getState())
						|| ScheduleStateEnum.CLOSED.equals(schVerCol.get(i).getSchedule().getState())){
					FDCMsgBox.showInfo("进度计划‘"+schVerCol.get(i).getSchedule().getName()+"’状态不符合操作条件，不能提交！");
					SysUtil.abort();
				}
			}
			super.actionSubmit_actionPerformed(e);
			FDCMsgBox.showInfo("操作成功！");
			setSaved(false);
			setSave(false);
		}
	}
	
	/**
	 * 反审批对应总进度计划，仅当选中的记录是该工程项目的最新版本且为已审批状态时，才可以进行反审批操作
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",editData.getBaseVer().getId().toString(),CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isLatestVer",Boolean.TRUE,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state",ScheduleStateEnum.AUDITTED,CompareType.EQUALS));
		view.setFilter(filter);
		ScheduleVerManagerCollection schVerCol = ScheduleVerManagerFactory.getRemoteInstance().getScheduleVerManagerCollection(view);
		if(schVerCol.size() <= 0){
			FDCMsgBox.showError("本记录不符合操作条件，不能反审批！");
			SysUtil.abort();
		}else{
			ScheduleVerManagerFactory.getRemoteInstance().unAudit(schVerCol.get(0).getId());
			FDCMsgBox.showInfo("操作成功！");
		}
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return ScheduleVerManagerFactory.getRemoteInstance();
	}
	protected void verifyInputForSubmit() throws Exception {
	}
	public boolean destroyWindow() {
		boolean b = super.destroyWindow();
		if( ProjectScheduleListUI.class.getName().equals(
				getUIContext().get(UIContext.OWNER).getClass().toString())){
			ProjectScheduleListUI owner = (ProjectScheduleListUI) getUIContext().get(UIContext.OWNER);
			try {
				owner.actionRefresh_actionPerformed(null);
			} catch (Exception e) {
				this.handleException(e);
			}
		}
		return b;
	}

}