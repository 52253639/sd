package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExpertAppraiseEntryScoreFactory
{
    private ExpertAppraiseEntryScoreFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IExpertAppraiseEntryScore getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertAppraiseEntryScore)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("94AA34F9") ,com.kingdee.eas.fdc.invite.IExpertAppraiseEntryScore.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IExpertAppraiseEntryScore getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertAppraiseEntryScore)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("94AA34F9") ,com.kingdee.eas.fdc.invite.IExpertAppraiseEntryScore.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IExpertAppraiseEntryScore getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertAppraiseEntryScore)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("94AA34F9"));
    }
    public static com.kingdee.eas.fdc.invite.IExpertAppraiseEntryScore getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertAppraiseEntryScore)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("94AA34F9"));
    }
}