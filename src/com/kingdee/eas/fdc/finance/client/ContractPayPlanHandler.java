package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

public class ContractPayPlanHandler {
	public static String resourcePath = "com.kingdee.eas.fdc.finance.client.PayPlanResource";

	public static void fillTable(String contractId, KDTable table)
			throws BOSException, SQLException {
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		table.removeColumns();
		table.removeRows();
		table.setUserObject(null);
		IColumn iCol = table.addColumn();
		iCol.setKey("id");
		iCol.getStyleAttributes().setHided(true);
		iCol = table.addColumn();
		iCol.setKey("payDate");

		KDDatePicker datePicker = new KDDatePicker();
		datePicker.setRequired(true);
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(datePicker);
		dateEditor.setClickCountToStart(1);
		iCol.setEditor(dateEditor);
//		String formatString = "yyyy-MM-dd";
		String formatString = "yyyy-MM";//涛哥说：改为计划付款月份不显示具体日期
		iCol.getStyleAttributes().setNumberFormat(formatString);
		iCol.getStyleAttributes().setBackground(
				FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);

		iCol = table.addColumn();
		iCol.setKey("payProportion");
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		formattedTextField.setRequired(true);
		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(
				formattedTextField);
		numberEditor.setClickCountToStart(1);
		iCol.setEditor(numberEditor);
		dateEditor.setClickCountToStart(1);
		iCol.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		iCol.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		iCol.getStyleAttributes().setBackground(
				FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);

		// 原币金额
		iCol = table.addColumn();
		iCol.setKey("payOriAmount");
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		formattedTextField.setRequired(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		numberEditor.setClickCountToStart(1);
		iCol.setEditor(numberEditor);
		iCol.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		iCol.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		iCol.getStyleAttributes().setBackground(
				FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);
		iCol.getStyleAttributes().setLocked(true);

		iCol = table.addColumn();
		iCol.setKey("payAmount");
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		formattedTextField.setRequired(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		numberEditor.setClickCountToStart(1);
		iCol.setEditor(numberEditor);
		iCol.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		iCol.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		iCol.getStyleAttributes().setBackground(
				FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);
		
		KDCheckBox isBailAmtCheBox = new KDCheckBox();
		isBailAmtCheBox.setSelected(false);
//		ICellEditor isBailAmtEditor = new KDTDefaultCellEditor(isBailAmtCheBox);
		iCol = table.addColumn();
		iCol.setKey("isBailAmt");
		// iCol.getStyleAttributes().setLocked(false);
//		table.getColumn("isBailAmt").setEditor(isBailAmtEditor);
		iCol.getStyleAttributes().isLocked();

		iCol = table.addColumn();
		iCol.setKey("description");
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		txtEditor.setClickCountToStart(1);
		iCol.setEditor(txtEditor);

		iCol = table.addColumn();
		iCol.setKey("hasPayAmount");
		iCol.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		iCol.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		iCol.getStyleAttributes().setLocked(true);

		IRow headRow = table.addHeadRow();
		headRow.getCell("payDate").setValue("计划付款月份");
//		headRow.getCell("payDate").setValue(
//				ContractPayPlanHandler.getResource("payDate"));
		headRow.getCell("payProportion").setValue(
				ContractPayPlanHandler.getResource("payProportion"));
		headRow.getCell("payOriAmount").setValue("原币金额");
		headRow.getCell("payAmount").setValue(
				ContractPayPlanHandler.getResource("payAmount"));
		headRow.getCell("description").setValue(
				ContractPayPlanHandler.getResource("description"));
		headRow.getCell("isBailAmt").setValue("返还履约保证金");
		headRow.getCell("hasPayAmount").setValue(
				ContractPayPlanHandler.getResource("hasPayAmount"));

		EntityViewInfo view = new EntityViewInfo();
		view.getSorter().add(new SorterItemInfo("seq"));

		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("contractId.id", contractId));

		ContractPayPlanCollection payPlans = ContractPayPlanFactory
				.getRemoteInstance().getContractPayPlanCollection(view);

		for (int i = 0; i < payPlans.size(); i++) {
			ContractPayPlanInfo info = payPlans.get(i);
			IRow row = table.addRow();
			row.setUserObject(info);
			row.getCell("id").setValue(info.getId().toString());
			row.getCell("payDate").setValue(info.getPayDate());
			row.getCell("payProportion").setValue(info.getPayProportion());
			row.getCell("payAmount").setValue(info.getPayAmount());
			row.getCell("payOriAmount").setValue(info.getPayOriAmount());
			row.getCell("description").setValue(info.getDescription());
			row.getCell("isBailAmt").setValue(
					info.isIsBailAmt() == true ? Boolean.TRUE : Boolean.FALSE);// by
																				// Cassiel_peng
			Date date = DateTimeUtils.truncateDate((Date) row
					.getCell("payDate").getValue());

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DATE, 1);
			Date firstDay = DateTimeUtils.truncateDate(cal.getTime());
			if (date.before(firstDay)) {
				row.getStyleAttributes()
						.setBackground(new Color(243, 100, 112));
				row.getStyleAttributes().setLocked(true);
			}
			cal.setTime(date);
			BigDecimal payAmount = getPaymentAmount(contractId, cal
					.get(Calendar.YEAR), cal.get(Calendar.MONTH));
			row.getCell("hasPayAmount").setValue(payAmount);
		}
	}

