package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBusinessScopeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractBusinessScopeInfo()
    {
        this("id");
    }
    protected AbstractBusinessScopeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 业务范围 's 父节点 property 
     */
    public com.kingdee.eas.fdc.tenancy.BusinessScopeInfo getParent()
    {
        return (com.kingdee.eas.fdc.tenancy.BusinessScopeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.tenancy.BusinessScopeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5B406F6B");
    }
}