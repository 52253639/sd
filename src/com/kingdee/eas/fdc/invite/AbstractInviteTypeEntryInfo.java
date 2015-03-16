package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteTypeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteTypeEntryInfo()
    {
        this("id");
    }
    protected AbstractInviteTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:表头's 名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:表头's 是否报价列property 
     */
    public boolean isIsQuoting()
    {
        return getBoolean("isQuoting");
    }
    public void setIsQuoting(boolean item)
    {
        setBoolean("isQuoting", item);
    }
    /**
     * Object:表头's 描述property 
     */
    public com.kingdee.eas.fdc.invite.DescriptionEnum getDescription()
    {
        return com.kingdee.eas.fdc.invite.DescriptionEnum.getEnum(getString("description"));
    }
    public void setDescription(com.kingdee.eas.fdc.invite.DescriptionEnum item)
    {
        setString("description", item.getValue());
    }
    /**
     * Object: 表头 's 表头类型 property 
     */
    public com.kingdee.eas.fdc.invite.InviteTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5BBD18AB");
    }
}