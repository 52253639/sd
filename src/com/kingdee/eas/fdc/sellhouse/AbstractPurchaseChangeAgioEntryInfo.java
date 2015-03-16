package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseChangeAgioEntryInfo extends com.kingdee.eas.fdc.sellhouse.AgioEntryInfo implements Serializable 
{
    public AbstractPurchaseChangeAgioEntryInfo()
    {
        this("id");
    }
    protected AbstractPurchaseChangeAgioEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 认购变更折扣分录 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8CB3A5BA");
    }
}