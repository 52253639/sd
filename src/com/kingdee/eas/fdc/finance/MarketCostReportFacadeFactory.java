package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketCostReportFacadeFactory
{
    private MarketCostReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IMarketCostReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IMarketCostReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FA359386") ,com.kingdee.eas.fdc.finance.IMarketCostReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IMarketCostReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IMarketCostReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FA359386") ,com.kingdee.eas.fdc.finance.IMarketCostReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IMarketCostReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IMarketCostReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FA359386"));
    }
    public static com.kingdee.eas.fdc.finance.IMarketCostReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IMarketCostReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FA359386"));
    }
}