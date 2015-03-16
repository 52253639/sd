package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWorkAreaEntryInfo extends AbstractObjectValue implements Serializable 
{
    public AbstractWorkAreaEntryInfo()
    {
        this("id");
    }
    protected AbstractWorkAreaEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:工作区域f7多选分录's nullproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId()
    {
        return getBOSUuid("id");
    }
    public void setId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("id", item);
    }
    /**
     * Object: 工作区域f7多选分录 's  property 
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
        return new BOSObjectType("DE8E28D9");
    }
}