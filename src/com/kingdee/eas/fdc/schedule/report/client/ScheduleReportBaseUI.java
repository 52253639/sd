/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.chart.plot.CategoryPlot;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.ScheduleChartHelper;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述:进度报表基类界面
 * @author adward_huang  date:2012-10-17
 * @version EAS6.1
 */
public class ScheduleReportBaseUI extends AbstractScheduleReportBaseUI {

	private static final long serialVersionUID = 827340447499844046L;

	private static final Logger logger = CoreUIObject.getLogger(ScheduleReportBaseUI.class);

	private CommonQueryDialog commonQueryDialog = null;
	
	// 显示数字按钮
	private KDWorkButton isShowChartNumberBtn;

	// 嵌套在查询窗体中的自定义查询界面
	private ScheduleReportQueryPanelUI filterUI = null;
	
	// 报表图形对象
	private JFreeChart chart = null;
	
	// 报表图形大小
	private Dimension chartDimension = null;

	//	private static final int DIVIDE_SCALE = 4;

	private static final int MULTI_SCALE = 2;

	// 选择的组织或工程
	private Map<String, Map<String, String>> selectAllOrgsMap;

	// 开始日期
	private Date startDate;

	// 结束日期
	private Date endDate;

	// 图形标题
	private String chartTitle = "";

	// 图形类型
	private String scheduleChartType;
	
	// 显示的组织或工程项目名称
	private String projectName;
	
	

	/**
	 * output class constructor
	 */
	public ScheduleReportBaseUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		initTableStyle();
		
		initPanelState();
		// 构造表格和图形界面
		buildTableAndChartPanel();

