package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import java.util.Calendar;

import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.util.DateTimeUtils;

public class FDCBudgetPeriodInfo extends AbstractFDCBudgetPeriodInfo implements Serializable 
{
    public FDCBudgetPeriodInfo()
    {
        super();
    }
    protected FDCBudgetPeriodInfo(String pkField)
    {
        super(pkField);
    }
    public String toString() {
    	if(isIsYear()){
    		return this.getYear()+"";
    	}else{
    		return this.getYear()+"-"+this.getMonth();
    	}
    	
    }
    public java.util.Date toDate(){
    	Calendar cal=Calendar.getInstance();
    	cal.set(getYear(), getMonth()-1, 1);
    	if(isIsYear()){
    		return FDCDateHelper.getYearDate(cal.getTime());
    	}else{
    		return FDCDateHelper.getMonthDate(cal.getTime());
    	}
    }
    /**
     * 根据当前月构造默认期间
     * @param isYear
     * @return
     */
    public static FDCBudgetPeriodInfo getCurrentPeriod(boolean isYear){
    	Calendar cal=Calendar.getInstance();
    	FDCBudgetPeriodInfo info=new FDCBudgetPeriodInfo();
    	info.setYear(cal.get(Calendar.YEAR));
    	info.setIsYear(isYear);
    	if(!isYear){
    		info.setMonth(cal.get(Calendar.MONTH)+1);
    	}
    	return info;
    }
    
    /**
     * 根据传入的参数返回一个新的期间
     * @param year
     * @param month
     * @param isYear
     * @return
     */
    public static FDCBudgetPeriodInfo getPeriod(int year,int month,boolean isYear){
    	FDCBudgetPeriodInfo info=new FDCBudgetPeriodInfo();
    	info.setYear(year);
    	info.setIsYear(isYear);
    	info.setMonth(month);
    	return info;
    }
    
    public static FDCBudgetPeriodInfo getPeriod(java.util.Date date,boolean isYear){
    	FDCBudgetPeriodInfo info=new FDCBudgetPeriodInfo();
    	info.setYear(DateTimeUtils.getYear(date));
    	info.setIsYear(isYear);
    	info.setMonth(DateTimeUtils.getMonth(date));
    	return info;
    }
    
    /**
     * 返回下一个期间,按12个月计算,没有ID
     * @return
     */
    public FDCBudgetPeriodInfo getPrePeriod(){
    	FDCBudgetPeriodInfo period=new FDCBudgetPeriodInfo();
    	period.setIsYear(isIsYear());
    	if(isIsYear()){
    		period.setYear(getYear()-1);
    	}else{
    		if(getMonth()==1){
    			period.setYear(getYear()-1);
    			period.setMonth(12);
    		}else{
    			period.setYear(getYear());
    			period.setMonth(getMonth()-1);
    		}
    	}
    	return period;
    }
    
    /**
     * 返回下一个期间,按12个月计算,没有ID
     * @return
     */
    public FDCBudgetPeriodInfo getNextPeriod(){
    	FDCBudgetPeriodInfo period=new FDCBudgetPeriodInfo();
    	period.setIsYear(isIsYear());
    	if(isIsYear()){
    		period.setYear(getYear()+1);
    	}else{
    		if(getMonth()==12){
    			period.setYear(getYear()+1);
    			period.setMonth(1);
    		}else{
    			period.setYear(getYear());
    			period.setMonth(getMonth()+1);
    		}
    	}
    	return period;
    }
    
}