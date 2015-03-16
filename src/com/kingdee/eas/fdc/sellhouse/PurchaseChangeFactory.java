package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseChangeFactory
{
    private PurchaseChangeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChange getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChange)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DE662846") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChange.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChange getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChange)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DE662846") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChange.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChange getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChange)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DE662846"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChange getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChange)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DE662846"));
    }
}