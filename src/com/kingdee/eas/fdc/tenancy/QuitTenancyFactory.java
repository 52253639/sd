package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuitTenancyFactory
{
    private QuitTenancyFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IQuitTenancy getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IQuitTenancy)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("756D1468") ,com.kingdee.eas.fdc.tenancy.IQuitTenancy.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IQuitTenancy getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IQuitTenancy)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("756D1468") ,com.kingdee.eas.fdc.tenancy.IQuitTenancy.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IQuitTenancy getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IQuitTenancy)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("756D1468"));
    }
    public static com.kingdee.eas.fdc.tenancy.IQuitTenancy getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IQuitTenancy)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("756D1468"));
    }
}