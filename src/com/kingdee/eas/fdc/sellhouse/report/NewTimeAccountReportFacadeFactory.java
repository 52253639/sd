package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewTimeAccountReportFacadeFactory
{
    private NewTimeAccountReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.INewTimeAccountReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.INewTimeAccountReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("82363B3A") ,com.kingdee.eas.fdc.sellhouse.report.INewTimeAccountReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.INewTimeAccountReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.INewTimeAccountReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("82363B3A") ,com.kingdee.eas.fdc.sellhouse.report.INewTimeAccountReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.INewTimeAccountReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.INewTimeAccountReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("82363B3A"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.INewTimeAccountReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.INewTimeAccountReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("82363B3A"));
    }
}