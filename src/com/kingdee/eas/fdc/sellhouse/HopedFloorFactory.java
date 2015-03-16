package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HopedFloorFactory
{
    private HopedFloorFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IHopedFloor getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopedFloor)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C347BB5F") ,com.kingdee.eas.fdc.sellhouse.IHopedFloor.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IHopedFloor getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopedFloor)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C347BB5F") ,com.kingdee.eas.fdc.sellhouse.IHopedFloor.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IHopedFloor getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopedFloor)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C347BB5F"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IHopedFloor getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopedFloor)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C347BB5F"));
    }
}