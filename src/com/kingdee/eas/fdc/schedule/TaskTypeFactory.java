package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaskTypeFactory
{
    private TaskTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITaskType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("137FE435") ,com.kingdee.eas.fdc.schedule.ITaskType.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITaskType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("137FE435") ,com.kingdee.eas.fdc.schedule.ITaskType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITaskType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("137FE435"));
    }
    public static com.kingdee.eas.fdc.schedule.ITaskType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("137FE435"));
    }
}