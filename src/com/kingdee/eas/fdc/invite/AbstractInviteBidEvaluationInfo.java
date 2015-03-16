package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteBidEvaluationInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractInviteBidEvaluationInfo()
    {
        this("id");
    }
    protected AbstractInviteBidEvaluationInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.InviteBidEvaluationEntrysCollection());
    }
    /**
     * Object: ÆÀ±ê±¨¸æ 's null property 
     */
    public com.kingdee.eas.fdc.invite.InviteBidEvaluationEntrysCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.InviteBidEvaluationEntrysCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("441E41EC");
    }
}