/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.CollectionFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CollectionListUI extends AbstractCollectionListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CollectionListUI.class);
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	/**
	 * output class constructor
	 */
	public CollectionListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(CRMTreeHelper.getSellProjectTree(this.actionOnLoad));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.treeMain.setSelectionRow(0);
	}
	
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}
	
	public void onLoad() throws Exception {
		
		super.onLoad();
		this.actionCancelCancel.setVisible(true);
		this.actionCancel.setVisible(true);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();
		if (saleOrg.isIsBizUnit() && activeRowIndex != -1) {
			if (this.tblMain.getRow(activeRowIndex).getCell("enable") != null) {
				Boolean value = (Boolean) this.tblMain.getCell(activeRowIndex,
						"enable").getValue();
				if (value == null) {
					value = Boolean.FALSE;
				}
				boolean status = (value).booleanValue();
				actionCancelCancel.setEnabled(!status);
				actionCancel.setEnabled(status);
			}
		} else {
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] {
					actionCancel, actionCancelCancel }, false);
		}
	}


	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			if (saleOrg.isIsBizUnit()) {
				FDCClientHelper
						.setActionEnableAndNotSetVisible(new ItemAction[] {
								actionAddNew, actionEdit, actionRemove,
								actionCancel, actionCancelCancel }, true);
			}
		} else {
			this.actionAddNew.setEnabled(false);
			if (!saleOrg.isIsBizUnit())
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] {
					actionAddNew, actionEdit, actionRemove, actionCancel,
					actionCancelCancel }, false);
			
				
		}
		execQuery();
//		super.treeMain_valueChanged(e);
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo project = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("project.longNumber", project.getLongNumber()));
				filter.getFilterItems().add(new FilterItemInfo("project.longNumber", project.getLongNumber()+"!%",CompareType.LIKE));
				filter.setMaskString("#0 or #1");
			}else{
	    		Map spIdMap = FDCTreeHelper.getAllObjectIdMap(node, "SellProject");
	    		if(spIdMap.size()>0)
	    			filter.getFilterItems().add(new FilterItemInfo("project.id",FDCTreeHelper.getStringFromSet(spIdMap.keySet()),CompareType.INNER));
	    		else
	    			filter.getFilterItems().add(new FilterItemInfo("project.id", null));
	    	} 
		}else {
			filter.getFilterItems().add(new FilterItemInfo("project.id", null));
		}
		
		// 合并查询条件
		viewInfo = (EntityViewInfo) mainQuery.clone();
		try {
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		
		if(CollectionFactory.getRemoteInstance().exists("where isEnabled =1 and id='"+id+"'")) {
			MsgBox.showInfo("该条记录已启用，不允许修改！");
			return;
		}
//		if (id != null) {
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("sheCollection", id.toString()));
//			if (PurchaseElsePayListEntryFactory.getRemoteInstance().exists(filter)) {
//				MsgBox.showInfo("该记录已经使用,不能修改!");
//				return;
//			}
//		}
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
//		String key = this.getSelectedKeyValue();
//		if (key != null) {
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("payType.id", key));
//			if (PurchaseFactory.getRemoteInstance().exists(filter)) {
//				MsgBox.showInfo("付款方案已经使用,不能删除!");
//				return;
//			}
//		}
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		if(CollectionFactory.getRemoteInstance().exists("where isEnabled =1 and id='"+id+"'")) {
			MsgBox.showInfo("该条记录已启用，不允许修改！");
			return;
		}
//		if (id != null) {
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("sheCollection", id.toString()));
//			if (PurchaseElsePayListEntryFactory.getRemoteInstance().exists(filter)) {
//				MsgBox.showInfo("该记录已经使用,不能删除!");
//				return;
//			}
//		}
//		checkSelected();
		if (confirmRemove()) {
			Remove();
			refresh(e);
		}
	}


	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		CollectionFactory.getRemoteInstance().disEnable(new ObjectUuidPK(id));
		MsgBox.showInfo(EASResource.getString(
				FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
//		CacheServiceFactory.getInstance().discardType(new ObjectUuidPK(id).getObjectType());
		actionRefresh_actionPerformed(e);
//		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		String moneyName =  (String) row.getCell("moneyName").getValue();
/*		SellProjectInfo project  = null;
	    DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
	    if(node.getUserObject() instanceof SellProjectInfo){
	    	project = (SellProjectInfo) node.getUserObject();
	    }*/
	    

/*		if (id != null) {
			FilterInfo filter = new FilterInfo();
			
			filter.getFilterItems().add(new FilterItemInfo("project.orgUnit.id",SysContext.getSysContext().getCurrentSaleUnit().getId()));
			
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE ));
			filter.getFilterItems().add(new FilterItemInfo("moneyName.name", moneyName));
			if (CollectionFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("该款项名称已存在启用的公式,请检查！");
				return;
			}
		}
		 //BT589130 要求可以重复启用
		*/
		
		
		CollectionFactory.getRemoteInstance().enable(new ObjectUuidPK(id));
		MsgBox.showInfo(EASResource.getString(
				FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
		actionRefresh_actionPerformed(e);
//		super.actionCancelCancel_actionPerformed(e);
	}

	
	protected ITreeBase getTreeInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}protected ICoreBase getBizInterface() throws Exception {

		return CollectionFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return CollectionEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof String) {
			MsgBox.showInfo("请选择具体销售项目！");
			this.abort();
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
					.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
		super.prepareUIContext(uiContext, e);
	}


}