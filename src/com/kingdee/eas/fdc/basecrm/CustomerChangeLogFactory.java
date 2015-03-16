package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerChangeLogFactory
{
    private CustomerChangeLogFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.ICustomerChangeLog getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ICustomerChangeLog)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E7FDDB5A") ,com.kingdee.eas.fdc.basecrm.ICustomerChangeLog.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.ICustomerChangeLog getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ICustomerChangeLog)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E7FDDB5A") ,com.kingdee.eas.fdc.basecrm.ICustomerChangeLog.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.ICustomerChangeLog getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ICustomerChangeLog)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E7FDDB5A"));
    }
    public static com.kingdee.eas.fdc.basecrm.ICustomerChangeLog getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ICustomerChangeLog)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E7FDDB5A"));
    }
}