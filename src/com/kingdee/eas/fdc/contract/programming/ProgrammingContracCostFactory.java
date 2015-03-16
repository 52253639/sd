package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingContracCostFactory
{
    private ProgrammingContracCostFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingContracCost getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingContracCost)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9E6FDD26") ,com.kingdee.eas.fdc.contract.programming.IProgrammingContracCost.class);
    }
    
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingContracCost getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingContracCost)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9E6FDD26") ,com.kingdee.eas.fdc.contract.programming.IProgrammingContracCost.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingContracCost getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingContracCost)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9E6FDD26"));
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingContracCost getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingContracCost)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9E6FDD26"));
    }
}