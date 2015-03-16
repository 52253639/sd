package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomLoanDetailReportFacadeFactory
{
    private RoomLoanDetailReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomLoanDetailReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomLoanDetailReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CFB2E4F6") ,com.kingdee.eas.fdc.sellhouse.report.IRoomLoanDetailReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomLoanDetailReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomLoanDetailReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CFB2E4F6") ,com.kingdee.eas.fdc.sellhouse.report.IRoomLoanDetailReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomLoanDetailReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomLoanDetailReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CFB2E4F6"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomLoanDetailReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomLoanDetailReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CFB2E4F6"));
    }
}