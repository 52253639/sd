package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerSignReportFacadeFactory
{
    private CustomerSignReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ICustomerSignReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ICustomerSignReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F7B72795") ,com.kingdee.eas.fdc.sellhouse.report.ICustomerSignReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.ICustomerSignReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ICustomerSignReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F7B72795") ,com.kingdee.eas.fdc.sellhouse.report.ICustomerSignReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ICustomerSignReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ICustomerSignReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F7B72795"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ICustomerSignReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ICustomerSignReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F7B72795"));
    }
}