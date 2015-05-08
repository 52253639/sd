package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectSpecialFactory
{
    private ProjectSpecialFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IProjectSpecial getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectSpecial)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FB704CB6") ,com.kingdee.eas.fdc.schedule.IProjectSpecial.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IProjectSpecial getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectSpecial)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FB704CB6") ,com.kingdee.eas.fdc.schedule.IProjectSpecial.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IProjectSpecial getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectSpecial)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FB704CB6"));
    }
    public static com.kingdee.eas.fdc.schedule.IProjectSpecial getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectSpecial)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FB704CB6"));
    }
}