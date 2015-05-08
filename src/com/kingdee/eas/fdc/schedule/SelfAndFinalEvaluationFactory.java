package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SelfAndFinalEvaluationFactory
{
    private SelfAndFinalEvaluationFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluation getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluation)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E2F0163D") ,com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluation.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluation getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluation)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E2F0163D") ,com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluation.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluation getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluation)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E2F0163D"));
    }
    public static com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluation getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluation)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E2F0163D"));
    }
}