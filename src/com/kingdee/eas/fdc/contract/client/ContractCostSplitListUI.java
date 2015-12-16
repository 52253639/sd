/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sql.RowSet;
import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ClearSplitFacadeFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.SettledMonthlyHelper;
import com.kingdee.eas.fdc.finance.client.PaymentSplitListUI;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class ContractCostSplitListUI extends AbstractContractCostSplitListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractCostSplitListUI.class);
	//付款单提交时，是否检查合同未完全拆分
	private boolean checkAllSplit = true;
	boolean isMeasureSplit=false;
	/**
	 * output class constructor
	 */
	public ContractCostSplitListUI() throws Exception {
		super();
	}
	
	/**
	 * 覆盖父类的checkSplit()方法，添加如下判断：
	 * 非复杂模式情况下，合同拆分被付款拆分引用，拆分需要更改时，提示引用，可由用户选择是否直接删除后续拆分 
	 * R090601-190、R090602-207 大华、铜陵——by neo
	 */
	protected void checkSplit() throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper
					.getRes("multiRowSelected"));
			SysUtil.abort();
		}
		String billId = getMainTable().getCell(selectRows[0],
				getCostBillIdFieldName()).getValue().toString();
		boolean existCostSplitColumn=getMainTable().getColumnIndex("isCostSplit")!=-1;
		boolean isCostSplit=false;
		if(existCostSplitColumn){
			Boolean temp=(Boolean)getMainTable().getCell(selectRows[0],"isCostSplit").getValue();
			if(temp!=null){
				isCostSplit=temp.booleanValue();
			}
		}
		FilterInfo filtertemp = new FilterInfo();
		filtertemp.getFilterItems().add(
				new FilterItemInfo("costBillId", billId));
		// 作废单据不算
		filtertemp.getFilterItems().add(
				new FilterItemInfo("parent.state",
						FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));

		
		boolean existSettlecoll = false;
		boolean existcollPay = false;
		boolean existcollNoCost = false;
		boolean existcollPayNoCost = false;
		if (existCostSplitColumn && isCostSplit) {
			existSettlecoll = SettlementCostSplitEntryFactory.getRemoteInstance().exists(filtertemp);
			existcollPay = PaymentSplitEntryFactory.getRemoteInstance().exists(filtertemp);
		}
		if (existCostSplitColumn && !isCostSplit) {
			existcollNoCost = SettNoCostSplitEntryFactory.getRemoteInstance().exists(filtertemp);
			existcollPayNoCost = PaymentNoCostSplitEntryFactory.getRemoteInstance().exists(filtertemp);
		}
		if((existSettlecoll||existcollPay)){		// 成本拆分：结算/工程量/付款拆分被引用  
			if(isSimpleFinacialMode()){
				int value = MsgBox.showConfirm2(this, "合同拆分被后续拆分引用，是否直接删除后续拆分");
				if(value == MsgBox.YES){
					if(existSettlecoll){
						filtertemp = new FilterInfo();
						filtertemp.getFilterItems().add(
								new FilterItemInfo("contractBill.id",billId,CompareType.EQUALS));
						SettlementCostSplitFactory.getRemoteInstance().delete(filtertemp);
					}
					if(existcollPay){
						filtertemp = new FilterInfo();
						filtertemp.getFilterItems().add(
								new FilterItemInfo("contractBill.id",billId,CompareType.EQUALS));					
						PaymentSplitFactory.getRemoteInstance().delete(filtertemp);
					}
				}else
					SysUtil.abort();
			}else{
				if (existSettlecoll) {			//被结算拆分引用
					MsgBox.showError(this, FDCSplitClientHelper.getRes("impBySett"));
					SysUtil.abort();
				}
				if (existcollPay) {				//拆分被工程量拆分或付款拆分引用
					MsgBox.showError(this, FDCSplitClientHelper.getRes("impByPay"));
					SysUtil.abort();
				}
			}
		}
		if((existcollNoCost||existcollPayNoCost)){		//非成本拆分：结算/工程量/付款拆分被引用
			if(!isFinacialModel()){
				int value = MsgBox.showConfirm2(this, "合同拆分被后续拆分引用，是否直接删除后续拆分");
				if(value == MsgBox.YES){
					if(existcollNoCost){
						filtertemp = new FilterInfo();
						filtertemp.getFilterItems().add(
								new FilterItemInfo("contractBill.id",billId,CompareType.EQUALS));
						SettNoCostSplitFactory.getRemoteInstance().delete(filtertemp);
					}
					if(existcollPayNoCost){
						filtertemp = new FilterInfo();
						filtertemp.getFilterItems().add(
								new FilterItemInfo("contractBill.id",billId,CompareType.EQUALS));
						PaymentNoCostSplitFactory.getRemoteInstance().delete(filtertemp);
					}
				}else
					SysUtil.abort();
			}else{
				if (existcollNoCost) {			//被结算拆分引用
					MsgBox.showError(this, FDCSplitClientHelper.getRes("impBySett"));
					SysUtil.abort();
				}
				if (existcollPayNoCost) {				//拆分被工程量拆分或付款拆分引用
					MsgBox.showError(this, FDCSplitClientHelper.getRes("impByPay"));
					SysUtil.abort();
				}
			}
		}
		
	}
	
	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList ids = getSelectedIdValues();
		//update by renliang 改回原来的ids.size()>1
		if (ids != null && ids.size() > 1) {
			for (Iterator iter = ids.iterator(); iter.hasNext();) {
				String id = (String) iter.next();
				if (isCostSplit(id)
						&& (SettledMonthlyHelper.existObject(null, costSplit,
								getSelectedKeyValue()))) {
					MsgBox.showWarning(this,
							"成本月结已经引用本数据，不能删除，如需修改，请把此付款对应的合同进入待处理流程！");
					SysUtil.abort();
				}

			}
			checkBeforeRemove();
			
			//付款单提交时，是否检查合同拆分			
			if(checkAllSplit){
				checkImp();			
			}

			if (confirmRemove()) {
				List costSplits = new ArrayList();
				List noCostSplits = new ArrayList();
				for (Iterator iter = ids.iterator(); iter.hasNext();) {
					String id = (String) iter.next();
					if (isCostSplit(id)) {
						costSplits.add(id);
					}
					if (isNoCostSplit(id)) {
						noCostSplits.add(id);
					}
				}
				if (costSplits.size() > 0) {
					IObjectPK[] pks = new ObjectUuidPK[costSplits.size()];
					for (int i = 0; i < costSplits.size(); i++) {
						pks[i] = new ObjectUuidPK(costSplits.get(i).toString());
					}
					ContractCostSplitFactory.getRemoteInstance().delete(pks);
				}

				if (noCostSplits.size() > 0) {
					IObjectPK[] pks = new ObjectUuidPK[costSplits.size()];
					for (int i = 0; i < noCostSplits.size(); i++) {
						pks[i] = new ObjectUuidPK(noCostSplits.get(i)
								.toString());
					}
					ConNoCostSplitFactory.getRemoteInstance().delete(pks);
				}
				// afterRemove();
				showOprtOKMsgAndRefresh();
			}

		} else {
			//update by renliang
			if(ids!=null && ids.size()>0){
				for (Iterator iter = ids.iterator(); iter.hasNext();) {
					String id = (String) iter.next();
					if (isCostSplit(id)
							&& (SettledMonthlyHelper.existObject(null, costSplit,
									getSelectedKeyValue()))) {
						MsgBox.showWarning(this,
								"成本月结已经引用本数据，不能删除，如需修改，请把此付款对应的合同进入待处理流程！");
						SysUtil.abort();

					}
				}
				super.actionRemove_actionPerformed(e);
			}
		}

	}

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		if (isCostSplit()) {
			return com.kingdee.eas.fdc.contract.client.ContractCostSplitEditUI.class
					.getName();
		} else {
			return com.kingdee.eas.fdc.contract.client.ConNoCostSplitEditUI.class
					.getName();
		}
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return getRemoteInterface();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.ContractCostSplitInfo objectValue = new com.kingdee.eas.fdc.contract.ContractCostSplitInfo();
		return objectValue;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillListUI#getRemoteInterface()
	 */
	protected ICoreBase getRemoteInterface() throws BOSException {
		if (isCostSplit()) {
			return com.kingdee.eas.fdc.contract.ContractCostSplitFactory
					.getRemoteInstance();
		} else {
			return com.kingdee.eas.fdc.contract.ConNoCostSplitFactory
					.getRemoteInstance();
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillListUI#setSplitStateColor()
	 */
	protected void setSplitStateColor() {
		// TODO 自动生成方法存根
		// super.setSplitStateColor();
	}

	protected void updateButtonStatus() {

		super.updateButtonStatus();

		// 如果是虚体成本中心，则不能增、删、改
//		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
//			actionEdit.setEnabled(false);
//			actionRemove.setEnabled(false);
//			actionCostSplit.setEnabled(false);
//			actionAddNew.setVisible(false);
//			actionEdit.setVisible(false);
//			actionRemove.setVisible(false);
//			actionCostSplit.setVisible(false);
//
//			menuEdit.setVisible(false);
			// actionView.setVisible(false);
			// actionView.setEnabled(false);
//		}
		actionRemove.setVisible(true);
		actionRemove.setEnabled(true);
		actionAddNew.setEnabled(false);
		menuEdit.setVisible(true);
	}

	protected boolean checkBeforeSplit() throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		/*
		 * if(selectRows.length>1){
		 * MsgBox.showWarning(this,"你选择了多行，不能同时对多行进行操作"); SysUtil.abort(); }
		 * String
		 * bill=getMainTable().getCell(selectRows[0],"id").getValue().toString();
		 * if (bill!= null) { ContractBillInfo con =
		 * ContractBillFactory.getRemoteInstance().getContractBillInfo(new
		 * ObjectUuidPK(bill)); if(!con.isIsCoseSplit()){
		 * MsgBox.showWarning(this,FDCSplitClientHelper.getRes("canNotSplit"));
		 * SysUtil.abort(); } }
		 */
		boolean isAddNew = super.checkBeforeSplit();
		if (isAddNew) {
			// 到数据库内进行检查
			String billId = getMainTable().getCell(selectRows[0], "id")
					.getValue().toString();
			billId = getSelectedCostBillID();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", billId));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			if (getRemoteInterface().exists(filter)) {
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				String costBillID = getRemoteInterface().getCollection(view)
						.get(0).getId().toString();
				getMainTable().getCell(selectRows[0], getKeyFieldName())
						.setValue(costBillID);
				isAddNew = false;
			}
		}
		if (!isAddNew
				&& isCostSplit()
				&& SettledMonthlyHelper.existObject(null, costSplit,
						getSelectedKeyValue())) {
			MsgBox.showWarning(this, "成本月结已经引用本数据，不能修改，如需修改，请先作废本合同拆分！");
			SysUtil.abort();
		}
		return isAddNew;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillListUI#getBillStatePropertyName()
	 */
	protected String getBillStatePropertyName() {
		// TODO 自动生成方法存根
		return super.getBillStatePropertyName();

		// return "billStatus";
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillListUI#getCurProjectPath()
	 */
	protected String getCurProjectPath() {
		// TODO 自动生成方法存根
		// return super.getCurProjectPath();

		return "curProject";

	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillListUI#getCostBillStateFieldName()
	 */
	protected String getCostBillStateFieldName() {
		// TODO 自动生成方法存根
		return super.getCostBillStateFieldName();

		// return "contractBill.state";
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillListUI#getSplitStateFieldName()
	 */
	protected String getSplitStateFieldName() {
		// TODO 自动生成方法存根
		return super.getSplitStateFieldName();

		// return "splitState";
	}

	protected String getCostBillIdFieldName() {
		return super.getCostBillIdFieldName();

		// return "contractBill.id";
	}

	protected String getCostBillFieldName() {
		return super.getCostBillFieldName();

		// return "costBill";
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.framework.client.ListUI#getKeyFieldName()
	 */
	protected String getKeyFieldName() {
		// TODO 自动生成方法存根
		return super.getKeyFieldName();

		// return "id";
	}

	private boolean isCostSplit() {
		boolean isCostSplit = false;
		// 非成本科目拆分
		int[] selectRows = KDTableUtil.getSelectedRows(getMainTable());
		if (selectRows.length < 1) {
			return false;
		}
		/*
		 * String
		 * bill=getMainTable().getCell(selectRows[0],"id").getValue().toString();
		 * if (bill!= null) { try{ ContractBillInfo con =
		 * ContractBillFactory.getRemoteInstance().getContractBillInfo(new
		 * ObjectUuidPK(bill)); if(con.isIsCoseSplit()){ isCostSplit=true; }
		 * }catch (Exception e){ handUIException(e); } }
		 */
		Object costSplit = getMainTable().getCell(selectRows[0], "isCostSplit")
				.getValue();
		if (costSplit instanceof Boolean) {
			isCostSplit = ((Boolean) costSplit).booleanValue();
		}
		return isCostSplit;
	}

	private static final BOSObjectType costSplit = new ContractCostSplitInfo()
			.getBOSType();

	private boolean isCostSplit(String id) {
		return id == null ? false : BOSUuid.read(id).getType()
				.equals(costSplit);
	}

	private boolean isNoCostSplit(String id) {
		return id == null ? false : !BOSUuid.read(id).getType().equals(
				costSplit);
	}

	protected void getRowSetBeforeFillTable(IRowSet rowSet) {
		super.getRowSetBeforeFillTable(rowSet);
		// handleNoCostSplit(rowSet);

	}

	private void handleNoCostSplit(RowSet rowSet) {
		boolean isCostSplit = false;
		EntityViewInfo view = new EntityViewInfo();

		view.setFilter(getNoCostSplitFilter());

		view.getSelector().add("splitState");
		view.getSelector().add("state");

		ConNoCostSplitCollection conNoCostSplitCollection = null;
		try {
			conNoCostSplitCollection = ConNoCostSplitFactory
					.getRemoteInstance().getConNoCostSplitCollection(view);
		} catch (BOSException e1) {
			handUIException(e1);
		}
		HashMap map = null;
		if (conNoCostSplitCollection != null
				&& conNoCostSplitCollection.size() > 0) {
			final int size = conNoCostSplitCollection.size();
			map = new HashMap(size);
			for (int i = 0; i < size; i++) {
				map.put(conNoCostSplitCollection.get(i).getContractBill()
						.getId().toString(), conNoCostSplitCollection.get(i));
			}
		} else {
			return;
		}
		ConNoCostSplitInfo conNoCostSplit = null;

		try {
			rowSet.beforeFirst();

			while (rowSet.next()) {
				isCostSplit = rowSet.getBoolean("isCostSplit");

				if (!isCostSplit) {
					Object object = map.get(rowSet.getString("id"));
					if (object != null) {
						conNoCostSplit = (ConNoCostSplitInfo) object;
						rowSet.updateString("costSplit.splitState",
								conNoCostSplit.getSplitState().getAlias());
						rowSet.updateObject("costSplit.state", conNoCostSplit
								.getState());
						// rowSet.updateObject("costSplit.splitState",
						// conNoCostSplit.getSplitState());
						// rowSet.updateObject("costSplit.splitState",
						// conNoCostSplit.getSplitState());
					}
				}
			}

			rowSet.beforeFirst();
			conNoCostSplit = null;
			map = null;
			conNoCostSplitCollection = null;
		} catch (SQLException e) {
			handleException(e);
		} catch (UuidException e) {
			handleException(e);
		}
	}

	/**
	 * 无文本合同的过滤条件
	 * 
	 * @author sxhong Date 2007-2-2
	 * @return
	 */
	private FilterInfo getNoCostSplitFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();

		/*
		 * 工程项目树
		 */
		if (getProjSelectedTreeNode() != null
				&& getProjSelectedTreeNode().getUserObject() instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) getProjSelectedTreeNode()
					.getUserObject();
			try {
				// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					BOSUuid id = ((OrgStructureInfo) projTreeNodeInfo)
							.getUnit().getId();

					Set idSet = FDCClientUtils.genOrgUnitIdSet(id);
					filterItems.add(new FilterItemInfo("orgUnit.id", idSet,
							CompareType.INCLUDE));
				}
				// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
				else if (projTreeNodeInfo instanceof CurProjectInfo) {
					BOSUuid id = projTreeNodeInfo.getId();
					Set idSet = FDCClientUtils.genProjectIdSet(id);
					filterItems.add(new FilterItemInfo(
							"contractBill.curProject.id", idSet,
							CompareType.INCLUDE));
				}
			} catch (Exception e) {
				handUIException(e);
			}
		}

		filterItems.add(new FilterItemInfo("contractBill.curProject.isEnabled",
				Boolean.TRUE));

		return filter;
	}

	public void onLoad() throws Exception {
		if (isCostSplit()) {
			initParam = ContractCostSplitFactory.getRemoteInstance().fetchInitParam();
		}else{
			initParam = ConNoCostSplitFactory.getRemoteInstance().fetchInitParam();
		}
		
		super.onLoad();
		checkAllSplit = FDCUtils.getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_CHECKALLSPLIT);
		if (!isFinacialModel()) {
			if (isIncorporation) {
				FDCClientHelper.setActionEnable(new ItemAction[] {
						actionVoucher, actionDelVoucher }, false);
				FDCClientHelper.setActionEnable(new ItemAction[] {
						actionClearSplit, actionViewInvalid }, true);
			} else
				FDCClientHelper.setActionEnable(new ItemAction[] {
						actionVoucher, actionDelVoucher, actionClearSplit },
						false);
		}
		// actionVoucher.setVisible(true);
		// actionVoucher.setEnabled(true);
		// actionDelVoucher.setVisible(true);
		// actionDelVoucher.setEnabled(true);
		// int contractType_name =
		// getMainTable().getColumn("contractType.name").getColumnIndex();
		// getMainTable().getViewManager().setFreezeView(-1,
		// contractType_name+1);
		isMeasureSplit=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_MEASURESPLIT);
		if(isMeasureSplit){
			actionMeasureSplit.setEnabled(actionCostSplit.isEnabled());
			actionMeasureSplit.setVisible(actionCostSplit.isVisible());
			actionMeasureSplit.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_disassemble"));
		}else{
			actionMeasureSplit.setEnabled(false);
			actionMeasureSplit.setVisible(false);
		}
//		if (isCostSplit()) {
//			initParam = ContractCostSplitFactory.getRemoteInstance().fetchInitParam();
//		}else{
//			initParam = ConNoCostSplitFactory.getRemoteInstance().fetchInitParam();
//		}
		actionViewContract.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_sequencecheck"));
	}

	protected void freezeTableColumn() {
		super.freezeTableColumn();
		int contractType_name = getMainTable().getColumn("contractType.name")
				.getColumnIndex();
		getMainTable().getViewManager()
				.setFreezeView(-1, contractType_name + 1);

	}

	protected void checkbeforeAudit() throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),// "id");
				getKeyFieldName());

		List costSplits = new ArrayList();
		List noCostSplits = new ArrayList();
		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			if (isCostSplit(id)) {
				costSplits.add(id);
			}
			if (isNoCostSplit(id)) {
				noCostSplits.add(id);
			}
		}
		Set idSetCostSplit = ContractClientUtils.listToSet(costSplits);
		Set idSetNoCostSplit = ContractClientUtils.listToSet(noCostSplits);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		CoreBaseCollection collCostsplit = new CoreBaseCollection();
		CoreBaseCollection collnoCostsplit = new CoreBaseCollection();
		view.getSelector().add("id");
		view.getSelector().add("splitState");
		if (idSetCostSplit.size() > 0) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", idSetCostSplit,
							CompareType.INCLUDE));
			view.setFilter(filter);
			collCostsplit = ContractCostSplitFactory.getRemoteInstance()
					.getCollection(view);
		}

		filter = new FilterInfo();

		if (idSetNoCostSplit.size() > 0) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", idSetNoCostSplit,
							CompareType.INCLUDE));
			view.setFilter(filter);
			collnoCostsplit = ConNoCostSplitFactory.getRemoteInstance()
					.getCollection(view);
		}
		if (collnoCostsplit.size() + collCostsplit.size() == 0) {
			MsgBox.showWarning(this, FDCSplitClientHelper.getRes("noView"));
			SysUtil.abort();
		}

		if ((collnoCostsplit.size() + collCostsplit.size()) < idList.size()) {
			MsgBox.showWarning(this, FDCSplitClientHelper
					.getRes("existNoSplitRecord"));
			SysUtil.abort();
		}

		for (Iterator iter = collCostsplit.iterator(); iter.hasNext();) {
			CoreBaseInfo element = (CoreBaseInfo) iter.next();

			// 检查单据是否在工作流中
			FDCClientUtils
					.checkBillInWorkflow(this, element.getId().toString());
			if (((element.getString("splitState"))
					.equals(CostSplitStateEnum.NOSPLIT_VALUE))) {
				MsgBox.showWarning(this, FDCSplitClientHelper
						.getRes("partSplited"));
				SysUtil.abort();
			}
		}

		for (Iterator iter = collnoCostsplit.iterator(); iter.hasNext();) {
			CoreBaseInfo element = (CoreBaseInfo) iter.next();

			// 检查单据是否在工作流中
			FDCClientUtils
					.checkBillInWorkflow(this, element.getId().toString());
			if (((element.getString("splitState"))
					.equals(CostSplitStateEnum.NOSPLIT_VALUE))) {
				MsgBox.showWarning(this, FDCSplitClientHelper
						.getRes("partSplited"));
				SysUtil.abort();
			}
		}
		/*
		 * List splitStateList =
		 * ContractClientUtils.getSelectedIdValues(getMainTable(),
		 * getSplitStateFieldName()); for(Iterator
		 * iter=splitStateList.iterator();iter.hasNext();){ Object
		 * splitState=iter.next();
		 * if(splitState.equals(CostSplitState.NOSPLIT.toString())){
		 * MsgBox.showWarning(this,"存在未拆分的单据，不能进行此项操作！"); SysUtil.abort(); } }
		 */
	}

	protected void audit(List ids) throws Exception {
		List costSplits = new ArrayList();
		List noCostSplits = new ArrayList();
		for (Iterator iter = ids.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			if (isCostSplit(id)) {
				costSplits.add(id);
			}
			if (isNoCostSplit(id)) {
				noCostSplits.add(id);
			}
		}
		ContractCostSplitFactory.getRemoteInstance().audit(costSplits);
		ConNoCostSplitFactory.getRemoteInstance().audit(noCostSplits);

	}

	protected void unAudit(List ids) throws Exception {
		List costSplits = new ArrayList();
		List noCostSplits = new ArrayList();
		for (Iterator iter = ids.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			if (isCostSplit(id)) {
				costSplits.add(id);
			}
			if (isNoCostSplit(id)) {
				noCostSplits.add(id);
			}
		}
		ContractCostSplitFactory.getRemoteInstance().unAudit(costSplits);
		ConNoCostSplitFactory.getRemoteInstance().unAudit(noCostSplits);

	}

	/**
	 * 作废已生成凭证的付款拆分/结算拆分
	 */
	public void actionClearSplit_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		super.actionClearSplit_actionPerformed(e);

		String selectedContractBillID = getSelectedCostBillID();
		String ids = getSelectedKeyValue();
		if (isCostSplit(ids)
				&& SplitClearClientHelper
						.isNewVersionContract(selectedContractBillID)) {
			if (MsgBox
					.showConfirm2(this,
							"此合同相关的拆分（合同拆分、变更拆分、结算拆分、付款拆分）存在拆分期间在当前期间之后的版本，需要将当前期间之后的相关拆分清除，确认是否操作？") == MsgBox.OK) {
				boolean success = true;
				try {
					String result = ClearSplitFacadeFactory.getRemoteInstance()
							.clearPeriodConSplit(selectedContractBillID,
									"contract");
					if (result != null) {
						success = false;
						String msg = "已存在之后期间的付款拆分生成的凭证,凭证号为:"
								+ result + " 。请先删除凭证！";
						if(isAdjustVourcherModel()){
							msg = "已存在之后期间的付款生成的凭证,凭证号为:"
								+ result + " 。请先删除凭证！";
						}
						MsgBox.showError(this, msg);
						SysUtil.abort();
					}
				} catch (Exception ex) {
					success = false;
					handUIException(ex);
				}
				if (success) {
					refresh(e);
					FDCClientUtils.showOprtOK(this);
				}
			}
		} else {
			if (isNoCostSplit(ids) || 
					!(SettledMonthlyHelper.existObject(null,costSplit, getSelectedKeyValue()))) {
				//财务类的单据以及成本类没有月结的要检查是否生成了凭证 by sxhong 2008/12/14
				SplitClearClientHelper.checkClearable(this,
						selectedContractBillID, true);
			} else {
				SplitClearClientHelper.checkClearable(this,
						selectedContractBillID, false);
			}
			String msg = "作废付款拆分/结算拆分将把合同相关拆分作废，若对应的合同有已经生成凭证的付款拆分，则此合同进入待处理流程,待处理合同凭证处理将自动处理已生成凭证的付款拆分，确认是否操作？";
			if(isAdjustVourcherModel()){
				msg = "作废合同拆分将把合同相关拆分作废，若对应的合同存在被付款单引用生成凭证的付款拆分或者生成凭证的调整单，则此合同进入待处理流程，需要财务进行后续凭证处理，确认是否操作？";
			}
			if (MsgBox
					.showConfirm2(
							this,
							msg) == MsgBox.OK) {
				// SplitClearClientHelper.checkClearable(this,
				// selectedContractBillID);
				boolean sccuss = true;
				try {
					ClearSplitFacadeFactory.getRemoteInstance().clearSplitBill(
							selectedContractBillID);
				} catch (Exception ex) {
					sccuss = false;
					handUIException(ex);
				}

				if (sccuss) {
					refresh(e);
					FDCClientUtils.showOprtOK(this);
				}
			}
		}
	}

	public void actionViewInvalid_actionPerformed(ActionEvent e)
			throws Exception {
		IUIWindow testUI = null;
		UIContext uiContext = new UIContext(this);
		// uiContext.put(UIContext.ID, info.getId());
		try {
			testUI = UIFactory
					.createUIFactory(UIFactoryName.NEWTAB)
					.create(
							"com.kingdee.eas.fdc.contract.client.InvalidContractSplitListUI",
							uiContext, null);
			testUI.show();
		} catch (BOSException exe) {
			super.handUIException(exe);// OprtState.EDIT);
		}
	}
	
	
	
	public void actionMeasureSplit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = getSelectedKeyValue();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(FDCSplitClientHelper.getRes("listSplit"));
			SysUtil.abort();
		}
		checkSplit();
		boolean isAddNew = checkBeforeSplit();
		UIContext uiContext = new UIContext(this);
		uiContext.put("isMeasureSplit", Boolean.TRUE);
		if (isAddNew) {
			uiContext.put("costBillID", getSelectedCostBillID());
		} else {
			String costBillID = getSelectedKeyValue();
			//非量价合同请用拆分打开
			FilterInfo myFilter=new FilterInfo();
			myFilter.appendFilterItem("id", getSelectedCostBillID());
			myFilter.appendFilterItem("isMeasureContract", Boolean.TRUE);
			if(!ContractBillFactory.getRemoteInstance().exists(myFilter)){
				FDCMsgBox.showWarning(this, "已拆分的非量价拆分请用拆分进行再次拆分");
				SysUtil.abort();
			}
			uiContext.put(UIContext.ID, costBillID);
		}
		IUIWindow uiWin = UIFactory.createUIFactory(getEditUIModal()).create(
				getEditUIName(), uiContext, null, isAddNew?OprtState.ADDNEW:OprtState.EDIT);
		
		uiWin.show();
	
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionMeasureSplit)) {
			uiContext.put("isMeasureSplit", Boolean.TRUE);
		}
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		if(isAdjustVourcherModel()){
			menuItemClearSplit.setText("作废合同拆分");
			btnClearSplit.setText("作废合同拆分");
		}
		actionClearSplit.setEnabled(true);
		btnClearSplit.setIcon(EASResource.getIcon("imgTbtn_clear"));
		actionViewInvalid.setEnabled(true);

		menuItemClearSplit.setText(menuItemClearSplit.getText() + "(C)");
		menuItemClearSplit.setMnemonic('C');
		menuItemViewInvalid.setText(menuItemViewInvalid.getText() + "(V)");
		menuItemViewInvalid.setMnemonic('V');

		btnAuditResult.setVisible(false);
		btnAuditResult.setEnabled(false);
		actionAuditResult.setVisible(false);
		actionAuditResult.setEnabled(false);
	}
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
		if(this.isFinacialModel()){
			this.actionViewInvalid.setVisible(true);
		}else{
			this.actionViewInvalid.setVisible(false);
		}
	}
	/**
	 * 关闭editui后会自动刷新序时簿界面，而刷新较慢导致关闭EditUI会很慢。所以关闭EditUI时，不自动刷新序时簿 modify by zhiqiao_yang at 2010-09-28
	 * @modifyed Owen_wen 只有在按下F5或者点“刷新”按钮时才刷新。
	 */
	protected void refresh(ActionEvent e) throws Exception {
		if (e != null && (e.getSource().equals(this.menuItemRefresh) || e.getSource().equals(this.btnRefresh)))
			super.refresh(e);
	}
	protected void filterByBillState(EntityViewInfo ev) {
		 super.filterByBillState(ev);
		 if(ev != null && ev.getFilter() != null)
			 try
		 {
				 FilterInfo f = new FilterInfo();
				 f.getFilterItems().add(new FilterItemInfo("contractBill.state", "11BACK", CompareType.NOTEQUALS));
				 ev.getFilter().mergeFilter(f, "and");
		                     }
		 catch(BOSException e)
		 {
			 handUIExceptionAndAbort(e);
			 }
	}
	public void actionViewContract_actionPerformed(ActionEvent e)
	throws Exception {
BOSUuid.create(new ParamInfo().getBOSType());
checkSelected();
int selectRows[] = KDTableUtil.getSelectedRows(getMainTable());
if (selectRows.length > 1) {
	MsgBox.showWarning(this, FDCSplitClientHelper
			.getRes("multiRowSelected"));
	SysUtil.abort();
} else if (selectRows.length == 1) {
	Object obj = getMainTable()
	.getCell(selectRows[0], "id").getValue();
if (obj != null) {
String contractId = obj.toString();
UIContext uiContext = new UIContext(this);
uiContext.put(UIContext.ID, contractId);
IUIWindow testUI = UIFactory
		.createUIFactory(UIFactoryName.NEWTAB)
		.create(
				com.kingdee.eas.fdc.contract.client.ContractBillEditUI.class
						.getName(), uiContext, null,
				OprtState.VIEW);
testUI.show();
}
}
}
}