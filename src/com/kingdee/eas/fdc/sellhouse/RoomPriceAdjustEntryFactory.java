package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPriceAdjustEntryFactory
{
    private RoomPriceAdjustEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceAdjustEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceAdjustEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BD32DB70") ,com.kingdee.eas.fdc.sellhouse.IRoomPriceAdjustEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceAdjustEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceAdjustEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BD32DB70") ,com.kingdee.eas.fdc.sellhouse.IRoomPriceAdjustEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceAdjustEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceAdjustEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BD32DB70"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceAdjustEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceAdjustEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BD32DB70"));
    }
}