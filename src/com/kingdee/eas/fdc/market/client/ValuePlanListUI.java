/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import java.util.ArrayList;

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
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.fdc.basedata.Utils.UIHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.IValuePlan;
import com.kingdee.eas.fdc.market.ValuePlanFactory;
import com.kingdee.eas.fdc.market.ValuePlanInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ValuePlanListUI extends AbstractValuePlanListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ValuePlanListUI.class);
    public ValuePlanListUI() throws Exception
    {
        super();
    }
    protected static final String CANTEDIT = "cantEdit";
	protected static final String CANTREMOVE = "cantRemove";
    public void onLoad() throws Exception {
    	this.actionQuery.setEnabled(false);
    	super.onLoad();
    	buildProjectTree();
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
        locateNames[0] = "number";
        locateNames[1] = "year";
        locateNames[2] = "month";
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
		
		this.actionModify.setVisible(false);
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
        return ValuePlanEditUI.class.getName();
    }
    protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}
    protected ICoreBase getBizInterface() throws Exception {
		return ValuePlanFactory.getRemoteInstance();
	}
    protected void checkBeforeEditOrRemove(String warning,String id) throws EASBizException, BOSException, Exception {
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		SelectorItemCollection sels =new SelectorItemCollection();
		sels.add("state");
		ValuePlanInfo info=(ValuePlanInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels);
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
			
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("state");
			ValuePlanInfo info=(ValuePlanInfo)getBizInterface().getValue(new ObjectUuidPK(id.get(i).toString()),sels);
			if (!FDCBillStateEnum.SUBMITTED.equals(info.getState())) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			((IValuePlan)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("state");
			ValuePlanInfo info=(ValuePlanInfo)getBizInterface().getValue(new ObjectUuidPK(id.get(i).toString()),sels);
			if (!FDCBillStateEnum.AUDITTED.equals(info.getState())) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			((IValuePlan)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	protected boolean isShowAttachmentAction() {
		return true;
	}
	public void buildProjectTree() throws Exception {
		UIHelper.isCompany();
		ProjectTreeForValue projectTreeBuilder = new ProjectTreeForValue();
		projectTreeBuilder.build(this, this.treeMain, actionOnLoad);
		
		if(this.treeMain.getRowCount() > 0){
			this.treeMain.setSelectionRow(0);
			this.treeMain.expandAllNodes(true,(DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
		}
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	treeSelectChange();
    	this.refresh(null);
    }
    protected void treeSelectChange() throws Exception {
//		DefaultKingdeeTreeNode projectNode  = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
//		Object project  = null;
//		if(projectNode!=null) {
//			project = projectNode.getUserObject();
//			if (project instanceof OperationPhasesInfo) {
//				actionAddNew.setEnabled(true);
//			} else {
//				actionAddNew.setEnabled(false);
//			}
//		}
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
				
				if(filter!=null){
					filter.mergeFilter(f,"and");
				}
			}else if(projTreeNodeInfo instanceof ProjectBaseInfo) {
				String  proID = ((ProjectBaseInfo)projTreeNodeInfo).getId().toString();
				filter.getFilterItems().add(new FilterItemInfo("project.id", proID));
			}
		}
		return filter;
	}
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode projectNode  = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
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
			viewInfo.getSorter().add(new SorterItemInfo("project.number"));
			SorterItemInfo year=new SorterItemInfo("year");
			year.setSortType(SortType.DESCEND);
			viewInfo.getSorter().add(year);
			SorterItemInfo month=new SorterItemInfo("month");
			month.setSortType(SortType.DESCEND);
			viewInfo.getSorter().add(month);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
    public DefaultKingdeeTreeNode getSellProjectTreeNode() {
		return (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
	}
    public void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	DefaultKingdeeTreeNode node  = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
    	ItemAction act = getActionFromActionEvent(e);
    	if (act.equals(actionAddNew)) {
    		if (node != null) {
    			Object obj = node.getUserObject();
    			if (obj instanceof ProjectBaseInfo) {
    				uiContext.put("project", obj);
    			}else{
    				MsgBox.showInfo("请选中项目新增单据!");
    				SysUtil.abort();
    			}
    		}else{
    			MsgBox.showInfo("请选中项目新增单据!");
    			SysUtil.abort();
    		}
    	}
		super.prepareUIContext(uiContext, e);
	}
}