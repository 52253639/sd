package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSincerityPurchaseCustomerEntryInfo extends com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo implements Serializable 
{
    public AbstractSincerityPurchaseCustomerEntryInfo()
    {
        this("id");
    }
    protected AbstractSincerityPurchaseCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 房间排号客户分录 's 预留排号头 property 
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
        return new BOSObjectType("282C39B8");
    }
}