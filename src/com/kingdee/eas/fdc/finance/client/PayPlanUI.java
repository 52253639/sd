/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OUException;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.basedata.scm.common.ExpenseTypeCollection;
import com.kingdee.eas.basedata.scm.common.ExpenseTypeInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI;
import com.kingdee.eas.fdc.finance.ContractPayPlanInfo;
import com.kingdee.eas.fdc.finance.IPayPlan;
import com.kingdee.eas.fdc.finance.PayPlanFactory;
import com.kingdee.eas.fdc.finance.PayPlanInfo;
import com.kingdee.eas.fdc.finance.PlanEntryCollection;
import com.kingdee.eas.fdc.finance.PlanEntryFactory;
import com.kingdee.eas.fdc.finance.PlanEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 月度付款计划 
 */
public class PayPlanUI extends AbstractPayPlanUI {

	private CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext()
			.getCurrentCostUnit();

	/*
	 * private BigDecimal amount1; //保存planAmount1的合计金额 private BigDecimal
	 * amount2; //保存planAmount2的合计金额 private BigDecimal amount3;
	 * //保存planAmount3的合计金额
	 */
	BigDecimal amount;

	private String selectOrgId = null;

	private PayPlanInfo payPlan;

	private Map proLongNameMap = new HashMap();

	// private KDTree projectTree = new KDTree();
	private List projectIdList;

	// 获取当前节点下工程项目叶子节点ID
	private Set projectLeafset = null;
	// 获取当前节点下合同类型叶子节点ID
	private Set conTypeLeafSet = null;

	// 合计行总金额
	private Map amountMap = new HashMap();

	/*
	 * //合计预测月总金额 private Map monthAmount = new HashMap();
	 */

	// 是否使用成本月结,当前财务组织的期间
	private Date[] pkdate;

	private int dymicCols = 0;

	boolean viewPlanByCostcenter = false;

	Map descMap = new HashMap();

	// 修改合同付款计划
	private static boolean payPlanEdit = false;

	public static boolean payPlanEdit() {
		return payPlanEdit;
	}

