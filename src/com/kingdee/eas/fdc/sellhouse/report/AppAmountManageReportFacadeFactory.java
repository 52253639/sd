package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AppAmountManageReportFacadeFactory
{
    private AppAmountManageReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IAppAmountManageReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IAppAmountManageReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F2E714E0") ,com.kingdee.eas.fdc.sellhouse.report.IAppAmountManageReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.IAppAmountManageReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IAppAmountManageReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F2E714E0") ,com.kingdee.eas.fdc.sellhouse.report.IAppAmountManageReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IAppAmountManageReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IAppAmountManageReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F2E714E0"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IAppAmountManageReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IAppAmountManageReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F2E714E0"));
    }
}