	public static BigDecimal getPaymentAmount(String contractId, int year,
			int month) throws BOSException, SQLException {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, 1);
		Date beginDate = DateTimeUtils.truncateDate(cal.getTime());
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		Date endDate = DateTimeUtils.truncateDate(cal.getTime());
		return getPaymentAmount(contractId, beginDate, endDate,false);
	}

	public static BigDecimal getPaymentAmount(String contractId,
			Date beginDate, Date endDate,boolean isGetPrjAmtInContract) throws BOSException, SQLException {
		if(isGetPrjAmtInContract){
			//在ContractPayPlanEditUI.java的Verify方法中进行校验"当前月之前的合同内工程款实付金额加本月及之后月份的计划金额大于合同最新造价，是否继续保存？"时
			//的工程款实付金额取合同下本月之前已付款的付款单上的合同内工程款。
			BigDecimal projectPriceInContractAmt=FDCHelper.ZERO;
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select MONTH(fpaydate),fprojectPriceInContract from t_cas_paymentbill \n ");
			builder.appendSql("where MONTH(fpaydate)<MONTH(CURDATE()) and fcontractbillid=?  and FBillStatus=15 \n");
			builder.addParam(contractId);
			IRowSet rowSet=builder.executeQuery();
			while(rowSet.next()){
				BigDecimal tempAmount=FDCHelper.toBigDecimal(rowSet.getBigDecimal("fprojectPriceInContract"));
				projectPriceInContractAmt=FDCHelper.add(projectPriceInContractAmt, tempAmount);
			}
			return projectPriceInContractAmt;
			//这个是取的合同下所有已付款的付款单上的合同内工程款，未按月份过滤.
			/*SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("prjPriceInConPaid");
			ContractBillInfo contractBill=null;
			try {
				contractBill = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)),selector);
			} catch (Exception e) {
				e.printStackTrace();
			}
			BigDecimal amount = null;
			if(contractBill!=null){
				amount=FDCHelper.toBigDecimal(contractBill.getPrjPriceInConPaid(),2);
			}
			return amount;*/
		}else{
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("localAmt");
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("contractBillId", contractId));
			if (beginDate != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("bizDate", beginDate,
								CompareType.GREATER_EQUALS));
			}
			filter.getFilterItems().add(
					new FilterItemInfo("bizDate", endDate, CompareType.LESS));
			filter.getFilterItems().add(
					new FilterItemInfo("billStatus", new Integer(
							BillStatusEnum.PAYED_VALUE)));
			PaymentBillCollection payRequestBillCollection = PaymentBillFactory
			.getRemoteInstance().getPaymentBillCollection(view);
			BigDecimal amount = null;
			for (int i = 0; i < payRequestBillCollection.size(); i++) {
				PaymentBillInfo info = payRequestBillCollection.get(i);
				if (amount == null) {
					amount = FDCHelper.ZERO;
				}
				amount = amount.add(info.getLocalAmt());
			}
			
			return amount;
		}
		
		
	}

	public static String getResource(String resourceName) {
		return EASResource.getString(resourcePath, resourceName);
	}
}
