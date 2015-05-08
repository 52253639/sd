package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectImageFactory
{
    private ProjectImageFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IProjectImage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectImage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8A7E9098") ,com.kingdee.eas.fdc.schedule.IProjectImage.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IProjectImage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectImage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8A7E9098") ,com.kingdee.eas.fdc.schedule.IProjectImage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IProjectImage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectImage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8A7E9098"));
    }
    public static com.kingdee.eas.fdc.schedule.IProjectImage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectImage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8A7E9098"));
    }
}