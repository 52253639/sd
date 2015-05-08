/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentCollection;
import com.kingdee.eas.base.uiframe.client.UIModelDialogFactory;
import com.kingdee.eas.basedata.org.client.AdminOrgRangeF7PromptDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FDCAttachmentUtil;
import com.kingdee.eas.fdc.basedata.util.FdcManagementUtil;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractEntryCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo;
import com.kingdee.eas.fdc.schedule.AchievementManagerCollection;
import com.kingdee.eas.fdc.schedule.AchievementManagerFactory;
import com.kingdee.eas.fdc.schedule.AchievementManagerInfo;
import com.kingdee.eas.fdc.schedule.FDCSchTaskWithContractInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.FDCScheduleExecuteEnum;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.IScheduleTaskProgressReport;
import com.kingdee.eas.fdc.schedule.ProjectImageCollection;
import com.kingdee.eas.fdc.schedule.ProjectImageFactory;
import com.kingdee.eas.fdc.schedule.ProjectImageInfo;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleHelper;
import com.kingdee.eas.fdc.schedule.ScheduleQualityCheckPointCollection;
import com.kingdee.eas.fdc.schedule.ScheduleQualityCheckPointFactory;
import com.kingdee.eas.fdc.schedule.ScheduleQualityCheckPointInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportInfo;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;

/**
 * 进度任务属性页签 编辑界面
 */
public class FDCScheduleTaskPropertiesNewUI extends AbstractFDCScheduleTaskPropertiesNewUI {
	private static final long serialVersionUID = -8148943158704130300L;

	private static final Logger logger = CoreUIObject.getLogger(FDCScheduleTaskPropertiesNewUI.class);

	public ScheduleGanttProject scheduleGanttProject;
	public static final String STATUS_ADDNEW = "ADDNEW";
	public static final String STATUS_EDIT = "EDIT";
	public static final String STATUS_VIEW = "VIEW";
	/*
	 * by ZhouHangjian for Enabled
	 */
	public static final String STATE_VIEW = "VIEW";

	/* 新增行按扭 */
	private static final String BTN_ADDROW = "btnAddRow";

	/* 删除行按扭 */
	private static final String BTN_REMOVEROW = "btnRemoveRow";

	/* 新增行按扭图标 */
	private static final String IMG_BTN_ADDLINE = "imgTbtn_addline";

	/* 删除行按扭图标 */
	private static final String IMG_BTN_REMOVELINE = "imgTbtn_deleteline";
	private CoreBaseCollection removeRowRelationTask;
	private TaskMainPanelNewHelper taskMainPanelNewHelper;
	private StandandTaskGuidePanelNewHelper standandTaskGuidePanelNewHelper;
	private PredecessorsPanelNewHelper predecessorsPanelNewHelper;
	protected KDTask selectTask = null;
	private boolean isNeedRefresh = false;
	
	private boolean isCmptPerGood = true;

	/*
	 * 由于进度任务较多的时候，刷新界面会消耗很多资料，特增加此变量 有以下几种情况需要刷新： 1、在任务汇报界面做了任务汇报的相关操作
	 * 2、在进度评价界面做了相应的操作
	 */
	public boolean isNeedRefresh() {
		return isNeedRefresh;
	}

	public void setNeedRefresh(boolean isNeedRefresh) {
		this.isNeedRefresh = isNeedRefresh;
	}

	/*
	 * TaskApprisePane by zhouhj
	 */
	private FDCScheduleTaskAppriseHelper taskAppriseHelper;

	public FDCScheduleTaskPropertiesNewUI() throws Exception {
	}

	private FDCScheduleBaseEditUI baseEditUI;

	public void onLoad() throws Exception {
		super.onLoad();
		setCompontStyle();
		load();
		// initUIStatus();
		
		// 初始化进度汇报表格按钮 add by libing at 2011-8-23(check)
		initschedulereportbtn();
		// 加载进度汇报表格数据 add by libing at 2011-8-24
		initschedulereporttableData();
		// 为进度汇报加事件
		// addScheduleReportEvent();

		/*
		 * TaskApprisePane by zhouhj
		 */
		loadDataTaskApprisePane();

		// 初始化形象进度表格数据
		initPScheduleTable();

		// 初始化阶段性成果表格数据
		initAchievementManagerTable();

		// 初始化质量检查点表格数据
		initQualityCheckPointTable();

		// 初始化相关专项任务表格数据
		initRelatedTaskTable();
		
		// 初始化合同表格数据
		initReloationContractTable();

		initButtonAndTableState();
		
//		initProjectImageButton(); 
		
//		initAchievementManagerButton();
		//在编制界面，这三个页签不需要这两个按钮；
		if(!isFromEditUI()){
			initQualityCheckPointButtion();
			initAchievementManagerButton();
			initProjectImageButton(); 
		}
		if (!isNeedShowBtn()) {
			this.saveBtn.setEnabled(false);
		}

		if (isFromExecuteUI()) {
			setUITitle("计划执行详细信息");

		}

		this.kDTabbedPane1.getComponentAt(this.kDTabbedPane1.getTabCount() - 1).setVisible(false);
	}

	/**
	 * 
	 * @description 根据任务的编制状态控制基础页签和高级页签的控件是否可用
	 * 
	 * @author 周航健
	 * @createDate 2011-08-26
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 */
	public void afterLoadInitSeting() {
		String state = baseEditUI.getOprtState();
		if (state.equals(STATE_VIEW)) {
			/*
			 * 基础信息页签控件控制
			 */
			this.saveBtn.setEnabled(false);
			this.contTaskName.setEnabled(false);
			this.contTaskGuide.setEnabled(false);
			this.contTaskType.setEnabled(false);
			this.contBizType.setEnabled(false);
			this.contBizType.setEnabled(false);
			this.contPlanStart.setEnabled(false);
			this.contPlanEnd.setEnabled(false);
			this.contWorkDay.setEnabled(false);
			this.txtYes.setEnabled(false);
			this.txtNo.setEnabled(false);
			this.contAccessDate.setEnabled(false);
			this.contCheckNode.setEnabled(false);
			this.contScheduleAppraisePerson.setEnabled(false);
			this.contDutyPerson.setEnabled(false);
			this.contDutyDep.setEnabled(false);
			this.contQualityAppraisePerson.setEnabled(false);
			this.kDLabelContainer1.setEnabled(false);

			/*
			 * 高级页签控件控制
			 */
			this.contHelpPerson.setEnabled(false);
			this.contHelpDep.setEnabled(false);
			this.contPriorityLevel.setEnabled(false);
			this.contShape.setEnabled(false);
			this.contColour.setEnabled(false);
			this.contTaskCalendar.setEnabled(false);
			this.contRiskChargePerson.setEnabled(false);
			this.cbColour.setEnabled(false);
			this.prmtDutyDep.setEnabled(false);

			/*
			 * 前置任务页签按钮
			 */
			this.btnAdd.setEnabled(false);
			this.btnDel.setEnabled(false);

		} else {
			/*
			 * 基础信息页签控件控制
			 */
			this.saveBtn.setEnabled(true);
			this.contTaskName.setEnabled(true);
			this.contTaskGuide.setEnabled(true);
			this.contTaskType.setEnabled(true);
			this.contBizType.setEnabled(true);
			this.contBizType.setEnabled(true);
			this.contPlanStart.setEnabled(true);
			this.contPlanEnd.setEnabled(true);
			this.contWorkDay.setEnabled(true);
			this.txtYes.setEnabled(true);
			this.txtNo.setEnabled(true);
//			this.contAccessDate.setEnabled(true);
//			this.contCheckNode.setEnabled(true);
			this.contScheduleAppraisePerson.setEnabled(true);
			this.contDutyPerson.setEnabled(true);
			this.contDutyDep.setEnabled(true);
			this.contQualityAppraisePerson.setEnabled(true);
			this.kDLabelContainer1.setEnabled(true);

			/*
			 * 高级页签控件控制
			 */
			this.contHelpPerson.setEnabled(true);
			this.contHelpDep.setEnabled(true);
			this.contPriorityLevel.setEnabled(true);
			this.contShape.setEnabled(true);
			this.contColour.setEnabled(true);
			this.contTaskCalendar.setEnabled(true);
			this.contRiskChargePerson.setEnabled(true);
			this.cbColour.setEnabled(true);
			this.prmtDutyDep.setEnabled(true);

			/*
			 * 前置任务页签按钮
			 */
			this.btnAdd.setEnabled(true);
			this.btnDel.setEnabled(true);

		}
	}

	/**
	 * @discription <为进度汇报加表格点击事件>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/27>
	 */
	public void addScheduleReportEvent() {

		EventListenerList list = scheduleReportTable.getListenerList();
		Object[] allListener = list.getListenerList();
		for (int i = 0; i < allListener.length; i++) {
			if (allListener[i] instanceof KDTMouseListener) {
				logger.info(allListener[i]);
				scheduleReportTable.removeKDTMouseListener((KDTMouseListener) allListener[i]);
			}
		}

		this.scheduleReportTable.addKDTMouseListener(new KDTMouseListener() {

			public void tableClicked(KDTMouseEvent e) {
				if (e.getClickCount() == 2) {
					try {
						int rowIndex = scheduleReportTable.getSelectManager().getActiveRowIndex();
						if (rowIndex < 0) {
							return;
						}
						IRow row = scheduleReportTable.getRow(rowIndex);
						if (null == row.getCell("id").getValue()) {
							return;
						}
						String id = row.getCell("id").getValue().toString();
						FDCScheduleTaskInfo taskinfo = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
						UIContext uiContext = new UIContext(this);
						uiContext.put(UIContext.ID, id);
						uiContext.put("enableState", "enableState");
						uiContext.put("taskinfo", taskinfo);
						IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
						IUIWindow uiWindow = uiFactory.create(ScheduleTaskProgressReportEditUI.class.getName(), uiContext, null, OprtState.VIEW);
						uiWindow.show();
					} catch (UIException e1) {
						e1.printStackTrace();
						handUIException(e1);
					}
				}
			}

		});
	}

	public void onShow() throws Exception {
		super.onShow();

		// 控制页签状态
		controlState();

		/*
		 * by ZhouHangjian
		 * 
		 * 不是编制状态不可编辑
		 */
		this.afterLoadInitSeting();
		lockTaskAttr();
		setExecutingUIStatus();

		if (!saveBtn.isEnabled()) {
			prmtAchievementType.setEnabled(false);
		}
		contTaskCalendar.setEnabled(false);
		setControlByCompletedAndParam();
		
		this.btnReport.setVisible(this.actionSaveReport.isVisible());
	}

	/**
	 * 根据完成情况和参数FDCSH011来设置控件的可用性
	 * @author owen_wen  2013-7-25
	 */
	private void setControlByCompletedAndParam() throws EASBizException, BOSException {
		contCompletePercent.setEnabled(true);
		contActualStartDate.setEnabled(true);
		conIntendEndDate.setEnabled(true);
		contWorkLoad.setEnabled(true);
		kDLabelContainer1.setEnabled(true);
		
		FDCScheduleTaskInfo taksInfo = (FDCScheduleTaskInfo) getSelectedTask().getScheduleTaskInfo();
		boolean shouldBeControled = FDCScheduleTaskExecuteHelper.checkShouldBeControledBeforeProgressReport(taksInfo);
		if (shouldBeControled) {
			kdDpActualStartDate.setEnabled(false);
			kdDpFinishDate.setEnabled(false);
			txtCompletePercent.setEnabled(false);
		} 
	}

	private void lockTaskAttr() {
		boolean isSuperTask = selectTask.isSupertask();
		if (isSuperTask) {
			contPlanStart.setEnabled(false);
			contPlanEnd.setEnabled(false);
			contWorkDay.setEnabled(false);

			schedulereportbtn.setVisible(false);
			schedulereportbtndel.setVisible(false);
		}
	}

