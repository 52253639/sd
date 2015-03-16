package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierReviewGatherFactory
{
    private SupplierReviewGatherFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGather getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGather)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BEAF2997") ,com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGather.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGather getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGather)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BEAF2997") ,com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGather.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGather getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGather)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BEAF2997"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGather getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGather)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BEAF2997"));
    }
}