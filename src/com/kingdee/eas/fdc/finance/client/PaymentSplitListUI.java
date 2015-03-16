/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTViewManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.dap.DAPTransImpl;
import com.kingdee.eas.base.param.ParamInfo;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewFactory;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo;
import com.kingdee.eas.fdc.basedata.CostSplitException;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IBeforeAccountView;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ClearSplitFacadeFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.contract.client.SplitClearClientHelper;
import com.kingdee.eas.fdc.finance.IPaymentNoCostSplit;
import com.kingdee.eas.fdc.finance.IPaymentSplit;
import com.kingdee.eas.fdc.finance.IPaymentSplitEntry;
import com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitVoucherHelper;
import com.kingdee.eas.fdc.finance.SettForPayVoucherEntryInfo;
import com.kingdee.eas.fdc.finance.SettForPayVoucherFactory;
import com.kingdee.eas.fdc.finance.SettForPayVoucherInfo;
import com.kingdee.eas.fdc.finance.SettledMonthlyHelper;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fm.common.client.FMClientHelper;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.IDAPTrans;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 合同付款单拆分序时薄界面
 */
public class PaymentSplitListUI extends AbstractPaymentSplitListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PaymentSplitListUI.class);

	private boolean isCost = true;

	private BOSUuid splitBillNullID = BOSUuid.create("null");

	private static final String VOUCHERFLAG = "Fivouchered";
	private static final String REFERFLAG = "voucherRefer";

	private IDAPTrans dapTrans = null;

	// 是否DAP转换，如果是，则不允许分录生成凭证方式。
	private boolean isDAPTrans = false;

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

	public PaymentSplitListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		initParam = PaymentSplitFactory.getRemoteInstance().fetchInitParam();
