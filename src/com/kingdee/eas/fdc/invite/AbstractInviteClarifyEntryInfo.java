package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteClarifyEntryInfo extends com.kingdee.eas.fdc.invite.BaseInviteEntryInfo implements Serializable 
{
    public AbstractInviteClarifyEntryInfo()
    {
        this("id");
    }
    protected AbstractInviteClarifyEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 询标谈判分录 's 询标谈判 property 
     */
    public com.kingdee.eas.fdc.invite.InviteClarifyInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteClarifyInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteClarifyInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D99FE6FD");
    }
}