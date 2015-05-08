/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.tree.TreeModel;

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
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IREAutoRemember;
import com.kingdee.eas.fdc.basedata.REAutoRememberFactory;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.schedule.OpReportBaseHelper;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.framework.client.KDTaskStatePanel;

/**
 * output class name
 */
public class ViewProjectReportBaseUI extends AbstractViewProjectReportBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(ViewProjectReportBaseUI.class);
    private Map data = null;
    
    /**
     * output class constructor
     */
    public ViewProjectReportBaseUI() throws Exception
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
    	fetchData();
		super.onLoad();
		 initUI();
		fillData();
		initPreTime();
	}

    protected DefaultKingdeeTreeNode DefaultNode;
	private void initPreTime() {
		// 当前用户
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		// 当前组是织
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();

		String userID = user.getId().toString();
		String orgUnitID = orgUnit.getId().toString();
		String treeId = null;
		try {
			treeId = REAutoRememberFactory.getRemoteInstance().getValue(userID, orgUnitID, getFuncation(getUIName()));
			if (!FDCHelper.isEmpty(treeId)) {
				TreeModel model = treeMain.getModel();
				DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) model.getRoot();
				Enumeration e = root.depthFirstEnumeration();
				while (e.hasMoreElements()) {
					DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) e.nextElement();
					if (node != null && node.getUserObject() != null && node.getUserObject() instanceof CurProjectInfo) {
						CurProjectInfo info = (CurProjectInfo) node.getUserObject();
						if (info.getId().toString().equals(treeId)) {
							treeMain.setSelectionNode(node);
							DefaultNode = node;
							return;
						}
					}
					if (node != null && node.getUserObject() != null && node.getUserObject() instanceof ProjectSpecialInfo) {
						ProjectSpecialInfo info = (ProjectSpecialInfo) node.getUserObject();
						if (info.getId().toString().equals(treeId)) {
							treeMain.setSelectionNode(node);
							DefaultNode = node;
							return;
						}
					}
					if (node != null && node.getUserObject() != null && node.getUserObject() instanceof AdminOrgUnitInfo) {
						AdminOrgUnitInfo info = (AdminOrgUnitInfo) node.getUserObject();
						if (info.getId().toString().equals(treeId)) {
							treeMain.setSelectionNode(node);
							DefaultNode = node;
							return;
						}
					}
					if (node != null && node.getUserObject() != null && node.getUserObject() instanceof CostCenterOrgUnitInfo) {
						CostCenterOrgUnitInfo info = (CostCenterOrgUnitInfo) node.getUserObject();
						if (info.getId().toString().equals(treeId)) {
							treeMain.setSelectionNode(node);
							DefaultNode = node;
							return;
						}
					}
					if (node != null && node.getUserObject() != null && node.getUserObject() instanceof CompanyOrgUnitInfo) {
						CompanyOrgUnitInfo info = (CompanyOrgUnitInfo) node.getUserObject();
						if (info.getId().toString().equals(treeId)) {
							treeMain.setSelectionNode(node);
							DefaultNode = node;
							return;
						}
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /*
     *将Map中的数据填充到UI
     *TABLE等 
     */
    public void fillData() {
		
		
	}

	/*
     * 从服务器一次获取所有所需要的数据
     * 将数据存储到全局变量map中
     * 
     * 注意：
     * 1、这里有可能是从计划执行界面调用，如果是从计划执行界面调用，不需要进行重服务器取数
     * 从计划执行界面进行调用时，从UIContext可以把报告的对象传递到本界面
     * 然后再进行图形化，及数据的展示
     * 
     */
	public void fetchData() {
		
		
	}
	/**
	 * 树选择改变
	 * 
	 * 1、需要根据所选项目重新初始化选择控件的值
	 * 2、默认暂时最近一份项目报告
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {

	}

	/**
	 * 年份选择改变
	 * 需要重新从服务器取数
	 */
	protected void cbYear_itemStateChanged(java.awt.event.ItemEvent e)
			throws Exception {
	}

	/**
	 * 初始化界面<br>
	 * 分为几步：<br>
	 * 1、设置树面板标题，初始化树<br>
	 * 2、设置图像面板标题，初始化年份<br>
	 * 3、初始化表格
	 * 4、如果是从计划执行界面调用，需要隐藏树，需要隐藏周期选择的控件
	 * 
	 * 
	 * @throws Exception
	 */
	protected void initUI() throws Exception {
		this.sptTop.setDividerLocation(100);
		this.sptDown.setDividerLocation(200);
		initTree();
		// initChart();
		initTable();
	}


	/**
	 * 初始化树<br>
	 * 可以构建一个成本中心或者工程项目树
	 */
	protected void initTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		treeMain.setShowsRootHandles(true);
		treeMain.expandRow(0);
	}

	/**
	 * 初始化图形面板<br>
	 * 包括设置年份下拉框值，设置分隔容器上区域高度等<br>
	 * 注意填充图表数据不在此完成
	 */
	protected void initChart() {
		pnlChart.add(createChartPanel(), BorderLayout.CENTER);
	}

	/**
	 * 初始化表格<br>
	 * 表格设置基本在元数据完成<br>
	 */
	protected void initTable() {
		KDTaskStatePanel pnlDesc = new KDTaskStatePanel();
		contThis.getContentPane().add(pnlDesc, BorderLayout.SOUTH);
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
		DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("按时完成", new Double(43.2));
        result.setValue("延时完成", new Double(10.0));
        result.setValue("带确认", new Double(17.5));
        result.setValue("延迟未完成", new Double(32.5));
        
        
        return result;
	}
	/*
	 * 获取图片的Title
	 */
	public String getChartTitle() {
		// TODO Auto-generated method stub
		return "YYYY年第WW周周报";
	}
	public void onShow() throws Exception {
		super.onShow();
		sptTop.setDividerLocation(450);
		sptDown.setDividerLocation(250);
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
		plot
				.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}\n({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
		plot.setStartAngle(0);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setCircular(true);
		plot.setOutlineStroke(new BasicStroke(0));// 边框粗细
		plot.setOutlinePaint(Color.white);// 边框颜色
		plot.setLabelFont(new Font("黑体", Font.PLAIN, 10)); 
		plot.setForegroundAlpha(0.75f);
		plot.setBackgroundPaint(Color.white);
		 //设置突出显示的数据块         
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
		plot.setSectionPaint("待确认", Color.ORANGE);
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
	
	/**
	 * @description 关闭窗口自动记忆最后选择的工程项目
	 * @author 杜红明
	 * @createDate 2011-10-25
	 * @version EAS7.0
	 * @see
	 */

	public boolean destroyWindow() {
		try {
			DefaultKingdeeTreeNode lastSelectedNode = getLastSelectedNode();
			if (lastSelectedNode == null) {
				return super.destroyWindow();
			}
			CurProjectInfo curProject = null;
			ProjectSpecialInfo projectSpecial = null;
			AdminOrgUnitInfo adminOrgUnit = null;
			CostCenterOrgUnitInfo costCenterUnit = null;
			CompanyOrgUnitInfo companyUnit = null;
			Object obj = lastSelectedNode.getUserObject();
			if (lastSelectedNode != null) {
				if (obj instanceof ProjectSpecialInfo) {
					projectSpecial = (ProjectSpecialInfo) obj;
				} else if (obj instanceof CurProjectInfo) {
					curProject = (CurProjectInfo) obj;
				} else if (obj instanceof AdminOrgUnitInfo) {
					adminOrgUnit = (AdminOrgUnitInfo) obj;
				} else if (obj instanceof CostCenterOrgUnitInfo) {
					costCenterUnit = (CostCenterOrgUnitInfo) obj;
				} else if (obj instanceof CompanyOrgUnitInfo) {
					companyUnit = (CompanyOrgUnitInfo) obj;
				} else {
					return super.destroyWindow();
				}
					
				IREAutoRemember autoRemember = REAutoRememberFactory.getRemoteInstance();
				UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
				OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
				Object[] objs = new Object[] { user, orgUnit };
				if (exits(objs)) {
					String userID = user.getId().toString();
					String orgUnitID = orgUnit.getId().toString();
					String function = getFuncation(getUIName());
					String value = null;
					if (curProject != null && projectSpecial == null) {
						value = curProject.getId().toString();
					} else if (projectSpecial != null) {
						value = projectSpecial.getId().toString();
					} else if (adminOrgUnit != null) {
						value = adminOrgUnit.getId().toString();
					} else if (costCenterUnit != null) {
						value = costCenterUnit.getId().toString();
					} else if (companyUnit != null) {
						value = companyUnit.getId().toString();
					}
					if (value != null) {
						autoRemember.save(userID, orgUnitID, function, value);
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return super.destroyWindow();
	}

	private String getFuncation(String function) {
		if (getUIName().equals(ViewProjectWeekReportUI.class.getName())) {
			function = "ViewProjectWeekReportProject";
		}
		if (getUIName().equals(ViewProjectMonthReportUI.class.getName())) {
			function = "ViewProjectMonthReportUI";
		}
		if (getUIName().equals(ViewCompanyMonthReportUI.class.getName())) {
			function = "ViewCompanyMonthReportUI";
		}
		if (getUIName().equals(ViewDepartMonthReportUI.class.getName())) {
			function = "ViewDepartMonthReportUI";
		}
		return function;
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

	protected DefaultKingdeeTreeNode getLastSelectedNode() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		return node;
	}
	
	// TODO 由于计划执行那边的数据周少了一,所以还需要修改
	protected int getCurrWeekOfYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.WEEK_OF_YEAR) - 1;
	}

	protected int getCurrYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR);
	}
	
	protected int getCurrMonthOfYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.MONTH) + 1;
	}
	
	protected void setDefalutYearSelected() throws BOSException {
		// 初始加载当前周的数据 modify by duhongming
		String currSelectString = getCompareStr();
		
		for (int index = 0; index < cbYear.getItemCount(); index++) {
			String item = cbYear.getItemAt(index).toString();
			if (item.equals(currSelectString)) {
				cbYear.setSelectedItem(currSelectString);
				return;
			}
		}
	}
    
	private String getCompareStr() throws BOSException {
		Date currDate = FDCDateHelper.getServerTimeStamp();
		int year = OpReportBaseHelper.getDateFiled(currDate, Calendar.YEAR);
		int week = OpReportBaseHelper.getDateFiled(currDate, Calendar.WEEK_OF_YEAR);
		int month = OpReportBaseHelper.getDateFiled(currDate, Calendar.MONTH) + 1;
		
		if (getUIName().equals(ViewProjectWeekReportUI.class.getName())) {
			return year + "-" + (week < 10 ? "0" + week : week);
		}
		return year + "-" + (month < 10 ? "0" + month : month);
	}
	
	protected String getUIName() {
		return "";
	}
	
	protected String getTDPath() {
		return "/bim/fdc/process/processProjectbill";
	}
}