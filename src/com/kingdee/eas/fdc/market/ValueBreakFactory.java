package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ValueBreakFactory
{
    private ValueBreakFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IValueBreak getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueBreak)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CE9E2A9F") ,com.kingdee.eas.fdc.market.IValueBreak.class);
    }
    
    public static com.kingdee.eas.fdc.market.IValueBreak getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueBreak)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CE9E2A9F") ,com.kingdee.eas.fdc.market.IValueBreak.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IValueBreak getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueBreak)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CE9E2A9F"));
    }
    public static com.kingdee.eas.fdc.market.IValueBreak getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueBreak)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CE9E2A9F"));
    }
}