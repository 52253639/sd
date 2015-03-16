package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomModelFactory
{
    private RoomModelFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomModel getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomModel)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B0C9FA93") ,com.kingdee.eas.fdc.sellhouse.IRoomModel.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomModel getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomModel)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B0C9FA93") ,com.kingdee.eas.fdc.sellhouse.IRoomModel.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomModel getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomModel)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B0C9FA93"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomModel getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomModel)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B0C9FA93"));
    }
}