package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MeasurePlanTargetEntryFactory
{
    private MeasurePlanTargetEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMeasurePlanTargetEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMeasurePlanTargetEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("897E604B") ,com.kingdee.eas.fdc.market.IMeasurePlanTargetEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMeasurePlanTargetEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMeasurePlanTargetEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("897E604B") ,com.kingdee.eas.fdc.market.IMeasurePlanTargetEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMeasurePlanTargetEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMeasurePlanTargetEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("897E604B"));
    }
    public static com.kingdee.eas.fdc.market.IMeasurePlanTargetEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMeasurePlanTargetEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("897E604B"));
    }
}