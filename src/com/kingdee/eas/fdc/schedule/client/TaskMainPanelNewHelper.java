/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.ganttproject.cache.ActivityCache;
import net.sourceforge.ganttproject.calendar.ScheduleCalendarImpl;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.shape.PaintCellRenderer;
import net.sourceforge.ganttproject.shape.ShapeConstants;
import net.sourceforge.ganttproject.shape.ShapePaint;
import net.sourceforge.ganttproject.task.CustomColumnsException;
import net.sourceforge.ganttproject.task.TaskMutator;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.ext.IFilterInfoProducer;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractWFEntryInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractWFTypeInfo;
import com.kingdee.eas.fdc.schedule.AchievementTypeInfo;
import com.kingdee.eas.fdc.schedule.CheckNodeCollection;
import com.kingdee.eas.fdc.schedule.CheckNodeFactory;
import com.kingdee.eas.fdc.schedule.CheckNodeInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.HelpDeptEntryCollection;
import com.kingdee.eas.fdc.schedule.HelpDeptEntryInfo;
import com.kingdee.eas.fdc.schedule.HelpPersonEntryCollection;
import com.kingdee.eas.fdc.schedule.HelpPersonEntryInfo;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.GanttTreeTableModelExt;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.enums.EnumUtils;

/**
 * 版权： 金蝶国际软件集团有限公司版权所有
 * <p>
 * 本类主要用于处理任务属性基本信息界面
 * <p>
 * 重点关注：开始、结束日期、工期、搭接天数中任意一个改变，其余字段联动计算<br>
 * add by emanon<br>
 * 1、工期改变<br>
 * 开始不变，计算结束、搭接<br>
 * 2、开始日期改变：<br>
 * 工期不变，计算结束、搭接<br>
 * 3、结束日期改变：<br>
 * 开始不变，计算工期、搭接<br>
 * 4、搭接天数改变<br>
 * 工期不变，计算开始、结束<br>
 * 搭接改变计算写在PredecessorsPanelNewHelper.table_editStopped()中<br>
 * 5、日历改变<br>
 * 我擦，只改工期(还应该微调开始、结束日期到最近工作日）<br>
 * 
 * @author emanon
 * @version EAS7.0
 * @createDate 2011-8-20
 * @see
 */
public class TaskMainPanelNewHelper implements ITaskPanelHelper {

	private FDCScheduleTaskPropertiesNewUI taskProperties;
	private GanttLanguage language = GanttLanguage.getInstance();
	private KDTask selectTask;
	private boolean isStageAchievement = false;
	private ScheduleCalendarInfo schCalendar = null;
	private CurProjectInfo project = null;
	private String passMustEvaluation = "0";
	
	private final static int DURATION = 0;
	private final static int START = 1;
	private final static int END = 2;
	

	public TaskMainPanelNewHelper(
			FDCScheduleTaskPropertiesNewUI taskProperties,
			CurProjectInfo project) {
		this.taskProperties = taskProperties;
		selectTask = taskProperties.getSelectedTask();
		schCalendar = (ScheduleCalendarInfo) taskProperties.prmtTaskCalendar
				.getValue();
		this.project = project;
		if (schCalendar == null) {
			try {
				String prjID = project == null ? null : project.getId()
						.toString();
				schCalendar = ScheduleCalendarFactory.getRemoteInstance()
						.getDefaultCal(prjID);
			} catch (BOSException e) {
				taskProperties.handUIException(e);
			} catch (EASBizException e) {
				taskProperties.handUIException(e);
			}
		}
		taskProperties.txtTaskName.setRequired(true);
		initCtrl();
	}

