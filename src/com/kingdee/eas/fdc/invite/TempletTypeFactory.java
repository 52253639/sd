package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TempletTypeFactory
{
    private TempletTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.ITempletType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITempletType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3EF483FD") ,com.kingdee.eas.fdc.invite.ITempletType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.ITempletType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITempletType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3EF483FD") ,com.kingdee.eas.fdc.invite.ITempletType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.ITempletType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITempletType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3EF483FD"));
    }
    public static com.kingdee.eas.fdc.invite.ITempletType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITempletType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3EF483FD"));
    }
}