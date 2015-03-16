package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayPlanFactory
{
    private PayPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPayPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DD879802") ,com.kingdee.eas.fdc.finance.IPayPlan.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPayPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DD879802") ,com.kingdee.eas.fdc.finance.IPayPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPayPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DD879802"));
    }
    public static com.kingdee.eas.fdc.finance.IPayPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DD879802"));
    }
}