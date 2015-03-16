package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomFactory
{
    private RoomFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoom getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoom)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("903E0236") ,com.kingdee.eas.fdc.sellhouse.IRoom.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoom getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoom)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("903E0236") ,com.kingdee.eas.fdc.sellhouse.IRoom.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoom getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoom)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("903E0236"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoom getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoom)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("903E0236"));
    }
}