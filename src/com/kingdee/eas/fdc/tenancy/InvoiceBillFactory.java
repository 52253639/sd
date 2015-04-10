package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvoiceBillFactory
{
    private InvoiceBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IInvoiceBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IInvoiceBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F345578B") ,com.kingdee.eas.fdc.tenancy.IInvoiceBill.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IInvoiceBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IInvoiceBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F345578B") ,com.kingdee.eas.fdc.tenancy.IInvoiceBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IInvoiceBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IInvoiceBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F345578B"));
    }
    public static com.kingdee.eas.fdc.tenancy.IInvoiceBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IInvoiceBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F345578B"));
    }
}