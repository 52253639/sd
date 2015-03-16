package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseSaleManFactory
{
    private PurchaseSaleManFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseSaleMan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseSaleMan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0B777B17") ,com.kingdee.eas.fdc.sellhouse.IPurchaseSaleMan.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseSaleMan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseSaleMan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0B777B17") ,com.kingdee.eas.fdc.sellhouse.IPurchaseSaleMan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseSaleMan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseSaleMan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0B777B17"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseSaleMan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseSaleMan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0B777B17"));
    }
}