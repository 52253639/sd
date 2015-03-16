package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerChangeEntryFactory
{
    private CustomerChangeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerChangeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerChangeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("55D2379B") ,com.kingdee.eas.fdc.tenancy.ICustomerChangeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ICustomerChangeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerChangeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("55D2379B") ,com.kingdee.eas.fdc.tenancy.ICustomerChangeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerChangeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerChangeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("55D2379B"));
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerChangeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerChangeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("55D2379B"));
    }
}