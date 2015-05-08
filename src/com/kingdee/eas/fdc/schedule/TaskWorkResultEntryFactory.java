package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaskWorkResultEntryFactory
{
    private TaskWorkResultEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITaskWorkResultEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskWorkResultEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6E3EA6A9") ,com.kingdee.eas.fdc.schedule.ITaskWorkResultEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITaskWorkResultEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskWorkResultEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6E3EA6A9") ,com.kingdee.eas.fdc.schedule.ITaskWorkResultEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITaskWorkResultEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskWorkResultEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6E3EA6A9"));
    }
    public static com.kingdee.eas.fdc.schedule.ITaskWorkResultEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskWorkResultEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6E3EA6A9"));
    }
}