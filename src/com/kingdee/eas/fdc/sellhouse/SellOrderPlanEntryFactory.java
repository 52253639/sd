package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SellOrderPlanEntryFactory
{
    private SellOrderPlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellOrderPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellOrderPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FBA9F928") ,com.kingdee.eas.fdc.sellhouse.ISellOrderPlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISellOrderPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellOrderPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FBA9F928") ,com.kingdee.eas.fdc.sellhouse.ISellOrderPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellOrderPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellOrderPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FBA9F928"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellOrderPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellOrderPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FBA9F928"));
    }
}