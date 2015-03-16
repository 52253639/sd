package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RentRatioFacadeFactory
{
    private RentRatioFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IRentRatioFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentRatioFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BB055EC3") ,com.kingdee.eas.fdc.tenancy.IRentRatioFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IRentRatioFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentRatioFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BB055EC3") ,com.kingdee.eas.fdc.tenancy.IRentRatioFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IRentRatioFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentRatioFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BB055EC3"));
    }
    public static com.kingdee.eas.fdc.tenancy.IRentRatioFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentRatioFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BB055EC3"));
    }
}