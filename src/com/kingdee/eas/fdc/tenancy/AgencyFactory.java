package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AgencyFactory
{
    private AgencyFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IAgency getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAgency)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E4042AEE") ,com.kingdee.eas.fdc.tenancy.IAgency.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IAgency getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAgency)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E4042AEE") ,com.kingdee.eas.fdc.tenancy.IAgency.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IAgency getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAgency)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E4042AEE"));
    }
    public static com.kingdee.eas.fdc.tenancy.IAgency getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAgency)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E4042AEE"));
    }
}