package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierStockSupplierPersonEntryFactory
{
    private MarketSupplierStockSupplierPersonEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierPersonEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierPersonEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("38C1EB11") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierPersonEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierPersonEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierPersonEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("38C1EB11") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierPersonEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierPersonEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierPersonEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("38C1EB11"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierPersonEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierPersonEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("38C1EB11"));
    }
}