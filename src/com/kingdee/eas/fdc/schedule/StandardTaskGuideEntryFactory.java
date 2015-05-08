package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class StandardTaskGuideEntryFactory
{
    private StandardTaskGuideEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IStandardTaskGuideEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IStandardTaskGuideEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DF52EDAE") ,com.kingdee.eas.fdc.schedule.IStandardTaskGuideEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IStandardTaskGuideEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IStandardTaskGuideEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DF52EDAE") ,com.kingdee.eas.fdc.schedule.IStandardTaskGuideEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IStandardTaskGuideEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IStandardTaskGuideEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DF52EDAE"));
    }
    public static com.kingdee.eas.fdc.schedule.IStandardTaskGuideEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IStandardTaskGuideEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DF52EDAE"));
    }
}