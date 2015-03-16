package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class KeyIndexReportFacadeFactory
{
    private KeyIndexReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IKeyIndexReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IKeyIndexReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3629848D") ,com.kingdee.eas.fdc.sellhouse.report.IKeyIndexReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.IKeyIndexReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IKeyIndexReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3629848D") ,com.kingdee.eas.fdc.sellhouse.report.IKeyIndexReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IKeyIndexReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IKeyIndexReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3629848D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IKeyIndexReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IKeyIndexReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3629848D"));
    }
}