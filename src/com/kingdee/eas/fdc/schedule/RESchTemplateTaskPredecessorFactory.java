package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RESchTemplateTaskPredecessorFactory
{
    private RESchTemplateTaskPredecessorFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IRESchTemplateTaskPredecessor getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplateTaskPredecessor)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C46BD47B") ,com.kingdee.eas.fdc.schedule.IRESchTemplateTaskPredecessor.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IRESchTemplateTaskPredecessor getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplateTaskPredecessor)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C46BD47B") ,com.kingdee.eas.fdc.schedule.IRESchTemplateTaskPredecessor.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IRESchTemplateTaskPredecessor getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplateTaskPredecessor)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C46BD47B"));
    }
    public static com.kingdee.eas.fdc.schedule.IRESchTemplateTaskPredecessor getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplateTaskPredecessor)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C46BD47B"));
    }
}