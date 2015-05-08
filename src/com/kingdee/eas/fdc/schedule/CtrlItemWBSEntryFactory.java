package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CtrlItemWBSEntryFactory
{
    private CtrlItemWBSEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EF2B205E") ,com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EF2B205E") ,com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EF2B205E"));
    }
    public static com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EF2B205E"));
    }
}