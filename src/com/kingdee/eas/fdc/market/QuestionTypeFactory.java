package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuestionTypeFactory
{
    private QuestionTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IQuestionType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("65381091") ,com.kingdee.eas.fdc.market.IQuestionType.class);
    }
    
    public static com.kingdee.eas.fdc.market.IQuestionType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("65381091") ,com.kingdee.eas.fdc.market.IQuestionType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IQuestionType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("65381091"));
    }
    public static com.kingdee.eas.fdc.market.IQuestionType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("65381091"));
    }
}