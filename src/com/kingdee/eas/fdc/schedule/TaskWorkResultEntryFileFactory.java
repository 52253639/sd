package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaskWorkResultEntryFileFactory
{
    private TaskWorkResultEntryFileFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITaskWorkResultEntryFile getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskWorkResultEntryFile)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4BEA93C5") ,com.kingdee.eas.fdc.schedule.ITaskWorkResultEntryFile.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITaskWorkResultEntryFile getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskWorkResultEntryFile)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4BEA93C5") ,com.kingdee.eas.fdc.schedule.ITaskWorkResultEntryFile.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITaskWorkResultEntryFile getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskWorkResultEntryFile)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4BEA93C5"));
    }
    public static com.kingdee.eas.fdc.schedule.ITaskWorkResultEntryFile getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskWorkResultEntryFile)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4BEA93C5"));
    }
}