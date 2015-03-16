package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExpertTypeFactory
{
    private ExpertTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IExpertType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("12A8B2A8") ,com.kingdee.eas.fdc.invite.IExpertType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IExpertType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("12A8B2A8") ,com.kingdee.eas.fdc.invite.IExpertType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IExpertType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("12A8B2A8"));
    }
    public static com.kingdee.eas.fdc.invite.IExpertType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IExpertType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("12A8B2A8"));
    }
}