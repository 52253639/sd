package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonthPlanEntryFactory
{
    private MonthPlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMonthPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMonthPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("071C503A") ,com.kingdee.eas.fdc.market.IMonthPlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMonthPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMonthPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("071C503A") ,com.kingdee.eas.fdc.market.IMonthPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMonthPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMonthPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("071C503A"));
    }
    public static com.kingdee.eas.fdc.market.IMonthPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMonthPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("071C503A"));
    }
}