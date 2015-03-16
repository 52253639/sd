package com.kingdee.eas.fdc.market.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectAreaSellReportFacadeFactory
{
    private ProjectAreaSellReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.report.IProjectAreaSellReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.report.IProjectAreaSellReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C94DD970") ,com.kingdee.eas.fdc.market.report.IProjectAreaSellReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.market.report.IProjectAreaSellReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.report.IProjectAreaSellReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C94DD970") ,com.kingdee.eas.fdc.market.report.IProjectAreaSellReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.report.IProjectAreaSellReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.report.IProjectAreaSellReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C94DD970"));
    }
    public static com.kingdee.eas.fdc.market.report.IProjectAreaSellReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.report.IProjectAreaSellReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C94DD970"));
    }
}