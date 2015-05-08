package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaskMaterialItemEntryFactory
{
    private TaskMaterialItemEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITaskMaterialItemEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskMaterialItemEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F8B4029D") ,com.kingdee.eas.fdc.schedule.ITaskMaterialItemEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITaskMaterialItemEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskMaterialItemEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F8B4029D") ,com.kingdee.eas.fdc.schedule.ITaskMaterialItemEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITaskMaterialItemEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskMaterialItemEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F8B4029D"));
    }
    public static com.kingdee.eas.fdc.schedule.ITaskMaterialItemEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskMaterialItemEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F8B4029D"));
    }
}