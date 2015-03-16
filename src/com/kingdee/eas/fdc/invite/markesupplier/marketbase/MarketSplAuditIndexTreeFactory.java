package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSplAuditIndexTreeFactory
{
    private MarketSplAuditIndexTreeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndexTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndexTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6DDF155B") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndexTree.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndexTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndexTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6DDF155B") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndexTree.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndexTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndexTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6DDF155B"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndexTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndexTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6DDF155B"));
    }
}