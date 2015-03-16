package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseAgioEntryFactory
{
    private PurchaseAgioEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseAgioEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseAgioEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7FEF668A") ,com.kingdee.eas.fdc.sellhouse.IPurchaseAgioEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseAgioEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseAgioEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7FEF668A") ,com.kingdee.eas.fdc.sellhouse.IPurchaseAgioEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseAgioEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseAgioEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7FEF668A"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseAgioEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseAgioEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7FEF668A"));
    }
}