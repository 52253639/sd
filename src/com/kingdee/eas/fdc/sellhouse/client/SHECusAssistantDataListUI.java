/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDTreeView.ControlPanel;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitCollection;
import com.kingdee.eas.basedata.org.SaleOrgUnitFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ISHECusAssistantData;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataCollection;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataFactory;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataGroupFactory;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataGroupInfo;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SHECusAssistantDataListUI extends AbstractSHECusAssistantDataListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SHECusAssistantDataListUI.class);
    
    
    /*
     * 非售楼组织登录时显示上图，所有功能只能查看不可维护
     * 下方的树为当前用户有权限的项目树
     * 
     * 新增的默认全不勾选是否预设字段，不允许编辑。
     * 保存后则在树节点上增加一个新增的类别，需要校验名称和编码的唯一性；辅助资料类别通过组织隔离，
     * 下级售楼组织可以查看上级售楼组织数据，但不能维护，各级售楼组织只可以维护自己创建的资料类别。
     * 
     * 保存时需校验名称编码组织内唯一，保存后则在类别为客户来源的辅助资料下增加一条记录，根据类别和项目树过滤显示；如果选择组织，则显示对应类别下项目为空的明细记录；选择项目时，则显示对应类别下项目为空的明细记录+对应类别下项目等于选中项目的明细记录。
3、	新增的数据默认都是启用状态。
4、	当新增的辅助资料已被引用后，不能修改删除。
注意事项：上图上列的客户辅助资料树节点上的6种类别，在客户补丁打上的时候都默认挂在树上做为预设数据，预设辅助资料类别不能修改、删除，可以对该类别增加明细数据；只有非预设类别可以进行修改删除。

     * */
    
    /**
     * output class constructor
     */
    public SHECusAssistantDataListUI() throws Exception
    {
        super();
        actionLocate.setVisible(false);
		actionMoveTree.setVisible(false);
		actionQueryScheme.setVisible(true);
		actionQuery.setVisible(false);
		actionCancel.setVisible(true);
		actionCancelCancel.setVisible(true);
		actionGroupMoveTree.setVisible(false);
    }
    
    protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
    	this.tblMain.checkParsed();
		super.tblMain_tableSelectChanged(e);
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			Boolean isEnabled = (Boolean) row.getCell("idEnabled").getValue();
			
			//不用销售组织判断,改为售楼组织
			FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
			String idStr =null;
			if(null!= orgUnit.getId()){
				idStr = orgUnit.getId().toString();
			}
			if(!FDCSysContext.getInstance().getOrgMap().containsKey(idStr)){
				this.btnCancel.setEnabled(false);
				this.btnCancelCancel.setEnabled(false);
			} else {
				if (isEnabled.booleanValue()) {// 如果是启用,禁用按钮可用,修改按钮不可用
					actionCancel.setEnabled(true);
					actionCancelCancel.setEnabled(false);
				} else {
					actionCancel.setEnabled(false);
					actionCancelCancel.setEnabled(true);
				}
			}
