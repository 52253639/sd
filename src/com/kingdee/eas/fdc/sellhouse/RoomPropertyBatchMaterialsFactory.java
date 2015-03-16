package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPropertyBatchMaterialsFactory
{
    private RoomPropertyBatchMaterialsFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchMaterials getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchMaterials)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("78AADD1D") ,com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchMaterials.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchMaterials getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchMaterials)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("78AADD1D") ,com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchMaterials.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchMaterials getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchMaterials)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("78AADD1D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchMaterials getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBatchMaterials)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("78AADD1D"));
    }
}