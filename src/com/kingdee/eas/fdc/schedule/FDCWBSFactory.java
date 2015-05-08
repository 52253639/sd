package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCWBSFactory
{
    private FDCWBSFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IFDCWBS getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCWBS)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("786DC4B9") ,com.kingdee.eas.fdc.schedule.IFDCWBS.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IFDCWBS getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCWBS)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("786DC4B9") ,com.kingdee.eas.fdc.schedule.IFDCWBS.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IFDCWBS getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCWBS)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("786DC4B9"));
    }
    public static com.kingdee.eas.fdc.schedule.IFDCWBS getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCWBS)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("786DC4B9"));
    }
}