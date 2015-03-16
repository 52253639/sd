package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketingUnitSellProjectFactory
{
    private MarketingUnitSellProjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IMarketingUnitSellProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketingUnitSellProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B5FA2AE6") ,com.kingdee.eas.fdc.tenancy.IMarketingUnitSellProject.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IMarketingUnitSellProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketingUnitSellProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B5FA2AE6") ,com.kingdee.eas.fdc.tenancy.IMarketingUnitSellProject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IMarketingUnitSellProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketingUnitSellProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B5FA2AE6"));
    }
    public static com.kingdee.eas.fdc.tenancy.IMarketingUnitSellProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMarketingUnitSellProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B5FA2AE6"));
    }
}