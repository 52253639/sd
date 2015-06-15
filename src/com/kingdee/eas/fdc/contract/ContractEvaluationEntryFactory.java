package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractEvaluationEntryFactory
{
    private ContractEvaluationEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractEvaluationEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractEvaluationEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F63B8289") ,com.kingdee.eas.fdc.contract.IContractEvaluationEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractEvaluationEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractEvaluationEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F63B8289") ,com.kingdee.eas.fdc.contract.IContractEvaluationEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractEvaluationEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractEvaluationEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F63B8289"));
    }
    public static com.kingdee.eas.fdc.contract.IContractEvaluationEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractEvaluationEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F63B8289"));
    }
}