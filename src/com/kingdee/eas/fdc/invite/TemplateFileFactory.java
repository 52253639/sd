package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TemplateFileFactory
{
    private TemplateFileFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.ITemplateFile getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITemplateFile)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CCDB563A") ,com.kingdee.eas.fdc.invite.ITemplateFile.class);
    }
    
    public static com.kingdee.eas.fdc.invite.ITemplateFile getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITemplateFile)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CCDB563A") ,com.kingdee.eas.fdc.invite.ITemplateFile.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.ITemplateFile getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITemplateFile)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CCDB563A"));
    }
    public static com.kingdee.eas.fdc.invite.ITemplateFile getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITemplateFile)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CCDB563A"));
    }
}