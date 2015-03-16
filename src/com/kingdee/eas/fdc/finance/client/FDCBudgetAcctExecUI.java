/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

//import com.ibm.db2.jcc.b.ob;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.analysis.report.ui.style.MegerNameSelectedDlg;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.client.AimCostClientHelper;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCRenderHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctDataInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctItemInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctFactory;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class FDCBudgetAcctExecUI extends AbstractFDCBudgetAcctExecUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FDCBudgetAcctExecUI.class);

	// 责任部门过滤界面
	private FDCBudgetAcctDeptFilterUI deptFilterUI =null;
	/**
	 * output class constructor
	 */
	public FDCBudgetAcctExecUI() throws Exception{
		super();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCMonthBudgetAcctFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

	public void onLoad() throws Exception {
		// initTable();
		super.onLoad();

		 //是否显示付款单上实际付款拆分金额及对应差异列  @author ling_peng
		if(!isShowMonthActDif()){
			getMainTable().getColumn("shifu").getStyleAttributes().setHided(true);
			getMainTable().getColumn("shifuDif").getStyleAttributes().setHided(true);
			getMainTable().getColumn("shifuDifRate").getStyleAttributes().setHided(true);
		}
		
		tblMain.getColumn("acctNumber").setRenderer(
				FDCRenderHelper.getLongNumberRender());
		tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int number_col_index = tblMain.getColumn("acctName")
						.getColumnIndex();
				tblMain.getViewManager()
						.setFreezeView(-1, number_col_index + 1);
			}
		});
		getMainTable().getHeadMergeManager().setMergeMode(
				KDTMergeManager.FREE_MERGE);
		if (getUIContext().get("costCenterId") != null) {
			fillTable();
		}
		setUnionAll();
	}

	protected void initWorkButton() {
		super.initWorkButton();
		actionAddNew.setVisible(false);
		actionAddNew.setEnabled(false);
		actionEdit.setVisible(false);
		actionEdit.setEnabled(false);
		actionView.setVisible(false);
		actionView.setEnabled(false);
		actionRemove.setVisible(false);
		actionRemove.setEnabled(false);
		actionLocate.setVisible(false);
		actionLocate.setEnabled(false);

		actionShowBlankRow.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_entirely"));
		actionHideBlankRow.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_partshow"));
		actionDeptQuery.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_filter"));
		actionShowBlankRow.setVisible(true);
		actionShowBlankRow.setEnabled(true);
		actionHideBlankRow.setVisible(true);
		actionHideBlankRow.setEnabled(true);
		// 暂不提供打印
		actionPrint.setEnabled(false);
		actionPrintPreview.setEnabled(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		actionCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
		menuBiz.setVisible(true);
		menuBiz.setEnabled(true);
		menuEdit.setVisible(false);
	}

	protected void execQuery() {
		try {
			fillTable();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
			// 修改过
			SysUtil.abort();
		}
	}

	private CommonQueryDialog commonQueryDialog = null;
	private CustomerQueryPanel filterUI = null;

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowFilter(false);
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setUiObject(null);

		return commonQueryDialog;
	}

	protected boolean isShowAttachmentAction() {

		return false;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new FDCBudgetAcctExecFilterUI();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	protected boolean initDefaultFilter() {
		return true;
	}

	protected void fetchData() {
		FDCCustomerParams cp = new FDCCustomerParams(getFilterUI()
				.getCustomerParams());
		if (cp.get("prjId") == null && this.mainQuery != null
				&& this.mainQuery.getFilter() != null) {
			// 从fitlerInfo中获取
			FilterInfo filter = this.mainQuery.getFilter();
			for (Iterator iter = filter.getFilterItems().iterator(); iter
					.hasNext();) {
				FilterItemInfo item = (FilterItemInfo) iter.next();
				if (item.getPropertyName() != null
						&& item.getPropertyName().equals("curProject.id")) {
					cp.add("prjId", (String) item.getCompareValue());
				}
				if (item.getPropertyName() != null
						&& item.getPropertyName().equals("fdcPeriod.year")) {
					cp.add("year", (Integer) item.getCompareValue());
				}
				if (item.getPropertyName() != null
						&& item.getPropertyName().equals("fdcPeriod.month")) {
					cp.add("month", (Integer) item.getCompareValue());
				}

			}
		}

		/*
		 * 处理从预算系统进行的查看 成本中心key：costCenterId（字符串） 年度key：year（Integer）
		 * 月度key：month（Integer）
		 */
		if (getUIContext().get("costCenterId") != null) {
			if (getUIContext().get("year") == null
					|| getUIContext().get("month") == null) {
				FDCMsgBox.showError(this, "传入的参数年/月为空!");
				SysUtil.abort();
			}
			String costCenterId = (String) getUIContext().get("costCenterId");

			try {
				String curProjectId = FDCHelper.getCurProjectId(null,
						costCenterId);
				if (curProjectId == null) {
					FDCMsgBox.showWarning(this, "项目不存在");
					SysUtil.abort();
				}
				cp.add("prjId", curProjectId);
			} catch (BOSException e) {
				handUIException(e);
			}
			cp.add("year", (Integer) getUIContext().get("year"));
			cp.add("month", (Integer) getUIContext().get("month"));
			getUIContext().remove("costCenterId");
			getUIContext().remove("year");
			getUIContext().remove("month");
		}

		if (cp.get("prjId") == null) {
			return;
		}
		Map param = new HashMap();
		param.put("projectId", cp.get("prjId"));
		param.put("fdcPeriod", FDCBudgetPeriodInfo.getPeriod(cp.getInt("year"),
				cp.getInt("month"), false));
		param.put("deptmentId", getUIContext().get("deptmentId"));
		param.put("type", "FDCBudgetAcctExecUI");
		try {
			dataMap = FDCMonthBudgetAcctFactory.getRemoteInstance()
					.fetchExecDate(param);
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		}
	}

	public void fillTable() throws EASBizException, BOSException {
		clear();
		fetchData();
		prmtProject.setValue(getDataMap().get("curProject"));
		FDCBudgetPeriodInfo period = (FDCBudgetPeriodInfo) getDataMap().get(
				"fdcPeriod");
		if (period != null) {
			this.spYear.setValue(new Integer(period.getYear()));
			this.spMonth.setValue(new Integer(period.getMonth()));
		}
		try {
			tblMain.setRefresh(false);
			tblMain.removeRows(false);
			initTable();
			addColumnTree();
			Integer maxLevel = (Integer) getDataMap().get("maxLevel");
			if (maxLevel != null) {
				getMainTable().getTreeColumn()
						.setDepth(maxLevel.intValue() + 2);
			}
			CostAccountCollection costAccounts = getCostAccounts();
			for (Iterator iter = costAccounts.iterator(); iter.hasNext();) {
				CostAccountInfo info = (CostAccountInfo) iter.next();
				IRow row = tblMain.addRow();
				row.getCell("acctNumber").setValue(info.getLongNumber());
				row.getCell("acctName").setValue(info.getName());
				row.setTreeLevel(info.getLevel() - 1);
				// 明细行上才会有科目实体
				if (info.isIsLeaf()) {
					fillEntrys(info);
					row.setUserObject(info);
					fillCostAccountRow(row);
					// row.getStyleAttributes().setBackground(FDCTableHelper.
					// daySubTotalColor);
				} else {
				}
				row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			}
			afterFillTable();
		} finally {
			tblMain.setRefresh(true);
			tblMain.repaint();
			// setOprtState(this.oprtState);
		}
	}

	protected void afterFillTable() throws EASBizException, BOSException {
		setUnionAll();
		KDTable table = getMainTable();
		table.getColumn("dynCost").getStyleAttributes().setHided(
				!isShowDynAimCost());
		table.getColumn("aimCost").getStyleAttributes().setHided(
				!isShowDynAimCost());
		Set costColumns = getCostColumns();
		boolean isShowCost = isShowCost();
		for (Iterator iter = costColumns.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			table.getColumn(key).getStyleAttributes().setHided(!isShowCost);
		}

		actionDeptQuery.setVisible(isHasQueryDeptment());
		this.btnHideBlankRow.doClick();
	}

	/**
	 * @author ling_peng
	 */
	private void setUnionAll() throws BOSException, EASBizException {
		if (getMainTable().getRowCount() > 0) {
			for (int i = 0; i < getMainTable().getRowCount(); i++) {
				_setUnionSubRow(getMainTable().getRow(i), getSumColumns(),
						getUnSettledSumColumns(), getSettledSumColumns());
			}
			for (int i = 0; i < getMainTable().getRowCount(); i++) {
				IRow row = getMainTable().getRow(i);
				Object obj = row.getUserObject();
				if (obj == null || obj instanceof CostAccountInfo) {
					// 累计发生-欠款
					row.getCell("lstAllNoPaid").setValue(
							FDCNumberHelper.subtract(row.getCell("lstAllCost")
									.getValue(), row.getCell("lstAllPay")
									.getValue()));
					// 累计发生-付款率
					row.getCell("lstAllPaidRate").setValue(
							FDCNumberHelper.divide(row.getCell("lstAllPay")
									.getValue(), row.getCell("lstAllCost")
									.getValue(), 4, BigDecimal.ROUND_HALF_UP));
					/*
					 * 待发生余额-成本：动态成本-累计发生-成本 待发生余额-付款：动态成本-累计发生-付款
					 */
					row.getCell("waitCost").setValue(
							FDCNumberHelper.subtract(row.getCell("dynCost")
									.getValue(), row.getCell("lstAllCost")
									.getValue()));
					row.getCell("waitPay").setValue(
							FDCNumberHelper.subtract(row.getCell("dynCost")
									.getValue(), row.getCell("lstAllPay")
									.getValue()));
					/*
					 * 年度预算余额-成本：年度预算-本年实际,待签订合同行无数据 年度预算余额-付款：年度预算-本年实际 直接汇总
					 */
					row.getCell("yearBudgetCostBalance").setValue(
							FDCNumberHelper.subtract(row.getCell("yearCost")
									.getValue(), row.getCell("yearActCost")
									.getValue()));
					// row.getCell("yearBudgetPayBalance").setValue(
					// FDCNumberHelper
					// .subtract(row.getCell("yearPay").getValue(),
					// row.getCell("yearActPay").getValue()));

					/**
					 * 根据用户选择实施某一种方案
					 * 
					 * @author ling_peng
					 */
					if (isChooseWhichPayType()) {
						
						//付款差异-金额：本月实际付款-本月计划付款 直接汇总
						row.getCell("payDif").setValue(
								FDCNumberHelper.subtract(row.getCell("actPay")
										.getValue(), row.getCell("planPay")
										.getValue()));
						//实付差异-金额：本月实际_实付-本月计划_付款
						row.getCell("shifuDif").setValue(FDCNumberHelper.subtract(row.getCell("shifu").getValue(),row.getCell("planPay").getValue()));
					} else {
						// 付款差异-金额:本月计划付款-本月实际付款 直接汇总
						row.getCell("payDif").setValue(
								FDCNumberHelper.subtract(row.getCell("planPay")
										.getValue(), row.getCell("actPay")
										.getValue()));
						//实付差异-金额：本月实际_付款-本月计划_实付
						row.getCell("shifuDif").setValue(FDCNumberHelper.subtract(row.getCell("planPay").getValue(), row.getCell("shifu").getValue()));
					}
					
					//付款差异-比例：金额/本月计划付款
				
					row.getCell("payDifRate").setValue(
							FDCNumberHelper.divide(row.getCell("payDif")
									.getValue(), row.getCell("planPay")
									.getValue(), 4, BigDecimal.ROUND_HALF_UP));
					//实付差异-比例：实付差异_金额/本月计划_付款
					row.getCell("shifuDifRate").setValue(FDCNumberHelper.divide(row.getCell("shifuDif").getValue(), row.getCell("planPay").getValue(), 4, BigDecimal.ROUND_HALF_UP));
				}
				if (isSettlecContractRow(row)) {
					/**
					 * @author ling_peng
					 */
					if (isChooseWhichPayType()) {
						row.getCell("payDif").setValue(
								FDCNumberHelper.subtract(row.getCell("actPay")
										.getValue(), row.getCell("planPay")
										.getValue()));
						row.getCell("shifuDif").setValue(FDCNumberHelper.subtract(row.getCell("shifu").getValue(),row.getCell("planPay").getValue()));
					} else {
						row.getCell("payDif").setValue(
								FDCNumberHelper.subtract(row.getCell("planPay")
										.getValue(), row.getCell("actPay")
										.getValue()));
						row.getCell("shifuDif").setValue(FDCNumberHelper.subtract(row.getCell("planPay").getValue(), row.getCell("shifu").getValue()));
					}
					row.getCell("payDifRate").setValue(
							FDCNumberHelper.divide(row.getCell("payDif")
									.getValue(), row.getCell("planPay")
									.getValue(), 4, BigDecimal.ROUND_HALF_UP));
					//实付差异-比例：实付差异_金额/本月计划_付款
					row.getCell("shifuDifRate").setValue(FDCNumberHelper.divide(row.getCell("shifuDif").getValue(), row.getCell("planPay").getValue(), 4, BigDecimal.ROUND_HALF_UP));
				}

				List list = new ArrayList();
				list.add("budget");
				list.add("plan");
				list.add("act");
				for (int h = 0; h < list.size(); h++) {
					String key = (String) list.get(h);
					String costKey = key + "Cost";
					String payKey = key + "Pay";
					BigDecimal cost = (BigDecimal) row.getCell(costKey)
							.getValue();
					BigDecimal pay = (BigDecimal) row.getCell(payKey)
							.getValue();
					Color oldColor = Color.WHITE;
					if (row.getCell(costKey).getUserObject() == null) {
						oldColor = row.getCell(costKey).getStyleAttributes()
								.getBackground();
						row.getCell(costKey).setUserObject(oldColor);
					} else {
						oldColor = (Color) row.getCell(costKey).getUserObject();
					}
					if (FDCNumberHelper.subtract(FDCHelper.toBigDecimal(cost),
							FDCHelper.toBigDecimal(pay)).signum() < 0) {
						row.getCell(costKey).getStyleAttributes()
								.setBackground(FDCTableHelper.warnColor);
						row.getCell(payKey).getStyleAttributes().setBackground(
								FDCTableHelper.warnColor);
					} else {
						row.getCell(costKey).getStyleAttributes()
								.setBackground(oldColor);
						row.getCell(payKey).getStyleAttributes().setBackground(
								oldColor);
					}
				}
			}
		}
		afterSetUnionAll();
	}

	/**
	 * 递归汇总
	 * 
	 * @param row
	 */
	private void _setUnionSubRow(IRow row, List sumCols, List unSettledList,
			List settledList) {
		KDTable table = getMainTable();
		Object obj = row.getUserObject();
		int level = row.getTreeLevel();
		if (obj instanceof FDCBudgetAcctEntryInfo) {
			// 明细行
			return;
		}
		if (isUnSettlecContractRow(row)) {
			// 待签订合同行
			List subRows = new ArrayList();
			for (int i = row.getRowIndex() + 1; i < getMainTable()
					.getRowCount(); i++) {
				IRow tmpRow = getMainTable().getRow(i);
				if (tmpRow.getTreeLevel() == level + 1) {
					subRows.add(tmpRow);
				}
				if (tmpRow.getTreeLevel() <= level) {
					break;
				}
			}
			sumRow(row, subRows, unSettledList);
			return;
		}
		if (isSettlecContractRow(row)) {
			// 已签订合同行
			List subRows = new ArrayList();
			for (int i = row.getRowIndex() + 1; i < getMainTable()
					.getRowCount(); i++) {
				IRow tmpRow = getMainTable().getRow(i);
				if (tmpRow.getTreeLevel() == level + 1) {
					subRows.add(tmpRow);
				}
				if (tmpRow.getTreeLevel() <= level) {
					break;
				}
			}
			sumRow(row, subRows, settledList);
			return;
		}
		// 明细科目行
		if (row.getUserObject() instanceof CostAccountInfo
				&& ((CostAccountInfo) row.getUserObject()).isIsLeaf()) {
			List subRows = new ArrayList();
			for (int i = row.getRowIndex() + 1; i < table.getRowCount(); i++) {
				IRow tmpRow = table.getRow(i);
				if (tmpRow.getTreeLevel() == level + 1) {
					_setUnionSubRow(tmpRow, sumCols, unSettledList, settledList);
					subRows.add(tmpRow);
				}
				if (tmpRow.getTreeLevel() <= level) {
					break;
				}
			}
			sumRow(row, subRows, settledList);
			return;
		}
		// 成本科目行
		List subRows = new ArrayList();
		for (int i = row.getRowIndex() + 1; i < getMainTable().getRowCount(); i++) {
			IRow tmpRow = getMainTable().getRow(i);
			if (tmpRow.getTreeLevel() == level + 1) {
				_setUnionSubRow(tmpRow, sumCols, unSettledList, settledList);
				subRows.add(tmpRow);
			}
			if (tmpRow.getTreeLevel() <= level) {
				break;
			}
		}
		sumRow(row, subRows, sumCols);

	}

	/**
	 * 将subRows按cols列汇总到row
	 * 
	 * @param row
	 * @param subRows
	 * @param cols
	 */
	private void sumRow(IRow row, List subRows, List cols) {
		if (cols.size() == 0) {
			return;
		}
		for (Iterator iter = cols.iterator(); iter.hasNext();) {
			// 先清空再汇总
			row.getCell((String) iter.next()).setValue(null);
		}
		for (Iterator iter = subRows.iterator(); iter.hasNext();) {
			IRow tmpRow = (IRow) iter.next();
			for (Iterator iter2 = cols.iterator(); iter2.hasNext();) {
				String key = (String) iter2.next();
				row.getCell(key).setValue(
						FDCNumberHelper.add(row.getCell(key).getValue(), tmpRow
								.getCell(key).getValue()));
			}
		}
	}

	/**
	 * 科目汇总列
	 * 
	 * @return
	 */
	private List getSumColumns() {
		List list = getSettledSumColumns();
		list.add("aimCost");
		list.add("dynCost");
		list.add("actCost");
		list.add("yearActCost");
		list.add("lstAllCost");
		return list;
	}

	/**
	 * 待签订合同汇总列
	 * 
	 * @return
	 */
	private List getUnSettledSumColumns() {
		List list = new ArrayList();
		list.add("planCost");
		list.add("planPay");
		list.add("splitAmt");
		return list;
	}

	/**
	 * 已签订合同汇总列
	 * 
	 * @return
	 */
	private List getSettledSumColumns() {
		List list = new ArrayList();
		// list.add("splitAmt");
		list.add("planCost");
		list.add("planPay");
		list.add("yearCost");
		list.add("yearPay");
		list.add("toDateYearCost");
		list.add("toDateYearPay");
		list.add("budgetCost");
		list.add("budgetPay");
		list.add("actPay");
		list.add("yearActPay");
		list.add("lstAllPay");
		list.add("yearBudgetPayBalance");
		list.add("splitAmt");
		/**
		 * @author ling_peng 
		 */
		list.add("shifu");
		return list;
	}

	protected void fillCostAccountRow(IRow row) {
		if (row.getUserObject() instanceof CostAccountInfo) {
			CostAccountInfo info = (CostAccountInfo) row.getUserObject();
			String key = info.getId().toString();
			Map map = (Map) getDataMap().get("costMap");
			if (map != null && map.size() > 0) {
				FDCBudgetAcctDataInfo dataInfo = (FDCBudgetAcctDataInfo) map
						.get(key);
				if (dataInfo == null) {
					return;
				}
				row.getCell("aimCost").setValue(dataInfo.getAimCost());
				row.getCell("dynCost").setValue(dataInfo.getDynCost());
				row.getCell("actCost").setValue(
						dataInfo.getBigDecimal("actCost"));
				row.getCell("yearActCost").setValue(
						dataInfo.getBigDecimal("yearActCost"));
				row.getCell("lstAllCost").setValue(
						dataInfo.getBigDecimal("lstAllCost"));

			}
		}
	}

	public void fillEntrys(CostAccountInfo info) throws EASBizException,
			BOSException {
		String costAcctId = info.getId().toString();
		AbstractObjectCollection entrys = getFDCBudgetAcctEntryCollection(costAcctId
				+ FDCBudgetAcctItemTypeEnum.CONTRACT_VALUE);

		if (entrys != null) {
			addSettledContractRow(tblMain.addRow(), info);
			for (Iterator iterator = entrys.iterator(); iterator.hasNext();) {
				FDCBudgetAcctEntryInfo entry = (FDCBudgetAcctEntryInfo) iterator
						.next();
				entry.setCostAccount(info);
				IRow row = tblMain.addRow();
				row.setUserObject(entry);
				loadRow(row);
			}
		}
		// unsettled contract item
		entrys = getFDCBudgetAcctEntryCollection(costAcctId
				+ FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT_VALUE);
		IRow unSettledRow = tblMain.addRow();
		unSettledRow.setUserObject(getFDCBudgetAcctEntryCollection(costAcctId
				+ FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT_VALUE + "year"));
		addUnSettledContractRow(unSettledRow, info);
		if (entrys != null) {
			for (Iterator iterator = entrys.iterator(); iterator.hasNext();) {
				FDCBudgetAcctEntryInfo entry = (FDCBudgetAcctEntryInfo) iterator
						.next();
				entry.setCostAccount(info);
				IRow row = tblMain.addRow();
				row.setUserObject(entry);
				loadRow(row);
			}
			// //add a blank row
			// IObjectValue detailData = createNewDetailData(getMainTable());
			// IRow row=getMainTable().addRow();
			// row.setUserObject(detailData);
			// ((FDCBudgetAcctEntryInfo)detailData).setCostAccount(info);
			// loadRow(row);
		}

	}

	public void loadRow(IRow row) throws EASBizException, BOSException {
		FDCBudgetAcctEntryInfo entry = (FDCBudgetAcctEntryInfo) row
				.getUserObject();
		row.setTreeLevel(entry.getCostAccount().getLevel() + 1);
		row.getCell("conNumber").setValue(entry.getNumber());
		row.getCell("conName").setValue(entry.getName());
		if (entry.getContractBill() != null
				&& entry.getContractBill().getPartB() != null) {
			row.getCell("partB").setValue(
					entry.getContractBill().getPartB().getName());
		} else {
			row.getCell("partB").setValue(null);
		}
		row.getCell("conAmt").setValue(
				entry.getContractBill() == null ? null : entry
						.getContractBill().getAmount());
		row.getCell("conLatestAmt").setValue(entry.getConLatestPrice());
		row.getCell("splitAmt").setValue(entry.getSplitAmt());
		// row.getCell("aimCost").setValue(entry.getAimCost());
		// row.getCell("dynCost").setValue(entry.getDynCost());
		row.getCell("creator").setValue(entry.getCreator());
		row.getCell("deptment").setValue(entry.getDeptment());
		row.getCell("id").setValue(entry.getId());
		row.getCell("id").setUserObject(Boolean.valueOf(entry.isEmptyRow()));

		row.getCell("planCost").setValue(entry.getCost());
		row.getCell("planPay").setValue(entry.getAmount());
		FDCBudgetAcctItemTypeEnum itemType = entry.getItemType();
		if (itemType == FDCBudgetAcctItemTypeEnum.CONTRACT) {
			row.getCell("yearCost").setValue(entry.getBigDecimal("yearCost"));
			row.getCell("yearPay").setValue(entry.getBigDecimal("yearPay"));
			row.getCell("toDateYearCost").setValue(entry.getBigDecimal("toDateYearCost"));
			row.getCell("toDateYearPay").setValue(entry.getBigDecimal("toDateYearPay"));
			row.getCell("budgetCost").setValue(entry.getBigDecimal("budgetCost"));
			row.getCell("budgetPay").setValue(entry.getBigDecimal("budgetPay"));
			row.getCell("actPay").setValue(entry.getBigDecimal("actPay"));
			row.getCell("yearActPay").setValue(entry.getBigDecimal("yearActPay"));
			row.getCell("lstAllPay").setValue(entry.getBigDecimal("lstAllPay"));
			//拆分的已付款数据 @author ling_peng
			row.getCell("shifu").setValue(entry.getBigDecimal("shifu"));
			
			// 年度预算余额
			row.getCell("yearBudgetPayBalance").setValue(
					FDCNumberHelper.subtract(row.getCell("yearPay").getValue(),
							row.getCell("actPay").getValue()));
			BigDecimal payDif = new BigDecimal("0");
			if (isChooseWhichPayType()) {
				payDif = FDCNumberHelper.subtract(
						entry.getBigDecimal("actPay"), entry.getAmount());
				row.getCell("shifuDif").setValue(FDCNumberHelper.subtract(row.getCell("shifu").getValue(),row.getCell("planPay").getValue()));
			} else {
				payDif = FDCNumberHelper.subtract(entry.getAmount(), entry
						.getBigDecimal("actPay"));
				row.getCell("shifuDif").setValue(FDCNumberHelper.subtract(row.getCell("planPay").getValue(), row.getCell("shifu").getValue()));
			}
			//实付差异-比例：实付差异_金额/本月计划_付款
			row.getCell("shifuDifRate").setValue(FDCNumberHelper.divide(row.getCell("shifuDif").getValue(), row.getCell("planPay").getValue(), 4, BigDecimal.ROUND_HALF_UP));
			// 差异
			row.getCell("payDif").setValue(payDif);
			if (entry.getAmount() != null && entry.getAmount().signum() > 0) {
				row.getCell("payDifRate").setValue(
						FDCNumberHelper.divide(payDif, entry.getAmount(), 4,
								BigDecimal.ROUND_HALF_UP));
			}
		} else if (itemType == FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT) {

		} else {

		}

		row.getCell("actCost").setValue(entry.getBigDecimal("actCost"));
		row.getCell("actPay").setValue(entry.getBigDecimal("actPay"));
		fillItems(row);
	}

	public void fillItems(IRow row) {
		FDCBudgetAcctEntryInfo entry = (FDCBudgetAcctEntryInfo) row
				.getUserObject();
		AbstractObjectCollection items = (AbstractObjectCollection) entry
				.get("items");
		if (items == null || items.size() == 0) {
			return;
		}
		// for(Iterator iterator=items.iterator();iterator.hasNext();){
		// FDCBudgetAcctItemInfo item=(FDCBudgetAcctItemInfo)iterator.next();
		//row.getCell(getMonthCostKey(item.getMonth())).setValue(item.getCost())
		// ;
		//row.getCell(getMonthPayKey(item.getMonth())).setValue(item.getAmount()
		// );
		// }
	}

	private CostAccountCollection getCostAccounts() {
		CostAccountCollection costAccountCollection = (CostAccountCollection) getDataMap()
				.get("costAccounts");
		if (costAccountCollection == null) {
			costAccountCollection = new CostAccountCollection();
		}
		return costAccountCollection;
	}

	protected void initTable() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int number_col_index = tblMain.getColumn("acctName")
						.getColumnIndex();
				tblMain.getViewManager()
						.setFreezeView(-1, number_col_index + 1);
			}
		});
		 // @author ling_peng
		FDCHelper.formatTableNumber(getMainTable(), "shifu");
		FDCHelper.formatTableNumber(getMainTable(),"shifuDif");
		FDCHelper.formatTableNumber(getMainTable(), "shifuDifRate","##0.00%");
		
		FDCHelper.formatTableNumber(getMainTable(), "aimCost");
		FDCHelper.formatTableNumber(getMainTable(), "dynCost");
		FDCHelper.formatTableNumber(getMainTable(), getMainTable()
				.getColumnIndex("conAmt"), getMainTable().getColumnIndex(
				"payDif"));
		FDCHelper.formatTable(getMainTable(), "payDifRate", "##0.00%");
		getMainTable().getColumn("payDifRate").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		FDCHelper.formatTable(getMainTable(), "lstAllPaidRate", "##0.00%");
		getMainTable().getColumn("lstAllPaidRate").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
	}

	private Map dataMap = null;

	public Map getDataMap() {
		if (dataMap == null) {
			fetchData();
			if (dataMap == null) {
				dataMap = new HashMap();
			}
		}
		return dataMap;
	}

	private void clear() {
		dataMap = null;
	}

	/**
	 * 按责任部门过滤
	 * 
	 * @return
	 */
	protected boolean isHasQueryDeptment() {
		Boolean obj = (Boolean) getDataMap().get("isHasQueryDeptment");
		return obj == null ? false : obj.booleanValue();
	}

	/**
	 * 是否显示目标成本,动态成本列
	 * 
	 * @param ctx
	 * @return
	 */
	protected boolean isShowDynAimCost() {
		Boolean obj = (Boolean) getDataMap().get("isShowDynAimCost");
		return obj == null ? false : obj.booleanValue();
	}

	/**
	 * 付款计划差异计算采用何种方案
	 * @author ling_peng
	 */
	protected boolean isChooseWhichPayType() {
		Boolean obj = (Boolean) getDataMap().get("isChooseWhichPayType");
		return obj == null ? false : obj.booleanValue();
	}
	/**
	 * 项目计划执行表是否显示付款单上实际付款拆分金额及对应差异列
	 * @author ling_peng
	 */
	protected boolean isShowMonthActDif(){
		Boolean obj=(Boolean)getDataMap().get("isShowMonthActDif");
		return obj==null?false:obj.booleanValue();
	}

	/**
	 * 是否显示成本列
	 * 
	 * @param ctx
	 * @return
	 */
	protected boolean isShowCost() {
		Boolean obj = (Boolean) getDataMap().get("isShowCost");
		return obj == null ? false : obj.booleanValue();
	}

	/**
	 * 是否显示成本合同列
	 * 
	 * @param ctx
	 * @return
	 */
	protected boolean isShowTotal() {
		Boolean obj = (Boolean) getDataMap().get("isShowTotal");
		return obj == null ? false : obj.booleanValue();
	}

	/**
	 * key+getItemTye
	 * 
	 * @param key
	 * @return
	 */
	private AbstractObjectCollection getFDCBudgetAcctEntryCollection(String key) {
		Map map = (Map) getDataMap().get("retBudgetEntrys");
		return (AbstractObjectCollection) map.get(key);
	}

	private void addSettledContractRow(IRow row, CostAccountInfo info) {
		// row.setUserObject(info);
		row.getCell("acctName").setValue(FDCBudgetAcctItemTypeEnum.CONTRACT);
		row.setTreeLevel(info.getLevel());
		row.getStyleAttributes().setBackground(FDCTableHelper.daySubTotalColor);
		row.setUserObject("addSettledContractRow");
	}

	private void addUnSettledContractRow(IRow row, CostAccountInfo info) {
		// row.setUserObject(info);
		row.getCell("acctName").setValue(
				FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT);
		row.setTreeLevel(info.getLevel());
		row.getStyleAttributes().setBackground(FDCTableHelper.daySubTotalColor);
		AbstractObjectCollection c = (AbstractObjectCollection) row
				.getUserObject();
		if (c != null && c.size() > 0) {
			FDCYearBudgetAcctEntryInfo entry = (FDCYearBudgetAcctEntryInfo) c
					.getObject(0);
			if (entry != null) {
				row.getCell("yearCost").setValue(entry.getCost());
				row.getCell("yearPay").setValue(entry.getAmount());
				int queryMonth = getQueryMonth();
				BigDecimal tmpCost = FDCNumberHelper.ZERO;
				BigDecimal tmpPay = FDCNumberHelper.ZERO;
				BigDecimal tmpsumCost = FDCNumberHelper.ZERO;
				BigDecimal tmpSumPay = FDCNumberHelper.ZERO;
				for (Iterator iter = entry.getItems().iterator(); iter
						.hasNext();) {
					FDCBudgetAcctItemInfo item = (FDCBudgetAcctItemInfo) iter
							.next();
					if (item.getMonth() <= queryMonth) {
						if (item.getMonth() == queryMonth) {
							tmpCost = item.getCost();
							tmpPay = item.getAmount();
						}
						tmpsumCost = FDCNumberHelper.add(tmpsumCost, item
								.getCost());
						tmpSumPay = FDCNumberHelper.add(tmpSumPay, item
								.getAmount());
					}
				}
				row.getCell("budgetCost").setValue(tmpCost);
				row.getCell("budgetPay").setValue(tmpPay);
				row.getCell("toDateYearCost").setValue(tmpsumCost);
				row.getCell("toDateYearPay").setValue(tmpSumPay);
			}
		}
	}

	private int getQueryMonth() {
		return this.spMonth.getIntegerVlaue().intValue();
	}

	protected boolean isUnSettlecContractRow(IRow row) {
		if (row.getCell("acctName").getValue() == FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT) {
			return true;
		}
		return false;
	}

	protected boolean isSettlecContractRow(IRow row) {
		if (row.getCell("acctName").getValue() == FDCBudgetAcctItemTypeEnum.CONTRACT) {
			return true;
		}
		return false;
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		// 空实现，防止双击表格中断
	}

	private void addColumnTree() {
		// 设置表格列树
		KDTable table = getMainTable();
		IRow headRow = table.getHeadRow(0);
		// headRow.getStyleAttributes().setLocked(false);
		List list = getColumnTreeCols();
		TreeNodeClickListener listener = new TreeNodeClickListener();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			String[] cols = (String[]) iter.next();
			CellTreeNode node = new CellTreeNode();
			node.setTreeLevel(0);
			node.setHasChildren(true);
			node.setValue(headRow.getCell(cols[0]).getValue());
			listener.addNode(node, cols);
			node.addClickListener(listener);
			for (int i = 0; i < cols.length; i++) {
				headRow.getCell(cols[i]).setValue(node);
				headRow.getCell(cols[i]).getStyleAttributes().setLocked(false);
			}
		}
	}

	protected List getColumnTreeCols() {
		List list = new ArrayList();
		list.add(new String[] { "conName", "conNumber", "partB", "conAmt",
				"splitAmt", "conLatestAmt" });
		list.add(new String[] { "lstAllPay", "lstAllCost", "lstAllNoPaid",
				"lstAllPaidRate", "waitCost", "waitPay" });
		list.add(new String[] { "yearPay", "yearCost", "toDateYearCost",
				"toDateYearPay", "yearActCost", "yearActPay",
				"yearBudgetCostBalance", "yearBudgetPayBalance" });
		list.add(new String[] { "budgetPay", "budgetCost", "planCost",
				"planPay", "actCost", "actPay","shifu", "payDif", "payDifRate", "shifuDif", "shifuDifRate" });
		return list;
	}

	private class TreeNodeClickListener implements NodeClickListener {
		private Map map = new HashMap();

		public TreeNodeClickListener() {
		}

		void addNode(CellTreeNode node, String[] cols) {
			map.put(node, cols);
		}

		public void doClick(CellTreeNode source, ICell cell, int type) {
			//是否显示成本列
			boolean isHide = source.isCollapse();
			String[] cols = (String[]) map.get(source);
			if (cols == null) {
				return;
			}
			Set set = getCostColumns();
			for (int i = 1; i < cols.length; i++) {
				getMainTable().getColumn(cols[i]).getStyleAttributes()
						.setHided(isHide);
				if (!isShowCost() && set.contains(cols[i])) {
					getMainTable().getColumn(cols[i]).getStyleAttributes()
							.setHided(true);
				}
			}
		}
	}

	public void actionDeptQuery_actionPerformed(ActionEvent e) throws Exception {
		showFDCBudgetAcctDeptFilterUI();
	}

	public void actionShowBlankRow_actionPerformed(ActionEvent e)
			throws Exception {
		handleBlankRow(e);
	}

	public void actionHideBlankRow_actionPerformed(ActionEvent e)
			throws Exception {
		handleBlankRow(e);
	}

	private void handleBlankRow(ActionEvent e) {
		ItemAction act = getActionFromActionEvent(e);
		boolean isHided = false;
		if (act.equals(this.actionShowBlankRow)) {
			isHided = false;
		} else {
			isHided = true;
		}
		for (int i = 0; i < getMainTable().getRowCount(); i++) {
			IRow row = getMainTable().getRow(i);
			if (row.getUserObject() instanceof FDCBudgetAcctEntryInfo) {
				if (isEmptyRow(row)) {
					row.getStyleAttributes().setHided(isHided);
				}
			}
			if (isSettlecContractRow(row)) {

			}
			if (isUnSettlecContractRow(row)) {

			}
		}
	}

	protected boolean isEmptyRow(IRow row) {
		if (row.getUserObject() instanceof FDCBudgetAcctEntryInfo) {
			boolean isEmpty = Boolean.TRUE.equals(row.getCell("id")
					.getUserObject());
			return isEmpty;
		}
		return true;
	}

	protected void showFDCBudgetAcctDeptFilterUI() throws Exception {
		if (deptFilterUI == null) {
			deptFilterUI = new FDCBudgetAcctDeptFilterUI();
		}
		AdminOrgUnitInfo adminOrgUnitInfo = deptFilterUI.showUI();
		if (adminOrgUnitInfo != null && adminOrgUnitInfo.getId() != null) {
			getUIContext().put("deptmentId",
					adminOrgUnitInfo.getId().toString());
		} else {
			getUIContext().put("deptmentId", null);
		}
		fillTable();
		this.btnShowBlankRow.doClick();
	}

	/**
	 * 成本列,用于对成本列的操作
	 * 
	 * @return
	 */
	protected Set getCostColumns() {
		Set costList = new HashSet();
		costList.add("budgetCost");
		costList.add("planCost");
		costList.add("actCost");
		costList.add("lstAllCost");
		costList.add("waitCost");
		costList.add("yearCost");
		costList.add("yearBudgetCostBalance");
		costList.add("toDateYearCost");
		costList.add("yearActCost");
		return costList;
	}
	
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		return;
	}

	protected void afterSetUnionAll() {
		List columns = new ArrayList();
		if (isShowDynAimCost()) {
			columns.add("aimCost");
			columns.add("dynCost");
		}
		boolean isShowCost = isShowCost();
		if (isShowCost) {
			Set costColumns = getCostColumns();
			columns.addAll(costColumns);// 进行成本汇总
		}
		columns.add("splitAmt");
		columns.add("yearPay");
		columns.add("toDateYearPay");
		columns.add("budgetPay");
		columns.add("actPay");
		columns.add("yearActPay");
		columns.add("lstAllPay");
		columns.add("waitPay");
		columns.add("planPay");
		columns.add("yearBudgetPayBalance");
		if (isShowTotal()) {
			String[] arrays = new String[columns.size()];
			columns.toArray(arrays);
			try {
				AimCostClientHelper.setTotalCostRow(getMainTable(), arrays);
			} catch (Exception e) {
				handUIException(e);
			}
		}
	}

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		if (getUIContext().get("costCenterId") != null) {
			// 来源预算系统,不显示查询框
			return;
		}
		super.actionQuery_actionPerformed(e);
	}
}