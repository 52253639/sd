package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuitPayListEntryFactory
{
    private QuitPayListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitPayListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitPayListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("62D08F76") ,com.kingdee.eas.fdc.sellhouse.IQuitPayListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IQuitPayListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitPayListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("62D08F76") ,com.kingdee.eas.fdc.sellhouse.IQuitPayListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitPayListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitPayListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("62D08F76"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitPayListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitPayListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("62D08F76"));
    }
}