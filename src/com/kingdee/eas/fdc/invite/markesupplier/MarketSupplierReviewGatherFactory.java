package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierReviewGatherFactory
{
    private MarketSupplierReviewGatherFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGather getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGather)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3C4D1DC9") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGather.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGather getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGather)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3C4D1DC9") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGather.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGather getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGather)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3C4D1DC9"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGather getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGather)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3C4D1DC9"));
    }
}