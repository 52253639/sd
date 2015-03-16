package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierSplAreaEntryFactory
{
    private SupplierSplAreaEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierSplAreaEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierSplAreaEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2FB4B91A") ,com.kingdee.eas.fdc.invite.supplier.ISupplierSplAreaEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierSplAreaEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierSplAreaEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2FB4B91A") ,com.kingdee.eas.fdc.invite.supplier.ISupplierSplAreaEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierSplAreaEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierSplAreaEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2FB4B91A"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierSplAreaEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierSplAreaEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2FB4B91A"));
    }
}