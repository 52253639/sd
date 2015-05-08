/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustReqBillFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ScheduleAdjustReqBillListUI extends AbstractScheduleAdjustReqBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ScheduleAdjustReqBillListUI.class);
    
    /**
     * output class constructor
     */
    public ScheduleAdjustReqBillListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output treeProject_valueChanged method
     */
    protected void treeProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeProject_valueChanged(e);
    }
    
    protected FilterInfo getTreeFilter() throws Exception {
    	//做工程项目隔离及部门隔离
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeProject.getLastSelectedPathComponent();
    	Set prjIdSet=getProjectLeafsOfNode(node);
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("project.id",prjIdSet,CompareType.INCLUDE));
		return filter;
    }
    
    public void onLoad() throws Exception {
    	//启用甘特图的调整申请模式
		boolean adjustByGrant = FDCUtils.getDefaultFDCParamByKey(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString(), FDCConstants.FDC_PARAM_ADJUSTBYGRANT);
		if(adjustByGrant){
			MsgBox.showWarning("已启用甘特图的调整申请模式，此功能不可用！");
			this.abort();
		}
    	super.onLoad();
    	treeProject.setSelectionRow(0);
    	
    	 //增加KDtreeView
		KDTreeView treeView=new KDTreeView();
		treeView.setTree(treeProject);
		treeView.setShowButton(true);
		treeView.setTitle("工程项目");
        kDSplitPane1.add(treeView, "left");
		treeView.setShowControlPanel(true);
    }
    public void onShow() throws Exception {
    	super.onShow();
    } 
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	super.prepareUIContext(uiContext, e);
    }
    protected ICoreBase getBizInterface() throws Exception {
    	return ScheduleAdjustReqBillFactory.getRemoteInstance();
    }
    protected String getEditUIName() {
    	return ScheduleAdjustReqBillEditUI.class.getName();
    }
    
    


	protected ICoreBase getRemoteInterface() throws BOSException {
		return ScheduleAdjustReqBillFactory.getRemoteInstance();
	}

	protected void audit(List ids) throws Exception {
		ScheduleAdjustReqBillFactory.getRemoteInstance().audit(ids);
	}
	protected void unAudit(List ids) throws Exception {
		ScheduleAdjustReqBillFactory.getRemoteInstance().unAudit(ids);
	}

	/**
     * 获取选择节点的所有下级明细工程项目
     * @return
     */
    protected Set getProjectLeafsOfNode(DefaultKingdeeTreeNode node){
    	Set idSet=new HashSet();
    	if(node!=null&&node.getUserObject() instanceof CurProjectInfo){
    		CurProjectInfo prj = (CurProjectInfo)node.getUserObject();
			if(prj.isIsLeaf()){
    			idSet.add(prj.getId().toString());
    			return idSet;
    		}
    	}
    	//直接遍历树去到节点可以保证一致性
    	Enumeration childrens = node.depthFirstEnumeration();
    	while(childrens.hasMoreElements()){
    		DefaultKingdeeTreeNode childNode = (DefaultKingdeeTreeNode)childrens.nextElement();
    		if(childNode.getUserObject() instanceof CurProjectInfo){
    			CurProjectInfo prj=(CurProjectInfo)childNode.getUserObject();
    			if(prj.isIsLeaf()){
    				idSet.add(prj.getId().toString());
    			}
    		}
    	}
    	if(idSet.size()==0){
    		//如果选择的节点下没有任何明细的工程项目节点则随便加个节点保证过滤不出来任何数据
    		idSet.add(OrgConstants.DEF_CU_ID);
    	}
    	return idSet;
    }
    
	protected void updateButtonStatus() {
		this.actionAddNew.setEnabled(true);
		this.actionEdit.setEnabled(true);
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(false);
		this.actionUnAudit.setVisible(false);
	}
	
	public void buildProjectTree() throws Exception {
		OrgUnitInfo currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		try{
			//用财务组织构造树保证下级组织能够看到其他的项目
			SysContext.getSysContext().setCurrentOrgUnit(SysContext.getSysContext().getCurrentFIUnit());
			ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

			projectTreeBuilder.build(this, treeProject, actionOnLoad);
		} finally {
			SysContext.getSysContext().setCurrentOrgUnit(currentOrgUnit);
		}
		treeProject.setShowsRootHandles(true);
	}
}