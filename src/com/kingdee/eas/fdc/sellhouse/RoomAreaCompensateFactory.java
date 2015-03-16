package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomAreaCompensateFactory
{
    private RoomAreaCompensateFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomAreaCompensate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAreaCompensate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("455E117A") ,com.kingdee.eas.fdc.sellhouse.IRoomAreaCompensate.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomAreaCompensate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAreaCompensate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("455E117A") ,com.kingdee.eas.fdc.sellhouse.IRoomAreaCompensate.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomAreaCompensate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAreaCompensate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("455E117A"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomAreaCompensate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAreaCompensate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("455E117A"));
    }
}