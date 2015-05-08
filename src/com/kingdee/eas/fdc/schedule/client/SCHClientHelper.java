package com.kingdee.eas.fdc.schedule.client;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.Icon;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.org.client.f7.AdminF7;
import com.kingdee.eas.basedata.person.client.PersonF7UI;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFacadeFactory;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.EASResource;

public class SCHClientHelper {

	//图标常量
	/**  图标 － 附件管理 **/
	public static final Icon ICON_ATTACHEMENT = EASResource.getIcon("imgTbtn_affixmanage");
	
	/** 图标 － 新增行 */
	public static final Icon ICON_ADDLINE = EASResource.getIcon("imgTbtn_addline");
	
	/** 图标 － 插入行 */
	public static final Icon ICON_INSERTLINE = EASResource.getIcon("imgTbtn_insert");

	/** 图标 － 删除行 */
	public static final Icon ICON_REMOVELINE = EASResource.getIcon("imgTbtn_deleteline");
	
	/** 图标 － 全选 */
	public static final Icon ICON_SELECTALL = EASResource.getIcon("imgTbtn_selectall");
	
	/** 图标 － 全清 */
	public static final Icon ICON_DELETEALL = EASResource.getIcon("imgTbtn_deleteall");
	
	/** 图标 － 上传附件 */
	public static final Icon ICON_ADDFILE = EASResource.getIcon("imgTbtn_impress");
	
	/** 图标 － 下载附件 */
	public static final Icon ICON_DOWNFILE = EASResource.getIcon("imgTbtn_reload");
	
	/** 图标 － 删除附件 */
	public static final Icon ICON_DELFILE = EASResource.getIcon("imgTbtn_deletecollection");
	
	/** 图标 － 导入 */
	public static final Icon ICON_IMPORT = EASResource.getIcon("imgTbtn_input");
	
	/** 图标 － 左移 */
	public static final Icon ICON_FORWARD = EASResource.getIcon("imgTbtn_move_left");
	
	/** 图标 － 右移 */
	public static final Icon ICON_BACKWARD = EASResource.getIcon("imgTbtn_move_right");
	
	/** 图标 － 上移 */
	public static final Icon ICON_UP = EASResource.getIcon("imgTbtn_movetop");
	
	/** 图标 － 上移 */
	public static final Icon ICON_DOWN = EASResource.getIcon("imgTbtn_movedown");
	
	/** 图标 － 编号重算 */
	public static final Icon ICON_RECALNUM = EASResource.getIcon("imgTbtn_archivesreturn");
	
	public static final String FDCWBSQUERY = "com.kingdee.eas.fdc.schedule.app.F7FDCWBSQuery";
	
	public final static String GANTT_RESOURCE_NAME = "com.kingdee.eas.common.EASCommonResource";
	
	
	/**
	 * 设置人员
	 * 
	 * @param box
	 * @param owner
	 * @param cuId
	 */
	public static void setPersonF7(KDPromptBox box, String cuId, CoreUIObject ui) {
		HashMap param = new HashMap();
		//param.put(PersonF7UI.ADMIN_ID, cuId);
		if(cuId!=null){
			param.put(PersonF7UI.ADMIN_ID, cuId);
		}else{
			param.put(PersonF7UI.ALL_ADMIN, "YES");
		}
		
		((KDBizPromptBox)box).setHasCUDefaultFilter(false);
		PersonPromptBox pmt = new PersonPromptBox(ui, param);
		pmt.showAllChildren();
		box.setSelector(pmt);
		// 防止输入编码调出通用F7来选择，那样无法做到隔离
		((KDBizPromptBox) box).setQueryInfo(null);

	}
	/**
	 * 设置可多选的F7
	 * @param box
	 * @param cuId
	 * @param ui
	 */
	public static void setMultiPersonF7(KDPromptBox box, String cuId, CoreUIObject ui) {
		HashMap param = new HashMap();
		//param.put(PersonF7UI.ADMIN_ID, cuId);
		if(cuId!=null){
			param.put(PersonF7UI.ADMIN_ID, cuId);
		}else{
			param.put(PersonF7UI.ALL_ADMIN, "YES");
		}
		
		((KDBizPromptBox)box).setHasCUDefaultFilter(false);
		PersonPromptBox pmt = new PersonPromptBox(ui, param);
		pmt.showAllChildren();
		pmt.setIsSingleSelect(false);
		box.setSelector(pmt);
		// 防止输入编码调出通用F7来选择，那样无法做到隔离
		((KDBizPromptBox) box).setQueryInfo(null);

	}

	
	/**
	 * 设置责任部门F7,只能选择明细节点
	 * 
	 * @param bizPromptBox
	 * @param ui
	 */

