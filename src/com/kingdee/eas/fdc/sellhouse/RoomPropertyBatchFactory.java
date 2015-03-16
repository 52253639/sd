package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPropertyBatchFactory
{
    private RoomPropertyBatchFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatch getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatch)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("96195BCF") ,com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatch.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatch getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatch)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("96195BCF") ,com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatch.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatch getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatch)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("96195BCF"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatch getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatch)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("96195BCF"));
    }
}