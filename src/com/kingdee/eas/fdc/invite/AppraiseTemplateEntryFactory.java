package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AppraiseTemplateEntryFactory
{
    private AppraiseTemplateEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseTemplateEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseTemplateEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("429A7EC9") ,com.kingdee.eas.fdc.invite.IAppraiseTemplateEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IAppraiseTemplateEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseTemplateEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("429A7EC9") ,com.kingdee.eas.fdc.invite.IAppraiseTemplateEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseTemplateEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseTemplateEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("429A7EC9"));
    }
    public static com.kingdee.eas.fdc.invite.IAppraiseTemplateEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAppraiseTemplateEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("429A7EC9"));
    }
}