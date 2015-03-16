package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierReviewGatherEntryFactory
{
    private MarketSupplierReviewGatherEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("435B3029") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("435B3029") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("435B3029"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("435B3029"));
    }
}