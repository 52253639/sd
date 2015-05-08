/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;

/**
 * output class name
 */
public class F7FDCWBSSelectUI extends AbstractF7FDCWBSSelectUI
{
    private static final String COL_ADMIN_PERSON = "adminPerson";
	private static final String COL_ADMIN_DEPT = "adminDept";
	private static final String COL_TYPE = "type";
	private static final String COL_LEVEL = "level";
	private static final String COL_WBS_NAME = "wbs.name";
	private static final String COL_WBS_NUMBER = "wbs.number";
	private static final String COL_PROJECT = "project";
	private static final String COL_IS_ENABLED = "isEnabled";
	private static final String COL_IS_SELECT = "isSelect";
	private static final Logger logger = CoreUIObject.getLogger(F7FDCWBSSelectUI.class);
    private Set selectedWBSIds = new HashSet();
    /**
     * output class constructor
     */
    public F7FDCWBSSelectUI() throws Exception {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()  {
        super.storeFields();
    }

    private boolean isCancel;
    
    public boolean isIsCancel(){
    	return isCancel;
    }
    public void setCancel(boolean isCancel){
    	this.isCancel = isCancel;
    }
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	KDWorkButton btnDelRow = new KDWorkButton("删除行",SCHClientHelper.ICON_REMOVELINE);
    	btnDelRow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int actRowIdx = tblBottom.getSelectManager().getActiveRowIndex();
				if(actRowIdx < 0) return;
				selectedWBSIds.remove(((FDCScheduleTaskInfo)tblBottom.getRow(actRowIdx).getUserObject()).
						getWbs().getId().toString());
				tblBottom.removeRow(actRowIdx);
			}
    	});
    	conBottom.addButton(btnDelRow);
    	btnOK.setEnabled(true);
    	btnCancel.setEnabled(true);
    	btnSelect.setEnabled(true);
    	tblTop.getStyleAttributes().setLocked(true);
    	tblBottom.getStyleAttributes().setLocked(true);
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	buildProjectTree();
    	setSelectComponent();
    	 //增加KDtreeView
		KDTreeView treeView=new KDTreeView();
		treeView.setTree(treeMain);
		treeView.setShowButton(true);
		treeView.setTitle("工程项目");
		mainSplitPanel.add(treeView,"left");
		treeView.setShowControlPanel(true);
    }

	private void setSelectComponent() {
		if(getUIContext().get("view") != null){
    		FilterInfo filter = ((EntityViewInfo)getUIContext().get("view")).getFilter();
    		if(filter != null){
    			String projectId = filter.getFilterItems().get(0).getCompareValue().toString();
    			String wbsId = filter.getFilterItems().get(1).getCompareValue().toString();
    			DefaultKingdeeTreeNode defaultNode = getNode((DefaultKingdeeTreeNode)treeMain.getModel().getRoot(), projectId);
    			treeMain.setSelectionNode(defaultNode);
//    			treeMain.setSelectionRow(((DefaultKingdeeTreeNode)treeMain.getModel().getRoot()).getIndex(defaultNode) + 1);
    			for(int i=0;i<tblTop.getRowCount();i++){
    				if(wbsId.equals(((FDCScheduleTaskInfo)tblTop.getRow(i).getUserObject()).getWbs().getId().toString())){
    					tblTop.getSelectManager().select(i, 0,i,8);
    				}
    			}
    		}
    	}
	}
    
    private DefaultKingdeeTreeNode getNode(DefaultKingdeeTreeNode root,String projectId){
    	Object obj = ((DefaultKingdeeTreeNode) root).getUserObject();
		if (!(obj instanceof CurProjectInfo)) {
			return null;
		}
		String prjId = ((CurProjectInfo)obj).getId().toString();
		if(projectId.equals(prjId)) return root;
		else{
			DefaultKingdeeTreeNode treeNode = null;
			int count = root.getChildCount();
			for (int i = 0; i < count; i++) {
				treeNode = (DefaultKingdeeTreeNode) root.getChildAt(i);
				obj = ((DefaultKingdeeTreeNode) treeNode).getUserObject();
				if (!(obj instanceof CurProjectInfo)) {
					continue;
				}
				prjId = ((CurProjectInfo) obj).getId().toString();
				if (projectId.equals(prjId)) {
					return treeNode;
				} else {
					DefaultKingdeeTreeNode value = getNode(treeNode, projectId);
					if (value != null) {
						return value;
					}
				}
			}
		}
		return null;
    }
    
