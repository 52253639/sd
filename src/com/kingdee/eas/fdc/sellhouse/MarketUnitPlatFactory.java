package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketUnitPlatFactory
{
    private MarketUnitPlatFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketUnitPlat getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketUnitPlat)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("07903ECA") ,com.kingdee.eas.fdc.sellhouse.IMarketUnitPlat.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMarketUnitPlat getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketUnitPlat)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("07903ECA") ,com.kingdee.eas.fdc.sellhouse.IMarketUnitPlat.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketUnitPlat getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketUnitPlat)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("07903ECA"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketUnitPlat getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketUnitPlat)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("07903ECA"));
    }
}