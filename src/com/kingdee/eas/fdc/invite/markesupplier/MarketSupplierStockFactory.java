package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierStockFactory
{
    private MarketSupplierStockFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStock getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStock)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("98690D80") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStock.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStock getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStock)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("98690D80") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStock.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStock getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStock)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("98690D80"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStock getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStock)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("98690D80"));
    }
}