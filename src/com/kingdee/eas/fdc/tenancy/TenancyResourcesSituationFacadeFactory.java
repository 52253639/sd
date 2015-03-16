package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyResourcesSituationFacadeFactory
{
    private TenancyResourcesSituationFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyResourcesSituationFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyResourcesSituationFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E362F3AA") ,com.kingdee.eas.fdc.tenancy.ITenancyResourcesSituationFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyResourcesSituationFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyResourcesSituationFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E362F3AA") ,com.kingdee.eas.fdc.tenancy.ITenancyResourcesSituationFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyResourcesSituationFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyResourcesSituationFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E362F3AA"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyResourcesSituationFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyResourcesSituationFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E362F3AA"));
    }
}