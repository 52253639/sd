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
import java.util.Iterator;

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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IREAutoRemember;
import com.kingdee.eas.fdc.basedata.REAutoRememberFactory;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ProjectSpecialCollection;
import com.kingdee.eas.fdc.schedule.ProjectSpecialFactory;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.fdc.schedule.framework.client.ScheduleChartProvider;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 描述： 专项达成率
 * @author 李兵
 * @version EAS7.0
 * @createDate 2011-9-2
 * @see
 */
public class SpecialRateAchievedUI extends AbstractSpecialRateAchievedUI
{
    private static final Logger logger = CoreUIObject.getLogger(SpecialRateAchievedUI.class);
    
    public String projectSpecialName;
    
    /**
     * output class constructor
     */
    public SpecialRateAchievedUI() throws Exception
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
    public void onLoad() throws Exception {
		super.onLoad();
		// 隐藏菜单栏 工具和服务
		menuTool.setVisible(false);
		MenuService.setVisible(false);
		menuItemPrint.setVisible(true);
		menuItemPrintPre.setVisible(true);
		tblMain.getStyleAttributes().setLocked(true);
		// 加载上一次的记忆
		initPreTime();
		tblMain.checkParsed();
		tblMain.getHead().getRow(0).getCell(0).setValue("月份");

	}

