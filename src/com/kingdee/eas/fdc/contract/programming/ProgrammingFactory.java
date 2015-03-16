package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingFactory
{
    private ProgrammingFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgramming getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgramming)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("53B0BDA9") ,com.kingdee.eas.fdc.contract.programming.IProgramming.class);
    }
    
    public static com.kingdee.eas.fdc.contract.programming.IProgramming getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgramming)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("53B0BDA9") ,com.kingdee.eas.fdc.contract.programming.IProgramming.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgramming getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgramming)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("53B0BDA9"));
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgramming getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgramming)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("53B0BDA9"));
    }
}