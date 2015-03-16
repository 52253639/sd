package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomAssistantFactory
{
    private RoomAssistantFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomAssistant getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAssistant)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BC8DAD48") ,com.kingdee.eas.fdc.sellhouse.IRoomAssistant.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomAssistant getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAssistant)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BC8DAD48") ,com.kingdee.eas.fdc.sellhouse.IRoomAssistant.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomAssistant getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAssistant)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BC8DAD48"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomAssistant getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAssistant)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BC8DAD48"));
    }
}