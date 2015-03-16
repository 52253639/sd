package com.kingdee.eas.fdc.market.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MediaCostAssessmentReportFacadeFactory
{
    private MediaCostAssessmentReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.report.IMediaCostAssessmentReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.report.IMediaCostAssessmentReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EBB7EBAB") ,com.kingdee.eas.fdc.market.report.IMediaCostAssessmentReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.market.report.IMediaCostAssessmentReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.report.IMediaCostAssessmentReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EBB7EBAB") ,com.kingdee.eas.fdc.market.report.IMediaCostAssessmentReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.report.IMediaCostAssessmentReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.report.IMediaCostAssessmentReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EBB7EBAB"));
    }
    public static com.kingdee.eas.fdc.market.report.IMediaCostAssessmentReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.report.IMediaCostAssessmentReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EBB7EBAB"));
    }
}