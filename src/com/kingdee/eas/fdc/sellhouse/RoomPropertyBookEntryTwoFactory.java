package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPropertyBookEntryTwoFactory
{
    private RoomPropertyBookEntryTwoFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntryTwo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntryTwo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6A98D44E") ,com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntryTwo.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntryTwo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntryTwo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6A98D44E") ,com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntryTwo.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntryTwo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntryTwo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6A98D44E"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntryTwo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookEntryTwo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6A98D44E"));
    }
}