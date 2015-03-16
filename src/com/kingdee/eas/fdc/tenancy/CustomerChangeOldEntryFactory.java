package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerChangeOldEntryFactory
{
    private CustomerChangeOldEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerChangeOldEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerChangeOldEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A1693EC2") ,com.kingdee.eas.fdc.tenancy.ICustomerChangeOldEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ICustomerChangeOldEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerChangeOldEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A1693EC2") ,com.kingdee.eas.fdc.tenancy.ICustomerChangeOldEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerChangeOldEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerChangeOldEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A1693EC2"));
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerChangeOldEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerChangeOldEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A1693EC2"));
    }
}