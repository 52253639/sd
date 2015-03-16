package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExpertAppraiseResultFactory
{
    private ExpertAppraiseResultFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IExpertAppraiseResult getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertAppraiseResult)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7F280456") ,com.kingdee.eas.fdc.invite.IExpertAppraiseResult.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IExpertAppraiseResult getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertAppraiseResult)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7F280456") ,com.kingdee.eas.fdc.invite.IExpertAppraiseResult.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IExpertAppraiseResult getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertAppraiseResult)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7F280456"));
    }
    public static com.kingdee.eas.fdc.invite.IExpertAppraiseResult getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertAppraiseResult)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7F280456"));
    }
}