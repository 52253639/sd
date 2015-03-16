package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DealRoomReportFacadeFactory
{
    private DealRoomReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IDealRoomReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IDealRoomReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3DCAB441") ,com.kingdee.eas.fdc.sellhouse.report.IDealRoomReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.IDealRoomReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IDealRoomReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3DCAB441") ,com.kingdee.eas.fdc.sellhouse.report.IDealRoomReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IDealRoomReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IDealRoomReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3DCAB441"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IDealRoomReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IDealRoomReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3DCAB441"));
    }
}