package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AgencyContractFactory
{
    private AgencyContractFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IAgencyContract getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAgencyContract)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("93E8D220") ,com.kingdee.eas.fdc.tenancy.IAgencyContract.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IAgencyContract getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAgencyContract)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("93E8D220") ,com.kingdee.eas.fdc.tenancy.IAgencyContract.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IAgencyContract getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAgencyContract)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("93E8D220"));
    }
    public static com.kingdee.eas.fdc.tenancy.IAgencyContract getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAgencyContract)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("93E8D220"));
    }
}