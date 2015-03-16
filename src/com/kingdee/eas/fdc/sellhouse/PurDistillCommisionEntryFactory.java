package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurDistillCommisionEntryFactory
{
    private PurDistillCommisionEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurDistillCommisionEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurDistillCommisionEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1E119071") ,com.kingdee.eas.fdc.sellhouse.IPurDistillCommisionEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurDistillCommisionEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurDistillCommisionEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1E119071") ,com.kingdee.eas.fdc.sellhouse.IPurDistillCommisionEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurDistillCommisionEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurDistillCommisionEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1E119071"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurDistillCommisionEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurDistillCommisionEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1E119071"));
    }
}