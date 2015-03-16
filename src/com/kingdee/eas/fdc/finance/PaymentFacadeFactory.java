package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaymentFacadeFactory
{
    private PaymentFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPaymentFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("18821D91") ,com.kingdee.eas.fdc.finance.IPaymentFacade.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPaymentFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("18821D91") ,com.kingdee.eas.fdc.finance.IPaymentFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPaymentFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("18821D91"));
    }
    public static com.kingdee.eas.fdc.finance.IPaymentFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("18821D91"));
    }
}