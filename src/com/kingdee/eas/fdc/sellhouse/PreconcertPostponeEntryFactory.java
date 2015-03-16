package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PreconcertPostponeEntryFactory
{
    private PreconcertPostponeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPreconcertPostponeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPreconcertPostponeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E68BEE5E") ,com.kingdee.eas.fdc.sellhouse.IPreconcertPostponeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPreconcertPostponeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPreconcertPostponeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E68BEE5E") ,com.kingdee.eas.fdc.sellhouse.IPreconcertPostponeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPreconcertPostponeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPreconcertPostponeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E68BEE5E"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPreconcertPostponeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPreconcertPostponeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E68BEE5E"));
    }
}