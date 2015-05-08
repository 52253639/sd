package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.TaskEvaluationBillCollection;
import com.kingdee.eas.fdc.schedule.TaskEvaluationBillFactory;
import com.kingdee.eas.fdc.schedule.TaskEvaluationBillInfo;
import com.kingdee.eas.fdc.schedule.TaskEvaluationCollection;
import com.kingdee.eas.fdc.schedule.TaskEvaluationFactory;
import com.kingdee.eas.fdc.schedule.TaskEvaluationInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * 
 * @(#)						
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		<重构> 主项任务执行-任务评价
 *		
 * @author		周航健
 * @version		EAS7.0		
 * @createDate	2011-08-25 
 * @see
 */
public class FDCScheduleTaskAppriseHelper implements ITaskPanelHelper {

	//属性界面
	private FDCScheduleTaskPropertiesNewUI taskPropertiesUI;

	//进度任务
	private FDCScheduleTaskInfo fdcScheduleTask;
	
	//界面进度任务的缓存
	private final KDTask selectTask;
	
	private String taskSrcId = "";
	
	private Map f7Enr;

//	private Map f7User;

	public FDCScheduleTaskAppriseHelper(FDCScheduleTaskPropertiesNewUI taskPropertiesUI) {
		this.taskPropertiesUI = taskPropertiesUI;
		selectTask = taskPropertiesUI.getSelectedTask();
		fdcScheduleTask = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
	}
	
	public void commit() {
		// TODO Auto-generated method stub
		
	}
	
	public void load() throws EASBizException, BOSException {
		taskPropertiesUI.tblTaskApprise.checkParsed();
		TaskEvaluationBillInfo billInfo=new TaskEvaluationBillInfo();
		billInfo.setRelateTask(fdcScheduleTask);
		taskSrcId = fdcScheduleTask.getSrcID().toString();
		initPaneButton();
		
		
		f7Enr = new HashMap();
//		f7User = new HashMap();
		this.initSetEvaluationF7Value();
		fillTaskApprisePaneTable();
	}

	public void setEditUIStatus() {
		// TODO Auto-generated method stub
		
		
		
	}

	public void setExecutingUIStatus() {
		// TODO Auto-generated method stub
	}

	public void setViewUIStatus() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * @description		初始化页签按钮
	 * @author			周航健	
	 * @createDate		2011-08-24
	 * @param			
	 * @return			
	 * 	
	 * @version			EAS7.0
	 */
	public void initPaneButton()
	{
		KDWorkButton btnAddTaskApprise = new KDWorkButton(taskPropertiesUI.actionAddTaskApprise);
		
		btnAddTaskApprise.setName("btnAddTaskApprise");
		btnAddTaskApprise.setIcon(EASResource.getIcon("imgTbtn_manualcount"));
		btnAddTaskApprise.setText("任务评价");
		btnAddTaskApprise.setEnabled(true);
		btnAddTaskApprise.setVisible(true);
		/*
		 * 监听事件
		 */
		btnAddTaskApprise.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e) {	
					FDCScheduleTaskExecuteHelper.beforeOperator(fdcScheduleTask, "任务评价");
					UIContext uiContext = new UIContext(this);
					uiContext.put(UIContext.ID, null);
					uiContext.put("taskId", fdcScheduleTask);
					IUIFactory uiFactory = null;
					IUIWindow uiWindow = null;
					try {
						 uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
						 uiWindow = uiFactory.create(TaskEvaluationBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
					} catch (UIException ue) {
						ue.printStackTrace();
					}
					uiWindow.show();
					fillTaskApprisePaneTable();
					taskPropertiesUI.setNeedRefresh(true);
				}
			}
		);
 
		KDWorkButton btnDelTaskApprise = new KDWorkButton(taskPropertiesUI.actionDeleteTaskApprise);
		btnDelTaskApprise.setName("btnDelTaskApprise");
		btnDelTaskApprise.setIcon(EASResource.getIcon("imgTbtn_delete"));
		btnDelTaskApprise.setText("删除评价");
		btnDelTaskApprise.setEnabled(true);
		btnDelTaskApprise.setVisible(true);
		
		btnDelTaskApprise.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					
					int rowIndex = taskPropertiesUI.tblTaskApprise.getSelectManager().getActiveRowIndex();
					if(rowIndex < 0 )
					{
						MsgBox.showWarning("请先选择记录行！");
					}
					else
					{
						/*
						 * 得到选中行ID
						 */
						Object id =  taskPropertiesUI.tblTaskApprise.getRow(rowIndex).getCell("ID").getValue();
						int yes=MsgBox.showConfirm2("是否确认删除?");
						if(yes > 0){
							SysUtil.abort();
						}else{
							try {
								FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
								String msg = "完成进度已汇报为100%，不能再删除评价";
								// 检查是否要根据完成情况来给出警告信息，并中断退出
								FDCScheduleTaskExecuteHelper.checkAndShowMsgInfoWhenCompleted(task, msg, true);
								
								TaskEvaluationBillFactory.getRemoteInstance().delete(new ObjectUuidPK(id.toString()));
							} catch (EASBizException e1) {
								e1.printStackTrace();
							} catch (BOSException e1) {
								e1.printStackTrace();
							}
						}
						
						fillTaskApprisePaneTable();
						taskPropertiesUI.setNeedRefresh(true);
					}
				}
				
			}	
		);
		
		taskPropertiesUI.kDContainerTaskApprise.addButton(btnAddTaskApprise);
		taskPropertiesUI.kDContainerTaskApprise.addButton(btnDelTaskApprise);
		
		if (!fdcScheduleTask.isIsLeaf()) {
			btnAddTaskApprise.setVisible(false);
			btnDelTaskApprise.setVisible(false);
		}
		
	}
	
	/**
	 * 
	 * @description		填充任务评价页签表
	 * @author			周航健	
	 * @createDate		2011-08-26
	 * @param			
	 * @return			
	 * 	
	 * @version			EAS7.0
	 */
	public void fillTaskApprisePaneTable()
	{
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("evaluationType"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("creator.*"));
		sic.add(new SelectorItemInfo("creator.id"));
		sic.add(new SelectorItemInfo("creator.name"));
		sic.add(new SelectorItemInfo("creator.person.name"));
		sic.add(new SelectorItemInfo("creator.person.id"));
		sic.add(new SelectorItemInfo("creator.person.number"));
		sic.add(new SelectorItemInfo("evaluationResult.*"));
		sic.add(new SelectorItemInfo("evaluationResult.id"));
		sic.add(new SelectorItemInfo("evaluationResult.name"));
		sic.add(new SelectorItemInfo("evalucationPerson.id"));
		sic.add(new SelectorItemInfo("evalucationPerson.name"));
		sic.add(new SelectorItemInfo("evalucationPerson.number"));
		
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo sortItem = new SorterItemInfo("lastupdatetime");
		sortItem.setSortType(SortType.DESCEND);
		sorter.add(sortItem);
		view.setSorter(sorter);
		view.setSelector(sic);
		
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("srcRelateTask", taskSrcId);
		view.setFilter(filter);
		try {
			taskPropertiesUI.tblTaskApprise.removeRows();
			TaskEvaluationBillCollection taskColl = TaskEvaluationBillFactory.getRemoteInstance().getTaskEvaluationBillCollection(view);
			if(null != taskColl && taskColl.size() > 0)
			{
				for(int i = 0; i < taskColl.size();i++){
					TaskEvaluationBillInfo info = taskColl.get(i);
					IRow row = taskPropertiesUI.tblTaskApprise.addRow();
					if(info.getCreator().getPerson()!=null && info.getCreator().getPerson().getName()!=null)
					row.getCell("creator").setValue(info.getCreator().getPerson().getName());
					if(null != f7Enr.get(info.getEvaluationResult().getId().toString())){
						row.getCell("evaluationResult").setValue(f7Enr.get(info.getEvaluationResult().getId().toString()));
					}
					row.getCell("evaluationType").setValue(info.getEvaluationType());
					row.getCell("createTime").setValue(info.getCreateTime());
					row.getCell("ID").setValue(info.getId());
					
					/*
					 * Lock Table
					 */
					this.setColumnLock();
				}
			}
			
			taskPropertiesUI.tblTaskApprise.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @description		评价结果F7查询显示
	 * @author			周航健	
	 * @createDate		2011-08-26
	 * @param			
	 * @return			
	 * 	
	 * @version			EAS7.0
	 */
	public void initSetEvaluationF7Value(){
		try {
			TaskEvaluationCollection  taskEvaluationCollection  = TaskEvaluationFactory.getRemoteInstance().getTaskEvaluationCollection();
			for(int k = 0; k < taskEvaluationCollection.size(); k ++){
				TaskEvaluationInfo taskEvaluationInfo = taskEvaluationCollection.get(k);
				this.f7Enr.put(taskEvaluationInfo.getId().toString(), taskEvaluationInfo.getEvaluationResult());
			}
//			UserCollection  userCollection  = UserFactory.getRemoteInstance().getUserCollection();
//			for(int k = 0; k < userCollection.size(); k ++){
//				UserInfo userInfo = userCollection.get(k);
//				this.f7User.put(userInfo.getId().toString(), userInfo);
//			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	
	  /**
	 * @description		任务评价：Lock Table<质量评价表>
	 * @author			周航建	
	 * @createDate		2011-08-26
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 */
    private void setColumnLock()
    {
    	/*
		 * 锁定单元格不可编辑
		 */
    	taskPropertiesUI.tblTaskApprise.getColumn("evaluationResult").getStyleAttributes().setLocked(true);
    	taskPropertiesUI.tblTaskApprise.getColumn("evaluationType").getStyleAttributes().setLocked(true);
    	taskPropertiesUI.tblTaskApprise.getColumn("creator").getStyleAttributes().setLocked(true);
    	taskPropertiesUI.tblTaskApprise.getColumn("createTime").getStyleAttributes().setLocked(true);
    	taskPropertiesUI.tblTaskApprise.getColumn("ID").getStyleAttributes().setLocked(true);
    	
    }
	
	public void actionAddTaskApprise_actionPerformed(ActionEvent e)
			throws Exception {
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(TaskEvaluationBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		taskPropertiesUI.actionAddTaskApprise_actionPerformed(e);
	}
	
	public void actionDeleteTaskApprise_actionPerformed(ActionEvent e)
			throws Exception {
		
		taskPropertiesUI.actionDeleteTaskApprise_actionPerformed(e);
	}

}
