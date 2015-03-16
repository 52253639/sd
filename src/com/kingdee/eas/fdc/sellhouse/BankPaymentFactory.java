package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BankPaymentFactory
{
    private BankPaymentFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBankPayment getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBankPayment)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1FD9DA6F") ,com.kingdee.eas.fdc.sellhouse.IBankPayment.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBankPayment getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBankPayment)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1FD9DA6F") ,com.kingdee.eas.fdc.sellhouse.IBankPayment.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBankPayment getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBankPayment)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1FD9DA6F"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBankPayment getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBankPayment)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1FD9DA6F"));
    }
}