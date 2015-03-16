package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAppraiseTemplateEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractAppraiseTemplateEntryInfo()
    {
        this("id");
    }
    protected AbstractAppraiseTemplateEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评标模板分录 's 评标模板 property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseTemplateInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseTemplateInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.AppraiseTemplateInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 评标模板分录 's 评标指标类型 property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo getGuideLineType()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo)get("guideLineType");
    }
    public void setGuideLineType(com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo item)
    {
        put("guideLineType", item);
    }
    /**
     * Object: 评标模板分录 's 评标指标 property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseGuideLineInfo getGuideLine()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseGuideLineInfo)get("guideLine");
    }
    public void setGuideLine(com.kingdee.eas.fdc.invite.AppraiseGuideLineInfo item)
    {
        put("guideLine", item);
    }
    /**
     * Object:评标模板分录's 权重property 
     */
    public java.math.BigDecimal getWeight()
    {
        return getBigDecimal("weight");
    }
    public void setWeight(java.math.BigDecimal item)
    {
        setBigDecimal("weight", item);
    }
    /**
     * Object:评标模板分录's 备注property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("429A7EC9");
    }
}