package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketManageMediaFactory
{
    private MarketManageMediaFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMarketManageMedia getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageMedia)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A8EFA5F2") ,com.kingdee.eas.fdc.market.IMarketManageMedia.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMarketManageMedia getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageMedia)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A8EFA5F2") ,com.kingdee.eas.fdc.market.IMarketManageMedia.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMarketManageMedia getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageMedia)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A8EFA5F2"));
    }
    public static com.kingdee.eas.fdc.market.IMarketManageMedia getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageMedia)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A8EFA5F2"));
    }
}