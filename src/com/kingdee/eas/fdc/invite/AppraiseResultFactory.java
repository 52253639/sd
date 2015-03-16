package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AppraiseResultFactory
{
    private AppraiseResultFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseResult getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseResult)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8FC8068C") ,com.kingdee.eas.fdc.invite.IAppraiseResult.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IAppraiseResult getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseResult)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8FC8068C") ,com.kingdee.eas.fdc.invite.IAppraiseResult.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseResult getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseResult)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8FC8068C"));
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseResult getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseResult)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8FC8068C"));
    }
}