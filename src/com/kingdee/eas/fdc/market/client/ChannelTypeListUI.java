/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.market.ChannelTypeFactory;
import com.kingdee.eas.fdc.market.ChannelTypeInfo;
import com.kingdee.eas.fdc.market.ChannelTypeTreeInfo;
import com.kingdee.eas.fdc.sellhouse.client.AgioSelectUI;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ChannelTypeListUI extends AbstractChannelTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChannelTypeListUI.class);
    protected IUIObject owner;
    /**
     * output class constructor
     */
    public ChannelTypeListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
		super.onLoad();
		this.setUITitle("媒体名称");
		this.btnCancelCancel.setEnabled(true);
		this.btnCancel.setEnabled(true);
		this.btnCancel.setVisible(true);
		this.btnCancelCancel.setVisible(true);
		setAddCtrl();
		setaddNameCtrl();
	}
  
	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    /**
     * 启用
     */
	public void actionCancelCancel_actionPerformed(ActionEvent actionevent)throws Exception {
		setAddCtrl();
		super.actionCancelCancel_actionPerformed(actionevent);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		  if(rowIndex==-1){
			  MsgBox.showWarning("请先选择记录行!");
	        	SysUtil.abort();
	        }
		 IRow row     = tblMain.getRow(rowIndex);
		 String id =(String)row.getCell("id").getValue(); 
		 ChannelTypeInfo channelInfo = ChannelTypeFactory.getRemoteInstance().getChannelTypeInfo(new ObjectUuidPK(id));
		 channelInfo.setStatrusing(true);
		 ChannelTypeFactory.getRemoteInstance().update(new ObjectUuidPK(channelInfo.getId()), channelInfo);
		 this.actionRefresh_actionPerformed(actionevent);
	}
    /**
     * 禁用
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
    	setAddCtrl();
		super.actionCancel_actionPerformed(e);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		 IRow row     = tblMain.getRow(rowIndex);
		 String id =(String)row.getCell("id").getValue(); 
		 ChannelTypeInfo channelInfo = ChannelTypeFactory.getRemoteInstance().getChannelTypeInfo(new ObjectUuidPK(id));
		 channelInfo.setStatrusing(false);
		 ChannelTypeFactory.getRemoteInstance().update(new ObjectUuidPK(channelInfo.getId()), channelInfo);
		 this.actionRefresh_actionPerformed(e);
	}
  

	//控制按钮状态
    private void setBtnStatuse()
    {
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    	chkIncludeChild.setSelected(false);
    	chkIncludeChild.setVisible(false);
    	if(node.getUserObject() instanceof ChannelTypeTreeInfo)
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

	/**
     * 控制集团下更改媒体类别
     * 
     */  
    private void setAddCtrl() {
    	OrgUnitInfo currOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
    	String cuid=OrgConstants.DEF_CU_ID;
    	if(currOrgUnit != null && cuid.equalsIgnoreCase(currOrgUnit.getCU().getId().toString())) {
    		this.btnGroupAddNew.setEnabled(true);
    		this.btnGroupEdit.setEnabled(true);
    		this.btnGroupRemove.setEnabled(true);
    	}else {
    		this.btnGroupAddNew.setEnabled(false);
    		this.btnGroupEdit.setEnabled(false);
    		this.btnGroupRemove.setEnabled(false);
//    		MsgBox.showError("当前组织下，不能操作媒体名称！");
//    		SysUtil.abort();
    	}
    }
    /**
     * 实体销售组织下更改媒体名称
     * 
     */  
    private void setaddNameCtrl() {
    	FDCSysContext fdcSysContext = FDCSysContext.getInstance();
    	SaleOrgUnitInfo saleOrgInfo = SysContext.getSysContext().getCurrentSaleUnit();
    	if(fdcSysContext.getOrgMap().containsKey(saleOrgInfo.getId().toString())){
    		this.btnAddNew.setEnabled(true);
    		this.btnEdit.setEnabled(true);
    		this.btnRemove.setEnabled(true);
    	}else {
    		this.btnAddNew.setEnabled(false);
    		this.btnEdit.setEnabled(false);
    		this.btnRemove.setEnabled(false);
//    		MsgBox.showError("当前组织下，不能操作媒体类别！");
//    		SysUtil.abort();
    	}
    }
    

    protected void prepareUIContext(UIContext uiContext, ActionEvent e) 
    {
		super.prepareUIContext(uiContext, e);
	}
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	setAddCtrl();
    	super.actionAddNew_actionPerformed(e);
	}
  
       /**
     * output actionGroupAddNew_actionPerformed
     */
    public void actionGroupAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
    	super.actionGroupAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex==-1){
			MsgBox.showWarning("请先选择记录行!");
        	SysUtil.abort();
		}
	    IRow prow     = tblMain.getRow(rowIndex);
	    Object using = prow.getCell("statrusing").getValue().toString();
	    if(using.equals("true")){
	    	MsgBox.showWarning("改行媒体名称已启用，不能修改!");
        	SysUtil.abort();
	    }
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex==-1){
			MsgBox.showWarning("请先选择记录行!");
        	SysUtil.abort();
		}
	    IRow prow     = tblMain.getRow(rowIndex);
	    Object using = prow.getCell("statrusing").getValue().toString();
	    if(using.equals("true")){
	    	MsgBox.showWarning("改行媒体名称已启用，不能删除!");
        	SysUtil.abort();
	    }
        super.actionRemove_actionPerformed(e);
    }
    /**
     * output actionGroupEdit_actionPerformed
     */
    public void actionGroupEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
    	super.actionGroupEdit_actionPerformed(e);
    }

    /**
     * output actionGroupRemove_actionPerformed
     */
    public void actionGroupRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
    	super.actionGroupRemove_actionPerformed(e);
    }


    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.ChannelTypeFactory.getRemoteInstance();
    }

    /**
     * output getTreeInterface method
     */
    protected ITreeBase getTreeInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.ChannelTypeTreeFactory.getRemoteInstance();
    }

    /**
     * output getGroupEditUIName method
     */
    protected String getGroupEditUIName()
    {
        return com.kingdee.eas.fdc.market.client.ChannelTypeTreeEditUI.class.getName();
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
        return "渠道分类";
    }
}