package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierStockAptitudeFileFactory
{
    private MarketSupplierStockAptitudeFileFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAptitudeFile getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAptitudeFile)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B4D97FE2") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAptitudeFile.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAptitudeFile getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAptitudeFile)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B4D97FE2") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAptitudeFile.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAptitudeFile getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAptitudeFile)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B4D97FE2"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAptitudeFile getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStockAptitudeFile)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B4D97FE2"));
    }
}