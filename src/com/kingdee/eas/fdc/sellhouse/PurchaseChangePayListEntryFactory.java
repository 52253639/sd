package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseChangePayListEntryFactory
{
    private PurchaseChangePayListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("34B74478") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("34B74478") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("34B74478"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("34B74478"));
    }
}