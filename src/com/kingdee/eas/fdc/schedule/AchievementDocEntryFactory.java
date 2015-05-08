package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AchievementDocEntryFactory
{
    private AchievementDocEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IAchievementDocEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementDocEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5A8292D3") ,com.kingdee.eas.fdc.schedule.IAchievementDocEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IAchievementDocEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementDocEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5A8292D3") ,com.kingdee.eas.fdc.schedule.IAchievementDocEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IAchievementDocEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementDocEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5A8292D3"));
    }
    public static com.kingdee.eas.fdc.schedule.IAchievementDocEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IAchievementDocEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5A8292D3"));
    }
}