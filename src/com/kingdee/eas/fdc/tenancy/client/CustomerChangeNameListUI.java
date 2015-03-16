/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.CustomerChangeName;
import com.kingdee.eas.fdc.tenancy.CustomerChangeNameFactory;
import com.kingdee.eas.fdc.tenancy.ITenBillBase;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CustomerChangeNameListUI extends AbstractCustomerChangeNameListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerChangeNameListUI.class);
    public CustomerChangeNameListUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
    }

    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
       String state = ((BizEnumValueDTO)(this.getSelectedRow().getCell("state").getValue())).getAlias();
        if(FDCBillStateEnum.AUDITTED_VALUE.equals(state)){
        	this.actionAudit.setVisible(false);
        }
    }

    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
        FDCBillStateEnum  state =  FDCBillStateEnum.getEnum(((BizEnumValueDTO)(this.getSelectedRow().getCell("state").getValue())).getName());
        if(FDCBillStateEnum.AUDITTED_VALUE.equals(state)){
        	this.actionAudit.setVisible(false);
        }
    }
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
    protected ICoreBase getBizInterface() throws Exception {
    	return CustomerChangeNameFactory.getRemoteInstance();
    }
    protected String getEditUIName() {
    	return	CustomerChangeNameEditUI.class.getName() ;
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	initTree();
    	this.treeMain.setSelectionRow(0);
    	btnAudit.setIcon(FDCClientHelper.ICON_AUDIT);
    }
    
    protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
    protected String getKeyFieldName() {
    	return "id";
    }
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if(!saleOrg.isIsBizUnit())
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
//			this.actionUnAudit.setEnabled(false);
//			this.actionHandleTenancy.setEnabled(false);
//			this.actionReceiveBill.setEnabled(false);
//			this.actionRefundment.setEnabled(false);
//			this.actionRepairStartDate.setEnabled(false);
//			this.btnSpecial.setEnabled(false);
		}else
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionAudit.setEnabled(true);
//			this.actionUnAudit.setEnabled(true);
//			this.actionHandleTenancy.setEnabled(true);
//			this.actionReceiveBill.setEnabled(true);
//			this.actionRefundment.setEnabled(true);
//			this.actionRepairStartDate.setEnabled(true);
//			this.btnSpecial.setEnabled(true);
			if(node.getUserObject() instanceof SellProjectInfo)
			{
				this.actionAddNew.setEnabled(true);				
			}
		}
		this.execQuery();
	}
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			FilterInfo filter = new FilterInfo();
			if (node != null  &&  node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", pro.getId().toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}

			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			handleException(e);
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    	if (node != null  &&  node.getUserObject() instanceof SellProjectInfo) {
    		SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
    		uiContext.put("sellProject", pro);
    	}
    	
    }
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	FDCBillStateEnum state = FDCBillStateEnum.getEnum(((BizEnumValueDTO)(this.getSelectedRow().getCell("state").getValue())).getValue().toString());
    	if(!FDCBillStateEnum.SUBMITTED.equals(state)){
    		MsgBox.showInfo("单据状态不适合审批！");
    		this.abort();
    	}
    	String id = getSelectedId();
		if (id != null) {
			((ITenBillBase)getBizInterface()).audit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		refresh(null);
    }
}