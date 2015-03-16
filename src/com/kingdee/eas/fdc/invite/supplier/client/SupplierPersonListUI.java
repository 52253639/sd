/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.KDTreeNode;

/**
 * output class name
 */
public class SupplierPersonListUI extends AbstractSupplierPersonListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierPersonListUI.class);
    private SupplierFileTypeInfo supplierFileType=null;
    public SupplierPersonListUI() throws Exception
    {
        super();
    }
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new SupplierPersonInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierPersonFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return SupplierPersonEditUI.class.getName();
	}
	protected void initTree() throws Exception {
		KDTreeNode root = new KDTreeNode("µµ∞∏∑÷¿‡");
		KingdeeTreeModel mode=new KingdeeTreeModel(root);
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view=new EntityViewInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));	
		view.setFilter(filter);
		SupplierFileTypeCollection col=SupplierFileTypeFactory.getRemoteInstance().getSupplierFileTypeCollection(view);
		CRMHelper.sortCollection(col, "number", true);
		for(int i=0;i<col.size();i++){
			KDTreeNode child = new KDTreeNode(col.get(i).getName());
			child.setUserObject(col.get(i));
			root.add(child);
		}
		mode.setRoot(root);
		this.treeMain.setModel(mode);
	}
    protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put("supplierFileType", supplierFileType);
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
		if(node!=null&&node.getUserObject() instanceof SupplierFileTypeInfo){
			supplierFileType=(SupplierFileTypeInfo)node.getUserObject();
			this.actionAddNew.setEnabled(true);
		}else{
			supplierFileType=null;
			this.actionAddNew.setEnabled(false);
		}
		this.execQuery();
	}
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		try {
			FilterInfo filter = new FilterInfo();
			if(node!=null&&node.getUserObject() instanceof SupplierFileTypeInfo){
				filter.getFilterItems().add(new FilterItemInfo("supplierFileType.id", supplierFileType.getId().toString()));	
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