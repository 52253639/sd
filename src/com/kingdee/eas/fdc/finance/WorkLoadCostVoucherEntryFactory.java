package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WorkLoadCostVoucherEntryFactory
{
    private WorkLoadCostVoucherEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadCostVoucherEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadCostVoucherEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("170A8C97") ,com.kingdee.eas.fdc.finance.IWorkLoadCostVoucherEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IWorkLoadCostVoucherEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadCostVoucherEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("170A8C97") ,com.kingdee.eas.fdc.finance.IWorkLoadCostVoucherEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadCostVoucherEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadCostVoucherEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("170A8C97"));
    }
    public static com.kingdee.eas.fdc.finance.IWorkLoadCostVoucherEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IWorkLoadCostVoucherEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("170A8C97"));
    }
}