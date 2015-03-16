/**
 * 
 */
package com.kingdee.eas.fdc.finance;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;

/**
 * 
 * @author sxhong Date 2007-3-22
 */
public class AutoSplitSortor implements Comparator {
	private Collator comparator = Collator.getInstance(Locale.getDefault());
	private static AutoSplitSortor sortComparator=null;
	private static Comparator getSortComparator(){
		if(sortComparator==null){
			sortComparator=new AutoSplitSortor();
		}
		return sortComparator;
	}
	public int compare(Object o1, Object o2) {
		if (o1 instanceof FDCSplitBillEntryInfo
				&& o2 instanceof FDCSplitBillEntryInfo) {
			return compare((FDCSplitBillEntryInfo) o1,
					(FDCSplitBillEntryInfo) o2);
		}
		return 0;
	}

	/**
	 * 排序规则：0级项目的编码－>项目开发顺序－>科目编码顺序－>显示顺序
	 * @param info1
	 * @param info2
	 * @return
	 */
	private int compare(FDCSplitBillEntryInfo info1, FDCSplitBillEntryInfo info2) {
		int compareValue = 0;
//		String curNumber1 = info1.getCostAccount().getCurProject()
//				.getLongNumber();
//		String curNumber2 = info2.getCostAccount().getCurProject()
//				.getLongNumber();
//		compareValue = compareNumber(curNumber1, curNumber2);
		compareValue=comparePrj(info1.getCostAccount().getCurProject(), info2.getCostAccount().getCurProject());
		String accountNumber1 = info1.getCostAccount().getLongNumber();
		String accountNumber2 = info2.getCostAccount().getLongNumber();
		// compareValue=curNumber1.compareTo(curNumber2);

		if (compareValue == 0) {
			// compareValue=accountNumber1.compareTo(accountNumber2);
			compareValue = compareNumber(accountNumber1, accountNumber2);
			if (compareValue == 0) {
				int seq1 = info1.getSeq();
				int seq2 = info2.getSeq();
				compareValue = seq1 - seq2;
			}
		}
		return compareValue;
	}
	
