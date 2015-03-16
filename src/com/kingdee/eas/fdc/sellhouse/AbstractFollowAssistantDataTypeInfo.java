package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFollowAssistantDataTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractFollowAssistantDataTypeInfo()
    {
        this("id");
    }
    protected AbstractFollowAssistantDataTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 跟进辅助资料类别 's parent property 
     */
    public com.kingdee.eas.fdc.sellhouse.FollowAssistantDataTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.FollowAssistantDataTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.FollowAssistantDataTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F203F616");
    }
}