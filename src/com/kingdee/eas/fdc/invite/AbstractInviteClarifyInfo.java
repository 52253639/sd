package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteClarifyInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractInviteClarifyInfo()
    {
        this("id");
    }
    protected AbstractInviteClarifyInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.InviteClarifyEntryCollection());
    }
    /**
     * Object: Ñ¯±êÌ¸ÅÐ 's ·ÖÂ¼ property 
     */
    public com.kingdee.eas.fdc.invite.InviteClarifyEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.InviteClarifyEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("913E7275");
    }
}