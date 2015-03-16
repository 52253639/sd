/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.PlanisphereFactory;
import com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class PlanisphereListUI extends AbstractPlanisphereListUI
{
    private static final Logger logger = CoreUIObject.getLogger(PlanisphereListUI.class);
    
    private String allOrgUnitIds = "null";			//所包含所有的组织id
    private String allProjectIds = "null";			//所包含所有的销售项目id    
    
    
    /**
     * output class constructor
     */
    public PlanisphereListUI() throws Exception
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

    protected String getEditUIModal() {
    	return UIFactoryName.NEWTAB;
    }
    
    protected ICoreBase getBizInterface() throws Exception {
    	return PlanisphereFactory.getRemoteInstance();
    }
    
    protected String getEditUIName() {
    	return PlanisphereEditUI.class.getName() ;
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	//构建单元树
    	this.kDUnitTree.setModel(SHEHelper.getUnitTree(this.actionOnLoad));
    	//this.kDUnitTree.setModel(SHEHelper.getPlanisphere(this.actionOnLoad));
    	this.kDUnitTree.expandAllNodes(true,(TreeNode)this.kDUnitTree.getModel().getRoot());
   
    	//统计出该树中的所有组织 以及 所有项目  -- 代表当前营销人员能看到的，传递给editUI
    	getAllOrgStructAndProjectIds((TreeNode)this.kDUnitTree.getModel().getRoot());   
    	this.getUIContext().put("AllOrgUnitIds",allOrgUnitIds);
    	this.getUIContext().put("AllProjectIds",allProjectIds);
    	
    	
    	//按扭和菜单显示控制
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if(!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}
		
		this.menuBiz.setVisible(false);
		this.menuWorkFlow.setVisible(false);
		this.actionAttachment.setVisible(false);
		//this.actionPrint.setVisible(false);
		//this.actionPrintPreview.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.btnAuditResult.setVisible(false);
		this.btnWorkFlowG.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);				
		
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.getColumn("id").getStyleAttributes().setHided(true);
    	
    }
    
    
    protected void kDUnitTree_valueChanged(TreeSelectionEvent e) throws Exception {
    	super.kDUnitTree_valueChanged(e);
    	
    	TreePath treePath = e.getNewLeadSelectionPath();
    	if(treePath==null) return;
    	
    	DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)treePath.getLastPathComponent();
    	getParentInfoMap(thisNode);
    	
    	this.execQuery();
    }
    
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
    	TreeNode treeNode = (TreeNode)this.kDUnitTree.getLastSelectedPathComponent();
    	//树型结构 销售组织 - 销售项目  - 分区  - 楼栋 - 单元
    	FilterInfo filter = new FilterInfo();
    	if(treeNode!=null) {
    		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)treeNode;
    		if(thisNode.getUserObject() instanceof OrgStructureInfo) {
    			OrgStructureInfo orgStrInfo = (OrgStructureInfo)thisNode.getUserObject();
    			filter.getFilterItems().add(new FilterItemInfo("ptype",PlanisphereTypeEnum.PICSELLPROJECT_VALUE));
    			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",orgStrInfo.getUnit()==null?"null":orgStrInfo.getUnit().getId().toString()));
    			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",null,CompareType.IS));
    		}else if(thisNode.getUserObject() instanceof SellProjectInfo) {
    			SellProjectInfo sellProInfo = (SellProjectInfo)thisNode.getUserObject();    			
    			filter.getFilterItems().add(new FilterItemInfo("ptype",PlanisphereTypeEnum.PICBUILDDIST_VALUE));
    			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProInfo.getId().toString()));
    			filter.getFilterItems().add(new FilterItemInfo("building.id",null,CompareType.IS));	
    		}else if(thisNode.getUserObject() instanceof SubareaInfo) {
    			filter.getFilterItems().add(new FilterItemInfo("id","null"));			
    		}else if(thisNode.getUserObject() instanceof BuildingInfo) {
    			BuildingInfo buidInfo = (BuildingInfo)thisNode.getUserObject();
    			filter.getFilterItems().add(new FilterItemInfo("ptype",PlanisphereTypeEnum.PICBUILDPLANE_VALUE));
    			filter.getFilterItems().add(new FilterItemInfo("building.id",buidInfo.getId().toString()));
    			if(buidInfo.getUnitCount()>0)
    				filter.getFilterItems().add(new FilterItemInfo("isShowUnit",new Boolean(false)));    			
    		}else if(thisNode.getUserObject() instanceof BuildingUnitInfo) {
    			BuildingUnitInfo buildUnit = (BuildingUnitInfo)thisNode.getUserObject();
    			DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode)thisNode.getParent();
    			BuildingInfo buidInfo = (BuildingInfo)parentNode.getUserObject();
    			filter.getFilterItems().add(new FilterItemInfo("ptype",PlanisphereTypeEnum.PICBUILDPLANE_VALUE));
    			filter.getFilterItems().add(new FilterItemInfo("building.id",buidInfo.getId().toString()));
    			filter.getFilterItems().add(new FilterItemInfo("isShowUnit",new Boolean(true)));
    			filter.getFilterItems().add(new FilterItemInfo("unit",new Integer(buildUnit.getSeq())));
    		}else{
    			filter.getFilterItems().add(new FilterItemInfo("id","null"));
    		}
    	}else{
    		filter.getFilterItems().add(new FilterItemInfo("id","null"));
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
    
    
    
    //得到所属组织的info,销售项目info,楼栋info的映射
    private Map getParentInfoMap(DefaultKingdeeTreeNode thisNode) {
    	Map idMap = new HashMap();
    	if(thisNode==null) return idMap;    	
   	
    	this.getUIContext().put("OrgUnitInfo",null);
    	this.getUIContext().put("SellProInfo",null);
    	this.getUIContext().put("BuildInfo",null);
    	this.getUIContext().put("UnitInfo",null);
    	while(thisNode!=null) {
    		if(thisNode.getUserObject() instanceof OrgStructureInfo){
    			OrgStructureInfo orgStructInfo = (OrgStructureInfo)thisNode.getUserObject();
    			if(orgStructInfo.getUnit()!=null) {
    			  idMap.put("OrgUnitInfo",orgStructInfo.getUnit());
    			  this.getUIContext().put("OrgUnitInfo",orgStructInfo.getUnit());
    			}
    		}else if(thisNode.getUserObject() instanceof SellProjectInfo){
    			SellProjectInfo sellProInfo = (SellProjectInfo)thisNode.getUserObject();
    			idMap.put("SellProInfo",sellProInfo);
    			this.getUIContext().put("SellProInfo",sellProInfo);
    		}else if(thisNode.getUserObject() instanceof BuildingInfo) {
    			BuildingInfo buildInfo = (BuildingInfo)thisNode.getUserObject();
    			idMap.put("BuildInfo",buildInfo);
    			this.getUIContext().put("BuildInfo",buildInfo);
    		}else if(thisNode.getUserObject() instanceof Integer) {
    			Integer unitInfo = (Integer)thisNode.getUserObject();
    			idMap.put("UnitInfo",unitInfo);
    			this.getUIContext().put("UnitInfo",unitInfo);
    		}
    		thisNode = (DefaultKingdeeTreeNode)thisNode.getParent();
    	}
    	return idMap;
    }
    
    
    
	/**
	 * 查询所有的组织及销售项目id
	 * @param treeNode
	 */
	private void getAllOrgStructAndProjectIds(TreeNode treeNode) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)treeNode;
		if(thisNode.getUserObject() instanceof SellProjectInfo){
			 SellProjectInfo project = (SellProjectInfo)thisNode.getUserObject();
			 allProjectIds += "," + project.getId().toString();
		}else if(thisNode.getUserObject() instanceof OrgStructureInfo){
			OrgStructureInfo orgStruct = (OrgStructureInfo)thisNode.getUserObject();
			if(orgStruct.getUnit()!=null)
				allOrgUnitIds += "," + orgStruct.getUnit().getId().toString();
		}
		
		if(!treeNode.isLeaf()){
			int childCount = treeNode.getChildCount();
			while(childCount>0) {				
				getAllOrgStructAndProjectIds(treeNode.getChildAt(childCount-1));		
				 childCount --;
			}	
		}	
	}
    
    
        
    
    
    
    
    
    
    
    
    
    
    
    
    
}