package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HelpPersonEntryFactory
{
    private HelpPersonEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IHelpPersonEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IHelpPersonEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EC59AD86") ,com.kingdee.eas.fdc.schedule.IHelpPersonEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IHelpPersonEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IHelpPersonEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EC59AD86") ,com.kingdee.eas.fdc.schedule.IHelpPersonEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IHelpPersonEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IHelpPersonEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EC59AD86"));
    }
    public static com.kingdee.eas.fdc.schedule.IHelpPersonEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IHelpPersonEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EC59AD86"));
    }
}