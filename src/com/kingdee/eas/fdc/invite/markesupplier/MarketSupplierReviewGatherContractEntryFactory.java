package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierReviewGatherContractEntryFactory
{
    private MarketSupplierReviewGatherContractEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherContractEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherContractEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6F716337") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherContractEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherContractEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherContractEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6F716337") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherContractEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherContractEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherContractEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6F716337"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherContractEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherContractEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6F716337"));
    }
}