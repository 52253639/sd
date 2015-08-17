/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework.ext;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.JTableHeader;
import javax.swing.tree.DefaultMutableTreeNode;

import net.sourceforge.ganttproject.GanttImagePanel;
import net.sourceforge.ganttproject.GanttProject;
import net.sourceforge.ganttproject.GanttTask;
import net.sourceforge.ganttproject.GanttTree2;
import net.sourceforge.ganttproject.GanttTreeTable;
import net.sourceforge.ganttproject.GanttTreeTableModel;
import net.sourceforge.ganttproject.Mediator;
import net.sourceforge.ganttproject.chart.Chart;
import net.sourceforge.ganttproject.chart.RenderedChartImage;
import net.sourceforge.ganttproject.gui.GanttLookAndFeelInfo;
import net.sourceforge.ganttproject.gui.TestGanttRolloverButton;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.io.GPSaver;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.parser.GPParser;
import net.sourceforge.ganttproject.parser.ParserFactory;
import net.sourceforge.ganttproject.task.BlankLineNode;
import net.sourceforge.ganttproject.task.CustomColumnsException;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskContainmentHierarchyFacade;
import net.sourceforge.ganttproject.task.TaskManagerImpl;
import net.sourceforge.ganttproject.task.TaskNode;
import net.sourceforge.ganttproject.task.algorithm.AdjustTaskBoundsAlgorithm;
import net.sourceforge.ganttproject.task.event.TaskDependencyEvent;
import net.sourceforge.ganttproject.task.event.TaskHierarchyEvent;
import net.sourceforge.ganttproject.task.event.TaskListener;
import net.sourceforge.ganttproject.task.event.TaskPropertyEvent;
import net.sourceforge.ganttproject.task.event.TaskScheduleEvent;

import org.apache.log4j.Logger;
import org.jdesktop.swing.decorator.Highlighter;
import org.jdesktop.swing.decorator.HighlighterPipeline;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.print.extend.KDImagePrinter;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDSplitPane;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FdcManagementUtil;
import com.kingdee.eas.fdc.schedule.FDCSCHUtils;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSTree;
import com.kingdee.eas.fdc.schedule.RESchTaskEffectDegreeEnum;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.client.EffectDegreePanel;
import com.kingdee.eas.fdc.schedule.client.RESchCompareUI;
import com.kingdee.eas.fdc.schedule.client.ScheduleClientHelper;
import com.kingdee.eas.fdc.schedule.client.SpecialScheduleEditUI;
import com.kingdee.eas.fdc.schedule.framework.DeleteParentDependency;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.client.IScheduleUIFacade;
import com.kingdee.eas.fdc.schedule.framework.client.KDGanttRowHighligther;
import com.kingdee.eas.fdc.schedule.framework.client.KDGanttTreeUI;
import com.kingdee.eas.fdc.schedule.framework.client.ScheduleBaseUI;
import com.kingdee.eas.fdc.schedule.framework.ext.plugins.SchedulePluginManager;
import com.kingdee.eas.fdc.schedule.framework.parser.AbstractKDParser;
import com.kingdee.eas.fdc.schedule.framework.parser.CalendarHandler;
import com.kingdee.eas.fdc.schedule.framework.parser.DefaultKDParser;
import com.kingdee.eas.fdc.schedule.framework.parser.DefaultKDSaver;
import com.kingdee.eas.fdc.schedule.framework.parser.ProjectHandler;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;
import com.kingdee.eas.fdc.schedule.framework.parser.TaskDisplayColumnsHandler;
import com.kingdee.eas.fdc.schedule.framework.parser.TaskHandler;
import com.kingdee.eas.fdc.schedule.framework.parser.TaskPropertiesHandler;
import com.kingdee.eas.fdc.schedule.framework.parser.ViewHandler;
import com.kingdee.eas.util.client.EASResource;

public class ScheduleGanttProject extends GanttProject {

	private static final long serialVersionUID = -5543432288206293946L;
	private static final Logger logger = CoreUIObject.getLogger(ScheduleGanttProject.class);
	private IScheduleUIFacade scheduleUIFacede = null;
	private FDCWBSTree wbsTree = null;

	/*
	 * Box容器控件 by ZhouHangjian
	 */
	private Box toolbarBox = null;
	
	private KDWorkButton delButton;

	public ScheduleGanttProject(boolean isOnlyViewer) {
		super(isOnlyViewer);
	}

	public ScheduleGanttProject(boolean isOnlyViewer, IScheduleUIFacade baseUIFacade) {
		super(isOnlyViewer);
		scheduleUIFacede = baseUIFacade;
		getTree().getTreeTable().setUIMark(
				ScheduleClientHelper.getUIMark(baseUIFacade));
		// removeGanttLogo();
	}

	public FDCWBSTree getWbsTree(boolean isLoad) throws BOSException {
		if (isLoad && wbsTree == null) {
			CurProjectInfo project = ((FDCScheduleInfo) editData).getProject();
			wbsTree = FDCWBSTree.getTreeFromDB(null, project.getId().toString());
		}
		return wbsTree;
	}

	public FDCWBSTree getWbsTree() throws BOSException {
		return getWbsTree(true);
	}

	public IScheduleUIFacade getScheduleUIFacede() {
		return scheduleUIFacede;
	}

	public void setScheduleUIFacede(IScheduleUIFacade scheduleUIFacede) {
		this.scheduleUIFacede = scheduleUIFacede;
	}

	public ScheduleGanttProject(boolean isOnlyViewer, boolean isApplet) {
		super(isOnlyViewer, isApplet);
	}

	private final static class ScheduleGanttProjectForOtherSingleHolder {
		private final static ScheduleGanttProject scheduleGanttProjectForOtherSingle = Mediator.getGanttProjectSingleton() == null ? new ScheduleGanttProject(
				true)
				: (ScheduleGanttProject) Mediator.getGanttProjectSingleton();
	}

	/**
	 * 为其他地方要用到这个东西的提供一个单例实现
	 * 
	 * @return
	 */
	public static ScheduleGanttProject getScheduleGanttProjectSingleForOther() {
		return ScheduleGanttProjectForOtherSingleHolder.scheduleGanttProjectForOtherSingle;
	}

	public JTable getTable() {
		return getTree().getTable();
	}

	public GanttTreeTable getGanttTreeTable() {
		return getTree().getTreeTable();
	}

	private ParserFactory myParserFactory = null;

	protected ParserFactory getParserFactory() {
		if (myParserFactory == null) {
			myParserFactory = new ScheduleParserFactoryImpl();
		}
		return myParserFactory;
	}

	private class ScheduleParserFactoryImpl implements ParserFactory {
		public GPParser newParser() {
			return new DefaultKDParser(prjInfos, getUIConfiguration(), getTaskManager(), getUIFacade());
		}

		public GPSaver newSaver() {
			return new DefaultKDSaver(ScheduleGanttProject.this, (GanttTree2) getTree(), getResourcePanel(), getArea(), getUIFacade());
		}
	}

	private ScheduleBaseInfo editData = null;

	public boolean isAdjustSchedule() {
		if (this.getScheduleUIFacede() != null)
			return this.getScheduleUIFacede().isAdjustSchedule();
		else
			return false;
	}

	public String getOprtState() {
		if (getUIContext() == null)
			return "ADDNEW";
		return (String) getUIContext().get("OprtState");
	}

	public void setOprtState(String oprtState) {
		getUIContext().put("OprtState", oprtState);
		handleTreeTableStatus();
	}

	public void setTaskAfterInit() {
		Map kdTaskMaps = getKDTaskMaps();
		for (Iterator iter = kdTaskMaps.values().iterator(); iter.hasNext();) {
			KDTask task = (KDTask) iter.next();
			task.setAfterInit(true);
		}
	}

