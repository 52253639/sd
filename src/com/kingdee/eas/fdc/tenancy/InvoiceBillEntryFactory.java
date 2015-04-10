package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvoiceBillEntryFactory
{
    private InvoiceBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IInvoiceBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IInvoiceBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B6AABFA7") ,com.kingdee.eas.fdc.tenancy.IInvoiceBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IInvoiceBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IInvoiceBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B6AABFA7") ,com.kingdee.eas.fdc.tenancy.IInvoiceBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IInvoiceBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IInvoiceBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B6AABFA7"));
    }
    public static com.kingdee.eas.fdc.tenancy.IInvoiceBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IInvoiceBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B6AABFA7"));
    }
}