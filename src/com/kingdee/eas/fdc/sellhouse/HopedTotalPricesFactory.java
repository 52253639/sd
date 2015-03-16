package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HopedTotalPricesFactory
{
    private HopedTotalPricesFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IHopedTotalPrices getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopedTotalPrices)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4AB949C1") ,com.kingdee.eas.fdc.sellhouse.IHopedTotalPrices.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IHopedTotalPrices getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopedTotalPrices)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4AB949C1") ,com.kingdee.eas.fdc.sellhouse.IHopedTotalPrices.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IHopedTotalPrices getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopedTotalPrices)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4AB949C1"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IHopedTotalPrices getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IHopedTotalPrices)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4AB949C1"));
    }
}