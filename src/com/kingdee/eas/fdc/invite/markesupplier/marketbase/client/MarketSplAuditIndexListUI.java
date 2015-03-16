/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.marketbase.client;

import java.awt.event.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexTreeInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexGroupInfo;
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
public class MarketSplAuditIndexListUI extends AbstractMarketSplAuditIndexListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSplAuditIndexListUI.class);
    
    /**
     * output class constructor
     */
    public MarketSplAuditIndexListUI() throws Exception
    {
        super();
    }
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	/*
    	 * 当是非叶子节点，才可以新增指标
    	 */
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getSupplierResources("notAddBase"));
        	SysUtil.abort();
		}
    	if(getTypeSelectedTreeNode().getUserObject() instanceof MarketSplAuditIndexTreeInfo&&((MarketSplAuditIndexTreeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
			getUIContext().put("group", ((MarketSplAuditIndexTreeInfo)getTypeSelectedTreeNode().getUserObject()));
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
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.actionCancel.setVisible(true);
    	this.actionCancelCancel.setVisible(true);
    	this.actionQuery.setVisible(false);
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
    
    public void onShow() throws Exception {
    	super.onShow();
    }
    
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
//    	super.actionCancel_actionPerformed(e);
    	checkSelected();
    	if(MsgBox.showConfirm2("确认禁用所选数据？")==2){
    		return;
    	}
        ArrayList list = this.getSelectedIdValues();
        for (int i = 0; i < list.size(); i++) {
			String id = list.get(i).toString();
			MarketSplAuditIndexInfo info = MarketSplAuditIndexFactory.getRemoteInstance().getMarketSplAuditIndexInfo(new ObjectUuidPK(id));
			info.setIsEnable(false);
			MarketSplAuditIndexFactory.getRemoteInstance().update(new ObjectUuidPK(info.getId()), info);
		}
        this.refresh(e);
    }
    
    public void actionCancelCancel_actionPerformed(ActionEvent e)
    		throws Exception {
    	checkSelected();
//    	super.actionCancelCancel_actionPerformed(e);
    	if(MsgBox.showConfirm2("确认启用所选数据？")==2){
    		return;
    	}
        ArrayList list = this.getSelectedIdValues();
        for (int i = 0; i < list.size(); i++) {
			String id = list.get(i).toString();
			MarketSplAuditIndexInfo info = MarketSplAuditIndexFactory.getRemoteInstance().getMarketSplAuditIndexInfo(new ObjectUuidPK(id));
			info.setIsEnable(true);
			MarketSplAuditIndexFactory.getRemoteInstance().update(new ObjectUuidPK(info.getId()), info);
		}
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
        super.tblMain_tableSelectChanged(e);
        checkSelected();
    	this.actionCancel.setEnabled(false);
		this.actionCancelCancel.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionEdit.setEnabled(false);
    	String id = this.getSelectedKeyValue();
    	MarketSplAuditIndexInfo info = MarketSplAuditIndexFactory.getRemoteInstance().getMarketSplAuditIndexInfo(new ObjectUuidPK(id));
    	if(info.isIsEnable()){
    		this.actionCancel.setEnabled(true);
    	}else{
    		this.actionCancelCancel.setEnabled(true);
    		this.actionRemove.setEnabled(true);
    		this.actionEdit.setEnabled(true);
    	}
    }


    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexFactory.getRemoteInstance();
    }

    /**
     * output getTreeInterface method
     */
    protected ITreeBase getTreeInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexTreeFactory.getRemoteInstance();
    }

    /**
     * output getGroupEditUIName method
     */
    protected String getGroupEditUIName()
    {
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.client.MarketSplAuditIndexTreeEditUI.class.getName();
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
        return "供应商评审指标";
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo();
		
        return objectValue;
    }

}