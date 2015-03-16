package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyBillFactory
{
    private TenancyBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7BA91DDE") ,com.kingdee.eas.fdc.tenancy.ITenancyBill.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7BA91DDE") ,com.kingdee.eas.fdc.tenancy.ITenancyBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7BA91DDE"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7BA91DDE"));
    }
}