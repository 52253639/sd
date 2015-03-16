package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTemplateTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractTemplateTypeInfo()
    {
        this("id");
    }
    protected AbstractTemplateTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评标模板类型 's 上级类别 property 
     */
    public com.kingdee.eas.fdc.invite.TemplateTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.TemplateTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.TemplateTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 评标模板类型 's 所属组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getFullOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("fullOrgUnit");
    }
    public void setFullOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("fullOrgUnit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CCE1EFF8");
    }
}