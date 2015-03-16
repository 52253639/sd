package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MeasureCostFactory
{
    private MeasureCostFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureCost getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureCost)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("99193494") ,com.kingdee.eas.fdc.aimcost.IMeasureCost.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IMeasureCost getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureCost)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("99193494") ,com.kingdee.eas.fdc.aimcost.IMeasureCost.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureCost getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureCost)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("99193494"));
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureCost getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureCost)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("99193494"));
    }
}