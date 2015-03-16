package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyCustomerEntryFactory
{
    private TenancyCustomerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyCustomerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyCustomerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E544FB3D") ,com.kingdee.eas.fdc.tenancy.ITenancyCustomerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyCustomerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyCustomerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E544FB3D") ,com.kingdee.eas.fdc.tenancy.ITenancyCustomerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyCustomerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyCustomerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E544FB3D"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyCustomerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyCustomerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E544FB3D"));
    }
}