package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AchievementStageFactory
{
    private AchievementStageFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IAchievementStage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementStage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("967428E5") ,com.kingdee.eas.fdc.schedule.IAchievementStage.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IAchievementStage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementStage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("967428E5") ,com.kingdee.eas.fdc.schedule.IAchievementStage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IAchievementStage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementStage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("967428E5"));
    }
    public static com.kingdee.eas.fdc.schedule.IAchievementStage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementStage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("967428E5"));
    }
}