package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomMortagageFactory
{
    private RoomMortagageFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomMortagage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomMortagage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("ED19B09F") ,com.kingdee.eas.fdc.sellhouse.IRoomMortagage.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomMortagage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomMortagage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("ED19B09F") ,com.kingdee.eas.fdc.sellhouse.IRoomMortagage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomMortagage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomMortagage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("ED19B09F"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomMortagage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomMortagage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("ED19B09F"));
    }
}