/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.aimcost.CostIndexCollection;
import com.kingdee.eas.fdc.aimcost.CostIndexFactory;
import com.kingdee.eas.fdc.aimcost.CostIndexInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractPayPlanCollection;
import com.kingdee.eas.fdc.contract.ContractPayPlanFactory;
import com.kingdee.eas.fdc.contract.ContractPayPlanInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractPayPlanEditUI;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class CostIndexListUI extends AbstractCostIndexListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostIndexListUI.class);
    public CostIndexListUI() throws Exception
    {
        super();
    }
    protected String[] getLocateNames() {
		return new String[] {"number", "contractName",  "partB.name", "contractType.name", "signDate"};
	}
	protected KDTable getBillListTable() {
		return this.tblPayRequestBill;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return CostIndexFactory.getRemoteInstance();
	}
	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return CostIndexFactory.getRemoteInstance();
	}
	protected boolean isFootVisible() {
		return false;
	}
	protected void initTable() {
		FDCHelper.formatTableDate(getBillListTable(), "bizDate");
		getBillListTable().getColumn("buildPrice").getStyleAttributes().setNumberFormat("#,##0.0;-#,##0.0");
		getBillListTable().getColumn("salePrice").getStyleAttributes().setNumberFormat("#,##0.0;-#,##0.0");
		
		getBillListTable().getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		getBillListTable().getColumn("saleArea").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		super.initTable();
	}
	protected void freezeBillTableColumn() {
		super.freezeBillTableColumn();
	}
	protected boolean  displayBillByContract(KDTSelectEvent e,EntityViewInfo view) throws BOSException {
		if(view==null){
			return false ;
		}
		CostIndexCollection contractChangeBillCollection = CostIndexFactory.getRemoteInstance().getCostIndexCollection(view);
		for (Iterator iter = contractChangeBillCollection.iterator(); iter.hasNext();) {
			CostIndexInfo element = (CostIndexInfo) iter
					.next();
			IRow row = getBillListTable().addRow();
			row.getCell("id").setValue(element.getId().toString());
			row.getCell("number").setValue(element.getNumber());
			row.getCell("bizDate").setValue(element.getBizDate());
			
			row.getCell("description").setValue(element.getDescription());
			if(element.getCreator()!=null)
				row.getCell("creator.name").setValue(element.getCreator().getName());
			row.getCell("createTime").setValue(element.getCreateTime());
			
			if(element.getInviteType()!=null)
				row.getCell("inviteType").setValue(element.getInviteType().getName());
			row.getCell("buildPrice").setValue(element.getBuildPrice());
			
			row.getCell("isLatest").setValue(element.isIsLatest());
			row.getCell("version").setValue(element.getVersion());
			
			row.getCell("state").setValue(element.getState());
			
			row.getCell("contractPhase").setValue(element.getContractPhase());
			
			row.getCell("amount").setValue(element.getAmount());
			row.getCell("saleArea").setValue(element.getSaleArea());
			row.getCell("salePrice").setValue(element.getSalePrice());
		}
		
		return true;
	}

    protected String getBillStatePropertyName() {
    	return "state";
    }
	protected EntityViewInfo genBillQueryView(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
		KDTSelectBlock selectBlock = e.getSelectBlock();
    	int top = selectBlock.getTop();
    	if(getMainTable().getCell(top, getKeyFieldName())==null){
    		return null;
    	}
    	
    	String contractId = (String)getMainTable().getCell(top, getKeyFieldName()).getValue();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contract.id", contractId));
    	view.setFilter(filter);
    	SorterItemCollection sort=new SorterItemCollection();
		SorterItemInfo isLatest=new SorterItemInfo("isLatest");
		isLatest.setSortType(SortType.DESCEND);
		sort.add(isLatest);
		SorterItemInfo version=new SorterItemInfo("version");
		version.setSortType(SortType.DESCEND);
		sort.add(version);
		view.setSorter(sort);
    	SelectorItemCollection selectors = genBillQuerySelector();
    	if(selectors != null && selectors.size() > 0) {
    		for (Iterator iter = selectors.iterator(); iter.hasNext();) {
				SelectorItemInfo element = (SelectorItemInfo) iter.next();
				view.getSelector().add(element);
				
			}
    	}
		return view;
	}
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("*");
		selectors.add("creator.name");
		selectors.add("inviteType.name");

		return selectors;
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}
	protected String getEditUIName() {
		return CostIndexEditUI.class.getName();
	}
	public void onShow() throws Exception{
		super.onShow();
		getBillListTable().setColumnMoveable(true);
		FDCClientHelper.setActionEnable(actionAudit, true);
		FDCClientHelper.setActionEnable(actionWorkFlowG, false);
		//合同修订增加附件功能         by Cassiel_peng
		FDCClientHelper.setActionEnable(actionAttachment, true);
		FDCClientHelper.setActionEnable(actionRemove, true);
		
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAuditResult.setVisible(false);
		
		this.menuWorkFlow.setVisible(false);
		this.menuBiz.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.tblPayRequestBill.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		this.actionModify.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_duizsetting"));
	}
	protected boolean isShowAttachmentAction() {
		return false;
	}
	protected void updateButtonStatus() {
		super.updateButtonStatus();
		FDCClientHelper.setActionEnable(actionAddNew, true);
		FDCClientHelper.setActionEnable(actionEdit, true);
		FDCClientHelper.setActionEnable(actionLocate, true);
		FDCClientHelper.setActionEnable(actionRefresh, true);
		FDCClientHelper.setActionEnable(actionRemove, true);
		menuEdit.setVisible(true);
	}
	protected void fetchInitData() throws Exception {
		Map param = new HashMap();
		Map initData = ((IFDCBill)getRemoteInterface()).fetchInitData(param);
		
		//获得当前组织
		orgUnit = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
	}
	protected void cbIsAll_itemStateChanged(ItemEvent e) throws Exception {
		treeSelectChange();
	}
	protected void audit(List ids) throws Exception {
		CostIndexFactory.getRemoteInstance().audit(ids);
	}
	protected void unAudit(List ids) throws Exception {
		CostIndexFactory.getRemoteInstance().unAudit(ids);
	}
	public void actionModify_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("*");
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("creator.*");
    	sel.add("auditor.*");
    	sel.add("curProject.*");
    	sel.add("contract.id");
    	sel.add("contract.number");
    	sel.add("contract.name");
    	sel.add("contract.curProject.displayName");
    	sel.add("contract.orgUnit.displayName");
    	sel.add("contract.amount");
    	sel.add("entry.*");
    	sel.add("entry.config.*");
    	sel.add("productType");
    	sel.add("isLatest");
    	sel.add("inviteType.*");
    	
		CostIndexInfo info=CostIndexFactory.getRemoteInstance().getCostIndexInfo(new ObjectUuidPK(getSelectedKeyValue()),sel);
		if (!FDCUtils.isBillAudited(info)) {
			MsgBox.showWarning(this, "非审批单据不能修订！");
	        SysUtil.abort();
		}
		if(!info.isIsLatest()){
			MsgBox.showWarning(this, "非最新版本不能修订！");
	        SysUtil.abort();
		}
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contract.id",info.getContract().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("inviteType",info.getInviteType().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("version",info.getVersion(),CompareType.GREATER));
		if(CostIndexFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this,"单据已修订！");
			return;
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("info", info);
		IUIWindow ui = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null,	OprtState.ADDNEW);
		ui.show();
	}
	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter=super.getTreeSelectChangeFilter();
		filter.getFilterItems().add(new FilterItemInfo("inviteType.isCostIndex",Boolean.TRUE));
		return filter;
	}
	protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
			IRow row = tblMain.getRow(i);
			ICell cell = row.getCell("isHasCostIndex");
			if (cell==null||!(Boolean)cell.getValue()) {
				row.getStyleAttributes().setBackground(new Color(0xFFEA67));
			}
		}
	}
}