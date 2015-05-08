/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.log4j.Logger;
import org.json.JSONException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.chart.encoders.SunJPEGEncoderAdapter;
import com.kingdee.bos.ctrl.freechart.chart.plot.CategoryPlot;
import com.kingdee.bos.ctrl.freechart.util.PngEncoder;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.TreeExpansionAdapter;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.cp.common.json.simple.JSONObject;
import com.kingdee.eas.cp.common.json.simple.JSONValue;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.REAutoRememberFactory;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.basedata.util.FileNameExtensionFilter;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.ScheduleChartHelper;
import com.kingdee.eas.fdc.schedule.ScheduleReportSearchCondition;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportFacadeFactory;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述:进度报表左侧为树界面的基类
 * 
 * @author adward_huang date:2012-10-17
 * @version EAS6.1
 */
public class ScheduleReportTreeBaseUI extends AbstractScheduleReportTreeBaseUI {

	private static final long serialVersionUID = 2144980347280745318L;

	private static final Logger logger = CoreUIObject
			.getLogger(ScheduleReportTreeBaseUI.class);

	// 报表图形对象
	protected JFreeChart chart = null;

	// 显示数字按钮
	private KDWorkButton isShowChartNumberBtn;

	// 开始日期
	private Date startDate;

	// 结束日期
	private Date endDate;

	// 图形标题
	private String chartTitle = "";

	// 选中的工程项目名称
	private String projectID;

	private static final int MULTI_SCALE = 2;

	// 缓存工程项目数据
	private Map<String, Map<String, String>> dataMap = null;

	/**
	 * output class constructor
	 */
	public ScheduleReportTreeBaseUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();

