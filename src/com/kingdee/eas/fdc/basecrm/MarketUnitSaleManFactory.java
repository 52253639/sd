package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketUnitSaleManFactory
{
    private MarketUnitSaleManFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.IMarketUnitSaleMan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IMarketUnitSaleMan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D016D3B7") ,com.kingdee.eas.fdc.basecrm.IMarketUnitSaleMan.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.IMarketUnitSaleMan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IMarketUnitSaleMan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D016D3B7") ,com.kingdee.eas.fdc.basecrm.IMarketUnitSaleMan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.IMarketUnitSaleMan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IMarketUnitSaleMan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D016D3B7"));
    }
    public static com.kingdee.eas.fdc.basecrm.IMarketUnitSaleMan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IMarketUnitSaleMan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D016D3B7"));
    }
}