package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectYearPlanTotalEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectYearPlanTotalEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectYearPlanTotalEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:项目年度付款规划汇总分录's 年份property 
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
     * Object:项目年度付款规划汇总分录's 计划支付property 
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
     * Object: 项目年度付款规划汇总分录 's 项目年度付款规划 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectYearPlanInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.ProjectYearPlanInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.ProjectYearPlanInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C22F61FE");
    }
}