package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomLoanReportFacadeFactory
{
    private RoomLoanReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomLoanReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomLoanReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4CE74745") ,com.kingdee.eas.fdc.sellhouse.report.IRoomLoanReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomLoanReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomLoanReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4CE74745") ,com.kingdee.eas.fdc.sellhouse.report.IRoomLoanReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomLoanReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomLoanReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4CE74745"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomLoanReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomLoanReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4CE74745"));
    }
}