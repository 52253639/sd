package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuestionPaperAnswerEntryFactory
{
    private QuestionPaperAnswerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IQuestionPaperAnswerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionPaperAnswerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("63809CFF") ,com.kingdee.eas.fdc.market.IQuestionPaperAnswerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IQuestionPaperAnswerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionPaperAnswerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("63809CFF") ,com.kingdee.eas.fdc.market.IQuestionPaperAnswerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IQuestionPaperAnswerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionPaperAnswerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("63809CFF"));
    }
    public static com.kingdee.eas.fdc.market.IQuestionPaperAnswerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionPaperAnswerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("63809CFF"));
    }
}