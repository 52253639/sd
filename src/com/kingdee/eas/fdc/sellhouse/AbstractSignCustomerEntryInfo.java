package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSignCustomerEntryInfo extends com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo implements Serializable 
{
    public AbstractSignCustomerEntryInfo()
    {
        this("id");
    }
    protected AbstractSignCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 签约客户信息分录 's 签约单id property 
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
        return new BOSObjectType("6D87EAFC");
    }
}