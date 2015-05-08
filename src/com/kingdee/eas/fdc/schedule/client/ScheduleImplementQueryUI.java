/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.kds.KDSSheet;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.Kdt2Kds;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.print.config.ui.HeaderFooterModel;
import com.kingdee.bos.ctrl.print.printjob.IPrintJob;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;

/**
 * output class name
 */
public class ScheduleImplementQueryUI extends AbstractScheduleImplementQueryUI {
	private static final Logger logger = CoreUIObject.getLogger(ScheduleImplementQueryUI.class);

	List tables = new ArrayList();
	public ScheduleImplementQueryUI() throws Exception {
		super();
	}
	/*
	public void onLoad() throws Exception {
		// TODO 自动生成方法存根
		super.onLoad();
		this.setUITitle("执行中任务查询");
		initQueryFilter();
		initTable(this.tableMain);
		this.tableMain.setName("汇总报表");
	}

	private void initQueryFilter() {
		SCHClientHelper.setRespDeptF7(this.prmtAdminDept, this, SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(
				"longNumber",SysContext.getSysContext().getCurrentOrgUnit().getLongNumber()+"%",CompareType.LIKE));
		view.setFilter(filter);
		prmtProject.setEntityViewInfo(view);
		
		this.prmtAdminDept.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				prmtAdminPerson.setValue(null);
				if (prmtAdminDept.getValue() != null) {
					if (prmtAdminDept.getValue() instanceof AdminOrgUnitInfo) {
						SCHClientHelper.setPersonF7(prmtAdminPerson,
								((AdminOrgUnitInfo) prmtAdminDept.getValue()).getId().toString(), ScheduleImplementQueryUI.this);
					}
				} else {
					if (SysContext.getSysContext().getCurrentAdminUnit() != null) {
						SCHClientHelper.setPersonF7(prmtAdminPerson, SysContext.getSysContext().getCurrentAdminUnit().getId()
								.toString(), ScheduleImplementQueryUI.this);
					} else {
						SCHClientHelper.setPersonF7(prmtAdminPerson, null, ScheduleImplementQueryUI.this);
					}

				}

			}

		});
		this.prmtAdminDept.setValue(SysContext.getSysContext().getCurrentAdminUnit());
	}

	public void initTable(KDTable table) {

		IRow headRow = table.addHeadRow();
		IColumn column = null;

		column = table.addColumn();
		column.setKey("projectName");
		KDDatePicker datePicker = new KDDatePicker();
		datePicker.setEditable(false);
		column.setWidth(150);
		headRow.getCell("projectName").setValue("计划名称");

		column = table.addColumn();
		column.setKey("status");
		headRow.getCell("status").setValue("任务状态");

		column = table.addColumn();
		column.setKey("statusDetail");
		column.setWidth(120);
		headRow.getCell("statusDetail").setValue("任务状态明细");

		column = table.addColumn();
		column.setKey("taskName");
		headRow.getCell("taskName").setValue("任务名称");

		column = table.addColumn();
		column.setKey("start");
		column.setWidth(80);
		headRow.getCell("start").setValue("开始时间");

		column = table.addColumn();
		column.setKey("end");
		column.setWidth(80);
		headRow.getCell("end").setValue("结束时间");

		column = table.addColumn();
		column.setKey("duration");
		column.getStyleAttributes().setNumberFormat("#,###");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow.getCell("duration").setValue("持续时间(天)");

		column = table.addColumn();
		column.setKey("freeTime");
		column.getStyleAttributes().setNumberFormat("#,###");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow.getCell("freeTime").setValue("自由时差(天)");

		column = table.addColumn();
		column.setKey("percent");
		column.getStyleAttributes().setNumberFormat("#,##0.00%");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		column.setWidth(80);
		headRow.getCell("percent").setValue("完成百分比");

		column = table.addColumn();
		column.setKey("part");
		column.setWidth(150);
		headRow.getCell("part").setValue("责任部门");

		column = table.addColumn();
		column.setKey("person");
		column.setWidth(60);
		headRow.getCell("person").setValue("责任人");

		column = table.addColumn();
		column.setKey("description");
		headRow.getCell("description").setValue("备注");

	}

	*//**
	 * output serchAction_actionPerformed
	 *//*
	public void serchAction_actionPerformed(ActionEvent e) throws Exception {
		EntityViewInfo viewInfo = getEntityViewInfo();
		if (viewInfo == null) {
			return;
		}
		for (int i = 1; i < this.tabReport.getTabCount();) {
			this.tabReport.remove(i);
		}
		this.tableMain.removeRows();
		this.tableMain.getTreeColumn().setDepth(2);
		tables = new ArrayList();
		tables.add(this.tableMain);
		OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
		FDCScheduleTaskCollection taskCollection = FDCScheduleTaskFactory.getRemoteInstance()
				.getFDCScheduleTaskCollection(
						"select *,project.*,person.* where state='6' and version is null and org.longnumber like '"
								+ org.getLongNumber() + "%' order by name");
		for (int i = 0; i < taskCollection.size(); i++) {
			FDCScheduleTaskInfo taskInfo = taskCollection.get(i);
			KDTable table = new KDTable();
			table.setName(taskInfo.getName());
			tables.add(table);
			initTable(table);
			this.tabReport.addTab(taskInfo.getName(), table);

			IRow row = this.tableMain.addRow();
			int rowIndex = this.tableMain.getRowCount();
			row.setTreeLevel(0);
			this.tableMain.getMergeManager().mergeBlock(row.getRowIndex(), 0, row.getRowIndex(),
					this.tableMain.getColumnCount() - 1);
			row.getCell(0).setValue(
					"计划编号: " + taskInfo.getNumber() + "; 计划名称: " + taskInfo.getName() + "; 所属工程项目: "
							+ taskInfo.getSchedule().getProject().getName() + "; 责任人: " + taskInfo.getAdminPerson().getName() + ";");
			EntityViewInfo viewInfoClone = (EntityViewInfo) viewInfo.clone();
			viewInfoClone.getFilter().appendFilterItem("project.id", taskInfo.getId().toString());
			if (viewInfoClone.getFilter().getMaskString() == null
					|| "".equals(viewInfoClone.getFilter().getMaskString())) {
				viewInfoClone.getFilter().setMaskString("#" + (viewInfoClone.getFilter().getFilterItems().size() - 1));
			} else {
				viewInfoClone.getFilter().setMaskString(
						viewInfoClone.getFilter().getMaskString() + " and #"
								+ (viewInfoClone.getFilter().getFilterItems().size() - 1));
			}
			List taskCommonWeiKaiShi = new ArrayList();
			List taskCommonZhengChangYiWanChen = new ArrayList();
			List taskCommonYanWuYiWanChen = new ArrayList();
			List taskCommonProcessJinXinZhong = new ArrayList();
			List taskDelayWeiWanChen = new ArrayList();
			List taskDelayWeiKaiShi = new ArrayList();
			Date standDate = ModifyStandDate.getStandDate();
			FDCScheduleTaskCollection tasksCollection = FDCScheduleTaskFactory.getRemoteInstance()
					.getFDCScheduleTaskCollection(viewInfoClone);
			for (int m = 0; m < tasksCollection.size(); m++) {
				FDCScheduleTaskInfo taskInfos = taskCollection.get(m);
				if (taskInfos.getPercent().floatValue() == 0) {
					if (standDate.after(taskInfo.getPioneerStartDate())) {
						taskDelayWeiKaiShi.add(taskInfo);
					} else {
						taskCommonWeiKaiShi.add(taskInfo);
					}
					continue;
				} else if (taskInfos.getPercent().floatValue() == 100) {
					if (taskInfo.isIsDelayComplate()) {
						taskCommonYanWuYiWanChen.add(taskInfo);
					} else {
						taskCommonZhengChangYiWanChen.add(taskInfo);
					}
					continue;
				} else if (0 < taskInfo.getPercent().floatValue() && taskInfo.getPercent().floatValue() < 100) {

					if (standDate.before(taskInfo.getPioneerEndDate())) {
						taskCommonProcessJinXinZhong.add(taskInfo);
					} else {
						taskDelayWeiWanChen.add(taskInfo);
					}
					continue;
				}
			}
			int select = this.cmbStatus.getSelectedIndex();
			if (select == 1) {
				fillRowByCommon(table, projectInfo, taskCommonWeiKaiShi, taskCommonZhengChangYiWanChen,
						taskCommonYanWuYiWanChen);
			} else if (select == 2) {
				fillRowByComplate(table, projectInfo, taskCommonProcessJinXinZhong);
			} else if (select == 3) {
				fillRowNoStart(table, projectInfo, taskDelayWeiWanChen, taskDelayWeiKaiShi);
			} else if (select == 0) {
				fillRowByCommon(table, projectInfo, taskCommonWeiKaiShi, taskCommonZhengChangYiWanChen,
						taskCommonYanWuYiWanChen);
				fillRowByComplate(table, projectInfo, taskCommonProcessJinXinZhong);
				fillRowNoStart(table, projectInfo, taskDelayWeiWanChen, taskDelayWeiKaiShi);
			}

			table.getMergeManager().mergeBlock(0, 0, table.getRowCount() - 1, 0);
			table.getStyleAttributes().setLocked(true);
			tableMain.getMergeManager().mergeBlock(rowIndex, 0, tableMain.getRowCount() - 1, 0);
		}
		this.tableMain.getStyleAttributes().setLocked(true);
	}

	// 正常
	void fillRowByCommon(KDTable table, ProcessProjectInfo info, List taskCommonWeiKaiShi,
			List taskCommonZhengChangYiWanChen, List taskCommonYanWuYiWanChen) {
		int rowIndex = table.getRowCount();
		int countRowIndex = tableMain.getRowCount();
		int rowCount = 0;
		if (taskCommonWeiKaiShi.size() > 0) {
			for (int i = 0; i < taskCommonWeiKaiShi.size(); i++) {
				ProcessProjectTaskInfo taskInfo = (ProcessProjectTaskInfo) taskCommonWeiKaiShi.get(i);
				IRow row = table.addRow();
				row.getCell("projectName").setValue(info.getName());
				row.getCell("status").setValue("正常任务");
				row.getCell("statusDetail").setValue("正常未开始任务(" + taskCommonWeiKaiShi.size() + ")");
				row.getCell("taskName").setValue(taskInfo.getName());
				row.getCell("start").setValue(taskInfo.getPioneerStartDate());
				row.getCell("end").setValue(taskInfo.getPioneerEndDate());
				row.getCell("duration").setValue(taskInfo.getDuration());
				row.getCell("freeTime").setValue(taskInfo.getFreeTime());
				row.getCell("description").setValue(taskInfo.getDescription());
				row.getCell("percent").setValue(
						(taskInfo.getPercent().divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP, 4)).setScale(4,
								BigDecimal.ROUND_HALF_UP));
				if (taskInfo.getPerson() != null) {
					row.getCell("person").setValue(taskInfo.getPerson().getName());
				}
				if (taskInfo.getAdminOrg() != null) {
					row.getCell("part").setValue(taskInfo.getAdminOrg().getName());
				}
				rowCount++;

				IRow rowClone = (IRow) row.clone();
				rowClone.setTreeLevel(1);
				tableMain.addRow(tableMain.getRowCount(), rowClone);
			}

			table.getMergeManager().mergeBlock(table.getRowCount() - taskCommonWeiKaiShi.size(), 2,
					table.getRowCount() - 1, 2);
			tableMain.getMergeManager().mergeBlock(tableMain.getRowCount() - taskCommonWeiKaiShi.size(), 2,
					tableMain.getRowCount() - 1, 2);
		} else {
			rowCount++;
			IRow row = table.addRow();
			row.getCell("projectName").setValue(info.getName());
			row.getCell("status").setValue("正常任务");
			row.getCell("statusDetail").setValue("正常未开始任务(" + taskCommonWeiKaiShi.size() + ")");

			row = tableMain.addRow();
			row.setTreeLevel(1);
			row.getCell("projectName").setValue(info.getName());
			row.getCell("status").setValue("正常任务");
			row.getCell("statusDetail").setValue("正常未开始任务(" + taskCommonWeiKaiShi.size() + ")");
		}

		if (taskCommonZhengChangYiWanChen.size() > 0) {
			for (int i = 0; i < taskCommonZhengChangYiWanChen.size(); i++) {
				ProcessProjectTaskInfo taskInfo = (ProcessProjectTaskInfo) taskCommonZhengChangYiWanChen.get(i);
				IRow row = table.addRow();
				row.getCell("projectName").setValue(info.getName());
				row.getCell("status").setValue("正常任务");
				row.getCell("statusDetail").setValue("正常已完成任务(" + taskCommonZhengChangYiWanChen.size() + ")");
				row.getCell("taskName").setValue(taskInfo.getName());
				row.getCell("start").setValue(taskInfo.getPioneerStartDate());
				row.getCell("end").setValue(taskInfo.getPioneerEndDate());
				row.getCell("duration").setValue(taskInfo.getDuration());
				row.getCell("freeTime").setValue(taskInfo.getFreeTime());
				row.getCell("description").setValue(taskInfo.getDescription());
				row.getCell("percent").setValue(
						(taskInfo.getPercent().divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP, 4)).setScale(4,
								BigDecimal.ROUND_HALF_UP));
				if (taskInfo.getPerson() != null) {
					row.getCell("person").setValue(taskInfo.getPerson().getName());
				}
				if (taskInfo.getAdminOrg() != null) {
					row.getCell("part").setValue(taskInfo.getAdminOrg().getName());
				}
				rowCount++;

				IRow rowClone = (IRow) row.clone();
				rowClone.setTreeLevel(1);
				tableMain.addRow(tableMain.getRowCount(), rowClone);
			}
			table.getMergeManager().mergeBlock(table.getRowCount() - taskCommonZhengChangYiWanChen.size(), 2,
					table.getRowCount() - 1, 2);
			tableMain.getMergeManager().mergeBlock(tableMain.getRowCount() - taskCommonZhengChangYiWanChen.size(), 2,
					tableMain.getRowCount() - 1, 2);
		} else {
			rowCount++;
			IRow row = table.addRow();
			row.getCell("projectName").setValue(info.getName());
			row.getCell("status").setValue("正常任务");
			row.getCell("statusDetail").setValue("正常已完成任务(" + taskCommonZhengChangYiWanChen.size() + ")");

			row = tableMain.addRow();
			row.setTreeLevel(1);
			row.getCell("projectName").setValue(info.getName());
			row.getCell("status").setValue("正常任务");
			row.getCell("statusDetail").setValue("正常已完成任务(" + taskCommonZhengChangYiWanChen.size() + ")");
		}

		if (taskCommonYanWuYiWanChen.size() > 0) {
			for (int i = 0; i < taskCommonYanWuYiWanChen.size(); i++) {
				ProcessProjectTaskInfo taskInfo = (ProcessProjectTaskInfo) taskCommonYanWuYiWanChen.get(i);
				IRow row = table.addRow();
				row.getCell("projectName").setValue(info.getName());
				row.getCell("status").setValue("正常任务");
				row.getCell("statusDetail").setValue("延误已完成任务(" + taskCommonYanWuYiWanChen.size() + ")");
				row.getCell("taskName").setValue(taskInfo.getName());
				row.getCell("start").setValue(taskInfo.getPioneerStartDate());
				row.getCell("end").setValue(taskInfo.getPioneerEndDate());
				row.getCell("duration").setValue(taskInfo.getDuration());
				row.getCell("freeTime").setValue(taskInfo.getFreeTime());
				row.getCell("description").setValue(taskInfo.getDescription());
				row.getCell("percent").setValue(
						(taskInfo.getPercent().divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP, 4)).setScale(4,
								BigDecimal.ROUND_HALF_UP));
				if (taskInfo.getPerson() != null) {
					row.getCell("person").setValue(taskInfo.getPerson().getName());
				}
				if (taskInfo.getAdminOrg() != null) {
					row.getCell("part").setValue(taskInfo.getAdminOrg().getName());
				}
				rowCount++;

				IRow rowClone = (IRow) row.clone();
				rowClone.setTreeLevel(1);
				tableMain.addRow(tableMain.getRowCount(), rowClone);
			}
			table.getMergeManager().mergeBlock(table.getRowCount() - taskCommonYanWuYiWanChen.size(), 2,
					table.getRowCount() - 1, 2);
			tableMain.getMergeManager().mergeBlock(tableMain.getRowCount() - taskCommonYanWuYiWanChen.size(), 2,
					tableMain.getRowCount() - 1, 2);
		} else {
			rowCount++;
			IRow row = table.addRow();
			row.getCell("projectName").setValue(info.getName());
			row.getCell("status").setValue("正常任务");
			row.getCell("statusDetail").setValue("延误已完成任务(" + taskCommonYanWuYiWanChen.size() + ")");

			row = tableMain.addRow();
			row.setTreeLevel(1);
			row.getCell("projectName").setValue(info.getName());
			row.getCell("status").setValue("正常任务");
			row.getCell("statusDetail").setValue("延误已完成任务(" + taskCommonYanWuYiWanChen.size() + ")");
		}
		table.getMergeManager().mergeBlock(rowIndex, 1, rowIndex + rowCount - 1, 1);
		tableMain.getMergeManager().mergeBlock(countRowIndex, 1, countRowIndex + rowCount - 1, 1);
	}

	// 正常进行中状态
	void fillRowByComplate(KDTable table, ProcessProjectInfo info, List taskCommonProcessJinXinZhong) {
		int rowIndex = table.getRowCount();
		int countRowIndex = tableMain.getRowCount();
		int rowCount = 0;
		if (taskCommonProcessJinXinZhong.size() > 0) {
			for (int i = 0; i < taskCommonProcessJinXinZhong.size(); i++) {
				ProcessProjectTaskInfo taskInfo = (ProcessProjectTaskInfo) taskCommonProcessJinXinZhong.get(i);
				IRow row = table.addRow();
				row.getCell("projectName").setValue(info.getName());
				row.getCell("status").setValue("正常进行中任务");
				row.getCell("statusDetail").setValue("正常进行中任务(" + taskCommonProcessJinXinZhong.size() + ")");
				row.getCell("taskName").setValue(taskInfo.getName());
				row.getCell("start").setValue(taskInfo.getPioneerStartDate());
				row.getCell("end").setValue(taskInfo.getPioneerEndDate());
				row.getCell("duration").setValue(taskInfo.getDuration());
				row.getCell("freeTime").setValue(taskInfo.getFreeTime());
				row.getCell("description").setValue(taskInfo.getDescription());
				row.getCell("percent").setValue(
						(taskInfo.getPercent().divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP, 4)).setScale(4,
								BigDecimal.ROUND_HALF_UP));
				if (taskInfo.getPerson() != null) {
					row.getCell("person").setValue(taskInfo.getPerson().getName());
				}
				if (taskInfo.getAdminOrg() != null) {
					row.getCell("part").setValue(taskInfo.getAdminOrg().getName());
				}
				rowCount++;

				IRow rowClone = (IRow) row.clone();
				rowClone.setTreeLevel(1);
				tableMain.addRow(tableMain.getRowCount(), rowClone);
			}
			table.getMergeManager().mergeBlock(table.getRowCount() - taskCommonProcessJinXinZhong.size(), 2,
					table.getRowCount() - 1, 2);
			tableMain.getMergeManager().mergeBlock(tableMain.getRowCount() - taskCommonProcessJinXinZhong.size(), 2,
					tableMain.getRowCount() - 1, 2);
		} else {
			rowCount++;
			IRow row = table.addRow();
			row.getCell("projectName").setValue(info.getName());
			row.getCell("status").setValue("正常进行中任务");
			row.getCell("statusDetail").setValue("正常进行中任务(" + taskCommonProcessJinXinZhong.size() + ")");

			row = tableMain.addRow();
			row.setTreeLevel(1);
			row.getCell("projectName").setValue(info.getName());
			row.getCell("status").setValue("正常进行中任务");
			row.getCell("statusDetail").setValue("正常进行中任务(" + taskCommonProcessJinXinZhong.size() + ")");
		}

		table.getMergeManager().mergeBlock(rowIndex, 1, rowIndex + rowCount - 1, 1);
		tableMain.getMergeManager().mergeBlock(countRowIndex, 1, countRowIndex + rowCount - 1, 1);
	}

	// 延误
	void fillRowNoStart(KDTable table, ProcessProjectInfo info, List taskDelayWeiWanChen, List taskDelayWeiKaiShi) {
		int rowIndex = table.getRowCount();
		int countRowIndex = tableMain.getRowCount();
		int rowCount = 0;
		if (taskDelayWeiWanChen.size() > 0) {
			for (int i = 0; i < taskDelayWeiWanChen.size(); i++) {
				ProcessProjectTaskInfo taskInfo = (ProcessProjectTaskInfo) taskDelayWeiWanChen.get(i);
				IRow row = table.addRow();
				row.getCell("projectName").setValue(info.getName());
				row.getCell("status").setValue("延误任务");
				row.getCell("statusDetail").setValue("延误未完成任务(" + taskDelayWeiWanChen.size() + ")");
				row.getCell("taskName").setValue(taskInfo.getName());
				row.getCell("start").setValue(taskInfo.getPioneerStartDate());
				row.getCell("end").setValue(taskInfo.getPioneerEndDate());
				row.getCell("duration").setValue(taskInfo.getDuration());
				row.getCell("freeTime").setValue(taskInfo.getFreeTime());
				row.getCell("description").setValue(taskInfo.getDescription());
				row.getCell("percent").setValue(
						(taskInfo.getPercent().divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP, 4)).setScale(4,
								BigDecimal.ROUND_HALF_UP));
				if (taskInfo.getPerson() != null) {
					row.getCell("person").setValue(taskInfo.getPerson().getName());
				}
				if (taskInfo.getAdminOrg() != null) {
					row.getCell("part").setValue(taskInfo.getAdminOrg().getName());
				}
				rowCount++;

				IRow rowClone = (IRow) row.clone();
				rowClone.setTreeLevel(1);
				tableMain.addRow(tableMain.getRowCount(), rowClone);
			}
			table.getMergeManager().mergeBlock(table.getRowCount() - taskDelayWeiWanChen.size(), 2,
					table.getRowCount() - 1, 2);
			tableMain.getMergeManager().mergeBlock(tableMain.getRowCount() - taskDelayWeiWanChen.size(), 2,
					tableMain.getRowCount() - 1, 2);
		} else {
			rowCount++;
			IRow row = table.addRow();
			row.getCell("projectName").setValue(info.getName());
			row.getCell("status").setValue("延误任务");
			row.getCell("statusDetail").setValue("延误未完成任务(" + taskDelayWeiWanChen.size() + ")");

			row = tableMain.addRow();
			row.setTreeLevel(1);
			row.getCell("projectName").setValue(info.getName());
			row.getCell("status").setValue("延误任务");
			row.getCell("statusDetail").setValue("延误未完成任务(" + taskDelayWeiWanChen.size() + ")");
		}

		if (taskDelayWeiKaiShi.size() > 0) {
			for (int i = 0; i < taskDelayWeiKaiShi.size(); i++) {
				ProcessProjectTaskInfo taskInfo = (ProcessProjectTaskInfo) taskDelayWeiKaiShi.get(i);
				IRow row = table.addRow();
				row.getCell("projectName").setValue(info.getName());
				row.getCell("status").setValue("延误任务");
				row.getCell("statusDetail").setValue("延误未开始任务(" + taskDelayWeiKaiShi.size() + ")");
				row.getCell("taskName").setValue(taskInfo.getName());
				row.getCell("start").setValue(taskInfo.getPioneerStartDate());
				row.getCell("end").setValue(taskInfo.getPioneerEndDate());
				row.getCell("duration").setValue(taskInfo.getDuration());
				row.getCell("freeTime").setValue(taskInfo.getFreeTime());
				row.getCell("description").setValue(taskInfo.getDescription());
				row.getCell("percent").setValue(
						(taskInfo.getPercent().divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP, 4)).setScale(4,
								BigDecimal.ROUND_HALF_UP));
				if (taskInfo.getPerson() != null) {
					row.getCell("person").setValue(taskInfo.getPerson().getName());
				}
				if (taskInfo.getAdminOrg() != null) {
					row.getCell("part").setValue(taskInfo.getAdminOrg().getName());
				}
				rowCount++;

				IRow rowClone = (IRow) row.clone();
				rowClone.setTreeLevel(1);
				tableMain.addRow(tableMain.getRowCount(), rowClone);
			}
			table.getMergeManager().mergeBlock(table.getRowCount() - taskDelayWeiKaiShi.size(), 2,
					table.getRowCount() - 1, 2);
			tableMain.getMergeManager().mergeBlock(tableMain.getRowCount() - taskDelayWeiKaiShi.size(), 2,
					tableMain.getRowCount() - 1, 2);
		} else {
			rowCount++;
			IRow row = table.addRow();
			row.getCell("projectName").setValue(info.getName());
			row.getCell("status").setValue("延误任务");
			row.getCell("statusDetail").setValue("延误未开始任务(" + taskDelayWeiKaiShi.size() + ")");

			row = tableMain.addRow();
			row.setTreeLevel(1);
			row.getCell("projectName").setValue(info.getName());
			row.getCell("status").setValue("延误任务");
			row.getCell("statusDetail").setValue("延误未开始任务(" + taskDelayWeiKaiShi.size() + ")");
		}
		table.getMergeManager().mergeBlock(rowIndex, 1, rowIndex + rowCount - 1, 1);
		tableMain.getMergeManager().mergeBlock(countRowIndex, 1, countRowIndex + rowCount - 1, 1);
	}

	public EntityViewInfo getEntityViewInfo() {
		EntityViewInfo viewInfo = new EntityViewInfo();
		viewInfo.getSelector().add("*");
		viewInfo.getSelector().add("person.name");
		viewInfo.getSelector().add("adminOrg.name");
		FilterInfo filterInfo = new FilterInfo();
		if (!(this.prmtAdminDept.getValue() instanceof AdminOrgUnitInfo)) {
			this.prmtAdminDept.setValue(null);
		}
		if (!(this.prmtAdminPerson.getValue() instanceof PersonInfo)) {
			this.prmtAdminPerson.setValue(null);
		}
		if (this.prmtAdminDept.getValue() != null && this.prmtAdminPerson.getValue() == null) {
			AdminOrgUnitInfo info = (AdminOrgUnitInfo) this.prmtAdminDept.getValue();
			filterInfo.getFilterItems().add(
					new FilterItemInfo("adminOrg.longNumber", info.getLongNumber() + "%", CompareType.LIKE));
			if (filterInfo.getMaskString() == null || "".equals(filterInfo.getMaskString())) {
				filterInfo.setMaskString("#" + (filterInfo.getFilterItems().size() - 1));
			} else {
				filterInfo.setMaskString(filterInfo.getMaskString() + " and #"
						+ (filterInfo.getFilterItems().size() - 1));
			}
		}

		if (this.prmtAdminPerson.getValue() != null) {
			PersonInfo info = (PersonInfo) this.prmtAdminPerson.getValue();
			filterInfo.appendFilterItem("person.id", info.getId().toString());
			if (filterInfo.getMaskString() == null || "".equals(filterInfo.getMaskString())) {
				filterInfo.setMaskString("#" + (filterInfo.getFilterItems().size() - 1));
			} else {
				filterInfo.setMaskString(filterInfo.getMaskString() + " and #"
						+ (filterInfo.getFilterItems().size() - 1));
			}
		}
		filterInfo.appendFilterItem("isLeaf", Boolean.TRUE);
		if (filterInfo.getMaskString() == null || "".equals(filterInfo.getMaskString())) {
			filterInfo.setMaskString("#" + (filterInfo.getFilterItems().size() - 1));
		} else {
			filterInfo.setMaskString(filterInfo.getMaskString() + " and #" + (filterInfo.getFilterItems().size() - 1));
		}
		int select = this.cmbStatus.getSelectedIndex();
		if (select == 1) {
			filterInfo.getFilterItems().add(new FilterItemInfo("percent", "0", CompareType.EQUALS));
			filterInfo.getFilterItems().add(new FilterItemInfo("percent", "100", CompareType.EQUALS));
			if (filterInfo.getMaskString() == null || "".equals(filterInfo.getMaskString())) {
				filterInfo.setMaskString("(#" + (filterInfo.getFilterItems().size() - 2) + " or #"
						+ (filterInfo.getFilterItems().size() - 1) + ")");
			} else {
				filterInfo.setMaskString(filterInfo.getMaskString() + " and (#"
						+ (filterInfo.getFilterItems().size() - 2) + " or #" + (filterInfo.getFilterItems().size() - 1)
						+ ")");
			}
		} else if (select == 2) {
			filterInfo.getFilterItems().add(new FilterItemInfo("percent", "0", CompareType.GREATER));
			filterInfo.getFilterItems().add(new FilterItemInfo("percent", "100", CompareType.LESS));
			if (filterInfo.getMaskString() == null || "".equals(filterInfo.getMaskString())) {
				filterInfo.setMaskString("(#" + (filterInfo.getFilterItems().size() - 2) + " and #"
						+ (filterInfo.getFilterItems().size() - 1) + ")");
			} else {
				filterInfo.setMaskString(filterInfo.getMaskString() + " and (#"
						+ (filterInfo.getFilterItems().size() - 2) + " or #" + (filterInfo.getFilterItems().size() - 1)
						+ ")");
			}
		} else if (select == 3) {
			filterInfo.getFilterItems().add(new FilterItemInfo("percent", "100", CompareType.LESS));
			if (filterInfo.getMaskString() == null || "".equals(filterInfo.getMaskString())) {
				filterInfo.setMaskString("#" + (filterInfo.getFilterItems().size() - 1));
			} else {
				filterInfo.setMaskString(filterInfo.getMaskString() + " and #"
						+ (filterInfo.getFilterItems().size() - 1));
			}
		}

		viewInfo.setFilter(filterInfo);
		viewInfo.getSorter().add(new SorterItemInfo("seq"));
		return viewInfo;
	}

	*//**
	 * output exportExcelAction_actionPerformed
	 *//*
	public void exportExcelAction_actionPerformed(ActionEvent e) throws Exception {
		File tempFile = KDTMenuManager.createTempFile(File.createTempFile("fdc", ".xls").getPath());
		if (tempFile != null) {
			String tempFileName = tempFile.getAbsolutePath();
			KDSSheet sheet = null;
			// 保存成临时文件
			ExportManager man = new ExportManager();
			KDSBook book = null;
			book = new KDSBook("kdtable export");
			for (int i = 0; i < tables.size(); i++) {
				KDTable table = (KDTable) tables.get(i);
				sheet = getKDSSheet(table, book, true);
				book.addSheet(null, sheet);
			}

			// 统一了异常处理的接口

			man.exportToExcel(book, tempFileName);

			// 尝试用excel打开

			KDTMenuManager.openFileInExcel(tempFileName);

		}
	}

	private KDSSheet getKDSSheet(KDTable table, KDSBook book, boolean wantHead) {
		Kdt2Kds trans = new Kdt2Kds(table, book);
		trans.setWithHead(wantHead);
		trans.setWithIndexColumn(false);
		trans.setWithHiddenCol(false);
		trans.setRange(null);
		return trans.transform(table.getName());

	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = (KDTable) this.tabReport.getSelectedComponent();
		IPrintJob job = table.getPrintManager().getNewPrintManager().createPrintJobs();
		HeadFootModel header = new HeadFootModel();
		StyleAttributes sa = Styles.getDefaultSA();
		sa.setFontSize(14);
		sa.setBold(true);
		header.addRow("任务状态明细表", sa);
		header.addRow("");
		HeaderFooterModel model = job.getConfig().getHeaderFooterModel();
		model.setHeaderModel(header);
		table.getPrintManager().print();
	}

	public void actionPreview_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = (KDTable) this.tabReport.getSelectedComponent();
		IPrintJob job = table.getPrintManager().getNewPrintManager().createPrintJobs();
		HeadFootModel header = new HeadFootModel();
		StyleAttributes sa = Styles.getDefaultSA();
		sa.setFontSize(14);
		sa.setBold(true);
		header.addRow("任务状态明细表", sa);
		header.addRow("");
		HeaderFooterModel model = job.getConfig().getHeaderFooterModel();
		model.setHeaderModel(header);
		table.getPrintManager().printPreview();
	}
*/}