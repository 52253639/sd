package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWorkAreaInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractWorkAreaInfo()
    {
        this("id");
    }
    protected AbstractWorkAreaInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 工作区域 's 父结点 property 
     */
    public com.kingdee.eas.fdc.sellhouse.WorkAreaInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.WorkAreaInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.WorkAreaInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6CED7719");
    }
}