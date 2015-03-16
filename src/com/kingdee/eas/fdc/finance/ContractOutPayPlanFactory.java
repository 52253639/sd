package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractOutPayPlanFactory
{
    private ContractOutPayPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IContractOutPayPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractOutPayPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FB970EC4") ,com.kingdee.eas.fdc.finance.IContractOutPayPlan.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IContractOutPayPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractOutPayPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FB970EC4") ,com.kingdee.eas.fdc.finance.IContractOutPayPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IContractOutPayPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractOutPayPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FB970EC4"));
    }
    public static com.kingdee.eas.fdc.finance.IContractOutPayPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractOutPayPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FB970EC4"));
    }
}