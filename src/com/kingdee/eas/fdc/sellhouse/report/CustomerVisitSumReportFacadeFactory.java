package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerVisitSumReportFacadeFactory
{
    private CustomerVisitSumReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ICustomerVisitSumReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ICustomerVisitSumReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4CE73958") ,com.kingdee.eas.fdc.sellhouse.report.ICustomerVisitSumReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.ICustomerVisitSumReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ICustomerVisitSumReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4CE73958") ,com.kingdee.eas.fdc.sellhouse.report.ICustomerVisitSumReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ICustomerVisitSumReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ICustomerVisitSumReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4CE73958"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ICustomerVisitSumReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ICustomerVisitSumReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4CE73958"));
    }
}