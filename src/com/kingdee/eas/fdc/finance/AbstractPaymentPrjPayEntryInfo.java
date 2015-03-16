package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPaymentPrjPayEntryInfo extends com.kingdee.eas.fdc.contract.PrjPayEntryBaseInfo implements Serializable 
{
    public AbstractPaymentPrjPayEntryInfo()
    {
        this("id");
    }
    protected AbstractPaymentPrjPayEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 付款单工程付款情况表 's 付款单 property 
     */
    public com.kingdee.eas.fi.cas.PaymentBillInfo getParent()
    {
        return (com.kingdee.eas.fi.cas.PaymentBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fi.cas.PaymentBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:付款单工程付款情况表's 发票号property 
     */
    public String getInvoiceNumber()
    {
        return getString("invoiceNumber");
    }
    public void setInvoiceNumber(String item)
    {
        setString("invoiceNumber", item);
    }
    /**
     * Object:付款单工程付款情况表's 发票金额property 
     */
    public java.math.BigDecimal getInvoiceAmt()
    {
        return getBigDecimal("invoiceAmt");
    }
    public void setInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("invoiceAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("15DF94DB");
    }
}