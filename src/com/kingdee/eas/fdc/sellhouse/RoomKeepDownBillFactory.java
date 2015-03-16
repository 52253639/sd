package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomKeepDownBillFactory
{
    private RoomKeepDownBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomKeepDownBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomKeepDownBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("10008BC4") ,com.kingdee.eas.fdc.sellhouse.IRoomKeepDownBill.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomKeepDownBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomKeepDownBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("10008BC4") ,com.kingdee.eas.fdc.sellhouse.IRoomKeepDownBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomKeepDownBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomKeepDownBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("10008BC4"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomKeepDownBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomKeepDownBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("10008BC4"));
    }
}