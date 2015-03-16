package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketAccreditationTypeFactory
{
    private MarketAccreditationTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketAccreditationType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketAccreditationType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E6C5EC6B") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketAccreditationType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketAccreditationType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketAccreditationType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E6C5EC6B") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketAccreditationType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketAccreditationType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketAccreditationType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E6C5EC6B"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketAccreditationType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketAccreditationType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E6C5EC6B"));
    }
}