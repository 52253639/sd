package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AchievementFactory
{
    private AchievementFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IAchievement getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IAchievement)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2AD45B71") ,com.kingdee.eas.fdc.invite.supplier.IAchievement.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IAchievement getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IAchievement)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2AD45B71") ,com.kingdee.eas.fdc.invite.supplier.IAchievement.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IAchievement getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IAchievement)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2AD45B71"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IAchievement getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IAchievement)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2AD45B71"));
    }
}