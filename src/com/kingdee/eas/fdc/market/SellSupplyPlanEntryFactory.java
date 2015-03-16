package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SellSupplyPlanEntryFactory
{
    private SellSupplyPlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.ISellSupplyPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISellSupplyPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1EFECB77") ,com.kingdee.eas.fdc.market.ISellSupplyPlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.ISellSupplyPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISellSupplyPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1EFECB77") ,com.kingdee.eas.fdc.market.ISellSupplyPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.ISellSupplyPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISellSupplyPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1EFECB77"));
    }
    public static com.kingdee.eas.fdc.market.ISellSupplyPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISellSupplyPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1EFECB77"));
    }
}