package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PlanEntryFactory
{
    private PlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AC337651") ,com.kingdee.eas.fdc.finance.IPlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AC337651") ,com.kingdee.eas.fdc.finance.IPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AC337651"));
    }
    public static com.kingdee.eas.fdc.finance.IPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AC337651"));
    }
}