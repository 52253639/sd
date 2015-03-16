package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPreconcertPostponeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPreconcertPostponeEntryInfo()
    {
        this("id");
    }
    protected AbstractPreconcertPostponeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PreconcertPostponeInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.PreconcertPostponeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.PreconcertPostponeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E68BEE5E");
    }
}