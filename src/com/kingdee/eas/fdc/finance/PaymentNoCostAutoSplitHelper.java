package com.kingdee.eas.fdc.finance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.util.backport.Collections;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo;
/**
 * 之前这个类里封装的都是处理(非)成本类付款拆分界面上点击“自动匹配拆分”按钮应该处理的逻辑 by sxhong
 * 现在这个类中封装非成本类付款拆分界面上点击“按比例拆分”按钮应该处理的算法逻辑  by cassiel_peng  2010-01-19
 */
public class PaymentNoCostAutoSplitHelper {
	public static void autoSplit(FDCNoCostSplitBillEntryCollection entrys,BigDecimal totalAmt,boolean hasSettleSplit){
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			PaymentNoCostSplitEntryInfo item = (PaymentNoCostSplitEntryInfo) iter.next();
			item.setAmount(FDCHelper.ZERO);
			if(item.getLevel()<0||!item.isIsLeaf()) {
			}else{
				BigDecimal amount=FDCHelper.ZERO;
				if(item.getSplitedCostAmt()!=null)
					amount=item.getCostAmt().subtract(item.getSplitedCostAmt());
				else
					amount=item.getCostAmt();
				item.put("distributeAmt", amount);
			}
			item.setDirectAmt(null);
		}
		innerDistributeAmt(entrys, totalAmt,"amount");
	}

	
	public static void autoSplitAmt(
			FDCNoCostSplitBillEntryCollection entrys, BigDecimal totalAmt,
			boolean hasSettleSplit, boolean hasSettlePayed, boolean isSettle,
			boolean isKeepAmt,boolean isAdjustModel) {
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			PaymentNoCostSplitEntryInfo item = (PaymentNoCostSplitEntryInfo) iter.next();
			item.setPayedAmt(FDCHelper.ZERO);
			if (item.getLevel() < 0 || !item.isIsLeaf()) {
				
			} else {
				if (isSettle) {
					BigDecimal amount = FDCHelper.ZERO;
					amount = FDCHelper.toBigDecimal(item.getShouldPayAmt());
					if(isAdjustModel){
						amount=FDCHelper.subtract(amount, item.getSplitedPayedAmt());
					}
					item.put("distributeAmt", amount);
				} else if (isKeepAmt) {
					BigDecimal amount = FDCHelper.ZERO;
					// if(item.getSplitedPayedAmt().compareTo(item.getShouldPayAmt())==1){
					// amount =
					// item.getCostAmt().subtract(item.getSplitedPayedAmt());
					// }else{
					amount = FDCHelper.toBigDecimal(item.getCostAmt()).subtract(FDCHelper.toBigDecimal(item.getShouldPayAmt())).subtract(FDCHelper.toBigDecimal(item.getSplitQualityAmt()));
					// }
					item.put("distributeAmt", amount);
				} else if (hasSettlePayed) {
					BigDecimal amount = FDCHelper.ZERO;
					amount = item.getShouldPayAmt().subtract(item.getSplitedPayedAmt());
					item.put("distributeAmt", amount);
				} else {
					BigDecimal amount = FDCHelper.ZERO;
					amount = FDCHelper.toBigDecimal(item.getSplitedCostAmt()).add(FDCHelper.toBigDecimal(item.getAmount())).subtract(FDCHelper.toBigDecimal(item.getSplitedPayedAmt()));
					item.put("distributeAmt", amount);
				}
			}
			item.setDirectAmt(null);
		}		
		innerDistributeAmt(entrys, totalAmt, "payedAmt");
	}
	
	/**
	 * 简单发票模式
	 * 自动匹配付款金额
	 * @param entrys
	 * @param totalInvoiceAmt
	 * @return
	 */
	public static void autoSplitSimpleInvoicePayAmt(FDCNoCostSplitBillEntryCollection entrys, BigDecimal totalInvoiceAmt) {
		String prop="payedAmt";
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			PaymentNoCostSplitEntryInfo item = (PaymentNoCostSplitEntryInfo) iter.next();
			item.setBigDecimal(prop, FDCHelper.ZERO);
			if (item.getLevel() < 0 || !item.isIsLeaf()) {
				continue;
			}
			BigDecimal amount = FDCHelper.subtract(item.getCostAmt(),item.getSplitedPayedAmt());
			//设置待分配金额
			item.put("distributeAmt", amount);
			item.setDirectAmt(null);
		}
		innerDistributeAmt(entrys, totalInvoiceAmt,prop);
	}
	
	/**
	 * 自动匹配发票金额
	 * @param entrys
	 * @param totalInvoiceAmt
	 * @return
	 */
	public static void autoSplitSimpleInvoiceInvoiceAmt(FDCNoCostSplitBillEntryCollection entrys, BigDecimal totalInvoiceAmt) {
		String prop="invoiceAmt";
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			PaymentNoCostSplitEntryInfo item = (PaymentNoCostSplitEntryInfo) iter.next();
			item.setBigDecimal(prop, FDCHelper.ZERO);
			if (item.getLevel() < 0 || !item.isIsLeaf()) {
				continue;
			}
			BigDecimal amount = FDCHelper.subtract(item.getCostAmt(),item.getSplitedInvoiceAmt());
			//设置待分配金额
			item.put("distributeAmt", amount);
			item.setDirectAmt(null);
		}
		innerDistributeAmt(entrys, totalInvoiceAmt,prop);

	}
	/**
	 * 自动匹配发票金额
	 * @param entrys
	 * @param totalInvoiceAmt
	 * @return
	 */
	public static void autoSplitInvoiceAmt(FDCNoCostSplitBillEntryCollection entrys, BigDecimal totalInvoiceAmt) {
		String prop="invoiceAmt";
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			PaymentNoCostSplitEntryInfo item = (PaymentNoCostSplitEntryInfo) iter.next();
			item.setBigDecimal(prop, FDCHelper.ZERO);
			if (item.getLevel() < 0 || !item.isIsLeaf()) {
				continue;
			}
			BigDecimal amount = FDCHelper.subtract(item.getCostAmt(),item.getSplitedInvoiceAmt());
			//设置待分配金额
			item.put("distributeAmt", amount);
			item.setDirectAmt(null);
		}
		innerDistributeAmt(entrys, totalInvoiceAmt,prop);

	}
	
	
	/**
	 * 使用自动分配算法，将金额分配到各个明细项
	 * @param entrys	分录集,分录集里面存在distributeAmt 用于保存待每一个分录项要分配的金额
	 * @param totalAmt	待分配的总金额
	 * @param prop		要进行分配的属性名称，如：amount，payedAmt，invoiceAmt等
	 *  by sxhong 2009-07-21 13:43:56
	 */
	private static void innerDistributeAmt(FDCNoCostSplitBillEntryCollection entrys, BigDecimal totalAmt,String prop) {
		if(totalAmt==null||totalAmt.signum()==0){
			return;
		}
		
		final List sortList = AutoNoCostSplitSortor.sort(entrys);
		FDCNoCostSplitBillEntryInfo item=null;
		for (Iterator iter = sortList.iterator(); iter.hasNext();) {
			if(totalAmt.signum()==0){
				break;
			}
			
			item = (FDCNoCostSplitBillEntryInfo) iter.next();
			boolean debug=true;
			if(debug){
				System.out.print(prop+" 工程项目编码："+item.getCurProject().getLongNumber());
				System.out.print("工程项目序号："+item.getCurProject().getSortNo());
				System.out.print("\t科目编码："+item.getAccountView().getLongNumber());
				System.out.print("\tseq："+item.getSeq());				
				System.out.println("\tTotal:"+totalAmt+"\t amount:"+item.get("distributeAmt"));
			}
			BigDecimal distributeAmt=FDCHelper.toBigDecimal(item.get("distributeAmt"));
			if(totalAmt.compareTo(distributeAmt)>0){
				item.setBigDecimal(prop, distributeAmt);
				totalAmt=totalAmt.subtract(distributeAmt);
			}else{
				item.setBigDecimal(prop, totalAmt);
				totalAmt=FDCHelper.ZERO;
			}		
		}
		if(item!=null){
			//多余的放置到最后一项
			if(totalAmt.compareTo(FDCHelper.ZERO)!=0){
				item.setBigDecimal(prop, FDCHelper.add(item.getBigDecimal(prop), totalAmt));
			}
		}
	}
	/**
	 * 看代码看不懂的话可以去看看
	 * void com.kingdee.eas.fdc.finance.PaymentAutoSplitHelper.splitCostAmtBaseOnProp(FDCSplitBillEntryCollection entrys, Map map)
	 * 方法里边的注释
	 * by cassiel_peng 2010-01-19 
	 */
	public static void splitCostAmtBaseOnProp(FDCNoCostSplitBillEntryCollection entrys, Map map){
		/**
		 * 归属成本金额
		 */
		BigDecimal headCostAmt = FDCHelper.toBigDecimal(map.get("headCostAmt"),2);//表头的本期成本金额
		BigDecimal hadSplitCostAmtSum = FDCHelper.toBigDecimal(map.get("hadSplitCostAmtSum"),2);//已拆分成本金额各明细行合计
		BigDecimal splitCostAmtSum = FDCHelper.toBigDecimal(map.get("splitCostAmtSum"),2);//成本拆分金额各明细行合计
		PaymentNoCostSplitEntryInfo item = null;
		final List sortList = AutoNoCostSplitSortor.filterExceptProductSplit(entrys);
		BigDecimal _tempTotalAmt = headCostAmt;
		BigDecimal tempTotalAmt = FDCHelper.ZERO;
		Boolean hasValue = null;
		List judgeLastProductSplit = null;
		Map tempMap = new HashMap();
		for(Iterator iter = sortList.iterator();iter.hasNext();){
			if(headCostAmt.compareTo(FDCHelper.ZERO)==0||headCostAmt.signum()==0){
				break;
			}
			item = (PaymentNoCostSplitEntryInfo) iter.next();
			if(CostSplitTypeEnum.PRODSPLIT.equals(item.getSplitType())){//产品拆分
				if(!item.isIsLeaf()){//是非明细节点
					hasValue = Boolean.FALSE;
					tempMap.put(item.getAccountView().getId().toString()+"_"+new Integer(item.getLevel()).toString(), item);//成本科目+级次作为建
					
					judgeLastProductSplit = new ArrayList();
					for(Iterator _iter = sortList.iterator();_iter.hasNext();){
						PaymentNoCostSplitEntryInfo splitEntry = (PaymentNoCostSplitEntryInfo)_iter.next();
						if(item.getAccountView().getId().toString().equals(splitEntry.getAccountView().getId().toString())&&CostSplitTypeEnum.PRODSPLIT.equals(splitEntry.getSplitType())&&splitEntry.isIsLeaf()){
							judgeLastProductSplit.add(new Integer(splitEntry.getSeq()));
						}
					}
					Collections.sort(judgeLastProductSplit);
				}else{//明细节点
					PaymentNoCostSplitEntryInfo currentProductFloorEntry = (PaymentNoCostSplitEntryInfo)tempMap.get(item.getAccountView().getId().toString()+"_"+new Integer(item.getLevel()-1).toString());
					if(currentProductFloorEntry!=null){
						BigDecimal tempCostSplitAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("costAmt"));
						BigDecimal distributeAmt = FDCHelper.divide(FDCHelper.multiply(item.getBigDecimal("costAmt"), currentProductFloorEntry.getBigDecimal("amount")), tempCostSplitAmt);
						if(hasValue!=null&&!hasValue.booleanValue()){
							tempTotalAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("amount"),2);
						}
						hasValue = Boolean.TRUE;
						if (tempTotalAmt.compareTo(distributeAmt) > 0) {
							item.setBigDecimal("amount", distributeAmt);
							tempTotalAmt = tempTotalAmt.subtract(distributeAmt);
						} else {
							item.setBigDecimal("amount", tempTotalAmt);
							tempTotalAmt = FDCHelper.ZERO;
						}
						if(item!=null&&judgeLastProductSplit.get(judgeLastProductSplit.size()-1).equals(new Integer(item.getSeq()))){
							if (tempTotalAmt.compareTo(FDCHelper.ZERO) != 0) {
								item.setBigDecimal("amount", FDCHelper.toBigDecimal(item.getBigDecimal("amount")).add(tempTotalAmt));
							}
						}
						continue;
					}
				}
			}
			//每个明细科目行：本行的归属成本金额=（已拆分成本金额各明细行合计+表头的本期成本金额）*（本行的成本拆分金额/成本拆分金额各明细行合计）-本行的已拆分成本金额
			BigDecimal distributeAmt = FDCHelper.subtract(FDCHelper.divide(FDCHelper.multiply(FDCHelper.add(hadSplitCostAmtSum,headCostAmt), item.getBigDecimal("costAmt")),splitCostAmtSum), item.getBigDecimal("splitedCostAmt"));
			System.err.println(distributeAmt);
			if (_tempTotalAmt.compareTo(distributeAmt) > 0) {
				item.setBigDecimal("amount", distributeAmt);
				_tempTotalAmt = _tempTotalAmt.subtract(distributeAmt);
			} else {
				item.setBigDecimal("amount", _tempTotalAmt);
				_tempTotalAmt = FDCHelper.ZERO;
			}
		}
		if(item!=null){
			if (_tempTotalAmt.compareTo(FDCHelper.ZERO) != 0) {
				item.setBigDecimal("amount", FDCHelper.toBigDecimal(item.getBigDecimal("amount")).add(_tempTotalAmt));
			}
		}
	}
	/**
	 * 看代码看不懂的话可以去看看
	 * void com.kingdee.eas.fdc.finance.PaymentAutoSplitHelper.splitCostAmtBaseOnProp(FDCSplitBillEntryCollection entrys, Map map)
	 * 方法里边的注释
	 * by cassiel_peng 2010-01-19 
	 */
	public static void splitPayedAmtBaseOnProp(FDCNoCostSplitBillEntryCollection entrys, Map map){
		/**
		 * 归属付款金额
		 */
		BigDecimal headPayedAmt = FDCHelper.toBigDecimal(map.get("headPayedAmt"),2);//表头的实付金额
		BigDecimal hadSplitPayedAmtSum = FDCHelper.toBigDecimal(map.get("hadSplitPayedAmtSum"),2);//已拆分付款金额各明细行合计
		BigDecimal splitCostAmtSum = FDCHelper.toBigDecimal(map.get("splitCostAmtSum"),2);//成本拆分金额各明细行合计
		PaymentNoCostSplitEntryInfo item = null;
		final List sortList = AutoNoCostSplitSortor.filterExceptProductSplit(entrys);
		BigDecimal _tempTotalAmt = headPayedAmt;
		BigDecimal tempTotalAmt = FDCHelper.ZERO;
		Boolean hasValue = null;
		List judgeLastProductSplit = null;
		Map tempMap = new HashMap();
		for(Iterator iter = sortList.iterator();iter.hasNext();){
//		for(Iterator iter = entrys.iterator();iter.hasNext();){
			if(headPayedAmt.compareTo(FDCHelper.ZERO)==0||headPayedAmt.signum()==0){
				break;
			}
			item = (PaymentNoCostSplitEntryInfo) iter.next();
			if(CostSplitTypeEnum.PRODSPLIT.equals(item.getSplitType())){//产品拆分
				if(!item.isIsLeaf()){//是非明细节点
					hasValue = Boolean.FALSE;
					tempMap.put(item.getAccountView().getId().toString()+"_"+new Integer(item.getLevel()).toString(), item);//成本科目+级次作为建
					
					judgeLastProductSplit = new ArrayList();
					for(Iterator _iter = sortList.iterator();_iter.hasNext();){
						PaymentNoCostSplitEntryInfo splitEntry = (PaymentNoCostSplitEntryInfo)_iter.next();
						if(item.getAccountView().getId().toString().equals(splitEntry.getAccountView().getId().toString())&&CostSplitTypeEnum.PRODSPLIT.equals(splitEntry.getSplitType())&&splitEntry.isIsLeaf()){
							judgeLastProductSplit.add(new Integer(splitEntry.getSeq()));
						}
					}
					Collections.sort(judgeLastProductSplit);
				}else{//明细节点
					PaymentNoCostSplitEntryInfo currentProductFloorEntry = (PaymentNoCostSplitEntryInfo)tempMap.get(item.getAccountView().getId().toString()+"_"+new Integer(item.getLevel()-1).toString());
					if(currentProductFloorEntry!=null){
						BigDecimal tempCostSplitAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("costAmt"));
						BigDecimal distributeAmt = FDCHelper.divide(FDCHelper.multiply(item.getBigDecimal("costAmt"), currentProductFloorEntry.getBigDecimal("payedAmt")), tempCostSplitAmt);
						if(hasValue!=null&&!hasValue.booleanValue()){
							tempTotalAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("payedAmt"),2);
						}
						hasValue = Boolean.TRUE;
						if (tempTotalAmt.compareTo(distributeAmt) > 0) {
							item.setBigDecimal("payedAmt", distributeAmt);
							tempTotalAmt = tempTotalAmt.subtract(distributeAmt);
						} else {
							item.setBigDecimal("payedAmt", tempTotalAmt);
							tempTotalAmt = FDCHelper.ZERO;
						}
						if(item!=null&&judgeLastProductSplit.get(judgeLastProductSplit.size()-1).equals(new Integer(item.getSeq()))){
							if (tempTotalAmt.compareTo(FDCHelper.ZERO) != 0) {
								item.setBigDecimal("payedAmt", FDCHelper.toBigDecimal(item.getBigDecimal("payedAmt")).add(tempTotalAmt));
							}
						}
						continue;
					}
				}
			}
			//本行的归属付款金额=（已拆分付款金额各明细行合计+表头的实付金额）*（本行的成本拆分金额/成本拆分金额各明细行合计）-本行的已拆分付款金额
			BigDecimal distributeAmt = FDCHelper.subtract(FDCHelper.divide(FDCHelper.multiply(FDCHelper.add(hadSplitPayedAmtSum,headPayedAmt), item.getBigDecimal("costAmt")),splitCostAmtSum), item.getBigDecimal("splitedPayedAmt"));
			
			if (_tempTotalAmt.compareTo(distributeAmt) > 0) {
				item.setBigDecimal("payedAmt", distributeAmt);
				_tempTotalAmt = _tempTotalAmt.subtract(distributeAmt);
			} else {
				item.setBigDecimal("payedAmt", _tempTotalAmt);
				_tempTotalAmt = FDCHelper.ZERO;
			}
		}
		if(item!=null){
			if (_tempTotalAmt.compareTo(FDCHelper.ZERO) != 0) {
				item.setBigDecimal("payedAmt", FDCHelper.toBigDecimal(item.getBigDecimal("payedAmt")).add(_tempTotalAmt));
			}
		}
	}
	
	/**
	 * 看代码看不懂的话可以去看看
	 * void com.kingdee.eas.fdc.finance.PaymentAutoSplitHelper.splitCostAmtBaseOnProp(FDCSplitBillEntryCollection entrys, Map map)
	 * 方法里边的注释
	 * by cassiel_peng 2010-01-19 
	 */
	public static void splitInvoiceAmtBaseOnProp(FDCNoCostSplitBillEntryCollection entrys, Map map){
		/**
		 * 归属发票金额
		 */
		BigDecimal headInvoiceAmt = FDCHelper.toBigDecimal(map.get("headInvoiceAmt"),2);//表头的发票金额
		BigDecimal hadSplitInvoiceAmtSum = FDCHelper.toBigDecimal(map.get("hadSplitInvoiceAmtSum"),2);//已拆分发票金额各明细行合计
		BigDecimal splitInvoiceAmtSum = FDCHelper.toBigDecimal(map.get("splitCostAmtSum"),2);//成本拆分金额各明细行合计
		PaymentNoCostSplitEntryInfo item = null;
		final List sortList = AutoNoCostSplitSortor.filterExceptProductSplit(entrys);
		BigDecimal _tempTotalAmt = headInvoiceAmt;
		BigDecimal tempTotalAmt = FDCHelper.ZERO;
		Boolean hasValue = null;
		List judgeLastProductSplit = null;
		Map tempMap = new HashMap();
		for(Iterator iter = sortList.iterator();iter.hasNext();){
//		for(Iterator iter = entrys.iterator();iter.hasNext();){
			if(headInvoiceAmt.compareTo(FDCHelper.ZERO)==0||headInvoiceAmt.signum()==0){
				break;
			}
			item = (PaymentNoCostSplitEntryInfo) iter.next();
			if(CostSplitTypeEnum.PRODSPLIT.equals(item.getSplitType())){//产品拆分
				if(!item.isIsLeaf()){//是非明细节点
					hasValue = Boolean.FALSE;
					tempMap.put(item.getAccountView().getId().toString()+"_"+new Integer(item.getLevel()).toString(), item);//成本科目+级次作为建
					
					judgeLastProductSplit = new ArrayList();
					for(Iterator _iter = sortList.iterator();_iter.hasNext();){
						PaymentNoCostSplitEntryInfo splitEntry = (PaymentNoCostSplitEntryInfo)_iter.next();
						if(item.getAccountView().getId().toString().equals(splitEntry.getAccountView().getId().toString())&&CostSplitTypeEnum.PRODSPLIT.equals(splitEntry.getSplitType())&&splitEntry.isIsLeaf()){
							judgeLastProductSplit.add(new Integer(splitEntry.getSeq()));
						}
					}
					Collections.sort(judgeLastProductSplit);
				}else{//明细节点
					PaymentNoCostSplitEntryInfo currentProductFloorEntry = (PaymentNoCostSplitEntryInfo)tempMap.get(item.getAccountView().getId().toString()+"_"+new Integer(item.getLevel()-1).toString());
					if(currentProductFloorEntry!=null){
						BigDecimal tempCostSplitAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("costAmt"));
						BigDecimal distributeAmt = FDCHelper.divide(FDCHelper.multiply(item.getBigDecimal("costAmt"), currentProductFloorEntry.getBigDecimal("invoiceAmt")), tempCostSplitAmt);
						if(hasValue!=null&&!hasValue.booleanValue()){
							tempTotalAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("invoiceAmt"),2);
						}
						hasValue = Boolean.TRUE;
						if (tempTotalAmt.compareTo(distributeAmt) > 0) {
							item.setBigDecimal("invoiceAmt", distributeAmt);
							tempTotalAmt = tempTotalAmt.subtract(distributeAmt);
						} else {
							item.setBigDecimal("invoiceAmt", tempTotalAmt);
							tempTotalAmt = FDCHelper.ZERO;
						}
						if(item!=null&&judgeLastProductSplit.get(judgeLastProductSplit.size()-1).equals(new Integer(item.getSeq()))){
							if (tempTotalAmt.compareTo(FDCHelper.ZERO) != 0) {
								item.setBigDecimal("invoiceAmt", FDCHelper.toBigDecimal(item.getBigDecimal("invoiceAmt")).add(tempTotalAmt));
							}
						}
						continue;
					}
				}
			}
			// 每个明细科目行：本行的归属发票金额=（已拆分发票金额各明细行合计+表头的发票金额）*（本行的成本拆分金额/成本拆分金额各明细行合计）-本行的已拆分发票金额
			BigDecimal distributeAmt = FDCHelper.subtract(FDCHelper.divide(FDCHelper.multiply(FDCHelper.add(hadSplitInvoiceAmtSum,headInvoiceAmt), item.getBigDecimal("costAmt")),splitInvoiceAmtSum), item.getBigDecimal("splitedInvoiceAmt"));
			//支持负数,以绝对值处理 by hpw 2010-03-23
			if (_tempTotalAmt.abs().compareTo(distributeAmt.abs()) > 0) {
				item.setBigDecimal("invoiceAmt", distributeAmt);
				_tempTotalAmt = _tempTotalAmt.subtract(distributeAmt);
			} else {
				item.setBigDecimal("invoiceAmt", _tempTotalAmt);
				_tempTotalAmt = FDCHelper.ZERO;
			}
		}
		if(item!=null){
			if (_tempTotalAmt.compareTo(FDCHelper.ZERO) != 0) {
				item.setBigDecimal("invoiceAmt", FDCHelper.toBigDecimal(item.getBigDecimal("invoiceAmt")).add(_tempTotalAmt));
			}
		}
	}
	
}
