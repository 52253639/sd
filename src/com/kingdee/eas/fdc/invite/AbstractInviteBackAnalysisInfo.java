package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteBackAnalysisInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractInviteBackAnalysisInfo()
    {
        this("id");
    }
    protected AbstractInviteBackAnalysisInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.InviteBackAnalysisEntrysCollection());
    }
    /**
     * Object: 回标分析 's 分录 property 
     */
    public com.kingdee.eas.fdc.invite.InviteBackAnalysisEntrysCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.InviteBackAnalysisEntrysCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8C0784B0");
    }
}