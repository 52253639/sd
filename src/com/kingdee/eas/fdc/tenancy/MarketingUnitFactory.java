package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketingUnitFactory
{
    private MarketingUnitFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IMarketingUnit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketingUnit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1C059DC1") ,com.kingdee.eas.fdc.tenancy.IMarketingUnit.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IMarketingUnit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketingUnit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1C059DC1") ,com.kingdee.eas.fdc.tenancy.IMarketingUnit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IMarketingUnit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketingUnit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1C059DC1"));
    }
    public static com.kingdee.eas.fdc.tenancy.IMarketingUnit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketingUnit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1C059DC1"));
    }
}