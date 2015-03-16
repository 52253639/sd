package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuestionPaperDefineFactory
{
    private QuestionPaperDefineFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IQuestionPaperDefine getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionPaperDefine)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("82FA1D30") ,com.kingdee.eas.fdc.market.IQuestionPaperDefine.class);
    }
    
    public static com.kingdee.eas.fdc.market.IQuestionPaperDefine getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionPaperDefine)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("82FA1D30") ,com.kingdee.eas.fdc.market.IQuestionPaperDefine.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IQuestionPaperDefine getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionPaperDefine)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("82FA1D30"));
    }
    public static com.kingdee.eas.fdc.market.IQuestionPaperDefine getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionPaperDefine)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("82FA1D30"));
    }
}