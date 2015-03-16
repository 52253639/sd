package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierFileTypeFactory
{
    private SupplierFileTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierFileType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierFileType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("05A756FA") ,com.kingdee.eas.fdc.invite.supplier.ISupplierFileType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierFileType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierFileType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("05A756FA") ,com.kingdee.eas.fdc.invite.supplier.ISupplierFileType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierFileType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierFileType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("05A756FA"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierFileType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierFileType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("05A756FA"));
    }
}