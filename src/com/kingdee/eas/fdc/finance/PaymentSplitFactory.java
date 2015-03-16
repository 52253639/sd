package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaymentSplitFactory
{
    private PaymentSplitFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPaymentSplit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentSplit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("962DB343") ,com.kingdee.eas.fdc.finance.IPaymentSplit.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPaymentSplit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentSplit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("962DB343") ,com.kingdee.eas.fdc.finance.IPaymentSplit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPaymentSplit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentSplit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("962DB343"));
    }
    public static com.kingdee.eas.fdc.finance.IPaymentSplit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentSplit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("962DB343"));
    }
}