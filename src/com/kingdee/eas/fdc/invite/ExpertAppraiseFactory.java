package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExpertAppraiseFactory
{
    private ExpertAppraiseFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IExpertAppraise getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertAppraise)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C35596D9") ,com.kingdee.eas.fdc.invite.IExpertAppraise.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IExpertAppraise getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertAppraise)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C35596D9") ,com.kingdee.eas.fdc.invite.IExpertAppraise.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IExpertAppraise getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertAppraise)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C35596D9"));
    }
    public static com.kingdee.eas.fdc.invite.IExpertAppraise getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertAppraise)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C35596D9"));
    }
}