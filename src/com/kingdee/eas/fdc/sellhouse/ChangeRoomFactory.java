package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeRoomFactory
{
    private ChangeRoomFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeRoom getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeRoom)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BCA781C6") ,com.kingdee.eas.fdc.sellhouse.IChangeRoom.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChangeRoom getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeRoom)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BCA781C6") ,com.kingdee.eas.fdc.sellhouse.IChangeRoom.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeRoom getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeRoom)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BCA781C6"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeRoom getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeRoom)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BCA781C6"));
    }
}