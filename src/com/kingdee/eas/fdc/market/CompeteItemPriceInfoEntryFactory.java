package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompeteItemPriceInfoEntryFactory
{
    private CompeteItemPriceInfoEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.ICompeteItemPriceInfoEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItemPriceInfoEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F5AD2EE4") ,com.kingdee.eas.fdc.market.ICompeteItemPriceInfoEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.ICompeteItemPriceInfoEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItemPriceInfoEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F5AD2EE4") ,com.kingdee.eas.fdc.market.ICompeteItemPriceInfoEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.ICompeteItemPriceInfoEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItemPriceInfoEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F5AD2EE4"));
    }
    public static com.kingdee.eas.fdc.market.ICompeteItemPriceInfoEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItemPriceInfoEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F5AD2EE4"));
    }
}