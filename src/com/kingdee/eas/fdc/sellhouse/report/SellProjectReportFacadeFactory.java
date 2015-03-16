package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SellProjectReportFacadeFactory
{
    private SellProjectReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISellProjectReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISellProjectReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("14BA2029") ,com.kingdee.eas.fdc.sellhouse.report.ISellProjectReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.ISellProjectReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISellProjectReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("14BA2029") ,com.kingdee.eas.fdc.sellhouse.report.ISellProjectReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISellProjectReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISellProjectReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("14BA2029"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISellProjectReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISellProjectReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("14BA2029"));
    }
}