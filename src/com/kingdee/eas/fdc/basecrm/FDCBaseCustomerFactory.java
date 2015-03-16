package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCBaseCustomerFactory
{
    private FDCBaseCustomerFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCBaseCustomer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCBaseCustomer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BD3B36F8") ,com.kingdee.eas.fdc.basecrm.IFDCBaseCustomer.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.IFDCBaseCustomer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCBaseCustomer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BD3B36F8") ,com.kingdee.eas.fdc.basecrm.IFDCBaseCustomer.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCBaseCustomer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCBaseCustomer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BD3B36F8"));
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCBaseCustomer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCBaseCustomer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BD3B36F8"));
    }
}