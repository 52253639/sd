package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaymentNoCostSplitEntryFactory
{
    private PaymentNoCostSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPaymentNoCostSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentNoCostSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6FB9E57D") ,com.kingdee.eas.fdc.finance.IPaymentNoCostSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPaymentNoCostSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentNoCostSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6FB9E57D") ,com.kingdee.eas.fdc.finance.IPaymentNoCostSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPaymentNoCostSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentNoCostSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6FB9E57D"));
    }
    public static com.kingdee.eas.fdc.finance.IPaymentNoCostSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentNoCostSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6FB9E57D"));
    }
}