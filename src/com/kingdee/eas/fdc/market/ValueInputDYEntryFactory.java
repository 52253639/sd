package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ValueInputDYEntryFactory
{
    private ValueInputDYEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IValueInputDYEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueInputDYEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E0947033") ,com.kingdee.eas.fdc.market.IValueInputDYEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IValueInputDYEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueInputDYEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E0947033") ,com.kingdee.eas.fdc.market.IValueInputDYEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IValueInputDYEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueInputDYEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E0947033"));
    }
    public static com.kingdee.eas.fdc.market.IValueInputDYEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueInputDYEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E0947033"));
    }
}