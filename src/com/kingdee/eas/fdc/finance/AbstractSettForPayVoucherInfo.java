package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettForPayVoucherInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractSettForPayVoucherInfo()
    {
        this("id");
    }
    protected AbstractSettForPayVoucherInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.SettForPayVoucherEntryCollection());
    }
    /**
     * Object:预提成本冲销's 是否已生成凭证property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    /**
     * Object: 预提成本冲销 's 财务成本一体化科目 property 
     */
    public com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo getBeAccount()
    {
        return (com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo)get("beAccount");
    }
    public void setBeAccount(com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo item)
    {
        put("beAccount", item);
    }
    /**
     * Object: 预提成本冲销 's 分录 property 
     */
    public com.kingdee.eas.fdc.finance.SettForPayVoucherEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.SettForPayVoucherEntryCollection)get("entrys");
    }
    /**
     * Object: 预提成本冲销 's 付款单 property 
     */
    public com.kingdee.eas.fi.cas.PaymentBillInfo getPaymentBill()
    {
        return (com.kingdee.eas.fi.cas.PaymentBillInfo)get("paymentBill");
    }
    public void setPaymentBill(com.kingdee.eas.fi.cas.PaymentBillInfo item)
    {
        put("paymentBill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CE66FA2E");
    }
}