/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.ScheduleChartHelper;
import com.kingdee.eas.fdc.schedule.ScheduleReportSearchCondition;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述:进度报表专项计划达成率界面
 * @author adward_huang  date:2012-10-17
 * @version EAS6.1
 */
public class ScheduleExpertPlanTreeRateUI extends AbstractScheduleExpertPlanTreeRateUI {

	private static final long serialVersionUID = 2971050138473397559L;

	private static final Logger logger = CoreUIObject.getLogger(ScheduleExpertPlanTreeRateUI.class);

	// 图形标题
	private static final String CHART_TITLE = "专项计划达成率";

	// 选择的工程名称
	private String selectProjectName;

	private boolean hasDateSearch = false;

	private Map<String, Map<String, String>> dataMap;

	/**
	 * output class constructor
	 */
	public ScheduleExpertPlanTreeRateUI() throws Exception {
		super();
		kDSplitPane2.setDividerLocation(650);
		// 初始化页面控件
		clearDates();

	}

	/**
	 * 描述：初始化表格和图形界面
	 * @Author：adward_huang
	 * @CreateTime：2012-9-12
	 */
	protected void buildTableAndChartPanel(boolean hasCacheData) {

		// 开始日期和结束日期
		Date startDate = getSelectedStartDate();
		Date endDate = getSelectedEndDate();
		String projectID = getProjectID();

		// 过滤后的进度项目完成信息
		dataMap = getScheduleExpertReportData(startDate, endDate, projectID);

		if (!hasCacheData) {
			// 保存缓存
			saveSearchCondition(projectID, startDate, endDate, dataMap);
		}

		this.setDataMap(dataMap);
		initReportPanel(dataMap);
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportTreeBaseUI#orgTree_valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	@Override
	protected void orgTree_valueChanged(TreeSelectionEvent e) throws Exception {
		//		super.orgTree_valueChanged(e);

		String[] idAndName = getSelectTreeNodeId();
		clearDates();
		if (idAndName[0] != null) {
			hasDateSearch = false;
			// 设置参数值
			setSelectorValue(idAndName[0]);
			this.setSelectProjectName(idAndName[1]);
			this.setChartTitle(this.getSelectProjectName() + CHART_TITLE);
			// 获取缓存数据
			ScheduleReportSearchCondition condition = getCacheSearchData(idAndName[0]);
			if (condition != null && condition.getStartDate() != null && condition.getEndDate() != null) {
				Date startDate = getStringToDate(condition.getStartDate());
				Date endDate = getStringToDate(condition.getEndDate());

				//				// 获取缓存数据
				//				Map<String, Map<String, String>> dataMap = condition.getDataMap();

				// 根据缓存重置日期
				resetDates(startDate, endDate);
				// 设置参数值
				//				setSelectorValue(idAndName[0]);
				this.setSelectorAllValue(idAndName[0], startDate, endDate, null, null);
				// 根据缓存数据重绘图形和表格
				//				rebuildTableAndChartPanelByCache(dataMap);

			}// 默认不保存缓存数据，点击树节点操作只显示缓存数据
			buildTableAndChartPanel(true);

		}
	}

	/**
	* 描述：获取搜索的缓存数据
	* @throws BOSException 
	* @Author：adward_huang
	* @CreateTime：2012-9-25
	*/
	private ScheduleReportSearchCondition getCacheSearchData(String projectId) throws BOSException {

		String searchValue = getSearchCondition(projectId);

		ScheduleReportSearchCondition condition = getSearchConditionObject(searchValue);

		return condition;
	}

	private void setSelectorValue(String projectID) {
		Date startDate = null;
		Date endDate = null;
		if (hasDateSearch && startDatePicker.getValue() != null && endDatePicker.getValue() != null) {
			startDate = (Date) startDatePicker.getValue();
			endDate = (Date) endDatePicker.getValue();
		}
		if (projectID == null) {
			projectID = getProjectID();
		}
		if (projectID == null) {
			FDCMsgBox.showInfo("请选择工程项目节点！");
			SysUtil.abort();
		}
		setSelectorAllValue(projectID, startDate, endDate, null, null);
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleExpertPlanTreeRateUI#actionSearch_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionSearch_actionPerformed(ActionEvent e) throws Exception {

		hasDateSearch = true;
		String id = getSelectTreeNodeId()[0];
		// 设置参数值
		setSelectorValue(id);

		if (getSelectedEndDate().before(getSelectedStartDate())) {
			MsgBox.showWarning(this, "开始日期不能大于结束日期，请重新选择！");
			SysUtil.abort();
		}
		// 默认每次搜索都保存搜索条件
		buildTableAndChartPanel(false);
	}

	/**
	 * 描述：初始化图形界面
	 * @param dataMap	项目进度信息
	 * @Author：adward_huang
	 * @CreateTime：2012-9-12
	 */
	protected void initChartPanelData(String[] columnKeys, double[][] barData, double[][] lineData, int maxValue) {

		ScheduleChartHelper chartHelper = new ScheduleChartHelper();
		String title = getChartTitle();

		chart = chartHelper.getChartPanel(title, columnKeys, barData, lineData,maxValue);

		ChartPanel chartPanel = new ChartPanel(chart);
		
		removeAllMouseListeners(chartPanel);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		Dimension chartDimension = new Dimension();
		chartDimension.setSize(width * 0.78, height * 0.63 - 100);
		if (height < 864) {
			chartDimension.setSize(width* 0.78 - 100, height * 0.63 - 100);
		}
//		chartPanel.setSize(new Dimension(980, 550));  //1280,1024
		chartPanel.setSize(chartDimension);
		this.chartPanel.removeAll();
		//		chartContainer.setSize(new Dimension(1010, 450));
		
		this.chartPanel.add(chartPanel);
		updateUI();
	}

	protected String getChartTitle() {
		return super.getChartTitle();
	}

	protected void setChartTitle(String chartTitle) {
		super.setChartTitle(chartTitle);
	}

	/**
	 * output actionExportToExcel_actionPerformed
	 */
	public void actionExportToExcel_actionPerformed(ActionEvent e) throws Exception {
		if (getProjectID() != null && dataMap != null && !dataMap.isEmpty()) {
			super.actionExportToExcel_actionPerformed(e);
		} else {
			MsgBox.showWarning(this, "没有可以导出的数据，请选择树节点操作后再重试！");
			SysUtil.abort();
		}
	}


	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrePrint_actionPerformed
	 */
	public void actionPrePrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrePrint_actionPerformed(e);
	}

