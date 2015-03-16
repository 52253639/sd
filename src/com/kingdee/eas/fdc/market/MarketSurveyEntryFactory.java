package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSurveyEntryFactory
{
    private MarketSurveyEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMarketSurveyEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketSurveyEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CA7B25AB") ,com.kingdee.eas.fdc.market.IMarketSurveyEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMarketSurveyEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketSurveyEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CA7B25AB") ,com.kingdee.eas.fdc.market.IMarketSurveyEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMarketSurveyEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketSurveyEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CA7B25AB"));
    }
    public static com.kingdee.eas.fdc.market.IMarketSurveyEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketSurveyEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CA7B25AB"));
    }
}