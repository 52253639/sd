package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractHabitationAreaInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractHabitationAreaInfo()
    {
        this("id");
    }
    protected AbstractHabitationAreaInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 居住区域 's 父结点 property 
     */
    public com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DE3809C9");
    }
}