	/**
	 *  为“形象进度”页签设置表格单击的监听事件
	 */
	protected void tblPSchedule_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			int i = tblPSchedule.getSelectManager().getActiveRowIndex();
			if (i < 0) {
				FDCMsgBox.showWarning("请先选择一行进行查看");
				SysUtil.abort();
			}
			IRow row = tblPSchedule.getRow(i);
			String projectId = row.getCell("ID").getValue().toString();
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, projectId);
			Object obj = getUIContext().get("Owner");
			if (obj instanceof MainScheduleEditUI || obj instanceof SpecialScheduleEditUI) {
				uiContext.put("enableState", "enableState");
			}
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = uiFactory.create(ProjectImageNewEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}
	
	/**
	 *  为“阶段性成果”页签设置表格单击的监听事件
	 */
	protected void tblAchievement_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			int m = tblAchievement.getSelectManager().getActiveRowIndex();
			if (m > -1) {
				String id = tblAchievement.getRow(m).getCell("id").getValue().toString();
				IUIFactory uiFactory = null;
				uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
				UIContext uicontext = new UIContext(this);
				FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
				uicontext.put("taskinfo", info);
				uicontext.put(UIContext.ID, id);
				Object obj = getUIContext().get("Owner");
				if (obj instanceof MainScheduleEditUI || obj instanceof SpecialScheduleEditUI) {
					uicontext.put("enableState", "enableState");
				}
				IUIWindow uiwindow = uiFactory.create(AchievementManagerEditUI.class.getName(), uicontext, null, OprtState.VIEW);
				uiwindow.show();
			}
		}
	}

	/**
	 * 为“质量检查点”页签设置表格单击的监听事件
	 */
	protected void tblQuality_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			int i = tblQuality.getSelectManager().getActiveRowIndex();
			if (i < 0) {
				FDCMsgBox.showWarning("请先选择一行进行查看");
				SysUtil.abort();
			}
			IRow row = tblQuality.getRow(i);
			FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
			String projectId = row.getCell("ID").getValue().toString();
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, projectId);
			Object obj = getUIContext().get("Owner");
			if (obj instanceof MainScheduleEditUI || obj instanceof SpecialScheduleEditUI) {
				uiContext.put("enableState", "enableState");
			}
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = uiFactory.create(ScheduleQualityCheckPointEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}
	
	/**
	 * 为“任务评价”页签设置表格单击的监听事件
	 */
	protected void tblTaskApprise_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			int i = tblTaskApprise.getSelectManager().getActiveRowIndex();
			if (i < 0) {
				FDCMsgBox.showWarning("请先选择一行进行查看");
				SysUtil.abort();
			}
			IRow row = tblTaskApprise.getRow(i);
			String projectId = row.getCell("ID").getValue().toString();
			FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
			UIContext uiContext = new UIContext(FDCScheduleTaskPropertiesNewUI.class);
			uiContext.put(UIContext.ID, projectId);
			uiContext.put("taskId", info);
			Object obj = getUIContext().get("Owner");
			if (obj instanceof MainScheduleEditUI || obj instanceof SpecialScheduleEditUI) {
				uiContext.put("enableState", "enableState");
			}
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = uiFactory.create(TaskEvaluationBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}

	/**
	 * @discription <控制页签状态>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/26>
	 */
	public void controlState() {

		if (this.prmtBizType.getValue() instanceof Object[]) {
			this.kDTSchedulePane.remove(this.kDPanelStageAchievement);
			this.kDTSchedulePane.remove(this.kDPanelQualityInspectPoint);
			this.kDTSchedulePane.remove(this.kDPanelFigureSchedule);
			this.kDTSchedulePane.remove(this.kDPanelRelevanceSpecialTask);
			this.kDTSchedulePane.remove(this.kDPanelRelevanceCompact);

			Object[] taskBizTypes = (Object[]) prmtBizType.getValue();
			for (int i = 0; i < taskBizTypes.length; i++) {
				ScheduleBizTypeInfo item = (ScheduleBizTypeInfo) taskBizTypes[i];
				if (null == item) {
					continue;
				}

				/* 控制“阶段性成果”页签是否显示 */
				if ("阶段性成果".equals(item.getName())) {
					this.kDTSchedulePane.add(kDPanelStageAchievement, resHelper.getString("kDPanelStageAchievement.constraints"));
				}

				/* 控制“质量检查点”页签是否显示 */
				if ("质量检查点".equals(item.getName())) {
					this.kDTSchedulePane.add(kDPanelQualityInspectPoint, resHelper.getString("kDPanelQualityInspectPoint.constraints"));
				}

				/* 控制“形象进度”页签是否显示 */
				if ("形象进度".equals(item.getName())) {
					this.kDTSchedulePane.add(kDPanelFigureSchedule, resHelper.getString("kDPanelFigureSchedule.constraints"));
				}

			}
			kDTSchedulePane.add(kDPanelRelevanceSpecialTask, resHelper.getString("kDPanelRelevanceSpecialTask.constraints"));
			// kDTSchedulePane.add(kDPanelRelevanceCompact, resHelper
			// .getString("kDPanelRelevanceCompact.constraints"));
		}
		/* 控制“相关合同专项”页签是否显示 */
		if (getUIContext().get("Owner") instanceof SpecialScheduleEditUI || getUIContext().get("Owner") instanceof SpecialScheduleExecuteUI) {
			this.kDTSchedulePane.remove(kDPanelRelevanceSpecialTask);
		}

		this.kDTableTaskGuideA.checkParsed();
		this.kDTableTaskGuideB.checkParsed();
		this.kDTableTaskGuideA.getColumn("docName").setWidth(277);
		this.kDTableTaskGuideB.getColumn("docName").setWidth(277);
		this.actionSaveReport.setVisible(getCurrentTask().isIsLeaf()); //非明细不能保存，明细才可以保存
		this.pnlExecuteInfo.setVisible(false);
		if (isFromEditUI()) {
//			this.kDLabelContainer1.setBounds(new Rectangle(3, 162, 890, 116));
//			this.kDLabelContainer1.setSize(this.txtDesciption.getWidth(), this.txtDesciption.getHeight() * 2);
			this.actionSaveReport.setVisible(false);
			this.btnReport.setVisible(false);
			this.actionFirst.setVisible(false);
			this.actionPreview.setVisible(false);
			this.actionNext.setVisible(false);
			this.actionLast.setVisible(false);
			this.updateUI();
		}
		if (isFromExecuteUI() && getCurrentTask().isIsLeaf()) {
			FDCScheduleTaskInfo task = getCurrentTask();
			this.txtDesciption.setEnabled(true);
			this.kDLabelContainer1.setBoundLabelText("完成/延迟情况说明");
			this.contTotalWorkLoad.setEnabled(false);
			this.actionSave.setVisible(false);
			this.kdDpActualStartDate.setRequired(true);
			this.kdDpActualStartDate.setValue(task.getActualStartDate() == null ? task.getStart() : task.getActualStartDate());
			if (task.getComplete().compareTo(FDCHelper.ONE_HUNDRED) < 0) {
				this.conIntendEndDate.setBoundLabelText("预计完成日期");
				this.kdDpFinishDate.setValue(task.getEnd());//Gantt图上计划完成日期用getEnd()，所以这里也用这个
			} else {
				this.conIntendEndDate.setBoundLabelText("实际完成日期");
				this.kdDpFinishDate.setValue(task.getActualEndDate());
			}
			if (task.getWorkLoad() != null) {
				this.txtTotalWorkload.setValue(task.getWorkLoad());
			}
			if (task.getComplete() != null) {
				/* modified by zhaoqin for R140623-0062 on 2014/06/23 */
				//this.txtCompletePercent.setValue(task.getComplete());
				this.txtCompletePercent.setValue(task.getComplete(), false);
			}
			this.kdDpFinishDate.setRequired(true);
			
			/* modified by zhaoqin for 联发进度任务 on 2014/10/15 */
			this.kdDpFinishDate.setEnabled(false);
			
			this.txtCompletePercent.setRequired(true);
			kDTSchedulePane.setSelectedIndex(2);

			this.btnReport.setIcon(EASResource.getIcon("imgTbtn_save"));
		}
	}
	
	private void openProjectImageEditUI() throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(ProjectImageNewEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	
	/**
	 *  删除形象进度列表的数据，支持多行删除
	 */
	private void deleteProjectImage() throws Exception {
		int[] rows = KDTableUtil.getSelectedRows(tblPSchedule);
		if (rows.length <= 0) {
			FDCMsgBox.showInfo("请选中要删除的行");
			SysUtil.abort();
		}
		if (!confirmRemove()) {
			SysUtil.abort();
		}
		// 将选择的行的ID放入到数组中
		IObjectPK[] pks = new IObjectPK[rows.length];
		for (int i = rows.length; i > 0; i--) {
			String strID = tblPSchedule.getRow(rows[i - 1]).getCell("ID").getValue().toString();
			pks[i - 1] = new ObjectUuidPK(strID);
			tblPSchedule.removeRow(rows[i - 1]);
		}
		ProjectImageFactory.getRemoteInstance().delete(pks);
	}

	/**
	 * 初始化 形象进度 相关的按钮
	 */
	private void initProjectImageButton() {
		KDWorkButton btnAddForImg = ScheduleClientHelper.createButton("btnAdd", ScheduleClientHelper.BUTTON_ADD_NAME, "imgTbtn_new");
		btnAddForImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openProjectImageEditUI();
				} catch (Exception e1) {
					logger.info(e1.getMessage(), e1);
					e1.printStackTrace();
				}
			}
		});

		KDWorkButton btnDelForImg = ScheduleClientHelper.createButton("btnDel", ScheduleClientHelper.BUTTON_DEL_NAME, "imgTbtn_delete");
		btnDelForImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					deleteProjectImage();
				} catch (Exception e1) {
					logger.info(e1);
					e1.printStackTrace();
				}
			}
		});

		contaner.addButton(btnAddForImg);
		contaner.addButton(btnDelForImg);
	}

	/**
	 * 初始化 阶段性成果 相关的按钮
	 */
	private void initAchievementManagerButton() {
		KDWorkButton btnAddNew = ScheduleClientHelper.createButton("btnAdd", ScheduleClientHelper.BUTTON_ADD_NAME, "imgTbtn_new");
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					achievementAddNew_actionPerformed(e);
				} catch (Exception e1) {
					logger.info(e1.getMessage(), e1);
					e1.printStackTrace();
				}
			}
		});
		
		KDWorkButton btnRemove = ScheduleClientHelper.createButton("btnDel", ScheduleClientHelper.BUTTON_DEL_NAME, "imgTbtn_delete");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					achievementDel_actionPerformed(e);
				} catch (Exception e1) {
					logger.info(e1);
					e1.printStackTrace();
				}
			}
		});
		
		kDContainer5.addButton(btnAddNew);
		kDContainer5.addButton(btnRemove);
	}
	
	/**
	 * 初始化 质量检查点 相关的按钮
	 */
	private void initQualityCheckPointButtion() {
		KDWorkButton btnAddNew = ScheduleClientHelper.createButton("btnAdd", ScheduleClientHelper.BUTTON_ADD_NAME, "imgTbtn_new");
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionQualityAddNew_actionPerformed(e);
				} catch (Exception e1) {
					logger.info(e1.getMessage(), e1);
					e1.printStackTrace();
				}
			}
		});

		KDWorkButton btnRemove = ScheduleClientHelper.createButton("btnDel", ScheduleClientHelper.BUTTON_DEL_NAME, "imgTbtn_delete");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionQualityDelete_actionPerformed(e);
				} catch (Exception e1) {
					logger.info(e1);
					e1.printStackTrace();
				}
			}
		});

		kDContainer6.addButton(btnAddNew);
		kDContainer6.addButton(btnRemove);
	}
	
	/**
	 * 新增 质量检查点
	 */
	private void actionQualityAddNew_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(ScheduleQualityCheckPointEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	
	/**
	 * 删除 质量检查点
	 */
	private void actionQualityDelete_actionPerformed(ActionEvent e) throws Exception {
		int rowIndexs[] = KDTableUtil.getSelectedRows(tblQuality);
		if (rowIndexs.length <= 0) {
			FDCMsgBox.showInfo("请先选要删除的行");
			SysUtil.abort();
		}
		if (!confirmRemove()) {
			SysUtil.abort();
		}
		IObjectPK[] pks = new IObjectPK[rowIndexs.length];
		for (int i = rowIndexs.length; i > 0; i--) {
			if(tblQuality.getRow(rowIndexs[i - 1]).getCell("ID")!=null){				
				String id = tblQuality.getRow(rowIndexs[i - 1]).getCell("ID").getValue().toString();
				pks[i - 1] = new ObjectUuidPK(id);
				tblQuality.removeRow(rowIndexs[i - 1]);
				
				FDCAttachmentUtil.deleteAllAtt(null, id);
			}
		}

		ScheduleQualityCheckPointFactory.getRemoteInstance().delete(pks);
	}

	/**
	 * 新增阶段性成果 可以不保存单据
	 */
	private void achievementAddNew_actionPerformed(ActionEvent e) throws Exception {
		FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) getCurrentTask();
		if (taskInfo != null && taskInfo.getAchievementType() == null) {
			FDCMsgBox.showError("当前任务没有设置成果类型，不能进行新增阶段性成果，请先指定任务的成果类型后，再进行填报！");
			abort();
		}

		IUIFactory uiFactory = UIFactory.createUIFactory(UIModelDialogFactory.class.getName());
		UIContext ctx = new UIContext(this);
		ctx.put("taskinfo", taskInfo);
		ctx.put("isFromSchReport", Boolean.TRUE); //此标志可以锁定相关任务F7，故传入Boolean.TRUE
		IUIWindow uiwindow = uiFactory.create(AchievementManagerEditUI.class.getName(), ctx, null, OprtState.ADDNEW);
		uiwindow.show();
	}
	
	/**
	 * 删除阶段性成果
	 */
	private void achievementDel_actionPerformed(ActionEvent e) throws Exception {
		tblAchievement.checkParsed();
		int m[] = KDTableUtil.getSelectedRows(tblAchievement);
		if (m.length <= 0) {
			FDCMsgBox.showInfo("请选要删除的行");
			SysUtil.abort();
		}
		if (!confirmRemove()) {
			SysUtil.abort();
		}
		IObjectPK[] pks = new IObjectPK[m.length];
		for (int i = m.length; i > 0; i--) {
			String id = tblAchievement.getRow(m[i - 1]).getCell("id").getValue().toString();
			pks[i - 1] = new ObjectUuidPK(id);
			tblAchievement.removeRow(m[i - 1]);
		}

		AchievementManagerFactory.getRemoteInstance().delete(pks);
	}

	/**
	 * @discription <初始化表格或按扭的一些状态>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/19>
	 */
	public void initButtonAndTableState() {

		if (isFromExecuteUI()) {
			schedulereportbtn.setVisible(true);
			schedulereportbtndel.setVisible(true);
			scheduleReportTable.getStyleAttributes().setLocked(false);
			tblTaskApprise.getStyleAttributes().setLocked(false);
		} else if (isFromEditUI() || isFromTotalUI()) {
			schedulereportbtn.setVisible(false);
			schedulereportbtndel.setVisible(false);
			scheduleReportTable.getStyleAttributes().setLocked(true);
			kDContainerTaskApprise.removeAllButton();
			tblTaskApprise.getStyleAttributes().setLocked(true);
		}
		kDContainer1.setEnableActive(false);
		// PBG098193 明细任务，“所属专项”才能使用。Modidied by owen_wen 2012-05-31 
		contBelongToSpecial.setEnabled(getSelectedTask().getScheduleTaskInfo().isIsLeaf());
	}

	public boolean isFromEditUI() {
		return getUIContext().get("Owner").getClass().equals( MainScheduleEditUI.class) || getUIContext().get("Owner").getClass().equals(SpecialScheduleEditUI.class);
	}

	public boolean isFromTotalUI() {
		return getUIContext().get("Owner") instanceof TotalScheduleEditUI;
	}

	public boolean isFromExecuteUI() {
		return getUIContext().get("Owner").getClass().equals(MainScheduleExecuteUI.class) || getUIContext().get("Owner").getClass().equals(SpecialScheduleExecuteUI.class);
	}

	public void setOprtState(String oprtType) {
		oprtType = getOwnerStatus(oprtType);
		super.setOprtState(oprtType);
		if (oprtType.equals(STATUS_VIEW)) {
			saveBtn.setEnabled(false);
		} else if (oprtType.equals(STATUS_EDIT)) {
			saveBtn.setEnabled(true);
		}
	}

	public String getOwnerStatus(String oprtType) {
		baseEditUI = ((FDCScheduleBaseEditUI) getUIContext().get("Owner"));
		if (oprtType == null) {
			oprtType = STATUS_VIEW;
		}
		oprtType = baseEditUI.getOprtState();
		return oprtType;
	}

	/**
	 * LIBING 进度汇报查看
	 */
	protected void scheduleReportTable_tableClicked(KDTMouseEvent e) throws Exception {
		/* modified by zhaoqin for R140928-0027 on 2015/01/19 */
		if (null != getUIContext().get("Owner") && (
				getUIContext().get("Owner").getClass().equals(MainScheduleEditUI.class) || 
				getUIContext().get("Owner").getClass().equals(SpecialScheduleEditUI.class))) {
			return;
		}
		super.scheduleReportTable_tableClicked(e);
		if (e.getClickCount() == 2) {
			String id = (this.scheduleReportTable.getRow(this.scheduleReportTable.getSelectManager().getActiveRowIndex()).getCell("id").getValue()).toString();
			UIContext uiContext = new UIContext(this);
			if (null == id) {
				SysUtil.abort();
			}
			uiContext.put(UIContext.ID, id);
			FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
			uiContext.put("taskinfo", info);
			IUIFactory uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
			IUIWindow uiWindow = null;
			uiWindow = uiFactory.create(ScheduleTaskProgressReportEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
			resetScheduleProperties();
		}

	}

	/*
	 * 进度汇报增加
	 */
	public void addNewScheduleReport_actionPerformed(ActionEvent e) throws Exception {
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
		UIContext uicontext = new UIContext(this);
		FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
		FDCScheduleTaskExecuteHelper.checkLeafTask(info, "进度汇报");
		FDCScheduleTaskExecuteHelper.checkAndShowMsgInfoWhenCompleted(info);
		uicontext.put("taskinfo", info);
		IUIWindow uiwindow = uiFactory.create(ScheduleTaskProgressReportEditUI.class.getName(), uicontext, null, OprtState.ADDNEW);
		uiwindow.show();
		setNeedRefresh(true);
		refreshScheduleReportData();
		refreshPropertyUITableData(info);

	}

	/**
	 * 
	 * 描述：在任务填报后，可能填写了对应的形象进度、质量检查点、阶段性成果管理
	 * 
	 * @param info
	 * @throws BOSException
	 *             创建人：yuanjun_lan 创建时间：2011-11-14
	 */
	private void refreshPropertyUITableData(FDCScheduleTaskInfo info) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id", info.getId().toString()));
		view.setFilter(filter);
		ScheduleTaskBizTypeCollection cols = ScheduleTaskBizTypeFactory.getRemoteInstance().getScheduleTaskBizTypeCollection(view);
		if (cols.size() == 0) {
			return;
		} else {
			refreshTable(cols);
		}
	}

	private void refreshTable(ScheduleTaskBizTypeCollection cols) throws BOSException {
		ScheduleTaskBizTypeInfo bizType = null;
		for (Iterator it = cols.iterator(); it.hasNext();) {
			bizType = (ScheduleTaskBizTypeInfo) it.next();
			if (bizType.getBizType().getId().toString().equalsIgnoreCase(FDCScheduleConstant.PROJECTIMAGE)) {
				tblPSchedule.removeRows();
				initPScheduleTable();
			} else if (bizType.getBizType().getId().toString().equalsIgnoreCase(FDCScheduleConstant.ACHIEVEMANAGER)) {
				tblAchievement.removeRows();
				initAchievementManagerTable();
			} else if (bizType.getBizType().getId().toString().equalsIgnoreCase(FDCScheduleConstant.QUALITYCHECKPOINT)) {
				tblQuality.removeRows();
				initQualityCheckPointTable();
			}
		}
	}

	private void refreshScheduleReportData() throws BOSException {
		this.scheduleReportTable.removeRows();
		initschedulereporttableData();
	}

	/**
	 * 
	 * 
	 * 描述： 进度汇报删除
	 * 
	 * @author 李兵
	 * @createDate
	 * @param
	 * @return
	 * @modifyDate 2011-10-5
	 * @modifyUser 周航健
	 * 
	 * @version EAS7.0
	 * 
	 */
	public void delScheduleReport_actionPerformed(ActionEvent e) throws Exception {
		// 检查是否要根据完成情况来给出警告信息，并中断退出
		FDCScheduleTaskExecuteHelper.checkAndShowMsgInfoWhenCompleted((FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo());
		int[] m = KDTableUtil.getSelectedRows(scheduleReportTable);
		if (m.length == 0) {
			FDCMsgBox.showInfo("请先选择记录行!");
			SysUtil.abort();
		}
		if (!confirmRemove()) {
			SysUtil.abort();
		}
		IObjectPK[] pk = new IObjectPK[m.length];
		for (int i = m.length; i > 0; i--) {
			String id = scheduleReportTable.getRow(m[i - 1]).getCell("id").getValue().toString();
			scheduleReportTable.removeRow(m[i - 1]);
			pk[i - 1] = new ObjectUuidPK(id);
		}
		ScheduleTaskProgressReportFactory.getRemoteInstance().delete(pk);
		refreshScheduleReportData();
		resetScheduleProperties();
		resetReportControl();

		// ////////////////////////////////////////////////////
		// 刷新表头
		// ////////////////////////////////////////////////////

		FDCScheduleTaskInfo taksInfo = (FDCScheduleTaskInfo) getSelectedTask().getScheduleTaskInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcRelateTask", taksInfo.getSrcID()));
		boolean hasTaskProgressEntry = ScheduleTaskProgressReportFactory.getRemoteInstance().exists(filter);
		if (!hasTaskProgressEntry) {
			boolean shouldBeControled = false;
			kdDpActualStartDate.setEnabled(!shouldBeControled);
			kdDpFinishDate.setEnabled(!shouldBeControled);
			txtCompletePercent.setEnabled(!shouldBeControled);

			kdDpFinishDate.setValue(pkPlanEnd.getValue());
			txtCompletePercent.setValue(null);
		} else {
			boolean shouldBeControled = FDCScheduleTaskExecuteHelper.checkShouldBeControledBeforeProgressReport(taksInfo);
			kdDpActualStartDate.setEnabled(!shouldBeControled);
			kdDpFinishDate.setEnabled(!shouldBeControled);
			txtCompletePercent.setEnabled(!shouldBeControled);
			
			if (!shouldBeControled) {
				kdDpFinishDate.setValue(pkPlanEnd.getValue());
				txtCompletePercent.setValue(null);
			}
		}

		// ////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////

		isNeedRefresh = true;
	}

	private void resetScheduleProperties() throws EASBizException, BOSException {
		selectTask = this.getSelectedTask();
		FDCScheduleTaskInfo info = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo(
				new ObjectUuidPK(((FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo()).getId()));
		this.txtAppendEndQuantity.setValue(info.getWorkLoad());
		this.txtEndSchedule.setValue(info.getComplete());
		// 更新界面上的task的 Complete和WorkLoad属性值
		((FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo()).setWorkLoad(info.getWorkLoad());
		((FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo()).setComplete(info.getComplete());
	}

	protected boolean confirmRemove() {
		return MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}

	/**
	 * 
	 * @description
	 * @author 李兵
	 * @createDate 2011-8-23 void
	 */
	private void initschedulereporttableData() throws BOSException {
		// 拿到任务信息
		scheduleReportTable.checkParsed();
		selectTask = this.getSelectedTask();
		FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
		if (info != null) {
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("*"));
			sic.add(new SelectorItemInfo("reportor.id"));
			sic.add(new SelectorItemInfo("reportor.name"));
			sic.add(new SelectorItemInfo("reportor.number"));
			view.setSelector(sic);

			SorterItemCollection sorter = new SorterItemCollection();
			SorterItemInfo sortItem = new SorterItemInfo("lastupdatetime");
			sortItem.setSortType(SortType.DESCEND);
			sorter.add(sortItem);
			view.setSorter(sorter);

			FilterInfo finfo = new FilterInfo();
			finfo.getFilterItems().add(new FilterItemInfo("srcRelateTask", info.getSrcID().toString()));
			view.setFilter(finfo);
			
			ScheduleTaskProgressReportCollection col = ScheduleTaskProgressReportFactory.getRemoteInstance().getScheduleTaskProgressReportCollection(
					view);
			ScheduleTaskProgressReportInfo info2 = null;
			IRow row = null;
			for (int i = 0; i < col.size(); i++) {
				info2 = col.get(i);
				row = scheduleReportTable.addRow();
				if (info2.getReportor() != null) {
					row.getCell("reportor").setValue(info2.getReportor().getName());
				}
				row.getCell("reportData").setValue(info2.getReportDate());
				row.getCell("completePrecent").setValue(info2.getCompletePrecent());
				row.getCell("completeAmount").setValue(info2.getCompleteAmount());
				row.getCell("description").setValue(info2.getDescription());
				row.getCell("id").setValue(info2.getId());
				row.getStyleAttributes().setLocked(true);
				row.setUserObject(info2);
			}
		}
	}

	/**
	 * 
	 * @description 初始化按钮
	 * @author 李兵
	 * @createDate 2011-8-23 void
	 * @version EAS7.0
	 * @see
	 */
	private void initschedulereportbtn() {
		//scheduleReportContainer.addButton(schedulereportbtn);
		//schedulereportbtn.setEnabled(true);
		scheduleReportContainer.addButton(schedulereportbtndel);
		schedulereportbtndel.setEnabled(true);
		schedulereportbtn.setIcon(EASResource.getIcon("imgTbtn_new"));
		schedulereportbtndel.setIcon(EASResource.getIcon("imgTbtn_delete"));

	}

	/**
	 * 
	 * @description 关闭进度汇报窗口时，自动增加行到表格
	 * @author 李兵
	 * @createDate 2011-8-23
	 * @param editData
	 *            void
	 * @version EAS7.0
	 * @see
	 */
	public void addScheduleReportTableRow(ScheduleTaskProgressReportInfo editData) {
		if (editData.getId() != null) {
			int m = scheduleReportTable.getRowCount();
			for (int i = 0; i < m; i++) {
				if (editData.getId().toString().trim().equals(scheduleReportTable.getRow(i).getCell("id").getValue().toString().trim())) {
					if (editData.getReportor() != null) {
						scheduleReportTable.getRow(i).getCell("reportor").setValue(editData.getReportor().getName());
					}
					scheduleReportTable.getRow(i).getCell("reportData").setValue(editData.getReportDate());
					scheduleReportTable.getRow(i).getCell("completePrecent").setValue(editData.getCompletePrecent());
					scheduleReportTable.getRow(i).getCell("completeAmount").setValue(editData.getCompleteAmount());
					scheduleReportTable.getRow(i).getCell("description").setValue(editData.getDescription());
					scheduleReportTable.getRow(i).getCell("id").setValue(editData.getId());
					return;
				}
			}
			IRow row = scheduleReportTable.addRow();
			if (editData.getReportor() != null) {
				row.getCell("reportor").setValue(editData.getReportor().getName());
			}
			row.getCell("reportData").setValue(editData.getReportDate());
			row.getCell("completePrecent").setValue(editData.getCompletePrecent());
			row.getCell("completeAmount").setValue(editData.getCompleteAmount());
			row.getCell("description").setValue(editData.getDescription());
			row.getCell("id").setValue(editData.getId());
			row.getStyleAttributes().setLocked(true);
		}

	}

	/**
	 * 
	 * @description 获取数据行对象
	 * @author 杜红明
	 * @createDate 2011-8-15
	 * @return ScheduleGanttProject
	 * @version EAS7.0
	 * @see
	 */
	private KDTask getTaskProject() {
		scheduleGanttProject = (ScheduleGanttProject) getUIContext().get("ScheduleGanttProject");
		List list = scheduleGanttProject.getTaskSelectionContext().getSelectedTasks();
		if (list.size() == 0) {
			FDCMsgBox.showWarning(this, "请选择具体任务");
			SysUtil.abort();
		}
		return (KDTask) list.get(0);
	}

	public void load() {
		try {
			FDCScheduleBaseEditUI fDCScheduleBaseEditUI = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
			CurProjectInfo currentProjectInfo = (CurProjectInfo) fDCScheduleBaseEditUI.prmtCurproject.getValue();

			taskMainPanelNewHelper = new TaskMainPanelNewHelper(this, currentProjectInfo);
			registerHelper(taskMainPanelNewHelper);

			standandTaskGuidePanelNewHelper = new StandandTaskGuidePanelNewHelper(this);
			registerHelper(standandTaskGuidePanelNewHelper);
			predecessorsPanelNewHelper = new PredecessorsPanelNewHelper(this);
			registerHelper(predecessorsPanelNewHelper);
			taskMainPanelNewHelper.load();
			predecessorsPanelNewHelper.load();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @description 获取选择的任务节点控件
	 * @author 杜红明
	 * @createDate 2011-8-16
	 * @return KDTask
	 * @version EAS7.0
	 * @see
	 */
	public KDTask getSelectedTask() {
		if (selectTask == null) {
			// 直接传入的任务节点最优先
			selectTask = (KDTask) getUIContext().get("selectTask");
		}
		if (selectTask == null) {
			selectTask = getTaskProject();
		}
		return selectTask;
	}

	/**
	 * 
	 * @description 设置控件风格
	 * @author 杜红明
	 * @createDate 2011-8-16 void
	 * @version EAS7.0
	 * @see
	 */
	private void setCompontStyle() {
		saveBtn.setIcon(EASResource.getIcon("imgTbtn_save"));
		actionSave.setEnabled(true);
		txtAppendEndQuantity.setHorizontalAlignment(FDCClientHelper.NUMBERTEXTFIELD_ALIGNMENT);
		txtEndSchedule.setHorizontalAlignment(FDCClientHelper.NUMBERTEXTFIELD_ALIGNMENT);
		txtWorkDay.setHorizontalAlignment(FDCClientHelper.NUMBERTEXTFIELD_ALIGNMENT);

		// 基本信息行政组织F7责任人控件分层显示
		FDCClientUtils.setRespDeptF7(prmtDutyDep,this);
		FDCClientUtils.setPersonF7(prmtDutyPerson, this, null);

		AdminOrgRangeF7PromptDialog dia=new AdminOrgRangeF7PromptDialog(SysContext.getSysContext().getCurrentUserInfo());
		this.prmtHelpDep.setDialog(dia);
//		FDCClientUtils.setRespDeptF7(prmtHelpDep, this, null, false);
		FDCClientUtils.setPersonF7(prmtHelpPerson, this, null,false);

		// 风险负责人F7
		FDCClientUtils.setPersonF7(prmtRiskChargePerson, this, null);
		// 进度评价人F7
		FDCClientUtils.setPersonF7(prmtScheduleAppraisePerson, this, null);
		// 质量评价人F7
		FDCClientUtils.setPersonF7(prmtQualityAppraisePerson, this, null);

	}

	/**
	 * @description
	 * @author 杜红明
	 * @createDate 2011-8-16
	 * @version EAS7.0
	 * @see
	 */

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		predecessorsPanelNewHelper.commit();
		taskMainPanelNewHelper.commit();
		// 后置任务提交
		new TaskExtProPanelNewHelper(this).commit();
		// 甘特图数据更新
		// this.uiWindow.close();
		
		// 保存相关专项任务数据
		this.saveRelatedTask();

		// 设置关联
		selectTask.getScheduleTaskInfo().put("relCon", new ArrayList());
		for (int i = 0; i < tblContract.getRowCount(); i++) {
			relevanceContract(tblContract.getRow(i));
		}
		FDCMsgBox.showInfo("任务保存成功！");

	}

	/**
	 * @description 处理页面状态
	 * @author 杜红明
	 * @createDate 2011-8-18
	 * @version EAS7.0
	 * @see
	 */
	public String getOprtState() {
		return STATUS_EDIT;
	}

	private boolean isExecting = false;
	private List helpers = new ArrayList(3);

	/**
	 * 是否任务已执行
	 * 
	 * @return
	 */
	protected boolean isExecuting() {
		return isExecting;
	}

	/**
	 * 设置执行状态的各UI状态
	 */
	protected void setExecutingUIStatus() {
		for (int i = 0; i < helpers.size(); i++) {
			((ITaskPanelHelper) helpers.get(i)).setExecutingUIStatus();
		}
	}

	/**
	 * 设置查看状态的各UI状态
	 */
	protected void setViewUIStatus() {
		for (int i = 0; i < helpers.size(); i++) {
			((ITaskPanelHelper) helpers.get(i)).setViewUIStatus();
		}
	}

	/**
	 * 设置编辑状态的UI状态--只有计划编制的时候才会有编辑状态的任务属性
	 */
	protected void setEditUIStatus() {
		for (int i = 0; i < helpers.size(); i++) {
			((ITaskPanelHelper) helpers.get(i)).setEditUIStatus();
		}
	}

	/**
	 * 界面状态初始化
	 */
	private void initUIStatus() {
		// 查看总进度计划时，任务属性卡的信息不可编辑，只能查看 modify by warship at 2010/07/21
		if (getOprtState().equals("FINDVIEW")) {
			setViewUIStatus();
		}
		if (isExecuting()) {
			setExecutingUIStatus();
		} else if (getOprtState().equals(OprtState.VIEW)) {
			setViewUIStatus();
		} else if (getOprtState().equals(OprtState.EDIT) || getOprtState().equals(OprtState.ADDNEW)) {
			setEditUIStatus();
		}
	}

	protected void registerHelper(ITaskPanelHelper helper) {
		helpers.add(helper);
	}

	/**
	 * @description
	 * @author 杜红明
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	protected void prmtTaskGuide_dataChanged(DataChangeEvent e) throws Exception {
		FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
		fDCScheduleTaskInfo.setStandardTask((StandardTaskGuideInfo) e.getNewValue());
		if (e.getNewValue() != null) {//用户在编辑属性时可能会清除标准任务指引的F7内容，newValue可能为空
			cbTaskType.setSelectedItem(((StandardTaskGuideInfo) e.getNewValue()).getTaskType());
		}
		standandTaskGuidePanelNewHelper.load();
	}

	/**
	 * 
	 * @description loadData任务评价页签
	 * @author 周航健
	 * @createDate 2011-08-26
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 */
	public void loadDataTaskApprisePane() {
		try {
			taskAppriseHelper = new FDCScheduleTaskAppriseHelper(this);
			taskAppriseHelper.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @discription <初始化阶段性成果表格>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/26>
	 */
	public void initAchievementManagerTable() throws BOSException {
		FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
		String taskID = String.valueOf(null == fDCScheduleTaskInfo.getSrcID() ? "" : fDCScheduleTaskInfo.getSrcID().toString());
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("achievementType.*"));
		sic.add(new SelectorItemInfo("creator.*"));
		sic.add(new SelectorItemInfo("creator.person.id"));
		sic.add(new SelectorItemInfo("creator.person.name"));
		sic.add(new SelectorItemInfo("creator.person.number"));
		view.setSelector(sic);
		FilterInfo finfo = new FilterInfo();
		finfo.getFilterItems().add(new FilterItemInfo("relateTask.id", taskID));
		view.setFilter(finfo);
		AchievementManagerCollection col = AchievementManagerFactory.getRemoteInstance().getAchievementManagerCollection(view);
		this.tblAchievement.checkParsed();
		this.tblAchievement.getColumn("state").getStyleAttributes().setLocked(true);
		this.tblAchievement.getColumn("state").setWidth(100);
		this.tblAchievement.getColumn("name").getStyleAttributes().setLocked(true);
		this.tblAchievement.getColumn("name").setWidth(150);
		this.tblAchievement.getColumn("aType").getStyleAttributes().setLocked(true);
		this.tblAchievement.getColumn("aType").setWidth(150);
		this.tblAchievement.getColumn("adoc").getStyleAttributes().setLocked(true);
		this.tblAchievement.getColumn("adoc").setWidth(150);
		this.tblAchievement.getColumn("submitPerson").getStyleAttributes().setLocked(true);
		this.tblAchievement.getColumn("submitPerson").setWidth(120);
		this.tblAchievement.getColumn("submitDate").getStyleAttributes().setLocked(true);
		this.tblAchievement.getColumn("submitDate").setWidth(150);
		for (int i = 0; i < col.size(); i++) {
			updateRowDataForAchievementManagerInfo(tblAchievement.addRow(), col.get(i));
		}
	}

	/**
	 * @discription <初始化质量检查点数据>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/27>
	 */
	public void initQualityCheckPointTable() throws BOSException {
		FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
		String taskID = String.valueOf(null == fDCScheduleTaskInfo.getSrcID() ? "" : fDCScheduleTaskInfo.getSrcID().toString());
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("checkPoint.*"));
		sic.add(new SelectorItemInfo("creator.person.name"));
		sic.add(new SelectorItemInfo("creator.person.number"));
		sic.add(new SelectorItemInfo("creator.person.id"));
		sic.add(new SelectorItemInfo("checkPost.*"));
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("relateTask.id", taskID);
		view.setFilter(filter);
		ScheduleQualityCheckPointCollection col = ScheduleQualityCheckPointFactory.getRemoteInstance().getScheduleQualityCheckPointCollection(view);
		this.tblQuality.getStyleAttributes().setLocked(true);
		this.tblQuality.checkParsed();
		this.tblQuality.removeRows();
		for (int i = 0; i < col.size(); i++) {
			updateRowDataForQualityCheckPoint(tblQuality.addRow(), col.get(i));
		}
	}

	/**
	 * @discription <初始化形象进度表格数据>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/27>
	 */
	public void initPScheduleTable() throws BOSException {
		this.tblPSchedule.getStyleAttributes().setLocked(true);
		FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
		String taskID = String.valueOf(null == fDCScheduleTaskInfo.getSrcID() ? "" : fDCScheduleTaskInfo.getSrcID().toString());
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("name"));
		view.getSelector().add(new SelectorItemInfo("createTime"));
		view.getSelector().add(new SelectorItemInfo("imgDescription"));
		view.getSelector().add(new SelectorItemInfo("creator.person.name"));
		view.getSelector().add(new SelectorItemInfo("creator.person.number"));
		view.getSelector().add(new SelectorItemInfo("creator.person.id"));
		filter.getFilterItems().add(new FilterItemInfo("relateTask.id", taskID));
		view.setFilter(filter);
		ProjectImageCollection collection;
		collection = ProjectImageFactory.getRemoteInstance().getProjectImageCollection(view);
		this.tblPSchedule.checkParsed();
		if (collection.size() > 0) {
			ProjectImageInfo project = null;
			IRow row = null;
			for (int i = 0; i < collection.size(); i++) {
				project = collection.get(i);
				row = this.tblPSchedule.addRow();
				row.getCell("name").setValue(project.getName());
				row.getCell("creator").setValue(project.getCreator().getPerson().getName());
				row.getCell("createTime").setValue(project.getCreateTime());
				row.getCell("imgDescription").setValue(project.getImgDescription());
				row.getCell("ID").setValue(project.getId());
				row.getCell("createTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
			}
		}
		tblPSchedule.getStyleAttributes().setLocked(true);
	}
	
	/**
	 * @discription <初始化相关任务专项表格数据>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/27>
	 *             <p>
	 * @param <空>
	 * @return <返回值描述>
	 * 
	 *         modifier <空>
	 *         <p>
	 *         modifyDate <空>
	 *         <p>
	 *         modifyInfo <空>
	 *         <p>
	 * @see <相关的类>
	 */
	public void initRelatedTaskTable() throws BOSException {
		removeRowRelationTask = new CoreBaseCollection();

		/* 对前置任务设置编辑按扭 */
		actionAddRow.putValue(Action.SHORT_DESCRIPTION, "新增行");
		actionAddRow.putValue(Action.SMALL_ICON, EASResource.getIcon(IMG_BTN_ADDLINE));
		actionRemoveRow.putValue(Action.SHORT_DESCRIPTION, "删除行");
		actionRemoveRow.putValue(Action.SMALL_ICON, EASResource.getIcon(IMG_BTN_REMOVELINE));

		/* 创建新增行按扭 */
		KDWorkButton btnAdd = new KDWorkButton(actionAddRow);
		btnAdd.setEnabled(true);
		btnAdd.setName(BTN_ADDROW);
		btnAdd.setText("新增行");
		btnAdd.setVisible(true);
		btnAdd.setSize(21, 8);

		/* 创建删除行按扭 */
		KDWorkButton btnRemove = new KDWorkButton(actionRemoveRow);
		btnRemove.setEnabled(true);
		btnRemove.setName(BTN_REMOVEROW);
		btnRemove.setText("删除行");
		btnRemove.setVisible(true);
		btnRemove.setSize(21, 8);
		/*
		 * by ZhouHangjian
		 */
		String state = baseEditUI.getOprtState();
		if (!(state.equals(STATE_VIEW))) {
			/* 为面板添加按扭 */
			this.kDContainerSpe.addButton(btnAdd);
			this.kDContainerSpe.addButton(btnRemove);
		}

		if (!isNeedShowBtn()) {

			btnAdd.setVisible(false);
			btnRemove.setVisible(false);

		}
		this.tblSpecially.checkParsed();
		this.tblSpecially.getStyleAttributes().setLocked(true);
		this.tblSpecially.getColumn("name").getStyleAttributes().setLocked(false);
		this.tblSpecially.getColumn("name").setRequired(true);

		final KDBizPromptBox f7PrmpTask = new KDBizPromptBox();
		f7PrmpTask.setQueryInfo("com.kingdee.eas.fdc.schedule.app.FDCScheduleTaskReaQuery");
		f7PrmpTask.setDisplayFormat("$name$");
		f7PrmpTask.setEditFormat("$name$");
		f7PrmpTask.setCommitFormat("$name$");
		f7PrmpTask.setRequired(true);
		f7PrmpTask.setEditable(false);
		f7PrmpTask.getQueryAgent().getQueryInfo().setAlias("相关专项任务");

		EntityViewInfo view = new EntityViewInfo();
		final FilterInfo filter = new FilterInfo();

		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo("schedule.projectSpecial.id");
		sorter.add(si);
		si = new SorterItemInfo("longnumber");
		sorter.add(si);

		view.setSorter(sorter);

		// Set rootIDs = new HashSet();
		// ScheduleHelper.getAllRootIDs((FDCScheduleTaskInfo)
		// selectTask.getScheduleTaskInfo(),rootIDs);

		/* 根据当前项目过滤 */
		if (null != getUIContext().get("Owner")) {
			FDCScheduleBaseEditUI fDCScheduleBaseEditUI = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
			CurProjectInfo currentProjectInfo = (CurProjectInfo) fDCScheduleBaseEditUI.prmtCurproject.getValue();
			// if(rootIDs.size()>0){
			// filter.getFilterItems().add(new FilterItemInfo("id",
			// rootIDs,CompareType.NOTINCLUDE));
			// }

			filter.appendFilterItem("schedule.project.id", currentProjectInfo.getId());
		}
		/* 根据最新版本过滤 */
		filter.appendFilterItem("schedule.isLatestVer", "1");
		if (getUIContext().get("Owner") instanceof MainScheduleEditUI || getUIContext().get("Owner") instanceof MainScheduleExecuteUI) {
			filter.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial.id", null, CompareType.NOTEQUALS));
			// filter.getFilterItems().add(new
			// FilterItemInfo("dependMainTaskID.id", null,CompareType.EQUALS));
			this.kDPanelRelevanceSpecialTask.setVisible(true);
		}
		view.setFilter(filter);
		f7PrmpTask.setEntityViewInfo(view);
		f7PrmpTask.getQueryAgent().resetRuntimeEntityView();
		f7PrmpTask.setSelectorCollection(getSpecialTaskSelector());
		f7PrmpTask.setEditable(false);
		KDTDefaultCellEditor f7EditorPrmpTask = new KDTDefaultCellEditor(f7PrmpTask);
		this.tblSpecially.getColumn("name").setEditor(f7EditorPrmpTask);
		this.tblSpecially.getColumn("name").setRequired(true);

		f7PrmpTask.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				if (validateF7PrmpTaskIsExist(f7PrmpTask)) {
					setReloationTaskRowValue(f7PrmpTask);

				} else {
					f7PrmpTask.setValue(null);
				}
			}
		});

		/* 初始化表格数据 */
		FDCScheduleTaskInfo scheduleTaskInfo = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
		EntityViewInfo taskView = new EntityViewInfo();
		taskView.setSelector(getSpecialTaskSelector());
		FilterInfo taskFilter = new FilterInfo();
		taskFilter.appendFilterItem("dependMainTaskID.id", scheduleTaskInfo.getSrcID());
		taskFilter.appendFilterItem("schedule.isLatestVer", Boolean.TRUE);

		taskView.setFilter(taskFilter);
		FDCScheduleTaskCollection fDCScheduleTaskCollection = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(taskView);
		for (int k = 0; k < fDCScheduleTaskCollection.size(); k++) {
			FDCScheduleTaskInfo fDCScheduleTaskInfo = fDCScheduleTaskCollection.get(k);
			IRow newRow = this.tblSpecially.addRow();
			newRow.getCell("name").setValue(fDCScheduleTaskInfo);

			if (null != fDCScheduleTaskInfo.getSchedule().getProjectSpecial()) {
				newRow.getCell("projectSpeci").setValue(fDCScheduleTaskInfo.getSchedule().getProjectSpecial().getName());
			}
			newRow.getCell("planStartDate").setValue(fDCScheduleTaskInfo.getStart());
			newRow.getCell("planEndDate").setValue(fDCScheduleTaskInfo.getEnd());
			newRow.getCell("projectTime").setValue(fDCScheduleTaskInfo.getEffectTimes());
			if (null != fDCScheduleTaskInfo.getAdminPerson()) {
				newRow.getCell("responPerson").setValue(fDCScheduleTaskInfo.getAdminPerson().getName());
			}
			if (null != fDCScheduleTaskInfo.getAdminDept()) {
				newRow.getCell("responDept").setValue(fDCScheduleTaskInfo.getAdminDept().getName());
			}
		}
	}

	private SelectorItemCollection getSpecialTaskSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("longnumber");
		sic.add("name");
		sic.add("schedule.projectSpecial.id");
		sic.add("schedule.projectSpecial.name");
		sic.add("start");
		sic.add("end");
		sic.add("actualStartDate");
		sic.add("actualEndDate");
		sic.add("effectTimes");
		sic.add("adminPerson.id");
		sic.add("adminPerson.name");
		sic.add("adminPerson.number");
		sic.add("adminDept.id");
		sic.add("adminDept.name");
		sic.add("adminDept.number");
		sic.add("parent.id");
		sic.add("parent.name");
		sic.add("parent.number");
		return sic;
	}
	
	/**
	 * @discription <判断该前置任务是否已经被选>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/25>
	 *             <p>
	 * @param <空>
	 * @return <返回值描述>
	 * 
	 *         modifier <空>
	 *         <p>
	 *         modifyDate <空>
	 *         <p>
	 *         modifyInfo <空>
	 *         <p>
	 * @see <相关的类>
	 */
	public boolean validateF7PrmpTaskIsExist(KDBizPromptBox f7PrmpTask) {
		int rowIndex = this.tblSpecially.getSelectManager().getActiveRowIndex();
		Object taskObj = f7PrmpTask.getValue();
		if (!(taskObj instanceof FDCScheduleTaskInfo)) {
			return true;
		}
		FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) taskObj;
		for (int i = 0; i < this.tblSpecially.getRowCount(); i++) {
			if (i == rowIndex) {
				continue;
			}
			IRow row = this.tblSpecially.getRow(i);
			Object celltaskObj = row.getCell("ID").getValue();
			if (null != fDCScheduleTaskInfo && null != celltaskObj && null != fDCScheduleTaskInfo.getId()
					&& fDCScheduleTaskInfo.getId().toString().equals(celltaskObj.toString())) {
				FDCMsgBox.showInfo("该任务已经被选择！");
				tblSpecially.removeRow(rowIndex);
				return false;
			}
		}
		return true;
	}

	/**
	 * @discription <设置相关任务新增行数据>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/27>
	 *             <p>
	 * @param <空>
	 * @return <返回值描述>
	 * 
	 *         modifier <空>
	 *         <p>
	 *         modifyDate <空>
	 *         <p>
	 *         modifyInfo <空>
	 *         <p>
	 * @throws SQLException
	 * @throws BOSException
	 * @see <相关的类>
	 */
	public void setReloationTaskRowValue(KDBizPromptBox f7PrmpTask) {
		int rowIndex = this.tblSpecially.getSelectManager().getActiveRowIndex();
		if (rowIndex < 0) {
			return;
		}
		IRow row = this.tblSpecially.getRow(rowIndex);
		if (null == f7PrmpTask.getValue()) {
			return;
		}
		if (f7PrmpTask.getValue() instanceof FDCScheduleTaskInfo) {
			// 对专项任务进行校验，当前专项只要祖孙级节点与主项任务有关联就不允许进行搭接

			FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) f7PrmpTask.getValue();
			Set resultSet = new HashSet();
			Set excludeSet = new HashSet();
			// 获取当前任务的所有父级任务ID
			ScheduleHelper.getAllRootIDs(fDCScheduleTaskInfo, resultSet);
			// 可能存在已经删除掉的，但是没有更新到数据库里面的
			String id = null;
			FDCScheduleTaskInfo tempTask = null;

			// 当前未保存，但是已添加表格上的任务也要放进去
			int rowCount = tblSpecially.getRowCount();
			CoreBaseCollection myRemoveRelationTask = (CoreBaseCollection) removeRowRelationTask.clone();
			for (int i = 0; i < rowCount; i++) {
				id = tblSpecially.getCell(i, "ID").getValue() == null ? null : tblSpecially.getCell(i, "ID").getValue().toString();
				if (!StringUtils.isEmpty(id)) {

					if (!id.equals(fDCScheduleTaskInfo.getId().toString())) {
						excludeSet.add(id);
					}

					for (int j = 0; j < myRemoveRelationTask.size(); j++) {
						tempTask = (FDCScheduleTaskInfo) myRemoveRelationTask.get(j);

						if (tempTask.getId().toString().equals(id)) {
							myRemoveRelationTask.removeObject(j);
						}
					}
				}

			}

			FDCScheduleTaskCollection cols = null;
			try {
				cols = ScheduleHelper.getAlreayRelateMainTask(fDCScheduleTaskInfo, resultSet, excludeSet);

				if (myRemoveRelationTask != null && myRemoveRelationTask.size() > 0) {
					FDCScheduleTaskInfo otherInfo = null;
					for (Iterator it = myRemoveRelationTask.iterator(); it.hasNext();) {
						tempTask = (FDCScheduleTaskInfo) it.next();
						for (int i = 0; i < cols.size(); i++) {
							otherInfo = cols.get(i);
							if (otherInfo.getId().equals(tempTask.getId())) {
								cols.remove(otherInfo);
							}
						}
					}
				}
			} catch (BOSException e1) {
				handUIException(e1);
			}

			if (cols != null && cols.size() > 0) {
				StringBuffer strMsg = new StringBuffer("详细信息：\n");
				strMsg.append("专项任务名称 \t   \t 主项任务名称 \n");
				for (Iterator it = cols.iterator(); it.hasNext();) {
					tempTask = (FDCScheduleTaskInfo) it.next();
					strMsg.append(tempTask.getName());
					strMsg.append("\t\t");
					strMsg.append(tempTask.getDependMainTaskID().getName());
					strMsg.append("\n");
				}
				strMsg.append("不能再次进行搭接！");
				FDCMsgBox.showDetailAndOK(this, "您选择的专项任务的父级任务或者子级任务已经与主项搭接", strMsg.toString(),
						com.kingdee.bos.ctrl.kdf.fd2.gui.util.MsgBox.ICONINFORMATION);
				tblSpecially.removeRow(rowIndex);
				abort();
			}

			if (null != fDCScheduleTaskInfo.getSchedule().getProjectSpecial()) {
				row.getCell("projectSpeci").setValue(fDCScheduleTaskInfo.getSchedule().getProjectSpecial().getName());
			}
			row.getCell("planStartDate").setValue(fDCScheduleTaskInfo.getPlanStart());
			row.getCell("planEndDate").setValue(fDCScheduleTaskInfo.getPlanEnd());
			row.getCell("projectTime").setValue(fDCScheduleTaskInfo.getEffectTimes());
			if (null != fDCScheduleTaskInfo.getAdminPerson()) {
				row.getCell("responPerson").setValue(fDCScheduleTaskInfo.getAdminPerson().getName());
			}
			if (null != fDCScheduleTaskInfo.getAdminDept()) {
				row.getCell("responDept").setValue(fDCScheduleTaskInfo.getAdminDept().getName());
			}
			row.getCell("ID").setValue(fDCScheduleTaskInfo.getId());
		}
	}

	/**
	 * 相关专项新增行
	 */
	public void actionAddRow_actionPerformed(ActionEvent e) throws Exception {
		// 创建新的一行
		this.tblSpecially.addRow();
	}

	/**
	 * 相关专项删除行
	 */
	public void actionRemoveRow_actionPerformed(ActionEvent e) throws Exception {
		int seletedRowIndex = this.tblSpecially.getSelectManager().getActiveRowIndex();
		if (seletedRowIndex < 0) {
			FDCMsgBox.showInfo("请选择需要删除的数据！");
			return;
		}
		int result = FDCMsgBox.showConfirm2("是否删除？");
		if (result == 2) {
			return;
		}
		IRow row = this.tblSpecially.getRow(seletedRowIndex);
		Object nameObj = row.getCell("name").getValue();
		if (null != nameObj && nameObj instanceof FDCScheduleTaskInfo) {
			FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) nameObj;
			fDCScheduleTaskInfo.setDependMainTaskID(null);
			removeRowRelationTask.add(fDCScheduleTaskInfo);
		}

		this.tblSpecially.removeRow(seletedRowIndex);
	}

	/**
	 * @discription <保存相关专项任务表格数据>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/27>
	 *             <p>
	 * @param <空>
	 * @return <返回值描述>
	 * 
	 *         modifier <空>
	 *         <p>
	 *         modifyDate <空>
	 *         <p>
	 *         modifyInfo <空>
	 *         <p>
	 * @throws BOSException
	 * @throws EASBizException
	 * @see <相关的类>
	 */
	public void saveRelatedTask() throws EASBizException, BOSException {
		FDCScheduleTaskFactory.getRemoteInstance().updateBatchData(removeRowRelationTask);
		
		/* modified by zhaoqin for R140226-0252 on 2014/03/13 start */
		//int rowIndex = this.tblSpecially.getSelectManager().getActiveRowIndex();
		int rowIndex = this.tblSpecially.getRowCount();
		if (rowIndex <= 0) {
			return;
		}
		FDCScheduleTaskInfo scheduleTaskInfo = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
		FDCScheduleTaskInfo mainTaskInfo = new FDCScheduleTaskInfo();
		mainTaskInfo.setId(BOSUuid.read(scheduleTaskInfo.getSrcID()));
		FDCScheduleTaskCollection coll = new FDCScheduleTaskCollection();
		CoreBaseCollection coreBaseCollection = new CoreBaseCollection();
		for (int r = 0; r < this.tblSpecially.getRowCount(); r++) {
			IRow row = this.tblSpecially.getRow(r);

			/* 判断该行是否为空行 */
			Object taskObj = row.getCell("name").getValue();
			if (null != taskObj && taskObj instanceof FDCScheduleTaskInfo) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) taskObj;
				/*
				FDCScheduleTaskInfo newInfo = new FDCScheduleTaskInfo();
				newInfo.setId(BOSUuid.read(scheduleTaskInfo.getSrcID()));
				fDCScheduleTaskInfo.setDependMainTaskID(mainTaskInfo);
				coreBaseCollection.add(fDCScheduleTaskInfo);
				*/
				coll.add(fDCScheduleTaskInfo);
			} else {
				FDCMsgBox.showWarning(this, "相关专项任务页签中，任务名称不能为空。");
				SysUtil.abort();
			}
		}
		
		getSubSpecialScheduleTask(coll, mainTaskInfo, coreBaseCollection);
		/* modified by zhaoqin for R140226-0252 on 2014/03/13 end */
		
		/* 先指更新被删除的数据 */

		/* 批量更新 */
		FDCScheduleTaskFactory.getRemoteInstance().updateBatchData(coreBaseCollection);
	}
	
	/**
	 * 查询非叶子节点专项任务下的子任务
	 * @param parentTaskInfo
	 * @param mainTaskInfo 主项任务
	 * 
	 * @author RD_zhaoqin
	 * @date 2014/03/13
	 */
	private void getSubSpecialScheduleTask(FDCScheduleTaskCollection scheduleTaskColl, 
			FDCScheduleTaskInfo mainTaskInfo, CoreBaseCollection coreBaseCollection) 
			throws EASBizException, BOSException {
		if(null == scheduleTaskColl || 0 == scheduleTaskColl.size())
			return;
		Set taskIDs = new HashSet();
		for(int i = 0; i < scheduleTaskColl.size(); i++) {
			FDCScheduleTaskInfo scheduleTaskInfo = scheduleTaskColl.get(i);
			if(!scheduleTaskInfo.isIsLeaf()) {
				taskIDs.add(scheduleTaskInfo.getId().toString());
			}
			scheduleTaskInfo.setDependMainTaskID(mainTaskInfo);
			coreBaseCollection.add(scheduleTaskInfo);
		}
		if(0 == taskIDs.size())
			return;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id", taskIDs, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("isLeaf");
		FDCScheduleTaskCollection coll = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
		getSubSpecialScheduleTask(coll, mainTaskInfo, coreBaseCollection);
	}
	

	/**
	 * @discription <初始化相关合同表格数据>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/28>
	 */
	public void initReloationContractTable() throws BOSException {
		/*
		 * 对前置任务设置编辑按扭 没办法，这兄弟在这里面直接加的按钮，只有在这里面控制了。。。。
		 */

		if (isNeedShowBtn()) {
			actionAddContractRow.putValue(Action.SHORT_DESCRIPTION, "新增行");
			actionAddContractRow.putValue(Action.SMALL_ICON, EASResource.getIcon(IMG_BTN_ADDLINE));
			actionRelevance.putValue(Action.SHORT_DESCRIPTION, "关联");
			actionRelevance.putValue(Action.SMALL_ICON, EASResource.getIcon(IMG_BTN_ADDLINE));
			actionRelevanceCancel.putValue(Action.SHORT_DESCRIPTION, "取消关联");
			actionRelevanceCancel.putValue(Action.SMALL_ICON, EASResource.getIcon(IMG_BTN_REMOVELINE));

			/* 创建新增合同行按扭 */
			KDWorkButton btnAddContract = new KDWorkButton(actionAddContractRow);
			btnAddContract.setEnabled(true);
			btnAddContract.setName(BTN_ADDROW);
			btnAddContract.setText("新增行");
			btnAddContract.setVisible(true);
			btnAddContract.setSize(21, 8);

			/* 创建新增行按扭 */
			KDWorkButton btnAdd = new KDWorkButton(actionRelevance);
			btnAdd.setEnabled(true);
			btnAdd.setName(BTN_ADDROW);
			btnAdd.setText("关联");
			btnAdd.setVisible(true);
			btnAdd.setSize(21, 8);

			/* 创建删除行按扭 */
			KDWorkButton btnRemove = new KDWorkButton(actionRelevanceCancel);
			btnRemove.setEnabled(true);
			btnRemove.setName(BTN_REMOVEROW);
			btnRemove.setText("取消关联");
			btnRemove.setVisible(true);
			btnRemove.setSize(21, 8);

			/*
			 * by ZhouHangjian
			 */
			String state = baseEditUI.getOprtState();
			if (!(state.equals(STATE_VIEW))) {
				/* 为面板添加按扭 */
				// this.kDContainerCon.addButton(btnAddContract);
				this.kDContainerCon.addButton(btnAdd);
				this.kDContainerCon.addButton(btnRemove);
			}

			/*
			 * 为关联按扭添加点击事件
			 */
			btnAdd.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					KDBizPromptBox f7PrmpContractNumber = getF7SelectedContractInfo();
					if (validateF7ContractIsExist(f7PrmpContractNumber)) {
						try {
							setReaContractRowValue(f7PrmpContractNumber);
						} catch (BOSException e1) {
							logger.error(e1.getMessage());
						}
					}
				}

			});
		}

		this.tblContract.checkParsed();
		this.tblContract.getStyleAttributes().setLocked(true);
		this.tblContract.getColumn("number").getStyleAttributes().setLocked(false);
		this.tblContract.getColumn("number").setRequired(true);
		this.tblContract.getColumn("signPartyB").setWidth(200);
		this.tblContract.getColumn("responPerson").setWidth(120);
		IColumn col = this.tblContract.addColumn(7);
		col.setKey("tcId");
		col.getStyleAttributes().setHided(true);
		IColumn colContrInfo = this.tblContract.addColumn(8);
		colContrInfo.setKey("contractInfo");
		colContrInfo.getStyleAttributes().setHided(true);

		this.tblPay.checkParsed();
		this.tblPay.getColumn("currentMonthPay").setWidth(120);

		/* 设置选择行 */
		this.tblContract.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		this.tblPay.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);

		this.tblPay.checkParsed();

		/* 为表格添加选择行的事件 */
		this.tblContract.addKDTMouseListener(new KDTMouseListener() {

			public void tableClicked(KDTMouseEvent kdtmouseevent) {
				setMonthPayPlanTableData();
				/* 查看合同 */
				if (kdtmouseevent.getClickCount() == 2) {
					contractView();
				}
			}

		});

		/* 初始化合同列表分录数据 */
		this.tblContract.removeRows();
		FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
		List lstRelCon = (List) fDCScheduleTaskInfo.get("relCon");
		if (lstRelCon != null) {
			for (int i = 0; i < lstRelCon.size(); i++) {
				FDCSchTaskWithContractInfo fDCSchTaskWithContractInfo = (FDCSchTaskWithContractInfo) lstRelCon.get(i);
				ContractBillInfo contractBillInfo = fDCSchTaskWithContractInfo.getContract();
				IRow row = this.tblContract.addRow();
				row.getCell("contractInfo").setValue(contractBillInfo);
				row.getCell("number").setValue(contractBillInfo.getNumber());
				row.getCell("name").setValue(contractBillInfo.getName());
				row.getCell("amount").setValue(contractBillInfo.getOriginalAmount());
				row.getCell("responPerson").setValue(contractBillInfo.getRespPerson());
				row.getCell("responDept").setValue(contractBillInfo.getRespDept());
				row.getCell("signPartyB").setValue(contractBillInfo.getPartB());
				row.getCell("ID").setValue(contractBillInfo.getId());
				row.getCell("tcId").setValue(fDCSchTaskWithContractInfo);
			}
		}
	}

	/**
	 * @discription <关联按扭弹击F7，获得被选择的值>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/10/09>
	 */
	public KDBizPromptBox getF7SelectedContractInfo() {

		/* 为合同编码列绑定F7 */
		KDBizPromptBox f7PrmpContractNumber = new KDBizPromptBox();
		f7PrmpContractNumber.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7FDCScheduleContractQuery");
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("state", "4AUDITTED");
		FDCScheduleBaseEditUI fDCScheduleBaseEditUI = ((FDCScheduleBaseEditUI) getUIContext().get("Owner"));
		CurProjectInfo currentProjectInfo = (CurProjectInfo) fDCScheduleBaseEditUI.prmtCurproject.getValue();
		filterInfo.appendFilterItem("curProject.id", currentProjectInfo.getId());
		viewInfo.setFilter(filterInfo);
		SelectorItemCollection sic = new SelectorItemCollection();
		f7PrmpContractNumber.setEntityViewInfo(viewInfo);
		f7PrmpContractNumber.setSelectorCollection(sic);
		f7PrmpContractNumber.setDataBySelector();

		return f7PrmpContractNumber;
	}

	/**
	 * @discription <合同查看>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/29>
	 */
	public void contractView() {
		/* 判断是否选中行 */
		int rowIndex = this.tblContract.getSelectManager().getActiveRowIndex();
		if (rowIndex < 0) {
			return;
		}
		/* 判断选中行有没有数据 */
		IRow row = this.tblContract.getRow(rowIndex);
		/*
		 * by ZhouHangjian (修改:新增行数据为空双击中断BUG )
		 */
		Object object = row.getCell("ID").getValue();
		if (null != object) {
			try {
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, object.toString());
				IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
				IUIWindow uiWindow = uiFactory.create(ContractBillEditUI.class.getName(), uiContext, null, OprtState.VIEW, WinStyle.SHOW_TITLEPANEL);
				uiWindow.show();
			} catch (Exception ex) {
				handUIException(ex);
				// logger.error(ex.getMessage());
			}
		}
	}

	/**
	 * @discription <为月度付款计划表格初始化数据>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/29>
	 */
	public void setMonthPayPlanTableData() {
		/* 判断是否选中行 */
		int rowIndex = this.tblContract.getSelectManager().getActiveRowIndex();
		if (rowIndex < 0) {
			return;
		}

		/* 判断选中行有没有数据 */
		IRow row = this.tblContract.getRow(rowIndex);
		if (null == row.getCell("ID").getValue() || "".equals(row.getCell("ID").getValue().toString().trim())) {
			return;
		}

		/* 清空所有行 */
		this.tblPay.removeRows();

		/* 设置表列属性 */
		this.tblPay.getStyleAttributes().setLocked(true);

		try {
			/* 获得月度付款计划分录数据 */
			String contractId = row.getCell("ID").getValue().toString().trim();

			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("*"));
			sic.add(new SelectorItemInfo("head.*"));
			sic.add(new SelectorItemInfo("entrys.*"));
			view.setSelector(sic);
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("contract.id", contractId);
			filter.appendFilterItem("head.state", FDCBillStateEnum.PUBLISH_VALUE);
			view.setFilter(filter);
			SimpleDateFormat fmtMonth = new SimpleDateFormat("yyyy年MM月");
			FDCDepConPayPlanContractCollection payPlanCol = FDCDepConPayPlanContractFactory.getRemoteInstance().getFDCDepConPayPlanContractCollection(view);

			// 循环添加月度付款计划
			for (int k = 0; k < payPlanCol.size(); k++) {
				FDCDepConPayPlanContractInfo payPlanInfo = payPlanCol.get(k);
				BigDecimal planPay = BigDecimal.valueOf(0);
				FDCDepConPayPlanContractEntryCollection planEntrys = payPlanInfo.getEntrys();
				for (int f = 0; f < planEntrys.size(); f++) {
					FDCDepConPayPlanContractEntryInfo planEntryInfo = planEntrys.get(f);
					IRow payRow = this.tblPay.addRow();
					payRow.getCell("ID").setValue(planEntryInfo.getId());
					payRow.getCell("payMonth").setValue(fmtMonth.format(planEntryInfo.getMonth()));
					payRow.getCell("amount").setValue(payPlanInfo.getLastMonthPayable());
					payRow.getCell("payMoneyY").setValue(payPlanInfo.getLastMonthPay());
					payRow.getCell("payMoneyN").setValue(payPlanInfo.getLastMonthNopay());
					planPay = planEntryInfo.getPlanPay();
					payRow.getCell("currentMonthPay").setValue(planPay);
					payRow.getCell("currentPayY").setValue(getLocalAmt(contractId, planEntryInfo.getMonth()));
				}
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * @discription <获得已付金额>
	 * @author <Xiaolong Luo>
	 * @param contractId
	 * @param planMonth
	 * @createDate <2011/08/29>
	 */
	public BigDecimal getLocalAmt(String contractId, Date planMonth) {
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select isnull(sum(pb.FProjectPriceInContract),0) + isnull(sum(ppe.FLocAdvance),0) ");
		sql.appendSql(" from T_CAS_PaymentBill as pb ");
		sql.appendSql(" left join T_FNC_PaymentPrjPayEntry as ppe ");
		sql.appendSql(" on pb.FPrjPayEntryID = ppe.FID ");
		sql.appendSql(" where pb.FContractbillId =? ");
		sql.appendSql(" and pb.fbillstatus = 15 ");
		sql.appendSql(" and pb.FBizDate < ?");
		sql.addParam(contractId);
		// Builder自动忽略时分秒，此处取下个月第一天凌晨
		sql.addParam(FDCDateHelper.getDayBegin(FDCDateHelper.getNextMonth(FDCDateHelper.getFirstDayOfMonth(planMonth))));
		try {
			IRowSet rs1 = sql.executeQuery();
			while (rs1.next()) {
				BigDecimal payed = rs1.getBigDecimal(1);
				return payed;
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return FDCHelper.ZERO;
	}

	/**
	 * @discription <设置相关合同行数据>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/29>
	 */
	public void setReaContractRowValue(KDBizPromptBox f7PrmpContractNumber) throws BOSException {
		/* 获得选择的合同对象 */
		if (!(f7PrmpContractNumber.getValue() instanceof ContractBillInfo)) {
			return;
		}
		ContractBillInfo contractBillInfo = (ContractBillInfo) f7PrmpContractNumber.getValue();

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("contractType.*"));
		sic.add(new SelectorItemInfo("respPerson.*"));
		sic.add(new SelectorItemInfo("respDept.*"));
		sic.add(new SelectorItemInfo("partB.*"));
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("id", contractBillInfo.getId());
		view.setFilter(filter);
		ContractBillCollection contractBillCollection = ContractBillFactory.getRemoteInstance().getContractBillCollection(view);
		contractBillInfo = contractBillCollection.get(0);
		/* 设置各列的值 */
		IRow row = tblContract.addRow();
		row.getCell("contractInfo").setValue(contractBillInfo);
		row.getCell("number").setValue(contractBillInfo.getNumber());
		row.getCell("name").setValue(contractBillInfo.getName());
		row.getCell("amount").setValue(contractBillInfo.getOriginalAmount());
		row.getCell("responPerson").setValue(contractBillInfo.getRespPerson());
		row.getCell("responDept").setValue(contractBillInfo.getRespDept());
		row.getCell("signPartyB").setValue(contractBillInfo.getPartB());
		row.getCell("ID").setValue(contractBillInfo.getId());
	}

	/**
	 * @discription <相关合同页签：验证该合同是否已被选择>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/29>
	 */
	private boolean validateF7ContractIsExist(KDBizPromptBox f7PrmpContractNumber) {
		Object taskObj = f7PrmpContractNumber.getValue();
		if (!(taskObj instanceof ContractBillInfo)) {
			return false;
		}
		ContractBillInfo contractBillInfo = (ContractBillInfo) taskObj;
		for (int i = 0; i < this.tblContract.getRowCount(); i++) {
			IRow row = this.tblContract.getRow(i);
			Object celltaskObj = row.getCell("contractInfo").getValue();
			ContractBillInfo billInfo = (ContractBillInfo) celltaskObj;
			if (null != taskObj && null != celltaskObj && contractBillInfo.getId().toString().equals(billInfo.getId().toString())) {
				FDCMsgBox.showInfo("该合同已经被选择！");
				return false;
			}
		}
		return true;
	}

	/**
	 * 关联合同
	 */
	public void actionRelevance_actionPerformed(ActionEvent e) throws Exception {

	}

	/**
	 * @discription <与合同相关联>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/10/09>
	 */
	public void relevanceContract(IRow row) {
		Object idObj = row.getCell("contractInfo").getValue();
		if (null == idObj || "".equals(idObj.toString().trim())) {
			return;
		}
		try {
			FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
			FDCSchTaskWithContractInfo fDCSchTaskWithContractInfo = new FDCSchTaskWithContractInfo();
			fDCSchTaskWithContractInfo.setId(BOSUuid.create((new FDCSchTaskWithContractInfo()).getBOSType()));
			fDCSchTaskWithContractInfo.setTask(fDCScheduleTaskInfo);
			fDCSchTaskWithContractInfo.setContract((ContractBillInfo) idObj);
			List lstRelCon = (List) fDCScheduleTaskInfo.get("relCon");
			lstRelCon.add(fDCSchTaskWithContractInfo);
			row.getCell("tcId").setValue(fDCSchTaskWithContractInfo);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	/**
	 * 取消关联合同
	 */
	public void actionRelevanceCancel_actionPerformed(ActionEvent e) throws Exception {
		int[] rows = KDTableUtil.getSelectedRows(tblContract);
		for (int i = rows.length - 1; i >= 0; i--) {
			tblContract.removeRow(rows[i]);
		}
	}

	/**
	 * 新增合同行
	 */
	public void actionAddContractRow_actionPerformed(ActionEvent e) throws Exception {
		// 创建新的一行
		this.tblContract.addRow();
	}

	public boolean isNeedShowBtn() {
		if (!(getUIContext().get("Owner") instanceof MainScheduleExecuteUI) && !(getUIContext().get("Owner") instanceof SpecialScheduleExecuteUI)) {
			return true;
		}
		return false;
	}

	public boolean destroyWindow() {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map destroyWindowMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "destroyWindow");
		// ////////////////////////////////////////////////////////////////////////

		if (isFromExecuteUI()) {
			try {
				FDCScheduleBaseEditUI ui = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
				/* modified by zhaoqin for R140925-0073 on 2015/01/19 start */
				if (ui instanceof MainScheduleExecuteUI) {
					FDCScheduleExecuteEnum item = 
						(FDCScheduleExecuteEnum)((MainScheduleExecuteUI)ui).kDComTaskFilter.getSelectedItem();
					if(null != item && !item.equals(FDCScheduleExecuteEnum.TASK_ALL)) {
						((MainScheduleExecuteUI) ui).whenComTaskFilterChange();
					}
				} else if (ui instanceof SpecialScheduleExecuteUI) {
					FDCScheduleExecuteEnum item = 
						(FDCScheduleExecuteEnum)((SpecialScheduleExecuteUI)ui).kDComboBox1.getSelectedItem();
					if(null != item && !item.equals(FDCScheduleExecuteEnum.TASK_ALL)) {
						((SpecialScheduleExecuteUI) ui).whenComTaskFilterChange();
					}
				}
				/* modified by zhaoqin for R140925-0073 on 2015/01/19 end */
			} catch (Exception e) {
				handUIException(e);
			}
		}
		boolean flag = super.destroyWindow();

		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "destroyWindow", destroyWindowMap);
		// ////////////////////////////////////////////////////////////////////////

		return flag;
	}

	public void actionSaveReport_actionPerformed(ActionEvent e) throws Exception {
		/* modified by zhaoqin for 联发进度任务 on 2014/10/14 start */
		//解决：主项计划，执行填报，完成进度超过范围后，点击保存弹出提示，进度汇报却多增加了一条信息
		if(!isCmptPerGood) {
			/* modified by zhaoqin for R141027-0008 on 2015/01/16 */
			isCmptPerGood = true;
			SysUtil.abort();
		}
		/* modified by zhaoqin for 联发进度任务 on 2014/10/14 end */
		
		// 验证必录入
		if (kdDpActualStartDate.isEnabled() && kdDpActualStartDate.isRequired()) {
			FDCClientVerifyHelper.verifyEmpty(this, kdDpActualStartDate);
		}
		if (kdDpFinishDate.isEnabled() && kdDpFinishDate.isRequired()) {
			FDCClientVerifyHelper.verifyEmpty(this, kdDpFinishDate);
		}
		if (txtCompletePercent.isEnabled() && txtCompletePercent.isRequired()) {
			FDCClientVerifyHelper.verifyEmpty(this, txtCompletePercent);
		}
		
		// 组织进度汇报数据
		FDCScheduleTaskInfo task = getCurrentTask();

		ScheduleTaskProgressReportInfo report = new ScheduleTaskProgressReportInfo();
		report.setCompleteAmount(FDCHelper.toBigDecimal(this.txtWorkload.getNumberValue()));
		report.setCompletePrecent(FDCHelper.toBigDecimal(this.txtCompletePercent.getNumberValue()));
		report.setRelateTask(task);
		report.setDescription(this.txtDesciption.getText());
		report.setRealStartDate(kdDpActualStartDate.getSqlDate());
		report.setSrcRelateTask(task.getSrcID());
		if (report.getCompletePrecent().compareTo(FDCHelper.ONE_HUNDRED) == 0) {
			report.setRealEndDate(DateTimeUtils.truncateDate(kdDpFinishDate.getSqlDate()));
			task.setActualEndDate(DateTimeUtils.truncateDate(kdDpFinishDate.getSqlDate()));
			report.setIsComplete(true);
		} else {
			report.setIntendEndDate(DateTimeUtils.truncateDate(kdDpFinishDate.getSqlDate()));
			task.setIntendEndDate(DateTimeUtils.truncateDate(kdDpFinishDate.getSqlDate()));
		}
		if (SysContext.getSysContext().getCurrentUserInfo().getPerson() != null)
			report.setReportor(SysContext.getSysContext().getCurrentUserInfo().getPerson());
		report.setAmount(FDCHelper.subtract(report.getCompletePrecent(),task.getComplete()));
		report.setReportDate(new Date());
		// 校验
//		if (report.isIsComplete() && report.getRealStartDate().after(report.getRealEndDate())) {
		if (report.isIsComplete() && days (report.getRealStartDate(),report.getRealEndDate()) < 0) {
			FDCMsgBox.showError("实际开始日期不能晚于实际完成日期！");
			abort();
		}
		if (report.getCompletePrecent().compareTo(task.getComplete()) < 0) {
			FDCMsgBox.showDetailAndOK(this, "本次汇报进度不能小于上次汇报！", "本次汇报进度：" + report.getCompletePrecent() + "\n上次汇报进度：" + task.getComplete(), 2);
			abort();
		}

		IScheduleTaskProgressReport scheduleReport = ScheduleTaskProgressReportFactory.getRemoteInstance();
		ScheduleTaskProgressReportInfo reportInfo = scheduleReport.getScheduleTaskProgressReportInfo(scheduleReport.save(report));
		addScheduleReportTableRow(reportInfo);
		changeReportControl(reportInfo);
		
		// 保存成功后，更新当前任务的进度百分比，下次判断时以更新后的值为准 Added owen_wen 2013-09-9
		task.setComplete(report.getCompletePrecent());
	}
	public static int days(Date starDate,Date endDate){
		long a = truncateDate(endDate).getTime();
		long b = truncateDate(starDate).getTime();
		long c = a - b;
		long y = c/(1000*60*60*24);
		return Integer.parseInt(y + "");
	}
	public static Date truncateDate(Date dt)
    {
        if(dt == null)
        {
            return null;
        } else
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            cal.set(cal.get(1), cal.get(2), cal.get(5), 0, 0, 0);
            return new Date((cal.getTimeInMillis() / 1000L) * 1000L);
        }
    }
	public FDCScheduleTaskInfo getCurrentTask() {
		return (FDCScheduleTaskInfo) this.selectTask.getScheduleTaskInfo();
	}

	public void changeReportControl(ScheduleTaskProgressReportInfo report) {
		if (report.isIsComplete()) {
			this.conIntendEndDate.setBoundLabelText("实际完成日期");
			this.kdDpActualStartDate.setValue(report.getRealStartDate());
			this.kdDpFinishDate.setValue(report.getRealEndDate());
		} else {
			this.conIntendEndDate.setBoundLabelText("预计完成日期");
			this.kdDpFinishDate.setValue(report.getIntendEndDate());
		}
		this.txtCompletePercent.setValue(null);
		this.txtWorkload.setValue(null);
	}

	protected void txtCompletePercent_dataChanged(DataChangeEvent e) throws Exception {
		if (e.getNewValue() != null) {
			BigDecimal value = FDCHelper.toBigDecimal(e.getNewValue());
			if (value.compareTo(FDCHelper.ZERO) < 0 || value.compareTo(FDCHelper.ONE_HUNDRED) > 0) {
				FDCMsgBox.showError("本次完工百分比只能为0-100之间的数值!");
				this.txtCompletePercent.setValue(e.getOldValue(), false);
				this.isCmptPerGood = false;	/* modified by zhaoqin for 联发进度任务 on 2014/10/14 */
				abort();
			}

			if (FDCHelper.toBigDecimal(value).compareTo(FDCHelper.ONE_HUNDRED) == 0) {
				this.conIntendEndDate.setBoundLabelText("实际完成日期");
				this.kdDpFinishDate.setValue(new Date());
				
				/* modified by zhaoqin for 联发进度任务 on 2014/10/15 */
				this.kdDpFinishDate.setEnabled(true);
			} else {
				this.conIntendEndDate.setBoundLabelText("预计完成日期");
				
				/* modified by zhaoqin for 联发进度任务 on 2014/10/15 */
				this.kdDpFinishDate.setEnabled(false);
			}
		}
	}

	protected void txtWorkload_dataChanged(DataChangeEvent e) throws Exception {
		FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
		BigDecimal currentWorkload = FDCHelper.toBigDecimal(this.txtWorkload.getNumberValue());
		BigDecimal total = task.getWorkLoad();
		this.txtTotalWorkload.setValue(FDCHelper.add(currentWorkload, total));
	}

	protected void kdDpActualStartDate_dataChanged(DataChangeEvent e, int field) throws Exception {
	}

	private void resetReportControl() throws BOSException {
		//获取最新的任务信息
		FDCScheduleTaskInfo task = getCurrentTask();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("schedule.id",task.getSchedule().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("srcId",Boolean.TRUE));
		view.setFilter(filter);
		FDCScheduleTaskCollection cols = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
		if (cols.size() > 0) {
			FDCScheduleTaskInfo newestTask = cols.get(0);
			task.setComplete(newestTask.getComplete());
			task.setWorkLoad(newestTask.getWorkLoad());
			task.setActualStartDate(newestTask.getActualStartDate());
			task.setActualEndDate(newestTask.getActualEndDate());
		}
	
		
		if(task.getComplete().compareTo(FDCHelper.ONE_HUNDRED)<0){
			this.conIntendEndDate.setBoundLabelText("预计完成日期");
			this.kdDpFinishDate.setValue(task.getIntendEndDate());
		}else{
			this.conIntendEndDate.setBoundLabelText("实际完成日期");
			this.kdDpFinishDate.setValue(task.getActualEndDate());
		}
		this.kdDpActualStartDate.setValue(task.getActualStartDate());
	}
	
	protected void prmtBelongSpecial_willShow(SelectorEvent e) throws Exception {
		super.prmtBelongSpecial_willShow(e);
		prmtBelongSpecial.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = new EntityViewInfo();
		FDCScheduleTaskInfo task = getCurrentTask();
		if(task != null && task.getSchedule().getProject()!= null){
			String prjId = task.getSchedule().getProject().getId().toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id",prjId));
			view.setFilter(filter);
			prmtBelongSpecial.setEntityViewInfo(view);
		}
	}
	// 选的不能再选了。
	protected void prmtCheckNode_willShow(SelectorEvent e) throws Exception {
		super.prmtCheckNode_willShow(e);
		prmtCheckNode.getQueryAgent().resetRuntimeEntityView();
		FDCScheduleTaskInfo task = getCurrentTask();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		FDCScheduleTaskCollection cols = baseEditUI.editData.getTaskEntrys();
		Set set = new HashSet();
		for(int i=0; i<cols.size();i++){
			if (cols.get(i).isIsCheckNode()) // modified by zhaoqin on 2014/06/20
				set.add(cols.get(i).getName().toString());
		}
		set.remove(task.getName().toString());
		if (set.size() > 0) // modified by zhaoqin on 2014/06/20
			filter.getFilterItems().add(new FilterItemInfo("name",set,CompareType.NOTINCLUDE));
		view.setFilter(filter);
		prmtCheckNode.setEntityViewInfo(view);
	}
	
	/**
	 * 更新 形象进度 表格中的数据
	 * @author owen_wen 2013-7-26
	 */
	public void refreshProjectImageTableData(ProjectImageInfo projectImageInfo) {
		if(projectImageInfo.getId()!=null){
			
			for (int i = 0; i < this.tblPSchedule.getRowCount(); i++) {
				if (tblPSchedule.getRow(i).getCell("ID").getValue().equals(projectImageInfo.getId())) {
					// update the row data
					IRow row = tblPSchedule.getRow(i);
					row.getCell("ID").setValue(projectImageInfo.getId());
					row.getCell("name").setValue(projectImageInfo.getName());
					row.getCell("creator").setValue(projectImageInfo.getCreator());
					row.getCell("createTime").setValue(projectImageInfo.getCreateTime());
					row.getCell("imgDescription").setValue(projectImageInfo.getImgDescription());
					return;
				}
			}
			
			// insert a new row and fill the data
			IRow row = tblPSchedule.addRow();
			row.getCell("ID").setValue(projectImageInfo.getId());
			row.getCell("name").setValue(projectImageInfo.getName());
			row.getCell("creator").setValue(projectImageInfo.getCreator());
			row.getCell("createTime").setValue(projectImageInfo.getCreateTime());
			row.getCell("imgDescription").setValue(projectImageInfo.getImgDescription());
		}
	}

	/**
	 * 更新 阶段性成果 表格中的数据 
	 * @author owen_wen 2013-7-29
	 * @throws BOSException 
	 */
	public void refreshAchievementManagerTableData(AchievementManagerInfo amInfo) throws BOSException {
		if(amInfo.getId() != null){				
			for (int i = 0; i < this.tblAchievement.getRowCount(); i++) {
				
				if (tblAchievement.getRow(i).getCell("id").getValue().equals(amInfo.getId())) {
					// update the row data
					updateRowDataForAchievementManagerInfo(tblAchievement.getRow(i), amInfo);
					return;
				}
				
			}
			
			// insert a new row and fill the data
			updateRowDataForAchievementManagerInfo(tblAchievement.addRow(), amInfo);
		}
	}
	
	private void updateRowDataForAchievementManagerInfo(IRow row, AchievementManagerInfo amInfo) throws BOSException {
	
		// 更新 “成果文档”列，多个文档用逗号分隔，最后一个文档不用逗号
		if(amInfo.getId() != null){			
			row.getCell("state").setValue(amInfo.getState().toString());
			row.getCell("name").setValue(amInfo.getName());
			row.getCell("aType").setValue(amInfo.getAchievementType());
			row.getCell("submitPerson").setValue(
					amInfo.getCreator().getPerson() == null ? amInfo.getCreator().getName() : amInfo.getCreator().getPerson().getName());
			row.getCell("submitDate").setValue(amInfo.getCreateTime());
			row.getCell("id").setValue(amInfo.getId());

			AttachmentCollection attCol = FDCAttachmentUtil.getAttachmentsInfo(amInfo.getId().toString());
			StringBuilder docNames = new StringBuilder();
			for (int i = 0; i < attCol.size(); i++) {
				docNames.append(attCol.get(i).getName());
				if (i < attCol.size() - 1) {
					docNames.append(";");
				}
			}
			row.getCell("adoc").setValue(docNames);
		}
	}
	
	public void refreshQualityCheckPointTableData(ScheduleQualityCheckPointInfo qualityCheckPointInfo) {
		if(qualityCheckPointInfo.getId() != null ){			
			for (int i = 0; i < this.tblQuality.getRowCount(); i++) {
				if (tblQuality.getRow(i).getCell("ID").getValue().equals(qualityCheckPointInfo.getId())) {
					// update the row data
					updateRowDataForQualityCheckPoint(tblQuality.getRow(i), qualityCheckPointInfo);
					return;
				}
			}
			
			// insert a new row and fill the data
			updateRowDataForQualityCheckPoint(tblQuality.addRow(), qualityCheckPointInfo);
		}
	}
	
	private void updateRowDataForQualityCheckPoint(IRow row, ScheduleQualityCheckPointInfo qualityCheckPointInfo) {
		// 质量检查项checkPoint
		row.getCell("checkPoint").setValue(qualityCheckPointInfo.getCheckPoint().toString());
		// 质量检查点
		row.getCell("checkPost").setValue(qualityCheckPointInfo.getCheckPost().toString());
		// 检查比列
		row.getCell("checkPercent").setValue(qualityCheckPointInfo.getCheckPercent());
		// 分值
		row.getCell("score").setValue(qualityCheckPointInfo.getScore());
		// 检查结果
		row.getCell("checkResult").setValue(qualityCheckPointInfo.getCheckResult());
		// 提交人
		row.getCell("subPerson").setValue(qualityCheckPointInfo.getCreator().getPerson().getName());
		// 提交日期
		row.getCell("subDate").setValue(new SimpleDateFormat("yyyy-MM-dd").format(qualityCheckPointInfo.getCreateTime()));
		row.getCell("ID").setValue(qualityCheckPointInfo.getId());
	}
}