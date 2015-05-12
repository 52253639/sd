package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DepositDealBillFactory
{
    private DepositDealBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IDepositDealBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IDepositDealBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("756F2288") ,com.kingdee.eas.fdc.tenancy.IDepositDealBill.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IDepositDealBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IDepositDealBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("756F2288") ,com.kingdee.eas.fdc.tenancy.IDepositDealBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IDepositDealBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IDepositDealBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("756F2288"));
    }
    public static com.kingdee.eas.fdc.tenancy.IDepositDealBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IDepositDealBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("756F2288"));
    }
}