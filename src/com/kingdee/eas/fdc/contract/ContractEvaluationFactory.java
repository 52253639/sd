package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractEvaluationFactory
{
    private ContractEvaluationFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractEvaluation getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractEvaluation)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A45ACF69") ,com.kingdee.eas.fdc.contract.IContractEvaluation.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractEvaluation getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractEvaluation)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A45ACF69") ,com.kingdee.eas.fdc.contract.IContractEvaluation.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractEvaluation getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractEvaluation)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A45ACF69"));
    }
    public static com.kingdee.eas.fdc.contract.IContractEvaluation getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractEvaluation)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A45ACF69"));
    }
}