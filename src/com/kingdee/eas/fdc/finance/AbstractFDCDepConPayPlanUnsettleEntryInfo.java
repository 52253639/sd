package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCDepConPayPlanUnsettleEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCDepConPayPlanUnsettleEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCDepConPayPlanUnsettleEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 待签定合同中的支付 's 父结点 property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo getParent1()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object:待签定合同中的支付's 计划支付金额property 
     */
    public java.math.BigDecimal getPlanPay()
    {
        return getBigDecimal("planPay");
    }
    public void setPlanPay(java.math.BigDecimal item)
    {
        setBigDecimal("planPay", item);
    }
    /**
     * Object: 待签定合同中的支付 's 款项类型 property 
     */
    public com.kingdee.eas.fdc.basedata.PaymentTypeInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.basedata.PaymentTypeInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.basedata.PaymentTypeInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object:待签定合同中的支付's 用款说明property 
     */
    public String getExplain()
    {
        return getString("explain");
    }
    public void setExplain(String item)
    {
        setString("explain", item);
    }
    /**
     * Object:待签定合同中的支付's 最终批复金额property 
     */
    public java.math.BigDecimal getOfficialPay()
    {
        return getBigDecimal("officialPay");
    }
    public void setOfficialPay(java.math.BigDecimal item)
    {
        setBigDecimal("officialPay", item);
    }
    /**
     * Object:待签定合同中的支付's 计划月份property 
     */
    public java.util.Date getMonth()
    {
        return getDate("month");
    }
    public void setMonth(java.util.Date item)
    {
        setDate("month", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("08EEA566");
    }
}