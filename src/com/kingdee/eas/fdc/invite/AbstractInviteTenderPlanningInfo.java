package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteTenderPlanningInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractInviteTenderPlanningInfo()
    {
        this("id");
    }
    protected AbstractInviteTenderPlanningInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.InviteTenderPlanningEntryCollection());
    }
    /**
     * Object: 招标策划 's 分录 property 
     */
    public com.kingdee.eas.fdc.invite.InviteTenderPlanningEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.InviteTenderPlanningEntryCollection)get("entry");
    }
    /**
     * Object:招标策划's 招标范围property 
     */
    public String getScope()
    {
        return getString("scope");
    }
    public void setScope(String item)
    {
        setString("scope", item);
    }
    /**
     * Object:招标策划's 付款方式property 
     */
    public String getPayMethod()
    {
        return getString("payMethod");
    }
    public void setPayMethod(String item)
    {
        setString("payMethod", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2277AADE");
    }
}