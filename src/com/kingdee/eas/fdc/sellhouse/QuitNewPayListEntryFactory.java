package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuitNewPayListEntryFactory
{
    private QuitNewPayListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitNewPayListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitNewPayListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CB755F62") ,com.kingdee.eas.fdc.sellhouse.IQuitNewPayListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IQuitNewPayListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitNewPayListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CB755F62") ,com.kingdee.eas.fdc.sellhouse.IQuitNewPayListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitNewPayListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitNewPayListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CB755F62"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IQuitNewPayListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IQuitNewPayListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CB755F62"));
    }
}