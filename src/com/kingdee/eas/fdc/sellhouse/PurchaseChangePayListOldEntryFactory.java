package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseChangePayListOldEntryFactory
{
    private PurchaseChangePayListOldEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListOldEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListOldEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("223D28C5") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListOldEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListOldEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListOldEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("223D28C5") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListOldEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListOldEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListOldEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("223D28C5"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListOldEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangePayListOldEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("223D28C5"));
    }
}