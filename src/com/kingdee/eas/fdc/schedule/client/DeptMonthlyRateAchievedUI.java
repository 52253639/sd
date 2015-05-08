/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.ChartFactory;
import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.chart.LegendItem;
import com.kingdee.bos.ctrl.freechart.chart.LegendItemCollection;
import com.kingdee.bos.ctrl.freechart.chart.axis.NumberAxis;
import com.kingdee.bos.ctrl.freechart.chart.axis.NumberTickUnit;
import com.kingdee.bos.ctrl.freechart.chart.axis.SubCategoryAxis;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardCategoryToolTipGenerator;
import com.kingdee.bos.ctrl.freechart.chart.plot.CategoryPlot;
import com.kingdee.bos.ctrl.freechart.chart.plot.Plot;
import com.kingdee.bos.ctrl.freechart.chart.plot.PlotOrientation;
import com.kingdee.bos.ctrl.freechart.chart.renderer.category.GroupedStackedBarRenderer;
import com.kingdee.bos.ctrl.freechart.data.KeyToGroupMap;
import com.kingdee.bos.ctrl.freechart.data.Range;
import com.kingdee.bos.ctrl.freechart.data.category.CategoryDataset;
import com.kingdee.bos.ctrl.freechart.data.category.DefaultCategoryDataset;
import com.kingdee.bos.ctrl.freechart.data.general.Dataset;
import com.kingdee.bos.ctrl.freechart.ui.GradientPaintTransformType;
import com.kingdee.bos.ctrl.freechart.ui.StandardGradientPaintTransformer;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IREAutoRemember;
import com.kingdee.eas.fdc.basedata.REAutoRememberFactory;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleCollection;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleFactory;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleInfo;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportCollection;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportInfo;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.fdc.schedule.framework.client.ScheduleChartProvider;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;

/**
 * output class name
 */
public class DeptMonthlyRateAchievedUI extends AbstractDeptMonthlyRateAchievedUI
{
    private static final Logger logger = CoreUIObject.getLogger(DeptMonthlyRateAchievedUI.class);
    
	public String costCenterName;
    
