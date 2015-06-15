package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationTypeFactory
{
    private EvaluationTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IEvaluationType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IEvaluationType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("71588B91") ,com.kingdee.eas.fdc.contract.IEvaluationType.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IEvaluationType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IEvaluationType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("71588B91") ,com.kingdee.eas.fdc.contract.IEvaluationType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IEvaluationType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IEvaluationType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("71588B91"));
    }
    public static com.kingdee.eas.fdc.contract.IEvaluationType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IEvaluationType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("71588B91"));
    }
}