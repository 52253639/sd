/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ActionMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.kds.KDSSheet;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAbstractAction;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
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
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ScheduleTimePeriodQueryUI extends AbstractScheduleTimePeriodQueryUI {
	private static final Logger logger = CoreUIObject.getLogger(ScheduleTimePeriodQueryUI.class);

	List tables = new ArrayList();

	/**
	 * output class constructor
	 */
	public ScheduleTimePeriodQueryUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initQueryFilters();
		initTable(this.tableMain);
		this.tableMain.setName("汇总报表");
	}

	private void initQueryFilters() {
		this.pkEndDate.setValue(null);
		prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");
		prmtSchedule.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7ScheduleQuery");
		prmtSchedule.setDisplayFormat("$name$");
		prmtSchedule.setEditFormat("$name$");
		prmtSchedule.setCommitFormat("$name$");
		prmtAdminDept.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7FullOrgUnitQuery");
		prmtAdminPerson.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");
		//设置责任部门，责任部门默认当前责任部门、责任人根据责任部门进行过滤
		SCHClientHelper.setRespDeptF7(
				this.prmtAdminDept, this, SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
		this.prmtAdminDept.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				prmtAdminPerson.setValue(null);
				if (prmtAdminDept.getValue() != null) {
					if (prmtAdminDept.getValue() instanceof AdminOrgUnitInfo) {
						SCHClientHelper.setPersonF7(
								prmtAdminPerson, ((AdminOrgUnitInfo) prmtAdminDept.getValue()).getId().toString(),ScheduleTimePeriodQueryUI.this);
					}
				} else {
					if (SysContext.getSysContext().getCurrentAdminUnit() != null) {
						SCHClientHelper.setPersonF7(prmtAdminPerson, SysContext.getSysContext().getCurrentAdminUnit().getId()
								.toString(), ScheduleTimePeriodQueryUI.this);
					} else {
						SCHClientHelper.setPersonF7(prmtAdminPerson, null, ScheduleTimePeriodQueryUI.this);
					}
				}
			}
		});
		this.prmtAdminDept.setValue(SysContext.getSysContext().getCurrentAdminUnit());
		
		prmtSchedule.setEnabled(false);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit.longNumber",SysContext.getSysContext().getCurrentOrgUnit().getLongNumber()+"%",CompareType.LIKE));
		view.setFilter(filter);
		prmtProject.setEntityViewInfo(view);
		prmtProject.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				prmtSchedule.setValue(null);
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				if(prmtProject.getValue() != null&& 
						prmtProject.getValue() instanceof CurProjectInfo){
					filter.getFilterItems().add(
							new FilterItemInfo("project.id",((CurProjectInfo)prmtProject.getValue()).getId().toString()));
					view.setFilter(filter);
					prmtSchedule.setEntityViewInfo(view);
					prmtSchedule.setEnabled(true);
				}else{
					prmtSchedule.setEnabled(false);
				}
			}
		});
	}

	public void initTable(KDTable table) {
		IRow headRow = table.addHeadRow();
		IColumn column = null;

		column = table.addColumn();
		column.setKey("name");
		KDDatePicker datePicker = new KDDatePicker();
		datePicker.setEditable(false);
		headRow.getCell("name").setValue("名称");

		column = table.addColumn();
		column.setKey("startDate");
		column.setWidth(80);
		column.getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		headRow.getCell("startDate").setValue("开始时间");

		column = table.addColumn();
		column.setKey("endDate");
		column.setWidth(80);
		column.getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		headRow.getCell("endDate").setValue("结束时间");

		column = table.addColumn();
		column.setWidth(80);
		column.setKey("duration");
		column.getStyleAttributes().setNumberFormat("#,###");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow.getCell("duration").setValue("持续时间");

		column = table.addColumn();
		column.setKey("percent");
		column.setWidth(80);
		column.getStyleAttributes().setNumberFormat("##0.00%");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow.getCell("percent").setValue("完成百分比");

		column = table.addColumn();
		column.setKey("adminPerson");
		headRow.getCell("adminPerson").setValue("责任人");

		column = table.addColumn();
		column.setWidth(200);
		column.setKey("adminDept");
		headRow.getCell("adminDept").setValue("责任部门");

		column = table.addColumn();
		column.setWidth(200);
		column.setKey("description");
		headRow.getCell("description").setValue("备注");

		ActionMap actionMap = table.getActionMap();
		actionMap.put(KDTAction.CUT, new KDTDeleteAction(table));
		actionMap.put(KDTAction.DELETE, new KDTDeleteAction(table));

	}

	/**
	 * output serchAction_actionPerformed
	 */
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
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(prmtProject.getValue() != null && prmtProject.getValue() instanceof CurProjectInfo){
			filter.getFilterItems().add(new FilterItemInfo(
					"project.id",((CurProjectInfo)prmtProject.getValue()).getId().toString(),CompareType.EQUALS));
		}
		if(prmtAdminDept.getValue() != null && prmtAdminDept.getValue() instanceof FullOrgUnitInfo){
			filter.getFilterItems().add(new FilterItemInfo(
					"adminDept.id",((FullOrgUnitInfo)prmtAdminDept.getValue()).getId().toString(),CompareType.EQUALS));
		}
		if(prmtAdminPerson.getValue() != null && prmtAdminPerson.getValue() instanceof PersonInfo){
			filter.getFilterItems().add(new FilterItemInfo(
					"adminPerson.id",((PersonInfo)prmtAdminPerson.getValue()).getId().toString(),CompareType.EQUALS));
		}
		if(pkStartDate.getTimestamp() != null){
			filter.getFilterItems().add(
					new FilterItemInfo("startDate",pkStartDate.getTimestamp(),CompareType.GREATER_EQUALS));
		}
		if(pkEndDate.getTimestamp() != null){
			filter.getFilterItems().add(
					new FilterItemInfo("endDate",pkEndDate.getTimestamp(),CompareType.LESS_EQUALS));
		}
		view.setFilter(filter);
		FDCScheduleCollection scheduleCollection = FDCScheduleFactory.getRemoteInstance().getFDCScheduleCollection(view);
		for (int i = 0; i < scheduleCollection.size(); i++) {
			FDCScheduleInfo scheduleInfo = scheduleCollection.get(i);
			KDTable table = new KDTable();
			table.setName(scheduleInfo.getName());
			tables.add(table);
			initTable(table);
			this.tabReport.addTab(scheduleInfo.getName(), table);

			IRow row = this.tableMain.addRow();

			row.setTreeLevel(0);
			this.tableMain.getMergeManager().mergeBlock(row.getRowIndex(), 0, row.getRowIndex(),
					this.tableMain.getColumnCount() - 1);
			row.getCell(0).getStyleAttributes().setLocked(true);
			row.getCell(0).setValue(
					"计划编号: " + scheduleInfo.getNumber() + "; 计划名称: " + scheduleInfo.getName() + "; 所属工程项目: "
							+ scheduleInfo.getProject().getName()+ "; 责任人: " + scheduleInfo.getAdminPerson().getName() + ";");
			FDCScheduleTaskCollection taskcollection = scheduleInfo.getTaskEntrys();
			for (int j = 0; j < taskcollection.size(); j++) {
				fillRow(table, taskcollection.get(j));
			}
			table.getStyleAttributes().setLocked(true);
			table.getColumn("name").getStyleAttributes().setLocked(false);
		}
		this.tableMain.getStyleAttributes().setLocked(true);
		this.tableMain.getColumn("name").getStyleAttributes().setLocked(false);
	}

	public void fillRow(KDTable table, FDCScheduleTaskInfo info) {
		CellTreeNode node = new CellTreeNode();
		IRow row = table.addRow();
		if (!info.isIsLeaf()) {
			node.setHasChildren(true);
		} else {
			node.setHasChildren(false);
		}
		node.setTreeLevel(info.getLevel());
		node.setValue(info.getName());
		row.getCell("name").setValue(node);
		row.getCell("startDate").setValue(info.getStart());
		row.getCell("endDate").setValue(info.getEnd());
		row.getCell("duration").setValue(Integer.valueOf(String.valueOf(info.getDuration())));
//		row.getCell("freeTime").setValue(info.getFreeTime());
		row.getCell("percent").setValue(info.getComplete());
		if (info.getAdminPerson() != null) {
			row.getCell("adminPerson").setValue(info.getAdminPerson().getName());
		}
		if (info.getAdminDept() != null) {
			row.getCell("adminDept").setValue(info.getAdminDept().getName());
		}
		row.getCell("description").setValue(info.getDescription());

		IRow rowClone = (IRow) row.clone();
		node = new CellTreeNode();
		if (!info.isIsLeaf()) {
			node.setHasChildren(true);
		} else {
			node.setHasChildren(false);
		}
		node.setTreeLevel(info.getLevel());
		node.setValue(info.getName());

		rowClone.getCell("name").setValue(node);
		rowClone.getCell("name").getStyleAttributes().setLocked(false);
		rowClone.setTreeLevel(1);
		tableMain.addRow(tableMain.getRowCount(), rowClone);
	}

	public EntityViewInfo getEntityViewInfo() {
		EntityViewInfo viewInfo = new EntityViewInfo();
		viewInfo.getSelector().add("*");
		viewInfo.getSelector().add("person.name");
		viewInfo.getSelector().add("adminOrg.name");
		FilterInfo filterInfo = new FilterInfo();
		Date start = null;
		Date end = null;
		if (!(this.prmtAdminDept.getValue() instanceof AdminOrgUnitInfo)) {
			this.prmtAdminDept.setValue(null);
		}
		if (!(this.prmtAdminPerson.getValue() instanceof PersonInfo)) {
			this.prmtAdminPerson.setValue(null);
		}

		if (this.prmtAdminDept.getValue() instanceof AdminOrgUnitInfo && this.prmtAdminPerson.getValue() == null) {
			AdminOrgUnitInfo info = (AdminOrgUnitInfo) this.prmtAdminDept.getValue();
			filterInfo.getFilterItems().add(
					new FilterItemInfo("adminOrg.longNumber", info.getLongNumber() + "%", CompareType.LIKE));
		}

		if (this.prmtAdminPerson.getValue() instanceof PersonInfo) {
			PersonInfo info = (PersonInfo) this.prmtAdminPerson.getValue();
			filterInfo.appendFilterItem("person.id", info.getId().toString());
		}

		if (this.pkStartDate.getValue() != null) {
			start = (Date) this.pkStartDate.getValue();
			filterInfo.getFilterItems().add(new FilterItemInfo("pioneerEndDate", start, CompareType.GREATER));
		}
		if (this.pkEndDate.getValue() != null) {
			end = (Date) this.pkEndDate.getValue();
			filterInfo.getFilterItems().add(new FilterItemInfo("pioneerStartDate", end, CompareType.LESS));
		}

		if (start != null && end != null && start.after(end)) {
			MsgBox.showWarning("结束日期应该大于开始日期!");
			return null;
		}

		viewInfo.setFilter(filterInfo);
		viewInfo.getSorter().add(new SorterItemInfo("seq"));
		return viewInfo;
	}

	/**
	 * output exportExcelAction_actionPerformed
	 */
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

	class KDTDeleteAction extends KDTAbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 528704412813139367L;

		public KDTDeleteAction(KDTable table) {
			super(table);
		}

		public void actionPerformed(ActionEvent arg0) {

		}

	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = (KDTable) this.tabReport.getSelectedComponent();
		IPrintJob job = table.getPrintManager().getNewPrintManager().createPrintJobs();
		HeadFootModel header = new HeadFootModel();
		StyleAttributes sa = Styles.getDefaultSA();
		sa.setFontSize(14);
		sa.setBold(true);
		header.addRow("任务信息表", sa);
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
		header.addRow("任务信息表", sa);
		header.addRow("");
		HeaderFooterModel model = job.getConfig().getHeaderFooterModel();
		model.setHeaderModel(header);
		table.getPrintManager().printPreview();
	}
}