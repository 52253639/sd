package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerRentalFacadeFactory
{
    private CustomerRentalFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerRentalFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerRentalFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FE7EE905") ,com.kingdee.eas.fdc.tenancy.ICustomerRentalFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ICustomerRentalFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerRentalFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FE7EE905") ,com.kingdee.eas.fdc.tenancy.ICustomerRentalFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerRentalFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerRentalFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FE7EE905"));
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerRentalFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerRentalFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FE7EE905"));
    }
}