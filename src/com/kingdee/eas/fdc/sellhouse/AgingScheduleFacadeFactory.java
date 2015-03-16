package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AgingScheduleFacadeFactory
{
    private AgingScheduleFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgingScheduleFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgingScheduleFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F13ED472") ,com.kingdee.eas.fdc.sellhouse.IAgingScheduleFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IAgingScheduleFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgingScheduleFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F13ED472") ,com.kingdee.eas.fdc.sellhouse.IAgingScheduleFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgingScheduleFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgingScheduleFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F13ED472"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgingScheduleFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgingScheduleFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F13ED472"));
    }
}