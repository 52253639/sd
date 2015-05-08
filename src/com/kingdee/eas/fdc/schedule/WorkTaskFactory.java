package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WorkTaskFactory
{
    private WorkTaskFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IWorkTask getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWorkTask)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2E05760C") ,com.kingdee.eas.fdc.schedule.IWorkTask.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IWorkTask getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWorkTask)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2E05760C") ,com.kingdee.eas.fdc.schedule.IWorkTask.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IWorkTask getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWorkTask)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2E05760C"));
    }
    public static com.kingdee.eas.fdc.schedule.IWorkTask getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWorkTask)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2E05760C"));
    }
}