	private void initPreTime() {
		// 当前用户
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		// 当前组是织
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();

		String userID = user.getId().toString();
		String orgUnitID = orgUnit.getId().toString();
		String function = "SpecialAchievedUI";
		 String functionYear = "SpecialAchievedUIYear";
		String treeId = null;
		 String preyear = null;
		try {
			treeId = REAutoRememberFactory.getRemoteInstance().getValue(userID, orgUnitID, function);
			 preyear = REAutoRememberFactory.getRemoteInstance().getValue(userID, orgUnitID, functionYear);
			if (!FDCHelper.isEmpty(treeId)) {
				// DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode();
				// CurProjectInfo info = new CurProjectInfo();
				// info.setId(BOSUuid.read(treeId));
				// node.setUserObject(info);
				TreeModel model = treeMain.getModel();
				DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) model.getRoot();
				Enumeration e = root.depthFirstEnumeration();
				while (e.hasMoreElements()) {
					DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) e.nextElement();
					if (node != null && node.getUserObject() != null && node.getUserObject() instanceof ProjectSpecialInfo) {
						ProjectSpecialInfo info = (ProjectSpecialInfo) node.getUserObject();
						if (info.getId().toString().equals(treeId)) {
							treeMain.setSelectionNode(node);
							projectSpecialName = info.getName();
							initChart();
							if (!FDCHelper.isEmpty(preyear)) {
								cbYear.setSelectedItem(new Integer(preyear));
							}
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
    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	if (isFirstOnload()) {
			return;
		}
    	projectSpecialName = "";
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.isLeaf() && node.getUserObject() instanceof ProjectSpecialInfo) {
			// 项目专项
			ProjectSpecialInfo cpinfo = (ProjectSpecialInfo) node.getUserObject();
			projectSpecialName = cpinfo.getName();
		}
    	// yupdate by libing at 2011-9-23 super改用单调
		// super.treeMain_valueChanged(e);
		initChart();
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
		// 当下来框有数据的时候，调用该方法会触发下拉框改变事件，本来就不存在数据的时候，调用该方法，不会触发下拉框改变事件
			cbYear.removeAllItems();
			fillChart();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();

		if (node != null && node.isLeaf() && node.getUserObject() instanceof ProjectSpecialInfo) {
			// 项目专项
			ProjectSpecialInfo cpinfo = (ProjectSpecialInfo) node.getUserObject();
			if (cpinfo.getId() != null) {
				String id = cpinfo.getId().toString();
				EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("id"));
				sic.add(new SelectorItemInfo("name"));
				// 开工日期
				sic.add(new SelectorItemInfo("startDate"));
				// 完工日期
				sic.add(new SelectorItemInfo("endDate"));
				view.setSelector(sic);
				FilterInfo info = new FilterInfo();
				info.getFilterItems().add(new FilterItemInfo("projectSpecial.id", id));
				info.getFilterItems().add(new FilterItemInfo("isLatestVer", Boolean.TRUE));

				view.setFilter(info);
				FDCScheduleCollection col = null;
				try {
					col = FDCScheduleFactory.getRemoteInstance().getFDCScheduleCollection(view);
					// 工程项目和计划是一对一的关系，加上最新版本只会一条数据，所以这里不用while循环
					if (col != null && col.size() > 0) {
						FDCScheduleInfo fdcsinfo = col.get(0);
						// 计划开工日期
						startDate = fdcsinfo.getStartDate();
						// 计划完工日期
						endDate = fdcsinfo.getEndDate();
						Date nowdate = new Date();
						// 应该去当前日期月份的最后一天
						Date endOfMonth = DateUtils.endOfMonth(nowdate);
						if (endOfMonth.before(endDate)) {
							// 目前日期比计划的早得完，只显示到当今日期~
							endDate = endOfMonth;
						}
						// if (nowdate.before(endDate)) {
						// // 目前日期比计划的早得完，只显示到当今日期~
						// endDate = nowdate;
						// }
						// 当前年
						Calendar cal = Calendar.getInstance();
						cal.setTime(startDate);
						int m = cal.get(Calendar.YEAR);
						cal.setTime(endDate);
						int n = cal.get(Calendar.YEAR);
						for (int i = m; i <= n; i++) {
							// 如果都是2008年
							cbYear.addItem(new Integer(i));
						}
						Date curDate = new Date();
						cal.setTime(curDate);
						int cur = cal.get(Calendar.YEAR);
						for (int i = 0; i < cbYear.getItemCount(); i++) {
							int year = Integer.parseInt(cbYear.getItemAt(i).toString());
							if (year == cur) {
								cbYear.setSelectedIndex(i);
							}
						}
					}
				} catch (BOSException e) {
					e.printStackTrace();
				}

			}
		}
	}
    
    /**
     * output cbYear_itemStateChanged method
     */
    protected void cbYear_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {

		// super.cbYear_itemStateChanged(e);
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
		Date planBegin = startDate;// 计划开始
		Date planEnd = endDate;// 计划结束

		Date begin = yearBegin.compareTo(planBegin) > 0 ? yearBegin : planBegin;

		Date end = yearEnd.compareTo(planEnd) < 0 ? yearEnd : planEnd;
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
		if (node != null && node.getUserObject() instanceof ProjectSpecialInfo) {
			ProjectSpecialInfo info = (ProjectSpecialInfo) node.getUserObject();
			FDCScheduleTaskCollection col = null;
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			// 实际完工日期
			sic.add(new SelectorItemInfo("actualEndDate"));
			// 实际开始日期
			sic.add(new SelectorItemInfo("actualStartDate"));
			// 计划开始日期
			sic.add(new SelectorItemInfo("start"));
			// 计划完成日期
			sic.add(new SelectorItemInfo("end"));
			// 完成情况
			sic.add(new SelectorItemInfo("complete"));
			// 是否叶节点
			sic.add(new SelectorItemInfo("isLeaf"));
			view.setSelector(sic);
			FilterInfo finfo = new FilterInfo();
			finfo.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial.id", info.getId().toString()));
			// 根据日期过滤出该年的任务
			// finfo.getFilterItems().add(new FilterItemInfo("start", begin,
			// CompareType.GREATER_EQUALS));
			finfo.getFilterItems().add(new FilterItemInfo("end", end, CompareType.LESS_EQUALS));
			// add by libing at 2011-10-12 过滤出最新计划的任务
			finfo.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
			//BT737384bug：这三个达成率中将非明细任务也统计出来了；
			//要求：这三张报表只需要统计最明细任务就可以了
			finfo.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
			view.setFilter(finfo);
			// 得到改工程项目下选中年的所有任务
			col = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
			for (int i = 0; i < col.size(); i++) {
				FDCScheduleTaskInfo info2 = col.get(i);
				Date jhwc = info2.getEnd();// 例如8月

				// update(add) by libing at 2011-10-13
				Object o = cbYear.getSelectedItem();
				int y = Integer.parseInt(o.toString());
				Calendar c1 = Calendar.getInstance();
				c1.set(y, 0, 1);
				int diffday = DateUtils.compareUpToDay(info2.getEnd(), c1.getTime());
				if (diffday < 0) {
					continue;
				}
				// 以上为13号修改内容
				
				cal.setTime(jhwc);
				int count = cal.get(Calendar.MONTH);
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
					if (info2.getActualEndDate() != null) {
						// if
						// (info2.getActualEndDate().compareTo(info2.getEnd())
						// <= 0) {
						// // 按时完成数（实际完成日期小于等于计划完成日期）
						// aswc[count] = aswc[count] + 1;
						// } else {
						// // 延时完成数(实际完成日期大于计划完成日期)
						// yswc[count] = yswc[count] + 1;
						// }
						if (DateUtils.compareUpToDay(info2.getActualEndDate(), info2.getEnd()) <= 0) {
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
			// 加载表格数据
			tblMain.checkParsed();
			tblMain.removeRows();
			cal.setTime(begin);
			int fstart = cal.get(Calendar.MONTH);
			cal.setTime(end);
			int fend = cal.get(Calendar.MONTH);
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
				// 累计计划完成数I
				// row.getCell("allPlan").setValue(new Integer(allPlan));
				// 累计计划完成数:默认隐藏,"计划完成日期"在当前月之前(包含历史年份，包含当前月)的主项任务.
				Calendar cc = Calendar.getInstance();
				Object oo = cbYear.getSelectedItem();
				int yy = Integer.parseInt(oo.toString());
				cc.set(yy, i + 1, 1, 0, 0, 0);
				Date d = cc.getTime();
				EntityViewInfo ee = new EntityViewInfo();
				FilterInfo ff = new FilterInfo();
				ff.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial.id", info.getId().toString()));
				// 计划结束日期早于等于当前年月
				ff.getFilterItems().add(new FilterItemInfo("end", d, CompareType.LESS));
				ff.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
				//BT737384bug：这三个达成率中将非明细任务也统计出来了；
				//要求：这三张报表只需要统计最明细任务就可以了
				ff.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
				ee.setFilter(ff);
				FDCScheduleTaskCollection fstc = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(ee);
				if (fstc != null) {
					row.getCell("allPlan").setValue(new Integer(fstc.size()));
				}
				// 累计实际完成数
				int allDone = 0;
				// 累计按时完成数
				int allWellDone = 0;
				// 累计延时完成数
				int allLateDone = 0;
				int equals = 0;
				for (int co = 0; co < fstc.size(); co++) {
					FDCScheduleTaskInfo scheduleTaskInfo = fstc.get(co);
					// 除去实际完成时间在当月以前的任务(累计时不显示提前完成的)上面过滤了一次，但是好像因为sql和util的不一样，
					// 所以没过滤到
					// 因此在这里在次过滤
					if (DateUtils.compareUpToDay(scheduleTaskInfo.getEnd(), d) >= 0) {
						equals++;
						continue;
					}
					if (scheduleTaskInfo.getComplete() == null) {
						scheduleTaskInfo.setComplete(new BigDecimal(0));
					}
					// 未完成
					if (scheduleTaskInfo.getComplete().compareTo(new BigDecimal(100)) < 0) {

					} else {
						// 已完成
						allDone += 1;
						// if (scheduleTaskInfo.getActualEndDate() != null &&
						// scheduleTaskInfo.getEnd() != null) {
						// if (scheduleTaskInfo.getActualEndDate().compareTo(
						// scheduleTaskInfo.getEnd()) <= 0) {
						// allWellDone += 1;
						// } else {
						// allLateDone += 1;
						// }
						// }
						if (DateUtils.compareUpToDay(scheduleTaskInfo.getActualEndDate(), scheduleTaskInfo.getEnd()) <= 0) {
							allWellDone += 1;
						} else {
							allLateDone += 1;
						}

					}
				}
				if (fstc != null) {
					/**
					 * 再次设置累计完成数的值<br>
					 * 同样要修改专项，公司，考核，里程碑达成率的问题<br>
					 * 累计计划完成数
					 */
					row.getCell("allPlan").setValue(new Integer(new Integer(fstc.size()).intValue() - equals));

				}
				// 累计实际完成数J
				row.getCell("allDone").setValue(new Integer(allDone));
				if (!row.getCell("allPlan").getValue().toString().equals("0")) {
					// 累计完成率K
					row.getCell("allRate").setExpressions("=J" + m + "/" + "I" + m);
				}
				// 累计按时完成数L
				row.getCell("allWellDone").setValue(new Integer(allWellDone));
				if (!row.getCell("allPlan").getValue().toString().equals("0")) {
					// 累计按时完成率M
					row.getCell("allWellRate").setExpressions("=L" + m + "/" + "I" + m);
				}
				// 累计延时完成数N
				row.getCell("allLateDone").setValue(new Integer(allLateDone));
				if (!row.getCell("allPlan").getValue().toString().equals("0")) {
					// 累计延时完成率O
					row.getCell("allLateRate").setExpressions("=N" + m + "/" + "I" + m);
				}
				m++;
			}
			fillChart();
		}
		
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

	/**
	 * 初始化左边树 add by libing
	 */
	protected void initTree() throws Exception {
		super.initTree();
		 ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		// ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		treeMain.setShowsRootHandles(true);
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		
		TreeNode node = (TreeNode) this.treeMain.getModel().getRoot();
		addSpecial(node);

	}

	private void addSpecial(TreeNode node) throws Exception {
		
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode childAt = (DefaultKingdeeTreeNode) node.getChildAt(i);
			if (childAt.getChildCount() == 0 && childAt.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo proj = (CurProjectInfo) childAt.getUserObject();
				if (proj != null) {
					String id = proj.getId().toString();
					ProjectSpecialCollection col = getSpecial(id);
					for (Iterator iter = col.iterator(); iter.hasNext();) {
						ProjectSpecialInfo element = (ProjectSpecialInfo) iter.next();
						// DefaultKingdeeTreeNode productTypeNode = new
						// DefaultKingdeeTreeNode(
						// element.get);
						childAt.add(new DefaultKingdeeTreeNode(element));

					}
				}
			} else {
				addSpecial(childAt);
			}
		}
	}
	// 项目专项
	private ProjectSpecialCollection getSpecial(String projId) throws Exception {
		ProjectSpecialCollection coll = new ProjectSpecialCollection();
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("curProject.id", projId));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		// view.getSelector().add("productType.id");
		// view.getSelector().add("productType.number");
		// view.getSelector().add("productType.name");
		coll = ProjectSpecialFactory.getRemoteInstance().getProjectSpecialCollection(view);
		return coll;
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
			if (tblMain.getRow(i).getCell("allWellRate").getValue() == null) {
				result.addValue(0, "累计达成率 (按时完成)", tblMain.getRow(i).getCell("month").getValue().toString());
			} else {
				result.addValue(Double.parseDouble(tblMain.getRow(i).getCell("allWellRate").getValue().toString()) * 100, "累计达成率 (按时完成)", tblMain
						.getRow(i).getCell("month").getValue().toString());
			}
			if (tblMain.getRow(i).getCell("allLateRate").getValue() == null) {
				result.addValue(0, "累计达成率 (延时完成)", tblMain.getRow(i).getCell("month").getValue().toString());
			} else {
				result.addValue(Double.parseDouble(tblMain.getRow(i).getCell("allLateRate").getValue().toString()) * 100, "累计达成率 (延时完成)", tblMain
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
		// 年份+项目专项+专项计划达成率
		String title = "专项计划达成率";
		if (!FDCHelper.isEmpty(projectSpecialName)) {
			title = projectSpecialName + " " + title;
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
		// 纵轴，小数类型
		NumberAxis rangeAxis = new NumberAxis("达成率(%)") {
			public void setRange(Range range, boolean turnOffAutoRange, boolean notify) {
				range = new Range(0, 110);
				super.setRange(range, turnOffAutoRange, notify);
			}
		};
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
		LegendItem item3 = new LegendItem("累计按时完成", "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(49, 82, 123));
		LegendItem item4 = new LegendItem("累计延时完成", "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(255, 45, 45));
		result.add(item1);
		result.add(item2);
		result.add(item3);
		result.add(item4);
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
	
	
	public boolean destroyWindow() {
		try {
			IREAutoRemember autoRemember = REAutoRememberFactory.getRemoteInstance();
			// 当前用户
			UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
			// 当前组是织
			OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			ProjectSpecialInfo projectSpecial = null;
			if (node != null && node.isLeaf() && node.getUserObject() instanceof ProjectSpecialInfo) {
				projectSpecial = (ProjectSpecialInfo) node.getUserObject();
			}
			// 工程项目
			Object[] objs = new Object[] { user, orgUnit, projectSpecial };
			if (exits(objs)) {
				String userID = user.getId().toString();
				String orgUnitID = orgUnit.getId().toString();
				String curProjectID = projectSpecial.getId().toString();
				autoRemember.save(userID, orgUnitID, "SpecialAchievedUI", curProjectID);
				if (!FDCHelper.isEmpty(cbYear.getSelectedItem())) {
					autoRemember.save(userID, orgUnitID, "SpecialAchievedUIYear", cbYear.getSelectedItem().toString());
				}
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