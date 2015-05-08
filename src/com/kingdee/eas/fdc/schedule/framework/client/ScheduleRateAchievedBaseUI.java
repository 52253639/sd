/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework.client;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.data.general.Dataset;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ui.face.CoreUIObject;

/**
 * 达成率分析报表基类（柱状图）
 * <p>
 * 子类主要实现初始化树(initTree)、初始化数据源(createDataset)、初始化图表(initChart、createChart)
 * 以及初始化表格(initTable)功能。<br>
 * 
 * @author emanon
 * 
 */
public class ScheduleRateAchievedBaseUI extends
		AbstractScheduleRateAchievedBaseUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ScheduleRateAchievedBaseUI.class);

	/**
	 * output class constructor
	 */
	public ScheduleRateAchievedBaseUI() throws Exception {
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
		tblMain.checkParsed();
		initUI();
	}

	/**
	 * 树选择改变，重新填充图表以及表格
	 */
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		initChart();
		fillChart();
		initTable();
	}

	/**
	 * 年份选择改变，重新填充图表以及表格
	 */
	protected void cbYear_itemStateChanged(ItemEvent e) throws Exception {
		fillChart();
		initTable();
	}

	/**
	 * 初始化界面<br>
	 * 分为几步：<br>
	 * 1、设置树面板标题，初始化树<br>
	 * 2、初始化并填充表头<br>
	 * 3、设置图像面板标题，初始化年份<br>
	 * 4、填充图表<br>
	 * 5、初始化并填充表格
	 * 
	 * @throws Exception
	 */
	protected void initUI() throws Exception {
		initTree();
		initHead();
		initChart();
		fillChart();
		initTable();
	}

	/**
	 * 初始化树<br>
	 * 可以构建一个成本中心或者工程项目树
	 */
	protected void initTree() throws Exception {

	}

	/**
	 * 初始化并填充表头<br>
	 */
	protected void initHead() throws Exception {

	}

	/**
	 * 初始化图形面板<br>
	 * 包括设置年份下拉框值，设置分隔容器上区域高度等<br>
	 * 注意填充图表数据不在此完成
	 */
	protected void initChart() {

	}

	/**
	 * 填充图表<br>
	 * 一般不需重载此方法，只需重载createChartPanel()和createDataset()即可
	 */
	protected void fillChart() {
		pnlChart.add(createChartPanel(), BorderLayout.CENTER);
	}

	/**
	 * 初始化表格<br>
	 * 包括根据年份初始化列，添加当月完成情况、累计完成情况2行等<br>
	 */
	protected void initTable() {

	}

	/**
	 * 创建一个图表面板<br>
	 * 
	 * @return
	 */
	protected JPanel createChartPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}

	/**
	 * 构建图表所需的数据源
	 * 
	 * @return
	 */
	protected Dataset createDataset() {
		return null;
	}

	/**
	 * 返回一个图表
	 * 
	 * @param dataset
	 * @return
	 */
	protected JFreeChart createChart(Dataset dataset) {
		return null;
	}

	/**
	 * 返回一张图片(重写父类)
	 * <p>
	 * 导出图片思路：<br>
	 * 1、将图表面板导出为img1<br>
	 * 2、将表格设置到合适的高宽，清除选择<br>
	 * 3、将表格导出为img2<br>
	 * 4、新建img，将img1等比缩放成宽度与img2一样，并画在img的上方<br>
	 * 5、将img2画在img的下方<br>
	 * 6、将表格高宽设回原样，并选择之前所选择的行<br>
	 * 7、返回img
	 */
	protected BufferedImage getUIIMG() {
		// 1、将图表面板导出为img1
		BufferedImage img1 = getChartIMG();
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();
		// BufferedImage img1 = new BufferedImage(w1, h1,
		// BufferedImage.TYPE_INT_RGB);
		// Graphics g = img1.getGraphics();
		// pnlChart.paintAll(g);
		// g.dispose();

		// 2、将表格设置到合适的高宽，清除选择
		// 保存现在高宽选择
		// int curDivLeft = sptLeft.getDividerLocation();
		// int curDivTop = sptTop.getDividerLocation();
		KDTSelectBlock curSelect = tblMain.getSelectManager().get();
		// 计算表格最合适宽度(2个像素的边界+序号列宽+所有列宽和)
		int fitWidth = 2 + tblMain.getIndexColumn().getWidth()
				+ tblMain.getColumns().getWidth();
		// sptLeft
		// .setDividerLocation(curDivLeft
		// + (tblMain.getWidth() - fitWidth));
		// 计算表格最合适高度(2个像素的边界+表头高+所有行高和)
		int fitHeight = 2 + tblMain.getHead().getHeight()
				+ tblMain.getBody().getHeight();
		// sptTop
		// .setDividerLocation(curDivTop
		// + (tblMain.getHeight() - fitHeight));
		// 清除选择
		tblMain.getSelectManager().remove();
		// 此句不可去掉，用于在生成图片前设置表格合适大小
		tblMain.setSize(fitWidth, fitHeight);

		// 3、将表格导出为img2
		int w2 = tblMain.getWidth();
		int h2 = tblMain.getHeight();
		BufferedImage img2 = new BufferedImage(w2, h2,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img2.getGraphics();
		tblMain.paintAll(g);
		g.dispose();

		// 4、新建img，将img1等比缩放成宽度与img2一样，并画在img的上方
		float scale = (float) w2 / w1;
		w1 = w2;
		h1 = (int) (h1 * scale);
		BufferedImage img = new BufferedImage(w1, h1 + h2,
				BufferedImage.TYPE_INT_RGB);
		Image scaleImage = img1.getScaledInstance(w1, h1,
				Image.SCALE_AREA_AVERAGING);
		g = img.createGraphics();
		g.drawImage(scaleImage, 0, 0, w1, h1, null);

		// 5、将img2画在img的下方
		g.drawImage(img2, 0, h1, w2, h2, null);
		g.dispose();

		// 6、将表格高宽设回原样，并选择之前所选择的行
		// sptLeft.setDividerLocation(curDivLeft);
		// sptTop.setDividerLocation(curDivTop);
		if (curSelect != null) {
			tblMain.getSelectManager().select(curSelect);
		}

		// 7、返回img
		return img;
	}

}