//  树 
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) 
    		throws Exception   {
    	treeSelectChange();
    }

    private void treeSelectChange() throws BOSException, EASBizException{
		if(!isSelectLeafPrj()){
    		tblTop.removeRows();
    		return;
    	}
		fillTopTable();
	}
	protected boolean isSelectLeafPrj(){
		Object obj=getSelectLeafProject();
		if(obj!=null && obj instanceof CurProjectInfo &&((CurProjectInfo)obj).isIsLeaf()){
			return true;
		}
		return false;
	}
	
	protected CurProjectInfo getSelectLeafProject() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo.isIsLeaf() ? projectInfo : null;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			return null;
		}
		return null;
	}
	
	private void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		treeMain.setShowsRootHandles(true);
		//treeMain.setCollapsePathDisabled(false);对目标节点全展
	}

//    表格
    private void fillTopTable() throws BOSException{
    	tblTop.removeRows();
    	tblTop.checkParsed();
    	tblTop.getColumn(COL_IS_SELECT).getStyleAttributes().setLocked(false);
		DefaultKingdeeTreeNode projectNode  = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		CurProjectInfo project  = null;
		if(projectNode!=null && projectNode.getUserObject() instanceof CurProjectInfo){
			project = (CurProjectInfo) projectNode.getUserObject();
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			view.setFilter(new FilterInfo());
			view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.project.id",project.getId().toString()));
			view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",Boolean.TRUE));
			view.getFilter().getFilterItems().add(new FilterItemInfo("wbs.isEnabled",Boolean.TRUE));
			sic.add("*");
			sic.add("wbs.*");
			sic.add("wbs.adminPerson.id");
			sic.add("wbs.adminPerson.name");
			sic.add("wbs.adminPerson.number");
			sic.add("wbs.adminDept.id");
			sic.add("wbs.adminDept.name");
			sic.add("wbs.adminDept.number");
			sic.add("wbs.taskType.id");
			sic.add("wbs.taskType.name");
			sic.add("wbs.taskType.number");
			sic.add("wbs.curProject.id");
			sic.add("wbs.curProject.name");
			sic.add("wbs.curProject.number");
			view.setSelector(sic);
			view.setSorter(new SorterItemCollection());
			view.getSorter().add(new SorterItemInfo("wbs.longNumber"));
			FDCScheduleTaskCollection taskCol = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
			tblTop.getTreeColumn().setDepth(getMaxLevel(taskCol));
			for(int i=0;i<taskCol.size();i++){
				FDCScheduleTaskInfo taskInfo = taskCol.get(i);
				IRow row = tblTop.addRow();
				loadLineField(row, taskInfo);
			}
		}
    }
    
    private void loadLineField(IRow row,IObjectValue value){
//		wbs表格
    	boolean isTopTable = false;
		if(value != null && value instanceof FDCScheduleTaskInfo){
			FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) value;
			FDCWBSInfo wbsInfo = taskInfo.getWbs();
			if(row.getCell(COL_IS_SELECT) != null)	isTopTable = true;
			row.setUserObject(taskInfo);
			row.setTreeLevel(wbsInfo.getLevel());
			if(isTopTable){
				if(wbsInfo.isIsLeaf()){
					row.getStyleAttributes().setBackground(FDCColorConstants.totalColor);
				}else{
					row.getStyleAttributes().setBackground(FDCColorConstants.lockColor);
				}
				row.getCell(COL_IS_SELECT).setValue(Boolean.FALSE);
				row.getCell(COL_IS_ENABLED).setValue(Boolean.valueOf(wbsInfo.isIsEnabled()));
			}else{
				row.getCell(COL_PROJECT).setValue(wbsInfo.getCurProject());
				row.getStyleAttributes().setBackground(FDCColorConstants.totalColor);
			}
			row.getCell(COL_WBS_NUMBER).setValue(wbsInfo.getLongNumber().replace('!', '.'));
			row.getCell(COL_WBS_NAME).setValue(wbsInfo.getName());
			row.getCell(COL_LEVEL).setValue(new Integer(wbsInfo.getLevel()));
			row.getCell(COL_TYPE).setValue(wbsInfo.getTaskType());
			row.getCell(COL_ADMIN_DEPT).setValue(wbsInfo.getAdminDept());
			row.getCell(COL_ADMIN_PERSON).setValue(wbsInfo.getAdminPerson());
		}
    }
    
    private int getMaxLevel(FDCScheduleTaskCollection taskCol){
    	int maxLevel = 0;
    	for(int i=0;i<taskCol.size();i++){
    		if(maxLevel < taskCol.get(i).getLevel())	maxLevel = taskCol.get(i).getLevel();
    	}
    	return maxLevel;
    }
    private void addBottomRow(){
    	boolean isNoLeaf = false;
    	for(int i=0;i<tblTop.getRowCount();i++){
    		if(Boolean.TRUE.equals(tblTop.getRow(i).getCell(COL_IS_SELECT).getValue())){
    			FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) tblTop.getRow(i).getUserObject();
    			if(!selectedWBSIds.contains(taskInfo.getWbs().getId().toString())){
    				selectedWBSIds.add(taskInfo.getWbs().getId().toString());
    				if(!taskInfo.isIsLeaf()){
    					isNoLeaf = true;
    					continue;
    				}
    				IRow row = tblBottom.addRow();
    				loadLineField(row, taskInfo);
    			}
    		}
    	}
    	if(isNoLeaf) 	FDCMsgBox.showInfo("非明细项将不被带入！");
    }
