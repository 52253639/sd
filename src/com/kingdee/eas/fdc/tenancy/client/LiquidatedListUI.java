/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.reportone.r1.print.designer.gui.ScriptViewer.Action;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.ILiquidated;
import com.kingdee.eas.fdc.tenancy.LiquidatedFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class LiquidatedListUI extends AbstractLiquidatedListUI
{
    private static final Logger logger = CoreUIObject.getLogger(LiquidatedListUI.class);
    private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    /**
     * output class constructor
     */
    public LiquidatedListUI() throws Exception
    {
        super();
    }

    /**
     * output treeMain_valueChanged method
     */
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
    		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
    		if (saleOrg.isIsBizUnit()) 
    		{
    			this.actionAddNew.setEnabled(true);
    			this.actionImport.setEnabled(true);
    		}
    	}
    	else
    	{
    		filter.getFilterItems().add(new FilterItemInfo("id", null));
    		this.actionAddNew.setEnabled(false);
    		this.actionImport.setEnabled(false);
    	}
    	this.mainQuery.setFilter(filter);
    	this.tblMain.removeRows();
    }
    public void onLoad() throws Exception    {
    	super.onLoad();
    	this.tblMain.getColumn("isEnabled").getStyleAttributes().setHided(false);
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
			this.actionImport.setEnabled(false);
		}
    }

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
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
	protected String getEditUIModal()
	{
		return UIFactoryName.MODEL;
	}
	protected void refresh(ActionEvent e) throws Exception
	{
		this.tblMain.removeRows();
	}
	protected String getEditUIName() {
		return LiquidatedEditUI.class.getName();
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return LiquidatedFactory.getRemoteInstance();
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) 
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof String) {
			MsgBox.showWarning("请选择具体租售项目！");
			this.abort();
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
			.getUserObject();
			uiContext.put("sellProject", sellProject);
		} 
		super.prepareUIContext(uiContext, e);
	}
	protected void initTree() throws Exception
	{
		this.treeMain.setModel(FDCTreeHelper.getSubareaTree(this.actionOnLoad,null));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected String getLongNumberFieldName() {
		return "number";
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
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		IRow row = checkSelected(e);
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ILiquidated iSurrenderReason = LiquidatedFactory.getRemoteInstance();
		FDCDataBaseInfo model = LiquidatedFactory.getRemoteInstance().getFDCDataBaseInfo(new ObjectUuidPK(id));
		
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
		ILiquidated iSurrenderReason = LiquidatedFactory.getRemoteInstance();
		FDCDataBaseInfo model = LiquidatedFactory.getRemoteInstance().getFDCDataBaseInfo(new ObjectUuidPK(id));
		if(iSurrenderReason.enabled(new ObjectUuidPK(id), model)){
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
			showMessage();
		}		
		actionRefresh_actionPerformed(e);
		this.btnCancelCancel.setEnabled(false);
		this.btnCancel.setEnabled(true);
	}
	protected void initWorkButton() {
		super.initWorkButton();
		this.actionImport.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_input"));
	}

	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();

		if (node.getUserObject() instanceof SellProjectInfo) {
			UIContext uiContext = new UIContext(this);
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			uiContext.put("sellProject", sellProject);
			IUIWindow uiWindow =UIFactory.createUIFactory(UIFactoryName.MODEL).create("com.kingdee.eas.fdc.tenancy.client.LiquidatedQueryUI", uiContext, null, OprtState.VIEW);		    
			uiWindow.show();
			actionRefresh_actionPerformed(e);
		} 
		super.actionImport_actionPerformed(e);
		
	}
	
	
}