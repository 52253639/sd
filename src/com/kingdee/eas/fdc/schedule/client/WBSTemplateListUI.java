/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.framework.client.ClientControlStrategy;
import com.kingdee.eas.basedata.framework.client.ControlTypeUtil;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.WBSTemplateFactory;
import com.kingdee.eas.fdc.schedule.WBSTemplateInfo;
import com.kingdee.eas.fdc.schedule.WBSTemplateTypeCollection;
import com.kingdee.eas.fdc.schedule.WBSTemplateTypeFactory;
import com.kingdee.eas.fdc.schedule.WBSTemplateTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class WBSTemplateListUI extends AbstractWBSTemplateListUI {
	private static final String COMPLEMENTTASKTEMPLATE = "施工任务模板";
	private static final String SPECIALTASKTEMPLATE = "专项任务模板";
	private static final String MAINTASKTEMPLATE = "主项节点任务模板";
	private static final String TEMPLATEBASE = "模板库";
	private static final Logger logger = CoreUIObject
			.getLogger(WBSTemplateListUI.class);

	public WBSTemplateListUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void tblMain_tableClicked(KDTMouseEvent e)throws Exception {
		super.tblMain_tableClicked(e);
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)	throws Exception {
		super.tblMain_tableSelectChanged(e);
	}

	protected void menuItemImportData_actionPerformed(ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
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
		Set orgUnitIds = new HashSet();
		orgUnitIds = FDCSCHClientHelper.getUpFIOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		orgUnitIds.add(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId().toString());
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",orgUnitIds,CompareType.INCLUDE));
		if (TEMPLATEBASE.equals(node.getText()) || node.getUserObject() == null){
			mainQuery.setFilter(filter);
		}else{
//			filter.getFilterItems().add(new FilterItemInfo("type",node.getText().trim() ,CompareType.EQUALS));
			WBSTemplateTypeInfo typeInfo = (WBSTemplateTypeInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("templateType.id",typeInfo.getId().toString(),CompareType.EQUALS));
			mainQuery.setFilter(filter);
		}	
		tblMain.removeRows();// 触发新查询
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkIsEnabled("WBS模板已启用，不能修改！");
		super.actionEdit_actionPerformed(e);
	}

	private void checkIsEnabled(String msg) {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		if(actRowIdx < 0){
			FDCMsgBox.showWarning("请先选中要操作的行！");
			SysUtil.abort();
		}
		if(tblMain.getCell(actRowIdx, "isEnabled").getValue() != null
				&& Boolean.TRUE.equals(Boolean.valueOf(tblMain.getCell(actRowIdx, "isEnabled").getValue().toString()))){
			FDCMsgBox.showError(msg);
			SysUtil.abort();
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if(getTaskTypeSelectedNode().getUserObject() == null){
			FDCMsgBox.showWarning("请先选中WBS模板！");
			SysUtil.abort();
		}
		if(!SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()){
			FDCMsgBox.showWarning("当前组织不是财务组织，不能新增WBS模板！");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}
	
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		if(actRowIdx < 0) return;
		super.actionCancel_actionPerformed(e);
	}

	public void actionCancelCancel_actionPerformed(ActionEvent e)	throws Exception {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		if(actRowIdx < 0) return;
		super.actionCancelCancel_actionPerformed(e);
	}

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new WBSTemplateInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return WBSTemplateFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return WBSTemplateEditUI.class.getName();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		 this.actionPrint.setVisible(true);
		 this.actionPrintPreview.setVisible(true);
		 btnCancel.setVisible(false);
		 buildWBSTemplateTypeTree();
	}
	protected void initWorkButton() {
		super.initWorkButton();
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		
		actionAddTemplate.putValue(Action.SHORT_DESCRIPTION, "新增方案");
		actionAddTemplate.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_new"));
		actionViewTemplate.putValue(Action.SHORT_DESCRIPTION, "查看方案");
		actionViewTemplate.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_view"));
		actionEditTemplate.putValue(Action.SHORT_DESCRIPTION, "修改方案");
		actionEditTemplate.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_edit"));
		actionDelTemplate.putValue(Action.SHORT_DESCRIPTION, "删除方案");
		actionDelTemplate.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_delete"));
		
		KDWorkButton btnAdd=new KDWorkButton(actionAddTemplate);
		btnAdd.setEnabled(true);
		KDWorkButton btnView=new KDWorkButton(actionViewTemplate);
		btnView.setEnabled(true);
		KDWorkButton btnEdit=new KDWorkButton(actionEditTemplate);
		btnEdit.setEnabled(true);
		KDWorkButton btnDel=new KDWorkButton(actionDelTemplate);
		btnDel.setEnabled(true);
		
        treeMainView.getControlPane().add(btnAdd);
        treeMainView.getControlPane().add(btnView);
        treeMainView.getControlPane().add(btnEdit);
        treeMainView.getControlPane().add(btnDel);
        treeMainView.setShowControlPanel(true);
        
//        actionLocateTemplate.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_distribute"));
		
//		FDCClientHelper.setActionEnable(new ItemAction[]{actionAddNew,actionEdit,actionLocate,
//				actionView,actionRemove,actionQuery,actionPrint,actionPrintPreview}, false);
	}
	
	public void loadFields() {
		super.loadFields();
		try {
			buildWBSTemplateTypeTree();
		} catch (Exception e) {
			this.handleException(e);
		}
	}
	
	private void buildWBSTemplateTypeTree() throws Exception{
		/*ITreeBuilder treeBuilder = null;
		FilterInfo filter = getTreeFilter();
		treeBuilder = TreeBuilderFactory.createTreeBuilder(getTreeCtrl(), 50, 5, filter);
		treeBuilder.buildTree(treeMain);
		treeMain.setShowPopMenuDefaultItem(false);
		treeMain.setShowsRootHandles(true);*/
		
		/**
		 * 根据李涛需求描述将左边的树写死
		 */
//		DefaultKingdeeTreeNode root=new DefaultKingdeeTreeNode();
//		root.setText(TEMPLATEBASE);
//		KingdeeTreeModel model=new KingdeeTreeModel(root);
//		DefaultKingdeeTreeNode node1=new DefaultKingdeeTreeNode();
//		node1.setText(MAINTASKTEMPLATE);
//		DefaultKingdeeTreeNode node2=new DefaultKingdeeTreeNode();
//		node2.setText(SPECIALTASKTEMPLATE);
//		DefaultKingdeeTreeNode node3=new DefaultKingdeeTreeNode();
//		node3.setText(COMPLEMENTTASKTEMPLATE);
//		model.insertNodeInto(node1, root, root.getChildCount());
//		model.insertNodeInto(node2, root, root.getChildCount());
//		model.insertNodeInto(node3, root, root.getChildCount());
//		treeMain.setModel(model);
		
//		根据李涛新需求，添加一个模板类型的基础资料  fint
		DefaultKingdeeTreeNode root=new DefaultKingdeeTreeNode();
		root.setText(TEMPLATEBASE);
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
	
	private DefaultKingdeeTreeNode getTaskTypeSelectedNode(){
		return (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
	}
	
//	新增的时候调用该方法
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		ItemAction act = getActionFromActionEvent(e);
		/*根据李涛需求，修改只取左边树的名称
		 * if(act != null && act.equals(actionAddNew)
				&& getTaskTypeSelectedNode() != null
				&& getTaskTypeSelectedNode().getUserObject() instanceof TaskTypeInfo){
			uiContext.put("taskType", ((TaskTypeInfo) getTaskTypeSelectedNode().getUserObject()).getName());
			uiContext.put(UIContext.PARENTNODE, null);
		}*/
		
		if(act != null && act.equals(actionAddNew)
				&& getTaskTypeSelectedNode() != null){
			uiContext.put("templateType", getTaskTypeSelectedNode().getUserObject());
			uiContext.put(UIContext.PARENTNODE, null);
		}
		if(tblMain.getSelectManager() != null && tblMain.getSelectManager().getActiveRowIndex() > 0){
			uiContext.put(UIContext.PARENTNODE,tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(),"id").getValue().toString());
		}
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkIsEnabled("WBS模板已启用，不能删除！");
		super.actionRemove_actionPerformed(e);
	}
	public void onShow() throws Exception {
			super.onShow();
			btnCancel.setVisible(true);
			btnCancelCancel.setVisible(true);
	 }
	public void actionAddTemplate_actionPerformed(ActionEvent e)	throws Exception {
		checkTemplateTypePermission("ActionAddTemplate");
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
		UIContext uicontext = new UIContext(this);
		IUIWindow uiWindow = uiFactory.create(
				WBSTemplateTypeEditUI.class.getName(), uicontext, null, OprtState.ADDNEW);
		uiWindow.show();
		buildWBSTemplateTypeTree();
	}
	 
	public void actionDelTemplate_actionPerformed(ActionEvent e)	throws Exception {
		checkTemplateTypePermission("ActionDelTemplate");
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(node.getUserObject() != null){
			int returnValue = FDCMsgBox.showConfirm3("确定删除该模板？");
			if(FDCMsgBox.OK != returnValue) return;
			WBSTemplateTypeInfo typeInfo = (WBSTemplateTypeInfo) node.getUserObject();
			
			//ADD by zhiyuan_tang 2010/11/11 增加基础资料的控件策略  非创建组织不允许删除
			ClientControlStrategy myStrategy = createWBSTemplateTypeControlStrategy();
			myStrategy.checkCanDelete(typeInfo.getId().toString());
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("templateType.id",typeInfo.getId().toString(),CompareType.EQUALS));
			if(WBSTemplateFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showError("模板类型被引用，不能删除！");
				SysUtil.abort();
			}
			WBSTemplateTypeFactory.getRemoteInstance().delete(new ObjectUuidPK(typeInfo.getId()));
			buildWBSTemplateTypeTree();
		}
	}
	 
	public void actionEditTemplate_actionPerformed(ActionEvent e)	throws Exception {
		checkTemplateTypePermission("ActionEditTemplate");
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(node.getUserObject() != null){
			WBSTemplateTypeInfo typeInfo = (WBSTemplateTypeInfo) node.getUserObject();
			String id = typeInfo.getId().toString();
			IUIFactory uiFactory = null;
			uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
			UIContext uicontext = new UIContext(this);
			uicontext.put(UIContext.ID, id);
			IUIWindow uiWindow = uiFactory.create(
					WBSTemplateTypeEditUI.class.getName(), uicontext, null, OprtState.EDIT);
			uiWindow.show();
			buildWBSTemplateTypeTree();
		}
	}
	 
	public void actionViewTemplate_actionPerformed(ActionEvent e)	throws Exception {
		checkTemplateTypePermission("ActionViewTemplate");
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(node.getUserObject() != null){
			WBSTemplateTypeInfo typeInfo = (WBSTemplateTypeInfo) node.getUserObject();
			String id = typeInfo.getId().toString();
			IUIFactory uiFactory = null;
			uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
			UIContext uicontext = new UIContext(this);
			uicontext.put(UIContext.ID, id);
			IUIWindow uiWindow = uiFactory.create(
					WBSTemplateTypeEditUI.class.getName(), uicontext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}
	
	/**
	 * 增加WBS模板类型的控件策略
	 * @return
	 * @throws BOSException
	 */
	private ClientControlStrategy createWBSTemplateTypeControlStrategy() throws BOSException {
		String myControlType = ControlTypeUtil.getControlType(BOSObjectType.create("2D62BF66"));
		ClientControlStrategy myStrategy = ClientControlStrategy.create(myControlType, null);
		return myStrategy;
	}

	private void checkTemplateTypePermission(String actionName) {
		try {
			PermissionFactory.getRemoteInstance().checkFunctionPermission(
					new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
					new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()),
					new MetaDataPK("com.kingdee.eas.fdc.schedule.client.WBSTemplateListUI"), new MetaDataPK(actionName));
		} catch (EASBizException e1) {
			handUIExceptionAndAbort(e1);
		} catch (BOSException e1) {
			handUIExceptionAndAbort(e1);
		}
	}
}