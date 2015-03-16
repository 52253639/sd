package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MediaFactory
{
    private MediaFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMedia getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMedia)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FC7A04B3") ,com.kingdee.eas.fdc.market.IMedia.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMedia getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMedia)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FC7A04B3") ,com.kingdee.eas.fdc.market.IMedia.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMedia getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMedia)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FC7A04B3"));
    }
    public static com.kingdee.eas.fdc.market.IMedia getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMedia)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FC7A04B3"));
    }
}