	/**
	 * output class constructor
	 */
	public PayPlanUI() throws Exception {
		super();
		pkdate = FDCClientHelper.getCompanyCurrentDate();

		viewPlanByCostcenter = FDCUtils.getDefaultFDCParamByKey(null,
				SysContext.getSysContext().getCurrentFIUnit().getId()
						.toString(), FDCConstants.FDC_PARAM_VIEWPLAN);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// super.tblMain_tableClicked(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		if (!this.verify()) {
			return;
		}
		if (!this.isEdited()) {
			return;
		}
		this.payPlan.getEntrys().clear();
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row.getUserObject() instanceof PlanEntryInfo) {
				PlanEntryInfo entry = (PlanEntryInfo) row.getUserObject();
				entry.setPlanAmount1((BigDecimal) row.getCell("planAmount1")
						.getValue());
				entry.setPlanAmount2((BigDecimal) row.getCell("planAmount2")
						.getValue());
				entry.setPlanAmount3((BigDecimal) row.getCell("planAmount3")
						.getValue());
				entry.setDescription((String) row.getCell("description")
						.getValue());
				entry.setNumber((String) row.getCell("contract").getValue());
				entry.setSeq(i + 1);
				this.payPlan.getEntrys().add(entry);
			}
		}
		PayPlanFactory.getRemoteInstance().submit(payPlan);
		this.setMessageText(EASResource
				.getString(FrameWorkClientUtils.strResource + "Msg_Save_OK"));
		this.showMessage();
		this.fillTable();
	}

	/**
	 * output actionPayRequest_actionPerformed
	 */
	public void actionPayRequest_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.tblMain.getRowCount() == 0) {
			return;
		}
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		IRow selectRow = this.tblMain.getRow(index);
		if (selectRow.getUserObject() instanceof PayRequestBillInfo) {
			PayRequestBillInfo payRequest = (PayRequestBillInfo) selectRow
					.getUserObject();
			UIContext uiContext = new UIContext(ui);
			uiContext.put(UIContext.ID, payRequest.getId().toString());
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(PayRequestBillEditUI.class.getName(), uiContext,
							null, "EDIT");
			uiWindow.show();
		}
		this.fillTable();
	}

	protected String getEditUIName() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PlanEntryFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		this.proLongNameMap = FDCHelper.createTreeDataMap(CurProjectFactory
				.getRemoteInstance(), "name", "\\");
		// ProjectTreeBuilder builder = new ProjectTreeBuilder();
		// String companyid =
		// SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		// builder.build(this, projectTree, this.actionOnLoad,companyid);

		super.onLoad();

		// 先初始化控件
		initControl();

		// 继承ListUI,但是有没有查询方案和query的情况下，实现此方法，可以进行表格设置
		this.tHelper.setCanSetTable(true);

		attachListeners();

		DefaultKingdeeTreeNode node = this.findNode(
				(DefaultKingdeeTreeNode) treeMain.getModel().getRoot(),
				this.currentOrg.getId().toString());
		treeMain.setSelectionNode(node);

		buildContractTypeTree();
		DefaultKingdeeTreeNode _node = (DefaultKingdeeTreeNode) contractTypeTree
				.getModel().getRoot();
		contractTypeTree.setSelectionNode(_node);

		fillTable();
		FDCClientHelper.setUIMainMenuAsTitle(this);

		// 右键保存当前样式

		tHelper = new TablePreferencesHelper(this);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	private void initControl() {
		this.menuItemView.setVisible(false);
		this.menuItemEdit.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemRemove.setVisible(false);
		actionLocate.setVisible(false);
		actionQuery.setVisible(true);
		this.actionPayRequest.setVisible(false);
		KDTable table = this.tblMain;
		table.checkParsed();
		table.setColumnMoveable(true);
		table.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		table.getStyleAttributes().setLocked(true);
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		table.getColumn("contractAmount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("contractAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("lastAmount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("lastAmount").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("hasPay").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("hasPay").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("balance").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("balance").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("onPaying").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("onPaying").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);

		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("description").setEditor(txtEditor);

		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("contract").setEditor(txtEditor);

		// int year = Calendar.getInstance().get(Calendar.YEAR);
		// int month = Calendar.getInstance().get(Calendar.MONTH);
		Date curDate = pkdate[0];
		int year = curDate.getYear() + 1900;
		int month = curDate.getMonth() + 1;

		SpinnerNumberModel yearStartMo = new SpinnerNumberModel(year, 1990,
				2099, 1);
		spiStartYear.setModel(yearStartMo);
		SpinnerNumberModel monthStartMo = new SpinnerNumberModel(month, 1, 12,
				1);
		spiStartMonth.setModel(monthStartMo);

		int endYear = year;
		int endMonth = month;
		if (month + 2 > 12) {
			endYear += 1;
			endMonth = month + 2 - 12;
		} else {
			endMonth = month + 2;
		}
		SpinnerNumberModel yearEndMo = new SpinnerNumberModel(endYear, 1990,
				2099, 1);
		spiEndYear.setModel(yearEndMo);
		SpinnerNumberModel monthEndMo = new SpinnerNumberModel(endMonth, 1, 12,
				1);
		spiEndMonth.setModel(monthEndMo);

		// this.tblMain.getHeadRow(0).getCell("planAmount1").setUserObject(
		// this.tblMain.getHeadRow(0).getCell("planAmount1").getValue());
		// this.tblMain.getHeadRow(0).getCell("planAmount2").setUserObject(
		// this.tblMain.getHeadRow(0).getCell("planAmount2").getValue());
		// this.tblMain.getHeadRow(0).getCell("planAmount3").setUserObject(
		// this.tblMain.getHeadRow(0).getCell("planAmount3").getValue());

		tblMain.setBeforeAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_DELETE == e.getType()) {
					if (tblMain.getRow(
							tblMain.getSelectManager().getActiveRowIndex())
							.getUserObject() == null) {
						e.setCancel(true);
					}
				}
			}
		});
	}

	protected void attachListeners() {
		tblMain.getActionMap().put(KDTAction.SELECT_DOWN_CELL,
				actionEntryCellDown);
	}

	protected void detachListeners() {
		tblMain.getActionMap().put(KDTAction.SELECT_DOWN_CELL, null);
	}

	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		super.tblMain_editStopped(e);
		if (tblMain.getColumn(e.getColIndex()).getKey().equals("planAmount1")) {
			setSumRow("planAmount1");
		}

		if (tblMain.getColumn(e.getColIndex()).getKey().equals("planAmount2")) {
			setSumRow("planAmount2");
		}
		if (tblMain.getColumn(e.getColIndex()).getKey().equals("planAmount3")) {
			setSumRow("planAmount3");
		}
	}

	private void setSumRow(String colName) {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row.getUserObject() != null) {
				continue;
			}
			BigDecimal sum = FDCHelper.ZERO;
			for (int j = i + 1; j < this.tblMain.getRowCount(); j++) {
				IRow nextRow = this.tblMain.getRow(j);
				// if (nextRow.getUserObject() == null) {
				if (!(nextRow.getUserObject() instanceof PlanEntryInfo)) {
					break;
				}
				BigDecimal value = (BigDecimal) nextRow.getCell(colName)
						.getValue();
				if (value != null) {
					sum = sum.add(value);
				}
			}
			row.getCell(colName).setValue(null);
			if (sum.compareTo(FDCHelper.ZERO) != 0) {
				row.getCell(colName).setValue(sum);
			}
		}

	}

	protected void spiStartYear_stateChanged(ChangeEvent e) throws Exception {

		check(1, e);

		this.verifySaved(null);
		this.fillTable();
	}

	protected void spiStartMonth_stateChanged(ChangeEvent e) throws Exception {
		check(2, e);

		this.verifySaved(null);
		this.fillTable();
	}

	/**
	 * output spiEndYear_stateChanged method
	 */
	protected void spiEndYear_stateChanged(javax.swing.event.ChangeEvent e)
			throws Exception {
		check(3, e);

		this.verifySaved(null);
		this.fillTable();
	}

	/**
	 * output spiEndMonth_stateChanged method
	 */
	protected void spiEndMonth_stateChanged(javax.swing.event.ChangeEvent e)
			throws Exception {
		check(4, e);

		this.verifySaved(null);
		this.fillTable();
	}

	// 检查能否改变值
	private void check(int tag, ChangeEvent e) {
		EventListener[] listeners1 = null;
		EventListener[] listeners2 = null;
		EventListener[] listeners3 = null;
		EventListener[] listeners4 = null;

		int year1 = getValue(spiStartYear);
		int year2 = getValue(spiEndYear);
		int month1 = getValue(spiStartMonth);
		int month2 = getValue(spiEndMonth);

		Integer startYear = null;
		Integer startMonth = null;
		Integer endYear = null;
		Integer endMonth = null;

		switch (tag) {
		case 1:

			listeners2 = spiStartMonth.getListeners(ChangeListener.class);
			removeListiner(spiStartMonth, listeners2);

			listeners3 = spiEndYear.getListeners(ChangeListener.class);
			removeListiner(spiEndYear, listeners3);

			listeners4 = spiEndMonth.getListeners(ChangeListener.class);
			removeListiner(spiEndMonth, listeners4);

			if (year1 > year2) {
				// spiEndYear.setValue(spiStartYear.getValue());
				endYear = new Integer(year1);
			} else if (year1 == year2) {
				if (month1 > month2 - 2) {
					if (month1 <= 10) {
						endMonth = new Integer(month1 + 2);
					} else {
						endYear = new Integer(year1 + 1);
						endMonth = new Integer(month1 + 2 - 12);
					}
					// spiStartMonth.setValue(spiStartMonth.getValue());
				}
			}

			break;
		case 2:

			listeners1 = spiStartYear.getListeners(ChangeListener.class);
			removeListiner(spiStartYear, listeners1);

			listeners3 = spiEndYear.getListeners(ChangeListener.class);
			removeListiner(spiEndYear, listeners3);

			listeners4 = spiEndMonth.getListeners(ChangeListener.class);
			removeListiner(spiEndMonth, listeners4);

			if (year1 == year2) {

				if (month1 > month2 - 2) {
					if (month1 <= 10) {
						endMonth = new Integer(month1 + 2);
					} else {
						endYear = new Integer(year1 + 1);
						endMonth = new Integer(month1 + 2 - 12);
					}
					// spiStartMonth.setValue(spiStartMonth.getValue());
				}
			} else if (year1 == year2 - 1 && month1 == 12) {
				endMonth = new Integer(month1 + 2 - 12);
			}

			break;

		case 3:

			listeners1 = spiStartYear.getListeners(ChangeListener.class);
			removeListiner(spiStartYear, listeners1);
			listeners2 = spiStartMonth.getListeners(ChangeListener.class);
			removeListiner(spiStartMonth, listeners2);

			listeners4 = spiEndMonth.getListeners(ChangeListener.class);
			removeListiner(spiEndMonth, listeners4);

			if (year1 > year2) {
				// spiStartYear.setValue(spiEndYear.getValue());
				startYear = new Integer(year2);
			} else if (year1 == year2) {
				if (month1 > month2 - 2) {
					if (month2 >= 3) {
						startMonth = new Integer(month2 - 2);
					} else {
						startYear = new Integer(year2 - 1);
						startMonth = new Integer(month2 + 12 - 2);
					}
					// spiStartMonth.setValue(spiStartMonth.getValue());
				}
			}

			break;
		case 4:

			listeners1 = spiStartYear.getListeners(ChangeListener.class);
			removeListiner(spiStartYear, listeners1);
			listeners2 = spiStartMonth.getListeners(ChangeListener.class);
			removeListiner(spiStartMonth, listeners2);

			listeners3 = spiEndYear.getListeners(ChangeListener.class);
			removeListiner(spiEndYear, listeners3);

			if (year1 == year2) {

				if (month1 > month2 - 2) {
					if (month2 >= 3) {
						startMonth = new Integer(month2 - 2);
					} else {
						startYear = new Integer(year2 - 1);
						startMonth = new Integer(month2 + 12 - 2);
					}
					// spiStartMonth.setValue(spiStartMonth.getValue());
				}
			} else if (year1 == year2 - 1 && month2 == 1) {
				startMonth = new Integer(month2 + 12 - 2);
			}

			break;
		}

		if (startYear != null) {
			SpinnerNumberModel yearStartMo = new SpinnerNumberModel(startYear
					.intValue(), 1990, 2099, 1);
			spiStartYear.setModel(yearStartMo);
		}
		if (startMonth != null) {
			SpinnerNumberModel monthStartMo = new SpinnerNumberModel(startMonth
					.intValue(), 1, 12, 1);
			spiStartMonth.setModel(monthStartMo);
		}
		if (endYear != null) {
			SpinnerNumberModel yearEndMo = new SpinnerNumberModel(endYear
					.intValue(), 1990, 2099, 1);
			spiEndYear.setModel(yearEndMo);
		}
		if (endMonth != null) {
			SpinnerNumberModel monthEndMo = new SpinnerNumberModel(endMonth
					.intValue(), 1, 12, 1);
			spiEndMonth.setModel(monthEndMo);
		}

		if (listeners1 != null) {
			addListiner(spiStartYear, listeners1);
		}
		if (listeners2 != null) {
			addListiner(spiStartMonth, listeners2);
		}
		if (listeners3 != null) {
			addListiner(spiEndYear, listeners3);
		}
		if (listeners4 != null) {
			addListiner(spiEndMonth, listeners4);
		}
	}

	private int getValue(KDSpinner spn) {
		return ((Integer) spn.getValue()).intValue();
	}

	private void removeListiner(KDSpinner spn, EventListener[] listeners) {
		for (int i = 0; i < listeners.length; i++) {
			spn.removeChangeListener((ChangeListener) listeners[i]);
		}
	}

	private void addListiner(KDSpinner spn, EventListener[] listeners) {
		for (int i = 0; i < listeners.length; i++) {
			spn.addChangeListener((ChangeListener) listeners[i]);
		}
	}

	/**
	 * 屏蔽回车键
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {

	}

	protected void fillTable() throws Exception {
		/*
		 * amount1=FDCHelper.ZERO; amount2=FDCHelper.ZERO;
		 * amount3=FDCHelper.ZERO;
		 */
		this.tblMain.removeRows();
		this.tblMain.setUserObject(null);
		amountMap.clear();

		this.setHead();
		if (projectLeafset == null || projectLeafset.isEmpty()) {
			return;
		}

		Integer startYear = (Integer) this.spiStartYear.getValue();
		if (startYear.intValue() == 0) {
			return;
		}
		Integer endYear = (Integer) this.spiEndYear.getValue();
		if (endYear.intValue() == 0) {
			return;
		}
		Integer startMonth = (Integer) this.spiStartMonth.getValue();
		Integer endMonth = (Integer) this.spiEndMonth.getValue();

		// 计算月数,删除列,增加列
		removeColumn();
		int months = calMonths(startYear.intValue(), startMonth.intValue(),
				endYear.intValue(), endMonth.intValue());
		addColumn(months);

		// 付款计划相关数据
		Map param = new HashMap();
		param.put("startYear", startYear);
		param.put("endYear", endYear);
		param.put("startMonth", startMonth);
		param.put("endMonth", endMonth);

		param.put("adminId", adminId);
		param.put("supplierId", supplierId);
		Map data = ((IPayPlan) PayPlanFactory.getRemoteInstance()).getData(
				projectLeafset, conTypeLeafSet, param);
		Iterator iter = projectLeafset.iterator();
		while (iter.hasNext()) {
			String SelcOrgId = (String) iter.next();

			// 付款计划
			payPlan = (PayPlanInfo) data.get(SelcOrgId + "_" + "payPlan");
			// 工程项目
			projectIdList = (List) data.get(SelcOrgId + "_" + "projectCol");
			// 合同付款计划
			Map contractPayPlanMap = (Map) data.get(SelcOrgId + "_"+ "contractPayPlanMap");
			// 付款申请单累计金额
			Map payRequestMap = (Map) data.get(SelcOrgId + "_"+ "payRequestMap");
			// 已付款
			Map curPayMap = (Map) data.get(SelcOrgId + "_" + "curPayMap");
			// 全部付款
			Map allPayMap = (Map) data.get(SelcOrgId + "_" + "allPayMap");

			// 合同最新造价
			Map conLastAmountMap = (Map) data.get(SelcOrgId + "_"+ "conLastAmountMap");
			// 合同分工程项目累计
			Map contractMap = (Map) data.get(SelcOrgId + "_" + "contractMap");
			// 付款计划分工程项目+事项
			Map dataMap = (Map) data.get(SelcOrgId + "_" + "dataMap");
			// 事项
			ExpenseTypeCollection expenseTypes = (ExpenseTypeCollection) data.get(SelcOrgId + "_" + "expenseTypes");

			// 待签订合同付款
			// Map dConPayPlanMap = (Map) data.get("dConPayPlanMap");
			Map dConPayPlanMap = (Map) data.get(SelcOrgId + "_"+ "dConPayPlanMap");

			// this.initPlanData(this.selectOrgId);
			// ContractBillCollection contractBills =
			// getContractColl(this.selectOrgId);
			// Map contractPayPlanMap = getContractPayPlanMap(contractBills);
			// Map payRequestMap = this.getRequestMap(contractBills);
			// Map curPayMap = this.getCurPaymentAmountMap(contractBills);
			// Map allPayMap = this.getCurPaymentAmountMap(contractBills);
			// Map conLastAmountMap = getConLastAmountMap(contractBills);
			// Map contractMap = getContractProMap(contractBills);
			//			
			// Map dataMap = getPlanMap();
			// ExpenseTypeCollection expenseTypes = ExpenseTypeFactory
			// .getRemoteInstance().getExpenseTypeCollection();

			// 添加合同计划
			addProjectContractPlan(contractMap, conLastAmountMap,
					contractPayPlanMap, payRequestMap, curPayMap, allPayMap);

			// 添加事项计划
			// addProjectThingPlan(expenseTypes, dataMap);
			addProjectThingPlan(expenseTypes, dataMap, dConPayPlanMap);
			// this.setSumRow("planAmount1");
			// this.setSumRow("planAmount2");
			// this.setSumRow("planAmount3");

		}

		setRelAction();

		// 增加合计行统计
		appendFootRow();
	}

	// 删除动态列
	private void removeColumn() {
		if (dymicCols == 0)
			return;
		int colInex = tblMain.getColumnIndex("onPaying");
		for (int i = dymicCols; i > 0; i--) {
			tblMain.removeColumn(colInex + i);
		}
	}

	// 计算动态列数
	private int calMonths(int startYear, int startMonth, int endYear,
			int endMonth) {
		int months = 1;
		if (startYear == endYear) {
			months = endMonth - startMonth + 1;
		} else {
			months = 12 - startMonth + 1;
			for (int i = startYear + 1; i < endYear; i++) {
				months += 12;
			}
			months += endMonth;
		}

		return months;
	}

	// 添加动态列
	private void addColumn(int months) {

		if (months == 0)
			return;
		dymicCols = months;
		Integer startYear = (Integer) this.spiStartYear.getValue();
		Integer startMonth = (Integer) this.spiStartMonth.getValue();
		// 增加列
		int colInex = tblMain.getColumnIndex("onPaying");
		String text = "月计划金额";
		String yearText = "年";

		for (int i = 1; i < dymicCols + 1; i++) {
			IColumn col = tblMain.addColumn(colInex + i);

			String key = "planAmount" + i;
			col.setKey(key);

			String colName = String.valueOf(startYear) + yearText
					+ (startMonth.intValue() + i - 1) + text;
			for (int j = 1; j < 6; j++) {
				if (startMonth.intValue() + i - 1 > 12 * j) {
					int newStartYear = startYear.intValue() + 1 * j;
					colName = String.valueOf(newStartYear) + yearText
							+ (startMonth.intValue() + i - 1 - 12 * j) + text;
				}
			}

			tblMain.getHeadRow(0).getCell(key).setValue(colName);

			tblMain.getColumn(key).getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			tblMain.getColumn(key).getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);

			KDFormattedTextField formattedTextField = new KDFormattedTextField(
					KDFormattedTextField.DECIMAL);
			formattedTextField.setPrecision(2);
			formattedTextField.setSupportedEmpty(true);
			KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(
					formattedTextField);
			tblMain.getColumn(key).setEditor(numberEditor);

		}

		// IColumn col = tblMain.addColumn(colInex+1);
		// col.setKey("planAmount1");
		// col = tblMain.addColumn(colInex+2);
		// col.setKey("planAmount2");
		// col = tblMain.addColumn(colInex+3);
		// col.setKey("planAmount3");

		// KDTable table = this.tblMain;
		// table.getColumn("planAmount1").getStyleAttributes().setNumberFormat(
		// FDCHelper.getNumberFtm(2));
		//table.getColumn("planAmount1").getStyleAttributes().setHorizontalAlign
		// (
		// HorizontalAlignment.RIGHT);
		// table.getColumn("planAmount2").getStyleAttributes().setNumberFormat(
		// FDCHelper.getNumberFtm(2));
		//table.getColumn("planAmount2").getStyleAttributes().setHorizontalAlign
		// (
		// HorizontalAlignment.RIGHT);
		// table.getColumn("planAmount3").getStyleAttributes().setNumberFormat(
		// FDCHelper.getNumberFtm(2));
		//table.getColumn("planAmount3").getStyleAttributes().setHorizontalAlign
		// (
		// HorizontalAlignment.RIGHT);

		// table.getColumn("planAmount1").setEditor(numberEditor);
		// formattedTextField = new KDFormattedTextField(
		// KDFormattedTextField.DECIMAL);
		// formattedTextField.setPrecision(2);
		// formattedTextField.setSupportedEmpty(true);
		// numberEditor = new KDTDefaultCellEditor(formattedTextField);
		// table.getColumn("planAmount2").setEditor(numberEditor);
		// formattedTextField = new KDFormattedTextField(
		// KDFormattedTextField.DECIMAL);
		// formattedTextField.setPrecision(2);
		// formattedTextField.setSupportedEmpty(true);
		// numberEditor = new KDTDefaultCellEditor(formattedTextField);
		// table.getColumn("planAmount3").setEditor(numberEditor);

	}

	// 添加合同计划
	private void addContractPlan(ContractBillCollection contractBills,
			Map conLastAmountMap, Map contractPayPlanMap, Map payRequestMap,
			Map curPayMap, Map allPayMap) {
		if (contractBills == null) {
			return;
		}
		for (int i = 0; i < contractBills.size(); i++) {
			ContractBillInfo contract = contractBills.get(i);
			String contractId = contract.getId().toString();

			int year = ((Integer) this.spiStartYear.getValue()).intValue();
			int month = ((Integer) this.spiStartMonth.getValue()).intValue() - 1;

			/*
			 * //预算依赖 ContractPayPlanInfo info1 = (ContractPayPlanInfo)
			 * contractPayPlanMap.get(contractId + year + month);
			 * 
			 * month++; if (month >= 12) { year++; month = 0; }
			 * 
			 * ContractPayPlanInfo info2 = (ContractPayPlanInfo)
			 * contractPayPlanMap.get(contractId + year + month);
			 * 
			 * month++; if (month >= 12) { year++; month = 0; }
			 * 
			 * ContractPayPlanInfo info3 = (ContractPayPlanInfo)
			 * contractPayPlanMap.get(contractId + year + month);
			 */

			// 动态列中只要有一个月付款数据不为空，就需要增加一行
			boolean isNeedAddRow = false;
			for (int j = 1; j < dymicCols + 1; j++) {

				ContractPayPlanInfo info = (ContractPayPlanInfo) contractPayPlanMap
						.get(contractId + year + month);

				month++;
				if (month >= 12) {
					year++;
					month = 1;
				}

				if (info != null) {
					isNeedAddRow = true;
					break;
				}

			}

			year = ((Integer) this.spiStartYear.getValue()).intValue();
			month = ((Integer) this.spiStartMonth.getValue()).intValue() - 1;
			if (isNeedAddRow) {
				// if (info1 != null || info2 != null || info3 != null) {
				IRow row = tblMain.addRow();
				row.getStyleAttributes().setLocked(true);
				row.setUserObject(contractId);
				String proName = (String) this.proLongNameMap.get(contract
						.getCurProject().getId().toString());
				row.getCell("project").setValue(proName);
				row.getCell("contractNum").setValue(contract.getNumber());
				row.getCell("contract").setValue(contract.getName());
				row.getCell("contractType").setValue(
						contract.getContractType() == null ? "" : contract
								.getContractType().getName());// 合同类型
				row.getCell("rept").setValue(
						contract.getRespDept() == null ? "" : contract
								.getRespDept().getName());// 责任人

				BigDecimal contractAmount = FDCHelper.ZERO;
				if (contract.getAmount() != null) {
					contractAmount = contract.getAmount();
				}
				row.getCell("contractAmount").setValue(contractAmount);

				if (contractAmount != null) {
					// 最后合计行需要的金额
					if (amountMap.containsKey("contractAmount")) {
						BigDecimal allcontractAmount = (BigDecimal) amountMap
								.get("contractAmount");
						amountMap.put("contractAmount", contractAmount
								.add(allcontractAmount));
					} else {
						amountMap.put("contractAmount", contractAmount);
					}
				}

				BigDecimal lastAmount = (BigDecimal) conLastAmountMap
						.get(contractId);
				row.getCell("lastAmount").setValue(lastAmount);

				if (lastAmount != null) {
					// 最后合计行需要的金额
					if (amountMap.containsKey("lastAmount")) {
						BigDecimal alllastAmount = (BigDecimal) amountMap
								.get("lastAmount");
						amountMap.put("lastAmount", lastAmount
								.add(alllastAmount));
					} else {
						amountMap.put("lastAmount", lastAmount);
					}
				}

				if (((ContractBillInfo) contract).getPartB() != null) {
					row.getCell("partB")
							.setValue(contract.getPartB().getName());
				}

				BigDecimal curHasPay = (BigDecimal) curPayMap.get(contractId);
				if (curHasPay != null
						&& curHasPay.compareTo(FDCHelper.ZERO) != 0) {
					row.getCell("hasPay").setValue(curHasPay);
				}

				if (curHasPay != null) {
					// 最后合计行需要的金额
					if (amountMap.containsKey("hasPay")) {
						BigDecimal allcurHasPay = (BigDecimal) amountMap
								.get("hasPay");
						amountMap.put("hasPay", curHasPay.add(allcurHasPay));
					} else {
						amountMap.put("hasPay", curHasPay);
					}
				}

				BigDecimal hasPay = FDCHelper.ZERO;
				if (allPayMap.get(contractId) != null) {
					hasPay = (BigDecimal) allPayMap.get(contractId);
				}
				if (lastAmount == null) {
					lastAmount = FDCHelper.ZERO;
				}
				BigDecimal balance = lastAmount.subtract(hasPay);
				if (balance.compareTo(FDCHelper.ZERO) != 0) {
					row.getCell("balance").setValue(balance);
				}

				if (balance != null) {
					// 最后合计行需要的金额
					if (amountMap.containsKey("balance")) {
						BigDecimal allbalance = (BigDecimal) amountMap
								.get("balance");
						amountMap.put("balance", balance.add(allbalance));
					} else {
						amountMap.put("balance", balance);
					}
				}

				BigDecimal payRequest = (BigDecimal) payRequestMap
						.get(contractId);
				BigDecimal onPaying = FDCHelper.ZERO;
				if (payRequest != null) {
					onPaying = onPaying.add(payRequest);
				}
				onPaying = onPaying.subtract(hasPay);
				if (onPaying.compareTo(FDCHelper.ZERO) != 0) {
					row.getCell("onPaying").setValue(onPaying);
				}
				if (onPaying != null) {
					// 最后合计行需要的金额
					if (amountMap.containsKey("onPaying")) {
						BigDecimal allPaying = (BigDecimal) amountMap
								.get("onPaying");
						amountMap.put("onPaying", onPaying.add(allPaying));
					} else {
						amountMap.put("onPaying", onPaying);
					}
				}
				row.getStyleAttributes().setBackground(new Color(0xF0EDD9));

				// if (info1 != null) {
				// row.getCell("planAmount1").setValue(info1.getPayAmount());
				// row.getCell("description").setValue(info1.getDescription());
				// }
				//				
				//				
				// if (info2 != null) {
				// row.getCell("planAmount2").setValue(info2.getPayAmount());
				// }
				//				
				//				
				// if (info3 != null) {
				// row.getCell("planAmount3").setValue(info3.getPayAmount());
				// }

				for (int j = 1; j < dymicCols + 1; j++) {

					ContractPayPlanInfo info = (ContractPayPlanInfo) contractPayPlanMap
							.get(contractId + year + month);

					month++;
					if (month >= 12) {
						year++;
						// 由于hongen_zhang在取月度付款计划金额时，保存用的是从0-11，所以应该赋值为0
						// month = 1;
						month = 0;
					}

					if (info != null) {
						row.getCell("planAmount" + j).setValue(
								info.getPayAmount());

						if (amountMap.containsKey("planAmount" + j)) {
							amount = (BigDecimal) amountMap.get("planAmount"
									+ j);
							amountMap.put("planAmount" + j, amount.add(info
									.getPayAmount()));
						} else {
							amountMap
									.put("planAmount" + j, info.getPayAmount());
						}

						// 备注
						if (info.getDescription() != null) {
							if (descMap.get(contractId) != null) {
								String desc = (String) descMap.get(contractId);
								if (desc.indexOf(info.getDescription()) == -1) {
									desc = desc.concat(";".concat(info
											.getDescription()));
									if (desc != null && desc.length() > 80) {
										desc = desc.substring(0, 80);
									}
									descMap.put(contractId, desc);
								}
							} else {
								descMap.put(contractId, info.getDescription());
							}
						}
						if (descMap.get(contractId) != null) {
							row.getCell("description").setValue(
									descMap.get(contractId));
						}
					}
				}

			}
		}
	}

	private void setRelAction() {
		if (payPlan.getAuditor() != null) {
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(true);
			this.actionSubmit.setEnabled(false);
			this.actionAddRow.setEnabled(false);
			this.actionDeleteRow.setEnabled(false);
			this.actionEditContractPlan.setEnabled(false);
			this.txtState.setText(ContractPayPlanHandler
					.getResource("HasAudit"));
		} else {
			this.actionAudit.setEnabled(true);
			this.actionUnAudit.setEnabled(false);
			this.actionSubmit.setEnabled(true);
			this.actionAddRow.setEnabled(true);
			this.actionDeleteRow.setEnabled(true);
			this.actionEditContractPlan.setEnabled(true);
			this.txtState
					.setText(ContractPayPlanHandler.getResource("NoAudit"));
		}
		if (this.currentOrg==null) {
			disableAction();
			this.tblMain.getStyleAttributes().setLocked(true);
		}
		if (projectLeafset != null && projectLeafset.size() > 1) {
			disableAction();
			// this.tblMain.getStyleAttributes().setLocked(true);
		}
		// if (!this.selectOrgId.equals(this.currentOrg.getId().toString())) {
		// disableAction();
		// this.tblMain.getStyleAttributes().setLocked(true);
		// }
	}

	private void disableAction() {
		this.actionAudit.setEnabled(false);
		this.actionUnAudit.setEnabled(false);
		this.actionSubmit.setEnabled(false);
		this.actionAddRow.setEnabled(false);
		this.actionDeleteRow.setEnabled(false);
		this.actionEditContractPlan.setEnabled(false);
	}

	private DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode node,
			String id) {
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			if (projectInfo.getId().toString().equals(id)) {
				return node;
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			FullOrgUnitInfo info = oui.getUnit();
			if (info.getId().toString().equals(id)) {
				return node;
			}
		}

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode findNode = findNode(
					(DefaultKingdeeTreeNode) node.getChildAt(i), id);
			if (findNode != null) {
				return findNode;
			}

		}
		return null;
	}

	private void addProjectContractPlan(Map contractMap, Map conLastAmountMap,
			Map contractPayPlanMap, Map payRequestMap, Map curPayMap,
			Map allPayMap) {
		// CurProjectCollection col = this.projectId;
		if (projectIdList == null || projectIdList.size() == 0) {
			return;
		}
		for (int i = 0; i < projectIdList.size(); i++) {
			String projectId = (String) projectIdList.get(i);
			addProjectContractPlan(projectId, contractMap, conLastAmountMap,
					contractPayPlanMap, payRequestMap, curPayMap, allPayMap);
		}
	}

	private void addProjectContractPlan(
			// DefaultKingdeeTreeNode node,
			String projectId, Map contractMap, Map conLastAmountMap,
			Map contractPayPlanMap, Map payRequestMap, Map curPayMap,
			Map allPayMap) {

		ContractBillCollection contractBills = (ContractBillCollection) contractMap
				.get(projectId);
		addContractPlan(contractBills, conLastAmountMap, contractPayPlanMap,
				payRequestMap, curPayMap, allPayMap);

	}

	private void addProjectThingPlan(ExpenseTypeCollection expenseTypes,
			Map dataMap, Map dConPayPlanMap) {
		if (projectIdList == null || projectIdList.size() == 0) {
			return;
			// 陕地电时发现问题，没有合同时工程项目费用项目没有显示；先提交，后续有需求再放开 by hpw 2009.09.30
			/*
			 * Set orgIDSet = new HashSet(); DefaultKingdeeTreeNode node =
			 * (DefaultKingdeeTreeNode) treeMain
			 * .getLastSelectedPathComponent(); Object object =
			 * node.getUserObject(); if(node!=null&&object!=null&&object
			 * instanceof OrgStructureInfo){ int count = node.getLeafCount();
			 * if(count>0 && !node.isLeaf()){ while(count>0 &&
			 * node.getUserObject()!=null){ if(node.isLeaf()){ OrgStructureInfo
			 * orgLeafInfo = (OrgStructureInfo)node.getUserObject();
			 * orgIDSet.add(orgLeafInfo.getUnit().getId().toString()); count--;
			 * } node = (DefaultKingdeeTreeNode) node.getNextNode(); } } else{
			 * OrgStructureInfo org = (OrgStructureInfo) node.getUserObject();
			 * String orgId = org.getUnit().getId().toString();
			 * orgIDSet.add(orgId); } } if(orgIDSet.size()==0){ return; }
			 * EntityViewInfo view = new EntityViewInfo(); FilterInfo filter =
			 * new FilterInfo(); view.setFilter(filter);
			 * view.getSelector().add("curProject");
			 * filter.getFilterItems().add(new
			 * FilterItemInfo("costCenterOU.id",orgIDSet, CompareType.INCLUDE));
			 * ProjectWithCostCenterOUCollection colls = null; try { colls =
			 * ProjectWithCostCenterOUFactory
			 * .getRemoteInstance().getProjectWithCostCenterOUCollection(view);
			 * } catch (BOSException e) { this.handUIException(e); }
			 * if(colls!=null){ for(int i=0;i<colls.size();i++){
			 * ProjectWithCostCenterOUInfo info = colls.get(i);
			 * if(info!=null&&info
			 * .getCurProject()!=null&&info.getCurProject().getId()!=null){
			 * projectIdList.add(info.getCurProject().getId().toString()); } }
			 * }else{ return; }
			 */
		}
		for (int i = 0; i < projectIdList.size(); i++) {
			String projectId = (String) projectIdList.get(i);
			addProjectThingPlan(projectId, expenseTypes, dataMap,
					dConPayPlanMap);
		}
	}

	private void addProjectThingPlan(
			// DefaultKingdeeTreeNode node,
			String projectId, ExpenseTypeCollection expenseTypes, Map dataMap,
			Map dConPayPlanMap) {
		// if (node.isLeaf() && node.getUserObject() instanceof CurProjectInfo)
		// {
		// CurProjectInfo project = (CurProjectInfo) node.getUserObject();
		IRow row = this.tblMain.addRow();
		String proName = (String) this.proLongNameMap.get(projectId);
		row.getCell("project").setValue(proName);
		CellTreeNode treeNode = new CellTreeNode();
		treeNode.addClickListener(payNodeClick);
		treeNode.setValue("");
		treeNode.setTreeLevel(0);
		treeNode.setCollapse(true);
		treeNode.setHasChildren(true);
		row.getCell("contractNum").setValue(treeNode);
		row.getCell("contractNum").getStyleAttributes().setLocked(false);

		// 在每个工程项目下增加一行"待签合同小计"
		CellTreeNode treeNode1 = new CellTreeNode();
		treeNode1.setValue("待签合同小计");
		treeNode1.setTreeLevel(1);
		treeNode1.setHasChildren(false);
		treeNode1.addClickListener(payNodeClick);
		IRow trow = tblMain.addRow();
		trow.getStyleAttributes().setHided(true);
		trow.setUserObject(projectId);
		trow.getCell("contractNum").setValue(treeNode1);
		trow.getStyleAttributes().setLocked(true);

		// 53界面只取年月，60暂取开始年与月
		int year = ((Integer) this.spiStartYear.getValue()).intValue();
		int month = ((Integer) this.spiStartMonth.getValue()).intValue() - 1;
		if (dConPayPlanMap == null) {
			dConPayPlanMap = new HashMap();
		}

		for (int j = 1; j < dymicCols + 1; j++) {

			BigDecimal info = (BigDecimal) dConPayPlanMap.get(projectId + year
					+ month);

			month++;
			if (month >= 12) {
				year++;
				month = 0;
			}

			if (info != null) {
				trow.getCell("planAmount" + j).setValue(info);
				if (amountMap.containsKey("planAmount" + j)) {
					amount = (BigDecimal) amountMap.get("planAmount" + j);
					amountMap.put("planAmount" + j, amount.add(info));
				} else {
					amountMap.put("planAmount" + j, info);
				}
			}
		}

		for (int i = 0; i < expenseTypes.size(); i++) {
			row = this.tblMain.addRow();
			row.getStyleAttributes().setHided(true);
			ExpenseTypeInfo expense = expenseTypes.get(i);
			row.getCell("project").setUserObject(projectId);
			row.getCell("contractNum")
					.setUserObject(expense.getId().toString());
			// row.getCell("planAmount1").setEditor(null);
			// row.getCell("planAmount1").setValue(new
			// BigDecimal("99999999999999.99"));
			String key = projectId + expense.getId().toString();
			PlanEntryCollection entrys = (PlanEntryCollection) dataMap.get(key);
			treeNode = new CellTreeNode();
			treeNode.addClickListener(payNodeClick);
			treeNode.setValue(expense.getName());
			treeNode.setTreeLevel(1);
			treeNode.setCollapse(false);
			treeNode.setHasChildren(true);
			row.getCell("contractNum").setValue(treeNode);
			row.getCell("contractNum").getStyleAttributes().setLocked(false);

			boolean hasChridren = false;
			if (entrys != null) {
				for (int j = 0; j < entrys.size(); j++) {
					row = this.tblMain.addRow();
					hasChridren = true;
					row.getStyleAttributes().setHided(true);
					row.getCell("project").setUserObject(projectId);
					row.getCell("contractNum").setUserObject(
							expense.getId().toString());
					CellTreeNode treeNodeEn = new CellTreeNode();
					treeNode.addClickListener(payNodeClick);
					treeNodeEn.setTreeLevel(2);
					row.getCell("contractNum").setValue(treeNodeEn);
					if (payPlan.getAuditor() == null) {
						if (projectLeafset != null
								&& projectLeafset.size() <= 1) {
							row.getCell("contract").getStyleAttributes()
									.setLocked(false);
							for (int k = 1; k < dymicCols + 1; k++) {
								row.getCell("planAmount" + k)
										.getStyleAttributes().setLocked(false);
							}
							row.getCell("planAmount1").getStyleAttributes()
									.setLocked(false);
							row.getCell("planAmount2").getStyleAttributes()
									.setLocked(false);
							row.getCell("planAmount3").getStyleAttributes()
									.setLocked(false);
							row.getCell("description").getStyleAttributes()
									.setLocked(false);
						}
					}
					PlanEntryInfo entry = entrys.get(j);
					row.getCell("contract").setValue(entry.getNumber());
					if (entry != null) {
						// for (int k = 1; k <dymicCols+1;k++) {
						//								
						// }
						row.getCell("planAmount1").setValue(
								entry.getPlanAmount1());
						row.getCell("planAmount2").setValue(
								entry.getPlanAmount2());
						row.getCell("planAmount3").setValue(
								entry.getPlanAmount3());
						row.getCell("description").setValue(
								entry.getDescription());
						row.setUserObject(entry);
					} else {
						PlanEntryInfo planEntryInfo = new PlanEntryInfo();
						planEntryInfo.setProjectId(projectId);
						planEntryInfo.setExpenseTypeId(expense.getId()
								.toString());
						row.setUserObject(planEntryInfo);
					}
				}
			}

			treeNode.setHasChildren(hasChridren);
		}
		// } else {
		// for (int i = 0; i < node.getChildCount(); i++) {
		// TreeNode child = node.getChildAt(i);
		// this.addProjectThingPlan((DefaultKingdeeTreeNode) child,
		// expenseTypes, dataMap);
		// }
		// }
	}

	private void setHead() {

		// Integer month = (Integer) this.spiStartMonth.getValue();
		// if (month.intValue() > 12) {
		// month = new Integer(1);
		// }
		// String text = "月计划金额";
		//
		// this.tblMain.getHeadRow(0).getCell("planAmount1").setValue(
		// month.intValue() + text);

		// month = new Integer(month.intValue() + 1);
		// if (month.intValue() > 12) {
		// month = new Integer(1);
		// }
		//
		// this.tblMain.getHeadRow(0).getCell("planAmount2").setValue(
		// month.intValue() + text);

		// month = new Integer(month.intValue() + 1);
		// if (month.intValue() > 12) {
		// month = new Integer(1);
		// }
		// this.tblMain.getHeadRow(0).getCell("planAmount3").setValue(
		// month.intValue() + text);

	}

	/**
	 * 获取query中的主键列名称，返回供编辑/删除时获取主键用，默认值为"id"，继承类可以重载
	 * 
	 */
	protected String getKeyFieldName() {
		return "contract";
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.btnPayRequest.setIcon(EASResource.getIcon("imgTbtn_showlist"));
		this.btnAddRow.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.btnDeleteRow.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		this.menuItemAddRow.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.menuItemDeleteRow.setIcon(EASResource
				.getIcon("imgTbtn_deleteline"));
		this.btnEditContractPlan.setIcon(EASResource.getIcon("imgTbtn_edit"));
		this.menuItemEditContractPlan.setIcon(EASResource
				.getIcon("imgTbtn_edit"));
		setButtonDefaultStyl(this.btnSubmit);

		menuItemSave.setIcon(EASResource.getIcon("imgTbtn_save"));
		menuBiz.setVisible(true);
		menuBiz.setEnabled(true);
		this.menuItemAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.menuItemUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));

		this.btnQuery.setVisible(true);
		this.btnQuery.setEnabled(true);
	}

	private boolean isEdited() {
		this.tblMain.getSelectManager().select(0, 0);
		if (this.tblMain.getUserObject() != null) {
			return true;
		}
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row.getUserObject() instanceof PlanEntryInfo) {
				PlanEntryInfo info = (PlanEntryInfo) row.getUserObject();
				if (!FDCHelper.isEqual(info.getNumber(), row
						.getCell("contract").getValue())) {
					return true;
				}
				String[] compareyKeys = new String[] { "planAmount1",
						"planAmount2", "planAmount3", "description" };
				for (int j = 0; j < compareyKeys.length; j++) {
					if (!FDCHelper.isEqual(info.get(compareyKeys[j]), row
							.getCell(compareyKeys[j]).getValue())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean destroyWindow() {
		if (this.isEdited()) {
			if (MsgBox.showConfirm2New(this, ContractPayPlanHandler
					.getResource("NoSave")) == MsgBox.YES) {
				try {
					this.actionSubmit_actionPerformed(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return super.destroyWindow();
	}

	private boolean verify() {
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row.getUserObject() instanceof PlanEntryInfo) {
				if (row.getCell("contract").getValue() == null) {
					this.setMessageText(ContractPayPlanHandler
							.getResource("IntendingNameNull"));
					this.showMessage();
					tblMain.getSelectManager().select(0, 0);
					tblMain.getSelectManager().select(row.getRowIndex(), 2);
					return false;
				}
			}
		}
		return true;
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		this.verifySaved(null);
		this.fillTable();

		payPlanEdit = false;
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (payPlan.getId() == null) {
			payPlan.setId(BOSUuid.read(PayPlanFactory.getRemoteInstance()
					.submit(payPlan).toString()));
		}
		this.verifySaved(null);
		
		if(projectIdList==null || projectIdList.size()==0){
			return;
		}
		Map paramMap = new HashMap();
		//最开始做融创的私包，根据罗罗的处理建议是将单据头所选的起始日期到截止日期的付款计划都审批掉的，结果呢，周鹏说只审批起始日期的，现在这个方法已不需要了 by cassiel
//		Map map = handleForAuditConPayPlan();
//		paramMap.put("notNullSet",map.get("notNullSet"));
//		paramMap.put("nullSet",map.get("nullSet"));
		
		Integer startYear = (Integer) this.spiStartYear.getValue();
		Integer startMonth = (Integer) this.spiStartMonth.getValue();
		String year_month = String.valueOf(startYear)+"_"+String.valueOf(startMonth);
		paramMap.put("year_month", year_month);
		paramMap.put("prjId", (projectIdList.iterator()).next().toString());
		paramMap.put("id", payPlan.getId());
		paramMap.put("prjId", (projectIdList.iterator()).next().toString());
		paramMap.put("id", payPlan.getId());
		
		PayPlanFactory.getRemoteInstance().audit(paramMap);
//		PayPlanFactory.getRemoteInstance().audit(payPlan.getId());
		MsgBox.showInfo(ContractPayPlanHandler.getResource("AuditSucces"));
		this.fillTable();
	}
	/**
	 * 最开始做融创的私包，根据罗罗的处理建议是将单据头所选的起始日期到截止日期的付款计划都审批掉的，结果呢，周鹏说只审批起始日期的，现在这个方法已不需要了 by cassiel 
	 * @deprecated
	 */
	private Map handleForAuditConPayPlan() {
		Set notNullSet = new HashSet();
		Set nullSet = new HashSet();
		Integer startYear = (Integer) this.spiStartYear.getValue();
		Integer endYear = (Integer) this.spiEndYear.getValue();
		Integer startMonth = (Integer) this.spiStartMonth.getValue();
		Integer endMonth = (Integer) this.spiEndMonth.getValue();

		int months = calMonths(startYear.intValue(), startMonth.intValue(),
				endYear.intValue(), endMonth.intValue());
		for(int i =1;i<months+1;i++){
			String key = "planAmount" + i;
			for (int j = 0; j < tblMain.getRowCount(); j ++) {
				Object o = this.tblMain.getRow(j).getCell(key);
				String colName = this.tblMain.getHeadRow(0).getCell(key).getValue().toString();
				int yearIndex = colName.indexOf('年');
				String year = colName.substring(0, yearIndex);
				int monthIndex = colName.indexOf("月");
				String month = colName.substring(yearIndex+1,monthIndex);
				if(o==null){
					nullSet.add(year+"_"+month);
				}else{
					notNullSet.add(year+"_"+month);
				}
			}
		}
		HashMap map = new HashMap();
		map.put("nullSet",nullSet);
		map.put("notNullSet", notNullSet);
		return map;
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (payPlan.getId() == null) {
			payPlan.setId(BOSUuid.read(PayPlanFactory.getRemoteInstance()
					.submit(payPlan).toString()));
		}
		if(projectIdList==null || projectIdList.size()==0){
			return;
		}
		Map paramMap = new HashMap();
//最开始做融创的私包，根据罗罗的处理建议是将单据头所选的起始日期到截止日期的付款计划都审批掉的，结果呢，周鹏说只审批其实日期的，现在这个方法已不需要了 by cassiel		
//		Map map = handleForAuditConPayPlan();
//		paramMap.put("notNullSet",map.get("notNullSet"));
//		paramMap.put("nullSet",map.get("nullSet"));
		
		Integer startYear = (Integer) this.spiStartYear.getValue();
		Integer startMonth = (Integer) this.spiStartMonth.getValue();
		String year_month = String.valueOf(startYear)+"_"+String.valueOf(startMonth);
		paramMap.put("year_month", year_month);
		paramMap.put("prjId", (projectIdList.iterator()).next().toString());
		paramMap.put("id", payPlan.getId());
		
		PayPlanFactory.getRemoteInstance().unAudit(paramMap);
		MsgBox.showInfo(ContractPayPlanHandler.getResource("UnauditSucces"));
		this.fillTable();
	}

	/**
	 * 修改合同付款计划
	 */
	public void actionEditContractPlan_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();

		payPlanEdit = true;
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();
		IRow row = this.tblMain.getRow(activeRowIndex);
		if (row != null && row.getUserObject() instanceof String) {
			String contractId = (String) row.getUserObject();
			this.setOprtState(OprtState.EDIT);
			try {
				pubFireVOChangeListener(contractId + "payPlan");
				ContractPayPlanEditUI.showEditUI(this, contractId, OprtState.EDIT);
				this.fillTable();
			} catch (Throwable e1) {
				this.handUIException(e1);
				SysUtil.abort();
			}
			// finally
			// {
			this.setOprtState("RELEASEALL");
			try {
				pubFireVOChangeListener(contractId + "payPlan");
			} catch (Throwable e1) {
				this.handUIException(e1);
			}
			// }
		}
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return ContractTypeFactory.getRemoteInstance();
	}

	protected void initTree() throws Exception {
		CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
		if (cuInfo == null) {
			throw new OUException(OUException.CU_CAN_NOT_NULL);
		}

		String rootid = SysContext.getSysContext().getCurrentOrgUnit().getId()
				.toString();

		TreeModel orgTreeModel = null;
		if (viewPlanByCostcenter) {
			orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COSTCENTER, "",
					rootid, null, FDCHelper.getActionPK(this.actionOnLoad));
		} else {
			// 参数否，检查工程树根结点是否对应的控制单元组织，并且付款计划根据合同责任部门过滤
			rootid = cuInfo.getId().toString();
			orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.ADMIN, "",
					rootid, null, FDCHelper.getActionPK(this.actionOnLoad));
		}
		this.treeMain.setModel(orgTreeModel);
		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	private void verifySaved(TreePath selectionPath) {
		if (this.isEdited()) {
			if (MsgBox.showConfirm2(this, ContractPayPlanHandler
					.getResource("NoSave")) == MsgBox.YES) {
				if (!this.verify()) {
					if (selectionPath != null) {
						this.treeMain.setSelectionPath(selectionPath);
					}
					this.abort();
				}
				this.btnSubmit.doClick();
			}
		}
	}

	// 最后一样往下移则，展开最后一个+号，保证增加行正确
	public void actionEntryCellDown_actionPerformed(ActionEvent e)
			throws Exception {

		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}

		KDTEditManager em = tblMain.getEditManager();
		int colindex = em.getEditColIndex();
		int count = tblMain.getRowCount();
		if (em.isEditing()) {
			em.stopEditing();
			if (index == count - 1 && btnAddRow.isEnabled()) {
				actionAddRow_actionPerformed(e);
			}
			if (index < count - 1) {
				em.editCellAt(index + 1, colindex);
			}
		} else {
			colindex = tblMain.getSelectManager().getActiveColumnIndex();
			if (index == count - 1 && btnAddRow.isEnabled()) {
				actionAddRow_actionPerformed(e);
			} else if (index < count - 1) {
				tblMain.getSelectManager().select(index + 1, colindex);
				// 展开最后一个+号
				Object obj = tblMain.getRow(index).getCell("contractNum")
						.getValue();
				if (obj != null && obj instanceof CellTreeNode) {
					CellTreeNode treeNode = (CellTreeNode) tblMain
							.getRow(index).getCell("contractNum").getValue();
					if (treeNode != null && treeNode.isCollapse()) {
						treeNode.doTreeClick(tblMain, tblMain.getRow(index)
								.getCell("contractNum"));
						tblMain.reLayoutAndPaint();
					}
				}
			}
		}
	}

	class PayNodeClickListener implements NodeClickListener {
		public void doClick(CellTreeNode source, ICell cell, int type) {
			// source.doTreeClick(tblMain,cell);
			tblMain.reLayoutAndPaint();
		}
	}

	private PayNodeClickListener payNodeClick = new PayNodeClickListener();

	public void actionAddRow_actionPerformed(ActionEvent e) throws Exception {
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		IRow row = this.tblMain.getRow(index);
		String projectId = (String) row.getCell("project").getUserObject();
		String typeId = (String) row.getCell("contractNum").getUserObject();
		if (projectId != null && typeId != null) {
			IRow addRow = this.tblMain.addRow(index + 1);
			PlanEntryInfo entry = new PlanEntryInfo();
			entry.setProjectId(projectId);
			entry.setExpenseTypeId(typeId);
			addRow.setUserObject(entry);
			CellTreeNode treeNode = new CellTreeNode();
			treeNode.addClickListener(payNodeClick);
			treeNode.setTreeLevel(1);

			// treeNode.addClickListener(new NodeClickListener(){
			// public void doClick(CellTreeNode source, ICell cell, int type) {
			// source.doTreeClick(tblMain,cell);
			// tblMain.reLayoutAndPaint();
			// }
			// });

			treeNode.setCollapse(true);
			addRow.getCell("project").setUserObject(projectId);
			addRow.getCell("contractNum").setUserObject(typeId);
			addRow.getCell("contractNum").setValue(treeNode);
			addRow.getCell("contract").getStyleAttributes().setLocked(false);
			addRow.getCell("planAmount1").getStyleAttributes().setLocked(false);
			addRow.getCell("planAmount2").getStyleAttributes().setLocked(false);
			addRow.getCell("planAmount3").getStyleAttributes().setLocked(false);
			addRow.getCell("description").getStyleAttributes().setLocked(false);
			this.tblMain.getEditManager().editCellAt(addRow.getRowIndex(),
					this.tblMain.getColumnIndex("contract"));
			this.tblMain.setUserObject("AddRow");
		}

	}

	public void actionDeleteRow_actionPerformed(ActionEvent e) throws Exception {
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		IRow row = this.tblMain.getRow(index);
		if (row.getUserObject() instanceof PlanEntryInfo) {
			this.tblMain.removeRow(index);
			this.tblMain.setUserObject("DeleteRow");
			this.setSumRow("planAmount1");
			this.setSumRow("planAmount2");
			this.setSumRow("planAmount3");
		}
	}

	/**
	 * 设置是否显示合计行 2005-03-09 haiti_yang
	 */
	protected boolean isFootVisible() {
		return true;
	}

	/**
	 * 添加合计行 2005-03-09 haiti_yang
	 */
	protected IRow appendFootRow() {
		if (!this.isFootVisible())
			return null;

		try {
			// 合计行,并进行计算

			List fieldSumList = new ArrayList();
			// contractAmount
			fieldSumList.add("contractAmount");
			fieldSumList.add("lastAmount");
			fieldSumList.add("hasPay");
			fieldSumList.add("balance");
			fieldSumList.add("onPaying");
			// 新增加预算列合计
			for (int i = 1; i < dymicCols + 1; i++) {
				fieldSumList.add("planAmount" + i);
			}
			/*
			 * fieldSumList.add("planAmount1"); fieldSumList.add("planAmount2");
			 * fieldSumList.add("planAmount3");
			 */

			if (fieldSumList.size() > 0) {
				// 进行列计算
				// 生成计算列
				IRow footRow = null;
				KDTFootManager footRowManager = tblMain.getFootManager();
				if (footRowManager == null) {
					String total = EASResource
							.getString(FrameWorkClientUtils.strResource
									+ "Msg_Total");
					footRowManager = new KDTFootManager(this.tblMain);
					footRowManager.addFootView();
					this.tblMain.setFootManager(footRowManager);
					footRow = footRowManager.addFootRow(0);
					footRow.getStyleAttributes().setHorizontalAlign(
							HorizontalAlignment.getAlignment("right"));
					this.tblMain.getIndexColumn().setWidthAdjustMode(
							KDTIndexColumn.WIDTH_MANUAL);
					this.tblMain.getIndexColumn().setWidth(30);
					footRowManager.addIndexText(0, total);
				} else {
					footRow = footRowManager.getFootRow(0);
				}
				// String colFormat="%{0##.##}f";
				int columnCount = this.tblMain.getColumnCount();
				for (int c = 0; c < columnCount; c++) {
					String fieldName = this.tblMain.getColumn(c).getKey();
					for (int i = 0; i < fieldSumList.size(); i++) {
						String colName = (String) fieldSumList.get(i);
						// String name=info.getName();
						if (colName.equalsIgnoreCase(fieldName)) {
							ICell cell = footRow.getCell(c);
							cell.getStyleAttributes().setNumberFormat(
									FDCHelper.strDataFormat);
							cell.getStyleAttributes().setHorizontalAlign(
									HorizontalAlignment.getAlignment("right"));
							cell.getStyleAttributes().setFontColor(Color.BLACK);

							cell.setValue(amountMap.get(colName));

							/*
							 * //预算合计 if(colName.equalsIgnoreCase("planAmount1")
							 * || colName.equalsIgnoreCase("planAmount2") ||
							 * colName.equalsIgnoreCase("planAmount3")) {
							 * if(monthAmount.size()!=0) {
							 * cell.setValue(monthAmount.get(colName)); } }
							 */
						}
					}
				}
				footRow.getStyleAttributes().setBackground(
						new Color(0xf6, 0xf6, 0xbf));
				return footRow;
			}
		} catch (Exception E) {
			E.printStackTrace();
		}
		return null;
	}

	/**
	 * 构造合同类型树
	 */
	private TreeSelectionListener treeSelectionListener;
	private ITreeBuilder treeBuilder;

	protected boolean containConWithoutTxt() {
		return false;
	}

	protected void buildContractTypeTree() throws Exception {
		KDTree treeContractType = this.contractTypeTree;
		TreeSelectionListener[] listeners = treeContractType
				.getTreeSelectionListeners();
		if (listeners.length > 0) {
			treeSelectionListener = listeners[0];
			treeContractType.removeTreeSelectionListener(treeSelectionListener);
		}

		treeBuilder = TreeBuilderFactory.createTreeBuilder(getLNTreeNodeCtrl(),
				getTreeInitialLevel(), getTreeExpandLevel(), this
						.getDefaultFilterForTree());

		if (getRootName() != null) {
			KDTreeNode rootNode = new KDTreeNode(getRootObject());
			((DefaultTreeModel) treeContractType.getModel()).setRoot(rootNode);

		} else {
			((DefaultTreeModel) treeContractType.getModel()).setRoot(null);
		}

		treeBuilder.buildTree(treeContractType);
		if (containConWithoutTxt()) {
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeContractType
					.getModel().getRoot();
			KDTreeNode node = new KDTreeNode("allContract");
			node.setUserObject("allContract");
			node.setText(getRootName());
			root.setText("合同");
			node.add(root);
			((DefaultTreeModel) treeContractType.getModel()).setRoot(node);

		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeContractType
				.getModel().getRoot();
		root.setText("合同");
		treeContractType.addTreeSelectionListener(treeSelectionListener);
		treeContractType.setShowPopMenuDefaultItem(false);

	}

	/**
	 * 左边树选择改变，重新构造条件执行查询 by cassiel_peng 2009-12-17
	 */
	private void treeSelectedChange(TreePath selectionPath) throws Exception {
		// TODO Auto-generated method stub
		DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		DefaultKingdeeTreeNode contractTypeNode = (DefaultKingdeeTreeNode) contractTypeTree
				.getLastSelectedPathComponent();

		Object project = null;
		Object contractType = null;
		if (projectNode == null || projectNode.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(projectNode)) {
			disableAction();
			return;
		} else if (projectNode != null) {
			project = projectNode.getUserObject();
		}
		if (contractTypeNode != null) {
			contractType = contractTypeNode.getUserObject();
		}

		if (project instanceof OrgStructureInfo) {
			OrgStructureInfo org = (OrgStructureInfo) project;
			this.selectOrgId = org.getUnit().getId().toString();
		}
		projectLeafset = new HashSet();
		int count = projectNode.getLeafCount();
		if (count > 0 && !projectNode.isLeaf()) {// 非明细节点
			//R100720-134注释下面代码，节点强制转换(框架的问题)错误 by hpw 2010.07.26
			if(projectNode.getUserObject()!=null){
				BOSUuid parentId = null;
				if (projectNode.getUserObject() instanceof OrgStructureInfo) {
					parentId = ((OrgStructureInfo)projectNode.getUserObject()).getUnit().getId();	
				}else{
					parentId = ((FullOrgUnitInfo)projectNode.getUserObject()).getId();
				}
				Set idSet = FDCClientUtils.genOrgUnitIdSet(parentId);
				Set authorzedOrgs = FDCUtils.getAuthorizedOrgs(null);
				for(Iterator iter=idSet.iterator();iter.hasNext();){
					String childId = (String)iter.next();
					if(authorzedOrgs.contains(childId)){
						projectLeafset.add(childId);
					}
				}
			}
			//
			/*while (count > 0 && projectNode.getUserObject() != null) {
				if (projectNode.isLeaf()) {
					OrgStructureInfo orgLeafInfo = (OrgStructureInfo) projectNode
							.getUserObject();
					projectLeafset
							.add(orgLeafInfo.getUnit().getId().toString());
					count--;
				}
				projectNode = (DefaultKingdeeTreeNode) projectNode
						.getNextNode();
			}*/
		} else {// 明细节点
			OrgStructureInfo org = (OrgStructureInfo) projectNode
					.getUserObject();
			String orgId = org.getUnit().getId().toString();
			verifySaved(selectionPath);
			projectLeafset.add(orgId);
		}

		conTypeLeafSet = new HashSet();
		if (contractTypeNode != null && contractTypeNode.getUserObject()!=null&&!contractTypeNode.getUserObject().toString().equals("root")) {

			int _count = contractTypeNode.getLeafCount();
			if (_count > 0 && !contractTypeNode.isLeaf()) {// 非明细节点
				BOSUuid parentId = ((ContractTypeInfo)contractTypeNode.getUserObject()).getId();
				Set idSet = FDCClientUtils.genContractTypeIdSet(parentId);
				conTypeLeafSet.addAll(idSet);
				
				/*while (_count > 0 && contractTypeNode.getUserObject() != null) {
					if (contractTypeNode.isLeaf()) {
						ContractTypeInfo contractTypeBill = (ContractTypeInfo) contractTypeNode
								.getUserObject();
						conTypeLeafSet.add(contractTypeBill.getId().toString());
						_count--;
					}
					contractTypeNode = (DefaultKingdeeTreeNode) contractTypeNode
							.getNextNode();
				}*/
			} else {// 明细节点
				ContractTypeInfo contractTypeBill = (ContractTypeInfo) contractTypeNode
						.getUserObject();
				String conTypeId = contractTypeBill.getId().toString();
				conTypeLeafSet.add(conTypeId);
			}
		}
		fillTable();

	}

	protected void contractTypeTree_valueChanged(TreeSelectionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.contractTypeTree_valueChanged(e);
		treeSelectedChange(e.getOldLeadSelectionPath());
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		// TODO Auto-generated method stub
		treeSelectedChange(e.getOldLeadSelectionPath());
	}
	
	private String adminId="";
	private String supplierId="";
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {

		if (isFirstDefaultQuery()) {//如果是第一次加载该序时簿不调用过滤对话框
			return;
		}
		
		Map uictx = new UIContext(this);
		IUIWindow win = UIFactory.createUIFactory(UIFactoryName.MODEL).create(MonPayPlanFilterUI.class.getName(), uictx);
		
		((MonPayPlanFilterUI) win.getUIObject()).setListUICtx(this.getUIContext());
		
		win.show();

		Object obj = this.getUIContext().get("QUERYPARAM");
		FDCCustomerParams param = null;
		if (obj instanceof FDCCustomerParams) {
			param = (FDCCustomerParams) obj;
			adminId=(String)param.get("adminId");
			supplierId=(String)param.get("supplierId");
		}
		fillTable();
	}
}
