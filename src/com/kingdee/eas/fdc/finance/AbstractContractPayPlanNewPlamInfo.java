package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractPayPlanNewPlamInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractContractPayPlanNewPlamInfo()
    {
        this("id");
    }
    protected AbstractContractPayPlanNewPlamInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 付款计划 's null property 
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
     * Object:付款计划's 计划付款月份property 
     */
    public String getPlanPayMonth()
    {
        return getString("planPayMonth");
    }
    public void setPlanPayMonth(String item)
    {
        setString("planPayMonth", item);
    }
    /**
     * Object:付款计划's 计划付款比例property 
     */
    public java.math.BigDecimal getPlanPayBili()
    {
        return getBigDecimal("planPayBili");
    }
    public void setPlanPayBili(java.math.BigDecimal item)
    {
        setBigDecimal("planPayBili", item);
    }
    /**
     * Object:付款计划's 计划付款金额property 
     */
    public java.math.BigDecimal getPlanPaymoney()
    {
        return getBigDecimal("planPaymoney");
    }
    public void setPlanPaymoney(java.math.BigDecimal item)
    {
        setBigDecimal("planPaymoney", item);
    }
    /**
     * Object:付款计划's 累计计划付款金额property 
     */
    public java.math.BigDecimal getLeiJiMoney()
    {
        return getBigDecimal("leiJiMoney");
    }
    public void setLeiJiMoney(java.math.BigDecimal item)
    {
        setBigDecimal("leiJiMoney", item);
    }
    /**
     * Object:付款计划's 累计计划付款比例property 
     */
    public java.math.BigDecimal getLeiJiBili()
    {
        return getBigDecimal("leiJiBili");
    }
    public void setLeiJiBili(java.math.BigDecimal item)
    {
        setBigDecimal("leiJiBili", item);
    }
    /**
     * Object:付款计划's 备注property 
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
     * Object:付款计划's 实付金额property 
     */
    public java.math.BigDecimal getSfje()
    {
        return getBigDecimal("sfje");
    }
    public void setSfje(java.math.BigDecimal item)
    {
        setBigDecimal("sfje", item);
    }
    /**
     * Object:付款计划's 累计实付金额property 
     */
    public java.math.BigDecimal getLjsfje()
    {
        return getBigDecimal("ljsfje");
    }
    public void setLjsfje(java.math.BigDecimal item)
    {
        setBigDecimal("ljsfje", item);
    }
    /**
     * Object:付款计划's 累计实付比例property 
     */
    public java.math.BigDecimal getLjsfbl()
    {
        return getBigDecimal("ljsfbl");
    }
    public void setLjsfbl(java.math.BigDecimal item)
    {
        setBigDecimal("ljsfbl", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("27E18938");
    }
}