package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaymentNoCostSplitFactory
{
    private PaymentNoCostSplitFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPaymentNoCostSplit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentNoCostSplit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BD7F63F5") ,com.kingdee.eas.fdc.finance.IPaymentNoCostSplit.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPaymentNoCostSplit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentNoCostSplit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BD7F63F5") ,com.kingdee.eas.fdc.finance.IPaymentNoCostSplit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPaymentNoCostSplit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentNoCostSplit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BD7F63F5"));
    }
    public static com.kingdee.eas.fdc.finance.IPaymentNoCostSplit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentNoCostSplit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BD7F63F5"));
    }
}