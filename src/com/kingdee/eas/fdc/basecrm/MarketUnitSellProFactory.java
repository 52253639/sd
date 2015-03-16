package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketUnitSellProFactory
{
    private MarketUnitSellProFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.IMarketUnitSellPro getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IMarketUnitSellPro)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D6ED721F") ,com.kingdee.eas.fdc.basecrm.IMarketUnitSellPro.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.IMarketUnitSellPro getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IMarketUnitSellPro)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D6ED721F") ,com.kingdee.eas.fdc.basecrm.IMarketUnitSellPro.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.IMarketUnitSellPro getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IMarketUnitSellPro)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D6ED721F"));
    }
    public static com.kingdee.eas.fdc.basecrm.IMarketUnitSellPro getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IMarketUnitSellPro)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D6ED721F"));
    }
}