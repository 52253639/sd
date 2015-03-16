package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class JudgeQuestionFactory
{
    private JudgeQuestionFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IJudgeQuestion getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IJudgeQuestion)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("767C5DEC") ,com.kingdee.eas.fdc.market.IJudgeQuestion.class);
    }
    
    public static com.kingdee.eas.fdc.market.IJudgeQuestion getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IJudgeQuestion)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("767C5DEC") ,com.kingdee.eas.fdc.market.IJudgeQuestion.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IJudgeQuestion getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IJudgeQuestion)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("767C5DEC"));
    }
    public static com.kingdee.eas.fdc.market.IJudgeQuestion getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IJudgeQuestion)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("767C5DEC"));
    }
}