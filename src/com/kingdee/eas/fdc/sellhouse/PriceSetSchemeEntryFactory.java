package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PriceSetSchemeEntryFactory
{
    private PriceSetSchemeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPriceSetSchemeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPriceSetSchemeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5470A2F9") ,com.kingdee.eas.fdc.sellhouse.IPriceSetSchemeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPriceSetSchemeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPriceSetSchemeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5470A2F9") ,com.kingdee.eas.fdc.sellhouse.IPriceSetSchemeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPriceSetSchemeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPriceSetSchemeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5470A2F9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPriceSetSchemeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPriceSetSchemeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5470A2F9"));
    }
}