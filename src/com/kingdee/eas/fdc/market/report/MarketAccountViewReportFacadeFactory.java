package com.kingdee.eas.fdc.market.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketAccountViewReportFacadeFactory
{
    private MarketAccountViewReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.report.IMarketAccountViewReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.report.IMarketAccountViewReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B26B9ECE") ,com.kingdee.eas.fdc.market.report.IMarketAccountViewReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.market.report.IMarketAccountViewReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.report.IMarketAccountViewReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B26B9ECE") ,com.kingdee.eas.fdc.market.report.IMarketAccountViewReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.report.IMarketAccountViewReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.report.IMarketAccountViewReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B26B9ECE"));
    }
    public static com.kingdee.eas.fdc.market.report.IMarketAccountViewReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.report.IMarketAccountViewReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B26B9ECE"));
    }
}