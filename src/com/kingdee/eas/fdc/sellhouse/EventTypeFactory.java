package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EventTypeFactory
{
    private EventTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IEventType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEventType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F348B879") ,com.kingdee.eas.fdc.sellhouse.IEventType.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IEventType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEventType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F348B879") ,com.kingdee.eas.fdc.sellhouse.IEventType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IEventType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEventType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F348B879"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IEventType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEventType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F348B879"));
    }
}