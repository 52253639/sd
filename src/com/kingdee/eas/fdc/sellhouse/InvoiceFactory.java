package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvoiceFactory
{
    private InvoiceFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IInvoice getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvoice)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C29B7FF2") ,com.kingdee.eas.fdc.sellhouse.IInvoice.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IInvoice getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvoice)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C29B7FF2") ,com.kingdee.eas.fdc.sellhouse.IInvoice.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IInvoice getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvoice)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C29B7FF2"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IInvoice getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInvoice)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C29B7FF2"));
    }
}