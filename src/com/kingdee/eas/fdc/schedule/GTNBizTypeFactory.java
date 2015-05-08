package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class GTNBizTypeFactory
{
    private GTNBizTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IGTNBizType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IGTNBizType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EB4FCAE2") ,com.kingdee.eas.fdc.schedule.IGTNBizType.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IGTNBizType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IGTNBizType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EB4FCAE2") ,com.kingdee.eas.fdc.schedule.IGTNBizType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IGTNBizType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IGTNBizType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EB4FCAE2"));
    }
    public static com.kingdee.eas.fdc.schedule.IGTNBizType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IGTNBizType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EB4FCAE2"));
    }
}