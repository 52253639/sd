package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDepConPayPlanSplitItemInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDepConPayPlanSplitItemInfo()
    {
        this("id");
    }
    protected AbstractDepConPayPlanSplitItemInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 部门合同付款计划拆分条目 's 分录 property 
     */
    public com.kingdee.eas.fdc.finance.DepConPayPlanSplitEntryInfo getEntry()
    {
        return (com.kingdee.eas.fdc.finance.DepConPayPlanSplitEntryInfo)get("entry");
    }
    public void setEntry(com.kingdee.eas.fdc.finance.DepConPayPlanSplitEntryInfo item)
    {
        put("entry", item);
    }
    /**
     * Object:部门合同付款计划拆分条目's 年份property 
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
     * Object:部门合同付款计划拆分条目's 月份property 
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
     * Object:部门合同付款计划拆分条目's 计划金额property 
     */
    public java.math.BigDecimal getPlanAmt()
    {
        return getBigDecimal("planAmt");
    }
    public void setPlanAmt(java.math.BigDecimal item)
    {
        setBigDecimal("planAmt", item);
    }
    /**
     * Object:部门合同付款计划拆分条目's 拆分金额property 
     */
    public java.math.BigDecimal getSplitAmt()
    {
        return getBigDecimal("splitAmt");
    }
    public void setSplitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("splitAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AF17653E");
    }
}