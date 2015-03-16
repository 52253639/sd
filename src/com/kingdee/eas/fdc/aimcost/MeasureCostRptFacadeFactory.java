package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MeasureCostRptFacadeFactory
{
    private MeasureCostRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureCostRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureCostRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1E3157FC") ,com.kingdee.eas.fdc.aimcost.IMeasureCostRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IMeasureCostRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureCostRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1E3157FC") ,com.kingdee.eas.fdc.aimcost.IMeasureCostRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureCostRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureCostRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1E3157FC"));
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureCostRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureCostRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1E3157FC"));
    }
}