/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MeasureStageFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class CopyScheduleUI extends AbstractCopyScheduleUI
{
    private static final Logger logger = CoreUIObject.getLogger(CopyScheduleUI.class);
    
    
    /**
     * output class constructor
     */
    public CopyScheduleUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	this.actionSaveExit.setEnabled(true);
    	this.txtCurrentVersion.setEnabled(false);
    	if(this.getUIContext().get("value") != null){
    		FDCScheduleInfo schedule = (FDCScheduleInfo) this.getUIContext().get("value");
    		this.txtCurrentVersion.setText(schedule.getVersionName());
    		if(schedule.getProjectSpecial()== null){
    			this.contProjectSpecial.setVisible(false);
    		}
    		loadCurProject();    		
    		
    	}
    	prmtProjectSpecial.addSelectorListener(new SelectorListener(){

			public void willShow(SelectorEvent e) {
				if(prmtProject.getData()==null){
					FDCMsgBox.showError("请选择工程项目！");
					abort();
				}
			    CurProjectInfo prj = (CurProjectInfo) prmtProject.getData();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("curproject.id",prj.getId().toString()));
				String sql = "select fid from t_sch_projectspecial where fid not in (select fprojectspecialid from t_sch_fdcschedule where fprojectid ='"+prj.getId().toString()+"' and fprojectspecialid  is not null) and  FCurProjectID ='"+prj.getId().toString()+"' ";
				filter.getFilterItems().add(new FilterItemInfo("id",sql,CompareType.INNER));
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				prmtProjectSpecial.getQueryAgent().resetRuntimeEntityView();
				prmtProjectSpecial.setEntityViewInfo(view);
			}});
    	prmtProject.addSelectorListener(new SelectorListener(){

			
			public void willShow(SelectorEvent e) {
					CacheServiceFactory.getInstance().discardType(BOSObjectType.create("F9E5E92B"));
			}});
    }
    
    private void loadCurProject() {
 		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
//		filter.getFilterItems().add(new FilterItemInfo("projectStatus.id", "KQNHQgEVEADgABmewKgTBY160rk=",CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		String sql = "select fid from t_fdc_curproject where FCostCenterId in (select fid from t_org_baseunit where flongnumber like  (select flongnumber||'%' from t_org_baseUnit where fid ='"
				+ SysContext.getSysContext().getCurrentCostUnit().getId().toString()
				+ "') and fisfreeze = 0 and fisOuSealUp = 0  and fid in (select forgid from T_PM_OrgRange where fuserid = '"
				+ SysContext.getSysContext().getCurrentUserInfo().getId()
						.toString() + "'))";
//		String sql = "select fid from t_fdc_curproject where   FCostCenterId in  (select forgid from T_PM_OrgRange where fuserid = '"
//			+ SysContext.getSysContext().getCurrentUserInfo().getId().toString() + "')";
		if(!contProjectSpecial.isVisible()){
//			sql = "select fid from t_fdc_curproject where fid not in (select fprojectid from t_sch_fdcschedule where FProjectSpecialID is null)";
			sql+=" and fid not in (select fprojectid from t_sch_fdcschedule where fprojectspecialid is null  group by fprojectid having count(fprojectid) >0) ";
//			sql =  "select fid from t_fdc_curproject where fid not in (select fprojectid from t_sch_fdcschedule where FProjectSpecialID is not null)";
//			filter.getFilterItems().add(new FilterItemInfo("id", sql, CompareType.INNER));
		}
		filter.getFilterItems().add(new FilterItemInfo("id", sql, CompareType.INNER));
		view.setFilter(filter);
		this.prmtProject.getQueryAgent().resetRuntimeEntityView();
		this.prmtProject.setEntityViewInfo(view);
		
	
	}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    
    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	MeasureStageInfo positionStage =MeasureStageFactory.getRemoteInstance().getMeasureStageInfo(new ObjectUuidPK("Ozeozpe0T7q4o5qUyYcYt/AIFDI="));
    	FDCScheduleInfo srcInfo = (FDCScheduleInfo) getUIContext().get("value");
    	CurProjectInfo project = (CurProjectInfo) this.prmtProject.getData();
    	ProjectSpecialInfo prjSpecial = (ProjectSpecialInfo) this.prmtProjectSpecial.getData();
    	if(project == null){
    		FDCMsgBox.showError("请选择工程项目!");
    		abort();
    	}
    	if(srcInfo.getProjectSpecial()!= null && prjSpecial  == null){
    		FDCMsgBox.showError("请选择专项!");
    		abort();
    	}
    	FDCScheduleInfo info = (FDCScheduleInfo) srcInfo.clone();
    	info.setVersionDate(FDCDateHelper.getServerTimeStamp());
    	info.setId(BOSUuid.create(info.getBOSType()));
    	info.setProject(project);
    	info.setVersion(1.0f);
        if(project != null ){
        	StringBuffer versionName = new StringBuffer(project.getName());
        	if(prjSpecial!= null){
        		versionName.append(prjSpecial.getName());
        	}else{
        		versionName.append("_");
        		//定位阶段执行版
        		versionName.append(positionStage);
        		versionName.append("执行版");
        	}
        	versionName.append(DateTimeUtils.format(info.getVersionDate(),"yyyy-MM-dd"));
        	versionName.append("_");
        	versionName.append(info.getVersion());
        	info.setVersionName(versionName.toString());
        }
        info.setIsCheckVersion(false);
    	info.setDescription(txtDescription.getText());
    	if(StringUtils.isEmpty(txtDescription.getText())){
    		info.setDescription(null);
    	}
    	if(prjSpecial != null){
    		info.setProjectSpecial(prjSpecial);
    	}else{
    		info.setScheduleStage(positionStage);
    	}
    	info.setIsLatestVer(false);
    	info.setState(ScheduleStateEnum.SAVED);
    	FDCScheduleTaskCollection tasks = (FDCScheduleTaskCollection) srcInfo.getTaskEntrys().clone();
    	ScheduleTaskBizTypeInfo bizType = null;
    	Map temp = new HashMap();
		for (int i = 0; i < tasks.size(); i++) {
			FDCScheduleTaskInfo scheduleTaskBaseInfo = (FDCScheduleTaskInfo) tasks.get(i);
			String oldID = scheduleTaskBaseInfo.getSrcID();
			BOSUuid newID = BOSUuid.create(scheduleTaskBaseInfo.getBOSType());
			scheduleTaskBaseInfo.setId(newID);
			scheduleTaskBaseInfo.setSrcID(newID.toString());
			temp.put(oldID, scheduleTaskBaseInfo);
			
		}
    	for (int i = 0; i < tasks.size(); i++) {
			FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) tasks.get(i);
			task.setSchedule(info);
			task.setComplete(FDCHelper.ZERO);
			task.setActualEndDate(null);
			task.setActualStartDate(null);
			task.setWorkLoad(null);
			task.setIntendEndDate(null);
			if(info.getProjectSpecial() != null){
				task.setDependMainTaskID(null);
			}
			
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
    	
    	
    	info.getTaskEntrys().clear();
    	info.getTaskEntrys().addCollection(tasks);
    	
    	FDCScheduleBaseEditUI editUI = (FDCScheduleBaseEditUI) getUIContext().get("editUI");
    	editUI.setDataObject(info);
    	editUI.refresh(info);
    	editUI.prmtCurproject.setDataNoNotify(project);
    	editUI.cbState.setSelectedItem(info.getState());
    	if(info.getProjectSpecial()== null){
    		if(editUI.getClass().equals(MainScheduleEditUI.class)){
    			MainScheduleEditUI ui = (MainScheduleEditUI) editUI;
    			ui.prmtProjectStage.setValue(positionStage);
    		}
    	}else{
    		SpecialScheduleEditUI spUi = (SpecialScheduleEditUI) editUI;
    		spUi.prmtPrjSpecial.setDataNoNotify(info.getProjectSpecial());
    	}
    	editUI.txtVersion.setText(info.getVersionName());
		FDCMsgBox.showInfo(this, "计划复制成功!");
		this.uiWindow.close();
    	
    }
    
    public void actionSaveExit_actionPerformed(ActionEvent e) throws Exception {
    	this.destroyWindow();
    }

    
}