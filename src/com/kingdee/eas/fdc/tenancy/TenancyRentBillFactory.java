package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyRentBillFactory
{
    private TenancyRentBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRentBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRentBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D27EE537") ,com.kingdee.eas.fdc.tenancy.ITenancyRentBill.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyRentBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRentBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D27EE537") ,com.kingdee.eas.fdc.tenancy.ITenancyRentBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRentBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRentBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D27EE537"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRentBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRentBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D27EE537"));
    }
}