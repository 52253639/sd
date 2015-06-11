package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TemplateNewPlanIndexPTFactory
{
    private TemplateNewPlanIndexPTFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndexPT getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndexPT)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A49BB65E") ,com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndexPT.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndexPT getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndexPT)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A49BB65E") ,com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndexPT.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndexPT getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndexPT)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A49BB65E"));
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndexPT getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateNewPlanIndexPT)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A49BB65E"));
    }
}