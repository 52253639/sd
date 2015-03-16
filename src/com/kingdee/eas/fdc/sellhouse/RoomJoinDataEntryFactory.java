package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomJoinDataEntryFactory
{
    private RoomJoinDataEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomJoinDataEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomJoinDataEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5678CA48") ,com.kingdee.eas.fdc.sellhouse.IRoomJoinDataEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomJoinDataEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomJoinDataEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5678CA48") ,com.kingdee.eas.fdc.sellhouse.IRoomJoinDataEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomJoinDataEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomJoinDataEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5678CA48"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomJoinDataEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomJoinDataEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5678CA48"));
    }
}