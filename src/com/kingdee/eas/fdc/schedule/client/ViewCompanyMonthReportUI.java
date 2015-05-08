/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.data.general.Dataset;
import com.kingdee.bos.ctrl.freechart.data.general.DefaultPieDataset;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskStateHelper;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportInfo;
import com.kingdee.eas.fdc.schedule.WeekReportEnum;
import com.kingdee.eas.fdc.schedule.framework.client.ScheduleChartProvider;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * 
 * @(#)						
 * 版权：		金蝶国际软件集团有限公司版权所有 描述： 计划管理任务列表编辑界面
 * 
 * @author 罗小龙
 * @version EAS7.0
 * @createDate 2011-09-08
 * @see
 */
public class ViewCompanyMonthReportUI extends AbstractViewCompanyMonthReportUI {
	/** 缺省版本号 */
	private static final long serialVersionUID = 1L;
	
	private Map startAndEndDateMap = new HashMap();
	
	private Map taskMap = new HashMap();
	
	private Map<String, String> paramMap = null;

	/** 日志 */
	private static final Logger logger = CoreUIObject.getLogger(ViewCompanyMonthReportUI.class);

	protected ITreeBuilder treeBuilder;

	public ViewCompanyMonthReportUI() throws Exception {
		super();
	}

	protected void initTree() throws Exception {
		// 展示财务组织
		buildCompanyTree();
		treeMain.setRootVisible(false);
		treeMain.setShowsRootHandles(true);
		treeMain.expandRow(0);
		treeMain.setSelectionRow(0);
	}

	public void onLoad() throws Exception {
		this.pnlChart.removeAll();
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		super.onLoad();

		this.thisTable.checkParsed();
		this.nextTable.checkParsed();
		thisTable.getColumn("project").setWidth(150);

		nextTable.getColumn("project").setWidth(220);
		nextTable.getColumn("taskName").setWidth(220);
		nextTable.getColumn("planStartDate").setWidth(220);
		nextTable.getColumn("planEndDate").setWidth(220);
		nextTable.getColumn("respPerson").setWidth(120);
		nextTable.getColumn("respDept").setWidth(120);

		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
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
	
	private void setDefalutSelected() {
		if (setDefalutYear1Selected()) {
			setDefalutMonth1SelectedItem();
		}
	}

	public boolean setDefalutYear1Selected() {
		for (int index = 0; index < cbYear1.getItemCount(); index++) {
			String item = (String) cbYear1.getItemAt(index);
			if (item.equals(getCurrYear() + "")) {
				cbYear1.setSelectedIndex(index);
				return true;
			}
		}
		return false;
	}

	protected boolean setDefalutMonth1SelectedItem() {
		for (int index = 0; index < cbMonth1.getItemCount(); index++) {
			String item = (String) cbMonth1.getItemAt(index);
			if (item.equals(getCurrMonthOfYear() + "")) {
				cbMonth1.setSelectedIndex(index);
				return true;
			}
		}
		return false;
	}

	public String getChartTitle() {
		String title = "";
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		CompanyOrgUnitInfo ccoui = (CompanyOrgUnitInfo) node.getUserObject();
		FDCScheduleTaskCollection currMonth = new FDCScheduleTaskCollection();
		FDCScheduleTaskCollection nextMonth = new FDCScheduleTaskCollection();

		/*
		 * 取报告年-月
		 */
		String selectedYear = cbYear1.getSelectedItem().toString().trim();
		String selectedMonth = cbMonth1.getSelectedItem().toString().trim();
		if (null == selectedYear || "".equals(selectedYear) || selectedYear.length() == 0) {
			this.pnlChart.removeAll();
			this.thisTable.removeRows();
			this.nextTable.removeRows();
			return title;
		}
		try {
			currMonth = getAllTask(new String(selectedYear + "-" + selectedMonth), ccoui.getId().toString(), false);
			nextMonth = getAllTask(new String(selectedYear + "-" + selectedMonth), ccoui.getId().toString(), true);
			title = selectedYear + "年" + selectedMonth + "月" + ccoui.getName() + "月报";
			if (currMonth.size() > 0) {
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

				String str = getTitle(Integer.parseInt(selectedYear), Integer.parseInt(selectedMonth), finished, delayed,
						unfinished, affirm, excudeing);
				Font font = new Font(str, Font.PLAIN, 12);
				title = title + "\n" + font.getName();
				return title;
			} else {
				String str = selectedYear + "年第" + selectedMonth + "月任务项" + currMonth.size() + "项,其中按时完成0项,延时完成0项,待确认0项,延时且未完成0项,执行中0项。计划下月任务"
						+ nextMonth.size()
						+ "项," + "\n" + "本月任务状态分布如下饼图:";
				Font font = new Font(str, Font.PLAIN, 12);
				title = title + "\n" + font.getName();
				return title;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return title;
	}

	private String getTitle(int year, int month, int finished, int delayed,
			int unfinished, int affirm, int excudeing) {
		return year + "年第" + month + "月任务项" + thisTable.getRowCount() + "项,其中按时完成" + finished + "项,延时完成" + delayed + "项,待确认" + affirm + "项,延时且未完成" + unfinished + "项,执行中" + excudeing + "项。计划下月任务"
				+ nextTable.getRowCount() + "项,本月任务状态分布如下饼图:";
	}

	public void onShow() throws Exception {
		super.onShow();
		this.sptTop.setDividerLocation(320);
		this.btnExportIMG.setText("导出Excel");
		this.btnExportIMG.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.btnExportIMG.setToolTipText("导出Excel");
		this.menuItemExpIMG.setText("导出Excel");
		this.menuItemExpIMG.setToolTipText("导出Excel");
		this.menuItemExpIMG.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.sptTop.setDividerLocation(246);
		this.sptDown.setDividerLocation(252);
	}

	public void actionExportIMG_actionPerformed(ActionEvent e) throws Exception {
		// super.actionExportIMG_actionPerformed(e);
		List tables = new ArrayList();
		tables.add(new Object[] { "本月任务", thisTable });
		tables.add(new Object[] { "下月任务", nextTable });
		FDCTableHelper.exportExcel(this, tables);
	}

	/**
	 * 
	 * @description 构建财务组织树
	 * @author 杜红明
	 * @createDate 2011-11-2
	 * @throws Exception void
	 * @version EAS7.0
	 * @see
	 */
	private void buildCompanyTree() throws Exception {
		FilterInfo filter = new FilterInfo();
		CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
		String longNumber = currentFIUnit.getLongNumber();
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber + "%", CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("isFreeze", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("isOUSealUp", Boolean.FALSE));
		treeBuilder = TreeBuilderFactory.createTreeBuilder(getTempLNTreeNodeCtrl(), 50, 5, filter, new SelectorItemCollection());
		treeBuilder.buildTree(treeMain);
		TreeModel treeModel = new KingdeeTreeModel((TreeNode) ((DefaultKingdeeTreeNode) treeMain.getModel().getRoot()));
		treeMain.setModel(treeModel);
	}

	/**
	 * 
	 * @description 获得财务组织接口
	 * @author 杜红明
	 * @createDate 2011-11-2
	 * @return
	 * @throws Exception
	 *             ILNTreeNodeCtrl
	 * @version EAS7.0
	 * @see
	 */
	private ILNTreeNodeCtrl getTempLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(CompanyOrgUnitFactory.getRemoteInstance());
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		initParams();
		pnlChart.removeAll();
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		initCbYear();
		setDefalutSelected();
	}
	
	protected void cbYear1_itemStateChanged(ItemEvent e) throws Exception {
		
		itemStateChanged();
	}

	protected void cbMonth1_itemStateChanged(ItemEvent e) throws Exception {
		itemStateChanged();
	}

	
	private void itemStateChanged() throws Exception {
		this.pnlChart.removeAll();
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		if (cbYear1.getSelectedItem() != null && cbMonth1.getSelectedItem() != null) {
			initData();
		}
		pnlChart.updateUI();
	}

	/**
	 * @discription <初始化页面数据>
	 * @author <Xiaolong Luo>
	 */
	public void initData() throws Exception {

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		CompanyOrgUnitInfo ccoui = (CompanyOrgUnitInfo) node.getUserObject();
		FDCScheduleTaskCollection currMonth = new FDCScheduleTaskCollection();
		FDCScheduleTaskCollection nextMonth = new FDCScheduleTaskCollection();
		String selectedYear = (String) cbYear1.getSelectedItem();
		String selectedMonth = (String) cbMonth1.getSelectedItem();
		if (cbYear1.getSelectedItem() == null || cbMonth1.getSelectedItem() == null) {
			return;
		}
		currMonth = getAllTask(new String(selectedYear + "-" + selectedMonth), ccoui.getId().toString(), false);
		nextMonth = getAllTask(new String(selectedYear + "-" + selectedMonth), ccoui.getId().toString(), true);

		if (currMonth.size() > 0) {
			loadThisTableFields(currMonth);
		}
		if (nextMonth.size() > 0) {
			loadNextTableFields(nextMonth);
		}

		if (currMonth.size() > 0) {
			initChart(currMonth);
		} else {
			pnlChart.removeAll();
		}

	}

	/**
	 * @discription <初始化下月计划表格数据>
	 * @author <Xiaolong Luo>
	 */
	public void loadNextTableFields(FDCScheduleTaskCollection list) {
		this.nextTable.removeRows(false);
		this.nextTable.checkParsed();
		if (list.size() > 0) {// 集合大于0
			for (int i = 0; i < list.size(); i++) {// 循环将分录放入表格中
				FDCScheduleTaskInfo entryInfo = (FDCScheduleTaskInfo) list.get(i);
				IRow row = this.nextTable.addRow();
				FDCScheduleInfo head = entryInfo.getSchedule();
				if (null != head.getProject()) {
					row.getCell("project").setValue(head.getProject().getName());
				}
				row.getCell("taskName").setValue(entryInfo.getName());
				row.getCell("planStartDate").setValue(entryInfo.getStart());
				row.getCell("planEndDate").setValue(entryInfo.getEnd());
				row.getCell("respPerson").setValue(entryInfo.getAdminPerson());
				row.getCell("respDept").setValue(entryInfo.getAdminDept());
				row.getCell("entryID").setValue(entryInfo.getId());
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
		Set evaluationSet = FDCScheduleTaskStateHelper.isEvaluationed(null, list);
		Map<String, ScheduleTaskProgressReportInfo> reportMap = ViewReportHelper.getScheudleReportData(list);
		String costCenterId = null;
		boolean isEvalutioned = false;
		String srcId = null;
		if (list.size() > 0) {
			FDCScheduleTaskInfo entryInfo = null;
			for (int i = 0; i < list.size(); i++) {
				entryInfo = (FDCScheduleTaskInfo) list.get(i);
				srcId = entryInfo.getSrcID();
				FDCScheduleInfo head = entryInfo.getSchedule();
				IRow row = this.thisTable.addRow();
				if (null != head.getProject()) {
					row.getCell("project").setValue(head.getProject().getName());
				}
				row.getCell("taskName").setValue(entryInfo.getName());
				row.getCell("planEndDate").setValue(entryInfo.getEnd());
				row.getCell("realEndDate").setValue(entryInfo.getActualEndDate());
				row.getCell("respPerson").setValue(entryInfo.getAdminPerson());
				row.getCell("respDept").setValue(entryInfo.getAdminDept());
				// row.getCell("respDept").setValue(entryInfo.getStart());
				row.getCell("entryID").setValue(entryInfo.getId());
				costCenterId = entryInfo.getSchedule().getProject().getCostCenter().getId().toString();
				if (evaluationSet.contains(entryInfo.getSrcID())) {
					isEvalutioned = true;
				} else {
					isEvalutioned = false;
				}
				ViewReportHelper.initStateCell(isEvalutioned, isParamControl(costCenterId), row, entryInfo);
				int complate = entryInfo.getComplete() == null ? 0 : entryInfo.getComplete().intValue();
				if (complate == 100) {
					row.getCell("isComplete").setValue(WeekReportEnum.YES);
					row.getCell("intendEndDate").setValue(entryInfo.getActualEndDate());
				} else {
					row.getCell("isComplete").setValue(WeekReportEnum.NO);
					row.getCell("intendEndDate").setValue(entryInfo.getIntendEndDate() == null ? entryInfo.getPlanEnd() : entryInfo.getIntendEndDate());
				}
				row.getCell("completePercent").setValue(entryInfo.getComplete());
				if (reportMap != null && reportMap.containsKey(srcId)) {
					row.getCell("description").setValue(reportMap.get(srcId).getDescription());
				}
			}
		}
	}

	private void initParams() throws BOSException {
		if (paramMap == null) {
			paramMap = new HashMap<String, String>();
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			CompanyOrgUnitInfo ccoui = (CompanyOrgUnitInfo) node.getUserObject();
			paramMap = ViewReportHelper.getParams(ccoui.getId().toString());
		}
	}

	private boolean isParamControl(String costCenterId) {
		boolean isControl = false;
		if(StringUtils.isEmpty(costCenterId)){
			return false;
		}
		//TODO这个参数是做什么的。。。客户帐套可以为空
		if (paramMap != null && paramMap.get(costCenterId) != null) {
			int value = Integer.parseInt(paramMap.get(costCenterId));
			if (value == 1 || value == 3) {
				isControl = true;
			}
		}
		return isControl;
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

	// public void initStateCell(boolean isEvaluation, boolean isParamControl,
	// IRow row, FDCScheduleTaskInfo entryInfo) throws BOSException {
	// int state = -1;
	// // 实际完成日期
	// Date realEndDate = entryInfo.getActualEndDate();
	// // 计划完成日期
	// Date planEndDate = entryInfo.getEnd();
	//
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
	// int completePrecent = entryInfo.getComplete() == null ? 0 :
	// entryInfo.getComplete().intValue();
	// state = ViewReportHelper.getState(isEvaluation, isParamControl,
	// entryInfo);
	// if (row != null) {
	// switch (state) {
	// case FDCScheduleConstant.ONTIME:
	// row.getCell("state").setValue(achieve);
	// row.getCell("state").setUserObject("0");
	// row.getCell("state").getStyleAttributes().setBold(true);
	// row.getCell("state").getStyleAttributes().setFontColor(green);
	// break;
	// case FDCScheduleConstant.DELAY:
	// row.getCell("state").setValue(achieve);
	// row.getCell("state").setUserObject("1");
	// row.getCell("state").getStyleAttributes().setBold(true);
	// row.getCell("state").getStyleAttributes().setFontColor(red);
	// break;
	// case FDCScheduleConstant.CONFIRMED:
	// row.getCell("state").setValue(pending);
	// row.getCell("state").setUserObject("2");
	// row.getCell("state").getStyleAttributes().setFontColor(orange);
	// break;
	// case FDCScheduleConstant.DELAYANDNOTCOMPLETE:
	// row.getCell("state").setValue(pending);
	// row.getCell("state").setUserObject("3");
	// row.getCell("state").getStyleAttributes().setFontColor(red);
	// break;
	// }
	// }
	// }

	protected void initChart(FDCScheduleTaskCollection thisMonth) {
		pnlChart.removeAll();
		pnlChart.add(createChartPanel(), BorderLayout.CENTER);
		updateUI();
	}

	/**
	 * @description 创建一个图表面板
	 * @author 车忠伟
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	protected JPanel createChartPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}
	

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
			
//			for (int i = 0; i < thisMonth.size(); i++) {
//				FDCScheduleTaskInfo entryInfo = (FDCScheduleTaskInfo) thisMonth.get(i);
//				Date planEndDate = entryInfo.getEnd();
//				Date realEndDate = entryInfo.getActualEndDate();
//				int completePrecent = entryInfo.getComplete() == null ? 0 : entryInfo.getComplete().intValue();
//				int state = ViewReportHelper.getState(isParamControl(entryInfo.getSchedule().getProject().getCostCenter().getId().toString()), entryInfo, realEndDate, planEndDate, completePrecent);
//				switch (state) {
//				case 0:
//					finished++;
//					break;
//				case 1:
//					delayed++;
//					break;
//				case 2:
//					unfinished++;
//					break;
//				case 3:
//					affirm++;
//					break;
//				case 4:
//					excudeing++;
//					break;
//				}
//			}
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
	private void initCbYear() throws EASBizException, BOSException, SQLException {
		cbYear1.removeAllItems();
		cbMonth1.removeAllItems();
		
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		
		
		 if (node != null && node.isLeaf() && node.getUserObject() instanceof CompanyOrgUnitInfo) {
			CompanyOrgUnitInfo cpinfo = (CompanyOrgUnitInfo) node.getUserObject();
			   Date[] startAndEndDate = ViewReportHelper.getStartAndEndDate(cpinfo.castToFullOrgUnitInfo(), startAndEndDateMap);
			
			if (startAndEndDate == null || startAndEndDate[0] == null || startAndEndDate[1] == null) {
				return;
			}
			Calendar startCal = Calendar.getInstance();
			Calendar endCal = Calendar.getInstance();

			startCal.setTime(startAndEndDate[0]);
			endCal.setTime(startAndEndDate[1]);

			int startYear = startCal.get(Calendar.YEAR);
			int endYear = endCal.get(Calendar.YEAR);
            
			ItemListener[] yearListener = cbYear.getItemListeners();
			ItemListener[] monthListener = cbMonth1.getItemListeners();
			for (int i = 0; i < monthListener.length; i++) {
				cbMonth1.removeItemListener(monthListener[i]);
			}
			for (int i = 0; i < yearListener.length; i++) {
				cbYear1.removeItemListener(yearListener[i]);
			}
			  

			for (int i = startYear; i <= endYear; i++) {
				cbYear1.addItem(i + "");
			}
			
			for (int j = 1; j <= 12; j++) {
				cbMonth1.addItem(j + "");
			}
			
			for (int i = 0; i < monthListener.length; i++) {
				cbMonth1.addItemListener(monthListener[i]);
			}
			for (int i = 0; i < yearListener.length; i++) {
				cbYear1.addItemListener(yearListener[i]);
			}
			  
		}
		// if (node != null && node.isLeaf() && node.getUserObject() instanceof
		// CompanyOrgUnitInfo) {
		// CompanyOrgUnitInfo cpinfo = (CompanyOrgUnitInfo)
		// node.getUserObject();
		// if (cpinfo.getId() != null) {
		// try {
		// Set tasksList = getAllTask(cpinfo.getId().toString());
		// if (tasksList.size() > 0) {
		// for (int i = 1; i <= 12; i++) {
		// cbMonth1.addItem(i + "");
		// }
		// int max = Integer.parseInt(((String)
		// tasksList.toArray()[0]).split("-")[0]);
		// int min = Integer.parseInt(((String)
		// tasksList.toArray()[tasksList.size() - 1]).split("-")[0]);
		// for (int i = max; i >= min && i <= max; i--) {
		// cbYear1.addItem(i + "");
		// }
		// }
		// } catch (Exception e) {
		// logger.error(e.getMessage());
		// }
		// }
		// }
	}

	/**
	 * @description
	 * @author 杜红明
	 * @createDate 2011-11-4 void
	 * @version EAS7.0
	 * @throws BOSException
	 * @throws SQLException
	 * @see
	 */

	private Set getAllTask(String id) throws BOSException, SQLException {
		Set projectIDS = getProjectsByOrgUnit(id);
		if (projectIDS == null || projectIDS.size() == 0) {
			return new HashSet();
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < projectIDS.size(); i++) {
			if (i == projectIDS.size() - 1) {
				sb.append("'" + (String) projectIDS.toArray()[i] + "'");
			} else {
				sb.append("'" + (String) projectIDS.toArray()[i] + "',");
			}

		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fstart,fend from t_sch_fdcscheduletask ");
		builder.appendSql("where fscheduleid in ( ");
		builder.appendSql("select fid  from t_sch_fdcschedule  where fversion=1 and fprojectid ");
		builder.appendSql(" in (" + sb + ")) order by fstart,fend desc");
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
	 * @description
	 * @author 杜红明
	 * @createDate 2011-11-4 void
	 * @version EAS7.0
	 * @see
	 */
	private Set getProjectsByOrgUnit(String orgUnitID) {
		Set ids = new HashSet();
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select fid from T_FDC_CurProject where fisEnabled = 1 and FFullOrgUnit=?");
			builder.addParam(orgUnitID);
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

	protected FDCScheduleTaskCollection getAllTask(String date, String nodeID, boolean isNext) throws BOSException, ParseException, SQLException {
		FDCScheduleTaskCollection col = null;
	
		if (taskMap.get(nodeID) != null) {
			col = (FDCScheduleTaskCollection) taskMap.get(nodeID);
		} else {
			// 取出当前财务组织下的所有工程项目
			// Set projectIDS = getProjectsByOrgUnit(nodeID);
			// if (projectIDS == null || projectIDS.size() == 0) {
			// return new FDCScheduleTaskCollection();
			// }
			// // 取出当前组织下所有工程项目下的任务ID
			// Set set = getTaskIDByProjects(projectIDS);
			// // 优化：取出当前组织下的所有最新版本的计划即可
			// // select fid from t_sch_fdcschedule where fprojectid in (select
			// fid
			// // from t_fdc_curproject where ffullorgunit = ?)
			if (StringUtils.isEmpty(nodeID)) {
				return null;
			}
			String sql = "select fid from t_sch_fdcschedule where (fprojectspecialid is null or fprojectspecialid = '') and fislatestver = 1 and fprojectid in (select fid from t_fdc_curproject where ffullorgunit = '"
					+ nodeID
					+ "')";
			
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection selectors = view.getSelector();
			selectors.add("id");
			selectors.add("name");
			selectors.add("parent.id");
			selectors.add("parent.name");
			selectors.add("complete");
			selectors.add("start");
			selectors.add("srcid");
			selectors.add("intendEndDate");
			selectors.add("end");
			selectors.add("actualStartDate");
			selectors.add("adminPerson.name");
			selectors.add("adminDept.name");
			selectors.add("actualEndDate");
			selectors.add("schedule.project.name");
			selectors.add("schedule.project.costcenter.id");
			FilterInfo filter = new FilterInfo();
		
			filter.getFilterItems().add(new FilterItemInfo("schedule.id", sql, CompareType.INNER));
			filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
			view.setFilter(filter);
		
			SorterItemCollection sorter = new SorterItemCollection();
			SorterItemInfo si = new SorterItemInfo("end");
			si.setSortType(SortType.ASCEND);
			sorter.add(si);
			view.setSorter(sorter);
			
			col = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
			// Set ids = new HashSet();
			// for (int i = 0; i < col.size(); i++) {
			// FDCScheduleTaskInfo parent = col.get(i).getParent();
			// if (col.get(i).getParent() != null) {
			// ids.add(parent.getId().toString());
			// }
			// }
			// view = new EntityViewInfo();
			// view.setSelector(selectors);
			// addSelectors(selectors);
			// filter.getFilterItems().add(new FilterItemInfo("id", ids,
			// CompareType.NOTINCLUDE));
			// filter.getFilterItems().add(new
			// FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
			// view.setFilter(filter);
			// col = FDCScheduleTaskFactory.getRemoteInstance().
			// getFDCScheduleTaskCollection(view);
			taskMap.put(nodeID, col);
		}
		if (isNext) {
			return ViewReportHelper.getNextMonthTasks(col, date);
		}
		return ViewReportHelper.getCurrMonthTasks(col, date);
	}

	private void addSelectors(SelectorItemCollection selectors) {
		selectors.add("schedule.project.name");
		selectors.add("adminPerson.name");
		selectors.add("adminDept.name");
		selectors.add("complete");
		selectors.add("id");
		selectors.add("name");
		selectors.add("start");
		selectors.add("end");
		selectors.add("actualEndDate");
		selectors.add("actualStartDate");
		selectors.add("*");
		selectors.add("parent.id");
		selectors.add("parent.name");
		selectors.add("parent.parent.id");
		selectors.add("parent.parent.name");
	}

	private Set getTaskIDByProjects(Set projectIDS) throws BOSException, SQLException {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < projectIDS.size(); i++) {
			if (i == projectIDS.size() - 1) {
				sb.append("'" + (String) projectIDS.toArray()[i] + "'");
			} else {
				sb.append("'" + (String) projectIDS.toArray()[i] + "',");
			}
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid from t_sch_fdcscheduletask ");
		builder.appendSql("where fscheduleid in ");
		builder.appendSql("( select fid  from t_sch_fdcschedule  where fisLatestVer=1 and fprojectid ");
		builder.appendSql("in(" + sb + ") and (FProjectSpecialID='' or FProjectSpecialID is NULL))  ");
		IRowSet rs = builder.executeQuery();
		Set set = new HashSet();
		while (rs.next()) {
			set.add(rs.getString("fid"));
		}
		return set;
	}


	protected String getUIName() {
		return ViewCompanyMonthReportUI.class.getName();
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
		int printpre3 = nextTable.getColumn(2).getWidth();
		int printpre4 = nextTable.getColumn(3).getWidth();
		int printpre5 = nextTable.getColumn(4).getWidth();
		int printpre6 = nextTable.getColumn(5).getWidth();
		nextTable.getColumn(0).setWidth(180);
		nextTable.getColumn(1).setWidth(190);
		nextTable.getColumn(2).setWidth(140);
		nextTable.getColumn(3).setWidth(130);
		nextTable.getColumn(4).setWidth(130);
		nextTable.getColumn(5).setWidth(180);
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
		nextTable.getColumn(2).setWidth(printpre3);
		nextTable.getColumn(3).setWidth(printpre4);
		nextTable.getColumn(4).setWidth(printpre5);
		nextTable.getColumn(5).setWidth(printpre6);
		// 7、返回img
		return img;
	}

}