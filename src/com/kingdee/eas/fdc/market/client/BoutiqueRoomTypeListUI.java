/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.eas.fdc.market.BoutiqueRoomTypeTreeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;

/**
 * 精品户型
 * @author liangfx
 *
 */
public class BoutiqueRoomTypeListUI extends AbstractBoutiqueRoomTypeListUI 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3664131245179520156L;
	private static final Logger logger = CoreUIObject.getLogger(BoutiqueRoomTypeListUI.class);
    
	
	//新增时产生页签窗口方法
    protected String getEditUIModal()
    {
    	String openModel = UIConfigUtility.getOpenModel();
        if (openModel != null)
        {
            return UIFactoryName.NEWWIN;
        }
        else
        {
            return UIFactoryName.NEWTAB;
        }
    }
    
    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeMain_valueChanged(e);
        setBtnStatuse();
//		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
//		.getLastSelectedPathComponent();
//		if (node == null) {
//			return;
//		}
//			UIContext uicontext=new UIContext();
//			BoutiqueRoomTypeTreeInfo roomtype=(BoutiqueRoomTypeTreeInfo)node.getUserObject();
//			uicontext.put("roomtype", roomtype);
			
    }
    
    public void onShow() throws Exception 
    {
    	setBtnStatuse();
    	
    	super.onShow();
	}
    //控制按钮状态
    private void setBtnStatuse()
    {
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    	chkIncludeChild.setSelected(false);
    	chkIncludeChild.setVisible(false);
    	if(node.getUserObject() instanceof BoutiqueRoomTypeTreeInfo)
    	{
    		this.actionAddNew.setEnabled(true);
    		this.actionEdit.setEnabled(true);
    		this.actionView.setEnabled(true);
    	}else
    	{
    		this.actionAddNew.setEnabled(false);
    		this.actionEdit.setEnabled(false);
    		this.actionView.setEnabled(false);
    	}
    }
    
    
    public void onLoad() throws Exception
	{
		// TODO Auto-generated method stub
//    	setAddCtrl();
		super.onLoad();
	}

	/**
     * 控制集团下更改基础资料
     * 
     */  
    private void setAddCtrl() {
    	OrgUnitInfo currOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
    	String cuid=OrgConstants.DEF_CU_ID;
    	if(currOrgUnit != null && cuid.equalsIgnoreCase(currOrgUnit.getCU().getId().toString())) {
    		this.btnAddNew.setEnabled(true);
    		this.btnEdit.setEnabled(true);
    		this.btnRemove.setEnabled(true);
    	}else {
//    		this.btnAddNew.setEnabled(false);
//    		this.btnEdit.setEnabled(false);
//    		this.btnRemove.setEnabled(false);
    		MsgBox.showError("当前组织下，不能操作该类基础资料！");
    		SysUtil.abort();
    	}
    }
    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
		super.actionAddNew_actionPerformed(e);
    }
    

    protected void prepareUIContext(UIContext uiContext, ActionEvent e) 
    {
        DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		BoutiqueRoomTypeTreeInfo roomtype=(BoutiqueRoomTypeTreeInfo)node.getUserObject();
		uiContext.put("roomtype", roomtype);
		super.prepareUIContext(uiContext, e);
	}

	/**
     * output class constructor
     */
    public BoutiqueRoomTypeListUI() throws Exception
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

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	if(e.getClickCount()==2)
    		return;
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output chkIncludeChild_itemStateChanged method
     */
    protected void chkIncludeChild_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.chkIncludeChild_itemStateChanged(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }
    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

    /**
     * output actionGroupAddNew_actionPerformed
     */
    public void actionGroupAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupAddNew_actionPerformed(e);
    }

    /**
     * output actionGroupView_actionPerformed
     */
    public void actionGroupView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupView_actionPerformed(e);
    }

    /**
     * output actionGroupEdit_actionPerformed
     */
    public void actionGroupEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupEdit_actionPerformed(e);
    }

    /**
     * output actionGroupRemove_actionPerformed
     */
    public void actionGroupRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupRemove_actionPerformed(e);
    }

    /**
     * output actionGroupMoveTree_actionPerformed
     */
    public void actionGroupMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupMoveTree_actionPerformed(e);
    }

    /**
     * output actionMoveTree_actionPerformed
     */
    public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMoveTree_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.BoutiqueRoomTypeFactory.getRemoteInstance();
    }

    /**
     * output getTreeInterface method
     */
    protected ITreeBase getTreeInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.BoutiqueRoomTypeTreeFactory.getRemoteInstance();
    }

    /**
     * output getGroupEditUIName method
     */
    protected String getGroupEditUIName()
    {
        return com.kingdee.eas.fdc.market.client.BoutiqueRoomTypeTreeEditUI.class.getName();
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
        return "精品户型";
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.BoutiqueRoomTypeInfo objectValue = new com.kingdee.eas.fdc.market.BoutiqueRoomTypeInfo();
		
        return objectValue;
    }
    protected void prepareGroupUIContext(UIContext uiContext, ActionEvent e)
    {
        KDTreeNode treeNode = (KDTreeNode) treeMain.getLastSelectedPathComponent();
        ArrayList childNodeName =new ArrayList();
        if (treeNode != null)
        {
        	for (int j = 0; j < treeNode.getChildCount(); j++)
			{
				if ((treeNode.getChildAt(j) instanceof KDTreeNode))
				{
					TreeBaseInfo tempTree = (TreeBaseInfo) ((KDTreeNode)treeNode.getChildAt(j)).getUserObject();
					childNodeName.add(tempTree.getName());
				}
			}
            uiContext.put("childNodeName", childNodeName);
        }
        else
        {
            uiContext.put("childNodeName", null);
        }
        ArrayList peerNodeName =new ArrayList();
        if(!treeNode.isRoot() && treeNode.getParent()!=null){
        	KDTreeNode parentNode= (KDTreeNode)treeNode.getParent();
        	if(parentNode!=null){
        		int index =parentNode.getIndex(treeNode);
        		for (int j = 0; j < parentNode.getChildCount(); j++)
    			{parentNode.getText();
    				if (index!=j &&(parentNode.getChildAt(j) instanceof KDTreeNode))
    				{
    					KDTreeNode tempNode = (KDTreeNode)parentNode.getChildAt(j);
    					peerNodeName.add(tempNode.getText().trim());
    				}
    			}
                uiContext.put("peerNodeName", peerNodeName);
        	}
        }
        else{
        	uiContext.put("peerNodeName", null);
        }
        super.prepareGroupUIContext(uiContext, e);
    }

}