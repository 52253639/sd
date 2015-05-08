/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.freechart.chart.ChartFactory;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.chart.LegendItem;
import com.kingdee.bos.ctrl.freechart.chart.LegendItemCollection;
import com.kingdee.bos.ctrl.freechart.chart.axis.NumberAxis;
import com.kingdee.bos.ctrl.freechart.chart.axis.SubCategoryAxis;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardCategoryToolTipGenerator;
import com.kingdee.bos.ctrl.freechart.chart.plot.CategoryPlot;
import com.kingdee.bos.ctrl.freechart.chart.plot.Plot;
import com.kingdee.bos.ctrl.freechart.chart.plot.PlotOrientation;
import com.kingdee.bos.ctrl.freechart.chart.renderer.category.GroupedStackedBarRenderer;
import com.kingdee.bos.ctrl.freechart.data.KeyToGroupMap;
import com.kingdee.bos.ctrl.freechart.data.category.CategoryDataset;
import com.kingdee.bos.ctrl.freechart.data.category.DefaultCategoryDataset;
import com.kingdee.bos.ctrl.freechart.data.general.Dataset;
import com.kingdee.bos.ctrl.freechart.ui.GradientPaintTransformType;
import com.kingdee.bos.ctrl.freechart.ui.StandardGradientPaintTransformer;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;

/**
 * output class name
 */
