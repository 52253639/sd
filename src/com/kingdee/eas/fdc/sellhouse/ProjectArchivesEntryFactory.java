package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectArchivesEntryFactory
{
    private ProjectArchivesEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IProjectArchivesEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IProjectArchivesEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("92090FC3") ,com.kingdee.eas.fdc.sellhouse.IProjectArchivesEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IProjectArchivesEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IProjectArchivesEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("92090FC3") ,com.kingdee.eas.fdc.sellhouse.IProjectArchivesEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IProjectArchivesEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IProjectArchivesEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("92090FC3"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IProjectArchivesEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IProjectArchivesEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("92090FC3"));
    }
}