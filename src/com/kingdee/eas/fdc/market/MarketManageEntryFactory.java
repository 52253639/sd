package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketManageEntryFactory
{
    private MarketManageEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMarketManageEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A8833E80") ,com.kingdee.eas.fdc.market.IMarketManageEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMarketManageEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A8833E80") ,com.kingdee.eas.fdc.market.IMarketManageEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMarketManageEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A8833E80"));
    }
    public static com.kingdee.eas.fdc.market.IMarketManageEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A8833E80"));
    }
}