//		if ((!SysContext.getSysContext().getCurrentOrgUnit()
//				.isIsCompanyOrgUnit())
//				&& (SysContext.getSysContext().getCurrentOrgUnit()
//						.isIsCostOrgUnit())) {
//			MsgBox.showWarning(this, PaymentBillClientUtils.getRes("CostUnit"));
//			SysUtil.abort();
//		}
		super.onLoad();
		if (isFinacialModel()) {
			actionVoucher.setEnabled(true);
			actionDelVoucher.setEnabled(true);
		} else {
			if (isIncorporation) {
				FDCClientHelper.setActionEnable(new ItemAction[] {
						actionClearSplit, actionViewInvalid }, true);
			} else {
				FDCClientHelper.setActionEnable(new ItemAction[] {
						actionClearSplit}, false);
			}
			FDCClientHelper.setActionEnable(new ItemAction[] { actionVoucher,
					actionDelVoucher, actionProcess, actionSettle,
					actionGuarante, actionNoCostProcess, actionNoCostSettle,
					actionNoCostGuarante, actionFlowProcess, actionFlowSettle,
					actionNoTextCost, actionNoTextNoCost, actionFlowNoText,actionViewInvalid },
					false);
			menuBatchVoucher.setVisible(false);
		}
		Object obj = getUIContext().get(UIContext.UICLASSPARAM);
		if (obj == null) {
			return;
		}
		if (obj.equals("isContract=\"true\"")) {
			if (SysContext.getSysContext().getCurrentOrgUnit()
					.isIsCompanyOrgUnit()
					&& SysContext.getSysContext().getCurrentFIUnit()
							.isIsBizUnit()) {
				actionSplitAll.setEnabled(true);
				actionSplitAll.setVisible(true);
				FDCClientHelper.setActionEnable(new ItemAction[] {
						actionProcess, actionSettle, actionGuarante,
						actionNoCostProcess, actionNoCostSettle,
						actionNoCostGuarante, actionFlowProcess,
						actionFlowSettle }, true);
				FDCClientHelper.setActionEnable(new ItemAction[] {
						actionNoTextCost, actionNoTextNoCost, actionFlowNoText,
						actionClearSplit }, false);
				// ,actionClearSplit }, false);
			} else {
				actionSplitAll.setEnabled(false);
				actionSplitAll.setVisible(false);
				menuBatchVoucher.setVisible(false);
				FDCClientHelper.setActionEnable(new ItemAction[] {
						actionProcess, actionSettle, actionGuarante,
						actionNoCostProcess, actionNoCostSettle,
						actionNoCostGuarante, actionFlowProcess,
						actionFlowSettle, actionFlowNoText, actionNoTextCost,
						actionNoTextNoCost, actionClearSplit }, false);
				// , actionClearSplit }, false);
				menuBatchVoucher.setVisible(false);
			}
		} else {
			if (SysContext.getSysContext().getCurrentOrgUnit()
					.isIsCompanyOrgUnit()
					&& SysContext.getSysContext().getCurrentFIUnit()
							.isIsBizUnit()) {
				FDCClientHelper.setActionEnable(new ItemAction[] {
						actionProcess, actionSettle, actionGuarante,
						actionNoCostProcess, actionNoCostSettle,
						actionNoCostGuarante, actionFlowProcess,
						actionFlowSettle }, false);
				FDCClientHelper.setActionEnable(
						new ItemAction[] { actionNoTextCost,
								actionNoTextNoCost, actionFlowNoText }, true);
			} else {
				FDCClientHelper.setActionEnable(new ItemAction[] {
						actionProcess, actionSettle, actionGuarante,
						actionNoCostProcess, actionNoCostSettle,
						actionNoCostGuarante, actionFlowProcess,
						actionFlowSettle, actionFlowNoText, actionNoTextCost,
						actionNoTextNoCost }, false);
				menuBatchVoucher.setVisible(false);
			}
			actionSplitAll.setEnabled(false);
			actionSplitAll.setVisible(false);
		}

		btnSplitAll.setEnabled(false);
		btnSplitAll.setVisible(false);

		obj = getUIContext().get("MainMenuName");
		if (!FDCHelper.isEmpty(obj)) {
			setUITitle(obj.toString());
		}
		tblMain.getColumn("isContractWithoutText").getStyleAttributes()
				.setHided(true);
		
		dapTrans = new DAPTransImpl(this);
		dapTrans.init();
		tblMain.checkParsed();
		setAdjustVourcherModelActionState();
	}

	/**
	 * 判断选择行是不是无文本合同，选择多行返回false
	 * 
	 * @author sxhong Date 2006-12-1
	 * @param table
	 * @return isConWithoutTxt
	 */
	private static final BOSObjectType withoutTxtConBosType = new ContractWithoutTextInfo()
			.getBOSType();

	/**
	 * 判断选中行是否是无文件合同
	 * @param table
	 * @return true 是无文本， false不是无文本合同
	 */
	private boolean isConWithoutTxt(KDTable table) {
		boolean isConWithoutTxt = false;
		int selectRows[] = KDTableUtil.getSelectedRows(table);
		if (selectRows.length == 1) {
			Object obj = table.getCell(selectRows[0], "contractId").getValue();
			if (obj != null) {
				String contractId = obj.toString();
				isConWithoutTxt = BOSUuid.read(contractId).getType().equals(
						withoutTxtConBosType);
			}
		}
		return isConWithoutTxt;
	}

	private int getSplit() {
		int cost = 0;
		int noCost = 0;
		KDTable table = getMainTable();
		boolean isConWithoutTxt = false;
		String contractId = null;
		int selectRows[] = KDTableUtil.getSelectedRows(table);
		int size = selectRows.length;
		for (int i = 0; i < size; i++) {
			// if(selectRows.length==1){
			Object obj = table.getCell(selectRows[i], "contractId").getValue();
			if (obj != null) {
				contractId = obj.toString();
				isConWithoutTxt = BOSUuid.read(contractId).getType().equals(
						withoutTxtConBosType);
			}
			// }

			if (contractId == null)
				return 0;
			if (isConWithoutTxt) {
				// if(true) return PAYMENTSPLIT_WITHOUTTEXT;
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", contractId));
				filter.getFilterItems().add(
						new FilterItemInfo("isCostSplit", 1 + ""));
				try {
					if (ContractWithoutTextFactory.getRemoteInstance().exists(
							filter)) {
						cost++;
					} else {
						noCost++;
					}
				} catch (Exception e) {
					handUIException(e);
				}
			} else {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", contractId));
				filter.getFilterItems().add(
						new FilterItemInfo("isCoseSplit", new Integer(1)));
				try {
					if (ContractBillFactory.getRemoteInstance().exists(filter)) {
						cost++;
					} else {
						noCost++;
					}
				} catch (Exception e) {
					handUIException(e);
				}
			}
		}
		if (cost == size) {
			return 1;
		} else if (noCost == size) {
			return 3;
		} else if (size > 0) {
			MsgBox.showWarning("不能同时处理成本拆分和非成本拆分，请重新选择！");
			SysUtil.abort();
			return 0;
		} else {
			SysUtil.abort();
			return 0;
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

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output actionAudit_actionPerformed
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
	}

	/**
	 * output actionUnAudit_actionPerformed
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
	}

	/**
	 * output getEditUIName method
	 */
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

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return getRemoteInterface();
	}

	/**
	 * output getKeyFieldName method
	 */
	/*
	 * protected String getKeyFieldName() { return "id"; }
	 */

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.finance.PaymentSplitInfo objectValue = new com.kingdee.eas.fdc.finance.PaymentSplitInfo();
		return objectValue;
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
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillListUI#getRemoteInterface()
	 */
	protected ICoreBase getRemoteInterface() throws BOSException {
		// switch (getSplitType()){
		switch (getSplit()) {
		case PAYMENTSPLIT:
		case PAYMENTSPLIT_WITHOUTTEXT:
			return com.kingdee.eas.fdc.finance.PaymentSplitFactory
					.getRemoteInstance();

		case PAYMENTNOCOSTSPLIT:
		case PAYMENTNOCOSTSPLIT_WITHOUTTEXT:
			return com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory
					.getRemoteInstance();
		default:
			return com.kingdee.eas.fdc.finance.PaymentSplitFactory
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
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillListUI#getCostBillStateFieldName()
	 */
	protected String getCostBillStateFieldName() {
		// TODO 自动生成方法存根
		// return super.getCostBillStateFieldName();

		return "billStatus";
	}

	// 显示合同名称和合同编码列，暂时去掉。
	/*
	 * public void onGetRowSet(IRowSet rowSet) { try { rowSet.beforeFirst();
	 * while (rowSet.next()) { String contractId=rowSet.getString("contractId");
	 * BOSObjectType type = BOSUuid.read(contractId).getType();
	 * if(type.equals(new ContractBillInfo().getBOSType())){ ContractBillInfo
	 * info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new
	 * ObjectUuidPK(BOSUuid.read(contractId)));
	 * rowSet.updateString("contractNumber",info.getNumber());
	 * rowSet.updateString("contractName",info.getName()); }else{
	 * ContractWithoutTextInfo info =
	 * ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new
	 * ObjectUuidPK(BOSUuid.read(contractId)));
	 * rowSet.updateString("contractNumber",info.getNumber());
	 * rowSet.updateString("contractName",info.getName()); } }
	 * rowSet.beforeFirst(); } catch (SQLException e) { e.printStackTrace(); }
	 * catch (EASBizException e) { e.printStackTrace(); } catch (BOSException e) {
	 * e.printStackTrace(); } catch (UuidException e) { e.printStackTrace(); }
	 * super.onGetRowSet(rowSet); }
	 */
	public String[] getMergeColumnKeys() {
		return new String[0];
	}

	/**
	 * 修改PBG028112：合同付款拆分序时簿界面工具栏及菜单栏上，缺少“拆分”“删除”“查看作废付款拆分”按钮
	 * 在updateBtnStatu中解除控制  -by neo
	 */
	protected void updateButtonStatus() {
		 super.updateButtonStatus();
		 menuEdit.setVisible(true);
//		 actionViewInvalid.setVisible(true);
//		 actionViewInvalid.setEnabled(true);
		// 如果是虚体财务组织，则不能增、删、改
//		if ((!SysContext.getSysContext().getCurrentOrgUnit()
//				.isIsCompanyOrgUnit())
//				|| (!SysContext.getSysContext().getCurrentFIUnit()
//						.isIsBizUnit())) {
//			actionAddNew.setEnabled(false);
//			actionEdit.setEnabled(false);
//			actionRemove.setEnabled(false);
//			actionCostSplit.setEnabled(false);
//			actionAddNew.setVisible(false);
//			actionEdit.setVisible(false);
//			actionRemove.setVisible(false);
//			actionCostSplit.setVisible(false);
//			menuEdit.setVisible(false);
		// else
		// tblMain.checkParsed();
		// tblMain.checkParsed(true);
		
		if(isAdjustVourcherModel()){
//			tblMain.getColumn("fiVouchered").getStyleAttributes().setHided(true);
			tblMain.getColumn(REFERFLAG).getStyleAttributes().setHided(false);
		}else{
			tblMain.getColumn(REFERFLAG).getStyleAttributes().setHided(true);
		}
		/*if(isSimpleFinacialMode()){
			tblMain.getColumn(REFERFLAG).getStyleAttributes().setHided(true);
		}*/
		if (isFinacialModel()) {
			actionVoucher.setEnabled(true);
			actionDelVoucher.setEnabled(true);
		}
		actionVoucher.setVisible(false);
		actionVoucher.setEnabled(false);
		if (isFinacialModel()&&!this.isAdjustVourcherModel()) {
			tblMain.getColumn("fiVouchered").getStyleAttributes().setHided(false);
		} else{
			tblMain.getColumn("fiVouchered").getStyleAttributes().setHided(true);
		}
		
		actionRemove.setEnabled(true);
		actionRemove.setVisible(true);
		
	}

	protected void treeSelectChange() throws Exception {
		// 金向平改了框架内，添加了getTreeFilter()在最后合并 by sxhong 2008-1-1
		// filter=new FilterInfo();
		execQuery();
		if (getMainTable().getRowCount() > 0) {
			// int size = ;
			for (int i = getMainTable().getRowCount() - 1; i >= 0; i--) {
				if (getMainTable().getCell(i, "costSplit.state").getValue() != null
						&& getMainTable().getCell(i, "costSplit.state")
								.getValue().toString().equals(
										FDCBillStateEnum.INVALID_VALUE)) {
					getMainTable().removeRow(i);
				}
			}

			getMainTable().getSelectManager().select(0, 0);
		}
	}

	protected FilterInfo getTreeFilter() throws Exception {

		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();

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

		return filter;
	}

	protected void freezeTableColumn() {
		int number_col_index = getMainTable().getColumnIndex("fdcPayReqNumber");
		KDTViewManager viewManager = getMainTable().getViewManager();
		// viewManager.setFreezeView(-1, state_col_index);
		// viewManager.setFreezeView(-1, number_col_index);
		viewManager.setFreezeView(-1, number_col_index);
	}

	protected void drawColorPanel() {
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(0);
		flowLayout.setHgap(10);
		// flowLayout.setVgap(5);
		// 将每个颜色放到一个Lable内
		colorPanel.setLayout(flowLayout);
		drawALogo(FDCSplitClientHelper.getRes("allSplitState"),
				FDCSplitClientHelper.COLOR_ALLSPLIT);
		// drawALogo(FDCSplitClientHelper.getRes("partSplitState"),FDCSplitClientHelper.COLOR_PARTSPLIT);
		// drawALogo(FDCSplitClientHelper.getRes("auditNotSplit"),FDCSplitClientHelper.COLOR_AUDITTED);
		// drawALogo(FDCSplitClientHelper.getRes("notAudit"),FDCSplitClientHelper.COLOR_UNAUDITTED);
		drawALogo(FDCSplitClientHelper.getRes("notSubmmit"),
				FDCSplitClientHelper.COLOR_NOSPLIT);

	}
	
	/**
	 * 判断选中行是否是成本拆分
	 * @return true是成本拆分，false非成本拆分 
	 */
	private boolean selectedRowIsCost(){
		int rowIndex = this.getMainTable().getSelectManager().getActiveRowIndex();
		return ((Boolean)this.getMainTable().getRow(rowIndex).getCell("contractBill.isCostSplit").getValue()).booleanValue();
	}
	
	/**
	 * 获取选中行的合同id
	 * @return
	 * @author owen_wen 2010-11-30
	 */
	private String getContractBillId(){
		int selectedRowIndex = this.getMainTable().getSelectManager().getActiveRowIndex();
		String contractId = this.getMainTable().getRow(selectedRowIndex).getCell("contractId").getValue().toString();
		return contractId;
	}
	
	public void actionCostSplit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (!isConWithoutTxt(getMainTable())) {
			checkContract();
			// Added By Owen_wen 2010-12-03
			FDCSplitClientHelper.checkAndShowMsgBeforeSplit(this.getContractBillId(), this, this.selectedRowIsCost());
		}
		boolean isAddNew = checkBeforeSplit();
		if (isAddNew && (!isConWithoutTxt(getMainTable()))) {
			int selectRows[] = KDTableUtil.getSelectedRows(getMainTable());
			String id = getMainTable().getCell(selectRows[0], "id").getValue()
					.toString();
			PaymentSplitClientVerify.checkBeforeSplitSettle(this, id, this.isFinacialModel(), this.isAdjustVourcherModel());
			
		} else if (!isAddNew) {
			//检查付款拆分对应的付款单是否已经生成过凭证，本来以为可以直接从kdTblMain的列fiVouchered来进行判断，但是
			//竟然是假的，那个列在某些场景下是不会有值的。啊呜啊呜啊呜..  by Cassiel_peng  2008-8-29
			
			String companyId=SysContext.getSysContext().getCurrentFIUnit().getId().toString();
			boolean isSimpleFinacial=FDCUtils.IsSimpleFinacial(null,companyId);
			if(isSimpleFinacial){
				String paymentBillId=(String)this.tblMain.getCell( this.tblMain.getSelectManager().getActiveRowIndex(),"id").getValue();
				SelectorItemCollection selector=new SelectorItemCollection();
				selector.add("fiVouchered");
				PaymentBillInfo paymentBill=PaymentBillFactory.getRemoteInstance().getPaymentBillInfo(new ObjectUuidPK(BOSUuid.read(paymentBillId)));
				if(paymentBill.isFiVouchered()){
					MsgBox.showWarning("此付款单之前已经生成了凭证，重新拆分之后需要进行手工调账！");
				}
			}
			
			// 凭证检查
			List idList = ContractClientUtils.getSelectedIdValues(
					getBillListTable(), getKeyFieldName());
			int size = idList.size();
			for (int i = 0; i < size; i++) {// 凭证检查
				String id = idList.get(i).toString();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", id));
				filter.getFilterItems().add(
						new FilterItemInfo(VOUCHERFLAG, Boolean.TRUE));
				filter.getFilterItems().add(
						new FilterItemInfo("state",
								FDCBillStateEnum.INVALID_VALUE,
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("voucherRefer",PaySplitVoucherRefersEnum.ADJUSTBILL_VALUE,CompareType.EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("voucherRefer",PaySplitVoucherRefersEnum.PAYMENTBILL_VALUE,CompareType.EQUALS));
				
				filter.setMaskString("#0 and ((#1 and #2) or (#3 or #4))");

				if (getBizInterface().exists(filter)) {
					if(!isSimpleFinacial){
						MsgBox.showWarning(this, "付款拆分已经被引用，不能执行此操作！");
						SysUtil.abort();
					}
				}
				if(!this.isSimpleFinacialMode()&&this.isAdjustVourcherModel()){
					FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
					sqlBuilder.appendSql("select pe.fid from t_fnc_fdccostvoucherentry pe left join t_fnc_paymentsplit ps on pe.fparentid=ps.fpaymentbillid where "); 
					sqlBuilder.appendParam("ps.fid",id);
					sqlBuilder.appendSql(" union all ");
					sqlBuilder.appendSql("select pe.fid from t_fnc_fdccostvoucherentry pe left join t_fnc_paymentnocostsplit ps on pe.fparentid=ps.fpaymentbillid where "); 
					sqlBuilder.appendParam("ps.fid",id);
					IRowSet rowSet = sqlBuilder.executeQuery();
					if(rowSet.size()>0){
						if(!isSimpleFinacial){
							MsgBox.showWarning(this, "对应的付款单已经生成凭证，不能执行此操作！");
							SysUtil.abort();
						}
					}
				}
			}
		}
		super.actionCostSplit_actionPerformed(e);
	}

	private void checkContract() throws Exception {
		int selectRows[] = KDTableUtil.getSelectedRows(getMainTable());
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper.getRes("multiRowSelected"));
			SysUtil.abort();
		}
		
		Object obj = getMainTable().getCell(selectRows[0], "contractId").getValue();
		if (obj != null) {
			String contractId = obj.toString();
			SelectorItemCollection selectorCon = new SelectorItemCollection();
			selectorCon.add("id");
			selectorCon.add("conSplitExecState");
			ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(
					new ObjectUuidPK(BOSUuid.read(contractId)), selectorCon);
			if (info.getConSplitExecState().equals(ConSplitExecStateEnum.INVALID)) {
				MsgBox.showWarning(this, "合同已经进入待处理流程，请先到待处理合同对此合同进行凭证处理！");
				SysUtil.abort();
			}
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill", contractId));
			filter.getFilterItems().add(new FilterItemInfo("splitState", CostSplitStateEnum.ALLSPLIT_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
			boolean hasAllSplit = false;
			int splitType = getSplitType();
			if (splitType == PAYMENTSPLIT) {
				hasAllSplit = ContractCostSplitFactory.getRemoteInstance().exists(filter);
				if (!hasAllSplit) {
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("amount");
					final ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance()
							.getContractBillInfo(new ObjectUuidPK(contractId), selector);
					if (contractBillInfo.getAmount() == null
							|| contractBillInfo.getAmount().compareTo(FDCHelper.ZERO) == 0) {
						FilterInfo f = new FilterInfo();
						f.getFilterItems().add(new FilterItemInfo("contractChange.contractBill.id", contractId));
						f.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
						hasAllSplit = ConChangeSplitFactory.getRemoteInstance().exists(f);
					}
				}
			} else if (splitType == PAYMENTNOCOSTSPLIT) {
				hasAllSplit = ConNoCostSplitFactory.getRemoteInstance().exists(filter);
			}

			if (!hasAllSplit) {
				MsgBox.showWarning(this, FDCSplitClientHelper.getRes("conNotSplited")); // 对应的合同未完全拆分，不能进行此操作！
				SysUtil.abort();
			}
		}
	}

	protected boolean checkBeforeSplit() throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper
					.getRes("multiRowSelected"));
			SysUtil.abort();
		}
		boolean isAddNew = super.checkBeforeSplit();
		String billId = getMainTable().getCell(selectRows[0], "id").getValue()
				.toString();
		if (isAddNew) {
			// 到数据库内进行检查
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("paymentBill.id", billId));
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
				&& isCostSplit(getSelectedKeyValue())
				&& SettledMonthlyHelper.existObject(null, costSplit,
						getSelectedKeyValue())) {
			MsgBox.showWarning(this,
					"财务成本月结已经引用本数据，不能修改，如需修改，请把此付款对应的合同进入待处理流程！");
			SysUtil.abort();
		}
		return isAddNew;
	}

	protected void getRowSetBeforeFillTable(IRowSet rowSet) {
		super.getRowSetBeforeFillTable(rowSet);
		try {
			rowSet.beforeFirst();
			while (rowSet.next()) {
				String contractId = rowSet.getString("contractId");
				if (contractId != null
						&& BOSUuid.read(contractId).getType().equals(
								withoutTxtConBosType)) {
					rowSet.updateObject("isContractWithoutText", Boolean.TRUE);
				}
				String refer = rowSet.getString(REFERFLAG);
				if(PaySplitVoucherRefersEnum.ADJUSTBILL_VALUE.equals(refer)){
					rowSet.updateObject(REFERFLAG,PaySplitVoucherRefersEnum.ADJUSTBILL);
				}else if(PaySplitVoucherRefersEnum.PAYMENTBILL_VALUE.equals(refer)){
					rowSet.updateObject(REFERFLAG,PaySplitVoucherRefersEnum.PAYMENTBILL);
				}else{
					rowSet.updateObject(REFERFLAG,PaySplitVoucherRefersEnum.NOTREFER);
				}
			}
			rowSet.beforeFirst();
		} catch (Exception e) {
			handUIException(e);
		}

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
		CoreBaseCollection collCostsplit = new CoreBaseCollection();
		CoreBaseCollection collnoCostsplit = new CoreBaseCollection();
		Set idSetCostSplit = ContractClientUtils.listToSet(costSplits);
		Set idSetNoCostSplit = ContractClientUtils.listToSet(noCostSplits);
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("splitState");
		FilterInfo filter = new FilterInfo();
		if (idSetCostSplit.size() > 0) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", idSetCostSplit,
							CompareType.INCLUDE));
			view.setFilter(filter);
			collCostsplit = PaymentSplitFactory.getRemoteInstance()
					.getCollection(view);

		}
		filter = new FilterInfo();
		if (idSetNoCostSplit.size() > 0) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", idSetNoCostSplit,
							CompareType.INCLUDE));
			view.setFilter(filter);
			collnoCostsplit = PaymentNoCostSplitFactory.getRemoteInstance()
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
		PaymentSplitFactory.getRemoteInstance().audit(costSplits);
		PaymentNoCostSplitFactory.getRemoteInstance().audit(noCostSplits);

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
		PaymentSplitFactory.getRemoteInstance().unAudit(costSplits);
		PaymentNoCostSplitFactory.getRemoteInstance().unAudit(noCostSplits);

	}

	private static final BOSObjectType costSplit = new PaymentSplitInfo()
			.getBOSType();

	private boolean isCostSplit(String id) {
		return id == null ? false : BOSUuid.read(id).getType()
				.equals(costSplit);
	}

	private boolean isNoCostSplit(String id) {
		return id == null ? false : !BOSUuid.read(id).getType().equals(
				costSplit);
	}

	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		int type = getSplit();

		PaymentSplitInfo splitInfo = new PaymentSplitInfo();
		IPaymentSplit split = PaymentSplitFactory.getRemoteInstance();

		PaymentNoCostSplitInfo noSplitInfo = new PaymentNoCostSplitInfo();
		IPaymentNoCostSplit noSplit = PaymentNoCostSplitFactory
				.getRemoteInstance();

		int size = idList.size();
		for (int i = 0; i < size; i++) {// 检查
			String id = idList.get(i).toString();

			FilterInfo filterSplit = new FilterInfo();
			filterSplit.getFilterItems().add(new FilterItemInfo("id", id));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID));
			if (getBizInterface().exists(filterSplit)) {
				MsgBox.showWarning(this, "存在已经作废的记录，不能生成凭证！");
				SysUtil.abort();
			}

			IObjectPK pk = new ObjectUuidPK(BOSUuid.read(id));
			if (!getBizInterface().exists(pk)) {
				MsgBox.showWarning(this, "存在未拆分的付款单，不能生成凭证！");
				SysUtil.abort();
			} else {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
				selector.add("paymentBill.id");
				selector.add("paymentBill.contractBillId");
				selector.add("paymentBill.billStatus");
				selector.add("paymentBill.fiVouchered");
				selector.add("paymentBill.fdcPayType.payType.id");
				selector.add("isProgress");
				selector.add("isConWithoutText");
				if (type == 1) {
					splitInfo = split.getPaymentSplitInfo(pk, selector);
					if (splitInfo.isIsConWithoutText()) {
						FilterInfo noTextFilter = new FilterInfo();
						noTextFilter.getFilterItems().add(
								new FilterItemInfo("id", splitInfo
										.getPaymentBill().getContractBillId()));
						noTextFilter.getFilterItems().add(
								new FilterItemInfo("isNeedPaid", Boolean.TRUE));
						if (ContractWithoutTextFactory.getRemoteInstance()
								.exists(noTextFilter)) {
							continue;
						} else if (!(splitInfo.getPaymentBill().getBillStatus()
								.equals(BillStatusEnum.PAYED))) {
							MsgBox.showWarning("存在未付款的付款单，不能生成凭证！");
							SysUtil.abort();
						} else if (splitInfo.getPaymentBill().isFiVouchered()) {
							MsgBox.showWarning("对应的付款单已经生成凭证，不能生成凭证！");
							SysUtil.abort();
						} else {
							continue;
						}
					} else {
						String contractId = splitInfo.getPaymentBill()
								.getContractBillId();
						SelectorItemCollection selectorCon = new SelectorItemCollection();
						selectorCon.add("id");
						selectorCon.add("conSplitExecState");
						ContractBillInfo info = ContractBillFactory
								.getRemoteInstance()
								.getContractBillInfo(
										new ObjectUuidPK(BOSUuid
												.read(contractId)), selectorCon);
						if (info.getConSplitExecState().equals(
								ConSplitExecStateEnum.INVALID)) {
							MsgBox.showWarning(this,
									"合同已经进入待处理流程，请先到待处理合同对此合同进行凭证处理！");
							SysUtil.abort();
						}
					}
					if (!(splitInfo.getPaymentBill().getBillStatus()
							.equals(BillStatusEnum.PAYED))) {
						MsgBox.showWarning("存在未付款的付款单，不能生成凭证！");
						SysUtil.abort();
					} else if (splitInfo.getPaymentBill().isFiVouchered()) {
						MsgBox.showWarning("对应的付款单已经生成凭证，不能生成凭证！");
						SysUtil.abort();

					} else if (splitInfo.isIsProgress()) {
						FilterInfo filterNoVoucher = new FilterInfo();
						filterNoVoucher.getFilterItems().add(
								new FilterItemInfo(
										"paymentBill.fdcPayType.payType.id",
										PaymentTypeInfo.progressID));
						filterNoVoucher.getFilterItems().add(
								new FilterItemInfo("Fivouchered", Boolean.TRUE,
										CompareType.NOTEQUALS));
						filterNoVoucher.getFilterItems().add(
								new FilterItemInfo(
										"paymentBill.contractBillId", splitInfo
												.getPaymentBill()
												.getContractBillId()));
						if (split.exists(filterNoVoucher)) {
							MsgBox
									.showWarning("存在未生成凭证的进度款付款单，不能生成凭证！请先处理合同下的进度款。");
							SysUtil.abort();
						}
					}
				} else {
					noSplitInfo = noSplit.getPaymentNoCostSplitInfo(pk,
							selector);
					if (noSplitInfo.isIsConWithoutText()) {
						FilterInfo noTextFilter = new FilterInfo();
						noTextFilter.getFilterItems().add(
								new FilterItemInfo("id", noSplitInfo
										.getPaymentBill().getContractBillId()));
						noTextFilter.getFilterItems().add(
								new FilterItemInfo("isNeedPaid", Boolean.TRUE));
						if (ContractWithoutTextFactory.getRemoteInstance()
								.exists(noTextFilter)) {
							continue;
						} else if (!(noSplitInfo.getPaymentBill()
								.getBillStatus().equals(BillStatusEnum.PAYED))) {
							MsgBox.showWarning("存在未付款的付款单，不能生成凭证！");
							SysUtil.abort();
						} else if (noSplitInfo.getPaymentBill().isFiVouchered()) {
							MsgBox.showWarning("对应的付款单已经生成凭证，不能生成凭证！");
							SysUtil.abort();
						} else {
							continue;
						}
					} else {
						String contractId = noSplitInfo.getPaymentBill()
								.getContractBillId();
						SelectorItemCollection selectorCon = new SelectorItemCollection();
						selectorCon.add("id");
						selectorCon.add("conSplitExecState");
						ContractBillInfo info = ContractBillFactory
								.getRemoteInstance()
								.getContractBillInfo(
										new ObjectUuidPK(BOSUuid
												.read(contractId)), selectorCon);
						if (info.getConSplitExecState().equals(
								ConSplitExecStateEnum.INVALID)) {
							MsgBox.showWarning(this,
									"合同已经进入待处理流程，请先到待处理合同对此合同进行凭证处理！");
							SysUtil.abort();
						}
					}
					if (!(noSplitInfo.getPaymentBill().getBillStatus()
							.equals(BillStatusEnum.PAYED))) {
						MsgBox.showWarning("存在未付款的付款单，不能生成凭证！");
						SysUtil.abort();
					} else if (noSplitInfo.getPaymentBill().isFiVouchered()) {
						MsgBox.showWarning("对应的付款单已经生成凭证，不能生成凭证！");
						SysUtil.abort();
					} else if (noSplitInfo.isIsProgress()) {
						FilterInfo filterNoVoucher = new FilterInfo();
						filterNoVoucher.getFilterItems().add(
								new FilterItemInfo(
										"paymentBill.fdcPayType.payType.id",
										PaymentTypeInfo.progressID));
						filterNoVoucher.getFilterItems().add(
								new FilterItemInfo("Fivouchered", Boolean.TRUE,
										CompareType.NOTEQUALS));
						filterNoVoucher.getFilterItems().add(
								new FilterItemInfo(
										"paymentBill.contractBillId",
										noSplitInfo.getPaymentBill()
												.getContractBillId()));
						if (noSplit.exists(filterNoVoucher)) {
							MsgBox
									.showWarning("存在未生成凭证的进度款付款单，不能生成凭证！请先处理合同下的进度款。");
							SysUtil.abort();
						}
					}
				}
			}
		}
		if (getSplit() == 1) {
			isCost = true;
			PaymentSplitFactory.getRemoteInstance().traceData(idList);
		} else {
			isCost = false;
			setDataForNoCost();
		}
		super.actionVoucher_actionPerformed(e);
	}

	private void batchVoucher4Cost(boolean isCostSplit,
			CoreBillBaseCollection sourceBillCollection) throws Exception {
		List idList = new ArrayList();
		for (int i = 0, size = sourceBillCollection.size(); i < size; i++) {
			idList.add(sourceBillCollection.get(i).getId().toString());
		}
		if (isCostSplit) {
			isCost = true;
			PaymentSplitFactory.getRemoteInstance().traceData(idList);
		} else {
			isCost = false;
			PaymentNoCostSplitFactory.getRemoteInstance().traceData(idList);
		}
		this.isDAPTrans = true;
		this.isDAPTrans = false;
		verifyBillList(sourceBillCollection);
		dapTrans.trans(sourceBillCollection);
		refreshList();
	}

	public ICoreBase getBillInterface() throws Exception {
		if (isCost) {
			return PaymentSplitFactory.getRemoteInstance();
		} else {
			return PaymentNoCostSplitFactory.getRemoteInstance();
		}
	}

	// 判断出拆分到的产品对应的工程项目下的这个产品是否已经结算，如果结算了，则要做凭证冲销
	protected void setVoucherForSett() throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		int size = idList.size();
		IPaymentSplitEntry iPaymentSplitEntry = PaymentSplitEntryFactory
				.getRemoteInstance();
		PaymentSplitEntryCollection coll;
		for (int i = 0; i < size; i++) {
			EntityViewInfo view = new EntityViewInfo();
			String id = idList.get(i).toString();
			PaymentSplitInfo splitInfo = PaymentSplitFactory
					.getRemoteInstance().getPaymentSplitInfo(
							new ObjectUuidPK(BOSUuid.read(id)));
			if (splitInfo.getVoucherTimes() <= 1) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems()
						.add(new FilterItemInfo("parent.id", id));
				filter.getFilterItems().add(
						new FilterItemInfo("isLeaf", Boolean.TRUE));
				filter.getFilterItems().add(
						new FilterItemInfo("product.id", null,
								CompareType.NOTEQUALS));
				view.setFilter(filter);
				view.getSelector().add(new SelectorItemInfo("id"));
				view.getSelector().add(new SelectorItemInfo("seq"));
				view.getSelector().add(new SelectorItemInfo("costAccount.*"));
				view.getSelector().add(
						new SelectorItemInfo("costAccount.curProject.*"));
				view.getSelector().add(new SelectorItemInfo("product.*"));
				coll = iPaymentSplitEntry.getPaymentSplitEntryCollection(view);
				int entrySize = coll.size();
				if (entrySize > 0) {
					SettForPayVoucherInfo settInfo = new SettForPayVoucherInfo();
					settInfo.setBeAccount(splitInfo.getBeAccount());
					settInfo.setFiVouchered(false);
					settInfo.setPaymentBill(splitInfo.getPaymentBill());
					BigDecimal totAmt = FDCHelper.ZERO;
					for (int j = 0; j < entrySize; j++) {
						PaymentSplitEntryInfo info = coll.get(j);
						EntityViewInfo viewExist = new EntityViewInfo();
						FilterInfo filterExist = new FilterInfo();
						filterExist.getFilterItems().add(
								new FilterItemInfo("curProject.id", info
										.getCostAccount().getCurProject()
										.getId().toString()));
						filterExist.getFilterItems()
								.add(
										new FilterItemInfo("fiVouchered",
												Boolean.TRUE));
						filterExist.getFilterItems()
								.add(
										new FilterItemInfo("isCompSettle",
												Boolean.TRUE));
						filterExist.getFilterItems().add(
								new FilterItemInfo("product.id", info
										.getProduct().getId().toString()));
						if (CurProjProductEntriesFactory.getRemoteInstance()
								.exists(filterExist)) {
							viewExist.setFilter(filterExist);
							CurProjProductEntriesCollection prjColl = CurProjProductEntriesFactory
									.getRemoteInstance()
									.getCurProjProductEntriesCollection(
											viewExist);
							if (prjColl.size() > 0) {
								SettForPayVoucherEntryInfo settEntryInfo = new SettForPayVoucherEntryInfo();
								settEntryInfo.setCurProject(info
										.getCostAccount().getCurProject());
								settEntryInfo.setProduct(info.getProduct());
								settEntryInfo.setSplitEntryID(info.getId()
										.toString());
								BigDecimal sale = ProjectHelper
										.getIndexValueByProjProdIdx(null, info
												.getCostAccount()
												.getCurProject().getId()
												.toString(), info.getProduct()
												.getId().toString(),
												FDCConstants.SALE_AREA_ID,
												ProjectStageEnum.DYNCOST);
								if (sale != null) {
									BigDecimal tempAmount = (info.getAmount()
											.multiply(prjColl.get(0)
													.getCompArea()))
											.divide(sale, 8,
													BigDecimal.ROUND_HALF_EVEN);
									settEntryInfo.setAmount(tempAmount);
									totAmt = totAmt.add(tempAmount);
								}
								settInfo.getEntrys().add(settEntryInfo);
							}
						}
					}
					settInfo.setAmount(totAmt);
					IObjectPK pk = SettForPayVoucherFactory.getRemoteInstance()
							.addnew(settInfo);
					if (settInfo.getAmount().compareTo(FDCHelper.ZERO) == 1)
						PaymentSplitVoucherHelper.SettForPayVoucher(pk);
				}
			}
		}
	}

	protected void setDataForNoCost() throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		Set idSet = ContractClientUtils.listToSet(idList);
		EntityViewInfo viewSplit = new EntityViewInfo();
		FilterInfo filterSplit = new FilterInfo();
		filterSplit.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		viewSplit.setFilter(filterSplit);
		viewSplit.getSelector().add("*");
		viewSplit.getSelector().add("paymentBill.*");
		viewSplit.getSelector().add("paymentBill.fdcPayType.*");
		viewSplit.getSelector().add("beAccount.*");
		viewSplit.getSelector().add("beAccount.proAccount.*");
		viewSplit.getSelector().add("beAccount.settAccount.*");
		IPaymentNoCostSplit iSplit = PaymentNoCostSplitFactory
				.getRemoteInstance();
		PaymentNoCostSplitCollection coll = iSplit
				.getPaymentNoCostSplitCollection(viewSplit);
		int size = coll.size();
		PaymentNoCostSplitInfo info = new PaymentNoCostSplitInfo();
		IBeforeAccountView iBefore = BeforeAccountViewFactory
				.getRemoteInstance();
		for (int i = 0; i < size; i++) {
			info = (PaymentNoCostSplitInfo) coll.get(i);
			// if (info.getBeAccount() == null) {
			if (info.getPaymentBill() != null
					&& info.getPaymentBill().getCompany() != null
					&& info.getPaymentBill().getCompany().getId() != null) {
				String id = info.getPaymentBill().getCompany().getId()
						.toString();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("company.id", id));
				view.setFilter(filter);
				boolean has = iBefore.exists(filter);
				if (has) {
					BeforeAccountViewInfo account = iBefore
							.getBeforeAccountViewCollection(view).get(0);
					info.setBeAccount(account);
					iSplit.update(new ObjectUuidPK(info.getId()), info);
				} else
					throw new CostSplitException(CostSplitException.NOBEACCOUNT);
				// }
			}
		}
	}

	public void actionDelVoucher_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		boolean isAddNew = checkBeforeVoucher();
		if (isAddNew) {
			MsgBox.showWarning("付款单未拆分，不能删除凭证！");
			SysUtil.abort();
		} else {
			List idList = ContractClientUtils.getSelectedIdValues(
					getBillListTable(), getKeyFieldName());
			int size = idList.size();
			for (int i = 0; i < size; i++) {// 凭证检查
				// String id=getSelectedKeyValue();
				String id = idList.get(i).toString();

				// FilterInfo filter = new FilterInfo();
				// filter.getFilterItems().add(new FilterItemInfo("id", id));
				// filter.getFilterItems().add(
				// new FilterItemInfo(VOUCHERFLAG, Boolean.TRUE));
				// filter.getFilterItems().add(
				// new FilterItemInfo("paymentBill.curProject.projectStatus.id",
				// ProjectStatusInfo.flowID));
				// if (getBizInterface().exists(filter)) {
				// MsgBox.showWarning(this, "工程项目已经流失，不能执行删除凭证操作！");
				// SysUtil.abort();
				// }

				FilterInfo filterSplit = new FilterInfo();
				filterSplit.getFilterItems().add(new FilterItemInfo("id", id));
				filterSplit.getFilterItems().add(
						new FilterItemInfo("state", FDCBillStateEnum.INVALID));
				if (getBizInterface().exists(filterSplit)) {
					MsgBox.showWarning(this, "存在已经作废的记录，不能执行删除凭证操作！");
					SysUtil.abort();
				}
			}
		}
		if (getSplit() == 1) {
			isCost = true;
		} else {
			isCost = false;
		}
		super.actionDelVoucher_actionPerformed(e);
	}

	protected boolean checkBeforeVoucher() throws Exception {
		int rowNumber = FMClientHelper.getSelectedRow(tblMain);
		// IRow row = tblMain.getRow(rowNumber);
		boolean isAddNew = checkBeforeVoucherCheck(rowNumber);
		if (isAddNew) {
			// 到数据库内进行检查
			String billId = getMainTable().getCell(rowNumber, "id").getValue()
					.toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("paymentBill.id", billId));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			if (getRemoteInterface().exists(filter)) {
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				String costBillID = getRemoteInterface().getCollection(view)
						.get(0).getId().toString();
				getMainTable().getCell(rowNumber, getKeyFieldName()).setValue(
						costBillID);
				isAddNew = false;
			}
		}

		return isAddNew;
	}

	protected boolean checkBeforeVoucherCheck(int i) throws Exception {
		boolean isAddNew = false;
		String keyValue = getSelectedKeyValue();

		if (keyValue == null || keyValue.equals(splitBillNullID.toString())) {
			isAddNew = true;
		} else {
			// 界面上显示有，但数据库内已经删除时的情况
			IObjectPK pk = new ObjectUuidPK(BOSUuid.read(keyValue));
			if (!getBizInterface().exists(pk)) {
				isAddNew = true;
				int[] selectedRows = KDTableUtil
						.getSelectedRows(getMainTable());
				getMainTable().getCell(selectedRows[0],
						getSplitStateFieldName()).setValue(
						CostSplitStateEnum.NOSPLIT.toString());
			}
		}
		return isAddNew;
	}

	/**
	 * 为了实现BOTP调用，根据参数返回keyFieldName
	 */
	protected String getKeyFieldName() {

		if (isVoucherUse)
			return "id";

		else
			return super.getKeyFieldName();
	}

	// 是否凭证使用，在gainBillSet（）方法中用
	boolean isVoucherUse = false;

	public void gainBillSet(List list) throws Exception {
		// 因为super.gainBillSet()方法要使用keyfieldname，所以这里设置参数
		isVoucherUse = true;
		super.gainBillSet(list, actionTDPrint);

		isVoucherUse = false; // 还原
	}

	protected void checkBeforeRemove() throws Exception {
		super.checkBeforeRemove();
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		int size = idList.size();
		for (int i = 0; i < size; i++) {// 凭证检查
			// String id=getSelectedKeyValue();
			String id = idList.get(i).toString();
			FilterInfo filterSplit = new FilterInfo();
			filterSplit.getFilterItems().add(new FilterItemInfo("id", id));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID));
			if (getBizInterface().exists(filterSplit)) {
				MsgBox.showWarning(this, "存在已经作废的记录，不能执行删除操作！");
				SysUtil.abort();
			}

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", id));//#0
			filter.getFilterItems().add(
					new FilterItemInfo(VOUCHERFLAG, Boolean.TRUE));//#1
			filter.getFilterItems().add(
					new FilterItemInfo("state",
							FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));//#2
			/***
			 * 如果是复杂模式，且是调整凭证模式，才加入这个判断
			 */
			if(!this.isSimpleFinacialMode()&&this.isAdjustVourcherModel()){
				filter.getFilterItems().add(new FilterItemInfo(REFERFLAG,PaySplitVoucherRefersEnum.ADJUSTBILL_VALUE,CompareType.EQUALS));//#3
				filter.getFilterItems().add(new FilterItemInfo(REFERFLAG,PaySplitVoucherRefersEnum.PAYMENTBILL_VALUE,CompareType.EQUALS));//#4
				filter.setMaskString("#0 and ((#1 and #2) or #3 or #4 )");
			}
			if (getBizInterface().exists(filter)) {
				MsgBox.showWarning(this, "存在已经被引用，不能执行删除操作！");
				SysUtil.abort();
			}
			if(!this.isSimpleFinacialMode()&&this.isAdjustVourcherModel()){
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
				sqlBuilder.appendSql("select pe.fid from t_fnc_fdccostvoucherentry pe left join t_fnc_paymentsplit ps on pe.fparentid=ps.fpaymentbillid where "); 
				sqlBuilder.appendParam("ps.fid",id);
				sqlBuilder.appendSql(" union all ");
				sqlBuilder.appendSql("select pe.fid from t_fnc_fdccostvoucherentry pe left join t_fnc_paymentnocostsplit ps on pe.fparentid=ps.fpaymentbillid where "); 
				sqlBuilder.appendParam("ps.fid",id);
				IRowSet rowSet = sqlBuilder.executeQuery();
				if(rowSet.size()>0){
					MsgBox.showWarning(this, "对应的付款单已经生成凭证，不能执行删除操作！");
					SysUtil.abort();
				}
			}
		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList ids = getSelectedIdValues();
		//update by renliang  改回来的size()>1
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
			checkImp();
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
					PaymentSplitFactory.getRemoteInstance().delete(pks);
				}

				if (noCostSplits.size() > 0) {
					IObjectPK[] pks = new ObjectUuidPK[noCostSplits.size()];
					for (int i = 0; i < noCostSplits.size(); i++) {
						pks[i] = new ObjectUuidPK(noCostSplits.get(i)
								.toString());
					}
					PaymentNoCostSplitFactory.getRemoteInstance().delete(pks);
				}

				// afterRemove();
				showOprtOKMsgAndRefresh();
			}
		} else {
			if(ids!=null && ids.size()>0){
				for (Iterator iter = ids.iterator(); iter.hasNext();) {
					String id = (String) iter.next();
					if (isCostSplit(id)
							&& (SettledMonthlyHelper.existObject(null, costSplit,
									getSelectedKeyValue()))) {
						MsgBox.showWarning(this,
								"财务成本月结已经引用本数据，不能删除，如需修改，请把此付款对应的合同进入待处理流程！");
						SysUtil.abort();
					}
				}
				super.actionRemove_actionPerformed(e);
			}
			
		}
	}

	public SelectorItemCollection getBOTPSelectors() {
		// SelectorItemCollection sic = super.getBOTPSelectors();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("isConWithoutText");
		sic.add("isProgress");
		sic.add("voucherTimes");
		sic.add("payAmount");
		sic.add("isRedVouchered");
		sic.add("isNeedTransit");
		sic.add("transitAccount.id");
		sic.add("transitAccount.longNumber");
		sic.add("transitAccount.longName");
		sic.add("contractBaseData.id");
		sic.add("contractBaseData.number");
		sic.add("contractBaseData.name");
		sic.add("paymentBill.id");
		// sic.add("paymentBill.*");
		// sic.add("payMENTBILL.COMPANY.ID");
		// SIC.ADD("PAYMENTBILL.COMPANY.LONGNUMBER");
		// SIC.ADD("PAYMENTBILL.COMPANY.NAME");
		// SIC.ADD("PAYMENTBill.company.displayName");
		sic.add("paymentBill.currency.id");
		sic.add("paymentBill.currency.name");
		sic.add("paymentBill.currency.number");
		sic.add("paymentBill.payerAccount.id");
		sic.add("paymentBill.payerAccount.longNumber");
		sic.add("paymentBill.payerAccount.longName");
		// sic.add("paymentBill.payerAccountBank.*");
		sic.add("paymentBill.actFdcPayeeName.id");
		sic.add("paymentBill.actFdcPayeeName.name");
		sic.add("paymentBill.actFdcPayeeName.number");
		sic.add("paymentBill.fdcPayType.payType.id");
		sic.add("paymentBill.curProject.projectStatus.id");
		sic.add("paymentBill.curProject.costOrg.id");
		sic.add("paymentBill.curProject.costOrg.longNumber");
		sic.add("paymentBill.curProject.costOrg.displayName");
		sic.add("paymentBill.curProject.costOrg.name");
		sic.add("paymentBill.curProject.costOrg.number");
		// sic.add("beAccount.*");
		sic.add("beAccount.proAccount.id");
		sic.add("beAccount.proAccount.longNumber");
		sic.add("beAccount.proAccount.longName");
		sic.add("beAccount.beforeOtherAccount.id");
		sic.add("beAccount.beforeOtherAccount.longNumber");
		sic.add("beAccount.beforeOtherAccount.longName");
		sic.add("beAccount.settAccount.id");
		sic.add("beAccount.settAccount.longNumber");
		sic.add("beAccount.settAccount.longName");
		sic.add("entrys.isLeaf");
		sic.add("entrys.id");
		sic.add("entrys.amount");
		sic.add("entrys.payedAmt");
		// sic.add("entrys.*");
		sic.add("entrys.costAccount.id");
		sic.add("entrys.costAccount.longNumber");
		sic.add("entrys.costAccount.displayName");
		sic.add("entrys.costAccount.name");
		sic.add("entrys.costAccount.number");
		sic.add("entrys.costAccount.curProject.longNumber");
		sic.add("entrys.costAccount.curProject.longName");
		sic.add("entrys.product.id");
		sic.add("entrys.product.name");
		sic.add("entrys.product.number");
		sic.add("entrys.apportionType.id");
		sic.add("entrys.accountView.id");
		sic.add("entrys.accountView.longNumber");
		sic.add("entrys.accountView.longName");
		// sic.add("voucherEntrys.*");
		sic.add("voucherEntrys.transitAccount.id");
		sic.add("voucherEntrys.transitAccount.longNumber");
		sic.add("voucherEntrys.transitAccount.longName");
		// sic.add("voucherEntrys.bankAccount.*");
		sic.add("voucherEntrys.accountView.id");
		sic.add("voucherEntrys.accountView.longNumber");
		sic.add("voucherEntrys.accountView.longName");
		sic.add("voucherEntrys.currency.id");
		sic.add("voucherEntrys.currency.name");
		sic.add("voucherEntrys.currency.number");
		return sic;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		actionViewContract.setEnabled(true);
		actionViewPayment.setEnabled(true);
		actionViewContract.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_sequencecheck"));
		actionViewPayment.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_linkviewlist"));

		actionSplitAll.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_split"));
		menuItemSplitAll.setText(menuItemSplitAll.getText() + "(C)");
		menuItemSplitAll.setMnemonic('C');
		menuItemSplitAllFI.setText(menuItemSplitAllFI.getText() + "(F)");
		menuItemSplitAllFI.setMnemonic('F');
		btnSplitAll.setVisible(false);
		btnSplitAll.setEnabled(true);
		//		
		// menuItemProcess.setAccelerator(KeyStroke.getKeyStroke("Alt P"));
		menuItemProcess.setText(menuItemProcess.getText().replaceAll("\\(E\\)",
				"")
				+ "(E)");
		menuItemProcess.setMnemonic('E');
		//		
		// menuItemSettle.setAccelerator(KeyStroke.getKeyStroke("Alt S"));
		menuItemSettle.setText(menuItemSettle.getText().replaceAll("\\(S\\)",
				"")
				+ "(S)");
		menuItemSettle.setMnemonic('S');
		//		
		// menuItemGuarante.setAccelerator(KeyStroke.getKeyStroke("Alt G"));
		menuItemGuarante.setText(menuItemGuarante.getText().replaceAll(
				"\\(G\\)", "")
				+ "(G)");
		menuItemGuarante.setMnemonic('G');
		//		
		// menuItemNoCostProcess.setAccelerator(KeyStroke.getKeyStroke("Alt
		// N"));
		menuItemNoCostProcess.setText(menuItemNoCostProcess.getText()
				.replaceAll("\\(N\\)", "")
				+ "(N)");
		menuItemNoCostProcess.setMnemonic('N');
		//		
		// menuItemNoCostSettle.setAccelerator(KeyStroke.getKeyStroke("Alt O"));
		menuItemNoCostSettle.setText(menuItemNoCostSettle.getText().replaceAll(
				"\\(O\\)", "")
				+ "(O)");
		menuItemNoCostSettle.setMnemonic('O');
		//		
		// menuItemNoCostGuarante.setAccelerator(KeyStroke.getKeyStroke("Alt
		// C"));
		menuItemNoCostGuarante.setText(menuItemNoCostGuarante.getText()
				.replaceAll("\\(C\\)", "")
				+ "(C)");
		menuItemNoCostGuarante.setMnemonic('C');
		//		
		// menuItemFlowProcess.setAccelerator(KeyStroke.getKeyStroke("Alt F"));
		menuItemFlowProcess.setText(menuItemFlowProcess.getText().replaceAll(
				"\\(F\\)", "")
				+ "(F)");
		menuItemFlowProcess.setMnemonic('F');
		//		
		// menuItemFlowSettle.setAccelerator(KeyStroke.getKeyStroke("Alt L"));
		menuItemFlowSettle.setText(menuItemFlowSettle.getText().replaceAll(
				"\\(L\\)", "")
				+ "(L)");
		menuItemFlowSettle.setMnemonic('L');
		//		
		// menuItemFlowGuarante.setAccelerator(KeyStroke.getKeyStroke("Alt W"));
		menuItemFlowNoText.setText(menuItemFlowNoText.getText().replaceAll(
				"\\(W\\)", "")
				+ "(W)");
		menuItemFlowNoText.setMnemonic('W');
		//		
		// menuItemNoTextCost.setAccelerator(KeyStroke.getKeyStroke("Alt T"));
		menuItemNoTextCost.setText(menuItemNoTextCost.getText().replaceAll(
				"\\(T\\)", "")
				+ "(T)");
		menuItemNoTextCost.setMnemonic('T');
		//		
		menuItemNoTextNoCost.setText(menuItemNoTextNoCost.getText().replaceAll(
				"\\(X\\)", "")
				+ "(X)");
		// menuItemNoTextNoCost.setAccelerator(KeyStroke.getKeyStroke("Alt X"));
		menuItemNoTextNoCost.setMnemonic('E');
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
			if (!isConWithoutTxt(getMainTable())) {
				Object obj = getMainTable()
						.getCell(selectRows[0], "contractId").getValue();
				if (obj != null) {
					String contractId = obj.toString();
					UIContext uiContext = new UIContext(this);
					uiContext.put(UIContext.ID, contractId);
					IUIWindow testUI = UIFactory.createUIFactory(
							UIFactoryName.NEWTAB).create(
							ContractBillEditUI.class.getName(), uiContext,
							null, OprtState.VIEW);
					testUI.show();
				}
			} else {
				Object obj = getMainTable()
						.getCell(selectRows[0], "contractId").getValue();
				if (obj != null) {
					String contractId = obj.toString();
					UIContext uiContext = new UIContext(this);
					uiContext.put(UIContext.ID, contractId);
					IUIWindow testUI = UIFactory
							.createUIFactory(UIFactoryName.NEWTAB)
							.create(
									com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class
											.getName(), uiContext, null,
									OprtState.VIEW);
					testUI.show();
				}
			}
		}
	}

	public void actionViewPayment_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		int selectRows[] = KDTableUtil.getSelectedRows(getMainTable());
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper
					.getRes("multiRowSelected"));
			SysUtil.abort();
		} else if (selectRows.length == 1) {
			Object obj = getMainTable().getCell(selectRows[0], "id").getValue();
			if (obj != null) {
				String id = obj.toString();
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, id);
				IUIWindow testUI = UIFactory.createUIFactory(
						UIFactoryName.NEWTAB).create(
						PaymentBillEditUI.class.getName(), uiContext, null,
						OprtState.VIEW);
				testUI.show();
			}
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	public void actionSplitAll_actionPerformed(ActionEvent e) throws Exception {
		super.actionSplitAll_actionPerformed(e);
		PaymentSplitProxy proxy = new PaymentSplitProxy();
		CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext()
				.getCurrentFIUnit();
		if (currentFIUnit.isIsBizUnit()) {
			boolean isCostSplit = e.getSource().equals(menuItemSplitAll);
/*			String msg = "请确认是否要拆分当前实体财务组织下满足拆分条件的所有成本类合同付款单?";
			if (!isCostSplit) {
				msg = "请确认是否要拆分当前实体财务组织下满足拆分条件的所有财务类合同付款单?";
			}
			int v = MsgBox.showConfirm2New(this, msg);
*/			
			//去掉提示
			int v=MsgBox.YES;
			if (v == MsgBox.YES) {
				proxy.setDebug(true);
//				this.setCursorOfWair();
				proxy.splitFIPayment(this,currentFIUnit, isCostSplit);
//				refreshList();
//				this.setCursorOfDefault();
			}
		} else {
			MsgBox.showWarning(this, "此功能仅在当前组织为财务组织时使用");
		}
	}

	protected void filterByBillState(EntityViewInfo ev) {
		super.filterByBillState(ev);
		final Object obj = getUIContext().get(UIContext.UICLASSPARAM);
		if (obj == null) {
			return;
		}
		try {
			FilterInfo filter = new FilterInfo();
			FilterInfo filter2 = null;
			if (obj.equals("isContract=\"true\"")) {
				filter.getFilterItems().add(
						new FilterItemInfo("test", null, CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("contractBill.conSplitExecState", ConSplitExecStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
				
			} else if (obj.equals("isContract=\"false\"")) {
				filter.getFilterItems().add(
						new FilterItemInfo("test", null, CompareType.EQUALS));
			}
			ev.getFilter().mergeFilter(filter, "and");
		
			
		} catch (Exception e) {
			handUIException(e);
		}
	}

	protected void execQuery() {
		super.execQuery();
	}

	public void actionViewInvalid_actionPerformed(ActionEvent e)
			throws Exception {
		IUIWindow testUI = null;
		UIContext uiContext = new UIContext(this);
		Object obj = getUIContext().get(UIContext.UICLASSPARAM);
		if (obj == null) {
			return;
		}
		if (obj.equals("isContract=\"true\"")) {
			uiContext.put("isContract", Boolean.TRUE);
		} else {
			uiContext.put("isContract", Boolean.FALSE);
		}
		// uiContext.put(UIContext.ID, info.getId());
		try {
			testUI = UIFactory
					.createUIFactory(UIFactoryName.NEWTAB)
					.create(
							"com.kingdee.eas.fdc.finance.client.InvalidPaymentSplitListUI",
							uiContext, null);
			testUI.show();
		} catch (BOSException exe) {
			super.handUIException(exe);// OprtState.EDIT);
		}
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
		// checkSelected();
		// List idList = ContractClientUtils.getSelectedIdValues(
		// getBillListTable(), getKeyFieldName());
		// int size = idList.size();
		// for (int i = 0; i < size; i++) {// 检查
		// String id = idList.get(i).toString();
		//			
		// FilterInfo filterSplit = new FilterInfo();
		// filterSplit.getFilterItems().add(new FilterItemInfo("id", id));
		// filterSplit.getFilterItems().add(
		// new FilterItemInfo("state", FDCBillStateEnum.INVALID));
		// if (getBizInterface().exists(filterSplit)) {
		// actionVoucher.setEnabled(false);
		// actionDelVoucher.setEnabled(false);
		// }
		//			
		// IObjectPK pk = new ObjectUuidPK(BOSUuid.read(id));
		// if (!getBizInterface().exists(pk)) {
		// actionVoucher.setEnabled(false);
		// actionDelVoucher.setEnabled(false);
		// }
		// }
	}

	protected void refresh(ActionEvent e) throws Exception {
		super.refresh(e);
		if (!isFinacialModel()) {
			if (isIncorporation) {
				FDCClientHelper.setActionEnable(new ItemAction[] {
						actionClearSplit, actionViewInvalid }, true);
			} else {
				FDCClientHelper.setActionEnable(new ItemAction[] {
						actionClearSplit, actionViewInvalid }, false);
			}
			FDCClientHelper.setActionEnable(new ItemAction[] { actionVoucher,
					actionDelVoucher ,actionViewInvalid}, false);
		}
		if (getMainTable().getRowCount() > 0) {
			for (int i = getMainTable().getRowCount() - 1; i >= 0; i--) {
				if (getMainTable().getCell(i, "costSplit.state").getValue() != null
						&& getMainTable().getCell(i, "costSplit.state")
								.getValue().toString().equals(
										FDCBillStateEnum.INVALID_VALUE)) {
					getMainTable().removeRow(i);
				}
			}
			getMainTable().getSelectManager().select(0, 0);
		}
		setAdjustVourcherModelActionState();
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
		if (getMainTable().getRowCount() > 0) {
			for (int i = getMainTable().getRowCount() - 1; i >= 0; i--) {
				if (getMainTable().getCell(i, "costSplit.state").getValue() != null
						&& getMainTable().getCell(i, "costSplit.state")
								.getValue().toString().equals(
										FDCBillStateEnum.INVALID_VALUE)) {
					getMainTable().removeRow(i);
				}
			}
		}
	}

	public void actionBatchVoucher_actionPerformed(ActionEvent e)
			throws Exception {
		CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext()
				.getCurrentFIUnit();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (currentFIUnit.isIsBizUnit()) {
			if (e.getSource().equals(menuItemProcess)) {
				// 成本类非流失进度款
				String msg = "请确认将当前实体财务组织下满足生成凭证条件的所有成本类（非流失工程项目）合同进度款付款单生成凭证？";
				int v = MsgBox.showConfirm2New(this, msg);
				if (v == MsgBox.YES) {
					this.setCursorOfWair();
					view = new EntityViewInfo();
					filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", currentFIUnit
									.getId().toString()));
					filter.getFilterItems().add(
							new FilterItemInfo("Fivouchered", Boolean.TRUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("isConWithoutText",
									Boolean.TRUE, CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.curProject.projectStatus.id",
									ProjectStatusInfo.flowID,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.fdcPayType.payType.id",
									PaymentTypeInfo.progressID,
									CompareType.EQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("paymentBill.billStatus",
									BillStatusEnum.PAYED, CompareType.EQUALS));
					view.setFilter(filter);
					view.getSelector().addObjectCollection(getBOTPSelectors());
					CoreBillBaseCollection coll = PaymentSplitFactory
							.getRemoteInstance()
							.getCoreBillBaseCollection(view);
					if (coll.size() > 0)
						batchVoucher4Cost(true, coll);
					refreshList();
					this.setCursorOfDefault();
				}
			} else if (e.getSource().equals(menuItemSettle)) {
				// 成本类非流失第一笔结算款
				String msg = "请确认将当前实体财务组织下满足生成凭证条件的所有成本类（非流失工程项目）合同结算款付款单生成凭证？";
				int v = MsgBox.showConfirm2New(this, msg);
				if (v == MsgBox.YES) {
					this.setCursorOfWair();
					view = new EntityViewInfo();
					filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", currentFIUnit
									.getId().toString()));
					filter.getFilterItems().add(
							new FilterItemInfo("Fivouchered", Boolean.TRUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("isConWithoutText",
									Boolean.TRUE, CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("isProgress", Boolean.TRUE,
									CompareType.EQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.curProject.projectStatus.id",
									ProjectStatusInfo.flowID,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.fdcPayType.payType.id",
									PaymentTypeInfo.settlementID,
									CompareType.EQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("paymentBill.billStatus",
									BillStatusEnum.PAYED, CompareType.EQUALS));
					view.setFilter(filter);
					view.getSelector().addObjectCollection(getBOTPSelectors());
					CoreBillBaseCollection coll = PaymentSplitFactory
							.getRemoteInstance()
							.getCoreBillBaseCollection(view);
					if (coll.size() > 0)
						batchVoucher4Cost(true, coll);
					refreshList();
					this.setCursorOfDefault();
				}
			} else if (e.getSource().equals(menuItemGuarante)) {
				// 成本类非流失结算后付款
				String msg = "请确认将当前实体财务组织下满足生成凭证条件的所有成本类（包含流失工程项目）合同结算后付款单生成凭证？";
				int v = MsgBox.showConfirm2New(this, msg);
				if (v == MsgBox.YES) {
					this.setCursorOfWair();
					view = new EntityViewInfo();
					filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", currentFIUnit
									.getId().toString()));
					filter.getFilterItems().add(
							new FilterItemInfo("Fivouchered", Boolean.TRUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("isConWithoutText",
									Boolean.TRUE, CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("isProgress", Boolean.TRUE,
									CompareType.NOTEQUALS));
					// filter.getFilterItems().add(
					// new FilterItemInfo(
					// "paymentBill.curProject.projectStatus.id",
					// ProjectStatusInfo.flowID,
					// CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.fdcPayType.payType.id",
									PaymentTypeInfo.progressID,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("paymentBill.billStatus",
									BillStatusEnum.PAYED, CompareType.EQUALS));
					view.setFilter(filter);
					view.getSelector().addObjectCollection(getBOTPSelectors());
					CoreBillBaseCollection coll = PaymentSplitFactory
							.getRemoteInstance()
							.getCoreBillBaseCollection(view);
					if (coll.size() > 0)
						batchVoucher4Cost(true, coll);
					refreshList();
					this.setCursorOfDefault();
				}
			} else if (e.getSource().equals(menuItemNoCostProcess)) {
				// 财务类进度款
				String msg = "请确认将当前实体财务组织下满足生成凭证条件的所有非成本类合同进度款付款单生成凭证？";
				int v = MsgBox.showConfirm2New(this, msg);
				if (v == MsgBox.YES) {
					this.setCursorOfWair();
					view = new EntityViewInfo();
					filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", currentFIUnit
									.getId().toString()));
					filter.getFilterItems().add(
							new FilterItemInfo("Fivouchered", Boolean.TRUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("isConWithoutText",
									Boolean.TRUE, CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.fdcPayType.payType.id",
									PaymentTypeInfo.progressID,
									CompareType.EQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("paymentBill.billStatus",
									BillStatusEnum.PAYED, CompareType.EQUALS));
					view.setFilter(filter);
					view.getSelector().addObjectCollection(getBOTPSelectors());
					CoreBillBaseCollection coll = PaymentNoCostSplitFactory
							.getRemoteInstance()
							.getCoreBillBaseCollection(view);
					if (coll.size() > 0)
						batchVoucher4Cost(false, coll);
					refreshList();
					this.setCursorOfDefault();
				}
			} else if (e.getSource().equals(menuItemNoCostSettle)) {
				// 财务类第一笔结算款
				String msg = "请确认将当前实体财务组织下满足生成凭证条件的所有非成本类合同结算款付款单生成凭证？";
				int v = MsgBox.showConfirm2New(this, msg);
				if (v == MsgBox.YES) {
					this.setCursorOfWair();
					view = new EntityViewInfo();
					filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", currentFIUnit
									.getId().toString()));
					filter.getFilterItems().add(
							new FilterItemInfo("Fivouchered", Boolean.TRUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("isConWithoutText",
									Boolean.TRUE, CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("isProgress", Boolean.TRUE,
									CompareType.EQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.fdcPayType.payType.id",
									PaymentTypeInfo.settlementID,
									CompareType.EQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("paymentBill.billStatus",
									BillStatusEnum.PAYED, CompareType.EQUALS));
					view.setFilter(filter);
					view.getSelector().addObjectCollection(getBOTPSelectors());
					CoreBillBaseCollection coll = PaymentNoCostSplitFactory
							.getRemoteInstance()
							.getCoreBillBaseCollection(view);
					if (coll.size() > 0)
						batchVoucher4Cost(false, coll);
					refreshList();
					this.setCursorOfDefault();
				}
			} else if (e.getSource().equals(menuItemNoCostGuarante)) {
				// 财务类结算后付款
				String msg = "请确认将当前实体财务组织下满足生成凭证条件的所有非成本类合同结算后付款单生成凭证？";
				int v = MsgBox.showConfirm2New(this, msg);
				if (v == MsgBox.YES) {
					this.setCursorOfWair();
					view = new EntityViewInfo();
					filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", currentFIUnit
									.getId().toString()));
					filter.getFilterItems().add(
							new FilterItemInfo("Fivouchered", Boolean.TRUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("isConWithoutText",
									Boolean.TRUE, CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.fdcPayType.payType.id",
									PaymentTypeInfo.progressID,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("paymentBill.billStatus",
									BillStatusEnum.PAYED, CompareType.EQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("isProgress", Boolean.TRUE,
									CompareType.NOTEQUALS));
					view.setFilter(filter);
					view.getSelector().addObjectCollection(getBOTPSelectors());
					CoreBillBaseCollection coll = PaymentNoCostSplitFactory
							.getRemoteInstance()
							.getCoreBillBaseCollection(view);
					if (coll.size() > 0)
						batchVoucher4Cost(false, coll);
					refreshList();
					this.setCursorOfDefault();
				}
			} else if (e.getSource().equals(menuItemFlowProcess)) {
				// 成本类流失进度款
				String msg = "请确认将当前实体财务组织下满足生成凭证条件的所有成本类（流失工程项目）合同进度款付款单生成凭证？";
				int v = MsgBox.showConfirm2New(this, msg);
				if (v == MsgBox.YES) {
					this.setCursorOfWair();
					view = new EntityViewInfo();
					filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", currentFIUnit
									.getId().toString()));
					filter.getFilterItems().add(
							new FilterItemInfo("Fivouchered", Boolean.TRUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("isConWithoutText",
									Boolean.TRUE, CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.curProject.projectStatus.id",
									ProjectStatusInfo.flowID,
									CompareType.EQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.fdcPayType.payType.id",
									PaymentTypeInfo.progressID,
									CompareType.EQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("paymentBill.billStatus",
									BillStatusEnum.PAYED, CompareType.EQUALS));
					view.setFilter(filter);
					view.getSelector().addObjectCollection(getBOTPSelectors());
					CoreBillBaseCollection coll = PaymentSplitFactory
							.getRemoteInstance()
							.getCoreBillBaseCollection(view);
					if (coll.size() > 0)
						batchVoucher4Cost(true, coll);
					refreshList();
					this.setCursorOfDefault();
				}
			} else if (e.getSource().equals(menuItemFlowSettle)) {
				// 成本类流失第一笔结算款
				String msg = "请确认将当前实体财务组织下满足生成凭证条件的所有成本类（流失工程项目）合同结算款付款单生成凭证？";
				int v = MsgBox.showConfirm2New(this, msg);
				if (v == MsgBox.YES) {
					this.setCursorOfWair();
					view = new EntityViewInfo();
					filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", currentFIUnit
									.getId().toString()));
					filter.getFilterItems().add(
							new FilterItemInfo("Fivouchered", Boolean.TRUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("isConWithoutText",
									Boolean.TRUE, CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("isProgress", Boolean.TRUE,
									CompareType.EQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.curProject.projectStatus.id",
									ProjectStatusInfo.flowID,
									CompareType.EQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.fdcPayType.payType.id",
									PaymentTypeInfo.settlementID,
									CompareType.EQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("paymentBill.billStatus",
									BillStatusEnum.PAYED, CompareType.EQUALS));
					view.setFilter(filter);
					view.getSelector().addObjectCollection(getBOTPSelectors());
					CoreBillBaseCollection coll = PaymentSplitFactory
							.getRemoteInstance()
							.getCoreBillBaseCollection(view);
					if (coll.size() > 0)
						batchVoucher4Cost(true, coll);
					refreshList();
					this.setCursorOfDefault();
				}
			} else if (e.getSource().equals(menuItemFlowNoText)) {
				// 成本类流失结算后付款
				String msg = "请确认将当前实体财务组织下满足生成凭证条件的所有成本类（流失工程项目）无文本合同付款生成凭证？";
				int v = MsgBox.showConfirm2New(this, msg);
				if (v == MsgBox.YES) {
					this.setCursorOfWair();
					// 不需要付款的部分
					EntityViewInfo viewNoText = new EntityViewInfo();
					FilterInfo filterNoText = new FilterInfo();
					filterNoText.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", currentFIUnit
									.getId().toString()));
					filterNoText.getFilterItems().add(
							new FilterItemInfo("Fivouchered", Boolean.TRUE,
									CompareType.NOTEQUALS));
					filterNoText.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					filterNoText.getFilterItems().add(
							new FilterItemInfo("isConWithoutText",
									Boolean.TRUE, CompareType.EQUALS));
					filterNoText.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.curProject.projectStatus.id",
									ProjectStatusInfo.flowID,
									CompareType.NOTEQUALS));
					filterNoText.getFilterItems().add(
							new FilterItemInfo("paymentBill.isNeedPay",
									Boolean.FALSE, CompareType.EQUALS));
					filterNoText.getFilterItems()
							.add(
									new FilterItemInfo(
											"paymentBill.billStatus",
											BillStatusEnum.AUDITED,
											CompareType.EQUALS));
					viewNoText.setFilter(filterNoText);
					viewNoText.getSelector().addObjectCollection(
							getBOTPSelectors());
					CoreBillBaseCollection collNoText = PaymentSplitFactory
							.getRemoteInstance()
							.getCoreBillBaseCollection(view);

					view = new EntityViewInfo();
					filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", currentFIUnit
									.getId().toString()));
					filter.getFilterItems().add(
							new FilterItemInfo("Fivouchered", Boolean.TRUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("isConWithoutText",
									Boolean.TRUE, CompareType.EQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.curProject.projectStatus.id",
									ProjectStatusInfo.flowID,
									CompareType.EQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("paymentBill.billStatus",
									BillStatusEnum.PAYED, CompareType.EQUALS));
					view.setFilter(filter);
					view.getSelector().addObjectCollection(getBOTPSelectors());
					CoreBillBaseCollection coll = PaymentSplitFactory
							.getRemoteInstance()
							.getCoreBillBaseCollection(view);
					coll.addCollection(collNoText);
					if (coll.size() > 0)
						batchVoucher4Cost(true, coll);
					refreshList();
					this.setCursorOfDefault();
				}
			} else if (e.getSource().equals(menuItemNoTextCost)) {
				// 成本类无文本合同付款拆分
				String msg = "请确认将当前实体财务组织下满足生成凭证条件的所有成本类（非流失项目）无文本合同付款拆分生成凭证？";
				int v = MsgBox.showConfirm2New(this, msg);
				if (v == MsgBox.YES) {
					this.setCursorOfWair();
					// 不需要付款的部分
					EntityViewInfo viewNoText = new EntityViewInfo();
					FilterInfo filterNoText = new FilterInfo();
					filterNoText.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", currentFIUnit
									.getId().toString()));
					filterNoText.getFilterItems().add(
							new FilterItemInfo("Fivouchered", Boolean.TRUE,
									CompareType.NOTEQUALS));
					filterNoText.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					filterNoText.getFilterItems().add(
							new FilterItemInfo("isConWithoutText",
									Boolean.TRUE, CompareType.EQUALS));
					filterNoText.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.curProject.projectStatus.id",
									ProjectStatusInfo.flowID,
									CompareType.NOTEQUALS));
					filterNoText.getFilterItems().add(
							new FilterItemInfo("paymentBill.isNeedPay",
									Boolean.FALSE, CompareType.EQUALS));
					filterNoText.getFilterItems()
							.add(
									new FilterItemInfo(
											"paymentBill.billStatus",
											BillStatusEnum.AUDITED,
											CompareType.EQUALS));
					viewNoText.setFilter(filterNoText);
					viewNoText.getSelector().addObjectCollection(
							getBOTPSelectors());
					CoreBillBaseCollection collNoText = PaymentSplitFactory
							.getRemoteInstance()
							.getCoreBillBaseCollection(view);

					// 需要付款的部分
					view = new EntityViewInfo();
					filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", currentFIUnit
									.getId().toString()));
					filter.getFilterItems().add(
							new FilterItemInfo("Fivouchered", Boolean.TRUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("isConWithoutText",
									Boolean.TRUE, CompareType.EQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.curProject.projectStatus.id",
									ProjectStatusInfo.flowID,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("paymentBill.billStatus",
									BillStatusEnum.PAYED, CompareType.EQUALS));
					view.setFilter(filter);
					view.getSelector().addObjectCollection(getBOTPSelectors());
					CoreBillBaseCollection coll = PaymentSplitFactory
							.getRemoteInstance()
							.getCoreBillBaseCollection(view);
					coll.addCollection(collNoText);

					if (coll.size() > 0)
						batchVoucher4Cost(true, coll);
					refreshList();
					this.setCursorOfDefault();
				}
			} else if (e.getSource().equals(menuItemNoTextNoCost)) {
				// 财务类无文本合同付款拆分
				String msg = "请确认将当前实体财务组织下满足生成凭证条件的所有财务类无文本合同付款拆分生成凭证？";
				int v = MsgBox.showConfirm2New(this, msg);
				if (v == MsgBox.YES) {
					this.setCursorOfWair();
					// 不需要付款的部分
					EntityViewInfo viewNoText = new EntityViewInfo();
					FilterInfo filterNoText = new FilterInfo();
					filterNoText.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", currentFIUnit
									.getId().toString()));
					filterNoText.getFilterItems().add(
							new FilterItemInfo("Fivouchered", Boolean.TRUE,
									CompareType.NOTEQUALS));
					filterNoText.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					filterNoText.getFilterItems().add(
							new FilterItemInfo("isConWithoutText",
									Boolean.TRUE, CompareType.EQUALS));
					filterNoText.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.curProject.projectStatus.id",
									ProjectStatusInfo.flowID,
									CompareType.NOTEQUALS));
					filterNoText.getFilterItems().add(
							new FilterItemInfo("paymentBill.isNeedPay",
									Boolean.FALSE, CompareType.EQUALS));
					filterNoText.getFilterItems()
							.add(
									new FilterItemInfo(
											"paymentBill.billStatus",
											BillStatusEnum.AUDITED,
											CompareType.EQUALS));
					viewNoText.setFilter(filterNoText);
					viewNoText.getSelector().addObjectCollection(
							getBOTPSelectors());
					CoreBillBaseCollection collNoText = PaymentSplitFactory
							.getRemoteInstance()
							.getCoreBillBaseCollection(view);

					view = new EntityViewInfo();
					filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", currentFIUnit
									.getId().toString()));
					filter.getFilterItems().add(
							new FilterItemInfo("Fivouchered", Boolean.TRUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("isConWithoutText",
									Boolean.TRUE, CompareType.EQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("paymentBill.billStatus",
									BillStatusEnum.PAYED, CompareType.EQUALS));
					view.setFilter(filter);
					view.getSelector().addObjectCollection(getBOTPSelectors());
					CoreBillBaseCollection coll = PaymentNoCostSplitFactory
							.getRemoteInstance()
							.getCoreBillBaseCollection(view);
					coll.addCollection(collNoText);
					if (coll.size() > 0)
						batchVoucher4Cost(false, coll);
					refreshList();
					this.setCursorOfDefault();
				}
			}
		}
	}

	public void actionProcess_actionPerformed(ActionEvent e) throws Exception {
		actionBatchVoucher_actionPerformed(e);
	}

	public void actionSettle_actionPerformed(ActionEvent e) throws Exception {
		actionBatchVoucher_actionPerformed(e);
	}

	public void actionGuarante_actionPerformed(ActionEvent e) throws Exception {
		actionBatchVoucher_actionPerformed(e);
	}

	public void actionNoCostProcess_actionPerformed(ActionEvent e)
			throws Exception {
		actionBatchVoucher_actionPerformed(e);
	}

	public void actionNoCostSettle_actionPerformed(ActionEvent e)
			throws Exception {
		actionBatchVoucher_actionPerformed(e);
	}

	public void actionNoCostGuarante_actionPerformed(ActionEvent e)
			throws Exception {
		actionBatchVoucher_actionPerformed(e);
	}

	public void actionFlowProcess_actionPerformed(ActionEvent e)
			throws Exception {
		actionBatchVoucher_actionPerformed(e);
	}

	public void actionFlowSettle_actionPerformed(ActionEvent e)
			throws Exception {
		actionBatchVoucher_actionPerformed(e);
	}

	public void actionFlowNoText_actionPerformed(ActionEvent e)
			throws Exception {
		actionBatchVoucher_actionPerformed(e);
	}

	public void actionNoTextCost_actionPerformed(ActionEvent e)
			throws Exception {
		actionBatchVoucher_actionPerformed(e);
	}

	public void actionNoTextNoCost_actionPerformed(ActionEvent e)
			throws Exception {
		actionBatchVoucher_actionPerformed(e);
	}

	public void actionClearSplit_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		super.actionClearSplit_actionPerformed(e);
		String paymentId = getSelectedCostBillID();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("contractBillId");
		PaymentBillInfo payment = PaymentBillFactory.getRemoteInstance()
				.getPaymentBillInfo(new ObjectUuidPK(BOSUuid.read(paymentId)),
						selector);
		String ids = getSelectedKeyValue();
		if (isCostSplit(ids)
				&& SplitClearClientHelper.isNewVersionSettlement(payment
						.getId().toString())) {
			if (MsgBox.showConfirm2(this,
					"此付款拆分存在拆分期间在当前期间之后的版本，需要将当前期间之后的相关拆分清除，确认是否操作？") == MsgBox.OK) {
				boolean success = true;
				try {
					String result = ClearSplitFacadeFactory.getRemoteInstance()
							.clearPeriodConSplit(payment.getId().toString(),
									"payment");
					if (result != null) {
						success = false;
						MsgBox.showError(this, "已存在之后期间的付款拆分生成的凭证,凭证号为:"
								+ result + " 。请先删除凭证！");
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
			if (!(isCostSplit(ids) && (SettledMonthlyHelper.existObject(null,
					costSplit, getSelectedKeyValue())))) {
				SplitClearClientHelper.checkClearable(this, payment
						.getContractBillId(), true);
			} else {
				SplitClearClientHelper.checkClearable(this, payment
						.getContractBillId(), false);
			}
			String msg = "作废付款拆分将把无文本合同已生成的凭证红冲，并需要进行重新拆分，确认是否操作？";
			if(!this.isSimpleFinacialMode()&&this.isAdjustVourcherModel())
				msg = "作废付款拆分需要进行重新拆分，并需要出调整凭证，确认是否操作？";
			if (MsgBox.showConfirm2(this,msg) == MsgBox.OK) {
				boolean sccuss = true;
				try {
					ClearSplitFacadeFactory.getRemoteInstance()
							.clearWithoutTextSplit(payment.getContractBillId());
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

	/**
	 * 设置调整凭证模式下的菜单以及按钮状态
	 */
	private void setAdjustVourcherModelActionState() {
		if (!isAdjustVourcherModel()) {
			return;
		}
		FDCClientHelper.setActionEnable(new ItemAction[] { actionVoucher,
				actionDelVoucher, actionTraceDown }, false);
		menuBatchVoucher.setVisible(false);
		menuBatchVoucher.setEnabled(false);
	}
	
	/**
	 * 
	 * 描述：无文本合同时序时薄权限与编辑界面保持一致;<pre>
	 *      此方法解决BT322149问题，因合同付款拆分与无文本付款拆分共用同一个
	 *      序时薄PaymentSplitListUI,导致序时薄拆分打印审批等权限冲突；
	 * 
	 * @author pengwei_hou Date:2009-02-06 20:11:23
	 * @param longName
	 */
	protected void handlePermissionForNoContractItemAction(String longName) {
		// TODO 自动生成方法存根
		final Object obj = getUIContext().get(UIContext.UICLASSPARAM);
		if (obj != null && obj.equals("isContract=\"false\"")) {
//			String longName = action.getClass().getName();
			int index = longName.indexOf('$');
			String actionName = longName.substring(index+1,longName.length());
			if("ActionCostSplit".equals(actionName)){
				actionName = "ActionSplitProd";
			}
			try {
				PermissionFactory.getRemoteInstance().checkFunctionPermission(
						new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
						new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()),
						new MetaDataPK("com.kingdee.eas.fdc.finance.client.PaymentSplitWithoutTxtConEditUI"),
						new MetaDataPK(actionName));
			} catch (EASBizException e) {
				handUIExceptionAndAbort(e);
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			}
		}
	}
	
	/**
	 * 描述：点击无文本付款拆分菜单时检查权限
	 */
	protected void handlePermissionForItemAction(ItemAction action) {
		// TODO 自动生成方法存根
		final Object obj = getUIContext().get(UIContext.UICLASSPARAM);
		if (obj != null && obj.equals("isContract=\"false\"")) {
			try {
				PermissionFactory.getRemoteInstance().checkFunctionPermission(
						new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
						new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()),
						new MetaDataPK("com.kingdee.eas.fdc.finance.client.PaymentSplitWithoutTxtConEditUI"),
						new MetaDataPK("ActionOnLoad"));
			} catch (EASBizException e) {
				handUIExceptionAndAbort(e);
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			}
		} else {
			super.handlePermissionForItemAction(action);
		}
	}
	
	public void beforeActionPerformed(ActionEvent e) {
		super.beforeActionPerformed(e);
		String action = e.getActionCommand();
		if(action == null || action.length()==0 || action.indexOf('$') == -1){
			return;
		}
		//确保每次操作每个action都调用
		handlePermissionForNoContractItemAction(action);
	}
	public IMetaDataPK getMetaDataPK() {
		// TODO 自动生成方法存根
		final Object obj = getUIContext().get(UIContext.UICLASSPARAM);
		if (obj != null && obj.equals("isContract=\"false\"")) {
			return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "PaymentSplitWithoutTxtConEditUI");
		} else {
			return super.getMetaDataPK();
		}
	}
	
	/**
	 * 增加快速定位字段：合同编码、合同名称、收款单位、原币金额、本位币金额；
	 * @author owen_wen 2010-09-07
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "contractBill.number", "contractBill.name", "fdcPayeeName.name", "amount", "localAmt", };
	}
}