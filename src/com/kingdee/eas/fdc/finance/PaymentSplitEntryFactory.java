package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaymentSplitEntryFactory
{
    private PaymentSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPaymentSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("27BEF6EF") ,com.kingdee.eas.fdc.finance.IPaymentSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPaymentSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("27BEF6EF") ,com.kingdee.eas.fdc.finance.IPaymentSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPaymentSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("27BEF6EF"));
    }
    public static com.kingdee.eas.fdc.finance.IPaymentSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("27BEF6EF"));
    }
}