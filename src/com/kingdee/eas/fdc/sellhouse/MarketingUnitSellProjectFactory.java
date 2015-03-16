package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketingUnitSellProjectFactory
{
    private MarketingUnitSellProjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketingUnitSellProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketingUnitSellProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("88C8EA18") ,com.kingdee.eas.fdc.sellhouse.IMarketingUnitSellProject.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMarketingUnitSellProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketingUnitSellProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("88C8EA18") ,com.kingdee.eas.fdc.sellhouse.IMarketingUnitSellProject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketingUnitSellProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketingUnitSellProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("88C8EA18"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketingUnitSellProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketingUnitSellProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("88C8EA18"));
    }
}