package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierReviewGatherEntryFactory
{
    private SupplierReviewGatherEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("130E551B") ,com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("130E551B") ,com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("130E551B"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("130E551B"));
    }
}