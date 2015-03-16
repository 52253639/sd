package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketChargePaymentEntryInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractMarketChargePaymentEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketChargePaymentEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 营销活动费用付款明细 's 营销活动费用分录 property 
     */
    public com.kingdee.eas.fdc.market.MarketManageEntryInfo getMarketManageEntry()
    {
        return (com.kingdee.eas.fdc.market.MarketManageEntryInfo)get("marketManageEntry");
    }
    public void setMarketManageEntry(com.kingdee.eas.fdc.market.MarketManageEntryInfo item)
    {
        put("marketManageEntry", item);
    }
    /**
     * Object:营销活动费用付款明细's 费用已付款金额property 
     */
    public java.math.BigDecimal getPaidAmount()
    {
        return getBigDecimal("paidAmount");
    }
    public void setPaidAmount(java.math.BigDecimal item)
    {
        setBigDecimal("paidAmount", item);
    }
    /**
     * Object:营销活动费用付款明细's 付款金额property 
     */
    public java.math.BigDecimal getPayment()
    {
        return getBigDecimal("payment");
    }
    public void setPayment(java.math.BigDecimal item)
    {
        setBigDecimal("payment", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DE13BF0D");
    }
}