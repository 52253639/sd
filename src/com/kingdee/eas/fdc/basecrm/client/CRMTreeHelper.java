package com.kingdee.eas.fdc.basecrm.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ISellProject;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.ShareOrgUnitCollection;
import com.kingdee.eas.fdc.sellhouse.ShareOrgUnitFactory;
import com.kingdee.eas.util.client.EASResource;

public class CRMTreeHelper {

	public static TreeModel getSaleOrgTree(ItemAction actionOnLoad, OrgUnitInfo orgInfo)	throws Exception {
		MetaDataPK dataPK = null;
		if (actionOnLoad != null) {
			String actoinName = actionOnLoad.getClass().getName();
			if (actoinName.indexOf("$") >= 0) {
				actoinName = actoinName.substring(actoinName.indexOf("$") + 1);
			}
			dataPK = new MetaDataPK(actoinName);
		}
		TreeModel tree = NewOrgUtils.getTreeModel(OrgViewType.SALE, "", orgInfo.getId().toString(), null, dataPK);		
		return tree;
	}	
	
	
	
	/**
	 * 构建项目树 
	 * includeSonSellPorject 是否包含子项目
	 * */	
	public static TreeModel getSellProjectTree(ItemAction actionOnLoad,boolean includeSonSellPorject)	throws Exception {
		FDCSysContext fdcSysContext = FDCSysContext.getInstance();
		OrgUnitInfo saleOrgInfo = SysContext.getSysContext().getCurrentOrgUnit();
		UserInfo currUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		
		//登陆组织是售楼组织
		if(fdcSysContext.getOrgMap().containsKey(saleOrgInfo.getId().toString())){
			OrgStructureInfo rootOrgStruct = new OrgStructureInfo();
			rootOrgStruct.setUnit(saleOrgInfo.castToFullOrgUnitInfo());
			rootOrgStruct.setDisplayName(saleOrgInfo.getName());
			DefaultKingdeeTreeNode rootNode = new DefaultKingdeeTreeNode(rootOrgStruct);
			
			String curOrgLongNumber = SysContext.getSysContext().getCurrentOrgUnit().getLongNumber();
			Map orgIdNodeMap = new HashMap();  //组织id对应的树节点
			Map orgLongNumberNodeMap = new HashMap();  //组织长编码对应的树节点
			orgIdNodeMap.put(saleOrgInfo.getId().toString(), rootNode);
			orgLongNumberNodeMap.put(curOrgLongNumber, rootNode);	
			FDCOrgStructureCollection fdcOrgColl = FDCOrgStructureFactory.getRemoteInstance()
				.getFDCOrgStructureCollection("select *,unit.name where tree.id = '"+OrgConstants.SALE_VIEW_ID+"'" +
						" and  longNumber like '"+curOrgLongNumber+"!%' and sellHouseStrut = 1 and IsHis = 0 and isValid = 1 order by longNumber ");
		
			for(int i=0;i<fdcOrgColl.size();i++){
				FDCOrgStructureInfo thisFDCOrgInfo= fdcOrgColl.get(i);
				if(orgIdNodeMap.get(thisFDCOrgInfo.getUnit().getId().toString())!=null) continue;
				
				OrgStructureInfo curOrgInfo = new OrgStructureInfo();
				curOrgInfo.setUnit(thisFDCOrgInfo.getUnit());
				curOrgInfo.setDisplayName(thisFDCOrgInfo.getUnit().getName());
				DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(curOrgInfo);
				
				DefaultKingdeeTreeNode parentNode = rootNode;	//寻找要挂的父售楼节点，但上级可能是非售楼节点，要继续往上找
				String thisLongNumber = thisFDCOrgInfo.getLongNumber();
				while(thisLongNumber.indexOf("!")>0){
					String parentLongNumber = thisLongNumber.substring(0,thisLongNumber.lastIndexOf("!"));
					if(orgLongNumberNodeMap.get(parentLongNumber)!=null){
						parentNode = (DefaultKingdeeTreeNode)orgLongNumberNodeMap.get(parentLongNumber);
						break;
					}
					thisLongNumber = parentLongNumber;
				}				
				parentNode.add(sonNode);				
				orgIdNodeMap.put(thisFDCOrgInfo.getUnit().getId().toString(), sonNode);
				orgLongNumberNodeMap.put(thisFDCOrgInfo.getLongNumber(), sonNode);
			}
			
			Set orgIdspIdSet = new HashSet(); //校验相同组织下的相同项目
			Map spIdToNode = new HashMap();   //项目id对应树的节点
			String permitSpIdStr = MarketingUnitFactory.getRemoteInstance().getPermitSellProjectIdSql(currUserInfo);
  			SellProjectCollection sellProjectColl = SellProjectFactory.getRemoteInstance().getSellProjectCollection(
					"select * where id in ("+  permitSpIdStr  +") "+(includeSonSellPorject?"":" and level = 1")+" order by level,number"); 
			for (int i = 0; i < sellProjectColl.size(); i++) {
				SellProjectInfo thisSpInfo = sellProjectColl.get(i);
				//thisSpInfo.setOrgUnit(saleOrgInfo.castToFullOrgUnitInfo());
				DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(thisSpInfo);
				sonNode.setCustomIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
				if(spIdToNode.get(thisSpInfo.getId().toString())!=null) continue;
				
				spIdToNode.put(thisSpInfo.getId().toString(), sonNode);
				if(thisSpInfo.getLevel()==1){
					DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode)orgIdNodeMap.get(thisSpInfo.getOrgUnit().getId().toString()); //创建组织
					if(parentNode!=null) {
						parentNode.add(sonNode);
						orgIdspIdSet.add(thisSpInfo.getId() + "," + thisSpInfo.getOrgUnit().getId());
					}else {
						rootNode.add(sonNode);
						orgIdspIdSet.add(thisSpInfo.getId() + "," + saleOrgInfo.getId());
					}
				}else{
					SellProjectInfo parentSpInfo = sellProjectColl.get(i).getParent();
					if(parentSpInfo==null) continue;
					DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode)spIdToNode.get(parentSpInfo.getId().toString());
					if(parentNode!=null) 
						parentNode.add(sonNode);
					else
						rootNode.add(sonNode);
				}
			}			
			
