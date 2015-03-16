/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
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
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.client.helper.OrgHelper;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilderForXH;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.IOrgUnitMonthPlanGather;
import com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherFactory;
import com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherFactory;
import com.kingdee.eas.fdc.finance.VersionTypeEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class OrgUnitMonthPlanGatherListUI extends AbstractOrgUnitMonthPlanGatherListUI
{
    private static final Logger logger = CoreUIObject.getLogger(OrgUnitMonthPlanGatherListUI.class);
    //获取有权限的组织
    protected Set authorizedOrgs = null;
	protected static final String CANTEDIT = "cantEdit";
	protected static final String CANTREMOVE = "cantRemove";
    public OrgUnitMonthPlanGatherListUI() throws Exception
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
        return OrgUnitMonthPlanGatherEditUI.class.getName();
    }
    protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node  = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		if (node != null) {
			Object obj = node.getUserObject();
			if (obj instanceof CompanyOrgUnitInfo) {
				uiContext.put("orgUnit", obj);
			}
		}
		super.prepareUIContext(uiContext, e);
	}
    public void buildProjectTree() throws Exception {
		authorizedOrgs = (Set)ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
		if(authorizedOrgs==null){
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
			            OrgType.Company,null,  null, null);
			if(orgs!=null){
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while(it.hasNext()){
					authorizedOrgs.add(it.next());
				}
			}		
		}
//		String number=null;
//		if(orgUnit.getLevel()>=4){
//			number=orgUnit.getNumber().substring(0,5);
//		}
		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("level",Integer.valueOf(4)));	
		filter.getFilterItems().add(new FilterItemInfo("longNumber",orgUnit.getLongNumber()+"%",CompareType.LIKE));	
		if (authorizedOrgs == null || authorizedOrgs.size() == 0) {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", authorizedOrgs,CompareType.INCLUDE));
		}
