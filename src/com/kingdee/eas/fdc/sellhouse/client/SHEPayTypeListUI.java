/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SHEPayTypeListUI extends AbstractSHEPayTypeListUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1293359314159658848L;
	private static final Logger logger = CoreUIObject
	.getLogger(SHEPayTypeListUI.class);
	
	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}

	/**
	 * output class constructor
	 */
	public SHEPayTypeListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
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
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.actionCancel.setVisible(true);
		this.actionCancelCancel.setVisible(true);
		if (!isSellOrg()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
		}
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
	
	private boolean isSellOrg() {
		boolean res = false;
		try {
			FullOrgUnitInfo fullOrginfo = SysContext.getSysContext()
					.getCurrentOrgUnit().castToFullOrgUnitInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("sellHouseStrut", Boolean.TRUE,
							CompareType.EQUALS));
			if (fullOrginfo != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("unit.id", fullOrginfo.getId()
								.toString(), CompareType.EQUALS));
			}
			filter.getFilterItems().add(
					new FilterItemInfo("tree.id", OrgConstants.SALE_VIEW_ID,
							CompareType.EQUALS));
			res = FDCOrgStructureFactory.getRemoteInstance().exists(filter);
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "获得售楼组织失败!");
		}

		return res;
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
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
			if (this.isSellOrg()) {
				this.actionAddNew.setEnabled(true);
			}
		} else {
			this.actionAddNew.setEnabled(false);
		}
		this.execQuery();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo project = (SellProjectInfo) node
						.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", project.getId()
								.toString()));
				
				/*String allSpIdStr = FDCTreeHelper
						.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node,
								"SellProject").keySet());
				if (allSpIdStr.trim().length() == 0) {
					allSpIdStr = "'nullnull'";
				}

				filter.getFilterItems()
						.add(
								new FilterItemInfo("project.id", allSpIdStr,
										CompareType.INNER));*/
		
				if (this.isSellOrg()) {
					this.actionAddNew.setEnabled(true);
				}
			}  else {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", null));
				this.actionAddNew.setEnabled(false);
			}
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
		
		String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper
				.getAllObjectIdMap(getNodeForParent(node), "SellProject").keySet());
		if (allSpIdStr.trim().length() >0) {
			uiContext.put("allSpIdStr", allSpIdStr);
		}
		super.prepareUIContext(uiContext, e);
	}
	
	private TreeNode getNodeForParent(TreeNode node){
		DefaultKingdeeTreeNode resultNode = (DefaultKingdeeTreeNode)node.getParent();
		
		if(resultNode!=null){
 			return getNodeForParent(resultNode);
		}else{
			return node;
		}
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SHEPayTypeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return SHEPayTypeEditUI.class.getName();
	}

	public void actionRemove_actionPerformed(ActionEvent arg0) throws Exception {
		String key = this.getSelectedKeyValue();
		if (key != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("payType.id", key));
			if (PurchaseFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("付款方案已经使用,不能删除!");
				return;
			}
		}
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		if(SHEPayTypeFactory.getRemoteInstance().exists("where isEnabled =1 and id='"+id+"'")) {
			MsgBox.showInfo("该条记录已启用，不允许删除！");
			return;
		}
		checkSelected();
		if (confirmRemove()) {
			//prepareRemove(null).callHandler();
			//Remove();
			//逻辑删除  add by renliang at 2011-6-1
			SHEPayTypeFactory.getRemoteInstance().deleteById(id);
			refresh(arg0);
		}
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		SHEPayTypeFactory.getRemoteInstance().disEnable(new ObjectUuidPK(id));
		MsgBox.showInfo(EASResource.getString(
				FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
		CacheServiceFactory.getInstance().discardType(new ObjectUuidPK(id).getObjectType());
		actionRefresh_actionPerformed(e);
	}

	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		SHEPayTypeFactory.getRemoteInstance().enable(new ObjectUuidPK(id));
		MsgBox.showInfo(EASResource.getString(
				FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
		CacheServiceFactory.getInstance().discardType(new ObjectUuidPK(id).getObjectType());
		actionRefresh_actionPerformed(e);
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();
		if (this.isSellOrg()&& activeRowIndex != -1) {
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
	
	protected String getLongNumberFieldName() {
		return "number";
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		
		if(SHEPayTypeFactory.getRemoteInstance().exists("where isEnabled =1 and id='"+id+"'")) {
			MsgBox.showInfo("该条记录已启用，不允许修改！");
			return;
		}

		
		super.actionEdit_actionPerformed(e);
	}

}