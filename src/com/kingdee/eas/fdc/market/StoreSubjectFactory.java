package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class StoreSubjectFactory
{
    private StoreSubjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IStoreSubject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IStoreSubject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3DF3995C") ,com.kingdee.eas.fdc.market.IStoreSubject.class);
    }
    
    public static com.kingdee.eas.fdc.market.IStoreSubject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IStoreSubject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3DF3995C") ,com.kingdee.eas.fdc.market.IStoreSubject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IStoreSubject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IStoreSubject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3DF3995C"));
    }
    public static com.kingdee.eas.fdc.market.IStoreSubject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IStoreSubject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3DF3995C"));
    }
}