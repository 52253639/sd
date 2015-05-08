package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AchievementTypeFactory
{
    private AchievementTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IAchievementType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2E255073") ,com.kingdee.eas.fdc.schedule.IAchievementType.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IAchievementType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2E255073") ,com.kingdee.eas.fdc.schedule.IAchievementType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IAchievementType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2E255073"));
    }
    public static com.kingdee.eas.fdc.schedule.IAchievementType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2E255073"));
    }
}