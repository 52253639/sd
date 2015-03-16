package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractPayPlanNewPayByStageInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractContractPayPlanNewPayByStageInfo()
    {
        this("id");
    }
    protected AbstractContractPayPlanNewPayByStageInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分期 's null property 
     */
    public com.kingdee.eas.fdc.finance.ContractPayPlanNewInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.ContractPayPlanNewInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.ContractPayPlanNewInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分期's 计划付款日期property 
     */
    public java.util.Date getPayDate()
    {
        return getDate("payDate");
    }
    public void setPayDate(java.util.Date item)
    {
        setDate("payDate", item);
    }
    /**
     * Object:分期's 计划付款月份property 
     */
    public java.util.Date getMonth()
    {
        return getDate("month");
    }
    public void setMonth(java.util.Date item)
    {
        setDate("month", item);
    }
    /**
     * Object:分期's 计划付款比例property 
     */
    public java.math.BigDecimal getPayBili()
    {
        return getBigDecimal("payBili");
    }
    public void setPayBili(java.math.BigDecimal item)
    {
        setBigDecimal("payBili", item);
    }
    /**
     * Object:分期's 计划付款金额property 
     */
    public java.math.BigDecimal getPayMoney()
    {
        return getBigDecimal("payMoney");
    }
    public void setPayMoney(java.math.BigDecimal item)
    {
        setBigDecimal("payMoney", item);
    }
    /**
     * Object:分期's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("11F5712F");
    }
}