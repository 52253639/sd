package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierStorageNumberFactory
{
    private MarketSupplierStorageNumberFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStorageNumber getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStorageNumber)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("77110E0E") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStorageNumber.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStorageNumber getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStorageNumber)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("77110E0E") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStorageNumber.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStorageNumber getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStorageNumber)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("77110E0E"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStorageNumber getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStorageNumber)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("77110E0E"));
    }
}