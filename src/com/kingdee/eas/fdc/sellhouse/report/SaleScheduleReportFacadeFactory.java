package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SaleScheduleReportFacadeFactory
{
    private SaleScheduleReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8FBE2858") ,com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8FBE2858") ,com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8FBE2858"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8FBE2858"));
    }
}