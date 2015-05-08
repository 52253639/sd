package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RESchTemplateTaskFactory
{
    private RESchTemplateTaskFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IRESchTemplateTask getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplateTask)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("198DF1AE") ,com.kingdee.eas.fdc.schedule.IRESchTemplateTask.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IRESchTemplateTask getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplateTask)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("198DF1AE") ,com.kingdee.eas.fdc.schedule.IRESchTemplateTask.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IRESchTemplateTask getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplateTask)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("198DF1AE"));
    }
    public static com.kingdee.eas.fdc.schedule.IRESchTemplateTask getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplateTask)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("198DF1AE"));
    }
}