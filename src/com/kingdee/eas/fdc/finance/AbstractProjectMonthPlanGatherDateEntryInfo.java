package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthPlanGatherDateEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectMonthPlanGatherDateEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthPlanGatherDateEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:项目月度付款计划汇总明细分录's 月份property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    /**
     * Object:项目月度付款计划汇总明细分录's 年份property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object: 项目月度付款计划汇总明细分录 's 预算项目 property 
     */
    public com.kingdee.eas.ma.budget.BgItemInfo getBgItem()
    {
        return (com.kingdee.eas.ma.budget.BgItemInfo)get("bgItem");
    }
    public void setBgItem(com.kingdee.eas.ma.budget.BgItemInfo item)
    {
        put("bgItem", item);
    }
    /**
     * Object:项目月度付款计划汇总明细分录's 计划支付property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object: 项目月度付款计划汇总明细分录 's 项目年度付款计划分录 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryInfo getHeadEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryInfo)get("headEntry");
    }
    public void setHeadEntry(com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryInfo item)
    {
        put("headEntry", item);
    }
    /**
     * Object:项目月度付款计划汇总明细分录's 用款说明property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:项目月度付款计划汇总明细分录's 上报金额property 
     */
    public java.math.BigDecimal getReportAmount()
    {
        return getBigDecimal("reportAmount");
    }
    public void setReportAmount(java.math.BigDecimal item)
    {
        setBigDecimal("reportAmount", item);
    }
    /**
     * Object:项目月度付款计划汇总明细分录's 核定金额property 
     */
    public java.math.BigDecimal getExecuteAmount()
    {
        return getBigDecimal("executeAmount");
    }
    public void setExecuteAmount(java.math.BigDecimal item)
    {
        setBigDecimal("executeAmount", item);
    }
    /**
     * Object:项目月度付款计划汇总明细分录's 完成工程量property 
     */
    public java.math.BigDecimal getAmt()
    {
        return getBigDecimal("amt");
    }
    public void setAmt(java.math.BigDecimal item)
    {
        setBigDecimal("amt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("84AB89EA");
    }
}