package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomKeepCustomerEntryFactory
{
    private RoomKeepCustomerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomKeepCustomerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomKeepCustomerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("615EA819") ,com.kingdee.eas.fdc.sellhouse.IRoomKeepCustomerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomKeepCustomerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomKeepCustomerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("615EA819") ,com.kingdee.eas.fdc.sellhouse.IRoomKeepCustomerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomKeepCustomerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomKeepCustomerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("615EA819"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomKeepCustomerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomKeepCustomerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("615EA819"));
    }
}