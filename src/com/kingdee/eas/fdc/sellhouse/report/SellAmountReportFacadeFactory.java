package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SellAmountReportFacadeFactory
{
    private SellAmountReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISellAmountReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISellAmountReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AA237FA4") ,com.kingdee.eas.fdc.sellhouse.report.ISellAmountReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.ISellAmountReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISellAmountReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AA237FA4") ,com.kingdee.eas.fdc.sellhouse.report.ISellAmountReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISellAmountReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISellAmountReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AA237FA4"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISellAmountReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISellAmountReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AA237FA4"));
    }
}