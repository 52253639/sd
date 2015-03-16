package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompeteItemMarketingEntryFactory
{
    private CompeteItemMarketingEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.ICompeteItemMarketingEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItemMarketingEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DF68A3B5") ,com.kingdee.eas.fdc.market.ICompeteItemMarketingEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.ICompeteItemMarketingEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItemMarketingEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DF68A3B5") ,com.kingdee.eas.fdc.market.ICompeteItemMarketingEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.ICompeteItemMarketingEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItemMarketingEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DF68A3B5"));
    }
    public static com.kingdee.eas.fdc.market.ICompeteItemMarketingEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItemMarketingEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DF68A3B5"));
    }
}