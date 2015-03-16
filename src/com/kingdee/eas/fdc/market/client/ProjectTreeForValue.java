/*
 * @(#)ProjectTreeBuilder.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitFactory;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.OperationPhasesCollection;
import com.kingdee.eas.fdc.basedata.OperationPhasesFactory;
import com.kingdee.eas.fdc.basedata.OperationPhasesInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseCollection;
import com.kingdee.eas.fdc.basedata.ProjectBaseFactory;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.client.EASResource;

/**
 * 
 * 描述:根据当前组织构造工程项目树，实际上是： 同时具备财务组织和成本中心属性的组织树 ＋ 工程项目树
 * 
 * @author eric_wang date:20110805
 * @version EAS7.0.1
 */
public class ProjectTreeForValue{

	// parent UI
	private CoreUIObject ui;
	// 纯工程项目树
	private KDTree projTree = new KDTree();

	// 不进行组织隔离？
	private boolean noOrgIsolation;


	// 获取有权限的组织
	protected Set authorizedOrgs = null;


	public void updateTreeNodeColor(KDTree projectTree){
		DefaultKingdeeTreeNode root=(DefaultKingdeeTreeNode)projectTree.getModel().getRoot();
		visitAllNodes(root);
	}
	public void visitAllNodes(DefaultKingdeeTreeNode node) {	   
	       if (node.getChildCount() >= 0) {
	           for (Enumeration e=node.children(); e.hasMoreElements(); ) {
	        	   DefaultKingdeeTreeNode n = (DefaultKingdeeTreeNode)e.nextElement();
	        	   if(n.getUserObject() instanceof CurProjectInfo){
	        		   CurProjectInfo info=(CurProjectInfo)n.getUserObject();
	        		   //Boolean.parseBoolean(info.get("hasAimCost").toString());
	        		   if(info.get("hasAimCost")!=null){//NP
		        		   boolean b =Boolean.valueOf(info.get("hasAimCost").toString()).booleanValue();
		        		   if(!b && n.isLeaf()){
		        			   n.setTextColor(Color.RED);
		        		   }
	        		   }
	        	   }
	               visitAllNodes(n);
	           }
	       }
	   }

	public void build(CoreUI ui, KDTree projectTree, ItemAction actionOnLoad)
			throws Exception {
		final KDTree prjTree = projectTree;
		prjTree.addTreeWillExpandListener(new TreeWillExpandListener() {

			public void treeWillCollapse(TreeExpansionEvent event)
					throws ExpandVetoException {

			}

			public void treeWillExpand(TreeExpansionEvent event)
					throws ExpandVetoException {
				updateTreeNodeColor(prjTree);
			}

		});
		build(ui, prjTree, actionOnLoad, SysContext.getSysContext().getCurrentAdminUnit().getId().toString());
	}

	public void build(CoreUI ui, KDTree projectTree, ItemAction actionOnLoad,
			String curOrgId) throws Exception {
		this.ui = ui;
//		添加项目
		buildProjectTree(projectTree, buildOrgTree(actionOnLoad,curOrgId));
		// 刷新图标
		setCustomerIcon((TreeNode)projectTree.getModel().getRoot());
	}
	
