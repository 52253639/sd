package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSplServiceTypeFactory
{
    private MarketSplServiceTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplServiceType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplServiceType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("53AE7DE9") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplServiceType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplServiceType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplServiceType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("53AE7DE9") ,com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplServiceType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplServiceType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplServiceType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("53AE7DE9"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplServiceType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.IMarketSplServiceType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("53AE7DE9"));
    }
}