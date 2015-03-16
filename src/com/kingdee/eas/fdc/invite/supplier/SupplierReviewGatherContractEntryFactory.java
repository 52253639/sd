package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierReviewGatherContractEntryFactory
{
    private SupplierReviewGatherContractEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherContractEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherContractEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A4717629") ,com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherContractEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherContractEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherContractEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A4717629") ,com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherContractEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherContractEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherContractEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A4717629"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherContractEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherContractEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A4717629"));
    }
}