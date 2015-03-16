package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPayEntryFactory
{
    private RoomPayEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPayEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPayEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("95A06100") ,com.kingdee.eas.fdc.sellhouse.IRoomPayEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomPayEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPayEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("95A06100") ,com.kingdee.eas.fdc.sellhouse.IRoomPayEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPayEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPayEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("95A06100"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPayEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPayEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("95A06100"));
    }
}