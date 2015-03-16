package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AgencyPersonFactory
{
    private AgencyPersonFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IAgencyPerson getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAgencyPerson)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("531047E3") ,com.kingdee.eas.fdc.tenancy.IAgencyPerson.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IAgencyPerson getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAgencyPerson)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("531047E3") ,com.kingdee.eas.fdc.tenancy.IAgencyPerson.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IAgencyPerson getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAgencyPerson)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("531047E3"));
    }
    public static com.kingdee.eas.fdc.tenancy.IAgencyPerson getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAgencyPerson)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("531047E3"));
    }
}