package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierGuideEntryFactory
{
    private SupplierGuideEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierGuideEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierGuideEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("ED86A740") ,com.kingdee.eas.fdc.invite.supplier.ISupplierGuideEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierGuideEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierGuideEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("ED86A740") ,com.kingdee.eas.fdc.invite.supplier.ISupplierGuideEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierGuideEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierGuideEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("ED86A740"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierGuideEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierGuideEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("ED86A740"));
    }
}