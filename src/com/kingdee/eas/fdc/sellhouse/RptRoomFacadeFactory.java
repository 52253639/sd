package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RptRoomFacadeFactory
{
    private RptRoomFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRptRoomFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRptRoomFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("739ABCF0") ,com.kingdee.eas.fdc.sellhouse.IRptRoomFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRptRoomFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRptRoomFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("739ABCF0") ,com.kingdee.eas.fdc.sellhouse.IRptRoomFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRptRoomFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRptRoomFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("739ABCF0"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRptRoomFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRptRoomFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("739ABCF0"));
    }
}