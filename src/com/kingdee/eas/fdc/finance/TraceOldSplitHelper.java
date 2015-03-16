package com.kingdee.eas.fdc.finance;

import java.math.BigDecimal;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;

/**
 * 重新优化了此类，此类只作为中间类，共
 * @author xiaohong_shi
 *
 */
public class TraceOldSplitHelper {
	public static void autoSplit(
			PaymentSplitEntryCollection collection, BigDecimal totalAmt,
			boolean hasSettleSplit) {
//		客户端与服务器端统一用一套代码
		FDCSplitBillEntryCollection entrys=(FDCSplitBillEntryCollection)collection.cast(FDCSplitBillEntryCollection.class);
		PaymentAutoSplitHelper.autoSplit(entrys, totalAmt);
	}

	public static void autoSplitAmt(
			PaymentSplitEntryCollection collection, BigDecimal totalAmt,
			boolean hasSettleSplit, boolean hasSettlePayed, boolean isSettle,
			boolean isKeepAmt,boolean isAdjustModel) {
		FDCSplitBillEntryCollection entrys=(FDCSplitBillEntryCollection)collection.cast(FDCSplitBillEntryCollection.class);
		PaymentAutoSplitHelper.autoSplitAmt(entrys, totalAmt, hasSettleSplit, hasSettlePayed, isSettle, isKeepAmt, isAdjustModel);
	}
	
	public static void autoNoCostSplit(PaymentNoCostSplitEntryCollection collection,BigDecimal totalAmt,boolean hasSettleSplit){
//		客户端与服务器端统一用一套代码
		FDCNoCostSplitBillEntryCollection entrys=(FDCNoCostSplitBillEntryCollection)collection.cast(FDCNoCostSplitBillEntryCollection.class);
		PaymentNoCostAutoSplitHelper.autoSplit(entrys, totalAmt, hasSettleSplit);
	}

	public static void autoNoCostSplitAmt(PaymentNoCostSplitEntryCollection collection,BigDecimal totalAmt,
			boolean hasSettleSplit, boolean hasSettlePayed, boolean isSettle,
			boolean isKeepAmt,boolean isAdjustModel) {
//		客户端与服务器端统一用一套代码
		FDCNoCostSplitBillEntryCollection entrys=(FDCNoCostSplitBillEntryCollection)collection.cast(FDCNoCostSplitBillEntryCollection.class);
		PaymentNoCostAutoSplitHelper.autoSplitAmt(entrys, totalAmt, hasSettleSplit, hasSettlePayed, isSettle, isKeepAmt, isAdjustModel);
	}
	
	public static void autoSplitInvoiceAmt(
			PaymentSplitEntryCollection collection, BigDecimal totalInvoiceAmt) {
		//客户端与服务器端统一用一套代码
		FDCSplitBillEntryCollection entrys=(FDCSplitBillEntryCollection)collection.cast(FDCSplitBillEntryCollection.class);
		PaymentAutoSplitHelper.autoSplitInvoiceAmt(entrys, totalInvoiceAmt);
	}
	
	//按比例拆分成本金额
	public static void splitCostAmtBaseOnProp(PaymentSplitEntryCollection entrys, Map costMap){
		PaymentAutoSplitHelper.splitCostAmtBaseOnProp((FDCSplitBillEntryCollection) entrys.cast(FDCSplitBillEntryCollection.class), costMap);
	}
	
	//按比例拆分付款金额
	public static void splitPayedAmtBaseOnProp(PaymentSplitEntryCollection entrys, Map payedMap) throws BOSException{
		PaymentAutoSplitHelper.splitPayedAmtBaseOnProp((FDCSplitBillEntryCollection) entrys.cast(FDCSplitBillEntryCollection.class), payedMap);
	}
	
	//按比例拆分发票金额
	public static void splitInvoiceAmtBaseOnProp(PaymentSplitEntryCollection entrys, Map invoiceMap){
		PaymentAutoSplitHelper.splitInvoiceAmtBaseOnProp((FDCSplitBillEntryCollection) entrys.cast(FDCSplitBillEntryCollection.class), invoiceMap);
	}
	
	//非成本类：按比例拆分成本金额
	public static void splitCostAmtBaseOnProp(PaymentNoCostSplitEntryCollection entrys, Map costMap){
		PaymentNoCostAutoSplitHelper.splitCostAmtBaseOnProp((FDCNoCostSplitBillEntryCollection) entrys.cast(FDCNoCostSplitBillEntryCollection.class), costMap);
	}
	
	//非成本类：按比例拆分付款金额
	public static void splitPayedAmtBaseOnProp(PaymentNoCostSplitEntryCollection entrys, Map payedMap) throws BOSException{
		PaymentNoCostAutoSplitHelper.splitPayedAmtBaseOnProp((FDCNoCostSplitBillEntryCollection) entrys.cast(FDCNoCostSplitBillEntryCollection.class), payedMap);
	}
}
