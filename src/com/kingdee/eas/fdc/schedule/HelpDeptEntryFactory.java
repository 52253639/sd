package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HelpDeptEntryFactory
{
    private HelpDeptEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IHelpDeptEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IHelpDeptEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0ECA66D6") ,com.kingdee.eas.fdc.schedule.IHelpDeptEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IHelpDeptEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IHelpDeptEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0ECA66D6") ,com.kingdee.eas.fdc.schedule.IHelpDeptEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IHelpDeptEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IHelpDeptEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0ECA66D6"));
    }
    public static com.kingdee.eas.fdc.schedule.IHelpDeptEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IHelpDeptEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0ECA66D6"));
    }
}