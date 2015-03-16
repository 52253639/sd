package com.kingdee.eas.fdc.market.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EnterpriseDevReportFacadeFactory
{
    private EnterpriseDevReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.report.IEnterpriseDevReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.report.IEnterpriseDevReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("89E3072C") ,com.kingdee.eas.fdc.market.report.IEnterpriseDevReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.market.report.IEnterpriseDevReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.report.IEnterpriseDevReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("89E3072C") ,com.kingdee.eas.fdc.market.report.IEnterpriseDevReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.report.IEnterpriseDevReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.report.IEnterpriseDevReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("89E3072C"));
    }
    public static com.kingdee.eas.fdc.market.report.IEnterpriseDevReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.report.IEnterpriseDevReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("89E3072C"));
    }
}