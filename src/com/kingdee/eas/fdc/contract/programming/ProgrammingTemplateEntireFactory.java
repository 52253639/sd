package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingTemplateEntireFactory
{
    private ProgrammingTemplateEntireFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingTemplateEntire getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingTemplateEntire)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B9EEC6B4") ,com.kingdee.eas.fdc.contract.programming.IProgrammingTemplateEntire.class);
    }
    
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingTemplateEntire getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingTemplateEntire)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B9EEC6B4") ,com.kingdee.eas.fdc.contract.programming.IProgrammingTemplateEntire.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingTemplateEntire getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingTemplateEntire)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B9EEC6B4"));
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingTemplateEntire getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingTemplateEntire)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B9EEC6B4"));
    }
}