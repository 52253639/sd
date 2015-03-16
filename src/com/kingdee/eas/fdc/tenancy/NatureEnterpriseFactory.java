package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NatureEnterpriseFactory
{
    private NatureEnterpriseFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.INatureEnterprise getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INatureEnterprise)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D4516711") ,com.kingdee.eas.fdc.tenancy.INatureEnterprise.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.INatureEnterprise getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INatureEnterprise)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D4516711") ,com.kingdee.eas.fdc.tenancy.INatureEnterprise.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.INatureEnterprise getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INatureEnterprise)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D4516711"));
    }
    public static com.kingdee.eas.fdc.tenancy.INatureEnterprise getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INatureEnterprise)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D4516711"));
    }
}