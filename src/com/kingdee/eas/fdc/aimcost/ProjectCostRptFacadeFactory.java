package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectCostRptFacadeFactory
{
    private ProjectCostRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IProjectCostRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProjectCostRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0FC51A21") ,com.kingdee.eas.fdc.aimcost.IProjectCostRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IProjectCostRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProjectCostRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0FC51A21") ,com.kingdee.eas.fdc.aimcost.IProjectCostRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IProjectCostRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProjectCostRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0FC51A21"));
    }
    public static com.kingdee.eas.fdc.aimcost.IProjectCostRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProjectCostRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0FC51A21"));
    }
}