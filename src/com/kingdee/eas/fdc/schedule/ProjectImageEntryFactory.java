package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectImageEntryFactory
{
    private ProjectImageEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IProjectImageEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectImageEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("157C26BA") ,com.kingdee.eas.fdc.schedule.IProjectImageEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IProjectImageEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectImageEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("157C26BA") ,com.kingdee.eas.fdc.schedule.IProjectImageEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IProjectImageEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectImageEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("157C26BA"));
    }
    public static com.kingdee.eas.fdc.schedule.IProjectImageEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectImageEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("157C26BA"));
    }
}