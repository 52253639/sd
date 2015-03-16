package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomJoinFactory
{
    private RoomJoinFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomJoin getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomJoin)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("60894880") ,com.kingdee.eas.fdc.sellhouse.IRoomJoin.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomJoin getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomJoin)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("60894880") ,com.kingdee.eas.fdc.sellhouse.IRoomJoin.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomJoin getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomJoin)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("60894880"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomJoin getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomJoin)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("60894880"));
    }
}