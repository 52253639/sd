/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class RESchCompareUI extends AbstractRESchCompareUI {
	
	public static Color RED = new Color(250, 150, 150);
	public static Color GREEN = new Color(150, 250, 150);
	public static Color BLUE = new Color(150, 150, 250);

	private static final Logger logger = CoreUIObject
			.getLogger(RESchCompareUI.class);

	/**
	 * output class constructor
	 */
	public RESchCompareUI() throws Exception {
		super();
	}

	private FDCScheduleInfo ownerEditData;

	private FDCScheduleInfo baseVerSchedule;

	private FDCScheduleInfo compareVerSchedule;

	public void onLoad() throws Exception {
		super.onLoad();

		DataChangeListener prmtBaseVerDataListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				prmtBaseVerDateListener(e);
			}
		};

		DataChangeListener prmtCompareVerDataListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				prmtCompareVerDateListener(e);
			}
		};
		prmtBaseVer.addDataChangeListener(prmtBaseVerDataListener);
		prmtCompareVer.addDataChangeListener(prmtCompareVerDataListener);

		ownerEditData = (FDCScheduleInfo) getUIContext().get("editData");

		EntityViewInfo view = getPrmtView();
		prmtBaseVer.setEntityViewInfo(view);
		prmtCompareVer.setEntityViewInfo(view);

		initDefualtData();

		loadCompareBtn(ownerEditData);
		loadComparePage(ownerEditData);
	}

	/**
	 * 设置F7过滤条件
	 * 
	 * @return
	 */
	protected EntityViewInfo getPrmtView() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("project.id", ownerEditData.getProject()
						.getId().toString()));
		// filter.getFilterItems().add(
		// (new FilterItemInfo("id", ownerEditData.getId().toString(),
		// CompareType.NOTEQUALS)));
		if (ownerEditData.getProjectSpecial() == null) {
			filter.getFilterItems().add(
					(new FilterItemInfo("projectSpecial.id", null,
							CompareType.EQUALS)));
		} else {
			filter.getFilterItems().add(
					(new FilterItemInfo("projectSpecial.id", ownerEditData.getProjectSpecial().getId().toString())));
		}
		view.setFilter(filter);
		return view;
	}

	/**
	 * 设置F7默认值
	 */
	protected void initDefualtData() {
		prmtCompareVer.setValue(ownerEditData);
		EntityViewInfo view = getPrmtView();
		if (ownerEditData != null && ownerEditData.getVersion() > 1) {
			view.getFilter().getFilterItems().add(
					new FilterItemInfo("version", new Integer(
							(int) (ownerEditData.getVersion() - 1))));
			try {
				FDCScheduleCollection col = FDCScheduleFactory
						.getRemoteInstance().getFDCScheduleCollection(view);
				if (col != null && col.size() > 0) {
					FDCScheduleInfo schedule = col.get(0);
					prmtBaseVer.setValue(schedule);
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}

	private void prmtBaseVerDateListener(DataChangeEvent e) {
		baseVerSchedule = (FDCScheduleInfo) e.getNewValue();
		prmtBaseVer.setValue(baseVerSchedule);
		// TODO
	}

	private void prmtCompareVerDateListener(DataChangeEvent e) {
		compareVerSchedule = (FDCScheduleInfo) e.getNewValue();
		prmtCompareVer.setValue(compareVerSchedule);
		// TODO
	}

	protected void loadCompareBtn(FDCScheduleInfo scheduleInfo) {
		boolean haveTaskEntrys = haveTaskEntrys(scheduleInfo);
		if (!haveTaskEntrys || !haveHistory(scheduleInfo.getVersion())) {
			btnVerCompareOfPage.setEnabled(false);
			prmtBaseVer.setEnabled(false);
			prmtCompareVer.setEnabled(false);
		} else if (haveTaskEntrys && haveHistory(scheduleInfo.getVersion())) {
			btnVerCompareOfPage.setEnabled(true);
			prmtBaseVer.setEnabled(true);
			prmtCompareVer.setEnabled(true);
		}
	}

	protected void loadComparePage(FDCScheduleInfo scheduleInfo)
			throws BOSException {
		boolean haveTaskEntrys = haveTaskEntrys(scheduleInfo);
		if (!haveTaskEntrys) {
			return;
		}
		if (haveTaskEntrys && !haveHistory(scheduleInfo.getVersion())) {
			loadCompareData(ownerEditData);
			return;
		}
		if (haveTaskEntrys && haveHistory(scheduleInfo.getVersion())) {
			scheduleInfo = FDCScheduleFactory.getRemoteInstance()
					.getSchedule4Compare(null, scheduleInfo.getId().toString());
			loadCompareData(scheduleInfo);
		}
	}

	public void loadFields() {
		// TODO Auto-generated method stub
		super.loadFields();
		kDTable1.checkParsed();
	}

	protected void loadCompareData(FDCScheduleInfo scheduleInfo) {
		FDCScheduleTaskCollection taskEntrys = scheduleInfo.getTaskEntrys();
		kDTable1.removeRows();
		kDTable1.checkParsed();
		for (int i = 0; i < taskEntrys.size(); i++) {
			FDCScheduleTaskInfo scheduleTask = taskEntrys.get(i);
			String type = (String) scheduleTask.get("type");
			IRow row = kDTable1.addRow();
			if (type == null) {
				fillTaskNormal(scheduleTask, row);
			} else {
				if (type.equals("newTask")) {
					row.getStyleAttributes().setBackground(RED);
					fillTaskNewTask(scheduleTask, row);
				} else {
					fillTaskNormal(scheduleTask, row);
				}
			}
		}
		setLevel(kDTable1, taskEntrys);
	}

	private void fillTaskNewTask(FDCScheduleTaskInfo scheduleTask, IRow row) {
		int duration = scheduleTask.getDuration();
		Integer oldDuration = (Integer) scheduleTask.get("MyOldDuration");
		Date startDate = scheduleTask.getStart();
		Date oldStartDate = (Date) scheduleTask.get("myOldStartDate");
		Date endDate = scheduleTask.getEnd();
		Date oldEndDate = (Date) scheduleTask.get("myOldEndDate");
		Date checkDate = scheduleTask.getCheckDate();
		Date oldCheckDate = (Date) scheduleTask.get("myOldCheckDate");
		row.getCell("taskName").setValue(scheduleTask.getName());
		row.getCell("workDayPre").setValue(null);
		row.getCell("workDayDep").setValue(Integer.valueOf(duration));

		row.getCell("planStartPre").setValue(null);
		row.getCell("planStartDep").setValue(startDate);

		row.getCell("planEndPre").setValue(null);
		row.getCell("planEndDep").setValue(endDate);

		row.getCell(7).setValue(null);
		row.getCell(8).setValue(checkDate);
		
		row.getCell("isLeaf").setValue(new Boolean(scheduleTask.isIsLeaf()));
		row.getCell("level").setValue(new Integer(scheduleTask.getLevel()));
		row.getCell("longNumber").setValue(scheduleTask.getLongNumber());
	}

	private void fillTaskNormal(FDCScheduleTaskInfo scheduleTask, IRow row) {
		int duration = scheduleTask.getDuration();
		Integer oldDuration = (Integer) scheduleTask.get("MyOldDuration");
		Date startDate = scheduleTask.getStart();
		Date oldStartDate = (Date) scheduleTask.get("myOldStartDate");
		Date endDate = scheduleTask.getEnd();
		Date oldEndDate = (Date) scheduleTask.get("myOldEndDate");
		Date checkDate = scheduleTask.getCheckDate();
		Date oldCheckDate = (Date) scheduleTask.get("myOldCheckDate");
		String type = (String) scheduleTask.get("type");
		row.getCell("taskName").setValue(scheduleTask.getName());
		row.getCell("workDayPre").setValue(new Integer(duration));
		row.getCell("workDayDep").setValue(oldDuration);

		row.getCell("planStartPre").setValue(startDate);
		row.getCell("planStartDep").setValue(oldStartDate);

		row.getCell("planEndPre").setValue(endDate);
		row.getCell("planEndDep").setValue(oldEndDate);

		row.getCell(8).setValue(oldCheckDate);
		row.getCell(7).setValue(checkDate);
		row.getCell("isLeaf").setValue(new Boolean(scheduleTask.isIsLeaf()));
		row.getCell("level").setValue(new Integer(scheduleTask.getLevel()));
		row.getCell("longNumber").setValue(scheduleTask.getLongNumber());
		
		if (type != null && type.equals("delete")) {
			row.getStyleAttributes().setBackground(BLUE);
		} else if (type == null) {
			setcellStyle(new Integer(duration), oldDuration, row, 2);
			setcellStyle(startDate, oldStartDate, row, 4);
			setcellStyle(endDate, oldEndDate, row, 6);
			setcellStyle(checkDate, oldCheckDate, row, 8);
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
	private void setLevel(final KDTable table, FDCScheduleTaskCollection tasks) {
		kDTable1.checkParsed();
		for (int i = 0; i < table.getRowCount(); i++) {
			FDCScheduleTaskInfo scheduleTask = tasks.get(i);
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
					public void doClick(CellTreeNode source, ICell cell,
							int type) {
						table.revalidate();
					}
				});
				row.getStyleAttributes().setLocked(true);
				row.getCell("taskName").getStyleAttributes().setLocked(false);
				row.getCell("taskName").setValue(treeNode);// 设置前面构建的单元格树到表
			}
		}
	}

	private void setcellStyle(Object base, Object compare, IRow row, int index) {
		if (base instanceof Date) {
			Date date = (Date) base;
			Date oldDate = (Date) compare;
			if (date == null || oldDate == null) {
				return;
			}
			if (date.after(oldDate)) {
				row.getCell(index).getStyleAttributes().setBackground(
						GREEN);
				row.getCell(index - 1).getStyleAttributes().setBackground(
						GREEN);
			} else if (date.before(oldDate)) {
				row.getCell(index).getStyleAttributes()
						.setBackground(RED);
				row.getCell(index - 1).getStyleAttributes().setBackground(
						RED);
			}
		}
		if (base instanceof Integer) {
			Integer duration = (Integer) base;
			Integer oldDuration = (Integer) compare;
			if (duration != null && oldDuration != null
					&& duration.intValue() < oldDuration.intValue()) {
				row.getCell(index).getStyleAttributes().setBackground(RED);
				row.getCell(index - 1).getStyleAttributes().setBackground(RED);
			} else if (duration != null && oldDuration != null
					&& duration.intValue() > oldDuration.intValue()) {
				row.getCell(index).getStyleAttributes().setBackground(GREEN);
				row.getCell(index - 1).getStyleAttributes()
						.setBackground(GREEN);
			}
		}
	}

	public boolean haveHistory(float version) {
		return version > 1f;
	}

	public boolean haveTaskEntrys(FDCScheduleInfo info) {
		return info != null && info.getTaskEntrys() != null
				&& info.getTaskEntrys().size() > 0;
	}

	public void actionCompareVer_actionPerformed(ActionEvent e)
			throws Exception {
		if (null == compareVerSchedule && null == baseVerSchedule) {
			FDCMsgBox.showWarning(this, "请选择基准版本");
			SysUtil.abort();
		}
		if (null == compareVerSchedule || null == baseVerSchedule) {
			FDCMsgBox.showWarning(this, "请选择基准版本\\比较版本");
			SysUtil.abort();
		}
		if (compareVerSchedule != null && baseVerSchedule != null) {
			FDCScheduleInfo scheduleInfo = FDCScheduleFactory
					.getRemoteInstance().getSchedule4Compare(
							baseVerSchedule.getId().toString(),
							compareVerSchedule.getId().toString());
			loadCompareData(scheduleInfo);
		}
	}
}