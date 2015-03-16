/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.marketbase.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexTreeInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateTreeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;

/**
 * output class name
 */
public class MarketSupplierAppraiseTemplateListUI extends AbstractMarketSupplierAppraiseTemplateListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierAppraiseTemplateListUI.class);
    
    /**
     * output class constructor
     */
    public MarketSupplierAppraiseTemplateListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	/*
    	 * 当是非叶子节点，才可以新增指标
    	 */
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getSupplierResources("notAddBase"));
        	SysUtil.abort();
		}
    	if(getTypeSelectedTreeNode().getUserObject() instanceof MarketSupplierAppraiseTemplateTreeInfo&&((MarketSupplierAppraiseTemplateTreeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
			getUIContext().put("group", ((MarketSupplierAppraiseTemplateTreeInfo)getTypeSelectedTreeNode().getUserObject()));
    		super.actionAddNew_actionPerformed(e);
		}else{
			FDCMsgBox.showWarning(this,getResource("leafage"));
		}
    }
    private String getResource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
	}
    private String getSupplierResources(String key) {
	    return	EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource",key);

	}
    
    public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.treeMain
				.getLastSelectedPathComponent();
	}
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.actionCancel.setVisible(true);
    	this.actionCancelCancel.setVisible(true);
    	this.actionAudit.setVisible(true);
    	this.actionUnaudit.setVisible(true);
    	this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
    	this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
    	/*
		 * 锁定表格，仅选择一行
		 */
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		/*
		 * 锁向下方向键
		 */
		KDTableHelper.downArrowAutoAddRow(tblMain, false, null);
		/*
		 * 锁回车
		 */
		KDTableHelper.updateEnterWithTab(tblMain, false);
		
		/*
		 * 将树形菜单中移动菜单的按钮设置为隐藏且不可用
		 */
		this.actionGroupMoveTree.setVisible(false);
		this.actionGroupMoveTree.setEnabled(false);
    }
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionAudit_actionPerformed(e);
    	if(MsgBox.showConfirm2("确认审批所选数据？")==2){
    		return;
    	}
    	String id = this.getSelectedKeyValue();
    	MarketSupplierAppraiseTemplateInfo info = MarketSupplierAppraiseTemplateFactory.getRemoteInstance().getMarketSupplierAppraiseTemplateInfo(new ObjectUuidPK(id));
    	info.setState(FDCBillStateEnum.AUDITTED);
    	info.setAuditDate(SysUtil.getAppServerTime(null));
    	info.setAuditPerson(getUserInfo());
    	MarketSupplierAppraiseTemplateFactory.getRemoteInstance().update(new ObjectUuidPK(info.getId()), info);
    	this.refresh(e);
    }
    
    public void actionUnaudit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionUnaudit_actionPerformed(e);
    	if(MsgBox.showConfirm2("确认反审批所选数据？")==2){
    		return;
    	}
    	String id = this.getSelectedKeyValue();
    	MarketSupplierAppraiseTemplateInfo info = MarketSupplierAppraiseTemplateFactory.getRemoteInstance().getMarketSupplierAppraiseTemplateInfo(new ObjectUuidPK(id));
    	info.setState(FDCBillStateEnum.SUBMITTED);
    	info.setAuditDate(null);
    	info.setAuditPerson(null);
    	MarketSupplierAppraiseTemplateFactory.getRemoteInstance().update(new ObjectUuidPK(info.getId()), info);
    	this.refresh(e);
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    }
    
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
//    	super.actionCancel_actionPerformed(e);
    	checkSelected();
    	if(MsgBox.showConfirm2("确认禁用所选数据？")==2){
    		return;
    	}
    	String id = this.getSelectedKeyValue();
    	MarketSupplierAppraiseTemplateInfo info = MarketSupplierAppraiseTemplateFactory.getRemoteInstance().getMarketSupplierAppraiseTemplateInfo(new ObjectUuidPK(id));
    	info.setIsEnable(false);
    	MarketSupplierAppraiseTemplateFactory.getRemoteInstance().update(new ObjectUuidPK(info.getId()), info);
    	this.refresh(e);
    }
    
    public void actionCancelCancel_actionPerformed(ActionEvent e)
    		throws Exception {
//    	super.actionCancelCancel_actionPerformed(e);
    	if(MsgBox.showConfirm2("确认启用所选数据？")==2){
    		return;
    	}
    	String id = this.getSelectedKeyValue();
    	MarketSupplierAppraiseTemplateInfo info = MarketSupplierAppraiseTemplateFactory.getRemoteInstance().getMarketSupplierAppraiseTemplateInfo(new ObjectUuidPK(id));
    	info.setIsEnable(true);
    	MarketSupplierAppraiseTemplateFactory.getRemoteInstance().update(new ObjectUuidPK(info.getId()), info);
    	this.refresh(e);
    }
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
//        super.tblMain_tableSelectChanged(e);
    	checkSelected();
    	this.actionEdit.setEnabled(false);
    	this.actionRemove.setEnabled(false);
    	this.actionCancel.setEnabled(false);
    	this.actionCancelCancel.setEnabled(false);
    	this.actionAudit.setEnabled(false);
    	this.actionUnaudit.setEnabled(false);
    	String id = this.getSelectedKeyValue();
    	MarketSupplierAppraiseTemplateInfo info = MarketSupplierAppraiseTemplateFactory.getRemoteInstance().getMarketSupplierAppraiseTemplateInfo(new ObjectUuidPK(id));
    	if(info.isIsEnable()){//启用
    		this.actionCancel.setEnabled(true);
    	}else{
    		if(info.getState().equals(FDCBillStateEnum.SAVED)){
    			this.actionEdit.setEnabled(true);
    	    	this.actionRemove.setEnabled(true);
    		}else if(info.getState().equals(FDCBillStateEnum.SUBMITTED)){
    			this.actionEdit.setEnabled(true);
    			this.actionAudit.setEnabled(true);
    	    	this.actionRemove.setEnabled(true);
    		}else if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
    			this.actionUnaudit.setEnabled(true);
    			this.actionCancelCancel.setEnabled(true);
    		}
    	}
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateFactory.getRemoteInstance();
    }

    /**
     * output getTreeInterface method
     */
    protected ITreeBase getTreeInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateTreeFactory.getRemoteInstance();
    }

    /**
     * output getGroupEditUIName method
     */
    protected String getGroupEditUIName()
    {
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.client.MarketSupplierAppraiseTemplateTreeEditUI.class.getName();
    }

    /**
     * output getQueryFieldName method
     */
    protected String getQueryFieldName()
    {
        return "treeid.id";
    }

    /**
     * output getKeyFieldName method
     */
    protected String getKeyFieldName()
    {
        return "id";
    }

    /**
     * output getRootName method
     */
    protected String getRootName()
    {
        return "供应商评审模板";
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo();
		
        return objectValue;
    }

}