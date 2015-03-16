package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettForPayVoucherFactory
{
    private SettForPayVoucherFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.ISettForPayVoucher getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISettForPayVoucher)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CE66FA2E") ,com.kingdee.eas.fdc.finance.ISettForPayVoucher.class);
    }
    
    public static com.kingdee.eas.fdc.finance.ISettForPayVoucher getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISettForPayVoucher)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CE66FA2E") ,com.kingdee.eas.fdc.finance.ISettForPayVoucher.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.ISettForPayVoucher getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISettForPayVoucher)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CE66FA2E"));
    }
    public static com.kingdee.eas.fdc.finance.ISettForPayVoucher getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISettForPayVoucher)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CE66FA2E"));
    }
}