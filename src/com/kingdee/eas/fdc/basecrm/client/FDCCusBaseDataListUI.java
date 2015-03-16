/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataCollection;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataFactory;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupFactory;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupInfo;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerFactory;
import com.kingdee.eas.fdc.basecrm.IFDCCusBaseData;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FDCCusBaseDataListUI extends AbstractFDCCusBaseDataListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCCusBaseDataListUI.class);
    
    public FDCCusBaseDataListUI() throws Exception {
		super();

//		actionLocate.setVisible(false);
		actionMoveTree.setVisible(false);
		actionQueryScheme.setVisible(true);
//		actionQuery.setVisible(false);
		actionCancel.setVisible(true);
		actionCancelCancel.setVisible(true);
		actionGroupMoveTree.setVisible(false);
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
		IFDCCusBaseData iFDCCusBaseData = FDCCusBaseDataFactory.getRemoteInstance();
		String[] listId = getSelectedListId();
		boolean isCance=true;
		for (int i = 0; listId!= null && i < listId.length; i++) {
			FDCCusBaseDataInfo fDCCusBaseDataInfo = iFDCCusBaseData.getFDCCusBaseDataInfo("where id = '" + listId[i] + "'");
			if (!fDCCusBaseDataInfo.isIsEnabled()) {
				MsgBox.showConfirm2("此数据已是禁用状态,不能再禁用!");
				this.abort();
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
		IFDCCusBaseData iFDCCusBaseData = FDCCusBaseDataFactory.getRemoteInstance();
		String[] listId = getSelectedListId();
		boolean isCance=true;
		for (int i = 0; listId!=null && i < listId.length; i++) {
			FDCCusBaseDataInfo fDCCusBaseDataInfo = iFDCCusBaseData.getFDCCusBaseDataInfo("where id = '" + listId[i] + "'");
			if (fDCCusBaseDataInfo.isIsEnabled()) {
				MsgBox.showConfirm2("此数据已是启用状态,不能再启用!");
				this.abort();
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

	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		ControlPanel cp = (ControlPanel) treeView.getComponent(0);
		cp.getComponent(12).setVisible(false);
		cp.getComponent(12).setEnabled(false);
		chkIncludeChild.setSelected(false);
		chkIncludeChild.setVisible(false);

	}

	PropertyChangeListener treeMain_PropertyChangeListener=null;
	public void onShow() throws Exception {
		super.onShow();
		DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode) ((DefaultTreeModel) treeMain.getModel()).getRoot();
		rootNode.setText("客户基础资料");
		treeBuilder.refreshTreeNode(treeMain, (KDTreeNode) rootNode);
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
						rootNode.setText("客户基础资料");
						rootNode.setUserObject("客户基础资料");
						try { 
							treeBuilder.refreshTreeNode(tree, (KDTreeNode) rootNode);
							treeMain.expandAllNodes(true, (TreeNode) FDCCusBaseDataListUI.this.treeMain.getModel().getRoot());
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

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
		DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode) ((DefaultTreeModel) treeMain.getModel()).getRoot();
		rootNode.setText("客户基础资料");
		treeBuilder.refreshTreeNode(treeMain, (KDTreeNode) rootNode);
		treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		treeMain.setSelectionRow(0);
	}

	protected String getEditUIName() {
		return "com.kingdee.eas.fdc.basecrm.client.FDCCusBaseDataEditUI";
	}

	public void actionGroupAddNew_actionPerformed(ActionEvent e) throws Exception {
		KDTreeNode treeNode = getSelectedTreeNode();
		if (!allowAddDetailNode() && treeNode != null && (treeNode.getUserObject() instanceof TreeBaseInfo)) {
			SysUtil.abort();
		}
		
		if(treeNode.getLevel() < 1){
			MsgBox.showInfo(this, "此级别不允许新增!");
			this.abort();
		}
		
//		if(isHasData()){
//			MsgBox.showInfo("此组别下有数据，不能建立下级!");
//			SysUtil.abort();
//		}
		
		super.actionGroupAddNew_actionPerformed(e);
//		this.actionRefresh_actionPerformed(e);
	}

	
	
	private boolean isHasData() throws EASBizException, BOSException {
		Object obj = getSelectedTreeNode().getUserObject();
		if (obj != null && obj instanceof FDCCusBaseDataGroupInfo) {
			return FDCCusBaseDataFactory.getRemoteInstance().exists(
					"where group.id='" + ((FDCCusBaseDataGroupInfo) obj).getString("id") + "'");
		}
		return false;
	}
	
	protected void prepareGroupUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareGroupUIContext(uiContext, e);
		Object obj = getSelectedTreeNode().getUserObject();
		if(obj!=null && obj instanceof FDCCusBaseDataGroupInfo){
			uiContext.put("parentObj", obj);
		}
	}
	
	protected boolean allowAddDetailNode() {
		return true;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return FDCCusBaseDataGroupFactory.getRemoteInstance();
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
			
			AdminOrgUnitInfo orgInfo = SysContext.getSysContext().getCurrentAdminUnit();
			if (orgInfo == null || orgInfo.getUnitLayerType() == null || !("00000000-0000-0000-0000-00000000000162824988".equals(orgInfo.getUnitLayerType().getString("id")))) {
				MsgBox.showInfo("当前系统组织不是集团,不能操作!");
				SysUtil.abort();
			}
		}
		super.beforeActionPerformed(e);
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
		
		FDCCusBaseDataInfo da = FDCCusBaseDataFactory.getRemoteInstance().getFDCCusBaseDataInfo(new ObjectUuidPK(getSelectedKeyValue()));
		if(da.isIsEnabled()){
			MsgBox.showWarning(this, "已启用，不允许删除!");
			this.abort();
		}else if("IZ0jdIxGTO2yLHZ+AfoGFsRvOTM=".equals(da.getId().toString())){
			MsgBox.showWarning(this, "默认数据，不允许删除!");
			this.abort();
		}else if(isUse(da)){
			MsgBox.showWarning(this, "已被引用，不允许删除!");
			this.abort();
		}
		
		super.actionRemove_actionPerformed(e);
	}
	
	private boolean isUse(FDCCusBaseDataInfo info) throws EASBizException, BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("certificateType.id", info.getId().toString()));
		view.setFilter(filter);
		SHECustomerCollection coll = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
		FDCOrgCustomerCollection orgColl = FDCOrgCustomerFactory.getRemoteInstance().getFDCOrgCustomerCollection(view);
        if((coll != null && coll.size()>0) || (orgColl != null && orgColl.size()>0)){
			return true;
		}
        return false;
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (getSelectedIdValues() == null || getSelectedIdValues().size()==0) {
			MsgBox.showWarning(this, "请选中行!");
			this.abort();
		}
		
		checkSelected();
		if (getSelectedKeyValue() == null) {
			return;
		}
		if (!getBizInterface().exists(new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())))) {
			refreshList();
			throw new EASBizException(EASBizException.CHECKEXIST);
		}
		
		FDCCusBaseDataInfo da = FDCCusBaseDataFactory.getRemoteInstance().getFDCCusBaseDataInfo(new ObjectUuidPK(getSelectedKeyValue()));
		if(da.isIsEnabled()){
			MsgBox.showWarning(this, "已启用，不允许修改!");
			this.abort();
		}else if("IZ0jdIxGTO2yLHZ+AfoGFsRvOTM=".equals(da.getId().toString())){
			MsgBox.showWarning(this, "默认数据，不允许修改!");
			this.abort();
		}
		
		UIContext uiContext = new UIContext(this);
		Object obj = getSelectedTreeNode().getUserObject();
		if (obj != null && obj instanceof FDCCusBaseDataGroupInfo) {
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

	protected String getGroupEditUIName() {
		return "com.kingdee.eas.fdc.basecrm.client.FDCCusBaseDataGroupEditUI"; 
	}

	protected String getQueryFieldName() {
		return "group.id";
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCCusBaseDataFactory.getRemoteInstance();
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
		if(obj!=null && obj instanceof FDCCusBaseDataGroupInfo){
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
	

	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
    	this.tblMain.checkParsed();
		super.tblMain_tableSelectChanged(e);
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			Boolean isEnabled = (Boolean) row.getCell("isEnable").getValue();

			if (isEnabled.booleanValue()) {// 如果是启用,禁用按钮可用,修改按钮不可用
				actionCancel.setEnabled(true);
				actionCancelCancel.setEnabled(false);
			} else {
				actionCancel.setEnabled(false);
				actionCancelCancel.setEnabled(true);
			}
		}
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		try {
			Object obj=getSelectedTreeNode().getUserObject();
			if(obj!=null && obj instanceof FDCCusBaseDataGroupInfo){
				if(getSelectedTreeNode().getLevel() < 2){
					MsgBox.showInfo("此类别不允许进行新增操作!");
					this.abort();
				}
				
				String destBillEditUIClassName = "com.kingdee.eas.fdc.basecrm.client.FDCCusBaseDataEditUI";
				Map map = new UIContext(this);
				map.put(UIContext.OWNER, this);
				map.put("srcBillBOSTypeString", new FDCCusBaseDataInfo().getBOSType());
				map.put("GroupInfo", getSelectedTreeNode().getUserObject());// RetailAssistantDataGroupInfo
				IUIWindow uiWindow = null;
				uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(destBillEditUIClassName, map, null, OprtState.ADDNEW);
				// 可对创建的ui进行操作
				FDCCusBaseDataEditUI ui = (FDCCusBaseDataEditUI) uiWindow.getUIObject();
				ui.setSize(380, 295);
				// 开始展现UI
				uiWindow.show();
				this.refresh(e);
			}else{
				MsgBox.showInfo("此节点是根节点,不允许进行新增操作!");
				this.abort();
			}
			
			
		} catch (Exception err) {
			this.handUIException(err);
		}
	}
		
	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode) ((DefaultTreeModel) treeMain.getModel()).getRoot();
		
		rootNode.setText("客户基础资料");
	}
	
	public void actionGroupEdit_actionPerformed(ActionEvent e) throws Exception {
		if(getSelectedTreeNode().getLevel() < 2){
			MsgBox.showInfo(this, "此级别不允许修改!");
			this.abort();
		}
		
		Object obj = getSelectedTreeNode().getUserObject();
		if (obj != null && obj instanceof FDCCusBaseDataGroupInfo) {
			if(((FDCCusBaseDataGroupInfo)obj).isSysPreSet()){
				MsgBox.showInfo(this, "系统预设数据不允许修改");
				this.abort();
			}
		}
		super.actionGroupEdit_actionPerformed(e);
	}
	
	public void actionGroupRemove_actionPerformed(ActionEvent e) throws Exception {
		if(getSelectedTreeNode().getLevel() < 2){
			MsgBox.showInfo(this, "此级别不允许删除!");
			this.abort();
		}
		
		Object obj = getSelectedTreeNode().getUserObject();
		if (obj != null && obj instanceof FDCCusBaseDataGroupInfo) {
			if(((FDCCusBaseDataGroupInfo)obj).isSysPreSet()){
				MsgBox.showInfo(this, "系统预设数据不允许修改");
				this.abort();
			}
		}
		
		if(obj!=null && obj instanceof FDCCusBaseDataGroupInfo){
			FDCCusBaseDataCollection rs=FDCCusBaseDataFactory.getRemoteInstance()
				.getFDCCusBaseDataCollection("where group.id='"+((FDCCusBaseDataGroupInfo)obj).getString("id")+"'");
			if (rs != null && rs.size() > 0) {
				MsgBox.showInfo("此组别下有数据，不能删除!");
				SysUtil.abort();
			}
		}
		

		super.actionGroupRemove_actionPerformed(e);
	}
	
	protected String getOnloadPermItemName() {
//		return "FDCCusBaseData_view";
		return null;
	}

}