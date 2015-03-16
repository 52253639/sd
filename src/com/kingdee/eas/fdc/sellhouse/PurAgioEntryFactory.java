package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurAgioEntryFactory
{
    private PurAgioEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurAgioEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurAgioEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("500EF1D4") ,com.kingdee.eas.fdc.sellhouse.IPurAgioEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurAgioEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurAgioEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("500EF1D4") ,com.kingdee.eas.fdc.sellhouse.IPurAgioEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurAgioEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurAgioEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("500EF1D4"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurAgioEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurAgioEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("500EF1D4"));
    }
}