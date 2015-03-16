package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DefaultCollectionFactory
{
    private DefaultCollectionFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultCollection getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultCollection)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("74410E44") ,com.kingdee.eas.fdc.sellhouse.IDefaultCollection.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDefaultCollection getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultCollection)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("74410E44") ,com.kingdee.eas.fdc.sellhouse.IDefaultCollection.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultCollection getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultCollection)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("74410E44"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultCollection getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultCollection)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("74410E44"));
    }
}