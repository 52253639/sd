package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleCodingTypeFactory
{
    private ScheduleCodingTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleCodingType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleCodingType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("197FC211") ,com.kingdee.eas.fdc.schedule.IScheduleCodingType.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IScheduleCodingType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleCodingType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("197FC211") ,com.kingdee.eas.fdc.schedule.IScheduleCodingType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleCodingType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleCodingType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("197FC211"));
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleCodingType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleCodingType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("197FC211"));
    }
}