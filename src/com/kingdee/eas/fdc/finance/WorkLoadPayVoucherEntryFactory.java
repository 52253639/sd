package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WorkLoadPayVoucherEntryFactory
{
    private WorkLoadPayVoucherEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadPayVoucherEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadPayVoucherEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5381B0A6") ,com.kingdee.eas.fdc.finance.IWorkLoadPayVoucherEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IWorkLoadPayVoucherEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadPayVoucherEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5381B0A6") ,com.kingdee.eas.fdc.finance.IWorkLoadPayVoucherEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadPayVoucherEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadPayVoucherEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5381B0A6"));
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadPayVoucherEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadPayVoucherEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5381B0A6"));
    }
}