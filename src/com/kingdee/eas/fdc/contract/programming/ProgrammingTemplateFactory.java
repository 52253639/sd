package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingTemplateFactory
{
    private ProgrammingTemplateFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingTemplate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingTemplate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BFE63543") ,com.kingdee.eas.fdc.contract.programming.IProgrammingTemplate.class);
    }
    
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingTemplate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingTemplate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BFE63543") ,com.kingdee.eas.fdc.contract.programming.IProgrammingTemplate.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingTemplate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingTemplate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BFE63543"));
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingTemplate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingTemplate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BFE63543"));
    }
}