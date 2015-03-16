package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.HandleTenancyFactory;
import com.kingdee.eas.framework.ICoreBase;


public class HandleTenancyListUI extends AbstractHandleTenancyListUI
{
    private static final Logger logger = CoreUIObject.getLogger(HandleTenancyListUI.class);

  //组织单元
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    public HandleTenancyListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.actionRemove.setVisible(false);
    	this.actionAddNew.setVisible(false);
    	initTree();
    	this.btnEdit.setVisible(false);
    	this.actionEdit.setVisible(false);
    	this.treeMain.setSelectionRow(0);
    	
    	String formatString = "yyyy-MM-dd";
		this.tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat(formatString);
 //   	this.tblMain.getColumn("id").getStyleAttributes().setHided(false);
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}
    
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
	throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
			filter.getFilterItems()
			.add(
					new FilterItemInfo("head.tenancyBill.sellProject.id", pro.getId()
							.toString()));
			if (this.saleOrg.isIsBizUnit()) {				
				this.actionAddNew.setEnabled(true);
			}
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
			this.actionAddNew.setEnabled(false);
		}
		this.mainQuery.setFilter(filter);
		this.tblMain.removeRows();
	}   
    
    protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {		
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
	}
    
	protected ICoreBase getBizInterface() throws Exception {
		return HandleTenancyFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return HandleRoomTenancyEditUI.class.getName();
	}

}