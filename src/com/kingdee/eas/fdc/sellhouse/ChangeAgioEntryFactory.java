package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeAgioEntryFactory
{
    private ChangeAgioEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeAgioEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeAgioEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("393049FB") ,com.kingdee.eas.fdc.sellhouse.IChangeAgioEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChangeAgioEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeAgioEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("393049FB") ,com.kingdee.eas.fdc.sellhouse.IChangeAgioEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeAgioEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeAgioEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("393049FB"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeAgioEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeAgioEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("393049FB"));
    }
}