package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteBidEvaluationEntrysFactory
{
    private InviteBidEvaluationEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteBidEvaluationEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBidEvaluationEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EAEB134D") ,com.kingdee.eas.fdc.invite.IInviteBidEvaluationEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteBidEvaluationEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBidEvaluationEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EAEB134D") ,com.kingdee.eas.fdc.invite.IInviteBidEvaluationEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteBidEvaluationEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBidEvaluationEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EAEB134D"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteBidEvaluationEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBidEvaluationEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EAEB134D"));
    }
}