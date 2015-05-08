package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RESchTemplateTaskBizTypeFactory
{
    private RESchTemplateTaskBizTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IRESchTemplateTaskBizType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplateTaskBizType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7184C55F") ,com.kingdee.eas.fdc.schedule.IRESchTemplateTaskBizType.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IRESchTemplateTaskBizType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplateTaskBizType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7184C55F") ,com.kingdee.eas.fdc.schedule.IRESchTemplateTaskBizType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IRESchTemplateTaskBizType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplateTaskBizType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7184C55F"));
    }
    public static com.kingdee.eas.fdc.schedule.IRESchTemplateTaskBizType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplateTaskBizType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7184C55F"));
    }
}