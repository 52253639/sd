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
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
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
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.IProjectYearPlan;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherFactory;
import com.kingdee.eas.fdc.finance.ProjectYearPlanFactory;
import com.kingdee.eas.fdc.finance.ProjectYearPlanInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class ProjectYearPlanListUI extends AbstractProjectYearPlanListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectYearPlanListUI.class);
    //获取有权限的组织
	protected Set authorizedOrgs = null;
	protected static final String CANTEDIT = "cantEdit";
	protected static final String CANTREMOVE = "cantRemove";
    public ProjectYearPlanListUI() throws Exception
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
        return ProjectYearPlanEditUI.class.getName();
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
		return ProjectYearPlanFactory.getRemoteInstance();
	}
    protected void checkBeforeEditOrRemove(String warning,String id) throws EASBizException, BOSException, Exception {
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");
		
		ProjectYearPlanInfo info=(ProjectYearPlanInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels);
		
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
		Object obj = node.getUserObject();
		
		String proId = ((CurProjectInfo) obj).getId().toString();
		boolean isExist = getBizInterface().exists("where curProject = '".concat(proId).concat("'"));
		if (isExist) {
			throw new EASBizException(new NumericExceptionSubItem("1", "此工程项目下已存在项目年度付款规划,请进行修订操作！"));
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
	public FDCBillStateEnum getBizState(String id) throws EASBizException, BOSException, Exception{
		if(id==null) return null;
		SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("state");
    	return ((ProjectYearPlanInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels)).getState();
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
			((IProjectYearPlan)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
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
			((IProjectYearPlan)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionModify_actionPerformed(ActionEvent e) throws Exception {
		ProjectYearPlanInfo info=getSelectedInfo();
		checkAudited(info);
		checkLastVersion(info);
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id",info.getCurProject().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("version",info.getVersion(),CompareType.GREATER));
		if(ProjectYearPlanFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this,"单据已修订！");
			return;
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("info", info);
		IUIWindow ui = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null,	OprtState.ADDNEW);
		ui.show();
	}
	private void checkAudited(ProjectYearPlanInfo info) throws BOSException, EASBizException {
		if (!FDCUtils.isBillAudited(info)) {
			MsgBox.showWarning(this, "非审批单据不能修订！");
	        SysUtil.abort();
		}
	}
	private void checkLastVersion(ProjectYearPlanInfo info) throws BOSException, EASBizException {
		if(!info.isIsLatest()){
			MsgBox.showWarning(this, "非最新版本不能修订！");
	        SysUtil.abort();
		}
	}
	private ProjectYearPlanInfo getSelectedInfo() throws BOSException, EASBizException {
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
    	sel.add("totalEntry.*");
    	sel.add("totalBgEntry.*");
    	sel.add("totalBgEntry.bgItem.*");
		return ProjectYearPlanFactory.getRemoteInstance().getProjectYearPlanInfo(new ObjectUuidPK(getSelectedKeyValue()),sel);
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
			SorterItemInfo version=new SorterItemInfo("version");
			version.setSortType(SortType.DESCEND);
			viewInfo.getSorter().add(version);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	protected boolean initDefaultFilter() {
		return true;
	}
	private CommonQueryDialog queryDlg = null;
	protected CommonQueryDialog initCommonQueryDialog() {
		if (queryDlg != null)
			return queryDlg;
		try {
			queryDlg = super.initCommonQueryDialog();
			queryDlg.addUserPanel(getFilterUI());
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		return queryDlg;
	}
	ProjectPlanFilterUI filterUI;
	public ProjectPlanFilterUI getFilterUI() throws Exception {
		if (filterUI == null)
			filterUI = new ProjectPlanFilterUI();
		return filterUI;
	}
}