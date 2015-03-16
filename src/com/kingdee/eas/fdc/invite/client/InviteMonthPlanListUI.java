/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgTree;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractBillListUI;
import com.kingdee.eas.fdc.contract.client.ContractBillReviseListUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.invite.InviteMonthPlanFactory;
import com.kingdee.eas.fdc.invite.InviteMonthPlanInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class InviteMonthPlanListUI extends AbstractInviteMonthPlanListUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteMonthPlanListUI.class);
    protected OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentCostUnit();
    protected static final String CANTEDIT = "cantEdit";
	protected static final String CANTREMOVE = "cantRemove";
    public InviteMonthPlanListUI() throws Exception
    {
        super();
    }
    protected String getEditUIName() {
		return InviteMonthPlanEditUI.class.getName();
	}
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return InviteMonthPlanFactory.getRemoteInstance();
	}
	public KDTable getBillListTable() {
		return this.tblMain;
	}
	protected void initWorkButton() {
		super.initWorkButton();
		this.actionAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_AUDIT);
		this.actionUnAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_UNAUDIT);
		
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);

		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		
		this.btnSet.setIcon(EASResource.getIcon("imgTbtn_duizsetting"));
	}
	protected void refresh(ActionEvent e) throws Exception{
		this.tblMain.removeRows();
	}
	
	private void checkCreateNewData(DefaultKingdeeTreeNode selectTreeNode) throws EASBizException, BOSException {
		if(selectTreeNode == null || !(selectTreeNode.getUserObject() instanceof CurProjectInfo) || !selectTreeNode.isLeaf()) {
			this.actionAddNew.setEnabled(false);
			return;
		}
		
		CurProjectInfo project = (CurProjectInfo) selectTreeNode.getUserObject();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id",project.getId().toString()));
		/*if(this.editData.getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
		}*/
		
		if(InviteMonthPlanFactory.getRemoteInstance().exists(filter)){
			this.actionAddNew.setEnabled(false);
			return;
		}
		
		this.actionAddNew.setEnabled(true);

	}
	
