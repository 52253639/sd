package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierBusinessModeFactory
{
    private SupplierBusinessModeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierBusinessMode getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierBusinessMode)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EFBF57A7") ,com.kingdee.eas.fdc.invite.supplier.ISupplierBusinessMode.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierBusinessMode getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierBusinessMode)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EFBF57A7") ,com.kingdee.eas.fdc.invite.supplier.ISupplierBusinessMode.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierBusinessMode getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierBusinessMode)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EFBF57A7"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierBusinessMode getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierBusinessMode)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EFBF57A7"));
    }
}