	private static void setCustomerIcon(TreeNode treeNode){		
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)treeNode;
		if(thisNode.getUserObject() instanceof ProjectBaseInfo) {
			thisNode.setCustomIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
		}else if(thisNode.getUserObject() instanceof OperationPhasesInfo) {
			thisNode.setCustomIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
		}
		int childCount = treeNode.getChildCount();
		while(childCount>0) {				
			setCustomerIcon(treeNode.getChildAt(childCount-1));		
			childCount--;
		}	

	}

	private TreeModel buildOrgTree(ItemAction actionOnLoad,String curOrgId) throws Exception{
		if (curOrgId == null && !noOrgIsolation) {
			curOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		}
		authorizedOrgs = new HashSet();
		String oql = "select id where longnumber like'%"+ SysContext.getSysContext().getCurrentOrgUnit().getLongNumber()+"%'";
		FullOrgUnitCollection coll = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(oql);
		for (int i = 0; i < coll.size(); i++) {
			authorizedOrgs.add(coll.get(i).getId().toString());
		}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,
				"", curOrgId, null, getActionPK(actionOnLoad));
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) orgTreeModel.getRoot();
		root.removeAllChildren();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isleaf",Integer.valueOf(0)));	
		filter.getFilterItems().add(new FilterItemInfo("number","B%",CompareType.LIKE));	
		if (authorizedOrgs == null || authorizedOrgs.size() == 0) {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", authorizedOrgs,CompareType.INCLUDE));
		}
		ITreeBuilder treeBuilder = null;
		treeBuilder = TreeBuilderFactory.createTreeBuilder(new DefaultLNTreeNodeCtrl(FullOrgUnitFactory.getRemoteInstance()), Integer.MAX_VALUE, 5, filter);
		KDTree tree =  treeBuilder.buildTree(null);
		KDTree newKDTree = new KDTree((DefaultKingdeeTreeNode)root.clone());
		cloneTree((DefaultKingdeeTreeNode)(newKDTree.getModel().getRoot()), (DefaultKingdeeTreeNode)(tree.getModel().getRoot()));
		return newKDTree.getModel();
	}
	/**
	 * 根据部门获得所有子部门（所有深度下的子节点）
	 * 
	 * @param parent
	 * @return
	 * @throws BOSException
	 */
	public static AdminOrgUnitCollection getChildByParent(AdminOrgUnitInfo parent) throws BOSException {
		AdminOrgUnitCollection resutl = new AdminOrgUnitCollection();
		resutl.add(parent);
		EntityViewInfo evInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evInfo.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("parent",parent.getId().toString()));
		AdminOrgUnitCollection collection = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitCollection(evInfo);
		for (int i = collection.size(); --i >= 0;) {
			AdminOrgUnitInfo adminOrgUnitInfo = collection.get(i);
			if (!adminOrgUnitInfo.isIsLeaf()) {
				resutl.addCollection(getChildByParent(adminOrgUnitInfo));
			}
		}
		resutl.addCollection(collection);
		return resutl;
	}
	private void buildProjectTree(KDTree projectTree, TreeModel newTreeModel) throws Exception {
		projectTree.setModel(newTreeModel);
		buildProjTree();
		Map idNode = (Map)projTree.getUserObject();
		Map orgMap = new HashMap();
		genNodeMap((DefaultKingdeeTreeNode)newTreeModel.getRoot(),orgMap);
		//使用项目的ORGID 里匹配是哪个组织的
		for(Iterator it = idNode.keySet().iterator();it.hasNext();){
			String id = (String)it.next();
			DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode)idNode.get(id);
			ProjectBaseInfo info = (ProjectBaseInfo)proNode.getUserObject();
			String orgId = info.getEngineeProject().getId().toString();
			DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode)orgMap.get(orgId);
			proNode.setText(info.getName());
			if(orgNode!=null )
				orgNode.add(proNode);
		}
	}

	

	public ProjectTreeForValue() {
		noOrgIsolation = false;
	}


	private IMetaDataPK getActionPK(ItemAction action) {
		if (action == null) {
			return null;
		}
		String actoinName = action.getClass().getName();
		if (actoinName.indexOf("$") >= 0) {
			actoinName = actoinName.substring(actoinName.indexOf("$") + 1);
		}

		return new MetaDataPK(actoinName);
	}


	private ITreeBuilder treeBuilder;

	/**
	 * 构造项目分期树
	 */
	private KDTree buildOperationPhasesTree() throws Exception {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isNewProject","1"));
//		filter.getFilterItems().add(new FilterItemInfo("level","1"));
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("*");
		sel.add("projectBase.*");
		treeBuilder = TreeBuilderFactory.createTreeBuilder(getProjLNTreeNodeCtrl(), getTreeInitialLevel(),getTreeExpandLevel(),filter,sel);

		return treeBuilder.buildTree(null);
	}

	/**
	 * 构造项目树
	 * @throws Exception 
	 */
	private void buildProjTree() throws Exception{
		KDTree ProjecTree = projTree;
		Map idProjectNodes = new HashMap(); 
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add("*");
		coll.add("cityProject.*");
		view.setSelector(coll);
		/*显示最新项目*/
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isNewProject","1"));
		view.setFilter(filter);
		ProjectBaseCollection  projectColl = ProjectBaseFactory.getRemoteInstance().getProjectBaseCollection(view);
		for(int i = 0 ; i <projectColl.size(); i++){
			ProjectBaseInfo info = projectColl.get(i);
			DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode();
			node.setUserObject(info);
			idProjectNodes.put(info.getId().toString(), node);
		}
		
		
//		KDTree opTree = buildOperationPhasesTree();
//		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)opTree.getModel().getRoot();
//		//<KEY = 项目ID,VALUE = 节点>
//		Map ProjectIdNodeMap = getProjectIdNodeMap(root);
//		for(Iterator it = ProjectIdNodeMap.keySet().iterator(); it.hasNext();){
//			String id = (String)it.next();
//			//分期节点
//			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)ProjectIdNodeMap.get(id);
//			OperationPhasesInfo info = (OperationPhasesInfo)node.getUserObject();
//			//项目节点
//			DefaultKingdeeTreeNode pNode = (DefaultKingdeeTreeNode)idProjectNodes.get(info.getProjectBase().getId().toString());
//			if(pNode!=null)
//				pNode.add(node);
//		}
		ProjecTree.setUserObject(idProjectNodes);
	}

	private Map getProjectIdNodeMap(DefaultKingdeeTreeNode root) throws BOSException {
		Map idOperationNode = new HashMap();
		Set idSet = new HashSet();
		//取出所有的一级节点ID，取得projectID
		String id = null;
		for(int i = 0 ; i < root.getChildCount() ; i++){
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)root.getChildAt(i);
			id = (((OperationPhasesInfo)node.getUserObject())).getId().toString();
			idOperationNode.put(id, node);
			idSet.add(id);
		}
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add("projectBase.id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isNewProject","1"));
		filter.getFilterItems().add(new FilterItemInfo("level","1"));
		view.setSelector(coll);
		view.setFilter(filter);
		//src begin modified 2012-03-19 15:29
		view.getSorter().add(new SorterItemInfo("number"));
		//src end
		OperationPhasesCollection  opColl = OperationPhasesFactory.getRemoteInstance().getOperationPhasesCollection(view);
		Map resultMap = new LinkedHashMap();
		for(int i = 0 ; i < opColl.size(); i++){
			OperationPhasesInfo info = opColl.get(i);
			String proId = info.getId().toString();
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)idOperationNode.get(proId);
			if(node!=null)
				node.setUserObject(info);
			resultMap.put(proId,node);
		}
		return resultMap;
	}
	/**
	 * 克隆所选树节点，包含子节点
	 */
	private void cloneTree(DefaultKingdeeTreeNode newNode,	DefaultKingdeeTreeNode oriNode) {
		for (int i = 0; i < oriNode.getChildCount(); i++) {
			DefaultKingdeeTreeNode oriChild = (DefaultKingdeeTreeNode) oriNode.getChildAt(i);
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) oriChild.clone();
			newNode.add(child);
			cloneTree(child, oriChild);
		}
	}

	private int getTreeInitialLevel() {
		return 50;
	}

	private int getTreeExpandLevel() {
		return 5;
	}

	private ILNTreeNodeCtrl getProjLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(getProjTreeInterface());
	}

	private ITreeBase getProjTreeInterface() throws Exception {
		ITreeBase treeBase = OperationPhasesFactory.getRemoteInstance();
		return treeBase;
	}

	

	/**
	 * 
	 * 描述：生成指定节点及下级节点的Map[id, node]
	 * 
	 * @param root
	 * @param map
	 * @author:liupd 创建时间：2006-7-20
	 *               <p>
	 */
	public void genNodeMap(TreeNode root, Map map) {
		int count = root.getChildCount();
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) root.getChildAt(i);
			Object userObj = treeNode.getUserObject();
			String id = null;
			if (userObj instanceof FullOrgUnitInfo) {
				id = ((FullOrgUnitInfo) userObj).getId().toString();

			} 

			if (id != null) {
				map.put(id, treeNode);
			}

			genNodeMap(treeNode, map);
		}
	}

	// 遍历projectTree，找到unitId为Root的子树
	public static DefaultKingdeeTreeNode getDefaultKingdeeTreeNode(
			DefaultKingdeeTreeNode root, String unitId) {
		if (unitId == null) {
			return root;
		} else {

			Object obj = ((DefaultKingdeeTreeNode) root).getUserObject();

			if (!(obj instanceof OrgStructureInfo)) {
				return null;
			}

			String orgRootId = ((OrgStructureInfo) obj).getUnit().getId()
					.toString();
			if (!orgRootId.equals(unitId)) {
				int count = root.getChildCount();
				DefaultKingdeeTreeNode treeNode = null;
				for (int i = 0; i < count; i++) {
					treeNode = (DefaultKingdeeTreeNode) root.getChildAt(i);

					obj = ((DefaultKingdeeTreeNode) treeNode).getUserObject();
					if (!(obj instanceof OrgStructureInfo)) {
						continue;
					}
					orgRootId = ((OrgStructureInfo) obj).getUnit().getId()
							.toString();

					if (!orgRootId.equals(unitId)) {
						DefaultKingdeeTreeNode value = getDefaultKingdeeTreeNode(
								treeNode, unitId);
						if (value != null) {
							return value;
						}
					} else {
						return treeNode;
					}
				}

			} else {
				return root;
			}
		}
		return null;

	}


}
