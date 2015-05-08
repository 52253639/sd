package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CtrlItemWBSEntrysFactory
{
    private CtrlItemWBSEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F638EBD5") ,com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F638EBD5") ,com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F638EBD5"));
    }
    public static com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ICtrlItemWBSEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F638EBD5"));
    }
}