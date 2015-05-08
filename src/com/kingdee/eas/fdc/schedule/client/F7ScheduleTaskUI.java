/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class F7ScheduleTaskUI extends AbstractF7ScheduleTaskUI {
	private static final Logger logger = CoreUIObject.getLogger(F7ScheduleTaskUI.class);
	private FDCScheduleTaskInfo curTask;

	/**
	 * output class constructor
	 */
	public F7ScheduleTaskUI() throws Exception {
		super();
	}

	public void setCurTask(FDCScheduleTaskInfo curTask) {
		this.curTask = curTask;
	}

	public FDCScheduleTaskInfo getCurTask() {
		return curTask;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output btnOK_actionPerformed method
	 */
	protected void btnOK_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		isCancel = false;
		IRow row = FDCTableHelper.getSelectedRow(getTable());
		if (row == null || row.getUserObject() == null) {
			FDCMsgBox.showWarning(this, "请选择行");
			SysUtil.abort();
		}
		this.getUIContext().put(RETURNDATA, row.getUserObject());
		super.btnOK_actionPerformed(e);
		destroyWindow();
	}

	protected KDTable getTable() {
		return this.tbnMain;
	}

	/**
	 * output btnNo_actionPerformed method
	 */
	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		// getUIContext().remove(RETURNDATA);
		super.btnNo_actionPerformed(e);
		isCancel = true;
		this.destroyWindow();
	}

	public boolean isCancel() {
		return isCancel;
	}

	public ScheduleBaseInfo getScheduleInfo() {
		return (ScheduleBaseInfo) getUIContext().get(SCHEUDLEINFO);
	}

	protected boolean isCancel = false;
	public final static String SCHEUDLEINFO = "scheduleInfo";
	public final static String FILTERTASK = "filterTask";
	public final static String RETURNDATA = "returnData";
	public final static String CURTASK = "curTask";

	/**
	 * 不显示的任务
	 * 
	 * @return
	 */
	protected Set getFilterTask() {
		return (Set) getUIContext().get(FILTERTASK);
	}

	public void setFilterTask(Set taskIdList) {
		getUIContext().put(FILTERTASK, taskIdList);
		fillTable();
	}

	public void fillTable() {
		KDTable table = this.tbnMain;
		table.checkParsed();
		table.removeRows();
		getTable().getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		getTable().getStyleAttributes().setLocked(true);
		Set filterTaskSet = getFilterTask();
		FDCScheduleInfo schedule = (FDCScheduleInfo) getScheduleInfo();
		List orderTask = orderTask(schedule);
		for (Iterator iter = orderTask.iterator(); iter.hasNext();) {
			FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) iter.next();
			/***
			 * 专项任务，不能连接主项任务，会有循环产生
			 */
			FDCScheduleTaskInfo curTask = (FDCScheduleTaskInfo) getUIContext().get(CURTASK);
			FDCScheduleTaskDependCollection curTaskDepends = curTask.getDependEntrys();

			if (task.getLongNumber().startsWith(((FDCScheduleTaskInfo) getUIContext().get(CURTASK)).getLongNumber())) {
				continue;
			}
			if (filterTaskSet == null || !filterTaskSet.contains(task.getId().toString())) {
				IRow row = table.addRow();
				row.setUserObject(task);
				loadRow(row);
			}
		}
	}

	public List orderTask(FDCScheduleInfo schedule) {
		List ret = new ArrayList();
		for (Iterator iter = schedule.getScheduleTasks().iterator(); iter.hasNext();) {
			ret.add(iter.next());
		}
		Collections.sort(ret, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				FDCScheduleTaskInfo source = (FDCScheduleTaskInfo) arg0;
				FDCScheduleTaskInfo target = (FDCScheduleTaskInfo) arg1;
				return source.getLongNumber().compareTo(target.getLongNumber());
			}
		});
		return ret;
	}

	//update 2011-10-05 by ZhouHangjian  F7过滤树编码显示格式
	protected void loadRow(IRow row) {
		FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) row.getUserObject();
		row.getCell("taskNumber").setValue(task.getLongNumber().replace('!', '.'));
		row.getCell("number").setValue(task.getNumber());
		CellTreeNode treeNode = new CellTreeNode();
		treeNode.setValue(task.getName());
		treeNode.setTreeLevel(task.getLevel());// 级次，从0开始，此处为任务的树级次
		treeNode.setHasChildren(!task.isIsLeaf());
		treeNode.isCollapse();
		treeNode.addClickListener(new NodeClickListener() {
			public void doClick(CellTreeNode source, ICell cell, int type) {
				tbnMain.revalidate();
			}
		});
		row.getCell("name").getStyleAttributes().setLocked(false);
		row.getCell("name").setValue(treeNode);
		// row.getCell("name").setValue(task.getName());
		row.getCell("effectTimes").setValue(task.getEffectTimes());
		row.getCell("startDate").setValue(task.getStart());
		row.getCell("endDate").setValue(task.getEnd());
	}

	public void onLoad() throws Exception {
		if (getScheduleInfo().get("projectSpecial") == null) {
			kDContainer1.setTitle("主项任务");
		} else {
			kDContainer1.setTitle("专项任务");
		}
		fillTable();
	}

	public Object getData() {
		return getUIContext().get(RETURNDATA);
	}
}