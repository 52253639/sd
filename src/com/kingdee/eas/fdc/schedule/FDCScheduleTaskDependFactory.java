package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCScheduleTaskDependFactory
{
    private FDCScheduleTaskDependFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleTaskDepend getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleTaskDepend)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C848A877") ,com.kingdee.eas.fdc.schedule.IFDCScheduleTaskDepend.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleTaskDepend getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleTaskDepend)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C848A877") ,com.kingdee.eas.fdc.schedule.IFDCScheduleTaskDepend.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleTaskDepend getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleTaskDepend)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C848A877"));
    }
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleTaskDepend getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleTaskDepend)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C848A877"));
    }
}