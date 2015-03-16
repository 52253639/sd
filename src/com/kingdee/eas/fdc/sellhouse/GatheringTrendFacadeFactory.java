package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class GatheringTrendFacadeFactory
{
    private GatheringTrendFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IGatheringTrendFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IGatheringTrendFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E379196B") ,com.kingdee.eas.fdc.sellhouse.IGatheringTrendFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IGatheringTrendFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IGatheringTrendFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E379196B") ,com.kingdee.eas.fdc.sellhouse.IGatheringTrendFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IGatheringTrendFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IGatheringTrendFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E379196B"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IGatheringTrendFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IGatheringTrendFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E379196B"));
    }
}