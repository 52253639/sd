package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPartAMainContractInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractPartAMainContractInfo()
    {
        this("id");
    }
    protected AbstractPartAMainContractInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 甲供主合同列表 's 付款单 property 
     */
    public com.kingdee.eas.fi.cas.PaymentBillInfo getPaymentBill()
    {
        return (com.kingdee.eas.fi.cas.PaymentBillInfo)get("paymentBill");
    }
    public void setPaymentBill(com.kingdee.eas.fi.cas.PaymentBillInfo item)
    {
        put("paymentBill", item);
    }
    /**
     * Object: 甲供主合同列表 's 主合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getMainContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("mainContractBill");
    }
    public void setMainContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("mainContractBill", item);
    }
    /**
     * Object:甲供主合同列表's 本币金额property 
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
     * Object:甲供主合同列表's 备注property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:甲供主合同列表's 原币金额property 
     */
    public java.math.BigDecimal getOriginalAmount()
    {
        return getBigDecimal("originalAmount");
    }
    public void setOriginalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("originalAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B2F9E68A");
    }
}