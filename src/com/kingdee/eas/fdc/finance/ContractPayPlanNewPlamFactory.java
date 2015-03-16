package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractPayPlanNewPlamFactory
{
    private ContractPayPlanNewPlamFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IContractPayPlanNewPlam getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlanNewPlam)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("27E18938") ,com.kingdee.eas.fdc.finance.IContractPayPlanNewPlam.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IContractPayPlanNewPlam getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlanNewPlam)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("27E18938") ,com.kingdee.eas.fdc.finance.IContractPayPlanNewPlam.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IContractPayPlanNewPlam getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlanNewPlam)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("27E18938"));
    }
    public static com.kingdee.eas.fdc.finance.IContractPayPlanNewPlam getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlanNewPlam)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("27E18938"));
    }
}