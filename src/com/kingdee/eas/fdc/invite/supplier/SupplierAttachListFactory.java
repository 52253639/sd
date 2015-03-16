package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierAttachListFactory
{
    private SupplierAttachListFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAttachList getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAttachList)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("56B86BA7") ,com.kingdee.eas.fdc.invite.supplier.ISupplierAttachList.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAttachList getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAttachList)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("56B86BA7") ,com.kingdee.eas.fdc.invite.supplier.ISupplierAttachList.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAttachList getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAttachList)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("56B86BA7"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAttachList getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAttachList)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("56B86BA7"));
    }
}