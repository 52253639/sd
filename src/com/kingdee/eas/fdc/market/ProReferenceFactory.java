package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProReferenceFactory
{
    private ProReferenceFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IProReference getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IProReference)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("472F936F") ,com.kingdee.eas.fdc.market.IProReference.class);
    }
    
    public static com.kingdee.eas.fdc.market.IProReference getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IProReference)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("472F936F") ,com.kingdee.eas.fdc.market.IProReference.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IProReference getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IProReference)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("472F936F"));
    }
    public static com.kingdee.eas.fdc.market.IProReference getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IProReference)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("472F936F"));
    }
}