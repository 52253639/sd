package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomAssistantTypeFactory
{
    private RoomAssistantTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomAssistantType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAssistantType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("380DCCA2") ,com.kingdee.eas.fdc.sellhouse.IRoomAssistantType.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomAssistantType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAssistantType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("380DCCA2") ,com.kingdee.eas.fdc.sellhouse.IRoomAssistantType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomAssistantType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAssistantType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("380DCCA2"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomAssistantType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomAssistantType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("380DCCA2"));
    }
}