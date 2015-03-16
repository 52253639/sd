package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierAppraiseTypeFactory
{
    private SupplierAppraiseTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("321B8A2F") ,com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("321B8A2F") ,com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("321B8A2F"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("321B8A2F"));
    }
}