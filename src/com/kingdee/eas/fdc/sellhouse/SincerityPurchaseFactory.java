package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SincerityPurchaseFactory
{
    private SincerityPurchaseFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISincerityPurchase getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISincerityPurchase)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4994CEDC") ,com.kingdee.eas.fdc.sellhouse.ISincerityPurchase.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISincerityPurchase getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISincerityPurchase)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4994CEDC") ,com.kingdee.eas.fdc.sellhouse.ISincerityPurchase.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISincerityPurchase getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISincerityPurchase)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4994CEDC"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISincerityPurchase getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISincerityPurchase)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4994CEDC"));
    }
}