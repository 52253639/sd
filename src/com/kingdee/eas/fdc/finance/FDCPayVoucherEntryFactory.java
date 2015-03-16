package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCPayVoucherEntryFactory
{
    private FDCPayVoucherEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCPayVoucherEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCPayVoucherEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AF19A856") ,com.kingdee.eas.fdc.finance.IFDCPayVoucherEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCPayVoucherEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCPayVoucherEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AF19A856") ,com.kingdee.eas.fdc.finance.IFDCPayVoucherEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCPayVoucherEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCPayVoucherEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AF19A856"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCPayVoucherEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCPayVoucherEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AF19A856"));
    }
}