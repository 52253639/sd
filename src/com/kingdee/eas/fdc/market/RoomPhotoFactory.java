package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPhotoFactory
{
    private RoomPhotoFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IRoomPhoto getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IRoomPhoto)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B1CDB366") ,com.kingdee.eas.fdc.market.IRoomPhoto.class);
    }
    
    public static com.kingdee.eas.fdc.market.IRoomPhoto getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IRoomPhoto)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B1CDB366") ,com.kingdee.eas.fdc.market.IRoomPhoto.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IRoomPhoto getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IRoomPhoto)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B1CDB366"));
    }
    public static com.kingdee.eas.fdc.market.IRoomPhoto getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IRoomPhoto)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B1CDB366"));
    }
}