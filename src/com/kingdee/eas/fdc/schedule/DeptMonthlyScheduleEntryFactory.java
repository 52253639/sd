package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DeptMonthlyScheduleEntryFactory
{
    private DeptMonthlyScheduleEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2B7250C9") ,com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2B7250C9") ,com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2B7250C9"));
    }
    public static com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IDeptMonthlyScheduleEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2B7250C9"));
    }
}