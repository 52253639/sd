package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteTenderPlanningEntryInfo extends com.kingdee.eas.fdc.invite.BaseInviteEntryInfo implements Serializable 
{
    public AbstractInviteTenderPlanningEntryInfo()
    {
        this("id");
    }
    protected AbstractInviteTenderPlanningEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 招标策划分录 's 招标策划 property 
     */
    public com.kingdee.eas.fdc.invite.InviteTenderPlanningInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteTenderPlanningInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteTenderPlanningInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("66968834");
    }
}