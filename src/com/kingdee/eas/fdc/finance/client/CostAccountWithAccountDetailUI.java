/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.finance.FDCAdjustBillCollection;
import com.kingdee.eas.fdc.finance.FDCAdjustBillFactory;
import com.kingdee.eas.fdc.finance.FDCAdjustBillInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.gl.AccountBalanceCollection;
import com.kingdee.eas.fi.gl.AccountBalanceFactory;
import com.kingdee.eas.fi.gl.AccountBalanceInfo;
import com.kingdee.eas.fi.gl.EntryDC;
import com.kingdee.eas.fi.gl.VoucherCollection;
import com.kingdee.eas.fi.gl.VoucherEntryCollection;
import com.kingdee.eas.fi.gl.VoucherEntryFactory;
import com.kingdee.eas.fi.gl.VoucherEntryInfo;
import com.kingdee.eas.fi.gl.VoucherFactory;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.fi.gl.VoucherStatusEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class CostAccountWithAccountDetailUI extends AbstractCostAccountWithAccountDetailUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostAccountWithAccountDetailUI.class);
    
    /**
     * output class constructor
     */
    public CostAccountWithAccountDetailUI() throws Exception
    {
        super();
    }

	protected String getEditUIName() {
		// TODO 自动生成方法存根
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return null;
	}

	public void onLoad() throws Exception {
		// TODO 自动生成方法存根
		super.onLoad();
		fillTable();
		setQueryPreference(true);
        this.actionAddNew.setEnabled(false);
        this.actionAddNew.setVisible(false);
        this.actionEdit.setEnabled(false);
        this.actionEdit.setVisible(false);
        this.actionAttachment.setVisible(false);
        this.actionAttachment.setEnabled(false);
        this.actionRemove.setEnabled(false);
        this.actionRemove.setVisible(false);
        this.actionRefresh.setEnabled(false);
        this.actionRefresh.setVisible(false);
        this.actionView.setVisible(false);
        this.actionView.setEnabled(false);
        this.actionQuery.setEnabled(false);
        this.actionQuery.setVisible(false);
        this.actionLocate.setEnabled(false);
        this.actionLocate.setVisible(false);
	}
	private void fillTable() throws BOSException{
		/****
		 * TODO
		 * 需要把代码移植到服务器端
		 */
		KDTable table = this.getMainTable();
		//更改为实模式，否则无法选中且无法打印。
		table.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		table.removeRows();
		table.checkParsed();
		if(getUIContext().get("accountid")==null)
			return;
		String accountid = (String)getUIContext().get("accountid");
		Integer periodYear = (Integer)getUIContext().get("periodYear");
		Integer periodNumber = (Integer)getUIContext().get("periodNumber");
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		/**
		 * 废除原有取数方式，直接从总账取数，减少rpc交互次数和数据量 2009-06-11
		 */
		
//		filter.getFilterItems().add(new FilterItemInfo("account.id",accountid,CompareType.EQUALS));
//		filter.getFilterItems().add(new FilterItemInfo("bill.period.periodYear",periodYear,CompareType.EQUALS));
//		filter.getFilterItems().add(new FilterItemInfo("bill.period.periodNumber",periodNumber,CompareType.EQUALS));
//		Set statusSet = new HashSet();
//		//只取 提交 审批和已过账的数据
//		statusSet.add(new Integer(VoucherStatusEnum.AUDITTED_VALUE));
//		statusSet.add(new Integer(VoucherStatusEnum.SUBMITTED_VALUE));
//		statusSet.add(new Integer(VoucherStatusEnum.POSTED_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("bill.bizStatus",statusSet,CompareType.INCLUDE));
//		view.getSelector().add("id");
//		view.getSelector().add("entryDC");
//		view.getSelector().add("localAmount");
//		view.getSelector().add("bill.id");
//		view.getSelector().add("bill.sourceBillId");
//		view.getSelector().add("bill.number");
//		view.getSelector().add("bill.bookedDate");		
//		view.getSelector().add("description");
//		SorterItemInfo sorterItemInfo = new SorterItemInfo();
//		sorterItemInfo.setPropertyName("bill.createTime");
//		view.getSorter().add(sorterItemInfo);
//		
//		VoucherEntryCollection voucherEntryColls = VoucherEntryFactory.getRemoteInstance().getVoucherEntryCollection(view);
		
//		/**
//		 * 取余额
//		 */
		String orgUnitId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		String currencyId = null;
		try {
			currencyId = FDCHelper.getBaseCurrency(null).getId().toString();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
//		view.getFilter().getFilterItems().clear();
//		view.getSelector().clear();
//		view.getSelector().add("id");
//		view.getSelector().add("account.id");		
//		view.getSelector().add("debitLocal");
//		view.getSelector().add("creditLocal");
//		view.getSelector().add("endBalanceLocal");
//		view.getFilter().getFilterItems().add(new FilterItemInfo("orgUnit",orgUnitId,CompareType.EQUALS));
//		view.getFilter().getFilterItems().add(new FilterItemInfo("account.id",accountid,CompareType.INCLUDE));
//		view.getFilter().getFilterItems().add(new FilterItemInfo("period.periodYear",periodYear,CompareType.EQUALS));
//		view.getFilter().getFilterItems().add(new FilterItemInfo("period.periodNumber",periodNumber,CompareType.EQUALS));
//		view.getFilter().getFilterItems().add(new FilterItemInfo("currency.id",currencyId,CompareType.EQUALS));
//		AccountBalanceCollection accountBalances = AccountBalanceFactory.getRemoteInstance().getCollection(view);
//		BigDecimal accountBalance = FDCHelper.ZERO;
//		for(Iterator it = accountBalances.iterator();it.hasNext();){
//			AccountBalanceInfo accountBalanceInfo = (AccountBalanceInfo)it.next();
//			accountBalance = accountBalanceInfo.getEndBalanceLocal();
//		}
		/**
		 * 取总账明细分类账数据
		 */
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" SELECT TV.FBookedDate,TV.FSourceBillId,TVE.FEntryDC,TV.FBizDate,TV.FNumber as vNumber,TVT.fnumber,TVT.FName_l2,TV.FCREATETIME,TVE.FSeq,TVE.FDescription, \r\n");
		builder.appendSql("	(CASE WHEN TVE.FEntryDC=1 THEN TVE.FOriginalAmount ELSE 0 END) FDebitFor ,\r\n");
		builder.appendSql("	(CASE WHEN TVE.FEntryDC=0 THEN TVE.FOriginalAmount ELSE 0 END) FCreditFor ,\r\n");
		builder.appendSql(" TVE.FLocalExchangeRate FLocalRate, \r\n");
		builder.appendSql(" TVE.FReportingExchangeRate FRptRate, \r\n");
		builder.appendSql("	(CASE WHEN TVE.FEntryDC=1 THEN TVE.FLocalAmount ELSE 0 END) FDebitLocal ,\r\n");
		builder.appendSql("	(CASE WHEN TVE.FEntryDC=0 THEN TVE.FLocalAmount ELSE 0 END) FCreditLocal ,\r\n");
		builder.appendSql("	(CASE WHEN TVE.FEntryDC=1 THEN TVE.FReportingAmount ELSE 0 END) FDebitRpt ,\r\n");
		builder.appendSql("	(CASE WHEN TVE.FEntryDC=0 THEN TVE.FReportingAmount ELSE 0 END) FCreditRpt ,\r\n");
		builder.appendSql("CASE WHEN MUG.FDefaultunitID IS NULL THEN AV.FMeasureUnitID ELSE MUG.FDefaultunitID END FBalanceUnitId, \r\n");
		builder.appendSql("	0 FLineType,TV.FID FVoucherID, \r\n");
		builder.appendSql("	TP.FPeriodYear  FPeriodYear,TP.FPeriodNumber FPeriodNumber \r\n");
		builder.appendSql("FROM T_GL_Voucher TV \r\n");
		builder.appendSql("	INNER JOIN T_BD_VoucherTypes TVT \r\n");
		builder.appendSql(" 	ON TV.FVoucherTypeID = TVT.FID \r\n");
		builder.appendSql("	INNER JOIN T_GL_VoucherEntry TVE \r\n");
		builder.appendSql("	ON TV.FID = TVE.FBillID \r\n");
		builder.appendSql("	INNER JOIN T_BD_Period TP \r\n");
		builder.appendSql("	ON TV.FPeriodID = TP.FID \r\n");
		builder.appendSql("	INNER JOIN T_BD_AccountView AV \r\n");
		builder.appendSql("	ON TVE.FAccountID =AV.FID \r\n");
		builder.appendSql("	LEFT JOIN t_bd_measureunitgroup MUG ON AV.FMeasureUnitGroupID=MUG.fid \r\n");
		builder.appendSql(" WHERE TV.FCompanyID = ? AND \r\n");
		builder.appendSql("    (TV.FBizStatus =   5  \r\n");
		builder.appendSql("    OR TV.FBizStatus =   1 \r\n");
		builder.appendSql("    OR TV.FBizStatus =   3  \r\n");
		builder.appendSql(") AND \r\n");
		builder.appendSql("	TVE.FAccountID = ? AND \r\n");
		builder.appendSql(" TVE.FCurrencyID = ? AND \r\n ");
		builder.appendSql("	TP.FNumber>= ? AND \r\n");
		builder.appendSql("	TP.FNumber<= ?   ORDER BY TV.FCreateTime ASC,TVE.FEntryDC DESC  \r\n");
		builder.addParam(orgUnitId);
		builder.addParam(accountid);
		builder.addParam(currencyId);
		String number = "";
		if(periodNumber.intValue() < 10){
			number = periodYear +"0"+periodNumber;
		}else{
			number = periodYear.toString() + periodNumber.toString();
		}
		builder.addParam(number);
		builder.addParam(number);
		IRowSet rs = builder.executeQuery();
//		Map map = new HashMap();
		BigDecimal amt = FDCHelper.ZERO;
		Map sourceBillIdNumber = new HashMap();
		try {
			while(rs.next()){
				String vNumber = rs.getString("vNumber");
				BigDecimal temp = rs.getBigDecimal("FDebitLocal");
				BigDecimal tempCr = rs.getBigDecimal("FCreditLocal");
				Integer entryDC = new Integer(rs.getInt("FEntryDC"));
				amt = FDCHelper.subtract(FDCHelper.add(amt, temp),tempCr);
//				//与可能是同一个凭证，但借贷方向不同（都在同一个科目上），故key再加上借贷方向以区别
//				if(!map.containsKey(vNumber+entryDC)){
//					map.put(vNumber+entryDC, amt);
//				}
				IRow iRow = table.addRow();
				String key = rs.getString("FSourceBillId");
				String remark = rs.getString("FDescription");
				Date bookedDate = rs.getDate("FBookedDate");
				if(key != null){
					BOSUuid.read(key).getType();
					
					sourceBillIdNumber.put(key,iRow);
				}
				iRow.getCell("voucherNumber").setValue(vNumber);
				iRow.getCell("bizDate").setValue(bookedDate);
				iRow.getCell("remark").setValue(remark);
				iRow.getCell("amount").setValue(amt);
				if(entryDC.intValue() == 0)
					iRow.getCell("credit").setValue(tempCr);
				else
					iRow.getCell("debit").setValue(temp);
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			MsgBox.showError("余额数据获取错误！");
			SysUtil.abort();
		}
		
		
//		for(Iterator it=voucherEntryColls.iterator();it.hasNext();){
//			VoucherEntryInfo entry = (VoucherEntryInfo) it.next();
//			IRow iRow = table.addRow();
//			String key = entry.getBill().getSourceBillId();
//			if(key != null){
//				BOSUuid.read(key).getType();
//				
//				sourceBillIdNumber.put(key,iRow);
//			}
//			iRow.getCell("voucherNumber").setValue(entry.getBill().getNumber());
//			iRow.getCell("bizDate").setValue(entry.getBill().getBookedDate());
//			iRow.getCell("remark").setValue(entry.getDescription());
//			Integer entryDC = new Integer(entry.getEntryDC().getValue());
//			iRow.getCell("amount").setValue(map.get(entry.getBill().getNumber()+entryDC));
//			if(entry.getEntryDC().equals(EntryDC.CREDIT))
//				iRow.getCell("credit").setValue(entry.getLocalAmount());
//			else
//				iRow.getCell("debit").setValue(entry.getLocalAmount());
//		
//		}
		if(sourceBillIdNumber.size() > 0){
			//Map.keySet()无法序列化
			Set billIds = new HashSet(sourceBillIdNumber.keySet());
//			view.getFilter().getFilterItems().clear();
//			view.getSelector().clear();
//			view.getSorter().clear();
			view.getFilter().getFilterItems().add(new FilterItemInfo("id",billIds,CompareType.INCLUDE));
			view.getSelector().add("id");
			view.getSelector().add("number");
			view.getSelector().add("name");
			PaymentBillCollection paymentBills = PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(view);
			for(Iterator it=paymentBills.iterator();it.hasNext();){
				PaymentBillInfo paymentbill = (PaymentBillInfo) it.next();
				String key = paymentbill.getId().toString();
				if(sourceBillIdNumber.containsKey(key)&&sourceBillIdNumber.get(key)!=null){
					IRow iRow = (IRow)sourceBillIdNumber.get(key);
					iRow.getCell("billNumber").setValue(paymentbill.getNumber());
					iRow.getCell("billType").setValue("付款单");
				}
			}
			view.getFilter().getFilterItems().clear();
			view.getSelector().clear();
			view.getFilter().getFilterItems().add(new FilterItemInfo("id",billIds,CompareType.INCLUDE));
			view.getSelector().add("id");
			view.getSelector().add("paymentbill.number");
			view.getSelector().add("paymentbill.name");
			PaymentSplitCollection paymentSplits = PaymentSplitFactory.getRemoteInstance().getPaymentSplitCollection(view);
			for(Iterator it=paymentSplits.iterator();it.hasNext();){
				PaymentSplitInfo paymentSplit = (PaymentSplitInfo) it.next();
				String key = paymentSplit.getId().toString();
				if(sourceBillIdNumber.containsKey(key)&&sourceBillIdNumber.get(key)!=null){
					IRow iRow = (IRow)sourceBillIdNumber.get(key);
					iRow.getCell("billNumber").setValue(paymentSplit.getPaymentBill().getNumber());
					iRow.getCell("billType").setValue("付款拆分");
				}
			}
			view.getFilter().getFilterItems().clear();
			view.getSelector().clear();
			view.getSorter().clear();
			view.getFilter().getFilterItems().add(new FilterItemInfo("id",billIds,CompareType.INCLUDE));
			view.getSelector().add("id");
			view.getSelector().add("number");
			FDCAdjustBillCollection adjustBills = FDCAdjustBillFactory.getRemoteInstance().getFDCAdjustBillCollection(view);
			for(Iterator it=adjustBills.iterator();it.hasNext();){
				FDCAdjustBillInfo adjustBill = (FDCAdjustBillInfo) it.next();
				String key = adjustBill.getId().toString();
				if(sourceBillIdNumber.containsKey(key)&&sourceBillIdNumber.get(key)!=null){
					IRow iRow = (IRow)sourceBillIdNumber.get(key);
					iRow.getCell("billNumber").setValue(adjustBill.getNumber());
					iRow.getCell("billType").setValue("调整单");
				}
			}
		}
	}
	//重载父类方法
	protected String getKeyFieldName() {
		return "billNumber";
	}
	//不需要此事件，重载掉
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		
	}
	
	

}