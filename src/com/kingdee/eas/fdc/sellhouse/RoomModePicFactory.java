package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomModePicFactory
{
    private RoomModePicFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomModePic getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomModePic)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A63545D1") ,com.kingdee.eas.fdc.sellhouse.IRoomModePic.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomModePic getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomModePic)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A63545D1") ,com.kingdee.eas.fdc.sellhouse.IRoomModePic.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomModePic getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomModePic)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A63545D1"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomModePic getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomModePic)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A63545D1"));
    }
}