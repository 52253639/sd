package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaskWorkResultFactory
{
    private TaskWorkResultFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITaskWorkResult getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskWorkResult)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E9B99749") ,com.kingdee.eas.fdc.schedule.ITaskWorkResult.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITaskWorkResult getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskWorkResult)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E9B99749") ,com.kingdee.eas.fdc.schedule.ITaskWorkResult.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITaskWorkResult getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskWorkResult)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E9B99749"));
    }
    public static com.kingdee.eas.fdc.schedule.ITaskWorkResult getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskWorkResult)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E9B99749"));
    }
}