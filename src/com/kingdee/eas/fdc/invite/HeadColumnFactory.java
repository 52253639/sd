package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HeadColumnFactory
{
    private HeadColumnFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IHeadColumn getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IHeadColumn)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("52E98B7A") ,com.kingdee.eas.fdc.invite.IHeadColumn.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IHeadColumn getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IHeadColumn)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("52E98B7A") ,com.kingdee.eas.fdc.invite.IHeadColumn.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IHeadColumn getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IHeadColumn)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("52E98B7A"));
    }
    public static com.kingdee.eas.fdc.invite.IHeadColumn getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IHeadColumn)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("52E98B7A"));
    }
}