    /**
     * output class constructor
     */
    public DeptMonthlyRateAchievedUI() throws Exception
    {
        super();
    }
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	if (isFirstOnload()) {
			return;
		}
    	costCenterName = "";
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    	if (node != null && node.getUserObject() instanceof CostCenterOrgUnitInfo) {
			CostCenterOrgUnitInfo cpinfo = (CostCenterOrgUnitInfo) node.getUserObject();
			costCenterName = cpinfo.getName();
		}
    	// yupdate by libing at 2011-9-23 super改用单调
		// super.treeMain_valueChanged(e);
		initChart();
    }
   public void onLoad() throws Exception {
		super.onLoad();
		// 隐藏菜单栏 工具和服务
		menuTool.setVisible(false);
		MenuService.setVisible(false);
		menuItemPrint.setVisible(true);
		menuItemPrintPre.setVisible(true);
		this.windowTitle = "部门月度计划达成率";
		// 锁定表格
		tblMain.getStyleAttributes().setLocked(true);
		// 加载上一次的记忆
		initPreTime();
		// 隐藏累计字段
		tblMain.getColumn("allDone").getStyleAttributes().setHided(true);
		tblMain.getColumn("allRate").getStyleAttributes().setHided(true);
		// 拉长
		tblMain.getColumn("curPlan").setWidth(120);
		tblMain.getColumn("curDone").setWidth(120);
		tblMain.getColumn("curRate").setWidth(120);
		tblMain.getColumn("wellDone").setWidth(120);
		tblMain.getColumn("wellRate").setWidth(120);
		tblMain.getColumn("lateDone").setWidth(120);
		tblMain.getColumn("lateRate").setWidth(120);
		tblMain.getHead().getRow(0).getCell(0).setValue("月份");
	}
    
   private void initPreTime() {
		// 当前用户
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		// 当前组是织
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();

		String userID = user.getId().toString();
		String orgUnitID = orgUnit.getId().toString();
		String function = "DeptMonthlyRateAchievedUI";
		String treeId = null;
		try {
			treeId = REAutoRememberFactory.getRemoteInstance().getValue(userID, orgUnitID, function);
			if (!FDCHelper.isEmpty(treeId)) {
				// DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode();
				// CurProjectInfo info = new CurProjectInfo();
				// info.setId(BOSUuid.read(treeId));
				// node.setUserObject(info);
				// treeMain.setSelectionNode(node);
				TreeModel model = treeMain.getModel();
				DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) model.getRoot();
				Enumeration e = root.depthFirstEnumeration();
				while (e.hasMoreElements()) {
					DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) e.nextElement();
					if (node != null && node.getUserObject() != null && node.getUserObject() instanceof CostCenterOrgUnitInfo) {
						CostCenterOrgUnitInfo info = (CostCenterOrgUnitInfo) node.getUserObject();
						if (info.getId().toString().equals(treeId)) {
							treeMain.setSelectionNode(node);
							costCenterName = info.getName();
							initChart();
						}
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 计划开工日期
	private Date startDate;
	// 计划完工日期
	private Date endDate;

	/**
	 * 初始化图形面板 libing
	 */
	protected void initChart() {
	
		super.initChart();
		cbYear.removeAllItems();
		fillChart();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();

		if (node != null && node.getUserObject() instanceof CostCenterOrgUnitInfo) {
			CostCenterOrgUnitInfo cpinfo = (CostCenterOrgUnitInfo) node.getUserObject();
			if (cpinfo.getId() != null) {
				String longNumber = cpinfo.getId().toString();
				EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("id"));
				sic.add(new SelectorItemInfo("name"));
				// 开工日期
				sic.add(new SelectorItemInfo("scheduleMonth"));
				// 完工日期
				sic.add(new SelectorItemInfo("year"));
				sic.add(new SelectorItemInfo("month"));
				view.setSelector(sic);
				FilterInfo info = new FilterInfo();
				info.getFilterItems().add(new FilterItemInfo("adminDept.id", longNumber, CompareType.EQUALS));
				view.setFilter(info);
				DeptMonthlyScheduleCollection col = null;
				try {
				
					col = DeptMonthlyScheduleFactory.getRemoteInstance().getDeptMonthlyScheduleCollection(view);
					if (col != null) {
						startDate = null;
						endDate = null;
						Set set = new HashSet();
						for (int count = 0; count < col.size(); count++) {
							DeptMonthlyScheduleInfo fdcsinfo = col.get(count);
							set.add(new Integer(fdcsinfo.getYear()));
						}
						Iterator it = set.iterator();
						while (it.hasNext()) {
							cbYear.addItem(it.next());
						}
					}
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					int cur = cal.get(Calendar.YEAR);
					for (int i = 0; i < cbYear.getItemCount(); i++) {
						int year = Integer.parseInt(cbYear.getItemAt(i).toString());
						if (year == cur) {
							cbYear.setSelectedIndex(i);
						}
					}
				
				} catch (BOSException e) {
					e.printStackTrace();
				}

			}
		}
	}
	
	
	// 额外防止一个collection 用于清除重复任务
	private DeptMonthlyScheduleTaskCollection doRep(DeptMonthlyScheduleTaskCollection col) {
		DeptMonthlyScheduleTaskCollection repcol = new DeptMonthlyScheduleTaskCollection();
		for (int m = 0; m < col.size(); m++) {
			DeptMonthlyScheduleTaskInfo old = col.get(m);
			boolean flag = false;
			if (repcol.size() == 0) {
				repcol.add(old);
			} else {
				int x = repcol.size();
				DeptMonthlyScheduleTaskInfo info3 = null;
				DeptMonthlyScheduleTaskInfo info4 = null;
				for (int n = 0; n < x; n++) {
					DeptMonthlyScheduleTaskInfo alreadyhas = repcol.get(n);
					// 任务名称 + 任务来源 + 任务的计划完成日期
					if (old.getTaskName().equals(alreadyhas.getTaskName()) && old.getTaskOrigin().equals(alreadyhas.getTaskOrigin())
							&& old.getPlanFinishDate().equals(alreadyhas.getPlanFinishDate())) {
						flag = true;
						info3 = old;
						info4 = alreadyhas;
					}
				}
				if (flag) {
					// 判断进度
					if (info4.getComplete() == null) {
						repcol.remove(info4);
						repcol.add(old);
					}
					if (info3.getComplete() == null) {
						break;
					}
					if (info3.getComplete() != null && info4.getComplete() != null && info4.getComplete().compareTo(info3.getComplete()) < 0) {
						repcol.remove(info4);
						repcol.add(old);
					}
				} else {
					repcol.add(old);
				}
			}
			
		}

		return repcol;
	}
    /**
     * output cbYear_itemStateChanged method
     */
    protected void cbYear_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
		super.cbYear_itemStateChanged(e);
		// 根据这里的选择去初始化表格数据
		// Date curdate = new Date();
		tblMain.checkParsed();
		tblMain.removeRows();
		Calendar cal = Calendar.getInstance();
		 Object obj = cbYear.getSelectedItem();
		 if (obj == null) {
			return;
		}
		int year = Integer.parseInt(obj.toString());

		cal.set(year, 0, 1);
		// 选择日期的开始
		Date yearBegin = cal.getTime();// 2010-1-1
		cal.set(year, 11, 31);
		// 选择日期的结束
		Date yearEnd = cal.getTime();// 2010-12-31

		// 当月计划完成数
		int[] curPlan = new int[12];
		// 当月实际完成数
		int[] curAct = new int[12];
		// 按时完成数
		int[] aswc = new int[12];
		int[] curPlanUN = new int[12];
		// 延时完成数
		int[] yswc = new int[12];

		// 根据工程项目选中年的全部任务
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		CostCenterOrgUnitInfo info = (CostCenterOrgUnitInfo) node.getUserObject();
		String longN = info.getId().toString();
		DeptMonthlyScheduleTaskCollection col = null;
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		// 实际完工日期
		sic.add(new SelectorItemInfo("name"));
		// 计划完成日期
		sic.add(new SelectorItemInfo("planFinishDate"));
		// 完成情况
		sic.add(new SelectorItemInfo("complete"));
		FilterInfo finfo = new FilterInfo();
		finfo.getFilterItems().add(new FilterItemInfo("schedule.adminDept.id", longN, CompareType.EQUALS));
		// 根据日期过滤出该年的任务
		// finfo.getFilterItems().add(new FilterItemInfo("start", begin,
		// CompareType.GREATER_EQUALS));
		finfo.getFilterItems().add(new FilterItemInfo("planFinishDate", yearEnd, CompareType.LESS_EQUALS));
		view.setFilter(finfo);
		// 得到改工程项目下选中年的所有任务
		DeptMonthlyScheduleTaskCollection col1 = DeptMonthlyScheduleTaskFactory.getRemoteInstance().getDeptMonthlyScheduleTaskCollection(view);
		if (col1 == null) {
			return;
		}
		col = doRep(col1);
		
		for (int i = 0; i < col.size(); i++) {

			DeptMonthlyScheduleTaskInfo info2 = col.get(i);
			Date jhwc = info2.getPlanFinishDate();// 例如8月
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(jhwc);
			int count = cal2.get(Calendar.MONTH);
			int year2 = cal2.get(Calendar.YEAR);
			if (new Integer(year2).compareTo((Integer) cbYear.getSelectedItem()) < 0) {
				continue;
			}
			// 8月份的当月计划完成数加1("计划完成日期在当前月的主项计划")
			curPlan[count] = curPlan[count] + 1;
			if (info2.getComplete() == null) {
				info2.setComplete(new BigDecimal(0));
			}
			if (info2.getComplete().compareTo(new BigDecimal(100)) < 0) {
				// 8分月份的未完成数加1
				curPlanUN[count] = curPlanUN[count] + 1;
			} else {
				// 8月份的实际完成数加1(任务状态为已完成，且计划完成日期在当前月的主项计划)
				curAct[count] = curAct[count] + 1;
				EntityViewInfo eview2 = new EntityViewInfo();
				FilterInfo finfo2 = new FilterInfo();
				SelectorItemCollection sic2 = new SelectorItemCollection();
				sic2.add(new SelectorItemInfo("id"));
				sic2.add(new SelectorItemInfo("realEndDate"));
				sic2.add(new SelectorItemInfo("planEndDate"));
				finfo2.getFilterItems().add(new FilterItemInfo("relateTask.id", info2.getId().toString()));
				finfo2.getFilterItems().add(new FilterItemInfo("progressEdition", Boolean.TRUE));
				eview2.setFilter(finfo2);
				eview2.setSelector(sic2);
				DeptTaskProgressReportCollection repcol = null;
				repcol = DeptTaskProgressReportFactory.getRemoteInstance().getDeptTaskProgressReportCollection(eview2);
				if (repcol != null && repcol.size() > 0) {
					DeptTaskProgressReportInfo deptTaskProgressReportInfo = repcol.get(0);
					if (deptTaskProgressReportInfo.getRealEndDate() != null && deptTaskProgressReportInfo.getPlanEndDate() != null) {
						if (DateUtils.compareUpToDay(deptTaskProgressReportInfo.getRealEndDate(), deptTaskProgressReportInfo.getPlanEndDate()) <= 0) {
							// 按时完成数（实际完成日期小于等于计划完成日期）
							aswc[count] = aswc[count] + 1;
						} else {
							// 延时完成数(实际完成日期大于计划完成日期)
							yswc[count] = yswc[count] + 1;
						}
					} else {
						continue;
					}
				}
			}
		}
		// 加载表格数据
		tblMain.checkParsed();
		tblMain.removeRows();
		cal.setTime(yearBegin);
		int fstart = cal.get(Calendar.MONTH);
		cal.setTime(yearEnd);
		int fend = cal.get(Calendar.MONTH);
		Date date = new Date();
		if (DateUtils.compareUpToDay(date, yearEnd) <= 0) {
			cal.setTime(date);
			fend = cal.get(Calendar.MONTH);
		}
		// 累计计划完成数
		int allPlan = 0;
		int m = 1;
		for (int i = fstart; i <= fend; i++) {
			IRow row = tblMain.addRow();
			// 月份A
			row.getCell("month").setValue((i + 1) + "月");
			// 本月计划完成数B
			row.getCell("curPlan").setValue(new Integer(curPlan[i]));
			allPlan += curPlan[i];
			// 本月实际完成数C
			row.getCell("curDone").setValue(new Integer(curAct[i]));

			
			if (!row.getCell("curPlan").getValue().toString().equals("0")) {
				// 本月完成率D
				row.getCell("curRate").setExpressions("=C" + m + "/" + "B" + m);
			}
			
			// 按时完成数E
			row.getCell("wellDone").setValue(new Integer(aswc[i]));

			
			if (!row.getCell("curPlan").getValue().toString().equals("0")) {
				// 按时完成率F
				row.getCell("wellRate").setExpressions("=E" + m + "/" + "B" + m);
			}
			// 延时完成数G
			row.getCell("lateDone").setValue(new Integer(yswc[i]));

			
			if (!row.getCell("curPlan").getValue().toString().equals("0")) {
				// 延时完成率H
				row.getCell("lateRate").setExpressions("=G" + m + "/" + "B" + m);
			}
			m++;
		}
		fillChart();
    }

    protected void fillChart() {
		pnlChart.removeAll();
		pnlChart.add(createChartPanel(), BorderLayout.CENTER);
		updateUI();
	}

	protected JPanel createChartPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}
	
	protected void initTree() throws Exception {
		super.initTree();
		// 展示成本中心组织
		buildCostCenterTree();
		treeMain.setRootVisible(false);
		treeMain.setShowsRootHandles(true);
		treeMain.expandRow(0);
		treeMain.setSelectionRow(0);
		// 默认展开到最末级
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
	protected ITreeBuilder treeBuilder;

	private void buildCostCenterTree() throws Exception {
		 EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection col = new SelectorItemCollection();
		col.add(new SelectorItemInfo("id"));
		view.setSelector(col);
		FilterInfo info = new FilterInfo();
		// 是否是行政组织
		info.getFilterItems().add(new FilterItemInfo("isAdminOrgUnit", Boolean.TRUE));
		// 是否是成本中心
		info.getFilterItems().add(new FilterItemInfo("isCostOrgUnit", Boolean.TRUE));
		info.getFilterItems().add(new FilterItemInfo("isFreeze", Boolean.FALSE));
		info.getFilterItems().add(new FilterItemInfo("isOUSealUp", Boolean.FALSE));
		// 组织
		Set set2 = new HashSet();
		set2.add("00000000-0000-0000-0000-00000000000362824988");
		// 保留集团名字
		set2.add("00000000-0000-0000-0000-00000000000162824988");
		// 公司
		set2.add("00000000-0000-0000-0000-00000000000262824988");
		
		info.getFilterItems().add(new FilterItemInfo("unitLayerType.id", set2, CompareType.INCLUDE));
		view.setFilter(info);
		AdminOrgUnitCollection adminCol = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitCollection(view);
		Set set = new HashSet();
		if (adminCol != null) {
			for (int i = 0; i < adminCol.size(); i++) {
				AdminOrgUnitInfo adminInfo = adminCol.get(i);
				set.add(adminInfo.getId().toString());
			}
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", set, CompareType.INCLUDE));
		
		// kaishi
		Set authorizedOrgs = new HashSet();
		Map orgs = (Map) ActionCache.get("FDCBillEditUIHandler.authorizedOrgs");
		if (orgs == null) {
			orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
					OrgType.Admin, null, null, null);
		}
		if (orgs != null) {
			Set orgSet = orgs.keySet();
			Iterator it = orgSet.iterator();
			while (it.hasNext()) {
				authorizedOrgs.add(it.next());
			}
		}
		FilterInfo filterID = new FilterInfo();
		filterID.getFilterItems().add(new FilterItemInfo("id", authorizedOrgs, CompareType.INCLUDE));

		filter.mergeFilter(filterID, "and");
		// jiesu
		
		treeBuilder = TreeBuilderFactory.createTreeBuilder(getTempLNTreeNodeCtrl(), 50, 5, filter, new SelectorItemCollection());
		treeBuilder.buildTree(treeMain);
	}

	private ILNTreeNodeCtrl getTempLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(CostCenterOrgUnitFactory.getRemoteInstance());
	}
	
    /**
	 * 初始话表格
	 */
	protected void initTable() {
		super.initTable();
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
		// return super.createDataset();
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		int count = tblMain.getRowCount();
		for (int i = 0; i < count; i++) {
			 if (tblMain.getRow(i).getCell("wellRate").getValue() == null) {
				result.addValue(0, "当月达成率 (按时完成)", tblMain.getRow(i).getCell("month").getValue().toString());
			} else {
				result.addValue(Double.parseDouble(tblMain.getRow(i).getCell("wellRate").getValue().toString()) * 100, "当月达成率 (按时完成)", tblMain
						.getRow(i).getCell("month").getValue().toString());
			}
			if (tblMain.getRow(i).getCell("lateRate").getValue() == null) {
				result.addValue(0, "当月达成率 (延时完成)", tblMain.getRow(i).getCell("month").getValue().toString());
			} else {
				result.addValue(Double.parseDouble(tblMain.getRow(i).getCell("lateRate").getValue().toString()) * 100, "当月达成率 (延时完成)", tblMain
						.getRow(i).getCell("month").getValue().toString());
			}
		}
		return result;
	}

	protected int getChartHeight() {
		return getHeight() - 200;
	}

	/**
	 * 返回图标
	 */
	protected JFreeChart createChart(Dataset dataset) {
		// 年份+工程项目+部门月度计划达成率
		String title = "部门月度计划达成率";
		if (!FDCHelper.isEmpty(costCenterName)) {
			title = costCenterName + " " + title;
		}
		if (!FDCHelper.isEmpty(cbYear.getSelectedItem())) {
			title = cbYear.getSelectedItem().toString() + " " + title;
		}
		JFreeChart chart = ChartFactory.createStackedBarChart(title, // 标题
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
		Paint p1 = new GradientPaint(0.0f, 0.0f, new Color(149, 255, 149), 0.0f, 0.0f, new Color(89, 183, 89));
		renderer.setSeriesPaint(0, p1);

		Paint p2 = new GradientPaint(0.0f, 0.0f, new Color(255, 200, 200), 20.0f, 0.0f, new Color(255, 145, 145));
		renderer.setSeriesPaint(1, p2);

		Paint p3 = new GradientPaint(0.0f, 0.0f, new Color(109, 142, 255), 0.0f, 0.0f, new Color(49, 82, 123));
		renderer.setSeriesPaint(2, p3);

		Paint p4 = new GradientPaint(0.0f, 0.0f, new Color(255, 170, 170), 20.0f, 0.0f, new Color(255, 45, 45));
		renderer.setSeriesPaint(3, p4);
		// 颜色渐变方向，此处为中心渐变
		renderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_HORIZONTAL));

		// 鼠标停留在柱状图内显示提示信息格式
		renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator("({0}, {1}) = {2}%", NumberFormat.getInstance()));
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
		NumberAxis rangeAxis = new NumberAxis("达成率(%)") {
			public void setRange(Range range, boolean turnOffAutoRange, boolean notify) {
				range = new Range(0, 110);
				super.setRange(range, turnOffAutoRange, notify);
			}
		};
		// 纵轴，小数类型
		rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
		rangeAxis.setUpperMargin(0.10);
		rangeAxis.setLowerBound(0.0);
		rangeAxis.setUpperBound(110.0);
		rangeAxis.setTickUnit(new NumberTickUnit(5.0));
		plot.setRangeAxis(rangeAxis);

		// 最底下说明框
		plot.setFixedLegendItems(createLegendItems());

		return chart;
	}

	private static LegendItemCollection createLegendItems() {
		LegendItemCollection result = new LegendItemCollection();
		LegendItem item1 = new LegendItem("当月按时完成", "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(89, 183, 89));
		LegendItem item2 = new LegendItem("当月延时完成", "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(255, 105, 105));

		result.add(item1);
		result.add(item2);
		return result;
	}

	// 打印
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getTDName(), getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDPath(), dataPvd, SwingUtilities.getWindowAncestor(this));
	}

	// 打印预览
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionPrintPreview_actionPerformed(e);
	}

	protected String getTDPath() {
		// TODO Auto-generated method stub
		return super.getTDPath();
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
		int fitWidth = 2 + tblMain.getIndexColumn().getWidth() + tblMain.getColumns().getWidth();
		// sptLeft
		// .setDividerLocation(curDivLeft
		// + (tblMain.getWidth() - fitWidth));
		// 计算表格最合适高度(2个像素的边界+表头高+所有行高和)
		int fitHeight = 2 + tblMain.getHead().getHeight() + tblMain.getBody().getHeight();
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
		BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img2.getGraphics();
		tblMain.paintAll(g);
		g.dispose();

		// 4、新建img，将img1等比缩放成宽度与img2一样，并画在img的上方
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
		// sptLeft.setDividerLocation(curDivLeft);
		// sptTop.setDividerLocation(curDivTop);
		if (curSelect != null) {
			tblMain.getSelectManager().select(curSelect);
		}

		// 7、返回img
		return img;
	}

	protected BufferedImage getChartIMG() {
		// TODO Auto-generated method stub
		return super.getChartIMG();
	}
	public void actionExportIMG_actionPerformed(ActionEvent e) throws Exception {
		tblMain.checkParsed();
		if (tblMain.getRowCount() <= 0) {
			FDCMsgBox.showInfo("没有相应的图表，无需导出");
			abort();
		}
		super.actionExportIMG_actionPerformed(e);
	}
	// add by libing at 2011-09-14
	public boolean destroyWindow() {
		try {
			IREAutoRemember autoRemember = REAutoRememberFactory.getRemoteInstance();
			// 当前用户
			UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
			// 当前组是织
			OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			CostCenterOrgUnitInfo costCenterOrgUnitInfo = null;
			if (node != null && node.isLeaf() && node.getUserObject() instanceof CostCenterOrgUnitInfo) {
				costCenterOrgUnitInfo = (CostCenterOrgUnitInfo) node.getUserObject();
			}
			// 工程项目
			Object[] objs = new Object[] { user, orgUnit, costCenterOrgUnitInfo };
			if (exits(objs)) {
				String userID = user.getId().toString();
				String orgUnitID = orgUnit.getId().toString();
				String curProjectID = costCenterOrgUnitInfo.getId().toString();
				autoRemember.save(userID, orgUnitID, "DeptMonthlyRateAchievedUI", curProjectID);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

		return super.destroyWindow();
	}

	/**
	 * @description 数组中的值是否存在
	 * @author 杜红明
	 * @createDate 2011-8-25 void
	 * @version EAS7.0
	 * @see
	 */

	private boolean exits(Object[] objs) {
		for (int i = 0; i < objs.length; i++) {
			if (FDCHelper.isEmpty(objs[i])) {
				return false;
			}
		}
		return true;
	}
	
	
	
}