	/**
	 * @see com.kingdee.eas.framework.client.CoreUI#getTableForCommon()
	 */
	@Override
	protected KDTable getTableForCommon() {
		return this.chartTable;
	}

	/**
	 * 描述：情况日期数据
	 * @Author：adward_huang
	 * @CreateTime：2012-9-25
	 */
	private void clearDates() {
		startDatePicker.setValue(null);
		endDatePicker.setValue(null);

	}

	/**
	 * 描述：充填日期数据
	 * @param startDate
	 * @param endDate
	 * @Author：adward_huang
	 * @CreateTime：2012-9-25
	 */
	public void resetDates(Date startDate, Date endDate) {
		startDatePicker.setValue(startDate);
		endDatePicker.setValue(endDate);
	}

	public boolean isHasDateSearch() {
		return hasDateSearch;
	}

	public void setHasDateSearch(boolean hasDateSearch) {
		this.hasDateSearch = hasDateSearch;
	}

	public String getSelectProjectName() {
		return selectProjectName;
	}

	public void setSelectProjectName(String selectProjectName) {
		this.selectProjectName = selectProjectName;
	}
	
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
		uiContext.put("selectedOrgs", getSelectTreeNodeId()[0]);//工程项目ID
		uiContext.put("currentOrgId", this.chartTable.getCell(rowIndex, "id").getValue());
		uiContext.put("currentOrgName", this.chartTable.getCell(rowIndex, "projectName").getValue());
		uiContext.put("taskType", "proPrj");
		uiContext.put("chartType", "proPrj");
		uiContext.put("Owner", this);
		uiContext.put("state", chartTable.getColumnKey(columnIndex));//计划完成，按时完成，延时完成，待确认,未完成(根据列名来)
		
		UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(detailUIName, uiContext).show();
	}

}