/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class QuestionPaperAnswerListUI extends AbstractQuestionPaperAnswerListUI
{
    private static final Logger logger = CoreUIObject.getLogger(QuestionPaperAnswerListUI.class);
    protected SellProjectInfo sellProject = null;
    protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());

    
	public void onLoad() throws Exception {
		super.onLoad();
		//src start
		this.setUITitle("问卷管理");
		//src end
		this.actionCreateTo.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.tblMain.getColumn("id").getStyleAttributes().setHided(true);
		
		this.actionAnswerOnline.setEnabled(true);
		
		if(!(this.getUIContext().get("isHideTree")!=null&&((Boolean)this.getUIContext().get("isHideTree")).booleanValue())){
//			this.kDTree.setModel(FDCTreeHelper.getSaleOrgTree(this.actionOnLoad));
			this.kDTree.setModel(CRMTreeHelper.getSellProjectTree(actionOnLoad,false));
			this.kDTree.expandAllNodes(true, (TreeNode) this.kDTree.getModel().getRoot());
			this.kDTree.setSelectionNode((DefaultKingdeeTreeNode)this.kDTree.getModel().getRoot());
		}else{
			this.kDTree.setVisible(false);
			this.kDTreeView1.setVisible(false);
		}
	}

	/**
	 * output class constructor
	 */
	public QuestionPaperAnswerListUI() throws Exception {
		super();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory.getRemoteInstance();
	}

	/**
	 * output getKeyFieldName method
	 */
	protected String getKeyFieldName() {
		return "id";
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo objectValue = new com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo();

		return objectValue;
	}

    protected OrgType getMainBizOrgType()    {
        return OrgType.Sale;
    }
 
    protected String getEditUIName() {
    	return QuestionPaperAnswerEditUI.class.getName();
    }
    
    public void actionAnswerOnline_actionPerformed(ActionEvent e)
    		throws Exception {
		UIContext uiContext = new UIContext(this); 		
		uiContext.put("ParentListUI", this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(QuestionAnswerGuideUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
    }
    protected void prepareUIContext(UIContext uiContext, ActionEvent e)
	{
    	//src start
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("sellProject", sellProject);
		//src end
	}
    protected void kDTree_valueChanged(TreeSelectionEvent e) throws Exception {
    	//src start
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) kDTree.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		sellProject=null;
		if (node.getUserObject() instanceof SellProjectInfo){
			//项目
			if(node.getUserObject() != null){
				sellProject=(SellProjectInfo) node.getUserObject();
			}			
		}
		
		if (node.getUserObject() instanceof OrgStructureInfo){
			this.actionAddNew.setEnabled(false);
		}else if(node.getUserObject() instanceof SellProjectInfo){
			if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}else{
			if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}
		//src end
    	this.execQuery();
    }

    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
    		EntityViewInfo viewInfo) {
    	//src start
    	if(!(this.getUIContext().get("isHideTree")!=null&&((Boolean)this.getUIContext().get("isHideTree")).booleanValue())){
    		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) kDTree.getLastSelectedPathComponent();
    		String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet());
    		if(node==null||allSpIdStr.trim().length()==0){
    			allSpIdStr = "'null'"; 
    		}
    		try	{
    			FilterInfo filter = new FilterInfo();
    			if(node!=null){
    				if (node.getUserObject() instanceof SellProjectInfo){
    					//项目
    					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", SHEManageHelper.getAllSellProjectCollection(null,sellProject),CompareType.INCLUDE));	
    				}else if (node.getUserObject() instanceof OrgStructureInfo){
    					//组织
    					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", allSpIdStr,CompareType.INNER));
    				}
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
    	}
		//src end
		return super.getQueryExecutor(queryPK, viewInfo);
    }
    
}