package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSurveyFactory
{
    private MarketSurveyFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMarketSurvey getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketSurvey)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6A863F07") ,com.kingdee.eas.fdc.market.IMarketSurvey.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMarketSurvey getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketSurvey)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6A863F07") ,com.kingdee.eas.fdc.market.IMarketSurvey.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMarketSurvey getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketSurvey)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6A863F07"));
    }
    public static com.kingdee.eas.fdc.market.IMarketSurvey getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMarketSurvey)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6A863F07"));
    }
}