	/**
	 * 工程项目排序
	 * @return
	 */
	private int comparePrj(CurProjectInfo info1,CurProjectInfo info2){
		String curNumber1 = info1.getLongNumber();
		String curNumber2 = info2.getLongNumber();
		if(FDCHelper.isEmpty(curNumber1)){
			return -1;
		}
		if(FDCHelper.isEmpty(curNumber2)){
			return 1;
		}
		if(curNumber1.indexOf('!')<0){
			curNumber1=curNumber1+"!";
		}
		if(curNumber2.indexOf('!')<0){
			curNumber2=curNumber2+"!";
		}
		//R110708-0056：付款拆分中实付款拆分与发票金额拆分自动匹配顺序不一致
		//比较上级工程项目是否相同，如果相同则比较开发顺序
//		String topCurNumber1=curNumber1.substring(0,curNumber1.indexOf('!'));
//		String topCurNumber2=curNumber2.substring(0,curNumber2.indexOf('!'));
		String topCurNumber1=curNumber1.substring(0,curNumber1.lastIndexOf('!'));
		String topCurNumber2=curNumber2.substring(0,curNumber2.lastIndexOf('!'));
		int value=compareNumber(topCurNumber1, topCurNumber2);
		if(value==0){
			value=info1.getSortNo()-info2.getSortNo();
		}
		if(value==0){
			value=compareNumber(curNumber1, curNumber2);
		}
		return value;
		
	}
	private int compareNumber(String number1, String number2) {
		if(FDCHelper.isEmpty(number1)){
			return -1;
		}
		if(FDCHelper.isEmpty(number2)){
			return 1;
		}
		String nums1[] = number1.split("!");
		String nums2[] = number2.split("!");
		int value = 0;
		for (int i = 0; i < nums1.length && i < nums2.length; i++) {
			// value=nums1[i].compareTo(nums2[i]);
			value = comparator.compare(nums1[i],nums2[i]);
			if (value != 0) {
				return value;
			}
		}
		// number1,number2长短不一致且前半部分是一样的
		value = nums2.length - nums1.length;

		return value;
	}
	/**
	 * 非明细行从明细行汇总上去而不是通过某种计算方法直接计算出来
	 * by cassiel_peng 2010-01-23 
	 */
	public static List filterExceptProductSplit(AbstractObjectCollection collection) {
		List list = new ArrayList();
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			FDCSplitBillEntryInfo info = (FDCSplitBillEntryInfo) iter.next();
			//产品拆分的除外,因为本来非明细行是需要从明细行汇总上去的，但是如果有产品拆分，在点付款拆分编辑界面的 "按比例拆分"时有如下公式需要遵循：
			//归属成本金额=所在明细科目行的归属成本金额(成本拆分金额/所在明细科目行的成本拆分金额)  
			//归属付款金额=所在明细科目行的归属付款金额(成本拆分金额/所在明细科目行的成本拆分金额)  
			//归属发票金额=所在明细科目行的归属发票金额(成本拆分金额/所在明细科目行的成本拆分金额)  
			//所以如果在过滤非明细行数据的时候将该拆品拆分记录也过滤除去的话，就计算不到产品拆分的最明细记录的相应归属成本金额、归属付款金额、归属发票金额了
			if ((info.getLevel() < 0 || !info.isIsLeaf())&&!CostSplitTypeEnum.PRODSPLIT.equals(info.getSplitType()))
				continue;

			list.add(info);
		}
		for(Iterator iter=list.iterator();iter.hasNext();){
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)iter.next();
			System.err.print("科目："+entry.getCostAccount().getLongNumber());
			System.err.print("costAmount："+entry.getAmount());
			System.err.println("序号："+entry.getSeq());
		}
		return list;
	}
	public static List sort(AbstractObjectCollection collection) {
		List list = new ArrayList();
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			FDCSplitBillEntryInfo info = (FDCSplitBillEntryInfo) iter.next();

			if (info.getLevel() < 0 || !info.isIsLeaf())
				continue;

			list.add(info);
		}
		Collections.sort(list, getSortComparator());
		
		if(true){
			for(Iterator iter=list.iterator();iter.hasNext();){
				PaymentSplitEntryInfo entry=(PaymentSplitEntryInfo)iter.next();
				System.out.print("项目："+entry.getCostAccount().getCurProject().getLongNumber());
				System.out.print("开发顺序："+entry.getCostAccount().getCurProject().getSortNo());
				System.out.print("科目："+entry.getCostAccount().getLongNumber());
				System.out.print("costAmount："+entry.getCostAmt());
				System.out.println("序号："+entry.getSeq());
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
//		// test number compare
//		String number1 = "001!002!一二";
//		String number2 = "001!002!四五";
//
		AutoSplitSortor sortor = new AutoSplitSortor();
//
//		System.out.println("number1:" + number1);
//		System.out.println("number2:" + number2);
//		int value = sortor.compareNumber(number1, number2);
		int value = sortor.compareNumber("001!成本一体化化复杂模式!鸿!H1开发顺序", "001!成本一体化化复杂模式!时!L1开发顺序");
		System.out.println("Compare Value:" + value);
		if(true)return;
		PaymentSplitEntryCollection coll=new PaymentSplitEntryCollection();
		PaymentSplitEntryInfo entry=new PaymentSplitEntryInfo();
		CostAccountInfo acct=new CostAccountInfo();
		CurProjectInfo prj=new CurProjectInfo();
		prj.setLongNumber("100!01!11");
		prj.setSortNo(5);
		acct.setCurProject(prj);
		acct.setLongNumber("4301!01!01");
		entry.setSeq(3);
		entry.setCostAccount(acct);
		entry.setIsLeaf(true);
		entry.setCostAmt(FDCNumberHelper.ONE);
		coll.add(entry);
		
		entry=new PaymentSplitEntryInfo();
		acct=new CostAccountInfo();
		prj=new CurProjectInfo();
		prj.setLongNumber("100!01!11");
		prj.setSortNo(5);
		acct.setCurProject(prj);
		acct.setLongNumber("4301!01!02");
		entry.setSeq(3);
		entry.setCostAccount(acct);
		entry.setIsLeaf(true);
		entry.setCostAmt(FDCNumberHelper.ONE);
		coll.add(entry);
		
		entry=new PaymentSplitEntryInfo();
		acct=new CostAccountInfo();
		prj=new CurProjectInfo();
		prj.setLongNumber("100!01!11");
		prj.setSortNo(5);
		acct.setCurProject(prj);
		acct.setLongNumber("4301!01!01");
		entry.setSeq(4);
		entry.setCostAccount(acct);
		entry.setIsLeaf(true);
		entry.setCostAmt(FDCNumberHelper.ONE);
		coll.add(entry);
		
		entry=new PaymentSplitEntryInfo();
		acct=new CostAccountInfo();
		prj=new CurProjectInfo();
		prj.setLongNumber("100!01!01");
		prj.setSortNo(6);
		acct.setCurProject(prj);
		acct.setLongNumber("4301!01!01");
		entry.setSeq(3);
		entry.setCostAccount(acct);
		entry.setIsLeaf(true);
		entry.setCostAmt(FDCNumberHelper.ONE);
		coll.add(entry);
		
		entry=new PaymentSplitEntryInfo();
		acct=new CostAccountInfo();
		prj=new CurProjectInfo();
		prj.setLongNumber("101!01!01");
		prj.setSortNo(6);
		acct.setCurProject(prj);
		acct.setLongNumber("4301!01!01");
		entry.setSeq(3);
		entry.setCostAccount(acct);
		entry.setIsLeaf(true);
		entry.setCostAmt(FDCNumberHelper.ONE);
		coll.add(entry);
		
		entry=new PaymentSplitEntryInfo();
		acct=new CostAccountInfo();
		prj=new CurProjectInfo();
		prj.setLongNumber("101!01!01");
		prj.setSortNo(6);
		acct.setCurProject(prj);
		acct.setLongNumber("4301!02!01");
		entry.setSeq(3);
		entry.setCostAccount(acct);
		entry.setCostAmt(FDCNumberHelper.ONE);
		entry.setIsLeaf(true);
		coll.add(entry);
		
		List sort = sort(coll);
		for(Iterator iter=sort.iterator();iter.hasNext();){
			entry=(PaymentSplitEntryInfo)iter.next();
			System.out.print("项目："+entry.getCostAccount().getCurProject().getLongNumber());
			System.out.print("开发顺序："+entry.getCostAccount().getCurProject().getSortNo());
			System.out.print("科目："+entry.getCostAccount().getLongNumber());
			System.out.println("序号："+entry.getSeq());
		}
	}
}
