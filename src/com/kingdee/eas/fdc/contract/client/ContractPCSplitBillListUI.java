/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillFactory;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.finance.SettledMonthlyHelper;
import com.kingdee.eas.fdc.finance.client.PaymentSplitListUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.config.client.ConfigServiceUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ContractPCSplitBillListUI extends AbstractContractPCSplitBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractPCSplitBillListUI.class);
    protected static final BOSUuid splitBillNullID = BOSUuid.create("null");
    private String[] contractType=null;
    public ContractPCSplitBillListUI() throws Exception
    {
        super();
    	HashMap hmParamIn = new HashMap();
		hmParamIn.put("CIFI_PCCONTRACTTYPE", null);
		try {
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			if(hmAllParam.get("CIFI_PCCONTRACTTYPE")!=null&&!"".equals(hmAllParam.get("CIFI_PCCONTRACTTYPE"))){
				contractType=hmAllParam.get("CIFI_PCCONTRACTTYPE").toString().split(";");
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    }
    protected void drawALogo(String name, Color color) {
		if (color == null) {
			color = colorPanel.getBackground();
		}
		KDLabel lable = new KDLabel(name);
		KDLabel colorLable = new KDLabel();
		Dimension d = new Dimension(40, 10);
		colorLable.setPreferredSize(d);
		colorLable.setOpaque(true);
		colorLable.setBackground(color);
		colorPanel.add(lable);
		colorPanel.add(colorLable);

	}
    protected void drawColorPanel() {
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setHgap(10);
		colorPanel.setLayout(flowLayout);
		drawALogo(FDCSplitClientHelper.getRes("allSplitState"),FDCSplitClientHelper.COLOR_ALLSPLIT);
		drawALogo(FDCSplitClientHelper.getRes("partSplitState"),FDCSplitClientHelper.COLOR_PARTSPLIT);
		drawALogo(FDCSplitClientHelper.getRes("notSubmmit"),FDCSplitClientHelper.COLOR_NOSPLIT);
	}
    protected String getStateForAudit() {
		return FDCBillStateEnum.SAVED_VALUE;
	}
    protected void checkBeforeAudit() throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (idSet.size() > 0) {
			filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		}
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("splitState");
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);

		if (!coll.iterator().hasNext()) {
			MsgBox.showWarning(this, FDCSplitClientHelper.getRes("noView"));
			SysUtil.abort();
		}

		if (coll.size() < idList.size()) {
			MsgBox.showWarning(this, FDCSplitClientHelper.getRes("existNoSplitRecord"));
			SysUtil.abort();
		}

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBaseInfo element = (CoreBaseInfo) iter.next();

			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());
			if (((element.getString("splitState")).equals(CostSplitStateEnum.NOSPLIT_VALUE))) {
				MsgBox.showWarning(this, FDCSplitClientHelper.getRes("partSplited"));
				SysUtil.abort();
			}
		}
	}
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception{
    	checkBeforeAudit();
		super.actionAudit_actionPerformed(e);
    }
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception{
    	checkBeforeAudit();
        super.actionUnAudit_actionPerformed(e);
    }
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),getKeyFieldName());
		Set idSet = ContractClientUtils.listToSet(idList);

		FilterInfo filter = new FilterInfo();
		if (idSet.size() > 0) {
			filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		}
		if (!getBizInterface().exists(filter)) {
			MsgBox.showWarning(this, FDCSplitClientHelper.getRes("noView"));
			SysUtil.abort();
		}
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(FDCSplitClientHelper.getRes("listRemove"));
			SysUtil.abort();
		}
		if (confirmRemove()) {
			for(int i=0;i<idList.size();i++){
				ContractPCSplitBillFactory.getRemoteInstance().delete(new ObjectUuidPK(idList.get(i).toString()));
			}
			showOprtOKMsgAndRefresh();
		}
	}
    public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();

		boolean isNotSplit = false;
		String keyValue = getSelectedKeyValue();

		if (keyValue == null || keyValue.equals(splitBillNullID.toString())) {
			isNotSplit = true;
		} else {
			// 界面上显示有，但数据库内已经删除时的情况
			IObjectPK pk = new ObjectUuidPK(BOSUuid.read(keyValue));
			// 暂时去掉这个操作
			if (!getBizInterface().exists(pk)) {
				isNotSplit = true;
				int[] selectedRows = KDTableUtil.getSelectedRows(getMainTable());
				getMainTable().getCell(selectedRows[0],getSplitStateFieldName()).setValue(CostSplitStateEnum.NOSPLIT.toString());
			}
		}
		if (isNotSplit) {
			MsgBox.showWarning(this, FDCSplitClientHelper.getRes("noView"));
			SysUtil.abort();
		} else {
			super.actionView_actionPerformed(e);
		}
	}
    protected boolean checkBeforeSplit() throws Exception {
    	boolean isAddNew = false;
		String keyValue = getSelectedKeyValue();

		if (keyValue == null || keyValue.equals(splitBillNullID.toString())) {
			isAddNew = true;
		} else {
			IObjectPK pk = new ObjectUuidPK(BOSUuid.read(keyValue));
			if (!getBizInterface().exists(pk)) {
				isAddNew = true;
				int[] selectedRows = KDTableUtil.getSelectedRows(getMainTable());
				getMainTable().getCell(selectedRows[0],getSplitStateFieldName()).setValue(CostSplitStateEnum.NOSPLIT.toString());
			}
		}
		return isAddNew;
	}
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		boolean hasMutex = false;
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getSelectedKeyValue());
		try{
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
			String id = getSelectedKeyValue();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", id));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			if (getBizInterface().exists(filter)) {
				MsgBox.showWarning(FDCSplitClientHelper.getRes("listSplit"));
				SysUtil.abort();
			}
			boolean isAddNew = checkBeforeSplit();
			if (isAddNew) {
				ActionEvent evt = new ActionEvent(btnAddNew, 0, "FDCCostSplit");
				ItemAction actAddNew = getActionFromActionEvent(evt);
				actAddNew.actionPerformed(evt);
			} else {
				String costBillID = getSelectedKeyValue();
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, costBillID);
				DefaultKingdeeTreeNode node = getProjSelectedTreeNode();
				uiContext.put("node",node);
				IUIWindow uiWin = null;
				uiWin = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null, OprtState.EDIT);
				uiWin.show();
			}
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
	}
    protected String getEditUIName() {
		return ContractPCSplitBillEditUI.class.getName();
	}
	protected ICoreBase getRemoteInterface() throws BOSException {
		return ContractPCSplitBillFactory.getRemoteInstance();
	}
	protected void audit(List ids) throws Exception {
		((IFDCBill) getRemoteInterface()).audit(ids);
	}
	protected void unAudit(List ids) throws Exception {
		((IFDCBill) getRemoteInterface()).unAudit(ids);
	}
	protected void updateButtonStatus() {
		this.actionView.setEnabled(true);
	}
	public void onLoad() throws Exception {
		ConfigServiceUtils.saveUserConfigData("HeadView", this, null);
		tblMain.addKDTDataFillListener(new KDTDataFillListener() {
			public void afterDataFill(KDTDataRequestEvent e) {
				tblMain_tableAfterDataFill(e);
			}
		});
		super.onLoad();
		FDCHelper.formatTableNumber(getMainTable(), "settleAmt");
		drawColorPanel();
		this.actionSplit.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_split"));
		this.actionUnAudit.setVisible(false);
		this.actionAudit.setVisible(false);
		this.menuBiz.setVisible(false);
		
		this.actionEdit.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionWorkflowList.setVisible(false);
		this.menuWorkFlow.setVisible(false);
		
		this.actionQuery.setEnabled(true);
		this.actionQuery.setVisible(true);
	}
	protected void tblMain_tableAfterDataFill(KDTDataRequestEvent e) {
		int start = e.getFirstRow();
		int end = e.getLastRow();
		setSplitStateColor(start, end);
	}
	protected String getContractKeyFieldName() {
		return "id";
	}
	protected String getKeyFieldName() {
		return "contractPCSplitBill.id";
	}
	protected String getSplitStateFieldName() {
		return "contractPCSplitBill.splitState";
	}
	protected SorterItemCollection getSorter() {
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(getSplitStateFieldName()));
		sorter.add(new SorterItemInfo("number"));
		return sorter;
	}
	protected FilterInfo getTreeFilter() throws Exception{
		FilterInfo filter=super.getTreeFilter();
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("curProject.isWholeAgeStage", Boolean.TRUE));
		
		if(contractType!=null&&contractType.length>0){
			FilterInfo contractTypeFilter=new FilterInfo();
    		for(int i=0;i<contractType.length;i++){
    			FilterInfo ct=new FilterInfo();
    			ct.getFilterItems().add(new FilterItemInfo("contractType.longNumber", contractType[i]+"%",CompareType.LIKE));
    			contractTypeFilter.mergeFilter(ct, "or");
    		}
    		filter.mergeFilter(contractTypeFilter, "and");
    	}
		return filter;
	}
	public void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(1);
		projectTreeBuilder.build(this, treeProject, actionOnLoad);
		treeProject.setShowsRootHandles(true);
	}
	protected void setSplitStateColor(int start, int end) {
		String splitState;
		for (int i = start; i <= end; i++) {
			IRow row = tblMain.getRow(i);

			ICell cell = row.getCell(getSplitStateFieldName());
			if (cell.getValue() == null|| cell.getValue().toString().equals("")) {
				splitState = CostSplitStateEnum.NOSPLIT.toString();
				cell.setValue(splitState);
			} else {
				splitState = cell.getValue().toString();
			}
			if (splitState.equals(CostSplitStateEnum.ALLSPLIT.toString())) {
				row.getStyleAttributes().setBackground(FDCSplitClientHelper.COLOR_ALLSPLIT);
			} else if (splitState.equals(CostSplitStateEnum.PARTSPLIT.toString())) {
				row.getStyleAttributes().setBackground(FDCSplitClientHelper.COLOR_PARTSPLIT);
			}else {
				row.getStyleAttributes().setBackground(FDCSplitClientHelper.COLOR_NOSPLIT);
			}
		}
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);

		if (act.equals(actionAddNew)) {
			uiContext.put("contractBillId", getSelectedCostBillID());
		} else if (act.equals(actionEdit) || act.equals(actionView)) {
			uiContext.put(UIContext.ID, getSelectedKeyValue());
		}
	}
	protected String getSelectedCostBillID() {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows.length > 0) {
			int rowIndex = selectRows[0];
			IRow row = tblMain.getRow(rowIndex);
			if (row == null) {
				return null;
			}
			ICell cell = row.getCell(getContractKeyFieldName());
			if (cell == null) {
				MsgBox.showError(EASResource.getString(FrameWorkClientUtils.strResource+ "Error_KeyField_Fail"));
				SysUtil.abort();
			}
			Object keyValue = cell.getValue();
			if (keyValue != null) {
				return keyValue.toString();
			}
		}
		return null;
	}
	protected void filterByBillState(EntityViewInfo ev)
    {
		FilterInfo newFilter = new FilterInfo();
		Set set = new HashSet(3);
		set.add("1SAVED");
		set.add("9INVALID");
		set.add("2SUBMITTED");
		set.add("3AUDITTING");
		newFilter.getFilterItems().add(new FilterItemInfo("state", set, CompareType.NOTINCLUDE));
		newFilter.appendFilterItem("isAmtWithoutCost", Boolean.FALSE);
		try
        {
			if(ev.getFilter() == null)
				ev.setFilter(newFilter);
			else
				ev.getFilter().mergeFilter(newFilter, "and");
        }
		catch(Exception e)
        {
			handUIExceptionAndAbort(e);
        }
    }
}