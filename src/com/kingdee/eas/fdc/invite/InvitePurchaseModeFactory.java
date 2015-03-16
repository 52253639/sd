package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvitePurchaseModeFactory
{
    private InvitePurchaseModeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInvitePurchaseMode getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePurchaseMode)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("89A40A11") ,com.kingdee.eas.fdc.invite.IInvitePurchaseMode.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInvitePurchaseMode getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePurchaseMode)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("89A40A11") ,com.kingdee.eas.fdc.invite.IInvitePurchaseMode.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInvitePurchaseMode getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePurchaseMode)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("89A40A11"));
    }
    public static com.kingdee.eas.fdc.invite.IInvitePurchaseMode getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePurchaseMode)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("89A40A11"));
    }
}