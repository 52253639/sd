package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSchedulePropBaseInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSchedulePropBaseInfo()
    {
        this("id");
    }
    protected AbstractSchedulePropBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:进度属性基类's 名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5683C311");
    }
}