package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierStockYearSaleFactory
{
    private MarketSupplierStockYearSaleFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockYearSale getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockYearSale)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("635547C4") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockYearSale.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockYearSale getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockYearSale)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("635547C4") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockYearSale.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockYearSale getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockYearSale)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("635547C4"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockYearSale getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockYearSale)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("635547C4"));
    }
}