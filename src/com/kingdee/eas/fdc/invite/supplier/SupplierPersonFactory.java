package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierPersonFactory
{
    private SupplierPersonFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierPerson getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierPerson)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B75AD079") ,com.kingdee.eas.fdc.invite.supplier.ISupplierPerson.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierPerson getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierPerson)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B75AD079") ,com.kingdee.eas.fdc.invite.supplier.ISupplierPerson.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierPerson getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierPerson)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B75AD079"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierPerson getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierPerson)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B75AD079"));
    }
}