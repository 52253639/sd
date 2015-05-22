package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SaleScheduleOnLoadDetailReportFacadeFactory
{
    private SaleScheduleOnLoadDetailReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleOnLoadDetailReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleOnLoadDetailReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("92F0A08E") ,com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleOnLoadDetailReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleOnLoadDetailReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleOnLoadDetailReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("92F0A08E") ,com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleOnLoadDetailReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleOnLoadDetailReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleOnLoadDetailReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("92F0A08E"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleOnLoadDetailReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISaleScheduleOnLoadDetailReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("92F0A08E"));
    }
}