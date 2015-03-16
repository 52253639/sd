package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseElsePayListEntryFactory
{
    private PurchaseElsePayListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseElsePayListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseElsePayListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9D280781") ,com.kingdee.eas.fdc.sellhouse.IPurchaseElsePayListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseElsePayListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseElsePayListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9D280781") ,com.kingdee.eas.fdc.sellhouse.IPurchaseElsePayListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseElsePayListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseElsePayListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9D280781"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseElsePayListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseElsePayListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9D280781"));
    }
}