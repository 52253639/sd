package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeEntryFactory
{
    private ChangeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D79124E7") ,com.kingdee.eas.fdc.sellhouse.IChangeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChangeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D79124E7") ,com.kingdee.eas.fdc.sellhouse.IChangeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D79124E7"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D79124E7"));
    }
}