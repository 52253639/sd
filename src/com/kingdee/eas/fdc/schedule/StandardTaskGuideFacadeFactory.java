package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class StandardTaskGuideFacadeFactory
{
    private StandardTaskGuideFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IStandardTaskGuideFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IStandardTaskGuideFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0C0072DE") ,com.kingdee.eas.fdc.schedule.IStandardTaskGuideFacade.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IStandardTaskGuideFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IStandardTaskGuideFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0C0072DE") ,com.kingdee.eas.fdc.schedule.IStandardTaskGuideFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IStandardTaskGuideFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IStandardTaskGuideFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0C0072DE"));
    }
    public static com.kingdee.eas.fdc.schedule.IStandardTaskGuideFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IStandardTaskGuideFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0C0072DE"));
    }
}