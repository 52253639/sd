package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHEPriceHistoryEntryFactory
{
    private SHEPriceHistoryEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEPriceHistoryEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEPriceHistoryEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2F1FB8B2") ,com.kingdee.eas.fdc.sellhouse.ISHEPriceHistoryEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHEPriceHistoryEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEPriceHistoryEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2F1FB8B2") ,com.kingdee.eas.fdc.sellhouse.ISHEPriceHistoryEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEPriceHistoryEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEPriceHistoryEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2F1FB8B2"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEPriceHistoryEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEPriceHistoryEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2F1FB8B2"));
    }
}