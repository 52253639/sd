package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TemplateTaskWithBizTypeFactory
{
    private TemplateTaskWithBizTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITemplateTaskWithBizType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITemplateTaskWithBizType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A5ECA412") ,com.kingdee.eas.fdc.schedule.ITemplateTaskWithBizType.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITemplateTaskWithBizType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITemplateTaskWithBizType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A5ECA412") ,com.kingdee.eas.fdc.schedule.ITemplateTaskWithBizType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITemplateTaskWithBizType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITemplateTaskWithBizType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A5ECA412"));
    }
    public static com.kingdee.eas.fdc.schedule.ITemplateTaskWithBizType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITemplateTaskWithBizType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A5ECA412"));
    }
}