/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
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
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ProjectSpecialCollection;
import com.kingdee.eas.fdc.schedule.ProjectSpecialFactory;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class F7RelatedTaskSelectUI extends AbstractF7RelatedTaskSelectUI {
	
	private static final Logger logger = CoreUIObject.getLogger(F7RelatedTaskSelectUI.class);

	public F7RelatedTaskSelectUI() throws Exception {
		super();
	}

	/**
	 * 设置工程项目
	 * 
	 * @throws Exception
	 */
	private void initTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		treeMain.setShowsRootHandles(true);
		// 设置树节点全展开
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
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-22
	 * @version EAS7.0
	 * @see
	 */

	public void onShow() throws Exception {
		// TODO Auto-generated method stub
		this.chkShowAllTask.setEnabled(true);
		this.tblMain.getStyleAttributes().setLocked(true);
		super.onShow();

	}

	/**
	 * 任务名字生成子节点树
	 * 
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-22 void
	 * @version EAS7.0
	 * @see
	 */
	public void fullTable() {
		int count = this.tblMain.getRowCount();
		for (int i = 0; i < count; i++) {
			// 取得原名称
			IRow row = this.tblMain.getRow(i);
			String value = (String) row.getCell("name").getValue();
			// 取得树级次和是否明细节点
			boolean isLeaf = ((Boolean) row.getCell("isLeaf").getValue()).booleanValue();
			int level = ((Integer) row.getCell("level").getValue()).intValue();
			CellTreeNode treeNode = new CellTreeNode();
			treeNode.setValue(value);// 显示的值，此处是‘任务名称’字符串
			treeNode.setTreeLevel(level);// 级次，从0开始，此处为任务的树级次
			treeNode.setHasChildren(!isLeaf);
			treeNode.isCollapse();
			treeNode.addClickListener(new NodeClickListener() {
				public void doClick(CellTreeNode source, ICell cell, int type) {
					tblMain.revalidate();
				}
			});

			row.getStyleAttributes().setLocked(true);
			 row.getCell("name").getStyleAttributes().setLocked(false);
			row.getCell("name").setValue(treeNode);// 设置前面构建的单元格树到表
		}
	}

	/**
	 * 
	 * @description 向表中填充数据
	 * @author 李建波
	 * @createDate 2011-8-22
	 * @throws BOSException void
	 * @version EAS7.0
	 * @see
	 */
	private void fillTable() throws BOSException {
		//不勾选出项目有问题。勾选也是过滤的。。。
		// 勾选后
//		if (chkShowAllTask.isSelected()) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			tblMain.refresh();
			if (node == null) {
				return;
			}
			Object obj = node.getUserObject();
			if (obj != null) {
				
				if (obj instanceof CurProjectInfo) {
					CurProjectInfo prj = (CurProjectInfo) obj;

					EntityViewInfo view = new EntityViewInfo();
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("id");
					sic.add("taskType");
					sic.add("name");
					sic.add("start");
					sic.add("end");
					sic.add("adminDept.id");
					sic.add("adminDept.name");
					sic.add("adminPerson.id");
					sic.add("adminPerson.name");
					sic.add("isLeaf");
					sic.add("level");
					sic.add("schedule.projectSpecial.*");
					sic.add("complete");
					sic.add("longNumber");
					sic.add("schedule.project.id");
					sic.add("schedule.project.name");
					sic.add("schedule.project.number");

					view.setSelector(sic);
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
					filter.getFilterItems().add(new FilterItemInfo("schedule.project.id", prj.getId().toString()));
					// 判断是否为主项
					if (!node.isLeaf()) {
						filter.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial", null, CompareType.EQUALS));
					}
					
					// filter.getFilterItems().add(new
					// FilterItemInfo("schedule.projectSpecial", null,
					// CompareType.EQUALS));
					view.setFilter(filter);
					SorterItemCollection sorter = new SorterItemCollection();
					sorter.add(new SorterItemInfo("longNumber"));
					view.setSorter(sorter);
					FDCScheduleTaskCollection col = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
					if (col != null && col.size() > 0) {

						for (int i = 0; i < col.size(); i++) {
							FDCScheduleTaskInfo taskInfo = col.get(i);
							IRow iRow1 = tblMain.addRow();
							iRow1.setUserObject(taskInfo);
							iRow1.getCell("isLeaf").setValue(new Boolean(taskInfo.isIsLeaf()));
							iRow1.getCell("id").setValue(taskInfo.getId().toString());
							iRow1.getCell("name").setValue(taskInfo.getName());
							iRow1.getCell("planStartData").setValue(taskInfo.getStart());
							iRow1.getCell("planEndData").setValue(taskInfo.getEnd());
							if (taskInfo.getComplete() != null) {
								iRow1.getCell("complete").setValue(taskInfo.getComplete());
							}
							if (null != taskInfo.getAdminDept()) {
								iRow1.getCell("adminDept").setValue(taskInfo.getAdminDept().getName());
							}
							if (null != taskInfo.getAdminPerson()) {
								iRow1.getCell("adminPerson").setValue(taskInfo.getAdminPerson().getName());
							}
							iRow1.getCell("level").setValue(new Integer(taskInfo.getLevel()));

						}
					}
				}
				if (obj instanceof ProjectSpecialInfo) {
					ProjectSpecialInfo prj = (ProjectSpecialInfo) obj;

					EntityViewInfo view = new EntityViewInfo();
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("id");
					sic.add("taskType");
					sic.add("name");
					sic.add("start");
					sic.add("end");
					sic.add("adminDept.id");
					sic.add("adminDept.name");
					sic.add("adminPerson.id");
					sic.add("adminPerson.name");
					sic.add("isLeaf");
					sic.add("level");
					sic.add("schedule.projectSpecial.*");
					sic.add("complete");
					sic.add("longNumber");
					sic.add("schedule.project.id");
					sic.add("schedule.project.name");
					sic.add("schedule.project.number");

					view.setSelector(sic);
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
					filter.getFilterItems().add(new FilterItemInfo("schedule.projectspecial.id", prj.getId().toString()));
					view.setFilter(filter);
					SorterItemCollection sorter = new SorterItemCollection();
					sorter.add(new SorterItemInfo("longNumber"));
					view.setSorter(sorter);
					FDCScheduleTaskCollection col = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
					if (col != null && col.size() > 0) {

						for (int i = 0; i < col.size(); i++) {
							FDCScheduleTaskInfo taskInfo = col.get(i);
							IRow iRow1 = tblMain.addRow();
							iRow1.setUserObject(taskInfo);
							iRow1.getCell("isLeaf").setValue(new Boolean(taskInfo.isIsLeaf()));
							iRow1.getCell("id").setValue(taskInfo.getId().toString());
							iRow1.getCell("name").setValue(taskInfo.getName());
							iRow1.getCell("planStartData").setValue(taskInfo.getStart());
							iRow1.getCell("planEndData").setValue(taskInfo.getEnd());
							if (taskInfo.getComplete() != null) {
								iRow1.getCell("complete").setValue(taskInfo.getComplete());
							}
							if (null != taskInfo.getAdminDept()) {
								iRow1.getCell("adminDept").setValue(taskInfo.getAdminDept().getName());
							}
							if (null != taskInfo.getAdminPerson()) {
								iRow1.getCell("adminPerson").setValue(taskInfo.getAdminPerson().getName());
							}
							iRow1.getCell("level").setValue(new Integer(taskInfo.getLevel()));

						}
					}
				}
				
				
			}
//		} else {
//			// 没勾选
//			this.admininfo = (AdminOrgUnitInfo) getUIContext().get("seladmininfo");
//			this.selDate = (Date) getUIContext().get("sltDate");
//			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
//			tblMain.refresh();
//			if (node == null) {
//				return;
//			}
//			Object obj = node.getUserObject();
//			if (obj != null) {
//				if (obj instanceof CurProjectInfo) {
//					CurProjectInfo prj = (CurProjectInfo) obj;
//
//					EntityViewInfo view = new EntityViewInfo();
//					SelectorItemCollection sic = new SelectorItemCollection();
//					sic.add("id");
//					sic.add("taskType");
//					sic.add("name");
//					sic.add("start");
//					sic.add("end");
//					sic.add("adminDept.id");
//					sic.add("adminDept.name");
//					sic.add("adminPerson.id");
//					sic.add("adminPerson.name");
//					sic.add("isLeaf");
//					sic.add("level");
//					sic.add("schedule.projectSpecial.*");
//					sic.add("complete");
//					sic.add("longNumber");
//					sic.add("schedule.project.id");
//					sic.add("schedule.project.name");
//					sic.add("schedule.project.number");
//
//					view.setSelector(sic);
//					FilterInfo filter = new FilterInfo();
//					filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
//					filter.getFilterItems().add(new FilterItemInfo("schedule.project.id", prj.getId().toString()));
//					// 判断是否为主项
//					if (!node.isLeaf()) {
//						filter.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial", null, CompareType.EQUALS));
//					}
//					// add by libing 增加2个筛选条件
//					if (admininfo != null) {
//						filter.getFilterItems().add(new FilterItemInfo("adminDept.id", admininfo.getId().toString()));
//					}
//
//					view.setFilter(filter);
//					SorterItemCollection sorter = new SorterItemCollection();
//					sorter.add(new SorterItemInfo("longNumber"));
//					view.setSorter(sorter);
//					FDCScheduleTaskCollection col = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
//					if (col != null && col.size() > 0) {
//						// add by libing 计划完成日期
//						Calendar cal = Calendar.getInstance();
//						cal.setTime(selDate);
//						int year = cal.get(Calendar.YEAR);
//						int month = cal.get(Calendar.MONTH);
//						cal.set(year, month, 1);
//						Date timeBeginCal = cal.getTime();
//						cal.set(year, month + 1, 1);
//						Date timeEndCal = cal.getTime();
//						for (int i = 0; i < col.size(); i++) {
//							FDCScheduleTaskInfo taskInfo = col.get(i);
//							Date endDate = taskInfo.getEnd();
//							if (DateUtils.compareUpToDay(timeBeginCal, endDate) > 0 || DateUtils.compareUpToDay(endDate, timeEndCal) >= 0) {
//								continue;
//							}
//							IRow iRow1 = tblMain.addRow();
//							iRow1.setUserObject(taskInfo);
//							iRow1.getCell("isLeaf").setValue(new Boolean(taskInfo.isIsLeaf()));
//							iRow1.getCell("id").setValue(taskInfo.getId().toString());
//							iRow1.getCell("name").setValue(taskInfo.getName());
//							iRow1.getCell("planStartData").setValue(taskInfo.getStart());
//							iRow1.getCell("planEndData").setValue(taskInfo.getEnd());
//							if (taskInfo.getComplete() != null) {
//								iRow1.getCell("complete").setValue(taskInfo.getComplete());
//							}
//							if (null != taskInfo.getAdminDept()) {
//								iRow1.getCell("adminDept").setValue(taskInfo.getAdminDept().getName());
//							}
//							if (null != taskInfo.getAdminPerson()) {
//								iRow1.getCell("adminPerson").setValue(taskInfo.getAdminPerson().getName());
//							}
//							iRow1.getCell("level").setValue(new Integer(taskInfo.getLevel()));
//
//						}
//					}
//				}
//				if (obj instanceof ProjectSpecialInfo) {
//					ProjectSpecialInfo prj = (ProjectSpecialInfo) obj;
//
//					EntityViewInfo view = new EntityViewInfo();
//					SelectorItemCollection sic = new SelectorItemCollection();
//					sic.add("id");
//					sic.add("taskType");
//					sic.add("name");
//					sic.add("start");
//					sic.add("end");
//					sic.add("adminDept.id");
//					sic.add("adminDept.name");
//					sic.add("adminPerson.id");
//					sic.add("adminPerson.name");
//					sic.add("isLeaf");
//					sic.add("level");
//					sic.add("schedule.projectSpecial.*");
//					sic.add("complete");
//					sic.add("longNumber");
//					sic.add("schedule.project.id");
//					sic.add("schedule.project.name");
//					sic.add("schedule.project.number");
//
//					view.setSelector(sic);
//					FilterInfo filter = new FilterInfo();
//					filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
//					filter.getFilterItems().add(new FilterItemInfo("schedule.projectspecial.id", prj.getId().toString()));
//					// add by libing 增加2个筛选条件
//					if (admininfo != null) {
//						filter.getFilterItems().add(new FilterItemInfo("adminDept.id", admininfo.getId().toString()));
//					}
//
//					view.setFilter(filter);
//					SorterItemCollection sorter = new SorterItemCollection();
//					sorter.add(new SorterItemInfo("longNumber"));
//					view.setSorter(sorter);
//					FDCScheduleTaskCollection col = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
//					if (col != null && col.size() > 0) {
//						// add by libing 计划完成日期
//						Calendar cal = Calendar.getInstance();
//						cal.setTime(selDate);
//						int year = cal.get(Calendar.YEAR);
//						int month = cal.get(Calendar.MONTH);
//						cal.set(year, month, 1);
//						Date timeBeginCal = cal.getTime();
//						cal.set(year, month + 1, 1);
//						Date timeEndCal = cal.getTime();
//						for (int i = 0; i < col.size(); i++) {
//							FDCScheduleTaskInfo taskInfo = col.get(i);
//							Date endDate = taskInfo.getEnd();
//							if (DateUtils.compareUpToDay(timeBeginCal, endDate) > 0 || DateUtils.compareUpToDay(endDate, timeEndCal) >= 0) {
//								continue;
//							}
//							IRow iRow1 = tblMain.addRow();
//							iRow1.setUserObject(taskInfo);
//							iRow1.getCell("isLeaf").setValue(new Boolean(taskInfo.isIsLeaf()));
//							iRow1.getCell("id").setValue(taskInfo.getId().toString());
//							iRow1.getCell("name").setValue(taskInfo.getName());
//							iRow1.getCell("planStartData").setValue(taskInfo.getStart());
//							iRow1.getCell("planEndData").setValue(taskInfo.getEnd());
//							if (taskInfo.getComplete() != null) {
//								iRow1.getCell("complete").setValue(taskInfo.getComplete());
//							}
//							if (null != taskInfo.getAdminDept()) {
//								iRow1.getCell("adminDept").setValue(taskInfo.getAdminDept().getName());
//							}
//							if (null != taskInfo.getAdminPerson()) {
//								iRow1.getCell("adminPerson").setValue(taskInfo.getAdminPerson().getName());
//							}
//							iRow1.getCell("level").setValue(new Integer(taskInfo.getLevel()));
//						}
//					}
//				}
//			}
//		}
		// this.tblSelect.getStyleAttributes().setLocked(true);
	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		initTree();
		this.btnDown.setEnabled(true);
		this.btnUp.setEnabled(true);
		this.btnOK.setEnabled(true);
		this.btnCancel.setEnabled(true);
		tblMain.checkParsed();
		tblMain.getColumn("name").getStyleAttributes().setLocked(false);
		tblMain.getColumn("complete").getStyleAttributes().setHided(true);
	}

	private void actiondoubleClick() {
		 ActionEvent e = new ActionEvent(tblMain, tblMain.getSelectManager().getActiveRowIndex(), null);
		try {
			actionOK_actionPerformed(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 下移操作
	 * 
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-22
	 * @param e
	 *            void
	 * @version EAS7.0
	 * @see
	 */

	public void actionDown_actionPerformed(ActionEvent e) throws Exception {
		super.actionDown_actionPerformed(e);
		DownMove();
	}

	/**
	 * 
	 * @description 删除分录行
	 * @author 李建波
	 * @createDate 2011-8-20 void
	 * @version EAS7.0
	 * @see
	 */
	private void removeTable() {
		for (int i = tblMain.getRowCount() - 1; i >= 0; i--) {
			tblMain.removeRow(i);
		}	
	}

	/**
	 * @description 点击确定执行
	 * @author 李建波
	 * @createDate 2011-8-22
	 * @version EAS7.0
	 * @see
	 */

	public void actionOK_actionPerformed(ActionEvent e) throws Exception {
		// Object[] sltData = new Object[];
		// int rowIndex = tblMain.getSelectManager().getActiveColumnIndex();
	
		FDCScheduleTaskInfo sltData = new FDCScheduleTaskInfo();
			int size = KDTableUtil.getSelectedRowCount(tblMain);
			if (size != 1) {
				MsgBox.showInfo(this, "请选择一条任务");
				return;
			}
			// .setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT.ROW_SELECT
			// );
			// IRow row = tblMain.getRow(rowIndex);
			IRow row = KDTableUtil.getSelectedRow(tblMain);
			// 数据库本身数据存在问题，因此采用这个方法来过滤
			FDCScheduleTaskCollection col = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection();
			List list = new ArrayList();
			for (int i = 0; i < col.size(); i++) {
				FDCScheduleTaskInfo schTaskInfo = col.get(i);
				if (schTaskInfo.getParent() != null) {
					FDCScheduleTaskInfo parent2 = schTaskInfo.getParent();
					list.add(parent2.getId().toString());
				}
			}
			if (row != null) {
				Boolean isLeaf = (Boolean) row.getCell("isLeaf").getValue();
				String seletId = row.getCell("id").getValue().toString();
				for (int a = 0; a < list.size(); a++) {
					if (seletId.equals(list.get(a))) {
						FDCMsgBox.showInfo("请选择最明细的任务");
						abort();
					}
				}
				sltData = (FDCScheduleTaskInfo) row.getUserObject();
				String scheduleInfo = sltData.getSchedule().getId().toString();
				SelectorItemCollection col2 = new SelectorItemCollection();
				col2.add(new SelectorItemInfo("id"));
				col2.add(new SelectorItemInfo("name"));
				col2.add(new SelectorItemInfo("project.*"));
				FDCScheduleInfo scheduleInfo2 = FDCScheduleFactory.getRemoteInstance().getFDCScheduleInfo(new ObjectUuidPK(scheduleInfo), col2);
				if (scheduleInfo2 != null && scheduleInfo2.getProject() != null) {
					sltData.getSchedule().setProject(scheduleInfo2.getProject());
				}
				
			}
			DeptMonthlySchedulePromptSelector slt = (DeptMonthlySchedulePromptSelector) getUIContext().get("slt");
			slt.setSltData(sltData);
		destroyWindow();
	}

	/**
	 * @description 显示所有任务
	 * @author 李建波
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */

	public void actionShowAllTask_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		//如果左边数上没选中，则直接显示空白
		DefaultKingdeeTreeNode selectnode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (selectnode == null) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		FilterInfo filterinfo = new FilterInfo();
		if (chkShowAllTask.isSelected()) {
			sic.add("id");
			sic.add("name");
			sic.add("taskType");
			sic.add("start");
			sic.add("end");
			sic.add("adminDept.id");
			sic.add("adminDept.name");
			sic.add("adminPerson.id");
			sic.add("adminPerson.name");
			sic.add("isLeaf");
			sic.add("level");
			sic.add("schedule.projectSpecial.*");
			sic.add("complete");
			sic.add("longNumber");
			sic.add("schedule.project.id");
			sic.add("schedule.project.name");
			sic.add("schedule.project.number");

			view.setSelector(sic);
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (node != null && node.getUserObject() != null && node.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo curPro = (CurProjectInfo) node.getUserObject();
				filterinfo.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
				filterinfo.getFilterItems().add(new FilterItemInfo("schedule.project.id", curPro.getId().toString()));
				// 判断是否为主项
				if (!node.isLeaf()) {
					filterinfo.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial", null, CompareType.EQUALS));
				}
				view.setFilter(filterinfo);
			}
			if (node != null && node.getUserObject() != null && node.getUserObject() instanceof ProjectSpecialInfo) {
				ProjectSpecialInfo Pinfo = (ProjectSpecialInfo) node.getUserObject();
				filterinfo.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
				filterinfo.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial.id", Pinfo.getId().toString()));
				view.setFilter(filterinfo);
			}
			if (node == null) {
				filterinfo.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
				view.setFilter(filterinfo);
			}
			SorterItemCollection sorter = new SorterItemCollection();
			sorter.add(new SorterItemInfo("longNumber"));
			view.setSorter(sorter);
			FDCScheduleTaskCollection col = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
			if (col != null && col.size() > 0) {
				removeTable();
				for (int i = 0; i < col.size(); i++) {
					FDCScheduleTaskInfo taskInfo = col.get(i);
					IRow iRow1 = tblMain.addRow();
					iRow1.setUserObject(taskInfo);
					iRow1.getCell("isLeaf").setValue(new Boolean(taskInfo.isIsLeaf()));
					iRow1.getCell("id").setValue(taskInfo.getId().toString());
					iRow1.getCell("name").setValue(taskInfo.getName());
					iRow1.getCell("planStartData").setValue(taskInfo.getStart());
					iRow1.getCell("planEndData").setValue(taskInfo.getEnd());
					if (taskInfo.getComplete() != null) {
						iRow1.getCell("complete").setValue(taskInfo.getComplete());
					}
					if (null != taskInfo.getAdminDept()) {
						iRow1.getCell("adminDept").setValue(taskInfo.getAdminDept().getName());
					}
					if (null != taskInfo.getAdminPerson()) {
						iRow1.getCell("adminPerson").setValue(taskInfo.getAdminPerson().getName());
					}
					iRow1.getCell("level").setValue(new Integer(taskInfo.getLevel()));
				}
			}
		} else {
			fillTable();
		}
		super.actionShowAllTask_actionPerformed(e);
		fullTable();
	}

	private FDCScheduleTaskInfo relateTask;
	
	public void actionCancle_actionPerformed(ActionEvent e) throws Exception {
		
		List sltData = new ArrayList();
		this.relateTask = (FDCScheduleTaskInfo) getUIContext().get("relateTask");
		if (relateTask != null) {
			FDCScheduleTaskInfo sltInfo = relateTask;
			if (sltInfo.getSchedule() == null) {
				FDCScheduleTaskInfo sinfo = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo(
						new ObjectUuidPK(sltInfo.getId().toString()));
				if (sinfo != null && sinfo.getSchedule() != null) {
					sltInfo = sinfo;
					FDCScheduleInfo scheduleinfo = sinfo.getSchedule();
					sltInfo.setSchedule(scheduleinfo);
					String scheduleInfo = sltInfo.getSchedule().getId().toString();
					SelectorItemCollection col2 = new SelectorItemCollection();
					col2.add(new SelectorItemInfo("id"));
					col2.add(new SelectorItemInfo("name"));
					col2.add(new SelectorItemInfo("project.*"));
					FDCScheduleInfo scheduleInfo2 = FDCScheduleFactory.getRemoteInstance().getFDCScheduleInfo(new ObjectUuidPK(scheduleInfo), col2);
					if (scheduleInfo2 != null && scheduleInfo2.getProject() != null) {
						sltInfo.getSchedule().setProject(scheduleInfo2.getProject());
					}
					sltData.add(sltInfo);
				}
			} else {
				FDCScheduleTaskInfo sinfo = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo(
						new ObjectUuidPK(sltInfo.getId().toString()));
				sltInfo = sinfo;
				String scheduleInfo = sltInfo.getSchedule().getId().toString();
				SelectorItemCollection col2 = new SelectorItemCollection();
				col2.add(new SelectorItemInfo("id"));
				col2.add(new SelectorItemInfo("name"));
				col2.add(new SelectorItemInfo("project.*"));
				FDCScheduleInfo scheduleInfo2 = FDCScheduleFactory.getRemoteInstance().getFDCScheduleInfo(new ObjectUuidPK(scheduleInfo), col2);
				if (scheduleInfo2 != null && scheduleInfo2.getProject() != null) {
					sltInfo.getSchedule().setProject(scheduleInfo2.getProject());
				}
				sltData.add(sltInfo);
			}
		}
		DeptMonthlySchedulePromptSelector slt = (DeptMonthlySchedulePromptSelector) getUIContext().get("slt");
		slt.setSltData(sltData.toArray());

		 destroyWindow();
		
	}

	/**
	 * 单条数据向下移动
	 * 
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-22 void
	 * @version EAS7.0
	 * @see
	 */
	private void DownMove() {
		if (tblMain.getRowCount() < 1) {
			return;
		}
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();

		for (int k = rowIndex; k < tblMain.getRowCount(); k++) {
			if (k == tblMain.getRowCount() - 1) {
				break;
			}
			Object level = tblMain.getRow(rowIndex).getCell("level").getValue();
			int levSize = Integer.parseInt(level.toString());
			if (levSize >= Integer.parseInt(tblMain.getRow(k + 1).getCell("level").getValue().toString())) {
				break;
			}
		}
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		fillTable();
		fullTable();
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			ActionEvent e2 = new ActionEvent(tblMain, 0, null);
			actionOK_actionPerformed(e2);
		}
	}
}