package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaskEvaluationBillFactory
{
    private TaskEvaluationBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITaskEvaluationBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskEvaluationBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7629389E") ,com.kingdee.eas.fdc.schedule.ITaskEvaluationBill.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITaskEvaluationBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskEvaluationBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7629389E") ,com.kingdee.eas.fdc.schedule.ITaskEvaluationBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITaskEvaluationBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskEvaluationBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7629389E"));
    }
    public static com.kingdee.eas.fdc.schedule.ITaskEvaluationBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskEvaluationBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7629389E"));
    }
}