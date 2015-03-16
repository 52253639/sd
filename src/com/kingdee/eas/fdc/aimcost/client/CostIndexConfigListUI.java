/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.*;

import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigFactory;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.invite.InviteTypeCollection;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.KDTreeNode;

/**
 * output class name
 */
public class CostIndexConfigListUI extends AbstractCostIndexConfigListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostIndexConfigListUI.class);
    private InviteTypeInfo inviteType=null;
    public CostIndexConfigListUI() throws Exception
    {
        super();
    }
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new CostIndexConfigInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return CostIndexConfigFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return CostIndexConfigEditUI.class.getName();
	}
	protected void initTree() throws Exception {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		TreeModel model = FDCClientHelper.createDataTree(InviteTypeFactory.getRemoteInstance(), filter, "²É¹ºÀà±ð");
		this.treeMain.setModel(model);
	}
    protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put("inviteType", inviteType);
	}
	public void onLoad() throws Exception {
		initTree();
		super.onLoad();
		this.treeMain.setSelectionRow(0);
	}
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if(node!=null&&node.getUserObject() instanceof InviteTypeInfo){
			inviteType=(InviteTypeInfo)node.getUserObject();
			if(inviteType.isIsLeaf()){
				this.actionAddNew.setEnabled(true);
			}else{
				this.actionAddNew.setEnabled(false);
			}
		}else{
			inviteType=null;
			this.actionAddNew.setEnabled(false);
		}
		this.execQuery();
	}
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		try {
			FilterInfo filter = new FilterInfo();
			if(node!=null&&node.getUserObject() instanceof InviteTypeInfo){
				filter.getFilterItems().add(new FilterItemInfo("inviteType.longNumber",inviteType.getLongNumber()+"%",CompareType.LIKE));
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (BOSException e) {
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
}