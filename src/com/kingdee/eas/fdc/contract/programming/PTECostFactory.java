package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PTECostFactory
{
    private PTECostFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.programming.IPTECost getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IPTECost)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A1B5BF1E") ,com.kingdee.eas.fdc.contract.programming.IPTECost.class);
    }
    
    public static com.kingdee.eas.fdc.contract.programming.IPTECost getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IPTECost)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A1B5BF1E") ,com.kingdee.eas.fdc.contract.programming.IPTECost.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.programming.IPTECost getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IPTECost)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A1B5BF1E"));
    }
    public static com.kingdee.eas.fdc.contract.programming.IPTECost getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IPTECost)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A1B5BF1E"));
    }
}