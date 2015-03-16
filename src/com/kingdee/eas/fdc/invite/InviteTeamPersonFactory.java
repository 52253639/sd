package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteTeamPersonFactory
{
    private InviteTeamPersonFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteTeamPerson getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTeamPerson)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("29D31F1F") ,com.kingdee.eas.fdc.invite.IInviteTeamPerson.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteTeamPerson getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTeamPerson)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("29D31F1F") ,com.kingdee.eas.fdc.invite.IInviteTeamPerson.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteTeamPerson getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTeamPerson)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("29D31F1F"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteTeamPerson getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTeamPerson)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("29D31F1F"));
    }
}