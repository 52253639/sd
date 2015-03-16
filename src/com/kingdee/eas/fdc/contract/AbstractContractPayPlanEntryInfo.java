package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractPayPlanEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractPayPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractContractPayPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合同付款计划分录 's 付款类型 property 
     */
    public com.kingdee.eas.fdc.basedata.PaymentTypeInfo getPaymentType()
    {
        return (com.kingdee.eas.fdc.basedata.PaymentTypeInfo)get("paymentType");
    }
    public void setPaymentType(com.kingdee.eas.fdc.basedata.PaymentTypeInfo item)
    {
        put("paymentType", item);
    }
    /**
     * Object:合同付款计划分录's 计划付款比例property 
     */
    public java.math.BigDecimal getPayRate()
    {
        return getBigDecimal("payRate");
    }
    public void setPayRate(java.math.BigDecimal item)
    {
        setBigDecimal("payRate", item);
    }
    /**
     * Object:合同付款计划分录's 计划付款金额property 
     */
    public java.math.BigDecimal getPayAmount()
    {
        return getBigDecimal("payAmount");
    }
    public void setPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payAmount", item);
    }
    /**
     * Object:合同付款计划分录's 用款说明property 
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
     * Object: 合同付款计划分录 's 合同付款计划 property 
     */
    public com.kingdee.eas.fdc.contract.ContractPayPlanInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.ContractPayPlanInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.ContractPayPlanInfo item)
    {
        put("head", item);
    }
    /**
     * Object:合同付款计划分录's 计划月份property 
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
     * Object:合同付款计划分录's 计划年份property 
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
     * Object: 合同付款计划分录 's 预算项目 property 
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
     * Object:合同付款计划分录's 完成工程量property 
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
        return new BOSObjectType("13073BEE");
    }
}