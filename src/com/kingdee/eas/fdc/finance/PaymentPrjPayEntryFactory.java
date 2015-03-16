package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaymentPrjPayEntryFactory
{
    private PaymentPrjPayEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPaymentPrjPayEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentPrjPayEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("15DF94DB") ,com.kingdee.eas.fdc.finance.IPaymentPrjPayEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPaymentPrjPayEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentPrjPayEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("15DF94DB") ,com.kingdee.eas.fdc.finance.IPaymentPrjPayEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPaymentPrjPayEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentPrjPayEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("15DF94DB"));
    }
    public static com.kingdee.eas.fdc.finance.IPaymentPrjPayEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentPrjPayEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("15DF94DB"));
    }
}