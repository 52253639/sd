package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketingCostFacadeFactory
{
    private MarketingCostFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMarketingCostFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketingCostFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1D8FAC1C") ,com.kingdee.eas.fdc.market.IMarketingCostFacade.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMarketingCostFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketingCostFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1D8FAC1C") ,com.kingdee.eas.fdc.market.IMarketingCostFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMarketingCostFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketingCostFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1D8FAC1C"));
    }
    public static com.kingdee.eas.fdc.market.IMarketingCostFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketingCostFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1D8FAC1C"));
    }
}