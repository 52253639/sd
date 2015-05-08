package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DeptMonthlyScheduleTaskFactory
{
    private DeptMonthlyScheduleTaskFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleTask getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleTask)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CFE1064E") ,com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleTask.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleTask getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleTask)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CFE1064E") ,com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleTask.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleTask getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleTask)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CFE1064E"));
    }
    public static com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleTask getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleTask)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CFE1064E"));
    }
}