package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuitOldPayListEntryFactory
{
    private QuitOldPayListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitOldPayListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitOldPayListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9EA7D169") ,com.kingdee.eas.fdc.sellhouse.IQuitOldPayListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IQuitOldPayListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitOldPayListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9EA7D169") ,com.kingdee.eas.fdc.sellhouse.IQuitOldPayListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitOldPayListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitOldPayListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9EA7D169"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitOldPayListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitOldPayListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9EA7D169"));
    }
}