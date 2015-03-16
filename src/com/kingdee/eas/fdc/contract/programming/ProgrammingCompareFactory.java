package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingCompareFactory
{
    private ProgrammingCompareFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingCompare getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingCompare)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("93F716FC") ,com.kingdee.eas.fdc.contract.programming.IProgrammingCompare.class);
    }
    
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingCompare getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingCompare)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("93F716FC") ,com.kingdee.eas.fdc.contract.programming.IProgrammingCompare.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingCompare getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingCompare)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("93F716FC"));
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingCompare getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingCompare)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("93F716FC"));
    }
}