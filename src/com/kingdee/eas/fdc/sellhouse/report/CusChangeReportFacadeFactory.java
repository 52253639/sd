package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CusChangeReportFacadeFactory
{
    private CusChangeReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ICusChangeReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ICusChangeReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4DDF8813") ,com.kingdee.eas.fdc.sellhouse.report.ICusChangeReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.ICusChangeReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ICusChangeReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4DDF8813") ,com.kingdee.eas.fdc.sellhouse.report.ICusChangeReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ICusChangeReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ICusChangeReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4DDF8813"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ICusChangeReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ICusChangeReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4DDF8813"));
    }
}