	public void loadData(int uiMark) throws Exception {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map loadDataMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "loadData");
		// ////////////////////////////////////////////////////////////////////////
		
		editData.setBoolean("hasInit", false);
		editData.setInt("uiMark", uiMark);
		prjInfos = new KDPrjInfos(editData);
		AbstractKDParser parser = (AbstractKDParser) getParserFactory().newParser();
		addHandler(parser);
		
		// 进度性能优化: 性能瓶颈 78.1% - 68,976 ms - 1 inv by skyiter_wang 2014-06-11
		parser.parse();
		
		setAskForSave(false);
		editData.setBoolean("hasInit", false);
		setTaskAfterInit();
		
		Date start = getTaskManager().getProjectStart();
		Date end = getTaskManager().getProjectEnd();
		setFromDate(start);
		setToDate(end);
		Integer max = Integer.parseInt((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24) + "");
		initSlider(max.intValue());

		this.repaint();
		this.getTable().repaint();
		this.getArea().repaint();
		
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "loadData", loadDataMap);
		// ////////////////////////////////////////////////////////////////////////
	}

	/**
	 * 重新加载数据
	 * 
	 * @throws Exception
	 */
	public void reLoadData() throws Exception {
		editData.setBoolean("hasInit", false);
		prjInfos = new KDPrjInfos(editData);
		AbstractKDParser parser = (AbstractKDParser) getParserFactory().newParser();
		parser.reParse();
		setAskForSave(false);
		getTaskManager().processCriticalPath((TaskNode) getTree().getRoot());
		editData.setBoolean("hasInit", true);

		Map kdTaskMaps = getKDTaskMaps();
		for (Iterator iter = kdTaskMaps.values().iterator(); iter.hasNext();) {
			KDTask task = (KDTask) iter.next();
			task.setAfterInit(true);
		}
	}

	private void addHandler(AbstractKDParser parser) {
		// 展示相关
		ProjectHandler prjHandler = new ProjectHandler();
		// 处理甘特图日历的操作类，用于设置默认周末和节假日
		CalendarHandler calendarHandler = new CalendarHandler(this.getActiveCalendar());
		// 处理甘特表列属性操作类，用于设置Editor和Renderer
		TaskPropertiesHandler propertihandler = new TaskPropertiesHandler(this);
		// 甘特表列显示隐藏操作类
		TaskDisplayColumnsHandler displayHandler = new TaskDisplayColumnsHandler(this.getUIFacade().getTaskTree().getVisibleFields());
		// 任务操作类，加载前置任务等
		TaskHandler taskHandler = new TaskHandler(this);

		ViewHandler viewHandler = new ViewHandler();
		parser.addHandler(prjHandler);
		parser.addHandler(calendarHandler);
		parser.addHandler(propertihandler);
		parser.addHandler(displayHandler);
		parser.addHandler(taskHandler);
		parser.addHandler(viewHandler);
		parser.addParsingListener(displayHandler);
		parser.addParsingListener(taskHandler);
		parser.addParsingListener(prjHandler);
	}

	public ScheduleBaseInfo getEditData() {
		return editData;
	}

	/**
	 * set for demo
	 * 
	 * @param editData
	 */
	public void setEditData(ScheduleBaseInfo editData) {
		if (this.editData != null) {
			// 重新设置应该先做清理
			this.close();
		}
		this.editData = editData;
	}

	/*
	 * 此方法会在构造方法内调用，可以把一些初始化得方法放到这个里面
	 */
	private boolean hasChange = false;

	public void changeLookAndFeel(GanttLookAndFeelInfo lookAndFeel) {
		// super.changeLookAndFeel(lookAndFeel);
		if (hasChange) {
			return;
		}
		init();
		hasChange = true;
	}

	/**
	 * 初始化,在基类的构造方法内调用
	 */
	protected void init() {
		initMyOptions();
		removeGanttLogo();
		ToolTipManager.sharedInstance().registerComponent(this.getArea());
		// this.getArea().setToolTipText("");
		// 设置表头颜色
		this.getTree().getTreeTable().setColumnMargin(2);
		// this.getTree().getTreeTable().setRowMargin(2);
		this.getTree().getTreeTable().setBackground(Color.GRAY);
		Color colorTemp = UIManager.getColor("KDTable.gridLineColor");
		colorTemp = (colorTemp != null) ? colorTemp : KDTStyleConstants.GRID_LINE_COLOR;
		this.getTable().setBorder(new LineBorder(colorTemp));
		this.getTable().setGridColor(colorTemp);
		this.getGanttTreeTable().setGridColor(colorTemp);
		// 设置默认底色
		colorTemp = UIManager.getColor("KDTable.background");
		colorTemp = (colorTemp != null) ? colorTemp : KDTStyleConstants.TABLE_BACKGROUND;
		this.getTable().setBackground(colorTemp);
		this.getTable().setShowGrid(true);
		// 表头表体默认背景色
		Color bodyBackColor = UIManager.getColor("KDTable.bodyBackground");
		bodyBackColor = (bodyBackColor != null) ? bodyBackColor : KDTStyleConstants.BODY_BACKGROUND;
		Color headBackColor = UIManager.getColor("KDTable.headBackground");
		headBackColor = (headBackColor != null) ? headBackColor : KDTStyleConstants.HEAD_BACKGROUND;
		Color foreColor = UIManager.getColor("KDTable.foreground");
		foreColor = (foreColor != null) ? foreColor : KDTStyleConstants.TABLE_FOREGROUND;

		this.getGanttTreeTable().setHeaderForeground(foreColor);
		this.getGanttTreeTable().setHeaderBackground(headBackColor.darker());// new
		getTable().getTableHeader().setBackground(new Color(140, 182, 206));
		BasicTableHeaderUI headerUI = new BasicTableHeaderUI() {
			public Dimension getPreferredSize(JComponent arg0) {
				Dimension dimension = super.getPreferredSize(arg0);
				dimension.setSize(dimension.getWidth(), 45);
				return dimension;
			}
		};
		JTableHeader header = this.getTable().getTableHeader();
		header.setUI(headerUI);
		this.getTable().setTableHeader(header);
		// 表头高度
		/*
		 * Dimension preferredSize =
		 * this.getTable().getTableHeader().getPreferredSize();
		 * preferredSize.setSize(preferredSize.getWidth(),
		 * preferredSize.getHeight()1.5);
		 * this.getTable().getTableHeader().setPreferredSize(preferredSize);
		 */
		// this.getArea().
		// 选中状态背景色,modify by harley--2006.06.19
		Color selectColor = UIManager.getColor("KDTable.selectionBackground");
		selectColor = (selectColor != null) ? selectColor : KDTStyleConstants.SELECT_COLOR;
		this.getTable().setSelectionBackground(selectColor);
		colorTemp = UIManager.getColor("KDTable.treeColBackground");
		colorTemp = (colorTemp != null) ? colorTemp : KDTStyleConstants.HEAD_BACKGROUND;
		// table.getTreeColumn().getStyleAttributes().setBackground(colorTemp);
		KDGanttTreeUI treeUI = new KDGanttTreeUI(this);
		// treeUI.setMyHashColor(colorTemp);
		treeUI.setMyHashColor(new Color(105, 92, 255));
		this.getGanttTreeTable().getTree().setUI(treeUI);

		// 设置行的背景色
		// this.getGanttTreeTable().setHighlighters(new HighlighterPipeline(new
		// Highlighter[] {
		// AlternateRowHighlighter.quickSilver,
		// new HierarchicalColumnHighlighter() }));

		this.getGanttTreeTable().setHighlighters(new HighlighterPipeline(new Highlighter[] { new KDGanttRowHighligther(this.getTree().getJTree()) }));
		// UIManager.put("Tree.hash", new
		// ColorUIResource(colorTemp.getRed(),colorTemp
		// .getGreen(),colorTemp.getBlue()));
		// disable 升降级
		// this.getTree().getIndentAction().setEnabled(false);
		// this.getTree().getUnindentAction().setEnabled(false);
		// this.getTree().getJTree().setCellRenderer(new
		// KDGanttTreeCellRenderer());
		// this.getTree().getJTree().getCellRenderer();
		// this.getGanttTreeTable().set
		// this.getTable().setSelectionBackground(RESchCompareUI.RED);
		this.getTabs().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (getTabs().getSelectedIndex() != 0) {
					// 添加影响情况add by warship at 2010/07/08
					setAffectRpt();
					// 刷新PERT
					Chart[] charts = Mediator.getPluginManager().getCharts();
					for (int i = 0; i < charts.length; i++) {
						charts[i].reset();
					}
				}
			}
		});

		this.getTaskManager().addTaskListener(new TaskListener() {
			public void dependencyAdded(TaskDependencyEvent e) {
				// TODO Auto-generated method stub

			}

			public void dependencyRemoved(TaskDependencyEvent e) {
				// TODO Auto-generated method stub

			}

			public void taskAdded(TaskHierarchyEvent e) {
				// TODO Auto-generated method stub

			}

			public void taskMoved(TaskHierarchyEvent e) {
				// TODO Auto-generated method stub

			}

			public void taskRemoved(TaskHierarchyEvent e) {
				// TODO Auto-generated method stub

			}

			public void taskProgressChanged(TaskPropertyEvent e) {
				// TODO Auto-generated method stub

			}

			public void taskPropertiesChanged(TaskPropertyEvent e) {
				// TODO Auto-generated method stub

			}

			public void taskScheduleChanged(TaskScheduleEvent e) {
				
				if (!(e.getTask() instanceof KDTask)) {
					return;
				}
				KDTask task = (KDTask) e.getTask();
				// ScheduleTaskChangeHelper.addChangeTask(task); //@See
				// KDTask.isAdjustable
				ScheduleTaskBaseInfo taskInfo = task.getScheduleTaskInfo();
				taskInfo.setStart(ScheduleParserHelper.parseGanttCalendarToDate(task.getStart()));
				taskInfo.setEnd(ScheduleParserHelper.parseGanttCalendarToDate(task.getEnd()));
				taskInfo.setDuration((int) task.getDuration().getLength());
				taskInfo.put("effectTimes", new BigDecimal(String.valueOf(task.getDuration().getLength() + KDGPConstants.EFFECTTIMESBALANCE)));
				// TODO H 硬编码
				try {
					// task.getCustomValues().setValue("有效工期", new
					// BigDecimal(Integer.toString(task.getLength() +
					// KDGPConstants.EFFECTTIMESBALANCE)));
					// if (task.getStart() != null && task.getEnd() != null) {
					// int natureTimes = task.getEnd().diff(task.getStart());s
					// task.getCustomValues().setValue("自然工期",
					// ScheduleCalendarHelper
					// .getNatureTimes(taskInfo.getStart(), taskInfo.getEnd()));
					// }
					/** 有效工期名称改为了工期(天) by duhongming **/
					task.getCustomValues().setValue("工期(天)", new BigDecimal(Integer.toString(task.getLength() + KDGPConstants.EFFECTTIMESBALANCE)));
					// if (task.getStart() != null && task.getEnd() != null) {
					// int natureTimes = task.getEnd().diff(task.getStart());
					// task.getCustomValues().setValue("自然工期",
					// ScheduleCalendarHelper
					// .getNatureTimes(taskInfo.getStart(), taskInfo.getEnd()));
					// }
				} catch (CustomColumnsException e1) {
					e1.printStackTrace();
					System.out.println("更新natureTimes时出错!");
				}
				/*
				 * if(e.getNewFinishDate().compareTo(e.getOldFinishDate())>0){
				 * System
				 * .out.println("greate it's "+e.getNewFinishDate()+"\t"+e.
				 * getOldFinishDate()); task.setEnd(e.getOldFinishDate()); }
				 */
				
				
			}

		});
		this.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				// System.out.println("test");
			}
		});
		// this.getTree().getIndentAction()
	}

	/**
	 * 根据任务的编辑情况，处理任务的影响程度，优先级是：里程碑>关键任务>一般任务>日常任务
	 * @return 任务的影响程度
	 * @Author：RD_owen_wen
	 * @CreateTime：2013-12-3
	 */
	public RESchTaskEffectDegreeEnum getTaskEffectDegree() {
		Task tasks[] = getTaskManager().getTasks();
		List taskTypesList = new ArrayList();
		taskTypesList.add(RESchTaskEffectDegreeEnum.AllTask.getAlias());
		taskTypesList.add(RESchTaskEffectDegreeEnum.DailyTask.getAlias());
		taskTypesList.add(RESchTaskEffectDegreeEnum.NormalTask.getAlias());
		taskTypesList.add(RESchTaskEffectDegreeEnum.KeyTask.getAlias());
		taskTypesList.add(RESchTaskEffectDegreeEnum.MilestoneTask.getAlias()); 
		String effectTaskType = RESchTaskEffectDegreeEnum.AllTask.getAlias(); //要取得影响程度最高的任务级别
		for (int i = 0; i < tasks.length; i++) {
			FDCScheduleTaskInfo scheduleTask = (FDCScheduleTaskInfo) ((KDTask) tasks[i]).getScheduleTaskInfo();
			Date oldStartDate = (Date) scheduleTask.get("myOldStartDate");
			Date oldEndDate = (Date) scheduleTask.get("myOldEndDate");
			Date oldCheckDate = (Date) scheduleTask.get("myOldCheckDate");
			if (!scheduleTask.getStart().equals(oldStartDate) || !scheduleTask.getEnd().equals(oldEndDate)
					|| (oldCheckDate != null && !oldCheckDate.equals(scheduleTask.getCheckDate()))) {
				if (taskTypesList.indexOf(scheduleTask.getTaskType().toString()) > taskTypesList.indexOf(effectTaskType)) {
					effectTaskType = scheduleTask.getTaskType().toString();
					if (taskTypesList.indexOf(effectTaskType) == (taskTypesList.size() - 1)) {
						break; //已经有里程碑任务受影响，跳出循环不需要再继续判断
					}
				}
			}
		}
		//根据字符串来找到对应的enum
		for (Iterator it = RESchTaskEffectDegreeEnum.iterator(); it.hasNext();) {
			RESchTaskEffectDegreeEnum taskEffectDegreeEnum = (RESchTaskEffectDegreeEnum) it.next();
			if (taskEffectDegreeEnum.getAlias().equals(effectTaskType)) {
				return taskEffectDegreeEnum;
			}
		}

		return RESchTaskEffectDegreeEnum.AllTask;
	}

	/**
	 * 根据任务的影响程度，设置comboBox以及scheduleInfo的影响程度
	 * @Author：RD_owen_wen
	 * @CreateTime：2013-11-28
	 */
	private void setEffectDegree() {
		RESchTaskEffectDegreeEnum taskEffectDegreeEnum = getTaskEffectDegree();
		if (!taskEffectDegreeEnum.equals(RESchTaskEffectDegreeEnum.AllTask)) {
			// getTabs().getComponentAt(1) 是kdPanel，BoderLayout，
			// Center放的是KDTable（首次add），North放的是EffectDegreePanel（第二个add）
			EffectDegreePanel effectDegreePanel = (EffectDegreePanel) ((KDPanel) getTabs().getComponentAt(1)).getComponent(1);
			effectDegreePanel.getCBEffect().setSelectedItem(taskEffectDegreeEnum.getAlias());
			// 放到scheduleInfo的EffectDegree字段中
			Task tasks[] = getTaskManager().getTasks();
			((FDCScheduleTaskInfo) ((KDTask) tasks[0]).getScheduleTaskInfo()).getSchedule().setEffectDegree(taskEffectDegreeEnum);
		}
	}
	
	/**
	 * 显示影响情况:如果开始时间或结束时间有改变就会显示 如果结束时间比原来结束时间提前显示蓝色 如果结束时间比原来结束时间延后显示红色
	 * 如果是新增加的任务不用显示 add by warship at 2010/07/08
	 * 
	 * @author modfiy by duhongming 2011-08-30
	 */
	protected void setAffectRpt() {
		final KDTable table = (KDTable) ((KDPanel) getTabs().getComponentAt(1)).getComponent(0);
		table.removeRows();
		Task tasks[] = (Task[]) this.getTaskManager().getTasks();
		for (int i = 0; i < tasks.length; i++) {
			KDTask task = (KDTask) tasks[i];
			FDCScheduleTaskInfo scheduleTask = (FDCScheduleTaskInfo) task.getScheduleTaskInfo();
			if (scheduleTask != null) {
				FDCScheduleInfo schedule = (FDCScheduleInfo) scheduleTask.getSchedule();
				ScheduleCalendarInfo calendar = schedule.getCalendar();
				Date startDate = scheduleTask.getStart();
				Date endDate = scheduleTask.getEnd();
				Date checkDate = scheduleTask.getCheckDate();
				Date oldStartDate = (Date) scheduleTask.get("myOldStartDate");
				Date oldEndDate = (Date) scheduleTask.get("myOldEndDate");
				Date oldCheckDate = (Date) scheduleTask.get("myOldCheckDate");
				/* modified by zhaoqin for R141014-0479 on 2015/01/16 start */
				//if (oldStartDate != null && oldEndDate != null || oldCheckDate != null) {
					IRow row = table.addRow();
					row.setUserObject(scheduleTask);
					/** 工期 **/
					BigDecimal oldDuration = ScheduleCalendarHelper.getEffectTimes(oldStartDate, oldEndDate, calendar);
					BigDecimal newDuration = ScheduleCalendarHelper.getEffectTimes(startDate, endDate, calendar);
					row.getCell(0).setValue(scheduleTask.getName());
					if(0 != oldDuration.intValue())
						row.getCell(1).setValue(oldDuration);
					row.getCell(2).setValue(newDuration);
					if(0 != oldDuration.intValue())
						row.getCell(3).setValue(newDuration.subtract(oldDuration));
					/** 计划开始日期 **/
					row.getCell(4).setValue(oldStartDate);
					row.getCell(5).setValue(startDate);

					if (null != oldStartDate && startDate.after(oldStartDate)) {
						row.getCell(6).setValue(ScheduleCalendarHelper.getEffectTimes(oldStartDate, startDate, calendar).subtract(new BigDecimal(1)));
					} else if (null != oldStartDate && startDate.before(oldStartDate)) {
						row.getCell(6).setValue(ScheduleCalendarHelper.getEffectTimes(oldStartDate, startDate, calendar).add((new BigDecimal(1))));
					} else if (null != oldStartDate) {
						row.getCell(6).setValue(ScheduleCalendarHelper.getEffectTimes(oldStartDate, startDate, calendar).subtract(new BigDecimal(1)));
					}
					/** 计划完成日期 **/
					row.getCell(7).setValue(oldEndDate);
					row.getCell(8).setValue(endDate);
					if (null != oldEndDate && endDate.after(oldEndDate)) {
						row.getCell(9).setValue(ScheduleCalendarHelper.getEffectTimes(oldEndDate, endDate, calendar).subtract(new BigDecimal(1)));
					} else if (null != oldEndDate && endDate.before(oldEndDate)) {
						row.getCell(9).setValue(ScheduleCalendarHelper.getEffectTimes(oldEndDate, endDate, calendar).add(new BigDecimal(1)));
					} else if (null != oldEndDate) {
						row.getCell(9).setValue(ScheduleCalendarHelper.getEffectTimes(oldEndDate, endDate, calendar).subtract(new BigDecimal(1)));
					}
					/** 考核日期 **/
					if (oldCheckDate == null && checkDate != null ) {
						if(scheduleTask.isIsCheckNode()){							
							row.getCell(11).setValue(checkDate);
						}else{
							row.getCell(11).setValue(null);
						}
					}
					if (checkDate == null && oldCheckDate != null) {
						if(scheduleTask.isIsCheckNode()){							
							row.getCell(10).setValue(oldCheckDate);
						}else{
							row.getCell(10).setValue(null);
						}
					}
					if (oldCheckDate != null && !checkDate.equals(oldCheckDate)) {
						if(scheduleTask.isIsCheckNode()){							
							row.getCell(10).setValue(oldCheckDate);
							row.getCell(11).setValue(checkDate);
						}else{
							row.getCell(10).setValue(null);
							row.getCell(11).setValue(null);
						}
						if(scheduleTask.isIsCheckNode()){							
							if (checkDate.after(oldCheckDate)) {
								row.getCell(12).setValue(
										ScheduleCalendarHelper.getEffectTimes(oldCheckDate, checkDate, calendar).subtract(new BigDecimal(1)));
							} else if (checkDate.before(oldCheckDate)) {
								row.getCell(12).setValue(ScheduleCalendarHelper.getEffectTimes(oldCheckDate, checkDate, calendar).add(new BigDecimal(1)));
							} else {
								row.getCell(12).setValue(
										ScheduleCalendarHelper.getEffectTimes(oldCheckDate, checkDate, calendar).subtract(new BigDecimal(1)));
							}
						}else{
							row.getCell(12).setValue(null);
						}
					}
					/** 颜色变化 **/
					// endData <oldEndDate 计划完成日期 小于旧的计划完成日期
					setcellStyle(startDate, oldStartDate, row, 4);
					setcellStyle(endDate, oldEndDate, row, 7);
					setcellStyle(checkDate, oldCheckDate, row, 10);
					if (null != oldStartDate && oldDuration != null && oldDuration != null && oldDuration.intValue() < newDuration.intValue()) {
						row.getCell(1).getStyleAttributes().setBackground(RESchCompareUI.RED);
						row.getCell(1 + 1).getStyleAttributes().setBackground(RESchCompareUI.RED);
						row.getCell(1 + 2).getStyleAttributes().setBackground(RESchCompareUI.RED);
					} else if (null != oldStartDate && oldDuration != null && oldDuration != null && oldDuration.intValue() > newDuration.intValue()) {
						row.getCell(1).getStyleAttributes().setBackground(RESchCompareUI.GREEN);
						row.getCell(1 + 1).getStyleAttributes().setBackground(RESchCompareUI.GREEN);
						row.getCell(1 + 2).getStyleAttributes().setBackground(RESchCompareUI.GREEN);
					}
					row.getCell("isLeaf").setValue(new Boolean(scheduleTask.isIsLeaf()));
					row.getCell("level").setValue(new Integer(scheduleTask.getLevel()));
					row.getCell("longNumber").setValue(scheduleTask.getLongNumber());
				//}
				/* modified by zhaoqin for R141014-0479 on 2015/01/16 end */
			}
		}
		setLevel(table, tasks);
		if (table.getRowCount() > 0) { //初始版本，还没有调整，所以没有影响程度要处理
			setEffectDegree();
		}
	}

	private void setcellStyle(Date date, Date oldDate, IRow row, int index) {
		if (date == null || oldDate == null) {
			return;
		}
		if (date.before(oldDate)) {
			row.getCell(index).getStyleAttributes().setBackground(RESchCompareUI.GREEN);
			row.getCell(index + 1).getStyleAttributes().setBackground(RESchCompareUI.GREEN);
			row.getCell(index + 2).getStyleAttributes().setBackground(RESchCompareUI.GREEN);
		} else if (date.after(oldDate)) {
			row.getCell(index).getStyleAttributes().setBackground(RESchCompareUI.RED);
			row.getCell(index + 1).getStyleAttributes().setBackground(RESchCompareUI.RED);
			row.getCell(index + 2).getStyleAttributes().setBackground(RESchCompareUI.RED);
		}
	}

	/**
	 * 
	 * @description 影响情况面板树型展示
	 * @author 杜红明
	 * @createDate 2011-9-13
	 * @param table
	 * @param tasks
	 * @version EAS7.0
	 * @see
	 */
	private void setLevel(final KDTable table, Task[] tasks) {
		for (int i = 0; i < table.getRowCount(); i++) {
			KDTask task = (KDTask) tasks[i];
			FDCScheduleTaskInfo scheduleTask = (FDCScheduleTaskInfo) task.getScheduleTaskInfo();
			if (scheduleTask != null) {
				IRow row = table.getRow(i);
				// 取得树级次和是否明细节点
				String value = (String) row.getCell("taskName").getValue();
				// 取得树级次和是否明细节点
				boolean isLeaf = ((Boolean) row.getCell("isLeaf").getValue()).booleanValue();
				int level = ((Integer) row.getCell("level").getValue()).intValue();
				CellTreeNode treeNode = new CellTreeNode();
				treeNode.setValue(value);// 显示的值，此处是‘任务名称’字符串
				treeNode.setTreeLevel(level);// 级次，从0开始，此处为任务的树级次
				treeNode.setHasChildren(!isLeaf);
				treeNode.isCollapse();
				treeNode.addClickListener(new NodeClickListener() {
					public void doClick(CellTreeNode source, ICell cell, int type) {
						table.revalidate();
					}
				});
				row.getStyleAttributes().setLocked(true);
				row.getCell("taskName").getStyleAttributes().setLocked(false);
				row.getCell("taskName").setValue(treeNode);// 设置前面构建的单元格树到表
			}
		}
	}

	/**
	 * 初始化配置项
	 */
	protected void initMyOptions() {
		// 不显示今日提示
		this.getOptions().setOpenTips(false);
		this.getOptions().setExport3dBorders(true);
		this.getOptions().setExportComplete(true);
		this.getOptions().setExportName(true);
		this.getOptions().setExportRelations(true);
	}

	/**
	 * 删除甘特图商标、多余页签、修改所有按钮为KDWorkButtion
	 * <p>
	 * 总之是修改甘特图外观就是了<br>
	 * edit by emanon
	 */
	private void removeGanttLogo() {
		// 删除Logo
		Container panel = SwingUtilities.getAncestorOfClass(JPanel.class, getTree());
		for (int i = 0; i < panel.getComponentCount(); i++) {
			Component comp = panel.getComponent(i);
			if (comp instanceof Box) {
				Box box = (Box) comp;
				for (int j = box.getComponentCount() - 1; j >= 0; j--) {
					Component myComp = box.getComponent(i);
					if (myComp instanceof GanttImagePanel) {
						box.remove(myComp);
					}
				}
			}
		}
		
		// 删除资源页签
		if (this.getScheduleUIFacede() == null) {
			getTabs().remove(UIFacade.RESOURCES_INDEX);
		}

		// 将JSplitPanel 换成KDSplitPanel
		JSplitPane splitPanel = (JSplitPane) SwingUtilities.getAncestorOfClass(JSplitPane.class, getTree());
		Container parentUI = SwingUtilities.getAncestorOfClass(JPanel.class, splitPanel);
//		ScheduleBaseUI coreUI = (ScheduleBaseUI) SwingUtilities.getAncestorOfClass(ScheduleBaseUI.class, parentUI);
		KDSplitPane newSplitPanel = new KDSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		newSplitPanel.setLeftComponent(splitPanel.getLeftComponent());
//		newSplitPanel.setRightComponent(splitPanel.getRightComponent());
		newSplitPanel.setPreferredSize(new Dimension(0,0));
		newSplitPanel.setDividerLocation(newSplitPanel.getDividerLocation());
		newSplitPanel.setDividerSize(newSplitPanel.getDividerSize());
		newSplitPanel.setResizeWeight(newSplitPanel.getResizeWeight());
		newSplitPanel.setOneTouchExpandable(true);
		parentUI.remove(splitPanel);
		// TODO 根据左边默认列宽设置位置
		newSplitPanel.setDividerLocation(800);
		parentUI.add(newSplitPanel, BorderLayout.CENTER);

		// 重新设置按钮，将原来不要的隐藏，并将所有按钮转换为KDWorkButton
		BorderLayout layout = (BorderLayout) parentUI.getLayout();
		Container comp = (Container) layout.getLayoutComponent(BorderLayout.NORTH);
		layout = (BorderLayout) comp.getLayout();
		if (toolbarBox == null) {
			Box box = (Box) layout.getLayoutComponent(BorderLayout.WEST);
			box.setName("toolbarBox");
			setToolbarBox(box);
		}
		// 原框架只保留升级、降级、上移、下移按钮
		for (int i = toolbarBox.getComponentCount() - 1; i >= 0; i--) {
			Component obj = toolbarBox.getComponent(i);
			if (obj instanceof TestGanttRolloverButton) {
				TestGanttRolloverButton button = (TestGanttRolloverButton) obj;
				Object actName = button.getAction().getValue(Action.NAME);
				// 链接特殊处理，不可能的2个任务不能链接（暂时不显示这个按钮，代码勿删，要的时候不会写）
				// if (actName != null
				// && (actName.equals(GanttLanguage.getInstance().getText(
				// "link")))) {
				// KDWorkButton myButton = new KDWorkButton(
				// new LinkTasksActionExt(this.getTaskManager(),
				// (TaskSelectionManager) this
				// .getTaskSelectionContext(), this
				// .getUIFacade()));
				// toolbarBox.remove(i);
				// toolbarBox.add(myButton, i);
				// continue;
				// }
				if ("上移".equals(actName) || "下移".equals(actName)
						|| "升级".equals(actName) || "降级".equals(actName)) {
					KDWorkButton myButton = new KDWorkButton(button.getAction());
					myButton.setFactType(KDWorkButton.BLUE_FACE);
					toolbarBox.remove(i);
					toolbarBox.add(myButton, i);
				} else {
					toolbarBox.remove(i);
				}
			}
		}
		// 新添加 新任务、删除 按钮在最开始，添加 大纲级次 按钮在最后
		// filler是按钮间间隔
		Dimension dms = new Dimension(3, 0);
		toolbarBox.add(new Box.Filler(dms, dms, dms), 0);
		KDWorkButton newTaskBtn = new KDWorkButton(this.getTree().getNewTaskAction());
		newTaskBtn.setFactType(KDWorkButton.BLUE_FACE);
		toolbarBox.add(newTaskBtn, 1);

		delButton = new KDWorkButton();
		delButton.setText("删除");
		delButton.setName("delLine");
		delButton.setFactType(KDWorkButton.BLUE_FACE);
		delButton.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteTasks(true);
			}
		});
		toolbarBox.add(delButton, 2);
		dms = new Dimension(3, 0);
		toolbarBox.add(new Box.Filler(dms, dms, dms), 3);
		
		// 大纲级次的按钮在ScheduleBaseUI中，此处不加，在UI的onLoad方法最后添加上

		// fix jView tooltips bug
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JViewport myComponent = (JViewport) SwingUtilities
						.getAncestorOfClass(JViewport.class,
								getGanttTreeTable());
				ToolTipManager.sharedInstance()
						.unregisterComponent(myComponent);
				ToolTipManager.sharedInstance().unregisterComponent(
						getGanttTreeTable().getScrollPane().getViewport());
			}
		});
	}

	/*
	 * 此方法在基类的构造方法一开始会调用，在这做一些预处理
	 */
	public void setLayout(LayoutManager mgr) {
		SchedulePluginManager.resolvePlugins();
		super.setLayout(mgr);
	}

	private boolean isEnabled = false;

	public void setEnabledNewTask(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void gotoDate(Date date) {
		getUIFacade().getScrollingManager().scrollLeft(date);
	}

	private boolean isEmptyNode(DefaultMutableTreeNode node) {
		return node.getChildCount() == 0;
	}

	public void deleteTasks(boolean confirmation) {
		getTabs().setSelectedIndex(UIFacade.GANTT_INDEX);

		final DefaultMutableTreeNode[] cdmtn = getTree().getSelectedNodes();
		if (cdmtn == null || cdmtn.length == 0) {
			getUIFacade().setStatusText(getLanguage().getText("msg21"));
			return;
		}
		boolean isConfirmDelBranch = false;
		boolean isNeedShowMessage = false;
		// TODO 校验是否是否已经开始（当前版本不可能开始， 所有需校验上一版本计划是否已经进行业务）
		for (int i = 0; i < cdmtn.length; i++) {
			if (cdmtn[i] != null && cdmtn[i] instanceof TaskNode) {
				if (!isEmptyNode(cdmtn[i])) {
					isNeedShowMessage = true;
					break;
				}
			}
		}
		if (isNeedShowMessage) {
			if (FDCMsgBox.showConfirm2New(this, "您所选择的任务中包含非明细任务，删除这些任务将同时删除其包含的所有下级任务，您确定删除？") == FDCMsgBox.NO) {
				return;
			} else {
				isConfirmDelBranch = true;
			}
		}
		// , getLanguage().getText("question")
		if (isConfirmDelBranch
				|| FDCMsgBox.YES == FDCMsgBox.showConfirm2("您确认要删除选中的任务？")) {
			getUndoManager().undoableEdit("Task removed", new Runnable() {
				public void run() {
					ArrayList fathers = new ArrayList();
					getTree().stopEditing();
					// 删除客户端数据
					for (int i = 0; i < cdmtn.length; i++) {
						if (cdmtn[i] != null && cdmtn[i] instanceof TaskNode) {
							TaskNode taskNode = (TaskNode) cdmtn[i];
							removeNodeSetDeleteWbsSet(taskNode);
							KDTask ttask = (KDTask) (cdmtn[i].getUserObject());
							FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) ttask.getScheduleTaskInfo();
							getTaskManager().deleteTask(ttask);
							ttask.delete();
							FDCScheduleInfo schedule = taskInfo.getSchedule();
							schedule.setInt("initIndex", schedule.getInt("initIndex") - 1);

							DefaultMutableTreeNode father = getTree().getFatherNode(ttask);
							getTree().removeCurrentNode(cdmtn[i]);
							if (father != null) {
								GanttTask taskFather = (GanttTask) father.getUserObject();
								AdjustTaskBoundsAlgorithm alg = getTaskManager().getAlgorithmCollection().getAdjustTaskBoundsAlgorithm();
								alg.run(taskFather);
								// taskFather.refreshDateAndAdvancement(tree);
								father.setUserObject(taskFather);
								fathers.add(father);
							}
						} else if (cdmtn[i] != null && cdmtn[i] instanceof BlankLineNode) {
							((GanttTreeTableModel) getTree().getTreeTable().getTreeTableModel()).removeNodeFromParent(cdmtn[i]);
						}

					}
					for (int i = 0; i < fathers.size(); i++) {
						DefaultMutableTreeNode father = (DefaultMutableTreeNode) fathers.get(i);
						if (father.getChildCount() == 0)
							((Task) father.getUserObject()).setProjectTask(false);
					}
				}

			});

			GanttTree2.resetTask(this, null, 0);
			refreshProjectInfos();
			getArea().repaint();
			this.repaint2();
			getResourcePanel().area.repaint();
			// TODO FIXME 此处代码请以后做优化
			Date start = getTaskManager().getProjectStart();
			Date end = getTaskManager().getProjectEnd();

			Integer max = Integer.parseInt((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24) + "");
			setFromDate(start);
			setToDate(end);
			initSlider(max.intValue());
			setAskForSave(true);
			// setQuickSave (true);
			// quickSave("deleteTasks");
		}
	}

	public void removeNodeSetDeleteWbsSet(TaskNode taskNode) {
		KDTask ttask = (KDTask) (taskNode.getUserObject());
		if (ttask.getScheduleTaskInfo() != null) {
			FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) ttask.getScheduleTaskInfo();
			((FDCScheduleInfo) editData).getTaskEntrys().remove(taskInfo);
		}
		if (taskNode.getChildCount() > 0) {
			for (int i = 0; i < taskNode.getChildCount(); i++) {
				removeNodeSetDeleteWbsSet((TaskNode) taskNode.getChildAt(i));
			}
		}
	}

	public DefaultMutableTreeNode getSelectedNode() {
		DefaultMutableTreeNode selectedNode = getTree().getSelectedNode();
		DefaultMutableTreeNode rootNode = getTree().getRoot();
		if (rootNode != null && rootNode.equals(selectedNode)) {
			// 过滤掉选择的root节点，出现提示框后可能自动选择根节点从而导致空指针异常。 这是如何自动选择上的。真奇怪 by
			// zhiqiao_yang
			DefaultMutableTreeNode[] selectedNodes = getTree().getSelectedNodes();
			if (selectedNodes != null) {
				for (int i = 0; i < selectedNodes.length; ++i) {
					if (!selectedNodes[i].equals(getTree().getRoot())) {
						// 返回选择的第一个非根节点
						return selectedNodes[i];
					}
				}
			}
			return null;
		}
		return selectedNode;
	}

	private FDCScheduleTaskInfo getParentTaskInfo(DefaultMutableTreeNode parentNode) {
		if (parentNode != null && parentNode.getUserObject() instanceof KDTask) {
			KDTask kdTask = (KDTask) parentNode.getUserObject();
			return (FDCScheduleTaskInfo) kdTask.getScheduleTaskInfo();
		}
		return null;
	}

	/**
	 * 新增时，设置任务的时间信息， 如开始时间，结束时间，工期等
	 */
	private void setTimeInfo(FDCScheduleTaskInfo scheduleTask, FDCScheduleTaskInfo parentTask) {
		// 持续时间默认为0,有效工期默认为1
		BigDecimal defaultDuration = FDCSCHUtils.getDefaultDuration();
		scheduleTask.setEffectTimes(defaultDuration);
		scheduleTask.setDuration(defaultDuration.intValue());
		if (parentTask != null) {
			scheduleTask.setBoundStart(parentTask.getBoundStart());
			scheduleTask.setBoundEnd(parentTask.getBoundEnd());
			scheduleTask.setStart(parentTask.getStart());
		} else {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 0);
			scheduleTask.setStart(((FDCScheduleInfo) editData).getStartDate());
		}
		// TODO 时间有问题
		scheduleTask.setEnd(scheduleTask.getStart());
		// scheduleTask.setEnd(ScheduleCalendarHelper.getEndDate(scheduleTask.
		// getStart(), scheduleTask.getEffectTimes(),
		// editData.getCalendar()));//
		scheduleTask.setNatureTimes(ScheduleCalendarHelper.getNatureTimes(scheduleTask.getStart(), scheduleTask.getEnd()));
	}

	private String getNewNumber(DefaultMutableTreeNode selectedNode) {
		if (selectedNode == null) {
			selectedNode = getTree().getRoot();
		}
		return FDCSCHUtils.formatTaskNumber(selectedNode.getChildCount() + 1);
	}

	/**
	 * 新增时，创建任务
	 */
	private FDCScheduleTaskInfo createScheduleTask(DefaultMutableTreeNode selectedNode) throws BOSException {
		// 暂时屏蔽在只读状态下新建任务 by duhongming
		if (getOprtState().equals("VIEW") || getOprtState().equals("MAIN_SCHEDULE_VIEW")) {
			return null;
		}
		FDCScheduleTaskInfo parentTask = getParentTaskInfo(selectedNode);
		FDCScheduleTaskInfo scheduleTask = new FDCScheduleTaskInfo();
		scheduleTask.setId(BOSUuid.create(scheduleTask.getBOSType()));
		scheduleTask.setSrcID(scheduleTask.getId().toString());
		scheduleTask.setParent(parentTask);
		if(parentTask != null ){
			if (parentTask.getBelongToSpecial()!= null) {
				parentTask.setIsLeaf(false);
				FDCScheduleTaskInfo dependTask  = new FDCScheduleTaskInfo();
				dependTask.setId(BOSUuid.read(parentTask.getSrcID()));
				dependTask.setName(parentTask.getName());
				dependTask.setNumber(parentTask.getNumber());
				scheduleTask.setDependMainTaskID(dependTask);
			} 
				scheduleTask.setBoundEnd(parentTask.getEnd());
				scheduleTask.setBoundStart(parentTask.getStart());
		} 
		scheduleTask.setNumber(getNewNumber(selectedNode));
		if (parentTask != null) {
			scheduleTask.setLongNumber(parentTask.getLongNumber() + "!" + scheduleTask.getNumber());
			scheduleTask.setLevel(parentTask.getLevel());
		} else {
			scheduleTask.setLongNumber(scheduleTask.getNumber());
			scheduleTask.setLevel(0);
		}
		scheduleTask.setName(getOptions().getTaskNamePrefix() + "_" + scheduleTask.getNumber());
		scheduleTask.setIsLeaf(true);
		scheduleTask.setComplete(FDCHelper.ZERO);
		scheduleTask.setWorkLoad(FDCHelper.ZERO);
		scheduleTask.setSchedule((FDCScheduleInfo) editData);
		scheduleTask.setLocked(false);
		setTimeInfo(scheduleTask, parentTask);
		scheduleTask.setIsScheduleTask(true);
		scheduleTask.setPriority(1);
		scheduleTask.setEditable(true);
		scheduleTask.setTaskType(RESchTaskTypeEnum.COMMONLY);
		scheduleTask.put("projectCostCenterID",((CurProjectInfo) editData.get("project")).getCostCenter().getId().toString());
		editData.getScheduleTasks().add(scheduleTask);
		return scheduleTask;
	}

	public Task newTask() {
		boolean isMainTask = ((FDCScheduleInfo) editData).getProjectSpecial() == null;
		return newTask(isMainTask);
	}

	/**
	 * 根据任务类型创建新的任务
	 * 
	 * @return
	 */
	public Task newTask(boolean isMainTask) {
		/***
		 * 当未初始化完成时，不允许新增任务
		 */
		if (this.isEnabled) {
			DefaultMutableTreeNode selectedNode = getSelectedNode();
			getTabs().setSelectedIndex(UIFacade.GANTT_INDEX);
			try {
				FDCScheduleTaskInfo scheduleTaskInfo = createScheduleTask(selectedNode);
				GanttLanguage lang = GanttLanguage.getInstance();
				int index = -1;// 插入到树中的位置
				if (selectedNode != null) {
					DefaultMutableTreeNode parent1 = (DefaultMutableTreeNode) selectedNode.getParent();
					index = parent1.getIndex(selectedNode) + 1;
				}

				KDTask task = new KDTask(getTaskManager(), scheduleTaskInfo);
				getTaskManager().registerTask(task);// create a new task in the
				// tab
				int initIndex = editData.getInt("initIndex");
				editData.setInt("initIndex", initIndex + 1);
				task.setAfterInit(true);
				task.setExpand(true);
				TaskContainmentHierarchyFacade taskHierarchy = getTaskManager().getTaskHierarchy();
				// 将当前节点放到他的父节点下面
				if (task.getScheduleTaskInfo().getObjectValue("parent") != null) {
					String parentId = task.getScheduleTaskInfo().getObjectValue("parent").getString("id");
					KDTask parentTask = (KDTask) kdTaskMap.get(parentId);
                    
					DeleteParentDependency deletor = new DeleteParentDependency(parentTask);
					Thread deleteThread = new Thread(deletor, "deleteParent");
					deleteThread.start();

					taskHierarchy.move(task, parentTask);
				} else {
					getTree().addObject(task, selectedNode, index);
				}
				kdTaskMap.put(scheduleTaskInfo.getId().toString(), task);

				this.getArea().repaint();
				setAskForSave(true);
				getUIFacade().setStatusText(lang.getText("createNewTask"));
				this.getTree().setEditingTask(task);
				if (this.getOptions().getAutomatic()) {
					propertiesTask();
				}
				GanttTree2.resetTask(this, null, 0);
				repaint2();
				return task;
			} catch (Exception e) {
				logger.warn(e.getCause(), e);
			}
		}
		return null;
	}

	private Map kdTaskMap = null;

	/**
	 * 取得一个实体到KDTask的映射
	 * 
	 * @return
	 */
	public Map getKDTaskMaps() {
		return this.kdTaskMap;
	}

	/**
	 * 通过实体任务的ID取得KDTask
	 * 
	 * @param infoId
	 * @return
	 */
	public KDTask getKDTaskById(String taskId) {
		if (getKDTaskMaps() != null) {
			return (KDTask) getKDTaskMaps().get(taskId);
		}
		return null;
	}

	/**
	 * 设置映射关系
	 * 
	 * @see TaskHandler
	 * @param kdTaskMap
	 */
	public void setKDTaskMap(Map kdTaskMap) {
		this.kdTaskMap = kdTaskMap;
	}

	public List getSortKDTask() {
		Task rootTask = this.getTaskManager().getTaskHierarchy().getRootTask();
		return _getSortKDTask(rootTask);
	}

	private List _getSortKDTask(Task parentTask) {
		List myList = new ArrayList();
		if (parentTask instanceof KDTask) {
			myList.add(parentTask);
		}
		if (this.getTaskManager().getTaskHierarchy().hasNestedTasks(parentTask)) {
			Task[] tasks = this.getTaskManager().getTaskHierarchy().getNestedTasks(parentTask);
			for (int i = 0; i < tasks.length; i++) {
				myList.addAll(_getSortKDTask(tasks[i]));
			}
		}
		return myList;
	}

	public void propertiesTask() {
		if (this.propertityUIName == null) {
			super.propertiesTask();
			return;
		}
		if (!this.isAdjustSchedule()) {
			UIContext uiContext = getUIContext();
			try {
				IUIWindow myUi = UIFactory.createUIFactory(UIFactoryName.MODEL).create(getPropertityUIName(), uiContext, null,
						(String) uiContext.get("OprtState"),
								WinStyle.SHOW_TOOLBAR);
				myUi.show();
			} catch (UIException e) {
				e.printStackTrace();
			}
		}
		int [] location = null;
		if(getSelectedNode() != null){
			location = getTable().getSelectedRows();
			KDTask task = (KDTask) getSelectedNode().getUserObject();
			FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) task.getScheduleTaskInfo();
			if(info.getCheckDate()!=null)
			  getTree().getTable().setValueAt(info.getCheckDate(), location[0], 5);
		}
		
	}

	public void propertiesTask(UIContext ctx, String propertityUIName, String state) {
		this.setPropertityUIName(propertityUIName);
		this.setUIContext(ctx);
		this.getUIContext().put("OprtState", state);
		propertiesTask();
	}

	private String propertityUIName = null;

	public String getPropertityUIName() {
//		if (this.getUIContext().get("Owner") instanceof SpecialScheduleEditUI) {
//			return "com.kingdee.eas.fdc.schedule.client.FDCSpecialScheduleTaskPropertiesUI";
//		} else {
			return this.propertityUIName;
//		}
	}

	public void setPropertityUIName(String propertityUIName) {
		this.propertityUIName = propertityUIName;
	}

	private UIContext ctx = null;

	public UIContext getUIContext() {
		return ctx;
	}

	public void setUIContext(UIContext ctx) {
		this.ctx = ctx;
		handleTreeTableStatus();
	}

	/**
	 * 甘特图状态改变时更新按钮可用
	 */
	public void handleTreeTableStatus() {
		if (this.getOprtState() != null
				&& ("ADDNEW".equals(this.getOprtState()) || "EDIT".equals(this
						.getOprtState()))) {
			isOnlyViewer = false;
			getTree().setActions();
			this.getTree().getMoveDownAction().setEnabled(true);
			this.getTree().getMoveUpAction().setEnabled(true);
			this.getTree().getLinkTasksAction().setEnabled(true);
			this.getTree().getUnlinkTasksAction().setEnabled(true);
			this.getTree().getIndentAction().setEnabled(true);
			this.getTree().getUnindentAction().setEnabled(true);
			// 当界面状态改变为新增或修改时，如果原状态中新任务按钮不可用，需要改为可用
			this.getTree().getNewTaskAction().setEnabled(true);
			delButton.setEnabled(true);
		} else {
			isOnlyViewer = true;
			this.getTree().getMoveDownAction().setEnabled(false);
			this.getTree().getMoveUpAction().setEnabled(false);
			this.getTree().getLinkTasksAction().setEnabled(false);
			this.getTree().getUnlinkTasksAction().setEnabled(false);
			this.getTree().getIndentAction().setEnabled(false);
			this.getTree().getUnindentAction().setEnabled(false);
			this.getTree().getNewTaskAction().setEnabled(false);
			delButton.setEnabled(false);
		}

		if (this.isAdjustSchedule()) {
			this.getTree().getMoveDownAction().setEnabled(false);
			this.getTree().getMoveUpAction().setEnabled(false);
			this.getTree().getIndentAction().setEnabled(false);
			this.getTree().getUnindentAction().setEnabled(false);
			delButton.setEnabled(false);
		}
	}

	public void printProject() {

		BufferedImage image = getImage();

		KDImagePrinter imageManager = new KDImagePrinter();
		imageManager.setParent(this);

		imageManager.setData(new Image[] { image });
		imageManager.print();
	}

	public void previewPrint() {
		BufferedImage image = getImage();
		KDImagePrinter imageManager = new KDImagePrinter();
		if (false) {
			try {
				ImageIO.write(image, "JPG", new File("C:/test.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		imageManager.setData(new Image[] { image });
		imageManager.printPreview(this);
	}

	public BufferedImage getImage() {
		Chart chart = getUIFacade().getActiveChart();
		// RenderedChartImage renderedImage =
		// (RenderedChartImage)chart.getRenderedImage
		// (this.getOptions().getExportSettings());
		// BufferedImage image =
		// renderedImage.getWholeImage();//chart.getChart(this
		// .getOptions().getExportSettings());
		BufferedImage image = null;
		if (chart.getRenderedImage(this.getOptions().getExportSettings()) instanceof RenderedChartImage) {
			RenderedChartImage renderedImage = (RenderedChartImage) chart.getRenderedImage(this.getOptions().getExportSettings());
			image = renderedImage.getWholeImage();
		} else if (chart.getRenderedImage(this.getOptions().getExportSettings()) instanceof BufferedImage) {
			image = (BufferedImage) chart.getRenderedImage(this.getOptions().getExportSettings());
		}
		if (true)
			return image;
		// //画一个大矩形
		Graphics g = image.getGraphics();
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.translate(0, 0);
		g.setColor(Color.black);
		g.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);

		// g.translate(0, image.getHeight());
		// g.setColor(Color.black);
		// g.drawRect(0, 0, image.getWidth(),100);

		g.translate(0, image.getHeight() - 200);
		g.setColor(Color.white);
		g.fillRect(0, 0, image.getWidth(), 200);

		g.setColor(Color.black);
		g.drawRect(0, 0, image.getWidth() - 1, 30);
		g.drawRect(0, 30, image.getWidth() - 1, 170 - 1);

		g.setColor(Color.black);
		g.drawRect(0, 170, image.getWidth() - 1, 30 - 1);

		// 设置工程项目属性
		setProjectData(g);

		// 设置图标示例
		setIcon(g);

		g.drawLine(520, 30, 520, 170 - 1);

		g.translate(0, 0);

		return image;
	}

	public void setProjectData(Graphics g) {
		g.drawString("项目：", 20, 60);
		g.drawString("日期：", 20, 100);
	}

	public void setIcon(Graphics g) {

		g.setColor(Color.GRAY);
		g.fillRect(580, 50, 100, 10);

		g.drawString("任务：", 540, 60);
		g.drawString("进度：[ 0.0% ]", 540, 100);

		// g.drawString("任务进度：", 720,60);
		// g.drawString("持续天数：", 720,100);
	}

	public void savePicture() {
		BufferedImage image = getImage();

		try {
			String strFullPath = getSelectFile(true, "jpg");
			if (strFullPath != null) {

				File file = new File(strFullPath);
				// 判断文件是否存在
				if (file.exists()) {
					// 是否覆盖原文件
					if (!showConFirm("是否覆盖原文件")) {
						return;
					}
				}

				ImageIO.write(image, "JPG", new File(strFullPath));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean showConFirm(String string) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 描述：通过文件对话框得到文件
	 * 
	 * @throws EASBizException
	 */
	public String getSelectFile(boolean isSave, String ext) throws Exception {
		return null;
	}

	/** Quit the application */
	public void quitApplication() {
		getOptions().setWindowPosition(getX(), getY());
		getOptions().setWindowSize(getWidth(), getHeight());
		getOptions().setUIConfiguration(getUIConfiguration());
		getOptions().save();
		getProject().close();
		dispose();
	}

	public void setEventsEnabled(boolean enable) {
		((TaskManagerImpl) getTaskManager()).setEventsEnabled(enable);
	}

	/**
	 * 
	 * @description 初始化设置数据、按钮
	 * @author 周航健
	 * @createDate 2011-09-29
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 */
	public void removeToolbar() {
		this.getToolbarBox().removeAll();
	}

	public void setToolbarBox(Box toolbarBox) {
		this.toolbarBox = toolbarBox;
	}

	public Box getToolbarBox() {
		if (toolbarBox == null) {
			JSplitPane splitPanel = (JSplitPane) SwingUtilities
					.getAncestorOfClass(JSplitPane.class, getTree());
			Container parentUI = SwingUtilities.getAncestorOfClass(
					JPanel.class, splitPanel);
			BorderLayout layout = (BorderLayout) parentUI.getLayout();
			Container comp = (Container) layout
					.getLayoutComponent(BorderLayout.NORTH);
			layout = (BorderLayout) comp.getLayout();
			toolbarBox = (Box) layout.getLayoutComponent(BorderLayout.WEST);
			toolbarBox.setName("toolbarBox");
		}
		return toolbarBox;
	}
}