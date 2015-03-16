package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.PayPlanCycleFactory;
import com.kingdee.eas.fdc.basedata.PayPlanCycleInfo;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.util.KDDetailedArea;
import com.kingdee.eas.fdc.basedata.util.KDDetailedAreaUtil;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractEntryCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractEntryCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettleEntryCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettleEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo;
import com.kingdee.eas.fdc.finance.IFDCDepConPayPlanBill;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class FDCDepConPayPlanEditUI extends AbstractFDCDepConPayPlanEditUI {

	private static final long serialVersionUID = 1859998184673045988L;

	private static final Logger logger = CoreUIObject
			.getLogger(FDCDepConPayPlanEditUI.class);

	// 当前分录表格
	private KDTable table = this.tblContract;

	// 年月的最后有效值，用于当改变月份到存在计划时，返回为此值
	Integer year_old = null;
	Integer month_old = null;

	// 分录表格滚动月份初始默认开始列号
	private static final int START_CON = 12;
	private static final int START_UNC = 7;
	private static final int START_NOC = 6;

	// 初始版本
	private String VERSION = "1.0";
	// 计划状态(修订、修改)
	String flag_state = "";
	// 审批时间
	Date auditTime = null;
	// 工程量确认流程与付款流程分离参数
	boolean separteFromPayment = true;

	public FDCDepConPayPlanEditUI() throws Exception {
		super();
	}

	public void loadFields() {
		detachListeners();
		super.loadFields();
		// initMonthWhenAddNew();
		clearTable();
		initTableColumn();
		loadEntryData();
		attachListeners();
		addSumRow();
		// TODO 删除
		initMonthWhenLoadFields();
	}

	/**
	 * KDSpinner控件去除Change监听后，本身setValue也不会触发显示值改变，<br>
	 * 造成显示值永远为0，此处在重新添加Change监听后<br>
	 * 先设一个不同的值再设回来以重新触发Change事件<br>
	 */
	private void initMonthWhenLoadFields() {
		spYear.setValue(new Integer(0), false);
		spYear.setValue(new Integer(editData.getYear()), false);
		year_old = new Integer(editData.getYear());
		spMonth.setValue(new Integer(0), false);
		spMonth.setValue(new Integer(editData.getMonth()), false);
		month_old = new Integer(editData.getMonth());
	}

	/**
	 * 清空3个表格所有数据
	 */
	protected void clearTable() {
		tblContract.removeRows();
		tblUnsettledCon.removeRows();
		tblNoContract.removeRows();
		for (int i = tblContract.getColumnCount() - 1; i >= START_CON; i--) {
			tblContract.removeColumn(i);
		}
		for (int i = tblUnsettledCon.getColumnCount() - 1; i >= START_UNC; i--) {
			tblUnsettledCon.removeColumn(i);
		}
		for (int i = tblNoContract.getColumnCount() - 1; i >= START_NOC; i--) {
			tblNoContract.removeColumn(i);
		}
	}

	/**
	 * 根据计划周期长度，初始化3个表格的动态列
	 */
	protected void initTableColumn() {
		tblContract.checkParsed();
		tblUnsettledCon.checkParsed();
		tblNoContract.checkParsed();
		tblContract.getColumn("lastMonthPayable").setEditor(
				getCellEditor("amount"));
		tblUnsettledCon.getColumn("unsettledConNumber").setEditor(
				getCellEditor("text"));

		tblUnsettledCon.getColumn("unsettledConName").setEditor(
				getCellEditor("text"));
		tblNoContract.getColumn("payMatters").setEditor(getCellEditor("text"));
		tblNoContract.getColumn("payMattersName").setEditor(
				getCellEditor("text"));

		if (editData.getPayPlanCycle() != null) {
			int cycle = editData.getPayPlanCycle().getCycle().getValue();
			int year = spYear.getIntegerVlaue().intValue();
			int month = spMonth.getIntegerVlaue().intValue();
			// 表头
			IRow conHead0 = tblContract.getHeadRow(0);
			IRow conHead1 = tblContract.getHeadRow(1);
			IRow unCHead0 = tblUnsettledCon.getHeadRow(0);
			IRow unCHead1 = tblUnsettledCon.getHeadRow(1);
			IRow noCHead0 = tblNoContract.getHeadRow(0);
			IRow noCHead1 = tblNoContract.getHeadRow(1);
			// 融合管理
			KDTMergeManager conMarge = tblContract.getHeadMergeManager();
			KDTMergeManager unCMarge = tblUnsettledCon.getHeadMergeManager();
			KDTMergeManager noCMarge = tblNoContract.getHeadMergeManager();
			// 列标
			int index = 0;
			for (int i = 0; i < cycle; i++) {
				if (month > 12) {
					year += 1;
					month = 1;
				}
				String monthStr;
				if (month < 10) {
					monthStr = year + "年0" + month + "月";
				} else {
					monthStr = year + "年" + month + "月";
				}
				String KeyHead = "MONTH" + year + "" + month;

				// 合同付款计划加列
				IColumn col = tblContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "id");
				conHead0.getCell(index).setValue(monthStr);
				conHead1.getCell(index).setValue("id");
				col.getStyleAttributes().setHided(true);
				col.setRequired(true);

				col = tblContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "plan");
				conHead1.getCell(index).setValue("计划支付金额");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				col = tblContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "define");
				conHead1.getCell(index).setValue("款项类型");
				col.setEditor(getCellEditor("define"));
				col.setRequired(true);

				col = tblContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "explain");
				conHead1.getCell(index).setValue("用款说明");
				col.setEditor(getCellEditor("explain"));
				col.setWidth(200);
				col.setRequired(true);

				col = tblContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "official");
				conHead1.getCell(index).setValue("最终批复金额");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setLocked(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				conMarge.mergeBlock(0, START_CON + (i * 5), 0, START_CON
						+ (i * 5) + 4);

				// 待签订合同付款计划加列
				col = tblUnsettledCon.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "id");
				unCHead0.getCell(index).setValue(monthStr);
				unCHead1.getCell(index).setValue("id");
				col.getStyleAttributes().setHided(true);
				col.setRequired(true);

				col = tblUnsettledCon.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "plan");
				unCHead1.getCell(index).setValue("计划支付金额");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				col = tblUnsettledCon.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "define");
				unCHead1.getCell(index).setValue("款项类型");
				col.setEditor(getCellEditor("define"));
				col.setRequired(true);

				col = tblUnsettledCon.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "explain");
				unCHead1.getCell(index).setValue("用款说明");
				col.setEditor(getCellEditor("explain"));
				col.setWidth(200);
				col.setRequired(true);

				col = tblUnsettledCon.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "official");
				unCHead1.getCell(index).setValue("最终批复金额");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setLocked(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				unCMarge.mergeBlock(0, START_UNC + (i * 5), 0, START_UNC
						+ (i * 5) + 4);

				// 无合同付款计划加列
				col = tblNoContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "id");
				noCHead0.getCell(index).setValue(monthStr);
				noCHead1.getCell(index).setValue("id");
				col.getStyleAttributes().setHided(true);
				col.setRequired(true);

				col = tblNoContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "plan");
				noCHead1.getCell(index).setValue("计划支付金额");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				col = tblNoContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "define");
				noCHead1.getCell(index).setValue("款项类型");
				col.setEditor(getCellEditor("define"));
				col.setRequired(true);

				col = tblNoContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "explain");
				noCHead1.getCell(index).setValue("用款说明");
				col.setEditor(getCellEditor("explain"));
				col.setWidth(200);
				col.setRequired(true);

				col = tblNoContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "official");
				noCHead1.getCell(index).setValue("最终批复金额");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setLocked(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				noCMarge.mergeBlock(0, START_NOC + (i * 5), 0, START_NOC
						+ (i * 5) + 4);

				month++;
			}
		}
	}

	protected void loadEntryData() {
		// 加载合同计划
		if (editData.getHasContract() != null) {
			for (int i = 0; i < editData.getHasContract().size(); i++) {
				IRow row = tblContract.addRow();
				FDCDepConPayPlanContractInfo hasCon = editData.getHasContract()
						.get(i);
				row.getCell("id").setValue(hasCon.getId());
				row.getCell("isBack").setValue(new Boolean(hasCon.isIsBack()));
				if (hasCon.isIsBack()) {
					row.getStyleAttributes().setBackground(Color.RED);
				}
				row.getCell("isEdit").setValue(new Boolean(hasCon.isIsEdit()));
				if (hasCon.isIsEdit()) {
					row.getStyleAttributes().setBackground(Color.PINK);
				}
				row.getCell("project").setValue(hasCon.getProject());
				row.getCell("conNumber").setValue(hasCon.getContract());
				row.getCell("conName").setValue(hasCon.getContractName());
				row.getCell("conPrice").setValue(hasCon.getContractPrice());
				if (!FDCHelper.isEmpty(hasCon.getContract())) {
					row.getCell("contractId").setValue(
							hasCon.getContract().getId().toString());
				}
				row.getCell("lastMonthPayable").setValue(
						hasCon.getLastMonthPayable());
				row.getCell("lastMonthPay").setValue(hasCon.getLastMonthPay());
				row.getCell("lastMonthNopay").setValue(
						hasCon.getLastMonthNopay());
				row.getCell("lastMonthEnRoute").setValue(
						hasCon.getLastMonthEnRoute());
				for (int j = 0; j < hasCon.getEntrys().size(); j++) {
					FDCDepConPayPlanContractEntryInfo conEntry = hasCon
							.getEntrys().get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(conEntry.getMonth());
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					String keyHead = "MONTH" + year + "" + month;
					row.getCell(keyHead + "id").setValue(conEntry.getId());
					row.getCell(keyHead + "plan").setValue(
							conEntry.getPlanPay());
					row.getCell(keyHead + "define").setValue(
							conEntry.getMoneyDefine());
					row.getCell(keyHead + "explain").setValue(
							conEntry.getExplain());
					row.getCell(keyHead + "official").setValue(
							conEntry.getOfficialPay());
				}
			}
		}
		// 加载待签订合同计划
		if (editData.getUnsettledCon() != null) {
			for (int i = 0; i < editData.getUnsettledCon().size(); i++) {
				IRow row = tblUnsettledCon.addRow();
				FDCDepConPayPlanUnsettledConInfo unCon = editData
						.getUnsettledCon().get(i);
				row.getCell("id").setValue(unCon.getId());
				row.getCell("isBack").setValue(new Boolean(unCon.isIsBack()));
				if (unCon.isIsBack()) {
					row.getStyleAttributes().setBackground(Color.RED);
				}
				row.getCell("isEdit").setValue(new Boolean(unCon.isIsEdit()));
				if (unCon.isIsEdit()) {
					row.getStyleAttributes().setBackground(Color.PINK);
				}
				row.getCell("project").setValue(unCon.getProject());
				row.getCell("unsettledConNumber").setValue(
						unCon.getUnConNumber());
				row.getCell("unsettledConName").setValue(unCon.getUnConName());
				row.getCell("unsettledConestPrice").setValue(
						unCon.getPlanAmount());
				for (int j = 0; j < unCon.getEntrys1().size(); j++) {
					FDCDepConPayPlanUnsettleEntryInfo unEntry = unCon
							.getEntrys1().get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(unEntry.getMonth());
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					String keyHead = "MONTH" + year + "" + month;
					row.getCell(keyHead + "id").setValue(unEntry.getId());
					row.getCell(keyHead + "plan")
							.setValue(unEntry.getPlanPay());
					row.getCell(keyHead + "define").setValue(
							unEntry.getMoneyDefine());
					row.getCell(keyHead + "explain").setValue(
							unEntry.getExplain());
					row.getCell(keyHead + "official").setValue(
							unEntry.getOfficialPay());
				}
			}
		}
		// 加载无合同计划
		if (editData.getNoContract() != null) {
			for (int i = 0; i < editData.getNoContract().size(); i++) {
				IRow row = tblNoContract.addRow();
				FDCDepConPayPlanNoContractInfo noCon = editData.getNoContract()
						.get(i);
				row.getCell("id").setValue(noCon.getId());
				row.getCell("isBack").setValue(new Boolean(noCon.isIsBack()));
				if (noCon.isIsBack()) {
					row.getStyleAttributes().setBackground(Color.RED);
				}
				row.getCell("isEdit").setValue(new Boolean(noCon.isIsEdit()));
				if (noCon.isIsEdit()) {
					row.getStyleAttributes().setBackground(Color.PINK);
				}
				row.getCell("project").setValue(noCon.getProject());
				row.getCell("payMatters").setValue(noCon.getPayMatters());
				row.getCell("payMattersName").setValue(
						noCon.getPayMattersName());
				for (int j = 0; j < noCon.getEntrys1().size(); j++) {
					FDCDepConPayPlanNoContractEntryInfo noEntry = noCon
							.getEntrys1().get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(noEntry.getMonth());
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					String keyHead = "MONTH" + year + "" + month;
					row.getCell(keyHead + "id").setValue(noEntry.getId());
					row.getCell(keyHead + "plan")
							.setValue(noEntry.getPlanPay());
					row.getCell(keyHead + "define").setValue(
							noEntry.getMoneyDefine());
					row.getCell(keyHead + "explain").setValue(
							noEntry.getExplain());
					row.getCell(keyHead + "official").setValue(
							noEntry.getOfficialPay());
				}
			}
		}
	}

	protected ICellEditor getCellEditor(String type) {
		if (type == null) {
			return null;
		} else if ("amount".equals(type)) {
			return FDCClientHelper.getNumberCellEditor();
		} else if ("explain".equals(type)) {
			KDDetailedArea explain = new KDDetailedArea(250, 200);
			explain.setMaxLength(1000);
			return new KDTDefaultCellEditor(explain);
		} else if ("define".equals(type)) {
			KDBizPromptBox prmtDefine = new KDBizPromptBox();
			prmtDefine
					.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");
			prmtDefine.setDisplayFormat("$name$");
			prmtDefine.setEditFormat("$number$");
			prmtDefine.setCommitFormat("$number$");
			prmtDefine.setRequired(true);
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.TRUE));
			view.setFilter(filter);
			prmtDefine.setEntityViewInfo(view);
			return new KDTDefaultCellEditor(prmtDefine);
		} else if ("contract".equals(type)) {
			KDBizPromptBox prmtcontract = new KDBizPromptBox();
			prmtcontract.setDisplayFormat("$name$");
			prmtcontract.setEditFormat("$number$");
			prmtcontract.setCommitFormat("$number$");
			prmtcontract.setRequired(true);
			prmtcontract
					.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ContractBillQueryForPayPlanQuery");

			prmtcontract.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					KDBizPromptBox f7 = (KDBizPromptBox) e.getSource();
					f7.getQueryAgent().resetRuntimeEntityView();
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					// 需要根据工程项目和责任部门过滤出合同
					Object dpt = prmtDeptment.getValue();
					AdminOrgUnitInfo admin = null;
					if (dpt == null) {
						admin = SysContext.getSysContext()
								.getCurrentAdminUnit();
					} else {
						admin = (AdminOrgUnitInfo) dpt;
					}
					filter.getFilterItems().add(
							new FilterItemInfo("respDept.id", admin.getId()
									.toString(), CompareType.EQUALS));// 责任部门
					filter.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.AUDITTED_VALUE,
									CompareType.EQUALS));// 已审批
					filter.getFilterItems().add(
							new FilterItemInfo("isCoseSplit", Boolean.TRUE));
					view.setFilter(filter);
					f7.setEntityViewInfo(view);
				}
			});
			return new KDTDefaultCellEditor(prmtcontract);
		} else if ("project".equals(type)) {
			KDBizPromptBox prmtCurProject = new KDBizPromptBox();
			prmtCurProject.setDisplayFormat("$name$");
			prmtCurProject.setEditFormat("$number$");
			prmtCurProject.setCommitFormat("$number$");
			prmtCurProject.setRequired(true);
			prmtCurProject
					.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");
			return new KDTDefaultCellEditor(prmtCurProject);
		} else if ("text".equals(type)) {
			KDTextField txt = new KDTextField();
			txt.setMaxLength(80);
			return new KDTDefaultCellEditor(txt);
		}
		return null;
	}

	/**
	 * 添加合计行
	 */
	protected void addSumRow() {
		IRow row = tblContract.getRow(tblContract.getRowCount() - 1);
		if (row != null && "小计".equals(row.getCell("project").getValue())) {
			tblContract.removeRow(tblContract.getRowCount() - 1);
		}
		row = tblUnsettledCon.getRow(tblUnsettledCon.getRowCount() - 1);
		if (row != null && "小计".equals(row.getCell("project").getValue())) {
			tblUnsettledCon.removeRow(tblUnsettledCon.getRowCount() - 1);
		}
		row = tblNoContract.getRow(tblNoContract.getRowCount() - 1);
		if (row != null && "小计".equals(row.getCell("project").getValue())) {
			tblNoContract.removeRow(tblNoContract.getRowCount() - 1);
		}

		int cycle = editData.getPayPlanCycle().getCycle().getValue();
		int[] COLS_Con = new int[cycle * 2 + 5];
		int[] COLS_UnC = new int[cycle * 2 + 1];
		int[] COLS_NoC = new int[cycle * 2];

		// 合计合同分录
		COLS_Con[0] = 6;
		COLS_Con[1] = 8;
		COLS_Con[2] = 9;
		COLS_Con[3] = 10;
		COLS_Con[4] = 11;
		for (int i = 0; i < cycle; i++) {
			COLS_Con[5 + 2 * i] = START_CON + (i * 5) + 1;
			COLS_Con[6 + 2 * i] = START_CON + (i * 5) + 4;
		}
		row = FDCTableHelper.calcSumForTable(0, tblContract.getRowCount() - 1,
				COLS_Con, tblContract);
		if (row != null) {
			row.getCell("project").setValue("小计");
		}
		// 合计待签订合同分录
		COLS_UnC[0] = 6;
		for (int i = 0; i < cycle; i++) {
			COLS_UnC[1 + 2 * i] = START_UNC + (i * 5) + 1;
			COLS_UnC[2 + 2 * i] = START_UNC + (i * 5) + 4;
		}
		row = FDCTableHelper.calcSumForTable(0,
				tblUnsettledCon.getRowCount() - 1, COLS_UnC, tblUnsettledCon);
		if (row != null) {
			row.getCell("project").setValue("小计");
		}
		// 合计无合同分录
		for (int i = 0; i < cycle; i++) {
			COLS_NoC[2 * i] = START_NOC + (i * 5) + 1;
			COLS_NoC[2 * i + 1] = START_NOC + (i * 5) + 4;
		}
		row = FDCTableHelper.calcSumForTable(0,
				tblNoContract.getRowCount() - 1, COLS_NoC, tblNoContract);
		if (row != null) {
			row.getCell("project").setValue("小计");
		}
	}

	public void storeFields() {
		super.storeFields();
		storeEntryData();
	}

	protected void storeEntryData() {
		editData.getHasContract().clear();
		editData.getUnsettledCon().clear();
		editData.getNoContract().clear();
		// 保存合同计划分录
		for (int i = 0; i < tblContract.getRowCount(); i++) {
			IRow row = tblContract.getRow(i);
			FDCDepConPayPlanContractInfo conPlan = new FDCDepConPayPlanContractInfo();
			BOSUuid id = (BOSUuid) row.getCell("id").getValue();
			Boolean isBack = (Boolean) row.getCell("isBack").getValue();
			if (isBack == null) {
				isBack = Boolean.FALSE;
			}
			Boolean isEdit = (Boolean) row.getCell("isEdit").getValue();
			if (isEdit == null) {
				isEdit = Boolean.FALSE;
			}
			Object obj = row.getCell("project").getValue();
			if ("小计".equals(obj)) {
				continue;
			}
			CurProjectInfo project = (CurProjectInfo) obj;
			ContractBillInfo contract = (ContractBillInfo) row.getCell(
					"conNumber").getValue();
			String conName = (String) row.getCell("conName").getValue();
			BigDecimal conPrice = (BigDecimal) row.getCell("conPrice")
					.getValue();
			BigDecimal lastMonthPayable = (BigDecimal) row.getCell(
					"lastMonthPayable").getValue();
			BigDecimal lastMonthPay = (BigDecimal) row.getCell("lastMonthPay")
					.getValue();
			BigDecimal lastMonthNopay = (BigDecimal) row.getCell(
					"lastMonthNopay").getValue();
			BigDecimal lastMonthEnRoute = (BigDecimal) row.getCell(
					"lastMonthEnRoute").getValue();

			conPlan.setId(id);
			conPlan.setIsBack(isBack.booleanValue());
			conPlan.setIsEdit(isEdit.booleanValue());
			conPlan.setProject(project);
			conPlan.setContract(contract);
			conPlan.setContractName(conName);
			conPlan.setContractPrice(conPrice);
			conPlan.setLastMonthPayable(lastMonthPayable);
			conPlan.setLastMonthPay(lastMonthPay);
			conPlan.setLastMonthNopay(lastMonthNopay);
			conPlan.setLastMonthEnRoute(lastMonthEnRoute);

			for (int j = START_CON; j < tblContract.getColumnCount(); j += 5) {
				FDCDepConPayPlanContractEntryInfo planEntry = new FDCDepConPayPlanContractEntryInfo();
				BOSUuid planID = (BOSUuid) row.getCell(j).getValue();
				BigDecimal planPay = (BigDecimal) row.getCell(j + 1).getValue();
				PaymentTypeInfo moneyDefine = (PaymentTypeInfo) row.getCell(
						j + 2).getValue();
				String explain = (String) row.getCell(j + 3).getValue();
				BigDecimal officialPay = (BigDecimal) row.getCell(j + 4)
						.getValue();
				planEntry.setId(planID);
				planEntry.setPlanPay(planPay);
				planEntry.setMoneyDefine(moneyDefine);
				planEntry.setExplain(explain);
				planEntry.setOfficialPay(officialPay);

				String key = tblContract.getColumn(j).getKey();
				String year = key.substring(5, 9);
				String month = key.substring(9, key.length() - 2);
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, new Integer(year).intValue());
				cal.set(Calendar.MONTH, new Integer(month).intValue() - 1);
				cal.set(Calendar.DAY_OF_MONTH, 15);
				planEntry.setMonth(cal.getTime());

				conPlan.getEntrys().add(planEntry);
			}
			editData.getHasContract().add(conPlan);
		}
		// 保存待签订合同计划分录
		for (int i = 0; i < tblUnsettledCon.getRowCount(); i++) {
			IRow row = tblUnsettledCon.getRow(i);
			FDCDepConPayPlanUnsettledConInfo unCPlan = new FDCDepConPayPlanUnsettledConInfo();
			BOSUuid id = (BOSUuid) row.getCell("id").getValue();
			Boolean isBack = (Boolean) row.getCell("isBack").getValue();
			if (isBack == null) {
				isBack = Boolean.FALSE;
			}
			Boolean isEdit = (Boolean) row.getCell("isEdit").getValue();
			if (isEdit == null) {
				isEdit = Boolean.FALSE;
			}
			Object obj = row.getCell("project").getValue();
			if ("小计".equals(obj)) {
				continue;
			}
			CurProjectInfo project = (CurProjectInfo) obj;
			String unConNumber;
			Object ucObj = row.getCell("unsettledConNumber").getValue();
			if (ucObj == null) {
				unConNumber = null;
			} else if (ucObj instanceof ProgrammingContractInfo) {
				unConNumber = ((ProgrammingContractInfo) ucObj).getLongNumber();
			} else {
				unConNumber = ucObj.toString();
			}
			String unConName = (String) row.getCell("unsettledConName")
					.getValue();
			BigDecimal unConPrice = (BigDecimal) row.getCell(
					"unsettledConestPrice").getValue();
			unCPlan.setId(id);
			unCPlan.setIsBack(isBack.booleanValue());
			unCPlan.setIsEdit(isEdit.booleanValue());
			unCPlan.setProject(project);
			unCPlan.setUnConNumber(unConNumber);
			unCPlan.setUnConName(unConName);
			unCPlan.setPlanAmount(unConPrice);

			for (int j = START_UNC; j < tblUnsettledCon.getColumnCount(); j += 5) {
				FDCDepConPayPlanUnsettleEntryInfo planEntry = new FDCDepConPayPlanUnsettleEntryInfo();
				BOSUuid planID = (BOSUuid) row.getCell(j).getValue();
				BigDecimal planPay = (BigDecimal) row.getCell(j + 1).getValue();
				PaymentTypeInfo moneyDefine = (PaymentTypeInfo) row.getCell(
						j + 2).getValue();
				String explain = (String) row.getCell(j + 3).getValue();
				BigDecimal officialPay = (BigDecimal) row.getCell(j + 4)
						.getValue();
				planEntry.setId(planID);
				planEntry.setPlanPay(planPay);
				planEntry.setMoneyDefine(moneyDefine);
				planEntry.setExplain(explain);
				planEntry.setOfficialPay(officialPay);

				String key = tblUnsettledCon.getColumn(j).getKey();
				String year = key.substring(5, 9);
				String month = key.substring(9, key.length() - 2);
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, new Integer(year).intValue());
				cal.set(Calendar.MONTH, new Integer(month).intValue() - 1);
				cal.set(Calendar.DAY_OF_MONTH, 15);
				planEntry.setMonth(cal.getTime());

				unCPlan.getEntrys1().add(planEntry);
			}
			editData.getUnsettledCon().add(unCPlan);
		}
		// 保存无合同计划分录
		for (int i = 0; i < tblNoContract.getRowCount(); i++) {
			IRow row = tblNoContract.getRow(i);
			FDCDepConPayPlanNoContractInfo noCPlan = new FDCDepConPayPlanNoContractInfo();
			BOSUuid id = (BOSUuid) row.getCell("id").getValue();
			Boolean isBack = (Boolean) row.getCell("isBack").getValue();
			if (isBack == null) {
				isBack = Boolean.FALSE;
			}
			Boolean isEdit = (Boolean) row.getCell("isEdit").getValue();
			if (isEdit == null) {
				isEdit = Boolean.FALSE;
			}
			Object obj = row.getCell("project").getValue();
			if ("小计".equals(obj)) {
				continue;
			}
			CurProjectInfo project = (CurProjectInfo) obj;
			String payMatters = (String) row.getCell("payMatters").getValue();
			String payMattersName = (String) row.getCell("payMattersName")
					.getValue();
			noCPlan.setId(id);
			noCPlan.setIsBack(isBack.booleanValue());
			noCPlan.setIsEdit(isEdit.booleanValue());
			noCPlan.setProject(project);
			noCPlan.setPayMatters(payMatters);
			noCPlan.setPayMattersName(payMattersName);

			for (int j = START_NOC; j < tblNoContract.getColumnCount(); j += 5) {
				FDCDepConPayPlanNoContractEntryInfo planEntry = new FDCDepConPayPlanNoContractEntryInfo();
				BOSUuid planID = (BOSUuid) row.getCell(j).getValue();
				BigDecimal planPay = (BigDecimal) row.getCell(j + 1).getValue();
				PaymentTypeInfo moneyDefine = (PaymentTypeInfo) row.getCell(
						j + 2).getValue();
				String explain = (String) row.getCell(j + 3).getValue();
				BigDecimal officialPay = (BigDecimal) row.getCell(j + 4)
						.getValue();
				planEntry.setId(planID);
				planEntry.setPlanPay(planPay);
				planEntry.setMoneyDefine(moneyDefine);
				planEntry.setExplain(explain);
				planEntry.setOfficialPay(officialPay);

				String key = tblNoContract.getColumn(j).getKey();
				String year = key.substring(5, 9);
				String month = key.substring(9, key.length() - 2);
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, new Integer(year).intValue());
				cal.set(Calendar.MONTH, new Integer(month).intValue() - 1);
				cal.set(Calendar.DAY_OF_MONTH, 15);
				planEntry.setMonth(cal.getTime());

				noCPlan.getEntrys1().add(planEntry);
			}
			editData.getNoContract().add(noCPlan);
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initUI();
		// spYear.setValue(new Integer(2222));
	}

	protected void initUI() {
		btnPublish.setIcon(EASResource.getIcon("imgTbtn_timingrefer"));
		menuItemPublish.setIcon(EASResource.getIcon("imgTbtn_timingrefer"));
		btnRevise.setIcon(EASResource.getIcon("imgTbtn_emend"));
		menuItemRevise.setIcon(EASResource.getIcon("imgTbtn_emend"));
		btnBack.setIcon(EASResource.getIcon("imgTbtn_untreadreport"));
		btnIntroPre.setIcon(EASResource.getIcon("imgTbtn_editiondifference"));
		btnViewContract.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));

		txtName.setMaxLength(100);
		description.setAutoscrolls(true);
		description.setRows(3);
		SpinnerNumberModel model = new SpinnerNumberModel();
		model.setMinimum(new Integer(1));
		model.setMaximum(new Integer(12));
		model.setStepSize(new Integer(1));
		spMonth.setModel(model);
		spMonth.setValue(new Integer(editData.getMonth()), false);

		if (getUIContext().get("flag_state") != null) {
			flag_state = (String) getUIContext().get("flag_state");
			actionSave.setEnabled(false);
			actionAddNew.setEnabled(false);
			actionRemove.setEnabled(false);
			txtName.setEnabled(false);
			txtNumber.setEnabled(false);
			prmtDeptment.setEnabled(false);
			spMonth.setEnabled(false);
			spYear.setEnabled(false);
			description.setEnabled(false);
			actionRevise.setEnabled(false);
		}

		year_old = this.spYear.getIntegerVlaue();
		month_old = this.spMonth.getIntegerVlaue();

		CtrlUnitInfo obj = editData.getCU();
		if (obj == null) {
			obj = SysContext.getSysContext().getCurrentCtrlUnit();
		}
		String cuId = obj.getId().toString();
		FDCClientUtils.setRespDeptF7(prmtDeptment, this,
				canSelecOtherOrgPerson() ? null : cuId);

		ctnCon.removeAllButton();
		ctnCon.addButton(btnIntroPre);
		ctnCon.addButton(btnAddLine);
		ctnCon.addButton(btnRemoveLine);

		ObjectValueRender ovrNum = new ObjectValueRender();
		ovrNum.setFormat(new BizDataFormat("$number$"));
		tblContract.getColumn("conNumber").setRenderer(ovrNum);
		tblContract.getColumn("conNumber").setEditor(getCellEditor("contract"));

		ovrNum = new ObjectValueRender();
		ovrNum.setFormat(new BizDataFormat("$longNumber$"));
		tblUnsettledCon.getColumn("unsettledConNumber").setRenderer(ovrNum);
		tblUnsettledCon.getColumn("unsettledConestPrice").setEditor(
				getCellEditor("amount"));

		ObjectValueRender ovrName = new ObjectValueRender();
		ovrName.setFormat(new BizDataFormat("$name$"));
		tblUnsettledCon.getColumn("project").setRenderer(ovrName);
		tblUnsettledCon.getColumn("project")
				.setEditor(getCellEditor("project"));
		tblNoContract.getColumn("project").setRenderer(ovrName);
		tblNoContract.getColumn("project").setEditor(getCellEditor("project"));

		if (FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
			actionPublish.setEnabled(true);
		} else if (FDCBillStateEnum.BACK.equals(editData.getState())) {
			actionAudit.setEnabled(false);
			actionRevise.setEnabled(true);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		} else if (FDCBillStateEnum.PUBLISH.equals(editData.getState())) {
			actionAudit.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		}

		// 参数未启用，则手工录入累计完工金额
		try {
			separteFromPayment = FDCUtils.getBooleanValue4FDCParamByKey(null,
					SysContext.getSysContext().getCurrentOrgUnit().getId()
							.toString(), "FDC317_SEPARATEFROMPAYMENT");
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		}
		if (!separteFromPayment) {
			tblContract.getColumn("lastMonthPayable").getStyleAttributes()
					.setLocked(false);
		}

		// 工作流可修改最终批复金额
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if (editData.getId() != null && isFromWorkflow != null
				&& "true".equals(isFromWorkflow.toString())) {
			actionSubmit.setEnabled(true);
			tblContract.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			tblContract.setEditable(true);
			tblContract.getStyleAttributes().setLocked(true);
			for (int i = START_CON + 4; i < tblContract.getColumnCount(); i += 5) {

				tblContract.getColumn(i).getStyleAttributes().setLocked(false);
			}
			tblUnsettledCon
					.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			tblUnsettledCon.setEditable(true);
			tblUnsettledCon.getStyleAttributes().setLocked(true);
			for (int i = START_UNC + 4; i < tblUnsettledCon.getColumnCount(); i += 5) {
				tblUnsettledCon.getColumn(i).getStyleAttributes().setLocked(
						false);
			}
			tblNoContract
					.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			tblNoContract.setEditable(true);
			tblNoContract.getStyleAttributes().setLocked(true);
			for (int i = START_NOC + 4; i < tblNoContract.getColumnCount(); i += 5) {
				tblNoContract.getColumn(i).getStyleAttributes()
						.setLocked(false);
			}
		}
	}

	protected boolean checkCanSubmit() throws Exception {
		// 工作流可修改最终批复金额
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if (editData.getId() != null && isFromWorkflow != null
				&& "true".equals(isFromWorkflow.toString())) {
			return true;
		} else {
			return super.checkCanSubmit();
		}
	}

	/**
	 * 由于onShow里面会调用此方法，给第一个表添加排序，而此单据不需要排序，故重载
	 */
	protected void initListener() {
		// super.initListener();
	}

	protected void attachListeners() {
		addDataChangeListener(spYear);
		addDataChangeListener(spMonth);
		addDataChangeListener(prmtDeptment);
	}

	protected void detachListeners() {
		removeDataChangeListener(spYear);
		removeDataChangeListener(spMonth);
		removeDataChangeListener(prmtDeptment);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCDepConPayPlanBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		if (this.pnlBig.getSelectedIndex() == 0) {
			table = this.tblContract;
		} else if (this.pnlBig.getSelectedIndex() == 1) {
			table = this.tblUnsettledCon;
		} else if (this.pnlBig.getSelectedIndex() == 2) {
			table = this.tblNoContract;
		}
		return table;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");

		sic.add("curProject.id");
		sic.add("curProject.name");
		sic.add("curProject.number");
		sic.add("curProject.codingNumber");
		sic.add("curProject.displayName");
		sic.add("curProject.fullOrgUnit.name");

		sic.add("CU.id");
		sic.add("orgUnit.id");

		sic.add("payPlanCycle.id");
		sic.add("payPlanCycle.cycle");

		sic.add("deptment.id");
		sic.add("deptment.number");
		sic.add("deptment.name");
		sic.add("deptment.isAdminOrgUnit");

		sic.add("creator.id");
		sic.add("creator.number");
		sic.add("creator.name");

		sic.add("auditor.id");
		sic.add("auditor.number");
		sic.add("auditor.name");

		sic.add("hasContract.id");
		sic.add("hasContract.isBack");
		sic.add("hasContract.isEdit");
		sic.add("hasContract.project.id");
		sic.add("hasContract.project.number");
		sic.add("hasContract.project.name");
		sic.add("hasContract.contract.id");
		sic.add("hasContract.contract.number");
		sic.add("hasContract.contractName");
		sic.add("hasContract.contractPrice");
		sic.add("hasContract.lastMonthPayable");
		sic.add("hasContract.lastMonthPay");
		sic.add("hasContract.lastMonthNopay");
		sic.add("hasContract.lastMonthEnRoute");
		sic.add("hasContract.entrys.*");
		sic.add("hasContract.entrys.moneyDefine.id");
		sic.add("hasContract.entrys.moneyDefine.number");
		sic.add("hasContract.entrys.moneyDefine.name");

		sic.add("unsettledCon.id");
		sic.add("unsettledCon.isBack");
		sic.add("unsettledCon.isEdit");
		sic.add("unsettledCon.project.id");
		sic.add("unsettledCon.project.number");
		sic.add("unsettledCon.project.name");
		sic.add("unsettledCon.unConNumber");
		sic.add("unsettledCon.unConName");
		sic.add("unsettledCon.planAmount");
		sic.add("unsettledCon.entrys1.*");
		sic.add("unsettledCon.entrys1.moneyDefine.id");
		sic.add("unsettledCon.entrys1.moneyDefine.number");
		sic.add("unsettledCon.entrys1.moneyDefine.name");

		sic.add("noContract.id");
		sic.add("noContract.isBack");
		sic.add("noContract.isEdit");
		sic.add("noContract.project.id");
		sic.add("noContract.project.number");
		sic.add("noContract.project.name");
		sic.add("noContract.payMatters");
		sic.add("noContract.payMattersName");
		sic.add("noContract.entrys1.*");
		sic.add("noContract.entrys1.moneyDefine.id");
		sic.add("noContract.entrys1.moneyDefine.number");
		sic.add("noContract.entrys1.moneyDefine.name");

		return sic;
	}

	protected void spYear_stateChanged(ChangeEvent e) throws Exception {
		if (isFirstOnload()) {
			return;
		}
		AdminOrgUnitInfo adminInfo = (AdminOrgUnitInfo) prmtDeptment.getValue();
		Integer year = spYear.getIntegerVlaue();
		Integer month = spMonth.getIntegerVlaue();
		checkHasMonthDepHasPlan(adminInfo, year, month);
		// 改变月份清空分录
		int result = MsgBox.OK;
		if (tblContract.getRowCount() > 0 || tblUnsettledCon.getRowCount() > 0
				|| tblNoContract.getRowCount() > 0) {
			result = MsgBox.showConfirm2("改变编制月份将清空分录数据！");
		}
		if (result == MsgBox.OK) {
			clearTable();
			initTableColumn();
			year_old = year;
		} else {
			spYear.setValue(year_old, false);
		}
	}

	/**
	 * 校验部门该月度的计划是否存在
	 * @param adminInfo
	 * @param year
	 * @param month
	 */
	private void checkHasMonthDepHasPlan(AdminOrgUnitInfo adminInfo, Integer year, Integer month) {
		if (hasMonthHasPlan(adminInfo, year, month)) {
			FDCMsgBox.showWarning(this, "部门为: \"" + adminInfo.getName()
					+ "\" 编制月份为：" + year + "年" + month + " 月的合同月度滚动付款计划已存在");
			spYear.setValue(year_old, false);
			spMonth.setValue(month_old, false);
			abort();
		}
	}

	protected void spMonth_stateChanged(ChangeEvent e) throws Exception {
		if (isFirstOnload()) {
			return;
		}
		AdminOrgUnitInfo adminInfo = (AdminOrgUnitInfo) prmtDeptment.getValue();
		Integer year = spYear.getIntegerVlaue();
		Integer month = spMonth.getIntegerVlaue();
		checkHasMonthDepHasPlan(adminInfo, year, month);
		// 改变月份清空分录
		int result = MsgBox.OK;
		if (tblContract.getRowCount() > 0 || tblUnsettledCon.getRowCount() > 0
				|| tblNoContract.getRowCount() > 0) {
			result = MsgBox.showConfirm2("改变编制月份将清空分录数据！");
		}
		if (result == MsgBox.OK) {
			clearTable();
			initTableColumn();
			month_old = month;
		} else {
			spMonth.setValue(month_old, false);
		}

	}

	protected void addDataChangeListener(JComponent com) {

		EventListener[] listeners = (EventListener[]) listenersMap.get(com);

		if (listeners != null && listeners.length > 0) {
			if (com instanceof KDPromptBox) {
				for (int i = 0; i < listeners.length; i++) {
					((KDPromptBox) com)
							.addDataChangeListener((DataChangeListener) listeners[i]);
				}
			} else if (com instanceof KDFormattedTextField) {
				for (int i = 0; i < listeners.length; i++) {
					((KDFormattedTextField) com)
							.addDataChangeListener((DataChangeListener) listeners[i]);
				}
			} else if (com instanceof KDDatePicker) {
				for (int i = 0; i < listeners.length; i++) {
					((KDDatePicker) com)
							.addDataChangeListener((DataChangeListener) listeners[i]);
				}
			} else if (com instanceof KDComboBox) {
				for (int i = 0; i < listeners.length; i++) {
					((KDComboBox) com)
							.addItemListener((ItemListener) listeners[i]);
				}
			} else if (com instanceof KDSpinner) {
				for (int i = 0; i < listeners.length; i++) {
					((KDSpinner) com)
							.addChangeListener((ChangeListener) listeners[i]);
				}
			}
		}

	}

	protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;

		if (com instanceof KDPromptBox) {
			listeners = com.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDPromptBox) com)
						.removeDataChangeListener((DataChangeListener) listeners[i]);
			}
		} else if (com instanceof KDFormattedTextField) {
			listeners = com.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDFormattedTextField) com)
						.removeDataChangeListener((DataChangeListener) listeners[i]);
			}
		} else if (com instanceof KDDatePicker) {
			listeners = com.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDDatePicker) com)
						.removeDataChangeListener((DataChangeListener) listeners[i]);
			}
		} else if (com instanceof KDComboBox) {
			listeners = com.getListeners(ItemListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDComboBox) com)
						.removeItemListener((ItemListener) listeners[i]);
			}
		} else if (com instanceof KDSpinner) {
			listeners = com.getListeners(ChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDSpinner) com)
						.removeChangeListener((ChangeListener) listeners[i]);
			}
		}

		if (listeners != null && listeners.length > 0) {
			listenersMap.put(com, listeners);
		}
	}

	/**
	 * 检查当前编制月份是否存在计划
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected boolean hasMonthHasPlan(AdminOrgUnitInfo adminInfo,
			Integer year,
			Integer month) {
		if (adminInfo == null
				|| (!getOprtState().equals(STATUS_ADDNEW) && !getOprtState()
						.equals(STATUS_EDIT))) {
			return false;
		}
		// Integer year = spYear.getIntegerVlaue();
		// Integer month = spMonth.getIntegerVlaue();
		FilterInfo filter = new FilterInfo();
		if (!STATUS_ADDNEW.equals(getOprtState())) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", editData.getId().toString(),
							CompareType.NOTEQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("year", year));
		filter.getFilterItems().add(new FilterItemInfo("month", month));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("deptment.id", adminInfo.getId()
								.toString()));
		boolean isDep = false;
		try {
			isDep = FDCDepConPayPlanBillFactory.getRemoteInstance().exists(
					filter);
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		}
		return isDep;
	}

	protected void prmtDeptment_dataChanged(DataChangeEvent e) throws Exception {
		if (isFirstOnload()) {
			return;
		}
		Object oldValue = e.getOldValue();
		Object newValue = e.getNewValue();
		if (newValue == null) {
			tblContract.removeRows();
			tblUnsettledCon.removeRows();
			tblNoContract.removeRows();
		} else if (!((AdminOrgUnitInfo) newValue).isIsLeaf()) {
			prmtDeptment.setDataNoNotify(oldValue);
		} else {
			AdminOrgUnitInfo adminInfo = (AdminOrgUnitInfo) newValue;
			Integer year = spYear.getIntegerVlaue();
			Integer month = spMonth.getIntegerVlaue();
			if (hasMonthHasPlan(adminInfo, year, month)) {
				FDCMsgBox.showWarning(this, "部门为: \"" + adminInfo.getName()
						+ "\" 编制月份为：" + year + "年" + month + " 月的合同月度滚动付款计划已存在");
				prmtDeptment.setDataNoNotify(oldValue);
				abort();
			}
			int result = MsgBox.showConfirm2("改变责任部门将清空分录数据！");
			if (result == MsgBox.OK) {
				tblContract.removeRows();
				tblUnsettledCon.removeRows();
				tblNoContract.removeRows();
			} else {
				prmtDeptment.setDataNoNotify(oldValue);
			}
		}
	}

	protected void pnlBig_stateChanged(ChangeEvent e) throws Exception {
		if (pnlBig.getSelectedIndex() == 0) {
			ctnCon.removeAllButton();
			ctnCon.addButton(btnIntroPre);
			ctnCon.addButton(btnAddLine);
			ctnCon.addButton(btnRemoveLine);
			actionViewContract.setEnabled(true);
		} else if (pnlBig.getSelectedIndex() == 1) {
			ctnUnC.removeAllButton();
			ctnUnC.addButton(btnIntroPre);
			ctnUnC.addButton(btnAddLine);
			ctnUnC.addButton(btnRemoveLine);
			actionViewContract.setEnabled(false);
		} else if (pnlBig.getSelectedIndex() == 2) {
			ctnNoC.removeAllButton();
			ctnNoC.addButton(btnIntroPre);
			ctnNoC.addButton(btnAddLine);
			ctnNoC.addButton(btnRemoveLine);
			actionViewContract.setEnabled(false);
		}
	}

	protected void tblContract_tableClicked(KDTMouseEvent e) throws Exception {
		int colIndex = e.getColIndex();
		if (STATUS_VIEW.equals(getOprtState())) {
			if (colIndex > START_CON && (colIndex - START_CON) % 5 == 3) {
				KDDetailedAreaUtil.showDialog(this, tblContract);
			}
		}
	}

	protected void tblContract_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		IRow row = tblContract.getRow(e.getRowIndex());
		if (row == null) {
			return;
		}
		String key = tblContract.getColumnKey(colIndex);
		// 累计完工金额列改变，需要同时更新未付列
		if ("lastMonthPayable".equals(key)) {
			BigDecimal lastMonthPayable = (BigDecimal) row.getCell(
					"lastMonthPayable").getValue();
			BigDecimal lastMonthPay = (BigDecimal) row.getCell("lastMonthPay")
					.getValue();
			lastMonthPayable = lastMonthPayable == null ? FDCHelper.ZERO
					: lastMonthPayable;
			lastMonthPay = lastMonthPay == null ? FDCHelper.ZERO : lastMonthPay;
			row.getCell("lastMonthNopay").setValue(
					lastMonthPayable.subtract(lastMonthPay));
		}
		// 合同编码列改变
		if ("conNumber".equals(key)) {
			Object old = e.getOldValue();
			Object cur = e.getValue();
			old = old == null ? "" : old;
			cur = cur == null ? "" : cur;
			if (old.equals(cur)) {
				return;
			}
			ContractBillInfo con = (ContractBillInfo) e.getValue();
			if (con != null) {
				if (con.getCurProject() != null) {
					CurProjectInfo prj = CurProjectFactory.getRemoteInstance()
							.getCurProjectInfo(
									new ObjectUuidPK(con.getCurProject()
											.getId()), getSimpleSelector());
					row.getCell("project").setValue(prj);
				} else {
					row.getCell("project").setValue(null);
				}
				row.getCell("conName").setValue(con.getName());

				String conID = con.getId().toString();
				Map values = calConInfo(conID);
				row.getCell("conPrice").setValue(values.get("conPrice"));
				row.getCell("lastMonthPayable").setValue(values.get("payable"));
				row.getCell("lastMonthPay").setValue(values.get("pay"));
				row.getCell("lastMonthNopay").setValue(values.get("noPay"));
				row.getCell("lastMonthEnRoute").setValue(values.get("enRoute"));
				
				// 带出上月计划数
				FDCDepConPayPlanBillInfo plan = getPreMonthConInfo();
				int year = spYear.getIntegerVlaue().intValue();
				int month = spMonth.getIntegerVlaue().intValue();
				month--;
				if (month < 1) {
					year--;
					month = 12;
				}
				Calendar cal = Calendar.getInstance();
				cal.set(year, month, 1, 0, 0, 0);
				if (plan != null) {
					FDCDepConPayPlanContractCollection hasCon = plan
							.getHasContract();
					if (hasCon != null && hasCon.size() > 0) {
						for (int i = 0; i < hasCon.size(); i++) {
							FDCDepConPayPlanContractInfo info = hasCon.get(i);
							String curConID = info.getContract().getId()
									.toString();
							if (conID.equals(curConID)) {
								for (int j = 0; j < info.getEntrys().size(); j++) {
									FDCDepConPayPlanContractEntryInfo entry = info
											.getEntrys().get(j);
									if (entry.getMonth().compareTo(
											cal.getTime()) >= 0) {
										Calendar date = Calendar.getInstance();
										date.setTime(entry.getMonth());
										String keyHead = "MONTH"
												+ date.get(Calendar.YEAR)
												+ ""
												+ (date.get(Calendar.MONTH) + 1);
										row.getCell(keyHead + "plan").setValue(
												entry.getPlanPay());
										row.getCell(keyHead + "define")
												.setValue(
														entry.getMoneyDefine());
										row.getCell(keyHead + "explain")
												.setValue(entry.getExplain());
										row.getCell(keyHead + "official")
												.setValue(
														entry.getOfficialPay());
									}
								}
							}
						}
					}
				}
			} else {
				row.getCell("project").setValue(null);
				row.getCell("conName").setValue(null);
				row.getCell("conPrice").setValue(null);
				row.getCell("lastMonthPayable").setValue(null);
				row.getCell("lastMonthPay").setValue(null);
				row.getCell("lastMonthNopay").setValue(null);
				row.getCell("lastMonthEnRoute").setValue(null);
			}
		}

		// 如果计划支付金额列改变，同时改变最终批复金额
		if (colIndex >= START_CON && (colIndex - START_CON) % 5 == 1) {
			row.getCell(colIndex + 3).setValue(e.getValue());
		}

		// 如果编辑详情列，完成后设置单元格不换行
		if (colIndex > START_CON && (colIndex - START_CON) % 5 == 3) {
			row.getCell(colIndex).getStyleAttributes().setWrapText(false);
		}

		// 重算小计
		addSumRow();

		Boolean isBack = (Boolean) row.getCell("isBack").getValue();
		if (isBack != null && isBack.booleanValue()) {
			Object old = e.getOldValue();
			Object cur = e.getValue();
			old = old == null ? "" : old;
			cur = cur == null ? "" : cur;
			if (!old.equals(cur)) {
				row.getCell("isEdit").setValue(Boolean.TRUE);
				row.getStyleAttributes().setBackground(Color.PINK);
			}
		}
	}

	protected void tblUnsettledCon_tableClicked(KDTMouseEvent e)
			throws Exception {
		int colIndex = e.getColIndex();
		if (STATUS_VIEW.equals(getOprtState())) {
			if (colIndex > START_UNC && (colIndex - START_UNC) % 5 == 3) {
				KDDetailedAreaUtil.showDialog(this, tblUnsettledCon);
			}
		}
		// 选择合约规划列，设置editor
		if (colIndex == 2) {
			IRow row = tblUnsettledCon.getRow(e.getRowIndex());
			if (row != null) {
				initProgrammingEditor(row);
			}
		}
	}

	protected void initProgrammingEditor(IRow row) {
		KDBizPromptBox myPrmtBox = new KDBizPromptBox() {
			protected Object stringToValue(String t) {
				if (t != null && t.length() > 80) {
					t = t.substring(0, 80);
				}
				return t;
			}
		};
		myPrmtBox
				.setQueryInfo("com.kingdee.eas.fdc.contract.programming.app.ProgrammingContractF7Query");
		myPrmtBox.setDisplayFormat("$name$");
		CurProjectInfo prj = (CurProjectInfo) row.getCell("project").getValue();
		if (prj != null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
					.add(
							new FilterItemInfo("project.id", prj.getId()
									.toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("programming.isLatest", Boolean.TRUE));
			view.setFilter(filter);
			myPrmtBox.setEntityViewInfo(view);
		}
		final boolean isNull = prj == null ? true : false;
		myPrmtBox.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				if (isNull) {
					MsgBox.showWarning(getDetailTable(), "必须先选择工程项目!");
					e.setCanceled(true);
				}
			}
		});
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(myPrmtBox);
		row.getCell("unsettledConNumber").setEditor(f7Editor);
	}

	protected void tblUnsettledCon_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		IRow row = tblUnsettledCon.getRow(e.getRowIndex());
		if (row == null) {
			return;
		}
		String key = tblUnsettledCon.getColumnKey(colIndex);
		// 如果计划支付金额列改变，同时改变最终批复金额
		if (colIndex >= START_UNC && (colIndex - START_UNC) % 5 == 1) {
			row.getCell(colIndex + 3).setValue(e.getValue());
		}

		// 如果编辑详情列，完成后设置单元格不换行
		if (colIndex > START_UNC && (colIndex - START_UNC) % 5 == 3) {
			row.getCell(colIndex).getStyleAttributes().setWrapText(false);
		}

		// 编辑工程项目列，重新设置设置合约规划editor
		if ("project".equals(key)) {
			initProgrammingEditor(row);
		}

		// 编辑待签订合同编码列，读取值，如果为合约规划，锁定后两列并带出，否则放开后两列
		if ("unsettledConNumber".equals(key)) {
			Object obj = row.getCell("unsettledConNumber").getValue();
			if (obj == null || obj instanceof String) {
				row.getCell("unsettledConName").getStyleAttributes().setLocked(
						false);
			} else if (obj instanceof ProgrammingContractInfo) {
				ProgrammingContractInfo cp = (ProgrammingContractInfo) obj;
				row.getCell("unsettledConName").getStyleAttributes().setLocked(
						true);
				row.getCell("unsettledConName").setValue(cp.getName());
				row.getCell("unsettledConestPrice").setValue(
						cp.getAmount());
			}
		}

		// 重算小计
		addSumRow();

		Boolean isBack = (Boolean) row.getCell("isBack").getValue();
		if (isBack != null && isBack.booleanValue()) {
			Object old = e.getOldValue();
			Object cur = e.getValue();
			old = old == null ? "" : old;
			cur = cur == null ? "" : cur;
			if (!old.equals(cur)) {
				row.getCell("isEdit").setValue(Boolean.TRUE);
				row.getStyleAttributes().setBackground(Color.PINK);
			}
		}
	}

	protected void tblNoContract_tableClicked(KDTMouseEvent e) throws Exception {
		int colIndex = e.getColIndex();
		if (STATUS_VIEW.equals(getOprtState())) {
			if (colIndex > START_NOC && (colIndex - START_NOC) % 5 == 3) {
				KDDetailedAreaUtil.showDialog(this, tblNoContract);
			}
		}
	}

	protected void tblNoContract_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		IRow row = tblNoContract.getRow(e.getRowIndex());

		// 如果计划支付金额列改变，同时改变最终批复金额
		if (colIndex >= START_NOC && (colIndex - START_NOC) % 5 == 1) {
			row.getCell(colIndex + 3).setValue(e.getValue());
		}

		// 如果编辑详情列，完成后设置单元格不换行
		if (colIndex > START_NOC && (colIndex - START_NOC) % 5 == 3) {
			row.getCell(colIndex).getStyleAttributes().setWrapText(false);
		}

		// 重算小计
		addSumRow();

		Boolean isBack = (Boolean) row.getCell("isBack").getValue();
		if (isBack != null && isBack.booleanValue()) {
			Object old = e.getOldValue();
			Object cur = e.getValue();
			old = old == null ? "" : old;
			cur = cur == null ? "" : cur;
			if (!old.equals(cur)) {
				row.getCell("isEdit").setValue(Boolean.TRUE);
				row.getStyleAttributes().setBackground(Color.PINK);
			}
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (prmtDeptment.getValue() == null) {
			MsgBox.showWarning("编辑部门不能为空！ ");
			abort();
		}
		//R110510-1166：合同月度滚动付款计划启用编码规则后，编码不能生成  by zhiyuan_tang 2010-05-12
//		if (FDCHelper.isEmpty(txtNumber.getText())) {
//			MsgBox.showWarning("编码不能为空！ ");
//			abort();
//		}
		if (FDCHelper.isEmpty(txtName.getText())) {
			MsgBox.showWarning("单据名称不能为空！ ");
			abort();
		}

		// 校验合同计划分录
		Set num = new HashSet();
		Set name = new HashSet();
		for (int i = 0; i < tblContract.getRowCount(); i++) {
			IRow row = tblContract.getRow(i);
			if ("小计".equals(row.getCell("project").getValue())) {
				continue;
			}
			Object value = tblContract.getCell(i, "conNumber").getValue();
			if (value == null) {
				MsgBox.showWarning("已签定合同付款计划第" + (i + 1) + "行合同编码为空！");
				abort();
			} else {
				ContractBillInfo con = (ContractBillInfo) value;
				if (num.contains(con.getId())) {
					MsgBox.showWarning("已签定合同付款计划第" + (i + 1) + "行合同编码重复！");
					abort();
				} else {
					num.add(con.getId());
				}
			}

			for (int j = START_CON; j < tblContract.getColumnCount(); j += 5) {
				String title = (String) tblContract.getHeadRow(0).getCell(j)
						.getValue();
				if (FDCHelper.isEmpty(tblContract.getCell(i, j + 1).getValue())) {
					MsgBox.showWarning("已签定合同付款计划第" + (i + 1) + "行" + title
							+ "计划支付不能为空！");
					abort();
				} else if (FDCHelper.isEmpty(tblContract.getCell(i, j + 2)
						.getValue())) {
					MsgBox.showWarning("已签定合同付款计划第" + (i + 1) + "行" + title
							+ "款项类型不能为空！");
					abort();
				} else if (FDCHelper.isEmpty(tblContract.getCell(i, j + 3)
						.getValue())) {
					MsgBox.showWarning("已签定合同付款计划第" + (i + 1) + "行" + title
							+ "用款说明不能为空！");
					abort();
				}
			}
		}

		// 校验待签订合同计划分录
		num = new HashSet();
		for (int i = 0; i < tblUnsettledCon.getRowCount(); i++) {
			IRow row = tblUnsettledCon.getRow(i);
			if ("小计".equals(row.getCell("project").getValue())) {
				continue;
			}
			if (FDCHelper.isEmpty(tblUnsettledCon.getCell(i, "project")
					.getValue())) {
				MsgBox.showWarning("待签定合同付款计划第" + (i + 1) + "行工程项目不能为空！");
				abort();
			}
			if (FDCHelper.isEmpty(tblUnsettledCon.getCell(i,
					"unsettledConNumber").getValue())) {
				MsgBox.showWarning("待签定合同付款计划第" + (i + 1) + "行待签订合同助记码不能为空！");
				abort();
			} else if (num.contains(tblUnsettledCon.getCell(i,
					"unsettledConNumber").getValue())) {
				MsgBox.showWarning("待签定合同付款计划第" + (i + 1) + "行待签订合同编码重复！");
				abort();
			} else {
				num.add(tblUnsettledCon.getCell(i, "unsettledConNumber")
						.getValue());
			}
			if (FDCHelper.isEmpty(tblUnsettledCon
					.getCell(i, "unsettledConName").getValue())) {
				MsgBox.showWarning("待签定合同付款计划第" + (i + 1) + "行待签订合同名称不能为空！");
				abort();
			} else if (name.contains(tblUnsettledCon.getCell(i,
					"unsettledConName").getValue())) {
				MsgBox.showWarning("待签定合同付款计划第" + (i + 1) + "行待签订合同名称重复！");
				abort();
			} else {
				name.add(tblUnsettledCon.getCell(i, "unsettledConName")
						.getValue());
			}
			if (FDCHelper.isEmpty(tblUnsettledCon.getCell(i,
					"unsettledConestPrice").getValue())) {
				MsgBox.showWarning("待签定合同付款计划第" + (i + 1) + "行预计签约金额不能为空！");
				abort();
			}
			for (int j = START_UNC; j < tblUnsettledCon.getColumnCount(); j += 5) {
				String title = (String) tblUnsettledCon.getHeadRow(0)
						.getCell(j).getValue();
				if (FDCHelper.isEmpty(tblUnsettledCon.getCell(i, j + 1)
						.getValue())) {
					MsgBox.showWarning("待签定合同付款计划第" + (i + 1) + "行" + title
							+ "计划支付不能为空！");
					abort();
				} else if (FDCHelper.isEmpty(tblUnsettledCon.getCell(i, j + 2)
						.getValue())) {
					MsgBox.showWarning("待签定合同付款计划第" + (i + 1) + "行" + title
							+ "款项类型不能为空！");
					abort();
				} else if (FDCHelper.isEmpty(tblUnsettledCon.getCell(i, j + 3)
						.getValue())) {
					MsgBox.showWarning("待签定合同付款计划第" + (i + 1) + "行" + title
							+ "用款说明不能为空！");
					abort();
				}
			}
		}

		// 校验无合同计划分录
		num = new HashSet();
		name = new HashSet();
		for (int i = 0; i < tblNoContract.getRowCount(); i++) {
			IRow row = tblNoContract.getRow(i);
			if ("小计".equals(row.getCell("project").getValue())) {
				continue;
			}
			if (FDCHelper.isEmpty(tblNoContract.getCell(i, "project")
					.getValue())) {
				MsgBox.showWarning("无合同付款计划第" + (i + 1) + "行工程项目不能为空！");
				abort();
			}
			if (FDCHelper.isEmpty(tblNoContract.getCell(i, "payMatters")
					.getValue())) {
				MsgBox.showWarning("无合同付款计划第" + (i + 1) + "行付款事项编码不能为空！");
				abort();
			} else if (num.contains(tblNoContract.getCell(i, "payMatters")
					.getValue())) {
				MsgBox.showWarning("无合同付款计划第" + (i + 1) + "行付款事项编码重复！");
				abort();
			} else {
				num.add(tblNoContract.getCell(i, "payMatters").getValue());
			}

			if (FDCHelper.isEmpty(tblNoContract.getCell(i, "payMattersName")
					.getValue())) {
				MsgBox.showWarning("无合同付款计划第" + (i + 1) + "行付款事项名称不能为空！");
				abort();
			} else if (name.contains(tblNoContract.getCell(i, "payMattersName")
					.getValue())) {
				MsgBox.showWarning("无合同合同付款计划第" + (i + 1) + "行付款事项名称重复！");
				abort();
			} else {
				name.add(tblNoContract.getCell(i, "payMattersName").getValue());
			}

			for (int j = START_NOC; j < tblNoContract.getColumnCount(); j += 5) {
				String title = (String) tblNoContract.getHeadRow(0).getCell(j)
						.getValue();
				if (FDCHelper.isEmpty(tblNoContract.getCell(i, j + 1)
						.getValue())) {
					MsgBox.showWarning("无合同付款计划第" + (i + 1) + "行" + title
							+ "计划支付不能为空！");
					abort();
				} else if (FDCHelper.isEmpty(tblNoContract.getCell(i, j + 2)
						.getValue())) {
					MsgBox.showWarning("无合同付款计划第" + (i + 1) + "行" + title
							+ "款项类型不能为空！");
					abort();
				} else if (FDCHelper.isEmpty(tblNoContract.getCell(i, j + 3)
						.getValue())) {
					MsgBox.showWarning("无合同付款计划第" + (i + 1) + "行" + title
							+ "用款说明不能为空！");
					abort();
				}
			}
		}
		
		//校验该部门该月份下是否已经编制过部门月度滚动付款计划
		AdminOrgUnitInfo admin = (AdminOrgUnitInfo) prmtDeptment.getValue();
		checkHasMonthDepHasPlan(admin, spYear.getIntegerVlaue(), spMonth.getIntegerVlaue());
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		// 工作流可修改最终批复金额，此时不再触发工作流
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if (editData.getId() != null && isFromWorkflow != null
				&& "true".equals(isFromWorkflow.toString())) {
			storeFields();
			getBizInterface().save(editData);
		} else {
			if ((flag_state != null) && (flag_state.equals("revist"))) {
				this.editData.setState(FDCBillStateEnum.REVISING);
				this.editData.setAuditor(SysContext.getSysContext()
						.getCurrentUserInfo());
				this.editData.setAuditTime(auditTime);
				FDCDepConPayPlanBillFactory.getRemoteInstance().save(
						this.editData);

				storeFields();
				this.editData.setState(FDCBillStateEnum.SUBMITTED);
				java.text.DecimalFormat myformat = new java.text.DecimalFormat(
						"#0.0");
				double version = Double.parseDouble(this.editData.getVersion()) + 0.1;
				this.editData.setVersion(myformat.format(version));
				// this.editData.setId(BOSUuid.create("F288954F"));
				// setOprtState(STATUS_ADDNEW);

				clearID();
				loadFields();
				promAuditor.setValue(null);
				picAuditorTime.setValue(null);
				flag_state = null;
			}
			super.actionSubmit_actionPerformed(e);
		}
	}

	/**
	 * 删除
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		getUIContext().put("department",
				editData.getDeptment().castToFullOrgUnitInfo());
		super.actionRemove_actionPerformed(e);
	}

	protected void clearID() {
		editData.setId(null);
		FDCDepConPayPlanContractCollection hasCon = editData.getHasContract();
		for (int i = 0; i < hasCon.size(); i++) {
			hasCon.get(i).setId(null);
			hasCon.get(i).setIsBack(false);
			FDCDepConPayPlanContractEntryCollection entrys = hasCon.get(i)
					.getEntrys();
			for (int j = 0; j < entrys.size(); j++) {
				entrys.get(j).setId(null);
			}
		}
		FDCDepConPayPlanUnsettledConCollection unCon = editData
				.getUnsettledCon();
		for (int i = 0; i < unCon.size(); i++) {
			unCon.get(i).setId(null);
			unCon.get(i).setIsBack(false);
			FDCDepConPayPlanUnsettleEntryCollection entrys = unCon.get(i)
					.getEntrys1();
			for (int j = 0; j < entrys.size(); j++) {
				entrys.get(j).setId(null);
			}
		}
		FDCDepConPayPlanNoContractCollection noCon = editData.getNoContract();
		for (int i = 0; i < noCon.size(); i++) {
			noCon.get(i).setId(null);
			noCon.get(i).setIsBack(false);
			FDCDepConPayPlanNoContractEntryCollection entrys = noCon.get(i)
					.getEntrys1();
			for (int j = 0; j < entrys.size(); j++) {
				entrys.get(j).setId(null);
			}
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		getUIContext().put("department",
				editData.getDeptment().castToFullOrgUnitInfo());
		super.actionAddNew_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
	}

	public void actionViewContract_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.pnlBig.getSelectedIndex() == 0) {
			IRow row = KDTableUtil.getSelectedRow(tblContract);
			if (row == null) {
				MsgBox.showWarning(this, "请先选中行!");
				abort();
			}
			ContractBillInfo con = (ContractBillInfo) row.getCell("conNumber")
					.getValue();
			String id = null;
			if (con != null) {
				id = con.getId().toString();
			}
			if (id == null) {
				MsgBox.showWarning(this, "选中分录合同不存在!");
				abort();
			}

			String uiName = ContractBillEditUI.class.getName();
			Map ctx = new UIContext(this);
			ctx.put(UIContext.ID, id);
			ctx.put(UIContext.OWNER, this);
			IUIWindow uiWindow = null;
			// UIFactoryName.MODEL 为弹出模式
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
					uiName, ctx, null, OprtState.VIEW);
			// 开始展现UI
			uiWindow.show();

		}
	}

	public void actionPublish_actionPerformed(ActionEvent e) throws Exception {
		if (MsgBox.showConfirm2("是否确定上报？") == MsgBox.CANCEL) {
			return;
		}
		if (this.editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
			this.editData.setState(FDCBillStateEnum.PUBLISH);
			String id = getSelectBOID();
			if (id != null) {
				FDCDepConPayPlanBillFactory.getRemoteInstance().setPublish(
						BOSUuid.read(id));
			}
			actionPublish.setEnabled(false);
		}
	}

	public void actionRevise_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getState().equals(FDCBillStateEnum.BACK)) {
			flag_state = "revist";
			if (picAuditorTime.getValue() != null) {
				auditTime = (Date) picAuditorTime.getValue();
			}
			super.actionEdit_actionPerformed(e);
			actionSave.setEnabled(false);
			actionAddNew.setEnabled(false);
			actionRemove.setEnabled(false);
			txtName.setEnabled(false);
			txtNumber.setEnabled(false);
			prmtDeptment.setEnabled(false);
			spMonth.setEnabled(false);
			spYear.setEnabled(false);
			description.setEnabled(false);
			actionRevise.setEnabled(false);
			promAuditor.setValue(null);
			picAuditorTime.setValue(null);
		} else {
			MsgBox.showWarning(this, "被打回的计划才能修订!");
		}
	}

	public void actionIntroPre_actionPerformed(ActionEvent e) throws Exception {
		if (FDCHelper.isEmpty(prmtDeptment.getValue())) {
			MsgBox.showWarning(this, "编制部门不能为空!");
			abort();
		}
		// 取得上月计划值
		FDCDepConPayPlanBillInfo plan = getPreMonthConInfo();
		if (plan == null) {
			MsgBox.showWarning(this, "不存在上一月的已审批或上报的付款计划");
			abort();
		}
		// 填值
		int year = spYear.getIntegerVlaue().intValue();
		int month = spMonth.getIntegerVlaue().intValue();
		month--;
		if (month < 1) {
			year--;
			month = 12;
		}
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1, 0, 0, 0);

		if (this.pnlBig.getSelectedIndex() == 0) {
			FDCDepConPayPlanContractCollection hasCon = plan.getHasContract();
			if (hasCon == null || hasCon.size() < 1) {
				MsgBox.showWarning(this, "上月的付款计划不包含已签订合同付款计划");
			} else {
				for (int i = 0; i < hasCon.size(); i++) {
					FDCDepConPayPlanContractInfo info = hasCon.get(i);
					String conID = info.getContract().getId().toString();
					if (!isHasPlanInTable("contract", conID)) {
						IRow row = tblContract.addRow();
						row.getCell("project").setValue(info.getProject());
						row.getCell("conNumber").setValue(info.getContract());
						row.getCell("conName").setValue(info.getContractName());
						Map values = calConInfo(info.getId().toString());
						row.getCell("conPrice").setValue(
								info.getContractPrice());
						row.getCell("lastMonthPayable").setValue(
								values.get("payable"));
						row.getCell("lastMonthPay").setValue(values.get("pay"));
						row.getCell("lastMonthNopay").setValue(
								values.get("noPay"));
						row.getCell("lastMonthEnRoute").setValue(
								values.get("enRoute"));
						for (int j = 0; j < info.getEntrys().size(); j++) {
							FDCDepConPayPlanContractEntryInfo entry = info
									.getEntrys().get(j);
							if (entry.getMonth().compareTo(cal.getTime()) >= 0) {
								Calendar date = Calendar.getInstance();
								date.setTime(entry.getMonth());
								String keyHead = "MONTH"
										+ date.get(Calendar.YEAR) + ""
										+ (date.get(Calendar.MONTH) + 1);
								row.getCell(keyHead + "plan").setValue(
										entry.getPlanPay());
								row.getCell(keyHead + "define").setValue(
										entry.getMoneyDefine());
								row.getCell(keyHead + "explain").setValue(
										entry.getExplain());
								row.getCell(keyHead + "official").setValue(
										entry.getOfficialPay());
							}
						}
					}
				}
			}
		} else if (this.pnlBig.getSelectedIndex() == 1) {
			FDCDepConPayPlanUnsettledConCollection unCon = plan
					.getUnsettledCon();
			if (unCon == null || unCon.size() < 1) {
				MsgBox.showWarning(this, "上月的付款计划不包含待签订合同付款计划");
			} else {
				for (int i = 0; i < unCon.size(); i++) {
					FDCDepConPayPlanUnsettledConInfo info = unCon.get(i);
					String conNum = info.getUnConNumber();
					if (!isHasPlanInTable("unContract", conNum)) {
						IRow row = tblUnsettledCon.addRow();
						row.getCell("project").setValue(info.getProject());
						row.getCell("unsettledConNumber").setValue(
								info.getUnConNumber());
						row.getCell("unsettledConName").setValue(
								info.getUnConName());
						row.getCell("unsettledConestPrice").setValue(
								info.getPlanAmount());
						for (int j = 0; j < info.getEntrys1().size(); j++) {
							FDCDepConPayPlanUnsettleEntryInfo entry = info
									.getEntrys1().get(j);
							if (entry.getMonth().compareTo(cal.getTime()) >= 0) {
								Calendar date = Calendar.getInstance();
								date.setTime(entry.getMonth());
								String keyHead = "MONTH"
										+ date.get(Calendar.YEAR) + ""
										+ (date.get(Calendar.MONTH) + 1);
								row.getCell(keyHead + "plan").setValue(
										entry.getPlanPay());
								row.getCell(keyHead + "define").setValue(
										entry.getMoneyDefine());
								row.getCell(keyHead + "explain").setValue(
										entry.getExplain());
								row.getCell(keyHead + "official").setValue(
										entry.getOfficialPay());
							}
						}
					}
				}
			}
		} else if (this.pnlBig.getSelectedIndex() == 2) {
			FDCDepConPayPlanNoContractCollection noCon = plan.getNoContract();
			if (noCon == null || noCon.size() < 1) {
				MsgBox.showWarning(this, "上月的付款计划不包含无合同付款计划");
			} else {
				for (int i = 0; i < noCon.size(); i++) {
					FDCDepConPayPlanNoContractInfo info = noCon.get(i);
					String payMatters = info.getPayMatters();
					if (!isHasPlanInTable("noContract", payMatters)) {
						IRow row = tblNoContract.addRow();
						row.getCell("project").setValue(info.getProject());
						row.getCell("payMatters")
								.setValue(info.getPayMatters());
						row.getCell("payMattersName").setValue(
								info.getPayMattersName());
						for (int j = 0; j < info.getEntrys1().size(); j++) {
							FDCDepConPayPlanNoContractEntryInfo entry = info
									.getEntrys1().get(j);
							if (entry.getMonth().compareTo(cal.getTime()) >= 0) {
								Calendar date = Calendar.getInstance();
								date.setTime(entry.getMonth());
								String keyHead = "MONTH"
										+ date.get(Calendar.YEAR) + ""
										+ (date.get(Calendar.MONTH) + 1);
								row.getCell(keyHead + "plan").setValue(
										entry.getPlanPay());
								row.getCell(keyHead + "define").setValue(
										entry.getMoneyDefine());
								row.getCell(keyHead + "explain").setValue(
										entry.getExplain());
								row.getCell(keyHead + "official").setValue(
										entry.getOfficialPay());
							}
						}
					}
				}
			}
		}
		// 删除合计行先(不能只删除最后一行，因为前面循环加行时，合计行不在最后了)
		for (int i = getDetailTable().getRowCount() - 1; i >= 0; i--) {
			IRow totalRow = getDetailTable().getRow(i);
			if (totalRow != null
					&& "小计".equals(totalRow.getCell("project").getValue())) {
				getDetailTable().removeRow(totalRow.getRowIndex());
			}
		}
		addSumRow();
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
		addSumRow();
	}

	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = KDTableUtil.getSelectedRow(getDetailTable());
		if (row == null) {
			MsgBox.showWarning(this, "请先选中行！");
			abort();
		} else if ("小计".equals(row.getCell("project").getValue())) {
			MsgBox.showWarning(this, "不能删除小计行！");
			abort();
		} else {
			super.actionRemoveLine_actionPerformed(e);
			addSumRow();
		}
	}

	protected FDCDepConPayPlanBillInfo getPreMonthConInfo()
			throws BOSException,
			Exception {
		FDCDepConPayPlanBillInfo plan = null;

		int year = spYear.getIntegerVlaue().intValue();
		int month = spMonth.getIntegerVlaue().intValue();
		month--;
		if (month < 1) {
			year--;
			month = 12;
		}
		AdminOrgUnitInfo admin = (AdminOrgUnitInfo) prmtDeptment.getValue();
		EntityViewInfo view = new EntityViewInfo();
		view.setSelector(getSelectors());
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("deptment.id", admin.getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("year", new Integer(year)));
		filter.getFilterItems().add(
				new FilterItemInfo("month", new Integer(month)));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.PUBLISH_VALUE));
		filter.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		view.setFilter(filter);
		FDCDepConPayPlanBillCollection col = ((IFDCDepConPayPlanBill) getBizInterface())
				.getFDCDepConPayPlanBillCollection(view);
		if (col != null && col.size() > 0) {
			plan = col.get(0);
		}

		return plan;
	}

	/**
	 * 根据合同ID计算最新造价、累计完工金额、累计已付工程款、累计未付工程款
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected Map calConInfo(String conID) throws EASBizException, BOSException {
		Map values = new HashMap();
		// 带出数据
		Map param = new HashMap();
		Integer year = (Integer) spYear.getValue();
		String date = "";
		if (month_old.intValue() == 12) {
			date = "{ts '" + (year.intValue() + 1) + "-1-1 00:00:00'}";
		} else {
			date = "{ts '" + year.intValue() + "-" + (month_old.intValue() + 1)
					+ "-1 00:00:00'}";
		}
		String flag = "false";
		if (separteFromPayment) {
			flag = "true";
		}
		param.put("date", date);
		param.put("conID", conID);
		param.put("flag", flag);
		values = FDCDepConPayPlanBillFactory.getRemoteInstance().getPlanPay(
				param);
		// row.getCell("lastMonthPayable").setValue(
		// valuse.get("payable"));
		// row.getCell("lastMonthPay").setValue(valuse.get("pay"));
		// row.getCell("lastMonthNopay").setValue(valuse.get("noPay"));
		// 得到合同最新造价
		Map retVal = FDCUtils.getLastAmt_Batch(null, new String[] { conID });
		BigDecimal conLastestPrice = (BigDecimal) retVal.get(conID);
		values.put("conPrice", conLastestPrice);
		// row.getCell("conPrice").setValue(conLastestPrice);
		return values;
	}

	protected boolean isHasPlanInTable(String table, String key) {
		boolean isHasPlan = false;

		if ("contract".equals(table)) {
			for (int i = 0; i < tblContract.getRowCount(); i++) {
				ContractBillInfo con = (ContractBillInfo) tblContract.getCell(
						i, "conNumber").getValue();
				if (!FDCHelper.isEmpty(con)) {
					String id = con.getId().toString();
					if (id.equals(key)) {
						return true;
					}
				}
			}
		} else if ("unContract".equals(table)) {
			for (int i = 0; i < tblUnsettledCon.getRowCount(); i++) {
				String con = (String) tblUnsettledCon.getCell(i,
						"unsettledConNumber").getValue();
				if (!FDCHelper.isEmpty(con) && con.equals(key)) {
					return true;
				}
			}
		} else if ("noContract".equals(table)) {
			for (int i = 0; i < tblNoContract.getRowCount(); i++) {
				String con = (String) tblNoContract.getCell(i, "payMatters")
						.getValue();
				if (!FDCHelper.isEmpty(con) && con.equals(key)) {
					return true;
				}
			}
		}

		return isHasPlan;
	}

	protected IObjectValue createNewData() {
		FDCDepConPayPlanBillInfo objectValue = new FDCDepConPayPlanBillInfo();

		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setCreateTime(new Timestamp(serverDate.getTime()));

		FullOrgUnitInfo fullOrg = (FullOrgUnitInfo) getUIContext().get(
				"department");
		AdminOrgUnitInfo adminOrg = null;
		if (fullOrg != null && fullOrg.isIsAdminOrgUnit()) {
			try {
				adminOrg = AdminOrgUnitFactory.getRemoteInstance()
						.getAdminOrgUnitInfo(new ObjectUuidPK(fullOrg.getId()));
				objectValue.setDeptment(adminOrg);
			} catch (EASBizException e) {
				handUIException(e);
			} catch (BOSException e) {
				handUIException(e);
			}
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(serverDate);
		while (hasMonthHasPlan(adminOrg, new Integer(cal.get(Calendar.YEAR)),
				new Integer(cal.get(Calendar.MONTH) + 1))) {
			cal.add(Calendar.MONTH, 1);
		}

		objectValue.setYear(cal.get(Calendar.YEAR));
		objectValue.setMonth(cal.get(Calendar.MONTH) + 1);
		
		year_old = new Integer(objectValue.getYear());
		month_old = new Integer(objectValue.getMonth());

		objectValue.setVersion(VERSION);

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("cycle");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		try {
			FDCDataBaseCollection col = PayPlanCycleFactory.getRemoteInstance()
					.getFDCDataBaseCollection(view);
			if (col != null && col.size() > 0) {
				PayPlanCycleInfo info = (PayPlanCycleInfo) col.get(0);
				objectValue.setPayPlanCycle(info);
			} else {
				MsgBox.showWarning(this, "必须启用一套付款计划周期!");
				abort();
			}
		} catch (BOSException e) {
			handUIException(e);
		}

		return objectValue;
	}

	protected void addLine(KDTable table) {
		if (table == null) {
			return;
		}
		int count = table.getRowCount();
		if (count > 0
				&& "小计".equals(table.getCell(count - 1, "project").getValue())) {
			table.addRow(table.getRowCount() - 1);
		} else {
			table.addRow();
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();
	}

	// 责任人是否可以选择其他部门的人员
	private boolean canSelecOtherOrgPerson() {
		boolean canSelectOtherOrgPerson = false;
		try {
			canSelectOtherOrgPerson = FDCUtils.getDefaultFDCParamByKey(null,
					null, FDCConstants.FDC_PARAM_SELECTPERSON);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return canSelectOtherOrgPerson;
	}

	private SelectorItemCollection getSimpleSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("name");
		return sic;
	}

	protected void initDataStatus() {
		super.initDataStatus();
		actionPublish.setEnabled(false);
		actionRevise.setEnabled(false);
		actionIntroPre.setEnabled(false);
		actionAddLine.setEnabled(false);
		actionRemoveLine.setEnabled(false);

		if (!"revist".equals(flag_state)
				&& STATUS_ADDNEW.equals(getOprtState())
				|| STATUS_EDIT.equals(getOprtState())) {
			actionIntroPre.setEnabled(true);
			actionAddLine.setEnabled(true);
			actionRemoveLine.setEnabled(true);
		}

		if (editData != null) {
			if (FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
				actionPublish.setEnabled(true);
			} else if (FDCBillStateEnum.BACK.equals(editData.getState())) {
				actionRevise.setEnabled(true);
				actionEdit.setEnabled(false);
			}
		}
		actionSave.setVisible(true);
	}

	public boolean isModify() {
		return false;
	}

}
