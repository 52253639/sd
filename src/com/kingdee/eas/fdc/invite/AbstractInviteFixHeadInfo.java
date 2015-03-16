package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteFixHeadInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractInviteFixHeadInfo()
    {
        this("id");
    }
    protected AbstractInviteFixHeadInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.InviteFixCollection());
    }
    /**
     * Object: 修正系数表头 's 分录 property 
     */
    public com.kingdee.eas.fdc.invite.InviteFixCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.InviteFixCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2C038FA8");
    }
}