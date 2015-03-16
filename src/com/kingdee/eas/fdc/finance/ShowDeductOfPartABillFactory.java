package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ShowDeductOfPartABillFactory
{
    private ShowDeductOfPartABillFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IShowDeductOfPartABill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IShowDeductOfPartABill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("33470BCF") ,com.kingdee.eas.fdc.finance.IShowDeductOfPartABill.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IShowDeductOfPartABill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IShowDeductOfPartABill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("33470BCF") ,com.kingdee.eas.fdc.finance.IShowDeductOfPartABill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IShowDeductOfPartABill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IShowDeductOfPartABill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("33470BCF"));
    }
    public static com.kingdee.eas.fdc.finance.IShowDeductOfPartABill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IShowDeductOfPartABill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("33470BCF"));
    }
}