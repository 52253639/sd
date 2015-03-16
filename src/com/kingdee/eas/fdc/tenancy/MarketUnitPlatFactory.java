package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketUnitPlatFactory
{
    private MarketUnitPlatFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IMarketUnitPlat getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketUnitPlat)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4C278918") ,com.kingdee.eas.fdc.tenancy.IMarketUnitPlat.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IMarketUnitPlat getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketUnitPlat)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4C278918") ,com.kingdee.eas.fdc.tenancy.IMarketUnitPlat.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IMarketUnitPlat getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketUnitPlat)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4C278918"));
    }
    public static com.kingdee.eas.fdc.tenancy.IMarketUnitPlat getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketUnitPlat)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4C278918"));
    }
}