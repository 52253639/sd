package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ValueBreakEntryFactory
{
    private ValueBreakEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IValueBreakEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueBreakEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("62FEB913") ,com.kingdee.eas.fdc.market.IValueBreakEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IValueBreakEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueBreakEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("62FEB913") ,com.kingdee.eas.fdc.market.IValueBreakEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IValueBreakEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueBreakEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("62FEB913"));
    }
    public static com.kingdee.eas.fdc.market.IValueBreakEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueBreakEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("62FEB913"));
    }
}