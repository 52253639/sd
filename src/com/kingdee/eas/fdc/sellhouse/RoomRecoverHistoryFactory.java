package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomRecoverHistoryFactory
{
    private RoomRecoverHistoryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomRecoverHistory getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomRecoverHistory)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E80BEE66") ,com.kingdee.eas.fdc.sellhouse.IRoomRecoverHistory.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomRecoverHistory getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomRecoverHistory)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E80BEE66") ,com.kingdee.eas.fdc.sellhouse.IRoomRecoverHistory.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomRecoverHistory getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomRecoverHistory)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E80BEE66"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomRecoverHistory getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomRecoverHistory)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E80BEE66"));
    }
}