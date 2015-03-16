/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.IProjectMonthPlanPro;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ProjectMonthPlanProListUI extends AbstractProjectMonthPlanProListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectMonthPlanProListUI.class);
    
    //获取有权限的组织
	protected Set authorizedOrgs = null;
	protected static final String CANTEDIT = "cantEdit";
	protected static final String CANTREMOVE = "cantRemove";
    public ProjectMonthPlanProListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	this.actionQuery.setEnabled(false);
    	super.onLoad();
    	buildProjectTree();
    	this.tblMain.getColumn("version").getStyleAttributes().setNumberFormat("#,##0.0;-#,##0.0");
    	this.tblMain.getColumn("bizDate").getStyleAttributes().setNumberFormat("yyyy-MM");
    	this.actionQuery.setEnabled(true);
    	this.actionQuery.setVisible(true);
    	this.actionLocate.setVisible(true);
    	this.actionLocate.setEnabled(true);
    	this.btnLocate.setEnabled(true);
		this.menuBiz.setVisible(false);
    }
    protected String[] getLocateNames()
    {
        String[] locateNames = new String[3];
        locateNames[0] = "bizDate";
        locateNames[1] = "number";
        locateNames[2] = "name";
        return locateNames;
    }
    protected void initWorkButton() {
		super.initWorkButton();
		this.actionAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_AUDIT);
		this.actionUnAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_UNAUDIT);
		this.actionModify.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_duizsetting"));
		
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);

		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
	}
    protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
    protected void refresh(ActionEvent e) throws Exception
	{
		this.tblMain.removeRows();
	}
    protected String getEditUIName(){
        return ProjectMonthPlanProEditUI.class.getName();
    }
    protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node  = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		if (node != null) {
			Object obj = node.getUserObject();
			if (obj instanceof CurProjectInfo) {
				uiContext.put("curProject", obj);
			}
		}
		super.prepareUIContext(uiContext, e);
	}
    public void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

		projectTreeBuilder.build(this, treeProject, actionOnLoad);
		
		authorizedOrgs = (Set)ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
		if(authorizedOrgs==null){
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
			            OrgType.CostCenter, 
			            null,  null, null);
			if(orgs!=null){
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while(it.hasNext()){
					authorizedOrgs.add(it.next());
				}
			}		
		}
		if (treeProject.getRowCount() > 0) {
			treeProject.setSelectionRow(0);
			treeProject.expandPath(treeProject.getSelectionPath());
		}
	}
    protected void treeProject_valueChanged(TreeSelectionEvent e) throws Exception {
    	treeSelectChange();
    	this.refresh(null);
    }
    protected void treeSelectChange() throws Exception {
		DefaultKingdeeTreeNode projectNode  = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		Object project  = null;
		if(projectNode!=null) {
			project = projectNode.getUserObject();
			if (project instanceof CurProjectInfo) {
				actionAddNew.setEnabled(true);
				kDContainer1.setTitle(((CurProjectInfo) project).getDisplayName());
			} else {
				actionAddNew.setEnabled(false);
				kDContainer1.setTitle(null);
			}
		}
    }
    protected FilterInfo getTreeSelectFilter(Object projectNode) throws Exception {
		FilterInfo filter = new FilterInfo();
		if (projectNode != null&& projectNode instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			BOSUuid id = null;
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
				f.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));
				f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
				f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs,CompareType.INCLUDE));
				
				f.setMaskString("#0 and #1 and #2");
				
				if(filter!=null){
					filter.mergeFilter(f,"and");
				}
			}else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filter.getFilterItems().add(new FilterItemInfo("curProject.id", idSet,CompareType.INCLUDE));
			}
		}
		return filter;
	}
    protected ICoreBase getBizInterface() throws Exception {
		return ProjectMonthPlanProFactory.getRemoteInstance();
	}
    protected void checkBeforeEditOrRemove(String warning,String id) throws EASBizException, BOSException, Exception {
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");
		ProjectMonthPlanProInfo info=(ProjectMonthPlanProInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels);
		FDCBillStateEnum state = info.getState();
		
		if (state != null
				&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED)) {
			if(warning.equals(CANTEDIT)){
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
				SysUtil.abort();
			}else{
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
				SysUtil.abort();
			}
		}
	}
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		DefaultKingdeeTreeNode node  = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		FDCClientUtils.checkSelectProj(this, node);
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select FID ,FIsEnabled from  T_FDC_PayPlanCycle where FIsEnabled = 1");
		IRowSet rowSet = builder.executeQuery();
		if (rowSet == null || rowSet.size() == 0) {
			FDCMsgBox.showWarning(this,"没有付款计划周期或没有启用付款计划周期，不能新增！");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	
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
			
			SelectorItemCollection sels = super.getSelectors();
			sels.add("state");
			ProjectMonthPlanProInfo info=(ProjectMonthPlanProInfo)getBizInterface().getValue(new ObjectUuidPK(id.get(i).toString()),sels);
			if (!FDCBillStateEnum.SUBMITTED.equals(info.getState())) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			((IProjectMonthPlanPro)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			SelectorItemCollection sels = super.getSelectors();
			sels.add("state");
			ProjectMonthPlanProInfo info=(ProjectMonthPlanProInfo)getBizInterface().getValue(new ObjectUuidPK(id.get(i).toString()),sels);
			if (!FDCBillStateEnum.AUDITTED.equals(info.getState())) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			((IProjectMonthPlanPro)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionModify_actionPerformed(ActionEvent e) throws Exception {
		ProjectMonthPlanProInfo info=getSelectedInfo();
//		if(SysContext.getSysContext().getCurrentUserInfo().getPerson()!=null){
//			if(!info.getRespPerson().getId().toString().equals(SysContext.getSysContext().getCurrentUserInfo().getPerson().getId().toString())){
//				FDCMsgBox.showWarning("责任人非本人，禁止操作！");
//				SysUtil.abort();
//			}
//		}
		checkAudited(info);
		checkLastVersion(info);
		UIContext uiContext = new UIContext(this);
		uiContext.put("info", info);
		IUIWindow ui = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null,	OprtState.ADDNEW);
		ui.show();
	}
	private void checkAudited(ProjectMonthPlanProInfo info) throws BOSException, EASBizException {
		if (!FDCUtils.isBillAudited(info)) {
			MsgBox.showWarning(this, "非审批单据不能修订！");
	        SysUtil.abort();
		}
	}
	private void checkLastVersion(ProjectMonthPlanProInfo info) throws BOSException, EASBizException {
		if(!info.isIsLatest()){
			MsgBox.showWarning(this, "非最新版本不能修订！");
	        SysUtil.abort();
		}
	}
	private ProjectMonthPlanProInfo getSelectedInfo() throws BOSException, EASBizException {
		checkSelected();
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("*");
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("creator.*");
    	sel.add("auditor.*");
    	sel.add("curProject.*");
    	sel.add("entry.*");
    	sel.add("entry.contractBill.*");
    	sel.add("entry.programmingContract.*");
    	sel.add("entry.dateEntry.*");
    	sel.add("entry.dateEntry.bgItem.*");
    	sel.add("respDept.*");
    	sel.add("respPerson.*");
    	
		return ProjectMonthPlanProFactory.getRemoteInstance().getProjectMonthPlanProInfo(new ObjectUuidPK(getSelectedKeyValue()),sel);
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode projectNode  = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		Object project  = null;
		FilterInfo filter=new FilterInfo();
		try	{
			if(projectNode!=null) {
				project = projectNode.getUserObject();
				filter=getTreeSelectFilter(project);
			}else{
				filter.getFilterItems().add(new FilterItemInfo("id", "'null'"));
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		}catch (Exception e)
		{
			handleException(e);
		}
		if(viewInfo.getSorter()!=null&&viewInfo.getSorter().size()<2){
			viewInfo.getSorter().clear();
			viewInfo.getSorter().add(new SorterItemInfo("curProject.number"));
			SorterItemInfo bizDate=new SorterItemInfo("bizDate");
			bizDate.setSortType(SortType.DESCEND);
			viewInfo.getSorter().add(bizDate);
			SorterItemInfo version=new SorterItemInfo("version");
			version.setSortType(SortType.DESCEND);
			viewInfo.getSorter().add(version);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
}