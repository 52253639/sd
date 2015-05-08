/*
 * @(#)EASTest.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.schedule;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.ChartFactory;
import com.kingdee.bos.ctrl.freechart.chart.ChartFrame;
import com.kingdee.bos.ctrl.freechart.chart.ChartUtilities;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.chart.axis.AxisLocation;
import com.kingdee.bos.ctrl.freechart.chart.axis.CategoryAxis;
import com.kingdee.bos.ctrl.freechart.chart.axis.CategoryLabelPositions;
import com.kingdee.bos.ctrl.freechart.chart.axis.NumberAxis;
import com.kingdee.bos.ctrl.freechart.chart.axis.NumberTickUnit;
import com.kingdee.bos.ctrl.freechart.chart.encoders.EncoderUtil;
import com.kingdee.bos.ctrl.freechart.chart.encoders.ImageFormat;
import com.kingdee.bos.ctrl.freechart.chart.labels.ItemLabelAnchor;
import com.kingdee.bos.ctrl.freechart.chart.labels.ItemLabelPosition;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardCategoryItemLabelGenerator;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardCategoryToolTipGenerator;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardPieSectionLabelGenerator;
import com.kingdee.bos.ctrl.freechart.chart.plot.CategoryPlot;
import com.kingdee.bos.ctrl.freechart.chart.plot.DatasetRenderingOrder;
import com.kingdee.bos.ctrl.freechart.chart.plot.PiePlot3D;
import com.kingdee.bos.ctrl.freechart.chart.plot.PlotOrientation;
import com.kingdee.bos.ctrl.freechart.chart.renderer.category.LineAndShapeRenderer;
import com.kingdee.bos.ctrl.freechart.chart.renderer.category.StackedBarRenderer;
import com.kingdee.bos.ctrl.freechart.chart.title.LegendTitle;
import com.kingdee.bos.ctrl.freechart.chart.title.TextTitle;
import com.kingdee.bos.ctrl.freechart.data.category.CategoryDataset;
import com.kingdee.bos.ctrl.freechart.data.general.DatasetUtilities;
import com.kingdee.bos.ctrl.freechart.data.general.DefaultPieDataset;
import com.kingdee.bos.ctrl.freechart.data.general.PieDataset;
import com.kingdee.bos.ctrl.freechart.ui.GradientPaintTransformType;
import com.kingdee.bos.ctrl.freechart.ui.StandardGradientPaintTransformer;
import com.kingdee.bos.ctrl.freechart.ui.TextAnchor;
import com.kingdee.bos.ctrl.freechart.util.ArrayUtilities;
import com.kingdee.bos.ctrl.kdf.table.KDTCell;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.common.json.simple.JSONObject;
import com.kingdee.eas.cp.common.json.simple.JSONValue;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgCollection;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgFactory;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;

/**
 * 描述:进度报表图形帮助类
 * @author adward_huang  date:2012-9-13
 * @version EAS6.1
 */
public class ScheduleChartHelper {

	private static final long serialVersionUID = -7839731547156290L;
	
	private static final Logger logger = CoreUIObject.getLogger(ScheduleChartHelper.class);

	// 标题颜色-淡蓝色
	public static final Color CHART_TITLE_COLOR = new Color(153, 153, 255);
	public static final Font CHART_TITLE_FONT = new Font("隶书", Font.BOLD, 28);

	// 柱状图颜色
	public static final Color CHART_TIMED_FINISHED_COLOR = new Color(0, 176, 80);// 按时完成颜色--绿色
	public static final Color CHART_15IN_TIME_FINISHED_COLOR = new Color(255, 255, 0);//  延迟15天内完成颜色--黄色
	public static final Color CHART_15OUTER_TIME_FINISHED_COLOR = new Color(217, 150, 148);// 延迟15天外完成颜色--浅褐色
	public static final Color CHART_NO_TIME_FINISHED_COLOR = new Color(255, 0, 0);// 未完成颜色--红色
	public static final Color CHART_UNCOMFIRMED_TIME_FINISHED_COLOR = new Color(153, 0, 153);// 待确认颜色--深蓝色

	// 折线图颜色
	public static final Color CHART_TIMED_FINISHED_COLOR_RATE = new Color(0, 255, 42);// 按时完成率--绿色
	public static final Color CHART_DEFERED_FINISHED_COLOR_RATE = new Color(255, 127, 42);// 延时完成率颜色--淡红色

	// 图形分类名称
	public static final String[] CHART_CATEGORY_ROW_LIST = { "按时完成", "延迟15天内完成", "延迟15天外完成", "未完成", "待确认" };
	public static final String[] CHART_CATEGORY_RATE_LIST = { "按时完成率", "延迟完成率" };
	
	// 设置chart图形的高度（以单元格高度为单位）
	private static final int CHART_HEIGHT = 32;
	
	// 报表导出的列宽度
	private static final int EXPORT_COLUMN_WIDTH = 19;
	
	private static final int CHART_Y_AXIS_GAP = 10;
	
	private static final String NUMBER_SEPRATOR = "!";
	

