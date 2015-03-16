package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseChangeAgioOldEntryFactory
{
    private PurchaseChangeAgioOldEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioOldEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioOldEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("24F72843") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioOldEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioOldEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioOldEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("24F72843") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioOldEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioOldEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioOldEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("24F72843"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioOldEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeAgioOldEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("24F72843"));
    }
}