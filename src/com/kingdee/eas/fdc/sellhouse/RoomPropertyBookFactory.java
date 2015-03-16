package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPropertyBookFactory
{
    private RoomPropertyBookFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBook getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBook)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("04BB90B9") ,com.kingdee.eas.fdc.sellhouse.IRoomPropertyBook.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBook getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBook)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("04BB90B9") ,com.kingdee.eas.fdc.sellhouse.IRoomPropertyBook.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBook getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBook)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("04BB90B9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBook getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBook)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("04BB90B9"));
    }
}