package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SellOrderRoomEntryFactory
{
    private SellOrderRoomEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellOrderRoomEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellOrderRoomEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3EB8C2B6") ,com.kingdee.eas.fdc.sellhouse.ISellOrderRoomEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISellOrderRoomEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellOrderRoomEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3EB8C2B6") ,com.kingdee.eas.fdc.sellhouse.ISellOrderRoomEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellOrderRoomEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellOrderRoomEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3EB8C2B6"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellOrderRoomEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellOrderRoomEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3EB8C2B6"));
    }
}