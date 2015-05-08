package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;

/**
 * 
 * 描述：工程项目的WBS树， 主要用来重算编码， 上移，下移，升级，降级等
 * @author zhiqiao_yang date：2010-7-28<p>
 * @version <EAS600>
 */
public class FDCWBSTree implements Serializable{
	 private static Logger logger =  Logger.getLogger("com.kingdee.eas.fdc.schedule.FDCWBSTree");
	private List rootList = new ArrayList();
	private Map id2Node = new HashMap();
	private final String projectId;
	private Set excludeWbsIds = new HashSet();
	
	private FDCWBSTree(String projectId){
		this.projectId = projectId;
	}
	/**
	 * 描述：
	 * @param wbsCol
	 * @param projectId
	 * @return
	 * 创建时间：2010-7-28
	 * 创建人：zhiqiao_yang
	 */
	public static FDCWBSTree getTreeFromCollection(FDCWBSCollection wbsCol, String projectId){
		FDCWBSTree ret = new FDCWBSTree(projectId);
		ret.buildTree(wbsCol);
		return ret;
	}
	/**
	 * 描述：
	 * @param ctx
	 * @param projectId
	 * @return
	 * @throws BOSException
	 * 创建时间：2010-7-28
	 * 创建人：zhiqiao_yang
	 */
	public static FDCWBSTree getTreeFromDB(Context ctx, String projectId) throws BOSException{
		FDCWBSTree ret = new FDCWBSTree(projectId);
		ret.buildTree(ctx, projectId);
		return ret;
	}
	
	public static FDCWBSTree getTreeFromDB(Context ctx, String projectId, Set excludeWbsIds) throws BOSException {
		FDCWBSTree ret = new FDCWBSTree(projectId);
		if (excludeWbsIds != null) {
			ret.excludeWbsIds = excludeWbsIds;
		}
		ret.buildTree(ctx, projectId);
		return ret;
	}
	
