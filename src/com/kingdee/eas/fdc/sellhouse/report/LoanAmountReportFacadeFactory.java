package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class LoanAmountReportFacadeFactory
{
    private LoanAmountReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ILoanAmountReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ILoanAmountReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("21D589C2") ,com.kingdee.eas.fdc.sellhouse.report.ILoanAmountReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.ILoanAmountReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ILoanAmountReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("21D589C2") ,com.kingdee.eas.fdc.sellhouse.report.ILoanAmountReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ILoanAmountReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ILoanAmountReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("21D589C2"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ILoanAmountReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ILoanAmountReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("21D589C2"));
    }
}