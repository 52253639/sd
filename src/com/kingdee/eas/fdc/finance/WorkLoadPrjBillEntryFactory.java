package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WorkLoadPrjBillEntryFactory
{
    private WorkLoadPrjBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadPrjBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadPrjBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E8D66BA9") ,com.kingdee.eas.fdc.finance.IWorkLoadPrjBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IWorkLoadPrjBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadPrjBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E8D66BA9") ,com.kingdee.eas.fdc.finance.IWorkLoadPrjBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadPrjBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadPrjBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E8D66BA9"));
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadPrjBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadPrjBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E8D66BA9"));
    }
}