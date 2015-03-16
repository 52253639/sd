package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class JudgeQuestionAnswerFactory
{
    private JudgeQuestionAnswerFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IJudgeQuestionAnswer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IJudgeQuestionAnswer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("867068CA") ,com.kingdee.eas.fdc.market.IJudgeQuestionAnswer.class);
    }
    
    public static com.kingdee.eas.fdc.market.IJudgeQuestionAnswer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IJudgeQuestionAnswer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("867068CA") ,com.kingdee.eas.fdc.market.IJudgeQuestionAnswer.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IJudgeQuestionAnswer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IJudgeQuestionAnswer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("867068CA"));
    }
    public static com.kingdee.eas.fdc.market.IJudgeQuestionAnswer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IJudgeQuestionAnswer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("867068CA"));
    }
}