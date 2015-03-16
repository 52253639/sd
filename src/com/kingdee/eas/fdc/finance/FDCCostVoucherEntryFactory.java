package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCCostVoucherEntryFactory
{
    private FDCCostVoucherEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCCostVoucherEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCCostVoucherEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2E718AE7") ,com.kingdee.eas.fdc.finance.IFDCCostVoucherEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCCostVoucherEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCCostVoucherEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2E718AE7") ,com.kingdee.eas.fdc.finance.IFDCCostVoucherEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCCostVoucherEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCCostVoucherEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2E718AE7"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCCostVoucherEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCCostVoucherEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2E718AE7"));
    }
}