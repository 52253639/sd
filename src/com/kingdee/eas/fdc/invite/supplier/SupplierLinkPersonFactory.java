package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierLinkPersonFactory
{
    private SupplierLinkPersonFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPerson getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPerson)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("17612059") ,com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPerson.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPerson getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPerson)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("17612059") ,com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPerson.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPerson getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPerson)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("17612059"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPerson getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPerson)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("17612059"));
    }
}