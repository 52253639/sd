package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomUsageFactory
{
    private RoomUsageFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomUsage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomUsage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B13C7D0B") ,com.kingdee.eas.fdc.sellhouse.IRoomUsage.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomUsage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomUsage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B13C7D0B") ,com.kingdee.eas.fdc.sellhouse.IRoomUsage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomUsage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomUsage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B13C7D0B"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomUsage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomUsage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B13C7D0B"));
    }
}