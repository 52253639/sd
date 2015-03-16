package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurPayListEntryFactory
{
    private PurPayListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurPayListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurPayListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F0CE637D") ,com.kingdee.eas.fdc.sellhouse.IPurPayListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurPayListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurPayListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F0CE637D") ,com.kingdee.eas.fdc.sellhouse.IPurPayListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurPayListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurPayListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F0CE637D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurPayListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurPayListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F0CE637D"));
    }
}