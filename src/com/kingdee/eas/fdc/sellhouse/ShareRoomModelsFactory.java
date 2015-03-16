package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ShareRoomModelsFactory
{
    private ShareRoomModelsFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IShareRoomModels getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareRoomModels)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("29A55029") ,com.kingdee.eas.fdc.sellhouse.IShareRoomModels.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IShareRoomModels getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareRoomModels)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("29A55029") ,com.kingdee.eas.fdc.sellhouse.IShareRoomModels.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IShareRoomModels getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareRoomModels)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("29A55029"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IShareRoomModels getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareRoomModels)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("29A55029"));
    }
}