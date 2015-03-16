package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChannelTypeFactory
{
    private ChannelTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IChannelType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IChannelType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("878CC46C") ,com.kingdee.eas.fdc.market.IChannelType.class);
    }
    
    public static com.kingdee.eas.fdc.market.IChannelType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IChannelType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("878CC46C") ,com.kingdee.eas.fdc.market.IChannelType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IChannelType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IChannelType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("878CC46C"));
    }
    public static com.kingdee.eas.fdc.market.IChannelType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IChannelType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("878CC46C"));
    }
}