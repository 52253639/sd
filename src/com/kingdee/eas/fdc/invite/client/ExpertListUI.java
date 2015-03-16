/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.ExpertFactory;
import com.kingdee.eas.fdc.invite.ExpertInfo;
import com.kingdee.eas.fdc.invite.ExpertQualifyEntryFactory;
import com.kingdee.eas.fdc.invite.ExpertTypeFactory;
import com.kingdee.eas.fdc.invite.ExpertTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;

/**
 * output class name
 */
public class ExpertListUI extends AbstractExpertListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ExpertListUI.class);
    private OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentCostUnit();
    
    /**
     * output class constructor
     */
    public ExpertListUI() throws Exception
    {
        super();
    }
  

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	if(getTypeSelectedTreeNode()!=null&&getTypeSelectedTreeNode().getUserObject() instanceof ExpertTypeInfo
				&& ((ExpertTypeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
			this.getUIContext().put("type", getTypeSelectedTreeNode().getUserObject());
		}else{
			FDCMsgBox.showInfo(this,"请先选择明细类别，再进行操作");
			abort();
		}
        super.actionAddNew_actionPerformed(e);
    }

     /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	this.checkSelected();
    	String id = this.getSelectedKeyValue();
    	FilterInfo filter = new FilterInfo();
    	if(id!=null)
    		filter.getFilterItems().add(new FilterItemInfo("id",id));
    	filter.getFilterItems().add(new FilterItemInfo("orgUnit",currentOrg.castToFullOrgUnitInfo().getId().toString()));
    	if(ExpertFactory.getRemoteInstance().exists(filter))
    		super.actionEdit_actionPerformed(e);
    	else
    		FDCMsgBox.showWarning(this,"非本组织维护的专家，不能修改");
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	this.checkSelected();
    	String id = this.getSelectedKeyValue();
    	
    	FilterInfo experQualifyilter = new FilterInfo();
    	if(id!=null){
    		experQualifyilter.getFilterItems().add(new FilterItemInfo("expert.id",id));
    		if(ExpertQualifyEntryFactory.getRemoteInstance().exists(experQualifyilter)){
    			FDCMsgBox.showWarning("专家已是评标专家，不能删除");
    			return;
    		}
    	}    	
    	
    	FilterInfo filter = new FilterInfo();
    	if(id!=null)
    		filter.getFilterItems().add(new FilterItemInfo("id",id));
    	filter.getFilterItems().add(new FilterItemInfo("orgUnit",currentOrg.castToFullOrgUnitInfo().getId().toString()));
    	if(ExpertFactory.getRemoteInstance().exists(filter))
    		super.actionRemove_actionPerformed(e);
    	else
    		FDCMsgBox.showWarning(this,"非本组织维护的专家，不能刪除");
    	refresh(e);
       
    }
    
    /**
     * output actionGroupAddNew_actionPerformed
     */
    public void actionGroupAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	if(getTypeSelectedTreeNode().getUserObject() instanceof ExpertTypeInfo&&((ExpertTypeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("expertType.id",((ExpertTypeInfo)getTypeSelectedTreeNode().getUserObject()).getId().toString()));
    		
    		if(this.getBizInterface().exists(filter)){
    			FDCMsgBox.showWarning(this,"明细类别已经有数据，不能进行此操作");
    			abort();
    		}
    	}
        super.actionGroupAddNew_actionPerformed(e);
    }

    /**
     * output actionGroupRemove_actionPerformed
     */
    public void actionGroupRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	if(getTypeSelectedTreeNode().getUserObject() instanceof ExpertTypeInfo){
    		if(((ExpertTypeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
	    		FilterInfo filter = new FilterInfo();
	    		filter.getFilterItems().add(new FilterItemInfo("expertType.id",((ExpertTypeInfo)getTypeSelectedTreeNode().getUserObject()).getId().toString()));
	    		if(this.getBizInterface().exists(filter)){
	    			FDCMsgBox.showWarning(this,"已经关联数据，不能执行此操作");
	    			abort();
	    		}
    		}else{
    			FDCMsgBox.showWarning(this,"非明细节点，不能执行此操作");
    			abort();
    		}
    	}
        super.actionGroupRemove_actionPerformed(e);
    }

    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		
		boolean canAdd = true ;
//		this.checkSelected();
    	String id = this.getSelectedKeyValue();
    	
    	if(id != null)
    	{
    		FilterInfo filter = new FilterInfo();

    		filter.getFilterItems().add(new FilterItemInfo("id",id));
    		filter.getFilterItems().add(new FilterItemInfo("orgUnit",currentOrg.castToFullOrgUnitInfo().getId().toString()));
    		try {
    			if(ExpertFactory.getRemoteInstance().exists(filter))
    				canAdd = true ;
    			else
    				canAdd = false;
    		} catch (EASBizException e1) {
    			e1.printStackTrace();
    		} catch (BOSException e1) {
    			e1.printStackTrace();
    		}

    		uiContext.put("CAN_ADD", Boolean.valueOf(canAdd));
    	}
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		// TODO 自动生成方法存根
		ExpertInfo expertInfo = null;
		IObjectPK detail = new ObjectUuidPK(getSelectedKeyValue());
		if (detail == null) {
			return null;
		}
		try {
			expertInfo = (ExpertInfo) getBizInterface().getValue(detail);
		} catch (Exception e) {
			handUIException(e);
		}
		if (expertInfo.getExpertType() == null
				|| expertInfo.getExpertType().getId() == null) {
			this.treeMain.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
			abort();
		}
		return new ObjectUuidPK(expertInfo.getExpertType().getId());
	}
	public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.treeMain
				.getLastSelectedPathComponent();
	}
	protected String getEditUIModal(){
		return UIFactoryName.MODEL;
	}

	protected String getGroupEditUIName() {
		// TODO 自动生成方法存根
		return ExpertTypeEditUI.class.getName();
	}
	protected String getEditUIName() {
		// TODO 自动生成方法存根
		return ExpertEditUI.class.getName();
	}

	protected String getQueryFieldName() {
		// TODO 自动生成方法存根
		return "expertType.id";
	}
	protected String getRootName()
    {
        return "专家类型";
    }

	protected ITreeBase getTreeInterface() throws Exception {
		return ExpertTypeFactory.getRemoteInstance();
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return ExpertFactory.getRemoteInstance();
	}
	
	public void onLoad() throws Exception{
		super.onLoad();
		boolean canAdd = currentOrg.isIsCompanyOrgUnit();
//		if(currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
//			canAdd = true;
//		}
		this.actionAddNew.setEnabled(canAdd);
		this.actionRemove.setEnabled(canAdd);
		this.actionEdit.setEnabled(canAdd);
		this.actionCancel.setEnabled(false);
		this.actionCancelCancel.setEnabled(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		
		this.actionGroupAddNew.setEnabled(currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID));
		this.actionGroupEdit.setEnabled(currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID));
		this.actionGroupMoveTree.setEnabled(false);
		this.actionGroupMoveTree.setVisible(false);
		this.actionGroupRemove.setEnabled(currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID));
		this.actionMoveTree.setVisible(false);
		this.actionMoveTree.setEnabled(false);
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("user.number"));
		sic.add(new SelectorItemInfo("expertType.name"));
		sic.add(new SelectorItemInfo("tel"));
		sic.add(new SelectorItemInfo("isEnable"));
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("orgUnit.name"));
		sic.add(new SelectorItemInfo("defOrgUnit.name"));
		return sic;
	}

}