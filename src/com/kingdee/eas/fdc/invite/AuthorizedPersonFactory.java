package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AuthorizedPersonFactory
{
    private AuthorizedPersonFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IAuthorizedPerson getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAuthorizedPerson)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("403A57F4") ,com.kingdee.eas.fdc.invite.IAuthorizedPerson.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IAuthorizedPerson getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAuthorizedPerson)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("403A57F4") ,com.kingdee.eas.fdc.invite.IAuthorizedPerson.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IAuthorizedPerson getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAuthorizedPerson)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("403A57F4"));
    }
    public static com.kingdee.eas.fdc.invite.IAuthorizedPerson getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IAuthorizedPerson)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("403A57F4"));
    }
}