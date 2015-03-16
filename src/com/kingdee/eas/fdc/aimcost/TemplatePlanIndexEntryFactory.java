package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TemplatePlanIndexEntryFactory
{
    private TemplatePlanIndexEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplatePlanIndexEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplatePlanIndexEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A2A6C3FA") ,com.kingdee.eas.fdc.aimcost.ITemplatePlanIndexEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ITemplatePlanIndexEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplatePlanIndexEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A2A6C3FA") ,com.kingdee.eas.fdc.aimcost.ITemplatePlanIndexEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplatePlanIndexEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplatePlanIndexEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A2A6C3FA"));
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplatePlanIndexEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplatePlanIndexEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A2A6C3FA"));
    }
}