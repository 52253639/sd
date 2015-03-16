package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AgioSchemeEntryFactory
{
    private AgioSchemeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgioSchemeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgioSchemeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1E27B226") ,com.kingdee.eas.fdc.sellhouse.IAgioSchemeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IAgioSchemeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgioSchemeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1E27B226") ,com.kingdee.eas.fdc.sellhouse.IAgioSchemeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgioSchemeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgioSchemeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1E27B226"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgioSchemeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgioSchemeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1E27B226"));
    }
}