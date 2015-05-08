/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;

import net.sourceforge.ganttproject.Mediator;
import net.sourceforge.ganttproject.action.CalculateCriticalPathAction;
import net.sourceforge.ganttproject.action.NewTaskAction;
import net.sourceforge.ganttproject.action.ZoomInAction;
import net.sourceforge.ganttproject.action.ZoomOutAction;
import net.sourceforge.ganttproject.cache.ActivityCache;
import net.sourceforge.ganttproject.cache.NonWorkingDayCache;
import net.sourceforge.ganttproject.gui.GanttTabbedPane;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskContainmentHierarchyFacade;
import net.sourceforge.ganttproject.task.TaskManager;
import net.sourceforge.ganttproject.task.TaskNode;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyException;
import net.sourceforge.ganttproject.time.gregorian.FramerImpl;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDPopupMenu;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FdcManagementUtil;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.client.ClassLoaderUtil;
import com.kingdee.eas.fdc.schedule.client.FDCScheduleDataProvider;
import com.kingdee.eas.fdc.schedule.client.MainScheduleEditUI;
import com.kingdee.eas.fdc.schedule.client.RESchCompareUI;
import com.kingdee.eas.fdc.schedule.client.ScheduleClientHelper;
import com.kingdee.eas.fdc.schedule.client.ScheduleHisVer;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyCollection;
import com.kingdee.eas.fdc.schedule.framework.ext.GanttTreeTableModelExt;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;
import com.kingdee.eas.fdc.schedule.framework.util.ScheduleTaskPropertyHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public abstract class ScheduleBaseUI extends AbstractScheduleBaseUI {
	private static final Logger logger = CoreUIObject.getLogger(ScheduleBaseUI.class);
	protected ScheduleGanttProject ganttProject = null;
	private boolean isInitEnd = false;
	private String[] displayColumns = null;
	private int[] location = null;

	/**
	 *  是否打开过其它一个甘特图窗口 Added by Owen_wen 2013.10.30
	 */
	private boolean hasOpendAnotherUI = false; 
	
    public int[] getCurrentSelectLocation(){
    	return this.location;
    }
	/*
	 * by ZhouHangjian
	 */
	// public static final String STATE_EDIT = "ADDEDIT";
	// public static final String STATE_VIEW = "VIEW";
	public boolean isInitEnd() {
		return isInitEnd;
	}

	public void setInitEnd(boolean isInitEnd) {
		this.isInitEnd = isInitEnd;
	}

	/**
	 * 是否已经打开了甘特图 以后可能要考虑同步,事实上在一个客户端应该不可能同时打开UI
	 */
	private static boolean hasOpenGanttProject = false;

	/**
	 * output class constructor
	 */
	public ScheduleBaseUI() throws Exception {
		super();
	}
	


	/**
	 * 是否只能查看
	 * 
	 * @return
	 */
	protected boolean isOnlyView() {
		return false;
	}

	protected void initGanttProject() {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map initGanttProjectMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "initGanttProject");
		// ////////////////////////////////////////////////////////////////////////
		
		
		if (this.getUIContext().get("isFromWorkflow")==null) {
			setHasOpenGanttProject(true);
		}
		TimeTools.getInstance().msValuePrintln("start init schedule baseUI");
		if (this instanceof IScheduleUIFacade) {
			ganttProject = new ScheduleGanttProject(isOnlyView(), (IScheduleUIFacade) this);
		} else {
			ganttProject = new ScheduleGanttProject(isOnlyView());
		}
		actionProperty.putValue(Action.NAME, "属性");
		ganttProject.getTree().setTaskPropertiesAction(this.actionProperty);
		//
		ganttProject.setEnabledNewTask(true);
		//
		TimeTools.getInstance().msValuePrintln("end init schedule baseUI");
		GanttTabbedPane tabs = getScheduleGanttProject().getTabs();
		mainPanel.add(tabs, BorderLayout.CENTER);
		resetMenu();
		resetToolBar();
		initAction();
		
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "initGanttProject", initGanttProjectMap);
		// ////////////////////////////////////////////////////////////////////////
	}

	/**
	 * 初始化从甘特中带过来的action
	 */
	protected Action scrollCenterAction = null;
	private CalculateCriticalPathAction calculateCriticalPathAction = null;
	protected ZoomOutAction zoomOutAction = null;
	protected ZoomInAction zoomInAction = null;
	private JMenuItem pertItem;

	private void initAction() {
		if (scrollCenterAction == null) {
			DefaultListModel buttonList = getScheduleGanttProject().getButtonList();
			for (int i = 0; i < buttonList.size(); i++) {
				if (buttonList.get(i) instanceof JButton) {
					JButton button = (JButton) buttonList.get(i);
					if (button.getAction() != null
							&& "net.sourceforge.ganttproject.GanttGraphicArea$ScrollGanttChartCenterAction".equals(button.getAction().getClass()
									.getName())) {
						scrollCenterAction = button.getAction();
						break;
					}
				}
			}
		}
		if (scrollCenterAction != null) {
			this.actionZoomCenter.putValue(Action.SMALL_ICON, scrollCenterAction.getValue(Action.SMALL_ICON));
		}

		if (calculateCriticalPathAction == null) {
			DefaultListModel buttonList = getScheduleGanttProject().getButtonList();
			for (int i = 0; i < buttonList.size(); i++) {
				if (buttonList.get(i) instanceof JButton) {
					JButton button = (JButton) buttonList.get(i);
					if (button.getAction() instanceof CalculateCriticalPathAction) {
						calculateCriticalPathAction = (CalculateCriticalPathAction) button.getAction();
						actionCritical.putValue(Action.SMALL_ICON, calculateCriticalPathAction.getValue(Action.SMALL_ICON));
						break;
					}
				}

			}
		}

		DefaultListModel buttonList = getScheduleGanttProject().getButtonList();
		for (int i = 0; i < buttonList.size(); i++) {
			if (buttonList.get(i) instanceof JButton) {
				JButton button = (JButton) buttonList.get(i);
				if (button.getAction() instanceof NewTaskAction) {
					NewTaskAction newTaskAction = (NewTaskAction) button.getAction();
					newTaskAction.setEnabled(false);
					break;
				}
			}
		}

		// getScheduleGanttProject().setVisible(true);
		if (pertItem == null) {
			JMenuBar menuBar = (JMenuBar) getScheduleGanttProject().mEdit.getParent();
			String viewText = GanttLanguage.getInstance().getText("view");
			for (int i = 0; i < menuBar.getMenuCount(); i++) {
				if (menuBar.getMenu(i) != null && menuBar.getMenu(i).getText() != null && menuBar.getMenu(i).getText().equals(viewText)) {
					JMenu viewMenu = menuBar.getMenu(i);
					for (int j = 0; j < viewMenu.getItemCount(); j++) {
						if (viewMenu.getItem(j) instanceof JCheckBoxMenuItem) {
							pertItem = viewMenu.getItem(j);
							break;
						}
					}
				}
			}
		}

		if (zoomInAction == null) {
			for (int i = 0; i < buttonList.size(); i++) {
				if (buttonList.get(i) instanceof JButton) {
					JButton button = (JButton) buttonList.get(i);
					if (button.getAction() instanceof ZoomInAction) {
						zoomInAction = (ZoomInAction) button.getAction();
						break;
					}
				}
			}
		}

		if (zoomOutAction == null) {
			for (int i = 0; i < buttonList.size(); i++) {
				if (buttonList.get(i) instanceof JButton) {
					JButton button = (JButton) buttonList.get(i);
					// System.out.println(button.getText()+"\t "+button.
					// getActionCommand());
					/*
					 * if(button.getAction()!=null){
					 * System.out.println(button.getAction
					 * ().getClass().getName()); }
					 */
					if (button.getAction() instanceof ZoomOutAction) {
						zoomOutAction = (ZoomOutAction) button.getAction();
						break;
					}
				}
			}
		}
	}

	protected void loadData() throws Exception {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map loadDataMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "loadData");
		// ////////////////////////////////////////////////////////////////////////
		
		/*
		 * super.loadData(); initGanttProject();//这里可以做成并发
		 */
		TimeTools.getInstance().reset();
		TimeTools.getInstance().msValuePrintln("------------ start load data------------");
		
		innerLoadData();
		TimeTools.getInstance().msValuePrintln("------------ end innerLoadData------------");
		
		load2Gantt(this.editData);
		TimeTools.getInstance().msValuePrintln("------------ end ganttProject.loadData();------------");
		
		setOprtState(getOprtState());
		ganttProject.setEventsEnabled(true);
		
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "loadData", loadDataMap);
		// ////////////////////////////////////////////////////////////////////////
	}
    /**
     * 
     * 描述：全部返回false,不使用虚模式 
     * @param info
     * @return
     * 创建人：yuanjun_lan
     * 创建时间：2011-11-16
     */
	private boolean isReadOnly(ScheduleBaseInfo info) {
//		if (info.getState() == null || ScheduleStateEnum.SAVED.equals(info.getState()) || ScheduleStateEnum.SUBMITTED.equals(info.getState())) {
//			return this instanceof ProjectScheduleUI;
//		}
		return false;
	}

	private void load2GanttBefore(ScheduleBaseInfo info) {
		FramerImpl.isClearFields = false;
		ActivityCache activityCache = ActivityCache.getInstance();
		NonWorkingDayCache restDayCache = NonWorkingDayCache.getInstance();
		restDayCache.setEnableCache(true);
		logger.debug("activityCache.size:" + activityCache.size() + "; restDayCache:" + restDayCache.size());
		// R110215-310 :增加参数：进度计划展示是否启用虚模式。
		// String companyId = SysContext.getSysContext().getCurrentFIUnit()
		// .getId().toString();
		// 再也不支持虚模式，谢谢
		boolean isUseVirtualMode = false;
		// try {
		// isUseVirtualMode = FDCHelper
		// .getBooleanValue4FDCParamByKey(null, companyId,
		// FDCConstants.FDC_PARAM_SCHUDLEEXECUSEVIRTUALMODE);
		// } catch (Exception e) {
		// logger.error("获取参数FDCSCH009_EXECUSEVIRTUALMODE失败", e);
		// }
		info.setBoolean("WEAKMODE", isUseVirtualMode);
		info.setReadOnly(isReadOnly(info));
		ganttProject.setEventsEnabled(false);
		logger.info("load2GanttBefore...");
	}

	public void load2GanttAfter(ScheduleBaseInfo info) {
		logger.info("load2GanttAfter...");
		this.isInitEnd = true;
		FramerImpl.isClearFields = true;
		ganttProject.setEventsEnabled(true);
		ganttProject.gotoDate(new Date());
		clearImportFlag();
		ActivityCache activityCache = ActivityCache.getInstance();
		NonWorkingDayCache restDayCache = NonWorkingDayCache.getInstance();
		logger.debug("activityCache.size:" + activityCache.size() + "; restDayCache:" + restDayCache.size());
		activityCache.clear();
		restDayCache.clear();
		ganttProject.setTaskAfterInit();
		restDayCache.setEnableCache(false);
		setOprtState(oprtState);
		getScheduleGanttProject().setUIContext(getPropertyUIContext());
		getScheduleGanttProject().setOprtState(this.getOprtState());
		centerAndShowAll();
	}

	/**
	 * 返回需要显示在甘特表中的列，根据UI判断
	 * <p>
	 * 去除以前写死每个UI的做法，改为在数据库中定义列属性<br>
	 * add by emanon
	 * 
	 * @param info
	 * @return
	 */
	public String[] getDisplayColumns(FDCScheduleInfo info) {
		// 版本对比暂时不需要
		// if (getUIName().equals(RESchCompareUI.class.getName())) {
		// return new String[] { "tpc0", "tpe0", "tpe1", "tpe2", "tpe3", "tpe4",
		// "tpe5", "tpe6", "tpe7" };
		// }
		ScheduleTaskPropertyCollection defaultColumns = null;
		try {
			if (editData.getTaskPropertys() != null) {
				defaultColumns = editData.getTaskPropertys();
			} else {
				defaultColumns = ScheduleTaskPropertyHelper.getAllColumns(ScheduleClientHelper.getUIMark((IScheduleUIFacade) this));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if (defaultColumns == null) {
			return new String[] {};
		}
		String[] disCols = new String[defaultColumns.size()];
		for (int i = 0; i < defaultColumns.size(); i++) {
			String propertyId = defaultColumns.get(i).getPropertyId();
			disCols[i] = propertyId;
		}
		return disCols;
		// // 主项编制
		// if (getUIName().equals(MainScheduleEditUI.class.getName())) {
		// return new String[] { "tpd10", "tpc0", "tpc1", "tpd4", "tpd5",
		// "tpe9", "tpc3", "tpc6", "tpc5" };
		// }
		// // 专项编制
		// else if (getUIName().equals(SpecialScheduleEditUI.class.getName())) {
		// return new String[] { "tpd10", "tpc0", "tpc1", "tpd4", "tpd5",
		// "tpc3", "tpc6", "tpc5" };
		// }
		// // 主项执行 add Table column by ZhouHangJian 2011-09-16
		// else if (getUIName().equals(MainScheduleExecuteUI.class.getName())) {
		// return new String[] { "tpd10", "tpe22", "tpc0", "tpc1", "tpe24",
		// "tpd4", "tpd5", "tpe23", "tpc4", "tpe9", "tpc3", "tpc6",
		// "tpc5" };
		// }
		// // 专项执行add Table column by ZhouHangJian 2011-09-16
		// else if
		// (getUIName().equals(SpecialScheduleExecuteUI.class.getName())) {
		// return new String[] { "tpc1", "tpe22", "tpc0", "tpd10", "tpe24",
		// "tpd4", "tpd5", "tpe23", "tpc4", "tpc3", "tpc6", "tpc5" };
		// }
		// // 总进度计划
		// else if (getUIName().equals(TotalScheduleEditUI.class.getName())) {
		// return new String[] { "tpc1", "tpe22", "tpc0", "tpd10", "tpd4",
		// "tpd5", "tpe8", "tpe9", "tpc3", "tpc8", "tpc6", "tpc5" };
		// }
		// return new String[] { "tpd10", "tpc0", "tpc1", "tpd4", "tpd5",
		// "tpc3",
		// "tpc5", "tpc6" };
	}

	protected String getUIName() {
		return MainScheduleEditUI.class.getName();
	}

	/**
	 * 加载数据到Gantt图,以后如果外面需要使用可以放开这个类,用于在外部改变Gantt图
	 * 
	 * @param info
	 * @throws Exception
	 */
	protected void load2Gantt(ScheduleBaseInfo info) throws Exception {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map load2GanttMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "load2Gantt");
		// ////////////////////////////////////////////////////////////////////////
		
		int uiMark = ScheduleClientHelper.getUIMark((IScheduleUIFacade) this);
		info.setTaskPropertys(ScheduleTaskPropertyHelper.getAllColumns(uiMark));
		info.setInt("uiMark", uiMark);
		
		if (info instanceof FDCScheduleInfo) {
			FDCScheduleInfo fdcInfo = (FDCScheduleInfo) info;
			displayColumns = getDisplayColumns(fdcInfo);
		}
		info.setDisplayColumn(displayColumns);
		load2GanttBefore(info);
		ganttProject.setEditData(info);
		ganttProject.getTree().setScheduleUI(this);
		
		// 进度性能优化: 性能瓶颈 78.2% - 69,098 ms - 1 inv by skyiter_wang 2014-06-11
		// 传入标示符
		ganttProject.loadData(uiMark);
		
		actionHistoryVersion();

		// 责任人
//		if (!getUIName().equals(RESchCompareUI.class.getName())) {
//			if (getScheduleGanttProject().getGanttTreeTable().getColumn(GanttTreeTableModelExt.stradminPerson) != null) {
//				TableCellEditor editor = getScheduleGanttProject().getGanttTreeTable().getColumn(GanttTreeTableModelExt.stradminPerson)
//						.getCellEditor();
//				if (editor instanceof ScheduleCellEditor) {
//					ScheduleCellEditor cellEditor = (ScheduleCellEditor) editor;
//					if (cellEditor.getComponent() instanceof KDBizPromptBox) {
//						ScheduleClientHelper.setPersonF7((KDBizPromptBox) cellEditor.getComponent(), this, SysContext.getSysContext()
//								.getCurrentCtrlUnit() != null ? SysContext.getSysContext().getCurrentCtrlUnit().getId().toString() : null);
//					}
//				}
//			}
//			// 责任部门 20111007王小龙与王长林确认此处取行政组织并要求能够选择全集团所有的行政组织。
//			if (getScheduleGanttProject().getGanttTreeTable().getColumn(
//					GanttTreeTableModelExt.strColAdminDept) != null) {
//				TableCellEditor editor = getScheduleGanttProject()
//						.getGanttTreeTable().getColumn(
//								GanttTreeTableModelExt.strColAdminDept)
//						.getCellEditor();
//				if (editor instanceof ScheduleCellEditor) {
//					ScheduleCellEditor cellEditor = (ScheduleCellEditor) editor;
//					if (cellEditor.getComponent() instanceof KDBizPromptBox) {
//						/** 老系统要求跟据参数值来判断是否能选择全部行政组织现在先注释掉 */
//						FDCClientUtils.setRespDeptF7(
//								(KDBizPromptBox) cellEditor.getComponent(),
//								this, null, false);
//					}
//				}
//			}
//		}
		
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "load2Gantt", load2GanttMap);
		// ////////////////////////////////////////////////////////////////////////
	}
	
	/**
	 * 
	 * @description 查看历史版本
	 * @author 杜红明
	 * @createDate 2011-9-5 void
	 * @version EAS7.0
	 * @throws BOSException
	 * @see
	 */
	protected void actionHistoryVersion() throws BOSException {
		ScheduleHisVer instance = ScheduleHisVer.getInstance();
		instance.loadHistoryVer(editData, this);
		this.setOprtState(OprtState.VIEW);
	}
	
	protected void innerLoadData() throws Exception {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map innerLoadDataMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "innerLoadData");
		// ////////////////////////////////////////////////////////////////////////
		
		super.loadData();
		initGanttProject();
		if (this.tempException != null) {
			this.handUIException(tempException);
			this.abort();
		}
		
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "innerLoadData", innerLoadDataMap);
		// ////////////////////////////////////////////////////////////////////////
	}

	protected Exception tempException = null;

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);
		storeTasks();
	}

	/**
	 * 将task 从界面保存到实体里面
	 */
	protected void storeTasks() {
		Task rootTask = getScheduleGanttProject().getTaskManager().getTaskHierarchy().getRootTask();
		Task[] tasks = getScheduleGanttProject().getTaskManager().getTaskHierarchy().getNestedTasks(rootTask);
		for (int i = 0; i < tasks.length; i++) {

		}
	}

	public void initLayout() {
		super.initLayout();
	}

	protected void resetMenu() {
		// 处理继承的菜单
		this.menuBar.remove(menuTable1);
		FDCClientHelper.setActionEnable(new ItemAction[] { this.actionAddLine, this.actionRemoveLine, this.actionCopyFrom, this.actionCreateFrom,
				this.actionCreateTo, this.actionTraceDown, this.actionTraceUp, this.actionNext, this.actionFirst, this.actionLast, this.actionPre,
				this.actionInsertLine }, false);
		FDCClientHelper.setActionEnable(new ItemAction[] { this.actionZoomIn, this.actionZoomOut, this.actionProperty }, true);
		// 添加Gantt内的其他菜单

	}

	protected void resetToolBar() {
		// 处理继承的工具栏
		// 添加其他工具栏
		btnWFViewdoProccess.setVisible(false);
		btnWFViewSubmitProccess.setVisible(false);
		btnWorkFlowG.setVisible(false);
		btnMultiapprove.setVisible(false);
		btnNextPerson.setVisible(false);
		btnAuditResult.setVisible(false);
		// btnViewSignature.setVisible(false);
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.actionZoomIn.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_zoomin"));
		this.actionZoomOut.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_zoomout"));
		this.actionZoomLeft.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_previous"));
		this.actionZoomRight.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_next"));
		this.actionProperty.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_modifyattribute"));
		this.actionGraphOption.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_pagesetting"));
		actionPert.setEnabled(false);
		actionPert.setVisible(false);
		initShowByLevelButton();
	}

	/**
	 * R100613-019在界面上加一个查询级次的按钮，级次可以自己选定<br>
	 */
	private void initShowByLevelButton() {
		actionShowAllLevels.setEnabled(true);
		actionShowAllLevels.setVisible(true);
		KDPopupMenu payPopupMenu = new KDPopupMenu();
		payPopupMenu.add(new KDMenuItem((IItemAction) ActionProxyFactory.getProxy(actionShowAllLevels, new Class[] { IItemAction.class },
				getServiceContext())));
		payPopupMenu.add(new KDMenuItem((IItemAction) ActionProxyFactory.getProxy(actionShowLevel1, new Class[] { IItemAction.class },
				getServiceContext())));
		payPopupMenu.add(new KDMenuItem((IItemAction) ActionProxyFactory.getProxy(actionShowLevel2, new Class[] { IItemAction.class },
				getServiceContext())));
		payPopupMenu.add(new KDMenuItem((IItemAction) ActionProxyFactory.getProxy(actionShowLevel3, new Class[] { IItemAction.class },
				getServiceContext())));
		payPopupMenu.add(new KDMenuItem((IItemAction) ActionProxyFactory.getProxy(actionShowLevel4, new Class[] { IItemAction.class },
				getServiceContext())));
		payPopupMenu.add(new KDMenuItem((IItemAction) ActionProxyFactory.getProxy(actionShowLevel5, new Class[] { IItemAction.class },
				getServiceContext())));
		payPopupMenu.add(new KDMenuItem((IItemAction) ActionProxyFactory.getProxy(actionShowLevel6, new Class[] { IItemAction.class },
				getServiceContext())));
		payPopupMenu.add(new KDMenuItem((IItemAction) ActionProxyFactory.getProxy(actionShowLevel7, new Class[] { IItemAction.class },
				getServiceContext())));
		payPopupMenu.add(new KDMenuItem((IItemAction) ActionProxyFactory.getProxy(actionShowLevel8, new Class[] { IItemAction.class },
				getServiceContext())));
		payPopupMenu.add(new KDMenuItem((IItemAction) ActionProxyFactory.getProxy(actionShowLevel9, new Class[] { IItemAction.class },
				getServiceContext())));
		
		toolBar.remove(btnShowByLevel);
		btnShowByLevel = new KDWorkButton(btnShowByLevel.getAction());
		btnShowByLevel.setFactType(KDWorkButton.BLUE_FACE);
		btnShowByLevel.setAssistPopup(payPopupMenu);
		btnShowByLevel.setIcon(EASResource.getIcon("imgTree_arrangementframe"));
		actionShowAllLevels.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_floor"));
		actionShowLevel1.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_floor"));
		actionShowLevel2.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_floor"));
		actionShowLevel3.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_floor"));
		actionShowLevel4.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_floor"));
		actionShowLevel5.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_floor"));
		actionShowLevel6.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_floor"));
		actionShowLevel7.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_floor"));
		actionShowLevel8.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_floor"));
		actionShowLevel9.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_floor"));
	}

	public void onLoad() throws Exception {
		ClassLoaderUtil.modifyClassLoader(true);
		if (this.getUIContext().get("isFromWorkflow")==null&& isHasOpenGanttProject()) {
			FDCMsgBox.showWarning(this, "已经打开了一个计划窗口,请先关闭");
			hasOpendAnotherUI = true;
			SysUtil.abort();
		}
		super.onLoad();
		ganttProject.setPropertityUIName(getPropertityUIName());
		ganttProject.setUIContext(getPropertyUIContext());
		
	}
	//TODO FIXME 进度日历缓存方案
	public void initScheduleCalendar(FDCScheduleInfo sch) throws BOSException{
		Date startDate = sch.getStartDate();
		Date endDate = sch.getEndDate();
		Calendar tempDate  = Calendar.getInstance();
		
		CurProjectInfo prj = sch.getProject();
//		ScheduleCalendarInfo cal = ScheduleCalendarFactory.getRemoteInstance().getDefaultCal(prj.getId().toString());
		for(int i=0;i<sch.getEffectTimes().intValue();i++){
			tempDate.setTime(startDate);
			tempDate.add(Calendar.DATE, 1);
			
		}
		
	}
	
	
	public void onShow() throws Exception {
		ScheduleBaseUI.super.onShow();
		btnAuditResult.setVisible(false);
		btnAuditResult.setEnabled(false);
		setShowMessagePolicy(SHOW_MESSAGE_BAR_ONLY);
		setMessageText("正在初始化数据...");
		showMessage();
		weakOnShow();
		ganttProject.repaint();
	}

	public void weakOnShow() throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				weakLoadData();
			}
		});

	}

	public void showMsg4LoadData() {
		if(editData == null){
			return ;
		}
		int initIndex = editData.getInt("initIndex") + 1;
		int total = initIndex;
		if (editData instanceof FDCScheduleInfo) {
			FDCScheduleInfo schedule = (FDCScheduleInfo) editData;
			total = schedule.getTaskEntrys().size();
		}
		setMessageText("显示 " + initIndex + " / " + total);
		showMessage();
	}

	/**
	 * 虚模式加载数据
	 * <p>
	 * 暂不支持虚模式！！！<br>
	 * 如果要支持，要按下面描述修改<br>
	 * 前置任务处理方式：<br>
	 * 之前是每加载一条任务，就加载其前置任务出来<br>
	 * 这样有一个后果是，如果他的后置任务暂时还没加载，这个关系就不显示了<br>
	 * initIndex为当前已经加载到何处，第一次加载到49，然后另起线程加载<br>
	 * 现在改为全部任务加载完成后，再统一加载前置任务<br>
	 */
	public void weakLoadData() {
		if (editData.isReadOnly()) {
			showMsg4LoadData();
			return;
		}
		Map kdTaskMap = ganttProject.getKDTaskMaps();
		TaskManager taskManager = ganttProject.getTaskManager();
		if (editData == null)
			return;
		// if(!editData.getBoolean("hasInit"))
		// return;
		int initIndex = editData.getInt("initIndex");
		try {
			if (editData instanceof FDCScheduleInfo) {
				FDCScheduleInfo schedule = (FDCScheduleInfo) editData;
				Map dependBaseKeyMap = null;
				if (schedule.containsKey("dependBaseKeyMap")) {
					dependBaseKeyMap = (HashMap) schedule.get("dependBaseKeyMap");
				} else {
					dependBaseKeyMap = new HashMap();
					schedule.put("dependBaseKeyMap", dependBaseKeyMap);
				}
				int size = schedule.getTaskEntrys().size();

				for (int i = initIndex + 1; i < size && isInitEnd == false; i++) {
					FDCScheduleTaskInfo scheduleTaskInfo = schedule.getTaskEntrys().get(i);
					boolean realLock = scheduleTaskInfo.isLocked();
					scheduleTaskInfo.setLocked(true);

					KDTask task = new KDTask(taskManager, scheduleTaskInfo);

					taskManager.registerTask(task);
					TaskContainmentHierarchyFacade taskHierarchy = taskManager.getTaskHierarchy();
					// 将当前节点放到他的父节点下面
					if (task.getScheduleTaskInfo().getObjectValue("parent") != null) {
						String parentId = task.getScheduleTaskInfo().getObjectValue("parent").getString("id");
						Task parentTask = (KDTask) kdTaskMap.get(parentId);
						taskHierarchy.move(task, parentTask);
					} else {
						TaskNode taskNode = ganttProject.getTree().addObject(task, ganttProject.getTree().getRoot(), -1);
					}

					kdTaskMap.put(scheduleTaskInfo.getId().toString(), task);

					ScheduleTaskDependCollection depends = scheduleTaskInfo.getDepends();
					for (Iterator iter = depends.iterator(); iter.hasNext();) {
						ScheduleTaskDependInfo dependInfo = (ScheduleTaskDependInfo) iter.next();
						if (dependInfo.getTaskBase() != null
								&& dependBaseKeyMap != null) {
							List dependeeList = null;
							if (dependBaseKeyMap.containsKey(dependInfo
									.getTaskBase().getId().toString())) {
								dependeeList = (ArrayList) dependBaseKeyMap
										.get(dependInfo.getTaskBase().getId()
												.toString());
							} else {
								dependeeList = new ArrayList();
								dependBaseKeyMap.put(dependInfo.getTaskBase()
										.getId().toString(), dependeeList);
							}
							dependeeList.add(dependInfo);
						}
						TaskLinkTypeEnum type = dependInfo.getType();
						FDCScheduleTaskInfo dependantInfo = (FDCScheduleTaskInfo) dependInfo
								.getTaskBase();
						Task dependant = (KDTask) kdTaskMap.get(dependantInfo.getId().toString());
						if (dependant != null) {
							int difference = dependInfo.getDifference();
							TaskDependency dependency;
							try {
								dependency = taskManager
										.getDependencyCollection()
										.createDependency(
												task,
												dependant,
										ScheduleParserHelper.getTaskDependencyConstraintByLinkType(type));
								dependency.setDifference(difference);
							} catch (TaskDependencyException e1) {
								logger.error(e1);
							}
						}
					}

					if (dependBaseKeyMap != null && dependBaseKeyMap.containsKey(scheduleTaskInfo.getId().toString())) {
						List dependeeList = (ArrayList) dependBaseKeyMap.get(scheduleTaskInfo.getId().toString());
						for (Iterator it = dependeeList.iterator(); it.hasNext();) {
							ScheduleTaskDependInfo dependInfo = (ScheduleTaskDependInfo) it.next();
							TaskLinkTypeEnum type = dependInfo.getType();
							FDCScheduleTaskInfo dependantInfo = (FDCScheduleTaskInfo) dependInfo.getTaskBase();
							Task dependant = (KDTask) kdTaskMap.get(dependantInfo.getId().toString());
							int difference = dependInfo.getDifference();
							TaskDependency dependency;
							try {
								dependency = taskManager.getDependencyCollection().createDependency(task, dependant,
										ScheduleParserHelper.getTaskDependencyConstraintByLinkType(type));
								dependency.setDifference(difference);
							} catch (TaskDependencyException e1) {
								logger.error(e1);
							}
						}
					}
					scheduleTaskInfo.setLocked(realLock);
					BigDecimal percent = FDCHelper.divide(FDCHelper.toBigDecimal(i), FDCHelper.toBigDecimal(size)).multiply(FDCHelper.ONE_HUNDRED);
					// setMessageText("正在初始化数据: " + (percent) + "%！");
					//logger.debug("初始化数据: "+"i="+i+" "+scheduleTaskInfo.getName
					// ());
					this.showMessage();
				}
				if (schedule.containsKey("dependBaseKeyMap")) {
					dependBaseKeyMap = null;
					schedule.remove("dependBaseKeyMap");

				}

			}

			// setMessageText("初始化数据完毕！");
			// showMessage();
		} catch (Exception exec) {
			logger.error(exec.getCause(), exec);
			setMessageText("初始化数据异常，需要查看客户端日志！");
			showMessage();
		}
		isInitEnd = true;
		load2GanttAfter(editData);
	}

	public void storeFields() {
		super.storeFields();
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (!checkInitBeforeAction())
			return;
		super.actionSave_actionPerformed(e);

	}

	public boolean checkInitBeforeAction() {
		if (!isWeakLoadEnd()) {
			FDCMsgBox.showInfo(this, "正在完成初始化，请稍候再操作！");
			return false;
		}
		return true;
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (!checkInitBeforeAction())
			return;
		super.actionSubmit_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}
		FDCScheduleDataProvider dataPvd = new FDCScheduleDataProvider(editData
				.getString("id"), getTDQueryPK(), isShowDetailDependInPrint());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));

		// getScheduleGanttProject().printProject();
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}
		FDCScheduleDataProvider dataPvd = new FDCScheduleDataProvider(editData
				.getString("id"), getTDQueryPK(), isShowDetailDependInPrint());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDFileName(), dataPvd,
				javax.swing.SwingUtilities.getWindowAncestor(this));

		// getScheduleGanttProject().previewPrint();
	}

	protected String getTDFileName() {
		return "/bim/fdc/process/FDCSchedule";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.schedule.app.FDCScheduleTDQuery");
	}

	/**
	 * 套打中，任务的前置任务，是否显示全名称
	 * 
	 * @return true 前置任务显示名称用逗号隔开 false 前置任务显示序号用逗号隔开
	 */
	protected boolean isShowDetailDependInPrint() {
		return false;
	}
	
	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// if(!isWeakLoadEnd())
		// {
		// return;
		// }
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (!checkInitBeforeAction())
			return;
		this.getScheduleGanttProject().setOprtState(OprtState.EDIT);
		super.actionEdit_actionPerformed(e);
		

	}

	public boolean isWeakLoadEnd() {
		return this.isInitEnd;
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}

	abstract protected IObjectValue createNewDetailData(KDTable table);

	abstract protected IObjectValue createNewData();

	abstract protected ICoreBase getBizInterface() throws Exception;

	public ScheduleGanttProject getScheduleGanttProject() {
		return ganttProject;
	}

	protected KDTable getDetailTable() {
		return null;
	}

	public void actionZoomLeft_actionPerformed(ActionEvent e) throws Exception {
		getScheduleGanttProject().getScrollingManager().scrollLeft();
	}

	public void actionZoomRight_actionPerformed(ActionEvent e) throws Exception {
		getScheduleGanttProject().getScrollingManager().scrollRight();
	}

	public void actionZoomCenter_actionPerformed(ActionEvent e) throws Exception {
		if (scrollCenterAction != null) {
			scrollCenterAction.actionPerformed(e);
		}

	}

	public void actionZoomIn_actionPerformed(ActionEvent e) throws Exception {
		if (zoomInAction != null) {
			zoomInAction.actionPerformed(e);
		}
	}

	public void actionZoomOut_actionPerformed(ActionEvent e) throws Exception {
		if (zoomOutAction != null) {
			zoomOutAction.actionPerformed(e);
		}
	}

	public void actionGraphOption_actionPerformed(ActionEvent e) throws Exception {
		getScheduleGanttProject().miChartOptions.doClick();
	}

	public void actionProperty_actionPerformed(ActionEvent e) throws Exception {
		if (!checkInitBeforeAction())
			return;
		if (ganttProject.getSelectedNode() == null) {
			FDCMsgBox.showWarning(this, "请选择具体任务");
			SysUtil.abort();
		}
		UIContext context = getPropertyUIContext();
		String oprtState = (String) context.get("OprtState");
		if (oprtState == null) {
			oprtState = getOprtState();
		}
		// this.onLoadBeforInitSeting();

		this.getScheduleGanttProject().propertiesTask(context, getPropertityUIName(), oprtState);

	}

	/**
	 * 
	 * 
	 * 描述： 加载初始化设置
	 * 
	 * @author 周航建
	 * @version EAS7.0
	 * @createDate 2011-10-6
	 * 
	 */
	protected String getPropertityUIName() {
		String propertyUIName = "com.kingdee.eas.fdc.schedule.client.FDCScheduleTaskPropertiesNewUI";
		return propertyUIName;
	}

	protected UIContext getPropertyUIContext() {
		UIContext uiContext = new UIContext(this);
		uiContext.put("ScheduleGanttProject", this.getScheduleGanttProject());
		uiContext.put("OprtState", getOprtState());
		return uiContext;
	}

	public void actionPert_actionPerformed(ActionEvent e) throws Exception {
		super.actionPert_actionPerformed(e);
		if (pertItem != null) {
			pertItem.doClick();
		}
	}

	public void actionCritical_actionPerformed(ActionEvent e) throws Exception {
		if (calculateCriticalPathAction != null) {
			calculateCriticalPathAction.actionPerformed(e);
		}
	}

	public boolean destroyWindow() {
		isInitEnd = true;
		boolean destroyWindow = super.destroyWindow();
		if (destroyWindow) {
		    if(getUIContext().get("isFromWorkflow")==null){
		    	clear();
		    	if (!hasOpendAnotherUI) {//abort方式关闭的窗口，destroyWindow不需要改变状态
					setHasOpenGanttProject(false);
				}
		    }
  		      
		}
		return destroyWindow;
	}

	/**
	 * 释放资源
	 */
	protected void clear() {
		// 清除单例的内容
		if(getScheduleGanttProject() !=null){
			getScheduleGanttProject().quitApplication();
			Mediator.registerGanttProject(null);
			Mediator.registerTaskSelectionManager(null);
			Mediator.registerDelayManager(null);
			Mediator.registerUndoManager(null);
		}
	}

	private void clearImportFlag() {
		if (editData != null && editData.getScheduleTasks() != null) {
			ScheduleTaskBaseCollection tasks = editData.getScheduleTasks();
			for (int i = 0; i < tasks.size(); ++i) {
				tasks.get(i).remove("isImport");
			}
		}
	}
	
	protected void centerAndShowAll() {
		try {
			// 数据加载完成后居中且展开
			actionZoomCenter_actionPerformed(null);
			actionShowAllLevels_actionPerformed(null);
		} catch (Exception e1) {
			// 尽人事，听天命。中断不需上报。
			// 一般onLoad时gantt控件未初始化完，会报错
			logger.error("未能展开级次:" + e1);
		}
	}

	public void actionShowAllLevels_actionPerformed(ActionEvent e) throws Exception {
		ganttProject.getTree().expandAllNode();
	}

	public void actionShowLevel1_actionPerformed(ActionEvent e) throws Exception {
		ganttProject.getTree().expandOnLevel(1);
	}

	public void actionShowLevel2_actionPerformed(ActionEvent e) throws Exception {
		ganttProject.getTree().expandOnLevel(2);
	}

	public void actionShowLevel3_actionPerformed(ActionEvent e) throws Exception {
		ganttProject.getTree().expandOnLevel(3);
	}

	public void actionShowLevel4_actionPerformed(ActionEvent e) throws Exception {
		ganttProject.getTree().expandOnLevel(4);
	}

	public void actionShowLevel5_actionPerformed(ActionEvent e) throws Exception {
		ganttProject.getTree().expandOnLevel(5);
	}

	public void actionShowLevel6_actionPerformed(ActionEvent e) throws Exception {
		ganttProject.getTree().expandOnLevel(6);
	}

	public void actionShowLevel7_actionPerformed(ActionEvent e) throws Exception {
		ganttProject.getTree().expandOnLevel(7);
	}

	public void actionShowLevel8_actionPerformed(ActionEvent e) throws Exception {
		ganttProject.getTree().expandOnLevel(8);
	}

	public void actionShowLevel9_actionPerformed(ActionEvent e) throws Exception {
		ganttProject.getTree().expandOnLevel(9);
	}

	protected void inOnload() throws Exception {
		// 检查是否外部已显式传递值对象？
		if (getUIContext().get(UIContext.INIT_DATAOBJECT) != null && getUIContext().get(UIContext.INIT_DATAOBJECT) instanceof IObjectValue) {
			setDataObject((IObjectValue) getUIContext().get(UIContext.INIT_DATAOBJECT));
		} else {
			// 由传递过来的ID获取值对象
			setDataObject(getValue(null));
		}

		loadFields();

		// 为了BOTP的集成，提供给BillEditUI重载使用
		Object id = getUIContext().get(UIContext.ID);

		if (id != null) {
			prepareData(id.toString());
		}
	}
	
	public String[] getDisplayColumns() {
		if (displayColumns == null || displayColumns.length < 1) {
			displayColumns = getDisplayColumns((FDCScheduleInfo) editData);
		}
		return displayColumns;
	}

	public static void setHasOpenGanttProject(boolean hasOpenGanttProject) {
		ScheduleBaseUI.hasOpenGanttProject = hasOpenGanttProject;
	}

	public static boolean isHasOpenGanttProject() {
		return hasOpenGanttProject;
	}
	
	}