package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractPayPlanNewPayByStageFactory
{
    private ContractPayPlanNewPayByStageFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IContractPayPlanNewPayByStage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlanNewPayByStage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("11F5712F") ,com.kingdee.eas.fdc.finance.IContractPayPlanNewPayByStage.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IContractPayPlanNewPayByStage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlanNewPayByStage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("11F5712F") ,com.kingdee.eas.fdc.finance.IContractPayPlanNewPayByStage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IContractPayPlanNewPayByStage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlanNewPayByStage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("11F5712F"));
    }
    public static com.kingdee.eas.fdc.finance.IContractPayPlanNewPayByStage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IContractPayPlanNewPayByStage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("11F5712F"));
    }
}