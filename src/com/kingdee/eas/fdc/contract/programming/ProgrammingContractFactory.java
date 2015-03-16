package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingContractFactory
{
    private ProgrammingContractFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingContract getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingContract)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("ECE079DB") ,com.kingdee.eas.fdc.contract.programming.IProgrammingContract.class);
    }
    
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingContract getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingContract)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("ECE079DB") ,com.kingdee.eas.fdc.contract.programming.IProgrammingContract.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingContract getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingContract)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("ECE079DB"));
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingContract getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingContract)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("ECE079DB"));
    }
}