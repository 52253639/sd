package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationTypeEntryFactory
{
    private EvaluationTypeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IEvaluationTypeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IEvaluationTypeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D2B51F61") ,com.kingdee.eas.fdc.contract.IEvaluationTypeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IEvaluationTypeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IEvaluationTypeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D2B51F61") ,com.kingdee.eas.fdc.contract.IEvaluationTypeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IEvaluationTypeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IEvaluationTypeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D2B51F61"));
    }
    public static com.kingdee.eas.fdc.contract.IEvaluationTypeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IEvaluationTypeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D2B51F61"));
    }
}