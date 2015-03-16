/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import javax.swing.tree.TreeNode;
import org.apache.log4j.Logger;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.ProductDetialFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ProductDetailListUI extends AbstractProductDetailListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProductDetailListUI.class);
    
    private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    
    /**
     * output class constructor
     */
    public ProductDetailListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception
    {
    	super.onLoad();
    	
    	FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		if (!saleOrg.isIsBizUnit())
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
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
    		}
    	}
    	else
    	{
    		filter.getFilterItems().add(new FilterItemInfo("id", null));
    		this.actionAddNew.setEnabled(false);
    	}
    	this.mainQuery.setFilter(filter);
    	this.tblMain.removeRows();
    }


	protected ITreeBase getTreeInterface() throws Exception
	{
		return null;
	}

	protected void refresh(ActionEvent e) throws Exception
	{
		this.tblMain.removeRows();
	}
	protected String getEditUIName()
	{
		return ProductDetailEditUI.class.getName();
	}
	protected void initTree() throws Exception
	{
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) 
	{
		DefaultKingdeeTreeNode sellProjectNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		uiContext.put("sellProject", sellProjectNode.getUserObject());
		super.prepareUIContext(uiContext, e);
	}

	protected String getEditUIModal()
	{
		return UIFactoryName.MODEL;
	}
	
	protected ICoreBase getBizInterface() throws Exception
	{
		return ProductDetialFactory.getRemoteInstance();
	}
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception
	{
	}
}