package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WorkAreaEntryFactory
{
    private WorkAreaEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IWorkAreaEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWorkAreaEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DE8E28D9") ,com.kingdee.eas.fdc.sellhouse.IWorkAreaEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IWorkAreaEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWorkAreaEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DE8E28D9") ,com.kingdee.eas.fdc.sellhouse.IWorkAreaEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IWorkAreaEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWorkAreaEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DE8E28D9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IWorkAreaEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWorkAreaEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DE8E28D9"));
    }
}