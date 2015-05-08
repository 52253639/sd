/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.ChartFactory;
import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardPieSectionLabelGenerator;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardPieToolTipGenerator;
import com.kingdee.bos.ctrl.freechart.chart.plot.PiePlot3D;
import com.kingdee.bos.ctrl.freechart.chart.title.LegendTitle;
import com.kingdee.bos.ctrl.freechart.chart.title.TextTitle;
import com.kingdee.bos.ctrl.freechart.data.general.Dataset;
import com.kingdee.bos.ctrl.freechart.data.general.DefaultPieDataset;
import com.kingdee.bos.ctrl.freechart.data.general.PieDataset;
import com.kingdee.bos.ctrl.freechart.ui.RectangleEdge;
import com.kingdee.bos.ctrl.freechart.util.Rotation;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskStateHelper;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportInfo;
import com.kingdee.eas.fdc.schedule.WeekReportEnum;
import com.kingdee.eas.fdc.schedule.framework.client.KDTaskStatePanel;
import com.kingdee.eas.fdc.schedule.framework.client.ScheduleChartProvider;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * @(#)						
 * 版权：		金蝶国际软件集团有限公司版权所有 描述： 集团月报告
 * 
 * @author 罗小龙
 * @version EAS7.0
 * @createDate 2011-09-08
 * @see
 */
public class ViewGroupMonthReportUI extends AbstractViewGroupMonthReportUI {
	/** 缺省版本号 */
	private static final long serialVersionUID = 1L;
	
	private Map allTask = new HashMap();
	
	private Map<String, String> paramMap = null;
	

	/** 日志 */
	private static final Logger logger = CoreUIObject.getLogger(ViewGroupMonthReportUI.class);

	/**
	 * output class constructor
	 */
	public ViewGroupMonthReportUI() throws Exception {
		super();
	}
	
	private void initParams() throws BOSException {
		if (paramMap == null) {
			paramMap = ViewReportHelper.getAllParams();
		}
	}

	private void initCbYearAndMonth() throws BOSException, SQLException {
		cbYear.removeAllItems();
		cbMonth.removeAllItems();
		ItemListener[] yearListeners = cbYear.getItemListeners();
		ItemListener[] monthListeners = cbMonth.getItemListeners();
		for (ItemListener aListener : yearListeners)
			cbYear.removeItemListener(aListener);
		for (ItemListener aListener : monthListeners)
			cbYear.removeItemListener(aListener);
		Set set = getAllTask();
		if (set.size() > 0) {
			for (int i = 1; i <= 12; i++) {
				cbMonth.addItem(i + "");
			}
			int max = Integer.parseInt(((String) set.toArray()[0]).split("-")[0]);
			int min = Integer.parseInt(((String) set.toArray()[set.size() - 1]).split("-")[0]);
			for (int i = max; i >= min && i <= max; i--) {
				cbYear.addItem(i + "");
			}
		}
		
		for (ItemListener aListener : yearListeners)
			cbYear.addItemListener(aListener);
		for (ItemListener aListener : monthListeners)
			cbYear.addItemListener(aListener);
	}

	/**
	 * 初始化表格<br>
	 * 表格设置基本在元数据完成<br>
	 */
	protected void initTable() {
		KDTaskStatePanel pnlDesc = new KDTaskStatePanel();
		contThis.getContentPane().add(pnlDesc, BorderLayout.SOUTH);
		this.thisTable.checkParsed();
		this.nextTable.checkParsed();
		thisTable.getColumn("project").setWidth(160);
		thisTable.getColumn("planEndDate").setWidth(100);
		thisTable.getColumn("checkDate").setWidth(100);
		thisTable.getColumn("realEndDate").setWidth(100);
		thisTable.getColumn("intendEndDate").setWidth(100);

		nextTable.getColumn("company").setWidth(140);
		nextTable.getColumn("project").setWidth(250);//
		nextTable.getColumn("taskName").setWidth(220);
		nextTable.getColumn("planStartDate").setWidth(220);
		nextTable.getColumn("planEndDate").setWidth(220);
		nextTable.getColumn("respPerson").setWidth(120);
		nextTable.getColumn("respDept").setWidth(120);
	}
	
	

	protected FDCScheduleTaskCollection getAllTask(String date, boolean isNext) throws BOSException, ParseException, SQLException {
		CurProjectInfo currProject = null;
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selectors = view.getSelector();
		addSelectors(selectors);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("schedule.project.isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("taskType", RESchTaskTypeEnum.MILESTONE));
		filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("schedule.project.costCenter.isFreeze", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("schedule.project.costCenter.isOUSealUp", Boolean.FALSE));

        FilterInfo filterOther = new FilterInfo();
		filterOther.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial  is null "));
		
		if (prmtProject.getValue() != null) {
			currProject = (CurProjectInfo) prmtProject.getData();
		}

		filter.mergeFilter(filterOther, "and");
		view.setFilter(filter);
		FDCScheduleTaskCollection col = null;
		if(allTask.containsKey("allTask")){
			col = (FDCScheduleTaskCollection) allTask.get("allTask");
		}else{
			col = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
			allTask.put("allTask", col);
		}
		 
		if (isNext) {
			return ViewReportHelper.getNextMonthTasks(col, date, currProject);
		}
		return ViewReportHelper.getCurrMonthTasks(col, date, currProject);
	}


	private void addSelectors(SelectorItemCollection selectors) {
		selectors.add("schedule.project.fullOrgUnit.name");
		selectors.add("schedule.project.fullOrgUnit.isCompanyOrgUnit");
		selectors.add("schedule.project.costCenter.parent.isCompanyOrgUnit");
		selectors.add("schedule.project.costCenter.parent.name");
		selectors.add("schedule.project.costCenter.name");
		selectors.add("schedule.project.costCenter.id");
		selectors.add("schedule.project.id");
		selectors.add("schedule.project.name");
		selectors.add("schedule.project.number");
		selectors.add("adminPerson.id");
		selectors.add("adminPerson.name");
		selectors.add("adminPerson.number");
		selectors.add("adminDept.id");
		selectors.add("adminDept.name");
		selectors.add("adminDept.number");
		selectors.add("id");
		selectors.add("name");
		selectors.add("start");
		selectors.add("end");
		selectors.add("actualEndDate");
		selectors.add("actualStartDate");
		selectors.add("*");
	}

	/**
	 * @description
	 * @author 杜红明
	 * @createDate 2011-11-3 void
	 * @version EAS7.0
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @see
	 */

	private Set getAllTask() throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select task.fstart,task.fend from t_sch_fdcscheduletask task right outer join t_sch_fdcschedule sch on sch.fid = task.fscheduleid ");
		builder
				.appendSql("left outer join t_fdc_curproject prj on sch.fprojectid = prj.fid where prj.fisenabled = 1 and sch.fversion =1 and task.ftasktype = 'milestone'");
		IRowSet rs = builder.executeQuery();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		LinkedHashSet set = new LinkedHashSet();
		while (rs.next()) {
			try {
				set.add(sdf.parse(sdf.format(rs.getDate("fstart"))));
				set.add(sdf.parse(sdf.format(rs.getDate("fend"))));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return ViewReportHelper.sortDateByYearMonth(set);
	}

	/**
	 * @discription <初始化页面数据>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/06>
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
	 * @throws SQLException
	 * @throws ParseException
	 * @see <相关的类>
	 */
	public void initData() throws EASBizException, BOSException, ParseException, SQLException {
		FDCScheduleTaskCollection currMonth = new FDCScheduleTaskCollection();
		FDCScheduleTaskCollection nextMonth = new FDCScheduleTaskCollection();
		/*
		 * 取报告年-月
		 */
		if (cbYear.getSelectedItem() == null || cbMonth.getSelectedItem() == null) {
			return;
		}
		String selectedYear = cbYear.getSelectedItem().toString().trim();
		String selectedMonth = cbMonth.getSelectedItem().toString().trim();
		currMonth = getAllTask(new String(selectedYear + "-" + selectedMonth), false);
		nextMonth = getAllTask(new String(selectedYear + "-" + selectedMonth), true);
		loadThisTableFields(currMonth);
		loadNextTableFields(nextMonth);
		
		if (currMonth.size() > 0) {
			initChart(currMonth);
		} else {
			pnlChart.removeAll();
		}
	}

	/**
	 * @discription <初始化下月计划表格数据>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/06>
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
	public void loadNextTableFields(FDCScheduleTaskCollection list) {
		this.nextTable.removeRows(false);
		this.nextTable.checkParsed();
		if (list.size() > 0) {// 集合大于0
			for (int i = 0; i < list.size(); i++) {// 循环将分录放入表格中
				FDCScheduleTaskInfo entryInfo = (FDCScheduleTaskInfo) list.get(i);
				IRow row = this.nextTable.addRow();
				FullOrgUnitInfo respDept = entryInfo.getAdminDept();
				FDCScheduleInfo monthReportInfo = entryInfo.getSchedule();
				row.getCell("taskName").setValue(entryInfo.getName());
				row.getCell("planStartDate").setValue(entryInfo.getStart());
				row.getCell("planEndDate").setValue(entryInfo.getEnd());
				row.getCell("checkDate").setValue(entryInfo.getCheckDate());
				row.getCell("respPerson").setValue(entryInfo.getAdminPerson());
				row.getCell("respDept").setValue(respDept);
				row.getCell("entryID").setValue(entryInfo.getId());
				FullOrgUnitInfo costCenter = entryInfo.getSchedule().getProject().getFullOrgUnit();
				if (null != costCenter) {
					if (costCenter.isIsCompanyOrgUnit()) {
						row.getCell("company").setValue(costCenter.getName());
					}
				}
				if (null != monthReportInfo.getProject()) {
					row.getCell("project").setValue(monthReportInfo.getProject().getName());
				}
				if (null != monthReportInfo.getProjectSpecial()) {
					row.getCell("project").setValue(monthReportInfo.getProjectSpecial().getName());
				}
			}
		}

	}

	/**
	 * @discription <初始化本月任务完成情况表格数据>
	 * @author <Xiaolong Luo>
	 * @throws SQLException
	 */
	public void loadThisTableFields(FDCScheduleTaskCollection list) throws BOSException, SQLException {
		this.thisTable.removeRows(false);
		this.thisTable.checkParsed();
		boolean isEvaluation = false;
		boolean isParamControl = false;
		Map<String, ScheduleTaskProgressReportInfo> reportMap = ViewReportHelper.getScheudleReportData(list);
		Set<String> evaluationSet = FDCScheduleTaskStateHelper.isEvaluationed(null, list);
		ScheduleTaskProgressReportInfo reportInfo = null;
		
		String srcId = null;
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				FDCScheduleTaskInfo entryInfo = (FDCScheduleTaskInfo) list.get(i);
				srcId = entryInfo.getSrcID();
				isEvaluation = false;
				isParamControl = false;
				if (evaluationSet.contains(srcId)) {
					isEvaluation = true;
				}
				//TODO这个参数是做什么的。。。客户帐套可以为空
				if (paramMap != null && paramMap.containsKey(entryInfo.getSchedule().getProject().getCostCenter().getId().toString())) {
					isParamControl = true;
				}
				
				
				IRow row = this.thisTable.addRow();
				row.getCell("taskName").setValue(entryInfo.getName());
				row.getCell("planEndDate").setValue(entryInfo.getEnd());
				row.getCell("realEndDate").setValue(entryInfo.getActualEndDate());
				row.getCell("checkDate").setValue(entryInfo.getCheckDate());
				row.getCell("completePercent").setValue(entryInfo.getComplete());
				if (reportMap.containsKey(srcId)) {
					reportInfo = reportMap.get(srcId);
					row.getCell("description").setValue(reportInfo.getDescription());
				}
				int complate = entryInfo.getComplete() == null ? 0 : entryInfo.getComplete().intValue();
				if (complate == 100) {
					row.getCell("isComplete").setValue(WeekReportEnum.YES);
					row.getCell("intendEndDate").setValue(entryInfo.getActualEndDate());
				} else {
					row.getCell("isComplete").setValue(WeekReportEnum.NO);
					row.getCell("intendEndDate").setValue(entryInfo.getIntendEndDate() == null ? entryInfo.getEnd() : entryInfo.getIntendEndDate());
				}
				FullOrgUnitInfo respDept = entryInfo.getAdminDept();
				row.getCell("respPerson").setValue(entryInfo.getAdminPerson());
				row.getCell("respDept").setValue(respDept);
				row.getCell("entryID").setValue(entryInfo.getId());
				FullOrgUnitInfo costCenter = entryInfo.getSchedule().getProject().getFullOrgUnit();
				if (null != costCenter) {
					if (costCenter.isIsCompanyOrgUnit()) {
						row.getCell("company").setValue(costCenter.getName());
					}
				}
				CurProjectInfo project = entryInfo.getSchedule().getProject();
				if (null != project) {
					row.getCell("project").setValue(project.getName());
				}
				ViewReportHelper.initStateCell(isEvaluation, isParamControl, row, entryInfo);
			}
		}
	}

	/**
	 * 
	 * @description 获取进度汇报数据
	 * @author 杜红明
	 * @createDate 2011-12-15
	 * @return Map
	 * @version EAS7.0
	 * @throws BOSException
	 * @see
	 */
	private Map getScheudleReportData(FDCScheduleTaskInfo entryInfo) throws BOSException {
		Map report = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("intendEndDate");
		view.getSelector().add("description");
		view.getSelector().add("completePrecent");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("relateTask.id", entryInfo.getId().toString()));
		view.setFilter(filter);

		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo item = new SorterItemInfo("reportDate");
		item.setSortType(SortType.DESCEND);
		sorter.add(item);
		view.setSorter(sorter);

		ScheduleTaskProgressReportCollection reportCol = ScheduleTaskProgressReportFactory.getRemoteInstance()
				.getScheduleTaskProgressReportCollection(view);
		if (reportCol != null && reportCol.size() > 0) {
			report.put("description", reportCol.get(0).getDescription());
			report.put("intendEndDate", reportCol.get(0).getIntendEndDate());
			report.put("completePrecent", reportCol.get(0).getCompletePrecent());
			reportCol.get(0).getReportDate();
		}
		return report;
	}

	/*
	 * 展示的时候，循环表格行，根据该隐藏列的值，设置显示的stateLogo列的展示方式。<br>
	 * 
	 * 注意：<br> 1、打钩的单元格需要设置字体为粗体，否则钩太细，画圈的则不需要；<br>
	 * 2、颜色未使用标准的Color.Green和Color.Orange，而使用了新构建的近似颜色代替，<br>
	 * 是因为纯色太亮，容易刺眼，大量使用时需减少亮度。
	 */
	// public void initStateCell(boolean isEvaluation, boolean isParamControl,
	// IRow row, FDCScheduleTaskInfo entryInfo) throws BOSException {
	// int state = -1;
	// // 实际完成日期
	// Date realEndDate = entryInfo.getActualEndDate();
	// // 计划完成日期
	// Date planEndDate = entryInfo.getEnd();
	// String rsPath = "com.kingdee.eas.fdc.schedule.ScheduleResource";
	// // 勾
	// String achieve = EASResource.getString(rsPath, "achieve");
	// // 圈
	// String pending = EASResource.getString(rsPath, "pending");
	// // 绿
	// Color green = new Color(10, 220, 10);
	// // 红
	// Color red = new Color(245, 0, 0);
	// // 橙
	// Color orange = new Color(220, 180, 0);
	// int complete = entryInfo.getComplete() == null ? 0 :
	// entryInfo.getComplete().intValue();
	// state = ViewReportHelper.getState(isEvaluation, isParamControl,
	// entryInfo);
	// if (row != null) {
	// switch (state) {
	// case 0:
	// row.getCell("state").setValue(achieve);
	// row.getCell("state").getStyleAttributes().setBold(true);
	// row.getCell("state").getStyleAttributes().setFontColor(green);
	// break;
	// case 1:
	// row.getCell("state").setValue(achieve);
	// row.getCell("state").getStyleAttributes().setBold(true);
	// row.getCell("state").getStyleAttributes().setFontColor(red);
	// break;
	// case 2:
	// row.getCell("state").setValue(pending);
	// row.getCell("state").getStyleAttributes().setFontColor(red);
	// break;
	// case 3:
	// row.getCell("state").setValue(pending);
	// row.getCell("state").getStyleAttributes().setFontColor(orange);
	// break;
	// }
	// }
	// }

	protected void initChart(FDCScheduleTaskCollection thisMonth) throws ParseException, SQLException {
		pnlChart.removeAll();
		pnlChart.add(createChartPanel(thisMonth), BorderLayout.CENTER);
		pnlChart.updateUI();
	}

	/**
	 * @description 创建一个图表面板
	 * @author
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @throws SQLException
	 * @throws ParseException
	 * @see
	 */
	protected JPanel createChartPanel(FDCScheduleTaskCollection thisMonth) throws ParseException, SQLException {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}

	/**
	 * 返回一个图表
	 * 
	 * @param dataset
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	protected JFreeChart createChart(Dataset dataset) throws ParseException, SQLException {
		JFreeChart chart = ChartFactory.createPieChart3D(getChartTitle(), (PieDataset) dataset, false, true, false);
		chart.setBackgroundPaint(Color.white);
		LegendTitle legendtitle = new LegendTitle(chart.getPlot());
		legendtitle.setPosition(RectangleEdge.RIGHT);
		chart.addSubtitle(legendtitle);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		// 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
		plot
				.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}\r\n({2})", NumberFormat.getNumberInstance(), new DecimalFormat(
						"0.00%")));
		plot.setStartAngle(0);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setCircular(true);
		plot.setOutlineStroke(new BasicStroke(0));// 边框粗细
		plot.setOutlinePaint(Color.white);// 边框颜色
		plot.setLabelFont(new Font("黑体", Font.PLAIN, 10));
		plot.setForegroundAlpha(0.75f);
		plot.setBackgroundPaint(Color.white);
		// 设置突出显示的数据块
		plot.setToolTipGenerator(new StandardPieToolTipGenerator("{0}:{1}\r\n({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
		// pie.setSectionLabelFont(new Font("黑体", Font.TRUETYPE_FONT, 12));
		// 设定背景透明度（0-1.0之间）
		plot.setBackgroundAlpha(0.6f);
		// 设定前景透明度（0-1.0之间）
		plot.setForegroundAlpha(0.9f);
		plot.setDepthFactor(0.08);
		plot.setNoDataMessage("未加载数据");
		plot.setSectionPaint("按时完成", Color.GREEN);
		plot.setSectionPaint("延时完成", Color.RED);
		plot.setSectionPaint("待确认", Color.ORANGE);
		plot.setSectionPaint("延迟未完成", new Color(139, 0, 180));
		plot.setSectionPaint("执行中", new Color(0, 82, 41));

		String[] titls = chart.getTitle().getText().split("\n");
		if (titls.length > 1) {
			chart.setTitle(new TextTitle(titls[0], new Font("黑体", Font.BOLD, 18)));
			chart.addSubtitle(new TextTitle(titls[1], new Font("黑体", Font.BOLD, 10)));
			chart.getSubtitle(1).setPadding(0, 55, 0, -10);
		}
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 10));
		return chart;
	}

	/*
	 * 获取图片的Title
	 */
	public String getChartTitle() throws ParseException, SQLException {
		FDCScheduleTaskCollection currMonth = new FDCScheduleTaskCollection();
		FDCScheduleTaskCollection nextMonth = new FDCScheduleTaskCollection();
		String title = "";
		/*
		 * 取报告年-月
		 */
		String selectedYear = cbYear.getSelectedItem().toString().trim();
		String selectedMonth = cbMonth.getSelectedItem().toString().trim();
		if (null == selectedYear || "".equals(selectedYear) || selectedYear.length() == 0) {
			this.pnlChart.removeAll();
			this.thisTable.removeRows();
			this.nextTable.removeRows();
			return title;
		}
		try {
			currMonth = getAllTask(new String(selectedYear + "-" + selectedMonth), false);
			nextMonth = getAllTask(new String(selectedYear + "-" + selectedMonth), true);
			title = selectedYear + "年" + selectedMonth + "月集团月度报告";
			if (thisTable.getRowCount() > 0) {
				int finished = 0;// 完成数
				int delayed = 0;// 延时数
				int unfinished = 0;// 未完成数
				int excudeing = 0;// 执行中
				int affirm = 0;// 待确认
				for (int i = 0; i < thisTable.getRowCount(); i++) {
					Object value = thisTable.getRow(i).getCell("state").getUserObject();
					if (value == null) {
						excudeing++;
						continue;
					}

					int state = Integer.parseInt(value.toString());
					switch (state) {
					case 0:
						finished++;
						break;
					case 1:
						delayed++;
						break;
					case 2:
						affirm++;
						break;
					case 3:
						unfinished++;
						break;
					}

					}
				String str = getTitle(currMonth, nextMonth, Integer.parseInt(selectedYear), Integer.parseInt(selectedMonth), finished, delayed,
						unfinished, affirm, excudeing);
				Font font = new Font(str, Font.PLAIN, 12);
				title = title + "\n" + font.getName();
				return title;
			} else {
				String str = selectedMonth + "年" + selectedMonth + "月里程碑任务" + currMonth.size() + "项,其中按时完成0项,延时完成0项,待确认0项,延时且未完成0项,执行中0项。计划下月里程碑任务"
						+ nextMonth.size() + "项," + "\n" + "。本月里程碑任务状态分布如下饼图:";
				Font font = new Font(str, Font.PLAIN, 12);
				title = title + "\n" + font.getName();
				return title;
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}

		return title;
	}

	private String getTitle(FDCScheduleTaskCollection currMonth, FDCScheduleTaskCollection nextMonth, int year, int month, int finished, int delayed,
			int unfinished, int affirm, int excudeing) {
		return year + "年" + month + "月里程碑任务" + currMonth.size() + "项,其中按时完成" + finished + "项,延时完成" + delayed + "项,待确认" + affirm + "项,延时且未完成"
				+ unfinished + "项,执行中" + excudeing + "项。计划下月里程碑任务" + nextMonth.size() + "项。本月里程碑任务状态分布如下饼图:";
	}

	/**
	 * @description 构建图表所需的数据源
	 * @author 车忠伟
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @throws BOSException
	 * @see
	 */
	protected Dataset createDataset() {
		if (thisTable.getRowCount() > 0) {
			int finished = 0;// 完成数
			int delayed = 0;// 延时数
			int unfinished = 0;// 未完成数
			int excudeing = 0;// 执行中
			int affirm = 0;// 待确认
			for (int i = 0; i < thisTable.getRowCount(); i++) {
				Object value = thisTable.getRow(i).getCell("state").getUserObject();
				if (value == null) {
					excudeing++;
					continue;
				}

				int state = Integer.parseInt(value.toString());
				switch (state) {
				case 0:
					finished++;
					break;
				case 1:
					delayed++;
					break;
				case 2:
					affirm++;
					break;
				case 3:
					unfinished++;
					break;
				}

			}
			return getDataValue(finished, delayed, unfinished, excudeing, affirm);
		}
		return new DefaultPieDataset();
	}

	private DefaultPieDataset getDataValue(int finished, int delayed, int unfinished, int excudeing, int affirm) {
		DefaultPieDataset result = new DefaultPieDataset();
		int size = thisTable.getRowCount();
		double fin = Double.parseDouble(String.valueOf(finished));
		double dely = Double.parseDouble(String.valueOf(delayed));
		double aff = Double.parseDouble(String.valueOf(affirm));
		double unfin = Double.parseDouble(String.valueOf(unfinished));
		double excude = Double.parseDouble(String.valueOf(excudeing));
		double total = fin + dely + aff + unfin + excude;
		if (finished == size) {
			result.setValue("按时完成", total);
		} else {
			result.setValue("按时完成", fin);
		}
		if (delayed == size) {
			result.setValue("延时完成", total);
		} else {
			result.setValue("延时完成", dely);
		}
		if (affirm == size) {
			result.setValue("待确认", total);
		} else {
			result.setValue("待确认", aff);
		}
		if (unfinished == size) {
			result.setValue("延迟未完成", total);
		} else {
			result.setValue("延迟未完成", unfin);
		}
		if (excude == size) {
			result.setValue("执行中", total);
		} else {
			result.setValue("执行中", excude);
		}
		return result;
	}

	protected void cbYear_itemStateChanged(ItemEvent e) throws Exception {
		itemStateChanged();
	}

	protected void cbMonth_itemStateChanged(ItemEvent e) throws Exception {
		itemStateChanged();
	}

	protected void itemStateChanged() throws Exception {
		this.pnlChart.removeAll();
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		if (cbYear.getSelectedItem() != null && cbMonth.getSelectedItem() != null) {
			initData();
		}
		pnlChart.updateUI();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initParams();
		initCbYearAndMonth();
		this.btnExportExcel.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.btnExportExcel.setEnabled(true);
		this.menueItemExportExcel.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.menueItemExportExcel.setEnabled(true);
		this.btnPrintPreview.setIcon(EASResource.getIcon("imgTbtn_preview"));
		this.btnPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		setDefalutSelected();
		this.nextTable.getStyleAttributes().setLocked(true);
		this.thisTable.getStyleAttributes().setLocked(true);
        this.btnRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
		 loadCurProject();
		thisTable.addKDTMouseListener(new KDTMouseListener() {
			public void tableClicked(KDTMouseEvent e) {
				if (e.getColIndex() == thisTable.getColumn("description").getColumnIndex()) {
					if (e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 1) {
						thisTable.showCellDetailInfo();
					}
				}
			}
		});
	}
	
	private void loadCurProject() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		// filter.getFilterItems().add(new FilterItemInfo("isEnabled",
		// Boolean.TRUE));
		// String sql =
		// "select fid from t_fdc_curproject where FCostCenterId in (select fid from t_org_baseunit where flongnumber like (select flongnumber||'%' from t_org_baseunit where fid ='"
		// + SysContext.getSysContext().getCurrentCostUnit().getId().toString()
		// +
		// "') and fisfreeze = 0 and fisOuSealUp = 0  and fid in (select forgid from T_PM_OrgRange where fuserid = '"
		// + SysContext.getSysContext().getCurrentUserInfo().getId().toString()
		// + "'))";
		// filter.getFilterItems().add(new FilterItemInfo("curProject.id", sql,
		// CompareType.INNER));
		// logger.error(sql);
		view.setFilter(filter);

		prmtProject.setEntityViewInfo(view);
		// prmtCurproject.setDataNoNotify(editData.getProject());
		// prmtCurproject.setData(editData.getProject());
		prmtProject.setEditable(false);
		logger.error(view);
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getChartTitle(), getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDPath(), dataPvd, SwingUtilities.getWindowAncestor(this));
	}

	public void actionPrintpreview_actionPerformed(ActionEvent e) throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getChartTitle(), getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDPath(), dataPvd, SwingUtilities.getWindowAncestor(this));
	}

	public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception {
		List tables = new ArrayList();
		tables.add(new Object[] { "本月任务", thisTable });
		tables.add(new Object[] { "下月任务", nextTable });
		FDCTableHelper.exportExcel(this, tables);
	}

	/**
	 * 返回套打标题
	 * <p>
	 * 子类需重载
	 * 
	 * @return
	 */
	// protected String getTDName() {
	// return "进度图表";
	// }
	/**
	 * 返回套打模板路径
	 * <p>
	 * 子类需重载
	 * 
	 * @return
	 */
	protected String getTDPath() {
		return "/bim/fdc/process/processProjectbill";
	}

	/**
	 * 返回一张图片
	 * <p>
	 * 通常是由界面生成，包括图表和表格上下两部分<br>
	 * 
	 * 建议子类重写此方法，否则导出的图片将与页面完全相同<br>
	 * 可能导致表格的某些行或者列看不到，且间隔处有黑条
	 * 
	 * @return
	 */
	protected BufferedImage getUIIMG() {
		BufferedImage img1 = getImgAndTable();
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();

		KDTSelectBlock curSelect = nextTable.getSelectManager().get();
		// 计算表格最合适宽度(2个像素的边界+序号列宽+所有列宽和)
		int fitWidth = 2 + nextTable.getIndexColumn().getWidth() + nextTable.getColumns().getWidth();
		// 计算表格最合适高度(2个像素的边界+表头高+所有行高和)
		int fitHeight = 2 + nextTable.getHead().getHeight() + nextTable.getBody().getHeight();
		// 清除选择
		nextTable.getSelectManager().remove();
		// 此句不可去掉，用于在生成图片前设置表格合适大小
		nextTable.setSize(fitWidth, fitHeight);

		// 3、将表格导出为img2
		// 下面4行代码在设置了图片长度后 设置回去
		int printpre1 = nextTable.getColumn(0).getWidth();
		int printpre2 = nextTable.getColumn(1).getWidth();
		int printpre3 = nextTable.getColumn(2).getWidth();
		int printpre4 = nextTable.getColumn(3).getWidth();
		int printpre5 = nextTable.getColumn(4).getWidth();
		int printpre6 = nextTable.getColumn(5).getWidth();
		int printpre7 = nextTable.getColumn(6).getWidth();
		int printpre8 = nextTable.getColumn(7).getWidth();
		nextTable.getColumn(0).setWidth(120);
		nextTable.getColumn(1).setWidth(140);
		nextTable.getColumn(2).setWidth(100);
		nextTable.getColumn(3).setWidth(100);
		nextTable.getColumn(4).setWidth(100);
		nextTable.getColumn(5).setWidth(100);
		nextTable.getColumn(6).setWidth(100);
		nextTable.getColumn(7).setWidth(100);
		int w2 = nextTable.getWidth();
		int h2 = nextTable.getHeight();
		BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img2.getGraphics();
		nextTable.paintAll(g);
		g.dispose();

		// 4、新建img，将img1等比缩放成宽度与img2一样，并画在img的上方
		float scale = (float) w2 / w1;
		w1 = w2;
		h1 = (int) (h1 * scale);
		BufferedImage img = new BufferedImage(w1 - 550, h1 + h2, BufferedImage.TYPE_INT_RGB);
		Image scaleImage = img1.getScaledInstance(w1, h1, Image.SCALE_AREA_AVERAGING);
		g = img.createGraphics();
		g.drawImage(scaleImage, 0, 0, w1, h1, null);

		// 5、将img2画在img的下方
		g.drawImage(img2, 0, h1, w2, h2, null);
		g.dispose();
		if (curSelect != null) {
			nextTable.getSelectManager().select(curSelect);
		}
		// 下面4行代码在设置了图片长度后 设置回去
		nextTable.getColumn(0).setWidth(printpre1);
		nextTable.getColumn(1).setWidth(printpre2);
		nextTable.getColumn(2).setWidth(printpre3);
		nextTable.getColumn(3).setWidth(printpre4);
		nextTable.getColumn(4).setWidth(printpre5);
		nextTable.getColumn(5).setWidth(printpre6);
		nextTable.getColumn(6).setWidth(printpre7);
		nextTable.getColumn(7).setWidth(printpre8);
		// 7、返回img
		return img;
	}

	protected BufferedImage getImgAndTable() {
		BufferedImage img1 = getChartIMG();
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();
		// 本周任务表格
		KDTSelectBlock curSelect = thisTable.getSelectManager().get();
		// 计算表格最合适宽度(2个像素的边界+序号列宽+所有列宽和)
		int fitWidth = 2 + thisTable.getIndexColumn().getWidth() + thisTable.getColumns().getWidth();
		// 计算表格最合适高度(2个像素的边界+表头高+所有行高和)
		int fitHeight = 2 + thisTable.getHead().getHeight() + thisTable.getBody().getHeight();
		// 清除选择
		thisTable.getSelectManager().remove();
		// 此句不可去掉，用于在生成图片前设置表格合适大小
		thisTable.setSize(fitWidth, fitHeight);

		// 3、将表格导出为img2
		// 打印钱窄点
		int printpre0 = thisTable.getColumn(0).getWidth();
		int printpre1 = thisTable.getColumn(1).getWidth();
		int printpre2 = thisTable.getColumn(2).getWidth();
		int printpre3 = thisTable.getColumn(3).getWidth();
		int printpre4 = thisTable.getColumn(4).getWidth();
		int printpre5 = thisTable.getColumn(5).getWidth();
		int printpre6 = thisTable.getColumn(6).getWidth();
		int printpre7 = thisTable.getColumn(7).getWidth();
		int printpre8 = thisTable.getColumn(8).getWidth();
		int printpre9 = thisTable.getColumn(9).getWidth();
		int printpre10 = thisTable.getColumn(10).getWidth();
		int printpre11 = thisTable.getColumn(11).getWidth();
		thisTable.getColumn(0).setWidth(50);
		thisTable.getColumn(1).setWidth(110);
		thisTable.getColumn(2).setWidth(110);
		thisTable.getColumn(3).setWidth(110);
		thisTable.getColumn(4).setWidth(80);
		thisTable.getColumn(5).setWidth(85);
		thisTable.getColumn(6).setWidth(80);
		thisTable.getColumn(7).setWidth(80);
		thisTable.getColumn(8).setWidth(80);
		thisTable.getColumn(9).setWidth(80);
		thisTable.getColumn(10).setWidth(80);
		thisTable.getColumn(11).setWidth(80);
		int w2 = thisTable.getWidth() + 10 + 290;
		// int w2 = kdtThisEntry.getWidth() + 10 + 430;
		int h2 = thisTable.getHeight() + 10;
		BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img2.getGraphics();
		thisTable.paintAll(g);
		g.dispose();
		float scale = (float) w2 / w1;
		w1 = w2;
		h1 = (int) (h1 * scale);
		BufferedImage img = new BufferedImage(w1, h1 + h2, BufferedImage.TYPE_INT_RGB);
		Image scaleImage = img1.getScaledInstance(w1, h1, Image.SCALE_AREA_AVERAGING);
		g = img.createGraphics();
		g.drawImage(scaleImage, 0, 0, w1, h1, null);

		// 5、将img2画在img的下方
		g.drawImage(img2, 0, h1, w2, h2, null);

		g.dispose();

		// 6、将表格高宽设回原样，并选择之前所选择的行
		if (curSelect != null) {
			thisTable.getSelectManager().select(curSelect);
		}
		// 打印后窄点
		thisTable.getColumn(0).setWidth(printpre0);
		thisTable.getColumn(1).setWidth(printpre1);
		thisTable.getColumn(2).setWidth(printpre2);
		thisTable.getColumn(3).setWidth(printpre3);
		thisTable.getColumn(4).setWidth(printpre4);
		thisTable.getColumn(5).setWidth(printpre5);
		thisTable.getColumn(6).setWidth(printpre6);
		thisTable.getColumn(7).setWidth(printpre7);
		thisTable.getColumn(8).setWidth(printpre8);
		thisTable.getColumn(9).setWidth(printpre9);
		thisTable.getColumn(10).setWidth(printpre10);
		thisTable.getColumn(11).setWidth(printpre11);
		// 7、返回img
		return img;
	}

	/**
	 * 单独返回图表的图像
	 * <p>
	 * 有时候套打只需要打印图表，而下面的表格使用另外的数据源打印<br>
	 * 此时套打取图片使用此方法，而不用getUIIMG()
	 */
	protected BufferedImage getChartIMG() {
		// int height2 = pnlChart.getHeight();
		// int width2 = pnlChart.getWidth();
		// BufferedImage img = new BufferedImage(width2, height2,
		// BufferedImage.TYPE_INT_RGB);
		// Graphics g = img.getGraphics();
		// pnlChart.paintComponents(g);
		// return img;
		// 打印前窄点
		Dimension size = pnlChart.getSize();
		int w1 = size.width;
		int h1 = size.height;
		size.setSize(w1 + 850, h1);
		pnlChart.setSize(size);
		int height2 = pnlChart.getHeight();
		int width2 = pnlChart.getWidth();
		// kDPanelImage.add(KDTitle);

		BufferedImage img = new BufferedImage(width2, height2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		pnlChart.paintComponents(g);
		// 改回去
		size.setSize(w1, h1);
		pnlChart.setSize(size);
		return img;
	}

	public void onShow() throws Exception {
		super.onShow();
		initTable();
		sptTop.setDividerLocation(220);
		sptDown.setDividerLocation(220);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	private void setDefalutSelected() {
		if (setDefalutYearSelected()) {
			setDefalutMonthSelected();
		}
	}

	protected boolean setDefalutYearSelected() {
		// 初始加载当前月的数据 modify by duhongming
		for (int index = 0; index < cbYear.getItemCount(); index++) {
			String item = (String) cbYear.getItemAt(index);
			if (item.equals(getCurrYear() + "")) {
				cbYear.setSelectedIndex(index);
				return true;
			}
		}
		return false;
	}

	protected boolean setDefalutMonthSelected() {
		for (int index = 0; index < cbMonth.getItemCount(); index++) {
			String item = (String) cbMonth.getItemAt(index);
			if (item.equals(getCurrMonthOfYear() + "")) {
				cbMonth.setSelectedIndex(index);
				return true;
			}
		}
		return false;
	}

	private int getCurrYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR);
	}

	private int getCurrMonthOfYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.MONTH) + 1;
	}
	
	@Override
	protected void prmtProject_dataChanged(DataChangeEvent e) throws Exception {
		itemStateChanged();
	}
	
	@Override
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {

		allTask.clear();
		itemStateChanged();
	}
}