			//考虑共享项目
			if(spIdToNode.size()>0) {
				String spIdsString = getStringFromSet(spIdToNode.keySet());
				ShareOrgUnitCollection shareSpColl = ShareOrgUnitFactory.getRemoteInstance()
						.getShareOrgUnitCollection("select orgUnit.id,head.id,head.name where head.id in ("+spIdsString+") and head.level=1 ");
				for(int i=0;i<shareSpColl.size();i++) {
					DefaultKingdeeTreeNode shareOrgNode = (DefaultKingdeeTreeNode)orgIdNodeMap.get(shareSpColl.get(i).getOrgUnit().getId().toString());//共享项目
					if(shareOrgNode!=null) {
						DefaultKingdeeTreeNode shareSpNode = (DefaultKingdeeTreeNode)spIdToNode.get(shareSpColl.get(i).getHead().getId().toString());
						if(shareSpNode==null) continue;
						DefaultKingdeeTreeNode cloneSpNode = (DefaultKingdeeTreeNode)shareSpNode.clone();
						cloneTree(cloneSpNode,shareSpNode);
						if(!orgIdspIdSet.contains(shareSpColl.get(i).getHead().getId()+","+shareSpColl.get(i).getOrgUnit().getId())){
							shareOrgNode.add(cloneSpNode);
						}
					}
				}
			}
			
