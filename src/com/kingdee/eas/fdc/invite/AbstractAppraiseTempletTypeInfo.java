package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAppraiseTempletTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractAppraiseTempletTypeInfo()
    {
        this("id");
    }
    protected AbstractAppraiseTempletTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:评标模板类别's 启用property 
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
     * Object: 评标模板类别 's 所属组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object: 评标模板类别 's 上级评标模板类型 property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CEED1492");
    }
}