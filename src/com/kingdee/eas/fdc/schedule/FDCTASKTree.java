/**
 * 
 */
package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.framework.TreeBaseCollection;
/**
 * @author warship_zhang
 *
 */
public class FDCTASKTree {
	
		private static Logger logger =  Logger.getLogger("com.kingdee.eas.fdc.schedule.FDCTASKTree");
		private List rootList = new ArrayList();
		private Map id2Node = new HashMap();
		private final String projectId;
		
		private FDCTASKTree(String projectId){
			this.projectId = projectId;
		}
		/**
		 * 描述：
		 * @param taskCol
		 * @param projectId
		 * @return
		 * 创建时间：2010-7-28
		 * 创建人：warship_zhang
		 */
		public static FDCTASKTree getTreeFromCollection(FDCScheduleTaskCollection taskCol, String projectId){
			FDCTASKTree ret = new FDCTASKTree(projectId);
			ret.buildTree(taskCol);
			return ret;
		}
		/**
		 * 描述：
		 * @param ctx
		 * @param projectId
		 * @return
		 * @throws BOSException
		 * 创建时间：2010-7-28
		 * 创建人：warship_zhang
		 */
		public static FDCTASKTree getTreeFromDB(Context ctx, String projectId) throws BOSException{
			FDCTASKTree ret = new FDCTASKTree(projectId);
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
			FDCScheduleTaskCollection taskCol = null;
			if (ctx != null) {
				taskCol = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
			} else {
				taskCol = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
			}
			buildTree(taskCol);
		}
		private void buildTree(FDCScheduleTaskCollection taskCol) {
			FDCScheduleTaskInfo task = null;
			FDCTASKTreeNode node = null;
			for (int i = 0; i < taskCol.size(); ++i) {
				task = taskCol.get(i);
				node = new FDCTASKTreeNode(this, task, null);
				id2Node.put(task.getId().toString(), node);
			}
			for (int i = 0; i < taskCol.size(); ++i) {
				task = taskCol.get(i);
				node = this.getNode(task.getId().toString());
				FDCTASKTreeNode parentNode = null;
				if(task.getParent() != null){
					parentNode = getTreeNode(task.getParent().getId().toString());
				}
				if(parentNode != null){
					node.setParent(parentNode);
					parentNode.getChildren().add(node);
				}else{
					rootList.add(node);
				}
				if(task.getParent() != null && parentNode == null){
					logger.error("Init FDCTASKTree error: parentId = "+task.getParent().getId()+"; id = "+task.getId());
				}
			}
//			for(Iterator it=rootList.iterator();it.hasNext();){
//				FDCTASKTreeNode temp_node=(FDCTASKTreeNode)it.next();
//				while(temp_node.children!=null){
//					
//				}
//				
//			}
//			Set nodeSet =id2Node.keySet();
//			FDCTASKTreeNode temp_node = null;
//			for(Iterator it=nodeSet.iterator();it.hasNext();){
//				String taskID= (String)it.next();
//				if(id2Node.containsKey(taskID)){
//					temp_node =(FDCTASKTreeNode)id2Node.get(taskID);
//				}
//				int index=getIndexInList(rootList,temp_node);
//				if(index!=-1){
//					temp_node.entity.setLevel(index+1);
//				}
//			}
		}
		public FDCScheduleTaskInfo getBrother(String taskId, boolean isLeft){
			FDCTASKTreeNode node = getNode(taskId);
			if(node != null){
				List brothers = rootList;
				if(node.getParent() != null){
					brothers = node.getParent().getChildren();
					int index = getIndexInList(brothers, node);
					if(isLeft){
						if(index > 0){
							return ((FDCTASKTreeNode)brothers.get(index - 1)).getEntity();
						}
					}else{
						if(index < brothers.size() - 1){
							return ((FDCTASKTreeNode)brothers.get(index + 1)).getEntity();
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
		public List getBorthers(FDCScheduleTaskInfo task){
			FDCTASKTreeNode parentNode = null;
			List brothers = this.rootList;
			if(task.getParent() != null && task.getParent().getId() != null){
				parentNode = getTreeNode(task.getParent().getId().toString());			
			}
			if(parentNode != null){
				brothers = parentNode.getChildren();
			}
			return brothers;
		}
		public void addChild(FDCScheduleTaskInfo task, int index){
			FDCTASKTreeNode parentNode = null;
			List brothers = getBorthers(task);
			if(task.getParent() != null && task.getParent().getId() != null){
				parentNode = getTreeNode(task.getParent().getId().toString());			
			}
			FDCTASKTreeNode node =new FDCTASKTreeNode(this, task, parentNode);
			brothers.add(index, node);
			id2Node.put(task.getId().toString(), node);
		}
		public void addChild(FDCScheduleTaskInfo task, boolean isInsertHead){
			FDCTASKTreeNode parentNode = null;
			if(task.getParent() != null && task.getParent().getId() != null){
				parentNode = getTreeNode(task.getParent().getId().toString());			
			}
			FDCTASKTreeNode node =new FDCTASKTreeNode(this, task, parentNode);
			List brothers = getBorthers(task);
			if(isInsertHead){
				brothers.add(0, node);
			}else{
				brothers.add(node);
			}
			id2Node.put(task.getId().toString(), node);
		}
		/**
		 * 描述：为Task树添加节点， 若待添加的task包含parent则作为改parent的子节点，否则改task将成为树的根节点
		 * @param task
		 * 创建时间：2010-7-28
		 * 创建人：warship_zhang
		 */
		public void addChild( FDCScheduleTaskInfo task){
			addChild(task, false);
		}
		/**
		 * 描述：为task树添加节点，循环调用{@link #addChild(FDCScheduleTaskInfo)}
		 * @param taskCol
		 * 创建时间：2010-7-28
		 * 创建人：warship_zhang
		 * 
		 * @see #addChild(FDCScheduleTaskInfo)
		 */
		public void addChildren( FDCScheduleTaskCollection taskCol){
			for (int i = 0; i < taskCol.size(); ++i) {
				addChild(taskCol.get(i));
			}
		}
		
		public Set getTASKIDs(){
			return id2Node.keySet();
		}
		public Map getId2TaskMap(){
			Map ret = new HashMap();
			Set set = id2Node.keySet();
			for(Iterator iter = set.iterator(); iter.hasNext();){
				Object id = iter.next();
				ret.put(id, getTreeNode((String)id).getEntity());			
			}
			return ret;
		}	
		
		private FDCTASKTreeNode getTreeNode(String taskCol){
			return (FDCTASKTreeNode) id2Node.get(taskCol);
		}
		private void removeTask(FDCTASKTreeNode currentNode, Map taskId2Node){
			for(int i = 0; i < currentNode.getChildren().size(); ++i){
				removeTask((FDCTASKTreeNode) currentNode.getChildren().get(i), taskId2Node);
			}
			List brothers = this.rootList;
			if(currentNode.getParent() != null){
				brothers = currentNode.getParent().getChildren();
			}
			brothers.remove(currentNode);
			String id = currentNode.getEntity().getId().toString();
			taskId2Node.put(id, currentNode);
			id2Node.remove(id);		
		}
		/**
		 * 描述：删除指定taskId对应的节点及其子孙节点。 并返回已删除taskId映射到的节点
		 * @param taskId
		 * @return 
		 * 创建时间：2010-7-28
		 * 创建人：warship_zhang
		 */
		public Map removeTask(String taskId){
			Map taskId2Node = new HashMap();
			FDCTASKTreeNode currentNode = getTreeNode(taskId);
			if(currentNode != null){
				removeTask(currentNode, taskId2Node);
			}
			return taskId2Node;
		}
		
		public void moveUp(String taskId) {
			FDCTASKTreeNode currentNode = getTreeNode(taskId);
			List nodeList = rootList;
			if(currentNode.getParent() != null){
				nodeList = currentNode.getParent().getChildren();
			}
			int index = getIndexInList(nodeList, currentNode);
			swap(nodeList, index, index - 1);
		}
		
		public void moveDown(String taskId) {
			FDCTASKTreeNode currentNode = getTreeNode(taskId);
			List nodeList = rootList;
			if(currentNode.getParent() != null){
				nodeList = currentNode.getParent().getChildren();
			}
			int index = getIndexInList(nodeList, currentNode);
			swap(nodeList, index, index +1);
		}

		public void degrade(String taskId) {
			testPrint();
			FDCTASKTreeNode currentNode = getTreeNode(taskId);
			List nodeList = rootList;
			if(currentNode.getParent() != null){
				nodeList = currentNode.getParent().getChildren();
			}
			int index = getIndexInList(nodeList, currentNode);
			if(index >0 ){
				//非叶子节点且不是第一个子节点才可降级
				FDCTASKTreeNode leftNode = (FDCTASKTreeNode) nodeList.get(index-1);
				nodeList.remove(index);
				currentNode.setParent(leftNode);
				currentNode.getEntity().setParent(leftNode.getEntity());
				leftNode.getChildren().add(currentNode);			
			}	
			testPrint();	
		}

		public void upgrade(String taskId) {
			testPrint();
			FDCTASKTreeNode currentNode = getTreeNode(taskId);
			if(currentNode.getParent()!=null){
				if(currentNode.getEntity().getWbs().getTaskType().getId().toString().equals(TaskTypeInfo.TASKTYPE_SPECIALTASK) 
						&& currentNode.getParent().getEntity().getWbs().getTaskType().getId().toString().equals(TaskTypeInfo.TASKTYPE_MAINTASK)){
					//父任务为主项，升级任务为专项时， 不可升级
					return;
				}
				//非根节点才可升级
				FDCTASKTreeNode parentNode = currentNode.getParent();			
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
		 * 描述：重算指定节点下所有子孙节点的task编码，长编码，index，level，isleaf。若指定节点为null，则重算整棵树中task相关属性
		 * 		自动将disabled节点放到同级最后
		 * @param parentTaskId
		 * 创建时间：2010-7-28
		 * 创建人：warship_zhang
		 */
		public void reCalculateCode(String parentTaskId) {
			testPrint();
			FDCTASKTreeNode parentNode = getTreeNode(parentTaskId);
			List children = rootList;
			if (parentNode != null) {
				children = parentNode.getChildren();
			}
			resetDisabledTask(children);
			for (int i = 0; i < children.size(); ++i) {
				FDCTASKTreeNode child = (FDCTASKTreeNode) children.get(i);
				child.reCalculateCode();
			}
			testPrint();
		}
		/**
		 * 描述：重算整棵树中task的编码，长编码，index，level，isleaf。
		 * 		通过调用 {@link #reCalculateCode(String)}实现
		 * 创建时间：2010-7-28
		 * 创建人：warship_zhang
		 * @see #reCalculateCode(String)
		 */
		public void reCalculateCode(){
			reCalculateCode(null);
		}

		public List getRoot() {
			return rootList;
		}

		public FDCTASKTreeNode getNode(String taskId) {
			return (FDCTASKTreeNode) id2Node.get(taskId);
		}
		
		public class FDCTASKTreeNode implements Serializable{
			private FDCScheduleTaskInfo entity;
			private FDCTASKTreeNode parent;
			private List children = new ArrayList();
			private FDCTASKTree tree;
			private FDCTASKTreeNode(FDCTASKTree tree, FDCScheduleTaskInfo entity, FDCTASKTreeNode parent) {
				this.tree = tree;
				this.entity = entity;
				this.parent = parent;
			}
			public FDCTASKTreeNode getLeftBrother(){
				List brothers = tree.getRoot();
				if(this.getParent() != null){
					brothers = this.getParent().getChildren();
				}
				int index = getIndexInList(brothers, this);
				if(index >0){
					return (FDCTASKTreeNode) brothers.get(index -1); 
				}
				return null;
			}
			public FDCScheduleTaskInfo getEntity() {
				return entity;
			}

			public FDCTASKTreeNode getParent() {
				return parent;
			}
			
			public void setParent(FDCTASKTreeNode parent) {
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
					FDCTASKTreeNode child = (FDCTASKTreeNode)children.get(i);
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
					FDCTASKTreeNode brother = (FDCTASKTreeNode)parentChildren.get(i);
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
					((FDCTASKTreeNode)children.get(i)).reCalculateCode();
				}
			}
			private String formatString(){
				int level = getLevel();
				String ret = "";
				for(int i = 1; i < level; ++i){
					ret +="\t";
				}
				ret +=entity.getWbs().getIndex()+"; "+entity.getLevel()+"; "+entity.getName()+"; "+entity.getNumber()+"; "+entity.getLongNumber();
				if(this.getParent() != null){
					ret +=" ("+this.getParent().getEntity().getLongNumber()+") ";
				}
				return ret;
			}
			public String testPrintString(){
				String ret = formatString();
				for(int i = 0; i < children.size(); ++i){
					FDCTASKTreeNode child = (FDCTASKTreeNode)children.get(i);
					ret +="\n"+child.testPrintString();
				}
				return ret;
			}
			public boolean equals(Object o){
				if(o == null) {
					return false;
				}
				if(o instanceof FDCTASKTreeNode){
					FDCTASKTreeNode node = (FDCTASKTreeNode)o;
					return getEntity().getId().toString().equals(node.getEntity().getId().toString());
				}else{
					return false;
				}
			}
			public  int hashCode(){
				return this.getEntity().getId().toString().hashCode();
			}
		}
		private static void resetDisabledTask(List children){
			List disabledNode = new ArrayList();
			for(int i = 0; i < children.size(); ++i){
				FDCTASKTreeNode child = (FDCTASKTreeNode)children.get(i);			
				resetDisabledTask(child.getChildren());
				if(!child.getEntity().getWbs().isIsEnabled()){
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
		
		private static int getIndexInList(List nodeList, FDCTASKTreeNode node){
			String nodeId = node.getEntity().getId().toString();
			for(int i = 0; i < nodeList.size(); ++i){
				FDCTASKTreeNode temp = (FDCTASKTreeNode) nodeList.get(i);
				if(nodeId.equals(temp.getEntity().getId().toString())){
					return i;
				}else{
					return getIndexInList(temp.children,temp)+1;
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
			if(logger.isDebugEnabled()){
//				logger.debug("FDCTASKTree testPrint: ");
				System.out.println("FDCTASKTree testPrint: ");
				for(int i = 0; i < rootList.size(); ++i){
					FDCTASKTreeNode rootNode = (FDCTASKTreeNode)rootList.get(i);
					//logger.debug("\n"+rootNode.testPrintString());
//					logger.debug(rootNode.testPrintString());
					System.out.println(rootNode.testPrintString());
				}
			}
		}
}
