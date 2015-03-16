package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuestionPaperAnswerFactory
{
    private QuestionPaperAnswerFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IQuestionPaperAnswer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionPaperAnswer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7E608333") ,com.kingdee.eas.fdc.market.IQuestionPaperAnswer.class);
    }
    
    public static com.kingdee.eas.fdc.market.IQuestionPaperAnswer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionPaperAnswer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7E608333") ,com.kingdee.eas.fdc.market.IQuestionPaperAnswer.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IQuestionPaperAnswer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionPaperAnswer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7E608333"));
    }
    public static com.kingdee.eas.fdc.market.IQuestionPaperAnswer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionPaperAnswer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7E608333"));
    }
}