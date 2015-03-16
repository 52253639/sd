package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomSignContractFactory
{
    private RoomSignContractFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomSignContract getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomSignContract)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EA57FB45") ,com.kingdee.eas.fdc.sellhouse.IRoomSignContract.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomSignContract getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomSignContract)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EA57FB45") ,com.kingdee.eas.fdc.sellhouse.IRoomSignContract.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomSignContract getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomSignContract)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EA57FB45"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomSignContract getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomSignContract)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EA57FB45"));
    }
}