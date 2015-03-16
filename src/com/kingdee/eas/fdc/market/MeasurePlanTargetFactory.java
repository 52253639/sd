package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MeasurePlanTargetFactory
{
    private MeasurePlanTargetFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMeasurePlanTarget getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMeasurePlanTarget)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("09D0E067") ,com.kingdee.eas.fdc.market.IMeasurePlanTarget.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMeasurePlanTarget getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMeasurePlanTarget)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("09D0E067") ,com.kingdee.eas.fdc.market.IMeasurePlanTarget.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMeasurePlanTarget getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMeasurePlanTarget)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("09D0E067"));
    }
    public static com.kingdee.eas.fdc.market.IMeasurePlanTarget getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMeasurePlanTarget)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("09D0E067"));
    }
}