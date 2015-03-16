/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.event.*;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class PaymentReportUI extends AbstractPaymentReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(PaymentReportUI.class);
    protected SellProjectInfo sellProject = null;
    public PaymentReportUI() throws Exception
    {
        super();
        FDCSysContext.getInstance().checkIsSHEOrg(this);
    }

    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}
	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
	}
	public void onLoad() throws Exception{
	    	
    	actionQuery.setEnabled(false);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		
		if(this.getUIContext().get("filter")==null){
			initTree();
		}else{
			this.toolBar.setVisible(false);
			this.treeView.setVisible(false);
			this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		}
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionView.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionRemove.setVisible(false);
		this.btnLocate.setVisible(false);
		
		String[] fields=new String[this.tblMain.getColumnCount()];
		for(int i=0;i<this.tblMain.getColumnCount();i++){
			fields[i]=this.tblMain.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(this.tblMain,fields);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		
		actionQuery.setEnabled(true);
		
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		try	{
			FilterInfo filter = new FilterInfo();
			if(this.getUIContext().get("filter")==null){
				if(node!=null){
					if (node.getUserObject() instanceof SellProjectInfo){
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));	
					}else if(node.getUserObject() instanceof OrgStructureInfo){
						Map spIdMap = FDCTreeHelper.getAllObjectIdMap(node, "SellProject");
			    		if(spIdMap.size()>0)
			    			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",FDCTreeHelper.getStringFromSet(spIdMap.keySet()),CompareType.INNER));
			    		else
			    			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",null));
					}
				}else{
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", "'null'"));
				}
			}else{
				filter.mergeFilter((FilterInfo) this.getUIContext().get("filter"), "and");
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
			
		}catch (Exception e)
		{
			handleException(e);
		}
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		sellProject=null;
		if (node.getUserObject() instanceof SellProjectInfo){
			if(node.getUserObject() != null){
				sellProject=(SellProjectInfo) node.getUserObject();
			}			
		}
		this.refresh(null);
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
	protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		CRMClientHelper.getFootRow(tblMain, new String[]{"entrys.revAmount","entrys.amount","entrys.appRevAmount"});
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"entrys.revAmount","entrys.amount","entrys.appRevAmount"});
	}
}