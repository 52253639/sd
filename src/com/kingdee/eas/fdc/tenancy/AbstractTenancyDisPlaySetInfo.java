package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyDisPlaySetInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractTenancyDisPlaySetInfo()
    {
        this("id");
    }
    protected AbstractTenancyDisPlaySetInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:租赁功能设置's 设置值property 
     */
    public byte[] getValue()
    {
        return (byte[])get("value");
    }
    public void setValue(byte[] item)
    {
        put("value", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("01ADF0B7");
    }
}