//		if(number!=null){
//			filter.getFilterItems().add(new FilterItemInfo("number", number,CompareType.LIKE));
//		}else{
//			FilterInfo cufilter = new FilterInfo();
//			cufilter.getFilterItems().add(new FilterItemInfo("level",Integer.valueOf(1)));
//			filter.mergeFilter(cufilter, "or");
//		}
		ITreeBuilder treeBuilder = TreeBuilderFactory.createTreeBuilder(new DefaultLNTreeNodeCtrl(CompanyOrgUnitFactory.getRemoteInstance()), Integer.MAX_VALUE, 5, filter);
		treeBuilder.buildTree(treeProject);
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
			if (project instanceof CompanyOrgUnitInfo) {
				actionAddNew.setEnabled(true);
				kDContainer1.setTitle(((CompanyOrgUnitInfo) project).getDisplayName());
			} else {
				actionAddNew.setEnabled(false);
				kDContainer1.setTitle(null);
			}
		}
    }
    protected FilterInfo getTreeSelectFilter(Object projectNode) throws Exception {
		FilterInfo filter = new FilterInfo();
		if (projectNode != null&& projectNode instanceof CompanyOrgUnitInfo) {
			CompanyOrgUnitInfo projTreeNodeInfo = (CompanyOrgUnitInfo) projectNode;
			String id = projTreeNodeInfo.getId().toString();
			String orgUnitLongNumber = null;
			if(orgUnit!=null && id.equals(orgUnit.getId().toString())){					
				orgUnitLongNumber = orgUnit.getLongNumber();
			}else{
				FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(id));
				orgUnitLongNumber = orgUnitInfo.getLongNumber();
			}
			FilterInfo f = new FilterInfo();
			if(orgUnitLongNumber.equals("G")){
				f.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber));
			}else{
				f.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));
			}
			f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
			f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs,CompareType.INCLUDE));
			
			f.setMaskString("#0 and #1 and #2");
			
			if(filter!=null){
				filter.mergeFilter(f,"and");
			}
		}
		return filter;
	}
    protected ICoreBase getBizInterface() throws Exception {
		return OrgUnitMonthPlanGatherFactory.getRemoteInstance();
	}
    protected void checkBeforeEditOrRemove(String warning,String id) throws EASBizException, BOSException, Exception {
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");
		OrgUnitMonthPlanGatherInfo info=(OrgUnitMonthPlanGatherInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels);
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
			OrgUnitMonthPlanGatherInfo info=(OrgUnitMonthPlanGatherInfo)getBizInterface().getValue(new ObjectUuidPK(id.get(i).toString()),sels);
			if (!FDCBillStateEnum.SUBMITTED.equals(info.getState())) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			((IOrgUnitMonthPlanGather)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
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
			OrgUnitMonthPlanGatherInfo info=(OrgUnitMonthPlanGatherInfo)getBizInterface().getValue(new ObjectUuidPK(id.get(i).toString()),sels);
			if (!FDCBillStateEnum.AUDITTED.equals(info.getState())) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			((IOrgUnitMonthPlanGather)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionModify_actionPerformed(ActionEvent e) throws Exception {
		OrgUnitMonthPlanGatherInfo info=getSelectedInfo();
		checkAudited(info);
		checkLastVersion(info);
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id",info.getOrgUnit().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("bizDate",info.getBizDate()));
		filter.getFilterItems().add(new FilterItemInfo("versionType",info.getVersionType().getValue()));
		filter.getFilterItems().add(new FilterItemInfo("version",info.getVersion(),CompareType.GREATER));
		if(ProjectMonthPlanGatherFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this,"单据已修订！");
			return;
		}
		if(info.getVersionType().equals(VersionTypeEnum.REPORT)){
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id",info.getOrgUnit().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("bizDate",info.getBizDate()));
			filter.getFilterItems().add(new FilterItemInfo("versionType",VersionTypeEnum.EXECUTE_VALUE));
			if(ProjectMonthPlanGatherFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this,"已存在执行版！");
				return;
			}
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("info", info);
		IUIWindow ui = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null,	OprtState.ADDNEW);
		ui.show();
	}
	private void checkAudited(OrgUnitMonthPlanGatherInfo info) throws BOSException, EASBizException {
		if (!FDCUtils.isBillAudited(info)) {
			MsgBox.showWarning(this, "非审批单据不能修订！");
	        SysUtil.abort();
		}
	}
	private void checkLastVersion(OrgUnitMonthPlanGatherInfo info) throws BOSException, EASBizException {
		if(!info.isIsLatest()){
			MsgBox.showWarning(this, "非最新版本不能修订！");
	        SysUtil.abort();
		}
	}
	private OrgUnitMonthPlanGatherInfo getSelectedInfo() throws BOSException, EASBizException {
		checkSelected();
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("*");
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("creator.*");
    	sel.add("auditor.*");
    	sel.add("entry.*");
    	sel.add("entry.orgUnit.*");
    	sel.add("entry.curProject.*");
    	sel.add("entry.dateEntry.*");
    	sel.add("entry.dateEntry.bgItem.*");
		return OrgUnitMonthPlanGatherFactory.getRemoteInstance().getOrgUnitMonthPlanGatherInfo(new ObjectUuidPK(getSelectedKeyValue()),sel);
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
			viewInfo.getSorter().add(new SorterItemInfo("orgUnit.number"));
			SorterItemInfo bizDate=new SorterItemInfo("bizDate");
			bizDate.setSortType(SortType.DESCEND);
			viewInfo.getSorter().add(bizDate);
			SorterItemInfo versionType=new SorterItemInfo("versionType");
			viewInfo.getSorter().add(versionType);
			SorterItemInfo version=new SorterItemInfo("version");
			version.setSortType(SortType.DESCEND);
			viewInfo.getSorter().add(version);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
			IRow row = tblMain.getRow(i);
			ICell versionTypeCell = row.getCell("versionType");
			ICell isLastestCell = row.getCell("isLatest");
			if (versionTypeCell.getValue() != null&&versionTypeCell.getValue().toString().equals("上报版")
					&&isLastestCell.getValue()!=null&&(Boolean)isLastestCell.getValue()) {
				row.getStyleAttributes().setBackground(new Color(0xFFEA67));
			}
			if (versionTypeCell.getValue() != null&&versionTypeCell.getValue().toString().equals("执行版")
					&&isLastestCell.getValue()!=null&&(Boolean)isLastestCell.getValue()) {
				row.getStyleAttributes().setBackground(new Color(0xD2E3CA));
			}
		}
	}
}