//			if (!saleOrg.isIsBizUnit()) {
//				this.btnCancel.setEnabled(false);
//				this.btnCancelCancel.setEnabled(false);
//			} else {
//				if (isEnabled.booleanValue()) {// 如果是启用,禁用按钮可用,修改按钮不可用
//					actionCancel.setEnabled(true);
//					actionCancelCancel.setEnabled(false);
//				} else {
//					actionCancel.setEnabled(false);
//					actionCancelCancel.setEnabled(true);
//				}
//			}
		}
	}
    
    protected boolean isIgnoreTreeCUFilter() {
    	return true;
    }

    protected FilterInfo getDefaultFilterForTree() {
    	FilterInfo filter = super.getDefaultFilterForTree();
    	
    	FilterInfo tmp = new FilterInfo();
    	
    	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    	String longNum = saleOrg.getLongNumber();
    	
    	Set set = new HashSet();
    	set.add(longNum);
    	
    	while(longNum.lastIndexOf("!") != -1){
    		int lastIndex = longNum.lastIndexOf("!");
    		longNum = longNum.substring(0, lastIndex);
    		set.add(longNum);
    	}
    	
    	EntityViewInfo view = new EntityViewInfo();
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("id");
    	
    	FilterInfo of = new FilterInfo();
    	of.getFilterItems().add(new FilterItemInfo("longNumber", set, CompareType.INCLUDE));
    	view.setFilter(of);
    	
    	try {
			SaleOrgUnitCollection sales = SaleOrgUnitFactory.getRemoteInstance().getSaleOrgUnitCollection(view);
			Set ids = new HashSet();
			for(int i=0; i<sales.size(); i++){
				ids.add(sales.get(i).getId().toString());
			}
			
			tmp.getFilterItems().add(new FilterItemInfo("createOrgUnit.id", ids, CompareType.INCLUDE));
    	} catch (BOSException e1) {
    		this.handleException(e1);
		}
    	
    	try {
			filter.mergeFilter(tmp, "AND");
		} catch (BOSException e) {
			e.printStackTrace();
		}
    	return filter;
    }
    
    protected boolean isIgnoreCUFilter() {
    	return true;
    }
    
	/**
	 * 组织隔离,查出组织为空的那些预设数据
	 */
	protected FilterInfo getFilterForTree() {
		FilterInfo filter = new FilterInfo();
		String longNumber = SysContext.getSysContext().getCurrentOrgUnit().getLongNumber();
		String numbers[] = longNumber.split("!");// 对上级长编码拆分
		
		FilterInfo numFilter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		Set set = new HashSet();
		try {
		for (int i = 0; i < numbers.length; i++) {// 所有上级的编码
			FilterInfo parentFilter = new FilterInfo();
			parentFilter.getFilterItems().add(
					new FilterItemInfo("number",
							numbers[i]));
			parentFilter.mergeFilter(parentFilter, "OR");
			numFilter.mergeFilter(parentFilter, "OR");
		}
		view.setFilter(numFilter);
		FullOrgUnitCollection FullOrgUnitColl = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(view);
		if(FullOrgUnitColl != null && FullOrgUnitColl.size() > 0){
			for(int i = 0;i<FullOrgUnitColl.size();i++){
				String id = FullOrgUnitColl.get(i).getId().toString();
				set.add(id);
			}
		}
		filter.getFilterItems().add(
				new FilterItemInfo("orgUnit.id",set,CompareType.INCLUDE));
		
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return filter;
	}
	
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		FilterInfo filter = getFilterForTree();
		if (node != null) {
			if(node.getUserObject() instanceof SellProjectInfo){
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper
						.getAllObjectIdMapForRoot(node, "SellProject").keySet());// 所有父类节点的id
				if (allSpIdStr.trim().length() == 0) {
					allSpIdStr = "'nullnull'";
				}
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", allSpIdStr,
								CompareType.INNER));
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", null));// id为空的
				filter.setMaskString(" #0 and ( #1 or #2 )");
			}else {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", null));
			}
