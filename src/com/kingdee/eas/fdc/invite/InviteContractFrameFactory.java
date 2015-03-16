package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteContractFrameFactory
{
    private InviteContractFrameFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteContractFrame getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteContractFrame)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DAD9BD4E") ,com.kingdee.eas.fdc.invite.IInviteContractFrame.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteContractFrame getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteContractFrame)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DAD9BD4E") ,com.kingdee.eas.fdc.invite.IInviteContractFrame.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteContractFrame getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteContractFrame)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DAD9BD4E"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteContractFrame getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteContractFrame)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DAD9BD4E"));
    }
}