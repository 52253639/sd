package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHEPayTypeAgioEntryInfo extends com.kingdee.eas.fdc.sellhouse.AgioEntryInfo implements Serializable 
{
    public AbstractSHEPayTypeAgioEntryInfo()
    {
        this("id");
    }
    protected AbstractSHEPayTypeAgioEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 付款方案折扣分录 's 付款明细头id property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("14552C39");
    }
}