package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PrefixWBSEntryFactory
{
    private PrefixWBSEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IPrefixWBSEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IPrefixWBSEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DAA99852") ,com.kingdee.eas.fdc.schedule.IPrefixWBSEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IPrefixWBSEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IPrefixWBSEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DAA99852") ,com.kingdee.eas.fdc.schedule.IPrefixWBSEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IPrefixWBSEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IPrefixWBSEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DAA99852"));
    }
    public static com.kingdee.eas.fdc.schedule.IPrefixWBSEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IPrefixWBSEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DAA99852"));
    }
}