package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomJoinApproachEntryFactory
{
    private RoomJoinApproachEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomJoinApproachEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomJoinApproachEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7FEF410A") ,com.kingdee.eas.fdc.sellhouse.IRoomJoinApproachEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomJoinApproachEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomJoinApproachEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7FEF410A") ,com.kingdee.eas.fdc.sellhouse.IRoomJoinApproachEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomJoinApproachEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomJoinApproachEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7FEF410A"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomJoinApproachEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomJoinApproachEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7FEF410A"));
    }
}