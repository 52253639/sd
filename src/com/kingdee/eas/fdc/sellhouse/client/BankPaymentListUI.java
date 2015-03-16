/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BankPaymentFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BankPaymentListUI extends AbstractBankPaymentListUI
{
    private static final Logger logger = CoreUIObject.getLogger(BankPaymentListUI.class);
    protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    
    public BankPaymentListUI() throws Exception
    {
        super();
    }
    
    private void tblMain_afterDataFill(KDTDataRequestEvent e) {
		int sr = e.getFirstRow();
		if(e.getFirstRow() > 0){
			sr = sr - 1;
		}
		
		this.tblMain.getGroupManager().group(sr, e.getLastRow());
	}
    
//    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
//    	return super.getQueryExecutor(queryPK, viewInfo);
//    }
    
    public void onLoad() throws Exception {
		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener(){
			public void afterDataFill(KDTDataRequestEvent e) {
				tblMain_afterDataFill(e);
			}
		});
    	
		super.onLoad();
		initTree();
		initControl();
		//不用实模式的情况会导致数据显示不全
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		this.treeMain.setSelectionRow(0);	
	}

   
    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void initTree() throws Exception
	{
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad, this.getSystemType()));
//    	this.treeMain.setModel(FDCTreeHelper.getSellProjectTree(this.actionOnLoad,null));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
    
    protected void initControl()
    {
    	this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionLocate.setVisible(false);
		this.menuAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
		this.menuUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
    }
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
    		EntityViewInfo viewInfo) {
    	FilterInfo filter = new FilterInfo();
    	DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
		this.actionAddNew.setEnabled(false);
    	if(thisNode!=null) {  
	    	if(thisNode.getUserObject() instanceof SellProjectInfo){
	    		if(isSaleHouseOrg && thisNode.isLeaf()){
		    		this.actionAddNew.setEnabled(true);
	    		}
	    		SellProjectInfo spInfo = (SellProjectInfo)thisNode.getUserObject();
	    		filter.getFilterItems().add(new FilterItemInfo("project.longNumber",spInfo.getLongNumber() + "%",CompareType.LIKE));
	    	}else{
	    		Map spIdMap = FDCTreeHelper.getAllObjectIdMap(thisNode, "SellProject");
	    		if(spIdMap.size()>0)
	    			filter.getFilterItems().add(new FilterItemInfo("project.id",FDCTreeHelper.getStringFromSet(spIdMap.keySet()),CompareType.INNER));
	    		else
	    			filter.getFilterItems().add(new FilterItemInfo("project.id",null));
	    	}
    	}else{
    		filter.getFilterItems().add(new FilterItemInfo("project.id",null));
    	}
		viewInfo = (EntityViewInfo)this.mainQuery.clone();
		if(viewInfo.getFilter()==null)
			viewInfo.setFilter(filter);
		else{
			try {
				viewInfo.getFilter().mergeFilter(filter,"AND");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
    	return super.getQueryExecutor(queryPK, viewInfo);
    }
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	this.execQuery();
    }
    
    /*
     * 银行放款单审批通过需要同步生成对应的收款单和出纳汇总单
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo bankState = (BizEnumValueInfo) row.getCell("state").getValue();
		if (bankState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)|| bankState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE))
		{
			MsgBox.showInfo("银行放款单已审批!");
			return;
		}
		BankPaymentFactory.getRemoteInstance().audit(BOSUuid.read(getSelectedKeyValue()));
		this.refresh(null);
    }
    
    /*
     * 银行放款单反审批时需要校验对应的出纳汇总单是否已生成凭证或是生成出纳的收付款单
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName == null) {
			MsgBox.showInfo("银行放款单未审批!");
			return;
		}
		
		if (MsgBox.showConfirm2New(this, "确认反审批吗?") == MsgBox.YES)
		{
			BankPaymentFactory.getRemoteInstance().unAudit(BOSUuid.read(getSelectedKeyValue()));
			this.refresh(null);
		}
    }
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo bankState = (BizEnumValueInfo) row.getCell("state").getValue();
		if (bankState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)|| bankState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE))
		{
			MsgBox.showInfo("已审批的单据不能删除!");
			return;
		}
    	super.actionRemove_actionPerformed(e);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo bankState = (BizEnumValueInfo) row.getCell("state").getValue();
		if (bankState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)|| bankState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE))
		{
			MsgBox.showInfo("已审批的单据不能修改!");
			return;
		}
    	super.actionEdit_actionPerformed(e);
    }
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e)
	{
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		
		if(node.getUserObject() instanceof SellProjectInfo){
			SellProjectInfo sellProjectInfo = (SellProjectInfo) node.getUserObject();
			if(sellProjectInfo!=null){
				uiContext.put("sellProject", sellProjectInfo);
			}
		}
	}
    
    protected ICoreBase getBizInterface() throws Exception {
    	return BankPaymentFactory.getRemoteInstance();
    }
    
    protected MoneySysTypeEnum getSystemType(){
    	return MoneySysTypeEnum.SalehouseSys;
    }
    
    protected boolean isIgnoreCUFilter() {
    	return true;
    }
    
    protected String[] getLocateNames() {
    	return super.getLocateNames();
    }
    
    protected String getEditUIName()
	{
		return BankPaymentEditUI.class.getName();
	}
    
    protected String getEditUIModal() {
    	return UIFactoryName.MODEL;
    }
    
}