package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaySplit4VoucherFactory
{
    private PaySplit4VoucherFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPaySplit4Voucher getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaySplit4Voucher)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4AEAFC3B") ,com.kingdee.eas.fdc.finance.IPaySplit4Voucher.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPaySplit4Voucher getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaySplit4Voucher)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4AEAFC3B") ,com.kingdee.eas.fdc.finance.IPaySplit4Voucher.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPaySplit4Voucher getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaySplit4Voucher)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4AEAFC3B"));
    }
    public static com.kingdee.eas.fdc.finance.IPaySplit4Voucher getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaySplit4Voucher)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4AEAFC3B"));
    }
}