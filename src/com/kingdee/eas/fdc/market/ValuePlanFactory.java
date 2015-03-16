package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ValuePlanFactory
{
    private ValuePlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IValuePlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValuePlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C4A00589") ,com.kingdee.eas.fdc.market.IValuePlan.class);
    }
    
    public static com.kingdee.eas.fdc.market.IValuePlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValuePlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C4A00589") ,com.kingdee.eas.fdc.market.IValuePlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IValuePlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValuePlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C4A00589"));
    }
    public static com.kingdee.eas.fdc.market.IValuePlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValuePlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C4A00589"));
    }
}