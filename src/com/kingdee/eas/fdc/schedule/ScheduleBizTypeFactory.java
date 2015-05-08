package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleBizTypeFactory
{
    private ScheduleBizTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleBizType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleBizType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("62C5E9C0") ,com.kingdee.eas.fdc.schedule.IScheduleBizType.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IScheduleBizType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleBizType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("62C5E9C0") ,com.kingdee.eas.fdc.schedule.IScheduleBizType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleBizType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleBizType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("62C5E9C0"));
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleBizType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleBizType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("62C5E9C0"));
    }
}