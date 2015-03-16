package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractPayPlanFactory
{
    private ContractPayPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IContractPayPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("350A8590") ,com.kingdee.eas.fdc.finance.IContractPayPlan.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IContractPayPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("350A8590") ,com.kingdee.eas.fdc.finance.IContractPayPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IContractPayPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("350A8590"));
    }
    public static com.kingdee.eas.fdc.finance.IContractPayPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("350A8590"));
    }
}