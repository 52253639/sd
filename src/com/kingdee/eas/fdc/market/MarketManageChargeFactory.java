package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketManageChargeFactory
{
    private MarketManageChargeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMarketManageCharge getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageCharge)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("641DAE66") ,com.kingdee.eas.fdc.market.IMarketManageCharge.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMarketManageCharge getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageCharge)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("641DAE66") ,com.kingdee.eas.fdc.market.IMarketManageCharge.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMarketManageCharge getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageCharge)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("641DAE66"));
    }
    public static com.kingdee.eas.fdc.market.IMarketManageCharge getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageCharge)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("641DAE66"));
    }
}