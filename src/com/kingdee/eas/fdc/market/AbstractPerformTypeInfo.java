package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPerformTypeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractPerformTypeInfo()
    {
        this("id");
    }
    protected AbstractPerformTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:执行方式类型's 简称property 
     */
    public String getShortName()
    {
        return getString("shortName");
    }
    public void setShortName(String item)
    {
        setString("shortName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1EF2CFCA");
    }
}