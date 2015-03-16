package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OrgCustomerLinkManFactory
{
    private OrgCustomerLinkManFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.IOrgCustomerLinkMan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IOrgCustomerLinkMan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6855C61A") ,com.kingdee.eas.fdc.basecrm.IOrgCustomerLinkMan.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.IOrgCustomerLinkMan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IOrgCustomerLinkMan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6855C61A") ,com.kingdee.eas.fdc.basecrm.IOrgCustomerLinkMan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.IOrgCustomerLinkMan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IOrgCustomerLinkMan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6855C61A"));
    }
    public static com.kingdee.eas.fdc.basecrm.IOrgCustomerLinkMan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IOrgCustomerLinkMan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6855C61A"));
    }
}