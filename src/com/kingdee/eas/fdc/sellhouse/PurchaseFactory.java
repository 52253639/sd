package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseFactory
{
    private PurchaseFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchase getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchase)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D2CB60DC") ,com.kingdee.eas.fdc.sellhouse.IPurchase.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchase getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchase)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D2CB60DC") ,com.kingdee.eas.fdc.sellhouse.IPurchase.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchase getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchase)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D2CB60DC"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchase getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchase)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D2CB60DC"));
    }
}