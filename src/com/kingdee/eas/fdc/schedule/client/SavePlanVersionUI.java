/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.ext.IFilterInfoProducer;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.PlanAdjustReasonInfo;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * 
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 描述： 计划调整页面
 * 
 * @author 杜红明
 * @version EAS7.0
 * @createDate 2011-8-28
 * @see
 */
public class SavePlanVersionUI extends AbstractSavePlanVersionUI {
	
	private static final Logger logger = CoreUIObject.getLogger(SavePlanVersionUI.class);
	private FDCScheduleInfo editData;
	private FDCScheduleBaseEditUI editUI;
	public boolean isSave = false;
	private FDCScheduleInfo newestVerSchedule;

	/**
	 * output class constructor
	 */
	public SavePlanVersionUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.btnSave.setEnabled(true);
		 editData = (FDCScheduleInfo) getUIContext().get("editData");
		editUI = (FDCScheduleBaseEditUI) getUIContext().get("editUI");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (editUI.prmtCurproject.getValue() == null) {
			editData.setProject((CurProjectInfo) editUI.prmtCurproject
					.getValue());
		}
		newestVerSchedule = FDCScheduleFactory.getRemoteInstance()
				.getNewestVerSchedule(editData.getProject(),
						editData.getProjectSpecial());
		if (editData.getProjectSpecial() != null
				&& editData.getProject() != null) {
			txtVersion.setText(editData.getProject().getName()
					+ editData.getProjectSpecial()+ "版" + new Float(editData.getVersion() + 1)  + "_"  + sdf.format(new Date()));
		}
		if (editData.getProjectSpecial() == null
				&& editData.getProject() != null) {
			String version = "执行版";
//			if(editData.isIsCheckVersion()){
//				version="考核版";
//			}
			String stage = editData.getScheduleStage()!= null?editData.getScheduleStage().getName():"";
			editData.setVersionName(editData.getProject().getName()+stage
					+version+ new Float(editData.getVersion()+1f)+"_"+
					sdf.format(new Date()));
			txtVersion.setText(editData.getVersionName());
		}
		
		prmtAdjustReson.setFilterInfoProducer(new IFilterInfoProducer(){

			public FilterInfo getFilterInfo() {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
				return filter;
			}

			public void setCurrentCtrlUnit(CtrlUnitInfo cui) {
				
			}

			public void setCurrentMainBizOrgUnit(OrgUnitInfo oui, OrgType ot) {
				
			}});
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		verify();
		editData.setVersionDate((Date) pkAdjustDate.getValue());
		editData.setVersion(new Float(newestVerSchedule.getVersion() + 1f).floatValue());
		editData.setDescription(txtDesc.getText());
		editData.setVersionName(txtVersion.getText());
		editData.setAdjustReason((PlanAdjustReasonInfo) prmtAdjustReson.getValue());
		editData.setId(null);
		editData.setIsLatestVer(false);
		editData.setCreateTime(new Timestamp(System.currentTimeMillis()));
		editData.setState(ScheduleStateEnum.SAVED);
		ScheduleTaskBaseCollection tasks = editData.getScheduleTasks();
		Map temp = new HashMap();
		ScheduleTaskBizTypeInfo bizType = null;
		for (int i = 0; i < tasks.size(); i++) {
			FDCScheduleTaskInfo scheduleTaskBaseInfo = (FDCScheduleTaskInfo) tasks.get(i);
			String oldID = scheduleTaskBaseInfo.getSrcID();
			BOSUuid newID = BOSUuid.create(scheduleTaskBaseInfo.getBOSType());
			scheduleTaskBaseInfo.setId(newID);
			temp.put(oldID, scheduleTaskBaseInfo);
			// temp.put(newID.toString(), oldID);
			scheduleTaskBaseInfo.put("myOldStartDate", scheduleTaskBaseInfo.getStart());
			scheduleTaskBaseInfo.put("myOldEndDate", scheduleTaskBaseInfo.getEnd());
			scheduleTaskBaseInfo.put("myOldCheckDate", ((FDCScheduleTaskInfo) scheduleTaskBaseInfo).getCheckDate()); 
			
		}
		
		for (int i = 0; i < tasks.size(); i++) {
			FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) tasks.get(i);
			
			ScheduleTaskBizTypeCollection bizTypeCollection = task.getBizType();
			ScheduleTaskBizTypeCollection newBizTypeCollection = new ScheduleTaskBizTypeCollection();
			for (Iterator it = bizTypeCollection.iterator(); it.hasNext();) {
				bizType = (ScheduleTaskBizTypeInfo) it.next();
				bizType.setId(BOSUuid.create(bizType.getBOSType()));
				bizType.setParent((FDCScheduleTaskInfo) temp.get(task.getSrcID()));
				newBizTypeCollection.add(bizType);
			}

			task.getBizType().clear();
			task.getBizType().addCollection(newBizTypeCollection);

			if (null != task.getParent() && temp.containsKey(task.getParent().getSrcID().toString())) {
				task.setParent((FDCScheduleTaskInfo) temp.get(task.getParent().getSrcID()));
			}


			FDCScheduleTaskDependCollection depends = task.getDependEntrys();
			if (depends == null || depends.size() < 1) {
				continue;
			}

			for (int j = 0; j < depends.size(); j++) {
				depends.get(j).setId(null);
				FDCScheduleTaskDependInfo depend = depends.get(j);
				String taskID = ((FDCScheduleTaskInfo) depend.getTaskBase()).getSrcID();
				String dependTaskId = ((FDCScheduleTaskInfo) depend.getDependTaskBase()).getSrcID();
				if (temp.containsKey(taskID)) {
					depend.put("task", temp.get(taskID));
					depend.put("dependTask", temp.get(dependTaskId));
				}
			}
		}
		isSave = true;
		editUI.setDataObject(editData);
		FDCMsgBox.showInfo(this, "计划版本调整保存成功!");
		this.uiWindow.close();
	}

	private void verify() {
		if (FDCHelper.isEmpty(txtVersion.getText())) {
			FDCMsgBox.showWarning(this, "版本名称不能为空！");
			SysUtil.abort();
		}
		if (FDCHelper.isEmpty(pkAdjustDate.getValue())) {
			FDCMsgBox.showWarning(this, "调整日期不能为空！");
			SysUtil.abort();
		}
		if (FDCHelper.isEmpty(prmtAdjustReson.getValue())) {
			FDCMsgBox.showWarning(this, "调整原因不能为空！");
			SysUtil.abort();
		} 
		
		if (txtDesc != null && txtDesc.getText() != null && txtDesc.getText() != null) {
			CurProjectInfo project = editData.getProject();
			ProjectSpecialInfo projectSpecial = editData.getProjectSpecial();
			if (project != null && projectSpecial != null) {
				String text = txtDesc.getText();
				if (text.length() > 500) {
					FDCMsgBox.showWarning(this, "调整说明不能超过500个字符！");
					SysUtil.abort();
				}
			}
			if (project != null && projectSpecial == null) {
				String text = txtDesc.getText();
				if (text.length() > 200) {
					FDCMsgBox.showWarning(this, "调整说明不能 超过200个字符！");
					SysUtil.abort();
				}
			}
		}
	}
	
	/**
	 * 
	 * @description 	修改UI标题名称
	 * @author 			周航健
	 * @createDate 		2011-10-06
	 * @param			title
	 * @return			
	 * 
	 * @version 		EAS7.0
	 */
	public void setUITitle(String title) {
		title = "计划版本调整";
		super.setUITitle(title);
	}
}