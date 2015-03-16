package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BackAmountCycleReportFacadeFactory
{
    private BackAmountCycleReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IBackAmountCycleReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IBackAmountCycleReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("69328EC9") ,com.kingdee.eas.fdc.sellhouse.report.IBackAmountCycleReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.IBackAmountCycleReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IBackAmountCycleReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("69328EC9") ,com.kingdee.eas.fdc.sellhouse.report.IBackAmountCycleReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IBackAmountCycleReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IBackAmountCycleReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("69328EC9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IBackAmountCycleReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IBackAmountCycleReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("69328EC9"));
    }
}