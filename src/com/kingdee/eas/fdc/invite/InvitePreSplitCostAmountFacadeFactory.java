package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvitePreSplitCostAmountFacadeFactory
{
    private InvitePreSplitCostAmountFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInvitePreSplitCostAmountFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePreSplitCostAmountFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("51EC1423") ,com.kingdee.eas.fdc.invite.IInvitePreSplitCostAmountFacade.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInvitePreSplitCostAmountFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePreSplitCostAmountFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("51EC1423") ,com.kingdee.eas.fdc.invite.IInvitePreSplitCostAmountFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInvitePreSplitCostAmountFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePreSplitCostAmountFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("51EC1423"));
    }
    public static com.kingdee.eas.fdc.invite.IInvitePreSplitCostAmountFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePreSplitCostAmountFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("51EC1423"));
    }
}