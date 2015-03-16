/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.client.SettlementCostSplitEditUI;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class InvalidPaymentSplitListUI extends
		AbstractInvalidPaymentSplitListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(InvalidPaymentSplitListUI.class);

	private static final BOSObjectType costSplit = new PaymentSplitInfo()
			.getBOSType();

	/**
	 * 成本科目合同付款拆分
	 */
	private static final int PAYMENTSPLIT = 1;

	/**
	 * 成本科目无文本合同付款拆分
	 */
	private static final int PAYMENTSPLIT_WITHOUTTEXT = 2;

	/**
	 * 财务科目（非成本科目）合同付款拆分
	 */
	private static final int PAYMENTNOCOSTSPLIT = 3;

	/**
	 * 财务科目（非成本科目）无文本合同付款拆分
	 */
	private static final int PAYMENTNOCOSTSPLIT_WITHOUTTEXT = 4;

	private static final BOSObjectType withoutTxtConBosType = new ContractWithoutTextInfo()
			.getBOSType();

	private BOSUuid splitBillNullID = BOSUuid.create("null");

	/**
	 * output class constructor
	 */
	public InvalidPaymentSplitListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		if (isCostSplit()) {
			return com.kingdee.eas.fdc.finance.PaymentSplitFactory
					.getRemoteInstance();
		} else {
			return com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory
					.getRemoteInstance();
		}
	}

	protected void audit(List ids) throws Exception {

	}

	protected void unAudit(List ids) throws Exception {

	}

	protected ICoreBase getBizInterface() throws Exception {
		return getRemoteInterface();
	}

	protected String getKeyFieldName() {
		// TODO 自动生成方法存根
		// return super.getKeyFieldName();

		return "costSplit.id";
	}

	protected String getEditUIName() {
		switch (getSplitType()) {
		case PAYMENTSPLIT:
			return com.kingdee.eas.fdc.finance.client.PaymentSplitEditUI.class
					.getName();
		case PAYMENTSPLIT_WITHOUTTEXT:
			return com.kingdee.eas.fdc.finance.client.PaymentSplitWithoutTxtConEditUI.class
					.getName();
		case PAYMENTNOCOSTSPLIT:
			return com.kingdee.eas.fdc.finance.client.PaymentNoCostSplitEditUI.class
					.getName();
		case PAYMENTNOCOSTSPLIT_WITHOUTTEXT:
			return com.kingdee.eas.fdc.finance.client.PaymentNoCostSplitWithoutTxtConEditUI.class
					.getName();
		default:
			return null;
		}
	}

	private int getSplitType() {
		KDTable table = getMainTable();
		boolean isConWithoutTxt = false;
		String contractId = null;
		int selectRows[] = KDTableUtil.getSelectedRows(table);
		if (selectRows.length == 1) {
			Object obj = table.getCell(selectRows[0], "contractId").getValue();
			if (obj != null) {
				contractId = obj.toString();
				isConWithoutTxt = BOSUuid.read(contractId).getType().equals(
						withoutTxtConBosType);
			}
		}

		if (contractId == null)
			return 0;
		if (isConWithoutTxt) {
			// if(true) return PAYMENTSPLIT_WITHOUTTEXT;
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", contractId));
			filter.getFilterItems().add(
					new FilterItemInfo("isCostSplit", 1 + ""));
			try {
				if (ContractWithoutTextFactory.getRemoteInstance().exists(
						filter)) {
					return PAYMENTSPLIT_WITHOUTTEXT;
				} else {
					return PAYMENTNOCOSTSPLIT_WITHOUTTEXT;
				}
			} catch (Exception e) {
				handUIException(e);
			}
		} else {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", contractId));
			filter.getFilterItems().add(
					new FilterItemInfo("isCoseSplit", 1 + ""));
			try {
				if (ContractBillFactory.getRemoteInstance().exists(filter)) {
					return PAYMENTSPLIT;
				} else {
					return PAYMENTNOCOSTSPLIT;
				}
			} catch (Exception e) {
				handUIException(e);
			}
		}

		return 0;
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
		Object costSplit = getMainTable().getCell(selectRows[0],
				"contractBill.isCostSplit").getValue();
		if (costSplit instanceof Boolean) {
			isCostSplit = ((Boolean) costSplit).booleanValue();
		}
		return isCostSplit;
	}

	protected String getCurProjectPath() {
		// TODO 自动生成方法存根
		// return super.getCurProjectPath();

		return "curProject";
	}

	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo(getCurProjectPath() + ".isEnabled",
				Boolean.TRUE));
		return filter;
	}

	protected void treeSelectChange() throws Exception {
		execQuery();

		if (getMainTable().getRowCount() > 0) {
			getMainTable().getSelectManager().select(0, 0);
		}
		setSplitStateColor();
	}

	protected FilterInfo getTreeFilter() throws Exception {

		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();

		Object obj = getUIContext().get("isContract");
		if (obj.equals(Boolean.TRUE)) {
			filter.getFilterItems().add(
					new FilterItemInfo("test", null, CompareType.NOTEQUALS));
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("test", null, CompareType.EQUALS));
		}
		/*
		 * 工程项目树
		 */
		if (getProjSelectedTreeNode() != null
				&& getProjSelectedTreeNode().getUserObject() instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) getProjSelectedTreeNode()
					.getUserObject();

			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				BOSUuid id = ((OrgStructureInfo) projTreeNodeInfo).getUnit()
						.getId();

				Set idSet = FDCClientUtils.genOrgUnitIdSet(id);
				filterItems.add(new FilterItemInfo("company.id", idSet,
						CompareType.INCLUDE));
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				BOSUuid id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo(getCurProjectPath() + ".id",
						idSet, CompareType.INCLUDE));
			}
		}

		filterItems.add(new FilterItemInfo(getCurProjectPath() + ".isEnabled",
				Boolean.TRUE));
		filterItems.add(new FilterItemInfo("costSplit.state",
				FDCBillStateEnum.INVALID_VALUE, CompareType.EQUALS));
		return filter;
		// filter=new FilterInfo();
		// mainQuery.setFilter(filter);
		// execQuery();
		//
		// if (getMainTable().getRowCount() > 0) {
		// getMainTable().getSelectManager().select(0, 0);
		// }
		// setSplitStateColor();
	}

	protected SorterItemCollection getSorter() {
		// TODO 排序方式
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(getSplitStateFieldName()));
		sorter.add(new SorterItemInfo(getCostBillStateFieldName()));
		return sorter;
	}

	protected String getBillStatePropertyName() {
		// TODO 自动生成方法存根
		return "costSplit.state";
	}

	protected String getCostBillStateFieldName() {
		// TODO 自动生成方法存根
		// return super.getCostBillStateFieldName();

		return "billStatus";
	}

	protected String getSplitStateFieldName() {
		return "costSplit.splitState";
	}

	protected void getRowSetBeforeFillTable(IRowSet rowSet) {
		// TODO 自动生成方法存根
		super.getRowSetBeforeFillTable(rowSet);
		String state = null;
		String splitState = null;
		try {
			rowSet.beforeFirst();

			while (rowSet.next()) {
				state = rowSet.getString("costSplit.state");
				splitState = rowSet.getString(getSplitStateFieldName());
				if (splitState == null) {
					// rowSet.updateString("costSplit.splitState",CostSplitState.NOSPLIT.toString());
					// rowSet.updateObject("costSplit.id",splitBillNullID);
					rowSet.updateString(getSplitStateFieldName(),
							CostSplitStateEnum.NOSPLIT.toString());
					rowSet.updateObject(getKeyFieldName(), splitBillNullID);
				} else if (splitState.equals(CostSplitStateEnum.ALLSPLIT_VALUE)) {
					rowSet.updateString(getSplitStateFieldName(),
							CostSplitStateEnum.ALLSPLIT.toString());
				} else if (splitState
						.equals(CostSplitStateEnum.PARTSPLIT_VALUE)) {
					rowSet.updateString(getSplitStateFieldName(),
							CostSplitStateEnum.PARTSPLIT.toString());
				}
				if (state == null) {
					// 不处理
				} else if (state.equals(FDCBillStateEnum.INVALID_VALUE)) {
					rowSet.updateString("costSplit.state",
							FDCBillStateEnum.INVALID.toString());
				}
			}
			rowSet.beforeFirst();

		} catch (SQLException e) {
			handleException(e);
		} catch (UuidException e) {
			handleException(e);
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		actionQuery.setVisible(true);
		actionQuery.setEnabled(true);
		actionVoucher.setVisible(false);
		actionDelVoucher.setVisible(false);
		actionTraceDown.setVisible(true);
		setSplitStateColor();
	}

	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		if (e.getSource() == btnRefresh || e.getSource() == menuItemRefresh) {
			actionVoucher.setVisible(false);
			actionDelVoucher.setVisible(false);
			setSplitStateColor();
		}
	}

	public void refreshList() throws Exception {
		super.refreshList();
		actionVoucher.setVisible(false);
		actionDelVoucher.setVisible(false);
		setSplitStateColor();
	}

	protected boolean isVoucherVisible() {
		return false;
	}

	public void refreshListForOrder() throws Exception {
		// TODO 自动生成方法存根
		super.refreshListForOrder();

		setSplitStateColor();
	}

	protected void setSplitStateColor() {

		// 完全拆分合同（绿色）、部分拆分合同（黄色）、已审批未拆分合同（桔色）、未审批合同（蓝色）、未提交合同（白色）
		String splitState;
		String costBillState;

		// IColumn col = tblMain.getColumn("costSplit.splitState")
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);

			// 成本单据状态
			// ICell cell = row.getCell("state");
			ICell cell = row.getCell(getCostBillStateFieldName());
			if (cell.getValue() != null) {
				costBillState = cell.getValue().toString();
			} else {
				costBillState = "";
			}

			// 成本拆分状态
			cell = row.getCell(getSplitStateFieldName());
			if (cell.getValue() == null
					|| cell.getValue().toString().equals("")) {
				splitState = CostSplitStateEnum.NOSPLIT.toString();
				cell.setValue(splitState);
			} else {
				splitState = cell.getValue().toString();
			}
			if (splitState.equals(CostSplitStateEnum.ALLSPLIT.toString())) {
				row.getStyleAttributes().setBackground(
						FDCSplitClientHelper.COLOR_ALLSPLIT);
			} else if (splitState.equals(CostSplitStateEnum.PARTSPLIT
					.toString())) {
				row.getStyleAttributes().setBackground(
						FDCSplitClientHelper.COLOR_PARTSPLIT);
			} else {
				row.getStyleAttributes().setBackground(
						FDCSplitClientHelper.COLOR_NOSPLIT);
			}

		}

	}

	protected void updateButtonStatus() {
		super.updateButtonStatus();
		actionEdit.setEnabled(false);
		actionAddNew.setEnabled(false);
		actionRemove.setEnabled(false);
	}

	protected void initTable() {

	}
}