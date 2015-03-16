package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ArrearsFacadeFactory
{
    private ArrearsFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IArrearsFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IArrearsFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("628EA4EF") ,com.kingdee.eas.fdc.tenancy.IArrearsFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IArrearsFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IArrearsFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("628EA4EF") ,com.kingdee.eas.fdc.tenancy.IArrearsFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IArrearsFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IArrearsFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("628EA4EF"));
    }
    public static com.kingdee.eas.fdc.tenancy.IArrearsFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IArrearsFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("628EA4EF"));
    }
}