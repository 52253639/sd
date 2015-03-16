package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangePayListEntryFactory
{
    private ChangePayListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangePayListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangePayListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B165AC97") ,com.kingdee.eas.fdc.sellhouse.IChangePayListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChangePayListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangePayListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B165AC97") ,com.kingdee.eas.fdc.sellhouse.IChangePayListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangePayListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangePayListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B165AC97"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangePayListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangePayListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B165AC97"));
    }
}