package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BatchRoomEntryFactory
{
    private BatchRoomEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBatchRoomEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBatchRoomEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AD1B7DD8") ,com.kingdee.eas.fdc.sellhouse.IBatchRoomEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBatchRoomEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBatchRoomEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AD1B7DD8") ,com.kingdee.eas.fdc.sellhouse.IBatchRoomEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBatchRoomEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBatchRoomEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AD1B7DD8"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBatchRoomEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBatchRoomEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AD1B7DD8"));
    }
}