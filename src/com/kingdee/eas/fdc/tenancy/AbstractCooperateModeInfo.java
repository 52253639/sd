package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCooperateModeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractCooperateModeInfo()
    {
        this("id");
    }
    protected AbstractCooperateModeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合作模式 's 父节点 property 
     */
    public com.kingdee.eas.fdc.tenancy.CooperateModeInfo getParent()
    {
        return (com.kingdee.eas.fdc.tenancy.CooperateModeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.tenancy.CooperateModeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D0889652");
    }
}