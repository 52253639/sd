package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomPropertyBookFacadeFactory
{
    private RoomPropertyBookFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("82CD556E") ,com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("82CD556E") ,com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("82CD556E"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomPropertyBookFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("82CD556E"));
    }
}