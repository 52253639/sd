package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierStockSupplierAttachListEntryFactory
{
    private MarketSupplierStockSupplierAttachListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierAttachListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierAttachListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("495290A3") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierAttachListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierAttachListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierAttachListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("495290A3") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierAttachListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierAttachListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierAttachListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("495290A3"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierAttachListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockSupplierAttachListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("495290A3"));
    }
}