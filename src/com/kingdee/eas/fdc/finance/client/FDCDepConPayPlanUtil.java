package com.kingdee.eas.fdc.finance.client;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import com.kingdee.eas.fdc.finance.FDCDepConPayPlanItemInfo;

public class FDCDepConPayPlanUtil implements Comparator{
	
	private Collator comparator = Collator.getInstance(Locale.getDefault());
	private static FDCDepConPayPlanUtil sortComparator=null;
	public static Comparator getSortComparator(){
		if(sortComparator==null){
				sortComparator=new FDCDepConPayPlanUtil();
		}
		return sortComparator;
	}
	public int compare(Object o1, Object o2) {
		if (o1 instanceof FDCDepConPayPlanItemInfo
				&& o2 instanceof FDCDepConPayPlanItemInfo) {
			int year1 =  ((FDCDepConPayPlanItemInfo)o1).getYear();
			int year2 =  ((FDCDepConPayPlanItemInfo)o2).getYear();
			int month1  = ((FDCDepConPayPlanItemInfo)o1).getMonth();
			int month2  = ((FDCDepConPayPlanItemInfo)o2).getMonth();
			if(year1 == year2){
				if(month1 > month2){
					return 1;
				}else{
					return -1;
				}
			}else{
				if(year1>year2){
					return 1;
				}else{
					return -1;
				}
			}
		}
		return 1;
	}
}
