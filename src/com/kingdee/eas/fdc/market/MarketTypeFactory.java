package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketTypeFactory
{
    private MarketTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMarketType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("99052E27") ,com.kingdee.eas.fdc.market.IMarketType.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMarketType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("99052E27") ,com.kingdee.eas.fdc.market.IMarketType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMarketType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("99052E27"));
    }
    public static com.kingdee.eas.fdc.market.IMarketType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("99052E27"));
    }
}