package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteBidEvaluationEntrysInfo extends com.kingdee.eas.fdc.invite.BaseInviteEntryInfo implements Serializable 
{
    public AbstractInviteBidEvaluationEntrysInfo()
    {
        this("id");
    }
    protected AbstractInviteBidEvaluationEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评标报告分录 's  property 
     */
    public com.kingdee.eas.fdc.invite.InviteBidEvaluationInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteBidEvaluationInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteBidEvaluationInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EAEB134D");
    }
}