package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPropertyBatchBooksFactory
{
    private RoomPropertyBatchBooksFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchBooks getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchBooks)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FF98E01B") ,com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchBooks.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchBooks getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchBooks)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FF98E01B") ,com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchBooks.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchBooks getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchBooks)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FF98E01B"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchBooks getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchBooks)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FF98E01B"));
    }
}