package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPropertyBookEntryFactory
{
    private RoomPropertyBookEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A117791E") ,com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A117791E") ,com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A117791E"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A117791E"));
    }
}