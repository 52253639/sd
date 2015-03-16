package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChannelTypeTreeFactory
{
    private ChannelTypeTreeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IChannelTypeTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IChannelTypeTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B1A4962A") ,com.kingdee.eas.fdc.market.IChannelTypeTree.class);
    }
    
    public static com.kingdee.eas.fdc.market.IChannelTypeTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IChannelTypeTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B1A4962A") ,com.kingdee.eas.fdc.market.IChannelTypeTree.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IChannelTypeTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IChannelTypeTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B1A4962A"));
    }
    public static com.kingdee.eas.fdc.market.IChannelTypeTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IChannelTypeTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B1A4962A"));
    }
}