	/**
	 * 描述：创建柱状图,折线图 数据集
	 * @param data
	 * @param rowKeys
	 * @param columnKeys
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-10
	 */
	public static CategoryDataset getChartDataset(double[][] data, String[] rowKeys, String[] columnKeys) {
		
		return DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);
	}

	/** 
	* 获取柱状图分类数据集 
	*/
	public CategoryDataset getBarDataset(double[][] data, String[] columnKeys) {
		CategoryDataset dataset = getChartDataset(data, ScheduleChartHelper.CHART_CATEGORY_ROW_LIST, columnKeys);
		return dataset;
	}

	/** 
	* 获取折线图分类数据集 
	*/
	public CategoryDataset getLineDataset(double[][] data, String[] columnKeys) {
		CategoryDataset dataset = getChartDataset(data, ScheduleChartHelper.CHART_CATEGORY_RATE_LIST, columnKeys);
		return dataset;
	}

	/** 
	* 获取折线图分类数据集 
	*/
	public PieDataset getPieDataset(double[] data) {
		PieDataset dataset = getPieDataset(data, ScheduleChartHelper.CHART_CATEGORY_ROW_LIST);
		return dataset;
	}
	
	/** 
	* 获取饼状图分类数据集 
	*/
	public PieDataset getPieDataset(double[] data, String[] rowKeys) {
		if (data != null && rowKeys != null) {
			if (data.length == rowKeys.length) {
				DefaultPieDataset dataset = new DefaultPieDataset();
				for (int i = 0; i < data.length; i++) {
					dataset.setValue(rowKeys[i], data[i]);
				}
				return dataset;
			}
		}
		return null;
	}

	/**
	 * 描述：提供对外接口，构造图形界面
	 * @param data
	 * @param columnKeys
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-13
	 */
	public JFreeChart getChartPanel(String chartTitle, String[] columnKeys, double[][] data, double[][] lineData,int max) {

		CategoryDataset barDataset = getBarDataset(data, columnKeys);
		CategoryDataset lineDataset = getLineDataset(lineData, columnKeys);
		JFreeChart chart = null;
		
		// 如果进度计划中项目数大于1，则以柱状图和线图的组合方式显示
		if (columnKeys != null && columnKeys.length > 1) {
			chart = createBarChart(barDataset, chartTitle, lineDataset,max);
		} else {
			// 如果进度计划中项目数为1时，则以饼状图显示
			if (data != null) {
				int len = data.length;
				double[] value = new double[len];
				for(int i = 0; i< len; i++){
					value[i] = data[i][0];
				}
				PieDataset pieDataset = getPieDataset(value);
				chart = createPieChart(pieDataset, chartTitle, CHART_CATEGORY_ROW_LIST);
			}
		}
		return chart;
	}

	/**
	 * 创建图表JfreeChart对象
	 * @param dataset 数据集
	 * @param title 图表标题
	 * @return 图表JFreeChart
	 */
	private JFreeChart createBarChart(CategoryDataset barDataset, String title, CategoryDataset lineDataset, int max) {

		//用ChartFactory来构建所需的Chart对象
		JFreeChart chart = ChartFactory.createStackedBarChart3D(title, //图表标题
				"",//横向标签 
				"",//纵向标签 
				barDataset, //数据集
				PlotOrientation.VERTICAL, // 图表方向：水平、垂直, 
				true,//是否显示图例(对于简单的柱状图必须是false) 
				true, //是都显示tooltip
				false //是否生成URL链接
				);

		// 设置chart属性
		setBarChart(chart, max);

		// 添加线性图形
		addLineChartDataset(chart, lineDataset);

		return chart;
	}

	/**
	  * 设置图表属性
	  * @param chart 需要设置的图表
	  * @return JFreeChart设置好的图表
	  */
	private void setBarChart(JFreeChart chart, int max) {
		//设置消除字体的锯齿渲染
		//chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF); 

		// 设置Title和Legend的字体(不然会乱码)
		chart.getTitle().setFont(CHART_TITLE_FONT);
		chart.getTitle().setPaint(CHART_TITLE_COLOR);
		chart.getLegend().setItemFont(new Font("宋体", Font.CENTER_BASELINE, 10));

		// 获得图表区域对象
		CategoryPlot plot = chart.getCategoryPlot();

		//设置横虚线可见
		plot.setRangeGridlinesVisible(true);
		// 设置虚线色彩
		plot.setRangeGridlinePaint(Color.gray);
		// 设置柱图背景色
		plot.setBackgroundPaint(Color.white);
		// 设置纵横坐标的显示位置
		plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		
		// 数据轴精度
		NumberAxis vn = (NumberAxis) plot.getRangeAxis();
		
		// 设置刻度必须从0开始 
		vn.setAutoRangeIncludesZero(true);
		
		// 数据轴数据标签的显示格式
		DecimalFormat df = new DecimalFormat("#0");
		vn.setNumberFormatOverride(df);

		plot.setRangeAxis(vn);
		// 设置图表的横轴（X轴）
		CategoryAxis domainAxis = plot.getDomainAxis();
		this.setXDomainAxis(domainAxis);

		// 设置图表的纵轴（Y轴）
//		ValueAxis valueAxis = plot.getRangeAxis();
		NumberAxis valueAxis = (NumberAxis)plot.getRangeAxis();
		this.setYDomainAxis(valueAxis, max);

		// 设置图表的颜色
		this.setBarRender(plot);
	}

	/**
	 * 描述：在原有图形上增加线性图形，及设置线性图的相关属性
	 * @param chart		柱状图chart对象
	 * @Author：adward_huang
	 * @CreateTime：2012-9-7
	 */
	private void addLineChartDataset(JFreeChart chart, CategoryDataset lineDataset) {

		CategoryPlot localCategoryPlot = (CategoryPlot) chart.getPlot();
		localCategoryPlot.setDataset(1, lineDataset);
		localCategoryPlot.mapDatasetToRangeAxis(1, 1);
		CategoryAxis localCategoryAxis = localCategoryPlot.getDomainAxis();
		// 设置X-AXIS坐标倾斜45度显示
		localCategoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		NumberAxis localNumberAxis = new NumberAxis();
		// 设置最大值是1  
		localNumberAxis.setUpperBound(1);
		// 数据显示格式是百分比  
		DecimalFormat df = new DecimalFormat("0.00%");
		localNumberAxis.setNumberFormatOverride(df); // 数据轴数据标签的显示格式  
		
		localCategoryPlot.setRangeAxis(1, localNumberAxis);
		
		LineAndShapeRenderer localLineAndShapeRenderer = new LineAndShapeRenderer();
		localLineAndShapeRenderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		localCategoryPlot.setRenderer(1, localLineAndShapeRenderer);
		localCategoryPlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

		this.setLineRenderColor(localLineAndShapeRenderer, getLineColors());
	}

	/**
	 * 对X轴的设置
	 * 
	 * @param domainAxis
	 */
	private void setXDomainAxis(CategoryAxis domainAxis) {

		Font labelFont = new Font("宋体", Font.CENTER_BASELINE, 10);
//		domainAxis.setLowerMargin(0.1);// 设置距离图片左端距离此时为10%
//		domainAxis.setUpperMargin(0.1);// 设置距离图片右端距离此时为百分之10

		domainAxis.setLabelFont(labelFont);// X轴标题
		domainAxis.setTickLabelFont(labelFont);// X轴数值

		domainAxis.setCategoryLabelPositionOffset(10);// 图表横轴与标签的距离(10像素)
//		domainAxis.setCategoryMargin(0.1);// 横轴标签之间的距离20%

		domainAxis.setMaximumCategoryLabelLines(1);
		domainAxis.setMaximumCategoryLabelWidthRatio(0);

		domainAxis.setLabelFont(labelFont);// 轴标题
		domainAxis.setTickLabelFont(labelFont);// 轴数值

		domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);// 横轴上的 Lable 是否完整显示
	}

	/**
	  * 对Y轴的设置
	  *
	  * @param ValueAxis
	  */
	private void setYDomainAxis(NumberAxis valueAxis, int max) {
		Font labelFont = new Font("宋体", Font.CENTER_BASELINE, 12);

		valueAxis.setUpperMargin(0.1);// 设置最高的一个柱与图片顶端的距离(最高柱的10%)
		valueAxis.setLowerMargin(0.15);// 设置最低的一个 Item 与图片底端的距离

		valueAxis.setLabelFont(labelFont);// Y轴标题
		valueAxis.setTickLabelFont(labelFont);// Y轴数值
		// Lable水平显示
		valueAxis.setLabelAngle(1.5);
		valueAxis.setLowerBound(0);
		
		double gap = max / CHART_Y_AXIS_GAP;
		if(max <= CHART_Y_AXIS_GAP){
			gap = 1D;
		}
		double max2 = max + 1 * gap;
		valueAxis.setUpperBound(max2);
		valueAxis.setTickUnit(new NumberTickUnit(gap));// 设置纵坐标间隔值大小
	}

	/**
	 * 设置柱状图的颜色(渲染图表) 
	 * 每根柱子的详细设置(三维设置)
	 * 
	 * @param categoryPlot
	 */
	private void setBarRender(CategoryPlot plot) {

		//BarRenderer3D renderer = new BarRenderer3D(); //柱状渲染器  每一类显示为一个柱子
		StackedBarRenderer renderer = new StackedBarRenderer();//柱状渲染器  多类数据在一个柱状图上显示
		renderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_HORIZONTAL));
		renderer.setBaseOutlineStroke(new BasicStroke(0.3F));
		renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());

		// 设置分类柱状图颜色
		setBarRenderColor(renderer, getBarColors());

		renderer.setItemMargin(0.1);// 组内柱子间隔为组宽的10%

		// 显示每个柱的数值，并修改该数值的字体属性
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		// 注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题(3D的才需啊这句，2D的上面那句足矣)OUTSIDE3,9
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3, TextAnchor.BASELINE_CENTER));
		renderer.setBaseItemLabelPaint(Color.BLUE);// 设置数值颜色，默认黑色

		renderer.setIncludeBaseInRange(true);
		renderer.setBaseItemLabelFont((new Font("宋体", Font.CENTER_BASELINE, 10)));// 12号黑体

		renderer.setBaseItemLabelPaint(Color.black);// 字体为黑色
