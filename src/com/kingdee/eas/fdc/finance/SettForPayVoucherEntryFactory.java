package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettForPayVoucherEntryFactory
{
    private SettForPayVoucherEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.ISettForPayVoucherEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISettForPayVoucherEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2F644AE4") ,com.kingdee.eas.fdc.finance.ISettForPayVoucherEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.ISettForPayVoucherEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISettForPayVoucherEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2F644AE4") ,com.kingdee.eas.fdc.finance.ISettForPayVoucherEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.ISettForPayVoucherEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISettForPayVoucherEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2F644AE4"));
    }
    public static com.kingdee.eas.fdc.finance.ISettForPayVoucherEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISettForPayVoucherEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2F644AE4"));
    }
}