//    事件
    public void actionOK_actionPerformed(ActionEvent e) throws Exception   {
    	getData();
    	disposeUIWindow();
    	setCancel(false);
    }

    public void actionSelect_actionPerformed(ActionEvent e) throws Exception   {
    	addBottomRow();
    }
    
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
    	destroyWindow();
    	setCancel(true);
    }
    public boolean destroyWindow() {
    	boolean b = super.destroyWindow();
    	return b;
    }
    public FDCScheduleTaskCollection getData() throws EASBizException, BOSException{
    	FDCScheduleTaskCollection taskCol = new FDCScheduleTaskCollection();
    	Map map = ContractAndTaskRelEntryFactory.getRemoteInstance().getTaskOtherData(selectedWBSIds);
    	Map fillBillMap = (Map) map.get("fillBill");
    	Map planWorkloadMap = (Map) map.get("planWorkload");
    	for(int i=0;i<tblBottom.getRowCount();i++){
    		FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) tblBottom.getRow(i).getUserObject();
    		taskInfo.put("fillBill", fillBillMap.get(taskInfo.getId().toString()));
    		taskInfo.put("planWorkLoad", planWorkloadMap.get(taskInfo.getId().toString()));
    		taskCol.add(taskInfo);
    	}
    	return taskCol;
    }
    
    public void initUIContentLayout()   {
    	 this.setBounds(new Rectangle(10, 10, 800, 600));
         this.setLayout(null);
         mainSplitPanel.setBounds(new Rectangle(10, 10, 780, 555));
         this.add(mainSplitPanel, null);
         btnOK.setBounds(new Rectangle(634, 574, 73, 21));
         this.add(btnOK, null);
         btnCancel.setBounds(new Rectangle(717, 574, 73, 21));
         this.add(btnCancel, null);
         //mainSplitPanel
         mainSplitPanel.add(tableSplitPanel, "right");
         mainSplitPanel.add(conTree, "left");
         //tableSplitPanel
         tableSplitPanel.add(pnlTop, "top");
         tableSplitPanel.add(conBottom, "bottom");
         //pnlTop
         pnlTop.setLayout(new KDLayout());
         //TODO 由于该容器采用KDLayout布局，请在下面一条语句中修正该容器的初始大小：
         pnlTop.putClientProperty("OriginalBounds", new Rectangle(0,0,530,280));        
         conTop.setBounds(new Rectangle(0, 0, 530, 250));
         pnlTop.add(conTop, new KDLayout.Constraints(0, 0, 530, 250, 
        		 KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
         btnSelect.setBounds(new Rectangle(250, 255, 70, 19));
         pnlTop.add(btnSelect, new KDLayout.Constraints(250, 255, 70, 19, 
        		 KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE ));
         //conTop
		 conTop.getContentPane().setLayout(new BorderLayout(0, 0));        
		 conTop.getContentPane().add(tblTop, BorderLayout.CENTER);
		         //conBottom
		 conBottom.getContentPane().setLayout(new BorderLayout(0, 0));        
		 conBottom.getContentPane().add(tblBottom, BorderLayout.CENTER);
		         //conTree
		 conTree.getContentPane().setLayout(new BorderLayout(0, 0));        
		 conTree.getContentPane().add(treeMain, BorderLayout.CENTER);

    }
}