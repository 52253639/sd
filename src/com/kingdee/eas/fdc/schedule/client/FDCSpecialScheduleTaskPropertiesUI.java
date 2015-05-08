/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleHelper;

/**
 * output class name
 */
public class FDCSpecialScheduleTaskPropertiesUI extends AbstractFDCSpecialScheduleTaskPropertiesUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCSpecialScheduleTaskPropertiesUI.class);
    
    /**
     * output class constructor
     */
    public FDCSpecialScheduleTaskPropertiesUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	contCheckNode.setVisible(false);
    	super.onLoad();
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    	
    	//初始化相关主项任务F7
    	initRelationMainTask();
    }

    /**
     * @discription  <初始化相关主项任务F7>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/09/29> <p>
     * @param        <空>
     * @return       <返回值描述>
     * 
     * modifier      <空> <p>
     * modifyDate    <空> <p>
     * modifyInfo	 <空> <p>
     * @throws BOSException 
     * @throws EASBizException 
     * @see          <相关的类>
     */
    public void initRelationMainTask() throws EASBizException, BOSException{
    	this.prmtRelationMainTask.setQueryInfo("com.kingdee.eas.fdc.schedule.app.FDCScheduleTaskReaQuery");
    	this.prmtRelationMainTask.setDisplayFormat("$name$");
    	this.prmtRelationMainTask.setEditFormat("$name$");
    	this.prmtRelationMainTask.setCommitFormat("$name$");
    	prmtRelationMainTask.getQueryAgent().getQueryInfo().setAlias("相关主项任务");
		this.prmtRelationMainTask.setEditable(false);
		prmtRelationMainTask.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				FDCScheduleTaskInfo mainTask = (FDCScheduleTaskInfo) e
						.getNewValue();
				
				if (mainTask != null) {
//					if (!mainTask.isIsLeaf()) {
//						FDCMsgBox.showWarning(
//								FDCSpecialScheduleTaskPropertiesUI.this,
//								"请选择末级主项任务");
//						prmtRelationMainTask.setDataNoNotify(e.getOldValue());
//					}
					Set resultSet = new HashSet();
					FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
					if(task.getSchedule().getId() == null){
						FDCMsgBox.showInfo("请先保存当前版本的任务，再进行关联");
						abort();
					}
					ScheduleHelper.getAllRootIDs(task, resultSet);
					try {
						FDCScheduleTaskCollection cols = ScheduleHelper.getAlreayRelateMainTask(task, resultSet);
						
						if(cols != null && cols.size()>0){
							StringBuffer strMsg = new StringBuffer("当前任务的父级任务或者子级任务已经与主项搭接：\n");
							strMsg.append("专项任务名称\t          \t 主项任务名称 \n");
							FDCScheduleTaskInfo tempTask = null;
							for(Iterator it = cols.iterator();it.hasNext();){
								tempTask = (FDCScheduleTaskInfo) it.next();
								strMsg.append(tempTask.getName());
								strMsg.append(" \t  ——>   \t");
								strMsg.append(tempTask.getDependMainTaskID().getName());
								strMsg.append("\n");
								
							}
							strMsg.append("不能再次进行搭接！");
							FDCMsgBox.showError(strMsg.toString());
							prmtRelationMainTask.setDataNoNotify(e.getOldValue());
							abort();
						}
					} catch (BOSException e1) {
						handUIException(e1);
					}
					
					prmtRelationMainTask.setValue(mainTask);
					
				}
			}
		});
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();

		/* 根据当前项目过滤 */
		if (null != getUIContext().get("Owner")) {
			FDCScheduleBaseEditUI fDCScheduleBaseEditUI = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
			CurProjectInfo currentProjectInfo = (CurProjectInfo) fDCScheduleBaseEditUI.prmtCurproject.getValue();
			filter.appendFilterItem("schedule.project.id", currentProjectInfo.getId());
		}
		
		
		
		/* 根据最新版本过滤 */
		filter.appendFilterItem("schedule.isLatestVer", Boolean.TRUE);
		filter.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial.id", null,CompareType.EQUALS));
		
		
		SorterItemInfo sort = new SorterItemInfo("longNumber");
		view.getSorter().add(sort);
		view.setFilter(filter);
		this.prmtRelationMainTask.setEntityViewInfo(view);
		this.prmtRelationMainTask.setEditable(false);
		
		/*
		 * 初始化值
		 */
		if(selectTask.getScheduleTaskInfo() instanceof FDCScheduleTaskInfo){
			FDCScheduleTaskInfo scheduleTaskInfo = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
			if(null != scheduleTaskInfo.getDependMainTaskID() && null != scheduleTaskInfo.getDependMainTaskID().getId() && !"".equals(scheduleTaskInfo.getDependMainTaskID().getId().toString())){
				try {
					EntityViewInfo srcView = new EntityViewInfo();
					FilterInfo filterInfo = new FilterInfo();
					filterInfo.getFilterItems().add(new FilterItemInfo("srcid", scheduleTaskInfo.getDependMainTaskID().getId().toString()));
					filterInfo.getFilterItems().add(new FilterItemInfo("schedule.isLatestver", Boolean.TRUE));

					srcView.setFilter(filterInfo);
					FDCScheduleTaskCollection cols = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(srcView);
					if (!cols.isEmpty()) {
						this.prmtRelationMainTask.setDataNoNotify(cols.get(0));
					}
				} catch (Exception e) {
					logger.error("读取相关主项失败，不设值");
				}
			}
			
		}
    }
   
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	/* modified by zhaoqin for R140226-0252 on 2014/04/10 start */    	
    	if(null == prmtRelationMainTask.getValue()) {
    		FDCScheduleTaskInfo scheduleTaskInfo = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
    		scheduleTaskInfo.setDependMainTaskID(null);
    	}
    	/* modified by zhaoqin for R140226-0252 on 2014/04/10 start */
    	
    	super.actionSave_actionPerformed(e);
    }

}