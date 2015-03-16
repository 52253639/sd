package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierReviewGatherEvaluationEntryFactory
{
    private MarketSupplierReviewGatherEvaluationEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEvaluationEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEvaluationEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AAF2658D") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEvaluationEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEvaluationEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEvaluationEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AAF2658D") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEvaluationEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEvaluationEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEvaluationEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AAF2658D"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEvaluationEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherEvaluationEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AAF2658D"));
    }
}