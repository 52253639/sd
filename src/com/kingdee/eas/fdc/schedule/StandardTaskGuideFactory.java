package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class StandardTaskGuideFactory
{
    private StandardTaskGuideFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IStandardTaskGuide getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IStandardTaskGuide)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0E9C4124") ,com.kingdee.eas.fdc.schedule.IStandardTaskGuide.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IStandardTaskGuide getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IStandardTaskGuide)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0E9C4124") ,com.kingdee.eas.fdc.schedule.IStandardTaskGuide.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IStandardTaskGuide getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IStandardTaskGuide)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0E9C4124"));
    }
    public static com.kingdee.eas.fdc.schedule.IStandardTaskGuide getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IStandardTaskGuide)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0E9C4124"));
    }
}