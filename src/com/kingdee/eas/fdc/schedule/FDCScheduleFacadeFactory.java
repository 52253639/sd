package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCScheduleFacadeFactory
{
    private FDCScheduleFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("796DDBC0") ,com.kingdee.eas.fdc.schedule.IFDCScheduleFacade.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("796DDBC0") ,com.kingdee.eas.fdc.schedule.IFDCScheduleFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("796DDBC0"));
    }
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("796DDBC0"));
    }
}