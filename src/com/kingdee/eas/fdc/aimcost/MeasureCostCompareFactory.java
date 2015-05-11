package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MeasureCostCompareFactory
{
    private MeasureCostCompareFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureCostCompare getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureCostCompare)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0FF211B1") ,com.kingdee.eas.fdc.aimcost.IMeasureCostCompare.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IMeasureCostCompare getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureCostCompare)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0FF211B1") ,com.kingdee.eas.fdc.aimcost.IMeasureCostCompare.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureCostCompare getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureCostCompare)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0FF211B1"));
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureCostCompare getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureCostCompare)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0FF211B1"));
    }
}