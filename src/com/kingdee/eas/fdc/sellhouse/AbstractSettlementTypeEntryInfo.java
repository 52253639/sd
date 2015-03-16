package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettlementTypeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSettlementTypeEntryInfo()
    {
        this("id");
    }
    protected AbstractSettlementTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 结算方式分录 's 款项类型 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 结算方式分录 's 结算方式 property 
     */
    public com.kingdee.eas.basedata.assistant.SettlementTypeInfo getSettlementType()
    {
        return (com.kingdee.eas.basedata.assistant.SettlementTypeInfo)get("settlementType");
    }
    public void setSettlementType(com.kingdee.eas.basedata.assistant.SettlementTypeInfo item)
    {
        put("settlementType", item);
    }
    /**
     * Object:结算方式分录's 自动带出property 
     */
    public boolean isIsBrought()
    {
        return getBoolean("isBrought");
    }
    public void setIsBrought(boolean item)
    {
        setBoolean("isBrought", item);
    }
    /**
     * Object:结算方式分录's 默认收款帐号property 
     */
    public String getPaymentAccount()
    {
        return getString("paymentAccount");
    }
    public void setPaymentAccount(String item)
    {
        setString("paymentAccount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9F3C0F74");
    }
}