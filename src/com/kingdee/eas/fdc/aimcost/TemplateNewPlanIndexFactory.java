package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TemplateNewPlanIndexFactory
{
    private TemplateNewPlanIndexFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndex getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndex)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("046EFADA") ,com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndex.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndex getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndex)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("046EFADA") ,com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndex.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndex getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndex)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("046EFADA"));
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndex getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndex)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("046EFADA"));
    }
}