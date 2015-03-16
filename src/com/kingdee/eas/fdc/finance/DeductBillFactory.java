package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DeductBillFactory
{
    private DeductBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IDeductBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDeductBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("953E59B9") ,com.kingdee.eas.fdc.finance.IDeductBill.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IDeductBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDeductBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("953E59B9") ,com.kingdee.eas.fdc.finance.IDeductBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IDeductBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDeductBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("953E59B9"));
    }
    public static com.kingdee.eas.fdc.finance.IDeductBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDeductBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("953E59B9"));
    }
}