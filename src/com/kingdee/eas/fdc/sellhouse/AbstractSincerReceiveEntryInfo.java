package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSincerReceiveEntryInfo extends com.kingdee.eas.fdc.sellhouse.TranPayListEntryInfo implements Serializable 
{
    public AbstractSincerReceiveEntryInfo()
    {
        this("id");
    }
    protected AbstractSincerReceiveEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 诚意认购付款分录 's 诚意认购单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A0835602");
    }
}