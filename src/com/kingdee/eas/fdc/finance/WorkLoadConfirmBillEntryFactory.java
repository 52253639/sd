package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WorkLoadConfirmBillEntryFactory
{
    private WorkLoadConfirmBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("043A7F91") ,com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("043A7F91") ,com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("043A7F91"));
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadConfirmBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("043A7F91"));
    }
}