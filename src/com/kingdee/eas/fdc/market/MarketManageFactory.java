package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketManageFactory
{
    private MarketManageFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMarketManage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5F2D3512") ,com.kingdee.eas.fdc.market.IMarketManage.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMarketManage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5F2D3512") ,com.kingdee.eas.fdc.market.IMarketManage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMarketManage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5F2D3512"));
    }
    public static com.kingdee.eas.fdc.market.IMarketManage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5F2D3512"));
    }
}