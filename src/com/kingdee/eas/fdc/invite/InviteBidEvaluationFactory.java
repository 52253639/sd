package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteBidEvaluationFactory
{
    private InviteBidEvaluationFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteBidEvaluation getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBidEvaluation)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("441E41EC") ,com.kingdee.eas.fdc.invite.IInviteBidEvaluation.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteBidEvaluation getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBidEvaluation)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("441E41EC") ,com.kingdee.eas.fdc.invite.IInviteBidEvaluation.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteBidEvaluation getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBidEvaluation)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("441E41EC"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteBidEvaluation getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBidEvaluation)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("441E41EC"));
    }
}