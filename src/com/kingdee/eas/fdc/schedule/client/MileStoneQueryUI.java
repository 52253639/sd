/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.TaskEvaluationBillCollection;
import com.kingdee.eas.fdc.schedule.TaskEvaluationBillFactory;
import com.kingdee.eas.fdc.schedule.TaskEvaluationBillInfo;
import com.kingdee.eas.fdc.schedule.TaskEvaluationTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.fdc.schedule.framework.client.KDTaskStatePanel;
import com.kingdee.eas.fdc.schedule.util.subway.KDSubwayItemRenderer;
import com.kingdee.eas.fdc.schedule.util.subway.KDSubwayItemState;
import com.kingdee.eas.fdc.schedule.util.subway.KDSubwayMap;
import com.kingdee.eas.fdc.schedule.util.subway.TestRenderer;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * 里程碑计划的UI界面。提供地铁图的的展示，列表的显示。
 */

public class MileStoneQueryUI extends AbstractMileStoneQueryUI {
	// 勾
	private String achieve = "√";
	// 圈
	private String pending = "○";
	// 叉
	private String fault = "×";
	// 待确定
	private String unSure = "?";
	// 待确定
	private String projectId = " ";
	// 红
	private Color RED = new Color(255, 0, 0);
	// 绿
	private Color GREEN = new Color(146, 208, 80);
	// 黄
	private Color ORANGE = new Color(255, 255, 0);
	// 粉
	private Color PINK = new Color(230, 185, 184);
	// 蓝
	private Color BLUE = new Color(79, 129, 189);
	// 茶色
	private Color TAN = new Color(196, 189, 151);

	/** 考核版 */
	public static final String evaluationEdition = "01";
	/** 执行版 */
	public static final String executEdition = "02";
	
	private String fdcParamValue = "";

	// 地铁图构造面板
	private JComponent comptDetail = null;
	// 说明面板
	private JPanel pnlDesc;
	
	// 获取说明面板
	public JPanel getPnlDesc() {
		return pnlDesc;
	}
	// 设置说明面板
	public void setPnlDesc(JPanel pnlDesc) {
		this.pnlDesc = pnlDesc;
	}

	// 弹出窗口
	private JDialog dlgDetail = null;

	// 渲染器
	private KDSubwayItemRenderer renderer = null;
	// 明细值
	private Object detail = null;
	
	//	获取地铁图构造面板
	public JComponent getComptDetail() {
		return comptDetail;
	}
	
	//	设置地铁图构造面板
	public void setComptDetail(JComponent comptDetail) {
		this.comptDetail = comptDetail;
	}
	
	//	获取渲染器
	public KDSubwayItemRenderer getRenderer() {
		return renderer;
	}

	//	设置渲染器
	public void setRenderer(KDSubwayItemRenderer renderer) {
		this.renderer = renderer;
	}
	
	/** String 分割符 */
	public final static String SEPARATOR = "-";

	private static final Logger logger = CoreUIObject.getLogger(MileStoneQueryUI.class);

	/**
	 * output class constructor
	 */
	public MileStoneQueryUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	/**
	 * 这个方法我也不清楚干嘛的，没用就删了吧
	 */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		 try {
			viewInfo.getFilter().mergeFilter(getMainFilter(), "and");

			SorterItemCollection sorter = new SorterItemCollection();
			SorterItemInfo si = new SorterItemInfo("longNumber");
			sorter.add(si);
			viewInfo.setSorter(sorter);

		} catch (Exception e) {
			this.handUIException(e);
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}
	/**
	 * 是否忽略CU过滤
	 * 
	 */
	protected boolean isIgnoreCUFilter() {
		return true;
	}

