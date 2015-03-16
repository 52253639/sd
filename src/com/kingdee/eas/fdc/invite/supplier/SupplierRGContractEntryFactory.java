package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierRGContractEntryFactory
{
    private SupplierRGContractEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierRGContractEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierRGContractEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2A04A1C7") ,com.kingdee.eas.fdc.invite.supplier.ISupplierRGContractEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierRGContractEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierRGContractEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2A04A1C7") ,com.kingdee.eas.fdc.invite.supplier.ISupplierRGContractEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierRGContractEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierRGContractEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2A04A1C7"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierRGContractEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierRGContractEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2A04A1C7"));
    }
}