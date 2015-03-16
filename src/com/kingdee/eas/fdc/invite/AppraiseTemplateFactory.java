package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AppraiseTemplateFactory
{
    private AppraiseTemplateFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseTemplate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseTemplate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("831F2B29") ,com.kingdee.eas.fdc.invite.IAppraiseTemplate.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IAppraiseTemplate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseTemplate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("831F2B29") ,com.kingdee.eas.fdc.invite.IAppraiseTemplate.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseTemplate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseTemplate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("831F2B29"));
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseTemplate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseTemplate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("831F2B29"));
    }
}