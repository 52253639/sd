/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTGroupManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.PeriodCollection;
import com.kingdee.eas.basedata.assistant.PeriodFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceFactory;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SaleFinancialCheckupUI extends AbstractSaleFinancialCheckupUI {

	private static final Logger logger = CoreUIObject.getLogger(SaleFinancialCheckupUI.class);
	private SaleFinancialCheckupFilterUI filterUI = null;
	private CommonQueryDialog commonQueryDialog = null;
	private String allProjectIds = null;

	protected OrgUnitInfo getCurOrgUnitInfo() {
		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentSaleUnit();
//		 OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentOrgUnit();
		return crrOu;
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		getAllSellProjectIds((TreeNode) this.treeMain.getModel().getRoot());
	}

	private SaleFinancialCheckupFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new SaleFinancialCheckupFilterUI();
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			return;
		} else {
			return;
		}
	}

	/**
	 * output class constructor
	 */
	public SaleFinancialCheckupUI() throws Exception {
		super();
	}

	protected boolean initDefaultFilter() {
		return true;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	protected String getKeyFieldName() {
		return "id";
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		this.actionRefresh.setVisible(true);
		this.actionEdit.setEnabled(false);
		this.menuEdit.setVisible(false);
	}

	protected void execQuery() {
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		if (this.tblMain.getRowCount() > 0) {
			this.tblMain.removeRows();
			this.tblMain.checkParsed();
		}
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		Date beginDate = (Date) filterMap.get("beginDate");
		Date endDate = (Date) filterMap.get("endDate");
		String periodId = (String) filterMap.get("periodId");
		try {
			getSellProjectAndOrgOrCompaney(periodId, beginDate, endDate);
		} catch (Exception e1) {
			handleException(e1);
			e1.printStackTrace();
		}
		if (this.tblMain.getRowCount() < 1) {
			MsgBox.showInfo("没有数据，请检查！");
		}
	}

	private void getSellProjectAndOrgOrCompaney(String periodId, Date beginDate, Date endDate) throws BOSException, SQLException, Exception {
		OrgUnitInfo orgUnitIfo = this.getCurOrgUnitInfo();
		initTree();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("  select DISTINCT    BaseUnit.FLongNumber AS longNumber, ");
		builder.appendSql("  BaseUnit.FID AS companyId,BaseUnit.FIsCompanyOrgUnit,  ");
		builder.appendSql("  SellProject.FID AS sellProjectId,                      ");
		builder.appendSql("  CurProject.FID AS projectId,                           ");
		builder.appendSql("  AccountView.FID AS accountViewId,                      ");
		builder.appendSql("  MoneyDefine.FID AS moneyDefineId,                      ");
		builder.appendSql("  BaseUnit.FName_l2 AS companyName,                      ");
		builder.appendSql("  SellProject.FName_l2 AS sellProjectName,               ");
		builder.appendSql("  CurProject.FName_l2 AS projectName,                    ");
		builder.appendSql("  AccountView.FNumber AS accountViewNumber,              ");
		builder.appendSql("  AccountView.FName_l2 AS accountViewName,               ");
		builder.appendSql("  MoneyDefine.FName_l2 AS moneyDefineName,               ");
		builder.appendSql("  MoneyDefine.FMoneyType AS moneyDefineType,             ");
		builder.appendSql("  Contrast.FContrastSide AS contrastSide                 ");
		builder.appendSql("  from T_SHE_SellProject SellProject                     ");
		// 关联组织
		builder.appendSql("   INNER JOIN  T_ORG_BaseUnit BaseUnit                   ");
		builder.appendSql("  ON BaseUnit.FID=SellProject.FOrgUnitID                 ");
		// 关联工程项目
		builder.appendSql("   LEFT JOIN  T_FDC_CurProject CurProject                     ");
		builder.appendSql("  ON CurProject.FID=SellProject.FProjectID                    ");
		// 关联收款单
		builder.appendSql("  INNER JOIN T_SHE_FDCReceiveBill FDCReceiveBill              ");
		builder.appendSql("  ON FDCReceiveBill.FSellProjectID=SellProject.FID            ");
		// 关联房地产收款单
		builder.appendSql("  INNER JOIN T_CAS_ReceivingBill ReceivingBill                ");
		builder.appendSql("  ON ReceivingBill.FFdcReceiveBillID=FDCReceiveBill.FID       ");
		// 关联房地产收款单分录
		builder.appendSql("  INNER JOIN T_SHE_FDCReceiveBillEntry FDCReceiveBillEntry    ");
		builder.appendSql("  ON FDCReceiveBillEntry.FReceivingBillID=ReceivingBill.FID   ");
		// 关联会计科目
		builder.appendSql("   INNER JOIN T_BD_AccountView AccountView                    ");
		builder.appendSql("  ON FDCReceiveBillEntry.FOppSubjectID=AccountView.FID        ");
		// 关联款项
		builder.appendSql("   INNER JOIN T_SHE_MoneyDefine MoneyDefine                   ");
		builder.appendSql("  ON FDCReceiveBillEntry.FMoneyDefineID=MoneyDefine.FID       ");

		// 关联对照表
		builder.appendSql("  LEFT JOIN T_SHE_MoneySubjectContrast Contrast     ON       ");
		builder.appendSql("    Contrast.FMoneyDefineID=MoneyDefine.FID                   ");
		builder.appendSql("    AND   Contrast.FFullOrgUnitID= BaseUnit.FID               ");

		builder.appendSql("  WHERE  MoneyDefine.FSysType='SalehouseSys'                  ");
		builder.appendSql("  AND SellProject.FID IN (" + allProjectIds + ")              ");
		appendDateFilterSql(builder, "ReceivingBill.FBizDate", beginDate, endDate);
		 builder.appendSql("  AND BaseUnit.FLongNumber LIKE '" + orgUnitIfo.getLongNumber() + "%' ");
		builder.appendSql("  ORDER BY                                     ");
		builder.appendSql("  AccountView.FID,                             ");
		builder.appendSql("  AccountView.FNumber,                         ");
		builder.appendSql("  AccountView.FName_l2,                        ");
		builder.appendSql("  BaseUnit.FID,BaseUnit.FName_l2,              ");
		builder.appendSql("  SellProject.FID,                             ");
		builder.appendSql("  SellProject.FName_l2,                        ");
		builder.appendSql("  CurProject.FID,                              ");
		builder.appendSql("  CurProject.FName_l2,                         ");
		builder.appendSql("  MoneyDefine.FMoneyType,                      ");
		builder.appendSql("  MoneyDefine.FID,                             ");
		builder.appendSql("  MoneyDefine.FName_l2                         ");
		IRowSet tableSet = builder.executeQuery();
		String[][] table = new String[tableSet.size()][14];
		int tableIndex = 0;
		while (tableSet.next()) {
			if (tableSet.getString("companyId") != null && tableSet.getString("companyId") != "") {
				table[tableIndex][0] = tableSet.getString("companyId");
				table[tableIndex][12] = tableSet.getString("companyId");
			}
			if (tableSet.getString("sellProjectId") != null && tableSet.getString("sellProjectId") != "") {
				table[tableIndex][1] = tableSet.getString("sellProjectId");
			}
			if (tableSet.getString("projectId") != null && tableSet.getString("projectId") != "") {
				table[tableIndex][2] = tableSet.getString("projectId");
			} else {
				table[tableIndex][2] = "";
			}
			if (tableSet.getString("accountViewId") != null && tableSet.getString("accountViewId") != "") {
				table[tableIndex][3] = tableSet.getString("accountViewId");
			}
			if (tableSet.getString("moneyDefineId") != null && tableSet.getString("moneyDefineId") != "") {
				table[tableIndex][4] = tableSet.getString("moneyDefineId");
			}
			if (tableSet.getString("FIsCompanyOrgUnit") != null && tableSet.getString("FIsCompanyOrgUnit") != "") {
				if (tableSet.getInt("FIsCompanyOrgUnit") != 1) {
					if (tableSet.getString("longNumber") != null && tableSet.getString("longNumber") != "") {
						String longNumberSet = getAllLongNumber(tableSet.getString("longNumber"));
						table[tableIndex][0] = getFinancialUnitId(longNumberSet);
						table[tableIndex][5] = getFinancialUnitName(longNumberSet);
					}
				} else {
					if (tableSet.getString("companyName") != null && tableSet.getString("companyName") != "") {
						table[tableIndex][5] = tableSet.getString("companyName");
					}
				}
			}

			if (tableSet.getString("sellProjectName") != null && tableSet.getString("sellProjectName") != "") {
				table[tableIndex][6] = tableSet.getString("sellProjectName");
			}
			if (tableSet.getString("projectName") != null && tableSet.getString("projectName") != "") {
				table[tableIndex][7] = tableSet.getString("projectName");
			}
			if (tableSet.getString("accountViewNumber") != null && tableSet.getString("accountViewNumber") != "") {
				table[tableIndex][8] = tableSet.getString("accountViewNumber");
			}
			if (tableSet.getString("accountViewName") != null && tableSet.getString("accountViewName") != "") {
				table[tableIndex][9] = tableSet.getString("accountViewName");
			}
			if (tableSet.getString("moneyDefineType") != null && tableSet.getString("moneyDefineType") != "") {
				table[tableIndex][10] = tableSet.getString("moneyDefineType");
			}
			if (tableSet.getString("moneyDefineName") != null && tableSet.getString("moneyDefineName") != "") {
				table[tableIndex][11] = tableSet.getString("moneyDefineName");
			}

			if (tableSet.getString("contrastSide") != null && tableSet.getString("contrastSide") != "") {
				table[tableIndex][13] = tableSet.getString("contrastSide");
			}
			tableIndex++;
		}
		String accountId = null;
		int start = 0;
		for (int i = 0; i < tableIndex; i++) {
			IRow row = this.tblMain.addRow();
			for (int j = 0; j < this.tblMain.getColumnCount(); j++) {
				row.getCell(j).setValue("");
			}
			row.getCell("companyName").setValue(table[i][5]);
			row.getCell("sellProjectName").setValue(table[i][6]);
			row.getCell("projectName").setValue(table[i][7]);
			row.getCell("accountViewNumber").setValue(table[i][8]);
			row.getCell("accountViewName").setValue(table[i][9]);
			MoneyTypeEnum moneyType = MoneyTypeEnum.getEnum(table[i][10]);
			row.getCell("moneyDefineType").setValue(moneyType);
			row.getCell("moneyDefineName").setValue(table[i][11]);
			row.getCell("id").setValue(table[i][12]);
			fillMoneyAmountData(row, table[i][1], table[i][3], table[i][4], table[i][10], beginDate, endDate, allProjectIds);
			fillMoneyAccountAmountData(row, allProjectIds, table[i][3], table[i][4], table[i][10], beginDate, endDate);
			fillAccountAmount(row, table[i][12], table[i][3], periodId, table[i][13], table[i][4], table[i][0]);
			fillAccountProjectAmount(row, table[i][12], table[i][2], table[i][3], periodId, table[i][13], table[i][4], table[i][0]);
			if (accountId == null) {
				accountId = table[i][3];
			} else {
				if (!accountId.equals(table[i][3])) {
					this.tblMain.getMergeManager().mergeBlock(start, tblMain.getColumnIndex("accountViewName"), i - 1, tblMain.getColumnIndex("accountViewName"), KDTMergeManager.FREE_ROW_MERGE);
					this.tblMain.getMergeManager().mergeBlock(start, tblMain.getColumnIndex("accountViewNumber"), i - 1, tblMain.getColumnIndex("accountViewNumber"), KDTMergeManager.FREE_ROW_MERGE);
					this.tblMain.getMergeManager().mergeBlock(start, tblMain.getColumnIndex("moneyAccountAmount"), i - 1, tblMain.getColumnIndex("moneyAccountAmount"), KDTMergeManager.FREE_ROW_MERGE);
					this.tblMain.getMergeManager().mergeBlock(start, tblMain.getColumnIndex("accountAmount"), i - 1, tblMain.getColumnIndex("accountAmount"), KDTMergeManager.FREE_ROW_MERGE);
					this.tblMain.getMergeManager().mergeBlock(start, tblMain.getColumnIndex("balance"), i - 1, tblMain.getColumnIndex("balance"), KDTMergeManager.FREE_ROW_MERGE);
					accountId = table[i][3];
					mergeSellProject(table, start, i - 1);
					start = i;
				}
				if (i == tableIndex - 1) {
					this.tblMain.getMergeManager().mergeBlock(start, tblMain.getColumnIndex("accountViewName"), i, tblMain.getColumnIndex("accountViewName"), KDTMergeManager.FREE_ROW_MERGE);
					this.tblMain.getMergeManager().mergeBlock(start, tblMain.getColumnIndex("accountViewNumber"), i, tblMain.getColumnIndex("accountViewNumber"), KDTMergeManager.FREE_ROW_MERGE);
					this.tblMain.getMergeManager().mergeBlock(start, tblMain.getColumnIndex("accountAmount"), i, tblMain.getColumnIndex("accountAmount"), KDTMergeManager.FREE_ROW_MERGE);
					this.tblMain.getMergeManager().mergeBlock(start, tblMain.getColumnIndex("moneyAccountAmount"), i, tblMain.getColumnIndex("moneyAccountAmount"), KDTMergeManager.FREE_ROW_MERGE);
					this.tblMain.getMergeManager().mergeBlock(start, tblMain.getColumnIndex("balance"), i, tblMain.getColumnIndex("balance"), KDTMergeManager.FREE_ROW_MERGE);
					mergeSellProject(table, start, i);
				}
			}
		}
		this.tblMain.getMergeManager().mergeBlock(0, tblMain.getColumnIndex("companyName"), tblMain.getRowCount() - 1, tblMain.getColumnIndex("companyName"), KDTMergeManager.FREE_ROW_MERGE);
	}

	private void mergeSellProject(String[][] table, int begin, int end) {
		String sellProjectId = null;
		int start = begin;
		for (int i = begin; i <= end; i++) {
			if (sellProjectId == null) {
				sellProjectId = table[i][1];
			} else {
				if (!sellProjectId.equals(table[i][1])) {
					this.tblMain.getMergeManager().mergeBlock(start, tblMain.getColumnIndex("sellProjectName"), i - 1, tblMain.getColumnIndex("sellProjectName"), KDTMergeManager.FREE_ROW_MERGE);
					mergeCurProject(table, start, i - 1);
					sellProjectId = table[i][1];
					start = i;
				}
				if (i == end) {
					this.tblMain.getMergeManager().mergeBlock(start, tblMain.getColumnIndex("sellProjectName"), i, tblMain.getColumnIndex("sellProjectName"), KDTMergeManager.FREE_ROW_MERGE);
					mergeCurProject(table, start, i);
				}
			}
		}
	}

	private void mergeCurProject(String[][] table, int start, int end) {
		String curProjectId = null;
		for (int i = start; i <= end; i++) {
			if (curProjectId == null) {
				curProjectId = table[i][2];
			} else {
				if (!curProjectId.equals(table[i][2])) {
					this.tblMain.getMergeManager().mergeBlock(start, tblMain.getColumnIndex("projectAmount"), i - 1, tblMain.getColumnIndex("projectAmount"), KDTMergeManager.FREE_ROW_MERGE);
					this.tblMain.getMergeManager().mergeBlock(start, tblMain.getColumnIndex("projectName"), i - 1, tblMain.getColumnIndex("projectName"), KDTMergeManager.FREE_ROW_MERGE);
					curProjectId = table[i][2];
					start = i;
				}
				if (i == end) {
					this.tblMain.getMergeManager().mergeBlock(start, tblMain.getColumnIndex("projectAmount"), i, tblMain.getColumnIndex("projectAmount"), KDTMergeManager.FREE_ROW_MERGE);
					this.tblMain.getMergeManager().mergeBlock(start, tblMain.getColumnIndex("projectName"), i, tblMain.getColumnIndex("projectName"), KDTMergeManager.FREE_ROW_MERGE);
				}
			}
		}
	}

	private void fillAccountProjectAmount(IRow row, String companyId, String curProjectId, String accountId, String periodId, String contrastSide, String moneyId, String fullOrgUnitId)
			throws BOSException, SQLException, Exception {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		/*
		 * FDC：会计科目余额方向 -1贷;1借 FEntryDC:凭证分录借贷方向 1借；0贷 款项对照取数方式：DebitSide
		 * 借方发生额;CreditSide 贷方发生额
		 */
		builder.appendSql(" select  sum(case when VoucherEntry.FEntryDC=1 THEN ISNULL(VoucherEntry.FOriginalAmount,0) ELSE 0 END) AS DebitSideAmount,   ");
		builder.appendSql("         sum(case when VoucherEntry.FEntryDC=0 THEN ISNULL(VoucherEntry.FOriginalAmount,0) ELSE 0 END) AS CreditSideAmount   ");
		builder.appendSql("  from  T_GL_Voucher Voucher        ");
		builder.appendSql("  inner join T_GL_VoucherEntry VoucherEntry on   ");
		builder.appendSql("  VoucherEntry.FBillID=Voucher.FID           ");
		builder.appendSql("  inner JOIN T_BD_AccountView AccountView ON    ");
		builder.appendSql("  VoucherEntry.FAccountID = AccountView.FID  ");
		builder.appendSql(" LEFT JOIN T_GL_VoucherAssistRecord VoucherAssistRecord   ON    ");
		builder.appendSql(" VoucherEntry.FID=VoucherAssistRecord.FEntryID          ");
		builder.appendSql("  LEFT JOIN T_BD_AssistantHG AssistantHG     ON       ");
		builder.appendSql("  VoucherAssistRecord.FAssGrpID=AssistantHG.FID   ");
		builder.appendSql("WHERE Voucher.FBizStatus in (3,5) ");
		appendStringFilterSql(builder, "Voucher.FPeriodID", periodId);
		appendStringFilterSql(builder, "VoucherEntry.FAccountID", accountId);
		appendStringFilterSql(builder, "Voucher.FCompanyId", fullOrgUnitId);
		if (curProjectId != null && (!curProjectId.equals("")) && (!" ".equals(curProjectId))) {
			appendStringFilterSql(builder, "AssistantHG.FCurProjectId", curProjectId);
		} else {
			builder.appendSql("         AND AssistantHG.FCurProjectId IS NULL  ");
		}

		BigDecimal debitAmount = FDCHelper.ZERO;
		BigDecimal creditAmount = FDCHelper.ZERO;
		BigDecimal accountAmount = FDCHelper.ZERO;
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			if (tableSet.getString("DebitSideAmount") != null && tableSet.getString("DebitSideAmount") != "") {
				debitAmount = FDCHelper.toBigDecimal(tableSet.getString("DebitSideAmount"));
			}
			if (tableSet.getString("CreditSideAmount") != null && tableSet.getString("CreditSideAmount") != "") {
				creditAmount = FDCHelper.toBigDecimal(tableSet.getString("CreditSideAmount"));
			}
		}
		contrastSide = getMoneyContrastSide(moneyId, fullOrgUnitId);
		if ("1DebitSide".equals(contrastSide)) {
			accountAmount = debitAmount;
		} else if ("2CreditSide".equals(contrastSide)) {
			accountAmount = creditAmount;
		} else if ("3DebitSubStractCredit".equals(contrastSide)) {
			accountAmount = debitAmount.subtract(creditAmount);
		} else if ("4CreditSubStractDebit".equals(contrastSide)) {
			accountAmount = creditAmount.subtract(debitAmount);
		} else {
			accountAmount = creditAmount.subtract(debitAmount);
		}
		row.getCell("projectAmount").setValue(accountAmount);
	}

	private String getMoneyContrastSide(String moneyDeinfId, String fullOrgUnitId) throws BOSException, SQLException, Exception {
		String FContrastSide = null;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select FContrastSide FROM T_SHE_MoneySubjectContrast ");
		builder.appendSql(" where FMoneyDefineID='" + moneyDeinfId + "' ");
		builder.appendSql(" AND FFullOrgUnitID='" + fullOrgUnitId + "' ");
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			if (tableSet.getString("FContrastSide") != null && tableSet.getString("FContrastSide") != "") {
				FContrastSide = tableSet.getString("FContrastSide");
			}
		}
		return FContrastSide;
	}

	private void fillAccountAmount(IRow row, String companyId, String accountId, String periodId, String contrastSide, String moneyId, String fullOrgUnitId) throws BOSException, SQLException,
			Exception {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		/*
		 * FDC：会计科目余额方向 -1贷;1借 FEntryDC:凭证分录借贷方向 1借；0贷 款项对照取数方式：DebitSide
		 * 借方发生额;CreditSide 贷方发生额
		 */
		builder.appendSql(" select  sum(case when VoucherEntry.FEntryDC=1 THEN ISNULL(VoucherEntry.FOriginalAmount,0) ELSE 0 END) AS DebitSideAmount,   ");
		builder.appendSql("         sum(case when VoucherEntry.FEntryDC=0 THEN ISNULL(VoucherEntry.FOriginalAmount,0) ELSE 0 END) AS CreditSideAmount   ");
		builder.appendSql("  from  T_GL_Voucher Voucher        ");
		builder.appendSql("  inner join T_GL_VoucherEntry VoucherEntry on   ");
		builder.appendSql("  VoucherEntry.FBillID=Voucher.FID           ");
		builder.appendSql("  inner JOIN T_BD_AccountView AccountView ON    ");
		builder.appendSql("  VoucherEntry.FAccountID = AccountView.FID  ");
		builder.appendSql("  WHERE Voucher.FBizStatus in (3,5) ");
		appendStringFilterSql(builder, "Voucher.FPeriodID", periodId);
		appendStringFilterSql(builder, "VoucherEntry.FAccountID", accountId);
		appendStringFilterSql(builder, "Voucher.FCompanyId", fullOrgUnitId);

		BigDecimal debitAmount = FDCHelper.ZERO;
		BigDecimal creditAmount = FDCHelper.ZERO;
		BigDecimal accountAmount = FDCHelper.ZERO;
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			if (tableSet.getString("DebitSideAmount") != null && tableSet.getString("DebitSideAmount") != "") {
				debitAmount = FDCHelper.toBigDecimal(tableSet.getString("DebitSideAmount"));
			}
			if (tableSet.getString("CreditSideAmount") != null && tableSet.getString("CreditSideAmount") != "") {
				creditAmount = FDCHelper.toBigDecimal(tableSet.getString("CreditSideAmount"));
			}
		}
		contrastSide = getMoneyContrastSide(moneyId, fullOrgUnitId);

		if ("1DebitSide".equals(contrastSide)) {
			accountAmount = debitAmount;
		} else if ("2CreditSide".equals(contrastSide)) {
			accountAmount = creditAmount;
		} else if ("3DebitSubStractCredit".equals(contrastSide)) {
			accountAmount = debitAmount.subtract(creditAmount);
		} else if ("4CreditSubStractDebit".equals(contrastSide)) {
			accountAmount = creditAmount.subtract(debitAmount);
		} else {
			accountAmount = creditAmount.subtract(debitAmount);
		}
		row.getCell("accountAmount").setValue(accountAmount);
		BigDecimal moneyAccountAmount = FDCHelper.ZERO;
		moneyAccountAmount = FDCHelper.toBigDecimal(row.getCell("moneyAccountAmount").getValue());
		moneyAccountAmount = moneyAccountAmount.subtract(accountAmount);
		row.getCell("balance").setValue(moneyAccountAmount);
		if (FDCHelper.toBigDecimal(row.getCell("moneyAmount").getValue()).compareTo(FDCHelper.ZERO) == 0) {
			if (FDCHelper.toBigDecimal(row.getCell("accountAmount").getValue()).compareTo(FDCHelper.ZERO) == 0) {
				row.getStyleAttributes().setHided(true);
			}
		}
	}

	private void fillMoneyAmountData(IRow row, String sellProjectId, String accountId, String moneyDefineId, String moneyDefineType, Date beginDate, Date endDate, String sellProjectIds)
			throws BOSException, SQLException, Exception {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("  SELECT  FDCReceiveBill.FSellProjectID,FDCReceiveBillEntry.FOppSubjectID,  ");
		builder.appendSql("          FDCReceiveBillEntry.FMoneyDefineID,MoneyDefine.FMoneyType,        ");
		builder.appendSql("  Sum(ISNULL(FDCReceiveBillEntry.FAmount,0)) as moneyAmount                 ");
		builder.appendSql("  FROM T_SHE_FDCReceiveBillEntry FDCReceiveBillEntry                        ");
		builder.appendSql("  INNER join  T_CAS_ReceivingBill ReceivingBill on                          ");
		builder.appendSql("   FDCReceiveBillEntry.FReceivingBillID=ReceivingBill.FID                   ");
		builder.appendSql("  INNER join  T_SHE_FDCReceiveBill FDCReceiveBill on                        ");
		builder.appendSql("   ReceivingBill.FFdcReceiveBillID=FDCReceiveBill.FID                       ");
		// 关联会计科目
		builder.appendSql("   INNER JOIN T_BD_AccountView AccountView                                  ");
		builder.appendSql("  ON FDCReceiveBillEntry.FOppSubjectID=AccountView.FID                      ");
		// 关联款项
		builder.appendSql("   INNER JOIN T_SHE_MoneyDefine MoneyDefine                                 ");
		builder.appendSql("  ON FDCReceiveBillEntry.FMoneyDefineID=MoneyDefine.FID                     ");
		builder.appendSql("  WHERE MoneyDefine.FSysType='SalehouseSys'                                 ");
		builder.appendSql("  AND FDCReceiveBill.FSellProjectID IN (" + allProjectIds + ")              ");
		appendStringFilterSql(builder, "FDCReceiveBill.FSellProjectID", sellProjectId);
		appendStringFilterSql(builder, "FDCReceiveBillEntry.FOppSubjectID", accountId);
		appendStringFilterSql(builder, "FDCReceiveBillEntry.FMoneyDefineID", moneyDefineId);
		appendStringFilterSql(builder, "MoneyDefine.FMoneyType", moneyDefineType);
		appendDateFilterSql(builder, "ReceivingBill.FBizDate", beginDate, endDate);
		builder.appendSql("  AND ReceivingBill.FBillStatus in (11,12,14)                                ");
		builder.appendSql("  GROUP BY FDCReceiveBill.FSellProjectID,FDCReceiveBillEntry.FOppSubjectID,  ");
		builder.appendSql("           FDCReceiveBillEntry.FMoneyDefineID,MoneyDefine.FMoneyType         ");
		BigDecimal moneyAmount = FDCHelper.ZERO;
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			if (tableSet.getString("moneyAmount") != null && tableSet.getString("moneyAmount") != "") {
				moneyAmount = FDCHelper.toBigDecimal(tableSet.getString("moneyAmount"));
			}
		}
		row.getCell("moneyAmount").setValue(moneyAmount);
	}

	private void fillMoneyAccountAmountData(IRow row, String sellProjectIds, String accountId, String moneyDefineId, String moneyDefineType, Date beginDate, Date endDate) throws BOSException,
			SQLException, Exception {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("  SELECT  FDCReceiveBillEntry.FOppSubjectID,                             ");
		builder.appendSql("  Sum(ISNULL(FDCReceiveBillEntry.FAmount,0)) as moneyAccountAmount       ");
		builder.appendSql("  FROM T_SHE_FDCReceiveBillEntry FDCReceiveBillEntry                     ");
		builder.appendSql("  INNER join  T_CAS_ReceivingBill ReceivingBill on                       ");
		builder.appendSql("   FDCReceiveBillEntry.FReceivingBillID=ReceivingBill.FID                ");
		builder.appendSql("  INNER join  T_SHE_FDCReceiveBill FDCReceiveBill on                     ");
		builder.appendSql("   ReceivingBill.FFdcReceiveBillID=FDCReceiveBill.FID                    ");
		// 关联会计科目
		builder.appendSql("   INNER JOIN T_BD_AccountView AccountView                               ");
		builder.appendSql("  ON FDCReceiveBillEntry.FOppSubjectID=AccountView.FID                   ");
		// 关联款项
		builder.appendSql("   INNER JOIN T_SHE_MoneyDefine MoneyDefine                              ");
		builder.appendSql("  ON FDCReceiveBillEntry.FMoneyDefineID=MoneyDefine.FID                  ");
		builder.appendSql("  WHERE MoneyDefine.FSysType='SalehouseSys'                              ");
		builder.appendSql("  AND FDCReceiveBill.FSellProjectID IN (" + allProjectIds + ")           ");
		appendStringFilterSql(builder, "FDCReceiveBillEntry.FOppSubjectID", accountId);
		appendDateFilterSql(builder, "ReceivingBill.FBizDate", beginDate, endDate);
		builder.appendSql("  AND ReceivingBill.FBillStatus in (11,12,14)                            ");
		builder.appendSql("  GROUP BY FDCReceiveBillEntry.FOppSubjectID  ");
		BigDecimal moneyAcountAmount = FDCHelper.ZERO;
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			if (tableSet.getString("moneyAccountAmount") != null && tableSet.getString("moneyAccountAmount") != "") {
				moneyAcountAmount = FDCHelper.toBigDecimal(tableSet.getString("moneyAccountAmount"));
			}
		}
		row.getCell("moneyAccountAmount").setValue(moneyAcountAmount);
	}

	/**
	 * 添加时间过滤
	 * 
	 * @param builder
	 * @param proName
	 * @throws Exception
	 */
	private void appendDateFilterSql(FDCSQLBuilder builder, String proName, Date beginDate, Date endDate) throws Exception {

		if (beginDate != null) {
			builder.appendSql(" and " + proName + " >= ? ");
			builder.addParam(FDCDateHelper.getSqlDate(beginDate));
		}
		if (endDate != null) {
			builder.appendSql(" and " + proName + " < ? ");
			builder.addParam(FDCDateHelper.getNextDay(endDate));
		}
	}

	/**
	 * 添加时间过滤
	 * 
	 * @param builder
	 * @param proName
	 * @throws Exception
	 */
	private void appendStringFilterSql(FDCSQLBuilder builder, String proName, String proValue) throws Exception {

		if (proValue != null) {
			builder.appendSql(" and " + proName + " = ? ");
			builder.addParam(proValue);
		}

	}

	private void getAllSellProjectIds(TreeNode treeNode) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		if (thisNode.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) thisNode.getUserObject();
			if (allProjectIds != null) {
				allProjectIds += ",'" + sellProject.getId().toString() + "'";
			} else {
				allProjectIds = "'" + sellProject.getId().toString() + "'";
			}
		}

		if (!treeNode.isLeaf()) {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllSellProjectIds(treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}

	public static String getAllLongNumber(String longNumber) throws Exception {
		String longNumberSqlSet = "'" + longNumber + "'";
		while (longNumber.indexOf("!") > 0) {
			longNumberSqlSet = longNumberSqlSet + ",'" + longNumber.substring(0, longNumber.lastIndexOf("!")) + "'";
			longNumber = longNumber.substring(0, longNumber.lastIndexOf("!"));
		}
		return longNumberSqlSet;
	}

	private static String getFinancialUnitId(String longNumberSet) throws Exception {
		String FID = null;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select FID,FLongNumber from T_ORG_BaseUnit  ");
		builder.appendSql("  where FIsCompanyOrgUnit=1 and              ");
		builder.appendSql("  FLongNumber in(" + longNumberSet + ")      ");
		builder.appendSql("  order by FLongNumber                       ");
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			if (tableSet.getString("FID") != null && tableSet.getString("FID") != "") {
				FID = tableSet.getString("FID");
			}
		}
		return FID;
	}

	private static String getFinancialUnitName(String longNumberSet) throws Exception {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		String FName = null;
		builder.appendSql(" select FName_l2 as FName,FLongNumber              ");
		builder.appendSql(" from T_ORG_BaseUnit  where FIsCompanyOrgUnit=1    ");
		builder.appendSql(" and    FLongNumber in(" + longNumberSet + ")      ");
		builder.appendSql("  order by FLongNumber                             ");
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			if (tableSet.getString("FName") != null && tableSet.getString("FName") != "") {
				FName = tableSet.getString("FName");
			}
		}
		return FName;
	}
	
	/**
	 * 解决右键导出的中断问题
	 */
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}
}