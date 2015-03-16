package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSignPayListEntryInfo extends com.kingdee.eas.fdc.sellhouse.TranPayListEntryInfo implements Serializable 
{
    public AbstractSignPayListEntryInfo()
    {
        this("id");
    }
    protected AbstractSignPayListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 签约付款明细分录 's 签约单id property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignManageInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignManageInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SignManageInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("94DD95E4");
    }
}