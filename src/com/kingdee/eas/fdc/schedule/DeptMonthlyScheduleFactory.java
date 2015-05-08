package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DeptMonthlyScheduleFactory
{
    private DeptMonthlyScheduleFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IDeptMonthlySchedule getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IDeptMonthlySchedule)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E32C1929") ,com.kingdee.eas.fdc.schedule.IDeptMonthlySchedule.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IDeptMonthlySchedule getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IDeptMonthlySchedule)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E32C1929") ,com.kingdee.eas.fdc.schedule.IDeptMonthlySchedule.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IDeptMonthlySchedule getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IDeptMonthlySchedule)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E32C1929"));
    }
    public static com.kingdee.eas.fdc.schedule.IDeptMonthlySchedule getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IDeptMonthlySchedule)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E32C1929"));
    }
}