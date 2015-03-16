package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPriceBillFactory
{
    private RoomPriceBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E897B75A") ,com.kingdee.eas.fdc.sellhouse.IRoomPriceBill.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E897B75A") ,com.kingdee.eas.fdc.sellhouse.IRoomPriceBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E897B75A"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPriceBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPriceBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E897B75A"));
    }
}