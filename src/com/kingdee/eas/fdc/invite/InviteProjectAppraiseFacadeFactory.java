package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteProjectAppraiseFacadeFactory
{
    private InviteProjectAppraiseFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteProjectAppraiseFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteProjectAppraiseFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9B0FA931") ,com.kingdee.eas.fdc.invite.IInviteProjectAppraiseFacade.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteProjectAppraiseFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteProjectAppraiseFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9B0FA931") ,com.kingdee.eas.fdc.invite.IInviteProjectAppraiseFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteProjectAppraiseFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteProjectAppraiseFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9B0FA931"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteProjectAppraiseFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteProjectAppraiseFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9B0FA931"));
    }
}