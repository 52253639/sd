package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSplAreaFactory
{
    private MarketSplAreaFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplArea getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplArea)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F48FCB73") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplArea.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplArea getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplArea)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F48FCB73") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplArea.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplArea getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplArea)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F48FCB73"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplArea getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplArea)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F48FCB73"));
    }
}