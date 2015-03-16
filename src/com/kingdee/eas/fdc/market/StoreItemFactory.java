package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class StoreItemFactory
{
    private StoreItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IStoreItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IStoreItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("571A24E3") ,com.kingdee.eas.fdc.market.IStoreItem.class);
    }
    
    public static com.kingdee.eas.fdc.market.IStoreItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IStoreItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("571A24E3") ,com.kingdee.eas.fdc.market.IStoreItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IStoreItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IStoreItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("571A24E3"));
    }
    public static com.kingdee.eas.fdc.market.IStoreItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IStoreItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("571A24E3"));
    }
}