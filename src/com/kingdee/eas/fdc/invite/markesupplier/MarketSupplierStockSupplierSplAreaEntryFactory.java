package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierStockSupplierSplAreaEntryFactory
{
    private MarketSupplierStockSupplierSplAreaEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierSplAreaEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierSplAreaEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("365C6C82") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierSplAreaEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierSplAreaEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierSplAreaEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("365C6C82") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierSplAreaEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierSplAreaEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierSplAreaEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("365C6C82"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierSplAreaEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierSplAreaEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("365C6C82"));
    }
}