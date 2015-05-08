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
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;
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
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskStateHelper;
import com.kingdee.eas.fdc.schedule.ProjectSpecialCollection;
import com.kingdee.eas.fdc.schedule.ProjectSpecialFactory;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportCollection;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryCollection;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportFactory;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportInfo;
import com.kingdee.eas.fdc.schedule.WeekReportEnum;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.StringUtils;

/**
 * 
 * @(#)						
 * 版权：		金蝶国际软件集团有限公司版权所有 描述： 运营报告管理-项目周报告
 * 
 * @author 周航健
 * @version EAS7.0
 * @createDate 2011-9-6
 * @see
 */
public class ViewProjectWeekReportUI extends AbstractViewProjectWeekReportUI {
	/**
	 * serialVersionNumber
	 */
	private static final long serialVersionUID = -7498776077658439566L;
	private static final Logger logger = CoreUIObject.getLogger(ViewProjectWeekReportUI.class);

	public ViewProjectWeekReportUI() throws Exception {
		super();
	}

	protected void initTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		treeMain.setShowsRootHandles(true);
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());

		TreeNode node = (TreeNode) this.treeMain.getModel().getRoot();
		addSpecial(node);
	}

	/**
	 * @discription <向工程项目树中加项目专项>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/16>
	 * @see <相关的类>
	 */
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
						childAt.add(new DefaultKingdeeTreeNode(element));

					}
				}
			} else {
				addSpecial(childAt);

			}

		}
	}

	/**
	 * @discription <获得项目专项集合Map>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/16>
	 */
	private ProjectSpecialCollection getSpecial(String projId) throws Exception {
		ProjectSpecialCollection coll = new ProjectSpecialCollection();
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("curProject.id", projId));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		coll = ProjectSpecialFactory.getRemoteInstance().getProjectSpecialCollection(view);
		return coll;
	}

	public void onLoad() throws Exception {
		initDiplay();
		super.onLoad();
		initTableStyle();
		KDWorkButton btnAttach=new KDWorkButton();
		btnAttach.setText("查看附件");
		btnAttach.setIcon(EASResource.getIcon("imgTbtn_view"));
		this.toolBar.add(btnAttach);
		btnAttach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                	btnAttach_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
	}
	private void btnAttach_actionPerformed(ActionEvent e) {
		DefaultKingdeeTreeNode node = getLastSelectedNode();
		/*
		 * 取报告年-周
		 */
		
		String selectedItem = (String) cbYear.getSelectedItem();
		if(selectedItem==null) return;
		int year = 1990;
		int Week = 1;
		if (!("".equals(selectedItem)) && selectedItem.length() > 0) {
			String[] split = selectedItem.split("-");
			year = Integer.parseInt(split[0]);
			Week = Integer.parseInt(split[1]);
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("creator.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.relateTask.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.respPerson.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.respDept.*"));
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof ProjectSpecialInfo) {
			ProjectSpecialInfo cpinfo = (ProjectSpecialInfo) node.getUserObject();
			CurProjectInfo curProjectInfo = cpinfo.getCurProject();
			filter.getFilterItems().add(new FilterItemInfo("project.id", curProjectInfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", cpinfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
			filter.getFilterItems().add(new FilterItemInfo("Week", new Integer(Week)));
		} else if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo cpinfo = (CurProjectInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("project.id", cpinfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
			filter.getFilterItems().add(new FilterItemInfo("Week", new Integer(Week)));
		}

		view.setFilter(filter);
		ProjectWeekReportCollection collection;
		try {
			collection = ProjectWeekReportFactory.getRemoteInstance().getProjectWeekReportCollection(view);
			if (collection.size() > 0) {
				ProjectWeekReportInfo projectWeekReportInfo = collection.get(0);
				 AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
			        acm.showAttachmentListUIByBoID(projectWeekReportInfo.getId().toString(),this,false); // boID 是 与附件关联的 业务对象的 ID
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
	}
	private void initTableStyle() {
		nextTable.checkParsed();
		thisTable.checkParsed();
		thisTable.getColumn("taskName").setWidth(170);
		thisTable.getColumn("completePercent").setWidth(100);
		thisTable.getColumn("planEndDate").setWidth(120);
		thisTable.getColumn("realEndDate").setWidth(120);
		thisTable.getColumn("intendEndDate").setWidth(120);
		thisTable.getColumn("respPerson").setWidth(120);
		thisTable.getColumn("respDept").setWidth(120);

		nextTable.getColumn("taskName").setWidth(280);
		nextTable.getColumn("planStartDate").setWidth(250);
		nextTable.getColumn("planEndDate").setWidth(250);
		nextTable.getColumn("respPerson").setWidth(170);
		nextTable.getColumn("respDept").setWidth(170);
		this.nextTable.getStyleAttributes().setLocked(true);
		this.thisTable.getStyleAttributes().setLocked(true);
		this.thisTable.getColumn("planEndDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.thisTable.getColumn("planEndDate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.nextTable.getColumn("planEndDate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.nextTable.getColumn("planEndDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
	}

	public void onShow() throws Exception {
		super.onShow();
		this.thisTable.getColumn("isComplete").getStyleAttributes().setHided(true);
		this.menuItemExpIMG.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.menuItemExpIMG.setText("导出Excel");
		this.btnExportIMG.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.btnExportIMG.setText("导出Excel");
		btnExportIMG.setEnabled(true);
		this.sptTop.setDividerLocation(246);
		this.sptDown.setDividerLocation(252);
	}

	/**
	 * @discription <初始化页面显示>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/16>
	 * @see <相关的类>
	 */
	public void initDiplay() {
		// 移除图表
		pnlChart.removeAll();
		// 移除表格数据
		thisTable.removeRows(false);
		nextTable.removeRows(false);
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.menuItemExpIMG.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.menuItemExpIMG.setText("导出Excel");
		this.btnExportIMG.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.btnExportIMG.setText("导出Excel");
	}

	protected String getTDPath() {
		return "/bim/fdc/process/processProjectbill";
	}
	protected void thisTable_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getColIndex() == thisTable.getColumn("description").getColumnIndex()) {
			if (e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 1) {
				thisTable.showCellDetailInfo();
			}
		}
		super.thisTable_tableClicked(e);
	}

	/**
	 * @discription <初始化下拉选框(年、周)的值>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/06>
	 * @see <相关的类>
	 */
	public void initCbYear() {
		cbYear.removeAllItems();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		/*
		 * 　前查专项，后查主项
		 */
		addItemToCbYear(node);
	}

	private void addItemToCbYear(DefaultKingdeeTreeNode node){
		if (node == null || node.getUserObject() instanceof OrgStructureInfo) {
			return;
		}
		
		String projectSepcialId = null;
		String projectId = null;
		if(node.getUserObject() != null && node.getUserObject() instanceof ProjectSpecialInfo){
			ProjectSpecialInfo info = (ProjectSpecialInfo) node.getUserObject();
			projectSepcialId = info.getId().toString();
		}
		
		if (node.getUserObject() != null && node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo prj = (CurProjectInfo) node.getUserObject();
			projectId = prj.getId().toString();
		}
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		// 开工日期
		sic.add(new SelectorItemInfo("startDate"));
		// 完工日期
		sic.add(new SelectorItemInfo("endDate"));
		
		FilterInfo info = new FilterInfo();
		if (!StringUtils.isEmpty(projectSepcialId)) {
			info.getFilterItems().add(new FilterItemInfo("projectSpecial.id", projectSepcialId));
		} else {
			info.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
		}
		if (!StringUtils.isEmpty(projectId)) {
			info.getFilterItems().add(new FilterItemInfo("project.id", projectId));
		}
		
		SorterItemCollection sorts = new SorterItemCollection();
		SorterItemInfo sorterItem = new SorterItemInfo("year");
		sorterItem.setSortType(SortType.DESCEND);
		sorts.add(sorterItem);
		sorterItem = new SorterItemInfo("week");
		sorterItem.setSortType(SortType.DESCEND);
		sorts.add(sorterItem);

		view.setSorter(sorts);
		view.setFilter(info);
		ProjectWeekReportCollection col = null;
		try {
			col = ProjectWeekReportFactory.getRemoteInstance().getProjectWeekReportCollection(view);
			if (col != null && col.size() > 0) {
				for (int i = 0; i < col.size(); i++) {
					ProjectWeekReportInfo WeekReportInfo = col.get(i);
					int year = WeekReportInfo.getYear();
					int Week = WeekReportInfo.getWeek();
					String str = year + "-" + Week;
					if (year != 0 && Week != 0) {
						boolean isAdd = true;
						for (int m = 0; m < cbYear.getItemCount(); m++) {
							String item = cbYear.getItemAt(m).toString();
							if (item.equals(str)) {
								isAdd = false;
							}
						}
						if (isAdd) {
							cbYear.addItem(str);
						}
					}
				}
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		
		
	}
     @Deprecated 
	private void testExtract(DefaultKingdeeTreeNode node) {
		
		
		
		
		if (node != null && node.isLeaf() && node.getUserObject() instanceof ProjectSpecialInfo) {
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
				FilterInfo info = new FilterInfo();
				info.getFilterItems().add(new FilterItemInfo("projectSpecial.id", id));

				view.setFilter(info);
				cbYear.addItem("");
				ProjectWeekReportCollection col = null;
				try {
					col = ProjectWeekReportFactory.getRemoteInstance().getProjectWeekReportCollection(view);
					if (col != null && col.size() > 0) {
						for (int i = 0; i < col.size(); i++) {
							ProjectWeekReportInfo WeekReportInfo = col.get(i);
							int year = WeekReportInfo.getYear();
							int Week = WeekReportInfo.getWeek();
							String str = year + "-" + Week;
							if (year != 0 && Week != 0) {
								boolean isAdd = true;
								for (int m = 0; m < cbYear.getItemCount(); m++) {
									String item = cbYear.getItemAt(m).toString();
									if (item.equals(str)) {
										isAdd = false;
									}
								}
								if (isAdd) {
									cbYear.addItem(str);
								}
							}
						}
					}
				} catch (BOSException e) {
					logger.error(e.getMessage());
				}
			}
		} else if (node != null && node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo cpinfo = (CurProjectInfo) node.getUserObject();
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
				FilterInfo info = new FilterInfo();
				info.getFilterItems().add(new FilterItemInfo("project.id", id));
				info.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));

				view.setFilter(info);
				ProjectWeekReportCollection col = null;
				try {
					col = ProjectWeekReportFactory.getRemoteInstance().getProjectWeekReportCollection(view);
					if (col != null && col.size() > 0) {
						for (int i = 0; i < col.size(); i++) {
							ProjectWeekReportInfo WeekReportInfo = col.get(i);
							int year = WeekReportInfo.getYear();
							int Week = WeekReportInfo.getWeek();
							String str = year + "-" + Week;
							if (year != 0 && Week != 0) {
								boolean isAdd = true;
								for (int m = 0; m < cbYear.getItemCount(); m++) {
									String item = cbYear.getItemAt(m).toString();
									if (item.equals(str)) {
										isAdd = false;
									}
								}
								if (isAdd) {
									cbYear.addItem(str);
								}
							}
						}
					}
				} catch (BOSException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}
		}
	}

	protected void cbYear_itemStateChanged(ItemEvent e) throws Exception {
		if (cbYear.getSelectedItem() != null && !"".equals(cbYear.getSelectedItem())) {
			initData();
		} else {
			this.pnlChart.removeAll();
			this.thisTable.removeRows();
			this.nextTable.removeRows();
			kDBizPromptBox1.setValue(null);
			kDDatePicker1.setValue(null);
			kDComboBox1.removeAllItems();
		}
		pnlChart.updateUI();
	}


	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.pnlChart.removeAll();
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		kDBizPromptBox1.setValue(null);
		kDDatePicker1.setValue(null);
		kDComboBox1.removeAllItems();
		/* 为下拉选框初始化数据 */
		initCbYear();
		setDefalutYearSelected();
	}

	/**
	 * @discription <初始化页面数据>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/06>
	 */
	public void initData() throws Exception {

		DefaultKingdeeTreeNode node = getLastSelectedNode();
		clear();
		/*
		 * 取报告年-周
		 */
		String selectedItem = (String) cbYear.getSelectedItem();
		int year = 1990;
		int Week = 1;
		if (!("".equals(selectedItem)) && selectedItem.length() > 0) {
			String[] split = selectedItem.split("-");
			year = Integer.parseInt(split[0]);
			Week = Integer.parseInt(split[1]);
		}

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("creator.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.relateTask.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.relateTask.schedule.project.costcenter"));
		view.getSelector().add(new SelectorItemInfo("entrys.respPerson.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.respDept.*"));
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof ProjectSpecialInfo) {
			ProjectSpecialInfo cpinfo = (ProjectSpecialInfo) node.getUserObject();
			CurProjectInfo curProjectInfo = cpinfo.getCurProject();
			filter.getFilterItems().add(new FilterItemInfo("project.id", curProjectInfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", cpinfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
			filter.getFilterItems().add(new FilterItemInfo("Week", new Integer(Week)));
		} else if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo cpinfo = (CurProjectInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("project.id", cpinfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
			filter.getFilterItems().add(new FilterItemInfo("Week", new Integer(Week)));
		}

		view.setFilter(filter);
		ProjectWeekReportCollection collection = ProjectWeekReportFactory.getRemoteInstance().getProjectWeekReportCollection(view);
		List currWeek = new ArrayList();
		List nextWeek = new ArrayList();
		if (collection.size() > 0) {
			ProjectWeekReportInfo projectWeekReportInfo = collection.get(0);
			ProjectWeekReportEntryCollection entrysColl = projectWeekReportInfo.getEntrys();

			for (int i = 0; i < entrysColl.size(); i++) {
				ProjectWeekReportEntryInfo entryInfo = entrysColl.get(i);
				if (entryInfo.isIsNext()) {
					nextWeek.add(entryInfo);
				} else {
					currWeek.add(entryInfo);
				}
			}
			this.kDComboBox1.addItem(projectWeekReportInfo.getState());
			this.kDBizPromptBox1.setValue(projectWeekReportInfo.getCreator());
			this.kDDatePicker1.setValue(projectWeekReportInfo.getCreateTime());
		}

		loadThisTableFields(currWeek);
		loadNextTableFields(nextWeek);
		initChart(currWeek);
	}

	private void clear() {
		this.thisTable.removeRows();
		this.nextTable.removeRows();
		this.pnlChart.removeAll();
		kDBizPromptBox1.setValue(null);
		kDDatePicker1.setValue(null);
		kDComboBox1.removeAllItems();
	}

	/**
	 * @discription <初始化下周计划表格数据>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/06>
	 */
	public void loadNextTableFields(List list) {
		this.nextTable.removeRows(false);
		this.nextTable.checkParsed();
		if (list.size() > 0) {// 集合大于0
			for (int i = 0; i < list.size(); i++) {// 循环将分录放入表格中
				ProjectWeekReportEntryInfo entryInfo = (ProjectWeekReportEntryInfo) list.get(i);
				IRow row = this.nextTable.addRow();
				row.getCell("taskName").setValue(entryInfo.getTaskName());
				row.getCell("planStartDate").setValue(entryInfo.getRelateTask().getStart());
				row.getCell("planEndDate").setValue(entryInfo.getRelateTask().getEnd());
				row.getCell("respPerson").setValue(entryInfo.getRespPerson());
				row.getCell("respDept").setValue(entryInfo.getRespDept());
				row.getCell("entryID").setValue(entryInfo.getId());

			}
		}

	}

	/**
	 * @discription <初始化本周任务完成情况表格数据>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/06>
	 *             <p>
	 * @param <空>
	 * @return <返回值描述>
	 * 
	 *         modifier <空>
	 *         <p>
	 *         modifyDate <空>
	 *         <p>
	 *         modifyInfo <空>
	 *         <p>
	 * @throws BOSException
	 * @throws SQLException
	 * @see <相关的类>
	 */
	public void loadThisTableFields(List list) throws BOSException, SQLException {
		this.thisTable.removeRows(false);
		this.thisTable.checkParsed();
		if (list.size() > 0) {// 集合大于0
			ProjectWeekReportEntryInfo entryInfo = null;
			entryInfo = (ProjectWeekReportEntryInfo) list.get(0);
			String costCenterId = entryInfo.getRelateTask().getSchedule().getProject().getCostCenter().getId().toString();
			DefaultKingdeeTreeNode node = getLastSelectedNode();
			boolean isParamControl = ViewReportHelper.isStartingParamControl(costCenterId, node.getUserObject());
			boolean isEvaluation = false;
			Set<String> evSet = null;
			FDCScheduleTaskCollection cols = new FDCScheduleTaskCollection();
			for (int i = 0; i < list.size(); i++) {
				entryInfo = (ProjectWeekReportEntryInfo) list.get(i);
				cols.add(entryInfo.getRelateTask());
			}
			if (isParamControl) {
				evSet = FDCScheduleTaskStateHelper.isEvaluationed(null, cols);
			}
			
			Map<String, FDCScheduleTaskInfo> realTaskEntry = FDCScheduleTaskStateHelper.getNewstTasks(null, cols);
			
			for (int i = 0; i < list.size(); i++) {// 循环将分录放入表格中
				isEvaluation = false;
				entryInfo = (ProjectWeekReportEntryInfo) list.get(i);
				IRow row = this.thisTable.addRow();
				row.getCell("taskName").setValue(entryInfo.getTaskName());
				row.getCell("planEndDate").setValue(entryInfo.getRelateTask().getEnd());
				if (entryInfo.getCompletePrecent() == 100) {
					row.getCell("isComplete").setValue(WeekReportEnum.YES);
				} else {
					row.getCell("isComplete").setValue(WeekReportEnum.NO);
				}
				row.getCell("completePercent").setValue(new Integer(entryInfo.getCompletePrecent()));
				row.getCell("realEndDate").setValue(entryInfo.getRealEndDate());
				row.getCell("intendEndDate").setValue(entryInfo.getIntendEndDate());
				if (entryInfo.getDescription() != null && !"".equals(entryInfo.getDescription())) {
					row.getCell("description").setValue(entryInfo.getDescription());
				} else {
					row.getCell("description").setValue("");
				}
				row.getCell("respPerson").setValue(entryInfo.getRespPerson());
				row.getCell("respDept").setValue(entryInfo.getRespDept());

				row.getCell("entryID").setValue(entryInfo.getId());
				if (evSet != null && evSet.contains(entryInfo.getRelateTask().getSrcID())) {
					isEvaluation = true;
				}
				ViewReportHelper.initStateCell(isEvaluation, isParamControl, row, realTaskEntry.get(entryInfo.getRelateTask().getSrcID()));
			}
		}
	}

	/**
	 * 返回图片名称
	 * 
	 * @return
	 */
	public String getChartTitle() {
		String title = "";
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (null == node) {
			return "";
		}
		/*
		 * 取报告年-周
		 */
		String selectedItem = (String) cbYear.getSelectedItem();
		int year = 1990;
		int Week = 1;
		if (null != selectedItem && !("".equals(selectedItem)) && selectedItem.length() > 0) {
			String[] split = selectedItem.split("-");
			year = Integer.parseInt(split[0]);
			Week = Integer.parseInt(split[1]);
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("entrys.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.relateTask.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.respPerson.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.respDept.*"));
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof ProjectSpecialInfo) {
			ProjectSpecialInfo cpinfo = (ProjectSpecialInfo) node.getUserObject();
			CurProjectInfo curProjectInfo = cpinfo.getCurProject();
			filter.getFilterItems().add(new FilterItemInfo("project.id", curProjectInfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", cpinfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
			filter.getFilterItems().add(new FilterItemInfo("Week", new Integer(Week)));
			if (null == curProjectInfo.getName() || "".equals(curProjectInfo.getName())) {
				try {
					CurProjectInfo curProject = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(curProjectInfo.getId()));
					title = year + "年" + Week + "周" + curProject.getName() + "项目周报";
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			} else {
				title = year + "年" + Week + "周" + curProjectInfo.getName() + "项目周报";
			}
		} else if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo cpinfo = (CurProjectInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("project.id", cpinfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
			filter.getFilterItems().add(new FilterItemInfo("Week", new Integer(Week)));
			title = year + "年" + Week + "周" + cpinfo.getName() + "项目周报";
		}

		view.setFilter(filter);
		try {
			ProjectWeekReportCollection collection = ProjectWeekReportFactory.getRemoteInstance().getProjectWeekReportCollection(view);
			List currWeek = new ArrayList();
			List nextWeek = new ArrayList();
			if (collection.size() > 0) {
				ProjectWeekReportInfo projectWeekReportInfo = collection.get(0);
				ProjectWeekReportEntryCollection entrysColl = projectWeekReportInfo.getEntrys();

				for (int i = 0; i < entrysColl.size(); i++) {
					ProjectWeekReportEntryInfo entryInfo = entrysColl.get(i);
					if (entryInfo.isIsNext()) {
						nextWeek.add(entryInfo);
					} else {
						currWeek.add(entryInfo);
					}
				}
			}

			if (currWeek.size() > 0) {
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

				String str = year + "年" + Week + "周任务项" + currWeek.size() + "项,其中按时完成" + finished + "项、延时完成" + delayed + "项、待确认" + affirm
						+ "项、延时且未完成" + unfinished + "项、执行中" + excudeing + "项。" + "计划下周任务" + nextWeek.size() + "项。本周任务状态分布见如下饼状图：";
				Font font = new Font(str, Font.BOLD + Font.ITALIC, 13);
				title = title + "\n" + font.getName();
				return title;
			} else {
				String str = year + "年" + Week + "周任务项" + currWeek.size() + "项,其中按时完成0项、延时完成0项、待确认0项、延时且未完成0项" + "、按时完成率0%," + "、计划下周任务"
						+ nextWeek.size() + "项。本周任务状态分布见如下饼状图：";
				Font font = new Font(str, Font.BOLD + Font.ITALIC, 13);
				title = title + "\n" + font.getName();
				return title;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		return "";
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
		nextTable.getColumn(0).setWidth(180);
		nextTable.getColumn(1).setWidth(180);
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
		// 7、返回img
		return img;
	}

	/**
	 * @param dataset
	 * @return
	 */
	protected JFreeChart createChart(Dataset dataset) {
		return super.createChart(dataset);
	}

	protected void initChart(List thisWeek) {
		pnlChart.add(createChartPanel(thisWeek), BorderLayout.CENTER);
	}

	protected JPanel createChartPanel(List thisWeek) {
		JFreeChart chart = createChart(createDataset(thisWeek));
		return new ChartPanel(chart);
	}

	protected Dataset createDataset(List thisWeek) {
		DefaultPieDataset result = new DefaultPieDataset();
		if (thisWeek.size() > 0) {
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

			double fin = Double.parseDouble(String.valueOf(finished));
			double dely = Double.parseDouble(String.valueOf(delayed));
			double aff = Double.parseDouble(String.valueOf(affirm));
			double unfin = Double.parseDouble(String.valueOf(unfinished));
			double excude = Double.parseDouble(String.valueOf(excudeing));
			double total = fin + dely + aff + unfin + excude;

			if (finished == thisWeek.size()) {
				result.setValue("按时完成", total);
			} else {
				result.setValue("按时完成", fin);
			}
			if (delayed == thisWeek.size()) {
				result.setValue("延时完成", total);
			} else {
				result.setValue("延时完成", dely);
			}
			if (affirm == thisWeek.size()) {
				result.setValue("待确认", total);
			} else {
				result.setValue("待确认", aff);
			}
			if (unfinished == thisWeek.size()) {
				result.setValue("延迟未完成", total);
			} else {
				result.setValue("延迟未完成", unfin);
			}
			if (excude == thisWeek.size()) {
				result.setValue("执行中", total);
			} else {
				result.setValue("执行中", excude);
			}
		}
		return result;
	}	
	
	public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception {
		List tables = new ArrayList();
		tables.add(new Object[] { "本周任务", thisTable });
		tables.add(new Object[] { "下周任务", nextTable });
		FDCTableHelper.exportExcel(this, tables);
	}
	
	protected String getUIName() {
		return ViewProjectWeekReportUI.class.getName();
	}
}