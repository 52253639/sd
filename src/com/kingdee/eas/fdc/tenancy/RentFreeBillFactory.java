package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RentFreeBillFactory
{
    private RentFreeBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IRentFreeBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentFreeBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A9348B15") ,com.kingdee.eas.fdc.tenancy.IRentFreeBill.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IRentFreeBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentFreeBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A9348B15") ,com.kingdee.eas.fdc.tenancy.IRentFreeBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IRentFreeBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentFreeBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A9348B15"));
    }
    public static com.kingdee.eas.fdc.tenancy.IRentFreeBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentFreeBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A9348B15"));
    }
}