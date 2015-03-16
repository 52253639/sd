package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerTenancyAreaChangeFacadeFactory
{
    private CustomerTenancyAreaChangeFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerTenancyAreaChangeFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerTenancyAreaChangeFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2BFC0C30") ,com.kingdee.eas.fdc.tenancy.ICustomerTenancyAreaChangeFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ICustomerTenancyAreaChangeFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerTenancyAreaChangeFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2BFC0C30") ,com.kingdee.eas.fdc.tenancy.ICustomerTenancyAreaChangeFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerTenancyAreaChangeFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerTenancyAreaChangeFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2BFC0C30"));
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerTenancyAreaChangeFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerTenancyAreaChangeFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2BFC0C30"));
    }
}