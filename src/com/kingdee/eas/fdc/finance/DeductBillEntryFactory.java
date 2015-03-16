package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DeductBillEntryFactory
{
    private DeductBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IDeductBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDeductBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2081EA39") ,com.kingdee.eas.fdc.finance.IDeductBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IDeductBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDeductBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2081EA39") ,com.kingdee.eas.fdc.finance.IDeductBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IDeductBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDeductBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2081EA39"));
    }
    public static com.kingdee.eas.fdc.finance.IDeductBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDeductBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2081EA39"));
    }
}