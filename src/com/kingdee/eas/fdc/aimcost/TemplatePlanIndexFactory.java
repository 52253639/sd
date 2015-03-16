package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TemplatePlanIndexFactory
{
    private TemplatePlanIndexFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplatePlanIndex getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplatePlanIndex)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0DF6AB58") ,com.kingdee.eas.fdc.aimcost.ITemplatePlanIndex.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ITemplatePlanIndex getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplatePlanIndex)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0DF6AB58") ,com.kingdee.eas.fdc.aimcost.ITemplatePlanIndex.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplatePlanIndex getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplatePlanIndex)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0DF6AB58"));
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplatePlanIndex getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplatePlanIndex)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0DF6AB58"));
    }
}