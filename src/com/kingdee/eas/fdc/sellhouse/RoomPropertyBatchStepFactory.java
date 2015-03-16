package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPropertyBatchStepFactory
{
    private RoomPropertyBatchStepFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchStep getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchStep)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EF80573B") ,com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchStep.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchStep getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchStep)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EF80573B") ,com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchStep.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchStep getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchStep)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EF80573B"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchStep getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchStep)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EF80573B"));
    }
}