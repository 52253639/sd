package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayRequestAcctPayFactory
{
    private PayRequestAcctPayFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPayRequestAcctPay getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayRequestAcctPay)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CB024B9F") ,com.kingdee.eas.fdc.finance.IPayRequestAcctPay.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPayRequestAcctPay getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayRequestAcctPay)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CB024B9F") ,com.kingdee.eas.fdc.finance.IPayRequestAcctPay.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPayRequestAcctPay getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayRequestAcctPay)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CB024B9F"));
    }
    public static com.kingdee.eas.fdc.finance.IPayRequestAcctPay getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayRequestAcctPay)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CB024B9F"));
    }
}