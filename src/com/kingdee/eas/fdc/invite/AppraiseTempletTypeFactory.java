package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AppraiseTempletTypeFactory
{
    private AppraiseTempletTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseTempletType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseTempletType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CEED1492") ,com.kingdee.eas.fdc.invite.IAppraiseTempletType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IAppraiseTempletType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseTempletType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CEED1492") ,com.kingdee.eas.fdc.invite.IAppraiseTempletType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseTempletType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseTempletType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CEED1492"));
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseTempletType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseTempletType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CEED1492"));
    }
}