package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAppraiseTemplateInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractAppraiseTemplateInfo()
    {
        this("id");
    }
    protected AbstractAppraiseTemplateInfo(String pkField)
    {
        super(pkField);
        put("templateEntry", new com.kingdee.eas.fdc.invite.AppraiseTemplateEntryCollection());
    }
    /**
     * Object: 评标模板 's 模板类型 property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo getTemplateType()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo)get("templateType");
    }
    public void setTemplateType(com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo item)
    {
        put("templateType", item);
    }
    /**
     * Object: 评标模板 's 模板分录 property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseTemplateEntryCollection getTemplateEntry()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseTemplateEntryCollection)get("templateEntry");
    }
    /**
     * Object:评标模板's 是否按权重计算评标分值property 
     */
    public boolean isIsUserWidth()
    {
        return getBoolean("isUserWidth");
    }
    public void setIsUserWidth(boolean item)
    {
        setBoolean("isUserWidth", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("831F2B29");
    }
}