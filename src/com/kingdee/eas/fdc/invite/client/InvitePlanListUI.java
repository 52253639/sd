/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.Action;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
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
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.contract.programming.IProgramming;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.fdc.invite.IInvitePlan;
import com.kingdee.eas.fdc.invite.InvitePlanFactory;
import com.kingdee.eas.fdc.invite.InvitePlanInfo;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteStateEnum;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class InvitePlanListUI extends AbstractInvitePlanListUI
{
	protected OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentCostUnit();
    protected static final String CANTEDIT = "cantEdit";
	protected static final String CANTREMOVE = "cantRemove";
	public InvitePlanListUI() throws Exception {
		super();
	}
	protected String getEditUIName() {
		return InvitePlanEditUI.class.getName();
	}
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return InvitePlanFactory.getRemoteInstance();
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
		refresh(null);
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
		viewInfo.setFilter(filter);
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
		OrgStructureInfo org = (OrgStructureInfo) orgNode.getUserObject();
		String orgId = org.getUnit().getId().toString();
		if (currentOrg.getId().toString().equals(orgId)) {
			flag = true;
		} else {
			flag = false;
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
			uiContext.put("org", ((OrgStructureInfo) orgNode.getUserObject()).getUnit());
		}else{
			uiContext.put("org", null);
		}
	}
	protected void buildOrgTree() throws Exception {
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,"", SysContext.getSysContext().getCurrentFIUnit().getId().toString(), null, FDCHelper.getActionPK(this.actionOnLoad));
		this.treeOrg.setModel(orgTreeModel);
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
		InvitePlanInfo info=getSelectedInfo();
		checkAudited(info);
		checkLastVersion(info);
		UIContext uiContext = new UIContext(this);
		uiContext.put("info", info);
		IUIWindow ui = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null,	OprtState.ADDNEW);
		ui.show();
	}
	private void checkAudited(InvitePlanInfo info) throws BOSException, EASBizException {
		if (!FDCUtils.isBillAudited(info)) {
			MsgBox.showWarning(this, "非审批单据不能修订！");
	        SysUtil.abort();
		}
	}
	private void checkLastVersion(InvitePlanInfo info) throws BOSException, EASBizException {
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id",info.getProject().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("version",info.getVersion(),CompareType.GREATER));
		if(InvitePlanFactory.getRemoteInstance().exists(filter)){
			MsgBox.showWarning(this, "非最新版本不能修订！");
	        SysUtil.abort();
		}
	}
	private InvitePlanInfo getSelectedInfo() throws BOSException, EASBizException {
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
		return InvitePlanFactory.getRemoteInstance().getInvitePlanInfo(new ObjectUuidPK(getSelectedKeyValue()), sel);
	}
}