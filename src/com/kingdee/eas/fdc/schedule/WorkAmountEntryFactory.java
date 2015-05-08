package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WorkAmountEntryFactory
{
    private WorkAmountEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IWorkAmountEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWorkAmountEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("44A9A893") ,com.kingdee.eas.fdc.schedule.IWorkAmountEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IWorkAmountEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWorkAmountEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("44A9A893") ,com.kingdee.eas.fdc.schedule.IWorkAmountEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IWorkAmountEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWorkAmountEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("44A9A893"));
    }
    public static com.kingdee.eas.fdc.schedule.IWorkAmountEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IWorkAmountEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("44A9A893"));
    }
}