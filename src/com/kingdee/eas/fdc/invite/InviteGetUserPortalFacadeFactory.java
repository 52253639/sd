package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteGetUserPortalFacadeFactory
{
    private InviteGetUserPortalFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteGetUserPortalFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteGetUserPortalFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("78B5C29A") ,com.kingdee.eas.fdc.invite.IInviteGetUserPortalFacade.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteGetUserPortalFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteGetUserPortalFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("78B5C29A") ,com.kingdee.eas.fdc.invite.IInviteGetUserPortalFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteGetUserPortalFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteGetUserPortalFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("78B5C29A"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteGetUserPortalFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteGetUserPortalFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("78B5C29A"));
    }
}