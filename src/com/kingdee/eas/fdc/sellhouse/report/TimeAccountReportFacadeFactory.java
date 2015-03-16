package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TimeAccountReportFacadeFactory
{
    private TimeAccountReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ITimeAccountReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ITimeAccountReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("90C7FDE2") ,com.kingdee.eas.fdc.sellhouse.report.ITimeAccountReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.ITimeAccountReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ITimeAccountReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("90C7FDE2") ,com.kingdee.eas.fdc.sellhouse.report.ITimeAccountReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ITimeAccountReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ITimeAccountReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("90C7FDE2"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ITimeAccountReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ITimeAccountReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("90C7FDE2"));
    }
}