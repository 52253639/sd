package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomJoinNoticeFactory
{
    private RoomJoinNoticeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomJoinNotice getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomJoinNotice)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D4FD6B78") ,com.kingdee.eas.fdc.sellhouse.IRoomJoinNotice.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomJoinNotice getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomJoinNotice)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D4FD6B78") ,com.kingdee.eas.fdc.sellhouse.IRoomJoinNotice.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomJoinNotice getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomJoinNotice)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D4FD6B78"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomJoinNotice getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomJoinNotice)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D4FD6B78"));
    }
}