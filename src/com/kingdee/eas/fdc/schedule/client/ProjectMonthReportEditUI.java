/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMultiLangArea;
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
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
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
import com.kingdee.eas.fdc.schedule.ProjectMonthReportCollection;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryCollection;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryInfo;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportFactory;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportInfo;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.WeekReportEnum;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.fm.common.DateHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 项目月度报告 编辑界面
 */
public class ProjectMonthReportEditUI extends AbstractProjectMonthReportEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectMonthReportEditUI.class);
	public ProjectMonthReportInfo projectMonthReportInfo = null;
	public String selectedID = null;

	public ProjectMonthReportEditUI() throws Exception {
		super();
	}

	public void setSelectedID(String selectedID) {
		this.selectedID = selectedID;
	}

	public String getSelectedID() {
		return selectedID;
	}
	/**
	 * 
	 * 描述：返回当前选择的年份是否有当前月
	 * 
	 * @param selectDate
	 * @return
	 * @throws BOSException
	 *             创建人：yuanjun_lan 创建时间：2012-6-1
	 */
	private boolean isCurrentMonth(Date selectDate) throws BOSException {
		// 此处应该取服务端日期
		Date d = FDCDateHelper.getServerTimeStamp();
		int currentYear = OpReportBaseHelper.getDateFiled(d, Calendar.YEAR);
		int currentMonth = OpReportBaseHelper.getDateFiled(d, Calendar.MONTH);

		int selectYear = OpReportBaseHelper.getDateFiled(selectDate, Calendar.YEAR);
		int selectMonth = OpReportBaseHelper.getDateFiled(selectDate, Calendar.MONTH);
		return (selectYear == currentYear && selectMonth == currentMonth);
	}

	public void storeFields() {
		super.storeFields();
		editData.setName(getCurProject().getName() + pkPeriod.getValue() + "月报");
		editData.setNumber(getCurProject().getName() + pkPeriod.getValue() + "月报");
		
		// 判断是否属于专项
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof SpecialScheduleExecuteUI) {
			ProjectSpecialInfo projectSpecialInfo = getProjectSpecialInfo();
			editData.setProjectSpecial(projectSpecialInfo);
		}
		
		storeLineFiles();
	}

	/**
	 * 得到专项的项目名称
	 * 
	 * @description
	 * @author 李健波
	 * @createDate 2011-10-12
	 * @return ProjectSpecialInfo
	 * @version EAS7.0
	 * @see
	 */
	public ProjectSpecialInfo getProjectSpecialInfo() {
		if (editData != null && editData.getProjectSpecial() != null) {
			return editData.getProjectSpecial();
		}
		
		ProjectSpecialInfo projectSpecialInfo = null;
		if (getUIContext().get("projectSpecial") != null && getUIContext().get("Owner") instanceof SpecialScheduleExecuteUI) {
			projectSpecialInfo = (ProjectSpecialInfo) getUIContext().get("projectSpecial");
		}
		
		return projectSpecialInfo;
	}

	public void storeLineFiles() {
		if (editData.getId() == null) { // 说明是新增的报告，分录不能重复多次加入
			editData.getEntrys().clear();
		}
		storeThisTableFields();
		storeNextTableFields();
	}

	/**
	 * @description 进入界面后判断当前项目本月是否存在“项目月报告”，如果不存在则为“新增”状态，否则为“修改”状态。工具栏说明
	 * @author 李建波
	 * @createDate 2011-8-29
	 */
	public void onShow() throws Exception {
		super.onShow();
		this.kDContainer1.add(kDPanel1);
		kDPanel1.setVisible(true);
		setBtnStateByBillState();
		this.thisTable.getColumn("description").setWidth(400);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(70);
		ICellEditor edit = new KDTDefaultCellEditor(textField);
		this.thisTable.getColumn("description").setEditor(edit);
		KDFormattedTextField kdfTextField = new KDFormattedTextField();
		kdfTextField.setPrecision(10);
		kdfTextField.setCaretPosition(0);
		kdfTextField.setMultiplier(100);
		kdfTextField.setMinimumIntegerDigits(0);
		kdfTextField.setDataType(BigDecimal.class);
		edit = new KDTDefaultCellEditor(kdfTextField);
		this.thisTable.getColumn("completeAmount").setEditor(edit);
		this.txtMonth.setRequired(true);
		txtMonth.setEditable(false);
		
		// 保证工作流中和查看历史报告打开的界面，这个控件可以生效
		chkNoHistory.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
	}

	private void lockedCell(Object completePercent, IRow row) {
		if (completePercent == null || "".equals(completePercent)) {
			completePercent = new Integer(0);
		}
		if (100 == new Integer(completePercent.toString()).intValue()) {
			ProjectMonthReportEntryInfo entryIfno = (ProjectMonthReportEntryInfo) row.getUserObject();
			int lastTaskComplete = entryIfno.getRelateTask().getComplete().intValue();
			if (lastTaskComplete == 100) { // 与计划执行中的比较来决定表格是否要锁定
				row.getCell("intendEndDate").getStyleAttributes().setLocked(true);
				row.getCell("completePercent").getStyleAttributes().setLocked(true);
				row.getCell("isComplete").getStyleAttributes().setLocked(true);
				row.getCell("realEndDate").getStyleAttributes().setLocked(false);
				row.getCell("completeAmount").getStyleAttributes().setLocked(true);
			}
		} else {
			row.getCell("intendEndDate").getStyleAttributes().setLocked(false);
			row.getCell("completePercent").getStyleAttributes().setLocked(false);
			row.getCell("isComplete").getStyleAttributes().setLocked(false);
			row.getCell("realEndDate").getStyleAttributes().setLocked(true);
			row.getCell("completeAmount").getStyleAttributes().setLocked(false);
		}
	}

	/**
	 * @description 查看历史月报
	 * @author 李建波
	 * @createDate 2011-9-1
	 * @version EAS7.0
	 * @see
	 */
	public CurProjectInfo curProjectInfo;

	public void actionViewMonthReport_actionPerformed(ActionEvent e) throws Exception {
		CurProjectInfo curProjectInfo = getCurProject();

		this.curProjectInfo = curProjectInfo;
		UIContext uiContext = new UIContext(this);
		uiContext.put("curprojectInfo", curProjectInfo);
		uiContext.put("projectSpecial", editData.getProjectSpecial());
		uiContext.put(UIContext.ID, null);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(ProjectMonthReportListUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		super.actionViewMonthReport_actionPerformed(e);
	}

	/**
	 * @description 导出模板Excel
	 * @author 李建波
	 * @createDate 2011-9-1
	 */
	public void actionExcutePort_actionPerformed(ActionEvent e) throws Exception {
		List tables = new ArrayList();
		if (tpMain.getSelectedIndex() == 1) {

			tables.add(new Object[] { "下月任务", nextTable });
			FDCTableHelper.exportExcel(this, tables);
		} else {
			tables.add(new Object[] { "本月任务", thisTable });
			FDCTableHelper.exportExcel(this, tables);
		}

	}

	/**
	 * 是否显示历史任务 
	 * 
	 * @description
	 * @author 李建波
	 * @createDate 2011-10-12
	 * @version EAS7.0
	 * @see
	 */
	public void chkNoHistory_mouseClicked(java.awt.event.MouseEvent e) throws Exception {
		if (this.chkNoHistory.isSelected()) {// 不显示历史记录
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
		} else { // 显示所有任务
			thisTable.removeRows(false);
			if (STATUS_FINDVIEW.equals(getOprtState()) || getOprtState().equals(OprtState.VIEW)) {// 查看时数据的变化
				loadEntryDataToTable();
			} else {
				recoverThisMonth();
			}
		}
	}

	/**
	 * 恢复本月任务
	 * 
	 * @throws Exception
	 */
	public void recoverThisMonth() throws Exception {
		Date date = (Date) pkPeriod.getValue();
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
		String yearStr = format.format(date);
		String monthStr = formatMonth.format(date);
		int year = Integer.parseInt(yearStr);
		int month = Integer.parseInt(monthStr);
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("entrys.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.relateTask.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.respPerson.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.respDept.*"));

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", getCurProjectIDForAll()));
		filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
		filter.getFilterItems().add(new FilterItemInfo("month", new Integer(month)));
		view.setFilter(filter);

		ProjectMonthReportCollection collection = ProjectMonthReportFactory.getRemoteInstance().getProjectMonthReportCollection(view);
		if (collection.size() > 0) {
			List thisMonth = new ArrayList();
			ProjectMonthReportInfo projectMonthReportInfo = collection.get(0);
			ProjectMonthReportEntryCollection entrycollection = projectMonthReportInfo.getEntrys();
			if (entrycollection.size() > 0) {

				for (int i = 0; i < entrycollection.size(); i++) {
					ProjectMonthReportEntryInfo entryInfo = entrycollection.get(i);
					if (!entryInfo.isIsNext()) {
						thisMonth.add(entryInfo);
					}
				}

			}
			loadThisTableFields(thisMonth);

		} else {
			changeAgainInitThisTable();
		}
	}

	/**
	 * 月发生改变后重新设置本月任务
	 */
	public void changeAgainInitThisTable() {
		this.thisTable.removeRows(false);
		try {
			Map map = fetchData();
			if (map != null && !map.isEmpty()) {// 不为空
				List thisMonth = (List) map.get("thisMonth");
				if (thisMonth.size() > 0) {// 集合大于0
					for (int i = 0; i < thisMonth.size(); i++) {// 循环将分录放入表格中
						ProjectMonthReportEntryInfo entryInfo = (ProjectMonthReportEntryInfo) thisMonth.get(i);
						IRow row = thisTable.addRow(i);
						row.getCell("taskName").setValue(entryInfo.getTaskName());
						row.getCell("planEndDate").setValue(entryInfo.getPlanEndDate());
						if (entryInfo.getCompletePrecent() == 100) {
							row.getCell("completePercent").getStyleAttributes().setLocked(true);
							row.getCell("isComplete").setValue(WeekReportEnum.YES);
						} else {
							row.getCell("completePercent").getStyleAttributes().setLocked(false);
							row.getCell("isComplete").setValue(WeekReportEnum.NO);
						}
						row.getCell("completePercent").setValue(new Integer(entryInfo.getCompletePrecent()));
						row.getCell("realEndDate").setValue(entryInfo.getRealEndDate());
						row.getCell("intendEndDate").setValue(entryInfo.getIntendEndDate());
						row.getCell("description").setValue(entryInfo.getDescription());
						row.getCell("respPerson").setValue(entryInfo.getRespPerson());
						row.getCell("respDept").setValue(entryInfo.getRespDept());
						row.getCell("entryID").setValue(entryInfo.getId());
						FDCScheduleTaskInfo task = entryInfo.getRelateTask();
						Date actualStartDate = task.getActualStartDate() == null ? new Date() : task.getActualStartDate();
						row.getCell("taskID").setValue(entryInfo.getRelateTask());
						row.getCell("actualStartDate").setValue(actualStartDate);
						row.getCell("completeAmount").setValue(entryInfo.getCompleteAmount());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 描述：设置本月任务表是否可以编辑
	 * @param willBeLocked 参数为true时，锁住不能编辑
	 * @Author：owen_wen
	 * @CreateTime：2013-7-4
	 */
	private void setThisMonthTableEditable(boolean willBeLocked) {
		thisTable.getColumn("isComplete").getStyleAttributes().setLocked(willBeLocked);
		thisTable.getColumn("completePercent").getStyleAttributes().setLocked(willBeLocked);
		thisTable.getColumn("actualStartDate").getStyleAttributes().setLocked(willBeLocked);
		thisTable.getColumn("realEndDate").getStyleAttributes().setLocked(willBeLocked);
		thisTable.getColumn("description").getStyleAttributes().setLocked(willBeLocked);
	}
	
	public void initEntry() {
		thisTable.checkParsed();
		// 初始化任务类型 枚举值
		this.thisTable.getColumn("isComplete").setEditor(getKDComboBoxEditor(WeekReportEnum.getEnumList()));
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
		// 设置下月任务不可编辑
		nextTable.getStyleAttributes().setLocked(true);
	}

	/**
	 * 绑定的控件为KDComboBox的cellEditor
	 * 
	 * @param enumList
	 *            枚举的list 例如：CertifacateNameEnum.getEnumList()
	 * @return
	 */
	public ICellEditor getKDComboBoxEditor(List enumList) {
		KDComboBox comboField = new KDComboBox();
		if (enumList != null)
			for (int i = 0; i < enumList.size(); i++) {
				comboField.addItem(enumList.get(i));
			}
		ICellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		return comboEditor;
	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-29
	 * @version EAS7.0
	 * @see
	 */

	public void onLoad() throws Exception {
		super.onLoad();
		
		initButtonStyle();
		
		Date date = (Date) pkPeriod.getValue();
		Date firstDate = DateHelper.getFirstDayOfMonth(date);
		Date lastDate = DateHelper.getLastDayOfMonth(date);
		
		this.pkStartDate.setValue(firstDate);
		this.pkEndDate.setValue(lastDate);
		
		int year = OpReportBaseHelper.getDateFiled(date, Calendar.YEAR);
		int month = OpReportBaseHelper.getDateFiled(date, Calendar.MONTH) + 1;
		
		txtMonth.setText(year + "年  第" + month + "月");
		
		this.pkPeriod.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent arg0) {
				try {
					pkPeriod.setDatePattern("yyyy年第MM月");
					Date selectDate = (Date) pkPeriod.getValue();
					boolean isCurrentMonth = isCurrentMonth(selectDate);
					actionEdit.setEnabled(isCurrentMonth);
					thisTable.getStyleAttributes().setLocked(!isCurrentMonth);
					setValueMonth();
				} catch (Exception e) {
					handUIException(e);
				}
			}
		});

		if (getUIContext().get("isFromWorkflow") != null && Boolean.valueOf(getUIContext().get("isFromWorkflow").toString()).booleanValue()) {
			// 工作流展示界面
			if (getUIContext().get("ID") != null) {
				loadHead();
				loadEntryDataToTable();
			}
		} else if (getOprtState().equals(OprtState.EDIT)) {
			isHaveMonthReport();
			setThisTableLockedStateByBillState();
			this.thisTable.getStyleAttributes().setLocked(true);
			this.btnEdit.setEnabled(true);
		} else if (getOprtState().equals(OprtState.VIEW)) {// 查看
			initBottn();
			if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof ProjectMonthReportListUI) {
				loadHead();
				loadEntryDataToTable();
			} 
		} else {// 新增
			editData.setYear(year);
			editData.setMonth(month);
			this.btnEdit.setVisible(false);
			loadField();
		}
		this.actionUnAudit.setEnabled(false);
		this.actionUnAudit.setVisible(false);
		initEntry();
	}

	private void initButtonStyle() {
		this.btnViewMonthReport.setEnabled(true);
		this.btnExportExcel.setEnabled(true);
		this.btnPrintReport.setEnabled(true);
		this.btnAudit.setEnabled(true);
		this.btnAudit.setVisible(true);
		this.btnAddNew.setVisible(false);
		txtDescription.setMaxLength(255);
		btnPrintReport.setIcon(EASResource.getIcon("imgTbtn_print"));
		btnViewMonthReport.setIcon(EASResource.getIcon("imgTbtn_archive"));
		btnExportExcel.setEnabled(true);
		btnExportExcel.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.prmtCreator.setDisplayFormat("$user.person.name$");
	}
	
	private void loadHead() {
		this.txtMonth.setText(editData.getYear() + "年第" + editData.getMonth() + "月");
		this.prmtCreator.setValue(editData.getCreator());
		this.pkCreateTime.setValue(editData.getCreateTime());
		this.pkStartDate.setValue(editData.getStartDate());
		this.pkEndDate.setValue(editData.getEndDate());
		this.comboState.setSelectedItem(editData.getState(), true);
		this.txtDescription.setText(editData.getDescription());
	}

	public void initBottn() {
		this.btnEdit.setEnabled(false);
		this.btnSave.setEnabled(false);
		this.btnSubmit.setEnabled(false);
		this.btnAudit.setEnabled(false);
		this.btnUnAudit.setEnabled(false);
		this.btnExportExcel.setEnabled(false);
		btnPrintReport.setEnabled(false);
		btnViewMonthReport.setEnabled(false);
		/* modified by zhaoqin for R140918-0150 on 2015/01/19 */
		//btnAttachment.setEnabled(false);
		btnPrintReport.setEnabled(false);
		btnAuditResult.setEnabled(false);
		btnAuditResult.setVisible(false);
	}

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
				row.getCell("intendEndDate").getStyleAttributes().setLocked(true);
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
				row.getCell("realEndDate").getStyleAttributes().setLocked(true);
				row.getCell("intendEndDate").getStyleAttributes().setLocked(false);
				row.getCell("realEndDate").setValue(null);
				if (completePercent != null && !"".equals(completePercent) && 100 == new Integer(completePercent.toString()).intValue()) {
					row.getCell("completePercent").setValue(new Integer(100 - 1));
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

	protected void thisTable_editValueChanged(KDTEditEvent e) throws Exception {

	}

	/**
	 * 
	 * @description 验证预计完成日期
	 * @author 杜红明
	 * @createDate 2011-12-26
	 * @param row
	 *            void
	 * @version EAS7.0
	 * @param e
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

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		if (isRunningWorkFlow()) {
			this.thisTable.getStyleAttributes().setLocked(true);
			MsgBox.showWarning(this,
					"\u5355\u636E\u5DF2\u7ECF\u8FDB\u5165\u5DE5\u4F5C\u6D41\uFF0C\u4E0D\u80FD\u8FDB\u884C\u4FEE\u6539\u64CD\u4F5C\uFF01");
			SysUtil.abort();
		}
		setBtnStateByBillState();
		this.thisTable.getStyleAttributes().setLocked(false);
		thisTable.getColumn("taskName").getStyleAttributes().setLocked(true);
		thisTable.getColumn("planEndDate").getStyleAttributes().setLocked(true);
		thisTable.getColumn("respPerson").getStyleAttributes().setLocked(true);
		thisTable.getColumn("respDept").getStyleAttributes().setLocked(true);
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
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof ProjectMonthReportListUI) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 描述：
	 * 
	 * @throws Exception
	 *             创建人：yuanjun_lan 创建时间：2012-5-20
	 */
	public void setValueMonth() throws Exception {
		Date date = (Date) pkPeriod.getValue();
	    int year = OpReportBaseHelper.getDateFiled(date, Calendar.YEAR);
		int month = OpReportBaseHelper.getDateFiled(date, Calendar.MONTH) + 1;

	
		ProjectMonthReportCollection collection = getProjectMonthReport(year, month);
		if (collection.size() > 0) {
			isHaveMonthReport(collection);
			setThisTableLockedStateByBillState();
			this.btnEdit.setEnabled(true);
		} else {//此月无报告数据
			this.comboState.setSelectedItem(FDCBillStateEnum.SAVED);
			Date d = (Date) pkPeriod.getValue();
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			editData = (ProjectMonthReportInfo) createNewData();
			editData.setYear(c.get(Calendar.YEAR));
			editData.setMonth(c.get(Calendar.MONTH) + 1);
			editData.setState(FDCBillStateEnum.SAVED);
			editData.setStartDate(FDCDateHelper.getFirstDayOfMonth(d));
			editData.setEndDate(FDCDateHelper.getLastDayOfMonth(d));
			setDataObject(editData);
			loadHead();
			this.actionEdit.setEnabled(true);
			/** 这个地方会产生二次取数 */
			Map map = fetchData();
			List thisMonthList = (List) map.get("thisMonth");
			List nextMonthList = (List) map.get("nextMonth");
			resetThisTableData(thisMonthList);
			loadNextTableFields(nextMonthList);
			setThisMonthTableEditable(false);
		}
		setBtnStateByBillState();
	}

	/**
	 * 根据选择的日期得出当前日期是本年度的第几月
	 * 
	 * @param date
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {

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
		sic.add("entrys.planStartDate");
		sic.add("creator.id");
		sic.add("creator.name");
		sic.add("creator.number");
		sic.add("creator.person.id");
		sic.add("creator.person.name");
		sic.add("creator.person.number");
		return sic;
	}

	/**
	 * 
	 * @description
	 * @author 李建波
	 * @throws SQLException 
	 * @throws EASBizException 
	 * @createDate 2011-8-31
	 * @see
	 */
	private void isHaveMonthReport() throws BOSException, EASBizException, SQLException {
		Date date = (Date) pkPeriod.getValue();
	    int year = OpReportBaseHelper.getDateFiled(date, Calendar.YEAR);
		int month = OpReportBaseHelper.getDateFiled(date, Calendar.MONTH) + 1;
		
		ProjectMonthReportCollection monthCollection = getProjectMonthReport(year, month);
		if (monthCollection.size() > 0) {
			editData = monthCollection.get(0);
			setDataObject(editData);
			loadHead();
			loadEntryDataToTable();
		}
	}
	
	/**
	 * 如果有月报告，那么进行数据填充处理
	 */
	private void isHaveMonthReport(ProjectMonthReportCollection monthCollection) throws BOSException {
		editData = monthCollection.get(0);
		setDataObject(editData);
		loadHead();
		loadEntryDataToTable();
	}

	private void setThisTableLockedStateByBillState() {
		if(FDCBillStateEnum.SAVED.equals((editData.getState()))
				|| FDCBillStateEnum.SUBMITTED.equals(editData.getState())){
			setThisMonthTableEditable(false);
		} else {
			setThisMonthTableEditable(true);
		}
	}

	/**
	 * 按年/月获取当前工程项目的月报告
	 * @throws SQLException 
	 * @throws EASBizException 
	 */
	private ProjectMonthReportCollection getProjectMonthReport(int year, int month) throws BOSException, EASBizException, SQLException {
		EntityViewInfo view = new EntityViewInfo();
	    view.setSelector(getSelectors());
		SorterItemCollection sic = new SorterItemCollection();
		sic.add(new SorterItemInfo("entrys.planEndDate"));
		view.setSorter(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", getCurProjectIDForAll()));
		filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
		filter.getFilterItems().add(new FilterItemInfo("month", new Integer(month)));
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof SpecialScheduleExecuteUI) {
			filter.getFilterItems().add(
					new FilterItemInfo("projectSpecial.id", editData.getProjectSpecial().getId().toString(), CompareType.EQUALS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
		}
		view.setFilter(filter);
		ProjectMonthReportCollection monthCollection = ProjectMonthReportFactory.getRemoteInstance().getProjectMonthReportCollection(view);
		return monthCollection;
	}

	public void loadEntryDataToTable() {
		List nextMonth = new ArrayList();
		List thisMonth = new ArrayList();
		ProjectMonthReportEntryCollection collection = editData.getEntrys();
		if (collection.size() > 0) {
			for (int i = 0; i < collection.size(); i++) {
				ProjectMonthReportEntryInfo entryInfo = collection.get(i);
				if (entryInfo.isIsNext()) {
					nextMonth.add(entryInfo);
				} else {
					thisMonth.add(entryInfo);
				}
			}
		}
		loadThisTableFields(thisMonth);
		loadNextTableFields(nextMonth);
	}
	
	public void loadFields() {
		super.loadFields();
		loadHead();
		loadEntryDataToTable();
	}

	private void loadField() {
		try {
			Map map = fetchData();
			if (map != null && !map.isEmpty()) {// 不为空
				List thisMonth = (List) map.get("thisMonth");
				List nextMonth = (List) map.get("nextMonth");
				loadThisTableFields(thisMonth);
				loadNextTableFields(nextMonth);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public KDTDefaultCellEditor getMultiEditor(int length) {
		KDMultiLangArea multTextArea = new KDMultiLangArea();
		multTextArea.setAutoscrolls(true);
		multTextArea.setMaxLength(length);
		multTextArea.setEditable(true);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(multTextArea);
		return editor;
	}

	public void loadNextTableFields(List list) {
		nextTable.removeRows(false);
		this.nextTable.checkParsed();
		if (list.size() > 0) {// 集合大于0
			for (int i = 0; i < list.size(); i++) {// 循环将分录放入表格中
				ProjectMonthReportEntryInfo entryInfo = (ProjectMonthReportEntryInfo) list.get(i);
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

	public void resetThisTableData(List thisMonth) {
		thisTable.removeRows(false);
		if (thisMonth.size() > 0) {// 集合大于0
			for (int i = 0; i < thisMonth.size(); i++) {// 循环将分录放入表格中
				ProjectMonthReportEntryInfo entryInfo = (ProjectMonthReportEntryInfo) thisMonth.get(i);
				fillMonthReportToTable(entryInfo);
			}
		}
	}
	

	public void loadThisTableFields(List list) {
		thisTable.removeRows(false);
		this.thisTable.checkParsed();
		if (list.size() > 0) {// 集合大于0
			for (int i = 0; i < list.size(); i++) {// 循环将分录放入表格中
				ProjectMonthReportEntryInfo entryInfo = (ProjectMonthReportEntryInfo) list.get(i);
				fillMonthReportToTable(entryInfo);
			}
		}
	}

	private void fillMonthReportToTable(ProjectMonthReportEntryInfo entryInfo) {
		IRow row = thisTable.addRow();
		row.getCell("taskName").setValue(entryInfo.getTaskName());
		row.getCell("planEndDate").setValue(entryInfo.getPlanEndDate());
		if (entryInfo.getCompletePrecent() == 100) {
			row.getCell("isComplete").setValue(WeekReportEnum.YES);
		} else {
			row.getCell("isComplete").setValue(WeekReportEnum.NO);
		}
		row.getCell("completePercent").setValue(new Integer(entryInfo.getCompletePrecent()));
		row.getCell("realEndDate").setValue(entryInfo.getRealEndDate());
		row.getCell("description").setValue(entryInfo.getDescription());
		row.getCell("respPerson").setValue(entryInfo.getRespPerson());
		row.getCell("respDept").setValue(entryInfo.getRespDept());
		row.getCell("entryID").setValue(entryInfo.getId());
		FDCScheduleTaskInfo task = entryInfo.getRelateTask();
		row.getCell("startDate").setValue(task.getStart());
		row.getCell("taskID").setValue(task);
		Date actualStartDate = null;
		if (editData.getId() == null) {
			actualStartDate = task.getActualStartDate() == null ? task.getStart() : task.getActualStartDate();
		} else {
			actualStartDate = entryInfo.getPlanStartDate() == null ? task.getStart() : entryInfo.getPlanStartDate();
		}
		row.getCell("intendEndDate").setValue(
				entryInfo.getIntendEndDate() == null ? (task.getIntendEndDate() == null ? task.getEnd() : task.getIntendEndDate())
						: entryInfo.getIntendEndDate());

		row.getCell("actualStartDate").setValue(actualStartDate);
		row.getCell("completeAmount").setValue(entryInfo.getCompleteAmount());
		row.setUserObject(entryInfo);
		lockedCell(new Integer(entryInfo.getCompletePrecent()), row);
	}

	public void storeNextTableFields() {
		int count = nextTable.getRowCount();
		for (int i = 0; i < count; i++) {
			IRow row = nextTable.getRow(i);
			Object obj = editData.getId();

			ProjectMonthReportEntryInfo entryInfo = new ProjectMonthReportEntryInfo();
			String taskName = row.getCell("taskName").getValue().toString().trim();
			entryInfo.setTaskName(taskName);

			if (FDCHelper.isEmpty(obj)) {
				entryInfo.setIsNext(true);
				Date ps = (Date) row.getCell("planStartDate").getValue();
				Date pe = (Date) row.getCell("planEndDate").getValue();
				entryInfo.setPlanStartDate(ps);
				entryInfo.setPlanEndDate(pe);
				PersonInfo personInfo = (PersonInfo) row.getCell("respPerson").getValue();
				FullOrgUnitInfo unitInfo = (FullOrgUnitInfo) row.getCell("respDept").getValue();
				entryInfo.setRespDept(unitInfo);
				if (null != row.getCell("taskID") && row.getCell("taskID").getValue() instanceof FDCScheduleTaskInfo) {
					entryInfo.setRelateTask((FDCScheduleTaskInfo) row.getCell("taskID").getValue());
				}
				entryInfo.setRespPerson(personInfo);
				entryInfo.setTaskName(taskName);
				entryInfo.setHead(editData);
				editData.getEntrys().add(entryInfo);
			}
		}
	}

	/**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 * 
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 * @author liang_ren969
	 * @date 2010-8-5
	 */
	public static int getStringlen(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		value = value.trim();
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
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
	 * @description 验证完成进度
	 * @author 杜红明
	 * @createDate 2011-12-26
	 * @param row
	 *            void
	 * @version EAS7.0
	 * @see
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
			ProjectMonthReportEntryInfo entryIfno = (ProjectMonthReportEntryInfo) row.getUserObject();
			int lastTaskComplete = entryIfno.getRelateTask().getComplete().intValue();
			if (lastTaskComplete > temp) {
				FDCMsgBox.showWarning(this, row.getCell("taskName").getValue() + "任务完成进度小于上次汇报记录!");
				SysUtil.abort();
			}
		}
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		chkMenuItemSubmitAndAddNew.setSelected(false);//不需要连续新增
	}

	public void storeThisTableFields() {
		if (!FDCHelper.isEmpty(editData.getId())) { //修改已有数据
			for (int i = 0; i < thisTable.getRowCount(); i++) {
				IRow row = thisTable.getRow(i);
				Date realEndDate = (Date) row.getCell("realEndDate").getValue();
				Date actualStartDate = (Date) row.getCell("actualStartDate").getValue();
				Date intendEndDate = (Date) row.getCell("intendEndDate").getValue();
				BigDecimal completeAmount = (BigDecimal) row.getCell("completeAmount").getValue();
				verifyCompletePercentInput(row);

				ProjectMonthReportEntryInfo entryInfo = (ProjectMonthReportEntryInfo) row.getUserObject();
				entryInfo.setIsComplete(row.getCell("isComplete").getValue().equals(WeekReportEnum.YES));
				entryInfo.setRealEndDate(realEndDate);
				entryInfo.setPlanStartDate(actualStartDate);
				entryInfo.setIntendEndDate(intendEndDate);
				entryInfo.setCompleteAmount(completeAmount);
				entryInfo.setCompletePrecent(Integer.parseInt(row.getCell("completePercent").getValue().toString()));
				String description = (String) row.getCell("description").getValue();
				if (description == null) {
					entryInfo.setDescription("");
				} else {
					entryInfo.setDescription(description.trim());
				}
			}

		} else { // 新增报告
			for (int i = 0; i < thisTable.getRowCount(); i++) {
				IRow row = thisTable.getRow(i);
				ProjectMonthReportEntryInfo entryInfo = (ProjectMonthReportEntryInfo) row.getUserObject();
				String taskName = row.getCell("taskName").getValue().toString().trim();
				entryInfo.setTaskName(taskName);
				Date endDate = (Date) row.getCell("planEndDate").getValue();
				entryInfo.setPlanEndDate(endDate);
				Date actualStartDate = (Date) row.getCell("actualStartDate").getValue();
				entryInfo.setPlanStartDate(actualStartDate);
				entryInfo.setIsNext(false);
				BigDecimal completeAmount = (BigDecimal) row.getCell("completeAmount").getValue();
				entryInfo.setCompleteAmount(completeAmount);
				Date realEndDate = (Date) row.getCell("realEndDate").getValue();
				Date intendEndDate = (Date) row.getCell("intendEndDate").getValue();
				if (row.getCell("completePercent").getValue() != null) {
					int newtemp = new BigDecimal(row.getCell("completePercent").getValue().toString()).intValue();
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
				entryInfo.setIntendEndDate(intendEndDate);
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
				editData.getEntrys().add(entryInfo);
			}
		}
	}

	/**
	 * 校验进度百分比只能录入0~100的整数
	 */
	private void verifyCompletePercentInput(IRow row) {
		Object complate = row.getCell("completePercent").getValue();
		if (complate == null || "".equals(complate)) {
			complate = new String("0");
		}
		int newtemp = new BigDecimal(complate.toString()).intValue();
		if (complate != null) {
			if (newtemp > 100) {
				FDCMsgBox.showInfo("只能输入0-100的整数");
				SysUtil.abort();
			}
		}
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProjectMonthReportFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return null;
	}

	protected Map getMainFilterParam() throws Exception {
		Map param = new HashMap();
		Date starDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkEndDate.getValue();
		Calendar calendar = Calendar.getInstance();
		// 月份+1
		calendar.setTime(starDate);
		calendar.add(Calendar.MONTH, 1);
		// 定义输出格式
		// 得到月份
		Date date = calendar.getTime();
		Date nextFist = DateHelper.getFirstDayOfMonth(date);

		Date nextLast = DateHelper.getLastDayOfMonth(date);

		param.put("starDate", starDate);
		param.put("endDate", endDate);
		param.put("nextFist", nextFist);
		param.put("nextLast", nextLast);
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("projectSpecial.*"));

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", getCurProjectIDForAll()));
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
			for (int i = 0; i < collection.size(); i++) {
				FDCScheduleInfo scheduleInfo = collection.get(0);
				param.put("stringPk", scheduleInfo.getId().toString());
				param.put("projectSpecial", scheduleInfo.getProjectSpecial());
			}
		}
		return param;
	}
	
	protected void verifyInputForSave() throws Exception {
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		beforeSave();
		comboState.setSelectedItem(FDCBillStateEnum.SAVED);
		printDebugInfo();
		super.actionSave_actionPerformed(e);
		prmtCreator.setValue(SysContext.getSysContext().getCurrentUser());
		
		/* modified by zhaoqin for R141011-0154 on 2015/01/16 */
		this.btnSave.setEnabled(false);
		this.btnEdit.setVisible(true);
		this.btnEdit.setEnabled(true);
		this.menuItemEdit.setEnabled(true);
		this.menuItemSave.setEnabled(false);
		this.txtDescription.setEnabled(false);
		this.thisTable.getStyleAttributes().setLocked(true);
	}
	
	private void printDebugInfo() {
		ProjectMonthReportInfo info = (ProjectMonthReportInfo) editData;
		for (int i = 0; i < info.getEntrys().size(); i++) {
			System.out.println(i + "\t" + info.getEntrys().get(i).getTaskName() + "\t" + info.getEntrys().get(i).getCompletePrecent()
					+ "\t" + info.getEntrys().get(i).getPlanStartDate());
		}
	}
	

	protected void thisTable_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getColIndex() == thisTable.getColumn("description").getColumnIndex()) {
			if (e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 1) {
				thisTable.showCellDetailInfo();
			}
		}
	}

	public boolean isRunningWorkFlow() throws BOSException {
		if (editData.getId() == null)
			return false;
		return isRunningWorkflow(editData.getId().toString());
	}

	public static boolean isRunningWorkflow(String objId) throws BOSException {
		boolean hasWorkflow = false;
		IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
		ProcessInstInfo procInsts[] = service2.getProcessInstanceByHoldedObjectId(objId);
		int i = 0;
		int n = procInsts.length;
		do {
			if (i >= n)
				break;
			if ("open.running".equals(procInsts[i].getState())) {
				hasWorkflow = true;
				break;
			}
			i++;
		} while (true);
		return hasWorkflow;
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null) {
			if (comboState.getSelectedItem() != null) {
				if (comboState.getSelectedItem().equals(FDCBillStateEnum.AUDITTED)) {
					if (editData.getId() != null) {
						ProjectMonthReportFactory.getRemoteInstance().unAudit(BOSUuid.read(editData.getId().toString()));
					}
					FDCMsgBox.showWarning("操作成功");
					this.btnUnAudit.setEnabled(false);
					this.btnAudit.setEnabled(true);
					editData.setState(FDCBillStateEnum.SUBMITTED);
					comboState.setSelectedItem(FDCBillStateEnum.SUBMITTED);
					actionEdit.setEnabled(true);
					menuItemEdit.setEnabled(true);
				} else {
					FDCMsgBox.showWarning("单据状态不允许进行此操作");
				}
			}
		}
	}

	public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception {
		if (editData == null || editData.getId() == null) {
			MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_WFHasNotInstance"));
			abort();
		}
		MultiApproveUtil.showApproveHis(editData.getId(), (com.kingdee.eas.base.uiframe.client.UIModelDialogFactory.class).getName(), this);
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (isModify()) {
			FDCMsgBox.showInfo("单据已被修改，请先提交。");
			this.abort();
		}

		//检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());
		
		String id = getSelectBOID();
		if (id != null) {
			ProjectMonthReportFactory.getRemoteInstance().audit(editData.getId());
			
			/* modified by zhaoqin for R141011-0154 on 2015/01/16 */
			editData.setState(FDCBillStateEnum.AUDITTED);
			comboState.setSelectedItem(FDCBillStateEnum.AUDITTED);
			FDCClientUtils.showOprtOK(this);
			//syncDataFromDB();
			//handleOldData();
			setSaveActionStatus();
			actionAudit.setEnabled(false);
		}
	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		beforeSave();
		Date firstDate = (Date) pkStartDate.getValue();
		Date lastDate = (Date) pkEndDate.getValue();
		prmtCreator.setValue(SysContext.getSysContext().getCurrentUser());
		if (pkStartDate.getValue() == null && pkEndDate.getValue() == null) {
			this.pkStartDate.setValue(firstDate);
			this.pkEndDate.setValue(lastDate);
		}
		
		/* modified by zhaoqin for R141011-0154 on 2015/01/16 */
		super.actionSubmit_actionPerformed(e);
		
		this.btnSubmit.setEnabled(false);
		this.menuItemSubmit.setEnabled(false);
		btnAudit.setEnabled(true);
		btnUnAudit.setEnabled(true);
		this.menuItemEdit.setEnabled(false);
		this.menuItemSave.setEnabled(false);
		this.btnSave.setEnabled(false);
		this.btnEdit.setVisible(true);
		this.btnEdit.setEnabled(true);
	}

	/**
	 * 要求提交后审批按钮还可以显示
	 * @author owen_wen 2013-07-08
	 */
	protected void setAuditButtonStatus(String oprtType) {
		if (STATUS_VIEW.equals(oprtType) || STATUS_EDIT.equals(oprtType)) {
			actionAudit.setVisible(true);
			actionUnAudit.setVisible(true);
			actionAudit.setEnabled(true);
			actionUnAudit.setEnabled(true);

			FDCBillInfo bill = (FDCBillInfo) editData;
			if (editData != null) {
				if (FDCBillStateEnum.AUDITTED.equals(bill.getState())) {
					actionUnAudit.setVisible(true);
					actionUnAudit.setEnabled(true);
					actionAudit.setVisible(false);
					actionAudit.setEnabled(false);
				} else {
					actionUnAudit.setVisible(false);
					actionUnAudit.setEnabled(false);
					actionAudit.setVisible(true);
					actionAudit.setEnabled(true);
				}
			}
		} else {
			actionAudit.setVisible(false);
			actionUnAudit.setVisible(false);
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		}
	}

	protected boolean isModifySave() {
		return false;
	}

	public boolean isModify() {
		return false;
	}

	protected IObjectValue createNewData() {
		ProjectMonthReportInfo projectMonthReportInfo = new ProjectMonthReportInfo();
		if (getCurProject() != null) {
			CurProjectInfo project = getCurProject();
			projectMonthReportInfo.setProject(project);
			projectMonthReportInfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
			projectMonthReportInfo.setCreator((UserInfo) SysContext.getSysContext().getCurrentUser());
			projectMonthReportInfo.setState(FDCBillStateEnum.SAVED);
			projectMonthReportInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		}
		if (getProjectSpecialInfo() != null) {
			projectMonthReportInfo.setProjectSpecial(getProjectSpecialInfo());
		}
		projectMonthReportInfo.setState(FDCBillStateEnum.SAVED);
		return projectMonthReportInfo;
	}

	public void actionPrintReport_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getId() == null) {
			FDCMsgBox.showWarning("请先保存单据");
			SysUtil.abort();
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put("reportInfo", editData);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
		IUIWindow uiWindow = uiFactory.create(ViewProjectMonthReportPrintUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	
	protected String getReportTableName() {
		return "t_sch_projectmonthreport";
	}

	public void loadNextTableFields() {
	}

	public void loadThisTableFields() {
	}
}