		actionQuery.setVisible(true);
		actionQuery.setEnabled(true);
	}
	
	private void initPanelState(){
		this.chartTable.setEditable(false);
		this.chartTable.setEnabled(false);
		this.exportToExcel.setIcon(EASResource.getIcon("imgTbtn_output"));
		
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
	 * 描述：初始化表格和图形界面
	 * @Author：adward_huang
	 * @CreateTime：2012-9-12
	 */
	public void buildTableAndChartPanel() {

		// 获取所选组织
		Map<String, Map<String, String>> allOrgsMap = getSelectedAllOrgsId();

		// 开始日期和结束日期
		Date startDate = getSelectedStartDate();
		Date endDate = getSelectedEndDate();
		String type = getScheduleType();

		// 过滤后的进度项目完成信息
		Map<String, Map<String, String>> dataMap = getScheduleReportData(allOrgsMap, startDate, endDate, type);

		initReportPanel(dataMap);
	}

	/**
	 * 描述：初始化报表界面，包含图形界面和表格界面
	 * @param dataMap 项目进度信息
	 * @Author：adward_huang
	 * @CreateTime：2012-9-13
	 */
	private void initReportPanel(Map<String, Map<String, String>> dataMap) {

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

		int index = 0;

		if (dataMap != null && !dataMap.isEmpty()) {
			this.chartTable.removeRows();
			// 对选中节点按组织层次级别排序
			List<Map.Entry<String, Map<String, String>>> sortedMapValueList = ScheduleChartHelper.sortedMapBySelectOrgLevel(dataMap);

			List<Integer> plannedResult = new ArrayList<Integer>();
			for (Map.Entry<String, Map<String, String>> entry : sortedMapValueList) {
				Map<String, String> map = entry.getValue();

				// 将Map中数据按序组装成List，方便存取
				List<String> dataList = ScheduleChartHelper.getReportDataList(map);
				plannedResult.add(Integer.parseInt(dataList.get(2)));
				for (int i = 0; i < dataBarColumnSize; i++) {
					barData[i][index] = Double.parseDouble(dataList.get(i + 3));
				}
				for (int i = 0; i < dataLineColumnSize; i++) {
					lineData[i][index] = new BigDecimal(dataList.get(i + 8)).doubleValue();
				}
				columnKeys[index] = map.get(FDCScheduleConstant.REPORT_CHART_PROJECT_NAME);

				index++;

				// 初始化表格
				IRow row = this.chartTable.addRow();
				initTableData(dataList, row);
			}

			// 对X-AXIS名称存在相同的情况下进行过滤
			ScheduleChartHelper.checkChartColumnDuplicateName(columnKeys);
			ScheduleChartHelper.sortedListByIntegerValue(plannedResult);
			
			int size = plannedResult.size() > 0 ? plannedResult.size() - 1: 0;
			// 初始化图形界面
			initChartPanelData(columnKeys, barData, lineData,plannedResult.get(size));
		}
	}

	private void initTableStyle() {
		
		String[] colNames = new String[]{"plannedComp","timedComp","fifInComp","fifOutComp","unComp","confirmComp"};
		for(String col : colNames){
			setTableStyle(col);
		}
	}
	
	private void setTableStyle(String columnName){
		if(chartTable.getColumn(columnName) != null){
			StyleAttributes style= chartTable.getColumn(columnName).getStyleAttributes();
			style.setUnderline(true);
			style.setFontColor(Color.BLUE);
			style.setHorizontalAlign(HorizontalAlignment.CENTER);
		}
		
	}
	

	/**
	 * 描述：初始化表格
	 * @param dataMap	项目进度信息
	 * @Author：adward_huang
	 * @CreateTime：2012-9-12
	 */
	private void initTableData(List<String> dataList, IRow row) {
		this.chartTable.checkParsed();
		row.getCell("id").setValue(dataList.get(0));
		row.getCell("projectName").setValue(dataList.get(1));
		row.getCell("plannedComp").setValue(dataList.get(2));
		row.getCell("timedComp").setValue(dataList.get(3));
		row.getCell("fifInComp").setValue(dataList.get(4));
		row.getCell("fifOutComp").setValue(dataList.get(5));
		row.getCell("unComp").setValue(dataList.get(6));
		row.getCell("confirmComp").setValue(dataList.get(7));
		String timedCompRate = new BigDecimal(dataList.get(8)).multiply(new BigDecimal(100)).setScale(MULTI_SCALE) + "%";
		row.getCell("timedCompRate").setValue(timedCompRate);
		String delayedCompRate = new BigDecimal(dataList.get(9)).multiply(new BigDecimal(100)).setScale(MULTI_SCALE) + "%";
		row.getCell("delayedCompRate").setValue(delayedCompRate);
	}

	/**
	 * 描述：初始化图形界面
	 * @param dataMap	项目进度信息
	 * @Author：adward_huang
	 * @CreateTime：2012-9-12
	 */
	private void initChartPanelData(String[] columnKeys, double[][] barData, double[][] lineData, Integer maxValue) {

		ScheduleChartHelper chartHelper = new ScheduleChartHelper();
		String title = getChartTitle();
		chart = chartHelper.getChartPanel(title, columnKeys, barData, lineData, maxValue);

		ChartPanel chartPanel = new ChartPanel(chart);
		
		// 移除图形界面的鼠标事件
		EventListener[] listeners = chartPanel.getListeners(MouseListener.class);
		for(EventListener listener: listeners){
			
			chartPanel.removeMouseListener((MouseListener) listener);
		}
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		chartDimension = new Dimension();
		chartDimension.setSize(width , height * 0.5);
		if (height < 864) {
			chartDimension.setSize(width, height - 440);
		}
		//		chartPanel.setSize(new Dimension(1166, 500));
		chartPanel.setSize(chartDimension);
		fillChart(chartPanel);
	}

	private void fillChart(JPanel chartPanel) {
		this.chartPanel.removeAll();
		this.chartPanel.add(chartPanel);
		updateUI();
	}

	/**
	* 描述：获取过滤后的进度项目完成情况，子类可以自行实现获取图形数据和表格数据的接口
	 * @param orgIds	由查询界面传入的所选组织ID集合
	 * @param startDate	由查询界面传入的完成日期开始日期
	 * @param endDate   由查询界面传入的完成日期结束日期
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-11
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Map<String, String>> getScheduleReportData(Map<String, Map<String, String>> orgIds, Date startDate, Date endDate,
			String type) {

		if (orgIds == null) {
			FDCMsgBox.showInfo("进度未关联组织，不能进行跟踪查询！");
			SysUtil.abort();
		}
		Map<String, Map<String, String>> dataMap = null;
		Map<String, Date> dates = new HashMap<String, Date>();
		dates.put(FDCScheduleConstant.REPORT_CHART_START_DATE, startDate);
		dates.put(FDCScheduleConstant.REPORT_CHART_END_DATE, endDate);
		try {
			dataMap = ScheduleReportFacadeFactory.getRemoteInstance().getScheduleReportData(orgIds, dates, type);
		} catch (EASBizException e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		}

		return dataMap;
	}

	/**
	* output actionQuery_actionPerformed
	*/
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		if (this.actionQuery.isVisible()) {
			getFilterUI(true);
			super.actionQuery_actionPerformed(e);
		}
	}

	/**
	 * output actionExportToExcel_actionPerformed
	 */
	public void actionExportToExcel_actionPerformed(ActionEvent e) throws Exception {

		if (getSelectedAllOrgsId() != null && !getSelectedAllOrgsId().isEmpty() && chart != null) {
			try {
				BufferedImage image = ScheduleChartHelper.getChartIMG(chart,(int)chartDimension.getWidth(),(int)chartDimension.getHeight());
				ScheduleChartHelper.exportToExcel(image, chartTable, getChartTitle());
			} catch (Exception ex) {
				MsgBox.showWarning(this, "导出EXCEL失败，请关闭所选择导出的EXCEL文件再重试！");
			}
		} else {
			MsgBox.showWarning(this, "没有可以导出的数据，请选择条件操作后再重试！");
			SysUtil.abort();
		}
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportBaseUI#actionPrint_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {

		List<List<String>> tableData = ScheduleChartHelper.getTableListData(chartTable);
		if(chart == null || tableData == null || tableData.isEmpty()){
			MsgBox.showWarning("没有生成图形数据，请保证生成数据后再打印！");
			return;
		}
		
		ScheduleReportTemplateProvider dataProvider = new ScheduleReportTemplateProvider(
				tableData, ScheduleChartHelper.getChartIMG(chart,(int)chartDimension.getWidth(),(int)chartDimension.getHeight()));
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDPath(), dataProvider, SwingUtilities.getWindowAncestor(this));

	}

	/**
	 * @see com.kingdee.eas.framework.client.ListUI#actionPrintPreview_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		
		List<List<String>> tableData = ScheduleChartHelper.getTableListData(chartTable);
		if(chart == null || tableData == null || tableData.isEmpty()){
			MsgBox.showWarning("没有生成图形数据，请保证生成数据后再打印预览！");
			return;
		}
		ScheduleReportTemplateProvider dataProvider = new ScheduleReportTemplateProvider(
				tableData, ScheduleChartHelper.getChartIMG(chart,(int)chartDimension.getWidth(),(int)chartDimension.getHeight()));
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDPath(), dataProvider, SwingUtilities.getWindowAncestor(this));

	}
	
	/**
	 * 描述：显示柱状图的数字
	 * @Author：adward_huang
	 * @CreateTime：2012-10-24
	 */
	private void showChartNumber() {
		if (chart != null) {
			CategoryPlot plot = chart.getCategoryPlot();
			if (isShowChartNumberBtn.getText().equals("显示数字")) {
				plot.getRenderer().setBaseItemLabelsVisible(true);
				isShowChartNumberBtn.setText("隐藏数字");
				isShowChartNumberBtn.setIcon(EASResource.getIcon("imgHotspot_2"));
			} else {
				plot.getRenderer().setBaseItemLabelsVisible(false);
				isShowChartNumberBtn.setText("显示数字");
				isShowChartNumberBtn.setIcon(EASResource.getIcon("imgHotspot_1"));
			}
		}
	}

	/**
	 * 描述：设置查询范围数据
	 * @param map
	 * @param startDate
	 * @param endDate
	 * @param chartTitle
	 * @param scheduleChartType
	 * @Author：adward_huang
	 * @CreateTime：2012-10-17
	 */
	protected void setSelectorAllValue(Map<String, Map<String, String>> map, Date startDate, Date endDate, String projectName,
			String scheduleChartType) {

		if (map != null) {
			this.setSelectAllOrgsId(map);
		}
		if (startDate != null) {
			this.setSelectedStartDate(startDate);
		}
		if (endDate != null) {
			this.setSelectedEndDate(endDate);
		}
		if (projectName != null) {
			this.setProjectName(projectName);
		}
		if (scheduleChartType != null) {
			this.setScheduleType(scheduleChartType);
		}
		if (scheduleChartType != null) {
			this.setScheduleType(scheduleChartType);
		}
	}

	/**
	 * 描述：初始化查询窗体，设置自定义查询界面
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-17
	 */
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog == null) {
			commonQueryDialog = super.initCommonQueryDialog();
			commonQueryDialog.setWidth(450);
			commonQueryDialog.setTitle("条件查询");
		}
		commonQueryDialog.addUserPanel(this.getFilterUI(false));
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI(boolean isUpdate) {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ScheduleReportQueryPanelUI();
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		this.filterUI.setScheduleReportBaseUI(this, isUpdate);
		return this.filterUI;
	}

	/**
	* @see com.kingdee.eas.framework.client.ListUI#initDefaultFilter()
	*/
	protected boolean initDefaultFilter() {
		return !FDCMsgBox.isHidedBox();
	}

	/**
	 * 描述：提供对外的接口，传入的所选组织ID集合
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-12
	 */
	protected Map<String, Map<String, String>> getSelectedAllOrgsId() {
		return selectAllOrgsMap;
	}

	private void setSelectAllOrgsId(Map<String, Map<String, String>> selectOrgsMap) {
		this.selectAllOrgsMap = new HashMap<String, Map<String, String>>();
		this.selectAllOrgsMap.putAll(selectOrgsMap);
	}

	/**
	 * 描述：提供对外的接口，传入的完成日期范围开始日期
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

	/**
	 * 描述：提供对外的接口，传入的统计指标类型
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-12
	 */
	protected String getScheduleType() {
		return scheduleChartType;
	}

	private void setScheduleType(String scheduleChartType) {
		this.scheduleChartType = scheduleChartType;
	}

	protected String getProjectName() {
		if(projectName != null && projectName.length() > 0){
			return projectName+ FDCScheduleConstant.ORG_PROJECT_NAME_SEPARATOR;
		}
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	* 描述：提供对外的接口，传入的图形的标题
	* @return
	* @Author：adward_huang
	* @CreateTime：2012-9-12
	*/
	protected String getChartTitle() {
		return chartTitle;
	}

	/**
	 * 单独返回图表的图像
	 * <p>
	 * 有时候套打只需要打印图表，而下面的表格使用另外的数据源打印<br>
	 * 此时套打取图片使用此方法，而不用getUIIMG()
	 *//*
	protected BufferedImage getChartIMG() {
		int height2 = chartContainer.getHeight();
		int width2 = chartContainer.getWidth();
		BufferedImage img = new BufferedImage(width2, height2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		chartContainer.paintComponents(g);
		return img;
	}*/

	/**
	 * 返回套打模板路径
	 * 子类需重载
	 * @return
	 */
	protected String getTDPath() {
		return "/bim/fdc/process/FDCSchedule";
	}

	/**
	 * @see com.kingdee.eas.framework.client.ListUI#getBizInterface()
	 */
	@Override
	protected ICoreBase getBizInterface() throws Exception {
		/* TODO 自动生成方法存根 */
		return null;
	}

	/**
	 * @see com.kingdee.eas.framework.client.ListUI#getEditUIName()
	 */
	@Override
	protected String getEditUIName() {
		/* TODO 自动生成方法存根 */
		return null;
	}
	
	@Override
	protected void chartTable_tableClicked(KDTMouseEvent e) throws Exception {
		int columnIndex = chartTable.getSelectManager().getActiveColumnIndex();
		int rowIndex = chartTable.getSelectManager().getActiveRowIndex();
		Object value = chartTable.getCell(rowIndex, columnIndex).getValue();
		int intValue = 0;
		try{
			intValue = Integer.parseInt(value+"");
		}catch (NumberFormatException ex) {
             //nothing to do
		}
		
//		if(value == null ||intValue==0){
//			FDCMsgBox.showInfo("不存在对应任务，无法查看任务列表信息!");
//		    return;
//		}
		
		String detailUIName = RptTaskListUI.class.getName();
		UIContext uiContext = new UIContext();
		uiContext.put("startDate", getSelectedStartDate());
		uiContext.put("endDate", getSelectedEndDate());
		uiContext.put("selectedOrgs", getSelectedAllOrgsId());
		uiContext.put("currentOrgId", this.chartTable.getCell(rowIndex, "id").getValue());
		uiContext.put("currentOrgName", this.chartTable.getCell(rowIndex, "projectName").getValue());
		uiContext.put("taskType", getScheduleType());
		uiContext.put("chartType", getScheduleType());
		uiContext.put("Owner", this);
		uiContext.put("state", chartTable.getColumnKey(columnIndex));//计划完成，按时完成，延时完成，待确认,未完成(根据列名来)
		
		UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(detailUIName, uiContext).show();
	}
	
	
	

}