package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TemplateMeasureCostFactory
{
    private TemplateMeasureCostFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplateMeasureCost getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateMeasureCost)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("241306BA") ,com.kingdee.eas.fdc.aimcost.ITemplateMeasureCost.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ITemplateMeasureCost getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateMeasureCost)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("241306BA") ,com.kingdee.eas.fdc.aimcost.ITemplateMeasureCost.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplateMeasureCost getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateMeasureCost)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("241306BA"));
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplateMeasureCost getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateMeasureCost)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("241306BA"));
    }
}