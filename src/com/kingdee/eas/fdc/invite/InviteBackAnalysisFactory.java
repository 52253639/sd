package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteBackAnalysisFactory
{
    private InviteBackAnalysisFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteBackAnalysis getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBackAnalysis)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8C0784B0") ,com.kingdee.eas.fdc.invite.IInviteBackAnalysis.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteBackAnalysis getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBackAnalysis)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8C0784B0") ,com.kingdee.eas.fdc.invite.IInviteBackAnalysis.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteBackAnalysis getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBackAnalysis)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8C0784B0"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteBackAnalysis getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBackAnalysis)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8C0784B0"));
    }
}