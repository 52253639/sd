package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCAdjustBillEntryFactory
{
    private FDCAdjustBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCAdjustBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCAdjustBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("18143306") ,com.kingdee.eas.fdc.finance.IFDCAdjustBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCAdjustBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCAdjustBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("18143306") ,com.kingdee.eas.fdc.finance.IFDCAdjustBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCAdjustBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCAdjustBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("18143306"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCAdjustBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCAdjustBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("18143306"));
    }
}