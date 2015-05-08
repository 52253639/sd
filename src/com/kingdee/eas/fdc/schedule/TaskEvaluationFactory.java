package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaskEvaluationFactory
{
    private TaskEvaluationFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITaskEvaluation getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskEvaluation)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("54E273F7") ,com.kingdee.eas.fdc.schedule.ITaskEvaluation.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITaskEvaluation getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskEvaluation)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("54E273F7") ,com.kingdee.eas.fdc.schedule.ITaskEvaluation.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITaskEvaluation getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskEvaluation)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("54E273F7"));
    }
    public static com.kingdee.eas.fdc.schedule.ITaskEvaluation getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskEvaluation)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("54E273F7"));
    }
}