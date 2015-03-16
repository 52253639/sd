package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayNoCostSplit4VoucherFactory
{
    private PayNoCostSplit4VoucherFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPayNoCostSplit4Voucher getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayNoCostSplit4Voucher)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3F82CC6D") ,com.kingdee.eas.fdc.finance.IPayNoCostSplit4Voucher.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPayNoCostSplit4Voucher getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayNoCostSplit4Voucher)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3F82CC6D") ,com.kingdee.eas.fdc.finance.IPayNoCostSplit4Voucher.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPayNoCostSplit4Voucher getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayNoCostSplit4Voucher)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3F82CC6D"));
    }
    public static com.kingdee.eas.fdc.finance.IPayNoCostSplit4Voucher getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayNoCostSplit4Voucher)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3F82CC6D"));
    }
}