	/**
	 * @description 初始化时修改控件状态
	 * @author 杜红明
	 * @createDate 2011-8-29 void
	 * @version EAS7.0
	 * @see
	 */
	private void initCtrl() {
		taskProperties.prmtAchievementType.setEditable(false);
		taskProperties.prmtBizType.setEditable(false);
		taskProperties.prmtBizType.setEnabledMultiSelection(true);
		taskProperties.prmtDutyDep.setEditable(false);
		taskProperties.prmtDutyPerson.setEditable(false);
		taskProperties.prmtHelpDep.setEditable(false);
		taskProperties.prmtHelpDep.setEnabledMultiSelection(true);
		taskProperties.prmtQualityAppraisePerson.setEditable(false);
		taskProperties.prmtHelpPerson.setEditable(false);
		taskProperties.prmtHelpPerson.setEnabledMultiSelection(true);
		taskProperties.prmtRiskChargePerson.setEditable(false);
		taskProperties.prmtTaskCalendar.setEditable(false);
		taskProperties.prmtTaskGuide.setEditable(false);		
		taskProperties.cbTaskType.setEditable(false);
		// taskProperties.txtWorkDay.setEditable(true);
		taskProperties.txtNumber.setEnabled(false);
		String status = taskProperties.getOwnerStatus("VIEW");
		if ("VIEW".equals(status)) {
			// taskProperties.actionSave.setVisible(false);
			taskProperties.prmtAchievementType.setEnabled(false);
			taskProperties.kDTablePredecessor.setEnabled(false);
			taskProperties.prmtScheduleAppraisePerson.setEnabled(false);
			taskProperties.cbShape.setEnabled(false);
			taskProperties.prmtAchievementType.setEnabled(false);

			if (taskProperties instanceof FDCSpecialScheduleTaskPropertiesUI) {
				((FDCSpecialScheduleTaskPropertiesUI) taskProperties).prmtRelationMainTask
						.setEnabled(false);
			}
		} else {
			// taskProperties.actionSave.setVisible(true);

			// taskProperties.txtNumber.setEditable(true);
			taskProperties.prmtAchievementType.setEnabled(true);
			taskProperties.kDTablePredecessor.setEnabled(true);
			taskProperties.cbShape.setEnabled(true);

			if (taskProperties instanceof FDCSpecialScheduleTaskPropertiesUI) {
				((FDCSpecialScheduleTaskPropertiesUI) taskProperties).prmtRelationMainTask
						.setEnabled(true);
			}
		}
	
		taskProperties.txtAppendEndQuantity.setRemoveingZeroInDispaly(false);
		
		//标准任务每指引排序
		EntityViewInfo view = taskProperties.prmtTaskGuide.getEntityViewInfo();
		if(view == null){
			taskProperties.prmtTaskGuide.getQueryAgent().resetRuntimeEntityView();
			view = new EntityViewInfo();
			SorterItemCollection sorter = new SorterItemCollection();
		    SorterItemInfo sort = new SorterItemInfo("longnumber");
		    sorter.add(sort);
			view.setSorter(sorter);
		}
		taskProperties.prmtTaskCalendar.getQueryAgent().getQueryInfo().setAlias("日历查询");	
		taskProperties.prmtTaskCalendar.setFilterInfoProducer(new IFilterInfoProducer(){

			public FilterInfo getFilterInfo() {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("objectId",project.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("objectId",null,CompareType.EQUALS));
				filter.setMaskString("#0 or #1");
				return filter;
			}

			public void setCurrentCtrlUnit(CtrlUnitInfo cui) {
				
			}

			public void setCurrentMainBizOrgUnit(OrgUnitInfo oui, OrgType ot) {
				
			}});
		
		
		
		// 参数启用，则进度评价人必录
		try {
			// 不控制#控制主项任务#控制专项任务#控制主项和专项任务
			passMustEvaluation = ParamManager.getParamValue(null,
					new ObjectUuidPK(project.getCostCenter().getId()),
					"FDCSH011");
			if ("0".equals(passMustEvaluation)) {
				taskProperties.prmtScheduleAppraisePerson.setRequired(false);
			} else if ("1".equals(passMustEvaluation)) {
				if (getOwner() instanceof MainScheduleEditUI) {
					taskProperties.prmtScheduleAppraisePerson.setRequired(true);
				} else {
					taskProperties.prmtScheduleAppraisePerson
							.setRequired(false);
				}
			} else if ("2".equals(passMustEvaluation)) {
				if (getOwner() instanceof SpecialScheduleEditUI) {
					taskProperties.prmtScheduleAppraisePerson.setRequired(true);
				} else {
					taskProperties.prmtScheduleAppraisePerson
							.setRequired(false);
				}
			} else if ("3".equals(passMustEvaluation)) {
				taskProperties.prmtScheduleAppraisePerson.setRequired(true);
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		taskProperties.prmtTaskCalendar.setEnabled(false);
	}
	
	private void verifyAdjust(int adjustType, DataChangeEvent e) {
		if (!canAdjuest(adjustType)) {
			FDCMsgBox.showInfo(taskProperties, "当前任务与其他任务存在搭接关系，不允许修改当前属性！");
			KDDatePicker dt = (KDDatePicker) e.getSource();
			dt.setValue(e.getOldValue(), false);
			SysUtil.abort();
		}
	}

	protected void initListener() {
		DataChangeListener workDayListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				txtWorkDayChanged_propertyChange(e);
			}
		};

		DataChangeListener planStartListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				verifyAdjust(START, e);
				pkPlanStart_DataChanged(e);
			    
			}
		};
		DataChangeListener planEndListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				verifyAdjust(END, e);
				pkPlanEnd_DataChanged(e);
				
			}
		};

		DataChangeListener calendarListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				cbSchCalendar_propertyChange(e);
			}
		};

		DataChangeListener prmtBizTypeDateListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				prmtBizTypeDateListener(e);
			}
		};
		DataChangeListener prmtCheckNodeDateListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				prmtCheckNodeDateListener(e);
			}
		};

		ItemListener yesListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				txtYes_ItemChanged();
			}
		};

		taskProperties.txtWorkDay.addDataChangeListener(workDayListener);
		taskProperties.pkPlanStart.addDataChangeListener(planStartListener);
		taskProperties.pkPlanEnd.addDataChangeListener(planEndListener);
		taskProperties.prmtTaskCalendar.addDataChangeListener(calendarListener);
		taskProperties.prmtCheckNode.addDataChangeListener(prmtCheckNodeDateListener);
		taskProperties.prmtBizType.addDataChangeListener(prmtBizTypeDateListener);
		taskProperties.txtYes.addItemListener(yesListener);
	}

	protected TaskLinkTypeEnum getCurrentTaskLinkType() {
		TaskLinkTypeEnum linkType = null;
		Object[] tasksInfo = null;
		List preTaskList = getTaskDependencyTask();
		int size = preTaskList.size();
		if (size == 1) {
			return (TaskLinkTypeEnum) ((Object[]) preTaskList.get(0))[2];
		}

		Date date = null;
		FDCScheduleTaskInfo baseTask = null;
		FDCScheduleTaskInfo pretask = null;
		BigDecimal diff = FDCHelper.ZERO;
		Date nextDate = null;
		int baseIndex = -1;
		for (int i = 0; i < size; i++) {
			tasksInfo = (Object[]) preTaskList.get(i);
			pretask = (FDCScheduleTaskInfo) tasksInfo[1];
			linkType = (TaskLinkTypeEnum) tasksInfo[2];
			if (tasksInfo[3] != null) {
				diff = FDCHelper.toBigDecimal(tasksInfo[3]);
			}

			if (linkType.equals(TaskLinkTypeEnum.FINISH_FINISH) || linkType.equals(TaskLinkTypeEnum.FINISH_START)) {
				Date preTaskEndDate = pretask.getEnd();
				nextDate = ScheduleCalendarHelper.getEndDate(preTaskEndDate, diff, pretask.getCalendar());
			} else if (linkType.equals(TaskLinkTypeEnum.START_START)) {
				Date preTaskStartDate = pretask.getStart();
				nextDate = ScheduleCalendarHelper.getEndDate(preTaskStartDate, diff, pretask.getCalendar());
			}
			if (date == null) {
				date = nextDate;
			}
			if (date.compareTo(nextDate) < 0) {
				date = nextDate;
				baseIndex = i;
			}
		}
		if (baseIndex > 0) {
			return (TaskLinkTypeEnum) ((Object[]) preTaskList.get(baseIndex))[2];
		}
		
		return linkType;
	}
	
	/**
	 * 工期改变监听
	 * <p>
	 * 开始不变，计算结束、搭接<br>
	 * 
	 * @param e
	 */
	protected void txtWorkDayChanged_propertyChange(DataChangeEvent e) {
		Date beginDate = getPlanStartDate();
		BigDecimal duration = taskProperties.txtWorkDay.getBigDecimalValue();
		
		// 如果是FS或者是SS调整工期修改结束日期，如果是FF的话则调整开始日期
		TaskLinkTypeEnum linkType = getCurrentTaskLinkType();
		BigDecimal newDuration = duration.subtract(FDCHelper.ONE);
		if (linkType == null) {
			Date endDate = ScheduleCalendarHelper.getEndDate(beginDate, newDuration, schCalendar);
			taskProperties.pkPlanEnd.setValue(endDate);
		} else {
			if (linkType.equals(TaskLinkTypeEnum.FINISH_FINISH)) {
				// TODO 改开始日期倒排
				Date endDate = ScheduleCalendarHelper.getEndDate(getPlanEndDate(),
						FDCHelper.multiply(newDuration, FDCHelper
						.toBigDecimal(-1)), schCalendar);
				taskProperties.pkPlanStart.setValue(endDate);
			} else if (linkType.equals(TaskLinkTypeEnum.FINISH_START) || linkType.equals(TaskLinkTypeEnum.START_START)) {
				// 计算结束
				Date endDate = ScheduleCalendarHelper.getEndDate(beginDate, newDuration, schCalendar);
				taskProperties.pkPlanEnd.setValue(endDate);
			}
		}
		
		
		
		
		

		
		// 计算搭接
		// computePreposing();
	}
	
	
	public boolean canModify(int changeSource) {
		FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
		FDCScheduleTaskDependCollection cols = taskInfo.getDependEntrys();
		// if (cols.size() == 1) {
		// FDCScheduleTaskDependInfo dependency = cols.get(0);
		// return isCanModifyEffectDependant(changeSource, dependency);
		//
		// } else {
			// 只要有一个不适合调整，就所有都不能调整。
            boolean isCanOperator = true;
			for (int i = 0; i < cols.size(); i++) {
				if (!isCanOperator) {
					isCanOperator = false;
				}
			}
			return isCanOperator;
		// }
		//
		// return true;
	}

	private boolean isCanModifyEffectDependant(int changeSource, FDCScheduleTaskDependInfo dependency) {
		if (dependency.getType().equals(TaskLinkTypeEnum.FINISH_START) || dependency.getType().equals(TaskLinkTypeEnum.FINISH_START)) {
			if (changeSource == START) {
				return false;
			} else {
				return true;
			}
		} else if (dependency.getType().equals(TaskLinkTypeEnum.FINISH_FINISH)) {
			if (changeSource == END) {
				return false;
			} else {
				return true;
			}
		}
		return true;
	}

	/**
	 * 
	 * 描述：当用户在属性界面进行调整时，首先校验其是否有搭接关系，如果有搭接关系，则根据以下规则进行调 FS:
	 * 修改工期（结束日期改变，其他不变），修改结束日期（工期改变，其他不变），开始日期不允许修改
	 * FF：修改工期（开始日期改变，其他不变），修改开始日期（工期改变，其他不变），结束日期不允许修改
	 * SS：修改工期（结束日期改变，其他不变），修改结束日期（工期改变，其他不变），开始日期不允许修改
	 * 当存在多个搭接关系时（以计算出来最晚的搭接关系为准，并按照相应的搭接关系约束） 当一个任务t1 即与t2有FS+1的搭接关系
	 * 又与t3有FF+2的搭接关系，则比较t1Fs+1和t2FF+2那个时间较晚，再取对应的搭接关系来做约束。 当一个任务t1
	 * 即与t2有FF+1的搭接关系 又与t3有SS+2的搭接关系，则比较t1 FF+1和t2 SS+2那个时间较晚，再取对应的搭接关系来做约束。
	 * 当一个任务t1 即与t2有FS+1的搭接关系 又与t3有SS+2的搭接关系，则比较t1 FF+1和t2
	 * SS+2那个时间较晚，再取对应的搭接关系来做约束。
	 * 
	 * @return 能不对当任务进行调整
	 * preTaskList数据结构｛TaskDependency,FDCScheduleTask,TaskLinkTypeEnum,Diff｝
	 * @param int adjustType 创建人：yuanjun_lan 创建时间：2012-3-3
	 */
	protected boolean canAdjuest(int adjustType) {
		List preTaskList = getTaskDependencyTask();
		boolean isCanModify = false;
		int size = preTaskList.size();
		TaskDependency dependency = null;
		FDCScheduleTaskInfo pretask = null;
		TaskLinkTypeEnum linkType = null;
		BigDecimal diff = FDCHelper.ZERO;
		Object[] tasksInfo = null;
		/* 如果当前任务只有一个前置任务时直接根据规则计算后返回 */
		if (size == 1) {
			tasksInfo = (Object[]) preTaskList.get(0);
			return isCanModify(adjustType, tasksInfo); 
		}
		/**
		 * 以下的处理是针对当前任务有多个搭接关系时做出的处理 根据任务对应的搭接关系算出对应的时间， FF算出当前任务的结束时间
		 * FS算出当前任务的开始时间 SS算出当前任务的开始时间 然后比较任务的值,取出时间最大的值，然后根据这条前置任务的搭接关系作出计算
		 */
		Date date = null;
		FDCScheduleTaskInfo baseTask = null;
		Date nextDate = null;
		int baseIndex = -1;
		for (int i = 0; i < size; i++) {
             tasksInfo = (Object[]) preTaskList.get(i);
			pretask = (FDCScheduleTaskInfo) tasksInfo[1];
			linkType = (TaskLinkTypeEnum) tasksInfo[2];
			if (tasksInfo[3] != null) {
				diff = FDCHelper.toBigDecimal(tasksInfo[3]);
			}
			
			if(linkType.equals(TaskLinkTypeEnum.FINISH_FINISH) || linkType.equals(TaskLinkTypeEnum.FINISH_START)){
				Date preTaskEndDate = pretask.getEnd();
				nextDate = ScheduleCalendarHelper.getEndDate(preTaskEndDate, diff, pretask.getCalendar());
			} else if (linkType.equals(TaskLinkTypeEnum.START_START)) {
				Date preTaskStartDate = pretask.getStart();
				nextDate = ScheduleCalendarHelper.getEndDate(preTaskStartDate, diff, pretask.getCalendar());
			}
			if (date == null) {
				date = nextDate;
				baseIndex = i;
			}
            if (date.compareTo(nextDate) < 0) {
				date = nextDate;
				baseIndex = i;
			} 
		}
	    if (baseIndex > -1) {
			Object[] resultTask = (Object[]) preTaskList.get(baseIndex);
			return isCanModify(adjustType, resultTask);
		}
		
		return true;
	}

	private boolean isCanModify(int adjustType, Object[] tasksInfo) {
		TaskDependency dependency;
		FDCScheduleTaskInfo pretask;
		TaskLinkTypeEnum linkType;
		dependency = (TaskDependency) tasksInfo[0];
		pretask = (FDCScheduleTaskInfo) tasksInfo[1];
		linkType = (TaskLinkTypeEnum) tasksInfo[2];
		if (linkType.equals(TaskLinkTypeEnum.FINISH_START) || linkType.equals(TaskLinkTypeEnum.START_START)) {
			if (adjustType == START) {
				return false;
			} else {
				return true;
			}
		} else if (linkType.equals(TaskLinkTypeEnum.FINISH_FINISH)) {
			if (adjustType == END) {
				return false;
			} else {
				return true;
			}

		}
		return true;
	}

	/**
	 * 开始日期改变监听事件
	 * <p>
	 * 工期不变，计算结束、搭接
	 * 
	 * @author emanon
	 * @createDate 2011-8-29
	 * @param e
	 * @version EAS7.0
	 * @see
	 */
	protected void pkPlanStart_DataChanged(DataChangeEvent e) {
		
		
		Date beginDate = getPlanStartDate();
		Date newDate = ScheduleCalendarHelper.getClosestWorkDay(beginDate, schCalendar);
		if (beginDate.compareTo(newDate) != 0) {
			beginDate = newDate;
			taskProperties.pkPlanStart.setValue(beginDate);
		}
		
		BigDecimal duration = taskProperties.txtWorkDay.getBigDecimalValue();
		TaskLinkTypeEnum linkType = getCurrentTaskLinkType();
		if (linkType == null) {
			Date endDate = ScheduleCalendarHelper.getEndDate(beginDate, duration.subtract(FDCHelper.ONE), schCalendar);
			taskProperties.pkPlanEnd.setValue(endDate);
		} else {
			if (linkType.equals(TaskLinkTypeEnum.FINISH_FINISH)) {
                 BigDecimal myDuration = ScheduleCalendarHelper.getEffectTimes(beginDate, getPlanEndDate(), schCalendar);
				taskProperties.txtWorkDay.setValue(myDuration);
			}
		}
		
		
		
		// 根据日历计算
		

		// computePreposing();
	}

	/**
	 * 结束日期改变监听事件
	 * <p>
	 * 开始日期不变，计算工期、搭接
	 * 
	 * @author emanon
	 * @param e
	 */
	protected void pkPlanEnd_DataChanged(DataChangeEvent e) {
		Date beginDate = getPlanStartDate();
		Date endDate = getPlanEndDate();
		Date newDate = ScheduleCalendarHelper.getClosestWorkDay(endDate, schCalendar);
		if (endDate.compareTo(newDate) != 0) {
			endDate = newDate;
			taskProperties.pkPlanEnd.setValue(endDate, false);
		}
		TaskLinkTypeEnum linkType = getCurrentTaskLinkType();
		BigDecimal duration = ScheduleCalendarHelper.getEffectTimes(beginDate,
				endDate, schCalendar);
		taskProperties.txtWorkDay.setValue(duration, false);
		if (taskProperties.txtYes.isSelected()) {
			Date checkDate = (Date) taskProperties.pkAccessDate.getValue();
			if (checkDate == null || checkDate.compareTo((Date) e.getOldValue()) == 0) {
				taskProperties.pkAccessDate.setValue(e.getNewValue());
			}
		}else{
			taskProperties.pkAccessDate.setValue(null);
			taskProperties.prmtCheckNode.setValue(null);
		}
		// computePreposing();
	}

	/**
	 * 业务类型改变
	 * <p>
	 * 如果有选择阶段性成功，则成果类别F7可选，否则不可选
	 * 
	 * @param e
	 */
	protected void prmtBizTypeDateListener(DataChangeEvent e) {
		taskProperties.controlState();
		Object[] taskBizTypes = (Object[]) taskProperties.prmtBizType
				.getValue();
		if (taskBizTypes == null) {
			isEnableArchType(false);
			return;
		}
		for (int i = 0; i < taskBizTypes.length; i++) {
			ScheduleBizTypeInfo item = (ScheduleBizTypeInfo) taskBizTypes[i];
			if (null != item && "阶段性成果".equals(item.getName())) {
				// isStageAchievement = true;
				isEnableArchType(true);
				return;
			}
			
		}
		// isStageAchievement = false;
		isEnableArchType(false);
	}
	
	
	/**
	 * 考核节点
	 * <p>
	 * 考核节点变化，把taskname改为考核节点名称
	 * 
	 * @param e
	 */
	protected void prmtCheckNodeDateListener(DataChangeEvent e) {
		if(taskProperties.txtYes.isSelected()){			
			if(e.getNewValue()!=null && e.getNewValue() instanceof CheckNodeInfo){			
				taskProperties.txtTaskName.setText(((CheckNodeInfo)e.getNewValue()).getName().toString());
			}
			if(isChecknodeName(taskProperties.txtTaskName.getText())) {				
				taskProperties.pkAccessDate.setValue(taskProperties.pkPlanEnd.getValue());
			}
		}
	}

	/**
	 * 日历改变，影响有效工期<br>
	 * 未确定前，不直接set到task上面<br>
	 * 
	 * @author emanon
	 * @param e
	 */
	protected void cbSchCalendar_propertyChange(DataChangeEvent e) {
		KDTask clone = new KDTask(selectTask, selectTask.getScheduleTaskInfo());
		Object obj = taskProperties.prmtTaskCalendar.getValue();
		if (obj != null && obj instanceof ScheduleCalendarInfo) {
			ActivityCache cache = ActivityCache.getInstance();
			ScheduleCalendarInfo calendar = cache
					.getCalendar(((ScheduleCalendarInfo) obj).getId()
							.toString());
			this.schCalendar = calendar;
			clone.setCalendar(new ScheduleCalendarImpl(calendar));
		} else {
			clone.setCalendar(new ScheduleCalendarImpl(null));
		}

		Date beginDate = getPlanStartDate();
		Date endDate = getPlanEndDate();
		long duration = clone.getCalendar().getEffectTimes(beginDate, endDate);
		taskProperties.txtWorkDay.setValue(FDCHelper.toBigDecimal(String
				.valueOf(duration)), false);
	}

	/**
	 * 选择是否考核节点改变
	 * <p>
	 * 是考核节点时考核日期必录
	 */
	protected void txtYes_ItemChanged() {
		if (taskProperties.txtYes.isSelected()) {
			taskProperties.contAccessDate.setEnabled(true);
			taskProperties.contCheckNode.setEnabled(true);
			taskProperties.pkAccessDate.setRequired(true);
			taskProperties.prmtCheckNode.setRequired(true);
			
//			//判断是不是集团管控节点，如果是，写入考核日期，否不写
//			if(isGlobalTaskName(taskProperties.txtTaskName.getText())) {				
//				taskProperties.pkAccessDate.setValue(taskProperties.pkPlanEnd.getValue());
//			}
			//判断是不是考核节点，如果是，写入考核日期，否不写
			if(taskProperties.txtTaskName.getText()!=null){
				
				if(isChecknodeName(taskProperties.txtTaskName.getText())) {				
					taskProperties.pkAccessDate.setValue(taskProperties.pkPlanEnd.getValue());
					if(taskProperties.prmtCheckNode.getValue() == null){
						EntityViewInfo view = new EntityViewInfo();
						FilterInfo filter = new FilterInfo();
						filter.appendFilterItem("name", taskProperties.txtTaskName.getText());
						view.setFilter(filter);		
						CheckNodeCollection cols = new CheckNodeCollection();
						try {
							cols = CheckNodeFactory.getRemoteInstance().getCheckNodeCollection(view);
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
						if(cols.size()>0){
							taskProperties.prmtCheckNode.setValue(cols.get(0));
						}
					}
				}
			}
		} else {
			taskProperties.pkAccessDate.setValue(null);
			taskProperties.prmtCheckNode.setValue(null);
			taskProperties.contAccessDate.setEnabled(false);
			taskProperties.contCheckNode.setEnabled(false);
			taskProperties.pkAccessDate.setRequired(false);
			taskProperties.prmtCheckNode.setRequired(false);
		}
	}

	protected boolean isGlobalTaskName(String taskName){
		boolean result = false;
		try {
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql.appendSql(" select fid from T_SCH_GlobalTaskNode "); 
			sql.appendSql(" where FNAME_L2=? ");
			sql.addParam(taskName);
			IRowSet rs = sql.executeQuery();
			if (rs.next()) {
				if (rs.getString(1) == null)
				{				
					result = false;
				}else{
					result = true;
				}
			}else{
				result = false;
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	protected boolean isChecknodeName(String taskName){
		boolean result = false;
		try {
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql.appendSql(" select fid from T_SCH_CheckNode "); 
			sql.appendSql(" where FNAME_L2=? ");
			sql.addParam(taskName);
			IRowSet rs = sql.executeQuery();
			if (rs.next()) {
				if (rs.getString(1) == null)
				{				
					result = false;
				}else{
					result = true;
				}
			}else{
				result = false;
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * 描述：从前置任务列表上取出所有的依赖信息
	 * 
	 * @return 所有前置任务列表 创建人：yuanjun_lan 创建时间：2012-3-3
	 */
	protected List getTaskDependencyTask() {
      List preTaskList = new ArrayList();
      for (int i = 0; i < taskProperties.kDTablePredecessor.getRowCount(); i++) {
			IRow row = taskProperties.kDTablePredecessor.getRow(i);
			TaskDependency dependency = (TaskDependency) row.getUserObject();
			if (dependency == null) {
				continue;
			}
			FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) row.getCell("number").getValue();
			if (taskInfo == null) {
				continue;
			}
			
			TaskLinkTypeEnum type = (TaskLinkTypeEnum) row.getCell("linkType").getValue();
			int diff = Integer.parseInt(row.getCell("diff").getValue().toString());
			preTaskList.add(new Object[] { dependency, taskInfo, type, diff });
			
		}
		return preTaskList;
	}

	/**
	 * 计算所有前置任务的搭接时间
	 * <p>
	 * 用于在修改开始、结束、工期时，重置前置任务分录中的搭接时间<br>
	 * 旭辉要求，所有任务调整，搭接关系不允许改变
	 * 
	 * @author emanon
	 */
	protected void computePreposing() {
		for (int i = 0; i < taskProperties.kDTablePredecessor.getRowCount(); i++) {
			IRow row = taskProperties.kDTablePredecessor.getRow(i);
			TaskDependency dependency = (TaskDependency) row.getUserObject();
			if (dependency == null) {
				continue;
			}
			FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) row.getCell(
					"number").getValue();
			if (taskInfo == null) {
				continue;
			}
			int oldDiff = 0;
			int newDiff = 0;
			Number diff = (Number) row.getCell("diff").getValue();
			if (diff != null) {
				oldDiff = diff.intValue();
			}
			TaskLinkTypeEnum type = (TaskLinkTypeEnum) row.getCell("linkType")
					.getValue();
			if (TaskLinkTypeEnum.FINISH_START.equals(type)) {
				Date preF = taskInfo.getPlanEnd();
				Date nxtS = (Date) taskProperties.pkPlanStart.getValue();
				newDiff = ScheduleCalendarHelper.getEffectTimes(preF, nxtS,
						schCalendar).intValue() - 2;
			} else if (TaskLinkTypeEnum.START_START.equals(type)) {
				Date preS = taskInfo.getPlanStart();
				Date nxtS = (Date) taskProperties.pkPlanStart.getValue();
				newDiff = ScheduleCalendarHelper.getEffectTimes(preS, nxtS,
						schCalendar).intValue() - 1;
			} else if (TaskLinkTypeEnum.FINISH_FINISH.equals(type)) {
				Date preF = taskInfo.getPlanEnd();
				Date nxtF = (Date) taskProperties.pkPlanEnd.getValue();
				newDiff = ScheduleCalendarHelper.getEffectTimes(preF, nxtF,
						schCalendar).intValue();
				if (newDiff < 0) {
					newDiff += 1;
				} else {
					newDiff -= 1;
				}
			}

			if (oldDiff != newDiff) {
				row.getCell("diff").setValue(new Integer(newDiff));
				dependency.setDifference(newDiff);
			}
		}
	}

	/**
	 * @description
	 * @author 杜红明
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	public void commit() {

		FDCScheduleTaskInfo fdcScheduleTask = (FDCScheduleTaskInfo) selectTask
				.getScheduleTaskInfo();
			try {
				commitBaseTask(fdcScheduleTask);
				commitBasePanel(fdcScheduleTask);
				commitAdvancedTask(fdcScheduleTask);
				commitAdvancedPanel(fdcScheduleTask);
			} catch (EASBizException e) {
				/* TODO 自动生成 catch 块 */
				e.printStackTrace();
			} catch (BOSException e) {
				/* TODO 自动生成 catch 块 */
				e.printStackTrace();
			} catch (CustomColumnsException e) {
				/* TODO 自动生成 catch 块 */
				e.printStackTrace();
			}
		
		
	}

	/**
	 * 
	 * @description 提交高级面板信息
	 * @author 杜红明
	 * @createDate 2011-8-30
	 * @param fdcScheduleTask
	 * @throws CustomColumnsException void
	 * @version EAS7.0
	 * @see
	 */
	private void commitAdvancedPanel(FDCScheduleTaskInfo fdcScheduleTask)
			throws CustomColumnsException {
		// "实际完工日期
		fdcScheduleTask.setActualEndDate((Date) taskProperties.pkRealityEnd
				.getValue());
		// "实际开工日期
		fdcScheduleTask.setActualStartDate((Date) taskProperties.pkRealityStart
				.getValue());
		
		fdcScheduleTask.getHelpPersonEntry().clear();
		if(taskProperties.prmtHelpPerson.getValue() != null){
			Object value[] = (Object[])taskProperties.prmtHelpPerson.getValue();
			for(int i = 0; i < value.length; i++){
				if(value[i] != null && (value[i] instanceof PersonInfo)){
					HelpPersonEntryInfo entry = new HelpPersonEntryInfo();
					entry.setPerson((PersonInfo)value[i]);
					fdcScheduleTask.getHelpPersonEntry().add(entry);
				}
			}
		}
		fdcScheduleTask.getHelpDeptEntry().clear();
		if(taskProperties.prmtHelpDep.getValue() != null){
			Object value[] = (Object[])taskProperties.prmtHelpDep.getValue();
			for(int i = 0; i < value.length; i++){
				if(value[i] != null){
					HelpDeptEntryInfo entry = new HelpDeptEntryInfo();
					if(value[i] instanceof FullOrgUnitInfo){
						entry.setDept((FullOrgUnitInfo)value[i]);
					}
					fdcScheduleTask.getHelpDeptEntry().add(entry);
				}
			}
		}
//		// 协助人
//		fdcScheduleTask
//				.setHelpPerson((PersonInfo) taskProperties.prmtHelpPerson
//						.getValue());
//		// 协助部门
//		// TODO 过滤为行政组织
//		if (taskProperties.prmtHelpDep.getValue() != null
//				&& taskProperties.prmtHelpDep.getValue() instanceof AdminOrgUnitInfo) {
//			fdcScheduleTask
//					.setHelpDept(((AdminOrgUnitInfo) taskProperties.prmtHelpDep
//							.getValue()).castToFullOrgUnitInfo());
//		} else {
//			fdcScheduleTask.setHelpDept(null);
//		}
		// 风险负责人
		fdcScheduleTask
				.setRiskResPerson((PersonInfo) taskProperties.prmtRiskChargePerson
						.getValue());
		// 任务日历
		Object obj = taskProperties.prmtTaskCalendar.getValue();
		if (obj != null && obj instanceof ScheduleCalendarInfo) {
			ScheduleCalendarInfo cal = (ScheduleCalendarInfo) obj;
			fdcScheduleTask.setCalendar(cal);
			selectTask.setCalendar(new ScheduleCalendarImpl(cal));
		} else {
			fdcScheduleTask.setCalendar(null);
			selectTask.setCalendar(ScheduleCalendarImpl.DEFAULT_CALENDAR);
		}
		// fdcScheduleTask.setCalendar((ScheduleCalendarInfo)
		// taskProperties.prmtTaskCalendar.getValue());
		// 累计完成工程量
		fdcScheduleTask.setWorkLoad(FDCHelper
				.toBigDecimal(taskProperties.txtAppendEndQuantity
						.getNumberValue()));

	}

	/**
	 * 
	 * @description 提交高级面板信息到甘特图中
	 * @author 杜红明
	 * @createDate 2011-8-31
	 * @param fdcScheduleTask
	 * @throws CustomColumnsException void
	 * @version EAS7.0
	 * @see
	 */
	private void commitAdvancedTask(FDCScheduleTaskInfo fdcScheduleTask)
			throws CustomColumnsException {
		// "实际完工日期
		// selectTask.getCustomValues().setValue(GanttTreeTableModelExt.
		// strColActualEndDate, taskProperties.pkRealityEnd.getValue());
		// color
		if (!taskProperties.cbColour.getColor().equals(selectTask.getColor())) {
			selectTask.setColor(taskProperties.cbColour.getColor());
		}
		// shape
		if ((selectTask.getShape() == null && taskProperties.cbShape
				.getSelectedIndex() != 0)
				|| (selectTask.getShape() != null && (selectTask.getShape()
						.equals(taskProperties.cbShape.getSelectedItem())))) {
			selectTask.setShape((ShapePaint) taskProperties.cbShape
					.getSelectedItem());
		}
		if (selectTask.getShape() != null) {
			selectTask.setShape(new ShapePaint(selectTask.getShape(),
					Color.white, selectTask.getColor()));
		}
		
		if(taskProperties.prmtHelpDep.getValue() != null){
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColHelpDep, taskProperties.prmtHelpDep.getText());
		}else{
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColHelpDep, null);
		}
		if(taskProperties.prmtHelpPerson.getValue() != null){
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColHelpPerson, taskProperties.prmtHelpPerson.getText());
		}else{
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColHelpPerson, null);
		}
		
		
		// 完成进度
		if (FDCHelper.toBigDecimal(
				taskProperties.txtEndSchedule.getNumberValue()).intValue() != selectTask
				.getCompletionPercentage()) {
			TaskMutator mutator = selectTask.createMutator();
			mutator.setCompletionPercentage(FDCHelper.toBigDecimal(
					taskProperties.txtEndSchedule.getNumberValue()).intValue());
			mutator.commit();
		}

	}

	/**
	 * 
	 * @description 提交基础面板信息
	 * @author 杜红明
	 * @createDate 2011-8-30
	 * @param fdcScheduleTask
	 * @param schedule
	 * @throws CustomColumnsException void
	 * @version EAS7.0
	 * @see
	 */
	private void commitBasePanel(FDCScheduleTaskInfo fdcScheduleTask)
			throws CustomColumnsException {
		//所属专项
		if(taskProperties.prmtBelongSpecial.getValue()!= null){
			fdcScheduleTask.setBelongToSpecial((ProjectSpecialInfo) taskProperties.prmtBelongSpecial.getValue());
		}else{
			fdcScheduleTask.setBelongToSpecial(null);
		}
		
		/** 任务名称 **/
		String taskname = (String) getVerifyInputData(
				taskProperties.txtTaskName.getText(), "任务名称");
		if (taskname.length() > 100) {
			FDCMsgBox.showWarning(taskProperties, "任务名称字符不能大于100!");
			SysUtil.abort();
		}
		fdcScheduleTask.setName(taskname);
		// 是否考核节点
		if (taskProperties.txtYes.isSelected()) {
			if (taskProperties.pkAccessDate.isRequired()){				
				if (FDCHelper.isEmpty(taskProperties.pkAccessDate.getValue())) {
//					FDCMsgBox.showWarning(taskProperties, "考核日期不能为空！提示：任务名称为集团管控节点考核日期会自动赋值。");
					FDCMsgBox.showWarning(taskProperties, "考核日期不能为空！提示：任务名称为考核节点，则考核日期会自动赋值。");
					SysUtil.abort();
				}
				if (FDCHelper.isEmpty(taskProperties.prmtCheckNode.getValue())) {
					FDCMsgBox.showWarning(taskProperties, "考核节点不能为空！提示：任务名称为考核节点，则考核日期会自动赋值。");
					SysUtil.abort();
				}
			}
//				fdcScheduleTask.setCheckDate((Date) getVerifyInputData(taskProperties.pkAccessDate.getValue(), "考核日期"));
			fdcScheduleTask.setIsCheckNode(true);
			//TODO 不用保存么。。。
			fdcScheduleTask.setCheckDate((Date) taskProperties.pkAccessDate.getValue());
			fdcScheduleTask.setCheckNode((CheckNodeInfo)taskProperties.prmtCheckNode.getValue());
			// 考核日期
		} else {
			fdcScheduleTask.setIsCheckNode(false);
			// 考核日期 清空
			fdcScheduleTask.setCheckDate(null);
//			fdcScheduleTask.setCheckDate((Date) taskProperties.pkAccessDate.getValue());
		}
		// 任务类别
		fdcScheduleTask.setTaskType((RESchTaskTypeEnum) getVerifyInputData(
				taskProperties.cbTaskType.getSelectedItem(), "任务类别"));
		// 任务名称
		selectTask.getCustomValues().setValue(
				GanttTreeTableModelExt.strColTaskName,
				taskProperties.txtTaskName.getText());
		// 工期(有效工期)
		fdcScheduleTask.setEffectTimes(taskProperties.txtWorkDay
				.getBigDecimalValue());
		// 质量评价人
		fdcScheduleTask
				.setQualityEvaluatePerson((PersonInfo) taskProperties.prmtQualityAppraisePerson
						.getValue());
		// 业务类型
		processBizType(fdcScheduleTask);

		// 任务指引
		fdcScheduleTask
				.setStandardTask((StandardTaskGuideInfo) taskProperties.prmtTaskGuide
						.getValue());
		// 备注
		fdcScheduleTask.setDescription(taskProperties.txtDesciption.getText());

		// 是否关键任务|里程碑任务

		// 责任人
		fdcScheduleTask
				.setAdminPerson((PersonInfo) taskProperties.prmtDutyPerson
						.getValue());
		// 责任部门-- 选择明细行政组织
		// TODO
		// 由于历史数据，可能会导致中断。
		AdminOrgUnitInfo dutyDep = null;
		if (taskProperties.prmtDutyDep.getValue() instanceof AdminOrgUnitInfo)
			dutyDep = (AdminOrgUnitInfo) taskProperties.prmtDutyDep.getValue();
		if (dutyDep != null)
			fdcScheduleTask.setAdminDept(dutyDep.castToFullOrgUnitInfo());
		else
			fdcScheduleTask.setAdminDept((FullOrgUnitInfo) taskProperties.prmtDutyDep.getValue());
		// 进度评价人
		fdcScheduleTask
				.setPlanEvaluatePerson((PersonInfo) taskProperties.prmtScheduleAppraisePerson
						.getValue());
		// 成果类别
		if (fdcScheduleTask.getSchedule().getProjectSpecial() == null) {
			if (isStageAchievement || (taskProperties.prmtAchievementType.isRequired() && taskProperties.prmtAchievementType.isEnabled())) {
				AchievementTypeInfo typeInfo = (AchievementTypeInfo) getVerifyInputData(taskProperties.prmtAchievementType.getValue(),
						"成果类别");
				fdcScheduleTask.setAchievementType(typeInfo);
			} else {
				fdcScheduleTask.setAchievementType(null);
			}
		}
	}

	private void processBizType(FDCScheduleTaskInfo fdcScheduleTask) {
		if (taskProperties.prmtBizType.getValue() == null) {
			if (fdcScheduleTask.getBizType() != null)
				fdcScheduleTask.getBizType().clear();
			return;
		}
		Object[] bizTypes = (Object[]) taskProperties.prmtBizType.getValue();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("bizType.id");
		view.getSelector().add("bizType.name");
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("parent.id", fdcScheduleTask.getId().toString());
		String[] arrays = new String[bizTypes.length];
		ScheduleTaskBizTypeCollection newCol = new ScheduleTaskBizTypeCollection();
		fdcScheduleTask.getBizType().clear();
		for (int i = 0; i < bizTypes.length; i++) {
			ScheduleBizTypeInfo bizType = (ScheduleBizTypeInfo) bizTypes[i];
			arrays[i] = bizType.getId().toString();
			if(arrays[i].equals(FDCScheduleConstant.ACHIEVEMANAGER)){
				if(taskProperties.prmtAchievementType.getValue() == null){
					continue;
				}
			}
			ScheduleTaskBizTypeInfo taskBizType = new ScheduleTaskBizTypeInfo();
			taskBizType.setBizType(bizType);
			taskBizType.setParent(fdcScheduleTask);
			fdcScheduleTask.getBizType().add(taskBizType);
			newCol.add(taskBizType);
		}
		filter.getFilterItems().add(
				new FilterItemInfo("bizType.id", new HashSet(Arrays
						.asList(arrays)), CompareType.INCLUDE));
		try {
			view.setFilter(filter);
			ScheduleTaskBizTypeCollection oldCol = ScheduleTaskBizTypeFactory
					.getRemoteInstance().getScheduleTaskBizTypeCollection(view);
			fdcScheduleTask.getBizType().clear();
			Map oldMap = new HashMap();
			Map newMap = new HashMap();
			for (int i = 0; i < oldCol.size(); i++) {
				ScheduleBizTypeInfo oldBizType = oldCol.get(i).getBizType();
				oldMap.put(oldBizType.getName(), oldCol.get(i));
			}
			for (int i = 0; i < newCol.size(); i++) {
				ScheduleBizTypeInfo newBizType = newCol.get(i).getBizType();
				newMap.put(newBizType.getName(), newCol.get(i));
			}

			for (Iterator it = oldMap.keySet().iterator(); it.hasNext();) {
				String name = (String) it.next();
				if (newMap.containsKey(name)) {
					fdcScheduleTask.getBizType().add(
							(ScheduleTaskBizTypeInfo) oldMap.get(name));
				}
			}

			for (Iterator it = newMap.keySet().iterator(); it.hasNext();) {
				String name = (String) it.next();
				if (!oldMap.containsKey(name)) {
					fdcScheduleTask.getBizType().add(
							(ScheduleTaskBizTypeInfo) newMap.get(name));
				}
			}

		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	private void commitBaseTask(FDCScheduleTaskInfo fdcScheduleTask)
			throws CustomColumnsException, EASBizException, BOSException {
		// 任务名称
		String taskname = (String) getVerifyInputData(
				taskProperties.txtTaskName.getText(), "任务名称");
		// String bizType = (String)
		// getVerifyInputData(taskProperties.prmtBizType.getText(), "业务类型");
		if (FDCHelper.isEmpty(taskname)) {
			FDCMsgBox.showWarning(taskProperties, "任务名称不能为空!");
			taskProperties.txtTaskName.requestFocus();
			SysUtil.abort();
		} else if (taskname.length() > 100) {
			FDCMsgBox.showWarning(taskProperties, "任务名称字符长度不能大于100!");
			SysUtil.abort();
		}
		selectTask.getCustomValues().setValue(
				GanttTreeTableModelExt.strColTaskName, taskname);
		selectTask.setName(taskname);
		// selectTask.getCustomValues().setValue(GanttTreeTableModelExt.
		// strColBizType, bizType);
		Date start = (Date) getVerifyInputData(getPlanStartDate(), "计划开始日期");
		Date end = (Date) getVerifyInputData(getPlanEndDate(), "计划完成日期");
		if (!FDCHelper.isEmpty(start) && !FDCHelper.isEmpty(end)) {
			if (getPlanStartDate().compareTo(getPlanEndDate()) > 0) {
				FDCMsgBox.showWarning(taskProperties, "任务开始日期不能大于任务完成日期");
				SysUtil.abort();
			}
		}
		// 进度评价人
		if (taskProperties.prmtScheduleAppraisePerson.getValue() == null) {
			boolean isNeedAppr = false;
			if ("3".equals(passMustEvaluation)) {
				isNeedAppr = true;
			} else if ("1".equals(passMustEvaluation)
					&& getOwner() instanceof MainScheduleEditUI) {
				isNeedAppr = true;
			} else if ("2".equals(passMustEvaluation)
					&& getOwner() instanceof SpecialScheduleEditUI) {
				isNeedAppr = true;
			}
			if (isNeedAppr) {
				FDCMsgBox.showWarning(taskProperties,
						"参数‘通过任务评价控制任务完成状态及其控制策略’启用，进度评价人不能为空！");
				SysUtil.abort();
			}
		}
		
		// 计划完成日期--不能超出关联主项任务的时间范围, 该校验要比mutator.commit()前调用
		String FDCSH010_param_value = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(project.getCostCenter().getId()),
				"FDCSH010");
		// 计划完成日期
		if (taskProperties instanceof FDCSpecialScheduleTaskPropertiesUI) {
			// 当前是专项任务，要校验是不是结束日期大于主项计划了
			FDCScheduleTaskInfo main = (FDCScheduleTaskInfo) ((FDCSpecialScheduleTaskPropertiesUI) taskProperties).prmtRelationMainTask
					.getValue();
			if (main != null) {
				if ("1".equals(FDCSH010_param_value)) {
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("srcID", main.getSrcID()));
					filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
					filter.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial is null"));
					filter.getFilterItems().add(new FilterItemInfo("end", end, CompareType.LESS));
					filter.getFilterItems().add(new FilterItemInfo("start", start, CompareType.GREATER));
					filter.setMaskString("#0 and #1 and #2 and (#3 or #4)");
					if (FDCScheduleTaskFactory.getRemoteInstance().exists(filter)) {
						FDCMsgBox.showWarning("若主专项有关联时，专项任务的计划开始必须大于主项的计划开始日期，且计划完成小于主项计划完成日期。");
						SysUtil.abort();
					}
				}
				FDCScheduleTaskInfo mTask = (FDCScheduleTaskInfo) main.clone();
				mTask.setId(BOSUuid.read(mTask.getSrcID()));
				((FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo()).setDependMainTaskID(mTask);
			}
		} else {
			// 当前是主项任务，要校验他的专项们结束日期是不是大于本任务
			StringBuffer str = new StringBuffer("详细信息如下：\n");
			str.append("专项任务名称");
			str.append("\t\t\t");
			str.append("主项任务名称\n");

			if ("1".equals(FDCSH010_param_value)) {
				int err = 0;
				for (int i = 0; i < taskProperties.tblSpecially.getRowCount(); i++) {
					IRow row = taskProperties.tblSpecially.getRow(i);
					FDCScheduleTaskInfo spTask = (FDCScheduleTaskInfo) row.getCell("name").getValue();
					if (spTask != null) {
						if (!(DateTimeUtils.dateDiff(start, spTask.getStart()) >= 0 && DateTimeUtils.dateDiff(end, spTask.getEnd()) <= 0)) {
							str.append(spTask.getSchedule().getProjectSpecial().getName() + "->" + spTask.getName() + "\t\t" + selectTask.getName()
									+ "\n");
							err++;
						}
					}
				}

				if (err > 0) {
					FDCMsgBox.showDetailAndOK(taskProperties, "若主专项有关联时，专项任务的计划开始必须大于主项的计划开始日期，且计划完成小于主项计划完成日期。请点击详细信息查看。", str.toString(),
							2);
					SysUtil.abort();
				}
			}
		}
		
		if (!start.equals(fdcScheduleTask.getStart())) {
			TaskMutator mutator = selectTask.createMutator();
			mutator.setStart(ScheduleParserHelper.parseDateToGanttCalendar(start));
			mutator.setDuration(selectTask.getManager().createLength(
					FDCHelper.toBigDecimal(taskProperties.txtWorkDay.getBigDecimalValue()).intValue()));
			mutator.commit();
		}
		
		if (!end.equals(fdcScheduleTask.getEnd())) {
			TaskMutator mutator = selectTask.createMutator();
			mutator.setEnd(ScheduleParserHelper.parseDateToGanttCalendar(end));
			mutator.commit();
		}
		// 责任部门-- 选择明细行政组织
		selectTask.getCustomValues().setValue(
				GanttTreeTableModelExt.strColAdminDept,
				taskProperties.prmtDutyDep.getValue());
		// 责任人
		selectTask.getCustomValues().setValue(
				GanttTreeTableModelExt.stradminPerson,
				taskProperties.prmtDutyPerson.getValue());

		// 是否关键任务|里程碑任务
		RESchTaskTypeEnum taskTypeEnum = ((RESchTaskTypeEnum) taskProperties.cbTaskType
				.getSelectedItem());
		selectTask.getCustomValues().setValue(
				GanttTreeTableModelExt.strColTaskType, taskTypeEnum);
		// 业务类型,专项不用处理业务类型
		if (!(taskProperties instanceof FDCSpecialScheduleTaskPropertiesUI)) {
			if (taskProperties.contBizType.isVisible() && taskProperties.prmtBizType.getValue() != null) {
				Object[] values = (Object[]) taskProperties.prmtBizType.getValue();
				ScheduleTaskBizTypeCollection cols = new ScheduleTaskBizTypeCollection();
				ScheduleTaskBizTypeInfo info = null;
				for (int i = 0; i < values.length; i++) {
					info = new ScheduleTaskBizTypeInfo();
					info.setBizType((ScheduleBizTypeInfo) values[i]);
					info.setParent(fdcScheduleTask);
					cols.add(info);
				}
				selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColBizType, cols);
			} else {
				selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColBizType, null);
			}
			
			if (taskProperties.prmtAchievementType.getValue() != null) {
				selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColAchievementType,
						taskProperties.prmtAchievementType.getValue());
			} else {
				selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColAchievementType, null);
			}
		}
		
		if(taskProperties.pkAccessDate.getValue()!=null){
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColCheckDate, taskProperties.pkAccessDate.getValue());
		}
		
		//：“成果类别”“任务指引”“进度评价人”“质量评价人”“协助人”“协助部门”等字段的值均不显示；
		
		
		//其他字段修改也要同步到GanttProject
		
		if(taskProperties.prmtScheduleAppraisePerson.getValue() != null){
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColPlanEvaPerson, taskProperties.prmtScheduleAppraisePerson.getValue());
		} else {
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColPlanEvaPerson, null);
		}
		if(taskProperties.prmtQualityAppraisePerson.getValue() != null){
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColQualityEvaPerson, taskProperties.prmtQualityAppraisePerson.getValue());
		} else {
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColQualityEvaPerson, null);
		}
		
		if(taskProperties.prmtTaskGuide.getValue() != null){
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColTaskGuilde, taskProperties.prmtTaskGuide.getValue());
		} else {
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColTaskGuilde, null);
		}
		
		
	}

	/**
	 * @description 加载主面板(基础面板、高级面板)信息
	 * @author 杜红明
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	public void load() throws EASBizException, BOSException {
		KDTask selectTask = this.selectTask;
		FDCScheduleTaskInfo fdcScheduleTask = (FDCScheduleTaskInfo) selectTask
				.getScheduleTaskInfo();
		loadBasePanel(fdcScheduleTask);
		loadAdvancedPanel(fdcScheduleTask);
		initCBCalendar(fdcScheduleTask);
		initListener();
	}

	private void initCBCalendar(FDCScheduleTaskInfo taskInfo) {
		ScheduleCalendarInfo cal = taskInfo.getCalendar();
		taskProperties.prmtTaskCalendar.setValue(cal);
	}

	/**
	 * 
	 * @description 加载基础面本信息
	 * @author 杜红明
	 * @createDate 2011-8-30
	 * @param fdcScheudleTask
	 * @param baseInfo
	 *            void
	 * @version EAS7.0
	 * @see
	 */
	private void loadBasePanel(FDCScheduleTaskInfo fdcScheudleTask) {
		//所属专项
		taskProperties.prmtBelongSpecial.setValue(fdcScheudleTask.getBelongToSpecial());
		// 名称
		taskProperties.txtTaskName.setText(fdcScheudleTask.getName());
		// 编码
		taskProperties.txtNumber.setText(fdcScheudleTask.getLongNumber().replace('!', '.'));
		// 备注
		taskProperties.txtDesciption.setText(fdcScheudleTask.getDescription());
		// 任务类别
		taskProperties.cbTaskType.addItems(EnumUtils.getEnumList(
				"com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum").toArray());
		if (fdcScheudleTask.getSchedule().getProjectSpecial() != null) {
			taskProperties.cbTaskType.removeItem(RESchTaskTypeEnum.MILESTONE);
		}
		if (FDCHelper.isEmpty(fdcScheudleTask.getTaskType())) {
			fdcScheudleTask.setTaskType(RESchTaskTypeEnum.COMMONLY);
		} else {
			taskProperties.cbTaskType.setSelectedItem(fdcScheudleTask
					.getTaskType());
		}
		changeTaskNameByRESchTaskType();
		// 任务指引
		taskProperties.prmtTaskGuide
				.setValue(fdcScheudleTask.getStandardTask());
		// 业务类型
		// 当“业务类型”为“阶段性成果”时，显示“成果类别”字段，否则不显示该字段。
		ScheduleTaskBizTypeCollection taskBizTypes = fdcScheudleTask.getBizType();
		 Object[] objs = taskBizTypes == null ? null : new Object[taskBizTypes.size()];
		taskProperties.prmtAchievementType.setEnabled(false);
		for (int i = 0; taskBizTypes != null && i < taskBizTypes.size(); i++) {
			ScheduleBizTypeInfo bizType = taskBizTypes.get(i).getBizType();
			objs[i] = bizType;
			if (!FDCHelper.isEmpty(bizType) && bizType.getName().equals("阶段性成果")) {
				taskProperties.prmtAchievementType.setEnabled(true);
			}
			// if (null != taskBizTypes.get(i).getBizType().getId()
			// && (null == taskBizTypes.get(i).getBizType().getName() || ""
			// .equals(taskBizTypes.get(i).getBizType().getName()))) {
			// try {
			// ScheduleBizTypeInfo scheduleBizTypeInfo = ScheduleBizTypeFactory
			// .getRemoteInstance().getScheduleBizTypeInfo(
			// new ObjectUuidPK(taskBizTypes.get(i)
			// .getBizType().getId()));
			// objs[i] = scheduleBizTypeInfo;
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
		}
		// taskProperties.prmtBizType.setDisplayFormat("$name$");
		// taskProperties.prmtBizType.setValue(fdcScheudleTask.getBizType());
		taskProperties.prmtBizType.setValue(objs);
		// 开工日期
		taskProperties.pkPlanStart.setValue(fdcScheudleTask.getStart());
		// 完工时间
		taskProperties.pkPlanEnd.setValue(fdcScheudleTask.getEnd());
		// 责任人(职员)
		taskProperties.prmtDutyPerson
				.setValue(fdcScheudleTask.getAdminPerson());
		// 责任部门
		// if (FDCHelper.isEmpty(fdcScheudleTask.getAdminDept())) {
		// taskProperties.prmtDutyDep.setValue(SysContext.getSysContext().
		// getCurrentOrgUnit());
		// } else {
		taskProperties.prmtDutyDep.setValue(fdcScheudleTask.getAdminDept());
		// }
		// 有效工期(自然工期)
		if (FDCHelper.isEmpty(fdcScheudleTask.getEffectTimes())) {
			taskProperties.txtWorkDay.setValue(new Integer(0));
		} else {
			taskProperties.txtWorkDay.setValue(new Integer(fdcScheudleTask
					.getEffectTimes().intValue()));
		}
		// 是否考核节点property
		if (fdcScheudleTask.isIsCheckNode()) {
			taskProperties.txtYes.setSelected(true);
			taskProperties.txtNo.setSelected(false);
			taskProperties.contAccessDate.setEnabled(true);
			taskProperties.contCheckNode.setEnabled(true);
			taskProperties.pkAccessDate.setRequired(true);
			taskProperties.prmtCheckNode.setRequired(true);
			// 考核日期
			taskProperties.pkAccessDate.setValue(fdcScheudleTask.getCheckDate());
			// 考核节点
			if(fdcScheudleTask.getCheckNode()!=null){
				try {
					taskProperties.prmtCheckNode.setValue(CheckNodeFactory.getRemoteInstance().getCheckNodeInfo(new ObjectUuidPK(fdcScheudleTask.getCheckNode().getId())));
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		} else {
			taskProperties.txtYes.setSelected(false);
			taskProperties.txtNo.setSelected(true);
			taskProperties.contAccessDate.setEnabled(false);
			taskProperties.contCheckNode.setEnabled(false);
			taskProperties.pkAccessDate.setRequired(false);
			taskProperties.prmtCheckNode.setRequired(false);
			taskProperties.pkAccessDate.setValue(null);
			taskProperties.prmtCheckNode.setValue(null);
		}
		// 质量评价人
		taskProperties.prmtQualityAppraisePerson.setValue(fdcScheudleTask
				.getQualityEvaluatePerson());
		// 进度评价人
		taskProperties.prmtScheduleAppraisePerson.setValue(fdcScheudleTask
				.getPlanEvaluatePerson());
		// 成果类别
		taskProperties.prmtAchievementType.setValue(fdcScheudleTask
				.getAchievementType());
	}

	private void isEnableArchType(boolean isVisible) {
		isStageAchievement = isVisible;
		if (isVisible) {
			taskProperties.prmtAchievementType.setEnabled(true);
		} else {
			taskProperties.prmtAchievementType.setEnabled(false);
			taskProperties.prmtAchievementType.setValue(null);
		}
	}

	/**
	 * 
	 * @description 加载高级面板信息
	 * @author 杜红明
	 * @createDate 2011-8-16
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void loadAdvancedPanel(FDCScheduleTaskInfo fdcScheudleTask) {
		// 实际开始时间
		taskProperties.pkRealityStart.setValue(fdcScheudleTask
				.getActualStartDate());
		// 实际完工日期
		taskProperties.pkRealityEnd
				.setValue(fdcScheudleTask.getActualEndDate());
		// 任务日历
		// TODO 选择“日历设置”中的日历，根据选择的日历，自动更新“工期“、“计划开始时间”、“计划结束时间”
		if (FDCHelper.isEmpty(fdcScheudleTask.getCalendar())) {
			taskProperties.prmtTaskCalendar.setValue(fdcScheudleTask
					.getSchedule().getCalendar());
		} else {
			taskProperties.prmtTaskCalendar.setValue(fdcScheudleTask
					.getCalendar());
		}
		// 形状
		// gantt project's shape Renderer

		taskProperties.cbShape.removeAllItems();
		taskProperties.cbShape.setRenderer(new PaintCellRenderer());
		taskProperties.cbShape.addItems(ShapeConstants.PATTERN_LIST);
		if (selectTask.shapeDefined()) {
			for (int j = 0; j < ShapeConstants.PATTERN_LIST.length; j++) {
				if (selectTask.getShape()
						.equals(ShapeConstants.PATTERN_LIST[j])) {
					taskProperties.cbShape.setSelectedIndex(j);
					break;
				}
			}
		}
		// 颜色
		taskProperties.cbColour.setColor(selectTask.getColor());
		// 优先级
		taskProperties.cbPriorityLevel.removeAllItems();
		taskProperties.cbPriorityLevel.addItem(language.getText("low"));
		taskProperties.cbPriorityLevel.addItem(language.getText("normal"));
		taskProperties.cbPriorityLevel.addItem(language.getText("hight"));
		taskProperties.cbPriorityLevel.setSelectedIndex(fdcScheudleTask.getPriority());

		HelpPersonEntryCollection pcol= fdcScheudleTask.getHelpPersonEntry();
		Object helpPerson[] = new Object[pcol.size()];
		for(int i = 0; i < pcol.size(); i++){
			HelpPersonEntryInfo person=pcol.get(i);
			helpPerson[i]=person.getPerson();
		}
		HelpDeptEntryCollection dcol=fdcScheudleTask.getHelpDeptEntry();
		Object helpDept[] = new Object[dcol.size()];
		for(int i = 0; i < dcol.size(); i++){
			HelpDeptEntryInfo orgUnit=dcol.get(i);
			helpDept[i]=orgUnit.getDept();
		}
		
		// 协助人
		taskProperties.prmtHelpPerson.setValue(helpPerson);
		// 协助部门
		taskProperties.prmtHelpDep.setValue(helpDept);
		// 完成情况(完成进度)
		if (FDCHelper.isEmpty(fdcScheudleTask.getComplete())) {
			fdcScheudleTask.setComplete(new BigDecimal(0));
		}
		taskProperties.txtEndSchedule.setValue(fdcScheudleTask.getComplete());
		// 累计完成工作量
		if (FDCHelper.isEmpty(fdcScheudleTask.getWorkLoad())) {
			taskProperties.txtAppendEndQuantity.setValue(new BigDecimal(0));
		} else {
			taskProperties.txtAppendEndQuantity.setValue(fdcScheudleTask
					.getWorkLoad());
		}
		// 风险负责人
		taskProperties.prmtRiskChargePerson.setValue(fdcScheudleTask
				.getRiskResPerson());
	}

	/**
	 * @description 验证数据
	 * @author 杜红明
	 * @createDate 2011-8-30 void
	 * @version EAS7.0
	 * @see
	 */

	protected Object getVerifyInputData(Object obj, String mesg) {
		if (FDCHelper.isEmpty(obj)) {
			FDCMsgBox.showWarning(taskProperties, mesg + "不能为空！");
			SysUtil.abort();
		}
		return obj;
	}

	/**
	 * 
	 * @description 任务名称随着任务类型改变
	 * @author 杜红明
	 * @createDate 2011-8-30
	 * @version EAS7.0
	 * @see
	 */
	private void cbTaskType_itemStateChanged(java.awt.event.ItemEvent e) {
		RESchTaskTypeEnum taskTypeEnum = null;
		if (e.getStateChange() == 1) {
			taskTypeEnum = (RESchTaskTypeEnum) e.getItem();
		} else {
			return;
		}
		if (taskTypeEnum.equals(RESchTaskTypeEnum.COMMONLY)) {
			taskProperties.txtTaskName.setCustomForegroundColor(Color.BLACK);
			return;
		}
		if (taskTypeEnum.equals(RESchTaskTypeEnum.KEY)) {
			taskProperties.txtTaskName.setCustomForegroundColor(Color.BLUE);
			return;
		}
		if (taskTypeEnum.equals(RESchTaskTypeEnum.MILESTONE)) {
			taskProperties.txtTaskName.setCustomForegroundColor(Color.RED);
			return;
		}
	}

	private void changeTaskNameByRESchTaskType() {
		taskProperties.cbTaskType
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent e) {
						try {
							cbTaskType_itemStateChanged(e);
						} catch (Exception exc) {
							taskProperties.handUIException(exc);
						} finally {
						}
					}
				});

	}

	/**
	 * @description
	 * @author 杜红明
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */
	public void setEditUIStatus() {
		// TODO Auto-generated method stub

	}

	/**
	 * @description
	 * @author 杜红明
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */
	public void setExecutingUIStatus() {
		// 主项和专项任务执行时，把所有控件给锁了
		if (getOwner() instanceof MainScheduleExecuteUI
				|| getOwner() instanceof SpecialScheduleExecuteUI) {
			lockControl();
		}
	}

	private Object getOwner() {
		Object owner = taskProperties.getUIContext().get("Owner");
		// 多处用到，避免总是判断空
		owner = owner == null ? "" : owner;
		return owner;
	}

	private void lockControl() {
		KDLabelContainer cont = null;

		for (int i = 0; i < taskProperties.pnlBaseInfo.getComponentCount(); i++) {

			if (taskProperties.pnlBaseInfo.getComponent(i) instanceof KDLabelContainer) {
				cont = (KDLabelContainer) taskProperties.pnlBaseInfo
						.getComponent(i);
				cont.setEnabled(false);
			}
		}

		for (int i = 0; i < taskProperties.pnlAdvanced.getComponentCount(); i++) {

			if (taskProperties.pnlAdvanced.getComponent(i) instanceof KDLabelContainer) {
				cont = (KDLabelContainer) taskProperties.pnlAdvanced
						.getComponent(i);
				cont.setEnabled(false);
			}
		}
		// 相关按钮也给禁用掉
		taskProperties.btnAdd.setVisible(false);
		taskProperties.btnDel.setVisible(false);
		taskProperties.actionSave.setEnabled(false);
		taskProperties.cbColour.setEnabled(false);
		
	}

	/**
	 * @description
	 * @author 杜红明
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */
	public void setViewUIStatus() {
		// TODO Auto-generated method stub
		lockControl();
	}

	/**
	 * 取得计划开始日期，时间清零
	 * 
	 * @return
	 */
	private Date getPlanStartDate() {
		Date date = (Date) taskProperties.pkPlanStart.getValue();
		if (date != null) {
			date = FDCDateHelper.getDayBegin(date);
		}
		return date;
	}

	/**
	 * 取得计划结束日期，时间清零
	 * 
	 * @return
	 */
	private Date getPlanEndDate() {
		Date date = (Date) taskProperties.pkPlanEnd.getValue();
		if (date != null) {
			date = FDCDateHelper.getDayBegin(date);
		}
		return date;
	}
}
