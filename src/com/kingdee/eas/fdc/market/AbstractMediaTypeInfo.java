package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMediaTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractMediaTypeInfo()
    {
        this("id");
    }
    protected AbstractMediaTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 媒体类型 's 父节点 property 
     */
    public com.kingdee.eas.fdc.market.MediaTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.MediaTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.MediaTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:媒体类型's 启用property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("71DA768D");
    }
}