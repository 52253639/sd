/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.client.ContractBillListUI;
import com.kingdee.eas.fdc.contract.client.ContractBillReviseListUI;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ProgrammingContractF7ForWholeAgeStageUI extends AbstractProgrammingContractF7ForWholeAgeStageUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgrammingContractF7ForWholeAgeStageUI.class);
    protected FullOrgUnitInfo orgUnit = null;
    /**
     * output class constructor
     */
    public ProgrammingContractF7ForWholeAgeStageUI() throws Exception
    {
        super();
        if(orgUnit==null){
        	orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		}
    }
    
    /**
	 * private方法修改为public方法，子类可以修改工程项目树的构建方式
	 * 原因：进度管理中工程项目的构建取当前组织上级财务组织下的所有工程项目，默认取的当前组织下的工程项目
	 * @throws Exception
	 */
	public void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, treeProject, actionOnLoad);
		this.treeProject.expandAllNodes(true, (TreeNode) this.treeProject.getModel().getRoot());
		
	}
	
	public void onLoad() throws Exception {
		mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.programming.app", "ProgrammingContractF7Query");
		buildProjectTree();
		super.onLoad();
	}
	
	
	protected void treeProject_valueChanged(TreeSelectionEvent e)
			throws Exception {
		super.treeProject_valueChanged(e);
		treeSelectChange();
		setTableDepth();
	}
	
	protected void treeSelectChange() throws Exception {

		DefaultKingdeeTreeNode projectNode  = getProjSelectedTreeNode();
		
		Object project  = null;
		if(projectNode!=null){
			project = projectNode.getUserObject();
		}
		mainQuery.setFilter(getTreeSelectFilter(project));

		execQuery();

	}
	
	/**
	 * 选择工程项目节点和合同类型节点后的选择事件
	 * @return
	 * @throws Exception
	 */
	protected FilterInfo getTreeSelectFilter(Object projectNode) throws Exception {
		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();
		/*
		 * 工程项目树
		 */
		if (projectNode != null 	&& projectNode instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			BOSUuid id = null;
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
//			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
//				
//				if (projTreeNodeInfo instanceof OrgStructureInfo) {
//					id = ((OrgStructureInfo)projTreeNodeInfo).getUnit().getId();	
//				}else{
//					id = ((FullOrgUnitInfo)projTreeNodeInfo).getId();
//				}				
//				
//				String orgUnitLongNumber = null;
//				if(orgUnit!=null && id.toString().equals(orgUnit.getId().toString())){					
//					orgUnitLongNumber = orgUnit.getLongNumber();
//				}else{
//					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance()
//					.getFullOrgUnitInfo(new ObjectUuidPK(id));
//					orgUnitLongNumber = orgUnitInfo.getLongNumber();
//				}
//				
//				FilterInfo f = new FilterInfo();
//				f.getFilterItems().add(new FilterItemInfo("programming.orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));
//				f.getFilterItems().add(new FilterItemInfo("programming.orgUnit.isCostOrgUnit", Boolean.TRUE));
////				f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs,CompareType.INCLUDE));
//				
////				f.setMaskString("#0 and #1 and #2");
//				f.setMaskString("#0 and #1");
//				
//				if(filter!=null){
//					filter.mergeFilter(f,"and");
//				}
//			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				ICurProject curFacad = CurProjectFactory.getRemoteInstance();
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(new FilterItemInfo("parent.id",id));
				if(curFacad.exists(filter1)){//有下级就不显示
					filterItems.add(new FilterItemInfo("id","nullnull"));
				}else{
					Set idSet = FDCClientUtils.genProjectIdSet(id);
					filterItems.add(new FilterItemInfo("programming.project.id", idSet,CompareType.INCLUDE));
					filterItems.add(new FilterItemInfo("programming.isLatest", Boolean.TRUE));
					Map map = this.getUIContext();
					if(map.get("view") != null){
						EntityViewInfo entityView = (EntityViewInfo)map.get("view");
						if(entityView.getFilter().getFilterItems().get(3)!=null){
							filterItems.add(entityView.getFilter().getFilterItems().get(3));
						}
					}
				}
			} else {
				filterItems.add(new FilterItemInfo("id","nullnull"));
			}

		}

//		FilterInfo typefilter =  new FilterInfo();
//		if(filter!=null && typefilter!=null){
//			filter.mergeFilter(typefilter,"and");
//		}
		return filter;
	}

	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeProject
				.getLastSelectedPathComponent();
	}
	
	/**
	 * 
	 * 描述：当左边的树选择变化时的缺省条件（提供默认实现，合同状态为审核，子类可以覆盖，如果没有条件，也要返回一个new FilterInfo()，不能直接返回null）
	 * @return
	 * @author:liupd
	 * 创建时间：2006-9-5 <p>
	 */
	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("programming.project.isEnabled", Boolean.TRUE));
		return filter;
	}
}