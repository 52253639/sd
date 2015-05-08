package com.kingdee.eas.fdc.schedule.framework;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HolidayEntryFactory
{
    private HolidayEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.framework.IHolidayEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.framework.IHolidayEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("85D83880") ,com.kingdee.eas.fdc.schedule.framework.IHolidayEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.framework.IHolidayEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.framework.IHolidayEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("85D83880") ,com.kingdee.eas.fdc.schedule.framework.IHolidayEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.framework.IHolidayEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.framework.IHolidayEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("85D83880"));
    }
    public static com.kingdee.eas.fdc.schedule.framework.IHolidayEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.framework.IHolidayEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("85D83880"));
    }
}