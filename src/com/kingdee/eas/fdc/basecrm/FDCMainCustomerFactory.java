package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCMainCustomerFactory
{
    private FDCMainCustomerFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCMainCustomer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCMainCustomer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BD66CDE0") ,com.kingdee.eas.fdc.basecrm.IFDCMainCustomer.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.IFDCMainCustomer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCMainCustomer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BD66CDE0") ,com.kingdee.eas.fdc.basecrm.IFDCMainCustomer.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCMainCustomer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCMainCustomer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BD66CDE0"));
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCMainCustomer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCMainCustomer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BD66CDE0"));
    }
}