package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AppAmountStructureReportFacadeFactory
{
    private AppAmountStructureReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IAppAmountStructureReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IAppAmountStructureReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1878ACF4") ,com.kingdee.eas.fdc.sellhouse.report.IAppAmountStructureReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.IAppAmountStructureReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IAppAmountStructureReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1878ACF4") ,com.kingdee.eas.fdc.sellhouse.report.IAppAmountStructureReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IAppAmountStructureReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IAppAmountStructureReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1878ACF4"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IAppAmountStructureReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IAppAmountStructureReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1878ACF4"));
    }
}