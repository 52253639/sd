package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierStockFactory
{
    private SupplierStockFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierStock getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierStock)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3B04106C") ,com.kingdee.eas.fdc.invite.supplier.ISupplierStock.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierStock getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierStock)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3B04106C") ,com.kingdee.eas.fdc.invite.supplier.ISupplierStock.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierStock getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierStock)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3B04106C"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierStock getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierStock)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3B04106C"));
    }
}