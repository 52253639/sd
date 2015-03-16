package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class StoreSubjectClassFactory
{
    private StoreSubjectClassFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IStoreSubjectClass getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IStoreSubjectClass)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3844997C") ,com.kingdee.eas.fdc.market.IStoreSubjectClass.class);
    }
    
    public static com.kingdee.eas.fdc.market.IStoreSubjectClass getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IStoreSubjectClass)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3844997C") ,com.kingdee.eas.fdc.market.IStoreSubjectClass.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IStoreSubjectClass getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IStoreSubjectClass)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3844997C"));
    }
    public static com.kingdee.eas.fdc.market.IStoreSubjectClass getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IStoreSubjectClass)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3844997C"));
    }
}