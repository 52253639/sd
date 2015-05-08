/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportInfo;

/**
 * output class name
 */
public class DeptMonthlyTaskReportUI extends AbstractDeptMonthlyTaskReportUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4582166030031599300L;
	private static final Logger logger = CoreUIObject.getLogger(DeptMonthlyTaskReportUI.class);
    
    /**
     * output class constructor
     */
    public DeptMonthlyTaskReportUI() throws Exception
    {
        super();
    }
   
	/**
	 * @throws Exception
	 */
	public void onLoad() throws Exception {
		super.onLoad();
		this.windowTitle = "部门月度报告";
		initData();
	}
	/**
	 * @return
	 */
	public String getChartTitle() {
		String title = "";
		AdminOrgUnitInfo adminDept = (AdminOrgUnitInfo) getUIContext().get("adminDept");
		String selectedItem = getUIContext().get("scheduleMonth").toString();
		List currMonth = new ArrayList();
		List nextMonth = new ArrayList();

		/*
		 * 取报告年-月
		 */
		// String selectedItem = (String) cbYear.getSelectedItem();
		if (null == selectedItem || "".equals(selectedItem) || selectedItem.length() == 0) {
			this.pnlChart.removeAll();
			this.thisTable.removeRows();
			this.nextTable.removeRows();
			return title;
		}

		int year = 1990;
		int month = 1;

		try {
			/*
			 * 　获得本月数据
			 */
			String[] split = selectedItem.split("-");
			year = Integer.parseInt(split[0]);
			month = Integer.parseInt(split[1]);
			currMonth = getDeptMonthScheduleTasks(year, month, adminDept, false);

			/*
			 * 获得下月数据　
			 */
			nextMonth = getDeptMonthScheduleTasks(year, month, adminDept, true);
			title = year + "年" + month + "月" + adminDept.getName() + "月报";
			if (currMonth.size() > 0) {
				int finished = 0;// 完成数
				int delayed = 0;// 延时数
				int unfinished = 0;// 未完成数
				int excudeing = 0;// 执行中
				for (int i = 0; i < currMonth.size(); i++) {
					DeptMonthlyScheduleTaskInfo entryInfo = (DeptMonthlyScheduleTaskInfo) currMonth.get(i);
					Date planEndDate = entryInfo.getPlanFinishDate();
					BigDecimal complete = new BigDecimal(0);
					complete = entryInfo.getComplete();
					Date realEndDate = null;
					DeptTaskProgressReportInfo report = (DeptTaskProgressReportInfo) entryInfo.get("report");
					if (report != null) {
						complete = report.getCompletePrecent();
						realEndDate = report.getRealEndDate();
					}
					int state = getState(realEndDate, planEndDate, complete == null ? 0 : complete
							.intValue());
					switch (state) {
					case 0:
						finished++;
						break;
					case 1:
						delayed++;
						break;
					case 2:
						unfinished++;
						break;
					case 4:
						excudeing++;
						break;
					}
				}
				String str = getTitle(currMonth, nextMonth, year, month, finished, delayed, unfinished, excudeing);
				Font font = new Font(str, Font.PLAIN, 12);
				title = title + "\n" + font.getName();
				return title;
			} else {
				String str = year + "年" + month + "月工作共" + currMonth.size() + "项,其中按时完成0项,延时完成0项,延时且未完成0项,执行中0项。计划下月任务" + nextMonth.size()
						+ "项," + "\n" + "本月任务状态分布如下饼图：";
				Font font = new Font(str, Font.PLAIN, 12);
				title = title + "\n" + font.getName();
				return title;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return "";
	}

	public void initData() throws Exception {
		AdminOrgUnitInfo adminDept = (AdminOrgUnitInfo) getUIContext().get("adminDept");
		String selectedItem = getUIContext().get("scheduleMonth").toString();
		this.cbYear.addItem(selectedItem);
		cbYear.setSelectedIndex(0);
		List currMonth = new ArrayList();
		List nextMonth = new ArrayList();
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		this.pnlChart.removeAll();
		/*
		 * 取报告年-月
		 */
		if (null == selectedItem || "".equals(selectedItem) || selectedItem.length() == 0) {
			return;
		}

		int year = 1990;
		int month = 1;

		/*
		 * 　获得本月数据
		 */
		String[] split = selectedItem.split("-");
		year = Integer.parseInt(split[0]);
		month = Integer.parseInt(split[1]);
		currMonth = getDeptMonthScheduleTasks(year, month, adminDept, false);

		/*
		 * 获得下月数据　
		 */
		nextMonth = getDeptMonthScheduleTasks(year, month, adminDept, true);

		initChart(currMonth);
		if (currMonth.size() > 0) {
			loadThisTableFields(currMonth);
		}
		if (nextMonth.size() > 0) {
			loadNextTableFields(nextMonth);
		}
	}
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }
    
	public boolean destroyWindow() {
		return true;
	}
	
	protected void cbYear_itemStateChanged(ItemEvent e) throws Exception {

	}
	
	protected String getUIName() {
		return DeptMonthlyTaskReportUI.class.getName();
	}
	/**
	 * @throws Exception
	 */
	protected void initTree() throws Exception {
		// super.initTree();
	}
}