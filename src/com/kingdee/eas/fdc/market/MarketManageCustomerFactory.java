package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketManageCustomerFactory
{
    private MarketManageCustomerFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMarketManageCustomer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageCustomer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A1F3E710") ,com.kingdee.eas.fdc.market.IMarketManageCustomer.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMarketManageCustomer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageCustomer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A1F3E710") ,com.kingdee.eas.fdc.market.IMarketManageCustomer.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMarketManageCustomer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageCustomer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A1F3E710"));
    }
    public static com.kingdee.eas.fdc.market.IMarketManageCustomer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketManageCustomer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A1F3E710"));
    }
}