package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCPaymentBillFactory
{
    private FDCPaymentBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCPaymentBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCPaymentBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D8735557") ,com.kingdee.eas.fdc.finance.IFDCPaymentBill.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCPaymentBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCPaymentBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D8735557") ,com.kingdee.eas.fdc.finance.IFDCPaymentBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCPaymentBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCPaymentBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D8735557"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCPaymentBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCPaymentBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D8735557"));
    }
}