package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierAttachListEntryFactory
{
    private SupplierAttachListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAttachListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAttachListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D3CBDD0B") ,com.kingdee.eas.fdc.invite.supplier.ISupplierAttachListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAttachListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAttachListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D3CBDD0B") ,com.kingdee.eas.fdc.invite.supplier.ISupplierAttachListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAttachListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAttachListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D3CBDD0B"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAttachListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAttachListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D3CBDD0B"));
    }
}