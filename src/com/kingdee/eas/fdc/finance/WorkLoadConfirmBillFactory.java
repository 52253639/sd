package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WorkLoadConfirmBillFactory
{
    private WorkLoadConfirmBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadConfirmBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadConfirmBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E4A3CD61") ,com.kingdee.eas.fdc.finance.IWorkLoadConfirmBill.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IWorkLoadConfirmBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadConfirmBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E4A3CD61") ,com.kingdee.eas.fdc.finance.IWorkLoadConfirmBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadConfirmBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadConfirmBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E4A3CD61"));
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadConfirmBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadConfirmBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E4A3CD61"));
    }
}