package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerChangeNameFactory
{
    private CustomerChangeNameFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerChangeName getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerChangeName)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("555D4242") ,com.kingdee.eas.fdc.tenancy.ICustomerChangeName.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ICustomerChangeName getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerChangeName)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("555D4242") ,com.kingdee.eas.fdc.tenancy.ICustomerChangeName.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerChangeName getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerChangeName)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("555D4242"));
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerChangeName getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerChangeName)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("555D4242"));
    }
}