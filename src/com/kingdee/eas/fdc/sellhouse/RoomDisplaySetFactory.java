package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomDisplaySetFactory
{
    private RoomDisplaySetFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomDisplaySet getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomDisplaySet)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4CB06F16") ,com.kingdee.eas.fdc.sellhouse.IRoomDisplaySet.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomDisplaySet getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomDisplaySet)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4CB06F16") ,com.kingdee.eas.fdc.sellhouse.IRoomDisplaySet.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomDisplaySet getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomDisplaySet)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4CB06F16"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomDisplaySet getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomDisplaySet)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4CB06F16"));
    }
}