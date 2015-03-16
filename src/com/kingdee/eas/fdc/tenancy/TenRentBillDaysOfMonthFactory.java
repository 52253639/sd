package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenRentBillDaysOfMonthFactory
{
    private TenRentBillDaysOfMonthFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenRentBillDaysOfMonth getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenRentBillDaysOfMonth)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9675751E") ,com.kingdee.eas.fdc.tenancy.ITenRentBillDaysOfMonth.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenRentBillDaysOfMonth getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenRentBillDaysOfMonth)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9675751E") ,com.kingdee.eas.fdc.tenancy.ITenRentBillDaysOfMonth.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenRentBillDaysOfMonth getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenRentBillDaysOfMonth)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9675751E"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenRentBillDaysOfMonth getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenRentBillDaysOfMonth)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9675751E"));
    }
}