		this.chartTable.setEditable(false);
		this.chartTable.setEnabled(false);
        initTableStyle();
		initUIState();
		initPanel();
	}

	private void initTableStyle() {
		chartTable.checkParsed();
		String[] colNames = new String[] { "plannedComp", "timedComp", "fifInComp", "fifOutComp", "unComp", "confirmComp" };
		for (String col : colNames) {
			setTableStyle(col);
		}
	}

	private void setTableStyle(String columnName) {
		if (chartTable.getColumn(columnName) != null) {
			StyleAttributes style = chartTable.getColumn(columnName).getStyleAttributes();
			style.setUnderline(true);
			style.setFontColor(Color.BLUE);
			style.setHorizontalAlign(HorizontalAlignment.CENTER);
		}

	}

	/**
	 * 描述：初始化面板
	 * 
	 * @throws Exception
	 * @Author：adward_huang
	 * @CreateTime：2012-10-17
	 */
	private void initPanel() throws Exception {

		buildLeftTreePanel();
		buildRightPanel();
	}

	/**
	 * 描述：初始化右侧面板
	 * 
	 * @Author：adward_huang
	 * @CreateTime：2012-10-17
	 */
	protected void buildRightPanel() {
		// 构造表格和图形界面
		buildTableAndChartPanel();
	}

	/**
	 * 描述：初始化左边树面板
	 * 
	 * @throws Exception
	 * @Author：adward_huang
	 * @CreateTime：2012-9-21
	 */
	protected void buildLeftTreePanel() throws Exception {
		initProjectTree();
	}

	/**
	 * 描述：初始化表格和图形界面
	 * 
	 * @Author：adward_huang
	 * @CreateTime：2012-9-12
	 */
	protected void buildTableAndChartPanel() {

		// // 开始日期和结束日期
		// Date startDate = getSelectedStartDate();
		// Date endDate = getSelectedEndDate();
		// String projectID = getProjectID();
		//
		// // 过滤后的进度项目完成信息
		// Map<String, Map<String, String>> dataMap =
		// getScheduleExpertReportData(startDate,endDate,projectID);
		// this.setDataMap(dataMap);
		// initReportPanel(dataMap);
	}

	/**
	 * 描述：获取过滤后的进度项目完成情况，子类可以自行实现获取图形数据和表格数据的接口
	 * 
	 * @param startDate
	 *            由查询界面传入的完成日期开始日期
	 * @param endDate
	 *            由查询界面传入的完成日期结束日期
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-11
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Map<String, String>> getScheduleExpertReportData(
			Date startDate, Date endDate, String projectID) {

		Map<String, Map<String, String>> dataMap = null;
		Map<String, Date> dates = new HashMap<String, Date>();
		dates.put(FDCScheduleConstant.REPORT_CHART_START_DATE, startDate);
		dates.put(FDCScheduleConstant.REPORT_CHART_END_DATE, endDate);
		try {
			dataMap = ScheduleReportFacadeFactory.getRemoteInstance()
					.getScheduleExpertReportData(dates, projectID);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}

		return dataMap;
	}

	/**
	 * 描述：初始化报表界面，包含图形界面和表格界面
	 * 
	 * @param dataMap
	 *            项目进度信息
	 * @Author：adward_huang
	 * @CreateTime：2012-9-13
	 */
	protected void initReportPanel(Map<String, Map<String, String>> dataMap) {

		// 默认是5个统计柱状图
		int dataBarColumnSize = ScheduleChartHelper.CHART_CATEGORY_ROW_LIST.length;

		// 默认两条统计线性图
		int dataLineColumnSize = ScheduleChartHelper.CHART_CATEGORY_RATE_LIST.length;

		// 项目数大小
		int dataPrjSize = 0;
		if (dataMap != null && !dataMap.isEmpty()) {
			dataPrjSize = dataMap.size();
		}

		// 存放图标数据，按类存放
		// 柱状图数据
		double[][] barData = new double[dataBarColumnSize][dataPrjSize];

		// 线性图数据
		double[][] lineData = new double[dataLineColumnSize][dataPrjSize];

		// 存放项目名称
		String[] columnKeys = new String[dataPrjSize];

		// 表格行数从第二行开始
		int index = 1;

		if (dataMap != null && !dataMap.isEmpty()) {
			this.chartTable.removeRows();

			// 在表格插入第一行（整期统计数据）
			List<String> expertDataList = insertFirstRowInTable(dataMap,
					dataBarColumnSize, dataLineColumnSize, barData, lineData,
					columnKeys);

			for (Map.Entry<String, Map<String, String>> entry : dataMap
					.entrySet()) {

				if (FDCScheduleConstant.REPORT_CHART_SCHEDULE_ALL_EXPERT_PLAN_ID
						.equals(entry.getKey())) {
					continue;
				}
				Map<String, String> map = entry.getValue();
				// 将Map中数据按序组装成List，方便存取
				List<String> dataList = ScheduleChartHelper
						.getReportDataList(map);
				for (int i = 0; i < dataBarColumnSize; i++) {
					barData[i][index] = Double.parseDouble(dataList.get(i + 3));
				}
				for (int i = 0; i < dataLineColumnSize; i++) {
					lineData[i][index] = new BigDecimal(dataList.get(i + 8))
							.doubleValue();
				}
				columnKeys[index] = map
						.get(FDCScheduleConstant.REPORT_CHART_PROJECT_NAME);

				index++;

				// 初始化表格
				IRow row = this.chartTable.addRow();
				initTableData(dataList, row);
			}

			// 对X-AXIS名称存在相同的情况下进行过滤
			ScheduleChartHelper.checkChartColumnDuplicateName(columnKeys);
			int max = 0;
			if (expertDataList != null && !expertDataList.isEmpty()) {
				try {
					max = Integer.parseInt(expertDataList.get(2));
				} catch (Exception e) {
					logger.error(e);
					max = 0;
				}
			}
			// 初始化图形界面
			initChartPanelData(columnKeys, barData, lineData, max);

		} else {
			this.chartTable.removeRows();
			this.chartPanel.removeAll();
			this.chartPanel.repaint();
		}

	}

	private List<String> insertFirstRowInTable(
			Map<String, Map<String, String>> dataMap, int dataBarColumnSize,
			int dataLineColumnSize, double[][] barData, double[][] lineData,
			String[] columnKeys) {
		Map<String, String> allExpertPlanData = dataMap
				.get(FDCScheduleConstant.REPORT_CHART_SCHEDULE_ALL_EXPERT_PLAN_ID);
		// 将Map中数据按序组装成List，方便存取
		List<String> expertDataList = ScheduleChartHelper
				.getReportDataList(allExpertPlanData);

		for (int i = 0; i < dataBarColumnSize; i++) {
			barData[i][0] = Double.parseDouble(expertDataList.get(i + 3));
		}
		for (int i = 0; i < dataLineColumnSize; i++) {
			lineData[i][0] = new BigDecimal(expertDataList.get(i + 8))
					.doubleValue();
		}
		columnKeys[0] = allExpertPlanData
				.get(FDCScheduleConstant.REPORT_CHART_PROJECT_NAME);

		// 添加专项的第一行整期统计数据
		IRow row1 = this.chartTable.addRow();
		initTableData(expertDataList, row1);
		return expertDataList;
	}

	/**
	 * 描述：初始化表格
	 * 
	 * @param dataMap
	 *            项目进度信息
	 * @Author：adward_huang
	 * @CreateTime：2012-9-12
	 */
	private void initTableData(List<String> dataList, IRow row) {
		this.chartTable.checkParsed();
		for (int i = 0; i < dataList.size(); i++) {
			if (i == 8 || i == 9) {
				String rate = new BigDecimal(dataList.get(i)).multiply(
						new BigDecimal(100)).setScale(MULTI_SCALE)
						+ "%";
				row.getCell(i).setValue(rate);
			} else {
				row.getCell(i).setValue(dataList.get(i));
			}
		}

	}

	/**
	 * 描述：初始化图形界面，供子类实现，子类可扩展图形类
	 * 
	 * @param dataMap
	 *            项目进度信息
	 * @Author：adward_huang
	 * @CreateTime：2012-9-12
	 */
	protected void initChartPanelData(String[] columnKeys, double[][] barData,
			double[][] lineData, int maxValue) {

	}

	/**
	 * 描述：初始化右下位置的表格面板
	 * 
	 * @Author：adward_huang
	 * @CreateTime：2012-9-21
	 */
	protected void buildRightBottomTablePanel() {

	}

	/**
	 * 描述： 初始化工程树
	 * 
	 * @throws Exception
	 * @Author：adward_huang
	 * @CreateTime：2012-9-17
	 */
	private void initProjectTree() throws Exception {

		TreeSelectionModel sm = new DefaultTreeSelectionModel();
		sm.setSelectionMode(2);
		this.orgTree.setSelectionModel(sm);

		this.orgTree.addTreeExpansionListener(new TreeExpansionAdapter() {
			public void treeCollapsed(TreeExpansionEvent event) {
				if (!(event.getPath().getLastPathComponent().equals(orgTree
						.getModel().getRoot())))
					return;
				orgTree.expandPath(new TreePath(orgTree.getModel().getRoot()));
			}
		});

		buildProjectTree();

	}

	/**
	 * private方法修改为public方法，子类可以修改工程项目树的构建方式
	 * 原因：进度管理中工程项目的构建取当前组织上级财务组织下的所有工程项目，默认取的当前组织下的工程项目
	 * 
	 * @throws Exception
	 */
	public void buildProjectTree() throws Exception {

		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

		projectTreeBuilder.build(this, orgTree, actionOnLoad);

		this.orgTree.setShowCheckBox(false);
		this.treeViewer.setShowControlPanel(false);
		this.orgTree.expandRow(0);

	}

	/**
	 * 描述：获取选择树节点ID
	 * 
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-24
	 */
	protected String[] getSelectTreeNodeId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) orgTree
				.getLastSelectedPathComponent();
		String selectID = null;
		String[] value = new String[2];
		if (node != null && node.isLeaf()
				&& node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo project = (CurProjectInfo) node.getUserObject();
			if (project != null && project.getId() != null) {
				selectID = project.getId().toString();
				String projectName = project.getName();
				value[0] = selectID;
				value[1] = projectName;
			}
		}
		return value;
	}

	protected void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	/**
	 * 描述：提供对外的接口，传入的图形的标题
	 * 
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-12
	 */
	protected String getChartTitle() {
		return chartTitle;
	}

	/**
	 * 描述：提供对外的接口，传入的完成日期范围开始日期
	 * 
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-12
	 */
	protected Date getSelectedStartDate() {
		return startDate;
	}

	private void setSelectedStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 描述：提供对外的接口，传入的完成日期范围结束日期
	 * 
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-12
	 */
	protected Date getSelectedEndDate() {
		return endDate;
	}

	private void setSelectedEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getProjectID() {
		return projectID;
	}

	private void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	protected void setSelectorAllValue(String projectID, Date startDate,
			Date endDate, String chartTitle, String scheduleChartType) {

		if (projectID != null) {
			this.setProjectID(projectID);
		}

		if (startDate != null) {
			this.setSelectedStartDate(startDate);
		}
		if (endDate != null) {
			this.setSelectedEndDate(endDate);
		}

		if (chartTitle != null) {
			this.setChartTitle(chartTitle);
		}
	}

	/**
	 * 打印
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {

		List<List<String>> tableData = ScheduleChartHelper
				.getTableListData(chartTable);
		if (tableData == null || tableData.isEmpty()) {
			MsgBox.showWarning("没有生成图形数据，请保证生成数据后再打印！");
			return;
		}
		ScheduleReportTemplateProvider dataProvider = new ScheduleReportTemplateProvider(
				tableData, getChartIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDPath(), dataProvider, SwingUtilities
				.getWindowAncestor(this));
	}

	/**
	 * 打印预览
	 */
	public void actionPrePrint_actionPerformed(ActionEvent e) throws Exception {

		List<List<String>> tableData = ScheduleChartHelper
				.getTableListData(chartTable);

		if (tableData == null || tableData.isEmpty()) {
			MsgBox.showWarning("没有生成图形数据，请保证生成数据后再打印预览！");
			return;
		}

		ScheduleReportTemplateProvider dataProvider = new ScheduleReportTemplateProvider(
				tableData, getChartIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDPath(), dataProvider, SwingUtilities
				.getWindowAncestor(this));
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportTreeBaseUI#actionExportToExcel_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionExportToExcel_actionPerformed(ActionEvent e)
			throws Exception {

		List<List<String>> tableData = ScheduleChartHelper
				.getTableListData(chartTable);

		if (tableData == null || tableData.isEmpty()) {
			MsgBox.showWarning("没有生成图形数据，请保证生成数据后再导出到EXCEL！");
			return;
		}

		try {
			// BufferedImage image =
			// ScheduleChartHelper.getChartIMG(chart,(int)chartDimension
			// .getWidth(),(int)chartDimension.getHeight());
			// ScheduleChartHelper.exportToExcel(image, chartTable,
			// getChartTitle());
			ScheduleChartHelper.exportToExcel(chartScrollPanel, chartTable,
					getChartTitle());
		} catch (Exception ex) {
			MsgBox.showWarning(this, "导出EXCEL失败，请关闭所选择导出的EXCEL文件再重试！");
		}
	}

	/**
	 * 导出图片至文件
	 * <p>
	 * 支持导出为jpg或者png<br>
	 * 当输入的文件名未包含后缀时，取选择的后缀<br>
	 * 
	 * 一般不需重写此方法，重写getUIIMG()返回图片即可
	 */
	public void actionExportIMG_actionPerformed(ActionEvent e) throws Exception {
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(KDFileChooser.FILES_ONLY);
		// 单选
		fc.setMultiSelectionEnabled(false);
		// 2种格式
		fc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("jpg",
				new String[] { "jpg" });
		FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("png",
				new String[] { "png" });
		fc.setFileFilter(jpgFilter);
		fc.setFileFilter(pngFilter);
		int retVal = fc.showSaveDialog(this);
		if (retVal == KDFileChooser.CANCEL_OPTION) {
			return;
		}
		File chooseFile = fc.getSelectedFile();
		// 得到图片
		BufferedImage img = getUIIMG();
		if (img == null) {
			return;
		}
		String imgName = chooseFile.getName().trim().toLowerCase();
		String newFile = chooseFile.getParent() + "\\" + imgName;
		// 判断选择了jpg或者png
		if (fc.getFileFilter().equals(jpgFilter)) {
			if (!imgName.endsWith("jpg")) {
				newFile += ".jpg";
			}
			File saveIMG = new File(newFile);
			// 写入jpg
			FileOutputStream fos = new FileOutputStream(saveIMG);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			new SunJPEGEncoderAdapter().encode(img, fos);
			bos.close();
		} else {
			if (!imgName.endsWith("png")) {
				newFile += ".png";
			}
			File saveIMG = new File(newFile);
			// 写入png
			FileOutputStream fos = new FileOutputStream(saveIMG);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			byte[] pngEncode = new PngEncoder(img).pngEncode();
			bos.write(pngEncode);
			bos.close();
		}
	}

	/**
	 * 返回套打标题
	 * <p>
	 * 子类需重载
	 * 
	 * @return
	 */
	protected String getTDName() {
		return "专项计划达成率";
	}

	/**
	 * 返回套打模板路径
	 * <p>
	 * 子类需重载
	 * 
	 * @return
	 */
	protected String getTDPath() {
		return "/bim/fdc/process/FDCSchedule";
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
		int height2 = kDSplitPane2.getHeight();
		int width2 = kDSplitPane2.getWidth();
		BufferedImage img = new BufferedImage(width2, height2,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		kDSplitPane2.paintComponents(g);
		return img;
	}

	/**
	 * 单独返回图表的图像
	 * <p>
	 * 有时候套打只需要打印图表，而下面的表格使用另外的数据源打印<br>
	 * 此时套打取图片使用此方法，而不用getUIIMG()
	 */
	protected BufferedImage getChartIMG() {
		int height2 = chartScrollPanel.getHeight();
		int width2 = chartScrollPanel.getWidth();
		BufferedImage img = new BufferedImage(width2, height2,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		chartScrollPanel.paintComponents(g);
		return img;
	}

	/**
	 * 树选择改变
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {

	}

	/**
	 * 设置界面状态
	 */
	protected void initUIState() {
		print.setIcon(EASResource.getIcon("imgTbtn_print"));
		prePrint.setIcon(EASResource.getIcon("imgTbtn_preview"));
		exportToExcel.setIcon(EASResource.getIcon("imgTbtn_chart"));

		actionPrint.setEnabled(true);
		actionPrePrint.setEnabled(true);
		actionExportToExcel.setEnabled(true);

		isShowChartNumberBtn = new KDWorkButton("显示数字");
		this.isShowChartNumberBtn.setIcon(EASResource.getIcon("imgHotspot_1"));
		isShowChartNumberBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 显示数字操作
				showChartNumber();
			}
		});

		chartContainer.addButton(isShowChartNumberBtn);
	}

	/**
	 * 图表的高度<br>
	 * 一般是尽量高一点，使用当前页面高度减去下表格高度<br>
	 * 在onshow中调用，因为onload时，页面高度还没有确定，只是默认高度
	 * 
	 * @return
	 */
	protected int getChartHeight() {
		return getHeight() - 150;
	}

	protected Map<String, Map<String, String>> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Map<String, String>> dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * 描述：判断两个日期是否相等
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-24
	 */
	public boolean isDateEquals(Date date1, Date date2) {
		boolean flag = false;

		SimpleDateFormat format = new SimpleDateFormat("yyyymmdd");
		String dateStr1 = format.format(date1);
		String dateStr2 = format.format(date2);
		if (dateStr1.equals(dateStr2)) {
			flag = true;
		}
		return flag;
	}

	protected void removeAllMouseListeners(JComponent panel) {
		// 移除图形界面的鼠标事件
		EventListener[] listeners = panel.getListeners(MouseListener.class);
		for (EventListener listener : listeners) {

			panel.removeMouseListener((MouseListener) listener);
		}
	}

	/**
	 * 描述：显示柱状图的数字
	 * 
	 * @Author：adward_huang
	 * @CreateTime：2012-10-24
	 */
	private void showChartNumber() {
		if (chart != null) {
			CategoryPlot plot = chart.getCategoryPlot();
			if (isShowChartNumberBtn.getText().equals("显示数字")) {
				plot.getRenderer().setBaseItemLabelsVisible(true);
				isShowChartNumberBtn.setText("隐藏数字");
				isShowChartNumberBtn.setIcon(EASResource
						.getIcon("imgHotspot_2"));
			} else {
				plot.getRenderer().setBaseItemLabelsVisible(false);
				isShowChartNumberBtn.setText("显示数字");
				isShowChartNumberBtn.setIcon(EASResource
						.getIcon("imgHotspot_1"));
			}
		}
	}

	/**
	 * 描述：保存搜索条件
	 * 
	 * @param projectId
	 * @param startDate
	 * @param endDate
	 * @param dataMap
	 * @throws JSONException
	 * @throws BOSException
	 * @Author：adward_huang
	 * @CreateTime：2012-9-25
	 */
	@SuppressWarnings("unchecked")
	public void saveSearchCondition(String projectId, Date startDate,
			Date endDate, Map<String, Map<String, String>> dataMap) {
		if (projectId == null) {
			return;
		}
		String startDateStr = getDateToString(startDate);
		String endDateStr = getDateToString(endDate);
		String userId = SysContext.getSysContext().getCurrentUserInfo().getId()
				.toString();

		JSONObject json = new JSONObject();
		try {
			json.put("userID", userId);
			json.put("projectID", projectId);
			json.put("startDate", startDateStr);
			json.put("endDate", endDateStr);
			// json.put("data", dataMap);

			String functionId = userId + "_" + projectId;
			String value = json.toString();
			REAutoRememberFactory.getRemoteInstance().save(userId, projectId,
					functionId, value);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}

	/**
	 * 描述：获取搜索条件
	 * 
	 * @param projectId
	 * @return
	 * @throws BOSException
	 * @Author：adward_huang
	 * @CreateTime：2012-9-25
	 */
	public String getSearchCondition(String projectId) throws BOSException {
		String userId = SysContext.getSysContext().getCurrentUserInfo().getId()
				.toString();
		String value = REAutoRememberFactory.getRemoteInstance().getValue(
				userId, projectId, userId + "_" + projectId);
		return value;
	}

	/**
	 * 描述：解析JSON格式数据，封装成搜索对象
	 * 
	 * @param value
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-25
	 */
	public ScheduleReportSearchCondition getSearchConditionObject(String value) {

		if (value == null) {
			return null;
		}
		JSONObject json = (JSONObject) JSONValue.parse(value);
		String userId = json.get("userID").toString();

		String projectId = json.get("projectID").toString();
		Object startObj = json.get("startDate");
		Object endObj = json.get("endDate");
		String startDate = startObj == null ? null : startObj.toString();
		String endDate = endObj == null ? null : endObj.toString();
		// Map<String, Map<String, String>> dataMap = (Map<String, Map<String,
		// String>>) json.get("data");
		ScheduleReportSearchCondition condition = new ScheduleReportSearchCondition();
		condition.setUserId(userId);
		condition.setProjectId(projectId);
		condition.setStartDate(startDate);
		condition.setEndDate(endDate);
		// condition.setDataMap(dataMap);
		return condition;
	}

	private String getDateToString(Date date) {
		if (date == null) {
			return null;
		}
		String longDate = date.getTime() + "";
		return longDate;
	}

	public Date getStringToDate(String dateStr) {
		if (dateStr == null) {
			return null;
		}
		Date date = new Date(Long.parseLong(dateStr));
		return date;
	}

}