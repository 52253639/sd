package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaymentSplitFacadeFactory
{
    private PaymentSplitFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPaymentSplitFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentSplitFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D11591BD") ,com.kingdee.eas.fdc.finance.IPaymentSplitFacade.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPaymentSplitFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentSplitFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D11591BD") ,com.kingdee.eas.fdc.finance.IPaymentSplitFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPaymentSplitFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentSplitFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D11591BD"));
    }
    public static com.kingdee.eas.fdc.finance.IPaymentSplitFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentSplitFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D11591BD"));
    }
}