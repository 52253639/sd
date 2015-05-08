/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
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
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.OpReportBaseHelper;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportCollection;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryCollection;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryFactory;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportFactory;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportInfo;
import com.kingdee.eas.fdc.schedule.WeekReportEnum;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * 项目周报告 编辑界面
 */
public class ProjectWeekReportEditUI extends AbstractProjectWeekReportEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectWeekReportEditUI.class);

	/** 项目工程 **/
	public CurProjectInfo curProjectInfo = null;
	/** 项目周报告 **/
	public ProjectWeekReportInfo weekReportInfo = null;

	/**
	 * output class constructor
	 */
	public ProjectWeekReportEditUI() throws Exception {
		super();
	}


	/*
	 * 本单据，不需要编码规则
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {

	}

	/*
	 * 当年度最后一周跨年的时候，JDK存在计算错误，所以本界面特别在之前的父类的pkPeriod前，增加了一个显示周控件textWeek
	 * 需要实现此计算，将原有的计算修正，并填入EditData中 修复jdk bug的逻辑如下：
	 * 首先使用Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)得到当前是本年度第几周
	 * 判断，如果是第一周，且 月份等于一月，则说明正确，否则，说明是本年度的最后一周且是跨周，其实就是下一年度的第一周
	 * 就把年度设置为下一年度，周设置为第一周 并把起始日期，设置为本周的第一天，结束日期，设置为本周的最后一天
	 * 并把本单据的number值，设置为工程项目编码+"-"+"yyyy年第ww周周报" 并把本单据的name
	 * 值，设置为工程项目名称+"-"+"yyyy年第ww周周报"
	 * 
	 * 当周期发生改变时，还需要提示用户，改变所选周期，会清空表格数据，重新获取服务器数据，调用fetchData
	 */
	protected void pkPeriod_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
	}

	public void onShow() throws Exception {
		this.menuItemAddNew.setVisible(false);
		setBtnStateByBillState();
		this.thisTable.getColumn("description").setWidth(400);
		// 加入输入限制 add by duhongming
		this.thisTable.getColumn("description").setWidth(400);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(70);
		ICellEditor edit = new KDTDefaultCellEditor(textField);
		this.thisTable.getColumn("description").setEditor(edit);
		KDFormattedTextField kdfTextField = new KDFormattedTextField();
		kdfTextField.setPrecision(10);
		kdfTextField.setScrollOffset(0);
		kdfTextField.setSelectionStart(0);
		kdfTextField.setSelectionEnd(0);
		kdfTextField.setCaretPosition(0);
		kdfTextField.setMultiplier(100);
		kdfTextField.setDataType(BigDecimal.class);
		edit = new KDTDefaultCellEditor(kdfTextField);
		this.thisTable.getColumn("completeAmount").setEditor(edit);
		this.textWeek.setRequired(true);
		textWeek.setEditable(false);
		super.onShow();
		
		// 保证工作流中和查看历史报告打开的界面，这个控件可以生效
		kDCNoShowHistory.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		// 按钮设置
		initButtonStyle();
		// 设置表头
		Date today = FDCDateHelper.getServerTimeStamp();

		int year = OpReportBaseHelper.getDateFiled(today, Calendar.YEAR);
		int week = OpReportBaseHelper.getDateFiled(today, Calendar.WEEK_OF_YEAR);

		Date firstDate = getFirstDayOfWeek(today);
		Date lastDate = getLastDayOfWeek(today);
		pkStartDate.setValue(firstDate);
		pkEndDate.setValue(lastDate);
		textWeek.setText(year + "年第" + week + "周");

		pkPeriod.addDataChangeListener(new DataChangeListener() {// 监听事件，
					// 根据选择的日期不同做数据处理
					public void dataChanged(DataChangeEvent arg0) {
						try {
							setTextWeek();
						} catch (Exception e) {
							handUIException(e);
						}
					}
				});

		if (getUIContext().get("isFromWorkflow") != null && Boolean.valueOf(getUIContext().get("isFromWorkflow").toString()).booleanValue()) {
			// 工作流查看
			
			loadExistsProjectWeekReport(editData);
		} else if (getOprtState().equals(OprtState.EDIT)) {// 编辑
			getHaveReport();
			this.txtDescription.setEnabled(false);
			this.thisTable.getStyleAttributes().setLocked(true);
			this.btnEdit.setEnabled(true);
			setBtnStateByBillState();
			
		} else if (getOprtState().equals(OprtState.VIEW)) {// 查看
			initBottn();
			if (editData.getId() != null) {
				this.pkPeriod.setEnabled(false);
			}
			if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof ProjectWeekReportListUI) {
				loadExistsProjectWeekReport(editData);
			}
		} else {//新增当前周数据
			editData.setWeek(week);
			editData.setYear(year);
			loadField();
			this.btnEdit.setEnabled(false);
			this.btnEdit.setVisible(false);
		}
		onladCtrl();
		setTableUnEnable();
		this.actionAttachment.setEnabled(true);
		this.btnAttachment.setEnabled(true);
	}
	
	private void loadHead(ProjectWeekReportInfo info) {
		textWeek.setText(info.getYear() + "年第" + info.getWeek() + "周");
		this.prmtCreator.setValue(info.getCreator());
		this.pkCreateTime.setValue(info.getCreateTime());
		this.pkStartDate.setValue(info.getStartDate());
		this.pkEndDate.setValue(info.getEndDate());
		this.txtDescription.setText(info.getDescription());
		this.comboState.setSelectedItem(info.getState());
	}

	private void initButtonStyle() {
		btnHistory.setEnabled(true);

		this.kDContainer1.add(kDPanel2);
		kDPanel2.setVisible(true);
		btnUnAudit.setEnabled(true);
		txtDescription.setMaxLength(255);
		btnAudit.setEnabled(true);
		btnAudit.setVisible(true);
		btnPrintReport.setIcon(EASResource.getIcon("imgTbtn_print"));
		btnHistory.setIcon(EASResource.getIcon("imgTbtn_archive"));
		btnLeadout.setEnabled(true);
		btnLeadout.setIcon(EASResource.getIcon("imgTbtn_output"));
		btnEdit.setEnabled(true);
		this.menuItemEdit.setEnabled(true);		
		this.prmtCreator.setDisplayFormat("$user.person.name$");
	}

	public void setTableUnEnable() {
		thisTable.checkParsed();
		thisTable.getColumn("startDate").getStyleAttributes().setLocked(true);
		thisTable.getColumn("taskName").getStyleAttributes().setLocked(true);
		thisTable.getColumn("planEndDate").getStyleAttributes().setLocked(true);
		thisTable.getColumn("planEndDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		thisTable.getColumn("respPerson").getStyleAttributes().setLocked(true);
		thisTable.getColumn("respDept").getStyleAttributes().setLocked(true);
	}

	/**
	 * @description 查看状态时按钮的状态
	 * @author 车忠伟
	 * @createDate 2011-9-20
	 * @version EAS7.0
	 */
	public void initBottn() {
		this.btnEdit.setEnabled(false);
		this.btnSave.setEnabled(false);
		this.btnSubmit.setEnabled(false);
		this.btnAudit.setEnabled(false);
		this.btnUnAudit.setEnabled(false);
		this.btnHistory.setEnabled(false);
		btnLeadout.setEnabled(false);
		/* modified by zhaoqin for R140918-0150 on 2015/01/19 */
		//btnAttachment.setEnabled(false);
		btnPrintReport.setEnabled(false);
		btnAuditResult.setEnabled(false);
	}

	/**
	 * @description 查看状态初始化表格数据
	 * @author 车忠伟
	 * @createDate 2011-9-20
	 * @version EAS7.0
	 */
	public void initTableForView(String reportId) throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("entrys.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.relateTask.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.respPerson.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.respDept.*"));
		SorterItemCollection sic = new SorterItemCollection();
		sic.add(new SorterItemInfo("entrys.planEndDate"));
		view.setSorter(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", reportId));
		view.setFilter(filter);
		ProjectWeekReportInfo weekreportInfo = ProjectWeekReportFactory.getRemoteInstance().getProjectWeekReportCollection(view).get(0);
		getInitDate(weekreportInfo);
	}
	
	private boolean isCurrentWeek(Date selectDate) throws BOSException {
		// 此处应该取服务端日期
		boolean isCurrentWeek = false;
		Date d = FDCDateHelper.getServerTimeStamp();
		int currentYear = OpReportBaseHelper.getDateFiled(d, Calendar.YEAR);
		int currentWeek = OpReportBaseHelper.getDateFiled(d, Calendar.WEEK_OF_YEAR);

		int selectYear = OpReportBaseHelper.getDateFiled(selectDate, Calendar.YEAR);
		int selectWeek = OpReportBaseHelper.getDateFiled(selectDate, Calendar.WEEK_OF_YEAR);
		if (selectYear == currentYear && selectWeek == currentWeek) {
			isCurrentWeek = true;
		}

		return isCurrentWeek;
	}

	/**
	 * @description 根据选择的日期设置表格中的数据
	 * @author 车忠伟
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 * @see
	 */
	public void setTextWeek() throws Exception {
		Date date = (Date) pkPeriod.getValue();
		
		int year = OpReportBaseHelper.getDateFiled(date, Calendar.YEAR);
		int week = OpReportBaseHelper.getDateFiled(date, Calendar.WEEK_OF_YEAR);

		if (isCurrentWeek(date)) {
			unlockui();
		} else {
			btnEdit.setEnabled(false);
			btnSave.setEnabled(false);
			btnSubmit.setEnabled(false);
			txtDescription.setEnabled(false);
			thisTable.getStyleAttributes().setLocked(true);
		}
		
		Date firstDate = getFirstDayOfWeek(date);
		Date lastDate = getLastDayOfWeek(date);
		
		this.textWeek.setText(year + "年第" + week + "周");
		pkStartDate.setValue(firstDate);
		pkEndDate.setValue(lastDate);
		
		ProjectWeekReportCollection collection = getProjectWeekReportData(year, week);
	    
		if (collection.size() > 0) {
			loadExistsProjectWeekReport(collection.get(0));
			this.btnEdit.setEnabled(true);
			
		} else {
			// 此处肯定是没有已有项目周月报，单据状态得重新
			Date d = (Date) pkPeriod.getValue();
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			unlockui();
			this.comboState.setSelectedItem(FDCBillStateEnum.SAVED);
			// 传递所属专项
			if (editData.getProjectSpecial() != null) {
				this.projectSpecialInfo = editData.getProjectSpecial();
			}
			editData = (ProjectWeekReportInfo) createNewData();
			editData.setYear(c.get(Calendar.YEAR));
			editData.setWeek(getWeekOfYear(d));
			editData.setMonth(c.get(Calendar.MONTH) + 1);
			editData.setState(FDCBillStateEnum.SAVED);
			editData.setProjectSpecial(this.projectSpecialInfo);
			setDataObject(editData);
			againLoadTable();
		}
		
		setBtnStateByBillState();
	}

	private void unlockui() {
		btnEdit.setEnabled(true);
		btnSave.setEnabled(true);
		btnSubmit.setEnabled(true);
		txtDescription.setEnabled(true);
		thisTable.getStyleAttributes().setLocked(false);
		thisTable.getColumn("taskName").getStyleAttributes().setLocked(true);
		thisTable.getColumn("planEndDate").getStyleAttributes().setLocked(true);
		thisTable.getColumn("respPerson").getStyleAttributes().setLocked(true);
		thisTable.getColumn("respDept").getStyleAttributes().setLocked(true);
	}

	/**
	 * @description 周期发生变化后重置本周任务
	 * @author 车忠伟
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 * @see
	 */
	public void againSetThisTable(List thisWeek) {
		this.thisTable.removeRows(false);
		for (int i = 0; i < thisWeek.size(); i++) {// 循环将分录放入表格中
			ProjectWeekReportEntryInfo entryInfo = (ProjectWeekReportEntryInfo) thisWeek.get(i);
			fillWeekReportToTable(entryInfo);
		}
	}

	/**
	 * @description 重置表格数据
	 * @author 车忠伟
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 * @throws Exception
	 * @see
	 */
	public void againLoadTable() throws Exception {
		// FDCMsgBox.showWarning("周期改变，数据发生变化");
		Map map = fetchData();
		List thisWeekList = (List) map.get("thisWeek");
		List nextWeekList = (List) map.get("nextWeek");

		loadNextTableFields(nextWeekList);
		againSetThisTable(thisWeekList);
	}

	/**
	 * @deprecated
	 * @description 周期发生变化时重置下周任务数据(重复方法,作废)
	 * @author 车忠伟
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 */
	public void againSetNextTable() {

		this.nextTable.removeRows(false);
		try {
			Map map = fetchData();
			if (map != null && !map.isEmpty()) {// 不为空
				List nextWeek = (List) map.get("nextWeek");
				if (nextWeek.size() > 0) {// 集合大于0
					for (int i = 0; i < nextWeek.size(); i++) {// 循环将分录放入表格中
						ProjectWeekReportEntryInfo entryInfo = (ProjectWeekReportEntryInfo) nextWeek.get(i);
						IRow row = nextTable.addRow();
						row.getCell("taskName").setValue(entryInfo.getTaskName());
						row.getCell("planStartDate").setValue(entryInfo.getPlanStartDate());
						row.getCell("planEndDate").setValue(entryInfo.getPlanEndDate());
						row.getCell("respPerson").setValue(entryInfo.getRespPerson());
						row.getCell("respDept").setValue(entryInfo.getRespDept());
						row.getCell("entryID").setValue(entryInfo.getId());
						row.getCell("taskID").setValue(entryInfo.getRelateTask());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description 根据日期控件得到当前日期是本年度的第几周
	 * @author 车忠伟
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 */
	public int getWeekOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * @description 根据选择的日期得到当前周的第一天的日期
	 * @author 车忠伟
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 * @see
	 */
	public Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK); // 获取周开始基准
		int current = calendar.get(Calendar.DAY_OF_WEEK); // 获取当天周内天数
		calendar.add(Calendar.DAY_OF_WEEK, min - current); // 当天-基准，获取周开始日期
		Date start = calendar.getTime();
		return start;
	}

	/**
	 * @description 根据选择的日期得到当前周的最后一天的日期
	 * @author 车忠伟
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 * @see
	 */
	public Date getLastDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK); // 获取周开始基准
		int current = calendar.get(Calendar.DAY_OF_WEEK); // 获取当天周内天数
		calendar.add(Calendar.DAY_OF_WEEK, min - current); // 当天-基准，获取周开始日期
		calendar.add(Calendar.DAY_OF_WEEK, 6); // 开始+6，获取周结束日期
		Date end = calendar.getTime();
		return end;
	}

	public ProjectSpecialInfo getProjectSpecialInfo() {
		ProjectSpecialInfo projectSpecialInfo = null;
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof SpecialScheduleExecuteUI) {
			SpecialScheduleExecuteUI executeUI = (SpecialScheduleExecuteUI) getUIContext().get("Owner");
			projectSpecialInfo = executeUI.projectSpecialInfo;
		}
		return projectSpecialInfo;
	}

	public void storeFields() {
		super.storeFields();
		CurProjectInfo info = getCurProject();
		editData.setName(info.getName() + this.textWeek.getText() + "周报");
		editData.setNumber(info.getName() + this.textWeek.getText() + "周报");
		
		/* modified by zhaoqin for R140606-0197 on 2014/07/04 */
		//editData.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		
		editData.setDescription(this.txtDescription.getText().trim());
		if (comboState.getSelectedItem() != null) {
			if (comboState.getSelectedItem().equals(FDCBillStateEnum.SAVED)) {
				editData.setState(FDCBillStateEnum.SAVED);
			}
			if (comboState.getSelectedItem().equals(FDCBillStateEnum.SUBMITTED)) {
				editData.setState(FDCBillStateEnum.SUBMITTED);
			}
		}
		
		storeLineFiles();
	}

	/***
	 * 重写getSelector()
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("entrys.relateTask.*"));
		sic.add(new SelectorItemInfo("entrys.respPerson.*"));
		sic.add(new SelectorItemInfo("entrys.respDept.*"));
		sic.add("projectSpecial.id");
		sic.add("projectSpecial.number");
		sic.add("projectSpecial.name");
		sic.add("project.name");
		sic.add("project.id");
		sic.add("project.number");
		sic.add("entrys.planEndDate");
		sic.add("creator.id");
		sic.add("creator.name");
		sic.add("creator.number");
		sic.add("creator.person.id");
		sic.add("creator.person.name");
		sic.add("creator.person.number");
		return sic;
	}

	/*
	 * 获取相关值 调用服务器端方法 1、获取本周的任务，及任务的完成情况 2、获取下周的任务
	 * 请将获取的值，生成ProjectWeekReportEntryInfo，放入到editData中
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#fetchInitData()
	 */
	protected Map fetchData() throws Exception {
		return super.fetchData();
	}

	/*
	 * 获取一些初始化的值
	 * 
	 * @see com.kingdee.eas.fdc.schedule.client.OpReportBaseUI#fetchInitData()
	 */
	protected void fetchInitData() throws Exception {
	}

	/*
	 * 通过UIContext,及SysContext取数； 初始化相应的组织，工程项目等信息
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#createNewData()
	 */
	protected IObjectValue createNewData() {
		ProjectWeekReportInfo info = new ProjectWeekReportInfo();
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		if (getCurProject() != null) {
			CurProjectInfo project = getCurProject();
			info.setProject(project);
			info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
			info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		}
		if (getUIContext().get("projectSpecial") != null) {
			ProjectSpecialInfo projectSpecila = (ProjectSpecialInfo) getUIContext().get("projectSpecial");
			info.setProjectSpecial(projectSpecila);
			this.projectSpecialInfo = projectSpecila;
		}
		info.setState(FDCBillStateEnum.SAVED);
		return info;
	}

	/**
	 * @description 周期发生变化时，当前周存在报告
	 * @author 车忠伟
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 * @see
	 */
	public void getHaveReport() throws Exception {
		Date currSelectDate = (Date) this.pkPeriod.getValue();
		int year = OpReportBaseHelper.getDateFiled(currSelectDate, Calendar.YEAR);
		int week = OpReportBaseHelper.getDateFiled(currSelectDate, Calendar.WEEK_OF_YEAR);
		ProjectWeekReportCollection collection = getProjectWeekReportData(year, week);
		if (collection.size() > 0) {
			ProjectWeekReportInfo projectWeekReportInfo = collection.get(0);
			loadExistsProjectWeekReport(projectWeekReportInfo);
		}
	}

	/***
	 * 
	 * 描述：加载项目周报告
	 * 
	 * @param projectWeekReportInfo
	 *            创建人：yuanjun_lan 创建时间：2012-5-19
	 */
	private void loadExistsProjectWeekReport(ProjectWeekReportInfo projectWeekReportInfo) {
		editData = projectWeekReportInfo;
		setDataObject(editData);
		loadHead(projectWeekReportInfo);
		getInitDate(projectWeekReportInfo);
	}

	/**
	 * 
	 * 描述：根据当前周去取数
	 * 
	 * @param year
	 * @param week
	 * @return
	 * @throws BOSException
	 *             创建人：yuanjun_lan 创建时间：2012-5-19
	 */
	private ProjectWeekReportCollection getProjectWeekReportData(int year, int week) throws BOSException, EASBizException, SQLException {
		EntityViewInfo view = new EntityViewInfo();
		view.setSelector(getSelectors());		
		SorterItemCollection sic = new SorterItemCollection();
		sic.add(new SorterItemInfo("entrys.planEndDate"));
		view.setSorter(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", getCurProjectIDForAll()));
		filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
		filter.getFilterItems().add(new FilterItemInfo("week", new Integer(week)));
		view.setFilter(filter);
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof SpecialScheduleExecuteUI) {
			filter.getFilterItems().add(
					new FilterItemInfo("projectSpecial.id", editData.getProjectSpecial().getId().toString(), CompareType.EQUALS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
		}

		ProjectWeekReportCollection collection = ProjectWeekReportFactory.getRemoteInstance().getProjectWeekReportCollection(view);
		return collection;
	}

	/**
	 * @description 得到数据
	 * @author 车忠伟
	 * @createDate 2011-9-21
	 * @version EAS7.0
	 */
	private void loadField() {
		if (editData.getProjectSpecial() != null) {
			this.projectSpecialInfo = editData.getProjectSpecial();
		}
		if (editData.getCreator() != null) {
			this.prmtCreator.setValue(editData.getCreator());
		}
		
		try {
			Map map = fetchData();
			if (map != null && !map.isEmpty()) {// 不为空
				List thisWeek = (List) map.get("thisWeek");
				List nextWeek = (List) map.get("nextWeek");
				loadThisTableFields(thisWeek);
				loadNextTableFields(nextWeek);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @description 初始化数据
	 * @author 车忠伟
	 * @createDate 2011-8-29
	 * @version EAS7.0
	 * @see
	 */
	public void getInitDate(ProjectWeekReportInfo info) {
		this.txtDescription.setText(info.getDescription());
		List thisWeek = new ArrayList();
		List nextWeek = new ArrayList();
		ProjectWeekReportEntryCollection collection = info.getEntrys();
		if (collection.size() > 0) {
			for (int i = 0; i < collection.size(); i++) {
				ProjectWeekReportEntryInfo entryInfo = collection.get(i);
				if (entryInfo.isIsNext()) {
					nextWeek.add(entryInfo);
				} else {
					thisWeek.add(entryInfo);
				}
			}
		}
		loadThisTableFields(thisWeek);
		loadNextTableFields(nextWeek);
	}

	/*
	 * 将对象中的数据，显示到下期表格中
	 */
	public void loadNextTableFields(List list) {
		nextTable.removeRows(false);
		this.nextTable.checkParsed();
		for (int i = 0; i < list.size(); i++) {// 循环将分录放入表格中
			ProjectWeekReportEntryInfo entryInfo = (ProjectWeekReportEntryInfo) list.get(i);
			IRow row = nextTable.addRow();
			row.getCell("taskName").setValue(entryInfo.getTaskName());
			row.getCell("planStartDate").setValue(entryInfo.getPlanStartDate());
			row.getCell("planEndDate").setValue(entryInfo.getPlanEndDate());
			row.getCell("taskID").setValue(entryInfo.getRelateTask());
			row.getCell("respPerson").setValue(entryInfo.getRespPerson());
			row.getCell("respDept").setValue(entryInfo.getRespDept());
			row.getCell("entryID").setValue(entryInfo.getId());
			
			/* modified by zhaoqin for R140812-0088 on 2014/09/24 */
			row.setUserObject(entryInfo);
		}
	}

	/*
	 * 将对象中的数据，显示到本期表格中
	 * 
	 * @see
	 * com.kingdee.eas.fdc.schedule.client.OpReportBaseUI#loadThisTableFields()
	 */
	public void loadThisTableFields(List list) {
		thisTable.removeRows(false);
		this.thisTable.checkParsed();
		for (int i = 0; i < list.size(); i++) {// 循环将分录放入表格中
			ProjectWeekReportEntryInfo entryInfo = (ProjectWeekReportEntryInfo) list.get(i);
			fillWeekReportToTable(entryInfo);
		}
	}

	private void fillWeekReportToTable(ProjectWeekReportEntryInfo entryInfo) {
		IRow row = thisTable.addRow();
		row.getCell("taskName").setValue(entryInfo.getTaskName());
		row.getCell("planEndDate").setValue(
				entryInfo.getPlanEndDate() == null ? entryInfo.getRelateTask().getEnd() : entryInfo.getPlanEndDate());
		int completePrecent = entryInfo.getCompletePrecent();
		if (completePrecent == 100) {
			row.getCell("isComplete").setValue(WeekReportEnum.YES);
		} else {
			row.getCell("isComplete").setValue(WeekReportEnum.NO);
		}
		row.getCell("completePercent").setValue(new Integer(completePrecent));
		row.getCell("realEndDate").setValue(entryInfo.getRealEndDate());
		if (entryInfo.getDescription() != null && !"".equals(entryInfo.getDescription())) {
			row.getCell("description").setValue(entryInfo.getDescription());
		} else {
			row.getCell("description").setValue("");
		}
		row.getCell("respPerson").setValue(entryInfo.getRespPerson());
		row.getCell("respDept").setValue(entryInfo.getRespDept());
		row.getCell("completeAmount").setValue(entryInfo.getCompleteAmount());
		row.getCell("entryID").setValue(entryInfo.getId());
		FDCScheduleTaskInfo task = entryInfo.getRelateTask();
		row.getCell("intendEndDate").setValue(
				entryInfo.getIntendEndDate() == null ? (task.getIntendEndDate() == null ? task.getEnd() : task.getIntendEndDate())
						: entryInfo.getIntendEndDate());
		row.getCell("startDate").setValue(task.getStart());
		row.getCell("taskID").setValue(task);
		Date actualStartDate = task.getActualStartDate() == null ? task.getStart() : task.getActualStartDate();
		row.getCell("actualStartDate").setValue(entryInfo.getPlanStartDate() == null ? actualStartDate : entryInfo.getPlanStartDate());
		lockedCell(new Integer(entryInfo.getCompletePrecent()), row);
		row.setUserObject(entryInfo);
	}

	public void storeLineFiles() {
		storeThisTableFields();
		storeNextTableFields();
	}

	/*
	 * 更新editData中的值
	 * 
	 * @see
	 * com.kingdee.eas.fdc.schedule.client.OpReportBaseUI#storeNextTableFields()
	 */
	public void storeNextTableFields() {
		weekReportInfo = (ProjectWeekReportInfo) editData;

		int count = nextTable.getRowCount();
		for (int i = 0; i < count; i++) {
			IRow row = nextTable.getRow(i);
			Object obj = weekReportInfo.getId();
			String taskName = row.getCell("taskName").getValue().toString();
			Date planStartDate = (Date) row.getCell("planStartDate").getValue();
			Date planEndDate = (Date) row.getCell("planEndDate").getValue();
			ProjectWeekReportEntryInfo entryInfo = new ProjectWeekReportEntryInfo();

			if (!FDCHelper.isEmpty(obj)) {
				weekReportInfo.setId(BOSUuid.read(obj.toString()));
				CoreBaseCollection collection = new CoreBaseCollection();
				collection.add(weekReportInfo);

				try {
					ProjectWeekReportEntryFactory.getRemoteInstance().update(collection);
				} catch (Exception e) {
					logger.info(e.getMessage(), e);
				}
			} else {
				entryInfo.setTaskName(taskName);
				entryInfo.setPlanStartDate(planStartDate);
				entryInfo.setPlanEndDate(planEndDate);
				PersonInfo personInfo = (PersonInfo) row.getCell("respPerson").getValue();
				FullOrgUnitInfo unitInfo = (FullOrgUnitInfo) row.getCell("respDept").getValue();
				entryInfo.setRespDept(unitInfo);
				entryInfo.setRespPerson(personInfo);
				entryInfo.setIsNext(true);
				entryInfo.setHead(weekReportInfo);
				entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
				if (null != row.getCell("taskID") && row.getCell("taskID").getValue() instanceof FDCScheduleTaskInfo) {
					entryInfo.setRelateTask((FDCScheduleTaskInfo) row.getCell("taskID").getValue());
				}
				weekReportInfo.getEntrys().add(entryInfo);
			}
		}
	}

	/*
	 * 更新editData中的值
	 * 
	 * @see
	 * com.kingdee.eas.fdc.schedule.client.OpReportBaseUI#storeThisTableFields()
	 */
	public void storeThisTableFields() {
		weekReportInfo = (ProjectWeekReportInfo) editData;
		if (weekReportInfo.getId() == null) {// 没有ID，说明是新增的周报告
			/* 因为这个方法在提交时会被调用多次，导致分录出现重复的
			 * 所以新增的周报告，每次调用前先清除一下分录，后面的代码会再加上 Added by Owen_wen 2013-06-25
			 */
			weekReportInfo.getEntrys().clear();
		}
		
		for (int i = 0; i < thisTable.getRowCount(); i++) {
			IRow row = thisTable.getRow(i);
			ProjectWeekReportEntryInfo entryInfo = new ProjectWeekReportEntryInfo();
			String taskName = row.getCell("taskName").getValue().toString().trim();
			entryInfo.setTaskName(taskName);
			Date endDate = (Date) row.getCell("planEndDate").getValue();
			entryInfo.setIsNext(false);
			Date realEndDate = (Date) row.getCell("realEndDate").getValue();
			Date intendEndDate = (Date) row.getCell("intendEndDate").getValue();
			
			BigDecimal completeAmount = (BigDecimal) row.getCell("completeAmount").getValue();
			entryInfo.setCompleteAmount(completeAmount);
			
			int newtemp = 0;
			if (row.getCell("completePercent").getValue() != null) {
				newtemp = new BigDecimal(row.getCell("completePercent").getValue().toString()).intValue();
			}
			entryInfo.setHead(weekReportInfo);
			if (weekReportInfo.getId() != null) {

				if (weekReportInfo.getEntrys().size() > 0) {
					weekReportInfo.getEntrys().get(i).setIsNext(false);
					if (row.getCell("completePercent").getValue() != null) {
						if (weekReportInfo.getEntrys().size() > 0) {
							if (newtemp > 100) {
								FDCMsgBox.showInfo("只能输入0-100的整数");
								SysUtil.abort();
							} else {
								weekReportInfo.getEntrys().get(i).setCompletePrecent(newtemp);
							}
						}
					}
					if (row.getCell("isComplete").getValue().equals(WeekReportEnum.YES)) {
						if (weekReportInfo.getEntrys().size() > 0) {
							weekReportInfo.getEntrys().get(i).setIsComplete(true);
						}

					} else {
						if (weekReportInfo.getEntrys().size() > 0) {
							weekReportInfo.getEntrys().get(i).setIsComplete(false);
						}
					}
					weekReportInfo.getEntrys().get(i).setCompleteAmount(completeAmount);
					weekReportInfo.getEntrys().get(i).setPlanEndDate(endDate);
					weekReportInfo.getEntrys().get(i).setRealEndDate(realEndDate);
					weekReportInfo.getEntrys().get(i).setIntendEndDate(intendEndDate);
					String description = (String) row.getCell("description").getValue();
					if (description == null) {
						weekReportInfo.getEntrys().get(i).setDescription("");
					} else {
						weekReportInfo.getEntrys().get(i).setDescription(description.trim());
					}

				}
				// 此处不增加实际开始日期，借用planStartDate
				if (entryInfo.getCompletePrecent() > 0) {
					Date actualStartDate = (Date) (row.getCell("actualStartDate").getValue() == null ? null : row
							.getCell("actualStartDate").getValue());
					entryInfo.setPlanStartDate(actualStartDate);
				}
				CoreBaseCollection collection = new CoreBaseCollection();
				collection.add(weekReportInfo);

				try {
					ProjectWeekReportEntryFactory.getRemoteInstance().update(collection);
				} catch (EASBizException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BOSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else { // 如果ID为空，说明是新增的editData
				if (row.getCell("completePercent").getValue() != null) {
						if (newtemp > 100) {
							FDCMsgBox.showInfo("只能输入0-100的整数");
							SysUtil.abort();
						} else {
							entryInfo.setCompletePrecent(newtemp);
					}
				}

				if (row.getCell("isComplete").getValue().equals(WeekReportEnum.YES)) {
					entryInfo.setIsComplete(true);
				} else {
					entryInfo.setIsComplete(false);
				}
				entryInfo.setRealEndDate(realEndDate);
				entryInfo.setPlanEndDate(endDate);
				entryInfo.setIntendEndDate(intendEndDate);
				entryInfo.setCompleteAmount(completeAmount);
				String description = (String) row.getCell("description").getValue();
				if (description == null) {
					entryInfo.setDescription("");
				} else {
					entryInfo.setDescription(description.trim());
				}
				PersonInfo personInfo = (PersonInfo) row.getCell("respPerson").getValue();
				FullOrgUnitInfo unitInfo = (FullOrgUnitInfo) row.getCell("respDept").getValue();
				entryInfo.setRespDept(unitInfo);
				entryInfo.setRespPerson(personInfo);
				if (null != row.getCell("taskID") && row.getCell("taskID").getValue() instanceof FDCScheduleTaskInfo) {
					entryInfo.setRelateTask((FDCScheduleTaskInfo) row.getCell("taskID").getValue());
				}
				entryInfo.setHead(weekReportInfo);
				entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
				if (entryInfo.getCompletePrecent() > 0) {
					Date actualStartDate = (Date) (row.getCell("actualStartDate").getValue() == null ? null : row
							.getCell("actualStartDate").getValue());
					entryInfo.setPlanStartDate(actualStartDate);
				}				
				weekReportInfo.getEntrys().add(entryInfo);
			}
		}
	}

	protected void verifyInputForSave() throws Exception {
	}

	/**
	 * @description 传入相应的参数
	 * @author 车忠伟
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 * @see
	 */
	protected Map getMainFilterParam() throws Exception {
		// 用于存放参数
		Map param = new HashMap();
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("projectSpecial.*"));

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", this.getCurProjectIDForAll()));
		filter.getFilterItems().add(new FilterItemInfo("isLatestVer", Boolean.valueOf(true)));
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof SpecialScheduleExecuteUI) {
			filter.getFilterItems().add(
					new FilterItemInfo("projectSpecial.id", editData.getProjectSpecial().getId().toString(), CompareType.EQUALS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
		}
		view.setFilter(filter);
		FDCScheduleCollection collection = FDCScheduleFactory.getRemoteInstance().getFDCScheduleCollection(view);

		if (collection.size() > 0) {
			FDCScheduleInfo scheduleInfo = collection.get(0);
			param.put("stringPk", scheduleInfo.getId().toString());
			param.put("projectSpecial", scheduleInfo.getProjectSpecial());
		}
		
		Date starDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkEndDate.getValue();
		Date nextFist = DateUtils.addDay(starDate, 7);
		Date nextLast = DateUtils.addDay(endDate, 7);
		param.put("starDate", starDate);
		param.put("endDate", endDate);
		param.put("nextFist", nextFist);
		param.put("nextLast", nextLast);
		return param;
	}

	/**
	 * @description 加载表格控件
	 * @author 车忠伟
	 * @createDate 2011-8-29
	 * @version EAS7.0
	 * @see
	 */
	public void onladCtrl() {
		thisTable.checkParsed();
		nextTable.checkParsed();
		KDComboBox box = new KDComboBox();
		Vector vector = new Vector(WeekReportEnum.getEnumList());
		box.addItems(vector);
		this.thisTable.getColumn("isComplete").setEditor(new KDTDefaultCellEditor(box));
		KDDatePicker date = new KDDatePicker();
		date.setEditable(false);
		KDTDefaultCellEditor editorDate = new KDTDefaultCellEditor(date);
		thisTable.getColumn("realEndDate").setEditor(editorDate);
		thisTable.getColumn("intendEndDate").setEditor(editorDate);
		thisTable.getColumn("realEndDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		thisTable.getColumn("intendEndDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		KDFormattedTextField completePercent = new KDFormattedTextField();
		completePercent.setPrecision(0);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(completePercent);
		thisTable.getColumn("completePercent").setEditor(txtEditor);
		this.actionUnAudit.setEnabled(false);
		this.actionUnAudit.setVisible(false);
	}

	public void checkThisTable() {
		thisTable.checkParsed();
		IRow row = KDTableUtil.getSelectedRow(thisTable);
		if (null != row) {
			if (row.getCell("isComplete").getValue().equals(WeekReportEnum.YES)) {
				row.getCell("completePercent").setValue(new Integer(100));
			} else {
				row.getCell("completePercent").setValue(new Integer(0));
			}
			if (row.getCell("completePercent").getValue() != null && !"".equals(row.getCell("completePercent").getValue().toString().trim())) {

				if ("100".equals((row.getCell("completePercent").getValue().toString().trim()))) {
					row.getCell("isComplete").setValue(WeekReportEnum.YES);

				}
				if (Integer.parseInt(row.getCell("completePercent").getValue().toString()) > 100) {
					FDCMsgBox.showWarning(this, "完成进度不能大于100%！");
					row.getCell("completePercent").setValue("0");
				}

			}
			if (row.getCell("isComplete").getValue().equals(WeekReportEnum.NO)) {
				row.getCell("realEndDate").getStyleAttributes().setLocked(true);
			} else {
				row.getCell("realEndDate").getStyleAttributes().setLocked(false);
			}
			if (row.getCell("isComplete").getValue().equals(WeekReportEnum.YES)) {
				row.getCell("intendEndDate").getStyleAttributes().setLocked(true);
			} else {
				row.getCell("intendEndDate").getStyleAttributes().setLocked(false);
			}
		}
	}

	/**
	 * @description 不显示历史记录
	 * @author 车忠伟
	 * @createDate 2011-8-29
	 * @version EAS7.0
	 * @see
	 */
	protected void kDCNoShowHistory_mouseClicked(MouseEvent e) throws Exception {
		if (this.kDCNoShowHistory.isSelected()) {// 不显示历史记录
			int count = thisTable.getRowCount();
			for (int i = count - 1; i >= 0; i--) {
				IRow row = this.thisTable.getRow(i);
				Date date = (Date) this.pkStartDate.getValue();
				Date plantEndDate = (Date) row.getCell("planEndDate").getValue();

				if (date != null && plantEndDate != null) {
					if (DateUtils.diffdates(plantEndDate, date) > 0) {
						thisTable.removeRow(i);
					}
				}
			}

		} else {
			thisTable.removeRows(false);
			if (STATUS_FINDVIEW.equals(getOprtState()) || getOprtState().equals(OprtState.VIEW)) {// 查看时数据的变化
				initTableForView(editData.getId().toString());
			} else {
				recoverThisWeek();
			}
		}
	}

	/**
	 * @description 恢复本周任务
	 * @author 车忠伟
	 * @createDate 2011-8-29
	 * @version EAS7.0
	 * @see
	 */
	public void recoverThisWeek() throws Exception {
		Date d = (Date) this.pkPeriod.getValue();

		int year = OpReportBaseHelper.getDateFiled(d, Calendar.YEAR);
		int week = OpReportBaseHelper.getDateFiled(d, Calendar.WEEK_OF_YEAR);
		ProjectWeekReportCollection collection = getProjectWeekReportData(year, week);
		if (collection.size() > 0) {
			List thisWeek = new ArrayList();
			ProjectWeekReportInfo projectWeekReportInfo = collection.get(0);
			ProjectWeekReportEntryCollection entrycollection = projectWeekReportInfo.getEntrys();
			if (entrycollection.size() > 0) {

				for (int i = 0; i < entrycollection.size(); i++) {
					ProjectWeekReportEntryInfo entryInfo = entrycollection.get(i);
					if (!entryInfo.isIsNext()) {
						thisWeek.add(entryInfo);
					}
				}
			}
			loadThisTableFields(thisWeek);
		} else {
			Map map = fetchData();
			againSetThisTable((List) map.get("thisWeek"));
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();
		btnAddLine.setVisible(false);
		btnCopy.setVisible(false);
		btnCopyFrom.setVisible(false);
		btnCopyLine.setVisible(false);
		btnCreateTo.setVisible(false);
		btnCreateFrom.setVisible(false);
		btnAddNew.setVisible(false);
		btnFirst.setVisible(false);
		btnInsertLine.setVisible(false);
		btnLast.setVisible(false);
		btnMultiapprove.setVisible(false);
		btnNext.setVisible(false);
		btnNextPerson.setVisible(false);
		btnFirst.setVisible(false);
		btnPageSetup.setVisible(false);
		btnPre.setVisible(false);
		btnCancelCancel.setVisible(false);
		btnRemoveLine.setVisible(false);
		btnRemove.setVisible(false);
		btnReset.setVisible(false);
		btnTraceDown.setVisible(false);
		btnTraceUp.setVisible(false);
		/* modfied by zhaoqin for R20141225-0158 on 2015/01/15 */
		//btnWorkFlowG.setVisible(false);
		btnWFViewdoProccess.setVisible(false);
		btnWFViewSubmitProccess.setVisible(false);
		btnCalculator.setVisible(false);
		btnPrintReport.setEnabled(true);
		chkMenuItemSubmitAndAddNew.setSelected(false);//不需要连续新增
	}

	protected void thisTable_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getColIndex() == thisTable.getColumn("description").getColumnIndex()) {
			if (e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 1) {
				thisTable.showCellDetailInfo();
			}
		}
	}

	/**
	 * @description 下周任务列表不可编辑
	 * @author 车忠伟
	 * @createDate 2011-8-29
	 * @version EAS7.0
	 * @see
	 */
	protected void nextTable_tableClicked(KDTMouseEvent e) throws Exception {
		this.nextTable.getStyleAttributes().setLocked(true);
	}

	/**
	 * @description 本周任务列表部分数据可编辑
	 * @author 车忠伟
	 * @createDate 2011-8-29
	 * @version EAS7.0
	 * @see
	 */
	protected void thisTable_editStopped(KDTEditEvent e) throws Exception {
		thisTable.checkParsed();
		IRow row = this.thisTable.getRow(this.thisTable.getSelectManager().getActiveRowIndex());
		if (null == row) {
			SysUtil.abort();
		}
		if (e.getColIndex() == thisTable.getColumn("completePercent").getColumnIndex()) {
			Object completePercent = row.getCell("completePercent").getValue();
			if (completePercent != null && !"".equals(completePercent)) {
				int intValue_completePercent = new Integer(completePercent.toString()).intValue();
				if (100 == intValue_completePercent) {
					row.getCell("isComplete").setValue(WeekReportEnum.YES);
					if (row.getCell("realEndDate").getValue() == null) {
						row.getCell("intendEndDate").getStyleAttributes().setLocked(true);
						row.getCell("realEndDate").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
						row.getCell("realEndDate").getStyleAttributes().setLocked(false);
						
						/* modified by zhaoqin for 联发进度任务 on 2014/10/14 start */
						//row.getCell("realEndDate").setValue(FDCDateHelper.getServerTimeStamp());
						Date actualStartDate = (Date)row.getCell("actualStartDate").getValue();
						Date now = new Date();
						if(DateUtils.diffdates(actualStartDate, now) >= 0) {
							row.getCell("realEndDate").setValue(now);
						} else {
							row.getCell("realEndDate").setValue(actualStartDate);
						}
						/* modified by zhaoqin for 联发进度任务 on 2014/10/14 end */
						
					} else {
						row.getCell("realEndDate").getStyleAttributes().setBackground(Color.WHITE);
					}
				} else {
					row.getCell("isComplete").setValue(WeekReportEnum.NO);
					row.getCell("intendEndDate").getStyleAttributes().setLocked(false);
					row.getCell("realEndDate").getStyleAttributes().setLocked(true);
				}
			}
			resetCompletePercent(row, e);
			if (completePercent == null || 0 == new Integer(completePercent.toString()).intValue()) {
				row.getCell("completeAmount").getStyleAttributes().setLocked(true);
			} else {
				row.getCell("completeAmount").getStyleAttributes().setLocked(false);
			}
		}
		if (e.getColIndex() == thisTable.getColumn("isComplete").getColumnIndex()) {
			Object isComplete = row.getCell("isComplete").getValue();
			Object completePercent = row.getCell("completePercent").getValue();
			if (completePercent == null || 0 == new Integer(completePercent.toString()).intValue()) {
				row.getCell("completeAmount").getStyleAttributes().setLocked(true);
			} else {
				row.getCell("completeAmount").getStyleAttributes().setLocked(false);
			}
			if (isComplete == null) {
				isComplete = WeekReportEnum.NO;
				row.getCell("isComplete").setValue(isComplete);
			}
			
			if (isComplete.equals(WeekReportEnum.YES)) {
				row.getCell("completePercent").setValue(new Integer(100));
				row.getCell("realEndDate").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
	
				/* modified by zhaoqin for 联发进度任务 on 2014/10/14 start */
				//row.getCell("realEndDate").setValue(FDCDateHelper.getServerTimeStamp());
				Date actualStartDate = (Date)row.getCell("actualStartDate").getValue();
				Date now = new Date();
				if(DateUtils.diffdates(actualStartDate, now) >= 0) {
					row.getCell("realEndDate").setValue(now);
				} else {
					row.getCell("realEndDate").setValue(actualStartDate);
				}
				/* modified by zhaoqin for 联发进度任务 on 2014/10/14 end */
	
				row.getCell("intendEndDate").getStyleAttributes().setLocked(true);
				row.getCell("realEndDate").getStyleAttributes().setLocked(false);
			} else {
				row.getCell("realEndDate").getStyleAttributes().setLocked(true);
				row.getCell("realEndDate").setValue(null);
				row.getCell("intendEndDate").getStyleAttributes().setLocked(false);
				// 这个地方肯定不能变成零
				if (completePercent != null && !"".equals(completePercent) && 100 == new Integer(completePercent.toString()).intValue()) {
					row.getCell("completePercent").setValue(100 - 1);
				}
				row.getCell("realEndDate").getStyleAttributes().setBackground(Color.WHITE);
			}
		}
		if (e.getColIndex() == thisTable.getColumn("intendEndDate").getColumnIndex()) {
			verifyIntendEndDate(row, e);
		}
		
		/* modified by zhaoqin for 联发进度任务 on 2014/10/14 start */
		if (e.getColIndex() == thisTable.getColumn("realEndDate").getColumnIndex()) {
			Date actualStartDate = (Date)row.getCell("actualStartDate").getValue();
			Date realEndDate = (Date)e.getValue();
			//实际完成日期不能早于实际开始日期
			if(DateUtils.diffdates(actualStartDate, realEndDate) < 0) {
				row.getCell("realEndDate").setValue(e.getOldValue());
				FDCMsgBox.showWarning("实际完成日期不能早于实际开始日期");
				SysUtil.abort();
			}
		}
		/* modified by zhaoqin for 联发进度任务 on 2014/10/14 end */
	}

	public void lockedCell(Object completePercent, IRow row) {
		if (completePercent == null || "".equals(completePercent)) {
			completePercent = new Integer(0);
		}
			if (100 == new Integer(completePercent.toString()).intValue()) {
				row.getCell("intendEndDate").getStyleAttributes().setLocked(true);
				row.getCell("completePercent").getStyleAttributes().setLocked(true);
				row.getCell("isComplete").getStyleAttributes().setLocked(true);
				row.getCell("realEndDate").getStyleAttributes().setLocked(false);
			} else {
				row.getCell("intendEndDate").getStyleAttributes().setLocked(false);
				row.getCell("completePercent").getStyleAttributes().setLocked(false);
				row.getCell("isComplete").getStyleAttributes().setLocked(false);
				row.getCell("realEndDate").getStyleAttributes().setLocked(true);
			}
			if (0 == new Integer(completePercent.toString()).intValue()) {
				row.getCell("completeAmount").getStyleAttributes().setLocked(true);
			} else {
				row.getCell("completeAmount").getStyleAttributes().setLocked(false);
			}
		}

	/**
	 * 
	 * @description 重置完成进度
	 * @author 杜红明
	 * @createDate 2011-12-26
	 * @param row
	 *            void
	 * @version EAS7.0
	 * @see
	 */
	public void resetCompletePercent(IRow row, KDTEditEvent e) {
		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
		if (oldValue == null || "".equals(oldValue)) {
			oldValue = new Integer(0);
		}
		if (newValue == null || "".equals(newValue)) {
			newValue = new Integer(0);
		}
		if (new Integer(newValue.toString()).intValue() > 100 || new Integer(newValue.toString()).intValue() < 0) {
			row.getCell("completePercent").setValue(oldValue);
		}
	}

	/**
	 * 
	 * @description 验证预计完成日期
	 * @author 杜红明
	 * @createDate 2011-12-26
	 * @param row
	 *            void
	 * @version EAS7.0
	 * @see
	 */
	public void verifyIntendEndDate(IRow row, KDTEditEvent e) {
		Date oldValue = (Date) e.getOldValue();
		Date newValue = (Date) e.getValue();
		boolean error = false;
		if (oldValue == null && newValue == null) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (oldValue == null && newValue != null) {
			if (DateUtils.diffdates(newValue, new Date()) > 0) {
				error = true;
			}
		}
		if (oldValue instanceof java.sql.Date || oldValue instanceof java.util.Date) {
			if (!sdf.format(oldValue).equals(sdf.format(newValue))) {
				Date intendEndDate = (Date) row.getCell("intendEndDate").getValue();
				if (intendEndDate != null && DateUtils.diffdates(intendEndDate, new Date()) > 0) {
					error = true;
				}
			}
		}
		if (error) {
			row.getCell("intendEndDate").setValue(oldValue);
		}
	}

	public ProjectSpecialInfo projectSpecialInfo;

	/**
	 * @description 查看历史记录
	 * @author 车忠伟
	 * @createDate 2011-8-30
	 * @version EAS7.0
	 * @see
	 */
	public void actionHistory_actionPerformed(ActionEvent e) throws Exception {
		CurProjectInfo curProjectInfo = getCurProject();
		this.curProjectInfo = curProjectInfo;
		UIContext uiContext = new UIContext(this);
		getUIContext().put("curprojectInfo", curProjectInfo);
		if (editData.getProjectSpecial() != null)
			this.projectSpecialInfo = editData.getProjectSpecial();
		uiContext.put(UIContext.ID, null);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(ProjectWeekReportListUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}

	public boolean isModify() {
		return false;
	}

	public void beforeSave() {
		int num = this.thisTable.getRowCount();
		if (num > 0) {
			for (int i = 0; i < this.thisTable.getRowCount(); i++) {
				IRow row = this.thisTable.getRow(i);
				if (row.getCell("isComplete").getValue().equals(WeekReportEnum.YES)) {
					if (row.getCell("realEndDate").getValue() == null || "".equals(row.getCell("realEndDate").getValue())
							|| row.getCell("completePercent").getValue() == null || "".equals(row.getCell("completePercent").getValue())) {
						FDCMsgBox.showWarning("完成进度为100%时,实际完成日期不能为空！");
						SysUtil.abort();
					}
				}
				verifyCompletePercent(row);
			}
		}
	}

	/**
	 * 
	 * @description 验证完成进度,不能少于计划任务中的进度完成值
	 * @author 杜红明
	 * @createDate 2011-12-26
	 * @version EAS7.0
	 */
	public void verifyCompletePercent(IRow row) {
		BOSUuid entryID = null;
		entryID = (BOSUuid) row.getCell("entryID").getValue();
		if (entryID == null) {
			entryID = ((FDCScheduleTaskInfo) row.getCell("taskID").getValue()).getId();
		}
		Object completePercent = row.getCell("completePercent").getValue();
		if (completePercent == null || "".equals(completePercent)) {
			completePercent = new Integer(0);
		}
		if (entryID != null && completePercent != null) {
			int temp = ((Integer) completePercent).intValue();
			ProjectWeekReportEntryInfo entryInfo = (ProjectWeekReportEntryInfo) row.getUserObject();
			int lastTaskComplete = entryInfo.getRelateTask().getComplete().intValue();
			if (lastTaskComplete > temp) {
				FDCMsgBox.showWarning(this, row.getCell("taskName").getValue() + "任务完成进度小于上次汇报记录!");
				SysUtil.abort();
			}
		}
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {

		beforeSave();
		comboState.setSelectedItem(FDCBillStateEnum.SAVED);
		super.actionSave_actionPerformed(e);
		prmtCreator.setValue(SysContext.getSysContext().getCurrentUserInfo());
		this.thisTable.getStyleAttributes().setLocked(true);
		this.btnSave.setEnabled(false);
		this.btnEdit.setVisible(true);
		this.btnEdit.setEnabled(true);
		this.menuItemEdit.setEnabled(true);
		this.menuItemSave.setEnabled(false);
		this.txtDescription.setEnabled(false);
		this.thisTable.getStyleAttributes().setLocked(true);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		setBtnStateByBillState();
		this.txtDescription.setEnabled(true);
		this.thisTable.getStyleAttributes().setLocked(false);
		setTableUnEnable();
		for (int i = 0; i < thisTable.getRowCount(); i++) {
			lockedCell(thisTable.getRow(i).getCell("completePercent").getValue(), thisTable.getRow(i));
		}
	}
	
	
	private void setBtnStateByBillState() {
		if (isFromListUI()) {
			return;
		}
		FDCBillStateEnum state = editData.getState();
		if (FDCBillStateEnum.SAVED.equals(state)) {
			this.actionSave.setEnabled(true);
			this.btnSave.setEnabled(true);
			this.menuItemSave.setEnabled(true);
			this.menuItemSubmit.setEnabled(true);
			this.btnSubmit.setEnabled(true);
			this.actionSubmit.setEnabled(true);
			this.menuItemAudit.setEnabled(false);
			this.btnAudit.setEnabled(false);
			this.actionAudit.setEnabled(false);
		} else if (FDCBillStateEnum.SUBMITTED.equals(state)) {
			this.actionSave.setEnabled(false);
			this.btnSave.setEnabled(false);
			this.menuItemSave.setEnabled(false);
			this.actionSubmit.setEnabled(true);
			this.btnSubmit.setEnabled(true);
			this.menuItemSave.setEnabled(true);
			this.menuItemAudit.setEnabled(true);
			this.btnAudit.setEnabled(true);
			this.actionAudit.setEnabled(true);
		} else {
			this.btnEdit.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionSave.setEnabled(false);
			this.btnSave.setEnabled(false);
			this.menuItemSave.setEnabled(false);
			this.actionSubmit.setEnabled(false);
			this.btnSubmit.setEnabled(false);
			this.menuItemSave.setEnabled(false);
			this.menuItemAudit.setEnabled(false);
			this.btnAudit.setEnabled(false);
			this.actionAudit.setEnabled(false);
		}
	}
	private boolean isFromListUI() {
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof ProjectWeekReportListUI) {
			return true;
		}
		return false;
	}

	/**
	 * @description 反审批
	 * @author 车忠伟
	 * @createDate 2011-9-1
	 * @version EAS7.0
	 * @see
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (comboState.getSelectedItem() != null) {
			if (comboState.getSelectedItem().equals(FDCBillStateEnum.AUDITTED)) {
				ProjectWeekReportFactory.getRemoteInstance().unAudit(BOSUuid.read(editData.getId().toString()));
				FDCMsgBox.showWarning("操作成功");
				this.btnUnAudit.setEnabled(false);
				this.btnAudit.setEnabled(true);
				editData.setState(FDCBillStateEnum.SUBMITTED);
				comboState.setSelectedItem(FDCBillStateEnum.SUBMITTED);
			} else {
				FDCMsgBox.showWarning("单据状态不允许进行此操作");
			}
		}
	}

	/**
	 * @description 审批
	 * @author 车忠伟
	 * @createDate 2011-9-1
	 * @version EAS7.0
	 * @see
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (isModify()) {
			FDCMsgBox.showInfo("单据已被修改，请先提交。");
			this.abort();
		}

		if (getSelectBOID() != null) {
			editData.setState(FDCBillStateEnum.AUDITTED);
			ProjectWeekReportFactory.getRemoteInstance().audit(editData.getId());
			this.btnAudit.setEnabled(false);
			comboState.setSelectedItem(FDCBillStateEnum.AUDITTED);
			FDCClientUtils.showOprtOK(this);
			setSaveActionStatus();
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		beforeSave();
		comboState.setSelectedItem(FDCBillStateEnum.SUBMITTED);
		Date firstDate = (Date) pkStartDate.getValue();
		Date lastDate = (Date) pkEndDate.getValue();
		super.actionSubmit_actionPerformed(e);
		comboState.setSelectedItem(FDCBillStateEnum.SUBMITTED);
		prmtCreator.setValue(SysContext.getSysContext().getCurrentUser());
		if (pkStartDate.getValue() == null && pkEndDate.getValue() == null) {
			this.pkStartDate.setValue(firstDate);
			this.pkEndDate.setValue(lastDate);
		}
		this.btnEdit.setVisible(true);
		this.btnEdit.setEnabled(true);
		this.menuItemSubmit.setEnabled(true);
		submitAfterNotEdit();
	}

	/**
	 * 提交后不能修改表单
	 * 
	 * @description
	 * @author
	 * @createDate 2011-9-1
	 * @version EAS7.0
	 * @see
	 */
	public void submitAfterNotEdit() {
		// this.btnEdit.setEnabled(false);
		this.btnSave.setEnabled(false);
		this.actionAudit.setEnabled(true);
		this.menuItemEdit.setEnabled(false);
		this.menuItemSave.setEnabled(false);
		this.btnSubmit.setEnabled(false);
		txtDescription.setEnabled(false);
		thisTable.getStyleAttributes().setLocked(true);
	}

	/**
	 * @description 打印报告
	 * @author 车忠伟
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	public void actionPrintReport_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getId() == null) {
			FDCMsgBox.showWarning("请先保存单据");
			return;
		}
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, null);
			uiContext.put("reportInfo", editData);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			IUIWindow uiWindow = uiFactory.create(ViewProjectWeekReportPrintUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
	}

	/**
	 * @description 导出Excel
	 * @author 车忠伟
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception {
		List tables = new ArrayList();
		tables.add(new Object[] { "本周任务", thisTable });
		tables.add(new Object[] { "下周任务", nextTable });
		FDCTableHelper.exportExcel(this, tables);
	}
	
	protected String getReportTableName() {
		return "t_sch_projectweekreport";
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProjectWeekReportFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return null;
	}

	public void loadNextTableFields() {

	}

	public void loadThisTableFields() {

	}

}