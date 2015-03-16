package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SinCustomerEntrysFactory
{
    private SinCustomerEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ISinCustomerEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISinCustomerEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4759782E") ,com.kingdee.eas.fdc.tenancy.ISinCustomerEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ISinCustomerEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISinCustomerEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4759782E") ,com.kingdee.eas.fdc.tenancy.ISinCustomerEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ISinCustomerEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISinCustomerEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4759782E"));
    }
    public static com.kingdee.eas.fdc.tenancy.ISinCustomerEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISinCustomerEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4759782E"));
    }
}