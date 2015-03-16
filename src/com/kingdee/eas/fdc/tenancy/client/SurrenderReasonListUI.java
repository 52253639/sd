/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.ISurrenderReason;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.SurrenderReasonFactory;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SurrenderReasonListUI extends AbstractSurrenderReasonListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SurrenderReasonListUI.class);
    private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

    public SurrenderReasonListUI() throws Exception
    {
        super();
    }

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionQuery_actionPerformed(e);
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		FilterInfo filter = viewInfo.getFilter();
		if (node != null && node.getUserObject() instanceof SellProjectInfo) 
    	{
    		SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
    		String sellProjectId = sellProject.getId().toString();
    		FilterInfo filter1 = new FilterInfo();
    		FilterItemInfo filterItemInfo = new FilterItemInfo("parent.id", sellProjectId);
    		filter1.getFilterItems().add(filterItemInfo);
    		try {
				filter.mergeFilter(filter1, "and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
		
		
		viewInfo.setFilter(filter);
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		IRow row = checkSelected(e);
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ISurrenderReason iSurrenderReason = SurrenderReasonFactory.getRemoteInstance();
		FDCDataBaseInfo model = SurrenderReasonFactory.getRemoteInstance().getFDCDataBaseInfo(new ObjectUuidPK(id));
		
		if(iSurrenderReason.disEnabled(new ObjectUuidPK(id), model)){
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
			showMessage();
		}		
		actionRefresh_actionPerformed(e);
		this.btnCancelCancel.setEnabled(true);
		this.btnCancel.setEnabled(false);
		
		
	}

	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = checkSelected(e);
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ISurrenderReason iSurrenderReason = SurrenderReasonFactory.getRemoteInstance();
		FDCDataBaseInfo model = SurrenderReasonFactory.getRemoteInstance().getFDCDataBaseInfo(new ObjectUuidPK(id));
		if(iSurrenderReason.enabled(new ObjectUuidPK(id), model)){
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
			showMessage();
		}		
		actionRefresh_actionPerformed(e);
		this.btnCancelCancel.setEnabled(false);
		this.btnCancel.setEnabled(true);
	}
    public void onLoad() throws Exception    {
    	super.onLoad();
    	
    	FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.actionQuery.setVisible(true);
		this.actionLocate.setVisible(true);
		actionCancel.setVisible(true);
		actionCancelCancel.setVisible(true);
		this.actionEdit.setEnabled(true);
		this.actionRemove.setEnabled(true);
		if (!saleOrg.isIsBizUnit())
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
		}
    }
    
	private IRow checkSelected(ActionEvent e) {
		IRow row = null;
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			// 请先指定一条记录
			MsgBox.showWarning("请先指定一条记录!");
		} else {
			row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		}
		return row;
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		if (saleOrg.isIsBizUnit()){
			if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
				if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled").getValue() != null) {
					boolean status = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled").getValue()).booleanValue();
					changeWBTEnabeld(status);
				}
			} else {
				disabledWBT();
	
			}
		}
	}

	/**
	 * 随着每一行规则的isEnabled的值改变，两个btn的状态也改变
	 * 
	 * @param isEnabled
	 *            boolean
	 */
	private void changeWBTEnabeld(boolean isEnabled) {
		this.btnCancelCancel.setEnabled(!isEnabled);
		this.btnCancel.setEnabled(isEnabled);

	}
	/**
	 * 把启用/禁止按钮disabled
	 */
	private void disabledWBT() {
		this.btnCancelCancel.setEnabled(false);
		this.btnCancel.setEnabled(false);

	}

    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }
	protected String getEditUIModal()
	{
		return UIFactoryName.MODEL;
	}
	protected void refresh(ActionEvent e) throws Exception
	{
		this.tblMain.removeRows();
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) 
	{
		DefaultKingdeeTreeNode sellProjectNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		uiContext.put("sellProject", sellProjectNode.getUserObject());
		super.prepareUIContext(uiContext, e);
	}
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();

    	if (node == null)
    	{
    		return;
    	}
    	FilterInfo filter = new FilterInfo();
    	if (node != null && node.getUserObject() instanceof SellProjectInfo) 
    	{
    		SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
    		String sellProjectId = sellProject.getId().toString();
    		filter.getFilterItems().add(new FilterItemInfo("parent.id", sellProjectId));
    		if (saleOrg.isIsBizUnit()) 
    		{
    			this.actionAddNew.setEnabled(true);
    		}
    	}
    	else
    	{
    		filter.getFilterItems().add(new FilterItemInfo("id", null));
    		this.actionAddNew.setEnabled(false);
    	}
    	this.mainQuery.setFilter(filter);
    	this.tblMain.removeRows();
    	//super.treeMain_valueChanged(e);
    }
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
			if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled").getValue() != null) {
				boolean status = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled").getValue()).booleanValue();
				if(status){
					MsgBox.showError("所选记录已经启用，请禁用后修改！");
					abort();
				}
			}
    	}
    	super.actionEdit_actionPerformed(e);
    }
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	//检查是否已被客户资料引用
		String idStr = this.getSelectedKeyValue();
		if(idStr!=null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("newQuitReason.id",idStr));
			if(QuitTenancyFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被退租管理引用，禁止删除!");
				abort();
			}
			
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("id",idStr));
			if(SurrenderReasonFactory.getRemoteInstance().exists(filter)){
				MsgBox.showInfo("所选记录已经启用，不可删除！");
				abort();
			}
			
		}
    	super.actionRemove_actionPerformed(e);
    }
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.tenancy.SurrenderReasonFactory.getRemoteInstance();
    }

	protected void initTree() throws Exception
	{
//		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad));
		this.treeMain.setModel(SHEHelper.getSellProjectTree(actionOnLoad, MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
//		KDTreeView treeView = new KDTreeView();
//		treeView.setTree(treeMain);
		treeView.setShowButton(true);

	}
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.tenancy.SurrenderReasonInfo objectValue = new com.kingdee.eas.fdc.tenancy.SurrenderReasonInfo();
		
        return objectValue;
    }

	protected ITreeBase getTreeInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}
}