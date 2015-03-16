package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPriceBillEntryFactory
{
    private RoomPriceBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C236E938") ,com.kingdee.eas.fdc.sellhouse.IRoomPriceBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C236E938") ,com.kingdee.eas.fdc.sellhouse.IRoomPriceBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C236E938"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C236E938"));
    }
}