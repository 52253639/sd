package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSupplierServiceTypeFactory
{
    private FDCSupplierServiceTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSupplierServiceType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSupplierServiceType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("591DD2B6") ,com.kingdee.eas.fdc.invite.supplier.IFDCSupplierServiceType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSupplierServiceType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSupplierServiceType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("591DD2B6") ,com.kingdee.eas.fdc.invite.supplier.IFDCSupplierServiceType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSupplierServiceType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSupplierServiceType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("591DD2B6"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSupplierServiceType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSupplierServiceType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("591DD2B6"));
    }
}