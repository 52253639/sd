package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierReviewGatherPersonFactory
{
    private MarketSupplierReviewGatherPersonFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherPerson getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherPerson)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3A506A7E") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherPerson.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherPerson getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherPerson)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3A506A7E") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherPerson.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherPerson getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherPerson)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3A506A7E"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherPerson getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierReviewGatherPerson)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3A506A7E"));
    }
}