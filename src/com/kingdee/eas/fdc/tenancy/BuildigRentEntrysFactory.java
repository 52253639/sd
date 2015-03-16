package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildigRentEntrysFactory
{
    private BuildigRentEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IBuildigRentEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBuildigRentEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F925217D") ,com.kingdee.eas.fdc.tenancy.IBuildigRentEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IBuildigRentEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBuildigRentEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F925217D") ,com.kingdee.eas.fdc.tenancy.IBuildigRentEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IBuildigRentEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBuildigRentEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F925217D"));
    }
    public static com.kingdee.eas.fdc.tenancy.IBuildigRentEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBuildigRentEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F925217D"));
    }
}