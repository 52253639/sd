package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AppraiseResultEntryFactory
{
    private AppraiseResultEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseResultEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseResultEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3D324946") ,com.kingdee.eas.fdc.invite.IAppraiseResultEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IAppraiseResultEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseResultEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3D324946") ,com.kingdee.eas.fdc.invite.IAppraiseResultEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseResultEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseResultEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3D324946"));
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseResultEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseResultEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3D324946"));
    }
}