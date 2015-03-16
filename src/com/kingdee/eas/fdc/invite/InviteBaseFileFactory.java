package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteBaseFileFactory
{
    private InviteBaseFileFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteBaseFile getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBaseFile)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("06BA4E3A") ,com.kingdee.eas.fdc.invite.IInviteBaseFile.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteBaseFile getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBaseFile)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("06BA4E3A") ,com.kingdee.eas.fdc.invite.IInviteBaseFile.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteBaseFile getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBaseFile)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("06BA4E3A"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteBaseFile getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteBaseFile)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("06BA4E3A"));
    }
}