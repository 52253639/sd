package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;

public class CostMap implements Serializable{
	private HashMap dataMap=new HashMap();
	public CostMap(){
	}
	
	public void put(String key,Object value){
		dataMap.put(key, value);
	}
	public Object get(String key){
		return dataMap.get(key);
	}
	public void addAdapter(String key,CostUIAdapter adapter){
		put(key,adapter);
	}
	public CostUIAdapter getAdapter(String key){
		return (CostUIAdapter)get(key);
	}
	
	public void setMaxLevel(int maxLevel){
		put("maxLevel",new Integer(maxLevel));
	}
	public int getMaxLevel(){
		Integer maxLevel=(Integer)get("maxLevel");
		if(maxLevel!=null){
			return maxLevel.intValue();
		}
		return 0;
	}
	public static class CostUIAdapter implements Serializable{
		private Map map=new HashMap();
		public void put(String key,Object value){
			map.put(key, value);
		}
		public Object get(String key){
			return map.get(key);
		}
		public BOSUuid getId(){
			return (BOSUuid)get("id");
		}
		public void setId(BOSUuid id){
			put("id",id);
		}
		public String getAcctNumber(){
			return (String)map.get("acctNumber");
		}
		public String getAcctName(){
			return (String)map.get("acctName");
		}
		
		public int getTreeLevel(){
			Integer treeLevel=(Integer)get("treeLevel");
			if(treeLevel!=null){
				return treeLevel.intValue();
			}
			return 0;
		}
		
		public void setAcctNumber(String acctNumber){
			put("acctNumber", acctNumber);
		}
		public void setAcctName(String acctName){
			put("acctName", acctName);
		}
		
		public void setTreeLevel(int treeLevel){
			put("treeLevel",new Integer(treeLevel));
		}
		
		public BigDecimal getAmount(){
			return (BigDecimal)get("amount");
		}
		public void setAmount(BigDecimal amount){
			put("amount",amount);
		}
		
		public String toString() {
			return map.toString();
		}
	}
	
	private static final BOSObjectType prjType = new CurProjectInfo().getBOSType();
	private static final boolean isProject(String orgOrprjId){
		if(orgOrprjId!=null){
			final BOSUuid bosid = BOSUuid.read(orgOrprjId);
			return prjType.equals(bosid.getType());
		}
			
		return false; 
	}
	
	public static TreeModel createAcctTreeMode(String orgOrprjId,Context ctx) throws BOSException{
		FilterInfo acctFilter = new FilterInfo();
		if (isProject(orgOrprjId)) {
			acctFilter.getFilterItems().add(
					new FilterItemInfo("curProject.id", orgOrprjId));
		} else {
			acctFilter.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id", orgOrprjId));
		}
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(acctFilter);
		view.getSelector().add("*");
		view.getSelector().add("curProject.*");
		view.getSelector().add("fullOrgUnit.*");
		view.getSorter().add(new SorterItemInfo("longNumber"));
		CostAccountCollection acct=null;
		if(ctx!=null){
			acct=CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		}else{
			acct=CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
		}
		return createTreeMode(acct);
		
	}
	public static TreeModel createTreeMode(CostAccountCollection acct){
		if(acct.size()<=0){
			return null;
		}
		DefaultMutableTreeNode root=new DefaultMutableTreeNode("root");
		TreeModel mode=new DefaultTreeModel(root);
		for(int i=0;i<acct.size();i++){
			CostAccountInfo acctInfo = acct.get(i);
			if(acctInfo.getLevel()==1){
				root.add(getSubTreeNode(i, acct));
			}
		}
		return mode;
	}
	
	private static DefaultMutableTreeNode getSubTreeNode(int index,CostAccountCollection acct){
		CostAccountInfo acctInfo = acct.get(index);
		DefaultMutableTreeNode node=new DefaultMutableTreeNode(acctInfo.getLongNumber());
		int level=acctInfo.getLevel();
		node.setUserObject(acctInfo);
		if(acctInfo.isIsLeaf()){
			return node;
		}else{
			for(int i=index+1;i<acct.size();i++){
				CostAccountInfo subAcctInfo = acct.get(i);
				if(subAcctInfo.getLevel()<=level){
					break;
				}else if(subAcctInfo.getLevel()==level+1){
					node.add(getSubTreeNode(i, acct));
				}
			}
			return node;
		}
	}
	
	public Set getKeySet(){
		return new TreeSet(dataMap.keySet());
	}
	
	public String toString() {
		return dataMap.toString();
	}
}
