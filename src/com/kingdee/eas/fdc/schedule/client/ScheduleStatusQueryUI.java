/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.kds.KDSSheet;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
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

/**
 * output class name
 */
public class ScheduleStatusQueryUI extends AbstractScheduleStatusQueryUI {
	private static final Logger logger = CoreUIObject.getLogger(ScheduleStatusQueryUI.class);

	List tables = new ArrayList();

	public ScheduleStatusQueryUI() throws Exception {
		super();
	}
	/*
	public void onLoad() throws Exception {
		// TODO 自动生成方法存根
		super.onLoad();
		this.setUITitle("任务状态汇总查询");
		SCHClientHelper.setRespDeptF7(this.prmtAdminDept, this, SysContext.getSysContext().getCurrentOrgUnit().getId()
				.toString());
		this.prmtAdminDept.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				prmtAdminPerson.setValue(null);
				if (prmtAdminDept.getValue() != null) {
					if (prmtAdminDept.getValue() instanceof AdminOrgUnitInfo) {
						SCHClientHelper.setPersonF7(prmtAdminPerson,
								((AdminOrgUnitInfo) prmtAdminDept.getValue()).getId().toString(),
								ScheduleStatusQueryUI.this);
					}
				} else {
					if (SysContext.getSysContext().getCurrentAdminUnit() != null) {
						SCHClientHelper.setPersonF7(prmtAdminPerson, SysContext.getSysContext().getCurrentAdminUnit().getId()
								.toString(), ScheduleStatusQueryUI.this);
					} else {
						SCHClientHelper.setPersonF7(prmtAdminPerson, null, ScheduleStatusQueryUI.this);
					}

				}

			}

		});
		this.prmtAdminDept.setValue(SysContext.getSysContext().getCurrentAdminUnit());
		initTable(this.tableMain);
		this.tableMain.setName("汇总报表");
	}

	public void initTable(KDTable table) {

		IRow headRow = table.addHeadRow();
		IColumn column = null;

		column = table.addColumn();
		column.setKey("name");
		KDDatePicker datePicker = new KDDatePicker();
		datePicker.setEditable(false);
		column.setWidth(200);
		headRow.getCell("name").setValue("计划名称");

		column = table.addColumn();
		column.setKey("status");
		column.setWidth(140);
		headRow.getCell("status").setValue("任务状态");

		column = table.addColumn();
		column.setKey("statusDetail");
		column.setWidth(140);
		headRow.getCell("statusDetail").setValue("任务状态明细");

		column = table.addColumn();
		column.setWidth(140);
		column.setKey("count");
		column.getStyleAttributes().setNumberFormat("#,###");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow.getCell("count").setValue("任务数");

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
		ProcessProjectCollection projectCollection = ProcessProjectFactory.getRemoteInstance()
				.getProcessProjectCollection(
						"select *,project.*,person.* where state='6' and version is null and org.longnumber like '"
								+ org.getLongNumber() + "%' order by name");
		for (int i = 0; i < projectCollection.size(); i++) {
			ProcessProjectInfo projectInfo = projectCollection.get(i);
			KDTable table = new KDTable();
			table.setName(projectInfo.getName());
			tables.add(table);
			initTable(table);
			this.tabReport.addTab(projectInfo.getName(), table);

			IRow row = this.tableMain.addRow();

			row.setTreeLevel(0);
			this.tableMain.getMergeManager().mergeBlock(row.getRowIndex(), 0, row.getRowIndex(),
					this.tableMain.getColumnCount() - 1);
			row.getCell(0).setValue(
					"计划编号: " + projectInfo.getNumber() + "; 计划名称: " + projectInfo.getName() + "; 所属工程项目: "
							+ projectInfo.getProject().getName() + "; 责任人: " + projectInfo.getPerson().getName() + ";");
			EntityViewInfo viewInfoClone = (EntityViewInfo) viewInfo.clone();
			viewInfoClone.getFilter().appendFilterItem("project.id", projectInfo.getId().toString());
			ProcessProjectTaskCollection collection = ProcessProjectTaskFactory.getRemoteInstance()
					.getProcessProjectTaskCollection(viewInfoClone);
			int commonWeiKaiShi = 0;
			int commonZhengChangYiWanChen = 0;
			int commonYanWuYiWanChen = 0;
			int commonProcessJinXingZhong = 0;
			int delayWeiWanChen = 0;
			int delayWeiKaiShi = 0;
			Date standDate = ModifyStandDate.getStandDate();
			for (int m = 0; m < collection.size(); m++) {
				ProcessProjectTaskInfo taskInfo = collection.get(m);
				if (taskInfo.getPercent().floatValue() == 0) {
					if (standDate.after(taskInfo.getPioneerStartDate())) {
						delayWeiKaiShi++;					
					} else {
						commonWeiKaiShi++;
					}
					continue;
				} else if (taskInfo.getPercent().floatValue() == 100) {
					if (taskInfo.isIsDelayComplate()) {
						commonYanWuYiWanChen++;
					} else {
						commonZhengChangYiWanChen++;
					}
					continue;
				} else if (0 < taskInfo.getPercent().floatValue() && taskInfo.getPercent().floatValue() < 100) {
					if (standDate.before(taskInfo.getPioneerEndDate())) {
						commonProcessJinXingZhong++;
					} else {
						delayWeiWanChen++;
					}
					continue;
				}
			}
			fillRow(table, projectInfo, commonWeiKaiShi, commonZhengChangYiWanChen, commonYanWuYiWanChen,
					commonProcessJinXingZhong, delayWeiWanChen, delayWeiKaiShi);
			table.getStyleAttributes().setLocked(true);
		}
		this.tableMain.getStyleAttributes().setLocked(true);
	}

	public void fillRow(KDTable table, ProcessProjectInfo projectInfo, int commonWeiKaiShi,
			int commonZhengChangYiWanChen, int commonYanWuYiWanChen, int commonProcessJinXingZhong,
			int delayWeiWanChen, int delayWeiKaiShi) {
		int rowIndex = table.getRowCount();
		int totalRowIndex = this.tableMain.getRowCount();
		IRow row = table.addRow();
		IRow totalRow = this.tableMain.addRow();
		totalRow.setTreeLevel(1);
		row.getCell("name").setValue(projectInfo.getName());
		row.getCell("status").setValue("正常任务");
		row.getCell("statusDetail").setValue("正常未开始任务");
		row.getCell("count").setValue(commonWeiKaiShi + "");
		totalRow.getCell("name").setValue(projectInfo.getName());
		totalRow.getCell("status").setValue("正常任务");
		totalRow.getCell("statusDetail").setValue("正常未开始任务");
		totalRow.getCell("count").setValue(commonWeiKaiShi + "");

		row = table.addRow();
		row.getCell("status").setValue("正常任务");
		row.getCell("statusDetail").setValue("正常已完成任务");
		row.getCell("count").setValue(commonZhengChangYiWanChen + "");
		totalRow = this.tableMain.addRow();
		totalRow.setTreeLevel(1);
		totalRow.getCell("status").setValue("正常任务");
		totalRow.getCell("statusDetail").setValue("正常已完成任务");
		totalRow.getCell("count").setValue(commonZhengChangYiWanChen + "");

		row = table.addRow();
		row.getCell("status").setValue("正常任务");
		row.getCell("statusDetail").setValue("延误已完成任务");
		row.getCell("count").setValue(commonYanWuYiWanChen + "");
		totalRow = this.tableMain.addRow();
		totalRow.setTreeLevel(1);
		totalRow.getCell("status").setValue("正常任务");
		totalRow.getCell("statusDetail").setValue("延误已完成任务");
		totalRow.getCell("count").setValue(commonYanWuYiWanChen + "");

		table.getMergeManager().mergeBlock(rowIndex, 1, rowIndex + 2, 1);
		this.tableMain.getMergeManager().mergeBlock(totalRowIndex, 1, totalRowIndex + 2, 1);

		row = table.addRow();
		row.getCell("status").setValue("正常进行中任务");
		row.getCell("statusDetail").setValue("正常进行中任务");
		row.getCell("count").setValue(commonProcessJinXingZhong + "");
		totalRow = this.tableMain.addRow();
		totalRow.setTreeLevel(1);
		totalRow.getCell("status").setValue("正常进行中任务");
		totalRow.getCell("statusDetail").setValue("正常进行中任务");
		totalRow.getCell("count").setValue(commonProcessJinXingZhong + "");

		row = table.addRow();
		row.getCell("status").setValue("延误任务");
		row.getCell("statusDetail").setValue("延误未完成任务");
		row.getCell("count").setValue(delayWeiWanChen + "");
		totalRow = this.tableMain.addRow();
		totalRow.setTreeLevel(1);
		totalRow.getCell("status").setValue("延误任务");
		totalRow.getCell("statusDetail").setValue("延误未完成任务");
		totalRow.getCell("count").setValue(delayWeiWanChen + "");


		row = table.addRow();
		row.getCell("status").setValue("延误任务");
		row.getCell("statusDetail").setValue("延误未开始任务");
		row.getCell("count").setValue(delayWeiKaiShi + "");
		totalRow = this.tableMain.addRow();
		totalRow.setTreeLevel(1);
		totalRow.getCell("status").setValue("延误任务");
		totalRow.getCell("statusDetail").setValue("延误未开始任务");
		totalRow.getCell("count").setValue(delayWeiKaiShi + "");

		table.getMergeManager().mergeBlock(rowIndex + 4, 1, rowIndex + 5, 1);
		this.tableMain.getMergeManager().mergeBlock(totalRowIndex + 4, 1, totalRowIndex + 5, 1);

		table.getMergeManager().mergeBlock(rowIndex, 0, rowIndex + 5, 0);
		this.tableMain.getMergeManager().mergeBlock(totalRowIndex, 0, totalRowIndex + 5, 0);

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
		}

		if (this.prmtAdminPerson.getValue() != null) {
			PersonInfo info = (PersonInfo) this.prmtAdminPerson.getValue();
			filterInfo.appendFilterItem("person.id", info.getId().toString());
		}
		filterInfo.appendFilterItem("isLeaf", Boolean.TRUE);

		viewInfo.setFilter(filterInfo);
		viewInfo.getSorter().add(new SorterItemInfo("seq"));
		return viewInfo;
	}

	*//**
	 * output exportExcelAction_actionPerformed
	 *//*
	public void exportExcelAction_actionPerformed(ActionEvent e) throws Exception {
//		File tempFile = KDTMenuManager.createTempFile(File.createTempFile("fdc", ".xls").getPath());
//		if (tempFile != null) {
//			String tempFileName = tempFile.getAbsolutePath();
//			KDSSheet sheet = null;
//			// 保存成临时文件
//			ExportManager man = new ExportManager();
//			KDSBook book = null;
//			book = new KDSBook("kdtable export");
//			for (int i = 0; i < tables.size(); i++) {
//				KDTable table = (KDTable) tables.get(i);
//				sheet = getKDSSheet(table, book, true);
//				book.addSheet(null, sheet);
//			}
//
//			// 统一了异常处理的接口
//
//			man.exportToExcel(book, tempFileName);
//
//			// 尝试用excel打开
//
//			KDTMenuManager.openFileInExcel(tempFileName);
//
//		}
		
		KDTable table = this.tableMain;
		File tempFile = File.createTempFile("eastemp", ".xls");
		table.getIOManager().setTempFileDirection(tempFile.getPath());
		table.getIOManager().exportExcelToTempFile(false);
		tempFile.deleteOnExit();
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
		header.addRow("任务状态汇总表", sa);
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
		header.addRow("任务状态汇总表", sa);
		header.addRow("");
		HeaderFooterModel model = job.getConfig().getHeaderFooterModel();
		model.setHeaderModel(header);
		table.getPrintManager().printPreview();
	}
*/}