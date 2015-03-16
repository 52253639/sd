package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExpertFactory
{
    private ExpertFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IExpert getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpert)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E1E8864E") ,com.kingdee.eas.fdc.invite.IExpert.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IExpert getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpert)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E1E8864E") ,com.kingdee.eas.fdc.invite.IExpert.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IExpert getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpert)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E1E8864E"));
    }
    public static com.kingdee.eas.fdc.invite.IExpert getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpert)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E1E8864E"));
    }
}