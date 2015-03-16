package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractExpertTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractExpertTypeInfo()
    {
        this("id");
    }
    protected AbstractExpertTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 专家类别 's 父节点 property 
     */
    public com.kingdee.eas.fdc.invite.ExpertTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.ExpertTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.ExpertTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("12A8B2A8");
    }
}