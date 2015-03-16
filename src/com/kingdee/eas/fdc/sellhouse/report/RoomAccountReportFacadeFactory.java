package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomAccountReportFacadeFactory
{
    private RoomAccountReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("79C7C914") ,com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("79C7C914") ,com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("79C7C914"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("79C7C914"));
    }
}