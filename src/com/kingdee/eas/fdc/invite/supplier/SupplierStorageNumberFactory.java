package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierStorageNumberFactory
{
    private SupplierStorageNumberFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierStorageNumber getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierStorageNumber)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("40F07C00") ,com.kingdee.eas.fdc.invite.supplier.ISupplierStorageNumber.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierStorageNumber getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierStorageNumber)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("40F07C00") ,com.kingdee.eas.fdc.invite.supplier.ISupplierStorageNumber.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierStorageNumber getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierStorageNumber)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("40F07C00"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierStorageNumber getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierStorageNumber)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("40F07C00"));
    }
}