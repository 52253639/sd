package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class StoreOptionFactory
{
    private StoreOptionFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IStoreOption getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IStoreOption)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("032FFE45") ,com.kingdee.eas.fdc.market.IStoreOption.class);
    }
    
    public static com.kingdee.eas.fdc.market.IStoreOption getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IStoreOption)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("032FFE45") ,com.kingdee.eas.fdc.market.IStoreOption.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IStoreOption getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IStoreOption)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("032FFE45"));
    }
    public static com.kingdee.eas.fdc.market.IStoreOption getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IStoreOption)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("032FFE45"));
    }
}