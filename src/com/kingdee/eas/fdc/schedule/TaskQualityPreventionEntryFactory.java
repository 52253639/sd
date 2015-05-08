package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaskQualityPreventionEntryFactory
{
    private TaskQualityPreventionEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITaskQualityPreventionEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskQualityPreventionEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B5A5E85E") ,com.kingdee.eas.fdc.schedule.ITaskQualityPreventionEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITaskQualityPreventionEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskQualityPreventionEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B5A5E85E") ,com.kingdee.eas.fdc.schedule.ITaskQualityPreventionEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITaskQualityPreventionEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskQualityPreventionEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B5A5E85E"));
    }
    public static com.kingdee.eas.fdc.schedule.ITaskQualityPreventionEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskQualityPreventionEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B5A5E85E"));
    }
}