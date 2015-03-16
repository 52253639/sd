package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseAgioEntryInfo extends com.kingdee.eas.fdc.sellhouse.AgioEntryInfo implements Serializable 
{
    public AbstractPurchaseAgioEntryInfo()
    {
        this("id");
    }
    protected AbstractPurchaseAgioEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: »œπ∫’€ø€∑÷¬º 's Õ∑ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7FEF668A");
    }
}