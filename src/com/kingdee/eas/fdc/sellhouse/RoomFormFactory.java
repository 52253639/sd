package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomFormFactory
{
    private RoomFormFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomForm getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomForm)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6087781A") ,com.kingdee.eas.fdc.sellhouse.IRoomForm.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomForm getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomForm)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6087781A") ,com.kingdee.eas.fdc.sellhouse.IRoomForm.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomForm getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomForm)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6087781A"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomForm getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomForm)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6087781A"));
    }
}