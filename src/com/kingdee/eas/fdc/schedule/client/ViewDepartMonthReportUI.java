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
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

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
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleCollection;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleFactory;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleInfo;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportCollection;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportInfo;
import com.kingdee.eas.fdc.schedule.WeekReportEnum;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.fdc.schedule.framework.client.KDTaskStatePanel;
import com.kingdee.eas.fdc.schedule.framework.client.KDTaskStatePanelNotToDo;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * @(#)						
 * 版权：		金蝶国际软件集团有限公司版权所有 描述： 计划管理任务列表编辑界面
 * 
 * @author 罗小龙、周航键
 * @version EAS7.0
 * @createDate 2011-09-08
 * @see
 */
public class ViewDepartMonthReportUI extends AbstractViewDepartMonthReportUI {

	/** 缺省版本号 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = CoreUIObject.getLogger(ViewDepartMonthReportUI.class);

	protected ITreeBuilder treeBuilder;

	/**
	 * output class constructor
	 */
	public ViewDepartMonthReportUI() throws Exception {
		super();
	}

	protected void initTree() throws Exception {
		// 展示成本中心组织
		buildCostCenterTree();
		treeMain.setRootVisible(false);
		treeMain.setShowsRootHandles(true);
		treeMain.expandRow(0);
		treeMain.setSelectionRow(0);
	}

	/**
	 * 
	 * @description 构建成本中心树
	 * @author 杜红明
	 * @createDate 2011-11-2
	 * @throws Exception void
	 * @version EAS7.0
	 * @see
	 */
	private void buildCostCenterTree() throws Exception {
		FilterInfo filter = new FilterInfo();
		CostCenterOrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentCostUnit();
		String longNumber = currentCostUnit.getLongNumber();
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber + "%", CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("isFreeze", Boolean.FALSE, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isOUSealUp", Boolean.FALSE, CompareType.EQUALS));
		treeBuilder = TreeBuilderFactory.createTreeBuilder(getTempLNTreeNodeCtrl(), 50, 5, filter, new SelectorItemCollection());
		treeBuilder.buildTree(treeMain);
	}

	/**
	 * 
	 * @description 成本中心组织接口
	 * @author 杜红明
	 * @createDate 2011-11-2
	 * @return
	 * @throws Exception
	 *             ILNTreeNodeCtrl
	 * @version EAS7.0
	 * @see
	 */
	private ILNTreeNodeCtrl getTempLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(CostCenterOrgUnitFactory.getRemoteInstance());
	}

	protected void cbYear_itemStateChanged(ItemEvent e) throws Exception {
		if (cbYear.getSelectedItem() != null && !"".equals(cbYear.getSelectedItem())) {
			initData();
		} else {
			this.pnlChart.removeAll();
			this.thisTable.removeRows();
			this.nextTable.removeRows();
		}
		pnlChart.updateUI();
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
	 * @see <相关的类>
	 */
	public void initData() throws Exception {

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		CostCenterOrgUnitInfo ccoui = (CostCenterOrgUnitInfo) node.getUserObject();
		List currMonth = new ArrayList();
		List nextMonth = new ArrayList();
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		this.pnlChart.removeAll();
		/*
		 * 取报告年-月
		 */
		String selectedItem = (String) cbYear.getSelectedItem();
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
		currMonth = getDeptMonthScheduleTasks(year, month, ccoui, false);

		/*
		 * 获得下月数据　
		 */
		nextMonth = getDeptMonthScheduleTasks(year, month, ccoui, true);

		initChart(currMonth);
		if (currMonth.size() > 0) {
			loadThisTableFields(currMonth);
		}
		if (nextMonth.size() > 0) {
			loadNextTableFields(nextMonth);
		}
	}

	protected void initChart(List thisMonth) throws BOSException {
		pnlChart.add(createChartPanel(thisMonth), BorderLayout.CENTER);
	}

	/**
	 * @description 创建一个图表面板
	 * @author
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @throws BOSException
	 * @see
	 */
	protected JPanel createChartPanel(List thisMonth) throws BOSException {
		JFreeChart chart = createChart(createDataset(thisMonth));
		return new ChartPanel(chart);
	}

	/**
	 * 返回一个图表
	 * 
	 * @param dataset
	 * @return
	 */
	protected JFreeChart createChart(Dataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart3D(getChartTitle(), (PieDataset) dataset, false, true, false);
		chart.setBackgroundPaint(Color.white);
		LegendTitle legendtitle = new LegendTitle(chart.getPlot());
		legendtitle.setPosition(RectangleEdge.RIGHT);
		chart.addSubtitle(legendtitle);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		// 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}\n({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
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
		plot.setSectionPaint("延迟未完成", new Color(139, 0, 180));
		plot.setSectionPaint("执行中", new Color(0, 82, 41));

		String[] titls = chart.getTitle().getText().split("\n");
		if (titls.length > 1) {
			chart.setTitle(new TextTitle(titls[0], new Font("黑体", Font.BOLD, 18)));
			chart.addSubtitle(new TextTitle(titls[1], new Font("黑体", Font.BOLD, 12)));
			chart.getSubtitle(1).setPadding(0, 55, 0, -10);
		}
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 12));
		return chart;
	}

	public String getChartTitle() {
		String title = "";
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		CostCenterOrgUnitInfo ccoui = (CostCenterOrgUnitInfo) node.getUserObject();
		List currMonth = new ArrayList();
		List nextMonth = new ArrayList();

		/*
		 * 取报告年-月
		 */
		String selectedItem = (String) cbYear.getSelectedItem();
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
			currMonth = getDeptMonthScheduleTasks(year, month, ccoui, false);
			title = year + "年" + month + "月" + ccoui.getName() + "月报";
			/*
			 * 获得下月数据　
			 */
			nextMonth = getDeptMonthScheduleTasks(year, month, ccoui, true);
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

	private DeptTaskProgressReportInfo getReportByScheduleTask(DeptMonthlyScheduleTaskInfo entryInfo) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selector = view.getSelector();
		selector.add("completePrecent");
		selector.add("planEndDate");
		selector.add("realStartDate");
		selector.add("realEndDate");
		selector.add("intendEndDate");
		selector.add("reportor.id");
		selector.add("reportor.name");
		selector.add("reportDate");
		selector.add("state");
		selector.add("description");
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("relateTask.id", entryInfo.getId().toString());
		filter.appendFilterItem("progressEdition", "1");
		view.setFilter(filter);
		DeptTaskProgressReportCollection reportCol = DeptTaskProgressReportFactory.getRemoteInstance()
				.getDeptTaskProgressReportCollection(view);
		if (reportCol != null && reportCol.size() > 0) {
			return reportCol.get(0);
		}
		return null;
	}

	public String getTitle(List currMonth, List nextMonth, int year, int month, int finished, int delayed, int unfinished,
			 int excudeing) {
		return year + "年" + month + "月工作共" + currMonth.size() + "项,其中按时完成" + finished + "项,延时完成" + delayed + "项,延时且未完成"
				+ unfinished + "项,执行中" + excudeing + "项。计划下月任务" + nextMonth.size() + "项,本月任务状态分布如下饼图：";
	}

	/**
	 * @description 构建图表所需的数据源
	 * @author 车忠伟
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @throws BOSException
	 * @see
	 */
	protected Dataset createDataset(List thisMonth) throws BOSException {
		DefaultPieDataset result = new DefaultPieDataset();
		if (thisMonth.size() > 0) {
			int finished = 0;// 完成数
			int delayed = 0;// 延时数
			int unfinished = 0;// 未完成数
			int excudeing = 0;// 执行中
			for (int i = 0; i < thisMonth.size(); i++) {
				DeptMonthlyScheduleTaskInfo entryInfo = (DeptMonthlyScheduleTaskInfo) thisMonth.get(i);
				Date planEndDate = entryInfo.getPlanFinishDate();
				BigDecimal complete = new BigDecimal(0);
				complete = entryInfo.getComplete();
				Date realEndDate = null;
				DeptTaskProgressReportInfo report = (DeptTaskProgressReportInfo) entryInfo.get("report");
				if (report != null) {
					complete = report.getCompletePrecent();
					realEndDate = report.getRealEndDate();
				}
				int state = getState(realEndDate, planEndDate, complete == null ? 0 : complete.intValue());
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
			return getDataValue(thisMonth, finished, delayed, unfinished, excudeing);
		}
		return result;
	}

	private DefaultPieDataset getDataValue(List thisMonth, int finished, int delayed, int unfinished, int excudeing) {
		DefaultPieDataset result = new DefaultPieDataset();
		double fin = Double.parseDouble(String.valueOf(finished));
		double dely = Double.parseDouble(String.valueOf(delayed));
		double unfin = Double.parseDouble(String.valueOf(unfinished));
		double excude = Double.parseDouble(String.valueOf(excudeing));
		double total = fin + dely + unfin + excude;
		if (finished == thisMonth.size()) {
			result.setValue("按时完成", total);
		} else {
			result.setValue("按时完成", fin);
		}
		if (delayed == thisMonth.size()) {
			result.setValue("延时完成", total);
		} else {
			result.setValue("延时完成", dely);
		}
		if (unfinished == thisMonth.size()) {
			result.setValue("延迟未完成", total);
		} else {
			result.setValue("延迟未完成", unfin);
		}
		if (excude == thisMonth.size()) {
			result.setValue("执行中", total);
		} else {
			result.setValue("执行中", excude);
		}
		return result;
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
	public void loadNextTableFields(List list) {
		this.nextTable.removeRows(false);
		this.nextTable.checkParsed();
		if (list.size() > 0) {// 集合大于0
			for (int i = 0; i < list.size(); i++) {// 循环将分录放入表格中
				DeptMonthlyScheduleTaskInfo entryInfo = (DeptMonthlyScheduleTaskInfo) list.get(i);
				IRow row = this.nextTable.addRow();
				row.getCell("taskName").setValue(entryInfo.getTaskName());
				row.getCell("planStartDate").setValue(entryInfo.getPlanStartDate());
				row.getCell("planEndDate").setValue(entryInfo.getPlanFinishDate());
				row.getCell("respPerson").setValue(entryInfo.getAdminPerson());
				row.getCell("respDept").setValue(entryInfo.getSchedule().getAdminDept());
				row.getCell("entryID").setValue(entryInfo.getId());
			}
		}

	}
	

	/**
	 * @discription <初始化本月任务完成情况表格数据>
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
	public void loadThisTableFields(List list) {
		this.thisTable.removeRows(false);
		this.thisTable.checkParsed();
		if (list.size() > 0) {// 集合大于0
			for (int i = 0; i < list.size(); i++) {// 循环将分录放入表格中
				DeptMonthlyScheduleTaskInfo entryInfo = (DeptMonthlyScheduleTaskInfo) list.get(i);
				DeptTaskProgressReportInfo report = (DeptTaskProgressReportInfo) entryInfo.get("report");
				if (report == null) {
					report = new DeptTaskProgressReportInfo();
				}
				IRow row = this.thisTable.addRow();
				row.getCell("taskName").setValue(entryInfo.getTaskName());
				row.getCell("planEndDate").setValue(entryInfo.getPlanFinishDate());
				BigDecimal complate = entryInfo.getComplete();
				if (null != complate && complate.intValue() == 100) {
					row.getCell("isComplete").setValue(WeekReportEnum.YES);
				} else {
					row.getCell("isComplete").setValue(WeekReportEnum.NO);
				}
				row.getCell("completePercent").setValue(report.getCompletePrecent());
				row.getCell("realEndDate").setValue(report.getRealEndDate());
				row.getCell("intendEndDate").setValue(report.getIntendEndDate());
				row.getCell("description").setValue(report.getDescription());
				row.getCell("respPerson").setValue(entryInfo.getAdminPerson());
				row.getCell("description").setValue(report.getDescription());
				row.getCell("respDept").setValue(entryInfo.getSchedule().getAdminDept());
				row.getCell("entryID").setValue(entryInfo.getId());
				initStateCell(row, entryInfo); 
			}
		}
	}

	/*
	 * 展示的时候，循环表格行，根据该隐藏列的值，设置显示的stateLogo列的展示方式。<br>
	 * 
	 * 注意：<br> 1、打钩的单元格需要设置字体为粗体，否则钩太细，画圈的则不需要；<br>
	 * 2、颜色未使用标准的Color.Green和Color.Orange，而使用了新构建的近似颜色代替，<br>
	 * 是因为纯色太亮，容易刺眼，大量使用时需减少亮度。
	 */
	public void initStateCell(IRow row, DeptMonthlyScheduleTaskInfo entryInfo) {
		Date planEndDate = entryInfo.getPlanFinishDate();
		BigDecimal complete = new BigDecimal(0);
		complete = entryInfo.getComplete();
		Date realEndDate = null;
		DeptTaskProgressReportInfo report = (DeptTaskProgressReportInfo) entryInfo.get("report");
		if (report != null) {
			complete = report.getCompletePrecent();
			realEndDate = report.getRealEndDate();
		} 
		String rsPath = "com.kingdee.eas.fdc.schedule.ScheduleResource";
		// 勾
		String achieve = EASResource.getString(rsPath, "achieve");
		// 圈
		String pending = EASResource.getString(rsPath, "pending");
		// 红
		Color red = new Color(245, 0, 0);
		// 绿
		Color green = new Color(10, 220, 10);
		// 橙
		Color orange = new Color(220, 180, 0);
		int state = getState(realEndDate, planEndDate, complete == null ? 0 : complete.intValue());
		if (row != null) {
			switch (state) {
			case 0:
				row.getCell("state").setValue(achieve);
				row.getCell("state").getStyleAttributes().setBold(true);
				row.getCell("state").getStyleAttributes().setFontColor(green);
				break;
			case 1:
				row.getCell("state").setValue(achieve);
				row.getCell("state").getStyleAttributes().setBold(true);
				row.getCell("state").getStyleAttributes().setFontColor(red);
				break;
			case 2:
				row.getCell("state").setValue(pending);
				row.getCell("state").getStyleAttributes().setFontColor(red);
				break;
			case 3:
				row.getCell("state").setValue(pending);
				row.getCell("state").getStyleAttributes().setFontColor(orange);
				break;
			}
		}
	}
	
	public static int getState(Date realEndDate, Date planEndDate, int complete) {
		// 如果进度为100 根据计划时间和实际时间来判断是按时完成或延时完成
		if (realEndDate != null && complete == 100) {
			if (DateUtils.diffdates(planEndDate, realEndDate) <= 0) {
				return 0;
			}
			return 1;
		}
		if (complete < 100) {
			if (DateUtils.diffdates(new Date(), planEndDate) >= 0) {
				return 4; // 如果进度小于100 计划完成日期>=当前时期 状态为空（执行中）
			}
			return 2;
		}
		return -1;
	}
	

	/**
	 * @discription <取得指定月份数据>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/08>
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
	 * @see <相关的类>
	 */
	public List getDeptMonthScheduleTasks(int year, int month, Object obj, boolean isNext) throws BOSException {
		if (isNext) {
			if (month == 12) {
				year = year + 1;
				month = 1;
			} else {
				month = month + 1;
			} 
		}
		String[] arrays = getDeptMonthSchedule(year, month, obj);
		List monthList = new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("adminPerson.name");
		view.getSelector().add("schedule.*");
		view.getSelector().add("schedule.adminDept.name");
		view.getSelector().add(new SelectorItemInfo("id"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("schedule.id", new HashSet(Arrays.asList(arrays)), CompareType.INCLUDE));
		view.setFilter(filter);
		DeptMonthlyScheduleTaskCollection collection = DeptMonthlyScheduleTaskFactory.getRemoteInstance().getDeptMonthlyScheduleTaskCollection(view);
		for (int j = 0; j < collection.size(); j++) {
			DeptMonthlyScheduleTaskInfo deptMonthlyScheduleTaskInfo = collection.get(j);
			deptMonthlyScheduleTaskInfo.put("report", getReportByScheduleTask(deptMonthlyScheduleTaskInfo));
			monthList.add(deptMonthlyScheduleTaskInfo);
		}
		return monthList;
	}

	/**
	 * 
	 * @description 根据组织、年、月获取部门月度计划
	 * @author 杜红明
	 * @createDate 2011-12-15
	 * @param year
	 * @param month
	 * @param obj
	 * @return
	 * @throws BOSException
	 *             String[]
	 * @version EAS7.0
	 * @see
	 */
	private String[] getDeptMonthSchedule(int year, int month, Object obj) throws BOSException {
		CostCenterOrgUnitInfo costUnit = null;
		AdminOrgUnitInfo adminUnit = null;
		String adminDeptID = "";
		if (obj instanceof CostCenterOrgUnitInfo) {
			costUnit = (CostCenterOrgUnitInfo) obj;
			adminDeptID = costUnit.getId().toString();
		} else if (obj instanceof AdminOrgUnitInfo) {
			adminUnit = (AdminOrgUnitInfo) obj;
			adminDeptID = adminUnit.getId().toString();
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid from T_SCH_DeptMonthlySchedule where FAdminDept='" + adminDeptID + "' and fyear=" + year + " and fmonth="
				+ month);
		IRowSet rs = builder.executeQuery();
		String[] arrays = new String[rs.size()];
		int i = 0;
		try {
			while (rs.next()) {
				arrays[i] = rs.getString("fid");
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrays;
	}


	private Set getProjectsByOrgUnit(String orgUnitID) {
		Set ids = new HashSet();
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select fid from T_FDC_CurProject where FFullOrgUnit='" + orgUnitID + "'");
			IRowSet rs = builder.executeQuery();
			while (rs.next()) {
				ids.add(rs.getString("fid"));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}
	
	public void onLoad() throws Exception {
		this.pnlChart.removeAll();
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		super.onLoad();
		nextTable.checkParsed();
		thisTable.checkParsed();
		thisTable.getColumn("taskName").setWidth(250);
		thisTable.getColumn("completePercent").setWidth(120);
		thisTable.getColumn("planEndDate").setWidth(120);
		thisTable.getColumn("realEndDate").setWidth(120);
		thisTable.getColumn("respPerson").setWidth(130);
		thisTable.getColumn("respDept").setWidth(130);

		nextTable.getColumn("taskName").setWidth(280);
		nextTable.getColumn("planStartDate").setWidth(250);
		nextTable.getColumn("planEndDate").setWidth(250);
		nextTable.getColumn("respPerson").setWidth(170);
		nextTable.getColumn("respDept").setWidth(170);

		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.btnExportIMG.setText("导出Excel");
		this.btnExportIMG.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.btnExportIMG.setToolTipText("导出Excel");
		this.menuItemExpIMG.setText("导出Excel");
		this.menuItemExpIMG.setToolTipText("导出Excel");
		this.menuItemExpIMG.setIcon(EASResource.getIcon("imgTbtn_output"));

		this.nextTable.getStyleAttributes().setLocked(true);
		this.thisTable.getStyleAttributes().setLocked(true);
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

	public void onShow() throws Exception {
		super.onShow();
		this.sptTop.setDividerLocation(246);
		this.sptDown.setDividerLocation(252);
		thisTable.getColumn("intendEndDate").getStyleAttributes().setHided(true);
		thisTable.getColumn("isComplete").getStyleAttributes().setHided(true);
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		this.pnlChart.removeAll();
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		/*
		 * 初始化年月下拉框的数据
		 */
		initCbYear();
		setDefalutYearSelected();
	}

	public void actionExportIMG_actionPerformed(ActionEvent e) throws Exception {
		// super.actionExportIMG_actionPerformed(e);
		List tables = new ArrayList();
		tables.add(new Object[] { "本月任务", thisTable });
		tables.add(new Object[] { "下月任务", nextTable });
		FDCTableHelper.exportExcel(this, tables);
	}

	/**
	 * @discription <初始化下拉选框(年、月)的值>
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
	public void initCbYear() {
		cbYear.removeAllItems();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.getUserObject() instanceof CostCenterOrgUnitInfo) {
			CostCenterOrgUnitInfo orgUnit = (CostCenterOrgUnitInfo) node.getUserObject();
			if (orgUnit.getId() != null) {
				EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("year");
				sic.add("month");
				FilterInfo info = new FilterInfo();
				info.getFilterItems().add(new FilterItemInfo("adminDept.id", orgUnit.getId().toString()));
				view.setFilter(info);
				view.setSelector(sic);
				DeptMonthlyScheduleCollection col = null;
				try {
					col = DeptMonthlyScheduleFactory.getRemoteInstance().getDeptMonthlyScheduleCollection(view);
					if (col != null && col.size() > 0) {
						for (int i = 0; i < col.size(); i++) {
							DeptMonthlyScheduleInfo departReportInfo = col.get(i);
							int year = departReportInfo.getYear();
							int month = departReportInfo.getMonth();
							String str = year + "-" + month;
							cbYear.addItem(str);
						}
					}
				} catch (BOSException e) {
					logger.error(e.getMessage());
				}
			}
		}
		sortCBYear();
	}

	/**
	 * 
	 * @description 日期排序
	 * @author 杜红明
	 * @createDate 2011-12-21 void
	 * @version EAS7.0
	 * @see
	 */
	private void sortCBYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		LinkedHashSet items = new LinkedHashSet();
		for (int i = 0; i < cbYear.getItemCount(); i++) {
			String item = (String) cbYear.getItemAt(i);
			try {
				items.add(sdf.parse(item));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Set sortDateByYearMonth = ViewReportHelper.sortDateByYearMonth(items);
		cbYear.removeAllItems();
		cbYear.addItems(sortDateByYearMonth.toArray());
	}
	protected BufferedImage getChartIMG() {
		// 打印前窄点
		Dimension size = pnlChart.getSize();
		int w1 = size.width;
		int h1 = size.height;
		size.setSize(w1 + 180, h1);
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
		thisTable.getColumn("description").setWidth(148);
		int w2 = thisTable.getWidth() + 10 + 130;
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
		thisTable.getColumn("description").setWidth(200);
		// 7、返回img
		return img;
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	protected BufferedImage getUIIMG() {
		// 1、将图表面板导出为img1
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
		nextTable.getColumn(0).setWidth(180);
		nextTable.getColumn(1).setWidth(180);
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
		BufferedImage img = new BufferedImage(w1 - 170, h1 + h2, BufferedImage.TYPE_INT_RGB);
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
		// 7、返回img
		return img;
	}
	
	protected void initTable() {
		KDTaskStatePanel pnlDesc = new KDTaskStatePanelNotToDo();
		contThis.getContentPane().add(pnlDesc, BorderLayout.SOUTH);
	}

	protected String getUIName() {
		return ViewDepartMonthReportUI.class.getName();
	}
}