package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierPersonEntryFactory
{
    private SupplierPersonEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierPersonEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierPersonEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9361AB79") ,com.kingdee.eas.fdc.invite.supplier.ISupplierPersonEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierPersonEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierPersonEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9361AB79") ,com.kingdee.eas.fdc.invite.supplier.ISupplierPersonEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierPersonEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierPersonEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9361AB79"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierPersonEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierPersonEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9361AB79"));
    }
}