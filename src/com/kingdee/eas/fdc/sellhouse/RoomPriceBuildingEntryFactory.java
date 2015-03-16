package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPriceBuildingEntryFactory
{
    private RoomPriceBuildingEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceBuildingEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceBuildingEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C565102B") ,com.kingdee.eas.fdc.sellhouse.IRoomPriceBuildingEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceBuildingEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceBuildingEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C565102B") ,com.kingdee.eas.fdc.sellhouse.IRoomPriceBuildingEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceBuildingEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceBuildingEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C565102B"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceBuildingEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceBuildingEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C565102B"));
    }
}