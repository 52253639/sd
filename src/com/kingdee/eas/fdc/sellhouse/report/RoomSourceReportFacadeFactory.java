package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomSourceReportFacadeFactory
{
    private RoomSourceReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomSourceReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomSourceReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2760CC30") ,com.kingdee.eas.fdc.sellhouse.report.IRoomSourceReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomSourceReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomSourceReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2760CC30") ,com.kingdee.eas.fdc.sellhouse.report.IRoomSourceReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomSourceReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomSourceReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2760CC30"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomSourceReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomSourceReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2760CC30"));
    }
}