	public static void setRespDeptF7(KDBizPromptBox bizPromptBox, CoreUIObject ui, String orgId) {
		bizPromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");

		EntityViewInfo view = new EntityViewInfo();

		SorterItemCollection sorc = view.getSorter();
		SorterItemInfo sort = new SorterItemInfo("number");
		sorc.add(sort);

		FilterInfo filter = new FilterInfo();

		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("isFreeze", new Integer(0)));
		fic.add(new FilterItemInfo("isSealUp", new Integer(0)));
		view.setFilter(filter);
		bizPromptBox.setEntityViewInfo(view);

		AdminF7 f7 = new AdminF7(ui);
		f7.showCheckBoxOfShowingAllOUs();
		f7.setRootUnitID(orgId);
		// f7.setIsCUFilter(true);
		// if (cuId != null)
		// f7.setCurrentCUID(cuId);

		bizPromptBox.setSelector(f7);
		SelectorItemCollection sic = bizPromptBox.getSelectorCollection();
		if (sic == null) {
			sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("number"));
			sic.add(new SelectorItemInfo("name"));
			bizPromptBox.setSelectorCollection(sic);
		}
		sic.add(new SelectorItemInfo("isLeaf"));
		sic.add(new SelectorItemInfo("displayName"));

		// 防止输入编码调出通用F7来选择，那样无法做到隔离
		bizPromptBox.setQueryInfo(null);
	}
	
	public static void setScheduleF7(){
		
	}
	
	/**
	 * 构建带专项的工程项目树
	 * @param ui
	 * @param treeMain
	 * @param actionOnLoad
	 * @throws Exception
	 */
	public void buildSpecialNodeTree(CoreUI ui, KDTree treeMain, ItemAction actionOnLoad) throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(ui, treeMain, actionOnLoad);
		treeMain.setShowsRootHandles(true);
		
		//遍历树，对每一个项目叶子节点，增加它的专项，主项节点
		addTaskNodes(treeMain);
    }
    
    /**
	 * 描述：获取项目节点下的所有专项任务节点，形成带专项任务的工程项目树
     * @throws BOSException 
     * @throws EASBizException 
	 */
	private void addTaskNodes(KDTree treeMain) throws EASBizException, BOSException {
		
		
		Set projectNodes = new HashSet();
		HashSet projectIds = new HashSet();
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
    	if(root!=null){
    		if(root.getUserObject() instanceof CurProjectInfo){
    			CurProjectInfo prj = (CurProjectInfo)root.getUserObject();
    			if (root.isLeaf()) {    				
    				projectNodes.add(root);
    				projectIds.add(prj.getId().toString());
    			}
    		}
    		//直接遍历树去到节点可以保证一致性
    		Enumeration childrens = root.depthFirstEnumeration();
    		while(childrens.hasMoreElements()){
    			DefaultKingdeeTreeNode childNode = (DefaultKingdeeTreeNode)childrens.nextElement();
    			if(childNode.getUserObject() instanceof CurProjectInfo){
    				CurProjectInfo prj=(CurProjectInfo)childNode.getUserObject();
    				if (childNode.isLeaf()) {
    					projectNodes.add(childNode);
    					projectIds.add(prj.getId().toString());
    				}
    			}
    		}
    	}
		
		//获取项目节点下的所有专项任务节点
		HashMap muTaskNodes  = FDCScheduleTaskFacadeFactory.getRemoteInstance().getMuTaskTreeNodes(projectIds);
		
		if (muTaskNodes == null || muTaskNodes.size() <= 0) {
			return;
		}
		//添加专项节点
		for (Iterator it = projectNodes.iterator(); it.hasNext();) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) it.next();
			node.setCustomIcon(EASResource.getIcon("imgTree_bigicon_end"));
			if (node.getUserObject() instanceof CurProjectInfo) {
				String projectId = ((CurProjectInfo) node.getUserObject()).getId().toString();
				ArrayList nodeList = (ArrayList) muTaskNodes.get(projectId);
				if (nodeList == null || nodeList.size() <= 0) {
					continue;
				}
				for (Iterator iterator = nodeList.iterator(); iterator.hasNext();) {
					DefaultKingdeeTreeNode spNode = (DefaultKingdeeTreeNode)iterator.next();
					node.add(spNode);
				}
			}
		}
	}

}
