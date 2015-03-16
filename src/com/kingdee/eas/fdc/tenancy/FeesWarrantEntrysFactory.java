package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FeesWarrantEntrysFactory
{
    private FeesWarrantEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IFeesWarrantEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IFeesWarrantEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2C0D2C88") ,com.kingdee.eas.fdc.tenancy.IFeesWarrantEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IFeesWarrantEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IFeesWarrantEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2C0D2C88") ,com.kingdee.eas.fdc.tenancy.IFeesWarrantEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IFeesWarrantEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IFeesWarrantEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2C0D2C88"));
    }
    public static com.kingdee.eas.fdc.tenancy.IFeesWarrantEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IFeesWarrantEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2C0D2C88"));
    }
}