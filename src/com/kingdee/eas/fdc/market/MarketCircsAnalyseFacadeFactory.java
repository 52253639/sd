package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketCircsAnalyseFacadeFactory
{
    private MarketCircsAnalyseFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMarketCircsAnalyseFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketCircsAnalyseFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E9153A3E") ,com.kingdee.eas.fdc.market.IMarketCircsAnalyseFacade.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMarketCircsAnalyseFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketCircsAnalyseFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E9153A3E") ,com.kingdee.eas.fdc.market.IMarketCircsAnalyseFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMarketCircsAnalyseFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketCircsAnalyseFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E9153A3E"));
    }
    public static com.kingdee.eas.fdc.market.IMarketCircsAnalyseFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketCircsAnalyseFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E9153A3E"));
    }
}