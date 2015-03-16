package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuestionPaperReportFacadeFactory
{
    private QuestionPaperReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IQuestionPaperReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IQuestionPaperReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0811DE88") ,com.kingdee.eas.fdc.sellhouse.report.IQuestionPaperReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.IQuestionPaperReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IQuestionPaperReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0811DE88") ,com.kingdee.eas.fdc.sellhouse.report.IQuestionPaperReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IQuestionPaperReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IQuestionPaperReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0811DE88"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IQuestionPaperReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IQuestionPaperReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0811DE88"));
    }
}