package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationResultFactory
{
    private EvaluationResultFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IEvaluationResult getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IEvaluationResult)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("78E20AB4") ,com.kingdee.eas.fdc.contract.IEvaluationResult.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IEvaluationResult getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IEvaluationResult)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("78E20AB4") ,com.kingdee.eas.fdc.contract.IEvaluationResult.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IEvaluationResult getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IEvaluationResult)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("78E20AB4"));
    }
    public static com.kingdee.eas.fdc.contract.IEvaluationResult getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IEvaluationResult)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("78E20AB4"));
    }
}