package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HeadTypeFactory
{
    private HeadTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IHeadType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IHeadType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3AB9005E") ,com.kingdee.eas.fdc.invite.IHeadType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IHeadType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IHeadType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3AB9005E") ,com.kingdee.eas.fdc.invite.IHeadType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IHeadType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IHeadType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3AB9005E"));
    }
    public static com.kingdee.eas.fdc.invite.IHeadType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IHeadType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3AB9005E"));
    }
}