//			//String sellProId = ((SellProjectInfo) node.getUserObject()).getId().toString();
//			FilterInfo tmp = new FilterInfo();
//			//tmp.getFilterItems().add(new FilterItemInfo("project.id", sellProId));
//			
//			
//			try {
//				filter.mergeFilter(tmp, "OR");
//			} catch (BOSException e) {
//				e.printStackTrace();
//			}
		}

		viewInfo = (EntityViewInfo) this.mainQuery.clone();
		if (viewInfo.getFilter() != null) {
			try {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		} else {
			viewInfo.setFilter(filter);
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}
    
    private String[] getSelectedListId() {
		KDTSelectBlock selectBlock = tblMain.getSelectManager().get();
		HashMap mapId = new HashMap();

		if (selectBlock != null) {
			for (int i = selectBlock.getTop(); i <= selectBlock.getEndRow(); i++) {
				IRow row = tblMain.getRow(i);
				ICell cell = row.getCell("id");
				Object keyValue = cell.getValue();

				if (keyValue != null) {
					// 过滤重复的行
					if (!mapId.containsKey(keyValue.toString())) {
						mapId.put(keyValue.toString(), keyValue.toString());
					}
				}
			}
		}

		String[] listId = null;
		if (mapId != null && mapId.size() > 0) {
			Iterator iterat = mapId.keySet().iterator();
			listId = new String[mapId.size()];
			int index = 0;
			while (iterat.hasNext()) {
				listId[index] = (String) iterat.next();
				index++;
			}
		}

		return listId;
	}
	
	//启用操作
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		ISHECusAssistantData iSHECusAssistantData = SHECusAssistantDataFactory.getRemoteInstance();
		String[] listId = getSelectedListId();
		boolean isCance=true;
		for (int i = 0; listId!= null && i < listId.length; i++) {
			SHECusAssistantDataInfo sHECusAssistantDataInfo = iSHECusAssistantData.getSHECusAssistantDataInfo("where id = '" + listId[i] + "'");
			if (!sHECusAssistantDataInfo.isIsEnabled()) {
				MsgBox.showInfo("此数据已是禁用状态,不能再禁用!");
				isCance=!isCance;
				break;
			}
		}
		if(isCance){
			super.actionCancel_actionPerformed(e);	
		}
		
		this.refresh(e);
	}
	
	//禁用操作
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		ISHECusAssistantData iSHECusAssistantData = SHECusAssistantDataFactory.getRemoteInstance();
		String[] listId = getSelectedListId();
		boolean isCance=true;
		for (int i = 0; listId!=null && i < listId.length; i++) {
			SHECusAssistantDataInfo sHECusAssistantDataInfo = iSHECusAssistantData.getSHECusAssistantDataInfo("where id = '" + listId[i] + "'");
			if (sHECusAssistantDataInfo.isIsEnabled()) {
				MsgBox.showInfo("此数据已是启用状态,不能再启用!");
				isCance=!isCance;
				break;
			}
		}
		if(isCance){
			super.actionCancelCancel_actionPerformed(e);
		}
		
		super.cancelCancel();
		this.refresh(e);
	}
    
	public void onLoad() throws Exception {
		super.onLoad();
		ControlPanel cp = (ControlPanel) treeView.getComponent(0);
		cp.getComponent(12).setVisible(false);
		cp.getComponent(12).setEnabled(false);
		chkIncludeChild.setSelected(false);
		chkIncludeChild.setVisible(false);
		
		//this.treeProject.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeProject.setModel(CRMTreeHelper.getSellProjectTree(actionOnLoad,false));
		treeProject.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		treeProject.setSelectionRow(0);
		
		if(!FDCSysContext.getInstance().checkIsSHEOrg()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			
			this.actionGroupAddNew.setEnabled(false);
			this.actionGroupEdit.setEnabled(false);
			this.actionGroupRemove.setEnabled(false);
			
		}
	}
	PropertyChangeListener treeMain_PropertyChangeListener=null;
	public void onShow() throws Exception {
		super.onShow();
		
		DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode) ((DefaultTreeModel) treeMain.getModel()).getRoot();
		rootNode.setText("客户辅助资料");
		treeBuilder.refreshTreeNode(treeMain, (KDTreeNode) rootNode, this.getDefaultFilterForTree());
		treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		treeMain.setSelectionRow(0);
		
		treeMain_PropertyChangeListener=new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getNewValue() instanceof TreePath && evt.getOldValue()==null){
					TreePath path=(TreePath)evt.getNewValue();
					if("[root]".equals(path.toString())){
						KDTree tree=(KDTree)evt.getSource();
						
						tree.removePropertyChangeListener(treeMain_PropertyChangeListener);
						DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode) ((DefaultTreeModel) tree.getModel()).getRoot();
						rootNode.setText("客户辅助资料");
						rootNode.setUserObject("客户辅助资料");
						try { 
							treeBuilder.refreshTreeNode(tree, (KDTreeNode) rootNode, SHECusAssistantDataListUI.this.getDefaultFilterForTree());
							treeMain.expandAllNodes(true, (TreeNode) SHECusAssistantDataListUI.this.treeMain.getModel().getRoot());
							treeMain.setSelectionRow(0);
						} catch (Exception e) {
							e.printStackTrace();
						}
						tree.addPropertyChangeListener(treeMain_PropertyChangeListener);
					}
				}
			}
		};
		treeMain.addPropertyChangeListener(treeMain_PropertyChangeListener);
		
	}

	protected void treeProject_valueChanged(TreeSelectionEvent e) throws Exception {
		execQuery();
	}
	
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
		DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode) ((DefaultTreeModel) treeMain.getModel()).getRoot();
		rootNode.setText("客户辅助资料");
		treeBuilder.refreshTreeNode(treeMain, (KDTreeNode) rootNode, this.getDefaultFilterForTree());
		treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		treeMain.setSelectionRow(0);
	}

	protected String getEditUIName() {
		return SHECusAssistantDataEditUI.class.getName();
	}

	public void actionGroupAddNew_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode treeNode = getSelectedTreeNode();
		if (!allowAddDetailNode() && treeNode != null && (treeNode.getUserObject() instanceof TreeBaseInfo)) {
			SysUtil.abort();
		}
		super.actionGroupAddNew_actionPerformed(e);
	}

	protected boolean allowAddDetailNode() {
		return true;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return SHECusAssistantDataGroupFactory.getRemoteInstance();
	}

	public void actionGroupView_actionPerformed(ActionEvent e) throws Exception {
		super.actionGroupView_actionPerformed(e);
	}

	public void beforeActionPerformed(ActionEvent e) {
		if (actionAddNew.getClass().getName().equals(e.getActionCommand()) 
				|| actionEdit.getClass().getName().equals(e.getActionCommand())
				|| actionRemove.getClass().getName().equals(e.getActionCommand()) 
				|| actionCancel.getClass().getName().equals(e.getActionCommand())
				|| actionCancelCancel.getClass().getName().equals(e.getActionCommand())
				|| actionGroupAddNew.getClass().getName().equals(e.getActionCommand())
				|| actionGroupEdit.getClass().getName().equals(e.getActionCommand())
				|| actionGroupRemove.getClass().getName().equals(e.getActionCommand())) {
			
			FDCSysContext.getInstance().checkIsSHEOrg(this);
		}
		super.beforeActionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (getSelectedIdValues() == null || getSelectedIdValues().size() == 0) {
			MsgBox.showWarning(this, "请选中行!");
			this.abort();
		}

//		super.actionEdit_actionPerformed(e);
		checkSelected();
		if (getSelectedKeyValue() == null) {
			return;
		}
		if (!getBizInterface().exists(new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())))) {
			refreshList();
			throw new EASBizException(EASBizException.CHECKEXIST);
		}
		
		SHECusAssistantDataInfo da = SHECusAssistantDataFactory.getRemoteInstance().getSHECusAssistantDataInfo(new ObjectUuidPK(getSelectedKeyValue()));
		if(da.isIsEnabled()){
			MsgBox.showWarning(this, "已启用，不允许修改!");
			this.abort();
		}
		
		UIContext uiContext = new UIContext(this);
		Object obj = getSelectedTreeNode().getUserObject();
		if (obj != null && obj instanceof SHECusAssistantDataGroupInfo) {
			uiContext.put("GroupInfo", getSelectedTreeNode().getUserObject());
		}
		uiContext.put(UIContext.ID, getSelectedKeyValue());

		prepareUIContext(uiContext, e);

		IUIWindow uiWindow = null;
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(getEditUIName(), uiContext, null,
				OprtState.EDIT);
		uiWindow.show();
		this.refresh(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (getSelectedKeyValue() == null) {
			return;
		}
		if (!getBizInterface().exists(new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())))) {
			refreshList();
			throw new EASBizException(EASBizException.CHECKEXIST);
		}
		
		SHECusAssistantDataInfo da = SHECusAssistantDataFactory.getRemoteInstance().getSHECusAssistantDataInfo(new ObjectUuidPK(getSelectedKeyValue()));
		if(da.isIsEnabled()){
			MsgBox.showWarning(this, "已启用，不允许删除!");
			this.abort();
		}
		
		super.actionRemove_actionPerformed(e);
	}
	
	protected String getGroupEditUIName() {
		return "com.kingdee.eas.fdc.sellhouse.client.SHECusAssistantDataGroupEditUI"; 
	}

	protected String getQueryFieldName() {
		return "group.id";
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SHECusAssistantDataFactory.getRemoteInstance();
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		return null;
	}
	
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();

		if (getSelectedKeyValue() == null) {
			return;
		}
		if (!getBizInterface().exists(new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())))) {
			refreshList();
			throw new EASBizException(EASBizException.CHECKEXIST);
		}

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		
		Object obj=getSelectedTreeNode().getUserObject();
		if(obj!=null && obj instanceof SHECusAssistantDataGroupInfo){
			uiContext.put("GroupInfo", getSelectedTreeNode().getUserObject());
		}
		
		// 供子类定义要传递到EditUI中扩展的属性
		prepareUIContext(uiContext, e);

		IUIWindow uiWindow = null;
		if (SwingUtilities.getWindowAncestor(this) != null && SwingUtilities.getWindowAncestor(this) instanceof JDialog) {
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(getEditUIName(), uiContext, null, OprtState.VIEW);
		} else {
			// 创建UI对象并显示
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(getEditUIName(), uiContext, null, OprtState.VIEW);
		}

		uiWindow.show();
		this.refresh(e);
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		if(node != null  &&  node.getUserObject() != null){
			if(node.getUserObject() instanceof SellProjectInfo){
				uiContext.put("sellProject", (SellProjectInfo)node.getUserObject());
			}
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		try {
			Object obj=getSelectedTreeNode().getUserObject();
			if(obj!=null && obj instanceof SHECusAssistantDataGroupInfo){
				String destBillEditUIClassName = "com.kingdee.eas.fdc.sellhouse.client.SHECusAssistantDataEditUI";
				Map map = new UIContext(this);
				prepareUIContext((UIContext) map, e);
				map.put(UIContext.OWNER, this);
				map.put("srcBillBOSTypeString", new SHECusAssistantDataInfo().getBOSType());
				map.put("GroupInfo", getSelectedTreeNode().getUserObject());// RetailAssistantDataGroupInfo
				IUIWindow uiWindow = null;
				uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(destBillEditUIClassName, map, null, OprtState.ADDNEW);
				// 可对创建的ui进行操作
				SHECusAssistantDataEditUI ui = (SHECusAssistantDataEditUI) uiWindow.getUIObject();
				ui.setSize(380, 295);
				// 开始展现UI
				uiWindow.show();
				this.refresh(e);
			}else{
				MsgBox.showInfo("此节点是根节点,不允许进行新增操作!");
			}
		} catch (Exception err) {
			this.handUIException(err);
		}
	}
		
	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode) ((DefaultTreeModel) treeMain.getModel()).getRoot();
		
		rootNode.setText("客户辅助资料");
	}
	
	public void actionGroupRemove_actionPerformed(ActionEvent e) throws Exception {
		Object obj=getSelectedTreeNode().getUserObject();
		if(obj!=null && obj instanceof SHECusAssistantDataGroupInfo){
			SHECusAssistantDataCollection rs=SHECusAssistantDataFactory.getRemoteInstance()
				.getSHECusAssistantDataCollection("where group.id='"+((SHECusAssistantDataGroupInfo)obj).getString("id")+"'");
			if (rs != null && rs.size() > 0) {
				MsgBox.showInfo("此组别下有数据，不能删除!");
				SysUtil.abort();
			}
			
			if(((SHECusAssistantDataGroupInfo)obj).isIsExtendField()){
				MsgBox.showInfo("预设数据，不能删除!");
				SysUtil.abort();
			}else{
				SHECusAssistantDataGroupInfo typeInfo = (SHECusAssistantDataGroupInfo)obj;
				FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
				if(typeInfo.getCreateOrgUnit() != null){
					if(!orgUnit.getId().equals(typeInfo.getCreateOrgUnit().getId())){
						MsgBox.showInfo("非当前组织数据不允许操作!");
						SysUtil.abort();
					}
				}
			}
		}
		
		super.actionGroupRemove_actionPerformed(e);
	}
	
	public void actionGroupEdit_actionPerformed(ActionEvent e) throws Exception {
		Object obj=getSelectedTreeNode().getUserObject();
		if(obj!=null && obj instanceof SHECusAssistantDataGroupInfo){
			SHECusAssistantDataGroupInfo typeInfo = (SHECusAssistantDataGroupInfo)obj;
			if(typeInfo.isIsExtendField()){
				MsgBox.showInfo("预设数据，不能修改!");
				SysUtil.abort();
			}else{
				FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
				if(typeInfo.getCreateOrgUnit() != null){
					if(!orgUnit.getId().equals(typeInfo.getCreateOrgUnit().getId())){
						MsgBox.showInfo("非当前组织数据不允许操作!");
						SysUtil.abort();
					}
				}
			}
		}
		super.actionGroupEdit_actionPerformed(e);
	}
	
	protected String getOnloadPermItemName() {
//		return "SHECusAssistantData_view";
		return null;
	}


}