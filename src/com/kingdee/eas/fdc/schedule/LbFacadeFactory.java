package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class LbFacadeFactory
{
    private LbFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ILbFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ILbFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BC0C2F86") ,com.kingdee.eas.fdc.schedule.ILbFacade.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ILbFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ILbFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BC0C2F86") ,com.kingdee.eas.fdc.schedule.ILbFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ILbFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ILbFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BC0C2F86"));
    }
    public static com.kingdee.eas.fdc.schedule.ILbFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ILbFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BC0C2F86"));
    }
}