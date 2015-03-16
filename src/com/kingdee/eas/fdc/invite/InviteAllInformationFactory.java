package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteAllInformationFactory
{
    private InviteAllInformationFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteAllInformation getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteAllInformation)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A9E40558") ,com.kingdee.eas.fdc.invite.IInviteAllInformation.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteAllInformation getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteAllInformation)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A9E40558") ,com.kingdee.eas.fdc.invite.IInviteAllInformation.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteAllInformation getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteAllInformation)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A9E40558"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteAllInformation getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteAllInformation)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A9E40558"));
    }
}