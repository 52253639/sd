package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaskWorkloadLogFactory
{
    private TaskWorkloadLogFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITaskWorkloadLog getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskWorkloadLog)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BCE5F2F2") ,com.kingdee.eas.fdc.schedule.ITaskWorkloadLog.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITaskWorkloadLog getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskWorkloadLog)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BCE5F2F2") ,com.kingdee.eas.fdc.schedule.ITaskWorkloadLog.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITaskWorkloadLog getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskWorkloadLog)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BCE5F2F2"));
    }
    public static com.kingdee.eas.fdc.schedule.ITaskWorkloadLog getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskWorkloadLog)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BCE5F2F2"));
    }
}