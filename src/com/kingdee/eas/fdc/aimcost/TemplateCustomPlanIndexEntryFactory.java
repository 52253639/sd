package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TemplateCustomPlanIndexEntryFactory
{
    private TemplateCustomPlanIndexEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplateCustomPlanIndexEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateCustomPlanIndexEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2619590B") ,com.kingdee.eas.fdc.aimcost.ITemplateCustomPlanIndexEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ITemplateCustomPlanIndexEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateCustomPlanIndexEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2619590B") ,com.kingdee.eas.fdc.aimcost.ITemplateCustomPlanIndexEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplateCustomPlanIndexEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateCustomPlanIndexEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2619590B"));
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplateCustomPlanIndexEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateCustomPlanIndexEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2619590B"));
    }
}