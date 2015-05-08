package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaskLoadEntryFactory
{
    private TaskLoadEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITaskLoadEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskLoadEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E71E0311") ,com.kingdee.eas.fdc.schedule.ITaskLoadEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITaskLoadEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskLoadEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E71E0311") ,com.kingdee.eas.fdc.schedule.ITaskLoadEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITaskLoadEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskLoadEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E71E0311"));
    }
    public static com.kingdee.eas.fdc.schedule.ITaskLoadEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskLoadEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E71E0311"));
    }
}