package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ReceiptInvoiceFacadeFactory
{
    private ReceiptInvoiceFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4B3B8E2A") ,com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4B3B8E2A") ,com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4B3B8E2A"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4B3B8E2A"));
    }
}