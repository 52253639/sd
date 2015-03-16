package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketDisplaySetFactory
{
    private MarketDisplaySetFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMarketDisplaySet getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketDisplaySet)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2182556D") ,com.kingdee.eas.fdc.market.IMarketDisplaySet.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMarketDisplaySet getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketDisplaySet)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2182556D") ,com.kingdee.eas.fdc.market.IMarketDisplaySet.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMarketDisplaySet getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketDisplaySet)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2182556D"));
    }
    public static com.kingdee.eas.fdc.market.IMarketDisplaySet getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketDisplaySet)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2182556D"));
    }
}