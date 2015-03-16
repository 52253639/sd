package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierStockSupplierServiceTypeFactory
{
    private MarketSupplierStockSupplierServiceTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierServiceType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierServiceType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E0A00883") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierServiceType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierServiceType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierServiceType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E0A00883") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierServiceType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierServiceType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierServiceType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E0A00883"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierServiceType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierServiceType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E0A00883"));
    }
}