package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectCostChangeLogFactory
{
    private ProjectCostChangeLogFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IProjectCostChangeLog getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProjectCostChangeLog)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7839CD65") ,com.kingdee.eas.fdc.aimcost.IProjectCostChangeLog.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IProjectCostChangeLog getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProjectCostChangeLog)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7839CD65") ,com.kingdee.eas.fdc.aimcost.IProjectCostChangeLog.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IProjectCostChangeLog getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProjectCostChangeLog)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7839CD65"));
    }
    public static com.kingdee.eas.fdc.aimcost.IProjectCostChangeLog getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProjectCostChangeLog)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7839CD65"));
    }
}