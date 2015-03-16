package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomDesFactory
{
    private RoomDesFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomDes getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomDes)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A00455FC") ,com.kingdee.eas.fdc.sellhouse.IRoomDes.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomDes getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomDes)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A00455FC") ,com.kingdee.eas.fdc.sellhouse.IRoomDes.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomDes getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomDes)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A00455FC"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomDes getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomDes)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A00455FC"));
    }
}