package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ArchivesEntryFactory
{
    private ArchivesEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IArchivesEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IArchivesEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C90B0406") ,com.kingdee.eas.fdc.sellhouse.IArchivesEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IArchivesEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IArchivesEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C90B0406") ,com.kingdee.eas.fdc.sellhouse.IArchivesEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IArchivesEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IArchivesEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C90B0406"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IArchivesEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IArchivesEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C90B0406"));
    }
}