/*	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
//		filterItems.add(new FilterItemInfo("project.isEnabled", Boolean.TRUE));
		return filter;
	}*/
	
	protected FilterInfo getTreeSelectFilter(Object projectNode) throws Exception {
//		FilterInfo filter = getTreeSelectChangeFilter();
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		
		/*
		 * 工程项目树
		 */
		if (projectNode != null && projectNode instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			BOSUuid id = null;
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					id = ((OrgStructureInfo)projTreeNodeInfo).getUnit().getId();	
				}else{
					id = ((FullOrgUnitInfo)projTreeNodeInfo).getId();
				}				
				
				String orgUnitLongNumber = null;
				if(orgUnit!=null && id.toString().equals(orgUnit.getId().toString())){					
					orgUnitLongNumber = orgUnit.getLongNumber();
				}else{
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance()
					.getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}
				
				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(
						new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));

				f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
				f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs,CompareType.INCLUDE));
				
				f.setMaskString("#0 and #1 and #2");
				
				if(filter!=null){
					filter.mergeFilter(f,"and");
				}
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("project.id", idSet,
						CompareType.INCLUDE));
			}
		}

		FilterInfo typefilter =  new FilterInfo();
		FilterItemCollection typefilterItems = typefilter.getFilterItems();	
		
		/*
		//三方合同
		//R110412-116：合同管理-合同订立-合同修订，修订审批后，被修订的合同在“合同列表”里消失
		if(!(this instanceof ContractBillListUI) && !(this instanceof ContractBillReviseListUI)){
			typefilter.appendFilterItem("isAmtWithoutCost", String.valueOf(0));
		}*/
		
		if(filter!=null && typefilter!=null){
			filter.mergeFilter(typefilter,"and");
		}
		
		return filter;
	}
	
	
	protected void treeSelectChange() throws Exception {
		DefaultKingdeeTreeNode projectNode  = (DefaultKingdeeTreeNode) treeOrg.getLastSelectedPathComponent();
		
		Object project  = null;
		if(projectNode!=null){
			project = projectNode.getUserObject();
		}
		
		mainQuery.setFilter(getTreeSelectFilter(project));

		execQuery();
//		this.tblMain.removeRows(false);
	/*	if(isHasBillTable()) {
			getBillListTable().removeRows(false);
		}	
		*/
		if (getMainTable().getRowCount() > 0) {
			getMainTable().getSelectManager().select(0, 0);
			btnAddNew.setEnabled(true);
		}
	}
	
	protected void treeOrg_valueChanged(TreeSelectionEvent e) throws Exception {
		
		if (checkCanOperate()) {
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionAudit.setEnabled(true);
			this.actionUnAudit.setEnabled(true);
		} else {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}
		treeSelectChange();
		this.checkCreateNewData((DefaultKingdeeTreeNode) treeOrg.getLastSelectedPathComponent());
	}
	protected FilterInfo getOrgFilter() throws Exception {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode orgNode = getSelectedTreeNode(treeOrg);
		if (orgNode != null&& orgNode.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo org = (OrgStructureInfo) orgNode.getUserObject();
			String orgId = org.getUnit().getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgId));
		}
		return filter;
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		FilterInfo treeFilter = viewInfo.getFilter();
		FilterInfo filter = null;
		try {
			filter = this.getOrgFilter();
			if (this.getDialog() != null) {
				FilterInfo commFilter = this.getDialog().getCommonFilter();
				if (filter != null && commFilter != null)
					filter.mergeFilter(commFilter, "and");
			} else {
				QuerySolutionInfo querySolution = this.getQuerySolutionInfo();
				if (querySolution != null
						&& querySolution.getEntityViewInfo() != null) {
					EntityViewInfo ev = Util.getInnerFilterInfo(querySolution);
					if (ev.getFilter() != null) {
						filter.mergeFilter(ev.getFilter(), "and");
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			treeFilter.mergeFilter(filter, "and");
		} catch (BOSException e) {
			e.printStackTrace();
		}
		viewInfo.setFilter(treeFilter);
		if(viewInfo.getSorter()!=null&&viewInfo.getSorter().size()<2){
			viewInfo.getSorter().clear();
			viewInfo.getSorter().add(new SorterItemInfo("project.number"));
			viewInfo.getSorter().add(new SorterItemInfo("isLatest"));
			SorterItemInfo version=new SorterItemInfo("version");
			version.setSortType(SortType.DESCEND);
			viewInfo.getSorter().add(version);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree.getLastSelectedPathComponent();
			return treeNode;
		}
		return null;
	}
	protected boolean checkCanOperate() {
		boolean flag = false;
		DefaultKingdeeTreeNode orgNode = getSelectedTreeNode(treeOrg);
		if (orgNode == null) {
			flag = false;
		}
		/*OrgStructureInfo org = (OrgStructureInfo) orgNode.getUserObject();
		String orgId = org.getUnit().getId().toString();
		if (currentOrg.getId().toString().equals(orgId)) {
			flag = true;
		} else {
			flag = false;
		}*/
		if(authorizedOrgs.contains(currentOrg.getId().toString())) {
			flag = true;
		}
		return flag;
	}
	public void onLoad() throws Exception {
		if (currentOrg == null || !currentOrg.isIsCompanyOrgUnit()) {
			MsgBox.showInfo("非财务组织不能进入!");
			this.abort();
		}
		super.onLoad();
	
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		buildOrgTree();
		DefaultKingdeeTreeNode orgRoot = (DefaultKingdeeTreeNode) ((TreeModel) this.treeOrg.getModel()).getRoot();
		DefaultKingdeeTreeNode node = this.findNode(orgRoot, this.currentOrg.getId().toString());
		this.removeAllBrotherNode(node);
		this.treeOrg.setSelectionNode(node);
		this.treeOrg.expandAllNodes(true, orgRoot);
		
		this.tblMain.setColumnMoveable(true);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.setAutoscrolls(true);
		
		tHelper = new TablePreferencesHelper(this);
		
	    tblMain.getColumn("version").getStyleAttributes().setNumberFormat("0.0");
	}

	public void removeAllBrotherNode(DefaultKingdeeTreeNode node) {
		DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode) node.getParent();
		if (parent == null) {
			return;
		}
		this.treeOrg.removeAllChildrenFromParent(parent);
		this.treeOrg.addNodeInto(node, parent);
		this.removeAllBrotherNode(parent);
	}
	protected void setActionState() {

	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		DefaultKingdeeTreeNode orgNode = getSelectedTreeNode(treeOrg);
		
		if(orgNode!=null&&orgNode.getUserObject()!=null){
			if(orgNode.getUserObject() instanceof OrgStructureInfo ) {
				uiContext.put("org", ((OrgStructureInfo) orgNode.getUserObject()).getUnit());
			}else if( orgNode.getUserObject() instanceof CurProjectInfo ) {
				uiContext.put("curProject", ((CurProjectInfo) orgNode.getUserObject()) );
			}
			
		}else{
			uiContext.put("org", null);
		}
	}
	/*protected void buildOrgTree() throws Exception {
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,"", SysContext.getSysContext().getCurrentFIUnit().getId().toString(), null, FDCHelper.getActionPK(this.actionOnLoad));
		this.treeOrg.setModel(orgTreeModel);
	}*/
	
	//获取有权限的组织
	protected Set authorizedOrgs = null;
	
	public void buildOrgTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, this.treeOrg, actionOnLoad);
		
		authorizedOrgs = (Set)ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
		if(authorizedOrgs==null){
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),OrgType.CostCenter,  null,  null, null);
			if(orgs!=null){
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while(it.hasNext()){
					authorizedOrgs.add(it.next());
				}
			}		
		}
		
	}
	
	private DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode node,
			String id) {
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			if (projectInfo.getId().toString().equals(id)) {
				return node;
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			FullOrgUnitInfo info = oui.getUnit();
			if (info.getId().toString().equals(id)) {
				return node;
			}
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode findNode = findNode(
					(DefaultKingdeeTreeNode) node.getChildAt(i), id);
			if (findNode != null) {
				return findNode;
			}
		}
		return null;
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
	protected void checkBeforeEditOrRemove(String warning,String id) throws EASBizException, BOSException, Exception {
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");
		
		FDCBillInfo info=(FDCBillInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels);
		
		FDCBillStateEnum state = info.getState();
		
		if (state != null&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED)) {
			if(warning.equals(CANTEDIT)){
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
				SysUtil.abort();
			}else{
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
				SysUtil.abort();
			}
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = getSelectedKeyValue();
    	checkBeforeEditOrRemove(CANTEDIT,id);
    	
		super.actionEdit_actionPerformed(e);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
	    	checkBeforeEditOrRemove(CANTREMOVE,id.get(i).toString());
		}
		super.actionRemove_actionPerformed(e);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			if (!FDCBillStateEnum.SUBMITTED.equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			((IFDCBill)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
			if (!FDCBillStateEnum.AUDITTED.equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			((IFDCBill)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public FDCBillStateEnum getBizState(String id) throws EASBizException, BOSException, Exception{
    	if(id==null) return null;
    	SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("state");
    	return ((FDCBillInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels)).getState();
    }
	public void actionSet_actionPerformed(ActionEvent e) throws Exception {
		InviteMonthPlanInfo info=getSelectedInfo();
		checkAudited(info);
		checkLastVersion(info);
		checkHasNextMonth(info);
		UIContext uiContext = new UIContext(this);
		uiContext.put("info", info);
		uiContext.put("set", Boolean.TRUE);
		IUIWindow ui = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null,	OprtState.ADDNEW);
		ui.show();
	}
	private void checkAudited(InviteMonthPlanInfo info) throws BOSException, EASBizException {
		if (!FDCUtils.isBillAudited(info)) {
			MsgBox.showWarning(this, "非审批单据不能修订！");
	        SysUtil.abort();
		}
	}
	private void checkLastVersion(InviteMonthPlanInfo info) throws BOSException, EASBizException {
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id",info.getProject().getId().toString()));
//		filter.getFilterItems().add(new FilterItemInfo("planYear",info.getPlanYear()));
//		filter.getFilterItems().add(new FilterItemInfo("planMonth",info.getPlanMonth()));
		filter.getFilterItems().add(new FilterItemInfo("version",info.getVersion(),CompareType.GREATER));
		if(InviteMonthPlanFactory.getRemoteInstance().exists(filter)){
			MsgBox.showWarning(this, "非最新版本不能修订！");
	        SysUtil.abort();
		}
	}
	private void checkHasNextMonth(InviteMonthPlanInfo info) throws BOSException, EASBizException {
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id",info.getProject().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("planYear",info.getPlanYear(),CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("planMonth",info.getPlanMonth(),CompareType.GREATER));
		if(InviteMonthPlanFactory.getRemoteInstance().exists(filter)){
			MsgBox.showWarning(this, "已存在新月份计划不能修订！");
	        SysUtil.abort();
		}
	}
	
	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeOrg.getLastSelectedPathComponent();
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());

		super.actionAddNew_actionPerformed(e);
	}
	
	private InviteMonthPlanInfo getSelectedInfo() throws BOSException, EASBizException {
		checkSelected();
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("*");
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("programming.*");
    	sel.add("measureStage.*");
    	sel.add("creator.*");
    	sel.add("auditor.*");
    	sel.add("project.*");
    	sel.add("entry.*");
    	sel.add("entry.requiredInviteForm.*");
		return InviteMonthPlanFactory.getRemoteInstance().getInviteMonthPlanInfo(new ObjectUuidPK(getSelectedKeyValue()), sel);
	}
}