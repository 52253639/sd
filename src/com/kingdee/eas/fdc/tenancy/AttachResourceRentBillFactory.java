package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AttachResourceRentBillFactory
{
    private AttachResourceRentBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachResourceRentBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachResourceRentBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E8050E5C") ,com.kingdee.eas.fdc.tenancy.IAttachResourceRentBill.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IAttachResourceRentBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachResourceRentBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E8050E5C") ,com.kingdee.eas.fdc.tenancy.IAttachResourceRentBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachResourceRentBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachResourceRentBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E8050E5C"));
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachResourceRentBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachResourceRentBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E8050E5C"));
    }
}