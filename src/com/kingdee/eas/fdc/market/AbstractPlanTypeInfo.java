package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPlanTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractPlanTypeInfo()
    {
        this("id");
    }
    protected AbstractPlanTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 计划类型 's 父节点 property 
     */
    public com.kingdee.eas.fdc.market.PlanTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.PlanTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.PlanTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:计划类型's 启用property 
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
        return new BOSObjectType("A07BD6D4");
    }
}