package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompeteItemSaleInfoEntryFactory
{
    private CompeteItemSaleInfoEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.ICompeteItemSaleInfoEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItemSaleInfoEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AE07E4F4") ,com.kingdee.eas.fdc.market.ICompeteItemSaleInfoEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.ICompeteItemSaleInfoEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItemSaleInfoEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AE07E4F4") ,com.kingdee.eas.fdc.market.ICompeteItemSaleInfoEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.ICompeteItemSaleInfoEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItemSaleInfoEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AE07E4F4"));
    }
    public static com.kingdee.eas.fdc.market.ICompeteItemSaleInfoEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItemSaleInfoEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AE07E4F4"));
    }
}