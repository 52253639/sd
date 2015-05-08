package com.kingdee.eas.fdc.schedule.framework;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DefaultWeekendEntryFactory
{
    private DefaultWeekendEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.framework.IDefaultWeekendEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.framework.IDefaultWeekendEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5474A8E6") ,com.kingdee.eas.fdc.schedule.framework.IDefaultWeekendEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.framework.IDefaultWeekendEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.framework.IDefaultWeekendEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5474A8E6") ,com.kingdee.eas.fdc.schedule.framework.IDefaultWeekendEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.framework.IDefaultWeekendEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.framework.IDefaultWeekendEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5474A8E6"));
    }
    public static com.kingdee.eas.fdc.schedule.framework.IDefaultWeekendEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.framework.IDefaultWeekendEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5474A8E6"));
    }
}