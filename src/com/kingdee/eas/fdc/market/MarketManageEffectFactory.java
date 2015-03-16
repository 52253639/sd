package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketManageEffectFactory
{
    private MarketManageEffectFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMarketManageEffect getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageEffect)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("676D4543") ,com.kingdee.eas.fdc.market.IMarketManageEffect.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMarketManageEffect getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageEffect)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("676D4543") ,com.kingdee.eas.fdc.market.IMarketManageEffect.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMarketManageEffect getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageEffect)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("676D4543"));
    }
    public static com.kingdee.eas.fdc.market.IMarketManageEffect getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageEffect)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("676D4543"));
    }
}