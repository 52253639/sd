package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CtrlItemFactory
{
    private CtrlItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ICtrlItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ICtrlItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("18F679F4") ,com.kingdee.eas.fdc.schedule.ICtrlItem.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ICtrlItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ICtrlItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("18F679F4") ,com.kingdee.eas.fdc.schedule.ICtrlItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ICtrlItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ICtrlItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("18F679F4"));
    }
    public static com.kingdee.eas.fdc.schedule.ICtrlItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ICtrlItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("18F679F4"));
    }
}