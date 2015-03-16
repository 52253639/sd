package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BankPaymentEntryFactory
{
    private BankPaymentEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBankPaymentEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBankPaymentEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A1A86B43") ,com.kingdee.eas.fdc.sellhouse.IBankPaymentEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBankPaymentEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBankPaymentEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A1A86B43") ,com.kingdee.eas.fdc.sellhouse.IBankPaymentEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBankPaymentEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBankPaymentEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A1A86B43"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBankPaymentEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBankPaymentEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A1A86B43"));
    }
}