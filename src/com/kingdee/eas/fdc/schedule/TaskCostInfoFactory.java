package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaskCostInfoFactory
{
    private TaskCostInfoFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITaskCostInfo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskCostInfo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DAFB3996") ,com.kingdee.eas.fdc.schedule.ITaskCostInfo.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITaskCostInfo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskCostInfo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DAFB3996") ,com.kingdee.eas.fdc.schedule.ITaskCostInfo.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITaskCostInfo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskCostInfo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DAFB3996"));
    }
    public static com.kingdee.eas.fdc.schedule.ITaskCostInfo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskCostInfo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DAFB3996"));
    }
}