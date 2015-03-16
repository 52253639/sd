package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TemplateFileCategoryFactory
{
    private TemplateFileCategoryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.ITemplateFileCategory getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITemplateFileCategory)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5D839D58") ,com.kingdee.eas.fdc.invite.ITemplateFileCategory.class);
    }
    
    public static com.kingdee.eas.fdc.invite.ITemplateFileCategory getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITemplateFileCategory)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5D839D58") ,com.kingdee.eas.fdc.invite.ITemplateFileCategory.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.ITemplateFileCategory getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITemplateFileCategory)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5D839D58"));
    }
    public static com.kingdee.eas.fdc.invite.ITemplateFileCategory getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITemplateFileCategory)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5D839D58"));
    }
}