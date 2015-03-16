package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketingUnitFactory
{
    private MarketingUnitFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketingUnit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketingUnit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B6B669CF") ,com.kingdee.eas.fdc.sellhouse.IMarketingUnit.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMarketingUnit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketingUnit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B6B669CF") ,com.kingdee.eas.fdc.sellhouse.IMarketingUnit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketingUnit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketingUnit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B6B669CF"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketingUnit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketingUnit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B6B669CF"));
    }
}