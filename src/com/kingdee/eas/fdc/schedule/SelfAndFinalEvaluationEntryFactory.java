package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SelfAndFinalEvaluationEntryFactory
{
    private SelfAndFinalEvaluationEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluationEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluationEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("69C56035") ,com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluationEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluationEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluationEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("69C56035") ,com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluationEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluationEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluationEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("69C56035"));
    }
    public static com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluationEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ISelfAndFinalEvaluationEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("69C56035"));
    }
}