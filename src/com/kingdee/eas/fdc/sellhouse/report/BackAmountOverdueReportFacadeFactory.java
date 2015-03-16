package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BackAmountOverdueReportFacadeFactory
{
    private BackAmountOverdueReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IBackAmountOverdueReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IBackAmountOverdueReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BFEAB643") ,com.kingdee.eas.fdc.sellhouse.report.IBackAmountOverdueReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.IBackAmountOverdueReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IBackAmountOverdueReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BFEAB643") ,com.kingdee.eas.fdc.sellhouse.report.IBackAmountOverdueReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IBackAmountOverdueReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IBackAmountOverdueReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BFEAB643"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IBackAmountOverdueReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IBackAmountOverdueReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BFEAB643"));
    }
}