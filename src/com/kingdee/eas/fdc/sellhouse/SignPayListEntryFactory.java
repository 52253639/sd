package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SignPayListEntryFactory
{
    private SignPayListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignPayListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignPayListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("94DD95E4") ,com.kingdee.eas.fdc.sellhouse.ISignPayListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISignPayListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignPayListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("94DD95E4") ,com.kingdee.eas.fdc.sellhouse.ISignPayListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignPayListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignPayListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("94DD95E4"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignPayListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignPayListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("94DD95E4"));
    }
}