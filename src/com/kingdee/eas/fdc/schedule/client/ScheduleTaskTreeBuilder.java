package com.kingdee.eas.fdc.schedule.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSCollection;
import com.kingdee.eas.fdc.schedule.FDCWBSFactory;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerFactory;
import com.kingdee.eas.framework.client.CoreUI;

public class ScheduleTaskTreeBuilder{
	
	/**
	 * 构造总进度计划树
	 * @param ui
	 * @param tree
	 * @param prjId
	 * @throws EASBizException
	 * @throws BOSException
	 * @author xiaohong_shi
	 */
	public static void build(CoreUI ui, KDTree tree,String prjId) throws EASBizException, BOSException{
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("curProject.id");
		view.getSelector().add("curProject.name");
		view.getSelector().add("number");
		view.getSelector().add("name");
		view.getSelector().add("isLeaf");
		view.getSelector().add("parent.id");
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("isEnabled", Boolean.TRUE);
		view.getFilter().appendFilterItem("curProject.id", prjId);
		view.getSorter().add(new SorterItemInfo("longNumber"));
//		FDCScheduleInfo info=ScheduleVerManagerFactory.getRemoteInstance().getNewVerData(prjId);
		FDCWBSCollection wbsColl = FDCWBSFactory.getRemoteInstance().getFDCWBSCollection(view);
		CurProjectInfo prj=null;
		if(wbsColl.size()==0){
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("id");
			selector.add("name");
			prj=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(prjId),selector);
		}else{
			prj=wbsColl.get(0).getCurProject();
		}
		//CurProject as root 
		DefaultKingdeeTreeNode root=new DefaultKingdeeTreeNode();
		root.setText(prj.getName());
		root.setUserObject(prj);
		
		KingdeeTreeModel model=new KingdeeTreeModel(root);
		Map wbsTreeNodeMap=new HashMap();//使用一个Map来辅助构造树
		for(Iterator iter=wbsColl.iterator();iter.hasNext();){
			FDCWBSInfo wbsInfo=(FDCWBSInfo)iter.next();
			DefaultKingdeeTreeNode node=new DefaultKingdeeTreeNode();
			node.setText(wbsInfo.getName());
			node.setUserObject(wbsInfo);
			
			DefaultKingdeeTreeNode parent=null;
			if(wbsInfo.getParent()==null){
				parent=root;
			}else{
				parent=(DefaultKingdeeTreeNode)wbsTreeNodeMap.get(wbsInfo.getParent().getId());
				if(parent==null){
					System.out.println("build tree error");
					parent=root;
				}
			}
			wbsTreeNodeMap.put(wbsInfo.getId(), node);
			model.insertNodeInto(node, parent, parent.getChildCount());
		}
		tree.setModel(model);
		wbsTreeNodeMap.clear();
	}
	
	/**
	 * 构造总进度计划树
	 * @param ui
	 * @param tree
	 * @param prjId
	 * @throws EASBizException
	 * @throws BOSException
	 * @author xiaohong_shi
	 */
	public static void buildTaskTree(CoreUI ui, KDTree tree,String prjId) throws EASBizException, BOSException{
		FDCScheduleInfo info=ScheduleVerManagerFactory.getRemoteInstance().getNewVerData(prjId);
		//CurProject as root 
		DefaultKingdeeTreeNode root=new DefaultKingdeeTreeNode();
		root.setText(info.getProject().getName());
		root.setUserObject(info.getProject());
		
		KingdeeTreeModel model=new KingdeeTreeModel(root);
		Map taskTreeNodeMap=new HashMap();//使用一个Map来辅助构造树
		for(Iterator iter=info.getTaskEntrys().iterator();iter.hasNext();){
			FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)iter.next();
			DefaultKingdeeTreeNode node=new DefaultKingdeeTreeNode();
			node.setText(task.getName());
			node.setUserObject(task);
			
			DefaultKingdeeTreeNode parent=null;
			if(task.getParent()==null){
				parent=root;
			}else{
				parent=(DefaultKingdeeTreeNode)taskTreeNodeMap.get(task.getParent().getWbs().getId());
				if(parent==null){
					System.out.println("build tree error");
					parent=root;
				}
			}
			taskTreeNodeMap.put(task.getWbs().getId(), node);
			model.insertNodeInto(node, parent, parent.getChildCount());
		}
		tree.setModel(model);
		taskTreeNodeMap.clear();
	}
	
	/**
	 * 构造单进度计划树
	 * @param ui
	 * @param tree
	 * @param prjId
	 * @param scheduleId
	 * @throws EASBizException
	 * @throws BOSException
	 * @author xiaohong_shi
	 */
	public static void build(CoreUI ui, KDTree tree,String prjId,String scheduleId) throws EASBizException, BOSException{
		EntityViewInfo view=new EntityViewInfo();
    	view.getSelector().add("*");
    	view.getSelector().add("schedule.project.id");
    	view.getSelector().add("schedule.project.name");
    	view.getSelector().add("wbs.id");
    	view.getSelector().add("wbs.number");
    	view.getSelector().add("wbs.name");
    	view.getSorter().add(new SorterItemInfo("wbs.index"));
    	view.getSorter().add(new SorterItemInfo("wbs.longNumber"));
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("schedule.id", scheduleId);
		FDCScheduleTaskCollection tasks=FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
		CurProjectInfo prj=null;
		if(tasks.size()==0){
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("id");
			selector.add("name");
			prj=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(prjId),selector);
		}else{
			prj=tasks.get(0).getSchedule().getProject();
		}
		//CurProject as root 
		DefaultKingdeeTreeNode root=new DefaultKingdeeTreeNode();
		root.setText(prj.getName());
		root.setUserObject(prj);
		
		KingdeeTreeModel model=new KingdeeTreeModel(root);
		Map taskTreeNodeMap=new HashMap();//使用一个Map来辅助构造树
		for(Iterator iter=tasks.iterator();iter.hasNext();){
			FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)iter.next();
			DefaultKingdeeTreeNode node=new DefaultKingdeeTreeNode();
			node.setText(task.getName());
			node.setUserObject(task);
			
			DefaultKingdeeTreeNode parent=null;
			if(task.getParent()==null){
				parent=root;
			}else{
				parent=(DefaultKingdeeTreeNode)taskTreeNodeMap.get(task.getParent().getId());
				if(parent==null){
					System.out.println("build tree error");
					parent=root;
				}
			}
			taskTreeNodeMap.put(task.getId(), node);
			model.insertNodeInto(node, parent, parent.getChildCount());
		}
		tree.setModel(model);
		taskTreeNodeMap.clear();
	}
}
