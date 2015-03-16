package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomTransferFactory
{
    private RoomTransferFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomTransfer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomTransfer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("91BE8841") ,com.kingdee.eas.fdc.sellhouse.IRoomTransfer.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomTransfer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomTransfer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("91BE8841") ,com.kingdee.eas.fdc.sellhouse.IRoomTransfer.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomTransfer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomTransfer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("91BE8841"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomTransfer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomTransfer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("91BE8841"));
    }
}