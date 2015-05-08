/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.schedule.TaskTypeFactory;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.WBSTemplateFactory;
import com.kingdee.eas.fdc.schedule.WBSTemplateTypeCollection;
import com.kingdee.eas.fdc.schedule.WBSTemplateTypeFactory;
import com.kingdee.eas.fdc.schedule.WBSTemplateTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;

/**
 * output class name
 */
public class WBSTemplateReferListUI extends AbstractWBSTemplateReferListUI {
//	private static final String COMPLEMENTTASK = "施工任务模板";
//	private static final String SPECIALTASK = "专项任务模板";
//	private static final String MAINTASK = "主项节点任务模板";
	private static final String TEMPBASE = "模板库";
	private static final Logger logger = CoreUIObject
			.getLogger(WBSTemplateReferListUI.class);

	/**
	 * output class constructor
	 */
	public WBSTemplateReferListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * 暂时使用基类的方法，如果不能满足要求则改为手动实现弹出窗口
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
//		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
//		if(node == null) return;
//		FilterInfo filter = new FilterInfo();
///*	根据李涛要求将方法写死，模板按照名称取	
// * if (node.getUserObject() instanceof TaskTypeInfo) {
//			TaskTypeInfo info = (TaskTypeInfo) node.getUserObject();
//			String innerSQL = "select distinct t1.fname_l2 from t_sch_tasktype t1" +
//					"	inner join t_sch_tasktype t2 on t1.flongnumber like concat(t2.flongnumber,'%')" +
//					"	where t2.fname_l2='"+info.getName()+"'";
//			filter.getFilterItems().add(new FilterItemInfo("type",innerSQL ,CompareType.INNER));
//			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE,CompareType.EQUALS));
//			mainQuery.setFilter(filter);
//			mainQuery.getFilter().setMaskString("#0 and #1");
//		} else	{*/
//		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",FDCSCHClientHelper.getUpFIOrgUnit(
//				SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo()),CompareType.INCLUDE));
//		if (TEMPBASE.equals(node.getText())){
//			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
//			mainQuery.setFilter(filter);
//		}else{
//			filter.getFilterItems().add(new FilterItemInfo("type",node.getText().trim() ,CompareType.EQUALS));
//			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
//			mainQuery.setFilter(filter);
//		}
//		tblMain.removeRows();// 触发新查询
//		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(node == null) return;
		FilterInfo filter = new FilterInfo();
		/*
		 * 根据李涛需求将树写死，取数方法改变
		 * if (node.getUserObject() instanceof TaskTypeInfo) {
			TaskTypeInfo info = (TaskTypeInfo) node.getUserObject();
			String innerSQL = "select distinct t1.fname_l2 from t_sch_tasktype t1" +
					"	inner join t_sch_tasktype t2 on t1.flongnumber like concat(t2.flongnumber,'%')" +
					"	where t2.fname_l2='"+info.getName()+"'";
			filter.getFilterItems().add(new FilterItemInfo("type",innerSQL ,CompareType.INNER));
			mainQuery.setFilter(filter);
		} else	mainQuery.setFilter(filter);*/
		Set orgIds = new HashSet();
		orgIds = FDCSCHClientHelper.getUpFIOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		orgIds.add(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId().toString());
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",orgIds,CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		if (TEMPBASE.equals(node.getText()) || node.getUserObject() == null){
			mainQuery.setFilter(filter);
		}else{
//			filter.getFilterItems().add(new FilterItemInfo("type",node.getText().trim() ,CompareType.EQUALS));
			WBSTemplateTypeInfo typeInfo = (WBSTemplateTypeInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("templateType.id",typeInfo.getId().toString(),CompareType.EQUALS));
			mainQuery.setFilter(filter);
		}	
		tblMain.removeRows();// 触发新查询
	}

	/**
	 * 同tblMain_tableClicked
	 */
	protected void btnOK_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
//		btnEdit.doClick();
		actionView_actionPerformed(new ActionEvent(btnView, 0, "Double Clicked"));
