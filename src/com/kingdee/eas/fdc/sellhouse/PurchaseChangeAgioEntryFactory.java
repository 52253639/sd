package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseChangeAgioEntryFactory
{
    private PurchaseChangeAgioEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8CB3A5BA") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8CB3A5BA") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8CB3A5BA"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8CB3A5BA"));
    }
}