package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AchievementProfessionFactory
{
    private AchievementProfessionFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IAchievementProfession getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementProfession)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("953A1375") ,com.kingdee.eas.fdc.schedule.IAchievementProfession.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IAchievementProfession getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementProfession)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("953A1375") ,com.kingdee.eas.fdc.schedule.IAchievementProfession.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IAchievementProfession getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementProfession)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("953A1375"));
    }
    public static com.kingdee.eas.fdc.schedule.IAchievementProfession getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementProfession)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("953A1375"));
    }
}