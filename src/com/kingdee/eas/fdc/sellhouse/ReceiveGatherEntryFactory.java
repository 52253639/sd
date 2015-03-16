package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ReceiveGatherEntryFactory
{
    private ReceiveGatherEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IReceiveGatherEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceiveGatherEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EF155E6F") ,com.kingdee.eas.fdc.sellhouse.IReceiveGatherEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IReceiveGatherEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceiveGatherEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EF155E6F") ,com.kingdee.eas.fdc.sellhouse.IReceiveGatherEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IReceiveGatherEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceiveGatherEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EF155E6F"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IReceiveGatherEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceiveGatherEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EF155E6F"));
    }
}