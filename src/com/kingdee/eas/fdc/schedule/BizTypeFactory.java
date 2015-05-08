package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BizTypeFactory
{
    private BizTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IBizType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IBizType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("03DBB657") ,com.kingdee.eas.fdc.schedule.IBizType.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IBizType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IBizType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("03DBB657") ,com.kingdee.eas.fdc.schedule.IBizType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IBizType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IBizType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("03DBB657"));
    }
    public static com.kingdee.eas.fdc.schedule.IBizType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IBizType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("03DBB657"));
    }
}