//		this.actionEdit_actionPerformed(null);
		
	}

	/**
	 * output btnNotOK_actionPerformed method
	 */
	protected void btnNotOK_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		FDCWBSListUI ownerUI = (FDCWBSListUI) getUIContext().get(UIContext.OWNER);
		ownerUI.refresh(null);
		this.uiWindow.close();
	}

	protected FDCDataBaseInfo getBaseDataInfo() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return WBSTemplateFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return WBSTemplateReferEditUI.class.getName();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		if(mainQuery.getFilter() == null){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE,CompareType.EQUALS));
			mainQuery.setFilter(filter);
		}
		buildTaskTypeTree();
		treeMain.setSelectionRow(0);
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
//		Object project = uiContext.get("prj4Import");
//		Object parent = uiContext.get("parent4Import");
		super.prepareUIContext(uiContext, e);
		uiContext.put("prj4Import", getUIContext().get("prj4Import"));
		uiContext.put("parent4Import", getUIContext().get("parent4Import"));
	}
	private void buildTaskTypeTree() throws Exception{
	/*	ITreeBuilder treeBuilder = null;
		FilterInfo filter = getTreeFilter();
		treeBuilder = TreeBuilderFactory.createTreeBuilder(getTreeCtrl(), 50, 5, filter);
		treeBuilder.buildTree(treeMain);
		treeMain.setShowPopMenuDefaultItem(false);
		treeMain.setShowsRootHandles(true);*/
		/**
		 * 根据李涛需求描述将左边的树写死
		 */
//		DefaultKingdeeTreeNode root=new DefaultKingdeeTreeNode();
//		root.setText(TEMPBASE);
//		KingdeeTreeModel model=new KingdeeTreeModel(root);
//		DefaultKingdeeTreeNode node1=new DefaultKingdeeTreeNode();
//		node1.setText(MAINTASK);
//		DefaultKingdeeTreeNode node2=new DefaultKingdeeTreeNode();
//		node2.setText(SPECIALTASK);
//		DefaultKingdeeTreeNode node3=new DefaultKingdeeTreeNode();
//		node3.setText(COMPLEMENTTASK);
//		model.insertNodeInto(node1, root, root.getChildCount());
//		model.insertNodeInto(node2, root, root.getChildCount());
//		model.insertNodeInto(node3, root, root.getChildCount());
//		treeMain.setModel(model);
		
		DefaultKingdeeTreeNode root=new DefaultKingdeeTreeNode();
		root.setText(TEMPBASE);
		KingdeeTreeModel model=new KingdeeTreeModel(root);
		EntityViewInfo view = new EntityViewInfo();
		SorterItemCollection sic = new SorterItemCollection();
		sic.add(new SorterItemInfo("number"));
		view.setSorter(sic);
		WBSTemplateTypeCollection typeCol = WBSTemplateTypeFactory.getRemoteInstance().getWBSTemplateTypeCollection(view);
		for(int i = 0;i<typeCol.size();i++){
			DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode();
			WBSTemplateTypeInfo info = typeCol.get(i);
			node.setUserObject(info);
			node.setText(info.getName());
			model.insertNodeInto(node, root, root.getChildCount());
		}
		treeMain.setModel(model);
		treeMain.setSelectionRow(0);
	} 
	private FilterInfo getTreeFilter(){
		FilterInfo filter = new FilterInfo();
		Set taskTypeSys = new HashSet();
		for(int i=0;i<TaskTypeInfo.SYSPREDESIGN.length;i++){
			taskTypeSys.add(TaskTypeInfo.SYSPREDESIGN[i]);
		}
		filter.getFilterItems().add(new FilterItemInfo("id",taskTypeSys,CompareType.INCLUDE));
		return filter;
	}
	private ILNTreeNodeCtrl getTreeCtrl() throws BOSException{
		return new DefaultLNTreeNodeCtrl(TaskTypeFactory.getRemoteInstance());
	}
	protected void initWorkButton() {
		super.initWorkButton();
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		actionAddNew.setVisible(false);
		actionEdit.setVisible(false);
		actionRemove.setVisible(false);
	}
	 public void onShow() throws Exception {
			super.onShow();
			getMainTable().setColumnMoveable(true);
			// 禁用启用/禁用按钮
			btnCancel.setVisible(false);
			btnCancelCancel.setVisible(false);
		}
}