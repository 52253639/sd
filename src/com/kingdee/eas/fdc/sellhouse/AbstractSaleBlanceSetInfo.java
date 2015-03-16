package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSaleBlanceSetInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractSaleBlanceSetInfo()
    {
        this("id");
    }
    protected AbstractSaleBlanceSetInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:销售结算设置's 设置值property 
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
        return new BOSObjectType("EAEBCD87");
    }
}