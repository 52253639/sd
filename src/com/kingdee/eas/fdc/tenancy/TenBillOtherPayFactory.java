package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenBillOtherPayFactory
{
    private TenBillOtherPayFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenBillOtherPay getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenBillOtherPay)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E17EA893") ,com.kingdee.eas.fdc.tenancy.ITenBillOtherPay.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenBillOtherPay getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenBillOtherPay)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E17EA893") ,com.kingdee.eas.fdc.tenancy.ITenBillOtherPay.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenBillOtherPay getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenBillOtherPay)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E17EA893"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenBillOtherPay getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenBillOtherPay)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E17EA893"));
    }
}