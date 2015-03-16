package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TemplateTypeFactory
{
    private TemplateTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.ITemplateType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITemplateType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CCE1EFF8") ,com.kingdee.eas.fdc.invite.ITemplateType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.ITemplateType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITemplateType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CCE1EFF8") ,com.kingdee.eas.fdc.invite.ITemplateType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.ITemplateType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITemplateType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CCE1EFF8"));
    }
    public static com.kingdee.eas.fdc.invite.ITemplateType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITemplateType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CCE1EFF8"));
    }
}