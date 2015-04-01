package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OtherBillFactory
{
    private OtherBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IOtherBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOtherBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("73402BAE") ,com.kingdee.eas.fdc.tenancy.IOtherBill.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IOtherBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOtherBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("73402BAE") ,com.kingdee.eas.fdc.tenancy.IOtherBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IOtherBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOtherBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("73402BAE"));
    }
    public static com.kingdee.eas.fdc.tenancy.IOtherBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOtherBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("73402BAE"));
    }
}