package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SignAccountReportFacadeFactory
{
    private SignAccountReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISignAccountReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISignAccountReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1FB12CF2") ,com.kingdee.eas.fdc.sellhouse.report.ISignAccountReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.ISignAccountReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISignAccountReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1FB12CF2") ,com.kingdee.eas.fdc.sellhouse.report.ISignAccountReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISignAccountReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISignAccountReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1FB12CF2"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISignAccountReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISignAccountReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1FB12CF2"));
    }
}