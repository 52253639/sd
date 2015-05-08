package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AchievementManagerFactory
{
    private AchievementManagerFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IAchievementManager getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementManager)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6CF097D4") ,com.kingdee.eas.fdc.schedule.IAchievementManager.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IAchievementManager getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementManager)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6CF097D4") ,com.kingdee.eas.fdc.schedule.IAchievementManager.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IAchievementManager getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementManager)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6CF097D4"));
    }
    public static com.kingdee.eas.fdc.schedule.IAchievementManager getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementManager)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6CF097D4"));
    }
}