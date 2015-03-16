package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SaleManReportFacadeFactory
{
    private SaleManReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISaleManReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISaleManReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("48069855") ,com.kingdee.eas.fdc.sellhouse.report.ISaleManReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.ISaleManReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISaleManReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("48069855") ,com.kingdee.eas.fdc.sellhouse.report.ISaleManReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISaleManReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISaleManReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("48069855"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISaleManReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISaleManReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("48069855"));
    }
}