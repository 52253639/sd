/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.tenancy.MarketUnitControlFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketingUnitListUI extends AbstractMarketingUnitListUI
{
	private static final Logger logger = CoreUIObject.getLogger(MarketingUnitListUI.class);


	public MarketingUnitListUI() throws Exception  {
		super();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MarketingUnitFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return MarketingUnitEditUI.class.getName();
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	
	private void initTree() throws Exception {
		DefaultKingdeeTreeNode oldSelected = null;
		Object objTree = this.treeMain.getLastSelectedPathComponent();
		if(objTree!=null) {
			oldSelected = (DefaultKingdeeTreeNode)objTree;
		}
		
		this.treeMain.setModel(FDCTreeHelper.getMarketTree(actionOnLoad, false));  //
		this.treeMain.expandAllNodes(true, (TreeNode)this.treeMain.getModel().getRoot());
		if(oldSelected!=null)
			this.treeMain.setSelectionNode(oldSelected);
		else
			this.treeMain.setSelectionNode((DefaultKingdeeTreeNode)this.treeMain.getModel().getRoot());
		
	} 
	
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		this.muControlQuery = new EntityViewInfo();
		this.tblControl.getStyleAttributes().setLocked(true);
		this.tblControl.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		

		initTree();

		
	}
	
	
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		TreePath newPath = e.getNewLeadSelectionPath();
		if(newPath==null) return;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)newPath.getLastPathComponent();
		if (node == null) {
			return;
		}
		
		this.tblControl.removeRows();
		
		if(node.getUserObject() instanceof OrgStructureInfo) {
			this.btnAddUser.setEnabled(true);
			this.btnDeleteUser.setEnabled(true);
			
			OrgStructureInfo orgStrInfo = (OrgStructureInfo)node.getUserObject();
			if(orgStrInfo.getUnit().getPartSale().isIsBizUnit()) {//实体销售组织节点
				this.actionAddNew.setEnabled(true);
				this.actionEdit.setEnabled(true);
				this.actionRemove.setEnabled(true);
			}else{
				this.actionAddNew.setEnabled(false);
				this.actionEdit.setEnabled(false);
				this.actionRemove.setEnabled(false);				
			}
			this.getUIContext().put("OrgStructureInfo", orgStrInfo);
			this.getUIContext().put("MarketingUnitInfo", null);
		}else if(node.getUserObject() instanceof MarketingUnitInfo) {
			this.btnAddUser.setEnabled(false);
			this.btnDeleteUser.setEnabled(false);
			
			MarketingUnitInfo muInfo = (MarketingUnitInfo)node.getUserObject();
			this.getUIContext().put("MarketingUnitInfo", muInfo);
			DefaultKingdeeTreeNode orgStrNode = (DefaultKingdeeTreeNode)FDCTreeHelper.getAllObjectIdMap((TreeNode)this.treeMain.getModel().getRoot(), "OrgStructure").get(muInfo.getOrgUnit().getId().toString());
			this.getUIContext().put("OrgStructureInfo", orgStrNode.getUserObject());
		}		
		
		this.execQuery();
	}
	
	

	protected void btnAddUser_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
		if(proNode!=null && proNode.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo orgStrInfo = (OrgStructureInfo)proNode.getUserObject();
			UIContext uiContext = new UIContext(this);
			uiContext.put("OrgStructureInfo", orgStrInfo);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
						MarketUnitControlModifyUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
			this.tblControl.refresh();
		}
	}
	
	protected void btnDeleteUser_actionPerformed(ActionEvent e)
			throws Exception {
		IRow selectRow = KDTableUtil.getSelectedRow(this.tblControl);
		if(selectRow==null) {
			MsgBox.showInfo("请先选择要删除的行！");
			return;
		}
		
		if(MsgBox.showConfirm2("确认要删除该管控用户吗？")==MsgBox.OK) {
			String idStr = (String)selectRow.getCell("id").getValue();
			this.tblControl.removeRow(selectRow.getRowIndex());
			MarketUnitControlFactory.getRemoteInstance().delete(new ObjectUuidPK(BOSUuid.read(idStr)));
			this.tblControl.refresh();
		}		
	}
	
	
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
	
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
		
		if(proNode==null) proNode = (DefaultKingdeeTreeNode)this.treeMain.getModel().getRoot();
		String orgUnitIdStr = "";
		String orgUnitIdStr2 = "'nullnull'";
		FilterInfo muFilter = null;
		if(proNode.getUserObject() instanceof OrgStructureInfo) {
			Map orgStrMap = FDCTreeHelper.getAllObjectIdMap(proNode, "OrgStructure");
			orgUnitIdStr = FDCTreeHelper.getStringFromSet(orgStrMap.keySet());
			orgUnitIdStr2 = orgUnitIdStr;
		}else if(proNode.getUserObject() instanceof MarketingUnitInfo) {
			MarketingUnitInfo muInfo = (MarketingUnitInfo)proNode.getUserObject();
			orgUnitIdStr = "'"+muInfo.getOrgUnit().getId().toString()+"'";
			String longNumber = muInfo.getLongNumber().replaceAll("!", ".");
			muFilter = new FilterInfo();
			//muFilter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber));
			muFilter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber+"%",CompareType.LIKE));
			
//			FilterInfo longNumFilter = new FilterInfo();
//			longNumFilter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber+".%",CompareType.LIKE));
//			try{
//				muFilter.mergeFilter(longNumFilter, "OR");
//			}catch(BOSException e) {e.printStackTrace();	}
		}
		
		FilterInfo filter = new FilterInfo();
		if(queryPK.equals(this.mainQueryPK))  {
			viewInfo = (EntityViewInfo) this.mainQuery.clone();			
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgUnitIdStr, CompareType.INNER));
			if(muFilter!=null) {
				try{
					filter.mergeFilter(muFilter, "AND");
				}catch(BOSException e) {e.printStackTrace();	}
			}
		}else if(queryPK.equals(this.muControlQueryPK)) {
			viewInfo = (EntityViewInfo) this.muControlQuery.clone();
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgUnitIdStr2, CompareType.INNER));
		}		
		
		if (viewInfo.getFilter() == null)
			viewInfo.setFilter(filter);
		else {
			try {
				viewInfo.getFilter().mergeFilter(filter, "AND");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}		
	
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		initTree();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String idKey = this.getSelectedKeyValue();
		if(idKey==null) return;
		
		if(MarketingUnitFactory.getRemoteInstance().exists("where parent.id = '"+idKey+"'")) {
			MsgBox.showInfo("非明细营销单元节点不能删除！");
			return;
		}
		
		super.actionRemove_actionPerformed(e);
		
		initTree();
	}
	
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	
	
	
}