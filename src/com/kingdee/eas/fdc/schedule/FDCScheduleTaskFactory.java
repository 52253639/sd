package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCScheduleTaskFactory
{
    private FDCScheduleTaskFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleTask getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleTask)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("90CCF22B") ,com.kingdee.eas.fdc.schedule.IFDCScheduleTask.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleTask getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleTask)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("90CCF22B") ,com.kingdee.eas.fdc.schedule.IFDCScheduleTask.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleTask getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleTask)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("90CCF22B"));
    }
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleTask getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleTask)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("90CCF22B"));
    }
}