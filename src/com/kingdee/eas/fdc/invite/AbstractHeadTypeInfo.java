package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractHeadTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractHeadTypeInfo()
    {
        this("id");
    }
    protected AbstractHeadTypeInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.fdc.invite.HeadColumnCollection());
    }
    /**
     * Object:表头类型's 是否启用property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object: 表头类型 's 父节点 property 
     */
    public com.kingdee.eas.fdc.invite.HeadTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.HeadTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.HeadTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 表头类型 's 所属组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrg()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("org");
    }
    public void setOrg(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("org", item);
    }
    /**
     * Object: 表头类型 's 表头 property 
     */
    public com.kingdee.eas.fdc.invite.HeadColumnCollection getEntries()
    {
        return (com.kingdee.eas.fdc.invite.HeadColumnCollection)get("entries");
    }
    /**
     * Object:表头类型's 供应商自定义property 
     */
    public boolean isIsDefined()
    {
        return getBoolean("isDefined");
    }
    public void setIsDefined(boolean item)
    {
        setBoolean("isDefined", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3AB9005E");
    }
}