package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketUnitControlFactory
{
    private MarketUnitControlFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IMarketUnitControl getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketUnitControl)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("82F132D4") ,com.kingdee.eas.fdc.tenancy.IMarketUnitControl.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IMarketUnitControl getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketUnitControl)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("82F132D4") ,com.kingdee.eas.fdc.tenancy.IMarketUnitControl.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IMarketUnitControl getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketUnitControl)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("82F132D4"));
    }
    public static com.kingdee.eas.fdc.tenancy.IMarketUnitControl getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketUnitControl)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("82F132D4"));
    }
}