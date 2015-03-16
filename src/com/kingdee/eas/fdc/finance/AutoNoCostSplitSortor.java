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
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;

/**
 * 
 * @author sxhong Date 2007-3-22
 */
public class AutoNoCostSplitSortor implements Comparator {
	private Collator comparator = Collator.getInstance(Locale.getDefault());
	private static AutoNoCostSplitSortor sortComparator=null;
	private static Comparator getSortComparator(){
		if(sortComparator==null){
			sortComparator=new AutoNoCostSplitSortor();
		}
		return sortComparator;
	}
	public int compare(Object o1, Object o2) {
		if (o1 instanceof FDCNoCostSplitBillEntryInfo
				&& o2 instanceof FDCNoCostSplitBillEntryInfo) {
			return compare((FDCNoCostSplitBillEntryInfo) o1,
					(FDCNoCostSplitBillEntryInfo) o2);
		}
		return 0;
	}

	private int compare(FDCNoCostSplitBillEntryInfo info1,
			FDCNoCostSplitBillEntryInfo info2) {
		int compareValue = 0;
/*		String curNumber1 = info1.getCurProject().getLongNumber();
		String curNumber2 = info2.getCurProject().getLongNumber();
		compareValue = compareNumber(curNumber1, curNumber2);
*/
		compareValue=comparePrj(info1.getCurProject(), info2.getCurProject());
		String accountNumber1 = info1.getAccountView().getLongNumber();
		String accountNumber2 = info2.getAccountView().getLongNumber();
		int seq1 = info1.getSeq();
		int seq2 = info2.getSeq();
		// compareValue=curNumber1.compareTo(curNumber2);

		if (compareValue == 0) {
			// compareValue=accountNumber1.compareTo(accountNumber2);
			compareValue = compareNumber(accountNumber1, accountNumber2);
			if (compareValue == 0) {
				compareValue = ((PaymentNoCostSplitEntryInfo) info1)
						.getCostAmt()
						.compareTo(((PaymentNoCostSplitEntryInfo) info2).getCostAmt());
				if (compareValue == 0)
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
		String topCurNumber1=curNumber1.substring(0,curNumber1.indexOf('!'));
		String topCurNumber2=curNumber2.substring(0,curNumber2.indexOf('!'));
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
			value = comparator.compare(nums1[i], nums2[i]);
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
			FDCNoCostSplitBillEntryInfo info = (FDCNoCostSplitBillEntryInfo) iter.next();
			//产品拆分的除外,因为本来非明细行是需要从明细行汇总上去的，但是如果有产品拆分，在点付款拆分编辑界面的 "按比例拆分"时有如下公式需要遵循：
			//归属成本金额=所在明细科目行的归属成本金额(成本拆分金额/所在明细科目行的成本拆分金额)  
			//归属付款金额=所在明细科目行的归属付款金额(成本拆分金额/所在明细科目行的成本拆分金额)  
			//归属发票金额=所在明细科目行的归属发票金额(成本拆分金额/所在明细科目行的成本拆分金额)  
			//所以如果在过滤非明细行数据的时候将该拆品拆分记录也过滤除去的话，就计算不到产品拆分的最明细记录的相应归属成本金额、归属付款金额、归属发票金额了
			if ((info.getLevel() < 0 || !info.isIsLeaf())&&!CostSplitTypeEnum.PRODSPLIT.equals(info.getSplitType()))
				continue;

			list.add(info);
		}
		if(true){
			for(Iterator iter=list.iterator();iter.hasNext();){
				FDCNoCostSplitBillEntryInfo entry=(FDCNoCostSplitBillEntryInfo)iter.next();
				System.err.print("科目："+entry.getAccountView().getLongNumber());
				System.err.print("costAmount："+entry.getAmount());
				System.err.println("序号："+entry.getSeq());
			}
		}
		
		return list;
	}
	public static List sort(AbstractObjectCollection collection) {
		List list = new ArrayList();
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			FDCNoCostSplitBillEntryInfo info = (FDCNoCostSplitBillEntryInfo) iter
					.next();

			if (info.getLevel() < 0 || !info.isIsLeaf())
				continue;

			list.add(info);
		}
		Collections.sort(list, new AutoNoCostSplitSortor());
		return list;
	}

	public static void main(String[] args) {
		// test number compare
		String number1 = "001!002!一二";
		String number2 = "001!002!四五";

		AutoNoCostSplitSortor sortor = new AutoNoCostSplitSortor();

		System.out.println("number1:" + number1);
		System.out.println("number2:" + number2);
		int value = sortor.compareNumber(number1, number2);
		System.out.println("Compare Value:" + value);
	}
}
