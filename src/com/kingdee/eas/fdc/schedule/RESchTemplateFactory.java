package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RESchTemplateFactory
{
    private RESchTemplateFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IRESchTemplate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1BD5B489") ,com.kingdee.eas.fdc.schedule.IRESchTemplate.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IRESchTemplate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1BD5B489") ,com.kingdee.eas.fdc.schedule.IRESchTemplate.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IRESchTemplate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1BD5B489"));
    }
    public static com.kingdee.eas.fdc.schedule.IRESchTemplate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1BD5B489"));
    }
}