package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCOrgCustomerFactory
{
    private FDCOrgCustomerFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCOrgCustomer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCOrgCustomer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("79DAF899") ,com.kingdee.eas.fdc.basecrm.IFDCOrgCustomer.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.IFDCOrgCustomer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCOrgCustomer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("79DAF899") ,com.kingdee.eas.fdc.basecrm.IFDCOrgCustomer.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCOrgCustomer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCOrgCustomer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("79DAF899"));
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCOrgCustomer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCOrgCustomer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("79DAF899"));
    }
}