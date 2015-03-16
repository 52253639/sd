package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketLevelSetUpFactory
{
    private MarketLevelSetUpFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketLevelSetUp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketLevelSetUp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("508EA982") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketLevelSetUp.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketLevelSetUp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketLevelSetUp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("508EA982") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketLevelSetUp.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketLevelSetUp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketLevelSetUp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("508EA982"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketLevelSetUp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketLevelSetUp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("508EA982"));
    }
}