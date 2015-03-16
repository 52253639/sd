package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchasePayListEntryFactory
{
    private PurchasePayListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchasePayListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchasePayListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8B4211A8") ,com.kingdee.eas.fdc.sellhouse.IPurchasePayListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchasePayListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchasePayListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8B4211A8") ,com.kingdee.eas.fdc.sellhouse.IPurchasePayListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchasePayListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchasePayListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8B4211A8"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchasePayListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchasePayListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8B4211A8"));
    }
}