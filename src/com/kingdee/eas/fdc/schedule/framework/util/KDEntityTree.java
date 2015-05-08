package com.kingdee.eas.fdc.schedule.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
/**
 * 
 * 描述：构建树形数据结构
 * 修改人：zhiqiao_yang <p>
 * 修改时间：2010-8-23 <p>
 */
public class KDEntityTree {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.framework.util.KDEntityTree");
	private List roots;
	private ITreeNodeAdaptor adaptor;
	private Map id2TreeNode;
	public KDEntityTree(AbstractObjectCollection col, ITreeNodeAdaptor adaptor) {
		id2TreeNode = new HashMap();
		roots = new ArrayList();
		this.adaptor = adaptor;
		if(adaptor == null){
			this.adaptor = new ITreeNodeAdaptor(){
				public String getId(IObjectValue obj) {					
					return obj.get("id").toString();
				}
				public String getParentId(IObjectValue obj) {
					Object parent = obj.get("parent");
					if(parent != null){
						return ((IObjectValue)parent).get("id").toString();
					}
					return null;
				}				
			};
		}
		buildTree(col);
	}
	
	public List getRoots(){
		return roots;
	}
	public KDEntityTreeNode getNodeById(String id){
		return (KDEntityTreeNode) id2TreeNode.get(id);
	}
	
	private void buildTree(AbstractObjectCollection col) {
		IObjectValue entity = null;
		for(int i = 0; i < col.size(); ++i){
			entity = col.getObject(i);
			String id = adaptor.getId(entity);
			id2TreeNode.put(id, new KDEntityTreeNode(entity));			
		}
		KDEntityTreeNode node = null;
		for(int i = 0; i < col.size(); ++i){
			entity = col.getObject(i);
			String id = adaptor.getId(entity);
			String parentId = adaptor.getParentId(entity);	
			node = (KDEntityTreeNode) id2TreeNode.get(id);
			KDEntityTreeNode parentNode = (KDEntityTreeNode) id2TreeNode.get(parentId);
			if(parentNode == null){
				roots.add(node);
			}else{
				node.setParent(parentNode);
				parentNode.getChildren().add(node);
			}
			if(parentId != null && parentNode == null){
				logger.error("Init KDEntityTree error: parentId = "+parentId+"; id = "+id);
			}
		}
	}


	class KDEntityTreeNode{
		private final IObjectValue userObject;
		private KDEntityTreeNode parent;
		private List children;
		public KDEntityTreeNode(IObjectValue userObject){
			this.userObject = userObject;
			children = new ArrayList();
		}
		public KDEntityTreeNode getParent() {
			return parent;
		}
		public void setParent(KDEntityTreeNode parent) {
			this.parent = parent;
		}
		public List getChildren() {
			return children;
		}
		public IObjectValue getUserObject() {
			return userObject;
		}
		public boolean isParentId(String parentId){
			if(parent == null) return false;			
			return adaptor.getId(parent.getUserObject()).equals(parentId) ? true : parent.isParentId(parentId);
		}
		public String getId(){
			return adaptor.getId(userObject);
		}
		public int hashCode(){
			return adaptor.getId(userObject).hashCode();
		}
		public boolean equals(Object obj){
			if(obj instanceof KDEntityTreeNode){
				KDEntityTreeNode node = (KDEntityTreeNode) obj;
				if(adaptor.getId(userObject).equals(adaptor.getId(node.getUserObject()))){
					return true;
				}
			}
			return false;
		}
		
	}
	public static interface ITreeNodeAdaptor{
		String getId(IObjectValue obj);
		String getParentId(IObjectValue obj);
	}

}
