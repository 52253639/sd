package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class GatheringDetailFacadeFactory
{
    private GatheringDetailFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IGatheringDetailFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IGatheringDetailFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FD2B29F7") ,com.kingdee.eas.fdc.sellhouse.IGatheringDetailFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IGatheringDetailFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IGatheringDetailFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FD2B29F7") ,com.kingdee.eas.fdc.sellhouse.IGatheringDetailFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IGatheringDetailFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IGatheringDetailFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FD2B29F7"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IGatheringDetailFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IGatheringDetailFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FD2B29F7"));
    }
}