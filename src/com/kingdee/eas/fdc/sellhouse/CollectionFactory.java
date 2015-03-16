package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CollectionFactory
{
    private CollectionFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICollection getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICollection)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D3F07FB9") ,com.kingdee.eas.fdc.sellhouse.ICollection.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICollection getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICollection)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D3F07FB9") ,com.kingdee.eas.fdc.sellhouse.ICollection.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICollection getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICollection)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D3F07FB9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICollection getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICollection)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D3F07FB9"));
    }
}