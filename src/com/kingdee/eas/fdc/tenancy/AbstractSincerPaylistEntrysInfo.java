package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSincerPaylistEntrysInfo extends com.kingdee.eas.fdc.basecrm.RevListInfo implements Serializable 
{
    public AbstractSincerPaylistEntrysInfo()
    {
        this("id");
    }
    protected AbstractSincerPaylistEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 诚意预留付款明细分录 's 诚意预留单 property 
     */
    public com.kingdee.eas.fdc.tenancy.SincerObligateInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.SincerObligateInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.SincerObligateInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9D1F2546");
    }
}