public class RateTestUI extends AbstractRateTestUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RateTestUI.class);

	/**
	 * output class constructor
	 */
	public RateTestUI() throws Exception {
		super();
	}

	public void loadFields() {
		super.loadFields();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
	}

	public void actionExportIMG_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportIMG_actionPerformed(e);
	}

	protected String getTDName() {
		return "我靠";
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
	}

	/**
	 * output cbYear_itemStateChanged method
	 */
	protected void cbYear_itemStateChanged(java.awt.event.ItemEvent e)
			throws Exception {
		super.cbYear_itemStateChanged(e);
	}

	protected void initTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		treeMain.setShowsRootHandles(true);
		treeMain.expandRow(0);
	}

	protected void initChart() {
		cbYear.removeAllItems();
		cbYear.addItem(new Integer(2010));
		cbYear.addItem(new Integer(2011));
		cbYear.addItem(new Integer(2012));
	}

	protected int getChartHeight() {
		return getHeight() - 200;
	}

	protected void initTable() {
		tblMain.checkParsed();
		tblMain.removeRows();
		tblMain.getStyleAttributes().setLocked(true);
		// tblMain.getColumn("allDone").getStyleAttributes().setHided(true);
		// tblMain.getColumn("allRate").getStyleAttributes().setHided(true);

		IRow row = tblMain.addRow();
		row.getCell("month").setValue("1月");
		row.getCell("curPlan").setValue(new Integer(2));
		row.getCell("curDone").setValue(new Integer(1));
		row.getCell("curRate").setExpressions("=C1/B1");
		row.getCell("wellDone").setValue(new Integer(1));
		row.getCell("wellRate").setExpressions("=E1/B1");
		row.getCell("lateDone").setValue(new Integer(0));
		row.getCell("lateRate").setExpressions("=G1/B1");
		row.getCell("allPlan").setValue(new Integer(2));
		row.getCell("allDone").setValue(new Integer(1));
		row.getCell("allRate").setExpressions("=J1/I1");
		row = tblMain.addRow();
		row.getCell("month").setValue("2月");
		row.getCell("curPlan").setValue(new Integer(2));
		row.getCell("curDone").setValue(new Integer(2));
		row.getCell("curRate").setExpressions("=C2/B2");
		row.getCell("wellDone").setValue(new Integer(1));
		row.getCell("wellRate").setExpressions("=E2/B2");
		row.getCell("lateDone").setValue(new Integer(1));
		row.getCell("lateRate").setExpressions("=G2/B2");
		row.getCell("allPlan").setValue(new Integer(4));
		row.getCell("allDone").setValue(new Integer(3));
		row.getCell("allRate").setExpressions("=J2/I2");
		row = tblMain.addRow();
		row.getCell("month").setValue("3月");
		row.getCell("curPlan").setValue(new Integer(3));
		row.getCell("curDone").setValue(new Integer(2));
		row.getCell("curRate").setExpressions("=C3/B3");
		row.getCell("wellDone").setValue(new Integer(2));
		row.getCell("wellRate").setExpressions("=E3/B3");
		row.getCell("lateDone").setValue(new Integer(0));
		row.getCell("lateRate").setExpressions("=G3/B3");
		row.getCell("allPlan").setValue(new Integer(7));
		row.getCell("allDone").setValue(new Integer(5));
		row.getCell("allRate").setExpressions("=J3/I3");
		row = tblMain.addRow();
		row.getCell("month").setValue("4月");
		row.getCell("curPlan").setValue(new Integer(4));
		row.getCell("curDone").setValue(new Integer(4));
		row.getCell("curRate").setExpressions("=C4/B4");
		row.getCell("wellDone").setValue(new Integer(1));
		row.getCell("wellRate").setExpressions("=E4/B4");
		row.getCell("lateDone").setValue(new Integer(3));
		row.getCell("lateRate").setExpressions("=G4/B4");
		row.getCell("allPlan").setValue(new Integer(11));
		row.getCell("allDone").setValue(new Integer(9));
		row.getCell("allRate").setExpressions("=J4/I4");
		row = tblMain.addRow();
		row.getCell("month").setValue("5月");
		row.getCell("curPlan").setValue(new Integer(5));
		row.getCell("curDone").setValue(new Integer(4));
		row.getCell("curRate").setExpressions("=C5/B5");
		row.getCell("wellDone").setValue(new Integer(3));
		row.getCell("wellRate").setExpressions("=E5/B5");
		row.getCell("lateDone").setValue(new Integer(1));
		row.getCell("lateRate").setExpressions("=G5/B5");
		row.getCell("allPlan").setValue(new Integer(16));
		row.getCell("allDone").setValue(new Integer(13));
		row.getCell("allRate").setExpressions("=J5/I5");
		row = tblMain.addRow();
		row.getCell("month").setValue("6月");
		row.getCell("curPlan").setValue(new Integer(6));
		row.getCell("curDone").setValue(new Integer(5));
		row.getCell("curRate").setExpressions("=C6/B6");
		row.getCell("wellDone").setValue(new Integer(3));
		row.getCell("wellRate").setExpressions("=E6/B6");
		row.getCell("lateDone").setValue(new Integer(2));
		row.getCell("lateRate").setExpressions("=G6/B6");
		row.getCell("allPlan").setValue(new Integer(22));
		row.getCell("allDone").setValue(new Integer(18));
		row.getCell("allRate").setExpressions("=J6/I6");
		row = tblMain.addRow();
		row.getCell("month").setValue("7月");
		row.getCell("curPlan").setValue(new Integer(3));
		row.getCell("curDone").setValue(new Integer(3));
		row.getCell("curRate").setExpressions("=C7/B7");
		row.getCell("wellDone").setValue(new Integer(2));
		row.getCell("wellRate").setExpressions("=E7/B7");
		row.getCell("lateDone").setValue(new Integer(1));
		row.getCell("lateRate").setExpressions("=G7/B7");
		row.getCell("allPlan").setValue(new Integer(25));
		row.getCell("allDone").setValue(new Integer(21));
		row.getCell("allRate").setExpressions("=J7/I7");
		row = tblMain.addRow();
		row.getCell("month").setValue("8月");
		row.getCell("curPlan").setValue(new Integer(5));
		row.getCell("curDone").setValue(new Integer(4));
		row.getCell("curRate").setExpressions("=C8/B8");
		row.getCell("wellDone").setValue(new Integer(4));
		row.getCell("wellRate").setExpressions("=E8/B8");
		row.getCell("lateDone").setValue(new Integer(0));
		row.getCell("lateRate").setExpressions("=G8/B8");
		row.getCell("allPlan").setValue(new Integer(30));
		row.getCell("allDone").setValue(new Integer(25));
		row.getCell("allRate").setExpressions("=J8/I8");
		row = tblMain.addRow();
		row.getCell("month").setValue("9月");
		row.getCell("curPlan").setValue(new Integer(2));
		row.getCell("curDone").setValue(new Integer(2));
		row.getCell("curRate").setExpressions("=C9/B9");
		row.getCell("wellDone").setValue(new Integer(1));
		row.getCell("wellRate").setExpressions("=E9/B9");
		row.getCell("lateDone").setValue(new Integer(1));
		row.getCell("lateRate").setExpressions("=G9/B9");
		row.getCell("allPlan").setValue(new Integer(32));
		row.getCell("allDone").setValue(new Integer(27));
		row.getCell("allRate").setExpressions("=J9/I9");
		row = tblMain.addRow();
		row.getCell("month").setValue("10月");
		row.getCell("curPlan").setValue(new Integer(3));
		row.getCell("curDone").setValue(new Integer(1));
		row.getCell("curRate").setExpressions("=C10/B10");
		row.getCell("wellDone").setValue(new Integer(0));
		row.getCell("wellRate").setExpressions("=E10/B10");
		row.getCell("lateDone").setValue(new Integer(1));
		row.getCell("lateRate").setExpressions("=G10/B10");
		row.getCell("allPlan").setValue(new Integer(35));
		row.getCell("allDone").setValue(new Integer(28));
		row.getCell("allRate").setExpressions("=J10/I10");
		row = tblMain.addRow();
		row.getCell("month").setValue("11月");
		row.getCell("curPlan").setValue(new Integer(6));
		row.getCell("curDone").setValue(new Integer(6));
		row.getCell("curRate").setExpressions("=C11/B11");
		row.getCell("wellDone").setValue(new Integer(4));
		row.getCell("wellRate").setExpressions("=E11/B11");
		row.getCell("lateDone").setValue(new Integer(2));
		row.getCell("lateRate").setExpressions("=G11/B11");
		row.getCell("allPlan").setValue(new Integer(41));
		row.getCell("allDone").setValue(new Integer(33));
		row.getCell("allRate").setExpressions("=J11/I11");
		row = tblMain.addRow();
		row.getCell("month").setValue("12月");
		row.getCell("curPlan").setValue(new Integer(2));
		row.getCell("curDone").setValue(new Integer(2));
		row.getCell("curRate").setExpressions("=C12/B12");
		row.getCell("wellDone").setValue(new Integer(1));
		row.getCell("wellRate").setExpressions("=E12/B12");
		row.getCell("lateDone").setValue(new Integer(1));
		row.getCell("lateRate").setExpressions("=G12/B12");
		row.getCell("allPlan").setValue(new Integer(43));
		row.getCell("allDone").setValue(new Integer(36));
		row.getCell("allRate").setExpressions("=J12/I12");
		// row.getCell(0).setValue("当月(按时/延时/计划)");
		// row.getCell(1).setValue("(1/0/2) 50%");
		// row.getCell(2).setValue("(1/1/2) 100%");
		// row.getCell(3).setValue("(2/0/3) 66%");
		// row.getCell(4).setValue("(1/3//4) 100%");
		// row.getCell(5).setValue("(3/1/5) 80%");
		// row.getCell(6).setValue("(3/2/5) 100%");
		// row.getCell(7).setValue("(2/1/3) 100%");
		// row.getCell(8).setValue("(4/0/5) 80%");
		// row.getCell(9).setValue("(1/1/2) 100%");
		// row.getCell(10).setValue("(0/1/3) 33%");
		// row.getCell(11).setValue("(4/2/6) 100%");
		// row.getCell(12).setValue("(1/1/2) 100%");
		// row = tblMain.addRow();
		// row.getCell(0).setValue("累计(按时/延时/计划)");
		// row.getCell(1).setValue("(1/0/2) 50%");
		// row.getCell(2).setValue("(2/1/4) 75%");
		// row.getCell(3).setValue("(4/1/7) 71%");
		// row.getCell(4).setValue("(5/4/11) 81%");
		// row.getCell(5).setValue("(8/5/16) 81%");
		// row.getCell(6).setValue("(11/7/22) 82%");
		// row.getCell(7).setValue("(13/8/25) 84%");
		// row.getCell(8).setValue("(17/8/30) 83%");
		// row.getCell(9).setValue("(18/9/32) 84%");
		// row.getCell(10).setValue("(18/10/35) 80%");
		// row.getCell(11).setValue("(22/12/41) 83%");
		// row.getCell(12).setValue("(23/13/43) 84%");
		// row.getCell(12).setValue("(323/613/943) 84%");
	}

	/**
	 * 返回一个柱状图数据集<br>
	 * 实际应用时，在此方法中取数并构建数据集返回
	 * <p>
	 * 第一个参数为完成百分比<br>
	 * 第二个参数为分组，其中括号外为属于柱分组，括号内为柱内分组<br>
	 * 第三个参数为月度
	 */
	protected Dataset createDataset() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		result.addValue(50, "当月达成率 (按时完成)", "1月");
		result.addValue(50, "当月达成率 (按时完成)", "2月");
		result.addValue(66.7, "当月达成率 (按时完成)", "3月");
		result.addValue(25, "当月达成率 (按时完成)", "4月");
		result.addValue(60, "当月达成率 (按时完成)", "5月");
		result.addValue(50, "当月达成率 (按时完成)", "6月");
		result.addValue(66.7, "当月达成率 (按时完成)", "7月");
		result.addValue(80, "当月达成率 (按时完成)", "8月");
		result.addValue(50, "当月达成率 (按时完成)", "9月");
		result.addValue(0, "当月达成率 (按时完成)", "10月");
		result.addValue(66.7, "当月达成率 (按时完成)", "11月");
		result.addValue(50, "当月达成率 (按时完成)", "12月");
		result.addValue(0, "当月达成率 (延时完成)", "1月");
		result.addValue(50, "当月达成率 (延时完成)", "2月");
		result.addValue(0, "当月达成率 (延时完成)", "3月");
		result.addValue(75, "当月达成率 (延时完成)", "4月");
		result.addValue(20, "当月达成率 (延时完成)", "5月");
		result.addValue(33.3, "当月达成率 (延时完成)", "6月");
		result.addValue(33.3, "当月达成率 (延时完成)", "7月");
		result.addValue(0, "当月达成率 (延时完成)", "8月");
		result.addValue(50, "当月达成率 (延时完成)", "9月");
		result.addValue(33.3, "当月达成率 (延时完成)", "10月");
		result.addValue(33.3, "当月达成率 (延时完成)", "11月");
		result.addValue(50, "当月达成率 (延时完成)", "12月");

		result.addValue(50, "累计达成率 (按时完成)", "1月");
		result.addValue(50, "累计达成率 (按时完成)", "2月");
		result.addValue(57, "累计达成率 (按时完成)", "3月");
		result.addValue(45, "累计达成率 (按时完成)", "4月");
		result.addValue(50, "累计达成率 (按时完成)", "5月");
		result.addValue(50, "累计达成率 (按时完成)", "6月");
		result.addValue(52, "累计达成率 (按时完成)", "7月");
		result.addValue(56.7, "累计达成率 (按时完成)", "8月");
		result.addValue(56.2, "累计达成率 (按时完成)", "9月");
		result.addValue(51.4, "累计达成率 (按时完成)", "10月");
		result.addValue(53.7, "累计达成率 (按时完成)", "11月");
		result.addValue(53.5, "累计达成率 (按时完成)", "12月");
		result.addValue(0, "累计达成率 (延时完成)", "1月");
		result.addValue(25, "累计达成率 (延时完成)", "2月");
		result.addValue(14.3, "累计达成率 (延时完成)", "3月");
		result.addValue(36.4, "累计达成率 (延时完成)", "4月");
		result.addValue(31.2, "累计达成率 (延时完成)", "5月");
		result.addValue(31.8, "累计达成率 (延时完成)", "6月");
		result.addValue(32, "累计达成率 (延时完成)", "7月");
		result.addValue(26.7, "累计达成率 (延时完成)", "8月");
		result.addValue(28.1, "累计达成率 (延时完成)", "9月");
		result.addValue(28.6, "累计达成率 (延时完成)", "10月");
		result.addValue(29.2, "累计达成率 (延时完成)", "11月");
		result.addValue(30.2, "累计达成率 (延时完成)", "12月");

		return result;
	}

	/**
	 * 返回图表
	 */
	protected JFreeChart createChart(Dataset dataset) {

		JFreeChart chart = ChartFactory.createStackedBarChart(
				"2010年 四季春项目 主项计划达成率", // 标题
				"月份", // 横轴
				"达成率", // 纵轴
				(CategoryDataset) dataset, // 数据源
				PlotOrientation.VERTICAL, // 图表方向
				true, // 说明面板
				true, // 鼠标停留提示
				false // urls
				);
		// 标题字体
		chart.getTitle().setFont(new Font("黑体", Font.PLAIN, 20));
		
		// 设置分组情况，当月开头的都属于一个组，累计开头的属于另一个组
		// 这里决定该数据显示于哪根柱子
		GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
		KeyToGroupMap map = new KeyToGroupMap("G1");
		map.mapKeyToGroup("当月达成率 (按时完成)", "G1");
		map.mapKeyToGroup("当月达成率 (延时完成)", "G1");
		map.mapKeyToGroup("累计达成率 (按时完成)", "G2");
		map.mapKeyToGroup("累计达成率 (延时完成)", "G2");
		renderer.setSeriesToGroupMap(map);
		// 柱内间隔
		renderer.setItemMargin(0.02);
		// 不画外框
		renderer.setDrawBarOutline(false);

		// 各组的颜色，属于渐变
		// setSeriesPaint设置该颜色适用的节点，每个月度内，从最左柱子的最底下一个区块开始
		// 下标为0，往上数递增，然后到第二个柱子，以此类推直到最后
		// 由于本例中，每个月只有2个柱子，每个柱子分2个区块，所以0、2表示当月和累计的按时完成
		// 1,3表示延时完成
		Paint p1 = new GradientPaint(0.0f, 0.0f, new Color(149, 255, 149),
				0.0f, 0.0f, new Color(89, 183, 89));
		renderer.setSeriesPaint(0, p1);

		Paint p2 = new GradientPaint(0.0f, 0.0f, new Color(255, 200, 200),
				20.0f, 0.0f, new Color(255, 145, 145));
		renderer.setSeriesPaint(1, p2);

		Paint p3 = new GradientPaint(0.0f, 0.0f, new Color(109, 142, 255),
				0.0f, 0.0f, new Color(49, 82, 123));
		renderer.setSeriesPaint(2, p3);

		Paint p4 = new GradientPaint(0.0f, 0.0f, new Color(255, 170, 170),
				20.0f, 0.0f, new Color(255, 45, 45));
		renderer.setSeriesPaint(3, p4);
		// 颜色渐变方向，此处为中心渐变
		renderer
				.setGradientPaintTransformer(new StandardGradientPaintTransformer(
						GradientPaintTransformType.CENTER_HORIZONTAL));

		// 鼠标停留在柱状图内显示提示信息格式
		renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
				"({0}, {1}) = {2}%", NumberFormat.getInstance()));
		// 柱图中显示内容的格式，此处显示百分比，并且靠上显示
		// renderer.setItemLabelGenerator(new
		// StandardCategoryItemLabelGenerator(
		// "{2}", NumberFormat.getInstance()));
		// renderer.setItemLabelsVisible(true);
		// renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
		// ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER,
		// TextAnchor.TOP_CENTER, 0.0));
		// renderer.setItemLabelFont(new Font("宋体", Font.PLAIN, 9));
		chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
		chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setRenderer(renderer);

		// 横轴，并设置每个月度之间的间隔
		SubCategoryAxis domainAxis = new SubCategoryAxis("");
		domainAxis.setCategoryMargin(0.2);
		// domainAxis.addSubCategory("当月");
		// domainAxis.addSubCategory("累计");
		plot.setDomainAxis(domainAxis);
		// 纵轴，小数类型
		NumberAxis rangeAxis = new NumberAxis("达成率(%)");
		rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
		rangeAxis.setUpperMargin(0.10);
		rangeAxis.setLowerBound(0.0);
		rangeAxis.setUpperBound(110.0);
		plot.setRangeAxis(rangeAxis);

		// 最底下说明框
		plot.setFixedLegendItems(createLegendItems());
		
		return chart;
	}

	private static LegendItemCollection createLegendItems() {
		LegendItemCollection result = new LegendItemCollection();
		LegendItem item1 = new LegendItem("当月按时完成", "-", null, null,
				Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(89, 183, 89));
		LegendItem item2 = new LegendItem("当月延时完成", "-", null, null,
				Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(255, 105, 105));
		LegendItem item3 = new LegendItem("累计按时完成", "-", null, null,
				Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(49, 82, 123));
		LegendItem item4 = new LegendItem("累计延时完成", "-", null, null,
				Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(255, 45, 45));
		result.add(item1);
		result.add(item2);
		result.add(item3);
		result.add(item4);
		return result;
	}

}