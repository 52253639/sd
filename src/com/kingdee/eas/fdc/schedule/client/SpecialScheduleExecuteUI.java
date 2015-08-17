/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FdcManagementUtil;
import com.kingdee.eas.fdc.schedule.FDCScheduleExecuteEnum;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskStateHelper;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportCollection;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportFactory;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportInfo;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportCollection;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportFactory;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportInfo;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.framework.client.KDTaskStatePanel;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * @(#)						
 * 版权：		金蝶国际软件集团有限公司版权所有 描述： 专项计划执行入口首页
 * 
 * @author 周航健
 * @version EAS7.0
 * @createDate 2011-08-29
 * @see
 */
public class SpecialScheduleExecuteUI extends AbstractSpecialScheduleExecuteUI {
	private static final long serialVersionUID = 4381100087941052308L;
	private static final Logger logger = CoreUIObject.getLogger(SpecialScheduleExecuteUI.class);
	/**
	 * output class constructor
	 */
	public SpecialScheduleExecuteUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		/*
		 * 设置EditUI状态
		 */
		setOprtState("MAIN_SCHEDULE_VIEW");
		super.onLoad();
		
		// add by libing
		// scheduleReportOnClick();
		// add by zhouhangjian
		// taskAppriseOnClick();
		
        this.actionAdjust.setVisible(false);
//		getScheduleGanttProject().removeToolbar();
		
		//在专项计划执行中不能有里程碑任务
		FDCScheduleExecuteEnum scheduleEnum;
		int index = -1;
        for(int i=0;i<this.kDComboBox1.getItemCount();i++){
        	scheduleEnum = (FDCScheduleExecuteEnum) kDComboBox1.getItemAt(i);
        	if(scheduleEnum.equals(FDCScheduleExecuteEnum.TASK_MILEPOST))
        	     index = i;
        }
        
        kDComboBox1.removeItemAt(index);
		
        /* modified by zhaoqin for R140103-0128,R140113-0141 on 2014/03/31 */
		//whenComTaskFilterChange();
	}
	
	protected void afterOnload() {
		/* TODO 自动生成方法存根 */
		super.afterOnload();
		if(editData.getProjectSpecial() == null){
			ganttProject.close();
		}
	}

	/**
	 * @description 进度汇报初始化、事件
	 * @author 李兵
	 * @createDate 2011-09-02
	 * @param
	 * @return void
	 * 
	 * @version EAS7.0
	 */
	private void scheduleReportOnClick() {

		btnAddScheduleReport = new KDWorkButton();

		btnAddScheduleReport.setName("btnAddScheduleReport");
		btnAddScheduleReport.setIcon(EASResource.getIcon("imgTbtn_execute"));
		btnAddScheduleReport.setText("进度汇报");
		btnAddScheduleReport.setEnabled(true);
		btnAddScheduleReport.setVisible(true);
		/*
		 * 监听事件
		 */
		btnAddScheduleReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openScheduleTaskProgressReport();
				} catch (Exception e1) {
					/* TODO 自动生成 catch 块 */
					handleException(e1);
				}
			}
		});
		this.toolBar.add(btnAddScheduleReport);

	}

	/**
	 * 打开进度汇报界面
	 * 
	 * @description
	 * @author 李兵
	 * @createDate 2011-9-27 void
	 * @version EAS7.0
	 * @throws Exception 
	 * @see
	 */
	public void openScheduleTaskProgressReport() throws Exception {
		/*
		 * 获取选择任务树节点对象
		 */
		int[] location = null;
		DefaultMutableTreeNode selectedNode = getScheduleGanttProject().getTree().getSelectedNode();
		FDCScheduleTaskInfo task = null;
		if (null != selectedNode) {
			location = getScheduleGanttProject().getTable().getSelectedRows();
			KDTask userObj = (KDTask) selectedNode.getUserObject();
			task = (FDCScheduleTaskInfo) userObj.getScheduleTaskInfo();
			FDCScheduleTaskExecuteHelper.checkLeafTask(task, "进度汇报");
		} else {
			MsgBox.showWarning("请选择具体任务进行进度汇报!");
			SysUtil.abort();
		}
		
		FDCScheduleTaskExecuteHelper.checkAndShowMsgInfoWhenCompleted(task);
		
		UIContext uiContext = getPropertyUIContext();
		uiContext.put(UIContext.ID, null);
		uiContext.put("taskinfo", task);
		IUIFactory uiFactory = null;
		IUIWindow uiWindow = null;
		try {
			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			//uiWindow = uiFactory.create(ScheduleTaskProgressReportEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			//根据建发的要求，增加 基于进度任务属性面签的进度汇报界面 Added By Owen_wen 2013-09-06
			uiWindow = uiFactory.create(getPropertityUIName(), uiContext, null, OprtState.ADDNEW);
		} catch (UIException ue) {
			ue.printStackTrace();
		}
		
		if(uiWindow != null){
			uiWindow.show();
			/*
			 * 不要用使用refresh()方法，因为那样会把所有任务又显示出来，需要按视图来过滤改用whenComTaskFilterChange()
			 * added by Owen-wen 2013-8-26
			 */
			whenComTaskFilterChange();
			if(location != null && location.length > 0){
				getScheduleGanttProject().getTree().selectTreeRow(location[0]);
			}
		}		
	}

	/**
	 * 
	 * @description 初始化设置菜单
	 * @author 周航健
	 * @createDate 2011-08-29
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 */
	protected void initWorkButton() {
		this.MenuService.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuTool.setVisible(false);
		this.menuWorkflow.setVisible(false);

		this.btnWeekReport.setEnabled(true);
		btnWeekReport.setIcon(EASResource.getIcon("imgTbtn_newdialog"));
		this.btnMonthReport.setEnabled(true);
		btnMonthReport.setIcon(EASResource.getIcon("imgTbtn_newdialog"));

		btnAddScheduleReport.setIcon(EASResource.getIcon("imgTbtn_execute"));
		btnAddTaskApprise.setIcon(EASResource.getIcon("imgTbtn_manualcount"));

		super.initWorkButton();
	}

	public void onShow() throws Exception {
		super.onShow();
		initSetButton();
	}

	/**
	 * 
	 * @description 初始化设置数据、按钮
	 * @author 周航健
	 * @createDate 2011-08-29
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 */
	public void initSetButton() {

//		this.btnZoomOut.setVisible(false);
//		this.btnZoomIn.setVisible(false);
		this.btnAttachment.setVisible(true);
		this.btnSubmit.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnBatChangeRespDept.setVisible(false);
		this.btnHideOther.setVisible(false);
		this.btnDisplayAll.setVisible(false);
		this.btnProperty.setText("查看");
		btnProperty.setIcon(EASResource.getIcon("imgTbtn_view"));
		/*
		 * 任务状态pane
		 */
//		KDTaskStatePanel pnlDesc = new KDTaskStatePanel();
//		this.paySubway.add(pnlDesc);
//		this.paySubway.setVisible(true);
		addStatePanel();

		this.cbState.setVisible(false);
		this.contState.setVisible(false);

		/*
		 * 移出影响情况table
		 */
		getScheduleGanttProject().getTabs().remove(this.kDTable1);

		getScheduleGanttProject().getTree().getMoveDownAction().setEnabled(false);
		getScheduleGanttProject().getTree().getMoveUpAction().setEnabled(false);
//		getScheduleGanttProject().getTree().getLinkTasksAction().setEnabled(false);
//		getScheduleGanttProject().getTree().getUnlinkTasksAction().setEnabled(false);
		getScheduleGanttProject().getTree().getIndentAction().setEnabled(false);
		getScheduleGanttProject().getTree().getUnindentAction().setEnabled(false);
//		getScheduleGanttProject().getTree().getNewTaskAction().setEnabled(false);

		this.btnHisVerion.setVisible(false);
		// this.btnVersionCompare.setVisible(false);
		this.btnRestore.setVisible(false);
		this.btnAdjust.setVisible(false);

		this.btnImportProject.setVisible(false);
		this.btnImportProject.setEnabled(false);
		this.btnImportPlanTemplate.setVisible(false);
		this.btnImportPlanTemplate.setEnabled(false);
		// this.btnExportPlanTemplate.setVisible(false);

		this.btnSave.setVisible(false);
		this.btnExportPlanTemplate.setVisible(false);
		this.btnAudit.setVisible(false);
		
		if(editData != null && editData.getState().equals(ScheduleStateEnum.AUDITTED )){
			this.btnWeekReport.setEnabled(true);
			this.btnMonthReport.setEnabled(true);
			this.btnAddScheduleReport.setEnabled(true);
			this.btnAddTaskApprise.setEnabled(true);
			
		}else{
			this.btnWeekReport.setEnabled(false);
			this.btnMonthReport.setEnabled(false);
			this.btnAddScheduleReport.setEnabled(false);
			this.btnAddTaskApprise.setEnabled(false);
		}	
	}
	
	private  void addStatePanel(){
		JComponent statePanel = null;
		if(getStatePanel() != null){
			statePanel = getStatePanel();
		}else{
			statePanel = new KDTaskStatePanel();
		}
		statePanel.setVisible(true);
		this.paySubway.add(statePanel);
		this.paySubway.setVisible(true);
	}
	/**
	 * 
	 * 描述：提供旭辉二次开发的接口
	 * @return
	 * 创建人：yuanjun_lan
	 * 创建时间：2012-2-23
	 */
	public JComponent getStatePanel(){
		return null;		
	}

	/**
	 * @description 数据保存后,无需验证数据是否被修改确认保存
	 * @author 周航健
	 * @createDate 2011-08-29
	 * @param
	 * @return boolean
	 * 
	 * @version EAS7.0
	 */
	public boolean isModify() {
		return false;
	}

	/**
	 * @description 任务评价初始化、事件
	 * @author 周航健
	 * @createDate 2011-08-29
	 * @param
	 * @return boolean
	 * 
	 * @version EAS7.0
	 */
	public void taskAppriseOnClick() {

		btnAddTaskApprise.setName("btnAddTaskApprise");
		btnAddTaskApprise.setIcon(EASResource.getIcon("imgTbtn_manualcount"));
		btnAddTaskApprise.setText("任务评价");
		btnAddTaskApprise.setEnabled(true);
		btnAddTaskApprise.setVisible(true);
		/*
		 * 监听事件
		 */
		btnAddTaskApprise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				UIContext uiContext = new UIContext(this);
				int [] location = null;
				/*
				 * 获取选择任务树节点对象
				 */
				location = getScheduleGanttProject().getTable().getSelectedRows();
				DefaultMutableTreeNode selectedNode = getScheduleGanttProject().getTree().getSelectedNode();
				FDCScheduleTaskInfo task = null;
				if (null != selectedNode) {
					KDTask userObj = (KDTask) selectedNode.getUserObject();
					task = (FDCScheduleTaskInfo) userObj.getScheduleTaskInfo();
					FDCScheduleTaskExecuteHelper.beforeOperator(task, "任务评价");
				} else {
					MsgBox.showWarning("请选择具体任务进行评价!");
					SysUtil.abort();
				}
				uiContext.put("taskId", task);
				IUIFactory uiFactory = null;
				IUIWindow uiWindow = null;
				try {
					uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
					uiWindow = uiFactory.create(SpecialTaskEvaluationBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
				} catch (UIException ue) {
					ue.printStackTrace();
				}
				
				if(uiWindow != null){
					uiWindow.show();
					try {
						/*
						 * 不要用使用refresh()方法，因为那样会把所有任务又显示出来，需要按视图来过滤改用whenComTaskFilterChange()
						 * added by Owen-wen 2013-8-26
						 */
						whenComTaskFilterChange();
						if(location != null && location.length>0){
							getScheduleGanttProject().getTree().selectTreeRow(location[0]);
						}
					} catch (Exception e1) {
						handleException(e1);
					}
				}
			}
		});
		this.toolBar.add(btnAddTaskApprise);

	}

	/**
	 * @description 根据当前日期时间计算当前周第一天日期时间
	 * @author 周航健
	 * @createDate 2011-08-29
	 * @param currTime
	 * @return Date
	 * 
	 * @version EAS7.0
	 */
	public Date getFirstDayOfWeek(Date currTime) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(currTime);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		return c.getTime();
	}

	/**
	 * @description 根据当前日期时间计算当前周最后一天日期时间
	 * @author 周航健
	 * @createDate 2011-08-29
	 * @param currTime
	 * @return Date
	 * 
	 * @version EAS7.0
	 */
	public Date getLastDayOfWeek(Date currTime) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(currTime);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
		return c.getTime();
	}

	/**
	 * @description 根据当前周的最后一天,计算出下一周的第一天和最后一天日期时间
	 * @author 周航健
	 * @createDate 2011-08-29
	 * @param currTime
	 *            <系统当前日期>
	 * @return Date[]
	 * 
	 * @version EAS7.0
	 */
	public Date[] getNextDayOfWeek(Date currTime) {
		Date[] nextWeek = new Date[2];
		nextWeek[0] = DateUtils.addDay(getLastDayOfWeek(currTime), 1);
		nextWeek[1] = DateUtils.addDay(getLastDayOfWeek(currTime), 7);

		return nextWeek;

	}

	/**
	 * 
	 * @description 视图过滤当前工程项目进度任务
	 * @author 周航健
	 * @createDate 2011-08-29
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 */
	protected void kDComTaskFilter_itemStateChanged(ItemEvent e) throws Exception {
		if (e.getStateChange() == 1) {
			whenComTaskFilterChange(); 
		}

		getScheduleGanttProject().getTree().getMoveDownAction().setEnabled(false);
		getScheduleGanttProject().getTree().getMoveUpAction().setEnabled(false);
		getScheduleGanttProject().getTree().getIndentAction().setEnabled(false);
		getScheduleGanttProject().getTree().getUnindentAction().setEnabled(false);
	}
	
	/**
	 * 从kDComTaskFilter_itemStateChanged抽取方法，可供其它地方调用（如关闭任务属性页签、进度汇报、任务评价时）
	 * @Author：owen_wen
	 * @CreateTime：2013-8-26
	 */
	protected void whenComTaskFilterChange() throws BOSException, EASBizException, SQLException, Exception {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map whenComTaskFilterChangeMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(),
				"whenComTaskFilterChange");
		// ////////////////////////////////////////////////////////////////////////
		
		if(editData!=null){			
			// 1、取任务数据
			Map getFDCScheduleTaskCollectionMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(),
					"IFDCScheduleTask.getFDCScheduleTaskCollection");
			EntityViewInfo taskView = FDCScheduleTaskExecuteHelper.getTaskFilterEntityView((FDCScheduleExecuteEnum) kDComboBox1.getSelectedItem(),
					editData.getId().toString());
			FDCScheduleTaskCollection taskColl = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(taskView);
			FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "IFDCScheduleTask.getFDCScheduleTaskCollection",
					getFDCScheduleTaskCollectionMap);

			// 2、设置任务状态
			FDCScheduleTaskStateHelper.putState(null, taskColl, editData.getProject());
			
			// 3、重新添加到集合中
			editData.getTaskEntrys().clear();
			editData.getTaskEntrys().addCollection(taskColl);
			
			// 4、刷新界面
			refresh(editData);
		}
		
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "whenComTaskFilterChange", whenComTaskFilterChangeMap);
		// ////////////////////////////////////////////////////////////////////////
	}

	/**
	 * 
	 * 
	 * 描述： GetUIName<专项执行>
	 * 
	 * @author 周航健
	 * @createDate 2011-9-16
	 * @param
	 * @return String
	 * 
	 * @version EAS7.0
	 * 
	 */
	protected String getUIName() {
		return SpecialScheduleExecuteUI.class.getName();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output btnAddLine_actionPerformed method
	 */
	protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.btnAddLine_actionPerformed(e);
	}

	/**
	 * output menuItemEnterToNextRow_itemStateChanged method
	 */
	protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception {
		super.menuItemEnterToNextRow_itemStateChanged(e);
	}

	/**
	 * 
	 * 
	 * 描述： 工程项目改变重新加载数据
	 * 
	 * @author 周航健
	 * @createDate 2011-9-21
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * 
	 */
	protected void prmtCurproject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		super.prmtCurproject_dataChanged(e);
		this.initSetButton();
		whenComTaskFilterChange();
	}
	
	
	protected void prmtPrjSpecial_dataChanged(DataChangeEvent e) throws Exception {
		/* TODO 自动生成方法存根 */
		super.prmtPrjSpecial_dataChanged(e);
		initSetButton();
		whenComTaskFilterChange();
	}

	
	

	public CurProjectInfo curprojectInfo;
	public ProjectSpecialInfo projectSpecialInfo;

	/**
	 * @description 项目周报告
	 * @author 车忠伟
	 * @createDate 2011-8-25
	 * @version EAS7.0
	 * @see
	 */
	public void actionWeekReport_actionPerformed(ActionEvent e) throws Exception {
		if (prmtCurproject.getValue() == null) {
			FDCMsgBox.showWarning(" 请选择工程项目");
			SysUtil.abort();
		}
		CurProjectInfo curprojectInfo = (CurProjectInfo) prmtCurproject.getValue();
		this.curprojectInfo = curprojectInfo;
		getUIContext().put("curprojectInfo", curprojectInfo);
		ProjectSpecialInfo projectSpecial = (ProjectSpecialInfo) this.prmtPrjSpecial.getValue();
		getUIContext().put("projectSpecial", projectSpecial);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", curprojectInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("year", new Integer(getCurrYear())));
		filter.getFilterItems().add(new FilterItemInfo("week", new Integer(getCurrWeekOfYear())));
		filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", projectSpecial.getId().toString(), CompareType.EQUALS));
		view.setFilter(filter);
		ProjectWeekReportCollection collection = ProjectWeekReportFactory.getRemoteInstance().getProjectWeekReportCollection(view);
		if (collection.size() > 0) {// 编辑
			ProjectWeekReportInfo weekReport = collection.get(0);
			FDCScheduleBaseEditUI scheduleUI = this;
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, weekReport.getId().toString());
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			IUIWindow uiWindow = uiFactory.create(ProjectWeekReportEditUI.class.getName(), uiContext, null, OprtState.EDIT);
			uiWindow.show();
		} else {
			FDCScheduleBaseEditUI scheduleUI = this;
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, null);
			uiContext.put("schedule", editData);
			uiContext.put("projectSpecial", projectSpecial);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			IUIWindow uiWindow = uiFactory.create(ProjectWeekReportEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();

		}
	}

	/**
	 * @description 得到日期是第几周
	 * @author 车忠伟
	 * @createDate 2011-8-25
	 * @version EAS7.0
	 * @see
	 */
	public int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 打开项目月报告 李建波
	 * 
	 */
	/**
	 * 打开项目月报告 李建波
	 * 
	 */
	public void actionMonthReport_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		if (prmtCurproject.getValue() == null) {
			FDCMsgBox.showWarning(" 请选择工程项目");
			SysUtil.abort();
		}
		this.projectSpecialInfo = (ProjectSpecialInfo) prmtPrjSpecial.getValue();
		CurProjectInfo curprojectInfo = (CurProjectInfo) prmtCurproject.getValue();
		this.curprojectInfo = curprojectInfo;
		getUIContext().put("curprojectInfo", curprojectInfo);
		ProjectSpecialInfo projectSpecial = editData.getProjectSpecial();
		getUIContext().put("projectSpecial", projectSpecial);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", curprojectInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("year", new Integer(getCurrYear())));
		filter.getFilterItems().add(new FilterItemInfo("month", new Integer(getCurrMonthOfYear())));
		filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", projectSpecial.getId().toString(), CompareType.EQUALS));
		view.setFilter(filter);
		ProjectMonthReportCollection monthCollection = ProjectMonthReportFactory.getRemoteInstance().getProjectMonthReportCollection(view);
		if (monthCollection.size() > 0) {
			ProjectMonthReportInfo weekReport = monthCollection.get(0);
			UIContext uiContext = new UIContext(this);
			uiContext.put("btnIsVisable", "true");
			uiContext.put(UIContext.ID, weekReport.getId().toString());
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			IUIWindow uiWindow = uiFactory.create(ProjectMonthReportEditUI.class.getName(), uiContext, null, OprtState.EDIT);
			uiWindow.show();
		} else {
			UIContext uiContext = new UIContext(this);
			uiContext.put("btnIsVisable", "false");
			uiContext.put(UIContext.ID, null);
			uiContext.put("projectSpecial", projectSpecial);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			IUIWindow uiWindow = uiFactory.create(ProjectMonthReportEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();

		}
	}

	/**
	 * @description 得到日期是第及月
	 * @author 李建波
	 * @createDate 2011-8-25
	 * @version EAS7.0
	 * @see
	 */
	public int getMonthOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH) + 1), 1, 0, 0, 0);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 专项进度汇报后反写页面的任务信息数数据
	 * 
	 * @description
	 * @author 李兵
	 * @createDate 2011-9-20 void
	 * @version EAS7.0
	 * @throws Exception
	 * @see
	 */
	public void writeback(FDCScheduleTaskInfo info) throws Exception {
		// UIContext uiContext = new UIContext(this);
		/*
		 */
		DefaultMutableTreeNode selectedNode = getScheduleGanttProject().getTree().getSelectedNode();
		FDCScheduleTaskInfo task = null;
		if (null != selectedNode) {
			KDTask userObj = (KDTask) selectedNode.getUserObject();
			task = (FDCScheduleTaskInfo) userObj.getScheduleTaskInfo();
			// try {
			// 实际完工日期
			if (info.getActualEndDate() != null) {
				// userObj.getCustomValues().setValue(GanttTreeTableModelExt.
				// strColActualEndDate, info.getActualEndDate());
			}
			// 完成率
			if (info.getComplete() != null) {
				task.setComplete(info.getComplete());
			}
			// 实际开始
			if (info.getActualStartDate() != null) {
				task.setActualStartDate(info.getActualStartDate());
			}
			// 实际完成
			if (info.getActualEndDate() != null) {
				task.setActualEndDate(info.getActualEndDate());
			}
			// 计划开始
			if (info.getStart() != null) {
				task.setStart(info.getStart());
			}
			// 计划完成
			if (info.getEnd() != null) {
				task.setEnd(info.getEnd());
			}
			// } catch (CustomColumnsException e) {
			// e.printStackTrace();
			// }
		}
		loadData2Gantt(this.editData);
	}
	
	protected void setUIEnabled(boolean isEnable) {
		/* TODO 自动生成方法存根 */
		super.setUIEnabled(isEnable);
		actionAdjust.setVisible(false);
		btnAdjust.setVisible(false);
	}
	
	/**
	 * 
	 * @description 获取当前时间的年
	 * @author 杜红明
	 * @createDate 2011-12-26
	 * @return int
	 * @version EAS7.0
	 * @see
	 */
	protected int getCurrYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR);
	}

	/**
	 * 
	 * @description 获取当前时间的月
	 * @author 杜红明
	 * @createDate 2011-12-26
	 * @return int
	 * @version EAS7.0
	 * @see
	 */
	protected int getCurrMonthOfYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 
	 * @description 获取当前时间周
	 * @author 杜红明
	 * @createDate 2011-12-26
	 * @return int
	 * @version EAS7.0
	 * @see
	 */
	protected int getCurrWeekOfYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		AttachmentUIContextInfo info = getAttacheInfo();
		if (info == null)
			info = new AttachmentUIContextInfo();
		if (info.getBoID() == null || info.getBoID().trim().equals("")) {
			String boID = getSelectBOID();
			info.setBoID(boID);
		}
		info.setEdit(true);
		// 2007-10-26 为工作流修改附件
		String multi = (String) getUIContext().get("MultiapproveAttachment");
		if (multi != null && multi.equals("true")) {
			acm.showAttachmentListUIByBoIDNoAlready(this, info);
		} else {
			acm.showAttachmentListUIByBoID(this, info);
		}

	}
	
	/** 
	 * @see com.kingdee.eas.fdc.schedule.client.SpecialScheduleExecuteUI#openScheduleTaskProgressReport()
	 */
	public void actionAddScheduleReport_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionAddScheduleReport_actionPerformed(e);

		openScheduleTaskProgressReport();
	}

	/** 
	 * @see com.kingdee.eas.fdc.schedule.client.SpecialScheduleExecuteUI#taskAppriseOnClick()
	 */
	public void actionAddTaskApprise_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionAddTaskApprise_actionPerformed(e);

		UIContext uiContext = new UIContext(this);
		int[] location = null;
		/*
		 * 获取选择任务树节点对象
		 */
		location = getScheduleGanttProject().getTable().getSelectedRows();
		DefaultMutableTreeNode selectedNode = getScheduleGanttProject().getTree().getSelectedNode();
		FDCScheduleTaskInfo task = null;
		if (null != selectedNode) {
			KDTask userObj = (KDTask) selectedNode.getUserObject();
			task = (FDCScheduleTaskInfo) userObj.getScheduleTaskInfo();
			FDCScheduleTaskExecuteHelper.beforeOperator(task, "任务评价");
		} else {
			MsgBox.showWarning("请选择具体任务进行评价!");
			SysUtil.abort();
		}
		uiContext.put("taskId", task);
		IUIFactory uiFactory = null;
		IUIWindow uiWindow = null;
		try {
			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			uiWindow = uiFactory.create(TaskEvaluationBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		} catch (UIException ue) {
			ue.printStackTrace();
		}

		if (uiWindow != null) {
			uiWindow.show();
			try {
				/*
				 * 不要用使用refresh()方法，因为那样会把所有任务又显示出来，需要按视图来过滤改用whenComTaskFilterChange() added by
				 * Owen-wen 2013-8-26
				 */
				/*
				 * 已经在FDCScheduleTaskPropertiesNewUI.destroyWindow()方法中调用过了,不用重复调用
				 * modified by zhaoqin for R140317-0259 on 2015/01/29
				 */
				//whenComTaskFilterChange();
				if (location != null && location.length > 0) {
					getScheduleGanttProject().getTree().selectTreeRow(location[0]);
				}
			} catch (Exception e1) {
				handleException(e1);
			}
		}
	}
	
	protected String getPropertityUIName() {
		// String propertyUIName = "com.kingdee.eas.fdc.schedule.client.FDCScheduleTaskPropertiesNewUI";
		// String propertyUIName = "com.kingdee.eas.fdc.schedule.client.SpecialProgressReportBaseSchTaskPropertiesNewUI";
		String propertyUIName = SpecialProgressReportBaseSchTaskPropertiesNewUI.class.getName();
		
		return propertyUIName;
	}
	
}