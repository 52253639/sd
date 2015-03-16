package com.kingdee.eas.fdc.finance;

import java.math.BigDecimal;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.util.backport.Collections;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
/**
 * 之前这个类里封装的都是处理成本类付款拆分界面上点击“自动匹配拆分”按钮应该处理的逻辑 by sxhong
 * 现在这个类中封装成本类付款拆分和工程量拆分界面上点击“按比例拆分”按钮应该处理的算法逻辑  by cassiel_peng  2010-01-19
 */
public class PaymentAutoSplitHelper implements Comparator {
	
	
	private Collator comparator = Collator.getInstance(Locale.getDefault());
	private static AutoSplitSortor sortComparator=null;
	private static Comparator getSortComparator(){
		if(sortComparator==null){
			sortComparator=new AutoSplitSortor();
		}
		return sortComparator;
	}
	public int compare(Object o1, Object o2) {
		if(o1!=null&&o2!=null&&o1 instanceof Integer&&o2 instanceof Integer){
			return compareAgain((Integer)o1, (Integer)o2);
		}
		return 0;
	}
	private int compareAgain(Integer o1,Integer o2) {
		if(o1.intValue()>o2.intValue())   
			return   1;   
		else   
			return   0;   
	}
	
	
	/**
	 * 自动匹配完工工程量
	 * @param entrys
	 * @param totalAmt
	 * @return
	 */
	public static void autoSplit(
			FDCSplitBillEntryCollection entrys, BigDecimal totalAmt) {
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			PaymentSplitEntryInfo item = (PaymentSplitEntryInfo) iter.next();
			item.setAmount(FDCHelper.ZERO);
			if (item.getLevel() < 0 || !item.isIsLeaf()) {
				continue;
			}
			BigDecimal amount = FDCHelper.subtract(item.getCostAmt(),item.getSplitedCostAmt());
			item.put("distributeAmt", amount);
			item.setDirectAmt(null);
		}
		