			return new DefaultTreeModel(rootNode);
		}else{	//非售楼组织           按项目的创建组织挂
			TreeModel tree = getSaleOrgTree(actionOnLoad,saleOrgInfo);  //在组织树上添加项目节点			
			DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)tree.getRoot();
			
			deleteNonCompanyNode(rootNode,rootNode,true);
			
			Map orgIdMap = getAllObjectIdMap(rootNode,"OrgStructure");
			Map spIdToNode = new HashMap();   //项目id对应树的节点
			SellProjectCollection sellProjectColl = SellProjectFactory.getRemoteInstance().getSellProjectCollection(
					"select * where id in ("+ MarketingUnitFactory.getRemoteInstance().getPermitSellProjectIdSql(currUserInfo)  +") " +
							" "+(includeSonSellPorject?"":" and level = 1")+" order by level,number"); 			
			for (int i = 0; i < sellProjectColl.size(); i++) {
				SellProjectInfo thisSpInfo = sellProjectColl.get(i);
				DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(thisSpInfo);
				sonNode.setCustomIcon(EASResource.getIcon("imgTbtn_assistantaccount"));	
				if(spIdToNode.get(thisSpInfo.getId().toString())!=null) continue;
				
				if(thisSpInfo.getLevel()==1){
					/*DefaultMutableTreeNode orgNode = (DefaultMutableTreeNode)orgIdMap.get(thisSpInfo.getOrgUnit().getId().toString());
					if(orgNode!=null) {
						if(spIdToNode.get(thisSpInfo.getId().toString())==null) {
							orgNode.add(sonNode);
							spIdToNode.put(thisSpInfo.getId().toString(), sonNode);
						}
					}*/
					if (thisSpInfo.getCompanyOrgUnit() == null) {
						continue;
					} else {
						if (orgIdMap.get(thisSpInfo.getCompanyOrgUnit().getId().toString()) == null) {
							continue;
						}
					}

					DefaultMutableTreeNode orgNode = (DefaultMutableTreeNode) orgIdMap
							.get(thisSpInfo.getCompanyOrgUnit().getId().toString());
					if (orgNode != null) {
						if (spIdToNode.get(thisSpInfo.getId().toString()) == null) {
							orgNode.add(sonNode);
							spIdToNode.put(thisSpInfo.getId().toString(), sonNode);
						}
					}
					
				}else{
					SellProjectInfo parentSpInfo = sellProjectColl.get(i).getParent();
					if(parentSpInfo==null) continue;
					DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode)spIdToNode.get(parentSpInfo.getId().toString());
					if(parentNode!=null) {
						if(spIdToNode.get(thisSpInfo.getId().toString())==null) {
							parentNode.add(sonNode);
							spIdToNode.put(thisSpInfo.getId().toString(), sonNode);
						}
					}
				}				
			}
			return tree;
		}		
	
	}
	
	
	
	/**构建项目树 ,包含子项目*/
	public static TreeModel getSellProjectTree(ItemAction actionOnLoad)	throws Exception {
		return getSellProjectTree(actionOnLoad,true);
	}
	
	
	
	/**
	 * 构建楼栋树（单元树）
	 * 树型结构 销售组织 - 销售项目 -  楼栋 - ( 单元 )
	 *  isShowUnit 是否显示单元
	 */
	public static TreeModel getBuildingTree(ItemAction actionOnLoad,boolean isShowUnit) throws Exception {
		return getBuildingTree(null,actionOnLoad,isShowUnit);
	}
	
	
	/**在指定的项目树下挂楼栋（单元）
	 * @param  sellProjectTree	指定的项目树 ，为空时取当前的权限项目树
	 * */
	public static TreeModel getBuildingTree(TreeModel sellProjectTree,ItemAction actionOnLoad,boolean isShowUnit) throws Exception {
		TreeModel retTree = sellProjectTree; 
		if(sellProjectTree==null) retTree = getSellProjectTree(actionOnLoad);
		
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)retTree.getRoot();	
		Map spIdMap = getAllObjectIdMap(rootNode,"SellProject");
		if(spIdMap.size()==0) return retTree;
		BuildingCollection buildColl = BuildingFactory.getRemoteInstance().getBuildingCollection(
					"select *"+ (isShowUnit?",units.*":"") +" where sellProject.id in ("+ getStringFromSet(spIdMap.keySet()) +") ");
		Map buildMap = new HashMap();
		CRMHelper.sortCollection(buildColl, "number", true);
		for (int i = 0; i < buildColl.size(); i++) {
			BuildingInfo buildInfo = buildColl.get(i);
			DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(buildInfo);
			sonNode.setCustomIcon(EASResource.getIcon("imgTbtn_copytotier"));		
			DefaultKingdeeTreeNode spNode = (DefaultKingdeeTreeNode)spIdMap.get(buildInfo.getSellProject().getId().toString());
			if(spNode!=null) {
				buildInfo.setSellProject((SellProjectInfo)spNode.getUserObject());
				spNode.add(sonNode);
				buildMap.put(buildInfo.getId().toString(), sonNode);
			}
		}
		
		if(!isShowUnit) return retTree;
		
		for (int i = 0; i < buildColl.size(); i++) {
			BuildingUnitCollection unitColl = buildColl.get(i).getUnits();
			CRMHelper.sortCollection(unitColl, "seq", true);
			for (int j = 0; j < unitColl.size(); j++) {
				BuildingUnitInfo buidUnitInfo = unitColl.get(j);
				if(!buidUnitInfo.isHaveUnit()){//(false显示，true不显示)
					buidUnitInfo.setBuilding(buildColl.get(i));
					DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(buidUnitInfo);
					sonNode.setCustomIcon(EASResource.getIcon("imgTree_folder_leaf"));	
					DefaultKingdeeTreeNode buildNode = (DefaultKingdeeTreeNode)buildMap.get(buildColl.get(i).getId().toString());
					if(buildNode!=null) {
						buildNode.add(sonNode);
					}
				}
			
			}
		}
		return retTree;
	}
	
	
	/**
	 * 构建指定项目下的楼栋树（单元树）
	 * 树型结构 销售项目 -  楼栋 - ( 单元 )
	 *  isShowUnit 是否显示单元
	 */
	public static TreeModel getBuildingTree(SellProjectInfo spInfo,boolean isShowUnit) throws Exception {
		DefaultKingdeeTreeNode rootNode = new DefaultKingdeeTreeNode(spInfo);
		if(spInfo==null || spInfo.getId()==null) return new DefaultTreeModel(rootNode);
		
		ISellProject spFactory = SellProjectFactory.getRemoteInstance();
		spInfo = spFactory.getSellProjectInfo("select * where id = '"+spInfo.getId()+"' ");
		rootNode = new DefaultKingdeeTreeNode(spInfo);
		
		if(!spInfo.isIsLeaf()) {
			Map spIdToNode = new HashMap();   //项目id对应树的节点
			SaleOrgUnitInfo saleOrgInfo = SysContext.getSysContext().getCurrentSaleUnit();
			SellProjectCollection sonsSpColl = spFactory.getSellProjectCollection(
											"select * where longNumber like '"+spInfo.getLongNumber()+"!%' order by level ");
			for (int i = 0; i < sonsSpColl.size(); i++) {
				SellProjectInfo thisSpInfo = sonsSpColl.get(i);
				thisSpInfo.setOrgUnit(saleOrgInfo.castToFullOrgUnitInfo());
				DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(thisSpInfo);
				sonNode.setCustomIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
				spIdToNode.put(thisSpInfo.getId().toString(), sonNode);
				if(thisSpInfo.getLevel()==(spInfo.getLevel()+1)){
					rootNode.add(sonNode);
				}else{
					SellProjectInfo parentSpInfo = sonsSpColl.get(i).getParent();
					if(parentSpInfo==null) continue;
					DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode)spIdToNode.get(parentSpInfo.getId().toString());
					if(parentNode!=null) {
						parentNode.add(sonNode);
					}
				}
			}
		}
		
		TreeModel sellPrjectTree = new DefaultTreeModel(rootNode);
		return getBuildingTree(sellPrjectTree, null, isShowUnit);
	}
	
	

	/**
	 * 查询某节点下所有的指定对象的id和节点的映射
	 * @param treeNode
	 * @param treeType  组织OrgStructure、租售项目SellProject
	 */
	
	public static Map getAllObjectIdMap(TreeNode treeNode,String treeType) {
		Map idMap = new HashMap();
		if(treeNode!=null) {
			String treeTypeStr = "OrgStructure、SellProject";
			if(treeType!=null && treeTypeStr.indexOf(treeType)>=0)
				fillTreeNodeIdMap(idMap,treeNode,treeType);
		}
		
		return idMap;
	}	
	
	private static void fillTreeNodeIdMap(Map idMap,TreeNode treeNode, String treeType){		
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)treeNode;
		
		if(treeType.equals("OrgStructure")) {  //存储的是组织单元id
			if(thisNode.getUserObject() instanceof OrgStructureInfo){
				OrgStructureInfo objectInfo = (OrgStructureInfo)thisNode.getUserObject();
				idMap.put(objectInfo.getUnit().getId().toString(),thisNode);
			}
		}else if(treeType.equals("SellProject")) {
			if(thisNode.getUserObject() instanceof SellProjectInfo){
				 SellProjectInfo objectInfo = (SellProjectInfo)thisNode.getUserObject();
				 idMap.put(objectInfo.getId().toString(),thisNode);
			}
		}

		int childCount = treeNode.getChildCount();
		
		while(childCount>0) {				
			fillTreeNodeIdMap(idMap,treeNode.getChildAt(childCount-1),treeType);		
			childCount--;
		}	

	}
	
	public static String getStringFromSet(Set srcSet){
		String retStr = "";
		if(srcSet==null || srcSet.size()==0) return retStr;
		Iterator iter = srcSet.iterator();
		while(iter.hasNext()){
			Object obj = iter.next();
			if(obj instanceof String) retStr += ",'" + (String)obj + "'";
		}
		if(!retStr.equals("")) retStr = retStr.replaceFirst(",", "");
		return retStr;
	}
	/**
	 * @description 得到节点的项目节点
	 * @author tim_gao
	 * @param node
	 * @return
	 */
	public static DefaultKingdeeTreeNode getSellProjectByNode(DefaultKingdeeTreeNode node){
		if(null!=node&&null!=node.getUserObject()){
			DefaultKingdeeTreeNode thisNode = node;
			if(node.getUserObject() instanceof SellProjectInfo){
				
			}else if(node.getUserObject() instanceof BuildingInfo){
				thisNode = getSellProjectByNode((DefaultKingdeeTreeNode)node.getParent());
			}else if(node.getUserObject() instanceof BuildingUnitInfo){
				thisNode =  getSellProjectByNode((DefaultKingdeeTreeNode)node.getParent());
			}
			return thisNode;
		}
		return null;
	}
	
	/**
	 * 删除组织结构中的非财务组织
	 * @param root
	 * @param treeNode
	 */
	private static void deleteNonCompanyNode(DefaultMutableTreeNode root,
			DefaultMutableTreeNode treeNode,boolean isEntryChild) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		List indexList = new ArrayList();
		if (thisNode.getUserObject() != null
				&& thisNode.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo org = (OrgStructureInfo) thisNode.getUserObject();
			if (org != null && org.getUnit() != null
					&& !org.getUnit().isIsCompanyOrgUnit()) {
				indexList.add(root.getIndex(thisNode) + "");
			}
		}
		
		if (indexList.size() > 0) {
			int temp = 0;
			for (int i = 0; i < indexList.size(); i++) {
				temp = Integer.parseInt(indexList.get(i).toString());
				if(temp == -1){
					if(thisNode.getParent()!=null){
						deleteNonCompanyNode((DefaultMutableTreeNode)thisNode.getParent(),thisNode,false);
					}
				}else{
					root.remove(temp);
				}
			}
		}

		if(isEntryChild){
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				deleteNonCompanyNode(root, (DefaultMutableTreeNode) treeNode
						.getChildAt(childCount - 1),true);
				childCount--;
			}
		}
	}
	
	
	/**
	 * 克隆所选树节点，包含子节点
	 */
	public static void cloneTree(DefaultKingdeeTreeNode newNode,DefaultKingdeeTreeNode oriNode) {
		for (int i = 0; i < oriNode.getChildCount(); i++) {
			DefaultKingdeeTreeNode oriChild = (DefaultKingdeeTreeNode)oriNode.getChildAt(i);
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode)oriChild.clone();
			newNode.add(child);
			cloneTree(child, oriChild);
		}
	}	
	
	
}
