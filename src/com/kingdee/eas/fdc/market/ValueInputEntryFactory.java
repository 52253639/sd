package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ValueInputEntryFactory
{
    private ValueInputEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IValueInputEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueInputEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("67F44128") ,com.kingdee.eas.fdc.market.IValueInputEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IValueInputEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueInputEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("67F44128") ,com.kingdee.eas.fdc.market.IValueInputEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IValueInputEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueInputEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("67F44128"));
    }
    public static com.kingdee.eas.fdc.market.IValueInputEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueInputEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("67F44128"));
    }
}