//		renderer.setBaseItemLabelsVisible(true);// 柱状图上是否显示数字

		// 设置柱子宽度
		renderer.setMaximumBarWidth(0.06);
		// 设置柱子高度
		renderer.setMinimumBarLength(0.2);
		
		plot.setRenderer(renderer);
	}

	/**
	 * 描述：设置柱状图分类颜色
	 * @param renderer
	 * @param barColors
	 * @Author：adward_huang
	 * @CreateTime：2012-9-10
	 */
	private void setBarRenderColor(StackedBarRenderer renderer, List<Color> barColors) {
		Paint p1 = new GradientPaint(0.0f, 0.0f, barColors.get(0), 0.0f, 0.0f, new Color(189, 183, 89));
		renderer.setSeriesPaint(0, p1);// 设置柱的颜色

		Paint p2 = new GradientPaint(0.0f, 0.0f, barColors.get(1), 0.0f, 0.0f, new Color(255, 145, 145));
		renderer.setSeriesPaint(1, p2);// 设置柱的颜色

		Paint p3 = new GradientPaint(0.0f, 0.0f, barColors.get(2), 0.0f, 0.0f, new Color(255, 145, 145));
		renderer.setSeriesPaint(2, p3);// 设置柱的颜色

		Paint p4 = new GradientPaint(0.0f, 0.0f, barColors.get(3), 0.0f, 0.0f, new Color(255, 45, 45));
		renderer.setSeriesPaint(3, p4);// 设置柱的颜色

		Paint p5 = new GradientPaint(0.0f, 0.0f, barColors.get(4), 0.0f, 0.0f, new Color(155, 45, 45));
		renderer.setSeriesPaint(4, p5);// 设置柱的颜色
	}

	/**
	 * 描述：设置折线图颜色
	 * @param renderer
	 * @param barColors
	 * @Author：adward_huang
	 * @CreateTime：2012-9-10
	 */
	private void setLineRenderColor(LineAndShapeRenderer renderer, List<Color> lineColors) {

		renderer.setBaseOutlinePaint(Color.red);// 边框颜色
		renderer.setSeriesPaint(0, lineColors.get(0));// 设置柱的颜色

		renderer.setSeriesOutlinePaint(1, Color.BLACK);// 边框颜色
		renderer.setSeriesPaint(1, lineColors.get(1));// 设置柱的颜色

	}

	/**
	 * 描述：获取柱状图颜色集合
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-10
	 */
	private List<Color> getBarColors() {

		List<Color> colorList = new ArrayList<Color>();
		// 柱状图颜色
		colorList.add(CHART_TIMED_FINISHED_COLOR);// 按时完成颜色--绿色
		colorList.add(CHART_15IN_TIME_FINISHED_COLOR);//  延迟15天内完成颜色--黄色
		colorList.add(CHART_15OUTER_TIME_FINISHED_COLOR);// 延迟15天外完成颜色--浅褐色
		colorList.add(CHART_NO_TIME_FINISHED_COLOR);// 未完成颜色--红色
		colorList.add(CHART_UNCOMFIRMED_TIME_FINISHED_COLOR);// 待确认颜色--蓝色

		return colorList;
	}

	/**
	 * 描述：获取折线图颜色集合
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-10
	 */
	private List<Color> getLineColors() {

		List<Color> colorList = new ArrayList<Color>();
		// 折线图颜色
		colorList.add(CHART_TIMED_FINISHED_COLOR_RATE);// 按时完成率--绿色
		colorList.add(CHART_DEFERED_FINISHED_COLOR_RATE);// 延时完成率颜色--淡红色

		return colorList;
	}

	/**
	 * 在窗体中显示生成的图表
	 * @param chart 需要显示的图表
	 * @param frameTitle 窗体标题
	 */
	@SuppressWarnings("unused")
	private void displayChart(JFreeChart chart, String frameTitle) {
		// 将生成的报表放到预览窗口中
		ChartFrame preview = new ChartFrame(frameTitle, chart);
		preview.pack();
		preview.setLocationRelativeTo(null);
		preview.setVisible(true);
	}

	/** 
	 * 饼状图 
	 *  
	 * @param dataset 
	 *            数据集 
	 * @param chartTitle 
	 *            图标题 
	 * @param charName 
	 *            生成图的名字 
	 * @param pieKeys 
	 *            分饼的名字集 
	 * @return 
	 */
	public static JFreeChart createPieChart(PieDataset dataset, String chartTitle, String[] pieKeys) {
		JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, // chart  
				// title  
				dataset,// data  
				true,// include legend  
				true, false);

		chart.setTextAntiAlias(false);
		// 设置图标题的字体重新设置title  
		TextTitle title = new TextTitle(chartTitle);
		title.setFont(CHART_TITLE_FONT);
		title.setPaint(CHART_TITLE_COLOR);
		chart.setTitle(title);
		chart.getLegend().setItemFont(new Font("宋体", Font.CENTER_BASELINE, 10));

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		// 图片中显示百分比:默认方式  

		// 指定饼图轮廓线的颜色  
		// plot.setBaseSectionOutlinePaint(Color.BLACK);  
		// plot.setBaseSectionPaint(Color.BLACK);  

		// 设置无数据时的信息  
		plot.setNoDataMessage("无对应的数据，请重新查询!");

		// 设置无数据时的信息显示颜色  
		plot.setNoDataMessagePaint(Color.red);

		// 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位  
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})", NumberFormat.getNumberInstance(), new DecimalFormat(
				"0.00%")));
		// 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例  
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));

		plot.setLabelFont(new Font("SansSerif", Font.TRUETYPE_FONT, 12));

		// 指定图片的透明度(0.0-1.0)  
		plot.setForegroundAlpha(1f);
		// 指定显示的饼图上圆形(false)还椭圆形(true)  
		plot.setCircular(false, true);

		// 设置第一个 饼块section 的开始位置，默认是12点钟方向  
		plot.setStartAngle(90);

		LegendTitle legend = chart.getLegend(0);
		legend.setItemFont(new Font("宋体", Font.BOLD, 14));

		// // 设置分饼颜色  
		plot.setSectionPaint(pieKeys[0], CHART_TIMED_FINISHED_COLOR);
		plot.setSectionPaint(pieKeys[1], CHART_15IN_TIME_FINISHED_COLOR);
		plot.setSectionPaint(pieKeys[2], CHART_15OUTER_TIME_FINISHED_COLOR);
		plot.setSectionPaint(pieKeys[3], CHART_NO_TIME_FINISHED_COLOR);
		plot.setSectionPaint(pieKeys[4], CHART_UNCOMFIRMED_TIME_FINISHED_COLOR);

		return chart;
	}
	
	/**
	 * 描述：图形横坐标轴如果有相同名字，则过滤为旭辉(1)，旭辉(2)等
	 * @param columnNames
	 * @Author：adward_huang
	 * @CreateTime：2012-9-20
	 */
	@SuppressWarnings("unchecked")
	public static void checkChartColumnDuplicateName(String[] columnNames){
		
		if(columnNames == null){
			return;
		}
		List<String> nameList = new ArrayList<String>();
		if(ArrayUtilities.hasDuplicateItems(columnNames)){
			int len = columnNames.length;
			 for (int i = 0; i < len; i++) {
				for (int j = 0; j < i; j++) {
					final Object o1 = columnNames[i];
					final Object o2 = columnNames[j];
					if (o1 != null && o2 != null) {
						if (o1.equals(o2)) {
							nameList.add(o1.toString());
						}
					}
				}
			}
		}
		List<String> list = Arrays.asList(columnNames);
		
		if(nameList.size() > 0){
			Map<String,Integer> map = new HashMap<String,Integer>();
			
			for(int i = 0; i< list.size();i++){
				if(nameList.contains(list.get(i))){
					String name = columnNames[i];
					Integer index = 1;
					if (map.containsKey(name)) {
						index = map.get(name);
						map.put(name, index+1);
					} else {
						map.put(name, index);
					}
					columnNames[i] = name + "("+(map.get(name))+")";
				}
			}
		}
	}
	
	/**
	 * 描述：将Map类型的数据封装为JSON格式的数据，主要是CustomerParams中不兼容集合类的数据
	 * @param data
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-27
	 */
	@SuppressWarnings("unchecked")
	public static String getMapToString(Map<String, Map<String, String>> data) {
		JSONObject json = new JSONObject();
		if(data != null && !data.isEmpty()){
			Iterator<Map.Entry<String, Map<String, String>>> iter = data.entrySet().iterator();
			Map<String,String> map = null;
			while(iter.hasNext()){
				Map.Entry<String, Map<String, String>> entry = iter.next();
				map = entry.getValue();
				JSONObject json2 = new JSONObject();
				for(Iterator<Map.Entry<String, String>> iter2 = map.entrySet().iterator();iter2.hasNext();){
					Map.Entry<String, String> resultMap = iter2.next();
					String key = resultMap.getKey();
					String value = resultMap.getValue();
					json2.put(key, value);
				}
				json.put(entry.getKey(), json2);
			}
		}
		return json.toString();
	}
	
	/**
	 * 描述：将JSON格式的数据转换为MAP类型数据
	 * @param data
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-27
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, String>> getStringToMap(String data){
		try {
			JSONObject json = (JSONObject) JSONValue.parse(data);
			Map<String, Map<String, String>> maps = (Map<String, Map<String, String>>) json;
			return maps;
		} catch (Exception e) {
			logger.error("查询方案数据获取失败，请删除该方案后再创建！"+e.getMessage());
		}
		return new HashMap<String, Map<String, String>>();
	}
	
	/**
	 * 描述：将图片和表格统一导出到EXCEL
	 * @param panel
	 * @param table
	 * @param title
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @Author：adward_huang
	 * @CreateTime：2012-9-29
	 */
	public static void exportToExcel(JComponent panel, KDTable table, String title) throws RowsExceededException, WriteException, IOException{
		// 获取chart图形
		BufferedImage chartImage = ScheduleChartHelper.getChartIMG(panel);
		
		// 获取表格表头数据
		List<String> headerData = ScheduleChartHelper.getTableHeaderData(table);
		
		// 获取表格数据
		List<List<String>> tableData = ScheduleChartHelper.getTableListData(table);
		
		
		ScheduleChartHelper.exportExcelSave(chartImage,headerData,tableData,title);
	}
	
	/**
	 * 描述：将图片和表格统一导出到EXCEL
	 * @param panel
	 * @param table
	 * @param title
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @Author：adward_huang
	 * @CreateTime：2012-9-29
	 */
	public static void exportToExcel(BufferedImage chartImage, KDTable table, String title) throws RowsExceededException, WriteException, IOException{
		
		// 获取表格表头数据
		List<String> headerData = ScheduleChartHelper.getTableHeaderData(table);
		
		// 获取表格数据
		List<List<String>> tableData = ScheduleChartHelper.getTableListData(table);
		
		
		ScheduleChartHelper.exportExcelSave(chartImage,headerData,tableData,title);
	}
	
	/**
	 * 描述：获取表格数据
	 * @param chartTable
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-28
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getTableHeaderData(KDTable chartTable) {
		List<String> headList = new ArrayList<String>();
		
		List list = chartTable.getHead().getRow(0).getCells();
		for(int i = 1;i<list.size();i++){
			KDTCell cell = (KDTCell)list.get(i);
			headList.add(cell.getValue().toString());
			
		}
		
		return headList;
	}
	
	/**
	 * 描述：获取表格数据
	 * @param chartTable
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-28
	 */
	public static List<List<String>> getTableListData(KDTable chartTable) {
		List<List<String>> tableList = new ArrayList<List<String>>();
		int columnCount = chartTable.getColumnCount();
		int rowCount = chartTable.getRowCount();

		List<String> rowList = null;
		for (int i = 0; i < rowCount; i++) {
			rowList = new ArrayList<String>();
			for (int j = 1; j < columnCount; j++) {
				Object obj = chartTable.getCell(i, j).getValue();
				String value = obj == null ? "" : obj.toString();
				rowList.add(value);
			}
			tableList.add(rowList);
		}
		return tableList;
	}
	
	/**
	 * 描述：导出到EXCEL
	 * @param bufferedImage
	 * @param header
	 * @param data
	 * @param title
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 * @Author：adward_huang
	 * @CreateTime：2012-9-28
	 */
	public static void exportToExcel(BufferedImage bufferedImage, List<String> header, 
			List<List<String>> data,String title, File file) 
					throws IOException, RowsExceededException, WriteException{
		
		WritableWorkbook book = null;
		try {
			book = Workbook.createWorkbook(file);
			WritableSheet sheet = book.createSheet(title, 0);
			
			int width = 0;
			if(header != null && !header.isEmpty()){
				width = header.size();
			}
			
			WritableImage writableImage = new WritableImage(0,0,width,CHART_HEIGHT,getImageFile(bufferedImage));
			Label label = null;
			for(int i = 0;i< width;i++){
				label = new Label(i,CHART_HEIGHT ,header.get(i),getChartTableHeadFormat());
				sheet.addCell(label);
			}
			
			sheet.addImage(writableImage);
			int columnSize = 0;
			if(data != null && !data.isEmpty()){
				int rows = data.size();
				for(int i = 0;i< rows ;i++){
					List<String> rowList = data.get(i);
					if(rowList != null && rowList.size() > columnSize){
						columnSize = rowList.size();
					}
					for(int j =0; j< rowList.size();j++){
						sheet.setColumnView(j, EXPORT_COLUMN_WIDTH); // 设置列的宽度
						label = new Label(j, CHART_HEIGHT + i + 1, rowList.get(j));
						sheet.addCell(label);
					}
				}
			}
			book.write();
		}finally{
			book.close();
		}
		
	}
	
	/**
	 * 描述：设置表头字体及颜色
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-26
	 */
	public static CellFormat getChartTableHeadFormat(){
		//添加带有字体颜色,带背景颜色 Formatting的对象    
		WritableFont font = new WritableFont(WritableFont.ARIAL, 13, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
			format.setBackground(Colour.LIGHT_ORANGE);
			
		} catch (WriteException e) {
			logger.error(e);
		}
		return format;
	}
	
	/**
	* 将图表保存到指定的路径
	* @param filePath 图片文件保存路径
	* @param chart 需要保存的图表
	* @param width 图片的宽度
	* @param height 图片高度
	 * @throws IOException 
	*/
	public static BufferedImage getChartIMG(JFreeChart chart, int width, int height) throws IOException {
		
		String path = getTempFilePath(".png");
		File file = new File(path);
		try {
			ChartUtilities.saveChartAsJPEG(file, chart, width, height);
		} catch (Exception e) {
			logger.error(e);
		}
		return ImageIO.read(file);
	}
	
	/**
	 * 单独返回图表的图像
	 */
	public static BufferedImage getChartIMG(JComponent panel) {
		int height2 = panel.getHeight();
		int width2 = panel.getWidth();
		BufferedImage img = new BufferedImage(width2, height2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		panel.paintComponents(g);
		return img;
	}
	
	/**
	 * 描述：获取图形文件
	 * @param image
	 * @return
	 * @throws IOException 
	 * @throws IOException
	 * @Author：adward_huang
	 * @CreateTime：2012-9-28
	 */
	public static File getImageFile(BufferedImage image) throws IOException {
		File file = new File(getTempFilePath(".png"));
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file));

			EncoderUtil.writeBufferedImage(image, ImageFormat.JPEG, out);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return file;
	}
	
	/** 
     * 序时簿“导出部分到excel”和“导出出选择部分到excel”
     * 的保存处理
     * 
     * @param isBlockOut 是否只导出选择块
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
     */
    public static void exportExcelSave(BufferedImage chartImage, List<String> headerData, List<List<String>> tableData, String title) throws RowsExceededException, WriteException, IOException {

		JFileChooser chooser = new JFileChooser();
		chooser.addChoosableFileFilter(new FileExtensionFilter(".xls", ".xls"));
		int result = chooser.showSaveDialog(null);

		if (result == JFileChooser.CANCEL_OPTION) {
			return;
		}

		File saveFile = null;
		String filePath = null;

		File selectedFile = chooser.getSelectedFile();

		//选择出来的是用户输入的文件名，也许不包含后缀
		String selectedFileName = selectedFile.getName();
		//设置后缀为.xls
		String saveFileName = addExtension(selectedFileName, ".xls");
		if (saveFileName == null) {
			return;
		}
		saveFile = new File(selectedFile.getParentFile(), saveFileName);
		filePath = saveFile.getAbsolutePath();

		//定义多语言处理“”
		String sureCover = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Exists");
		while (saveFile.exists()) {

			int value = JOptionPane.showConfirmDialog(null, filePath + sureCover, "", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE);

			// 点击“是”，替换到原来同名的文件
			if (value == 0) {
				exportToExcel(chartImage, headerData, tableData, title, saveFile);
				return;
			}
			// 点击“否”，重新输入文件名
			else {
				chooser = new JFileChooser();
				chooser.setSelectedFile(saveFile);
				chooser.addChoosableFileFilter(new FileExtensionFilter(".xls", ".xls"));
				result = chooser.showSaveDialog(null);

				if (result == JFileChooser.CANCEL_OPTION) {
					return;
				}
				saveFile = chooser.getSelectedFile();

				saveFileName = addExtension(saveFile.getName(), ".xls");
				saveFile = new File(saveFile.getParentFile(), saveFileName);
				filePath = saveFile.getAbsolutePath();
			}
		}

		exportToExcel(chartImage, headerData, tableData, title, saveFile);
		
	}
    
    /**
	 * 描述：对于选择的节点按组织结构层次大小来排序
	 * @param checkedNodeMap
	 * @Author：adward_huang
	 * @CreateTime：2012-10-9
	 */
	public static List<Map.Entry<String, Map<String, String>>> sortedMapBySelectOrgLevel(Map<String, Map<String, String>> checkedNodeMap){
		
		if(checkedNodeMap == null || checkedNodeMap.isEmpty()){
			return null;
		}
		
		// 将MAP转换为LIST进行排序
		List<Map.Entry<String, Map<String, String>>> mapList = new ArrayList<Map.Entry<String, Map<String, String>>>(checkedNodeMap.entrySet());
		
		//通过比较器实现比较排序 
		Collections.sort(mapList, new Comparator<Map.Entry<String, Map<String, String>>>() {
			public int compare(Map.Entry<String, Map<String, String>> mapping1, Map.Entry<String, Map<String, String>> mapping2) {
				int result = 0;
				try {
					String sortNumber1 = mapping1.getValue().get(FDCScheduleConstant.CHART_SELECT_SORTED_ORG);
					String sortNumber2 = mapping2.getValue().get(FDCScheduleConstant.CHART_SELECT_SORTED_ORG);
					result = getCompareResultBySortNumber(sortNumber1, sortNumber2);
				} catch (Exception e) {
					result = 0;// 此处防止排序编码为空或者出现非数字的时候报异常问题
					logger.error("method: sortedMapBySelectOrgLevel 编码为空或者level为非数字的异常：" + e.getMessage());
				}
				return  result;
			}
		});
		
		return mapList;
	}
	
	 /**
	 * 描述：对于选择的节点按组织结构层次大小来排序
	 * @param checkedNodeMap
	 * @Author：adward_huang
	 * @CreateTime：2012-10-9
	 */
	public static List<Map.Entry<String, DefaultKingdeeTreeNode>> sortedMapByLevel(Map<String, DefaultKingdeeTreeNode> checkedNodeMap){
		
		if(checkedNodeMap == null || checkedNodeMap.isEmpty()){
			return null;
		}
		
		// 将MAP转换为LIST进行排序
		List<Map.Entry<String, DefaultKingdeeTreeNode>> mapList = new ArrayList<Map.Entry<String, DefaultKingdeeTreeNode>>(checkedNodeMap.entrySet());
		
		//通过比较器实现比较排序 
		Collections.sort(mapList, new Comparator<Map.Entry<String, DefaultKingdeeTreeNode>>() {
			public int compare(Map.Entry<String, DefaultKingdeeTreeNode> mapping1, Map.Entry<String, DefaultKingdeeTreeNode> mapping2) {
				int result = 0;
				try {
					Integer level1 = mapping1.getValue().getLevel();
					Integer level2 = mapping2.getValue().getLevel();
					if (level1 != null && level2 != null) {
						result = level1.compareTo(level2);
					}
				} catch (Exception e) {
					result = 0;// 此处防止排序编码为空或者出现非数字的时候报异常问题
					logger.error("method: sortedMapByLevel 编码为空或者level为非数字的异常：" + e.getMessage());
				}
				return result;
			}
		});
		
		return mapList;
	}
	
	/**
	 * 描述：按照长编码对组织结构树进行过滤
	 * @param orgCollection
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-13
	 */
	@SuppressWarnings("unchecked")
	public static void sortedListBySortNumber(List<ScheduleReportOrgInfo> childrenOrgs) {
		
		Collections.sort(childrenOrgs, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				ScheduleReportOrgInfo source = (ScheduleReportOrgInfo) arg0;
				ScheduleReportOrgInfo target = (ScheduleReportOrgInfo) arg1;
				int result = 0;
				if (source.getLongNumber() != null && target.getLongNumber() != null) {
					// 按照用户操作的顺序进行树形节点的加载
					if(source.getLevel() == target.getLevel()){
						try{
						// 如果用String.compareTo做比较会有问题
						result = new Integer(source.getSortNumber()).compareTo(new Integer(target.getSortNumber()));
						}catch(Exception e){
							result = 0;// 此处防止排序编码为空或者出现非数字的时候报异常问题
							logger.error("method: sortedListBySortNumber 编码为空或者sortNumber为非数字的异常："+e.getMessage());
						}
					}
				}
				return result;
			}
		});
	}
	
	/**
	 * 描述：获取节点的排序数字
	 * @param node
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-11-8
	 */
	public static String getSortedNodeNumber(DefaultKingdeeTreeNode node) {

		String sortedNumber = "";
		if (node == null) {
			return sortedNumber;
		}
		ScheduleReportOrgInfo orgInfo = (ScheduleReportOrgInfo) node.getUserObject();
		sortedNumber = orgInfo.getSortNumber() + "";

		if (node.getParent() != null) {
			DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode) node.getParent();
			ScheduleReportOrgInfo parentOrg = (ScheduleReportOrgInfo) parentNode.getUserObject();
			sortedNumber = parentOrg.getSortNumber() + NUMBER_SEPRATOR + sortedNumber;
			while (parentNode.getParent() != null) {
				parentNode = (DefaultKingdeeTreeNode) parentNode.getParent();
				parentOrg = (ScheduleReportOrgInfo) parentNode.getUserObject();
				sortedNumber = parentOrg.getSortNumber() + NUMBER_SEPRATOR + sortedNumber;
			}
		}

		return sortedNumber;
	}
	
	/**
	 * 描述：根据排序编码排序
	 * @param value1
	 * @param value2
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-11-8
	 */
	public static int getCompareResultBySortNumber(String value1,String value2){
		
		if(value1 == null || value2 == null){
			return 0;
		}
		String[] valueA = value1.split(NUMBER_SEPRATOR);
		String[] valueB = value2.split(NUMBER_SEPRATOR);
		if(valueA.length < valueB.length){
			return -1;
		}
		if(valueA.length > valueB.length){
			return 1;
		}
		
		int len = valueA.length;
		int result = 0;
		for (int i = 0; i < len; i++) {
			if (!valueA[i].equals(valueB[i])) {
				try {
					Integer number1 = new Integer(valueA[i]);
					Integer number2 = new Integer(valueB[i]);
					if(number1 > number2){
						return 1;
					}else if(number1 < number2){
						return -1;
					}else{
						return 0;
					}
				} catch (Exception e) {
					result = 0;
					logger.error("method: getCompareResultBySortNumber 排序编码为非数字的异常：" + e.getMessage());
				}
			}
		}
		return result;
	}
	
	/**
	 * 描述：对于List集合进行排序
	 * @param checkedNodeMap
	 * @Author：adward_huang
	 * @CreateTime：2012-10-9
	 */
	@SuppressWarnings("unchecked")
	public static void sortedListByIntegerValue(List<Integer> list){
		
		if(list == null || list.isEmpty()){
			return ;
		}
		
		//通过比较器实现比较排序 
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				int result = 0;
				try {
					Integer level1 = Integer.parseInt(o1.toString());
					Integer level2 = Integer.parseInt(o2.toString());
					if (level1 != null && level2 != null) {
						result = level1.compareTo(level2);
					}
				} catch (Exception e) {
					result = 0;// 此处防止排序编码为空或者出现非数字的时候报异常问题
					logger.error("method: sortedListByIntegerValue value为非数字的异常：" + e.getMessage());
				}
				return result;
			}
			
		});
	}
	
	/**
	 * 描述：获取根节点信息
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @Author：adward_huang
	 * @CreateTime：2012-10-25
	 */
	public static ScheduleReportOrgInfo getRootNodeInfo() throws EASBizException, BOSException{
		String rootSQL = "select id,name,number,longnumber,level,relateOrg.id,sortnumber,remark from where relateOrg.id='"
			+ FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_ID + "'";
		ScheduleReportOrgInfo rootInfo = ScheduleReportOrgFactory.getRemoteInstance().getScheduleReportOrgInfo(rootSQL);
		return rootInfo;
	}
	
	/**
	 * 描述：根据根节点遍历获取所有树节点
	 * @param node
	 * @throws BOSException
	 * @Author：adward_huang
	 * @CreateTime：2012-10-16
	 */
	@SuppressWarnings("unchecked")
	public static List<ScheduleReportOrgInfo> getAllTreeNodeData(ScheduleReportOrgInfo orgInfo) throws BOSException {
		String childrenSQL = "select id, name,number,longnumber,sortnumber,level,remark,parent.id,parent.name,parent.number,parent.longnumber,parent.level,relateOrg.id,relateOrg.name from "
				+ "where longnumber like '" + orgInfo.getLongNumber() + "!%' ";
		ScheduleReportOrgCollection orgColl = ScheduleReportOrgFactory.getRemoteInstance().getScheduleReportOrgCollection(childrenSQL);
		List<ScheduleReportOrgInfo> list = new ArrayList<ScheduleReportOrgInfo>();
		for(Iterator iter = orgColl.iterator();iter.hasNext();){
			list.add((ScheduleReportOrgInfo)iter.next());
		}
		return list;
	}
	
	/**
	 * 描述：将list数据转换为Map数据
	 * @param list
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-25
	 */
	public static Map<String, ScheduleReportOrgInfo> getAllDataMap(List<ScheduleReportOrgInfo> list){
		Map<String, ScheduleReportOrgInfo> dataMap = new HashMap<String, ScheduleReportOrgInfo>();
		if(list == null){
			return dataMap;
		}
		for(ScheduleReportOrgInfo info: list){
			dataMap.put(info.getId().toString(), info);
		}
		return dataMap;
	}
	
	/**
	 * 描述：获取所有子节点信息，一次加载，避免多次远程访问
	 * @param node
	 * @param allTreeNodeData
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-17
	 */
	public static List<ScheduleReportOrgInfo> getChildrenOrgs(ScheduleReportOrgInfo orgInfo, List<ScheduleReportOrgInfo> orderedTreeNodeList) {
		List<ScheduleReportOrgInfo> childrenList = new ArrayList<ScheduleReportOrgInfo>();
		for (ScheduleReportOrgInfo child: orderedTreeNodeList) {
			if (child.getParent() != null && child.getParent().getId().toString().equals(orgInfo.getId().toString())) {
				childrenList.add(child);
			}
		}
		return childrenList;
	}
    
    /**
     * 描述：获取临时文件的路径
     * @return
     * @Author：adward_huang
     * @CreateTime：2012-9-28
     */
    public static String getTempFilePath(String suffix) {
		String tempPath = "";
		try {
			tempPath = File.createTempFile("tmp", suffix).getAbsolutePath();
		} catch (IOException ex) {
			tempPath = Math.random() * Math.random() + "";
		}
		return tempPath;
	}
    
    
    /**
     * 为用户输入或选择的文件加扩展名
     * @param fileName 文件名
     * @param ext	   扩展名	
     * @return	加扩展名后的文件名
     */
    public static String addExtension(String fileName, String ext){
    	
		if(fileName != null ){
			int dotIndex = fileName.indexOf(".");
			if(dotIndex>= 0){
				fileName = fileName.substring(0, dotIndex)+ ext;
			}
			else{
				fileName += ext;
			}
		}		
		
		return fileName;
    }
    
    /**
     * 描述：过滤非字母和数字的字符串
     * @param str
     * @return
     * @throws PatternSyntaxException
     * @Author：adward_huang
     * @CreateTime：2012-10-13
     */
    public static String filtNumberAndLetter(String str) throws PatternSyntaxException {
		// 只允许字母和数字        
		 String regEx= "[^a-zA-Z0-9]";                      
		// 清除掉所有特殊字符   
//		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

    /**
     * 描述：获取报表数据集合
     * @param map
     * @return
     * @Author：adward_huang
     * @CreateTime：2012-10-17
     */
    public static List<String> getReportDataList(Map<String, String> map) {

		String id = map.get(FDCScheduleConstant.REPORT_CHART_ID);
		String projectName = map.get(FDCScheduleConstant.REPORT_CHART_PROJECT_NAME);
		String plannedComp = map.get(FDCScheduleConstant.REPORT_CHART_PLANNED_COMP);
		String timedComp = map.get(FDCScheduleConstant.REPORT_CHART_TIMMED_COMP);
		String fifInComp = map.get(FDCScheduleConstant.REPORT_CHART_FIF_IN_COMP);
		String fifOutComp = map.get(FDCScheduleConstant.REPORT_CHART_FIF_OUT_COMP);
		String unComp = map.get(FDCScheduleConstant.REPORT_CHART_UNCOMP);
		String confirmedComp = map.get(FDCScheduleConstant.REPORT_CHART_CONFIRMED_COMP);
		String timedCompRate = map.get(FDCScheduleConstant.REPORT_CHART_TIMED_COMP_RATE);
		String delayedCompRate = map.get(FDCScheduleConstant.REPORT_CHART_DELAYED_COMP_RATE);

		List<String> dataList = new ArrayList<String>();
		dataList.add(nullStr(id));
		dataList.add(nullStr(projectName));
		dataList.add(nullNumberStr(plannedComp));
		dataList.add(nullNumberStr(timedComp));
		dataList.add(nullNumberStr(fifInComp));
		dataList.add(nullNumberStr(fifOutComp));
		dataList.add(nullNumberStr(unComp));
		dataList.add(nullNumberStr(confirmedComp));
		dataList.add(nullNumberStr(timedCompRate));
		dataList.add(nullNumberStr(delayedCompRate));
		return dataList;
	}
	
	/**
	 * 描述：将空值转换为""
	 * @param str
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-17
	 */
	public static String nullStr(String str){
		if(str == null){
			return "";
		}
		return str;
	}
	
	/**
	 * 描述：将空值转换为0
	 * @param str
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-17
	 */
	public static String nullNumberStr(String str){
		if(str == null){
			return "0";
		}
		return str;
	}
    
    private static class FileExtensionFilter extends FileFilter {

    	protected String ext;
    	protected String description;

    	public FileExtensionFilter(String ext, String description) {
    	   this.ext = ext.toLowerCase();
    	   this.description = description;
    	}

    	public boolean accept(File file) {
    	   if (file.isDirectory()) {
    	    return true;
    	   }

    	   if (file.getName().toLowerCase().endsWith(ext)) {
    	    return true;
    	   }

    	   return false;
    	}
    	public String getDescription() {
    		   return description;
    		}
    }
}
