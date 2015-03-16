package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaymentAdvicePrintFacadeFactory
{
    private PaymentAdvicePrintFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IPaymentAdvicePrintFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IPaymentAdvicePrintFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("408ADAD2") ,com.kingdee.eas.fdc.tenancy.IPaymentAdvicePrintFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IPaymentAdvicePrintFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IPaymentAdvicePrintFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("408ADAD2") ,com.kingdee.eas.fdc.tenancy.IPaymentAdvicePrintFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IPaymentAdvicePrintFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IPaymentAdvicePrintFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("408ADAD2"));
    }
    public static com.kingdee.eas.fdc.tenancy.IPaymentAdvicePrintFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IPaymentAdvicePrintFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("408ADAD2"));
    }
}