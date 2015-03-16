package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.util.FontUtil;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.olap.collection.ArrayList;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureInfo;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlCollection;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlFactory;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberInfo;
import com.kingdee.eas.fdc.sellhouse.PlanisphereInfo;
import com.kingdee.eas.fdc.sellhouse.SellOrderInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ObjectBaseInfo;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class SHEMarketingunitHelper {
	private static final Logger log = Logger.getLogger(SHEMarketingunitHelper.class);
	private static Map  orgMap = FDCSysContext.getInstance().getOrgMap();
	
	public static TreeModel getMarketTree(ItemAction actionOnLoad,boolean includeUser)	throws Exception {		
		TreeModel tree = getSaleOrgTree(actionOnLoad);  
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getRoot();
		Map orgIdMap = getAllObjectIdMap(node,"OrgStructure");
		//如果是售楼组织则高亮显示
		hightLightForSHEOrg(orgIdMap);
		String orgIdsStr = getStringFromSet(orgIdMap.keySet());
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",orgIdsStr,CompareType.INNER));		
		ITreeBuilder treeBuilder = TreeBuilderFactory.createTreeBuilder(
				new DefaultLNTreeNodeCtrl(MarketingUnitFactory.getRemoteInstance()),Integer.MAX_VALUE,5,filter);
		KDTree kdTree = treeBuilder.buildTree(null);
		
		KDTreeNode kdNode = (KDTreeNode)kdTree.getModel().getRoot();
		for(int i=0;i<kdNode.getChildCount();i++) {		//在组织树上添加营销单元节点
			KDTreeNode thisNode = (KDTreeNode)kdNode.getChildAt(i);
			KDTreeNode newNode = (KDTreeNode)thisNode.clone();
			cloneTree(newNode,thisNode);
			newNode.setCustomIcon(EASResource.getIcon("imgTbtn_BatchAdd"));
			MarketingUnitInfo muInfo = (MarketingUnitInfo)newNode.getUserObject();
			if(muInfo.getOrgUnit()!=null) {
				DefaultMutableTreeNode saleOrgNode = (DefaultMutableTreeNode)orgIdMap.get(muInfo.getOrgUnit().getId().toString());
				if(saleOrgNode!=null) saleOrgNode.add(newNode);				
			}			
			if(!newNode.isLeaf()) updateSonsNodeCustomIcon(newNode);
		}
		
		if(includeUser) {		
			//在组织上挂管控人员  
			MarketUnitControlCollection muControlColl = MarketUnitControlFactory.getRemoteInstance().getMarketUnitControlCollection(
					"select controler.id,controler.name,controler.number,orgUnit.id where orgUnit.id in("+ orgIdsStr + ") " );
			for(int i=0;i<muControlColl.size();i++) {
				MarketUnitControlInfo muControlInfo = muControlColl.get(i);
				UserInfo thisInfo = muControlInfo.getControler();
				KDTreeNode newNode = getNewKDTreeNodeByObject(thisInfo,"admin");
				if(newNode!=null) {
					DefaultMutableTreeNode muNode = (DefaultMutableTreeNode)orgIdMap.get(muControlInfo.getOrgUnit().getId().toString());
					if(muNode!=null) muNode.insert(newNode,0);
				}
			}
			
			//在营销单元节点上挂营销顾问
			Map muIdMap = getAllObjectIdMap(node,"MarketingUnit");
			MarketingUnitMemberCollection muMember = MarketingUnitMemberFactory.getRemoteInstance().getMarketingUnitMemberCollection(
					"select member.id,member.name,member.number,head.id,isDuty where head.orgUnit.id in ("+orgIdsStr+")"); 
			for(int i=0;i<muMember.size();i++) {
				MarketingUnitMemberInfo muMemberInfo = muMember.get(i);				
				UserInfo thisInfo = muMemberInfo.getMember();
				KDTreeNode newNode = getNewKDTreeNodeByObject(thisInfo,muMemberInfo.isIsDuty()?"admin":"person");
				if(newNode!=null) {
					DefaultMutableTreeNode muNode = (DefaultMutableTreeNode)muIdMap.get(muMemberInfo.getHead().getId().toString());
					if(muNode!=null)  {
						if(muMemberInfo.isIsDuty()) 
							muNode.insert(newNode,0);
						else
							muNode.add(newNode);
					}
				}
			}			
		}		
		return tree;		
	}
	
	/**
	 * 构件销售组织树  -- 
	 * @param actionOnLoad
	 * @return TreeModel
	 * @throws Exception
	 */
	public static TreeModel getSaleOrgTree(ItemAction actionOnLoad)	throws Exception {
		SaleOrgUnitInfo saleOrg = getCurrentSaleOrg();
		return getSaleOrgTree(actionOnLoad,saleOrg);
	}
	
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
	 * 获得当前的销售组织单元
	 * @return
	 */
	public static SaleOrgUnitInfo getCurrentSaleOrg() {
		SaleOrgUnitInfo saleUnit = SysContext.getSysContext().getCurrentSaleUnit();
		if (saleUnit == null) {
			MsgBox.showInfo("当前组织不是销售中心,不能进入!");
			SysUtil.abort();
		}
		return saleUnit;
	}
	
	/**
	 * 查询某节点下所有的指定对象的id和节点的映射
	 * @param treeNode
	 * @param treeType  组织OrgStructure、租售项目SellProject、盘次SellOrder、分区Subarea、楼栋Building、单元BuildingUnit、平面图Planisphere、营销单元MarketingUnit、营销顾问User
	 */
	
	public static Map getAllObjectIdMap(TreeNode treeNode,String treeType) {
		Map idMap = new HashMap();
		if(treeNode!=null) {
			String treeTypeStr = "OrgStructure、SellProject、SellOrder、Subarea、Building、BuildingUnit、Planisphere、MarketingUnit、User、FDCOrgStructure";
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
			//新增加获得组织id by add renliang by 2011-6-10
		}else if(treeType.equals("FDCOrgStructure")){
			if(thisNode.getUserObject() instanceof OrgStructureInfo){
				OrgStructureInfo objectInfo = (OrgStructureInfo)thisNode.getUserObject();
				idMap.put(objectInfo.getId().toString(),thisNode);
			}	
		}else if(treeType.equals("SellProject")) {
			if(thisNode.getUserObject() instanceof SellProjectInfo){
				 SellProjectInfo objectInfo = (SellProjectInfo)thisNode.getUserObject();
				 idMap.put(objectInfo.getId().toString(),thisNode);
			}
		}else if(treeType.equals("SellOrder")) {
			if(thisNode.getUserObject() instanceof SellOrderInfo){
				SellOrderInfo objectInfo = (SellOrderInfo)thisNode.getUserObject();
				idMap.put(objectInfo.getId().toString(),thisNode);
			}
		}else if(treeType.equals("Subarea")) {
			if(thisNode.getUserObject() instanceof SubareaInfo){
				SubareaInfo objectInfo = (SubareaInfo)thisNode.getUserObject();
				idMap.put(objectInfo.getId().toString(),thisNode);
			}
		}else if(treeType.equals("Building")) {
			if(thisNode.getUserObject() instanceof BuildingInfo){
				BuildingInfo objectInfo = (BuildingInfo)thisNode.getUserObject();
				idMap.put(objectInfo.getId().toString(),thisNode);
			}
		}else if(treeType.equals("BuildingUnit")) {
			if(thisNode.getUserObject() instanceof BuildingUnitInfo){
				BuildingUnitInfo objectInfo = (BuildingUnitInfo)thisNode.getUserObject();
				idMap.put(objectInfo.getId().toString(),thisNode);
			}
		}else if(treeType.equals("Planisphere")) {
			if(thisNode.getUserObject() instanceof PlanisphereInfo){
				PlanisphereInfo objectInfo = (PlanisphereInfo)thisNode.getUserObject();
				idMap.put(objectInfo.getId().toString(),thisNode);
			}
		}else if(treeType.equals("MarketingUnit")){
			if(thisNode.getUserObject() instanceof MarketingUnitInfo){
				MarketingUnitInfo objectInfo = (MarketingUnitInfo)thisNode.getUserObject();
				idMap.put(objectInfo.getId().toString(),thisNode);
			}
		}else if(treeType.equals("User")) {
			if(thisNode.getUserObject() instanceof UserInfo){
				UserInfo objectInfo = (UserInfo)thisNode.getUserObject();
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
	 * 克隆所选树节点，包含子节点
	 */
	public static void cloneTree(DefaultKingdeeTreeNode newNode,	DefaultKingdeeTreeNode oriNode) {
		for (int i = 0; i < oriNode.getChildCount(); i++) {
			DefaultKingdeeTreeNode oriChild = (DefaultKingdeeTreeNode) oriNode.getChildAt(i);
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) oriChild.clone();
			newNode.add(child);
			cloneTree(child, oriChild);
		}
	}
	
	private static void updateSonsNodeCustomIcon(KDTreeNode thisNode) {
		for(int i=0;i<thisNode.getChildCount();i++) {
			KDTreeNode sonNode = (KDTreeNode)thisNode.getChildAt(i);
			sonNode.setCustomIcon(EASResource.getIcon("imgTbtn_BatchAdd"));
			if(!sonNode.isLeaf()) 
				updateSonsNodeCustomIcon(sonNode);
		}
	}
	
	public static KDTreeNode getNewKDTreeNodeByObject(ObjectBaseInfo nodeObject,String iconType) {
		KDTreeNode treeNode = null;
		if(nodeObject!=null && (nodeObject instanceof DataBaseInfo || nodeObject instanceof FDCBillInfo || nodeObject instanceof UserInfo)) {
			if(nodeObject instanceof DataBaseInfo) {
	 			treeNode = new KDTreeNode(((DataBaseInfo)nodeObject).getName());
			}else if(nodeObject instanceof FDCBillInfo){
				treeNode = new KDTreeNode(((FDCBillInfo)nodeObject).getName());
			}else if(nodeObject instanceof UserInfo) {
				treeNode = new KDTreeNode(((UserInfo)nodeObject).getName());
			}
			treeNode.setUserObject(nodeObject);
		}else {
			return null;
		}
		if(nodeObject instanceof SellProjectInfo) {  //租售项目
			treeNode.setCustomIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
		}else if(nodeObject instanceof SellOrderInfo) {  //盘次
			treeNode.setCustomIcon(EASResource.getIcon("imgTbtn_sortorder"));
		}else if(nodeObject instanceof SubareaInfo) {  //分区
			treeNode.setCustomIcon(EASResource.getIcon("imgTbtn_showparent"));
		}else if(nodeObject instanceof BuildingInfo) {  //楼栋
			treeNode.setCustomIcon(EASResource.getIcon("imgTbtn_copytotier"));
		}else if(nodeObject instanceof BuildingUnitInfo) {  //单元
			treeNode.setCustomIcon(EASResource.getIcon("imgTree_folder_leaf"));
		}else if(nodeObject instanceof PlanisphereInfo) {  //平面图
			treeNode.setCustomIcon(EASResource.getIcon("imgTbtn_showpicture"));
		}else if(nodeObject instanceof UserInfo) {  //营销顾问 ： 负责人和成员
			if(iconType!=null) {
				if(iconType.equals("admin"))	treeNode.setCustomIcon(EASResource.getIcon("imgTbtn_conadministrator"));
				else if(iconType.equals("person"))	treeNode.setCustomIcon(EASResource.getIcon("imgTbtn_personnonepos"));
			}
		}
		return treeNode;	
	}
	
	private static void hightLightForSHEOrg(Map orgIdMap) throws BOSException {
		Set idSet = orgIdMap.keySet();
		for(Iterator it = idSet.iterator();it.hasNext();){
			String id = (String)it.next();
			if(orgMap.containsKey(id)){
				DefaultKingdeeTreeNode muNode = (DefaultKingdeeTreeNode)orgIdMap.get(id);
				muNode.setTextColor(Color.blue);
			}
		}
	}
	
	
	
}
