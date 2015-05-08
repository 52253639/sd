/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public abstract class ScheduleTreeListBaseUI extends AbstractScheduleTreeListBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(ScheduleTreeListBaseUI.class);
    
    /**
     * output class constructor
     */
    public ScheduleTreeListBaseUI() throws Exception
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
        super.tblMain_tableClicked(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    	if(node!=null){
    		this.refresh(null);
    	}
    }

    public void onLoad() throws Exception {
    	buildTree();
    	super.onLoad();
    	treeMain.expandRow(0);
    	treeMain.setSelectionRow(0);
    }
    protected void initWorkButton() {
    	super.initWorkButton();
    	this.actionQuery.setVisible(false);
    }
	protected void buildTree() throws Exception {
		OrgUnitInfo currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		try{
			//用财务组织构造树保证下级组织能够看到其他的项目
			SysContext.getSysContext().setCurrentOrgUnit(SysContext.getSysContext().getCurrentFIUnit());
			ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

			projectTreeBuilder.build(this, treeMain, actionOnLoad);
		} finally {
			SysContext.getSysContext().setCurrentOrgUnit(currentOrgUnit);
		}
		treeMain.setShowsRootHandles(true);
	}
	
	/**
	 * 得到当前选择的对象工程项目,组织ID,或Null
	 * @return 当前选择的对象工程项目,组织ID,或Null
	 */
	protected Object getSelectObj() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {
				return null;
			}
			FullOrgUnitInfo info = oui.getUnit();
			return info;
		}
		return null;
	}
	
	/**
	 * 选择的工程项目
	 * @return
	 */
	protected CurProjectInfo getSelectProject() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			return null;
		}
		return null;
	}

    /* 
     * 过滤的时候加上额外的过滤条件
     * @see com.kingdee.eas.framework.client.ListUI#getQueryExecutor(com.kingdee.bos.metadata.IMetaDataPK, com.kingdee.bos.metadata.entity.EntityViewInfo)
     */
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
    	viewInfo=(EntityViewInfo)viewInfo.clone();
    	try{
    		if(viewInfo.getFilter()!=null){
    			viewInfo.getFilter().mergeFilter(getMainFilter(), "and");
    		}else{
    			viewInfo.setFilter(getMainFilter());
    		}
    	}catch(Exception e){
    		this.handUIException(e);
    	}
    	IQueryExecutor exec = super.getQueryExecutor(queryPK, viewInfo);
    	exec.option().isIgnoreOrder=false;
		return exec;
    }
    
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}
	
    protected abstract ICoreBase getBizInterface() throws Exception ;

	protected abstract String getEditUIName();
	
	/**
	 * 过滤条件，用以添加Query之外的过滤条件,提供默认的按工程项目树过滤的功能，要求Query上面有工程项目的字段，并实现getProjectFieldName 方法返回
	 * @return
	 */
	protected FilterInfo getMainFilter(){
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    	Set prjIdSet=getProjectLeafsOfNode(node);
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo(getProjectFieldName(),prjIdSet,CompareType.INCLUDE));
		
    	return filter;
	}
	
	/**
	 * query上面的工程项目ID字段，在getMainFilter里面使用，如果不需要此字段的话请重写getMainFilter方法
	 * @return 如："project.id"
	 */
	protected abstract String getProjectFieldName();

	/**
     * 获取选择节点的所有下级明细工程项目
     * @return
     */
    protected Set getProjectLeafsOfNode(DefaultKingdeeTreeNode node){
    	Set idSet=new HashSet();
    	if(node!=null){
    		if(node.getUserObject() instanceof CurProjectInfo){
    			CurProjectInfo prj = (CurProjectInfo)node.getUserObject();
    			if(prj.isIsLeaf()){
    				idSet.add(prj.getId().toString());
    				return idSet;
    			}
    		}
    		//直接遍历树去到节点可以保证一致性
    		Enumeration childrens = node.depthFirstEnumeration();
    		while(childrens.hasMoreElements()){
    			DefaultKingdeeTreeNode childNode = (DefaultKingdeeTreeNode)childrens.nextElement();
    			if(childNode.getUserObject() instanceof CurProjectInfo){
    				CurProjectInfo prj=(CurProjectInfo)childNode.getUserObject();
    				if(prj.isIsLeaf()){
    					idSet.add(prj.getId().toString());
    				}
    			}
    		}
    	}
    	if(idSet.size()==0){
    		//如果选择的节点下没有任何明细的工程项目节点则随便加个节点保证过滤不出来任何数据
    		idSet.add(OrgConstants.DEF_CU_ID);
    	}
    	return idSet;
    }
    
    protected String getEditUIModal() {
    	return UIFactoryName.NEWTAB;
    }
}