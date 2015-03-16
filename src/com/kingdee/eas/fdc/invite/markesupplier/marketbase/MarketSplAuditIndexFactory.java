package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSplAuditIndexFactory
{
    private MarketSplAuditIndexFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndex getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndex)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2800841D") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndex.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndex getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndex)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2800841D") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndex.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndex getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndex)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2800841D"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndex getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplAuditIndex)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2800841D"));
    }
}