		innerDistributeAmt(entrys, totalAmt, "amount");
	}

	/**
	 *付款拆分与凭证生成的模式主要是第一笔结算款的处理存在关系 
	 * 进度款：红冲与调整一致　分配金额（distributeAmt）＝已拆分成本金额＋本期成本金额－已拆分付款金额
	 * 结算款：应付进度款＝成本金额－保修金额
	 * 第一笔结算款：
	 * 	红冲：分配金额（distributeAmt）＝为应付进度款？
	 * 	调整：分配金额（distributeAmt）＝应付进度款－已拆分进度款付款金额　（先拆分保修款怎么办？）
	 * 第二笔结算款：分配金额（distributeAmt）=应付进度款－已拆分进度款
	 * 
	 * 保修款：
	 * 	分配金额（distributeAmt）=保修款拆分金额（在结算拆分处拆）－已拆分保修款
	 *  by sxhong 2008-10-21 13:47:42
	 * @param entrys
	 * @param totalAmt
	 * @param hasSettleSplit 未知意义
	 * @param hasSettlePayed	非第一笔结算款
	 * @param isSettle	第一笔结算款
	 * @param isKeepAmt 保修款
	 * @param isAdjustModel 是否调整模式
	 * @return
	 */
	public static void autoSplitAmt(
			FDCSplitBillEntryCollection entrys, BigDecimal totalAmt,
			boolean hasSettleSplit, boolean hasSettlePayed, boolean isSettle,
			boolean isKeepAmt, boolean isAdjustModel) {
		/*
		 * if (!FDCHelper.isPositiveBigDecimal(totalAmt)) { return null; }
		 */
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			PaymentSplitEntryInfo item = (PaymentSplitEntryInfo) iter.next();
			item.setPayedAmt(FDCHelper.ZERO);
			if (item.getLevel() < 0 || !item.isIsLeaf()) {
				continue;
			}
			if (isSettle) {
				BigDecimal amount = FDCHelper.ZERO;
				amount = FDCHelper.toBigDecimal(item.getShouldPayAmt());
				if (isAdjustModel) {
					amount = FDCHelper.subtract(amount, item.getSplitedPayedAmt());
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

			item.setDirectAmt(null);
		}
		innerDistributeAmt(entrys, totalAmt,"payedAmt");

	}
	
	/**
	 * 自动匹配发票金额
	 * @param entrys
	 * @param totalInvoiceAmt
	 * @return
	 */
	public static void autoSplitInvoiceAmt(FDCSplitBillEntryCollection entrys, BigDecimal totalInvoiceAmt) {
		String prop="invoiceAmt";
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			PaymentSplitEntryInfo item = (PaymentSplitEntryInfo) iter.next();
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
	 * 简单发票模式
	 * 自动匹配付款金额
	 * @param entrys
	 * @param totalInvoiceAmt
	 * @return
	 */
	public static void autoSplitSimpleInvoicePayAmt(FDCSplitBillEntryCollection entrys, BigDecimal totalInvoiceAmt) {
		String prop="payedAmt";
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			PaymentSplitEntryInfo item = (PaymentSplitEntryInfo) iter.next();
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
	public static void autoSplitSimpleInvoiceInvoiceAmt(FDCSplitBillEntryCollection entrys, BigDecimal totalInvoiceAmt) {
		String prop="invoiceAmt";
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			PaymentSplitEntryInfo item = (PaymentSplitEntryInfo) iter.next();
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
	private static void innerDistributeAmt(FDCSplitBillEntryCollection entrys, BigDecimal totalAmt,String prop) {
		if(totalAmt==null||totalAmt.signum()==0){
			return;
		}
		//设置自动分配的顺序
		final List sortList = AutoSplitSortor.sort(entrys);
		FDCSplitBillEntryInfo item = null;
		for (Iterator iter = sortList.iterator(); iter.hasNext();) {
			if(totalAmt.signum()==0){
				break;
			}
			item = (FDCSplitBillEntryInfo) iter.next();
			boolean debug = FDCHelper.isFDCDebug();
			if (debug) {
				System.out.print(prop + item.getCostAccount().getCurProject().getLongNumber());
				System.out.print("\t科目编码："+ item.getCostAccount().getLongNumber());
				System.out.print("\tseq：" + item.getSeq());
				System.out.println("\tTotal:" + totalAmt + "\t distributeAmt:"+ item.get("distributeAmt"));
			}

			BigDecimal distributeAmt = FDCHelper.toBigDecimal(item.get("distributeAmt"));
			if (totalAmt.compareTo(distributeAmt) > 0) {
				item.setBigDecimal(prop, distributeAmt);
				totalAmt = totalAmt.subtract(distributeAmt);
			} else {
				item.setBigDecimal(prop, totalAmt);
				totalAmt = FDCHelper.ZERO;
			}

		}
		// 多余的放置到最后一项
		if(item!=null){
			if (totalAmt.compareTo(FDCHelper.ZERO) != 0) {
				item.setBigDecimal(prop, FDCHelper.toBigDecimal(item.getBigDecimal(prop)).add(totalAmt));
			}
		}
	}
	/**
	 * @param entrys 拆分分录
	 * @param map 里边包含了需要参与计算的变量值：表头的本期成本金额、已拆分成本金额各明细行合计、成本拆分金额各明细行合计
	 * by cassiel_peng 2010-01-19 
	 * 该方法中的注释可当作其他两个方法(splitPayedAmtBaseOnProp、splitInvoiceAmtBaseOnProp )的参考注释
	 */
	public static void splitCostAmtBaseOnProp(FDCSplitBillEntryCollection entrys, Map map){
		/**
		 * 归属成本金额
		 */
		BigDecimal headCostAmt = FDCHelper.toBigDecimal(map.get("headCostAmt"),2);//表头的本期成本金额
		BigDecimal hadSplitCostAmtSum = FDCHelper.toBigDecimal(map.get("hadSplitCostAmtSum"),2);//已拆分成本金额各明细行合计
		BigDecimal splitCostAmtSum = FDCHelper.toBigDecimal(map.get("splitCostAmtSum"),2);//成本拆分金额各明细行合计
		FDCSplitBillEntryInfo item = null;
		final List sortList = AutoSplitSortor.filterExceptProductSplit(entrys);
		BigDecimal _tempTotalAmt = headCostAmt;
		BigDecimal tempTotalAmt = FDCHelper.ZERO;
		Map tempMap = new HashMap();
		Boolean hasValue = null;
		//当是产品拆分时处理为差的时候将差额放到最后一笔，为了找到最后一笔，必须找到某一科目行下的所有产品拆分明细行对象
		List judgeLastProductSplit = null;
		for(Iterator iter = sortList.iterator();iter.hasNext();){
			if(headCostAmt.compareTo(FDCHelper.ZERO)==0||headCostAmt.signum()==0){
				break;
			}
			item = (FDCSplitBillEntryInfo) iter.next();
			if(CostSplitTypeEnum.PRODSPLIT.equals(item.getSplitType())){//产品拆分
				if(!item.isIsLeaf()){//是非明细节点
					hasValue = Boolean.FALSE;
					tempMap.put(item.getCostAccount().getId().toString()+"_"+new Integer(item.getLevel()).toString(), item);//成本科目+级次作为建
					
					judgeLastProductSplit = new ArrayList();
					//因为此时拆分的数据尚未保存，所以从数据库中是查不到某一成本科目行下的拆品拆分明细行对象的，那么在处理为差的时候试图将差额放到最后一个
					//产品拆分明细行上是不现实的。但是在界面上的kdtEntrys的每一行是绑定了拆分分录对象的，这些对象是有我们需要提出出来参与计算的相关属性的值的
//					for(Iterator _iter = sortList.iterator();_iter.hasNext();){
//						FDCSplitBillEntryInfo splitEntry = (FDCSplitBillEntryInfo)_iter.next();
//						if(item.getCostAccount().getId().toString().equals(splitEntry.getCostAccount().getId().toString())&&CostSplitTypeEnum.PRODSPLIT.equals(splitEntry.getSplitType())&&splitEntry.isIsLeaf()){
//							judgeLastProductSplit.add(new Integer(splitEntry.getSeq()));
//						}
//					}
					//R101108-137 这里应该只取该非明细节点下的产品拆分。因为有变更拆分，所以可能存在多个相同科目的拆分记录
					//如果按照之前的处理逻辑，会把所有的相同科目的产品拆分都取出来  by zhiyuan_tang
					int startIndex = sortList.indexOf(item);
					if (startIndex < sortList.size() - 1) {
						for (int i = startIndex + 1 ; i < sortList.size(); i++) {
							FDCSplitBillEntryInfo splitEntry = (FDCSplitBillEntryInfo)sortList.get(i);
							if(item.getCostAccount().getId().toString().equals(splitEntry.getCostAccount().getId().toString())&&CostSplitTypeEnum.PRODSPLIT.equals(splitEntry.getSplitType())&&splitEntry.isIsLeaf()){
								judgeLastProductSplit.add(new Integer(splitEntry.getSeq()));
							} else {
								break;
							}
						}
					}
					Collections.sort(judgeLastProductSplit);
				}else{//明细节点
					//拆分到产品，则按 每个产品行的   成本拆分金额/所在明细科目行的成本拆分金额    拆分
					//找到所在明细科目行(与当前分录的成本科目是一样，但是级次会比当前分录小1)的成本拆分金额
					FDCSplitBillEntryInfo currentProductFloorEntry = (FDCSplitBillEntryInfo)tempMap.get(item.getCostAccount().getId().toString()+"_"+new Integer(item.getLevel()-1).toString());
					if(currentProductFloorEntry!=null){
						BigDecimal tempCostSplitAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("costAmt"));//所在明细科目行的成本拆分金额 
						BigDecimal distributeAmt  = FDCHelper.divide(FDCHelper.multiply(item.getBigDecimal("costAmt"),currentProductFloorEntry.getBigDecimal("amount")),tempCostSplitAmt );
						if(hasValue!=null&&!hasValue.booleanValue()){
							tempTotalAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("amount"),2);
						}
						hasValue = Boolean.TRUE;
						//此处一定要处理当是某一个成本科目下的产品拆分的时候的尾差，因为产品拆分的科目行是根据本行的归属成本金额=（已拆分成本金额各明细行合计+表头的本期成本金额）*（本行的成本拆分金额/成本拆分金额各明细行合计）-本行的已拆分成本金额
						//计算出来的，然后这个科目行下边的产品拆分是按照每个产品行的   (成本拆分金额/所在明细科目行的成本拆分金额)*所在所在明细科目行的归属成本金额    来计算的，如果不处理尾差问题不仅仅是按比例计算出来的各行拆分数据之和不等于所在所在明细科目行的归属成本金额 
						//而且如果存在尾差问题没有处理，最后所在明细科目行的归属成本金额会根据其下所有的产品拆分行的数据来进行汇总，最后所在所在明细科目行的归属成本金额也会有问题   
						if(distributeAmt==null){
							distributeAmt = FDCHelper.ZERO;
						}
						if (tempTotalAmt.abs().compareTo(distributeAmt.abs()) > 0) {
							item.setBigDecimal("amount", distributeAmt);
							tempTotalAmt = tempTotalAmt.subtract(distributeAmt);
						} else {
							item.setBigDecimal("amount", tempTotalAmt);
							tempTotalAmt = FDCHelper.ZERO;
						}
						//最后一项拆分时
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
			//注意：对于类似于这种有比率的计算，先乘后除为上上策，否则由于精度的问题会导致小数位的取舍产生问题
			BigDecimal distributeAmt = FDCHelper.subtract(FDCHelper.divide(FDCHelper.multiply(FDCHelper.add(hadSplitCostAmtSum,headCostAmt), item.getBigDecimal("costAmt")),splitCostAmtSum), item.getBigDecimal("splitedCostAmt"));
			//尾差处理
			if(distributeAmt==null){
				distributeAmt=FDCHelper.ZERO;
			}
			if (_tempTotalAmt.abs().compareTo(distributeAmt.abs()) > 0) {
				item.setBigDecimal("amount", distributeAmt);
				_tempTotalAmt = _tempTotalAmt.subtract(distributeAmt);
			} else {
				item.setBigDecimal("amount", _tempTotalAmt);
				_tempTotalAmt = FDCHelper.ZERO;
			}
		}
		// 多余的放置到最后一项
		if(item!=null){
			if (_tempTotalAmt.compareTo(FDCHelper.ZERO) != 0) {
				item.setBigDecimal("amount", FDCHelper.toBigDecimal(item.getBigDecimal("amount")).add(_tempTotalAmt));
			}
		}
	}
	/**
	 * 如果代码有啥看不懂，可看splitCostAmtBaseOnProp方法里边对应的注释，里边有详细的代码说明。（可根据实际情况调整相关注释）
	 * by cassiel_peng 2010-01-19 
	 */
	public static void splitPayedAmtBaseOnProp(FDCSplitBillEntryCollection entrys, Map map) throws BOSException{
		/**
		 * 归属付款金额
		 */
		BigDecimal headPayedAmt = FDCHelper.toBigDecimal(map.get("headPayedAmt"),2);//表头的实付金额
		BigDecimal hadSplitPayedAmtSum = FDCHelper.toBigDecimal(map.get("hadSplitPayedAmtSum"),2);//已拆分付款金额各明细行合计
		BigDecimal splitCostAmtSum = FDCHelper.toBigDecimal(map.get("splitCostAmtSum"),2);//成本拆分金额各明细行合计
		FDCSplitBillEntryInfo item = null;
		final List sortList = AutoSplitSortor.filterExceptProductSplit(entrys);
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
			item = (FDCSplitBillEntryInfo) iter.next();
			if(CostSplitTypeEnum.PRODSPLIT.equals(item.getSplitType())){//产品拆分
				if(!item.isIsLeaf()){//是非明细节点
					hasValue = Boolean.FALSE;
					tempMap.put(item.getCostAccount().getId().toString()+"_"+new Integer(item.getLevel()).toString(), item);//成本科目+级次作为建
					
					judgeLastProductSplit = new ArrayList();
//					for(Iterator _iter = sortList.iterator();_iter.hasNext();){
//						FDCSplitBillEntryInfo splitEntry = (FDCSplitBillEntryInfo)_iter.next();
//						if(item.getCostAccount().getId().toString().equals(splitEntry.getCostAccount().getId().toString())&&CostSplitTypeEnum.PRODSPLIT.equals(splitEntry.getSplitType())&&splitEntry.isIsLeaf()){
//							judgeLastProductSplit.add(new Integer(splitEntry.getSeq()));
//						}
//					}
					//R101108-137 这里应该只取该非明细节点下的产品拆分。因为有变更拆分，所以可能存在多个相同科目的拆分记录
					//如果按照之前的处理逻辑，会把所有的相同科目的产品拆分都取出来  by zhiyuan_tang
					int startIndex = sortList.indexOf(item);
					if (startIndex < sortList.size() - 1) {
						for (int i = startIndex + 1 ; i < sortList.size(); i++) {
							FDCSplitBillEntryInfo splitEntry = (FDCSplitBillEntryInfo)sortList.get(i);
							if(item.getCostAccount().getId().toString().equals(splitEntry.getCostAccount().getId().toString())&&CostSplitTypeEnum.PRODSPLIT.equals(splitEntry.getSplitType())&&splitEntry.isIsLeaf()){
								judgeLastProductSplit.add(new Integer(splitEntry.getSeq()));
							} else {
								break;
							}
						}
					}
					Collections.sort(judgeLastProductSplit);
				}else{//明细节点
					FDCSplitBillEntryInfo currentProductFloorEntry = (FDCSplitBillEntryInfo)tempMap.get(item.getCostAccount().getId().toString()+"_"+new Integer(item.getLevel()-1).toString());
					if(currentProductFloorEntry!=null){
						BigDecimal tempCostSplitAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("costAmt"));
						BigDecimal distributeAmt = FDCHelper.divide(FDCHelper.multiply(item.getBigDecimal("costAmt"), currentProductFloorEntry.getBigDecimal("payedAmt")), tempCostSplitAmt);
						if(hasValue!=null&&!hasValue.booleanValue()){
							tempTotalAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("payedAmt"),2);
						}
						hasValue = Boolean.TRUE;
						if(distributeAmt==null){
							distributeAmt=FDCHelper.ZERO;
						}
						if (tempTotalAmt.abs().compareTo(distributeAmt.abs()) > 0) {
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
			if(distributeAmt==null){
				distributeAmt =FDCHelper.ZERO;
			}
			if (_tempTotalAmt.abs().compareTo(distributeAmt.abs()) > 0) {
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
	 * 如果代码有啥看不懂，可看splitCostAmtBaseOnProp方法里边对应的注释，里边有详细的代码说明。（可根据实际情况调整相关注释）
	 * by cassiel_peng 2010-01-19 
	 */
	public static void splitInvoiceAmtBaseOnProp(FDCSplitBillEntryCollection entrys, Map map){
		/**
		 * 归属发票金额
		 */
		BigDecimal headInvoiceAmt = FDCHelper.toBigDecimal(map.get("headInvoiceAmt"),2);//表头的发票金额
		BigDecimal hadSplitInvoiceAmtSum = FDCHelper.toBigDecimal(map.get("hadSplitInvoiceAmtSum"),2);//已拆分发票金额各明细行合计
		BigDecimal splitInvoiceAmtSum = FDCHelper.toBigDecimal(map.get("splitCostAmtSum"),2);//成本拆分金额各明细行合计
		FDCSplitBillEntryInfo item = null;
		final List sortList = AutoSplitSortor.filterExceptProductSplit(entrys);
		BigDecimal _tempTotalAmt = headInvoiceAmt;
		BigDecimal tempTotalAmt = FDCHelper.ZERO;
		Boolean hasValue = null;
		List judgeLastProductSplit = null;//用来判断是不是产品拆分类型某一个成本科目行下边的最后一条产品拆分行
		HashMap tempMap = new HashMap();
		for(Iterator iter = sortList.iterator();iter.hasNext();){
			if(headInvoiceAmt.compareTo(FDCHelper.ZERO)==0||headInvoiceAmt.signum()==0){
				break;
			}
			item = (FDCSplitBillEntryInfo) iter.next();
			if(CostSplitTypeEnum.PRODSPLIT.equals(item.getSplitType())){//产品拆分
				if(!item.isIsLeaf()){//是非明细节点
					hasValue = Boolean.FALSE;
					tempMap.put(item.getCostAccount().getId().toString()+"_"+new Integer(item.getLevel()).toString(), item);//成本科目+级次作为建
					
					judgeLastProductSplit = new ArrayList();
//					for(Iterator _iter = sortList.iterator();_iter.hasNext();){
//						FDCSplitBillEntryInfo splitEntry = (FDCSplitBillEntryInfo)_iter.next();
//						if(item.getCostAccount().getId().toString().equals(splitEntry.getCostAccount().getId().toString())&&CostSplitTypeEnum.PRODSPLIT.equals(splitEntry.getSplitType())&&splitEntry.isIsLeaf()){
//							judgeLastProductSplit.add(new Integer(splitEntry.getSeq()));
//						}
//					}
					//R101108-137 这里应该只取该非明细节点下的产品拆分。因为有变更拆分，所以可能存在多个相同科目的拆分记录
					//如果按照之前的处理逻辑，会把所有的相同科目的产品拆分都取出来  by zhiyuan_tang
					int startIndex = sortList.indexOf(item);
					if (startIndex < sortList.size() - 1) {
						for (int i = startIndex + 1 ; i < sortList.size(); i++) {
							FDCSplitBillEntryInfo splitEntry = (FDCSplitBillEntryInfo)sortList.get(i);
							if(item.getCostAccount().getId().toString().equals(splitEntry.getCostAccount().getId().toString())&&CostSplitTypeEnum.PRODSPLIT.equals(splitEntry.getSplitType())&&splitEntry.isIsLeaf()){
								judgeLastProductSplit.add(new Integer(splitEntry.getSeq()));
							} else {
								break;
							}
						}
					}
					Collections.sort(judgeLastProductSplit);
				}else{//明细节点
					FDCSplitBillEntryInfo currentProductFloorEntry = (FDCSplitBillEntryInfo)tempMap.get(item.getCostAccount().getId().toString()+"_"+new Integer(item.getLevel()-1).toString());
					if(currentProductFloorEntry!=null){
						BigDecimal tempCostSplitAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("costAmt"));
						BigDecimal distributeAmt  = FDCHelper.divide(FDCHelper.multiply(item.getBigDecimal("costAmt"),currentProductFloorEntry.getBigDecimal("invoiceAmt")),tempCostSplitAmt );
						if(hasValue!=null&&!hasValue.booleanValue()){
							tempTotalAmt = FDCHelper.toBigDecimal(currentProductFloorEntry.getBigDecimal("invoiceAmt"),2);
						}
						hasValue = Boolean.TRUE;
						if(distributeAmt==null){
							distributeAmt=FDCHelper.ZERO;
						}
						if (tempTotalAmt.abs().compareTo(distributeAmt.abs()) > 0) {
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
			if(distributeAmt==null){
				distributeAmt=FDCHelper.ZERO;
			}
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