	public String getProjectId(){
		return this.projectId;
	}
	private void buildTree(Context ctx, String projectId) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("curProject.id", projectId));
		view.getSelector().add("*");
		view.getSelector().add("parent.*");
		view.getSelector().add("taskType.id");
		view.getSelector().add("taskType.name");
		view.getSelector().add("taskType.longNumber");

		view.getSelector().add("adminPerson.id");
		view.getSelector().add("adminPerson.name");
		view.getSelector().add("adminPerson.number");

		view.getSelector().add("adminDept.id");
		view.getSelector().add("adminDept.name");
		view.getSelector().add("adminDept.number");
		
		view.getSelector().add("adminDept.CU.id");
		view.getSelector().add("adminDept.CU.name");
		view.getSelector().add("adminDept.CU.number");
		
		view.getSelector().add("respDept.id");
		view.getSelector().add("respDept.name");
		view.getSelector().add("respDept.number");
		
		view.getSelector().add("respDept.CU.id");
		view.getSelector().add("respDept.CU.name");
		view.getSelector().add("respDept.CU.number");
		view.getSorter().add(new SorterItemInfo("longnumber"));
		FDCWBSCollection wbsCol = null;
		if (ctx != null) {
			wbsCol = FDCWBSFactory.getLocalInstance(ctx).getFDCWBSCollection(view);
		} else {
			wbsCol = FDCWBSFactory.getRemoteInstance().getFDCWBSCollection(view);
		}
		buildTree(wbsCol);
	}
	private void buildTree(FDCWBSCollection wbsCol) {
		FDCWBSInfo wbs = null;
		FDCWBSTreeNode node = null;
		for (int i = 0; i < wbsCol.size(); ++i) {
			wbs = wbsCol.get(i);
			if (!this.excludeWbsIds.contains(wbs.getId().toString())) {
				node = new FDCWBSTreeNode(this, wbs, null);
				id2Node.put(wbs.getId().toString(), node);
			}
		}
		for (int i = 0; i < wbsCol.size(); ++i) {
			wbs = wbsCol.get(i);
			if (!this.excludeWbsIds.contains(wbs.getId().toString())) {
				node = this.getNode(wbs.getId().toString());
				FDCWBSTreeNode parentNode = null;
				if (wbs.getParent() != null) {
					parentNode = getTreeNode(wbs.getParent().getId().toString());
				}
				if (parentNode != null) {
					node.setParent(parentNode);
					parentNode.getChildren().add(node);
				} else {
					rootList.add(node);
				}
				if (wbs.getParent() != null && parentNode == null) {
					logger.error("Init FDCWBSTree error: parentId = " + wbs.getParent().getId() + "; id = " + wbs.getId());
				}
			}
		}
	}
	public FDCWBSInfo getBrother(String wbsId, boolean isLeft){
		FDCWBSTreeNode node = getNode(wbsId);
		if(node != null){
			List brothers = rootList;
			if(node.getParent() != null){
				brothers = node.getParent().getChildren();
				int index = getIndexInList(brothers, node);
				if(isLeft){
					if(index > 0){
						return ((FDCWBSTreeNode)brothers.get(index - 1)).getEntity();
					}
				}else{
					if(index < brothers.size() - 1){
						return ((FDCWBSTreeNode)brothers.get(index + 1)).getEntity();
					}					
				}
			}
		}
		return null;
	}
	public void clear(){
		rootList.clear();
		id2Node.clear();
	}
	public List getBorthers(FDCWBSInfo wbs){
		FDCWBSTreeNode parentNode = null;
		List brothers = this.rootList;
		if(wbs.getParent() != null && wbs.getParent().getId() != null){
			parentNode = getTreeNode(wbs.getParent().getId().toString());			
		}
		if(parentNode != null){
			brothers = parentNode.getChildren();
		}
		return brothers;
	}
	public void addChild(FDCWBSInfo wbs, int index){
		FDCWBSTreeNode parentNode = null;
		List brothers = getBorthers(wbs);
		if(wbs.getParent() != null && wbs.getParent().getId() != null){
			parentNode = getTreeNode(wbs.getParent().getId().toString());			
		}
		FDCWBSTreeNode node =new FDCWBSTreeNode(this, wbs, parentNode);
		brothers.add(index, node);
		id2Node.put(wbs.getId().toString(), node);
	}
	public void addChild(FDCWBSInfo wbs, boolean isInsertHead){
		FDCWBSTreeNode parentNode = null;
		if(wbs.getParent() != null && wbs.getParent().getId() != null){
			parentNode = getTreeNode(wbs.getParent().getId().toString());			
		}
		FDCWBSTreeNode node =new FDCWBSTreeNode(this, wbs, parentNode);
		List brothers = getBorthers(wbs);
		if(isInsertHead){
			brothers.add(0, node);
		}else{
			brothers.add(node);
		}
		id2Node.put(wbs.getId().toString(), node);
	}
	/**
	 * 描述：为WBS树添加节点， 若待添加的wbs包含parent则作为改parent的子节点，否则改wbs将成为树的根节点
	 * @param wbs
	 * 创建时间：2010-7-28
	 * 创建人：zhiqiao_yang
	 */
	public void addChild( FDCWBSInfo wbs){
		addChild(wbs, false);
	}
	/**
	 * 描述：为WBS树添加节点，循环调用{@link #addChild(FDCWBSInfo)}
	 * @param wbsCol
	 * 创建时间：2010-7-28
	 * 创建人：zhiqiao_yang
	 * 
	 * @see #addChild(FDCWBSInfo)
	 */
	public void addChildren( FDCWBSCollection wbsCol){
		for (int i = 0; i < wbsCol.size(); ++i) {
			addChild(wbsCol.get(i));
		}
	}
	
	public Set getWBSIDs(){
		return id2Node.keySet();
	}
	public Map getId2WBSMap(){
		Map ret = new HashMap();
		Set set = id2Node.entrySet();
		for(Iterator iter = set.iterator(); iter.hasNext();){
			Map.Entry entry = (Entry) iter.next();
			ret.put(entry.getKey(), ((FDCWBSTreeNode)entry.getValue()).entity);
		}
		return ret;
	}	
	
	private FDCWBSTreeNode getTreeNode(String wbsId){
		return (FDCWBSTreeNode) id2Node.get(wbsId);
	}
	private void removeWBS(FDCWBSTreeNode currentNode, Map wbsId2Node){
		for(int i = 0; i < currentNode.getChildren().size(); ++i){
			removeWBS((FDCWBSTreeNode) currentNode.getChildren().get(i), wbsId2Node);
		}
		List brothers = this.rootList;
		if(currentNode.getParent() != null){
			brothers = currentNode.getParent().getChildren();
		}
		brothers.remove(currentNode);
		String id = currentNode.getEntity().getId().toString();
		wbsId2Node.put(id, currentNode);
		id2Node.remove(id);		
	}
	/**
	 * 描述：删除指定wbsid对应的节点及其子孙节点。 并返回已删除wbsid映射到的节点
	 * @param wbsId
	 * @return 
	 * 创建时间：2010-7-28
	 * 创建人：zhiqiao_yang
	 */
	public Map removeWBS(String wbsId){
		Map wbsId2Node = new HashMap();
		FDCWBSTreeNode currentNode = getTreeNode(wbsId);
		if(currentNode != null){
			removeWBS(currentNode, wbsId2Node);
		}
		return wbsId2Node;
	}
	
	public void moveUp(String wbsId) {
		FDCWBSTreeNode currentNode = getTreeNode(wbsId);
		List nodeList = rootList;
		if(currentNode.getParent() != null){
			nodeList = currentNode.getParent().getChildren();
		}
		int index = getIndexInList(nodeList, currentNode);
		swap(nodeList, index, index - 1);
	}
	
	public void moveDown(String wbsId) {
		FDCWBSTreeNode currentNode = getTreeNode(wbsId);
		List nodeList = rootList;
		if(currentNode.getParent() != null){
			nodeList = currentNode.getParent().getChildren();
		}
		int index = getIndexInList(nodeList, currentNode);
		swap(nodeList, index, index +1);
	}

	public void degrade(String wbsId) {
		testPrint();
		FDCWBSTreeNode currentNode = getTreeNode(wbsId);
		List nodeList = rootList;
		if(currentNode.getParent() != null){
			nodeList = currentNode.getParent().getChildren();
		}
		int index = getIndexInList(nodeList, currentNode);
		if(index >0 ){
			//非叶子节点且不是第一个子节点才可降级
			FDCWBSTreeNode leftNode = (FDCWBSTreeNode) nodeList.get(index-1);
			nodeList.remove(index);
			currentNode.setParent(leftNode);
			currentNode.getEntity().setParent(leftNode.getEntity());
			leftNode.getChildren().add(currentNode);			
		}	
		testPrint();	
	}

	public void upgrade(String wbsId) {
		testPrint();
		FDCWBSTreeNode currentNode = getTreeNode(wbsId);
		if(currentNode.getParent()!=null){
			if(currentNode.getEntity().getTaskType().getId().toString().equals(TaskTypeInfo.TASKTYPE_SPECIALTASK) 
					&& currentNode.getParent().getEntity().getTaskType().getId().toString().equals(TaskTypeInfo.TASKTYPE_MAINTASK)){
				//父任务为主项，升级任务为专项时， 不可升级
				return;
			}
			//非根节点才可升级
			FDCWBSTreeNode parentNode = currentNode.getParent();			
			List nodeList = rootList;
			if(parentNode.getParent() != null){
				nodeList = parentNode.getParent().getChildren();
			}
			currentNode.getParent().getChildren().remove(currentNode);
			currentNode.setParent(parentNode.getParent());
			if(parentNode.getParent()!=null){
				currentNode.getEntity().setParent(parentNode.getParent().getEntity());
			}else{
				currentNode.getEntity().setParent(null);
			}
			nodeList.add(getIndexInList(nodeList, parentNode)+1, currentNode);			
		}
		testPrint();
	}

	/**
	 * 描述：重算指定节点下所有子孙节点的WBS编码，长编码，index，level，isleaf。若指定节点为null，则重算整棵树中WBS相关属性
	 * 		自动将disabled节点放到同级最后
	 * @param parentWbsId
	 * 创建时间：2010-7-28
	 * 创建人：zhiqiao_yang
	 */
	public void reCalculateCode(String parentWbsId) {
		testPrint();
		FDCWBSTreeNode parentNode = getTreeNode(parentWbsId);
		List children = rootList;
		if (parentNode != null) {
			children = parentNode.getChildren();
		}
		resetDisabledWbs(children);
		for (int i = 0; i < children.size(); ++i) {
			FDCWBSTreeNode child = (FDCWBSTreeNode) children.get(i);
			child.reCalculateCode();
		}
		testPrint();
	}
	/**
	 * 描述：重算整棵树中WBS的编码，长编码，index，level，isleaf。
	 * 		通过调用 {@link #reCalculateCode(String)}实现
	 * 创建时间：2010-7-28
	 * 创建人：zhiqiao_yang
	 * @see #reCalculateCode(String)
	 */
	public void reCalculateCode(){
		reCalculateCode(null);
	}

	public List getRoot() {
		return rootList;
	}

	public FDCWBSTreeNode getNode(String wbsId) {
		return (FDCWBSTreeNode) id2Node.get(wbsId);
	}
	
	public class FDCWBSTreeNode implements Serializable{
		private FDCWBSInfo entity;
		private FDCWBSTreeNode parent;
		private List children = new ArrayList();
		private FDCWBSTree tree;
		private FDCWBSTreeNode(FDCWBSTree tree, FDCWBSInfo entity, FDCWBSTreeNode parent) {
			this.tree = tree;
			this.entity = entity;
			this.parent = parent;
		}
		public FDCWBSTreeNode getLeftBrother(){
			List brothers = tree.getRoot();
			if(this.getParent() != null){
				brothers = this.getParent().getChildren();
			}
			int index = getIndexInList(brothers, this);
			if(index >0){
				return (FDCWBSTreeNode) brothers.get(index -1); 
			}
			return null;
		}
		public FDCWBSInfo getEntity() {
			return entity;
		}

		public FDCWBSTreeNode getParent() {
			return parent;
		}
		
		public void setParent(FDCWBSTreeNode parent) {
			this.parent = parent;
		}

		public List getChildren() {
			return children;
		}

		public void setChildren(List children) {
			this.children = children;
		}
		public boolean isLeaf(){
			return children.isEmpty();
		}
		public int getAllChildrenCount(){
			int count = 0;
			for(int i = 0; i < children.size(); ++i){
				FDCWBSTreeNode child = (FDCWBSTreeNode)children.get(i);
				count += child.getAllChildrenCount()+1;
			}
			return count;
		}
		public int getRowIndex(){
			int rowIndex = 0;
			List parentChildren = tree.getRoot();
			if(parent != null){
				rowIndex = parent.getRowIndex()+1;
				parentChildren = parent.getChildren();
			}			
			for(int i = 0; i < parentChildren.size(); ++i){
				FDCWBSTreeNode brother = (FDCWBSTreeNode)parentChildren.get(i);
				if(brother.getEntity().getId().toString().equals(entity.getId().toString())){
					break;
				}else{
					rowIndex += brother.getAllChildrenCount()+1;
				}
			}
			return rowIndex;
		}
		public int getLevel(){
			if(parent == null) return 1;
			return parent.getEntity().getLevel()+1;
		}
		public String getNumber(){
			if(parent == null){		
				return formatCode(getIndexInList(tree.getRoot(), this)+1);
			}else{
				return formatCode(getIndexInList(parent.getChildren(), this)+1);
			}
		}
		public String getLongNumber(){
			if(parent == null){		
				return entity.getNumber();	
			}else{
				return parent.getEntity().getLongNumber()+"!"+getNumber();
			}
		}
		public void reCalculateCode(){
			entity.setNumber(getNumber());
			entity.setLongNumber(getLongNumber());
			entity.setLevel(getLevel());
			entity.setIsLeaf(isLeaf());
			//entity.setIndex(getRowIndex()+1);
			for(int i = 0; i < children.size(); ++i){
				((FDCWBSTreeNode)children.get(i)).reCalculateCode();
			}
		}
		private String formatString(){
			int level = getLevel();
			String ret = "";
			for(int i = 1; i < level; ++i){
				ret +="\t";
			}
			ret +=entity.getIndex()+"; "+entity.getLevel()+"; "+entity.getName()+"; "+entity.getNumber()+"; "+entity.getLongNumber();
			if(this.getParent() != null){
				ret +=" ("+this.getParent().getEntity().getLongNumber()+") ";
			}
			return ret;
		}
		public String testPrintString(){
			String ret = formatString();
			for(int i = 0; i < children.size(); ++i){
				FDCWBSTreeNode child = (FDCWBSTreeNode)children.get(i);
				ret +="\n"+child.testPrintString();
			}
			return ret;
		}
		public boolean equals(Object o){
			if(o == null) {
				return false;
			}
			if(o instanceof FDCWBSTreeNode){
				FDCWBSTreeNode node = (FDCWBSTreeNode)o;
				return getEntity().getId().toString().equals(node.getEntity().getId().toString());
			}else{
				return false;
			}
		}
		public  int hashCode(){
			return this.getEntity().getId().toString().hashCode();
		}
	}
	private static void resetDisabledWbs(List children){
		List disabledNode = new ArrayList();
		for(int i = 0; i < children.size(); ++i){
			FDCWBSTreeNode child = (FDCWBSTreeNode)children.get(i);			
			resetDisabledWbs(child.getChildren());
			if(!child.getEntity().isIsEnabled()){
				disabledNode.add(child);
			}
		}
		for(int i = 0; i < disabledNode.size(); ++i){
			children.remove(disabledNode.get(i));
			children.add(disabledNode.get(i));
		}
	}
	private static String formatCode(int code){
		if(code < 10){
			return "00"+code;
		}else if(code < 100){
			return "0"+code;			
		}else{
			return Integer.toString(code);
		}
	}
	
	private static int getIndexInList(List nodeList, FDCWBSTreeNode node){
		String nodeId = node.getEntity().getId().toString();
		for(int i = 0; i < nodeList.size(); ++i){
			FDCWBSTreeNode temp = (FDCWBSTreeNode) nodeList.get(i);
			if(nodeId.equals(temp.getEntity().getId().toString())){
				return i;
			}
		}
		return -1;
	}
	private static void swap(List nodeList, int source, int dest){
		int size = nodeList.size();
		if(source <0 || dest < 0 || dest >= size || source >= size ){
			return;//无法交换位置，do nothing
		}
		Object temp = nodeList.get(source);
		nodeList.set(source, nodeList.get(dest));
		nodeList.set(dest, temp);
	}
	public void testPrint(){
		if (true) {
			logger.info("FDCWBSTree testPrint: ");
//			System.out.println("FDCWBSTree testPrint: ");
			for(int i = 0; i < rootList.size(); ++i){
				FDCWBSTreeNode rootNode = (FDCWBSTreeNode)rootList.get(i);
				logger.info(rootNode.testPrintString());
//				System.out.println(rootNode.testPrintString());
			}
		}
	}
	
}