	/**
	 * 点击执行版表格事件
	 * 给pnlSubway添加地铁图面板comptDetail
	 * 这里可以双击穿透属性
	 */

	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		int size = tblMain.getRowCount();		
		if (size < 1) {
			comptDetail = getDescPanel();
			pnlSubway.removeAll();
			pnlSubway.add(comptDetail);
		} else {
			// 获取当前行绑定的任务信息，弹出属性界面，查看信息
			if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			
			this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
			int rowIndex = tblMain.getSelectManager().getActiveRowIndex();

				if (rowIndex > -1) {

					if (tblMain.getRow(rowIndex).getUserObject() != null) {

						FDCScheduleTaskInfo selectedTask = (FDCScheduleTaskInfo) tblMain.getRow(rowIndex).getUserObject();
						String propertiesTaskUIName = FDCScheduleTaskPropertiesNewUI.class.getName();
						if (getUIContext().get("chartType") != null) {
							String chartType = (String) getUIContext().get("chartType");
							if (chartType.equals("proPrj")) {
								propertiesTaskUIName = FDCSpecialScheduleTaskPropertiesUI.class.getName();
						}
					}
					UIContext uiContext = new UIContext();
					uiContext.put("selectTask", selectedTask);
						uiContext.put("project", selectedTask.getSchedule().getProject());
						uiContext.put("Owner", this);

						UIFactory.createUIFactory().create(propertiesTaskUIName, uiContext).show();
					
					}
				}
			}
		}
	}
	
	/**
	 * 点击左树项目节点点击事件
	 * 给pnlSubway添加地铁图面板comptDetail
	 * 如果存在数据，展示地铁图的同时还要在面板上面添加图例
	 * 如果有必要可以在这里增加项目名称
	 */
	public void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		refresh(null);
		getProjectId();
		int size = tblMain.getRowCount();
		tblMain.removeRows(false);
		comptDetail = null;
		pnlSubway.removeAll();
		if (size < 1) {
			comptDetail = getDescPanel();
			pnlSubway.removeAll();
			pnlSubway.add(comptDetail);
		} else {
			if (comptDetail != null) {
				pnlSubway.remove(comptDetail);
			}
			StringBuffer stitle = new StringBuffer();
			stitle.append(getProjectName());	
			KDPanel pnlTitle = new KDPanel();
			// 添加图例
			JPanel pnlDesc = KDSubwayMap.addDesc();
			pnlTitle.add(pnlDesc);
			pnlSubway.add(pnlTitle, BorderLayout.NORTH);			
			KDPanel pnlCont = new KDPanel();
			// 加成两条
			pnlCont.setLayout(new GridLayout(2, 1));

			comptDetail = this.getSubwayRendererCompt();
			if (comptDetail != null) {				
				pnlCont.add(comptDetail);
			}			
			pnlSubway.add(comptDetail, BorderLayout.CENTER);
		}
		pnlSubway.updateUI();

	}
	
	/**
	 * 这个方法我也不清楚干嘛的，没用就删了吧
	 */
	protected FilterInfo getMainFilter() throws Exception {
		// 做工程项目隔离及部门隔离
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		Set prjIdSet = getProjectLeafsOfNode(node);
		FilterInfo filter = new FilterInfo();
		if (prjIdSet == null || prjIdSet.size() < 1) {
			//好像是不让他为空的意思，不是我写的代码。。。
			prjIdSet.add("dddddd");
		}
		filter.getFilterItems().add(new FilterItemInfo("project.id", prjIdSet, CompareType.INCLUDE));
		return filter;
	}

	/**
	 * 获取选择节点的所有下级明细工程项目
	 * 
	 */
	protected Set getProjectLeafsOfNode(DefaultKingdeeTreeNode node) {
		Set idSet = new HashSet();
		if (node != null) {
			if (node.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo prj = (CurProjectInfo) node.getUserObject();
				if (prj.isIsLeaf()) {
					idSet.add(prj.getId().toString());
					return idSet;
				} else {
					//好像是不让他为空的意思，不是我写的代码。。。
					idSet.add("aaa");
					return idSet;
				}
			} else {
				//好像是不让他为空的意思，不是我写的代码。。。
				idSet.add("aaa");
				return idSet;
			}
		}
		return idSet;
	}

	/**
	 * 设置工程项目
	 * 
	 */
	private void initTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		treeMain.setShowsRootHandles(true);
		// 设置树节点全展开
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());

	}
	
	/**
	 * 加载方法
	 * 设置地铁图的高度
	 */
	public void onLoad() throws Exception {
		super.onLoad();
		// 专项任务的计划完成日期大于相关主项任务的计划完成日期
		try {

			String companyId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
			ObjectUuidPK id = new ObjectUuidPK(BOSUuid.read(companyId));

			fdcParamValue = ParamControlFactory.getRemoteInstance().getParamValue(id, "FDCSH011");
			// fdcParamValue 0代表不控制 1 代表控制主项任务2代表控制转向任务3代表控制主项和专项任务
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", TaskRptClientHelper.getProjectFilterSql(), CompareType.INNER));
		view.setFilter(filter);
		initTree();
		this.logoPanel.removeAll();
		KDTaskStatePanel pnlDesc = new KDTaskStatePanel();
		this.logoPanel.add(pnlDesc);
		execQuery();
		// 设置地铁图的高度
		this.kDSplitPane2.setDividerLocation(300);
	}
	
	/**
	 * 获取表格
	 */
	public KDTable getMainTable() {
		return tblMain;
	}
	
	/**
	 * 执行查询，初始化执行版数据
	 */
	protected void execQuery() {
		try {
			mainQuery.setFilter(getMainFilter());
	   	    super.execQuery();
			initStateCell();
		} catch (BOSException e) {
			handUIException(e);
		} catch (Exception e) {
			handUIException(e);
		}
	}
	/**
	 * 是否需要评价
	 */
	private boolean isNeedEvaluation() {
		boolean isNeed = false;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo prjInfo = (CurProjectInfo) node.getUserObject();
			BOSUuid costCenterId = prjInfo.getCostCenter() != null ? prjInfo.getCostCenter().getId() : null;
			if (costCenterId != null) {
				try {
					String fdcParamValue = ParamControlFactory.getRemoteInstance()
							.getParamValue(new ObjectUuidPK(costCenterId), "FDCSH011");
					//如果参数等于1或3就是要评价，详情看参数介绍
					if (fdcParamValue.equals("1") || fdcParamValue.equals("3")) {
						isNeed = true;
					}
				} catch (EASBizException e) {
					handUIException(e);
				} catch (BOSException e) {
					handUIException(e);
				}
			}

		}
		return isNeed;
	}
	/*
	 * 展示的时候，循环表格行，根据该隐藏列的值，设置显示的stateLogo列的展示方式。<br>
	 * 
	 * 注意：<br> 1、打钩的单元格需要设置字体为粗体，否则钩太细，画圈的则不需要；<br>
	 * 2、颜色未使用标准的Color.Green和Color.Orange，而使用了新构建的近似颜色代替，<br>
	 * 是因为纯色太亮，容易刺眼，大量使用时需减少亮度。
	 */
	public void initStateCell() throws BOSException {
		String rsPath = "com.kingdee.eas.fdc.schedule.ScheduleResource";
		// 勾
		String achieve = EASResource.getString(rsPath, "achieve");
		// 圈
		String pending = EASResource.getString(rsPath, "pending");
		// 红
		Color red = new Color(245, 0, 0);
		// 绿
		Color green = new Color(10, 220, 10);
		// 橙
		Color orange = new Color(220, 180, 0);
		boolean isNeedEvaluation = isNeedEvaluation();
		// 取出当前所有里程碑任务的SRCID
		Set<String> srcIDs = new HashSet<String>();
		Map evaluationMap = null;
		IRow row = null;
		if (isNeedEvaluation) {
			evaluationMap = new HashMap();
			for (int i = 0; i < tblMain.getRowCount(); i++) {
				row = tblMain.getRow(i);
				if (row.getCell("srcid").getValue() != null) {
					srcIDs.add(row.getCell("srcid").getValue() + "");
				}
			}

			EntityViewInfo view = new EntityViewInfo();
			
			SelectorItemCollection sic = new SelectorItemCollection();
			// select * 。。。但必须去掉，否则是错误的，与schedule数据不一致
			// sic.add("*");
			sic.add("*");
			sic.add("evaluationResult.id");
			sic.add("evaluationResult.name");
			sic.add("evaluationResult.number");
			sic.add("evaluationResult.evaluationPass");
			view.setSelector(sic);
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("srcRelateTask", srcIDs, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("evaluationType", TaskEvaluationTypeEnum.SCHEDULE));
			
			SorterItemCollection sorter = new SorterItemCollection();
			SorterItemInfo si = new SorterItemInfo("lastupdatetime");
			si.setSortType(SortType.DESCEND);
			view.setSorter(sorter);
			view.setFilter(filter);

			try {
				//这里除了评价过滤
				TaskEvaluationBillCollection cols = TaskEvaluationBillFactory.getRemoteInstance().getTaskEvaluationBillCollection(view);
				TaskEvaluationBillInfo evaInfo = null;
				TaskEvaluationBillInfo otherInfo = null;
				for (int i = 0; i < cols.size(); i++) {
					evaInfo = cols.get(i);
					// if (!evaluationMap.containsKey(bill.getSrcRelateTask()))
					// evaluationMap.put(bill.getSrcRelateTask(),
					// bill.getEvaluationResult().isEvaluationPass());
					
					if (evaInfo.getEvaluationResult().isEvaluationPass()) {
						if (evaluationMap.get(evaInfo.getSrcRelateTask()) == null) {
							evaluationMap.put(evaInfo.getSrcRelateTask(), evaInfo);
						}
					} else {
						if (evaluationMap.get(evaInfo.getSrcRelateTask()) != null) {
							otherInfo = (TaskEvaluationBillInfo) evaluationMap.get(evaInfo.getSrcRelateTask());
							if (evaInfo.getLastUpdateTime() != null && otherInfo.getLastUpdateTime() != null) {								
								if (DateTimeUtils.dateDiff(evaInfo.getLastUpdateTime(), otherInfo.getLastUpdateTime()) < 0) {					
									evaluationMap.remove(evaInfo.getSrcRelateTask());
								}
							}

						}
					}
				}
			} catch (BOSException e) {
				handUIException(e);
			}

		}

		for (int i = 0; i < tblMain.getRowCount(); i++) {
			row = tblMain.getRow(i);
			if (row != null) {
				if (null == row.getCell("complete").getValue()) {
					row.getCell("complete").setValue("0");
				}
				int state = ((Integer) row.getCell("hiddenStatus").getValue()).intValue();
				int complete = new BigDecimal(row.getCell("complete").getValue().toString()).intValue();
				String srcId = "";
				String evaluationType = "";
				if (null != row.getCell("srcid").getValue()) {
					srcId = row.getCell("srcid").getValue().toString();
				}

			
				/*
				 * 参数启用时，完工百分比等于100%，并且经过进度评价，才能确认任务的完成状态 延时未完成对于两者来说是一样的
				 * 1、完工百分比小于100%，并且计划完成日期在当前日期之前 2、完工百分比等于100%是需要根据参数来区别对待
				 * a、完工并且已进行了进度评价 b、完工未进行进度评价
				 */

				row.getCell("status").getStyleAttributes().setBold(true);
				if (state == 3) {
					Date end = (Date) row.getCell("end").getValue();
					Date currDate = FDCDateHelper.getServerTimeStamp();
					if (DateUtils.compareUpToDay(currDate, end) > 0) {
						row.getCell("status").setValue(pending);
						row.getCell("status").getStyleAttributes().setFontColor(red);
						row.getCell("hiddenStatus").setValue(3);
					} else {
						row.getCell("hiddenStatus").setValue(2);
					}
					continue;
				}
				
				
				if (isNeedEvaluation) {// 需要进行评价
					if (evaluationMap.containsKey(srcId) && complete == 100) {
						boolean isPass = ((TaskEvaluationBillInfo) evaluationMap.get(srcId)).getEvaluationResult().isEvaluationPass();
						if (isPass) {
							if (state == 0) {
								row.getCell("status").setValue(achieve);
								row.getCell("status").getStyleAttributes().setFontColor(green);
								row.getCell("hiddenStatus").setValue(new Integer(0));
							}
							if (state == 1) {
								row.getCell("status").setValue(achieve);
								row.getCell("status").getStyleAttributes().setFontColor(red);
								row.getCell("hiddenStatus").setValue(new Integer(1));
								// update by libing
								// 同一天，按照时分秒来精确的话，可能存在问题，这里手动改一下
								if (row.getCell("end") != null && row.getCell("actualEndDate") != null) {
									int finalstatus = DateUtils.compareUpToDay((Date) row.getCell("actualEndDate").getValue(), (Date) row
											.getCell("end").getValue());
									if (finalstatus <= 0) {
										row.getCell("status").getStyleAttributes().setFontColor(green);
										row.getCell("hiddenStatus").setValue(new Integer(0));
									}
								}
							}
						} else {
							row.getCell("status").setValue(pending);
							row.getCell("status").getStyleAttributes().setFontColor(orange);
						}
						
					} else if (!evaluationMap.containsKey(srcId) && complete == 100) {
						row.getCell("status").setValue(pending);
						row.getCell("status").getStyleAttributes().setFontColor(orange);
						// row.getCell("hiddenStatus").setValue(new Integer(2));
						if (row.getCell("end") != null && row.getCell("actualEndDate") != null) {
							int finalstatus = DateUtils.compareUpToDay((Date) row.getCell("actualEndDate").getValue(), (Date) row.getCell(
									"end").getValue());
							if (finalstatus <= 0) {
								row.getCell("hiddenStatus").setValue(new Integer(0));
							}
						}
					} else if (complete < 100) {
						
					}

				} else {// 不需要进行评价
					if (state == 0) {
						row.getCell("status").setValue(achieve);
						row.getCell("status").getStyleAttributes().setFontColor(green);
						row.getCell("hiddenStatus").setValue(new Integer(0));
					}
					if (state == 1) {
						row.getCell("status").setValue(achieve);
						row.getCell("status").getStyleAttributes().setFontColor(red);
						row.getCell("hiddenStatus").setValue(new Integer(1));
						// update by libing 同一天，按照时分秒来精确的话，可能存在问题，这里手动改一下
						if (row.getCell("end") != null && row.getCell("actualEndDate") != null) {
							int finalstatus = DateUtils.compareUpToDay((Date) row.getCell("actualEndDate").getValue(), (Date) row.getCell(
									"end").getValue());
							if (finalstatus <= 0) {
								row.getCell("status").getStyleAttributes().setFontColor(green);
								row.getCell("hiddenStatus").setValue(new Integer(0));
							}
						}
					}
				}
			}
		}
	}

	/***
	 *获取最新进度汇报计划完成日期 added by andy_liu 2012-10-29
	 */
	public IRowSet getIntendEndDateSet(String taskId) throws BOSException, EASBizException, SQLException {
		
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select  top 1 report.fintendenddate from T_SCH_SchTaskProgressReport report ");
		sql.appendSql(" where report.frelatetaskid=? ");
		if (taskId != null) {
			sql.addParam(taskId);
		} else {
			sql.addParam(" ");
		}
		sql.appendSql(" order by fbookeddate desc "); 
		IRowSet rs = sql.executeQuery();
		return sql.executeQuery();
	}
	/***
	 *获取最新执行版数据集合
	 */
	public void getNewstExecutePlan(Map<Integer, Map> resultMap) throws EASBizException, BOSException, SQLException {
		execQuery();
		int size = tblMain.getRowCount();

		KDSubwayItemState FINISH = new KDSubwayItemState("按时完成", GREEN);
		FINISH.setStatusColor(GREEN);
		KDSubwayItemState HALFPEND = new KDSubwayItemState("延时完成", ORANGE);
		HALFPEND.setStatusColor(ORANGE);
		KDSubwayItemState UNFINISH = new KDSubwayItemState("延时且未完成", RED);
		UNFINISH.setStatusColor(RED);
		// 未汇报就计划完成
		KDSubwayItemState undo = new KDSubwayItemState("未达到完成日期", TAN);
		undo.setStatusColor(TAN);
		KDSubwayItemState[] allStates = new KDSubwayItemState[] { FINISH, HALFPEND, UNFINISH, undo };
		// 节点状态，对应上面状态集合，可知状态分别为：finish、finish、doing、finish、doing....
		int[] itemStates = new int[size];
		for (int j = 0; j < size; j++) {
			IRow row = tblMain.getRow(j);
			int status = Integer.parseInt(row.getCell("hiddenStatus").getValue().toString());
			switch (status) {
				case 0:
					itemStates[j] = 0;
				break;
			case 1:
				itemStates[j] = 1;
				break;
			case 2:
				itemStates[j] = 3;
				break;
			case 3:
				itemStates[j] = 2;
				break;
			}
		}
		// 节点详细信息，此处为一个Map数组，其中保存了名称、开始日期等信息，在展示的时候使用TestRenderer解析成html语句
		// 在进度中正式使用的时候，此处可能是里程碑节点的一些关键信息
		// 名称集合
		String[] names = new String[size];
		// longNumber集合
		String[] longNumbers = new String[size];
		Map[] values = new HashMap[size];
		Map[] dates = new HashMap[size];
		// 最新调整版节点名称集合
		// Map latestStageMap = resultMap.get(3);
		int[] taskComleteRates = new int[size];
		// String tmpLongNumbers="";
		for (int i = 0; i < size; i++) {
			IRow row = tblMain.getRow(i);
			Map value = new HashMap();
			Map date = new HashMap();
			value.put("name", (String) row.getCell("name").getValue());
			// 写名字
			names[i] = (String) row.getCell("name").getValue();
			// 写longNumber
			longNumbers[i] = (String) row.getCell("longnumber").getValue();

			// 写入完成率
			if (i > 0) {
				int completedCount = getCompletedTaskCount(longNumbers[i - 1], longNumbers[i]);
				int sumCount = getTaskCount(longNumbers[i - 1], longNumbers[i]);
				int complete = 0;
				float f1 = 100.00f;
				// 不乘100直接乘以线
				if (sumCount > 0) {
					f1 = ((float) completedCount / sumCount * 100);
					Float F1 = new Float(f1);
					complete = F1.intValue();
				}
				taskComleteRates[i] = complete;
				// value.put("completeRate", complete);
			}
			
			// 计划开始日期
			value.put("start", new SimpleDateFormat("yyyy-MM-dd").format(row.getCell("start").getValue()));
			// 执行阶段即最新版本计划完成日期
			String executeDate = new SimpleDateFormat("yyyy-MM-dd").format(row.getCell("end").getValue());
			// 计划完成日期
			value.put("end", executeDate);

			// 工期
			if (null == row.getCell("natureTimes").getValue()) {
				value.put("natureTimes", "0");
			} else {
				DecimalFormat df = new DecimalFormat("0");
				Object natureTimes = (Object) row.getCell("natureTimes").getValue();
				value.put("natureTimes", df.format(natureTimes));
			}
			
			// 实际开始-实际结束
			if (null == row.getCell("actualStartDate").getValue()) {
				value.put("actualStartDate", "");
			} else {
				value.put("actualStartDate", new SimpleDateFormat("yyyy-MM-dd").format(row.getCell("actualStartDate").getValue()));
			}			
			
			// 责任部门
			if (null == row.getCell("adminDept").getValue()) {
				value.put("adminDept", "");
			} else {
				value.put("adminDept", (String) row.getCell("adminDept").getValue());
			}
			
			// 责任人
			if (null == row.getCell("adminPerson").getValue()) {
				value.put("adminPerson", "");
			} else {
				value.put("adminPerson", (String) row.getCell("adminPerson").getValue());
			}
					
			// 是否完成
			boolean isCompleted = row.getCell("complete").getValue() != null
					&& (new BigDecimal(row.getCell("complete").getValue().toString()).compareTo(FDCHelper.ONE_HUNDRED) == 0);				

			// 如果未完成
			if (!isCompleted) {
				value.put("actualEndDate", "");
			} else {
				value.put("actualEndDate", new SimpleDateFormat("yyyy-MM-dd").format(row.getCell("actualEndDate").getValue()));
			}

			// 是否已汇报
			boolean isReport = row.getCell("actualStartDate").getValue() != null;
			String taskId = "";
			if (row.getCell("id").getValue() != null) {
				taskId = row.getCell("id").getValue().toString();
			}
			IRowSet IntendEndDateSet = getIntendEndDateSet(taskId); 
			Date intendEndDate = null;
			if (IntendEndDateSet.next()) {
				intendEndDate = IntendEndDateSet.getDate(1);
			} else {
				intendEndDate = null;
			}
			// 750需求没写这个，，，这么好的功能先注释下，备份时后悔药，果然有提出了这需求
			// 如果对应里程碑任务已进行过汇报：日期显示其“实际完成日期”或“预计完成日期”
			if (!isReport) {
				// 如果未汇报，显示为计划完成日期
				date.put("date", executeDate);
			} else {
				if (isCompleted) {
					// 如果汇报，如果完成,实际完成日期为显示日期
					date.put("date", new SimpleDateFormat("yyyy-MM-dd").format(row.getCell("actualEndDate").getValue()));
				} else {
					// 如果汇报，如果未完成,预计完成日期为显示日期
					if (intendEndDate != null) {
						date.put("date", new SimpleDateFormat("yyyy-MM-dd").format(intendEndDate));
					} else {
						// 预计完成日期为空，显示计划完成日期
						date.put("date", executeDate);
					}
				}				
			}
			// date.put("date", executeDate);

	
			// 添加date
			dates[i] = date;
			
			// 考核日期
			if (null == row.getCell("checkDate").getValue()) {
				value.put("checkDate", "");
			} else {
				value.put("checkDate", new SimpleDateFormat("yyyy-MM-dd").format(row.getCell("checkDate").getValue()));
			}
			
			// 完成进度
			if (null == row.getCell("complete").getValue() || row.getCell("complete").getValue() == "0") {
				value.put("complete", "0");
			} else {
				DecimalFormat df = new DecimalFormat("0");
				Object complete = (Object) row.getCell("complete").getValue();
				value.put("complete", df.format(complete));
			}
			
			// 状态
			Object status = (Object) row.getCell("hiddenStatus").getValue();
			value.put("status", status);
			values[i] = value;
		}
		
		    Map innerMap = new HashMap();
			// 构建地铁图(所有地铁节点名称，状态，任务状态，相关属性)
		innerMap.put("names", names);
		// 长编码
		innerMap.put("longNumbers", longNumbers);
		// 里程碑节点间主项任务完成情况
		innerMap.put("taskComleteRates", taskComleteRates);
		// 由于要显示颜色，dates变为hashmap
		innerMap.put("dates", dates);
		// 是否评价
		innerMap.put("isNeedEvaluation", isNeedEvaluation());
		innerMap.put("allStates", allStates);
		innerMap.put("itemStates", itemStates);
		innerMap.put("values", values);
		innerMap.put("project", getProjectId());
		// 计划执行。
		if (!innerMap.isEmpty()) {
			//1是执行版数据
			resultMap.put(1, innerMap);
		}
		
	}

	/*
	 * 读取两个里程碑节点的任务，任务完成情况。<br>
	 */
	public int getCompletedTaskCount(String longnumberA, String longnumberB) throws BOSException, EASBizException, SQLException {
		String projectId = getProjectId();
		FDCSQLBuilder sql = new FDCSQLBuilder();		
		sql.appendSql(" select count(task.fcomplete)    ");
		sql.appendSql(" from t_sch_fdcschedule as sch "); 
		sql.appendSql(" left outer join T_SCH_FDCScheduleTask  as task "); 
		sql.appendSql(" on ");
		sql.appendSql(" sch.FID=task.FScheduleID ");
		sql.appendSql(" LEFT OUTER JOIN T_SCH_TaskEvaluationbill as eval ");
		sql.appendSql(" on task.fsrcid =eval.fsrcrelatetask ");
		sql.appendSql(" LEFT OUTER JOIN T_SCH_TaskEvaluation as ev ");
		sql.appendSql(" on eval.FEvaluationResult =ev.fid ");
		sql.appendSql(" WHERE ");
		sql.appendSql("  sch.fislatestver=1 and  ");
		// 只考虑主项
		sql.appendSql("  sch.fprojectspecialid is null and  ");
		sql.appendSql(" task.fcomplete=100 ");
		// 启用评价才完成参数
		if (!fdcParamValue.equals("0")) {

			// sql.appendSql(" and ");
			// sql.appendSql(" eval.fevaluationtype='1SCHEDULE' ");
			sql.appendSql(" and "); 
			sql.appendSql(" ev.FEvaluationPass='1' ");
			sql.appendSql(" and eval.FEvaluationResult is  not null ");
		}
		sql.appendSql(" and "); 
		sql.appendSql(" sch.FProjectID=? ");
		if (projectId != null) {
			sql.addParam(projectId);
		} else {
			sql.addParam(" ");
		}
		sql.appendSql(" and ");
		sql.appendSql(" task.flongnumber >= ? ");
		if (longnumberA != null) {
			sql.addParam(longnumberA);
		} else {
			sql.addParam(" ");
		}
		sql.appendSql(" AND  task.flongnumber < ? ");
		if (longnumberB != null) {
			sql.addParam(longnumberB);
		} else {
			sql.addParam(" ");
		}
		// sql.appendSql(" order by fbookeddate desc ");
		IRowSet rs = sql.executeQuery();
		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}
	/*
	 * 获取两个长编码间的任务数目
	 */
	public int getTaskCount(String longnumberA, String longnumberB) throws BOSException, EASBizException, SQLException {
		String projectId = getProjectId();
		FDCSQLBuilder sql = new FDCSQLBuilder();		
		sql.appendSql(" select count(task.fcomplete)    ");
		sql.appendSql(" from t_sch_fdcschedule as sch "); 
		sql.appendSql(" left outer join T_SCH_FDCScheduleTask  as task "); 
		sql.appendSql(" on ");
		sql.appendSql(" sch.FID=task.FScheduleID ");
		sql.appendSql(" WHERE "); 
		sql.appendSql("  sch.fislatestver=1 and  ");
		// 只考虑主项
		sql.appendSql("  sch.fprojectspecialid is null and  ");
		sql.appendSql(" sch.FProjectID=? ");
		if (projectId != null) {
			sql.addParam(projectId);
		} else {
			sql.addParam(" ");
		}
		sql.appendSql(" and ");
		sql.appendSql(" task.flongnumber >= ? ");
		if (longnumberA != null) {
			sql.addParam(longnumberA);
		} else {
			sql.addParam(" ");
		}
		sql.appendSql(" AND task.flongnumber < ? ");
		if (longnumberB != null) {
			sql.addParam(longnumberB);
		} else {
			sql.addParam(" ");
		}
		// sql.appendSql(" order by fbookeddate desc ");
		// IRowSet rs = sql.executeQuery();
		// return sql.executeQuery();
		IRowSet rs = sql.executeQuery();
		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}	
	
	/*
	 * 页面初始化构建地铁图。<br>
	 */
	public JComponent getSubwayRendererCompt() throws EASBizException, BOSException, SQLException {
		Map<Integer, Map> map = new HashMap<Integer, Map>();
		// 没有其他版本，只有最新执行版
		//考核版就是0
		getSubwayRendererComptForStage(0, getCheckVersionTaskInfos(), map);
		getNewstExecutePlan(map);

		if (!map.isEmpty()) {
			// 构建地铁图(所有地铁节点名称，状态，任务状态，相关属性)
			KDSubwayMap subway = new KDSubwayMap(map);
			subway.setRenderer(new TestRenderer());
			// subway.setBounds(0, 178, 1000, 100);
			comptDetail = subway;
		} else {

			comptDetail = null;
		}
		return comptDetail;

	}
	/*
	 * 页面初始化构建地铁图。<br> 给参数，构建不同图
	 * 目前两个图，只有最新执行版
	 */
	public void getSubwayRendererComptForStage(int stage, IRowSet getXStageDataSet, Map<Integer, Map> resultMap) throws BOSException,
			SQLException, EASBizException {

		IRowSet xStageDataSet = getXStageDataSet;
		// execQuery();
		if (!(xStageDataSet.size() > 0)) {
			return;
		}
		int size = xStageDataSet.size();
		// 名称集合
		String[] names = new String[size];
		// longnumber集合
		String[] longNumbers = new String[size];
		// 日期集合
		Map[] dates = new HashMap[size];
		// 由于计划执行要显示日期颜色，改为HashMap
		// String[] dates = new String[size];
		int[] itemStates = new int[size];
		int i = 0;
		// 状态集合，此处计划完成日期绿色，计划时间调整红色
		KDSubwayItemState undo = new KDSubwayItemState("计划完成日期", BLUE);
		KDSubwayItemState changed = new KDSubwayItemState("计划时间调整", Color.RED);
		KDSubwayItemState[] allStates = new KDSubwayItemState[] { undo, changed };
		// 节点状态，对应上面状态集合，可知状态分别为：undo、undo、changed、undo、undo....
		// 节点详细信息为名称就ok了
		Map[] values = new HashMap[size];
		;
		// 初始化名称日期数组
		while (xStageDataSet.next()) {
			names[i] = xStageDataSet.getString(1);
			Map value = new HashMap();
			Map date = new HashMap();
			value.put("name", xStageDataSet.getString(1));
			// 要根据longnumber对齐里程碑节点
			value.put("longnumber", xStageDataSet.getString(3));
			longNumbers[i] = xStageDataSet.getString(3);

			value.put("start", new SimpleDateFormat("yyyy-MM-dd").format(xStageDataSet.getDate(4)));
			value.put("end", new SimpleDateFormat("yyyy-MM-dd").format(xStageDataSet.getDate(2)));
			value.put("natureTimes", xStageDataSet.getString(5));
			value.put("actualStartDate", xStageDataSet.getString(6));
			value.put("actualEndDate", xStageDataSet.getString(7));
			if (xStageDataSet.getDate(8) != null) {
				value.put("checkDate", new SimpleDateFormat("yyyy-MM-dd").format(xStageDataSet.getDate(8)));
			} else {
				value.put("checkDate", " ");
			}
			// value.put("checkDate", new
			// SimpleDateFormat("yyyy-MM-dd").format(xStageDataSet.getDate(8)));

			value.put("complete", xStageDataSet.getString(9));
			date.put("date", new SimpleDateFormat("yyyy-MM-dd").format(xStageDataSet.getDate(2)));
			// 前3个阶段没显示颜色要求，显示默认黑色。
			date.put("color", 1);
			// 都是蓝色的。
			if (stage == 0) {
				itemStates[i] = 0;
			} else {
				// int status =
				// cmpEndDate(getProjectId(),xStageDataSet.getString
				// (3),xStageDataSet.getString(2),stage);
				// switch (status) {
				// case 0:
				// itemStates[i] = 0;
				// break;
				// case 1:
				// itemStates[i] = 1;
				// break;
				// }
			}
			dates[i] = date;
			value.put("status", itemStates[i]);
			values[i] = value;
			i++;
		}

		Map innerMap = new HashMap();

		if (size > 0) {
			// 构建地铁图(所有地铁节点名称，状态，任务状态，相关属性)
			innerMap.put("names", names);
			innerMap.put("dates", dates);
			// 新增longNumbers
			innerMap.put("longNumbers", longNumbers);
			innerMap.put("allStates", allStates);
			innerMap.put("itemStates", itemStates);
			innerMap.put("values", values);
			// KDSubwayMap map = new KDSubwayMap(names,dates, allStates,
			// itemStates, values, 100, isShowName,true);
			// map.setRenderer(new TestRenderer());
			// map.setBounds(0, 1000, 1000, 100);
			// comptDetail = (JComponent) add(map);
		} else {
			comptDetail = null;
		}
		if (!innerMap.isEmpty()) {
			resultMap.put(stage, innerMap);
		}

	}
		
	/**
	 * 获取子节点项目Id
	 */
	public String getProjectId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		// String projectId=null;
		if (node != null) {
			if (node.getUserObject() != null && node.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo prj = (CurProjectInfo) node.getUserObject();
				if (prj.isIsLeaf()) {
					projectId = prj.getId().toString();
				}
			}
		}
		
		return projectId;
	}
	
	/**
	 * 获取子节点项目名称
	 */
	public String getProjectName() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		String projectName = null;
		if (node != null) {
			if (node.getUserObject() instanceof CurProjectInfo) {				
				CurProjectInfo prj = (CurProjectInfo) node.getUserObject();
				if (prj.isIsLeaf()) {
					projectName = prj.getName().toString();
				}
			}
		}
		return projectName;
	}
	
	/**
	 * 获取图例
	 */
	public JComponent getDescPanel() {
		setSize(800, 200);
		setLayout(null);

		// 名称集合
		// 状态集合，此处使用预设的4种颜色，也可以自己定义
		KDSubwayItemState finish = new KDSubwayItemState("按时完成", KDSubwayItemState.DONE);
		KDSubwayItemState doing = new KDSubwayItemState("延时完成", KDSubwayItemState.DOING);
		KDSubwayItemState undo = new KDSubwayItemState("延时且未完成", KDSubwayItemState.UNDO);
		KDSubwayItemState todo = new KDSubwayItemState("未达到完成日期", KDSubwayItemState.TODO);
		KDSubwayItemState[] allStates = new KDSubwayItemState[] { finish, doing, undo, todo };
		// 节点状态，对应上面状态集合，可知状态分别为：finish、finish、doing、finish、doing....
		// 节点详细信息，此处为一个Map数组，其中保存了名称、开始日期等信息，在展示的时候使用TestRenderer解析成html语句
		// 在进度中正式使用的时候，此处可能是里程碑节点的一些关键信息
		KDSubwayMap map = new KDSubwayMap(allStates);
		map.setBounds(0, 0, 1000, 200);
		comptDetail = (JComponent) add(map);
		return comptDetail;

	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}
	
	/**
	 * 考核版的数据集合
	 * 
	 * @param projectId
	 *            项目ID
	 * @return 取出需要的数据集合
	 */
	public IRowSet getCheckVersionTaskInfos() throws BOSException, EASBizException, SQLException {

		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select ");
		sql.appendSql(" T_SCH_FDCScheduleTask.fname_l2, ");
		sql.appendSql(" T_SCH_FDCScheduleTask.fend, ");
		sql.appendSql(" T_SCH_FDCScheduleTask.flongnumber, ");
		sql.appendSql(" T_SCH_FDCScheduleTask.fstart, ");
		sql.appendSql(" T_SCH_FDCScheduleTask.fduration, ");
		sql.appendSql(" T_SCH_FDCScheduleTask.factualStartDate, ");
		sql.appendSql(" T_SCH_FDCScheduleTask.factualEndDate, ");
		sql.appendSql(" T_SCH_FDCScheduleTask.fcheckDate, ");
		sql.appendSql(" T_SCH_FDCScheduleTask.fcomplete ");
		sql.appendSql(" from t_sch_fdcschedule ");
		sql.appendSql(" left outer join T_SCH_FDCScheduleTask  ");
		sql.appendSql(" on ");
		sql.appendSql(" t_sch_fdcschedule.FID=T_SCH_FDCScheduleTask.FScheduleID ");
		sql.appendSql(" where t_sch_fdcschedule.FISCHECKVERSION=1 ");
		sql.appendSql(" and ");
		sql.appendSql(" t_sch_fdcschedule.FID IN ");
		sql.appendSql(" (SELECT TOP 1 FDCSch.FID ");
		sql.appendSql(" FROM T_SCH_FDCSchedule AS FDCSch ");
		sql.appendSql(" WHERE ");
		sql.appendSql(" FDCSch.FProjectID=? ");
		sql.addParam(projectId);
		sql.appendSql(" and ");
		sql.appendSql(" FDCSch.FISCHECKVERSION=1 ");
		sql.appendSql(" ORDER BY FDCSch.FVersion DESC) ");
		sql.appendSql(" and ");
		sql.appendSql(" t_sch_fdcschedule.fprojectid=? ");
		sql.addParam(projectId);
		sql.appendSql(" and ");
		sql.appendSql(" T_SCH_FDCScheduleTask.FTaskType='milestone' ");
		sql.appendSql(" ORDER BY  ");
		sql.appendSql(" T_SCH_FDCScheduleTask.flongnumber ");
		IRowSet rs = sql.executeQuery();
		return sql.executeQuery();
	}

}