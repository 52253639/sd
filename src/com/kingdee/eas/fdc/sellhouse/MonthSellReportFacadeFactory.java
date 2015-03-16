package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonthSellReportFacadeFactory
{
    private MonthSellReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthSellReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthSellReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7B985A05") ,com.kingdee.eas.fdc.sellhouse.IMonthSellReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMonthSellReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthSellReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7B985A05") ,com.kingdee.eas.fdc.sellhouse.IMonthSellReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthSellReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthSellReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7B985A05"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthSellReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthSellReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7B985A05"));
    }
}