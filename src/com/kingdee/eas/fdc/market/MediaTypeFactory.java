package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MediaTypeFactory
{
    private MediaTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMediaType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMediaType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("71DA768D") ,com.kingdee.eas.fdc.market.IMediaType.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMediaType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMediaType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("71DA768D") ,com.kingdee.eas.fdc.market.IMediaType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMediaType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMediaType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("71DA768D"));
    }
    public static com.kingdee.eas.fdc.market.IMediaType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMediaType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("71DA768D"));
    }
}