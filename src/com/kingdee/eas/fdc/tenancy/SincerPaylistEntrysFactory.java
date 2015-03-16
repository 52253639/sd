package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SincerPaylistEntrysFactory
{
    private SincerPaylistEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ISincerPaylistEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISincerPaylistEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9D1F2546") ,com.kingdee.eas.fdc.tenancy.ISincerPaylistEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ISincerPaylistEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISincerPaylistEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9D1F2546") ,com.kingdee.eas.fdc.tenancy.ISincerPaylistEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ISincerPaylistEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISincerPaylistEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9D1F2546"));
    }
    public static com.kingdee.eas.fdc.tenancy.ISincerPaylistEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISincerPaylistEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9D1F2546"));
    }
}